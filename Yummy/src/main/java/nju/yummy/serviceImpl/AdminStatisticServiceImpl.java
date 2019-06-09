package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.service.AdminStatisticService;
import nju.yummy.vo.LocationAndNumberVO;
import nju.yummy.vo.OrderPriceVO;
import nju.yummy.vo.RepurchaseVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminStatisticServiceImpl implements AdminStatisticService {
    OrderDao orderDao = new OrderDaoImpl();
    CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public List<OrderPriceVO> getOrderPriceVO() {
        List<OrderEntity> orderEntities = orderDao.getAllOrders();
        List<OrderPriceVO> orderPriceVOS = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            orderPriceVOS.add(new OrderPriceVO(orderEntity.getOrderId(), orderEntity.getTotalMoney(),
                    orderEntity.getPayMoney(), orderEntity.getPayMoney() * 0.95));
        }

        return orderPriceVOS;
    }

    @Override
    public List<LocationAndNumberVO> getOrderLocationDistribution() {
        List<LocationAndNumberVO> result = new ArrayList<>();
        List<OrderEntity> orderEntities = orderDao.getAllOrders();

        for (OrderEntity orderEntity : orderEntities) {
            boolean isHave = false;

            AddressEntity addressEntity = customerDao.getAddress(orderEntity.getAddressId());
            double lan = addressEntity.getLatitude();
            double lon = addressEntity.getLongitude();

            for (LocationAndNumberVO locationAndNumberVO : result) {
                if (Math.abs(lan - locationAndNumberVO.getLatitude()) < 0.03 &&
                        Math.abs(lon - locationAndNumberVO.getLongitude()) < 0.03) {
                    isHave = true;

                    locationAndNumberVO.setNumber(locationAndNumberVO.getNumber() + 1);
                }
            }

            if (!isHave) {
                result.add(new LocationAndNumberVO(lan, lon));
            }
        }

        return result;
    }

    @Override
    public RepurchaseVO getOrderRepurchaseList() {
        List<OrderEntity> orderEntities = orderDao.getAllOrders();

        int re = 0;
        List<String> userids = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            if (userids.contains(orderEntity.getEmail())) {
                re++;
            }
        }


        return new RepurchaseVO(orderEntities.size() - re, re);
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOByHour() {
        List<OrderEntity> orderEntities = orderDao.getAllOrders();

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = orderEntity.getFinishTime().getHours()/12;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOByWeek() {
        List<OrderEntity> orderEntities = orderDao.getAllOrders();

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = (int)(new Date().getTime()-orderEntity.getFinishTime().getTime())/3600/24%7;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOByMonth() {
        List<OrderEntity> orderEntities = orderDao.getAllOrders();

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = orderEntity.getFinishTime().getMonth();

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }

    @Override
    public List<OrderPriceVO> getOrderPriceVOByQuater() {
        List<OrderEntity> orderEntities = orderDao.getAllOrders();

        List<OrderPriceVO> result = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            result.add(new OrderPriceVO(0, 0, 0));
        }

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                int index = orderEntity.getFinishTime().getMonth()/4;

                OrderPriceVO orderPriceVO = result.get(index);
                orderPriceVO.setPay(orderPriceVO.getPay()+orderEntity.getPayMoney());
                orderPriceVO.setTotal(orderPriceVO.getTotal()+orderEntity.getTotalMoney());
                orderPriceVO.setProfit(orderPriceVO.getProfit()+orderEntity.getPayMoney()*0.95);

                result.set(index, orderPriceVO);
            }
        }

        return result;
    }
}
