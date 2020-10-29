package com.example.filemanager;

import java.io.File;

public class ListItem {
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getText() {
        if(file!=null) {
            return file.getName();
        }
        else{
            return null;
        }
    }
}
