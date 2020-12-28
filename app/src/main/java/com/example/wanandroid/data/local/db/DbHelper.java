package com.example.wanandroid.data.local.db;

import com.example.wanandroid.data.bean.HistoryData;

import java.util.List;

/**
 * @author 52512
 * @date 2020/12/28
 */
public interface DbHelper {
	/**
	 * 增加历史数据
	 *
	 * @param data  added string
	 * @return  List<HistoryData>
	 */
	List<HistoryData> addHistoryData(String data);
	
	/**
	 * Clear search history data
	 */
	void clearHistoryData();
	
	/**
	 * Load all history data
	 *
	 * @return List<HistoryData>
	 */
	List<HistoryData> loadAllHistoryData();
}
