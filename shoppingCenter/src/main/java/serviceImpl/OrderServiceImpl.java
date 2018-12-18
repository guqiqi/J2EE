package serviceImpl;

import entity.OrderBean;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    // 成功返回一个orderB  ean对象，失败返回null
    public OrderBean placeOrder(int[] itemIndex) {
        // TODO
        return new OrderBean(300, 200, 100);
    }
}
