package com.example.filemanager.Clients;

import androidx.annotation.NonNull;

public class MessageBody {
    @NonNull
    private String tag;
    @NonNull
    private boolean android;
    @NonNull
    private String connection_code;
    private String value;

    public String getTag() {
        return tag;
    }

    public boolean isAndroid() {
        return android;
    }

    public String getConnection_code() {
        return connection_code;
    }

    public String getValue() {
        return value;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setAndroid(boolean android) {
        this.android = android;
    }

    public void setConnection_code(String connection_code) {
        this.connection_code = connection_code;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
