package com.louisk.wgstore.api;

import android.util.Log;

import com.louisk.wgstore.Constant;
import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.helper.OAuthInterceptor;
import com.louisk.wgstore.utils.DbUtils;
import com.louisk.wgstore.utils.PreferenceUtil;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 模拟网络请求的耗时操作，操作db并回调到mainThread
 *
 */
public class CampusRepository {

    private static CampusApi campusApi;
    public static final String BASE_URL = "http://192.168./";

    public static CampusRepository getInstance() {
        return new CampusRepository();
    }

    public static CampusRepository getInstance(String name, String password) {
        return new CampusRepository(name, password);
    }

    private CampusRepository() {//Token 登录
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("---", "==OKHTTP=" + message));

        Interceptor interceptor = chain -> {
            Request request = chain.request().newBuilder().addHeader("Token", PreferenceUtil.getString(Constant.TOKEN, "")).build();
            Log.e("CampusRepository------", "CampusRepository(), Token = " + PreferenceUtil.getString(Constant.TOKEN, ""));
            Response response;
            try {
                response = chain.proceed(request);
                Log.e("CampusRepository-----", "CampusRepository(), response = chain.proceed -->" + response);
            } catch (Exception e) {
                throw e;
            }
            return response;
        };

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).addNetworkInterceptor(httpLoggingInterceptor).build();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        campusApi = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(CampusApi.class);
    }

    private CampusRepository(String name, String password) {//用户名+密码 登录
        OAuthInterceptor interceptor = new OAuthInterceptor(new OAuthInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("CampusRepository----", "CampusRepository(String name, String password), ==OKHTTP=" + message);
            }
        }, name, password);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).addNetworkInterceptor(interceptor).build();

        campusApi = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(CampusApi.class);
    }


    //栗子
    public Observable<String> getHome() {
        return campusApi.getHome();
    }


    //添加商品到DB
    public Observable<Long> addGoodsToDB(String goodsType, String goodsName, double goodsPrice) {
        long resultCode = DbUtils.getInstance().addGoodsToDb(goodsType, goodsName, goodsPrice);
        return Observable.just(resultCode);
    }

    //更改同一商品的数量
    public Observable<Long> updateGoodsNum(int goodsNum, String goodsName) {
        long resultCode = DbUtils.getInstance().updateGoodsNum(goodsNum, goodsName);
        return Observable.just(resultCode);
    }


    //添加折扣券
    public Observable<Long> addPromotToDB(String goodsType, double discountExtent, String discountDeadLine, String discountAddTime) {
        long resultCode = DbUtils.getInstance().addDiscountToDb(goodsType, discountExtent, discountDeadLine, discountAddTime);
        return Observable.just(resultCode);
    }

    //添加优惠券
    public Observable<Long> addCouponToDB(int totalMoney, int freeMoney, String couponDeadLine, String couponAddTime) {
        long resultCode = DbUtils.getInstance().addCouponToDb(totalMoney, freeMoney, couponDeadLine, couponAddTime);
        return Observable.just(resultCode);
    }

    //获取购物车的所有商品
    public Observable<List<GoodsBean>> getGoodsBean() {
        List<GoodsBean> goodsBeanList = DbUtils.getInstance().getAllGoodsInShoppingCart();
        return Observable.just(goodsBeanList);
    }

    //获取折扣券
    public Observable<List<DiscountBean>> getDiscountBeanList() {
        List<DiscountBean> discountBeanList = DbUtils.getInstance().getDiscountBeanList();
        return Observable.just(discountBeanList);
    }

    //获取优惠券
    public Observable<List<CouponBean>> getCouponBeanList() {
        List<CouponBean> couponBeanList = DbUtils.getInstance().getCouponBeanList();
        return Observable.just(couponBeanList);
    }
}
