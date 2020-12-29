package com.example.wanandroid.data.sp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.wanandroid.App;
import com.example.wanandroid.Constants;

import javax.inject.Inject;

/**
 * @author 52512
 * @date 2020/12/13
 */
public class PreferenceHelperImpl implements PreferenceHelper {
	
	private final SharedPreferences mPreferences;
	
	@Inject
	public PreferenceHelperImpl(@NonNull Context context) {
		mPreferences = context.getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
	}
	
	@Override
	public void setLoginAccount(String account) {
		mPreferences.edit().putString(Constants.ACCOUNT, account).apply();
	}
	
	@Override
	public void setLoginPassword(String password) {
		mPreferences.edit().putString(Constants.PASSWORD, password).apply();
	}
	
	@Override
	public String getLoginAccount() {
		return mPreferences.getString(Constants.ACCOUNT, "");
	}
	
	@Override
	public String getLoginPassword() {
		return mPreferences.getString(Constants.PASSWORD, "");
	}
	
	@Override
	public void setLoginStatus(boolean isLogin) {
		mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
	}
	
	@Override
	public boolean getLoginStatus() {
		return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
	}
	
	@Override
	public void setCookie(String domain, String cookie) {
		mPreferences.edit().putString(domain, cookie).apply();
	}
	
	@Override
	public String getCookie(String domain) {
		return mPreferences.getString(Constants.COOKIE, "");
	}
	
	@Override
	public void setCurrentPage(int position) {
		mPreferences.edit().putInt(Constants.CURRENT_PAGE, position).apply();
	}
	
	@Override
	public int getCurrentPage() {
		return mPreferences.getInt(Constants.CURRENT_PAGE, 0);
	}
	
	@Override
	public void setProjectCurrentPage(int position) {
		mPreferences.edit().putInt(Constants.PROJECT_CURRENT_PAGE, position).apply();
	}
	
	@Override
	public int getProjectCurrentPage() {
		return mPreferences.getInt(Constants.PROJECT_CURRENT_PAGE, 0);
	}
	
	@Override
	public boolean getAutoCacheState() {
		return mPreferences.getBoolean(Constants.AUTO_CACHE_STATE, true);
	}
	
	@Override
	public boolean getNoImageState() {
		return mPreferences.getBoolean(Constants.NO_IMAGE_STATE, false);
	}
	
	@Override
	public boolean getNightModeState() {
		return mPreferences.getBoolean(Constants.NIGHT_MODE_STATE, false);
	}
	
	@Override
	public void setNightModeState(boolean b) {
		mPreferences.edit().putBoolean(Constants.NIGHT_MODE_STATE, b).apply();
	}
	
	@Override
	public void setNoImageState(boolean b) {
		mPreferences.edit().putBoolean(Constants.NO_IMAGE_STATE, b).apply();
	}
	
	@Override
	public void setAutoCacheState(boolean b) {
		mPreferences.edit().putBoolean(Constants.AUTO_CACHE_STATE, b).apply();
	}
	
	
	
	
}
