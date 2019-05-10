package nju.yummy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "courier", schema = "yummy")
public class CourierEntity {
    private Integer courierId;
    private String username;
    private String phone;
    private String password;

    public CourierEntity(Integer courierId, String username, String phone, String password) {
        this.courierId = courierId;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public CourierEntity(String username, String phone, String password) {
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public CourierEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "courierId")
    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourierEntity that = (CourierEntity) o;
        return Objects.equals(courierId, that.courierId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courierId, username, phone, password);
    }
}
