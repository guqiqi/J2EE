package nju.yummy.service;

import nju.yummy.entity.CourierEntity;

public interface CourierService {
    /**
     * 注册
     *
     * @param username   用户名
     * @param phone 电话
     * @param password 密码
     * @return 是否注册成功
     */
    public String register(String username, String phone, String password);

    /**
     * 登陆验证账号密码
     *
     * @param phone    电话
     * @param password 密码
     * @return 是否成功
     */
    public String login(String phone, String password);

    /**
     * 修改个人信息
     * @param courierId id
     * @param username 用户名
     * @param password 密码
     * @param phone    电话
     * @return 是否修改成功
     */
    public boolean modifyInfo(Integer courierId, String username, String phone, String password);

    /**
     * 得到用户信息
     *
     * @param courierId id
     * @return 用户详细信息
     */
    public CourierEntity getInfo(Integer courierId);

}
