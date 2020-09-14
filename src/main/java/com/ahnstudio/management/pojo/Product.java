package com.ahnstudio.management.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImage() {
        return subImage;
    }

    public void setSubImage(String subImage) {
        this.subImage = subImage;
    }

    public String getSubImage2() {
        return subImage2;
    }

    public void setSubImage2(String subImage2) {
        this.subImage2 = subImage2;
    }

    public String getSubImage3() {
        return subImage3;
    }

    public void setSubImage3(String subImage3) {
        this.subImage3 = subImage3;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
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

    public String toJSONString() {
        return "{" +
                "id:" + id +
                ", categoryId:" + categoryId +
                ", name:'" + name + '\'' +
                ", subtitle:'" + subtitle + '\'' +
                ", mainImage:'" + mainImage + '\'' +
                ", subImage:'" + subImage + '\'' +
                ", attribute:'" + attribute + '\'' +
                ", detail:'" + detail + '\'' +
                ", price:" + price +
                ", vipPrice:" + vipPrice +
                ", spec:'" + spec + '\'' +
                ", stock:" + stock +
                ", status:" + status +
                ", country:'" + country + '\'' +
                ", city:'" + city + '\'' +
                ", variety:'" + variety + '\'' +
                ", treatment:'" + treatment + '\'' +
                ", flavor:'" + flavor + '\'' +
                ", param1:'" + param1 + '\'' +
                ", param2:'" + param2 + '\'' +
                ", param3:'" + param3 + '\'' +
                ", param4:'" + param4 + '\'' +
                ", param5:'" + param5 + '\'' +
                ", createtime:" + createtime +
                ", updatetime:" + updatetime +
                '}';
    }
}