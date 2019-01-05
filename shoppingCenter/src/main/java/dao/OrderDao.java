package dao;

import entity.OrderBean;
import factory.DaoFactory;

public interface OrderDao{
    boolean insertOrderBean(OrderBean orderBean);
}
