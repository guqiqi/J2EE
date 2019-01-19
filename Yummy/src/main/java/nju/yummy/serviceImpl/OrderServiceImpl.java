package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.CustomerEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.service.OrderService;
import nju.yummy.util.DateToTimestamp;
import nju.yummy.util.OrderStatus;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private SellerDao sellerDao;
    private CustomerDao customerDao;

    public OrderServiceImpl() {
        orderDao = new OrderDaoImpl();
        sellerDao = new SellerDaoImpl();
        customerDao = new CustomerDaoImpl();
    }

    @Override
    public OrderEntity prePlaceOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount) {
        OrderEntity orderEntity = new OrderEntity(getTotalMoney(foods, amount),
                getDiscountMoney(email, sellerId, foods, amount));

        return orderEntity;
    }

    @Override
    public String placeOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount, Date reachTime, int addressId) {
        String orderId = generateOrderId();
        OrderEntity orderEntity = new OrderEntity(orderId, email, sellerId, getTotalMoney(foods, amount),
                getDiscountMoney(email, sellerId, foods, amount), DateToTimestamp.toTimeStamp(reachTime));

        orderDao.addOrder(orderEntity);
        return orderId;
    }

    // 计算没有折扣的总价
    private double getTotalMoney(List<Integer> foods, List<Integer> amount){
        double total = 0.0;
        FoodEntity foodEntity;

        for (int i = 0; i < foods.size(); i++) {
            foodEntity = sellerDao.getFoodById(foods.get(i));
            total += foodEntity.getMoney() * amount.get(i);
        }

        return total;
    }

    private double getDiscountMoney(String email, String sellerId, List<Integer> foods, List<Integer> amount){
        // TODO 计算折后价
        return 0.0;
    }

    private String generateOrderId(){
        String str = generate11alphabets();
        List<OrderEntity> orderEntities = orderDao.getAllOrders();
        Set<String> orderIds = new HashSet<>();
        for (OrderEntity orderEntity : orderEntities) {
            orderIds.add(orderEntity.getOrderId());
        }
        while (orderIds.contains(str)){
            str = generate11alphabets();
        }

        return str;
    }

    private String generate11alphabets(){
        String str = "";
        for (int i = 0; i < 24; i++) {
            double random = Math.random() * 10;

            str = str + (int)random;
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
                    orderDao.updateOrderStatus(orderId, OrderStatus.TOBEDELIVERED);
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
                return customerDao.updateCustomer(customerEntity) && orderDao.updateOrderStatus(orderId, OrderStatus.CANCEL);
            case 2: // 开始配送，返回50%钱
                customerEntity = customerDao.getCustomer(orderEntity.getEmail());
                customerEntity.setLeftMoney(customerEntity.getLeftMoney() + orderEntity.getPayMoney() * 0.5);
                return customerDao.updateCustomer(customerEntity) && orderDao.updateOrderStatus(orderId, OrderStatus.CANCEL);
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
