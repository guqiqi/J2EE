package nju.yummy.serviceImpl;

import nju.yummy.dao.CourierDao;
import nju.yummy.daoImpl.CourierDaoImpl;
import nju.yummy.entity.CourierEntity;
import nju.yummy.service.CourierService;

import java.util.List;

public class CourierServiceImpl implements CourierService {
    private CourierDao courierDao;

    public CourierServiceImpl() {
        courierDao = new CourierDaoImpl();
    }

    @Override
    public String register(String username, String phone, String password) {
        if(courierDao.addCourier(new CourierEntity(username, phone, password)))
            return login(phone, password);
        else
            return "注册失败，请稍后再试";
    }

    @Override
    public String login(String phone, String password) {
        List<CourierEntity> courierEntities = courierDao.getCourierByPhone(phone);
        if (courierEntities.size() == 0)
            return "该账号不存在";
        else {
            for (CourierEntity courierEntity : courierEntities) {
                if (courierEntity.getPassword().equals(password)) {
                    return courierEntity.getCourierId() + "";
                }
            }
            return "账号密码不匹配";
        }
    }

    @Override
    public boolean modifyInfo(Integer courierId, String username, String phone, String password) {
        return courierDao.updateCourier(new CourierEntity(courierId, username, phone, password));
    }

    @Override
    public CourierEntity getInfo(Integer courierId) {
        return courierDao.getInfo(courierId);
    }
}
