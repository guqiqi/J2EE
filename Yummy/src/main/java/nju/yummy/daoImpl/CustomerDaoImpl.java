package nju.yummy.daoImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean addCustomer(CustomerEntity customer) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        session.save(customer);

        tx.commit();

        session.close();

        return true;
    }

    @Override
    public boolean updateCustomer(CustomerEntity customer) {
        return false;
    }

    @Override
    public boolean writeOffCustomer(String email) {
        return false;
    }

    @Override
    public CustomerEntity getCustomer(String email) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        CustomerEntity customerEntity = session.get(CustomerEntity.class, email);

        tx.commit();

        session.close();
        return customerEntity;
    }

    @Override
    public boolean addAddress(AddressEntity addressEntity) {
        return false;
    }

    @Override
    public boolean updateAddress(AddressEntity addressEntity) {
        return false;
    }

    @Override
    public boolean deleteAddress(int addressId) {
        return false;
    }

    @Override
    public List<AddressEntity> getAddressByEmail(String email) {
        return null;
    }
}
