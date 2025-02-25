package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.PropertiesConfig;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.FileVO;
import com.ahnstudio.management.pojo.Spec;
import com.ahnstudio.management.service.FileService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/08/24
 */
@Slf4j
@RestController
@RequestMapping("/api/file/")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @RequestMapping(value = "/single/upload", method = RequestMethod.POST)
    public ServerResponse file_upload(HttpSession session
                                      , HttpServletRequest request
            , @RequestParam(value = "file", required = false) MultipartFile file
            ) {
//        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
//        if (currentUser == null) {
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }

        // 0. 파일 사이즈 체크 -->  최대 20MB
        if (file.getSize() > 0 && file.getSize() <= (Const.UPLOAD_IMAGE_MAX_SIZE * 1024)) {
            // 1. 서버에 파일 저장, 외부접근가능한 URL반환
            String targetFileName = fileService.saveSingleImage(file);
            String targetFileFinal = propertiesConfig.getFileServerHttpPrefix() + targetFileName;
            log.info(">>>>>>" + targetFileFinal);

            return ServerResponse.createBySuccess(targetFileFinal);
        } else {
            return ServerResponse.createByErrorMessage("文件大小不能超过20MB");
        }
    }

    @RequestMapping(value = "/main/upload", method = RequestMethod.POST)
    public ServerResponse file_upload2(HttpSession session
            , HttpServletRequest request
            , @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // 0. 파일 사이즈 체크 -->  최대 20MB
        if (file.getSize() > 0 && file.getSize() <= (Const.UPLOAD_IMAGE_MAX_SIZE * 1024)) {
            Map<String, Object> resultMap = fileService.saveSingleImage2(file);
            log.info(">>>>>>" + resultMap);

            return ServerResponse.createBySuccess(resultMap);
        } else {
            return ServerResponse.createByErrorMessage("文件大小不能超过20MB");
        }
    }

    @RequestMapping(value = "/single/upload/url", method = RequestMethod.POST)
    public Map<String, Object> file_upload_return_url(HttpSession session
            , HttpServletRequest request
            , @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles
    ) {
//        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
//        if (currentUser == null) {
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }
        Map<String, Object> result = new HashMap<>();
        List imgList = new ArrayList();
        log.info(">>>>>>>>>>" + String.valueOf(multipartFiles.length));
        // 0. 파일 사이즈 체크 -->  최대 20MB
        for (MultipartFile file : multipartFiles) {
            if (file.getSize() > 0 && file.getSize() <= (Const.UPLOAD_IMAGE_MAX_SIZE * 1024)) {
                String targetFileName = fileService.saveSingleImage(file);
                String targetFileFinalName = propertiesConfig.getFileServerHttpPrefix() + targetFileName;

                imgList.add(targetFileFinalName);
                result.put("errno", 0);
                result.put("data", imgList);
            } else {
                result.put("errno", 99);
                result.put("data", imgList);
            }
        }
        return result;
    }

    @RequestMapping(value = "main/save", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveFileVO(HttpServletRequest request, String arrFileVO) {
        List<FileVO> fileVOList = new ArrayList<>();

        if (JSONArray.parseArray(arrFileVO, FileVO.class).size() > 0) {
            List<FileVO> fileList = JSONArray.parseArray(arrFileVO, FileVO.class);
            for (FileVO fileVO : fileList) {
                fileVO = fileService.saveFileVO(fileVO);
                fileVOList.add(fileVO);
            }
        }

        return ServerResponse.createBySuccess(fileVOList);
    }

    @RequestMapping(value = "main/list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse mainFileList() {
        return fileService.mainFileList();
    }


}
