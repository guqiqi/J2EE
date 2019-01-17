package service;

import javax.ejb.Remote;

@Remote
public interface UserService {
    public boolean Login(String username, String password);
}
