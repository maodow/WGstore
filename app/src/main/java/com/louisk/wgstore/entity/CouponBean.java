package com.louisk.wgstore.entity;

import java.io.Serializable;

/**
 * 优惠券Bean
 */

public class CouponBean implements Serializable{
    private int totalMoney;//商品总额
    private int freeMoney;//减免额度
    private String couponBeanDeadLine;//截止日期
    private String couponAddTime;

    public CouponBean(int totalMoney, int freeMoney, String couponBeanDeadLine) {
        this.totalMoney = totalMoney;
        this.freeMoney = freeMoney;
        this.couponBeanDeadLine = couponBeanDeadLine;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getFreeMoney() {
        return freeMoney;
    }

    public String getCouponBeanDeadLine() {
        return couponBeanDeadLine;
    }

    public String getCouponAddTime() {
        return couponAddTime;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setFreeMoney(int freeMoney) {
        this.freeMoney = freeMoney;
    }

    public void setCouponBeanDeadLine(String couponBeanDeadLine) {
        this.couponBeanDeadLine = couponBeanDeadLine;
    }

    public void setCouponAddTime(String couponAddTime) {
        this.couponAddTime = couponAddTime;
    }
}
