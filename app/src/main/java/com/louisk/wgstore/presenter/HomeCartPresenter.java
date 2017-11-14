package com.louisk.wgstore.presenter;

import android.content.Context;

import com.louisk.wgstore.Model.HomeCartModel;
import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.view.HomeCartView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeCartPresenter extends Presenter {
  private HomeCartView mView;
  private HomeCartModel mSource;
  private Context mContext;


  public HomeCartPresenter(Context mContext, HomeCartView mView) {
    this.mView = mView;
    this.mContext = mContext;
    this.mSource = new HomeCartModel();
  }

  public void getGoods() {
      //目前无网络环境下请求，暂时注掉
//    if (!NetUtil.isNetworkAvailable(mContext)) {
//      mView.onNoNetwork();
//      return;
//    }

    Subscription subscription = mSource.getGoods()
        .doOnSubscribe(() -> mView.showLoading())
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<GoodsBean>>() {
          @Override
          public void onCompleted() {
            mView.hideLoading();
          }

          @Override
          public void onError(Throwable e) {
            mView.showError(e.getMessage());
          }

          @Override
          public void onNext(List<GoodsBean> goodsBeanList) {
            mView.hideLoading();
            mView.successGetgoods(goodsBeanList);
          }
        });

    addSubscription(subscription);

  }

    //获取折扣券
    public void getDiscountBeanList(){
        //目前无网络环境下请求，暂时注掉
//        if (!NetUtil.isNetworkAvailable(mContext)) {
//            mView.onNoNetwork();
//            return;
//        }

        Subscription subscription = mSource.getDiscountBeanList()
                .observeOn(Schedulers.io())
                .doOnSubscribe(() -> mView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DiscountBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.showError(e.getMessage());
                    }
                    @Override
                    public void onNext(List<DiscountBean> discountBeanList) {
                        mView.hideLoading();
                        mView.successGetDiscountBeans(discountBeanList);
                    }
                });

        addSubscription(subscription);

    }

    //获取优惠券
    public void getCouponBeanList(){
        //目前无网络环境下请求，暂时注掉
//        if (!NetUtil.isNetworkAvailable(mContext)) {
//            mView.onNoNetwork();
//            return;
//        }

        Subscription subscription = mSource.getCouponBeanList()
                .observeOn(Schedulers.io())
                .doOnSubscribe(() -> mView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CouponBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.showError(e.getMessage());
                    }
                    @Override
                    public void onNext(List<CouponBean> couponBeanList) {
                        mView.hideLoading();
                        mView.successGetCouponBeans(couponBeanList);
                    }
                });

        addSubscription(subscription);

    }

}
