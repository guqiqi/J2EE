package service;

import entity.Item;
import entity.OrderBean;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface OrderService {
    public OrderBean placeOrder(List<String> selectedList, String username);

    public List<Item> getItems();
}
