package com.example.wanandroid.ui.project;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.project.ProjectListData;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

public class ProjectListViewModel extends BaseViewModel {
	
	final MutableLiveData<ProjectListData> mProjectListLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public ProjectListViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	/**
	 * 获取项目列表数据
	 *
	 * @param page
	 * @param cid
	 * @param isShowError
	 */
	public void getProjectListData(int page, int cid, boolean isShowError) {
		addDisposable(mDataRepository.getProjectListData(page, cid)
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<ProjectListData>() {
					@Override
					public void onNext(ProjectListData projectListData) {
						mProjectListLiveData.postValue(projectListData);
					}
					
					@Override
					public void onError(Throwable e) {
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_project_list)));
						if (isShowError) {
							mShowErrorEvent.postValue(new Event<>(new Object()));
						}
					}
				}));
	}
}