package nju.yummy.service;

import nju.yummy.vo.ProfitVO;
import nju.yummy.vo.SellerCostVO;

import java.util.List;

public interface StatisticService {
    // 网站使用统计，这边的dao会用其他的
    /**
     * 用户在不同类型的商店消费情况
     * @return 消费列表
     */
    public List<SellerCostVO> getCostClassifiedByType();

    /**
     * 不同类型商店数量
     * @return 数量
     */
    public List<Integer> getSellerNumberClassifiedByType();

    /**
     * 近一周、一个月、一年的消费
     * @return [一周消费、一月消费、一年消费]
     */
    public double[] getCostByTime();

    /**
     * 不同时间段的消费统计（所有）
     * @return 按小时的用户消费数组
     */
    public double[] getCostByHour();

    /**
     * 商店总数
     * @return 商店总数
     */
    public int getSellerNumber();

    /**
     * 用户总数
     * @return 用户总数
     */
    public int getCustomerNumber();

    /**
     * 盈利列表
     * @return 盈利列表（按月计算）
     */
    public List<ProfitVO> getProfit();

    /**
     * 用户数量增长情况
     * @return 用户按月增长数量
     */
    public int[] getCustomerIncrease();

    /**
     * 商户数量增长情况
     * @return 商户按月增长数量
     */
    public int[] getSellerIncrease();

    /**
     * 网站结算
     */
    public void settleAccount();
}
