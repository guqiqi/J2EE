package entity;

public class OrderBean {
    private double total;
    private double discount;
    private double pay;


    public OrderBean(double total, double discount, double pay) {
        this.total = total;
        this.discount = discount;
        this.pay = pay;
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
