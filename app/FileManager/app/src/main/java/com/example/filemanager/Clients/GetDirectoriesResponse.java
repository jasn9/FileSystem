package com.example.filemanager.Clients;

import androidx.annotation.NonNull;

import com.example.filemanager.POJOS.Directory;

import java.util.List;

public class GetDirectoriesResponse {
    @NonNull
    private List<Directory> directories;

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public List<Directory> getDirectories() {
        return directories;
    }
}
