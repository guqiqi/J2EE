package daoImpl;

import dao.UserDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {
    public boolean findUserByName(String username, String password) {
        try {
            Connection connection = DaoSingleton.getDs().getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            while (rs.next()){
                if(rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
                    rs.close();
                    stmt.close();
                    connection.close();
                    return true;
                }
            }
            rs.close();
            stmt.close();
            connection.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
