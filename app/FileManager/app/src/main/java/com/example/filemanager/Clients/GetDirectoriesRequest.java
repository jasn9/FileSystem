package com.example.filemanager.Clients;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class GetDirectoriesRequest {
    @NonNull
    private String path;

    @NonNull
    private String code;

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
