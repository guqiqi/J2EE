package nju.yummy.vo;

public class RepurchaseVO {
    private int newPurchase;
    private int olderPurchase;

    public RepurchaseVO(int newPurchase, int olderPurchase) {
        this.newPurchase = newPurchase;
        this.olderPurchase = olderPurchase;
    }

    public int getNewPurchase() {
        return newPurchase;
    }

    public void setNewPurchase(int newPurchase) {
        this.newPurchase = newPurchase;
    }

    public int getOlderPurchase() {
        return olderPurchase;
    }

    public void setOlderPurchase(int olderPruchase) {
        this.olderPurchase = olderPruchase;
    }
}
