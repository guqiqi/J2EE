package nju.yummy.daoImpl;

import nju.yummy.dao.CourierDao;
import nju.yummy.entity.CourierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CourierDaoImpl implements CourierDao {
    private DaoUtil daoUtil = new DaoUtil();

    @Override
    public boolean addCourier(CourierEntity courierEntity) {
        return daoUtil.add(courierEntity);
    }

    @Override
    public boolean updateCourier(CourierEntity courierEntity) {
        return daoUtil.update(courierEntity);
    }

    @Override
    public CourierEntity getInfo(Integer courierId) {
        /* 如果用户不存在则返回null */
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        CourierEntity courierEntity = session.get(CourierEntity.class, courierId);

        tx.commit();

        session.close();
        return courierEntity;
    }

    @Override
    public List<CourierEntity> getCourierByPhone(String phone) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select courier from CourierEntity courier where phone=:phone");
        query.setParameter("phone", phone);

        List<CourierEntity> courierEntities = query.list();

        tx.commit();
        session.close();

        return courierEntities;
    }


}
