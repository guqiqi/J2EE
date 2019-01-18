package nju.yummy.service;

import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    /**
     * 用户注册
     * @param email 邮箱
     * @return 是否注册成功
     */
    public boolean register(String email);

    /**
     * 初始化个人信息
     * @param email 邮箱
     * @param username 用户名
     * @param password 密码
     * @param avatar 头像url
     * @param phone 电话
     * @return 是否增加成功
     */
    public boolean addCustomerInfo(String email, String username, String password, String avatar, String phone);

    /**
     * 修改个人信息
     * @param email 邮箱
     * @param username 用户名
     * @param password 密码
     * @param avatar 头像url
     * @param phone 电话
     * @return 是否修改成功
     */
    public boolean modifyCustomerInfo(String email, String username, String password, String avatar, String phone);

    /**
     * 得到用户信息
     * @param email 邮箱
     * @return 用户详细信息
     */
    public CustomerEntity getCustomerInfo(String email);

    /**
     * 增加收货地址
     * @param email 邮箱
     * @param receiver 收件人姓名
     * @param detail 详细地址
     * @param phone 联系电话
     * @param label 地址标签
     * @return 是否添加成功
     */
    public boolean addAddress(String email, String receiver, String detail, String phone, int label);

    /**
     * 修改收货地址
     * @param addressId 原来的地址id
     * @param email 邮箱
     * @param receiver 收件人姓名
     * @param detail 详细地址
     * @param phone 联系电话
     * @param label 地址标签
     * @return 是否修改成功
     */
    public boolean modifyAddress(int addressId, String email, String receiver, String detail, String phone, int label);

    /**
     * 删除一个地址
     * @param addressId 地址id
     * @return 是否删除成功
     */
    public boolean deleteAddress(int addressId);

    /**
     * 得到用户全部送餐地址
     * @param email 用户邮箱
     * @return 地址列表
     */
    public List<AddressEntity> getAddressByEmail(String email);

    /**
     * 用户注销
     * @param email 邮箱
     * @return 是否注销成功
     */
    public boolean writeOff(String email);

    // TODO 用户的统计信息
}
