package com.example.wanandroid.ui.user.register;

import android.text.TextUtils;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.main.login.LoginData;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

public class RegisterViewModel extends BaseViewModel {
	public static final String TAG = "RegisterViewModel";
	
	@ViewModelInject
	public RegisterViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	public void getRegisterData(String username, String password, String rePassword) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
			mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.account_password_null_tint)));
			return;
		}
		if (!password.equals(rePassword)) {
			mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.password_not_same)));
			return;
		}
		addDisposable(mDataRepository.getRegisterData(username, password, rePassword)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.filter(loginResponse -> !TextUtils.isEmpty(username)
						&& !TextUtils.isEmpty(password)
						&& !TextUtils.isEmpty(rePassword))
				.subscribeWith(new BaseObserver<LoginData>() {
					@Override
					public void onNext(LoginData loginData) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.register_success)));
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.register_fail)));
					}
				}));
	}
}