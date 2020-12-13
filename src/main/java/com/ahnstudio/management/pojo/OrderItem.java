package com.ahnstudio.management.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class OrderItem {
    private Integer id;
    private Integer userId;
    private String orderId;
    private Integer productId;
    private Integer specId;
    private String productName;
    private String productImage;
    private BigDecimal currentUnitPrice;
    private Integer qty;
    private BigDecimal totalPrice;
    private Date createtime;
    private Date updatetime;

}
