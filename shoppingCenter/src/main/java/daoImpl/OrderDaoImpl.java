package daoImpl;

import dao.OrderDao;
import entity.OrderBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    protected SessionFactory sessionFactory;

    public boolean insertOrderBean(OrderBean orderBean) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(orderBean);

        tx.commit();
        return true;
    }
}
