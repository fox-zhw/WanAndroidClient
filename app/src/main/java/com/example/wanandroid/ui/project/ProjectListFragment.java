package com.example.wanandroid.ui.project;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.example.wanandroid.data.bean.project.ProjectListData;
import com.example.wanandroid.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProjectListFragment extends BaseRootFragment {
	public static final String TAG = "ProjectListFragment";
	
	@BindView(R.id.normal_view)
	SmartRefreshLayout mRefreshLayout;
	@BindView(R.id.project_list_recycler_view)
	RecyclerView mRecyclerView;
	
	private ProjectListAdapter mAdapter;
	private boolean isRefresh = true;
	private int mCurrentPage;
	private int cid;
	
	private ProjectListViewModel mViewModel;
	
	public static ProjectListFragment getInstance(int param1, String param2) {
		ProjectListFragment fragment = new ProjectListFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_PARAM1, param1);
		args.putString(Constants.ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.project_list_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		List<FeedArticleData> mDatas = new ArrayList<>();
		mAdapter = new ProjectListAdapter(R.layout.item_project_list, mDatas);
		mAdapter.setOnItemClickListener((adapter, view, position) -> startProjectPager(position));
		mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(ProjectListViewModel.class);
		
		mViewModel.mProjectListLiveData.observe(this, new Observer<ProjectListData>() {
			@Override
			public void onChanged(ProjectListData projectListData) {
				if (isRefresh) {
					mAdapter.replaceData(projectListData.getDatas());
				} else {
					if (projectListData.getDatas().size() > 0) {
						mAdapter.addData(projectListData.getDatas());
					} else {
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
		
		setRefresh();
		Bundle bundle = getArguments();
		cid = bundle.getInt(Constants.ARG_PARAM1);
		mViewModel.getProjectListData(mCurrentPage, cid, true);
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
	}
	
	@Override
	public void reload() {
		super.reload();
		mViewModel.getProjectListData(0, cid, false);
	}
	
	private void clickChildEvent(View view, int position) {
		switch (view.getId()) {
			case R.id.item_project_list_install_tv:
				startInstallPager(position);
				break;
			default:
				break;
		}
	}
	
	private void startInstallPager(int position) {
		if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
			return;
		}
		if (TextUtils.isEmpty(mAdapter.getData().get(position).getApkLink())) {
			return;
		}
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.getData().get(position).getApkLink())));
	}
	
	private void startProjectPager(int position) {
		if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
			return;
		}
//		JudgeUtils.startArticleDetailActivity(_mActivity,
//				null,
//				mAdapter.getData().get(position).getId(),
//				mAdapter.getData().get(position).getTitle().trim(),
//				mAdapter.getData().get(position).getLink().trim(),
//				mAdapter.getData().get(position).isCollect(),
//				false,
//				true);
	}
	
	private void setRefresh() {
		mCurrentPage = 1;
		if (mRefreshLayout == null) {
			return;
		}
		mRefreshLayout.setOnRefreshListener(refreshLayout -> {
			mCurrentPage = 1;
			isRefresh = true;
			mViewModel.getProjectListData(mCurrentPage, cid, false);
			refreshLayout.finishRefresh(1000);
		});
		mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
			mCurrentPage++;
			isRefresh = false;
			mViewModel.getProjectListData(mCurrentPage, cid, false);
			refreshLayout.finishLoadMore(1000);
		});
	}
}