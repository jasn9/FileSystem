package com.example.filemanager.POJOS;

public class Session {
    private String code;
    private boolean started;
    private static Session sessionObject;
    private Session(){
    }

    public static Session getSessionObject(){
        if(sessionObject==null){
            sessionObject = new Session();
        }
        return sessionObject;
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
}
