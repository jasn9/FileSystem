package com.example.filemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final ListAdapter listAdapter = new ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void updateFilesListView(File dir){
        final TextView currentDir = findViewById(R.id.currentDir);
        final TextView parentDir = findViewById(R.id.parentDir);
        final TextView pathBreak = findViewById(R.id.pathBreak);

        final File[] files = dir.listFiles();
        final List<ListItem> filesList = FileUtils.convertToListItems(files);
        listAdapter.setList(filesList);

        if(dir.getParentFile()!=null) {
            parentDir.setText(dir.getParentFile().getName());
            parentDir.setTag(dir.getParentFile());
            parentDir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFilesListView((File) parentDir.getTag());
                }
            });

            currentDir.setText(dir.getName());
            pathBreak.setVisibility(View.VISIBLE);
        }
        else {
            currentDir.setText(dir.getName());
            pathBreak.setVisibility(View.GONE);
        }
    }

    private void startApp(){
        final ListView listFiles = findViewById(R.id.listFiles);
        listFiles.setAdapter(listAdapter);

        updateFilesListView(Environment.getExternalStorageDirectory());

        listFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = listAdapter.getItem(position).getFile();
                if(file.isDirectory()){
                    updateFilesListView(file);
                }
            }
        });
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