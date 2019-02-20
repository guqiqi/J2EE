package nju.yummy.vo;

public class SellerCostVO {
    private String name;

    private double cost;
    private double returnMoney;
    private int times;

    public SellerCostVO(String name, double cost) {
        this.name = name;
        this.cost = cost;
        this.times = 1;
        this.returnMoney = 0.0;
    }

    public SellerCostVO(String name) {
        this.name = name;
        this.cost = 0.0;
        this.times = 0;
        this.returnMoney = 0.0;
    }

    public void addCost(double cost) {
        this.cost = this.cost + cost;
        this.times = this.times + 1;
    }

    public void addCancelOrder(double cost, double returnMoney) {
        this.cost = this.cost + cost;
        this.returnMoney = this.returnMoney + returnMoney;
        this.times = this.times + 1;
    }

    public double getCost() {
        return cost;
    }

    public int getTimes() {
        return times;
    }

    public String getName(){
        return name;
    }
}
