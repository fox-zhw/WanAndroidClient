package com.example.wanandroid.rx;

import io.reactivex.observers.ResourceObserver;

/**
 * @author zhaohw
 * @date 2020/12/30
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {
	
	@Override
	public void onError(Throwable e) {
	
	}
	
	@Override
	public void onComplete() {
	
	}
}
