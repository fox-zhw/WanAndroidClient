package com.example.wanandroid.base.event;

import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * liveData 封装为 Event
 * 粘性事件 只响应一次
 * todo  多个观察者时，只有一个观察者响应且只响应一次
 *
 * @author zhaohw
 * @date 2021/1/4
 */
public class SingleLiveEvent<T> extends LiveData<Event<T>> {
	
	public void post(T t) {
		postValue(new Event<>(t));
	}
	
	@MainThread
	public void set(T t) {
		setValue(new Event<>(t));
	}
	
	public void clear() {
		if (isMainThread()) {
			setValue(null);
		} else {
			postValue(null);
		}
	}
	
	@Override
	public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Event<T>> observer) {
		super.observe(owner, observer);
	}
	
	public void observeEvent(@NonNull LifecycleOwner owner, @NonNull Event.EventObserver<T> observer) {
		super.observe(owner, observer);
	}
	
	private boolean isMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}
}
