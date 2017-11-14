package com.louisk.wgstore.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.louisk.wgstore.R;
import com.louisk.wgstore.base.PresenterFragment;
import com.louisk.wgstore.presenter.Presenter;

import butterknife.BindView;

/**
 * 我，页面
 */
public class HomeMeFragment extends PresenterFragment {
    @BindView(R.id.rb_left_discount)
    RadioButton rbLeftDiscount;
    @BindView(R.id.rb_right_coupon)
    RadioButton rbRightCoupon;
    @BindView(R.id.rg_discount)
    RadioGroup rgDiscount;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private Activity mActivity;
    private Fragment currFragment; //当前的fragment
    private FragmentTransaction transaction;
    private DiscountFragment discountFragment;
    private CouponFragment couponFragment;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);

        refreshShowCurrFragment();

        rgDiscount.setOnCheckedChangeListener((group, checkedId) -> refreshShowCurrFragment());
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshShowCurrFragment();
    }


    /**
     * 刷新当前正确显示的fragment
     */
    private void refreshShowCurrFragment() {
        if (mActivity.isFinishing())
            return;
        Fragment tem_Fragment = getShowFragment();
        if (currFragment != null && currFragment == tem_Fragment) { //如果已经正确显示则不变
            return;
        }
        Fragment last_fragment = currFragment;
        currFragment = tem_Fragment;
        transaction = getChildFragmentManager().beginTransaction();
        if (last_fragment != null) {
            transaction.hide(last_fragment);
        }
        if (currFragment.isAdded()) {
            transaction.show(currFragment);
        } else {
            transaction.add(R.id.fragment_container, currFragment);
        }
//		transaction.replace(R.id.fragment_container, currFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取当前应该正确显示的fragment
     *
     * @return
     */
    private Fragment getShowFragment() {
        Fragment fragment = null;
        switch (rgDiscount.getCheckedRadioButtonId()) {
            case R.id.rb_left_discount:
                if (discountFragment == null) {
                    discountFragment = new DiscountFragment();
                }
                fragment = discountFragment;
                break;
            case R.id.rb_right_coupon:
                if (couponFragment == null) {
                    couponFragment = new CouponFragment();
                }
                fragment = couponFragment;
                break;
            default:
                break;
        }

        return fragment;
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_home_me;
    }


    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
