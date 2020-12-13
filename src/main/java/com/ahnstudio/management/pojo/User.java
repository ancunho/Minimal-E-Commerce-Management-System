package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String openid;
    private String unionid;
    private String username;
    private String password;
    private String roleNo;
    private String role;
    private String status;
    private String realname;
    private String company;
    private String companyType;
    private String deviceSerial;
    private String deviceModel;
    private String deviceColor;
    private String phone;
    private String email;
    private String sex;
    private String birthday;
    private String wechat;
    private String qq;
    private String province;
    private String city;
    private String area;
    private String address;
    private String question;
    private String answer;
    private String imagePhoto;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private Date createtime;
    private Date updatetime;
}