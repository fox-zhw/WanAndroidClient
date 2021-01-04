package com.example.wanandroid.event;

import com.example.wanandroid.base.event.SingleLiveEvent;

/**
 * @author 52512
 * @date 2021/1/4
 */
public class LoginLiveEvent extends SingleLiveEvent<Boolean> {
	private static final LoginLiveEvent ourInstance = new LoginLiveEvent();
	
	public static LoginLiveEvent getInstance() {
		return ourInstance;
	}
	
	private LoginLiveEvent() {
	}
}
