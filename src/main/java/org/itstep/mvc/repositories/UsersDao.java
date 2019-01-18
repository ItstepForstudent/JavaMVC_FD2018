package org.itstep.mvc.repositories;

import org.itstep.mvc.entities.User;

import java.io.FileNotFoundException;
import java.util.List;

public interface UsersDao {
    List<User> getUsers();
    void addUser(User u);
    void delUser(String uid);
}
