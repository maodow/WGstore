package com.louisk.wgstore.view;

public interface IView {

  void showLoading();

  void hideLoading();

  void showError(String message);

  void onNoNetwork();

}
