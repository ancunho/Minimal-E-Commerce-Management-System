package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class Spec {
    private Integer id;
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private BigDecimal vipPrice;
    private String weight;
    private String stock;
    private BigDecimal deliveryPrice;
    private String deliveryType;
    private Integer status;
    private String area;
    private String country;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private Date createtime;
    private Date updatetime;

}