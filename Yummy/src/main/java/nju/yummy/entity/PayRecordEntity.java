package nju.yummy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payRecord", schema = "yummy")
public class PayRecordEntity {
    private Integer recordId;
    private Byte payType;
    private String userId;
    private Double money;
    private Byte userType;

    @Id
    @Column(name = "recordId")
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "payType")
    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    @Column(name = "userType")
    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayRecordEntity that = (PayRecordEntity) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(payType, that.payType) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(money, that.money) &&
                Objects.equals(userType, that.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, payType, userId, money, userType);
    }
}
