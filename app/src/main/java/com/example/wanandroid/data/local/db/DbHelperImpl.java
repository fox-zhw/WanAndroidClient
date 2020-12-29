package com.example.wanandroid.data.local.db;

import androidx.annotation.NonNull;

import com.example.wanandroid.data.bean.HistoryData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 52512
 * @date 2020/12/28
 */
public class DbHelperImpl implements DbHelper {
	private static final int HISTORY_LIST_SIZE = 10;
	@Inject
	HistoryDao mHistoryDao;
	
	@Inject
	public DbHelperImpl() {
	}
	
	@Override
	public List<HistoryData> addHistoryData(String data) {
		List<HistoryData> historyDataList = getHistoryDataList();
		HistoryData historyData = createHistoryData(data);
		if (historyDataForward(historyDataList, historyData)) {
			return historyDataList;
		}
		
		if (historyDataList.size() < HISTORY_LIST_SIZE) {
			mHistoryDao.insert(historyData);
		} else {
			historyDataList.remove(0);
			historyDataList.add(historyData);
			mHistoryDao.deleteAll();
			mHistoryDao.insert(historyDataList);
		}
		return historyDataList;
	}
	
	@Override
	public void clearHistoryData() {
		mHistoryDao.deleteAll();
	}
	
	@Override
	public List<HistoryData> loadAllHistoryData() {
		return getHistoryDataList();
	}
	
	private List<HistoryData> getHistoryDataList() {
		List<HistoryData> historyData = mHistoryDao.loadAll();
		if (historyData == null) {
			historyData = new ArrayList<>();
		}
		return historyData;
	}
	
	private HistoryData createHistoryData(String data) {
		HistoryData historyData = new HistoryData();
		historyData.setDate(System.currentTimeMillis());
		historyData.setData(data);
		return historyData;
	}
	
	/**
	 * 历史数据前移
	 *
	 * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
	 */
	private boolean historyDataForward(List<HistoryData> historyDataList, HistoryData historyData) {
		//重复搜索时进行历史记录前移
		Iterator<HistoryData> iterator = historyDataList.iterator();
		//不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
		while (iterator.hasNext()) {
			HistoryData historyData1 = iterator.next();
			if (historyData1.getData().equals(historyData.getData())) {
				historyDataList.remove(historyData1);
				historyDataList.add(historyData);
				mHistoryDao.deleteAll();
				mHistoryDao.insert(historyDataList);
				return true;
			}
		}
		return false;
	}
}
