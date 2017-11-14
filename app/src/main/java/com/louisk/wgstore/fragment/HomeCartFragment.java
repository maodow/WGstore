package com.louisk.wgstore.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louisk.wgstore.Constant;
import com.louisk.wgstore.R;
import com.louisk.wgstore.adapter.CartCouponAdapter;
import com.louisk.wgstore.adapter.CartDiscountAdapter;
import com.louisk.wgstore.adapter.CartGoodsListAdapter;
import com.louisk.wgstore.base.PresenterFragment;
import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.presenter.HomeCartPresenter;
import com.louisk.wgstore.utils.CollectionUtils;
import com.louisk.wgstore.view.DividerGridItemDecoration;
import com.louisk.wgstore.view.HomeCartView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 购物车
 */
public class HomeCartFragment extends PresenterFragment<HomeCartPresenter> implements HomeCartView {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recy_goods)
    RecyclerView recyGoods;
    @BindView(R.id.recy_discount)
    RecyclerView recyDiscount;
    @BindView(R.id.recy_coupon)
    RecyclerView recyCoupon;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_checkout)
    Button btnCheckout;
    @BindView(R.id.tv_label_discount)
    TextView tvLabelDiscount;
    @BindView(R.id.tv_label_coupon)
    TextView tvLabelCoupon;
    @BindView(R.id.ly_total)
    LinearLayout lyTotal;
    @BindView(R.id.ll_cart_null)
    LinearLayout llCartNull;

    private CartGoodsListAdapter cartGoodsListAdapter;
    private CartDiscountAdapter cartDiscountAdapter;
    private CartCouponAdapter cartCouponAdapter;
    private List<GoodsBean> goodsBeanList = new ArrayList<>();
    private List<DiscountBean> discountBeanList = new ArrayList<>();
    private List<CouponBean> couponBeanList = new ArrayList<>();

    private HomeCartPresenter presenter;

    //计算相关的变量
    private int roughTotal;//商品总价
    private double discountTotal;//打折后总价

    private double electrMoney;//电子类总价
    private double foodMoney;//食品类总价
    private double commodityMoney;//日用品类总价
    private double drinkMoney;//酒类总价

    private List<GoodsBean> electr_goodsList = new ArrayList<>();//电子类
    private List<GoodsBean> food_goodsList = new ArrayList<>();//食品类
    private List<GoodsBean> commodity_goodsList = new ArrayList<>();//日用品类
    private List<GoodsBean> drink_goodsList = new ArrayList<>();//酒类

    private static final int FINISH_CALCULATE = 1;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == FINISH_CALCULATE){
                tvTotalMoney.setText(discountTotal+"");
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle.setText("购物车");
        btnBack.setVisibility(View.GONE);
        init();
        //注册EventBus
//        EventBus.getDefault().register(this);
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyGoods.setLayoutManager(layoutManager);
        recyGoods.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        cartGoodsListAdapter = new CartGoodsListAdapter(getActivity());
        recyGoods.setAdapter(cartGoodsListAdapter);
        cartGoodsListAdapter.setRecyclerView(recyGoods);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyDiscount.setLayoutManager(layoutManager2);
        recyDiscount.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        cartDiscountAdapter = new CartDiscountAdapter(getActivity());
        recyDiscount.setAdapter(cartDiscountAdapter);
        cartDiscountAdapter.setRecyclerView(recyDiscount);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        recyCoupon.setLayoutManager(layoutManager3);
        recyCoupon.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        cartCouponAdapter = new CartCouponAdapter(getActivity());
        recyCoupon.setAdapter(cartCouponAdapter);
        cartCouponAdapter.setRecyclerView(recyCoupon);

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home_cart;
    }

    @Override
    public HomeCartPresenter createPresenter() {
        presenter = new HomeCartPresenter(getActivity(), this);
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getGoods();//获取商品列表
        presenter.getDiscountBeanList();//折扣券，优惠券
    }

