package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        final Button b1 = findViewById(R.id.b1);
        final Button b2 = findViewById(R.id.b2);
        final Button b3 = findViewById(R.id.b3);
        final Button b4 = findViewById(R.id.b4);
        final Button b5 = findViewById(R.id.b5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setText("pressed");
            }
        });
    }

    class TextAdapter extends BaseAdapter {

        private List<String> data = new ArrayList<>();
        private boolean[] selections;

        public void setData(List<String> data) {
            if (data != null) {
                this.data = data;
                notifyDataSetChanged();
            }
        }

        public void setSelections(boolean[] selections){
            if(selections!=null){
                this.selections = selections;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public String getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                convertView.setTag(new ViewHolder(convertView.findViewById(R.id.textItem)));
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            final String value = getItem(position);
            viewHolder.info.setText(value);
            if(selections!=null){
                if(selections[position]){
                    viewHolder.info.setBackgroundColor(Color.LTGRAY);
                }
                else{
                    viewHolder.info.setBackgroundColor(Color.WHITE);
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView info;

            ViewHolder(TextView info) {
                this.info = info;
            }
        }
    }

    private static final int REQUEST_PERMISSIONS = 1234;

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_COUNT = 2;

    @SuppressLint("NewApi")
    private boolean arePermissionsDenied() {
        for (String permission : PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    protected boolean isFileManagerInitiated = false;

    // to check if we still have permission, if user disabled permission later
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        if(!isFileManagerInitiated) {

            final String rootPath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            final String rootFolderName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getName();
            final File dir = new File(rootPath);
            final File[] files = dir.listFiles();
            final TextView pathOutput = findViewById(R.id.pathOutput);
            pathOutput.setText(rootFolderName);

            final ListView listView = findViewById(R.id.listView);
            final TextAdapter textAdapter1 = new TextAdapter();
            listView.setAdapter(textAdapter1);

            List<String> fileNames = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
                boolean[] selections = new boolean[files.length];
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        selections[position] = !selections[position];
                        textAdapter1.setSelections(selections);
                        return false;
                    }
                });
            }

            textAdapter1.setData(fileNames);
            isFileManagerInitiated = true;
        }else{

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if (arePermissionsDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            } else {
                onResume();
            }
        }
    }
}