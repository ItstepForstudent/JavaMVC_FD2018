package org.itstep.mvc.util;

import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.itstep.mvc.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class JsonFileUtil {
    private final String FILE_URL;
    public String getJsonString(){
        File f = new File(FILE_URL);
        if(!f.exists()) return "[]";
        try{
            Scanner sc = new Scanner(f);
            StringBuilder builder = new StringBuilder();
            while (sc.hasNext()){
                builder.append(sc.nextLine());
            }

            return builder.toString();
        }catch (Exception e) {
            return "[]";
        }
    }

    public void saveJsonString(String json){
        File f = new File(FILE_URL);
        if (testExist(f)) return;

        try(FileWriter writer = new FileWriter(f)){
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean testExist(File f) {
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }
}
