package nju.yummy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "orderTable", schema = "yummy")
public class OrderEntity {
    private String orderId;
    private String email;
    private String sellerId;
    private Integer status;
    private Timestamp placeTime;
    private Timestamp deliverTime;
    private Timestamp finishTime;
    private Double totalMoney;
    private Double payMoney;
    private Timestamp reachTime;
    private Integer addressId;
    private String foodIds;
    private String foodNumbers;
    private Integer courierId;

    public OrderEntity() {
    }

    public OrderEntity(String orderId, String email, String sellerId, Double totalMoney, Double payMoney,
                       Timestamp reachTime, Integer addressId, String foodIds, String foodNumbers) {
        this.orderId = orderId;
        this.email = email;
        this.sellerId = sellerId;
        this.status = 0;
        this.placeTime = new Timestamp(System.currentTimeMillis());
        this.totalMoney = totalMoney;
        this.payMoney = payMoney;
        this.reachTime = reachTime;
        this.addressId = addressId;
        this.foodIds = foodIds;
        this.foodNumbers = foodNumbers;
    }

    public OrderEntity(String orderId, String email, String sellerId, Double totalMoney, Double payMoney,
                       Timestamp reachTime, Integer addressId, String foodIds, String foodNumbers, Integer courierId) {
        this.orderId = orderId;
        this.email = email;
        this.sellerId = sellerId;
        this.status = 0;
        this.placeTime = new Timestamp(System.currentTimeMillis());
        this.totalMoney = totalMoney;
        this.payMoney = payMoney;
        this.reachTime = reachTime;
        this.addressId = addressId;
        this.foodIds = foodIds;
        this.foodNumbers = foodNumbers;
        this.courierId = courierId;
    }


    public OrderEntity(Double totalMoney, Double payMoney) {
        this.totalMoney = totalMoney;
        this.payMoney = payMoney;
    }

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
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
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
    @Column(name = "deliverTime")
    public Timestamp getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Timestamp deliverTime) {
        this.deliverTime = deliverTime;
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

    @Basic
    @Column(name = "addressId")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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
    @Column(name = "foodNumbers")
    public String getFoodNumbers() {
        return foodNumbers;
    }

    public void setFoodNumbers(String foodNumbers) {
        this.foodNumbers = foodNumbers;
    }

    @Basic
    @Column(name = "courierId")
    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }
}
