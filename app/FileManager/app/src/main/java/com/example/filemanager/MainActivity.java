package com.example.filemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startApp(){
        final ListView listFiles = findViewById(R.id.listFiles);
        final TextView path = findViewById(R.id.path);

        final String rootPath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        final File dir = new File(rootPath);
        final File[] files = dir.listFiles();
        final List<ListItem> filesList = FileUtils.convertToListItems(files);

        path.setText(rootPath);
        Log.d("DEBUG: ", rootPath);
        listFiles.setAdapter(new ListAdapter(filesList));
    }

    private static final int REQUEST_PERMISSIONS = 1234;

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private boolean checkPermission(String[] permissions){
        for(String permission: permissions){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkPermission(PERMISSIONS)){
            startApp();
        }
        else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSIONS && grantResults.length>0 && checkPermission(PERMISSIONS)){
            onResume();
        }
        else{
            Toast.makeText(getApplicationContext(), "PERMISSIONS DENIED!!!", Toast.LENGTH_LONG).show();
        }
    }
}