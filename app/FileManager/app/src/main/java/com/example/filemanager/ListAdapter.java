package com.example.filemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<ListItem> list;

    ListAdapter(){ }

    ListAdapter(List<ListItem> list){
        setList(list);
    }

    public void setList(List<ListItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public ListItem getItem(int position) {
        if(list!=null){
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_1, parent, false);
        }
        ListItem item = getItem(position);
        if(item!=null) {
            ((TextView) convertView.findViewById(R.id.list_item)).setText(item.getText());
        }
        return convertView;
    }
}
