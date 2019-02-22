package nju.yummy.entity;

import nju.yummy.util.DateToTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "seller", schema = "yummy")
public class SellerEntity {
    private String sellerId;
    private String password;
    private String name;
    private String type;
    private String address;
    private String phone;
    private String startHour;
    private String endHour;
    private Integer status;
    private String foodType;
    private Integer orderCount;
    private String discount;
    private String icon;
    private Timestamp registerTime;

    private String tempPassword;
    private String tempName;
    private String tempType;
    private String tempAddress;
    private String tempPhone;
    private String tempStartHour;
    private String tempEndHour;
    private String tempIcon;

    private double latitude;
    private double longitude;

    public SellerEntity() {
    }

    public SellerEntity(String sellerId, String password, String name, String type, String address, String phone,
                        String startHour, String endHour, Integer status, String foodType, String discount, String icon) {
        this.sellerId = sellerId;
        this.password = password;
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.startHour = startHour;
        this.endHour = endHour;
        this.status = status;
        this.foodType = foodType;
        this.orderCount = 0;
        this.discount = discount;
        this.icon = icon;
        this.registerTime = DateToTimestamp.toTimeStamp(new Date());
        this.longitude = Math.random()*180-90;
        this.latitude = Math.random()*360-180;
    }

    @Id
    @Column(name = "sellerId")
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "startHour")
    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    @Basic
    @Column(name = "endHour")
    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
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
    @Column(name = "foodType")
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Basic
    @Column(name = "orderCount")
    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    @Basic
    @Column(name = "discount")
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "tempPassword")
    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    @Basic
    @Column(name = "tempName")
    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    @Basic
    @Column(name = "tempType")
    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) {
        this.tempType = tempType;
    }

    @Basic
    @Column(name = "tempAddress")
    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }

    @Basic
    @Column(name = "tempPhone")
    public String getTempPhone() {
        return tempPhone;
    }

    public void setTempPhone(String tempPhone) {
        this.tempPhone = tempPhone;
    }

    @Basic
    @Column(name = "tempStartHour")
    public String getTempStartHour() {
        return tempStartHour;
    }

    public void setTempStartHour(String tempStartHour) {
        this.tempStartHour = tempStartHour;
    }

    @Basic
    @Column(name = "tempEndHour")
    public String getTempEndHour() {
        return tempEndHour;
    }

    public void setTempEndHour(String tempEndHour) {
        this.tempEndHour = tempEndHour;
    }

    @Basic
    @Column(name = "tempIcon")
    public String getTempIcon() {
        return tempIcon;
    }

    public void setTempIcon(String tempIcon) {
        this.tempIcon = tempIcon;
    }

    @Basic
    @Column(name = "registerTime")
    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerEntity that = (SellerEntity) o;
        return Objects.equals(sellerId, that.sellerId) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(startHour, that.startHour) &&
                Objects.equals(endHour, that.endHour) &&
                Objects.equals(status, that.status) &&
                Objects.equals(foodType, that.foodType) &&
                Objects.equals(orderCount, that.orderCount) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, password, name, type, address, phone, startHour, endHour, status, foodType, orderCount, discount, icon);
    }

    @Basic
    @Column(name = "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
