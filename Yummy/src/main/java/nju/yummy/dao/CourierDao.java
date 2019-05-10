package nju.yummy.dao;

import nju.yummy.entity.CourierEntity;

import java.util.List;

public interface CourierDao {
    public boolean addCourier(CourierEntity courierEntity);

    public boolean updateCourier(CourierEntity courierEntity);

    public CourierEntity getInfo(Integer courierId);

    public List<CourierEntity> getCourierByPhone(String phone);

    public List<CourierEntity> getAllCourier();
}
