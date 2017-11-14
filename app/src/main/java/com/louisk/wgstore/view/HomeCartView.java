package com.louisk.wgstore.view;

import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;

import java.util.List;

public interface HomeCartView extends IView {
   void successGetgoods(List<GoodsBean> goodsBeanList);
   void successGetDiscountBeans(List<DiscountBean> discountBeanList);
   void successGetCouponBeans(List<CouponBean> couponBeanList);
   void showError(String message);
}
