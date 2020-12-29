package com.example.wanandroid.di;

import android.content.Context;

import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.local.db.DbHelper;
import com.example.wanandroid.data.local.db.DbHelperImpl;
import com.example.wanandroid.data.remote.HttpHelper;
import com.example.wanandroid.data.remote.HttpHelperImpl;
import com.example.wanandroid.data.remote.api.GeeksApis;
import com.example.wanandroid.data.sp.PreferenceHelper;
import com.example.wanandroid.data.sp.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * @author 52512
 * @date 2020/12/13
 */
@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {
	
	
	@Provides
	@Singleton
	DbHelper providerDbHelper() {
		return new DbHelperImpl();
	}
	
	@Provides
	@Singleton
	HttpHelper providerHttpHelper(GeeksApis geeksApis) {
		return new HttpHelperImpl(geeksApis);
	}
	
	@Provides
	@Singleton
	PreferenceHelper providerPreferenceHelper(@ApplicationContext Context context) {
		return new PreferenceHelperImpl(context);
	}
	
	@Provides
	@Singleton
	DataRepository provideDataRepository(HttpHelper httpHelper,
	                                  DbHelper dbhelper,
	                                  PreferenceHelper preferencesHelper) {
		return new DataRepository(httpHelper, dbhelper, preferencesHelper);
	}
}
