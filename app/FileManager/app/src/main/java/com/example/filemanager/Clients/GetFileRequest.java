package com.example.filemanager.Clients;

import androidx.annotation.NonNull;

public class GetFileRequest {
    @NonNull
    private String path;

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
