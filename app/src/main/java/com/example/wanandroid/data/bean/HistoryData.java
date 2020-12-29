package com.example.wanandroid.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author 52512
 * @date 2020/12/28
 */
@Entity(tableName = "HistoryData")
public class HistoryData {
	
	@PrimaryKey(autoGenerate = true)
	private long id;
	
	private long date;
	
	private String data;
	
	public HistoryData() {
	
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}
