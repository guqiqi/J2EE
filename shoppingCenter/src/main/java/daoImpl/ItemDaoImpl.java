package daoImpl;

import dao.ItemDao;
import entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl extends ItemDao {
    public List<Item> getAllItem() {
        List<Item> itemList = new ArrayList<Item>();
        try {
            Connection connection = super.getDs().getConnection();
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM item");

            while (rs.next()) {
                itemList.add(new Item(rs.getString("name"), rs.getDouble("price")));
            }
            rs.close();
            stmt.close();
            connection.close();

            return itemList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Item>();
        }
    }
}
