package com.example.wanandroid.ui.wx;

import androidx.lifecycle.ViewModelProviders;

import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;

import butterknife.BindView;

public class WxArticleFragment extends BaseFragment {
	
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