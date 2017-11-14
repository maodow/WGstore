package com.louisk.wgstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.louisk.wgstore.Constant;
import com.louisk.wgstore.R;
import com.louisk.wgstore.adapter.GoodsListAdapter;
import com.louisk.wgstore.base.PresenterFragment;
import com.louisk.wgstore.db.GoodsChannelTableManager;
import com.louisk.wgstore.entity.GoodsBean;
import com.louisk.wgstore.presenter.HomePresenter;
import com.louisk.wgstore.utils.PreferenceUtil;
import com.louisk.wgstore.view.DividerGridItemDecoration;
import com.louisk.wgstore.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * des:商品列表fragment,
 * 本页面负责添加商品到购物车（DB获取），取得回调后，通知购物车页面刷新UI,
 * 并处理同一件商品的反复添加，修改DB表的num字段。
 */
public class GoodsFragment extends PresenterFragment<HomePresenter> implements HomeView{

    @BindView(R.id.rlv_goods_list)
    RecyclerView rlvGoodsList;

    private GoodsListAdapter adapter;

    private String mGoodsType;
    private int mGoodsNum;
    private List<GoodsBean> goodsInfoList = new ArrayList<>();
    private GoodsBean goodsBean;
    private HomePresenter presenter;
    private boolean isFirstAddGoods;//用户是否是在添加第一件商品


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        if(getArguments() != null){
            mGoodsType = getArguments().getString(Constant.GOODS_TYPE);
        }
        goodsInfoList = GoodsChannelTableManager.loadGoodsInfo(mGoodsType);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rlvGoodsList.setLayoutManager(layoutManager);
        rlvGoodsList.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        //商品添加到DB:添加一件商品之前先【查询】 DB中有无此商品(此查询操作应在用户添加第一件商品之后)。Y：update； N：add
        adapter = new GoodsListAdapter(getActivity(), (v, object) -> {
            int itemPosition = (int) object;
            goodsBean = goodsInfoList.get(itemPosition);
            isFirstAddGoods = PreferenceUtil.getBoolean("addGoodsFlag", true);
            if(isFirstAddGoods){
                presenter.addGoodsToDB(goodsBean.getGoodsType(), goodsBean.getGoodsName(), goodsBean.getGoodsPrice());
            }else{//第二次添加时，判断此商品是否已经添加过
                presenter.getAllGoodsInDB();//查询DB
            }
        });

        rlvGoodsList.setAdapter(adapter);
        adapter.setRecyclerView(rlvGoodsList);
        adapter.setGoodsBeanList(goodsInfoList);
    }

    @Override
    public HomePresenter createPresenter() {
        presenter = new HomePresenter(getActivity(), this);
        return presenter;
    }

    @Override
    public void onGetAllGoodsInDB(List<GoodsBean> goodsBeanList) {
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            boolean isExist = false;
            for (int i = 0; i < goodsBeanList.size(); i++) {
                if (goodsBeanList.get(i).getGoodsName().equals(goodsBean.getGoodsName())) {//此商品已存在
                    isExist = true;
                    mGoodsNum = goodsBeanList.get(i).getGoodsNum();
                }
            }

            if (isExist) {
                presenter.updateGoodsNum(mGoodsNum + 1, goodsBean.getGoodsName());
            } else {//不存在，直接添加
                presenter.addGoodsToDB(goodsBean.getGoodsType(), goodsBean.getGoodsName(), goodsBean.getGoodsPrice());
            }
        }
    }


    @Override
    public void onAddGoodsToDB(long resultCode) {
        if(resultCode != -1){
            showToast("一个 "+goodsBean.getGoodsName()+"被加入购物车");
            PreferenceUtil.commitBoolean("addGoodsFlag", false);
            //通知购物车页面刷新。。。
//            EventBus.getDefault().post(new NotifyEvent("refresh"));
            notifyCartSetChanged();

        }
    }

    @Override
    public void onUpdateGoodsNum(long resultCode) {
        Log.e("GoodsFragment, ====", "resultCode = "+resultCode);
        showToast(goodsBean.getGoodsName()+" +1");
        //同样的，同一商品的数量发生改变时，通知购物车刷新
        notifyCartSetChanged();
    }

    private void notifyCartSetChanged(){
        HomeCartFragment homeCartFragment = (HomeCartFragment)getActivity().getSupportFragmentManager()
                .findFragmentByTag("HomeCartFragment");
        homeCartFragment.refreshData();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_goods;
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
