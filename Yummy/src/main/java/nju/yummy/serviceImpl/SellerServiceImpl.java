package nju.yummy.serviceImpl;

import nju.yummy.dao.SellerDao;
import nju.yummy.daoImpl.SellerDaoImpl;
import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.SellerService;
import nju.yummy.util.Const;
import nju.yummy.util.DateToTimestamp;
import nju.yummy.util.SellerStatus;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SellerServiceImpl implements SellerService {
    private SellerDao sellerDao;

    public SellerServiceImpl() {
        sellerDao = new SellerDaoImpl();
    }

    @Override
    public String register(String password, String name, int type, String address, String phone, String startHour, String endHour, String foodType, String discount, String icon) {
        String sellerId = generateSellerId();
        SellerEntity sellerEntity = new SellerEntity(sellerId, password, name, type, address, phone, startHour,
                endHour, 1, foodType, discount, icon);
        sellerDao.addSeller(sellerEntity);
        return sellerId;
    }

    private String generateSellerId() {
        String str = generate7alphabets();
        List<SellerEntity> sellerEntities = sellerDao.getAllSellerEntities();
        Set<String> sellerIds = new HashSet<>();
        for (SellerEntity sellerEntity : sellerEntities) {
            sellerIds.add(sellerEntity.getSellerId());
        }
        while (sellerIds.contains(str)) {
            str = generate7alphabets();
        }

        return str;
    }

    private String generate7alphabets() {
        String str = "";
        for (int i = 0; i < 7; i++) {
            double random = Math.random() * 52;
            System.out.println(random / 26);

            str = str + (char) (random / 26 > 1 ? 'a' + random % 26 : 'A' + random % 26);
        }
        return str;
    }

    @Override
    public boolean verifySeller(String sellerId, boolean isPass) {
        if (isPass)
            return sellerDao.verifySeller(sellerId, SellerStatus.NORMAL);
        else
            return sellerDao.verifySeller(sellerId, SellerStatus.TOMODIFY);
    }

    @Override
    public boolean modifyInfo(String sellerId, String password, String name, int type, String address, String phone, String startHour, String endHour, String foodType, String discount, String icon) {
        SellerEntity sellerEntity = new SellerEntity(sellerId, password, name, type, address, phone, startHour,
                endHour, 1, foodType, discount, icon);

        sellerEntity.setOrderCount(sellerDao.getSellerEntity(sellerId).getOrderCount());
        return sellerDao.updateSeller(sellerEntity);
    }

    @Override
    public SellerEntity getSellerInfo(String sellerId) {
        return sellerDao.getSellerEntity(sellerId);
    }

    @Override
    public List<SellerEntity> getAllSellerEntities() {
        return sellerDao.getAllSellerEntities();
    }

    @Override
    public boolean addFood(String sellerId, String name, String photo, String foodType, double money, double discountMonty, Date startTime, Date endTime, int stock, String description) {
        FoodEntity foodEntity = new FoodEntity(sellerId, name, photo, foodType, money, discountMonty,
                DateToTimestamp.toTimeStamp(startTime), DateToTimestamp.toTimeStamp(endTime), stock, description);
        return sellerDao.addFood(foodEntity);
    }

    @Override
    public boolean modifyFood(int foodId, String sellerId, String name, String photo, String foodType, double money,
                              double discountMonty, Date startTime, Date endTime, int stock, String description) {
        FoodEntity foodEntity = new FoodEntity(sellerId, name, photo, foodType, money, discountMonty,
                DateToTimestamp.toTimeStamp(startTime), DateToTimestamp.toTimeStamp(endTime), stock, description);
        foodEntity.setFoodId(foodId);
        return sellerDao.addFood(foodEntity);
    }

    @Override
    public FoodEntity getFoodInfo(int foodId) {
        return sellerDao.getFoodById(foodId);
    }

    @Override
    public boolean deleteFood(List<Integer> foodIds) {
        return sellerDao.deleteFood(foodIds);
    }

    @Override
    public List<FoodEntity> getFoodListBySeller(String sellerId) {
        return sellerDao.getFoodListBySeller(sellerId);
    }

    @Override
    public List<DiscountTableEntity> getDiscountBySeller(String sellerId) {
        return sellerDao.getDiscountBySeller(sellerId);
    }

    @Override
    public boolean addGroupDiscount(String sellerId, List<Integer> foodIds, double discountMoney) {
        String foods = "";
        for (Integer i : foodIds) {
            foods = foods + Const.regix;
        }
        foods = foods.substring(0, foods.length() - Const.regix.length());
        DiscountTableEntity discountTableEntity = new DiscountTableEntity(sellerId, discountMoney, foods);
        return sellerDao.addGroupDiscount(discountTableEntity);
    }

    @Override
    public boolean deleteGroupDiscount(List<Integer> discountIds) {
        return sellerDao.deleteGroupDiscount(discountIds);
    }
}
