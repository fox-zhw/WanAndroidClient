package com.example.wanandroid.ui.mainpager;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.BaseResponse;
import com.example.wanandroid.data.bean.main.banner.BannerData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.data.bean.main.login.LoginData;
import com.example.wanandroid.event.Event;
import com.example.wanandroid.util.LogHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainPagerViewModel extends BaseViewModel {
	// TODO: Implement the ViewModel
	final DataRepository mDataRepository;
	// 自动登录成功/失败
	final MutableLiveData<Event<Boolean>> mAutoLoginLiveEvent = new MutableLiveData<>();
	// 文章列表
	final MutableLiveData<FeedArticleListData> mArticleListLiveData = new MutableLiveData<>();
	// 横幅数据
	final MutableLiveData<List<BannerData>> mBannerDataLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public MainPagerViewModel(DataRepository dataRepository) {
		mDataRepository = dataRepository;
		
		Observable<BaseResponse<FeedArticleListData>> mArticleObservable = mDataRepository.getFeedArticleList(0);
		Disposable subscribe = mArticleObservable
				.subscribeOn(Schedulers.io())
				.subscribe(new Consumer<BaseResponse<FeedArticleListData>>() {
					@Override
					public void accept(BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) throws Exception {
						LogHelper.e("网络请求成功了");
					}
				});
	}
	
	public void loadMainPagerData() {
		Observable<BaseResponse<LoginData>> mLoginObservable = mDataRepository.getLoginData(getLoginAccount(), getLoginPassword());
		Observable<BaseResponse<List<BannerData>>> mBannerObservable = mDataRepository.getBannerData();
		Observable<BaseResponse<FeedArticleListData>> mArticleObservable = mDataRepository.getFeedArticleList(0);
	}
	
	public String getLoginPassword() {
		return mDataRepository.getLoginPassword();
	}
	
	public String getLoginAccount() {
		return mDataRepository.getLoginAccount();
	}
	
	void autoRefresh(boolean b) {
	
	}
	
	public void loadMore() {
	
	}
}