package nju.yummy.daoImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DaoUtil {
    public boolean add(Object object){
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        session.save(object);

        tx.commit();
        session.close();

        return true;
    }

    public boolean update(Object object){
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        session.update(object);

        tx.commit();
        session.close();

        return true;
    }
}
