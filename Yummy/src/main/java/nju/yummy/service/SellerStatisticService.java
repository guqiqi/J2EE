package nju.yummy.service;

import nju.yummy.vo.*;

import java.util.List;

public interface SellerStatisticService {
    /**
     * 得到商家的地域分布
     * @return 地域分布列表
     */
    public List<LocationAndNumberVO> getSellerLocationDistribution();

    /**
     *  得到商家的用户收益列表
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySeller(String sellerId);

    /**
     *  得到商家按时间分布的用户收益
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByHour(String sellerId);


    /**
     *  得到商家按时间分布的用户收益
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByWeek(String sellerId);

    /**
     *  得到商家按时间分布的用户收益
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByMonth(String sellerId);


    /**
     *  得到商家按时间分布的用户收益
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByQuater(String sellerId);

    /**
     * 针对商家的复购情况 订单
     * @param sellerId 商家编号
     * @return 复购情况
     */
    public RepurchaseVO getOrderRepurchaseOrderBySeller(String sellerId);

    /**
     * 针对商家的复购情况 用户
     * @param sellerId 商家编号
     * @return 复购情况
     */
    public RepurchaseVO getOrderRepurchaseUserBySeller(String sellerId);


    /**
     * 得到不同性别的商家配送距离
     * @param sellerId 商家编号
     * @return 距离
     */
    public List<List<Integer>> getDistanceBySeller(String sellerId);

    /**
     *  得到商家按时间分布的用户收益 针对单个用户
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByHourOfUser(String sellerId);


    /**
     *  得到商家按时间分布的用户收益 针对单个用户
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByWeekOfUser(String sellerId);

    /**
     *  得到商家按时间分布的用户收益 针对单个用户
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByMonthOfUser(String sellerId);

    /**
     *  得到商家按时间分布的用户收益 针对单个用户
     * @param sellerId 商家编号
     * @return 用户收益列表
     */
    public List<OrderPriceVO> getOrderPriceVOBySellerByQuaterOfUser(String sellerId);

    /**
     * 得到套餐和商品的比例
     * @param sellerId 商家编号
     * @return 比例
     */
    public PackageAndSingleVO getPackageAndSingleVO(String sellerId);

    /**
     * 获取商品拉新数量
     * @param sellerId 商家编号
     * @return 数量列表
     */
    public List<Integer> getNewCustomerNumber(String sellerId);

    /**
     * 商家波士顿矩阵
     * @param sellerId 商家编号
     * @return 波士顿矩阵列表
     */
    public List<BostonVO> getBostonStatistic(String sellerId);

    /**
     * 留存失贡献度
     * @param sellerId 商家编号
     * @return 贡献度列表
     */
    public List<List<Double>> getRetainRate(String sellerId);

    /**
     * 根据时间获取单品销售额
     * @param sellerId 商家编号
     * @return 销售额列表
     */
    public List<List<Double>> getFoodMoneyByHour(String sellerId);

    /**
     * 根据时间获取单品销售额
     * @param sellerId 商家编号
     * @return 销售额列表
     */
    public List<List<Double>> getFoodMoneyByWeek(String sellerId);

    /**
     * 根据时间获取单品销售量
     * @param sellerId 商家编号
     * @return 销售额列表
     */
    public List<List<Double>> getFoodMoneyByMonth(String sellerId);

    /**
     * 根据时间获取单品销售量
     * @param sellerId 商家编号
     * @return 销售额列表
     */
    public List<List<Double>> getFoodCountByHour(String sellerId);

    /**
     * 根据时间获取单品销售量
     * @param sellerId 商家编号
     * @return 销售额列表
     */
    public List<List<Double>> getFoodCountByWeek(String sellerId);

    /**
     * 根据时间获取单品销售量
     * @param sellerId 商家编号
     * @return 销售额列表
     */
    public List<List<Double>> getFoodCountByMonth(String sellerId);
}
