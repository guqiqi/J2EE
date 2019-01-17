package factory;

import service.OrderService;
import service.UserService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class ServiceFactory {
    private Context context;

    public ServiceFactory() {
        try {
            Hashtable<String, Object> jndiProps = new Hashtable<String, Object>();
            jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");

            context = new InitialContext(jndiProps);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public OrderService getOrderService(){
        OrderService orderService = null;
        try {
            orderService = (OrderService) context.lookup("ejb:/ShoppingCenter/OrderServiceImpl" +
                    "!main.java.service.OrderService");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return orderService;
    }

    public UserService getUserService() {
        UserService userService = null;
        try {
            userService = (UserService) context.lookup("ejb:/ShoppingCenter/UserServiceImpl" +
                    "!main.java.service.UserService");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return userService;
    }
}
