package com.example.wanandroid.ui.mainpager;

import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class MainPagerFragment extends BaseFragment {
	@BindView(R.id.normal_view)
	SmartRefreshLayout mRefreshLayout;
	@BindView(R.id.main_pager_recycler_view)
	RecyclerView mRecyclerView;
	private MainPagerViewModel mViewModel;
	
	public static MainPagerFragment newInstance() {
		return new MainPagerFragment();
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