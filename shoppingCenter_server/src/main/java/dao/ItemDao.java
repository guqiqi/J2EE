package dao;

import entity.Item;
import factory.DaoFactory;

import java.util.List;

public interface ItemDao{
    List<Item> getAllItem();
}