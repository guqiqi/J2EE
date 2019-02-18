package nju.yummy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "payRecord", schema = "yummy")
public class PayRecordEntity {
    private Integer recordId;
    // 1为付款，0为收款
    private Byte payType;
    private String userId;
    private Double money;
    // 1为客户，0为商家
    private Byte userType;
    private Timestamp time;

    public PayRecordEntity(Byte payType, String userId, Double money, Byte userType) {
        this.payType = payType;
        this.userId = userId;
        this.money = money;
        this.userType = userType;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public PayRecordEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
