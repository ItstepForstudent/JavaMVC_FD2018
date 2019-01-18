package org.itstep.mvc.repositories;


import org.itstep.mvc.entities.User;
import org.junit.Test;

import java.io.Console;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;


public class UserDaoJsonTest {

    static UsersDao dao = new UserDaoJson();

    @Test
    public void getUsers() {
        List<User> users = dao.getUsers();
    }

    @Test
    public void addUser() {
        int len = dao.getUsers().size();
        dao.addUser(new User("","petia"));
        System.out.println(dao.getUsers());
        int len2 = dao.getUsers().size();
        assertEquals(len2,len+1);
    }

    @Test
    public void delUser() {

//        List<User> users = dao.getUsers();
//        int len1 = users.size();
//        if(len1==0) return;
//
//        User u = users.get(users.size()-1);
//        dao.delUser(u.getId());
//
//        int len2 = dao.getUsers().size();
//        assertEquals(len1,len2+1);

    }
}
