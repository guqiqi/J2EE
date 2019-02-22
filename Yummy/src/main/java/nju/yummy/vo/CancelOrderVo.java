package nju.yummy.vo;

import java.util.Date;

public class CancelOrderVO {
    String name;
    String placeTime;
    String cancelTime;
    String payMoney;
    String returnMoney;

    public CancelOrderVO(String name, Date cancelTime, Date placeTime, String payMoney, String returnMoney) {
        this.name = name;

        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formatTime = format.format(cancelTime);
        this.cancelTime = formatTime;
        formatTime = format.format(placeTime);
        this.placeTime = formatTime;

        this.payMoney = payMoney;
        this.returnMoney = returnMoney;
    }

    public String getName() {
        return name;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public String getPlaceTime(){
        return placeTime;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public String getReturnMoney() {
        return returnMoney;
    }
}
