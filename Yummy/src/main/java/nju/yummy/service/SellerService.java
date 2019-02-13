package nju.yummy.service;

import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.SellerEntity;

import java.util.Date;
import java.util.List;

public interface SellerService {
    /**
     * 登陆验证账号密码
     *
     * @param sellerId 编号
     * @param password 密码
     * @return 是否成功
     */
    public String login(String sellerId, String password);

    /**
     * 餐厅注册
     *
     * @param password  登陆密码
     * @param name      餐厅名称
     * @param type      餐厅类型
     * @param address   餐厅地址
     * @param phone     联系电话
     * @param startHour 营业开始时间
     * @param endHour   营业结束时间
     * @param icon      餐厅图标
     * @return 餐厅编号
     */
    public String register(String password, String name, String type, String address, String phone, String startHour,
                           String endHour, String icon);

    /**
     * 审核餐厅
     *
     * @param sellerId 餐厅编号
     * @param isPass   是否通过
     * @return 是否成功
     */
    public boolean verifySeller(String sellerId, boolean isPass);

    /**
     * 餐厅信息修改
     *
     * @param sellerId  餐厅编号
     * @param password  登陆密码
     * @param name      餐厅名称
     * @param type      餐厅类型
     * @param address   餐厅地址
     * @param phone     联系电话
     * @param startHour 营业开始时间
     * @param endHour   营业结束时间
     * @param icon      餐厅图标
     * @param status    修改后的餐厅状态，大部分改成1，修改重要信息改成3
     * @return 是否提交
     */
    public boolean modifyInfo(String sellerId, String password, String name, String type, String address, String phone,
                              String startHour, String endHour, String icon, int status);

    /**
     * 得到餐厅信息
     *
     * @param sellerId 餐厅编号
     * @return 餐厅详细信息
     */
    public SellerEntity getSellerInfo(String sellerId);

    /**
     * 得到所有餐厅信息
     *
     * @return 所有餐厅信息
     */
    public List<SellerEntity> getAllSellerEntities();

    /**
     * 添加商品
     *
     * @param sellerId      餐厅编号
     * @param name          食物名称
     * @param photo         食物图片
     * @param foodType      食物类型（直接选择，和餐厅的分类批评）
     * @param money         定价
     * @param discountMonty 折后价
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param stock         库存
     * @param description   描述
     * @return 是否添加成功
     */
    public boolean addFood(String sellerId, String name, String photo, String foodType, double money,
                           double discountMonty, Date startTime, Date endTime, int stock, String description);

    /**
     * 修改商品
     *
     * @param foodId        食物编号
     * @param sellerId      餐厅编号
     * @param name          食物名称
     * @param photo         食物图片
     * @param foodType      食物类型（直接选择，和餐厅的分类批评）
     * @param money         定价
     * @param discountMonty 折后价
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param stock         库存
     * @param description   描述
     * @return 是否修改成功
     */
    public boolean modifyFood(int foodId, String sellerId, String name, String photo, String foodType, double money,
                              double discountMonty, Date startTime, Date endTime, int stock, String description);


    /**
     * 得到食物信息
     *
     * @param foodId 食物编号
     * @return 食物详情
     */
    public FoodEntity getFoodInfo(int foodId);

    /**
     * 删除食物
     *
     * @param foodIds 食物编号列表
     * @return 是否删除成功
     */
    public boolean deleteFood(List<Integer> foodIds);

    /**
     * 得到一个餐厅的全部信息
     *
     * @param sellerId 餐厅编号
     * @return 食物列表
     */
    public List<FoodEntity> getFoodListBySeller(String sellerId);

    /**
     * 修改餐厅商品分类
     *
     * @param sellerId 商家编号
     * @param foodType 新的商品分类
     * @return 是否修改成功
     */
    public boolean modifyFoodType(String sellerId, String foodType);

    /**
     * 修改会员优惠
     *
     * @param sellerId 商家编号
     * @param discount 新的会员优惠
     * @return 是否修改成功
     */
    public boolean modifyCustomerDiscount(String sellerId, String discount);

    /**
     * 得到一个餐厅的组合优惠列表
     *
     * @param sellerId 餐厅编号
     * @return 组合优惠列表
     */
    public List<DiscountTableEntity> getDiscountBySeller(String sellerId);

    /**
     * 增加一个组合优惠
     *
     * @param sellerId      餐厅编号
     * @param foodIds       食物列表
     * @param foodNames     食物名称
     * @param discountMoney 组合折后价
     * @param money         原价
     * @param startTime     促销开始时间
     * @param endTime       促销结束时间
     * @return 是否添加成功
     */
    public boolean addGroupDiscount(String sellerId, String foodIds, String foodNames, double discountMoney,
                                    double money, Date startTime, Date endTime);


    /**
     * 增加一个组合优惠
     *
     * @param discountId    促销编号
     * @param sellerId      餐厅编号
     * @param foodIds       食物列表
     * @param foodNames     食物名称
     * @param discountMoney 组合折后价
     * @param money         原价
     * @param startTime     促销开始时间
     * @param endTime       促销结束时间
     * @return 是否添加成功
     */
    public boolean modifyGroupDiscount(Integer discountId, String sellerId, String foodIds, String foodNames,
                                       double discountMoney, double money, Date startTime, Date endTime);

    /**
     * 删除组合优惠
     *
     * @param discountIds 优惠列表
     * @return 是否删除成功
     */
    public boolean deleteGroupDiscount(List<Integer> discountIds);

    // TODO 餐厅信息统计

}
