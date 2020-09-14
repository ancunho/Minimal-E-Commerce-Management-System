package com.ahnstudio.management.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItem {

    private Integer id;

    private Integer userId;

    private Long orderId;

    private Integer productId;

    private Integer specId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer qty;

    private BigDecimal totalPrice;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", specId=" + specId +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", currentUnitPrice=" + currentUnitPrice +
                ", qty=" + qty +
                ", totalPrice=" + totalPrice +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
