package com.example.wanandroid.ui.main.launch;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.base.event.Event;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class LaunchViewModel extends BaseViewModel {
	// TODO: Implement the ViewModel
	final MutableLiveData<Event<Object>> startAnimationEvent = new MutableLiveData<>();
	
	final MutableLiveData<Event<Object>> jumpToMain = new MutableLiveData<>();
	
	@ViewModelInject
	public LaunchViewModel(DataRepository dataRepository) {
		super(dataRepository);
		if (!App.isFirstRun) {
			jumpToMain.setValue(new Event<>(new Object()));
		} else {
			App.isFirstRun = false;
			startAnimationEvent.setValue(new Event<>(new Object()));
			addDisposable(Observable.timer(3000, TimeUnit.MILLISECONDS)
					.subscribe(new Consumer<Long>() {
						@Override
						public void accept(Long aLong) throws Exception {
							jumpToMain.postValue(new Event<>(new Object()));
						}
					}));
		}
	}
	
}