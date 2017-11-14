package com.louisk.wgstore.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class Presenter {

  public void onUiReady() {

  }

  public void onUiDestroy() {
    if (mCompositeSubscription != null) {
      mCompositeSubscription.unsubscribe();
      mCompositeSubscription.clear();
    }
  }

  private CompositeSubscription mCompositeSubscription;


  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    this.mCompositeSubscription.add(s);
  }

  public CompositeSubscription getCompositeSubscription() {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    return this.mCompositeSubscription;
  }
}
