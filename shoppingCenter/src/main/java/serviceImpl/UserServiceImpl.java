package serviceImpl;

import factory.DaoFactory;
import service.UserService;

public class UserServiceImpl implements UserService {
    private DaoFactory daoFactory = new DaoFactory();

    public boolean Login(String username, String password) {
        return daoFactory.getUserDao().findUserByName(username, password);
    }
}
