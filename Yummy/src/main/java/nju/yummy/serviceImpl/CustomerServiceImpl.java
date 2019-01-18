package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;
import nju.yummy.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public CustomerServiceImpl() {
        customerDao = new CustomerDaoImpl();
    }

    @Override
    public boolean register(String email) {
        return new MailServiceImpl().sendEmail(email);
    }

    @Override
    public boolean addCustomerInfo(String email, String username, String password, String avatar, String phone) {
        CustomerEntity customerEntity = new CustomerEntity(email, username, password, avatar, phone);
        return customerDao.addCustomer(customerEntity);
    }

    @Override
    public boolean modifyCustomerInfo(String email, String username, String password, String avatar, String phone) {
        CustomerEntity customerEntity = new CustomerEntity(email, username, password, avatar, phone);
        return customerDao.updateCustomer(customerEntity);
    }

    @Override
    public CustomerEntity getCustomerInfo(String email) {
        return customerDao.getCustomer(email);
    }

    @Override
    public boolean addAddress(String email, String receiver, String detail, String phone, int label) {
        AddressEntity addressEntity = new AddressEntity(email, detail, phone, receiver, label);
        return customerDao.addAddress(addressEntity);
    }

    @Override
    public boolean modifyAddress(int addressId, String email, String receiver, String detail, String phone, int label) {
        AddressEntity addressEntity = new AddressEntity(email, detail, phone, receiver, label);
        return customerDao.updateAddress(addressEntity);
    }

    @Override
    public boolean deleteAddress(int addressId) {
        return customerDao.deleteAddress(addressId);
    }

    @Override
    public List<AddressEntity> getAddressByEmail(String email) {
        return customerDao.getAddressByEmail(email);
    }

    @Override
    public boolean writeOff(String email) {
        return customerDao.writeOffCustomer(email);
    }
}
