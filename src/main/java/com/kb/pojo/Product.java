package com.kb.pojo;

public class Product {
    private String pName;
    private Float pPrice;
    private String pImg;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Float getpPrice() {
        return pPrice;
    }

    public void setpPrice(Float pPrice) {
        this.pPrice = pPrice;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public Product() {

    }

    public Product(String pName, Float pPrice, String pImg) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pImg = pImg;
    }

    @Override
    public String toString() {
        return "Product{" +
                "产品名称='" + pName + '\'' +
                ", 产品价格='" + pPrice + '\'' +
                ", 图片地址='" + pImg + '\'' +
                '}';
    }
}
