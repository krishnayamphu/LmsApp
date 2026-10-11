package com.example.lmsapp.controllers.Media;

import java.io.File;
import java.util.ArrayList;

public class MediaHelper {
    public static ArrayList<String> getMediaFiles(){
        ArrayList<String> files=new ArrayList<>();
        File root=new File("c:\\uploads");
        for(File file:root.listFiles()){
            files.add(file.getName());
        }
        return files;
    }

    public static void remove(String filename){
        String root="c:\\uploads/"+filename;
        File file=new File(root);
        if(file.exists()){
            file.delete();
        }
    }
}