package com.example.wanandroid.ui.user.login;

import android.text.TextUtils;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.main.login.LoginData;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

public class LoginViewModel extends BaseViewModel {
	// TODO: Implement the ViewModel
	
	//
	final MutableLiveData<Event<Object>> mLoginSuccessLiveEvent = new MutableLiveData<>();
	
	@ViewModelInject
	public LoginViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	public void getLoginData(String username, String password) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.account_password_null_tint)));
			return;
		}
		addDisposable(mDataRepository.getLoginData(username, password)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<LoginData>() {
					@Override
					public void onNext(LoginData loginData) {
						setLoginAccount(loginData.getUsername());
						setLoginPassword(loginData.getPassword());
						setLoginStatus(true);
						
						mLoginSuccessLiveEvent.postValue(new Event<>(new Object()));
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.login_fail)));
						mShowErrorEvent.postValue(new Event<>(new Object()));
					}
				}));
	}
}