package com.example.wanandroid.ui.navigation;

import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NavigationFragment extends BaseRootFragment {
	
	private NavigationViewModel mViewModel;
	
	public static NavigationFragment newInstance() {
		return new NavigationFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.navigation_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
	}
	
}