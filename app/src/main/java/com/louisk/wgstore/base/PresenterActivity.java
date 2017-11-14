package com.louisk.wgstore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.louisk.wgstore.WG_Application;
import com.louisk.wgstore.presenter.Presenter;
import com.louisk.wgstore.utils.RxUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class PresenterActivity<T extends Presenter> extends AppCompatActivity {
  private CompositeSubscription mCompositeSubscription;
  private T mPresenter;

  public WG_Application mApp;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    mPresenter = createPresenter();
    initView();
    mApp = (WG_Application) getApplication();
    mApp.addActivity(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (mPresenter != null) {
      mPresenter.onUiReady();
    }
  }

  /*********************子类实现*****************************/
  //获取布局文件
  public abstract int getLayoutId();

  //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
  protected abstract T createPresenter();

  protected T getPresenter() {
    return this.mPresenter;
  }

  //初始化view
  public abstract void initView();


  /*********************UI控件的事件响应*****************************/
  protected void setOnViewClick(View v) {
    addSubscription(RxUtils.clicks(v).subscribe(aVoid -> onViewClick(v)));
  }
  protected void onViewClick(View v) {}


  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    this.mCompositeSubscription.add(s);
  }

  public void showToast(String string) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
  }

  public static String getDate(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(date);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mPresenter != null) {
      mPresenter.onUiDestroy();
    }
  }
}
