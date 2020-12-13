package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Shipping {
    private Integer id;
    private Integer userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverMobile;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private String receiverZip;
    private String isDefault;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private Date createtime;
    private Date updatetime;
}