package com.example.wanandroid.ui.hierarchy.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;
import com.example.wanandroid.data.bean.hierarchy.KnowledgeHierarchyData;
import com.example.wanandroid.rx.BaseObserver;
import com.example.wanandroid.rx.RxUtils;

import java.util.List;

public class KnowledgeHierarchyViewModel extends BaseViewModel {
	
	final MutableLiveData<List<KnowledgeHierarchyData>> mKnowledgeHierarchyListLiveData = new MutableLiveData<>();
	
	@ViewModelInject
	public KnowledgeHierarchyViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	/**
	 * 知识体系
	 *
	 * @param isShowError
	 */
	public void getKnowledgeHierarchyData(boolean isShowError) {
		addDisposable(mDataRepository.getKnowledgeHierarchyData()
				.compose(RxUtils.transObservable())
				.compose(RxUtils.handleResult())
				.subscribeWith(new BaseObserver<List<KnowledgeHierarchyData>>() {
					@Override
					public void onNext(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {
						mKnowledgeHierarchyListLiveData.postValue(knowledgeHierarchyDataList);
					}
					
					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mSnackBarMsgEvent.postValue(new Event<>(App.getContext().getString(R.string.failed_to_obtain_knowledge_data)));
						if (isShowError) {
							mShowErrorEvent.postValue(new Event<>(new Object()));
						}
					}
				}));
	}
}