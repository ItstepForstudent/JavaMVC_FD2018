package org.itstep.mvc.core.reflectutil;

import org.itstep.mvc.core.annotation.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    Map<Class,Object> instanceMap = new HashMap<>();
    List<Class> classList;
    public Context() {
        classList = new PackageScanner("org.itstep.mvc").getClassesWithAnnotation(Component.class);
    }

    synchronized public Object getInstance(Class c){
        for (Class mc:instanceMap.keySet()){
            if(c.isAssignableFrom(mc)){
                return instanceMap.get(mc);
            }
        }

        for (Class cls:classList){
            if(c.isAssignableFrom(cls)){
                try {
                    Object instance = cls.getConstructor().newInstance();
                    instanceMap.put(c,instance);
                    return instance;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
