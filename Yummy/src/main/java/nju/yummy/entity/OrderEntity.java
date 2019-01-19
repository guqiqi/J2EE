package nju.yummy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "order", schema = "yummy")
public class OrderEntity {
    private String orderId;
    private String email;
    private Integer sellerId;
    private Integer status;
    private Timestamp placeTime;
    private Timestamp finishTime;
    private Double totalMoney;
    private Double payMoney;
    private Timestamp reachTime;

    @Id
    @Column(name = "orderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "sellerId")
    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "placeTime")
    public Timestamp getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(Timestamp placeTime) {
        this.placeTime = placeTime;
    }

    @Basic
    @Column(name = "finishTime")
    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "totalMoney")
    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Basic
    @Column(name = "payMoney")
    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(email, that.email) &&
                Objects.equals(sellerId, that.sellerId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(placeTime, that.placeTime) &&
                Objects.equals(finishTime, that.finishTime) &&
                Objects.equals(totalMoney, that.totalMoney) &&
                Objects.equals(payMoney, that.payMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, email, sellerId, status, placeTime, finishTime, totalMoney, payMoney);
    }

    @Basic
    @Column(name = "reachTime")
    public Timestamp getReachTime() {
        return reachTime;
    }

    public void setReachTime(Timestamp reachTime) {
        this.reachTime = reachTime;
    }
}
