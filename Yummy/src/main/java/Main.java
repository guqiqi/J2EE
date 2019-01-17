import nju.yummy.entity.CustomerEntity;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.util.List;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            Query query = session.createQuery("from CustomerEntity ");
            //2、使用Query对象的list方法得到数据集合
            List<CustomerEntity> list = query.list();
            //3、遍历集合获取数据
            for (CustomerEntity good : list) {
                System.out.println(good.getStatus());
            }
        } finally {
            session.close();
        }
    }
}