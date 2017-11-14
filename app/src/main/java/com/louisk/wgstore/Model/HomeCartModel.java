package com.louisk.wgstore.Model;

import com.louisk.wgstore.api.CampusRepository;
import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeCartModel {

  public Observable<List<GoodsBean>> getGoods() {
    return CampusRepository.getInstance().getGoodsBean()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  //获取折扣券
  public Observable<List<DiscountBean>> getDiscountBeanList() {
    return CampusRepository.getInstance().getDiscountBeanList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  //获取优惠券
  public Observable<List<CouponBean>> getCouponBeanList() {
    return CampusRepository.getInstance().getCouponBeanList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
}
