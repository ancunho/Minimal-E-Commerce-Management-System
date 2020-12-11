package com.ahnstudio.management.pojo;

import com.ahnstudio.management.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Order extends BaseVO {
    private Integer id;
    private String orderId;
    private Integer userId;
    private Integer shippingId;
    private BigDecimal payment;
    private Integer paymentType;
    private Integer postage;
    private Integer status;
    private Date paymentTime;
    private Date sendTime;
    private Date endTime;
    private Date closeTime;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;
    private String createtime;
    private String updatetime;

    private List<OrderItem> lstOrderItem;
    private User user;

    private String STATUS_NAME;

}