package daoImpl;

import dao.OrderDao;
import entity.OrderBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderDaoImpl implements OrderDao {
    public boolean insertOrderBean(OrderBean orderBean) {
        Session session = MySessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        session.save(orderBean);

        tx.commit();
        return true;
    }
}
