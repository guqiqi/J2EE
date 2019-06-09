package nju.yummy.vo;

public class BostonVO {
    private double rate;
    private double price;
    private String name;
    private int number;

    public BostonVO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public BostonVO(double rate, double price, String name, int number) {
        this.rate = rate;
        this.price = price;
        this.name = name;
        this.number = number;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
