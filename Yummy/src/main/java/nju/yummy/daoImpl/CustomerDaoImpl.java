package nju.yummy.daoImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao{
    private DaoUtil daoUtil = new DaoUtil();

    @Override
    public boolean addCustomer(CustomerEntity customer) {
        return daoUtil.add(customer);
    }

    @Override
    public boolean updateCustomer(CustomerEntity customer) {
        return daoUtil.update(customer);
    }

    @Override
    public boolean writeOffCustomer(String email) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("update CustomerEntity c set c.status = :newStatus where email = :email");
        query.setParameter("newStatus", (byte)0);
        query.setParameter("email", email);

        query.executeUpdate();

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public CustomerEntity getCustomer(String email) {
        /* 如果用户不存在则返回null */
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        CustomerEntity customerEntity = session.get(CustomerEntity.class, email);

        tx.commit();

        session.close();
        return customerEntity;
    }

    @Override
    public AddressEntity getAddress(int addressId) {
        /* 如果用户不存在则返回null */
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        AddressEntity addressEntity = session.get(AddressEntity.class, addressId);

        tx.commit();

        session.close();
        return addressEntity;
    }

    @Override
    public boolean addAddress(AddressEntity addressEntity) {
        return daoUtil.add(addressEntity);
    }

    @Override
    public boolean updateAddress(AddressEntity addressEntity) {
        return daoUtil.update(addressEntity);
    }

    @Override
    public boolean deleteAddress(int addressId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("delete AddressEntity where addressId=:addressId");
        query.setParameter("addressId", addressId);

        query.executeUpdate();

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public List<AddressEntity> getAddressByEmail(String email) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select add from AddressEntity add where email=:email");
        query.setParameter("email", email);

        List<AddressEntity> addressEntities = (List<AddressEntity>)query.list();

        tx.commit();
        session.close();

        return addressEntities;
    }
}
