package com.example.wanandroid.ui.wx;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.tablayout.SlidingTabLayout;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class WxArticleFragment extends BaseRootFragment {
	
//	@BindView(R.id.wx_detail_tab_layout)
//	SlidingTabLayout mTabLayout;
	@BindView(R.id.wx_detail_viewpager)
	ViewPager mViewPager;
	
	private WxArticleViewModel mViewModel;
	
	public static WxArticleFragment newInstance() {
		return new WxArticleFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.wx_article_fragment;
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(WxArticleViewModel.class);
	}
	
}