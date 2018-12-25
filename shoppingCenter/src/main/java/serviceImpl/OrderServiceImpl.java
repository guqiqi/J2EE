package serviceImpl;

import daoImpl.ItemDaoImpl;
import daoImpl.OrderDaoImpl;
import entity.Item;
import entity.OrderBean;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    // 成功返回一个orderBean对象，失败返回null
    public OrderBean placeOrder(List<String> selectedList, String username) {
        List<Item> itemList = new ItemDaoImpl().getAllItem();

        double total = 0.0;

        System.out.println(selectedList);
        for (Item item : itemList) {
            for (int i = 0; i < selectedList.size(); i++) {
                if (selectedList.contains(item.getName())) {
                    total += item.getPrice();
                    selectedList.remove(item.getName());
                }
            }
        }

        OrderBean orderBean = new OrderBean(total, total > 15 ? total * 0.05 : 0.0, username);

        if (new OrderDaoImpl().insertOrderBean(orderBean))
            return orderBean;

        return null;
    }

    // 得到所有商品列表
    public List<Item> getItems() {
        return new ItemDaoImpl().getAllItem();
    }
}
