package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.entity.CustomerEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.service.CustomerStatisticService;
import nju.yummy.vo.CancelStatisticVO;
import nju.yummy.vo.DeliverTimeVO;
import nju.yummy.vo.LocationAndNumberVO;

import java.util.ArrayList;
import java.util.List;

public class CustomerStatisticServiceImpl implements CustomerStatisticService {
    CustomerDao customerDao = new CustomerDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();

    @Override
    public List<LocationAndNumberVO> getCustomerLocationDistribution() {
        List<LocationAndNumberVO> result = new ArrayList<>();
        List<CustomerEntity> customerEntities = customerDao.getAllCustomer();

        for (CustomerEntity customerEntity : customerEntities) {
            boolean isHave = false;

            for (LocationAndNumberVO locationAndNumberVO : result) {
                if (Math.abs(customerEntity.getLat() - locationAndNumberVO.getLatitude()) < 0.03 &&
                        Math.abs(customerEntity.getLon() - locationAndNumberVO.getLongitude()) < 0.03) {
                    isHave = true;

                    locationAndNumberVO.setNumber(locationAndNumberVO.getNumber() + 1);
                }
            }

            if (!isHave) {
                result.add(new LocationAndNumberVO(customerEntity.getLat(), customerEntity.getLon()));
            }
        }

        return result;
    }

    @Override
    public CancelStatisticVO getCancelStatisticByUser(String userId) {
        List<OrderEntity> orderEntities = orderDao.getOrderByEmail(userId);

        int totalNumber = orderEntities.size();
        int cancelNumber = 0;
        double totalMoney = 0;
        double returnMoney = 0;
        double fineMoney = 0;

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() != -1) {
                totalMoney += orderEntity.getTotalMoney();
            } else {
                cancelNumber += 1;
                returnMoney += orderEntity.getPayMoney();
                fineMoney += (orderEntity.getTotalMoney() - orderEntity.getPayMoney());
            }
        }

        CancelStatisticVO cancelStatisticVO = new CancelStatisticVO(totalNumber, cancelNumber, totalMoney,
                returnMoney, fineMoney);

        return cancelStatisticVO;
    }

    @Override
    public List<DeliverTimeVO> getDeliverTimeByUser(String userId) {
        List<OrderEntity> orderEntities = orderDao.getOrderByEmail(userId);

        int max = 0;
        int min = Integer.MAX_VALUE;
        int total = 0;
        int times = 0;

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus() == 3) {
                times++;
                int minutes = orderEntity.getFinishTime().getMinutes() - orderEntity.getDeliverTime().getMinutes();
                if(minutes>max)
                    max = minutes;
                if(minutes<min)
                    min = minutes;
                total+=minutes;
            }
        }

        List<DeliverTimeVO> deliverTimeVOS = new ArrayList<>();
        deliverTimeVOS.add(new DeliverTimeVO(min, max, total/times));

        return deliverTimeVOS;
    }
}
