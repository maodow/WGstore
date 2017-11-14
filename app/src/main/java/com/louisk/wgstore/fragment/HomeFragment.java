package com.louisk.wgstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.louisk.wgstore.Constant;
import com.louisk.wgstore.R;
import com.louisk.wgstore.adapter.BaseFragmentAdapter;
import com.louisk.wgstore.base.PresenterFragment;
import com.louisk.wgstore.db.GoodsChannelTableManager;
import com.louisk.wgstore.entity.GoodsChannelBean;
import com.louisk.wgstore.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页，商品列表
 */
public class HomeFragment extends PresenterFragment {
  @BindView(R.id.tabs)
  TabLayout tabs;
  @BindView(R.id.view_pager)
  ViewPager viewPager;
  private BaseFragmentAdapter fragmentAdapter;


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUserVisibleHint(true);
    init();
  }

  @Override
  protected int layoutRes() {
    return R.layout.fragment_home;
  }

  private void init() {
    List<String> channelNames = new ArrayList<>();
    List<GoodsChannelBean> goodsChannelBeanList = GoodsChannelTableManager.loadGoodsChannelsMine();
    List<Fragment> mNewsFragmentList = new ArrayList<>();
    for (int i = 0; i < goodsChannelBeanList.size(); i++) {
      channelNames.add(goodsChannelBeanList.get(i).getChannelName());
      mNewsFragmentList.add(createListFragments(goodsChannelBeanList.get(i)));
    }
    fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
    viewPager.setAdapter(fragmentAdapter);
    tabs.setupWithViewPager(viewPager);

  }

  private GoodsFragment createListFragments(GoodsChannelBean goodsChannelBean) {
    GoodsFragment fragment = new GoodsFragment();
    Bundle bundle = new Bundle();
    bundle.putString(Constant.GOODS_TYPE, goodsChannelBean.getChannelId());
    fragment.setArguments(bundle);
    return fragment;
  }


  @Override
  public Presenter createPresenter() {
    return null;
  }
}
