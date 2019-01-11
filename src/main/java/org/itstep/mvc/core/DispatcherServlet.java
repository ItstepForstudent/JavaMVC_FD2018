package org.itstep.mvc.core;



import org.itstep.mvc.core.annotation.Controller;
import org.itstep.mvc.core.annotation.GetMapping;
import org.itstep.mvc.core.annotation.PostMapping;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {

        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

        ArrayList<Class> classes = new ArrayList<Class>();

        while (resources.hasMoreElements()) {
            File directory = new File(resources.nextElement().getFile());
            classes.addAll(findClasses(directory, packageName));
        }

        return classes.toArray(new Class[classes.size()]);
    }
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists())  return classes;

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }




    private List<Class> getAllControllers() throws IOException, ClassNotFoundException {
        Class[] classes = getClasses("org.itstep.mvc");
        List<Class> controllers = new LinkedList<>();
        for (Class c:classes){
            if(c.isAnnotationPresent(Controller.class)) controllers.add(c);
        }
        return controllers;
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        String uri = req.getRequestURI();
        String method = req.getMethod();
        String baseurl = "/mvc";

        try {

            List<Class> controllers = getAllControllers();

            boolean isFind = false;

            label:
            for (Class controller : controllers) {
                Method[] methods= controller.getMethods();
                String testUrl=null;
                for (Method m : methods) {
                    if(m.isAnnotationPresent(GetMapping.class) && method.equals("GET")){
                        GetMapping annotation = m.getAnnotation(GetMapping.class);
                        testUrl = annotation.value();
                    }
                    else if(m.isAnnotationPresent(PostMapping.class) && method.equals("POST")){
                        PostMapping annotation = m.getAnnotation(PostMapping.class);
                        testUrl = annotation.value();
                    }

                    if(testUrl!=null && (baseurl+ testUrl).equals(uri)){
                        Object o = controller.getConstructor().newInstance();
                        m.invoke(o,req,resp);
                        isFind=true;
                        break label;
                    }
                }
            }


            if(!isFind){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("404 Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
