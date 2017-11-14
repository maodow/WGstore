package com.louisk.wgstore.listener;

import com.louisk.wgstore.entity.CouponBean;

import java.util.List;

/**
 * Created by admin on 2017/11/4.
 */

public interface RefreshCouponListListener {
    void refreshDiscountList(List<CouponBean> couponBeans);
}
