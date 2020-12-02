package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filemanager.Clients.GetCodeResponse;
import com.example.filemanager.POJOS.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class Web extends AppCompatActivity {

    private static final String URL = "https://file-system-backend.herokuapp.com/getCode";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private void createSocket(String url){
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        EchoWebSocketListener webSocketListener = new EchoWebSocketListener();
        Session.getSessionObject().setWebSocket(Session.getSessionObject().getOkHttpClient().newWebSocket(request, webSocketListener));
        Session.getSessionObject().setStarted(true);
    }

    private void sendMessage(String message){
        if(!Session.getSessionObject().isStarted()){
            createSocket(URL);
        }
        Session.getSessionObject().getWebSocket().send(message);
    }

    private void getCodeResponseProcess(JSONObject response){
        try {
            Log.d("GetCode Res: ",  response.toString());
            GetCodeResponse getCodeResponse = objectMapper.readValue(response.toString(), GetCodeResponse.class);
            Session.getSessionObject().setCode(getCodeResponse.getCode());
            TextView textView = findViewById(R.id.code);
            textView.setText("Code: "+Session.getSessionObject().getCode());
            createSocket(URL);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void setUpWeb(){
         if(!Session.getSessionObject().isStarted()) {
             RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
             JSONObject jsonObject = new JSONObject();
             try {
                 jsonObject.put("id", "1234");
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                     (Request.Method.POST, URL, jsonObject,
                             this::getCodeResponseProcess,
                             error -> {
                         Log.d("GetCode Error: ", error.getMessage());
                     });
             queue.add(jsonObjectRequest);
         }
         else{
             TextView textView = findViewById(R.id.code);
             textView.setText("Code: "+Session.getSessionObject().getCode());
         }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "in onResume");
        setUpWeb();
    }
}