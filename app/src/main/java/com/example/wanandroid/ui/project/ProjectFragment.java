package com.example.wanandroid.ui.project;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.library.tablayout.SlidingTabLayout;
import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.project.ProjectClassifyData;
import com.example.wanandroid.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProjectFragment extends BaseRootFragment {
	public static final String TAG = "ProjectFragment";
	@BindView(R.id.project_tab_layout)
	SlidingTabLayout mTabLayout;
	@BindView(R.id.project_divider)
	View mDivider;
	@BindView(R.id.project_viewpager)
	ViewPager mViewPager;
	private ProjectViewModel mViewModel;
	
	private List<ProjectClassifyData> mData;
	private List<BaseFragment> mFragments = new ArrayList<>();
	private int currentPage;
	
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
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
		mViewModel.mProjectClassifyLiveData.observe(this, new Observer<List<ProjectClassifyData>>() {
			@Override
			public void onChanged(List<ProjectClassifyData> projectClassifyData) {
				if (mViewModel.getCurrentPage() == Constants.TYPE_PROJECT) {
					setChildViewVisibility(View.VISIBLE);
				} else {
					setChildViewVisibility(View.INVISIBLE);
				}
				mData = projectClassifyData;
				initViewPagerAndTabLayout();
				showNormal();
			}
		});
		
		mViewModel.mShowErrorEvent.observe(this, new Event.EventObserver<Object>() {
			@Override
			public void onEventChanged(@NonNull Object o) {
				showError();
			}
		});
		
		mViewModel.mSnackBarMsgEvent.observe(this, new Event.EventObserver<String>() {
			@Override
			public void onEventChanged(@NonNull String s) {
				showSnackBar(s);
			}
		});
		
		mViewModel.mToastMsgEvent.observe(this, new Event.EventObserver<String>() {
			@Override
			public void onEventChanged(@NonNull String s) {
				showToast(s);
			}
		});
		
		mViewModel.getProjectClassifyData();
		currentPage = mViewModel.getCurrentPage();
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
	}
	
	@Override
	public void showError() {
		setChildViewVisibility(View.INVISIBLE);
		super.showError();
	}
	
	@Override
	public void reload() {
		if (mViewModel != null && mTabLayout.getVisibility() == View.INVISIBLE) {
			mViewModel.getProjectClassifyData();
		}
	}
	
	private void initViewPagerAndTabLayout() {
		for (ProjectClassifyData data : mData) {
			ProjectListFragment projectListFragment = ProjectListFragment.getInstance(data.getId(), null);
			mFragments.add(projectListFragment);
		}
		mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragments.get(position);
			}
			
			@Override
			public int getCount() {
				return mData == null? 0 : mData.size();
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				return mData.get(position).getName();
			}
		});
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			
			}
			
			@Override
			public void onPageSelected(int position) {
				currentPage = position;
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			
			}
		});
		mTabLayout.setViewPager(mViewPager);
		mViewPager.setCurrentItem(Constants.TAB_ONE);
	}
	
	private void setChildViewVisibility(int visibility) {
		mTabLayout.setVisibility(visibility);
		mDivider.setVisibility(visibility);
		mViewPager.setVisibility(visibility);
	}
}