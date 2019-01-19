package nju.yummy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "discountTable", schema = "yummy")
public class DiscountTableEntity {
    private Integer discountId;
    private Double discountMoney;
    private String foodIds;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "discountId")
    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
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
    @Column(name = "foodIds")
    public String getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(String foodIds) {
        this.foodIds = foodIds;
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
