package nju.yummy.dao;

import nju.yummy.entity.OrderEntity;
import nju.yummy.util.OrderStatus;

import java.util.List;

public interface OrderDao {
    public boolean addOrder(OrderEntity orderEntity);

    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus);

    public OrderEntity getOrderById(String orderId);

    public List<OrderEntity> getOrderByEmail(String email);

    public List<OrderEntity> getOrderBySellerId(String sellerId);

    public List<OrderEntity> getAllOrders();
}
