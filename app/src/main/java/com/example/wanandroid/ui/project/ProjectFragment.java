package com.example.wanandroid.ui.project;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.library.tablayout.SlidingTabLayout;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;

import butterknife.BindView;

public class ProjectFragment extends BaseRootFragment {
	public static final String TAG = "ProjectFragment";
	@BindView(R.id.project_tab_layout)
	SlidingTabLayout mTabLayout;
	@BindView(R.id.project_divider)
	View mDivider;
	@BindView(R.id.project_viewpager)
	ViewPager mViewPager;
	private ProjectViewModel mViewModel;
	
	public static ProjectFragment newInstance() {
		return new ProjectFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.project_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
	}
	
}