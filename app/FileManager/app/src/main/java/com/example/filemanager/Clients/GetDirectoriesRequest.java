package com.example.filemanager.Clients;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class GetDirectoriesRequest {
    @NonNull
    private String path;

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
