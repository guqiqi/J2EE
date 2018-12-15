package entity;

import java.util.List;

public class ItemListBean {
    private List<Item> itemList;

    public ItemListBean() {
    }

    public ItemListBean(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
