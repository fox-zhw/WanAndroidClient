package com.example.wanandroid.ui.mainpager;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.bean.main.banner.BannerData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.event.Event;

import java.util.List;

public class MainPagerViewModel extends BaseViewModel {
	// TODO: Implement the ViewModel
	// 自动登录成功/失败
	final MutableLiveData<Event<Boolean>> mAutoLoginLiveEvent = new MutableLiveData<>();
	// 文章列表
	final MutableLiveData<FeedArticleListData> mArticleListLiveData = new MutableLiveData<>();
	// 横幅数据
	final MutableLiveData<List<BannerData>> mBannerDataLiveData = new MutableLiveData<>();
	
	
	
	void loadMainPagerData() {
	
	}
	
	void autoRefresh() {
	
	}
}