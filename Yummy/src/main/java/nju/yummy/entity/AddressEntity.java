package nju.yummy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address", schema = "yummy")
public class AddressEntity {
    private Integer addressId;
    private String email;
    private String detail;
    private String phone;
    private String receiver;
    private double latitude;
    private double longitude;

    public AddressEntity() {
    }

    public AddressEntity(String email, String detail, String phone, String receiver) {
        this.email = email;
        this.detail = detail;
        this.phone = phone;
        this.receiver = receiver;

        this.longitude = Math.random()*180-90;
        this.latitude = Math.random()*360-180;
    }

    public AddressEntity(Integer addressId, String email, String detail, String phone, String receiver) {
        this.addressId = addressId;
        this.email = email;
        this.detail = detail;
        this.phone = phone;
        this.receiver = receiver;

        this.longitude = Math.random()*180-90;
        this.latitude = Math.random()*360-180;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "addressId")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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
    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
    @Column(name = "receiver")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(addressId, that.addressId) &&
                Objects.equals(email, that.email) &&
                Objects.equals(detail, that.detail) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(latitude, that.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, email, detail, phone, receiver, latitude, longitude);
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
