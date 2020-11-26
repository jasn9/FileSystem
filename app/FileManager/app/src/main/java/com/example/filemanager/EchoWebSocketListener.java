package com.example.filemanager;

import android.util.Log;

import com.example.filemanager.Clients.GetDirectoriesRequest;
import com.example.filemanager.Clients.GetDirectoriesResponse;
import com.example.filemanager.Clients.MessageBody;
import com.example.filemanager.POJOS.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUE = 1000;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onOpen(WebSocket webSocket, Response response){
        MessageBody res = new MessageBody();
        res.setTag(Constants.FIRST_MESSAGE);
        res.setAndroid(false);
        res.setConnection_code(Session.getSessionObject().getCode());
        res.setValue("Hello....UnderCover123");
        String stringRes = null;
        try {
            stringRes = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        webSocket.send(stringRes);
        //webSocket.close(NORMAL_CLOSURE_STATUE, "Bye");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text){
        Log.d("Socket Res: ", text);
        try {
            MessageBody messageBody = objectMapper.readValue(text, MessageBody.class);
            if(messageBody.getTag().equals(Constants.GET_DIRECTORY)) {
                GetDirectoriesRequest getDirectoriesRequest = objectMapper.readValue(messageBody.getValue(), GetDirectoriesRequest.class);
                GetDirectoriesResponse getDirectoriesResponse = FileUtils.getFiles(getDirectoriesRequest);
                getDirectoriesResponse.setCode(Session.getSessionObject().getCode());
                String value = objectMapper.writeValueAsString(getDirectoriesResponse);
                MessageBody res = new MessageBody();
                res.setAndroid(false);
                res.setConnection_code(Session.getSessionObject().getCode());
                res.setTag(Constants.GET_DIRECTORY);
                res.setValue(value);
                String stringRes = objectMapper.writeValueAsString(res);
                Log.d("Socket Res:", stringRes);
                webSocket.send(stringRes);
            }
            else{
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes){
        Log.d("Socket Res: ", bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason){
        webSocket.close(NORMAL_CLOSURE_STATUE, null);
        Session.getSessionObject().setCode(null);
        Session.getSessionObject().setStarted(false);
        Log.d("Socket Close: ", "Status: "+code + " Reason: "+ reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response){
        Session.getSessionObject().setCode(null);
        Session.getSessionObject().setStarted(false);
        if(t!=null && t.getMessage()!=null) {
            Log.d("Socket Failure:  ", t.getMessage());
        }
        else{
            Log.d("Socket Failure", "Exception is NULL");
        }
        webSocket.close(NORMAL_CLOSURE_STATUE, "Failure");
    }
}
