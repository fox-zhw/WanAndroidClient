package com.example.wanandroid.data.local.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroid.data.bean.HistoryData;

import java.util.List;

/**
 * @author 52512
 * @date 2020/12/28
 */
@Dao
public interface HistoryDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(HistoryData task);
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(List<HistoryData> task);
	
	@Query("SELECT * FROM HistoryData")
	List<HistoryData> loadAll();
	
	@Query("DELETE FROM HistoryData")
	void deleteAll();
}
