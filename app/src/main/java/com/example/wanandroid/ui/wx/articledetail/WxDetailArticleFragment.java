package com.example.wanandroid.ui.wx.articledetail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.main.collect.FeedArticleListData;
import com.example.wanandroid.event.Event;
import com.example.wanandroid.ui.mainpager.ArticleListAdapter;
import com.example.wanandroid.util.CommonUtils;
import com.example.wanandroid.util.LogHelper;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

@AndroidEntryPoint
public class WxDetailArticleFragment extends BaseRootFragment {
	public static final String TAG = "WxDetailArticleFragment";
	@BindView(R.id.normal_view)
	SmartRefreshLayout mRefreshLayout;
	@BindView(R.id.we_detail_list_recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.search_back_ib)
	ImageButton mBackIb;
	@BindView(R.id.search_tint_tv)
	TextView mTintTv;
	@BindView(R.id.search_edit)
	EditText mSearchEdit;
	@BindView(R.id.search_tv)
	TextView mSearchTv;
	
	private int id;
	private int mCurrentPage = 1;
	private ArticleListAdapter mAdapter;
	private boolean isRefresh = true;
	private int articlePosition;
	private String mAuthor;
	private String tint;
	private boolean isSearchStatus;
	private String searchString;
	
	private WxDetailArticleViewModel mViewModel;
	
	public static WxDetailArticleFragment newInstance() {
		return new WxDetailArticleFragment();
	}
	
	public static BaseFragment getInstance(int id, String name) {
		WxDetailArticleFragment fragment = new WxDetailArticleFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_PARAM1, id);
		args.putString(Constants.ARG_PARAM2, name);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.wx_detail_article_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		mAdapter = new ArticleListAdapter(R.layout.item_search_pager, null);
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				// 详情
			}
		});
		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				//
			}
		});
		mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(WxDetailArticleViewModel.class);
		isSearchStatus = false;
		setRefresh();
		Bundle bundle = getArguments();
		id = bundle.getInt(Constants.ARG_PARAM1, 0);
		if (id == 0) {
			return;
		}
		mAuthor = bundle.getString(Constants.ARG_PARAM2, "");
		initToolbar();
		
		mSearchEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
					mTintTv.setText(tint);
				} else {
					mTintTv.setText("");
				}
			}
		});
		
		mViewModel.addDisposable(RxView.clicks(mSearchTv)
				.throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
				.filter(new Predicate<Object>() {
					@Override
					public boolean test(Object o) throws Exception {
						return !TextUtils.isEmpty(mSearchEdit.getText().toString().trim());
					}
				})
				.subscribe(new Consumer<Object>() {
					@Override
					public void accept(Object o) throws Exception {
						isSearchStatus = true;
						isRefresh = true;
						mCurrentPage = 1;
						searchString = mSearchEdit.getText().toString();
						mViewModel.getWxSearchSumData(id, mCurrentPage, mSearchEdit.getText().toString());
					}
				}));
		
		//重置当前页数，防止页面切换后当前页数为较大而加载后面的数据或没有数据
		mCurrentPage = 1;
		mViewModel.getWxDetailData(id, mCurrentPage, true);
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
		
		initViewModel();
	}
	
	private void initViewModel() {
		mViewModel.mFeedArticleListDataLiveData.observe(this, new Observer<FeedArticleListData>() {
			@Override
			public void onChanged(FeedArticleListData feedArticleListData) {
				if (isRefresh) {
					mAdapter.replaceData(feedArticleListData.getDatas());
				} else {
					if (feedArticleListData.getDatas().size() > 0) {
						mAdapter.addData(feedArticleListData.getDatas());
					} else {
						CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
					}
				}
				showNormal();
			}
		});
		mViewModel.mFeedArticleListDataSearchLiveData.observe(this, new Observer<FeedArticleListData>() {
			@Override
			public void onChanged(FeedArticleListData feedArticleListData) {
				if (isRefresh) {
					mAdapter.replaceData(feedArticleListData.getDatas());
				} else {
					if (feedArticleListData.getDatas().size() > 0) {
						mAdapter.addData(feedArticleListData.getDatas());
					} else {
						CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
					}
				}
				showNormal();
			}
		});
		mViewModel.mSnackBarMsgEvent.observe(this, new Event.EventObserver<String>() {
			@Override
			public void onEventChanged(@NonNull String s) {
				showSnackBar(s);
			}
		});
	}
	
	private void initToolbar() {
		mBackIb.setVisibility(View.GONE);
		tint = mAuthor + "带你" + getString(R.string.search_tint);
		mTintTv.setText(tint);
	}
	
	private void setRefresh() {
		mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				mCurrentPage = 1;
				if (id != 0) {
					isRefresh = true;
					mViewModel.getWxDetailData(id, 0, false);
				}
				refreshLayout.finishRefresh(1000);
			}
		});
		mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				mCurrentPage++;
				if (id != 0) {
					isRefresh = false;
					if (isSearchStatus) {
						mViewModel.getWxSearchSumData(id, mCurrentPage, searchString);
					} else {
						mViewModel.getWxDetailData(id, mCurrentPage, false);
					}
				}
				refreshLayout.finishLoadMore(1000);
			}
		});
	}
}