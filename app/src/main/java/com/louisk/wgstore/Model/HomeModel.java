package com.louisk.wgstore.Model;

import com.louisk.wgstore.api.CampusRepository;
import com.louisk.wgstore.entity.GoodsBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/11/6.
 *
 */

public class HomeModel {

    //获取DB中所有商品
    public Observable<List<GoodsBean>> getAllGoodsInDB() {
        return CampusRepository.getInstance().getGoodsBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //添加商品到DB
    public Observable<Long> addGoodsToDB(String goodsType, String goodsName, double goodsPrice) {
        return CampusRepository.getInstance().addGoodsToDB(goodsType, goodsName, goodsPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //更改同一商品的数量
    public Observable<Long> updateGoodsNum(int goodsNum, String goodsName) {
        return CampusRepository.getInstance().updateGoodsNum(goodsNum, goodsName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
