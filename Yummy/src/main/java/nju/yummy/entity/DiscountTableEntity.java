package nju.yummy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "discountTable", schema = "yummy")
public class DiscountTableEntity {
    private Integer discountId;
    private String sellerId;
    private Double discountMoney;
    private Double money;
    private String foodIds;
    private String foodNames;
    private Date startTime;
    private Date endTime;

    public DiscountTableEntity() {
    }

    public DiscountTableEntity(String sellerId, Double discountMoney, Double money, String foodIds, String foodNames,
                               Date startTime, Date endTime) {
        this.sellerId = sellerId;
        this.discountMoney = discountMoney;
        this.money = money;
        this.foodIds = foodIds;
        this.foodNames = foodNames;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DiscountTableEntity(Integer discountId, String sellerId, double discountMoney, double money, String foodIds,
                               String foodNames, Date startTime, Date endTime) {
        this.discountId = discountId;
        this.sellerId = sellerId;
        this.discountMoney = discountMoney;
        this.money = money;
        this.foodIds = foodIds;
        this.foodNames = foodNames;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discountId")
    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    @Column(name = "sellerId")
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "discountMoney")
    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    @Basic
    @Column(name = "money")
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "foodIds")
    public String getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(String foodIds) {
        this.foodIds = foodIds;
    }

    @Basic
    @Column(name = "foodNames")
    public String getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(String foodNames) {
        this.foodNames = foodNames;
    }

    @Basic
    @Column(name = "startTime")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "endTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountTableEntity that = (DiscountTableEntity) o;
        return Objects.equals(discountId, that.discountId) &&
                Objects.equals(discountMoney, that.discountMoney) &&
                Objects.equals(foodIds, that.foodIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, discountMoney, foodIds);
    }

}
