package com.cha.carrotApi.service;

import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.MultipartFile;

@Primary
public interface FileService {
    void upload(MultipartFile file, String filename);
    void delete(String filename);
}
