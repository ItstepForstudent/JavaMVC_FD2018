package org.itstep.mvc.controllers;

import com.google.gson.Gson;
import org.itstep.mvc.core.annotation.Autowired;
import org.itstep.mvc.core.annotation.Controller;
import org.itstep.mvc.core.annotation.GetMapping;
import org.itstep.mvc.core.annotation.PostMapping;
import org.itstep.mvc.dto.GetUsersResponse;
import org.itstep.mvc.dto.StatusResponse;
import org.itstep.mvc.entities.User;
import org.itstep.mvc.repositories.UsersDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UserApiController {

    @Autowired
    UsersDao usersDao;

    Gson gson = new Gson();


    @GetMapping("/users/all")
    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<User> users = usersDao.getUsers();
        GetUsersResponse response = new GetUsersResponse("ok","",users);
        resp.getWriter().write(gson.toJson(response));
    }

    @PostMapping("/users/add")
    public void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        if(name==null || name.trim().equals("")){
            resp.getWriter().write(gson.toJson(new StatusResponse("error","No all params")));
            return;
        }
        User u = new User(null,name);
        usersDao.addUser(u);
        resp.getWriter().write(gson.toJson(new StatusResponse("ok","")));
    }

    @PostMapping("/users/del")
    public void delUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if(id==null || id.trim().equals("")){
            resp.getWriter().write(gson.toJson(new StatusResponse("error","No all params")));
            return;
        }
        usersDao.delUser(id);
        resp.getWriter().write(gson.toJson(new StatusResponse("ok","")));
    }
}
