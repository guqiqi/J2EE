package nju.yummy.dao;

import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.util.SellerStatus;

import java.util.List;

public interface SellerDao {
    public boolean addSeller(SellerEntity sellerEntity);

    public boolean updateSeller(SellerEntity sellerEntity);

    public boolean verifySeller(String sellerId, SellerStatus sellerStatus);

    public SellerEntity getSellerEntity(String sellerId);

    public List<SellerEntity> getAllSellerEntities();

    public boolean addFood(FoodEntity foodEntity);

    public boolean updateFood(FoodEntity foodEntity);

    public FoodEntity getFoodById(int foodId);

    public boolean deleteFood(List<Integer> foodIds);

    public List<FoodEntity> getFoodListBySeller(String sellerId);

    public List<DiscountTableEntity> getDiscountBySeller(String sellerId);

    public boolean addGroupDiscount(DiscountTableEntity discountTableEntity);

    public boolean deleteGroupDiscount(List<Integer> discountIds);
}
