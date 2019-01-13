package org.itstep.mvc.controllers;

import org.itstep.mvc.core.ViewController;
import org.itstep.mvc.core.annotation.Autowired;
import org.itstep.mvc.core.annotation.Controller;
import org.itstep.mvc.core.annotation.GetMapping;
import org.itstep.mvc.repositories.MRepo;
import org.itstep.mvc.repositories.MainRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController extends ViewController {

    @Autowired
    MRepo mainRepository;

    @GetMapping("/")
    public void index(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setAttribute("name",mainRepository.getName());
        renderView(req,resp,"index");
    }

    @GetMapping("/hello")
    public void hello(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Hello world !!!!");
    }
}
