package daoImpl;

import dao.ItemDao;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    @Autowired
    protected SessionFactory sessionFactory;

    public List<Item> getAllItem() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String hql = "SELECT entity.Item FROM entity.Item";
        Query query = session.createQuery(hql);
        List<Item> list = query.list();

        tx.commit();

        return list;
    }
}
