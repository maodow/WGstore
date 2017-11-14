package com.louisk.wgstore.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.louisk.wgstore.WG_Application;
import com.louisk.wgstore.presenter.Presenter;
import com.louisk.wgstore.utils.RxUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class PresenterFragment<T extends Presenter> extends Fragment {
    protected View mRootView;
    private T mPresenter;
    protected boolean isVisible;
    private CompositeSubscription mCompositeSubscription;
    public WG_Application mApp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mApp = (WG_Application) getActivity().getApplication();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(layoutRes(), container, false);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        if (mRootView != null && mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 刷新当前界面，  暂时屏蔽。。。。。。。
     */
//    @Override
//    public void onResume(){
//        super.onResume();
//        if (this.isHidden()){
//            onActivityResume();
//        }else{
//            onFragmentResume();
//        }
//    }

    /**
     * Fragment 所依附的Activity 执行onResume
     */
    public void onActivityResume(){}

    /**
     * Fragment 当前处于显示状态时 执行
     */
    public void onFragmentResume(){
        super.onResume();
    }

    protected abstract int layoutRes();


    protected void onVisible() {
        requestData();
    }

    protected void onInvisible() {
    }

    protected void requestData() {
    }

    public abstract T createPresenter();


    public T getPresenter() {
        return this.mPresenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onUiReady();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onUiDestroy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideSoftInput();
    }

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    protected void setOnViewClick(View v) {
        addSubscription(RxUtils.clicks(v).subscribe(aVoid -> onViewClick(v)));
    }

    @Subscribe
    public void onViewClickEvent(ViewClickEvent event) {
        onViewClick(event.clickedView);
    }

    protected void onViewClick(View view) {

    }

    public void setWaitingShowToUser(boolean b) {
    }

    private static class ViewClickEvent {

        View clickedView;

        ViewClickEvent(View view) {
            this.clickedView = view;
        }

    }

    protected void hideSoftInput() {
        View v = getActivity().getCurrentFocus();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager manager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = manager.isActive();
            if (isOpen) {
                manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}
