package com.example.wanandroid.ui.wx;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;

public class WxDetailArticleFragment extends Fragment {
	
	private WxDetailArticleViewModel mViewModel;
	
	public static WxDetailArticleFragment newInstance() {
		return new WxDetailArticleFragment();
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.wx_detail_article_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(WxDetailArticleViewModel.class);
		// TODO: Use the ViewModel
	}
	
}