package com.example.wanandroid.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wanandroid.data.bean.HistoryData;

/**
 * @author 52512
 * @date 2020/12/28
 */
@Database(entities = {HistoryData.class}, version = 1, exportSchema = false)
public abstract class HistoryDatabase extends RoomDatabase {
	
	public abstract HistoryDao historyDao();
	
}
