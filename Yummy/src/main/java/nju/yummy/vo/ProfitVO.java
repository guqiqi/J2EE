package nju.yummy.vo;

import nju.yummy.util.Const;

public class ProfitVO {
    private String sellerId;
    private String name;
    private double amount;
    private double profit;

    public ProfitVO(String sellerId, String name, double amount) {
        this.sellerId = sellerId;
        this.name = name;
        this.amount = (double) Math.round(amount * 100) / 100;
        this.profit = (double) Math.round(amount * (1 - Const.cut) * 100) / 100;
    }

    public void addAmount(double amount) {
        this.amount = (double) Math.round((this.amount + amount) * 100) / 100;
        this.profit = (double) Math.round(this.amount * (1 - Const.cut) * 100) / 100;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getProfit() {
        return profit;
    }
}
