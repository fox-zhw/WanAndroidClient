package com.example.wanandroid.ui.project;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.project.ProjectClassifyData;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

import java.util.List;

public class ProjectViewModel extends BaseViewModel {
	
	final MutableLiveData<List<ProjectClassifyData>> mProjectClassifyLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public ProjectViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	/**
	 * 项目分类数据
	 */
	public void getProjectClassifyData() {
		addDisposable(mDataRepository.getProjectClassifyData()
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<List<ProjectClassifyData>>() {
					@Override
					public void onNext(List<ProjectClassifyData> projectClassifyDataList) {
						mProjectClassifyLiveData.postValue(projectClassifyDataList);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_project_classify_data)));
						mShowErrorEvent.postValue(new Event<>(new Object()));
					}
				}));
	}
}