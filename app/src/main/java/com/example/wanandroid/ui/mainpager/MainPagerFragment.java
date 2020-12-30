package com.example.wanandroid.ui.mainpager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainPagerFragment extends BaseRootFragment {
	@BindView(R.id.normal_view)
	SmartRefreshLayout mRefreshLayout;
	@BindView(R.id.main_pager_recycler_view)
	RecyclerView mRecyclerView;
	private MainPagerViewModel mViewModel;
	
	private final List<FeedArticleData> mFeedArticleDataList = new ArrayList<>();
	private ArticleListAdapter mAdapter;
	
	private Banner mBanner;
	
	public static MainPagerFragment newInstance() {
		return new MainPagerFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.main_pager_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		initRecyclerView();
	}
	
	private void clickChildEvent(View view, int position) {
	
	}
	
	private void startArticleDetailPager(View view, int position) {
	
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		initRefresh();
		initViewModel();
	}
	
	@Override
	public void onSupportVisible() {
		super.onSupportVisible();
		if (mBanner != null) {
			mBanner.start();
		}
	}
	
	@Override
	public void onSupportInvisible() {
		super.onSupportInvisible();
		if (mBanner != null) {
			mBanner.stop();
		}
	}
	
	private void initViewModel() {
		mViewModel = ViewModelProviders.of(this).get(MainPagerViewModel.class);
	}
	
	private void initRefresh() {
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				mViewModel.autoRefresh(false);
				refreshLayout.finishRefresh(1000);
			}
		});
		
		mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				mViewModel.loadMore();
				refreshLayout.finishLoadMore(1000);
			}
		});
	}
	
	private void initRecyclerView() {
		mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mFeedArticleDataList);
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				startArticleDetailPager(view, position);
			}
		});
		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				clickChildEvent(view, position);
			}
		});
		mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
		mRecyclerView.setHasFixedSize(true);
		//add head banner
		LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null));
		mBanner = mHeaderGroup.findViewById(R.id.head_banner);
		mHeaderGroup.removeView(mBanner);
		mAdapter.addHeaderView(mBanner);
		mRecyclerView.setAdapter(mAdapter);
	}
}