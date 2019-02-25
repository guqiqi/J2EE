package nju.yummy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "payRecord", schema = "yummy")
public class PayRecordEntity {
    private Integer recordId;
    private String payId;
    private String receiverId;
    private String orderId;
    private Double money;
    private Timestamp time;

    public PayRecordEntity(String payId, String receiverId, Double money, String orderId) {
        this.payId = payId;
        this.receiverId = receiverId;
        this.money = money;
        this.time = new Timestamp(System.currentTimeMillis());
        this.orderId = orderId;
    }

    public PayRecordEntity(String receiverId, Double money) {
        this.payId = "-1";
        this.receiverId = receiverId;
        this.money = money;
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
    @Column(name = "payId")
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    @Basic
    @Column(name = "receiverId")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "orderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayRecordEntity that = (PayRecordEntity) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(payId, that.payId) &&
                Objects.equals(receiverId, that.receiverId) &&
                Objects.equals(money, that.money) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, payId, receiverId, money, time);
    }
}
