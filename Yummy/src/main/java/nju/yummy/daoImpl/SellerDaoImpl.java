package nju.yummy.daoImpl;

import nju.yummy.dao.SellerDao;
import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.FoodEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.util.SellerStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    private DaoUtil daoUtil = new DaoUtil();

    @Override
    public boolean addSeller(SellerEntity sellerEntity) {
        return daoUtil.add(sellerEntity);
    }

    @Override
    public boolean updateSeller(SellerEntity sellerEntity) {
        return daoUtil.update(sellerEntity);
    }

    @Override
    public boolean verifySeller(String sellerId, SellerStatus sellerStatus) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("update SellerEntity s set s.status = :newStatus where s.sellerId = " +
                ":sellerId");
        query.setParameter("newStatus", sellerStatus.getIndex());
        query.setParameter("sellerId", sellerId);

        query.executeUpdate();

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public SellerEntity getSellerEntity(String sellerId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        SellerEntity sellerEntity = session.get(SellerEntity.class, sellerId);

        tx.commit();

        session.close();
        return sellerEntity;
    }

    @Override
    public List<SellerEntity> getAllSellerEntities() {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select seller from SellerEntity seller");

        List<SellerEntity> sellerEntities = query.list();

        tx.commit();
        session.close();

        return sellerEntities;
    }

    @Override
    public boolean addFood(FoodEntity foodEntity) {
        return daoUtil.add(foodEntity);
    }

    @Override
    public boolean updateFood(FoodEntity foodEntity) {
        return daoUtil.update(foodEntity);
    }

    @Override
    public FoodEntity getFoodById(int foodId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        FoodEntity foodEntity = session.get(FoodEntity.class, foodId);

        tx.commit();

        session.close();
        return foodEntity;
    }

    @Override
    public boolean deleteFood(List<Integer> foodIds) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("delete FoodEntity where foodId=:foodId");
        for (Integer id : foodIds) {
            query.setParameter("foodId", id);
            query.executeUpdate();
        }

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public List<FoodEntity> getFoodListBySeller(String sellerId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select food from FoodEntity food where sellId=:sellerId");
        query.setParameter("sellerId", sellerId);

        List<FoodEntity> foodEntities = query.list();

        tx.commit();
        session.close();

        return foodEntities;
    }

    @Override
    public List<DiscountTableEntity> getDiscountBySeller(String sellerId) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select discount from DiscountTableEntity discount where sellerId=:sellerId");
        query.setParameter("sellerId", sellerId);

        List<DiscountTableEntity> discountTableEntities = query.list();

        tx.commit();
        session.close();

        return discountTableEntities;
    }

    @Override
    public boolean addGroupDiscount(DiscountTableEntity discountTableEntity) {
        return daoUtil.add(discountTableEntity);
    }

    @Override
    public boolean updateGroupDiscount(DiscountTableEntity discountTableEntity) {
        return daoUtil.update(discountTableEntity);
    }

    @Override
    public boolean deleteGroupDiscount(List<Integer> discountIds) {
        Session session = MySessionFactory.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("delete DiscountTableEntity where discountId=:discountId");
        for (Integer id : discountIds) {
            query.setParameter("discountId", id);
            query.executeUpdate();
        }

        tx.commit();
        session.close();

        return true;
    }
}
