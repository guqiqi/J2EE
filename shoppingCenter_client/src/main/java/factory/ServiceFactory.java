package factory;

import service.OrderService;
import service.UserService;
import serviceImpl.OrderServiceImpl;
import serviceImpl.UserServiceImpl;

public class ServiceFactory {
    public OrderService getOrderService(){
        return new OrderServiceImpl();
    }

    public UserService getUserService(){
        return new UserServiceImpl();
    }
}
