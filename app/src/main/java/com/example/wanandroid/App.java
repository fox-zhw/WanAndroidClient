package com.example.wanandroid;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

/**
 * @author 52512
 * @date 2020/12/13
 */
@HiltAndroidApp
public class App extends Application {
	
	public static Context context;
	
	public static Context getContext() {
		return context;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
	}
}
