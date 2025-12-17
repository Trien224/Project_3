package com.dttlibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "D:/dttlibrary/uploads/";

    public String storeFile(MultipartFile file) {

        if (file.isEmpty()) return null;

        String original = file.getOriginalFilename();
        String ext = original.substring(original.lastIndexOf("."));
        String fileName = UUID.randomUUID() + ext;

        try {
            file.transferTo(new File(UPLOAD_DIR + fileName));
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
    }
}
