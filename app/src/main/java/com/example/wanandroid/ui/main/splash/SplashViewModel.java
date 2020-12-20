package com.example.wanandroid.ui.main.splash;

import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.base.Event;
import com.example.wanandroid.base.viewmodel.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author 52512
 * @date 2020/12/20
 */
public class SplashViewModel extends BaseViewModel {
	
	final MutableLiveData<Event<Object>> startAnimationEvent = new MutableLiveData<>();
	
	final MutableLiveData<Event<Object>> jumpToMain = new MutableLiveData<>();
	
	public SplashViewModel() {
		if (!App.isFirstRun) {
			jumpToMain.setValue(new Event<>(new Object()));
		} else {
			App.isFirstRun = false;
			startAnimationEvent.setValue(new Event<>(new Object()));
			addDisposable(Observable.timer(5000, TimeUnit.MILLISECONDS)
					.subscribe(new Consumer<Long>() {
						@Override
						public void accept(Long aLong) throws Exception {
							jumpToMain.postValue(new Event<>(new Object()));
						}
					}));
		}
	}
}
