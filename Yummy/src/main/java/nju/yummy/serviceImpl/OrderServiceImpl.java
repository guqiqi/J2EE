package nju.yummy.serviceImpl;

import nju.yummy.entity.OrderEntity;
import nju.yummy.service.OrderService;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public double prePlaceOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount) {
        return 0;
    }

    @Override
    public String placeOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount, Date reachTime, int addressId) {
        return null;
    }

    @Override
    public boolean pay(String orderId) {
        return false;
    }

    @Override
    public boolean cancel(String orderId) {
        return false;
    }

    @Override
    public boolean confirmReceive(String orderId) {
        return false;
    }

    @Override
    public boolean startDelive(String orderId) {
        return false;
    }

    @Override
    public List<OrderEntity> getCustomerOrders(String email) {
        return null;
    }

    @Override
    public List<OrderEntity> getSellerOrders(String sellerId) {
        return null;
    }

    @Override
    public OrderEntity getOrder(String orderId) {
        return null;
    }
}
