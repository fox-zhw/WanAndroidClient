package com.example.wanandroid.ui.navigation;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.wanandroid.base.viewmodel.BaseViewModel;
import com.example.wanandroid.data.DataRepository;

public class NavigationViewModel extends BaseViewModel {
	
	@ViewModelInject
	public NavigationViewModel(DataRepository dataRepository) {
		super(dataRepository);
	}
	// TODO: Implement the ViewModel
}