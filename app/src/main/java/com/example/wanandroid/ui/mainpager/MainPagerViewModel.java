package com.example.wanandroid.ui.mainpager;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.BaseResponse;
import com.example.wanandroid.data.bean.main.banner.BannerData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.data.bean.main.login.LoginData;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;
import com.example.wanandroid.util.CommonUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class MainPagerViewModel extends BaseViewModel {
	public static final String TAG = "MainPagerViewModel";
	// 文章列表
	final MutableLiveData<FeedArticleListData> mArticleListLiveData = new MutableLiveData<>();
	// 横幅数据
	final MutableLiveData<List<BannerData>> mBannerDataLiveData = new MutableLiveData<>();
	final MutableLiveData<Event<Boolean>> mRefreshLiveEvent = new MutableLiveData<>();
	
	private boolean isRefresh = true;
	private int mCurrentPage;
	
	@ViewModelInject
	public MainPagerViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	public void loadMainPagerData() {
		Observable<BaseResponse<List<BannerData>>> mBannerObservable = mDataRepository.getBannerData();
		Observable<BaseResponse<FeedArticleListData>> mArticleObservable = mDataRepository.getFeedArticleList(0);
		addDisposable(Observable.zip(mBannerObservable, mArticleObservable, this::createResponseMap)
				.compose(RxUtils.transObservable())
				.subscribeWith(new BaseObserver<HashMap<String, Object>>() {
					@Override
					public void onNext(HashMap<String, Object> map) {
						BaseResponse<List<BannerData>> bannerResponse = CommonUtils.cast(map.get(Constants.BANNER_DATA));
						if (bannerResponse != null) {
							mBannerDataLiveData.postValue(bannerResponse.getData());
						}
						BaseResponse<FeedArticleListData> feedArticleListResponse = CommonUtils.cast(map.get(Constants.ARTICLE_DATA));
						if (feedArticleListResponse != null) {
							mArticleListLiveData.postValue(feedArticleListResponse.getData());
							mRefreshLiveEvent.postValue(new Event<>(isRefresh));
						}
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
					}
				}));
	}
	
	public void autoRefresh(boolean isShowError) {
		isRefresh = true;
		mCurrentPage = 0;
		getBannerData(isShowError);
		getFeedArticleList(isShowError);
	}
	
	public void loadMore() {
		isRefresh = false;
		mCurrentPage++;
		loadMoreData();
	}
	
	public void getBannerData(boolean isShowError) {
		addDisposable(mDataRepository.getBannerData()
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<List<BannerData>>() {
					@Override
					public void onNext(List<BannerData> bannerDataList) {
						mBannerDataLiveData.postValue(bannerDataList);
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_banner_data)));
						if (isShowError) {
							mShowErrorEvent.postValue(new Event<>(new Object()));
						}
					}
				}));
	}
	
	public void getFeedArticleList(boolean isShowError) {
		addDisposable(mDataRepository.getFeedArticleList(mCurrentPage)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<FeedArticleListData>() {
					@Override
					public void onNext(FeedArticleListData feedArticleListData) {
						mArticleListLiveData.postValue(feedArticleListData);
						mRefreshLiveEvent.postValue(new Event<>(isRefresh));
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_article_list)));
						if (isShowError) {
							mShowErrorEvent.postValue(new Event<>(new Object()));
						}
					}
				}));
	}
	
	public void loadMoreData() {
		addDisposable(mDataRepository.getFeedArticleList(mCurrentPage)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<FeedArticleListData>() {
					@Override
					public void onNext(FeedArticleListData feedArticleListData) {
						mArticleListLiveData.postValue(feedArticleListData);
						mRefreshLiveEvent.postValue(new Event<>(isRefresh));
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_article_list)));
					}
				}));
	}
	
	@NonNull
	private HashMap<String, Object> createResponseMap(
	                                                  BaseResponse<List<BannerData>> bannerResponse,
	                                                  BaseResponse<FeedArticleListData> feedArticleListResponse) {
		HashMap<String, Object> map = new HashMap<>(2);
		map.put(Constants.BANNER_DATA, bannerResponse);
		map.put(Constants.ARTICLE_DATA, feedArticleListResponse);
		return map;
	}
	
	public boolean isRefresh() {
		return isRefresh;
	}
}