package daoImpl;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MySessionFactory {
    private static SessionFactory sessionFactory;

    public static SessionFactory getInstance(){
        try {
            if (sessionFactory == null) {
                Configuration config = new Configuration().configure();
                sessionFactory = config.buildSessionFactory();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return sessionFactory;
    }
}
