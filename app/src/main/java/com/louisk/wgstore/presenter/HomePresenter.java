package com.louisk.wgstore.presenter;

import android.content.Context;

import com.louisk.wgstore.Model.HomeModel;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.view.HomeView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/11/6.
 *
 */

public class HomePresenter extends Presenter {

    private Context mContext;
    private HomeView mView;
    private HomeModel mSource;

    public HomePresenter(Context mContext, HomeView mView) {
        this.mView = mView;
        this.mContext = mContext;
        mSource = new HomeModel();
    }

    public void getAllGoodsInDB() {
//        if (!NetUtil.isNetworkAvailable(mContext)) {
//            mView.onNoNetwork();
//            return;
//        }
        Subscription subscription = mSource.getAllGoodsInDB()
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
                        mView.onGetAllGoodsInDB(goodsBeanList);
                    }
                });

        addSubscription(subscription);
    }

    //添加商品到DB
    public void addGoodsToDB(String goodsType, String goodsName, double goodsPrice) {
//        if (!NetUtil.isNetworkAvailable(mContext)) {
//            mView.onNoNetwork();
//            return;
//        }

        Subscription subscription = mSource.addGoodsToDB(goodsType, goodsName, goodsPrice)
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
                        mView.onAddGoodsToDB(resultCode);
                    }
                });

        addSubscription(subscription);
    }


    //更改同一商品的数量
    public void updateGoodsNum(int goodsNum, String goodsName) {
//        if (!NetUtil.isNetworkAvailable(mContext)) {
//            mView.onNoNetwork();
//            return;
//        }

        Subscription subscription = mSource.updateGoodsNum(goodsNum, goodsName)
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
                        mView.onUpdateGoodsNum(resultCode);
                    }
                });

        addSubscription(subscription);
    }
}
