package nju.yummy.vo;

public class BostonVO {
    private int rate;
    private int price;
    private int name;
    private int number;

    public BostonVO(int rate, int price, int name, int number) {
        this.rate = rate;
        this.price = price;
        this.name = name;
        this.number = number;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
