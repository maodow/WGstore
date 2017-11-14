package com.louisk.wgstore.utils;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class RxUtils {

  public static Observable<Void> clicks(View view) {
    return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS);
  }
}
