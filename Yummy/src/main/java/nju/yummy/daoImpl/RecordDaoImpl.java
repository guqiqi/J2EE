package nju.yummy.daoImpl;

import nju.yummy.dao.RecordDao;
import nju.yummy.entity.PayRecordEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RecordDaoImpl implements RecordDao {
    private DaoUtil daoUtil = new DaoUtil();

    @Override
    public boolean insertRecord(PayRecordEntity payRecordEntity) {
        return daoUtil.add(payRecordEntity);
    }

    @Override
    public List<PayRecordEntity> getAllPayRecord() {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select record from PayRecordEntity record");

        List<PayRecordEntity> payRecordEntities = (List<PayRecordEntity>)query.list();

        tx.commit();
        session.close();

        return payRecordEntities;
    }

    @Override
    public List<PayRecordEntity> getRecordByUserId(String userId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select record from PayRecordEntity record where userId=:userId");
        query.setParameter("userId", userId);

        List<PayRecordEntity> payRecordEntities = (List<PayRecordEntity>)query.list();

        tx.commit();
        session.close();

        return payRecordEntities;
    }
}
