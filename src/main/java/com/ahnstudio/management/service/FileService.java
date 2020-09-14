package com.ahnstudio.management.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveSingleImage(MultipartFile multipartFile);
}
