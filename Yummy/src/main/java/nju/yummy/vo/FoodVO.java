package nju.yummy.vo;

import java.util.Date;

public class FoodVO {
    private Date date;
    private String id;
    private int number;

    public FoodVO(Date date, String id, int number) {
        this.date = date;
        this.id = id;
        this.number = number;
    }
}
