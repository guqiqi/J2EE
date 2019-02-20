package nju.yummy.serviceImpl;

import nju.yummy.dao.CustomerDao;
import nju.yummy.dao.OrderDao;
import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.CustomerDaoImpl;
import nju.yummy.daoImpl.OrderDaoImpl;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.CustomerService;
import nju.yummy.util.Const;
import nju.yummy.util.StatisticUtil;
import nju.yummy.vo.SellerCostVO;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private OrderDao orderDao;
    private SellerDao sellerDao;

    public CustomerServiceImpl() {
        customerDao = new CustomerDaoImpl();
        orderDao = new OrderDaoImpl();
        sellerDao = new SellerDaoImpl();
    }

    @Override
    public boolean register(String email, JavaMailSender mailSender) {
        return new MailServiceImpl().sendEmail(email, mailSender);
    }

    @Override
    public boolean addCustomerInfo(String email, String username, String password, String avatar, String phone) {
        CustomerEntity customerEntity = new CustomerEntity(email, username, password, avatar, phone);
        return customerDao.addCustomer(customerEntity);
    }

    @Override
    public String login(String email, String password) {
        CustomerEntity customerEntity = customerDao.getCustomer(email);
        if (customerEntity == null)
            return "该账号不存在";
        else if (customerEntity.getStatus() == 0)
            return "账号已注销，请使用其他账号登陆";
        else if (customerEntity.getPassword().equals(password))
            return "成功登陆";
        else
            return "账号密码不匹配";
    }

    @Override
    public boolean modifyCustomerInfo(String email, String username, String password, String avatar, String phone) {
        CustomerEntity customerEntity = new CustomerEntity(email, username, password, avatar, phone);
        return customerDao.updateCustomer(customerEntity);
    }

    @Override
    public CustomerEntity getCustomerInfo(String email) {
        return customerDao.getCustomer(email);
    }

    @Override
    public boolean addAddress(String email, String receiver, String detail, String phone, int label) {
        AddressEntity addressEntity = new AddressEntity(email, detail, phone, receiver, label);

        return customerDao.addAddress(addressEntity);
    }

    @Override
    public boolean modifyAddress(int addressId, String email, String receiver, String detail, String phone, int label) {
        AddressEntity addressEntity = new AddressEntity(addressId, email, detail, phone, receiver, label);
        return customerDao.updateAddress(addressEntity);
    }

    @Override
    public boolean deleteAddress(int addressId) {
        return customerDao.deleteAddress(addressId);
    }

    @Override
    public AddressEntity getAddressById(int addressId) {
        return customerDao.getAddress(addressId);
    }

    @Override
    public List<AddressEntity> getAddressByEmail(String email) {
        return customerDao.getAddressByEmail(email);
    }

    @Override
    public boolean writeOff(String email) {
        return customerDao.writeOffCustomer(email);
    }

    @Override
    public double[] getCostByHour(String email) {
        double[] result = new double[24];

        List<OrderEntity> orderEntityList = orderDao.getOrderByEmail(email);

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
    public List<SellerCostVO> getCostClassifiedByType(String email) {
        List<SellerCostVO> sellerCostVOList = new ArrayList<>();

        for (int i = 0; i < Const.sellerType.length; i++) {
            sellerCostVOList.add(new SellerCostVO(Const.sellerType[i]));
        }

        List<OrderEntity> orderEntityList = orderDao.getOrderByEmail(email);

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
    public List<SellerCostVO> getCostBySeller(String email) {
        List<SellerCostVO> sellerCostVOList = new ArrayList<>();
        List<String> sellerNames = new ArrayList<>();

        List<OrderEntity> orderEntityList = orderDao.getOrderByEmail(email);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != 0) {
                SellerEntity sellerEntity = sellerDao.getSellerEntity(orderEntity.getSellerId());

                if (sellerNames.contains(sellerEntity.getName())) {
                    SellerCostVO sellerCostVO = sellerCostVOList.get(sellerNames.indexOf(sellerEntity.getName()));
                    sellerCostVO.addCost(orderEntity.getPayMoney());
                    sellerCostVOList.set(sellerNames.indexOf(sellerEntity.getName()), sellerCostVO);
                } else {
                    sellerNames.add(sellerEntity.getName());
                    sellerCostVOList.add(new SellerCostVO(sellerEntity.getName(), orderEntity.getPayMoney()));
                }
            }
        }

        return sellerCostVOList;
    }

    @Override
    public double[] getCostByTime(String email) {
        List<OrderEntity> orderEntityList = orderDao.getOrderByEmail(email);

        return StatisticUtil.getCostByTime(orderEntityList);
    }

    @Override
    public List<SellerCostVO> getCancelByUser(String email) {
        // TODO 退款金额计算
        List<SellerCostVO> sellerCostVOList = new ArrayList<>();

        List<String> sellerNames = new ArrayList<>();

        List<OrderEntity> orderEntityList = orderDao.getOrderByEmail(email);

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() == 0) {
                SellerEntity sellerEntity = sellerDao.getSellerEntity(orderEntity.getSellerId());

                if (sellerNames.contains(sellerEntity.getName())) {
                    SellerCostVO sellerCostVO = sellerCostVOList.get(sellerNames.indexOf(sellerEntity.getName()));
                    sellerCostVO.addCost(orderEntity.getPayMoney());
                    sellerCostVOList.set(sellerNames.indexOf(sellerEntity.getName()), sellerCostVO);
                } else {
                    sellerNames.add(sellerEntity.getName());
                    sellerCostVOList.add(new SellerCostVO(sellerEntity.getName(), orderEntity.getPayMoney()));
                }
            }
        }

        return sellerCostVOList;
    }
}
