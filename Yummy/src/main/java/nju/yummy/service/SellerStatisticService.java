package nju.yummy.service;

import nju.yummy.vo.CancelStatisticVO;
import nju.yummy.vo.LocationAndNumberVO;
import nju.yummy.vo.OrderPriceVO;

import java.util.List;

public interface SellerStatisticService {
    public List<LocationAndNumberVO> getSellerLocationDistribution(String sellerId);

    public List<OrderPriceVO> getOrderPriceVOBySeller(String sellerId);

    public CancelStatisticVO getCancelStatisticBySeller(String sellerId);


}
