package nju.yummy.service;

import nju.yummy.vo.CancelStatisticVO;
import nju.yummy.vo.DeliverTimeVO;
import nju.yummy.vo.LocationAndNumberVO;

import java.util.List;


public interface CustomerStatisticService {
    /**
     * 得到系统用户的地域分布
     * @return 地域分布列表
     */
    public List<LocationAndNumberVO> getCustomerLocationDistribution();

    /**
     * 得到用户退订情况
     * @param userId 用户ID
     * @return 退订统计
     */
    public CancelStatisticVO getCancelStatisticByUser(String userId);

    /**
     * 得到用户配送时间情况统计
     * @param userId 用户ID
     * @return 配送时间情况列表
     */
    public List<DeliverTimeVO> getDeliverTimeByUser(String userId);



}
