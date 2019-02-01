package org.itstep.mvc.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.itstep.mvc.core.annotation.Component;
import org.itstep.mvc.entities.User;
import org.itstep.mvc.util.JsonFileUtil;

import java.util.*;
import java.util.stream.Collectors;


public class UserDaoJson implements UsersDao{


    final static JsonFileUtil userFileUtil = new JsonFileUtil("users.json");

    private Gson gson = new Gson();

    @Override
    public List<User> getUsers() {
            String json = userFileUtil.getJsonString();
            List<User> users = gson.fromJson(json,new TypeToken<List<User>>(){}.getType());
            return new ArrayList<>(users);
    }


    @Override
    public void addUser(User u) {
        List<User> users = getUsers();
        //u.setId(DigestUtils.md5Hex(new Date().toString()));
        u.setId(0L);
        users.add(u);
        userFileUtil.saveJsonString(gson.toJson(users));
    }

    @Override
    public void delUser(String uid) {
        List<User> users = getUsers();

        users = users.stream()
                .filter(u->!u.getId().equals(uid))
                .collect(Collectors.toList());

        userFileUtil.saveJsonString(gson.toJson(users));
    }
}
