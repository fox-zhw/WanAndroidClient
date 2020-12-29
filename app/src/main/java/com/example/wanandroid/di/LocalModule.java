package com.example.wanandroid.di;

import android.content.Context;

import androidx.room.Room;

import com.example.wanandroid.data.local.db.DbHelper;
import com.example.wanandroid.data.local.db.DbHelperImpl;
import com.example.wanandroid.data.local.db.HistoryDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * @author 52512
 * @date 2020/12/29
 */
@Module
@InstallIn(ApplicationComponent.class)
public class LocalModule {
	
	@Singleton
	@Provides
	HistoryDatabase provideHistoryDatabase(@ApplicationContext Context context) {
		return Room.databaseBuilder(context.getApplicationContext(),
				HistoryDatabase.class, "HistoryData.db")
				.build();
	}
}
