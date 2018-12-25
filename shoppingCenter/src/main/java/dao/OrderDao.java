package dao;

import entity.OrderBean;
import factory.DaoFactory;

public abstract class OrderDao extends DaoFactory {
    public abstract boolean insertOrderBean(OrderBean orderBean);
}
