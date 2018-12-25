package service;

import entity.Item;
import entity.OrderBean;

import java.util.List;

public interface OrderService {
    public OrderBean placeOrder(List<String> selectedList, String username);

    public List<Item> getItems();
}
