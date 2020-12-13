package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.FileVO;
import org.apache.tomcat.jni.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    String saveSingleImage(MultipartFile multipartFile);
    Map<String, Object> saveSingleImage2(MultipartFile multipartFile);
    FileVO saveFileVO(FileVO fileVO);
    ServerResponse mainFileList();
}




