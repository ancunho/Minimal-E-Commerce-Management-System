package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Product {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImage;
    private String subImage2;
    private String subImage3;
    private String attribute;
    private String detail;
    private BigDecimal price;
    private BigDecimal vipPrice;
    private String spec;
    private List<Spec> specList;
    private Integer stock;
    private Integer status;
    private String country;
    private String city;
    private String variety;
    private String treatment;
    private String flavor;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private Date createtime;
    private Date updatetime;
}