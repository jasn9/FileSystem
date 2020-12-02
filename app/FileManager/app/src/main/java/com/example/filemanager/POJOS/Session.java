package com.example.filemanager.POJOS;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class Session {
    private String code;
    private boolean started;
    private WebSocket webSocket;
    private OkHttpClient okHttpClient;
    private static Session sessionObject;
    private Session(){
    }

    public static Session getSessionObject(){
        if(sessionObject==null){
            sessionObject = new Session();
            sessionObject.setOkHttpClient(new OkHttpClient());
        }
        return sessionObject;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public Session(boolean isStarted){
        this.started = started;
    }

    public String getCode() {
        return code;
    }

    public boolean isStarted() {
        return started;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
