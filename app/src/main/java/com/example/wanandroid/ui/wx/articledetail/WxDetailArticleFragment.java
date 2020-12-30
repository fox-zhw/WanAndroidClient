package com.example.wanandroid.ui.wx.articledetail;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.base.fragment.BaseRootFragment;

public class WxDetailArticleFragment extends BaseRootFragment {
	
	private WxDetailArticleViewModel mViewModel;
	
	public static WxDetailArticleFragment newInstance() {
		return new WxDetailArticleFragment();
	}
	
	public static BaseFragment getInstance(int id, String name) {
		return new WxDetailArticleFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.wx_detail_article_fragment;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(WxDetailArticleViewModel.class);
		// TODO: Use the ViewModel
	}
	
}