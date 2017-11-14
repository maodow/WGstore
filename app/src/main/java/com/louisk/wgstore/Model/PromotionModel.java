package com.louisk.wgstore.Model;

import com.louisk.wgstore.api.CampusRepository;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PromotionModel {

  //添加折扣券
  public Observable<Long> addPromotToDB(String goodsType, double discountExtent, String discountDeadLine, String discountAddTime) {
    return CampusRepository.getInstance().addPromotToDB(goodsType, discountExtent, discountDeadLine, discountAddTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  //添加优惠券
  public Observable<Long> addCouponToDB(int totalMoney, int freeMoney, String couponDeadLine, String couponAddTime) {
    return CampusRepository.getInstance().addCouponToDB(totalMoney, freeMoney, couponDeadLine, couponAddTime)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
}
