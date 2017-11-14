package com.louisk.wgstore.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.louisk.wgstore.Constant;
import com.louisk.wgstore.R;
import com.louisk.wgstore.base.PresenterActivity;
import com.louisk.wgstore.entity.TabEntity;
import com.louisk.wgstore.fragment.HomeCartFragment;
import com.louisk.wgstore.fragment.HomeFragment;
import com.louisk.wgstore.fragment.HomeMeFragment;
import com.louisk.wgstore.presenter.Presenter;
import java.util.ArrayList;
import butterknife.BindView;

public class MainActivity extends PresenterActivity {
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"首页", "我", "购物车"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal,R.mipmap.ic_me_normal,R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected,R.mipmap.ic_me_selected,R.mipmap.ic_care_selected };

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private HomeFragment homeFragment;
    private HomeMeFragment HomeMeFragment;
    private HomeCartFragment homeCartFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        initTab();
    }

    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        tabLayout.measure(0,0);
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
            HomeMeFragment = (HomeMeFragment) getSupportFragmentManager().findFragmentByTag("HomeMeFragment");
            homeCartFragment = (HomeCartFragment) getSupportFragmentManager().findFragmentByTag("HomeCartFragment");
            currentTabPosition = savedInstanceState.getInt(Constant.HOME_CURRENT_TAB_POSITION);
        } else {
            homeFragment = new HomeFragment();
            HomeMeFragment = new HomeMeFragment();
            homeCartFragment = new HomeCartFragment();

            transaction.add(R.id.fl_body, homeFragment, "HomeFragment");
            transaction.add(R.id.fl_body, HomeMeFragment, "HomeMeFragment");
            transaction.add(R.id.fl_body, homeCartFragment, "HomeCartFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //崩溃前保存位置
        Log.e("===", "onSaveInstanceState进来了1");
        if (tabLayout != null) {
            Log.e("===", "onSaveInstanceState进来了2");
            savedInstanceState.putInt(Constant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(HomeMeFragment);
                transaction.hide(homeCartFragment);
                transaction.show(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我
            case 1:
                transaction.hide(homeFragment);
                transaction.hide(homeCartFragment);
                transaction.show(HomeMeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //购物车
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(HomeMeFragment);
                transaction.show(homeCartFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

}
