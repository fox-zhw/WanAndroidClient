package com.example.wanandroid.ui.main.home;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;

public class HomeViewModel extends BaseViewModel {
	// TODO: Implement the ViewModel
	
	@ViewModelInject
	public HomeViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	
	public void setCurrentPage(int curfragment) {
		mDataRepository.setCurrentPage(curfragment);
	}
}