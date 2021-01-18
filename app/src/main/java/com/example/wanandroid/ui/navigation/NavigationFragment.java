package com.example.wanandroid.ui.navigation;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.verticaltablayout.VerticalTabLayout;
import com.example.library.verticaltablayout.adapter.TabAdapter;
import com.example.library.verticaltablayout.widget.ITabView;
import com.example.library.verticaltablayout.widget.TabView;
import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.navigation.NavigationListData;
import com.example.wanandroid.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NavigationFragment extends BaseRootFragment {
	public static final String TAG = "NavigationFragment";
	private NavigationViewModel mViewModel;
	@BindView(R.id.navigation_tab_layout)
	VerticalTabLayout mTabLayout;
	@BindView(R.id.normal_view)
	LinearLayout mNavigationGroup;
	@BindView(R.id.navigation_divider)
	View mDivider;
	@BindView(R.id.navigation_RecyclerView)
	RecyclerView mRecyclerView;
	
	private LinearLayoutManager mManager;
	private boolean needScroll;
	private int index;
	private boolean isClickTab;
	private NavigationAdapter mNavigationAdapter;
	
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
		List<NavigationListData> navigationDataList = new ArrayList<>();
		mNavigationAdapter = new NavigationAdapter(R.layout.item_navigation, navigationDataList);
		mManager = new LinearLayoutManager(_mActivity);
		mRecyclerView.setLayoutManager(mManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mNavigationAdapter);
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
		mViewModel.mNavigationListLiveData.observe(this, new Observer<List<NavigationListData>>() {
			@Override
			public void onChanged(List<NavigationListData> navigationListData) {
				mTabLayout.setTabAdapter(new TabAdapter() {
					@Override
					public int getCount() {
						return navigationListData == null ? 0 : navigationListData.size();
					}
					
					@Override
					public ITabView.TabBadge getBadge(int i) {
						return null;
					}
					
					@Override
					public ITabView.TabIcon getIcon(int i) {
						return null;
					}
					
					@Override
					public ITabView.TabTitle getTitle(int i) {
						return new TabView.TabTitle.Builder()
								.setContent(navigationListData.get(i).getName())
								.setTextColor(ContextCompat.getColor(_mActivity, R.color.shallow_green),
										ContextCompat.getColor(_mActivity, R.color.shallow_grey))
								.build();
					}
					
					@Override
					public int getBackground(int i) {
						return -1;
					}
				});
				if (mViewModel.getCurrentPage() == Constants.TYPE_NAVIGATION) {
					setChildViewVisibility(View.VISIBLE);
				} else {
					setChildViewVisibility(View.INVISIBLE);
				}
				mNavigationAdapter.replaceData(navigationListData);
				leftRightLinkage();
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
		mViewModel.getNavigationListData(true);
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
	}
	
	@Override
	public void reload() {
		super.reload();
		if (mViewModel != null && mNavigationGroup.getVisibility() == View.INVISIBLE) {
			mViewModel.getNavigationListData(false);
		}
	}
	
	private void setChildViewVisibility(int visibility) {
		mNavigationGroup.setVisibility(visibility);
		mTabLayout.setVisibility(visibility);
		mDivider.setVisibility(visibility);
	}
	
	private void leftRightLinkage() {
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
					scrollRecyclerView();
				}
				rightLinkageLeft(newState);
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (needScroll) {
					scrollRecyclerView();
				}
			}
		});
		
		mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabView tabView, int i) {
				isClickTab = true;
				selectTag(i);
			}
			
			@Override
			public void onTabReselected(TabView tabView, int i) {
			}
		});
	}
	
	private void scrollRecyclerView() {
		needScroll = false;
		int indexDistance = index - mManager.findFirstVisibleItemPosition();
		if (indexDistance >= 0 && indexDistance < mRecyclerView.getChildCount()) {
			int top = mRecyclerView.getChildAt(indexDistance).getTop();
			mRecyclerView.smoothScrollBy(0, top);
		}
	}
	
	private void selectTag(int i) {
		index = i;
		mRecyclerView.stopScroll();
		smoothScrollToPosition(i);
	}
	
	private void smoothScrollToPosition(int currentPosition) {
		int firstPosition = mManager.findFirstVisibleItemPosition();
		int lastPosition = mManager.findLastVisibleItemPosition();
		if (currentPosition <= firstPosition) {
			mRecyclerView.smoothScrollToPosition(currentPosition);
		} else if (currentPosition <= lastPosition) {
			int top = mRecyclerView.getChildAt(currentPosition - firstPosition).getTop();
			mRecyclerView.smoothScrollBy(0, top);
		} else {
			mRecyclerView.smoothScrollToPosition(currentPosition);
			needScroll = true;
		}
	}
	
	private void rightLinkageLeft(int newState) {
		if (newState == RecyclerView.SCROLL_STATE_IDLE) {
			if (isClickTab) {
				isClickTab = false;
				return;
			}
			int firstPosition = mManager.findFirstVisibleItemPosition();
			if (index != firstPosition) {
				index = firstPosition;
				setChecked(index);
			}
		}
	}
	
	private void setChecked(int position) {
		if (isClickTab) {
			isClickTab = false;
		} else {
			if (mTabLayout == null) {
				return;
			}
			mTabLayout.setTabSelected(index);
		}
		index = position;
	}
}