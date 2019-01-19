package nju.yummy.util;

public enum SellerStatus {
    TOMODIFY(0), TOVERIFY(1), NORMAL(2), MODIFYTOVERIFY(3);

    private int index ;

    private SellerStatus(int index ){
        this.index = index ;
    }

    public int getIndex() {
        return index;
    }
}
