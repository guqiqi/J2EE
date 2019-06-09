package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.*;
import nju.yummy.service.SellerStatisticService;
import nju.yummy.util.Const;
import nju.yummy.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerStatisticServiceImpl implements SellerStatisticService {
    SellerDao sellerDao = new SellerDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public List<LocationAndNumberVO> getSellerLocationDistribution() {
        List<LocationAndNumberVO> result = new ArrayList<>();
        List<SellerEntity> sellerEntities = sellerDao.getAllSellerEntities();

        for (SellerEntity sellerEntity : sellerEntities) {
            boolean isHave = false;

            for (LocationAndNumberVO locationAndNumberVO : result) {
                if (Math.abs(sellerEntity.getLatitude() - locationAndNumberVO.getLatitude()) < 0.03 &&
                        Math.abs(sellerEntity.getLongitude() - locationAndNumberVO.getLongitude()) < 0.03) {
                    isHave = true;

                    locationAndNumberVO.setNumber(locationAndNumberVO.getNumber() + 1);
                }
            }

            if (!isHave) {
                result.add(new LocationAndNumberVO(sellerEntity.getLatitude(), sellerEntity.getLongitude()));
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySeller(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<OrderPriceVO> orderPriceVOS = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            orderPriceVOS.add(new OrderPriceVO(orderEntity.getOrderId(), orderEntity.getTotalMoney(),
                    orderEntity.getPayMoney(), orderEntity.getPayMoney() * 0.95));
        }

        return orderPriceVOS;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByHour(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = orderEntity.getFinishTime().getHours() / 12;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay() + orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal() + orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit() + orderEntity.getPayMoney() * 0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByWeek(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = (int) (new Date().getTime() - orderEntity.getFinishTime().getTime()) / 3600 / 24 % 7;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay() + orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal() + orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit() + orderEntity.getPayMoney() * 0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByMonth(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = orderEntity.getFinishTime().getMonth();

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay() + orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal() + orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit() + orderEntity.getPayMoney() * 0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByQuater(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = orderEntity.getFinishTime().getMonth() / 4;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay() + orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal() + orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit() + orderEntity.getPayMoney() * 0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public RepurchaseVO getOrderRepurchaseOrderBySeller(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);

        int re = 0;
        List<String> userids = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            if (userids.contains(orderEntity.getEmail())) {
                re++;
            }
        }

        return new RepurchaseVO(orderEntities.size() - re, re);
    }

    @Override
    public RepurchaseVO getOrderRepurchaseUserBySeller(String sellerId) {
        return getOrderRepurchaseOrderBySeller(sellerId);
    }

    @Override
    public List<List<Integer>> getDistanceBySeller(String sellerId) {
        SellerEntity sellerEntity = sellerDao.getSellerEntity(sellerId);
        double sellerLat = sellerEntity.getLatitude();
        double sellerLon = sellerEntity.getLongitude();

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                temp.add(0);
            }
            result.add(temp);
        }

        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        for (OrderEntity orderEntity : orderEntities) {
            int sexIndex = customerDao.getCustomer(orderEntity.getEmail()).getSex();

            AddressEntity addressEntity = customerDao.getAddress(orderEntity.getAddressId());
            int distanceIndex = 0;
            double distance =
                    Math.sqrt(Math.pow((sellerLat - addressEntity.getLatitude()) * 11320, 2) +
                            Math.sqrt(Math.pow((sellerLon - addressEntity.getLongitude()) * 10000.0, 2)));
            if (distance > 500)
                distanceIndex++;
            if (distance > 1000)
                distanceIndex++;
            if (distance > 2000)
                distanceIndex++;
            if (distance > 4000)
                distanceIndex++;

            result.get(sexIndex).set(distanceIndex, result.get(sexIndex).get(distanceIndex) + 1);
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByHourOfUser(String sellerId) {
        return getOrderPriceVOBySellerByHour(sellerId);
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByWeekOfUser(String sellerId) {
        return getOrderPriceVOBySellerByWeek(sellerId);
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByMonthOfUser(String sellerId) {
        return getOrderPriceVOBySellerByMonth(sellerId);
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByQuaterOfUser(String sellerId) {
        return getOrderPriceVOBySellerByQuater(sellerId);
    }

    @Override
    public PackageAndSingleVO getPackageAndSingleVO(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);

        PackageAndSingleVO packageAndSingleVO = new PackageAndSingleVO(0, 0);

        // 组合优惠
        List<DiscountTableEntity> discountTableEntities = sellerDao.getDiscountBySeller(sellerId);

        for (OrderEntity orderEntity : orderEntities) {
            String[] foodids = orderEntity.getFoodIds().split(Const.regex);
            String[] foodNumbers = orderEntity.getFoodNumbers().split(Const.regex);

            List<Integer> foods = new ArrayList<>();
            List<Integer> amount = new ArrayList<>();

            for (int i = 0; i < foodids.length; i++) {
                foods.add(Integer.parseInt(foodids[i]));
                amount.add(Integer.parseInt(foodNumbers[i]));
            }

            for (int i = 0; i < discountTableEntities.size(); i++) {
                String[] ids = discountTableEntities.get(i).getFoodIds().split(Const.regex);

                // 应该含有的商品编号列表
                List<Integer> foodIds = new ArrayList<>();
                // 每找到一个满足条件的就要删去一个，以此判断是否满足条件
                List<Integer> toFindFoodIds = new ArrayList<>();
                for (String str : ids) {
                    foodIds.add(Integer.parseInt(str));
                    toFindFoodIds.add(Integer.parseInt(str));
                }

                // 最多有几组满足条件
                int maxSize = 0;
                for (int j = 0; j < foods.size(); j++) {
                    if (toFindFoodIds.contains(foods.get(j))) {
                        maxSize = maxSize > amount.get(j) ? maxSize : amount.get(j);
                        // 这边需要看看是删除索引还是数字
                        toFindFoodIds.remove(toFindFoodIds.indexOf(foods.get(j)));
                    }
                }

                // 查看是否有满足条件的商品组合
                if (toFindFoodIds.isEmpty()) {
                    packageAndSingleVO.setPackageNumber(packageAndSingleVO.getPackageNumber() * maxSize);

                    for (int m = 0; m < foodIds.size(); m++) {
                        packageAndSingleVO.setSingleVO(packageAndSingleVO.getSingleVO() + amount.get(foods.indexOf(foodIds.get(m))) - maxSize);
                    }
                }
            }
        }

        return packageAndSingleVO;
    }

    @Override
    public List<Integer> getNewCustomerNumber(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<String> customerIds = new ArrayList<>();
        // 当前商家所有商品id
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            result.add(0);
        }

        for (OrderEntity orderEntity : orderEntities) {
            // 第一次购买
            if (!customerIds.contains(orderEntity.getEmail())) {
                String[] foods = orderEntity.getFoodIds().split(Const.regex);
                String[] foodNumber = orderEntity.getFoodNumbers().split(Const.regex);

                for (int i = 0; i < foods.length; i++) {
                    int index = foodIds.indexOf(foods[i]);
                    result.set(index, result.get(index) + Integer.parseInt(foodNumber[i]));
                }
            }
        }

        return result;
    }

    @Override
    public List<BostonVO> getBostonStatistic(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<BostonVO> result = new ArrayList<>();

        List<Integer> last = new ArrayList<>();
        List<Integer> now = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            result.add(new BostonVO(sellerDao.getFoodById(foodIds.get(i)).getName(),
                    sellerDao.getFoodById(foodIds.get(i)).getMoney()));
            last.add(0);
            last.add(0);
        }

        for(FoodVO foodVO: foodVOS){
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            if(foodVO.getDate().getMonth() == new Date().getMonth()){
                now.set(index, now.get(index)+foodVO.getNumber());
            }
            else {
                last.set(index, last.get(index)+foodVO.getNumber());
            }
        }

        for (int i = 0; i < foodIds.size(); i++) {
            result.get(i).setNumber(now.get(i)+last.get(i));
            result.get(i).setRate(last.get(i)==0?1.0:now.get(i)/last.get(i));
        }

        return result;
    }

    @Override
    public List<List<Double>> getRetainRate(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<String> customerIds = new ArrayList<>();
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; j < 2; i++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (OrderEntity orderEntity : orderEntities) {
            // 不是第一次购买，有留存度
            if (!customerIds.contains(orderEntity.getEmail())) {
                String[] foods = orderEntity.getFoodIds().split(Const.regex);
                String[] foodNumber = orderEntity.getFoodNumbers().split(Const.regex);

                for (int i = 0; i < foods.length; i++) {
                    int index = foodIds.indexOf(foods[i]);
                    result.get(foodIds.indexOf(foods[i])).set(index,
                            result.get(foodIds.indexOf(foods[i])).get(index) + Integer.parseInt(foodNumber[i]));
                }
            }
        }

        return result;
    }

    @Override
    public List<List<Double>> getFoodMoneyByHour(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; i < 12; j++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (FoodVO foodVO : foodVOS) {
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            int timeIndex = foodVO.getDate().getHours() / 12;

            result.get(index).set(timeIndex, result.get(index).get(timeIndex) + foodVO.getNumber() * foodVO.getMoney());
        }

        return result;
    }

    @Override
    public List<List<Double>> getFoodMoneyByWeek(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; i < 7; j++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (FoodVO foodVO : foodVOS) {
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            int timeIndex = (int) (new Date().getTime() - foodVO.getDate().getTime()) / 3600 / 24 % 7;

            result.get(index).set(timeIndex, result.get(index).get(timeIndex) + foodVO.getNumber() * foodVO.getMoney());
        }

        return result;
    }

    @Override
    public List<List<Double>> getFoodMoneyByMonth(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; i < 12; j++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (FoodVO foodVO : foodVOS) {
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            int timeIndex = foodVO.getDate().getMonth();

            result.get(index).set(timeIndex, result.get(index).get(timeIndex) + foodVO.getNumber() * foodVO.getMoney());
        }

        return result;
    }

    @Override
    public List<List<Double>> getFoodCountByHour(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; i < 12; j++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (FoodVO foodVO : foodVOS) {
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            int timeIndex = foodVO.getDate().getHours() / 12;

            result.get(index).set(timeIndex, result.get(index).get(timeIndex) + foodVO.getNumber());
        }

        return result;
    }

    @Override
    public List<List<Double>> getFoodCountByWeek(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; i < 7; j++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (FoodVO foodVO : foodVOS) {
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            int timeIndex = (int) (new Date().getTime() - foodVO.getDate().getTime()) / 3600 / 24 % 7;

            result.get(index).set(timeIndex, result.get(index).get(timeIndex) + foodVO.getNumber());
        }

        return result;
    }

    @Override
    public List<List<Double>> getFoodCountByMonth(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);
        List<Integer> foodIds = getFoodIdsBySeller(sellerId);

        List<List<Double>> result = new ArrayList<>();

        for (int i = 0; i < foodIds.size(); i++) {
            List<Double> temp = new ArrayList<>();

            for (int j = 0; i < 12; j++) {
                temp.add(0.0);
            }

            result.add(temp);
        }

        for (FoodVO foodVO : foodVOS) {
            int index = foodIds.indexOf(Integer.parseInt(foodVO.getId()));
            int timeIndex = foodVO.getDate().getMonth();

            result.get(index).set(timeIndex, result.get(index).get(timeIndex) + foodVO.getNumber());
        }

        return result;
    }

    private List<FoodVO> getFoodVOs(List<OrderEntity> orderEntities) {
        List<FoodVO> foodVOS = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            String[] foodIds = orderEntity.getFoodIds().split(Const.regex);
            String[] foodNumbers = orderEntity.getFoodNumbers().split(Const.regex);

            for (int i = 0; i < foodIds.length; i++) {
                foodVOS.add(new FoodVO(orderEntity.getFinishTime(), foodIds[i], Integer.parseInt(foodNumbers[i]),
                        sellerDao.getFoodById(Integer.parseInt(foodNumbers[i])).getMoney()));
            }
        }

        return foodVOS;
    }

    private List<Integer> getFoodIdsBySeller(String sellerId) {
        List<Integer> foodIds = new ArrayList<>();

        List<FoodEntity> foodEntities = sellerDao.getFoodListBySeller(sellerId);

        for (FoodEntity foodEntity : foodEntities) {
            foodIds.add(foodEntity.getFoodId());
        }

        return foodIds;
    }
}
