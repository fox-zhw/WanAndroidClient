package com.example.wanandroid.ui.wx.articledetail;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

public class WxDetailArticleViewModel extends BaseViewModel {
	public static final String TAG = "WxDetailArticleViewMode";
	
	
	final MutableLiveData<FeedArticleListData> mFeedArticleListDataLiveData = new MutableLiveData<>();
	final MutableLiveData<FeedArticleListData> mFeedArticleListDataSearchLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public WxDetailArticleViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	public void getWxDetailData(int id, int page, boolean isShowError) {
		addDisposable(mDataRepository.getWxSumData(id, page)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<FeedArticleListData>() {
					@Override
					public void onNext(FeedArticleListData feedArticleListData) {
						mFeedArticleListDataLiveData.postValue(feedArticleListData);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_wx_data)));
						
						if (isShowError) {
							mShowErrorEvent.postValue(new Event<>(new Object()));
						}
					}
				}));
	}
	
	public void getWxSearchSumData(int id, int currentPage, String searchString) {
		addDisposable(mDataRepository.getWxSearchSumData(id, currentPage, searchString)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<FeedArticleListData>() {
					@Override
					public void onNext(FeedArticleListData feedArticleListData) {
						mFeedArticleListDataSearchLiveData.postValue(feedArticleListData);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_search_data_list)));
						mShowErrorEvent.postValue(new Event<>(new Object()));
					}
				}));
	}
	
	public void addCollectArticle(int position, FeedArticleData feedArticleData) {
		addDisposable(mDataRepository.addCollectArticle(feedArticleData.getId())
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<FeedArticleListData>() {
					@Override
					public void onNext(FeedArticleListData feedArticleListData) {
						feedArticleData.setCollect(true);
//						mView.showCollectArticleData(position, feedArticleData, feedArticleListData);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.collect_fail)));
						mShowErrorEvent.postValue(new Event<>(new Object()));
					}
				}));
	}
	
	public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
		addDisposable(mDataRepository.cancelCollectArticle(feedArticleData.getId())
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<FeedArticleListData>() {
					@Override
					public void onNext(FeedArticleListData feedArticleListData) {
						feedArticleData.setCollect(false);
//						mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.cancel_collect_fail)));
						mShowErrorEvent.postValue(new Event<>(new Object()));
					}
				}));
	}
}