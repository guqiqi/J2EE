package nju.yummy.util;

public enum OrderStatus {
    UNPAID(0), TOBEDELIVERED(1), DEVERING(2), FINISHED(3), CANCEL(-1);

    private int index ;

    private OrderStatus(int index ){
        this.index = index ;
    }

    public int getIndex() {
        return index;
    }
}
