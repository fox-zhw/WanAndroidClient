package com.example.wanandroid.event;

import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.base.event.Event;

import java.util.HashMap;

/**
 * @author 52512
 * @date 2020/12/22
 */
public class LiveEvent {
	private static final HashMap<Class<?>, MutableLiveData<Event<Object>>> mHashMap = new HashMap<>();
	public static MutableLiveData<?> get(Class<?> clazz) {
		synchronized (mHashMap) {
			if (!mHashMap.containsKey(clazz)) {
				mHashMap.put(clazz, new MutableLiveData<>());
			}
		}
		return mHashMap.get(clazz);
	}
}
