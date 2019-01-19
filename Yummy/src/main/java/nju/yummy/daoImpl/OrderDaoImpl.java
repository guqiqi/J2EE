package nju.yummy.daoImpl;

import nju.yummy.dao.OrderDao;
import nju.yummy.entity.OrderEntity;
import nju.yummy.util.OrderStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private DaoUtil daoUtil = new DaoUtil();

    @Override
    public boolean addOrder(OrderEntity orderEntity) {
        return daoUtil.add(orderEntity);
    }

    @Override
    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query;
        if(orderStatus.equals(OrderStatus.FINISHED)){
            query = session.createQuery("update OrderEntity o set o.status = :newStatus where orderId = :orderId and " +
                    "o.finishTime = :finishTime");
            query.setParameter("newStatus", orderStatus.getIndex());
            query.setParameter("orderId", orderId);
            query.setParameter("finishTime", new Date());
        }
        else {
            query = session.createQuery("update OrderEntity o set o.status = :newStatus where orderId = :orderId");
            query.setParameter("newStatus", orderStatus.getIndex());
            query.setParameter("orderId", orderId);
        }

        query.executeUpdate();

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public OrderEntity getOrderById(String orderId) {
        /* 如果订单不存在则返回null */
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        OrderEntity orderEntity = session.get(OrderEntity.class, orderId);

        tx.commit();

        session.close();
        return orderEntity;
    }

    @Override
    public List<OrderEntity> getOrderByEmail(String email) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select order from OrderEntity order where email=:email");
        query.setParameter("email", email);

        List<OrderEntity> orderEntities = query.list();

        tx.commit();
        session.close();

        return orderEntities;
    }

    @Override
    public List<OrderEntity> getOrderBySellerId(String sellerId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select order from OrderEntity order where sellerId=:sellerId");
        query.setParameter("sellerId", sellerId);

        List<OrderEntity> orderEntities = query.list();

        tx.commit();
        session.close();

        return orderEntities;
    }
}
