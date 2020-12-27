package com.example.wanandroid;

import android.app.Application;
import android.content.Context;

import com.example.wanandroid.util.LogHelper;

import dagger.hilt.android.HiltAndroidApp;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * @author 52512
 * @date 2020/12/13
 */
@HiltAndroidApp
public class App extends Application {
	
	public static Context context;
	public static boolean isFirstRun = true;
	
	public static Context getContext() {
		return context;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		LogHelper.init();
		Fragmentation.builder()
				.stackViewMode(Fragmentation.BUBBLE)
				.debug(true)
				.handleException(new ExceptionHandler() {
					@Override
					public void onException(Exception e) {
						LogHelper.e(e);
					}
				})
				.install();
	}
}
