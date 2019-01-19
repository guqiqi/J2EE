package nju.yummy.daoImpl;

import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerDaoImplTest {

    @Test
    public void addCustomer() {

    }

    @Test
    public void updateCustomer() {
        CustomerEntity customerEntity = new CustomerDaoImpl().getCustomer("222");
        customerEntity.setLeftMoney(600.0);
        customerEntity.setPassword("123456");
        boolean result = new CustomerDaoImpl().updateCustomer(customerEntity);
        assertEquals(result, true);
    }

    @Test
    public void writeOffCustomer() {
        new CustomerDaoImpl().writeOffCustomer("222");
    }

    @Test
    public void getCustomer() {
        CustomerEntity customerEntity = new CustomerDaoImpl().getCustomer("hsjsj");
        System.out.println(customerEntity);
    }

    @Test
    public void addAddress() {
        AddressEntity addressEntity = new AddressEntity("222", "南京大学", "137777777777", "kiki", 9);
        new CustomerDaoImpl().addAddress(addressEntity);
    }

    @Test
    public void updateAddress() {
        AddressEntity addressEntity = new AddressEntity("222", "南京大学222", "137777777777", "kiki", 9);
        addressEntity.setAddressId(7);
        new CustomerDaoImpl().updateAddress(addressEntity);
    }

    @Test
    public void deleteAddress() {
        new CustomerDaoImpl().deleteAddress(7);
    }

    @Test
    public void getAddressByEmail() {
        List<AddressEntity> addressEntities = new CustomerDaoImpl().getAddressByEmail("222");
        System.out.println(addressEntities.size());
    }
}