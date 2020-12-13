package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Category {
    private Integer id;
    private Integer parentId;
    private String name;
    private Boolean status;
    private Integer sortOrder;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private Date createtime;
    private Date updatetime;

}