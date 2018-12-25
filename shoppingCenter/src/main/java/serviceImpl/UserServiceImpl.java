package serviceImpl;

import daoImpl.UserDaoImpl;
import service.UserService;

public class UserServiceImpl implements UserService {
    public boolean Login(String username, String password) {
        return new UserDaoImpl().findUserByName(username, password);
    }
}
