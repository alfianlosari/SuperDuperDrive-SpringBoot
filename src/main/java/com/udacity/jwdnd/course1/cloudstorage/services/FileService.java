package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Resource loadFileAsResource(File file) throws Exception {
        Resource resource = new ByteArrayResource(file.getFiledata());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("Could not read file: " + file.getFilename());
        }
    }

    public File getFile(Integer fileid) {
        return fileMapper.getFile(fileid);
    }

    public List<File> getFiles(Integer userid) {
        return fileMapper.getFiles(userid);
    }

    public int saveFile(File file) {
        return fileMapper.insert(file);
    }

    public int deleteFile(Integer fileid) {
        return fileMapper.delete(fileid);
    }
}
