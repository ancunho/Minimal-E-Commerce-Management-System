package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.PropertiesConfig;
import com.ahnstudio.management.service.FileService;
import com.ahnstudio.management.util.DateUtil;
import com.ahnstudio.management.util.ValueUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public String saveSingleImage(MultipartFile multipartFile) {
        //기존파일명
        String fileName = multipartFile.getOriginalFilename();
        //파일확장자
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //새로운 파일명  UUID.randomUUID().toString()
        String uploadFileName = DateUtil.getTime() + "_" + ValueUtil.generateUid(10) + "." + fileExtensionName;
        //文件路径 + "/"
        String remotePath = File.separator +  DateUtil.getDays() + File.separator;
        //文件保存路径   /home
        String path = propertiesConfig.getFilePath() + remotePath;

        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{},文件格式：{}",fileName,path,uploadFileName, fileExtensionName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        FileInputStream fileInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {

            fileInputStream = (FileInputStream) multipartFile.getInputStream();
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + File.separator + uploadFileName));

            byte[] bs = new byte[1024];
            int len;

            while ((len = fileInputStream.read(bs)) != -1) {
                bufferedOutputStream.write(bs, 0, len);
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String finalFileName = propertiesConfig.getFileServerHttpPrefix() + remotePath + uploadFileName;
        String finalFileName = remotePath + uploadFileName;
        return finalFileName;
    }


}
