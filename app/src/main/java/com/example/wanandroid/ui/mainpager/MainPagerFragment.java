package com.example.wanandroid.ui.mainpager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.main.banner.BannerData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainPagerFragment extends BaseRootFragment {
	public static final String TAG = "MainPagerFragment";
	@BindView(R.id.normal_view)
	SmartRefreshLayout mRefreshLayout;
	@BindView(R.id.main_pager_recycler_view)
	RecyclerView mRecyclerView;
	private MainPagerViewModel mViewModel;
	
	private final List<FeedArticleData> mFeedArticleDataList = new ArrayList<>();
	private ArticleListAdapter mAdapter;
	
	private Banner mBanner;
	private boolean isRecreate;
	
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
		
		if (loggedAndNotRebuilt()) {
			mViewModel.loadMainPagerData();
		} else {
			mViewModel.autoRefresh(true);
		}
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
	}
	
	private void initViewModel() {
		mViewModel = ViewModelProviders.of(this).get(MainPagerViewModel.class);
		mViewModel.mArticleListLiveData.observe(this, new Observer<FeedArticleListData>() {
			@Override
			public void onChanged(FeedArticleListData feedArticleListData) {
				if (mViewModel.getCurrentPage() == Constants.TYPE_MAIN_PAGER) {
					mRecyclerView.setVisibility(View.VISIBLE);
				} else {
					mRecyclerView.setVisibility(View.INVISIBLE);
				}
				if (mAdapter == null) {
					return;
				}
				if (mViewModel.isRefresh()) {
					mFeedArticleDataList.clear();
					mFeedArticleDataList.addAll(feedArticleListData.getDatas());
					mAdapter.replaceData(feedArticleListData.getDatas());
				} else {
					mFeedArticleDataList.addAll(feedArticleListData.getDatas());
					mAdapter.addData(feedArticleListData.getDatas());
				}
				showNormal();
			}
		});
		mViewModel.mBannerDataLiveData.observe(this, new Observer<List<BannerData>>() {
			@Override
			public void onChanged(List<BannerData> bannerDataList) {
				mBanner.setAdapter(new BannerImageAdapter<BannerData>(bannerDataList) {
					@Override
					public void onBindView(BannerImageHolder holder, BannerData data, int position, int size) {
						Glide.with(holder.itemView)
								.load(data.getImagePath())
								.apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
								.into(holder.imageView);
					}
				});
				mBanner.start();
			}
		});
		mViewModel.mRefreshLiveEvent.observe(this, new Observer<Event<Boolean>>() {
			@Override
			public void onChanged(Event<Boolean> booleanEvent) {
			
			}
		});
	}
	
	@Override
	public void showError() {
		mRecyclerView.setVisibility(View.INVISIBLE);
		super.showError();
	}
	
	@Override
	public void reload() {
		if (mRefreshLayout != null && mViewModel != null
				&& mRecyclerView.getVisibility() == View.INVISIBLE
				&& CommonUtils.isNetworkConnected()) {
			mRefreshLayout.autoRefresh();
		}
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
		LinearLayout mHeaderGroup = ((LinearLayout)LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null));
		mBanner = mHeaderGroup.findViewById(R.id.head_banner);
		mBanner.addBannerLifecycleObserver(this);//添加生命周期观察者
		mBanner.setIndicator(new CircleIndicator(_mActivity));
		
		mHeaderGroup.removeView(mBanner);
		mAdapter.addHeaderView(mBanner);
		mRecyclerView.setAdapter(mAdapter);
	}
	
	private boolean loggedAndNotRebuilt() {
		return /*!TextUtils.isEmpty(mPresenter.getLoginAccount())
				&& !TextUtils.isEmpty(mPresenter.getLoginPassword())
				&&*/ !isRecreate;
	}
}