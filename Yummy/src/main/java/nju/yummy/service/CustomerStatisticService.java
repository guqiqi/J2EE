package nju.yummy.service;

import nju.yummy.vo.CancelStatisticVO;
import nju.yummy.vo.DeliverTimeVO;
import nju.yummy.vo.LocationAndNumberVO;

import java.util.List;


public interface CustomerStatisticService {
    public List<LocationAndNumberVO> getCustomerLocationDistribution();

    public CancelStatisticVO getCancelStatisticByUser(String userId);

    public List<DeliverTimeVO> getDeliverTimeByUser(String userId);

}
