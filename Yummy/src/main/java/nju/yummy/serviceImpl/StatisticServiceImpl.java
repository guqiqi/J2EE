package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.RecordDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.RecordDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.CustomerEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.entity.PayRecordEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.StatisticService;
import nju.yummy.util.Const;
import nju.yummy.util.StatisticUtil;
import nju.yummy.vo.ProfitVO;
import nju.yummy.vo.SellerCostVO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticServiceImpl implements StatisticService {
    private OrderDao orderDao;
    private SellerDao sellerDao;
    private CustomerDao customerDao;
    private RecordDao recordDao;


    public StatisticServiceImpl() {
        orderDao = new OrderDaoImpl();
        sellerDao = new SellerDaoImpl();
        customerDao = new CustomerDaoImpl();
        recordDao = new RecordDaoImpl();
    }

    @Override
    public List<SellerCostVO> getCostClassifiedByType() {
        List<SellerCostVO> sellerCostVOList = new ArrayList<>();

        for (int i = 0; i < Const.sellerType.length; i++) {
            sellerCostVOList.add(new SellerCostVO(Const.sellerType[i]));
        }

        List<OrderEntity> orderEntityList = orderDao.getAllOrders();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != -1) {
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
    public List<ProfitVO> getDailyProfit() {
        return getDailyCount();
    }

    @Override
    public List<ProfitVO> getMonthlyProfit() {
        List<OrderEntity> orderEntityList = orderDao.getAllOrders();
        List<ProfitVO> profitVOList = new ArrayList<>();
        List<String> sellerIds = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() == 3 && isSameMonth(orderEntity.getFinishTime())) {
                if (sellerIds.contains(orderEntity.getSellerId())) {
                    int index = sellerIds.indexOf(orderEntity.getSellerId());
                    profitVOList.get(index).addAmount(orderEntity.getPayMoney());
                } else {
                    sellerIds.add(orderEntity.getSellerId());
                    SellerEntity sellerEntity = sellerDao.getSellerEntity(orderEntity.getSellerId());
                    profitVOList.add(new ProfitVO(orderEntity.getSellerId(), sellerEntity.getName(), orderEntity.getPayMoney()));
                }
            }
        }

        return profitVOList;
    }

    @Override
    public List<ProfitVO> getTotalProfit() {
        List<OrderEntity> orderEntityList = orderDao.getAllOrders();
        List<ProfitVO> profitVOList = new ArrayList<>();
        List<String> sellerIds = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() == 3) {
                if (sellerIds.contains(orderEntity.getSellerId())) {
                    int index = sellerIds.indexOf(orderEntity.getSellerId());
                    profitVOList.get(index).addAmount(orderEntity.getPayMoney());
                } else {
                    sellerIds.add(orderEntity.getSellerId());
                    SellerEntity sellerEntity = sellerDao.getSellerEntity(orderEntity.getSellerId());
                    profitVOList.add(new ProfitVO(orderEntity.getSellerId(), sellerEntity.getName(), orderEntity.getPayMoney()));
                }
            }
        }

        return profitVOList;
    }

    @Override
    public int[] getCustomerIncrease() {
        List<CustomerEntity> customerEntities = customerDao.getAllCustomer();

        List<Date> dateList = new ArrayList<>();

        for (CustomerEntity customerEntity : customerEntities) {
            dateList.add(customerEntity.getRegisterTime());
        }

        return getRegisterNumber(dateList);
    }

    @Override
    public int[] getSellerIncrease() {
        List<SellerEntity> sellerEntities = sellerDao.getAllSellerEntities();

        List<Date> dateList = new ArrayList<>();

        for (SellerEntity sellerEntity : sellerEntities) {
            dateList.add(sellerEntity.getRegisterTime());
        }

        return getRegisterNumber(dateList);
    }

    @Override
    public void settleAccount() {
        // 网站定时结算，每天计算盈利，转给商家，就写入转账记录
        List<ProfitVO> profitVOList = getDailyProfit();

        // 网站固定把收入的固定比例给商家
        for (ProfitVO profitVO : profitVOList) {
            recordDao.insertRecord(new PayRecordEntity(profitVO.getSellerId(), (double) Math.round(profitVO.getAmount() * Const.cut * 100) / 100));
        }
    }

    private List<ProfitVO> getDailyCount() {
        List<OrderEntity> orderEntityList = orderDao.getAllOrders();
        List<ProfitVO> profitVOList = new ArrayList<>();
        List<String> sellerIds = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() == 3 && isSameDate(orderEntity.getFinishTime())) {
                if (sellerIds.contains(orderEntity.getSellerId())) {
                    int index = sellerIds.indexOf(orderEntity.getSellerId());
                    profitVOList.get(index).addAmount(orderEntity.getPayMoney());
                } else {
                    sellerIds.add(orderEntity.getSellerId());
                    SellerEntity sellerEntity = sellerDao.getSellerEntity(orderEntity.getSellerId());
                    profitVOList.add(new ProfitVO(orderEntity.getSellerId(), sellerEntity.getName(), orderEntity.getPayMoney()));
                }
            }
        }

        return profitVOList;
    }

    private static boolean isSameDate(Date date1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);

        return isSameMonth(date1)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
    }

    private static boolean isSameMonth(Date date1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);

        return isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    private int[] getRegisterNumber(List<Date> dates) {
        // 半年前，接下来每个月统计
        int[] result = new int[7];

        for (Date date : dates) {
            long days = (new Date().getTime() - date.getTime()) / (1000 * 3600 * 24);

            if (days > 180)
                result[0] += 1;
            if (days > 150)
                result[1] += 1;
            if (days > 120)
                result[2] += 1;
            if (days > 90)
                result[3] += 1;
            if (days > 60)
                result[4] += 1;
            if (days > 30)
                result[5] += 1;

            result[6] += 1;
        }
        return result;
    }
}
