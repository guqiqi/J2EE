package serviceImpl;

import entity.OrderBean;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    public OrderBean placeOrder(int[] itemIndex) {
        return new OrderBean(300, 200, 100);
    }
}
