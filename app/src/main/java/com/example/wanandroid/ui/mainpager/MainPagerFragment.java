package com.example.wanandroid.ui.mainpager;

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

public class MainPagerFragment extends BaseRootFragment {
	
	private MainPagerViewModel mViewModel;
	
	public static MainPagerFragment newInstance() {
		return new MainPagerFragment();
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_pager_fragment, container, false);
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.main_pager_fragment;
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(MainPagerViewModel.class);
	}
}