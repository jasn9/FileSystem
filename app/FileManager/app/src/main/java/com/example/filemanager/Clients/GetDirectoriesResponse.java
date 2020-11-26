package com.example.filemanager.Clients;

import androidx.annotation.NonNull;

import com.example.filemanager.POJOS.Item;

import java.util.List;

public class GetDirectoriesResponse {
    @NonNull
    private List<Item> items;

    @NonNull
    private String code;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getCode() {
        return code;
    }
}
