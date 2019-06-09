package nju.yummy.serviceImpl;

import nju.yummy.dao.OrderDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.SellerStatisticService;
import nju.yummy.util.Const;
import nju.yummy.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerStatisticServiceImpl implements SellerStatisticService {
    SellerDao sellerDao = new SellerDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();

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
                int index = orderEntity.getFinishTime().getHours()/12;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

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
                int index = (int)(new Date().getTime()-orderEntity.getFinishTime().getTime())/3600/24%7;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

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
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

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
                int index = orderEntity.getFinishTime().getMonth()/4;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

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

        return null;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByHourOfUser(String sellerId) {
        return null;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByWeekOfUser(String sellerId) {
        return null;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByMonthOfUser(String sellerId) {
        return null;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOBySellerByQuaterOfUser(String sellerId) {
        return null;
    }

    @Override
    public PackageAndSingleVO getPackageAndSingleVO(String sellerId) {

        return null;
    }

    @Override
    public List<Integer> getNewCustomerNumber(String sellerId) {
        return null;
    }

    @Override
    public List<BostonVO> getBostonStatistic(String sellerId) {
        return null;
    }

    @Override
    public List<List<Double>> getRetainRate(String sellerId) {
        return null;
    }

    @Override
    public List<List<Double>> getFoodMoneyByHour(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);



        return null;
    }

    @Override
    public List<List<Double>> getFoodMoneyByWeek(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);



        return null;
    }

    @Override
    public List<List<Double>> getFoodMoneyByMonth(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);

        return null;
    }

    @Override
    public List<List<Double>> getFoodCountByHour(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);



        return null;
    }

    @Override
    public List<List<Double>> getFoodCountByWeek(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);

        return null;
    }

    @Override
    public List<List<Double>> getFoodCountByMonth(String sellerId) {
        List<OrderEntity> orderEntities = orderDao.getOrderBySellerId(sellerId);
        List<FoodVO> foodVOS = getFoodVOs(orderEntities);

        return null;
    }

    private List<FoodVO> getFoodVOs(List<OrderEntity> orderEntities){
        List<FoodVO> foodVOS = new ArrayList<>();

        for(OrderEntity orderEntity: orderEntities){
            String[] foodIds = orderEntity.getFoodIds().split(Const.regex);
            String[] foodNumbers = orderEntity.getFoodNumbers().split(Const.regex);

            for(int i = 0; i < foodIds.length; i++){
                foodVOS.add(new FoodVO(orderEntity.getFinishTime(), foodIds[i], Integer.parseInt(foodNumbers[i])));
            }
        }

        return foodVOS;
    }

    private List<Integer> getFoodIdsBySeller(String sellerId){
        List<Integer> foodIds = new ArrayList<>();

        List<FoodEntity> foodEntities = sellerDao.getFoodListBySeller(sellerId);

        for(FoodEntity foodEntity: foodEntities){
            foodIds.add(foodEntity.getFoodId());
        }

        return foodIds;
    }

    private List<List<Double>> getByWeek(List<FoodVO> foodVOS){


        return null;
    }
}
