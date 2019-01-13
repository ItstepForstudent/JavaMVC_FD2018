package org.itstep.mvc.core;


import org.itstep.mvc.core.annotation.Autowired;
import org.itstep.mvc.core.annotation.Controller;
import org.itstep.mvc.core.annotation.GetMapping;
import org.itstep.mvc.core.annotation.PostMapping;
import org.itstep.mvc.core.reflectutil.Context;
import org.itstep.mvc.core.reflectutil.PackageScanner;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private Context ctx = new Context();

    private boolean staticProcess(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        String baseurl = req.getContextPath();
        if (!uri.startsWith(baseurl + "/static")) return false;
        try {
            getServletContext().getNamedDispatcher("default").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean callControllerMethod(Class controller,HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        String uri = req.getRequestURI();
        String method = req.getMethod();
        String baseurl = req.getContextPath();

        Method[] methods = controller.getMethods();
        String testUrl = null;
        for (Method m : methods) {
            if (m.isAnnotationPresent(GetMapping.class) && method.equals("GET")) {
                GetMapping annotation = m.getAnnotation(GetMapping.class);
                testUrl = annotation.value();
            } else if (m.isAnnotationPresent(PostMapping.class) && method.equals("POST")) {
                PostMapping annotation = m.getAnnotation(PostMapping.class);
                testUrl = annotation.value();
            }

            if (testUrl != null && (baseurl + testUrl).equals(uri)) {
                Object o = controller.getConstructor().newInstance();

                for(Field f:controller.getDeclaredFields()){
                    if(f.isAnnotationPresent(Autowired.class)){
                        f.setAccessible(true);
                        Object val = ctx.getInstance(f.getType());
                        if(val!=null){
                            f.set(o,val);
                        }
                    }
                }


                m.invoke(o, req, resp);


                return true;
            }
        }
        return false;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        if(staticProcess(req,resp)) return;
        PackageScanner packageScanner = new PackageScanner("org.itstep.mvc");
        try {
            List<Class> controllers = packageScanner.getClassesWithAnnotation(Controller.class);

            boolean isFind = false;

            for (Class controller : controllers) {
                if(callControllerMethod(controller,req,resp)){
                    isFind=true;
                    break;
                }
            }

            if (!isFind) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("404 Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
