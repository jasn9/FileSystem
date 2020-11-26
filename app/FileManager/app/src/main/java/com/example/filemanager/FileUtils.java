package com.example.filemanager;

import com.example.filemanager.Clients.GetDirectoriesRequest;
import com.example.filemanager.Clients.GetDirectoriesResponse;
import com.example.filemanager.POJOS.Directory;

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
        List<Directory> directories = new ArrayList<>();
        if(files!=null){
            for(File file: files){
                Directory directory = convertFile(file);
                directories.add(directory);
            }
        }
        GetDirectoriesResponse getDirectoriesResponse = new GetDirectoriesResponse();
        getDirectoriesResponse.setDirectories(directories);
        return getDirectoriesResponse;
    }

    protected static Directory convertFile(File file){
        Directory directory = new Directory();
        directory.setPath(file.getAbsolutePath());
        if(file.isDirectory()) {
            directory.setType("Folder");
        }
        else{
            directory.setType("File");
        }
        return directory;
    }
}
