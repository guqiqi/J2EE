package nju.yummy.vo;

public class OrderPriceVO {
    private String orderId;
    private double total;
    private double pay;
    private double profit;

    public OrderPriceVO(double total, double pay, double profit) {
        this.total = total;
        this.pay = pay;
        this.profit = profit;
    }

    public OrderPriceVO(String orderId, double total, double pay, double profit) {
        this.orderId = orderId;
        this.total = total;
        this.pay = pay;
        this.profit = profit;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
