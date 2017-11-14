package com.louisk.wgstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.louisk.wgstore.R;
import com.louisk.wgstore.activity.AddCouponActivity;
import com.louisk.wgstore.adapter.CouponListAdapter;
import com.louisk.wgstore.base.PresenterFragment;
import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.presenter.HomeCartPresenter;
import com.louisk.wgstore.view.HomeCartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 优惠券
 */

public class CouponFragment extends PresenterFragment<HomeCartPresenter> implements HomeCartView {
    @BindView(R.id.rlv_coupon)
    RecyclerView rlvCoupon;
    @BindView(R.id.iamgbtn_add2)
    ImageButton iamgbtnAdd2;

    private CouponListAdapter adapter;
    private List<CouponBean> couponBeans = new ArrayList<>();
    private HomeCartPresenter presenter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setOnViewClick(iamgbtnAdd2);
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rlvCoupon.setLayoutManager(layoutManager);
        adapter = new CouponListAdapter(getActivity());
        rlvCoupon.setAdapter(adapter);
        adapter.setRecyclerView(rlvCoupon);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_coupon;
    }

    @Override
    public HomeCartPresenter createPresenter() {
        presenter = new HomeCartPresenter(getActivity(), this);
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCouponBeanList();
    }


    @Override
    public void successGetgoods(List<GoodsBean> goodsBeanList) {

    }

    @Override
    public void successGetDiscountBeans(List<DiscountBean> discountBeanList) {

    }

    @Override
    public void successGetCouponBeans(List<CouponBean> couponBeanList) {
        if (!this.couponBeans.isEmpty()) this.couponBeans.clear();
        //原始数据
        CouponBean coupon1 = new CouponBean(1000, 200, "2020/3/1");
        couponBeans.add(coupon1);

        //新增数据
        if (couponBeanList != null && couponBeanList.size() > 0) {
            this.couponBeans.addAll(couponBeanList);
        }

        adapter.setCouponBeanList(couponBeans);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.iamgbtn_add2:
                getActivity().startActivity(new Intent(getActivity(), AddCouponActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onNoNetwork() {
        showToast("网络未连接");
    }

}
