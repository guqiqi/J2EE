package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.RecordDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.RecordDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.*;
import nju.yummy.service.OrderService;
import nju.yummy.util.Const;
import nju.yummy.util.DateToTimestamp;
import nju.yummy.util.OrderStatus;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private SellerDao sellerDao;
    private CustomerDao customerDao;
    private RecordDao recordDao;

    public OrderServiceImpl() {
        orderDao = new OrderDaoImpl();
        sellerDao = new SellerDaoImpl();
        customerDao = new CustomerDaoImpl();
        recordDao = new RecordDaoImpl();
    }

    @Override
    public OrderEntity prePlaceOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount) {
        if (isFoodEnough(foods, amount)) {
            return new OrderEntity(getTotalMoney(foods, amount),
                    getDiscountMoney(email, sellerId, foods, amount));
        } else {
            return null;
        }
    }

    @Override
    public OrderEntity placeOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount, Date reachTime,
                                  int addressId) {
        String orderId = generateOrderId();
        OrderEntity orderEntity = new OrderEntity(orderId, email, sellerId, getTotalMoney(foods, amount),
                getDiscountMoney(email, sellerId, foods, amount), DateToTimestamp.toTimeStamp(reachTime), addressId);

        orderDao.addOrder(orderEntity);

        // 店铺销量增加
        SellerEntity sellerEntity = sellerDao.getSellerEntity(sellerId);
        sellerEntity.setOrderCount(sellerEntity.getOrderCount() + 1);
        sellerDao.updateSeller(sellerEntity);

        return orderEntity;
    }

    private boolean isFoodEnough(List<Integer> foods, List<Integer> amount) {
        FoodEntity foodEntity;
        for (int i = 0; i < foods.size(); i++) {
            foodEntity = sellerDao.getFoodById(foods.get(i));
            if (foodEntity.getStock() < amount.get(i))
                return false;
        }
        return true;
    }

    // 计算没有折扣的总价
    private double getTotalMoney(List<Integer> foods, List<Integer> amount) {
        double total = 0.0;
        FoodEntity foodEntity;

        for (int i = 0; i < foods.size(); i++) {
            foodEntity = sellerDao.getFoodById(foods.get(i));
            total += foodEntity.getMoney() * amount.get(i);
        }

        return total;
    }

    private double getDiscountMoney(String email, String sellerId, List<Integer> foods, List<Integer> amount) {
        // 计算折后价
        double total = 0.0;

        double point = customerDao.getCustomer(email).getPoint();

        int level = 0;
        if (point >= 100)
            level = 1;
        if (point >= 500)
            level = 2;

        // 店铺给相应等级的会员优惠
        double customerDiscount =
                Double.parseDouble(sellerDao.getSellerEntity(sellerId).getDiscount().split(Const.regix)[level]);

        // yummy平台给用户的会员优惠
        double yummyDiscount = Const.discount[level];

        // 组合优惠
        List<DiscountTableEntity> discountTableEntities = sellerDao.getDiscountBySeller(sellerId);

        for (int i = 0; i < discountTableEntities.size(); i++) {
            String[] ids = discountTableEntities.get(i).getFoodIds().split(Const.regix);

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
                total += discountTableEntities.get(i).getDiscountMoney() * maxSize;

                for (int m = 0; m < foodIds.size(); m++) {
                    amount.set(foods.indexOf(foodIds.get(m)), amount.get(foods.indexOf(foodIds.get(m))) - maxSize);
                }
            }
        }

        // 没有使用组合优惠
        FoodEntity foodEntity;
        for (int i = 0; i < foods.size(); i++) {
            foodEntity = sellerDao.getFoodById(foods.get(i));
            total += foodEntity.getDiscountMoney() * amount.get(i);
        }

        // 组合优惠结束后使用店铺优惠和平台优惠
        return total * customerDiscount * yummyDiscount;
    }

    private String generateOrderId() {
        String str = generate11alphabets();
        List<OrderEntity> orderEntities = orderDao.getAllOrders();
        Set<String> orderIds = new HashSet<>();
        for (OrderEntity orderEntity : orderEntities) {
            orderIds.add(orderEntity.getOrderId());
        }
        while (orderIds.contains(str)) {
            str = generate11alphabets();
        }

        return str;
    }

    private String generate11alphabets() {
        String str = "";
        for (int i = 0; i < 24; i++) {
            double random = Math.random() * 10;

            str = str + (int) random;
        }
        return str;
    }

    @Override
    public boolean pay(String orderId) {
        // 判断钱够不够，够的话需要付款，积累积分，修改订单状态
        OrderEntity orderEntity = orderDao.getOrderById(orderId);
        CustomerEntity customerEntity = customerDao.getCustomer(orderEntity.getEmail());

        double payMoney = orderEntity.getPayMoney();
        double leftMoney = customerEntity.getLeftMoney();

        if (leftMoney >= payMoney) {
            customerEntity.setLeftMoney(leftMoney - payMoney);
            customerEntity.setPoint(customerEntity.getPoint() + payMoney);
            return customerDao.updateCustomer(customerEntity) &&
                    orderDao.updateOrderStatus(orderId, OrderStatus.TOBEDELIVERED) &&
                    recordDao.insertRecord(new PayRecordEntity((byte) 1, customerEntity.getEmail(), payMoney, (byte) 1));
        } else
            return false;
    }

    @Override
    public boolean cancel(String orderId) {
        // 根据订单状态
        OrderEntity orderEntity = orderDao.getOrderById(orderId);
        CustomerEntity customerEntity;

        switch (orderEntity.getStatus()) {
            case -1: // 已经取消不能退订
                return false;
            case 0: // 尚未付款，直接取消订单即可
                return orderDao.updateOrderStatus(orderId, OrderStatus.CANCEL);
            case 1: // 尚未配送，返回95%钱
                customerEntity = customerDao.getCustomer(orderEntity.getEmail());
                customerEntity.setLeftMoney(customerEntity.getLeftMoney() + orderEntity.getPayMoney() * 0.95);
                return customerDao.updateCustomer(customerEntity) && orderDao.updateOrderStatus(orderId,
                        OrderStatus.CANCEL) && recordDao.insertRecord(new PayRecordEntity((byte) 1,
                        customerEntity.getEmail(), orderEntity.getPayMoney() * 0.95, (byte) 0));
            case 2: // 开始配送，返回50%钱
                customerEntity = customerDao.getCustomer(orderEntity.getEmail());
                customerEntity.setLeftMoney(customerEntity.getLeftMoney() + orderEntity.getPayMoney() * 0.5);
                return customerDao.updateCustomer(customerEntity) && orderDao.updateOrderStatus(orderId,
                        OrderStatus.CANCEL) && recordDao.insertRecord(new PayRecordEntity((byte) 1,
                        customerEntity.getEmail(), orderEntity.getPayMoney() * 0.5, (byte) 0));
            case 3: // 已经完成不能退订
                return false;
        }

        return false;
    }

    @Override
    public boolean confirmReceive(String orderId) {
        // 确认收货，用户加积分
        OrderEntity orderEntity = orderDao.getOrderById(orderId);
        CustomerEntity customerEntity = customerDao.getCustomer(orderEntity.getEmail());

        customerEntity.setPoint(customerEntity.getPoint() + orderEntity.getPayMoney());

        return orderDao.updateOrderStatus(orderId, OrderStatus.FINISHED) && customerDao.updateCustomer(customerEntity);
    }

    @Override
    public boolean startDeliver(String orderId) {
        return orderDao.updateOrderStatus(orderId, OrderStatus.DEVERING);
    }

    @Override
    public List<OrderEntity> getCustomerOrders(String email) {
        return orderDao.getOrderByEmail(email);
    }

    @Override
    public List<OrderEntity> getSellerOrders(String sellerId) {
        return orderDao.getOrderBySellerId(sellerId);
    }

    @Override
    public OrderEntity getOrder(String orderId) {
        return orderDao.getOrderById(orderId);
    }
}
