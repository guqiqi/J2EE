package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "order")
public class OrderBean {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "total")
    private double total;

    @Column(name = "discount")
    private double discount;

    @Column(name = "pay")
    private double pay;

    @Column(name = "username")
    private String username;

    public OrderBean() {
    }

    public OrderBean(double total, double discount, String username) {
        this.total = total;
        BigDecimal bg = new BigDecimal(discount);
        this.discount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.pay = total - this.discount;
        this.username = username;
    }

    public double getTotal() {
        return total;
    }

    public double getDiscount() {
        return discount;
    }

    public double getPay() {
        return pay;
    }

    public String getUsername() {
        return username;
    }
}
