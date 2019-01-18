package nju.yummy.service;

import nju.yummy.entity.OrderEntity;

import java.util.Date;
import java.util.List;

public interface OrderService {
    /**
     * 用户预下单，数据库不变，返回折后价
     * @param email 邮箱
     * @param sellerId 餐厅编号
     * @param foods 食物id列表
     * @param amount 食物数量列表
     * @return 订单实际价格
     */
    public double prePlaceOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount);

    /**
     * 用户下单
     * @param email 邮箱
     * @param sellerId 餐厅编号
     * @param foods 食物id列表
     * @param amount 食物数量列表
     * @param reachTime 预期送达时间
     * @param addressId 地址id
     * @return 订单id，如果没有下单成功则返回null（可能是库存不够了）
     */
    public String placeOrder(String email, String sellerId, List<Integer> foods, List<Integer> amount, Date reachTime,
                             int addressId);

    /**
     * 用户支付订单
     * @param orderId 订单编号
     * @return 是否支付成功，失败应该是余额不够了
     */
    public boolean pay(String orderId);

    /**
     * 取消订单
     * @param orderId 订单编号
     * @return 是否取消成功
     */
    public boolean cancel(String orderId);

    /**
     * 确认收货或者确认送达
     * @param orderId 订单编号
     * @return 是否成功
     */
    public boolean confirmReceive(String orderId);

    /**
     * 开始配送
     * @param orderId 订单编号
     * @return 是否成功
     */
    public boolean startDelive(String orderId);

    /**
     * 得到用户所有订单
     * @param email 用户邮箱
     * @return 订单列表
     */
    public List<OrderEntity> getCustomerOrders(String email);

    /**
     * 得到所有商家订单
     * @param sellerId 商家编号
     * @return 订单列表
     */
    public List<OrderEntity> getSellerOrders(String sellerId);

    /**
     * 得到一个订单详情
     * @param orderId 订单编号
     * @return 订单信息
     */
    public OrderEntity getOrder(String orderId);
}
