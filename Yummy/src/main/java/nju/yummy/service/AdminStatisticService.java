package nju.yummy.service;

import nju.yummy.vo.LocationAndNumberVO;
import nju.yummy.vo.OrderPriceVO;
import nju.yummy.vo.RepurchaseVO;

import java.util.List;

public interface AdminStatisticService {
    /**
     * 订单表格数据
     * @return 订单表格数据
     */
    public List<OrderPriceVO> getOrderPriceVO();

    /**
     * 订单分布情况
     * @return 订单分布列表
     */
    public List<LocationAndNumberVO> getOrderLocationDistribution();

    /**
     * 订单复购情况
     * @return 订单复购情况
     */
    public RepurchaseVO getOrderRepurchaseList();

    /**
     *  得到按时间分布的用户收益
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOByHour();


    /**
     *  得到按时间分布的用户收益
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOByWeek();

    /**
     *  得到按时间分布的用户收益
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOByMonth();


    /**
     *  得到商家按时间分布的用户收益
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOByQuater();

}
