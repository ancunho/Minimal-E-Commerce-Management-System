package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FileVO {

    private Integer fileSeq;
    private String fileName;
    private String filePath;
    private String fileFullPath;
    private String useType;
    private String useYn;
    private String sort;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private Date createtime;
    private Date updatetime;

}