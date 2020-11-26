package com.example.filemanager;

import com.example.filemanager.Clients.GetDirectoriesRequest;
import com.example.filemanager.Clients.GetDirectoriesResponse;
import com.example.filemanager.POJOS.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    protected static List<ListItem> convertToListItems(File[] files){
        List<ListItem> listFiles = Collections.emptyList();
        if(files!=null){
            listFiles = new ArrayList<>();
            for(File file: files){
                listFiles.add(convertToListItem(file));
            }
        }
        return listFiles;
    }
    protected static ListItem convertToListItem(File file){
        ListItem listItem = new ListItem();
        listItem.setFile(file);
        return listItem;
    }

    protected static GetDirectoriesResponse getFiles(GetDirectoriesRequest getDirectoriesRequest){
        File dir = new File(getDirectoriesRequest.getPath());
        File[] files = dir.listFiles();
        GetDirectoriesResponse getDirectoriesResponse = convertFiles(files);
        return getDirectoriesResponse;
    }

    protected static GetDirectoriesResponse convertFiles(File[] files){
        List<Item> items = new ArrayList<>();
        if(files!=null){
            for(File file: files){
                Item item = convertFile(file);
                items.add(item);
            }
        }
        GetDirectoriesResponse getDirectoriesResponse = new GetDirectoriesResponse();
        getDirectoriesResponse.setItems(items);
        return getDirectoriesResponse;
    }

    protected static Item convertFile(File file){
        Item item = new Item();
        item.setFilePath(file.getAbsolutePath());
        if(file.isDirectory()) {
            item.setType("Folder");
        }
        else{
            item.setType("File");
        }
        return item;
    }
}
