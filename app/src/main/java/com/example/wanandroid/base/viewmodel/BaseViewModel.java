package com.example.wanandroid.base.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.wanandroid.rx.RxUtils;

import io.reactivex.Completable;
import io.reactivex.Flowable;
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
					.compose(RxUtils.FlowableTransformer())
					.subscribe(consumer));
		} catch (Exception e) {
			Timber.e(e);
		}
	}

	public <T> void addDisposable(Completable completable, Action action) {
		mDisposable.add(completable
				.compose(RxUtils.CompletableTransformer())
				.subscribe(action));
	}
	
	protected void addDisposable(Disposable disposable) {
		mDisposable.add(disposable);
	}
	
	@Override
	protected void onCleared() {
		mDisposable.clear();
		super.onCleared();
	}
}
