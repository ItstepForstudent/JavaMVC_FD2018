package org.itstep.mvc.core.reflectutil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageScanner {

    private String packageName;

    public PackageScanner(String packageName) {
        this.packageName = packageName;
    }
    public List<Class> getClassesWithAnnotation(final Class<? extends Annotation> annotation){
        return getAllClasses().stream()
                .filter(c->c.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public List<Class> getAllClasses(){
        String path = packageName.replace('.','/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            URI pathToFile = classLoader.getResource(path).toURI();
            return getAllInFolder(new File(pathToFile),packageName);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Class> getAllInFolder(File folder, String basepackage){
        List<Class> classList = new ArrayList<>();
        if (!folder.exists()) return classList;
        for(File f: folder.listFiles()){
            if(f.getName().endsWith(".class")){
                String className = basepackage+'.'+f.getName().substring(0,f.getName().length()-6);
                try {
                    classList.add(Class.forName(className));
                } catch (ClassNotFoundException ignored) { }
            }else if (f.isDirectory()){
                classList.addAll(getAllInFolder(f,basepackage+'.'+f.getName()));
            }
        }
        return classList;
    }



}
