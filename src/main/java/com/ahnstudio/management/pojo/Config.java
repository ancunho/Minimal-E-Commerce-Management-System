package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Config {
    private Integer id;
    private String cnfCode;
    private String cnfValue;
    private String cnfValueChange;
    private String cnfNote;
    private Date createtime;
    private Date updatetime;

}