package com.example.wanandroid.ui.hierarchy.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.hierarchy.KnowledgeHierarchyData;
import com.example.wanandroid.ui.hierarchy.KnowledgeHierarchyDetailActivity;
import com.example.wanandroid.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class KnowledgeHierarchyFragment extends BaseRootFragment {
	public static final String TAG = "KnowledgeHierarchyFragm";
	@BindView(R.id.normal_view)
	SmartRefreshLayout mRefreshLayout;
	@BindView(R.id.knowledge_hierarchy_recycler_view)
	RecyclerView mRecyclerView;
	private KnowledgeHierarchyViewModel mViewModel;
	
	private List<KnowledgeHierarchyData> mKnowledgeHierarchyDataList;
	private KnowledgeHierarchyAdapter mAdapter;
	private boolean isRefresh;
	
	public static KnowledgeHierarchyFragment newInstance() {
		return new KnowledgeHierarchyFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.knowledge_hierarchy_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		mKnowledgeHierarchyDataList = new ArrayList<>();
		mAdapter = new KnowledgeHierarchyAdapter(R.layout.item_knowledge_hierarchy, mKnowledgeHierarchyDataList);
		mAdapter.setOnItemClickListener((adapter, view, position) -> startDetailPager(view, position));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(KnowledgeHierarchyViewModel.class);
		setRefresh();
		mViewModel.mKnowledgeHierarchyListLiveData.observe(this, new Observer<List<KnowledgeHierarchyData>>() {
			@Override
			public void onChanged(List<KnowledgeHierarchyData> knowledgeHierarchyData) {
				if (mViewModel.getCurrentPage() == 1) {
					mRecyclerView.setVisibility(View.VISIBLE);
				} else {
					mRecyclerView.setVisibility(View.INVISIBLE);
				}
				if (mAdapter.getData().size() < knowledgeHierarchyData.size()) {
					mKnowledgeHierarchyDataList = knowledgeHierarchyData;
					mAdapter.replaceData(mKnowledgeHierarchyDataList);
				} else {
					if (!isRefresh) {
						CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
					}
				}
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
		
		
		mViewModel.getKnowledgeHierarchyData(true);
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
	}
	
	@Override
	public void showError() {
		mRecyclerView.setVisibility(View.INVISIBLE);
		super.showError();
	}
	
	@Override
	public void reload() {
		if (mViewModel != null && mRecyclerView.getVisibility() == View.INVISIBLE) {
			mViewModel.getKnowledgeHierarchyData(false);
		}
	}
	
	private void startDetailPager(View view, int position) {
		if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
			return;
		}
		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
		Intent intent = new Intent(_mActivity, KnowledgeHierarchyDetailActivity.class);
		intent.putExtra(Constants.ARG_PARAM1, mAdapter.getData().get(position));
		if (modelFiltering()) {
			startActivity(intent, options.toBundle());
		} else {
			startActivity(intent);
		}
	}
	
	/**
	 * 机型适配
	 *
	 * @return 返回true表示非三星机型且Android 6.0以上
	 */
	private boolean modelFiltering() {
		return !Build.MANUFACTURER.contains(Constants.SAMSUNG) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
	}
	
	private void setRefresh() {
		mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
		mRefreshLayout.setOnRefreshListener(refreshLayout -> {
			isRefresh = true;
			mViewModel.getKnowledgeHierarchyData(false);
			refreshLayout.finishRefresh(1000);
		});
		mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
			isRefresh = false;
			mViewModel.getKnowledgeHierarchyData(false);
			refreshLayout.finishLoadMore(1000);
		});
	}
}