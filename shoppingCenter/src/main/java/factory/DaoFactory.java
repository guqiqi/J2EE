package factory;

import dao.ItemDao;
import dao.OrderDao;
import dao.UserDao;
import daoImpl.ItemDaoImpl;
import daoImpl.OrderDaoImpl;
import daoImpl.UserDaoImpl;

public class DaoFactory {
    public DaoFactory(){}

    public ItemDao getItemDao(){
        return new ItemDaoImpl();
    }

    public OrderDao getOrderDao(){
        return new OrderDaoImpl();
    }

    public UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
