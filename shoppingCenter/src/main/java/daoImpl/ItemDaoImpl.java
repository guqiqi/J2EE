package daoImpl;

import dao.ItemDao;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDaoImpl implements ItemDao{
    public List<Item> getAllItem() {
        Session session = MySessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        String hql = "SELECT entity.Item FROM entity.Item";
        Query query = session.createQuery(hql);
        List<Item> list = query.list();

        tx.commit();

        return list;
    }
}
