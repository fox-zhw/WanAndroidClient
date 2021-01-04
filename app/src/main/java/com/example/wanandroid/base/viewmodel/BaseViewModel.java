package com.example.wanandroid.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.rx.RxUtils;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * @author zhaohw
 * @date 2020/12/16
 */
public abstract class BaseViewModel extends ViewModel {
	
	protected final DataRepository mDataRepository;
	
	final CompositeDisposable mDisposable = new CompositeDisposable();
	
	public BaseViewModel(DataRepository dataRepository) {
		mDataRepository = dataRepository;
	}
	
	public <T> void addDisposable(Flowable<T> flowable, Consumer<T> consumer) {
		try {
			mDisposable.add(flowable
					.compose(RxUtils.transFlowable())
					.subscribe(consumer));
		} catch (Exception e) {
			Timber.e(e);
		}
	}
	
	public <T> void addDisposable(Observable<T> observable, Consumer<T> consumer) {
		try {
			mDisposable.add(observable
					.compose(RxUtils.transObservable())
					.subscribe(consumer));
		} catch (Exception e) {
			Timber.e(e);
		}
	}

	public <T> void addDisposable(Completable completable, Action action) {
		mDisposable.add(completable
				.compose(RxUtils.transCompletable())
				.subscribe(action));
	}
	
	public void addDisposable(Disposable disposable) {
		mDisposable.add(disposable);
	}
	
	public boolean getNightModeState() {
		return mDataRepository.getNightModeState();
	}
	
	public void setLoginStatus(boolean loginStatus) {
		mDataRepository.setLoginStatus(loginStatus);
	}
	
	public boolean getLoginStatus() {
		return mDataRepository.getLoginStatus();
	}
	
	public String getLoginAccount() {
		return mDataRepository.getLoginAccount();
	}
	
	public void setLoginAccount(String account) {
		mDataRepository.setLoginAccount(account);
	}
	
	public void setLoginPassword(String password) {
		mDataRepository.setLoginPassword(password);
	}
	
	public int getCurrentPage() {
		return mDataRepository.getCurrentPage();
	}
	
	@Override
	protected void onCleared() {
		mDisposable.clear();
		super.onCleared();
	}
	
	// toast
	public final MutableLiveData<Event<String>> mToastMsgEvent = new MutableLiveData<>();
	// snack bar
	public final MutableLiveData<Event<String>> mSnackBarMsgEvent = new MutableLiveData<>();
	// show error
	public final MutableLiveData<Event<Object>> mShowErrorEvent = new MutableLiveData<>();
	
}
