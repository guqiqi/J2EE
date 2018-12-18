package service;

import entity.OrderBean;

public interface OrderService {
    public OrderBean placeOrder(int[] itemIndex);
}
