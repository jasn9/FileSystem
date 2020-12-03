package com.example.filemanager;

import com.example.filemanager.Clients.GetDirectoriesRequest;
import com.example.filemanager.Clients.GetDirectoriesResponse;
import com.example.filemanager.Clients.GetFileRequest;
import com.example.filemanager.Clients.GetFileResponse;
import com.example.filemanager.POJOS.Directory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    protected static GetFileResponse getFiles(GetFileRequest getFileRequest) throws IOException {
        File dir = new File(getFileRequest.getPath());
        if(dir!=null){
            if(dir.isFile()){
                byte[] bytes = new byte[(int) dir.length()];
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(dir));
                bufferedInputStream.read(bytes, 0, bytes.length);
                GetFileResponse getFileResponse = new GetFileResponse();
                getFileResponse.setData(bytes);
                return getFileResponse;
            }
        }
        return null;
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
