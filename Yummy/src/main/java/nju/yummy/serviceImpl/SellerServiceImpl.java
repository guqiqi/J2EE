package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.*;
import nju.yummy.service.SellerService;
import nju.yummy.util.Const;
import nju.yummy.util.DateToTimestamp;
import nju.yummy.util.SellerStatus;
import nju.yummy.vo.SellerCostVO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class SellerServiceImpl implements SellerService {
    private SellerDao sellerDao;
    private OrderDao orderDao;
    private CustomerDao customerDao;

    public SellerServiceImpl() {
        sellerDao = new SellerDaoImpl();
        orderDao = new OrderDaoImpl();
        customerDao = new CustomerDaoImpl();
    }

    @Override
    public String login(String sellerId, String password) {
        SellerEntity sellerEntity = sellerDao.getSellerEntity(sellerId);
        if (sellerEntity == null)
            return "该账号不存在";
        else if (sellerEntity.getPassword().equals(password)) {
            switch (sellerEntity.getStatus()) {
                case 0:
                    return "您的信息没有初始化,请先初始化商家信息";
                case 1:
                    return "您的信息正在加速审核中，请耐心等待";
                default:
                    return "成功登陆";
            }
        } else
            return "账号密码不匹配";
    }

    @Override
    public String register(String password, String name, String type, String address, String phone, String startHour,
                           String endHour, String icon) {
        String sellerId = generateSellerId();
        SellerEntity sellerEntity = new SellerEntity(sellerId, password, name, type, address, phone, startHour,
                endHour, 1, "", "1" + Const.regix + "1" + Const.regix + "1", icon);
        sellerDao.addSeller(sellerEntity);
        return sellerId;
    }

    private String generateSellerId() {
        String str = generate7alphabets();
        List<SellerEntity> sellerEntities = sellerDao.getAllSellerEntities();
        Set<String> sellerIds = new HashSet<>();
        for (SellerEntity sellerEntity : sellerEntities) {
            sellerIds.add(sellerEntity.getSellerId());
        }
        while (sellerIds.contains(str)) {
            str = generate7alphabets();
        }

        return str;
    }

    private String generate7alphabets() {
        String str = "";
        for (int i = 0; i < 7; i++) {
            double random = Math.random() * 52;
            System.out.println(random / 26);

            str = str + (char) (random / 26 > 1 ? 'a' + random % 26 : 'A' + random % 26);
        }
        return str;
    }

    @Override
    public boolean verifySeller(String sellerId, boolean isPass) {
        if (isPass)
            return sellerDao.verifySeller(sellerId, SellerStatus.NORMAL);
        else
            return sellerDao.verifySeller(sellerId, SellerStatus.TOMODIFY);
    }

    @Override
    public boolean modifyInfo(String sellerId, String password, String name, String type, String address, String phone,
                              String startHour, String endHour, String icon, int status) {
        SellerEntity sellerEntity1 = sellerDao.getSellerEntity(sellerId);
        SellerEntity sellerEntity = new SellerEntity(sellerId, password, name, type, address, phone, startHour,
                endHour, status, sellerEntity1.getFoodType(), sellerEntity1.getDiscount(), icon);

        sellerEntity.setOrderCount(sellerEntity1.getOrderCount());
        return sellerDao.updateSeller(sellerEntity);
    }

    @Override
    public SellerEntity getSellerInfo(String sellerId) {
        return sellerDao.getSellerEntity(sellerId);
    }

    @Override
    public List<SellerEntity> getAllSellerEntities() {
        return sellerDao.getAllSellerEntities();
    }

    @Override
    public boolean addFood(String sellerId, String name, String photo, String foodType, double money, double discountMonty, Date startTime, Date endTime, int stock, String description) {
        FoodEntity foodEntity = new FoodEntity(sellerId, name, photo, foodType, money, discountMonty,
                DateToTimestamp.toTimeStamp(startTime), DateToTimestamp.toTimeStamp(endTime), stock, description);
        return sellerDao.addFood(foodEntity);
    }

    @Override
    public boolean modifyFood(int foodId, String sellerId, String name, String photo, String foodType, double money,
                              double discountMonty, Date startTime, Date endTime, int stock, String description) {
        FoodEntity foodEntity = new FoodEntity(sellerId, name, photo, foodType, money, discountMonty,
                DateToTimestamp.toTimeStamp(startTime), DateToTimestamp.toTimeStamp(endTime), stock, description);
        foodEntity.setFoodId(foodId);
        return sellerDao.updateFood(foodEntity);
    }

    @Override
    public FoodEntity getFoodInfo(int foodId) {
        return sellerDao.getFoodById(foodId);
    }

    @Override
    public boolean deleteFood(List<Integer> foodIds) {
        return sellerDao.deleteFood(foodIds);
    }

    @Override
    public List<FoodEntity> getFoodListBySeller(String sellerId) {
        return sellerDao.getFoodListBySeller(sellerId);
    }

    @Override
    public boolean modifyFoodType(String sellerId, String foodType) {
        SellerEntity sellerEntity = sellerDao.getSellerEntity(sellerId);
        sellerEntity.setFoodType(foodType);

        return sellerDao.updateSeller(sellerEntity);
    }

    @Override
    public boolean modifyCustomerDiscount(String sellerId, String discount) {
        SellerEntity sellerEntity = sellerDao.getSellerEntity(sellerId);
        sellerEntity.setDiscount(discount);

        return sellerDao.updateSeller(sellerEntity);
    }

    @Override
    public List<DiscountTableEntity> getDiscountBySeller(String sellerId) {
        return sellerDao.getDiscountBySeller(sellerId);
    }

    @Override
    public boolean addGroupDiscount(String sellerId, String foodIds, String foodNames, double discountMoney,
                                    double money, Date startTime, Date endTime) {
        DiscountTableEntity discountTableEntity = new DiscountTableEntity(sellerId, discountMoney, money, foodIds,
                foodNames, startTime, endTime);
        return sellerDao.addGroupDiscount(discountTableEntity);
    }

    @Override
    public boolean modifyGroupDiscount(Integer discountId, String sellerId, String foodIds, String foodNames, double discountMoney, double money, Date startTime, Date endTime) {
        DiscountTableEntity discountTableEntity = new DiscountTableEntity(discountId, sellerId, discountMoney, money,
                foodIds, foodNames, startTime, endTime);

        return sellerDao.updateGroupDiscount(discountTableEntity);
    }

    @Override
    public boolean deleteGroupDiscount(List<Integer> discountIds) {
        return sellerDao.deleteGroupDiscount(discountIds);
    }

    @Override
    public List<SellerCostVO> getCostByCustomer(String sellerId) {
        List<SellerCostVO> customerCostList = new ArrayList<>();
        List<String> customerNames = new ArrayList<>();

        List<OrderEntity> orderEntityList = orderDao.getOrderBySellerId(sellerId);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                CustomerEntity customerEntity = customerDao.getCustomer(orderEntity.getEmail());

                if (customerCostList.contains(customerEntity.getUsername())) {
                    SellerCostVO customerCostVO =
                            customerCostList.get(customerNames.indexOf(customerEntity.getUsername()));
                    customerCostVO.addCost(orderEntity.getPayMoney());
                    customerCostList.set(customerCostList.indexOf(customerEntity.getUsername()), customerCostVO);
                } else {
                    customerNames.add(customerEntity.getUsername());
                    customerCostList.add(new SellerCostVO(customerEntity.getUsername(), orderEntity.getPayMoney()));
                }
            }
        }

        return customerCostList;
    }

    @Override
    public double[] getCostByHour(String sellerId) {
        double[] result = new double[24];

        List<OrderEntity> orderEntityList = orderDao.getOrderBySellerId(sellerId);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                Timestamp placeTime = orderEntity.getPlaceTime();
                SimpleDateFormat sd = new SimpleDateFormat("HH");
                int hour = Integer.parseInt(sd.format(placeTime)) - 1;
                result[hour] += orderEntity.getPayMoney();
            }
        }

        return result;
    }

    @Override
    public double[] getCostByTime(String sellerId) {
        double[] result = new double[3];

        List<OrderEntity> orderEntityList = orderDao.getOrderBySellerId(sellerId);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                long minutes = (System.currentTimeMillis() - orderEntity.getPlaceTime().getTime()) / (1000 * 60);

                if (minutes <= 7 * 24 * 60)
                    result[0] += orderEntity.getPayMoney();
                if (minutes <= 30 * 24 * 60)
                    result[1] += orderEntity.getPayMoney();
                if (minutes <= 365 * 24 * 60)
                    result[2] += orderEntity.getPayMoney();
            }
        }

        return result;
    }

    @Override
    public int getCustomerNumber(String sellerId) {
        List<String> customerEmails = new ArrayList<>();

        List<OrderEntity> orderEntityList = orderDao.getOrderBySellerId(sellerId);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                if(!customerEmails.contains(orderEntity.getEmail()))
                    customerEmails.add(orderEntity.getEmail());
            }
        }
        return customerEmails.size();
    }

    @Override
    public double getTotalSeller(String sellerId) {
        double total = 0;

        List<OrderEntity> orderEntityList = orderDao.getOrderBySellerId(sellerId);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                total += orderEntity.getPayMoney();
            }
        }
        return total;
    }

    @Override
    public List<SellerCostVO> getCancelByUser(String sellerId) {
        // TODO
        return null;
    }
}
