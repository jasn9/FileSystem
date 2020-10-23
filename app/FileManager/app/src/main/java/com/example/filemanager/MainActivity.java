package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        final ListView listView = findViewById(R.id.listView);
        final TextAdapter textAdapter1 = new TextAdapter();
        listView.setAdapter(textAdapter1);

        List<String> example = new ArrayList<>();
        for(int i=0;i<100;i++){
            example.add(String.valueOf(i));
        }

        textAdapter1.setData(example);
    }

    class TextAdapter extends BaseAdapter{

        private List<String> data = new ArrayList<>();

        public void setData(List<String> data){
            if(data!=null){
                this.data.clear();
                if(!data.isEmpty()){
                    this.data.addAll(data);
                }
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
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                convertView.setTag(new ViewHolder(convertView.findViewById(R.id.textItem)));
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            final String value = getItem(position);
            viewHolder.info.setText(value);
            return convertView;
        }

        class ViewHolder {
            TextView info;
            ViewHolder(TextView info){
                this.info = info;
            }
        }
    }

}