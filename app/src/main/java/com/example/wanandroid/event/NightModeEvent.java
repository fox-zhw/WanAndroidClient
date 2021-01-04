package com.example.wanandroid.event;

import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.base.event.Event;

/**
 * @author 52512
 * @date 2020/12/22
 */
public class NightModeEvent extends MutableLiveData<Event<Object>> {
	private static final NightModeEvent ourInstance = new NightModeEvent();
	
	public static NightModeEvent getInstance() {
		return ourInstance;
	}
	
	private NightModeEvent() {
	}
	
	
}
