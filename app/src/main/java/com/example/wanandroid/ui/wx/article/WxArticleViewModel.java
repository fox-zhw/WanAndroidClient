package com.example.wanandroid.ui.wx.article;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.wx.WxAuthor;
import com.example.wanandroid.event.Event;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

import java.util.List;

public class WxArticleViewModel extends BaseViewModel {
	public static final String TAG = "WxArticleViewModel";
	
	final DataRepository mDataRepository;
	
	final MutableLiveData<List<WxAuthor>> mWxAuthorListLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public WxArticleViewModel(DataRepository dataRepository) {
		mDataRepository = dataRepository;
	}
	
	/**
	 * 请求微信公众号作者数据
	 */
	public void requestWxAuthorListData() {
		addDisposable(mDataRepository.getWxAuthorListData()
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<List<WxAuthor>>() {
					@Override
					public void onNext(List<WxAuthor> wxAuthors) {
						mWxAuthorListLiveData.postValue(wxAuthors);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.fail_to_obtain_wx_author)));
						mShowErrorEvent.postValue(new Event<>(new Object()));
					}
				}));
	}
	
	public int getCurrentPage() {
		return mDataRepository.getCurrentPage();
	}
}