package org.itstep.mvc.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ViewController {
    public void renderView(HttpServletRequest req,HttpServletResponse resp,String viewName) throws ServletException, IOException {
        System.out.println("/WEB-INF/views/"+viewName+".jsp");
        req.getRequestDispatcher("/WEB-INF/views/"+viewName+".jsp").forward(req,resp);
    }
}
