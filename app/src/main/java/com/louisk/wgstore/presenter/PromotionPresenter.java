package com.louisk.wgstore.presenter;

import android.content.Context;

import com.louisk.wgstore.Model.PromotionModel;
import com.louisk.wgstore.view.PromotionView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PromotionPresenter extends Presenter {
  private PromotionView mView;
  private PromotionModel mSource;
  private Context mContext;


  public PromotionPresenter(PromotionView mView, Context mContext) {
    this.mView = mView;
    this.mContext = mContext;
    this.mSource = new PromotionModel();
  }

  //添加折扣券
  public void addPromotToDB(String goodsType, double discountExtent, String discountDeadLine, String discountAddTime) {
//    if (!NetUtil.isNetworkAvailable(mContext)) {
//      mView.onNoNetwork();
//      return;
//    }

    Subscription subscription = mSource.addPromotToDB(goodsType, discountExtent, discountDeadLine, discountAddTime)
        .doOnSubscribe(() -> mView.showLoading())
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Long>() {
          @Override
          public void onCompleted() {
            mView.hideLoading();
          }

          @Override
          public void onError(Throwable e) {
            mView.showError(e.getMessage());
          }

          @Override
          public void onNext(Long resultCode) {
            mView.hideLoading();
            mView.addPromotCallback(resultCode);
          }
        });
    addSubscription(subscription);
  }


  //添加优惠券
  public void addCouponToDB(int totalMoney, int freeMoney, String couponDeadLine, String couponAddTime) {
//        if (!NetUtil.isNetworkAvailable(mContext)) {
//            mView.onNoNetwork();
//            return;
//        }
        Subscription subscription = mSource.addCouponToDB(totalMoney, freeMoney, couponDeadLine, couponAddTime)
                .doOnSubscribe(() -> mView.showLoading())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Long resultCode) {
                        mView.hideLoading();
                        mView.addPromotCallback(resultCode);
                    }
                });
        addSubscription(subscription);
    }
}
