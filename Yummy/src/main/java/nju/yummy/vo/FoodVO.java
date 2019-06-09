package nju.yummy.vo;

import java.util.Date;

public class FoodVO {
    private Date date;
    private String id;
    private int number;
    private double money;

    public FoodVO(Date date, String id, int number, double money) {
        this.date = date;
        this.id = id;
        this.number = number;
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public double getMoney(){
        return money;
    }
}
