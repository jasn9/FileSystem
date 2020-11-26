package com.example.filemanager.POJOS;

public class Directory {
    private String path;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }
}
