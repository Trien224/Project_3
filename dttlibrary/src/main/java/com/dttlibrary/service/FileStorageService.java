package com.dttlibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    // KHỚP với FileUploadConfig
    private final String uploadDir = "E:/dttlibrary/";

    public String store(MultipartFile file) {

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Upload file failed", e);
        }
    }
}
