package com.louisk.wgstore.entity;

import java.io.Serializable;

/**
 * 折扣Bean
 */

public class DiscountBean implements Serializable{
    private String discountType;//打折种类: 电子类/食品类/酒类等
    private double discountExtent;//打折幅度
    private String discountDeadLine;//截止日期
    private String discountAddTime;//添加时间

    public DiscountBean(String discountType, double discountExtent, String discountDeadLine) {
        this.discountType = discountType;
        this.discountExtent = discountExtent;
        this.discountDeadLine = discountDeadLine;
    }

    public String getDiscountType() {
        return discountType;
    }

    public double getDiscountExtent() {
        return discountExtent;
    }

    public String getDiscountDeadLine() {
        return discountDeadLine;
    }

    public String getDiscountAddTime() {
        return discountAddTime;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setDiscountExtent(double discountExtent) {
        this.discountExtent = discountExtent;
    }

    public void setDiscountDeadLine(String discountDeadLine) {
        this.discountDeadLine = discountDeadLine;
    }

    public void setDiscountAddTime(String discountAddTime) {
        this.discountAddTime = discountAddTime;
    }
}
