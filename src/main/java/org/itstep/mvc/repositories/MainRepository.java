package org.itstep.mvc.repositories;

import org.itstep.mvc.core.annotation.Component;

@Component
public class MainRepository implements MRepo {
    public String getName(){
        return "Hello DI";
    }
}
