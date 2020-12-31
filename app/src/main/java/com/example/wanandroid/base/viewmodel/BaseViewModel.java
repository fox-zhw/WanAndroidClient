package com.example.wanandroid.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.event.Event;
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
	
	final CompositeDisposable mDisposable = new CompositeDisposable();
	
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
	
	@Override
	protected void onCleared() {
		mDisposable.clear();
		super.onCleared();
	}
	
	public final MutableLiveData<Event<String>> mToastMsgEvent = new MutableLiveData<>();
	public final MutableLiveData<Event<String>> mSnackBarMsgEvent = new MutableLiveData<>();
	public final MutableLiveData<Event<Object>> mShowErrorEvent = new MutableLiveData<>();
	
}
