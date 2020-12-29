package com.example.wanandroid.ui.mainpager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainPagerFragment extends BaseFragment {
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
//		initRecyclerView();
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
	
	private void clickChildEvent(View view, int position) {
	
	}
	
	private void startArticleDetailPager(View view, int position) {
	
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(MainPagerViewModel.class);
	}
}