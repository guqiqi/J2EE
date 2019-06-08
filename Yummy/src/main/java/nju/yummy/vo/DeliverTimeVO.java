package nju.yummy.vo;

public class DeliverTimeVO {
    private int fast;
    private int slow;
    private int average;

    public DeliverTimeVO(int fast, int slow, int average) {
        this.fast = fast;
        this.slow = slow;
        this.average = average;
    }

    public int getFast() {
        return fast;
    }

    public void setFast(int fast) {
        this.fast = fast;
    }

    public int getSlow() {
        return slow;
    }

    public void setSlow(int slow) {
        this.slow = slow;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }
}
