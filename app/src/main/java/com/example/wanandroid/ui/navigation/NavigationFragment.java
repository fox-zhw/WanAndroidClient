package com.example.wanandroid.ui.navigation;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;

public class NavigationFragment extends BaseRootFragment {
	
	private NavigationViewModel mViewModel;
	
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