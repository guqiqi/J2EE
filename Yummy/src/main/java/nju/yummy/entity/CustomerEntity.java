package nju.yummy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer", schema = "yummy")
public class CustomerEntity {
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String phone;
    private Double point;
    private Byte status;
    private Double leftMoney;

    public CustomerEntity() {
    }

    public CustomerEntity(String email, String username, String password, String avatar, String phone) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.status = 1;
        this.leftMoney = 1000.0;
        this.point = 0.0;
    }

    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
    @Column(name = "point")
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "leftMoney")
    public Double getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(Double leftMoney) {
        this.leftMoney = leftMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(point, that.point) &&
                Objects.equals(status, that.status) &&
                Objects.equals(leftMoney, that.leftMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, password, avatar, phone, point, status, leftMoney);
    }
}
