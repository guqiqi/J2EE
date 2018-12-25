package dao;

import factory.DaoFactory;

public abstract class UserDao extends DaoFactory {
    public abstract boolean findUserByName(String username, String password);
}
