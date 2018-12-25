package daoImpl;

import dao.OrderDao;
import entity.OrderBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDaoImpl extends OrderDao {
    public boolean insertOrderBean(OrderBean orderBean) {
        try {
            Connection connection = super.getDs().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders.order (username, " +
                    "total, discount, pay) VALUES(?,?,?,?);");
            preparedStatement.setString(1, orderBean.getUsername());
            preparedStatement.setDouble(2, orderBean.getTotal());
            preparedStatement.setDouble(3, orderBean.getDiscount());
            preparedStatement.setDouble(4, orderBean.getPay());
            preparedStatement.executeUpdate();

            connection.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
