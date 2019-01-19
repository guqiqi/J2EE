package nju.yummy.dao;

import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;

import java.util.List;

public interface CustomerDao {
    public boolean addCustomer(CustomerEntity customer);

    public boolean updateCustomer(CustomerEntity customer);

    /**
     * 用户注销
     * @param email 邮箱
     * @return 是否注销成功
     */
    public boolean writeOffCustomer(String email);

    public CustomerEntity getCustomer(String email);

    public boolean addAddress(AddressEntity addressEntity);

    public boolean updateAddress(AddressEntity addressEntity);

    public boolean deleteAddress(int addressId);

    public List<AddressEntity> getAddressByEmail(String email);

    // TODO 统计

}
