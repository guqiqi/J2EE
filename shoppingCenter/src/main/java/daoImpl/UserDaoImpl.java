package daoImpl;

import dao.UserDao;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoImpl implements UserDao {
    public boolean findUserByName(String username, String password) {
        Session session = MySessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        String hql = "SELECT entity.User FROM entity.User WHERE username = " + username;
        Query query = session.createQuery(hql);
        User user = (User) query.list().get(0);

        if (user.getPassword().equals(password))
            return true;
        return false;
    }
}
