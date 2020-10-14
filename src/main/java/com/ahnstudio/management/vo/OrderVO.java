package com.ahnstudio.management.vo;

import com.ahnstudio.management.pojo.Shipping;
import com.ahnstudio.management.pojo.User;

import java.math.BigDecimal;
import java.util.List;

public class OrderVO {

    private String orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeDesc;

    private Integer postage;

    private Integer status;


    private String statusDesc;

    private String paymentTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;

    //订单的明细
    private List<OrderItemVO> orderItemVoList;

    private String imageHost;

    private Integer shippingId;

    private String receiverName;

    private ShippingVO shippingVo;

    private String comment;

    private User customer;

    private Shipping shipping;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<OrderItemVO> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVO> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public ShippingVO getShippingVo() {
        return shippingVo;
    }

    public void setShippingVo(ShippingVO shippingVo) {
        this.shippingVo = shippingVo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderNo='" + orderNo + '\'' +
                ", payment=" + payment +
                ", paymentType=" + paymentType +
                ", paymentTypeDesc='" + paymentTypeDesc + '\'' +
                ", postage=" + postage +
                ", status=" + status +
                ", statusDesc='" + statusDesc + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", orderItemVoList=" + orderItemVoList +
                ", imageHost='" + imageHost + '\'' +
                ", shippingId=" + shippingId +
                ", receiverName='" + receiverName + '\'' +
                ", shippingVo=" + shippingVo +
                ", comment='" + comment + '\'' +
                ", customer=" + customer +
                ", shipping=" + shipping +
                '}';
    }
}
