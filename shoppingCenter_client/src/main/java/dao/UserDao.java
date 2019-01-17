package dao;

import factory.DaoFactory;

public interface UserDao{
    boolean findUserByName(String username, String password);
}
