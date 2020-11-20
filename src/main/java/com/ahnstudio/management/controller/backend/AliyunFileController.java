package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.PropertiesConfig;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.service.FileService;
import com.ahnstudio.management.util.OSSUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Cunho
 * @date : 2020/11/24
 */
@Slf4j
@RestController
@RequestMapping("/aliyun/")
public class AliyunFileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    private String Username = "ahnstudio@1378310925568455.onaliyun.com";
    private String EndPoint = "https://oss-cn-beijing.aliyuncs.com";
    private String AccessKeyId = "LTAI4GJNY3y28mxUuqzBnP84";
    private String AccessKeySecret = "4V0kb38ibOH2FVJGEPQQHl6Dzamhr2";
    private String BucketName = "ahnstudiominiapp910";

    @RequestMapping(value = "/simpleUpload", method = RequestMethod.POST)
    public ServerResponse file_upload(HttpSession session
            , HttpServletRequest request
            , @RequestParam(value = "file", required = false) MultipartFile file
    ) {

        OSS ossClient = OSSUtil.getOSSClient();
        ListObjectsV2Result result = ossClient.listObjectsV2(this.BucketName);
        List<OSSObjectSummary> ossObjectSummaries = result.getObjectSummaries();

        List resultList = new ArrayList();
        for (OSSObjectSummary obj : ossObjectSummaries) {
            System.out.println("\t" + obj.getKey());
            resultList.add(obj);
        }

        ossClient.shutdown();


        // 0. 파일 사이즈 체크 -->  최대 20MB
//        if (file.getSize() > 0) {
//            // 1. 서버에 파일 저장, 외부접근가능한 URL반환
//            String targetFileName = fileService.saveSingleImage(file);
////            String targetFileFinal = propertiesConfig.getFileServerHttpPrefix() + targetFileName;
//            log.info(">>>>>>" + targetFileName);
//
//            return ServerResponse.createBySuccess(targetFileName);
//        } else {
//            return ServerResponse.createByErrorMessage("ERROR By Cunho");
//        }

        return ServerResponse.createBySuccess(resultList);
    }


}
