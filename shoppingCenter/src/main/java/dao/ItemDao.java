package dao;

import entity.Item;
import factory.DaoFactory;

import java.util.List;

public abstract class ItemDao extends DaoFactory {
    public abstract List<Item> getAllItem();
}