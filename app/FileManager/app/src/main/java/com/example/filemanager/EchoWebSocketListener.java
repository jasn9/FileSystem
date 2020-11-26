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
        res.setAndroid(true);
        res.setConnection_code(Session.getSessionObject().getCode());
        res.setValue("Hello....UnderCover123");
        String stringRes = null;
        try {
            stringRes = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        webSocket.send(stringRes);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text){
        try {
            MessageBody messageBody = objectMapper.readValue(text, MessageBody.class);
            if(messageBody.getTag().equals(Constants.GET_DIRECTORY)) {
                Log.d("Socket Message: ", text);
                GetDirectoriesRequest getDirectoriesRequest = objectMapper.convertValue(messageBody.getValue(), GetDirectoriesRequest.class);
                GetDirectoriesResponse getDirectoriesResponse = FileUtils.getFiles(getDirectoriesRequest);
                MessageBody res = new MessageBody();
                res.setAndroid(true);
                res.setConnection_code(Session.getSessionObject().getCode());
                res.setTag(Constants.GET_DIRECTORY);
                res.setValue(getDirectoriesResponse);
                String stringRes = objectMapper.writeValueAsString(res);
                Log.d("Socket Res:", stringRes);
                webSocket.send(stringRes);
            }
            else{
                if(messageBody.getTag().equals(Constants.PING)){

                }
                else{
                    Log.d("Socket Message: ", text);
                }
            }
        } catch (JsonProcessingException e) {
            Log.d("Socket Message: ", text);
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes){
        Log.d("Socket Message: ", bytes.hex());
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
