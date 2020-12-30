package com.example.wanandroid.base;

public interface IView {
	
    void useNightMode(boolean isNightMode);

    void showNormal();

    void showError();

    void showLoading();

    void reload();

//    void showLoginView();

//    void showLogoutView();

//    void showCollectSuccess();

//    void showCancelCollectSuccess();

    void showToast(String message);

    void showSnackBar(String message);
}
