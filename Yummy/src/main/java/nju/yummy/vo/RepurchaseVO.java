package nju.yummy.vo;

public class RepurchaseVO {
    private int newPurchase;
    private int olderPruchase;

    public RepurchaseVO(int newPurchase, int olderPruchase) {
        this.newPurchase = newPurchase;
        this.olderPruchase = olderPruchase;
    }

    public int getNewPurchase() {
        return newPurchase;
    }

    public void setNewPurchase(int newPurchase) {
        this.newPurchase = newPurchase;
    }

    public int getOlderPruchase() {
        return olderPruchase;
    }

    public void setOlderPruchase(int olderPruchase) {
        this.olderPruchase = olderPruchase;
    }
}
