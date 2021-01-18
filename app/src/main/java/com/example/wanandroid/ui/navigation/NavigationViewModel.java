package com.example.wanandroid.ui.navigation;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.navigation.NavigationListData;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

import java.util.List;

public class NavigationViewModel extends BaseViewModel {
	public static final String TAG = "NavigationViewModel";
	
	final MutableLiveData<List<NavigationListData>> mNavigationListLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public NavigationViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	public void getNavigationListData(boolean isShowError) {
		addDisposable(mDataRepository.getNavigationListData()
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<List<NavigationListData>>() {
					@Override
					public void onNext(List<NavigationListData> navigationDataList) {
						mNavigationListLiveData.postValue(navigationDataList);
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_navigation_list)));
						if (isShowError) {
							mShowErrorEvent.postValue(new Event<>(new Object()));
						}
					}
				}));
	}
}