package nju.yummy.service;

import nju.yummy.vo.LocationAndNumberVO;
import nju.yummy.vo.OrderPriceVO;
import nju.yummy.vo.RepurchaseVO;

import java.util.List;

public interface AdminStatisticService {
    public List<OrderPriceVO> getOrderPriceVO();

    public List<LocationAndNumberVO> getOrderLocationDistribution();

    public List<RepurchaseVO> getOrderRepurchaseList();


}
