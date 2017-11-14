package com.louisk.wgstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.louisk.wgstore.R;
import com.louisk.wgstore.activity.AddDiscountActivity;
import com.louisk.wgstore.adapter.DiscountListAdapter;
import com.louisk.wgstore.base.PresenterFragment;
import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.presenter.HomeCartPresenter;
import com.louisk.wgstore.view.HomeCartView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 折扣
 */

public class DiscountFragment extends PresenterFragment<HomeCartPresenter> implements HomeCartView {
    @BindView(R.id.rlv_discounts)
    RecyclerView rlvDiscounts;
    @BindView(R.id.iamgbtn_add)
    ImageButton iamgbtnAdd;

    private DiscountListAdapter adapter;
    private List<DiscountBean> discountBeans = new ArrayList<>();
    private HomeCartPresenter presenter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setOnViewClick(iamgbtnAdd);
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rlvDiscounts.setLayoutManager(layoutManager);
        adapter = new DiscountListAdapter(getActivity());
        rlvDiscounts.setAdapter(adapter);
        adapter.setRecyclerView(rlvDiscounts);
    }

    @Override
    public HomeCartPresenter createPresenter() {
        presenter = new HomeCartPresenter(getActivity(), this);
        return presenter;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getDiscountBeanList();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_discount;
    }


    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.iamgbtn_add:
                getActivity().startActivity(new Intent(getActivity(), AddDiscountActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    public void successGetgoods(List<GoodsBean> goodsBeanList) {

    }

    @Override
    public void successGetDiscountBeans(List<DiscountBean> discountBeanList) {
        if(!this.discountBeans.isEmpty()) this.discountBeans.clear();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String deadLine = format.format(new Date(System.currentTimeMillis()));

        //原始数据
        DiscountBean discount1 = new DiscountBean("电子类", 7.0, deadLine);
        DiscountBean discount2 = new DiscountBean("日用品类", 6.0, deadLine);
        discountBeans.add(discount1);
        discountBeans.add(discount2);

        //新增数据
        if (discountBeanList != null && discountBeanList.size() > 0) {
            this.discountBeans.addAll(discountBeanList);
        }
        adapter.setDiscountBeanList(discountBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void successGetCouponBeans(List<CouponBean> couponBeanList) {

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

    }

}
