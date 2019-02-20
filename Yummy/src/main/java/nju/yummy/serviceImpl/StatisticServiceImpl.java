package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.OrderEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.StatisticService;
import nju.yummy.util.Const;
import nju.yummy.util.StatisticUtil;
import nju.yummy.vo.ProfitVO;
import nju.yummy.vo.SellerCostVO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticServiceImpl implements StatisticService {
    private OrderDao orderDao;
    private SellerDao sellerDao;
    private CustomerDao customerDao;


    public StatisticServiceImpl() {
        orderDao = new OrderDaoImpl();
        sellerDao = new SellerDaoImpl();
        customerDao = new CustomerDaoImpl();
    }

    @Override
    public List<SellerCostVO> getCostClassifiedByType() {
        List<SellerCostVO> sellerCostVOList = new ArrayList<>();

        for (int i = 0; i < Const.sellerType.length; i++) {
            sellerCostVOList.add(new SellerCostVO(Const.sellerType[i]));
        }

        List<OrderEntity> orderEntityList = orderDao.getAllOrders();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                SellerEntity sellerEntity = sellerDao.getSellerEntity(orderEntity.getSellerId());

                int index = 0;
                for (; index < Const.sellerType.length; index++) {
                    if (Const.sellerType[index].equals(sellerEntity.getType()))
                        break;
                }

                SellerCostVO sellerCostVO = sellerCostVOList.get(index);
                sellerCostVO.addCost(orderEntity.getPayMoney());
                sellerCostVOList.set(index, sellerCostVO);
            }
        }

        return sellerCostVOList;
    }

    @Override
    public List<Integer> getSellerNumberClassifiedByType() {
        List<Integer> sellerNumber = new ArrayList<>();

        for (int i = 0; i < Const.sellerType.length; i++) {
            sellerNumber.add(0);
        }

        List<SellerEntity> sellerEntities = sellerDao.getAllSellerEntities();

        for (SellerEntity sellerEntity : sellerEntities) {
            int index = 0;
            for (; index < Const.sellerType.length; index++) {
                if (Const.sellerType[index].equals(sellerEntity.getType()))
                    break;
            }

            sellerNumber.set(index, sellerNumber.get(index) + 1);
        }

        return sellerNumber;
    }

    @Override
    public double[] getCostByTime() {
        List<OrderEntity> orderEntityList = orderDao.getAllOrders();

        return StatisticUtil.getCostByTime(orderEntityList);
    }

    @Override
    public double[] getCostByHour() {
        double[] result = new double[24];

        List<OrderEntity> orderEntityList = orderDao.getAllOrders();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                Timestamp placeTime = orderEntity.getPlaceTime();
                SimpleDateFormat sd = new SimpleDateFormat("HH");
                int hour = Integer.parseInt(sd.format(placeTime)) - 1;
                result[hour] += orderEntity.getPayMoney();
            }
        }

        return result;
    }

    @Override
    public int getSellerNumber() {
        return sellerDao.getAllSellerEntities().size();
    }

    @Override
    public int getCustomerNumber() {
        return customerDao.getAllCustomer().size();
    }

    @Override
    public List<ProfitVO> getProfit() {
        // TODO
        return null;
    }

    @Override
    public List<Integer> getCustomerIncrease() {
        // TODO
        return null;
    }

    @Override
    public List<Integer> getSellerIncrease() {
        // TODO
        return null;
    }
}
