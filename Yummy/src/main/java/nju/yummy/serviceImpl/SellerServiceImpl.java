package nju.yummy.serviceImpl;

import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.SellerService;

import java.util.Date;
import java.util.List;

public class SellerServiceImpl implements SellerService {
    @Override
    public String register(String password, String name, int type, String address, String phone, String startHour, String endHour, String foodType, String discount, String icon) {
        return null;
    }

    @Override
    public boolean verifySeller(String sellerId, boolean isPass) {
        return false;
    }

    @Override
    public boolean modifyInfo(String sellerId, String password, String name, int type, String address, String phone, String startHour, String endHour, String foodType, String discount, String icon) {
        return false;
    }

    @Override
    public SellerEntity getSellerInfo(String sellerId) {
        return null;
    }

    @Override
    public boolean addFood(String sellerId, String name, String photo, String foodType, double money, double discountMonty, Date startTime, Date endTime, int stock, String description) {
        return false;
    }

    @Override
    public FoodEntity getFoodInfo(int foodId) {
        return null;
    }

    @Override
    public boolean deleteFood(List<Integer> foodIds) {
        return false;
    }

    @Override
    public List<FoodEntity> getFoodListBySeller(String sellerId) {
        return null;
    }

    @Override
    public List<DiscountTableEntity> getDiscountBySeller(String sellerId) {
        return null;
    }

    @Override
    public boolean addGroupDiscount(String sellerId, List<Integer> foodIds, double discountMoney) {
        return false;
    }

    @Override
    public boolean deleteGroupDiscount(List<Integer> discountIds) {
        return false;
    }
}
