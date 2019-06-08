package nju.yummy.vo;

public class CancelStatisticVO {
    private int totalNumber;
    private int cancelNumber;
    private double totalMoney;
    private double returnMoney;
    private double fineMoney;

    public CancelStatisticVO(int totalNumber, int cancelNumber, double totalMoney, double returnMoney, double fineMoney) {
        this.totalNumber = totalNumber;
        this.cancelNumber = cancelNumber;
        this.totalMoney = totalMoney;
        this.returnMoney = returnMoney;
        this.fineMoney = fineMoney;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getCancelNumber() {
        return cancelNumber;
    }

    public void setCancelNumber(int cancelNumber) {
        this.cancelNumber = cancelNumber;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public double getFineMoney() {
        return fineMoney;
    }

    public void setFineMoney(double fineMoney) {
        this.fineMoney = fineMoney;
    }
}
