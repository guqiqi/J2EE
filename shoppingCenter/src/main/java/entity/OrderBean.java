package entity;

import java.math.BigDecimal;

public class OrderBean {
    private double total;
    private double discount;
    private double pay;


    public OrderBean(double total, double discount) {
        this.total = total;
        BigDecimal bg = new BigDecimal(discount);
        this.discount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.pay = total - this.discount;
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
}
