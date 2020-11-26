package com.example.filemanager.POJOS;

public class Item {
    private String filePath;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getType() {
        return type;
    }
}
