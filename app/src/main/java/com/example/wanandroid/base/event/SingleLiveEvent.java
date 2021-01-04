package com.example.wanandroid.base.event;

import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

/**
 * liveData 封装为 Event
 * 粘性事件 只响应一次
 * refactor  每个观察者相应一次   还是只有一个观察值且只响应一次 需要验证
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
	
	private boolean isMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}
}
