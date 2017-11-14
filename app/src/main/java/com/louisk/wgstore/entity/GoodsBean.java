package com.louisk.wgstore.entity;

/**
 * des:商品详情
 *
 */
public class GoodsBean {
    private String goodsType;
    private String goodsName;
    private double goodsPrice;
    private int goodsNum;

    public GoodsBean(String goodsName, double goodsPrice){
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
    }

    public GoodsBean(String goodsType, String goodsName, double goodsPrice){
        this.goodsType = goodsType;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
    }

    public GoodsBean(String goodsName, double goodsPrice, int goodsNum){
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
    }

    public GoodsBean(String goodsType, String goodsName, double goodsPrice, int goodsNum){
        this.goodsType = goodsType;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
}