//    public void onEventMainThread(NotifyEvent event) {
//        if (event.getMsg().equals("refresh")) {
//            presenter.getGoods();//获取商品列表
//        }
//    }

    public void refreshData(){
        presenter.getGoods();
        presenter.getDiscountBeanList();//折扣券，优惠券
    }

    @Override
    public void successGetgoods(List<GoodsBean> goodsBeanList) {
        if (goodsBeanList.size() > 0) {
            this.goodsBeanList = goodsBeanList;
            llCartNull.setVisibility(View.GONE);
        } else {
            llCartNull.setVisibility(View.VISIBLE);
        }

        cartGoodsListAdapter.setGoodsList(goodsBeanList);
        cartGoodsListAdapter.notifyDataSetChanged();
    }


    //折扣券
    @Override
    public void successGetDiscountBeans(List<DiscountBean> discountBeanList) {
        //分步获取
        presenter.getCouponBeanList();//优惠券

        if (!this.discountBeanList.isEmpty()) this.discountBeanList.clear();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String deadLine = format.format(new Date(System.currentTimeMillis()));

        //原始数据
        DiscountBean discount1 = new DiscountBean("电子类", 7.0, deadLine);
        DiscountBean discount2 = new DiscountBean("日用品类", 6.0, deadLine);
        discountBeanList.add(discount1);
        discountBeanList.add(discount2);

        //新增数据
        if (discountBeanList != null && discountBeanList.size() > 0) {
            this.discountBeanList = discountBeanList;
        }

        cartDiscountAdapter.setDiscountBeanList(discountBeanList);
        cartDiscountAdapter.notifyDataSetChanged();
    }

    //优惠券
    @Override
    public void successGetCouponBeans(List<CouponBean> couponBeanList) {
        if (!this.couponBeanList.isEmpty()) this.couponBeanList.clear();

        CouponBean coupon1 = new CouponBean(1000, 200, "2020/3/1");
        couponBeanList.add(coupon1);

        //新增数据
        if (couponBeanList != null && couponBeanList.size() > 0) {
            this.couponBeanList = couponBeanList;
        }

        new Thread(new CalculatePriceRunnable()).start();//所有请求回调结束之后，计算最终价格

        cartCouponAdapter.setCouponBeanList(couponBeanList);
        cartCouponAdapter.notifyDataSetChanged();
    }

    /**
     * 商品最终价格的计算
     */
    class CalculatePriceRunnable implements Runnable {
        @Override
        public void run() {
            classifyGoods();
            discountCalculate();
            couponCalculate();
            handler.sendEmptyMessage(FINISH_CALCULATE);
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onNoNetwork() {
        showToast("网络未连接");
    }


    @Override
    public void showError(String message) {

    }

    private void classifyGoods() {//商品分拣
        //清空旧数据
        if(electr_goodsList.size() > 0){
            electr_goodsList.clear();
        }
        if(food_goodsList.size() > 0){
            food_goodsList.clear();
        }
        if(commodity_goodsList.size() > 0){
            commodity_goodsList.clear();
        }if(drink_goodsList.size() > 0){
            drink_goodsList.clear();
        }

        roughTotal = 0;
        for(int i = 0; i < goodsBeanList.size(); i++){
            if(goodsBeanList.get(i).getGoodsType().equals(Constant.GOODS_TYPE_ELEC)){
                electr_goodsList.add(goodsBeanList.get(i));
            }else if(goodsBeanList.get(i).getGoodsType().equals(Constant.GOODS_TYPE_FOOD)){
                food_goodsList.add(goodsBeanList.get(i));
            }else if(goodsBeanList.get(i).getGoodsType().equals(Constant.GOODS_TYPE_COMMODITY)){
                commodity_goodsList.add(goodsBeanList.get(i));
            }else{
                drink_goodsList.add(goodsBeanList.get(i));
            }

            roughTotal += goodsBeanList.get(i).getGoodsPrice() * goodsBeanList.get(i).getGoodsNum();
        }

        //分别计算各个商品类型的总价
        if(electr_goodsList.size() > 0){
            electrMoney = 0;
            for(int i = 0; i < electr_goodsList.size(); i++){
                electrMoney += electr_goodsList.get(i).getGoodsPrice() * electr_goodsList.get(i).getGoodsNum();
            }
        }
        if(food_goodsList.size() > 0){
            foodMoney = 0;
            for(int i = 0; i < food_goodsList.size(); i++){
                foodMoney += food_goodsList.get(i).getGoodsPrice() * food_goodsList.get(i).getGoodsNum();
            }
        }
        if(commodity_goodsList.size() > 0){
            commodityMoney = 0;
            for(int i = 0; i < commodity_goodsList.size(); i++){
                commodityMoney += commodity_goodsList.get(i).getGoodsPrice() * commodity_goodsList.get(i).getGoodsNum();
            }
        }
        if(drink_goodsList.size() > 0){
            drinkMoney = 0;
            for(int i = 0; i < drink_goodsList.size(); i++){
                drinkMoney += drink_goodsList.get(i).getGoodsPrice() * drink_goodsList.get(i).getGoodsNum();
            }
        }

        Log.e("HomeCartFragment===","商品总价："+roughTotal+", 其中，电子类："+electrMoney+", 食品类："+foodMoney+", 日用品类："+commodityMoney+", 酒类："+drinkMoney);

    }

    private void discountCalculate() {//进行打折计算
        String discountDeadLine = "";
        for(int i = 0; i < discountBeanList.size(); i++){
            discountDeadLine = CollectionUtils.dataTotimestamp(discountBeanList.get(i).getDiscountDeadLine());
            if(Long.parseLong(discountDeadLine)*1000 >= CollectionUtils.getCurrMillis()){//有效期内
                if(discountBeanList.get(i).getDiscountType().equals("电子类")){
                    electrMoney = electrMoney * discountBeanList.get(i).getDiscountExtent()/10;
                }else if(discountBeanList.get(i).getDiscountType().equals("食品类")){
                    foodMoney = foodMoney * discountBeanList.get(i).getDiscountExtent()/10;
                }else if(discountBeanList.get(i).getDiscountType().equals("日用品类")){
                    commodityMoney = commodityMoney * discountBeanList.get(i).getDiscountExtent()/10;
                }else {
                    drinkMoney = drinkMoney * discountBeanList.get(i).getDiscountExtent()/10;
                }
            }
        }

        discountTotal = electrMoney + foodMoney + commodityMoney + drinkMoney;
        Log.e("HomeCartFragment===","打折后："+discountTotal);
    }

    private void couponCalculate() {//计算优惠券
        String couponDeadLine = "";
        for(int i = 0; i < couponBeanList.size(); i++){
            couponDeadLine = CollectionUtils.dataTotimestamp(couponBeanList.get(i).getCouponBeanDeadLine());
            if(Long.parseLong(couponDeadLine)*1000 >= CollectionUtils.getCurrMillis()){
                if(discountTotal > couponBeanList.get(i).getTotalMoney()){
                    discountTotal = discountTotal - couponBeanList.get(i).getFreeMoney();
                    Log.e("HomeCartFragment===","满减后："+discountTotal);
                    return;//主要有一张满足就行，优惠券只能用一张
                }
            }
        }
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.btn_checkout:
                showToast("即将跳转支付界面。。。");
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);//反注册EventBus
    }

}
