package nju.yummy.service;

import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.SellerEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface SellerService {
    /**
     * 餐厅注册
     * @param password 登陆密码
     * @param name 餐厅名称
     * @param type 餐厅类型
     * @param address 餐厅地址
     * @param phone 联系电话
     * @param startHour 营业开始时间
     * @param endHour 营业结束时间
     * @param foodType 食物分类
     * @param discount 餐厅优惠政策
     * @param icon 餐厅图标
     * @return 餐厅编号
     */
    public String register(String password, String name, int type, String address, String phone, String startHour,
                           String endHour, String foodType, String discount, String icon);

    /**
     * 审核餐厅
     * @param sellerId 餐厅编号
     * @param isPass 是否通过
     * @return 是否成功
     */
    public boolean verifySeller(String sellerId, boolean isPass);

    /**
     * 餐厅信息修改
     * @param sellerId 餐厅编号
     * @param password 登陆密码
     * @param name 餐厅名称
     * @param type 餐厅类型
     * @param address 餐厅地址
     * @param phone 联系电话
     * @param startHour 营业开始时间
     * @param endHour 营业结束时间
     * @param foodType 食物分类
     * @param discount 餐厅优惠政策
     * @param icon 餐厅图标
     * @return 是否提交
     */
    public boolean modifyInfo(String sellerId, String password, String name, int type, String address, String phone,
                              String startHour, String endHour, String foodType, String discount, String icon);

    /**
     * 得到餐厅信息
     * @param sellerId 餐厅编号
     * @return 餐厅详细信息
     */
    public SellerEntity getSellerInfo(String sellerId);

    /**
     * 添加商品
     * @param sellerId 餐厅编号
     * @param name 食物名称
     * @param photo 食物图片
     * @param foodType 食物类型（直接选择，和餐厅的分类批评）
     * @param money 定价
     * @param discountMonty 折后价
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param stock 库存
     * @param description 描述
     * @return 是否添加成功
     */
    public boolean addFood(String sellerId, String name, String photo, String foodType, double money,
                           double discountMonty, Date startTime, Date endTime, int stock, String description);

    /**
     * 得到食物信息
     * @param foodId 食物编号
     * @return 食物详情
     */
    public FoodEntity getFoodInfo(int foodId);

    /**
     * 删除食物
     * @param foodIds 食物编号列表
     * @return 是否删除成功
     */
    public boolean deleteFood(List<Integer> foodIds);

    /**
     * 得到一个餐厅的全部信息
     * @param sellerId 餐厅编号
     * @return 食物列表
     */
    public List<FoodEntity> getFoodListBySeller(String sellerId);

    /**
     * 得到一个餐厅的组合优惠列表
     * @param sellerId 餐厅编号
     * @return 组合优惠列表
     */
    public List<DiscountTableEntity> getDiscountBySeller(String sellerId);

    /**
     * 增加一个组合优惠
     * @param sellerId 餐厅编号
     * @param foodIds 食物列表
     * @param discountMoney 组合折后价
     * @return 是否添加成功
     */
    public boolean addGroupDiscount(String sellerId, List<Integer> foodIds, double discountMoney);

    /**
     * 删除组合优惠
     * @param discountIds 优惠列表
     * @return 是否删除成功
     */
    public boolean deleteGroupDiscount(List<Integer> discountIds);

    // TODO 餐厅信息统计

}
