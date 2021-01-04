package com.example.wanandroid.ui.wx.article;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.viewpager.widget.ViewPager;

import com.example.library.tablayout.SlidingTabLayout;
import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.data.bean.wx.WxAuthor;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.ui.wx.articledetail.WxDetailArticleFragment;
import com.example.wanandroid.util.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WxArticleFragment extends BaseRootFragment {
	public static final String TAG = "WxArticleFragment";
	
	@BindView(R.id.wx_detail_tab_layout)
	SlidingTabLayout mTabLayout;
	@BindView(R.id.wx_detail_viewpager)
	ViewPager mViewPager;
	
	private final List<BaseFragment> mFragments = new ArrayList<>();
	
	private WxArticleViewModel mViewModel;
	
	public static WxArticleFragment newInstance() {
		return new WxArticleFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.wx_article_fragment;
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(WxArticleViewModel.class);
		
		mViewModel.mWxAuthorListLiveData.observe(this, new Observer<List<WxAuthor>>() {
			@Override
			public void onChanged(List<WxAuthor> wxAuthors) {
				if (mViewModel.getCurrentPage() == Constants.TYPE_WX_ARTICLE) {
					setChildViewVisibility(View.VISIBLE);
				} else {
					setChildViewVisibility(View.INVISIBLE);
				}
				mFragments.clear();
				for (WxAuthor wxAuthor : wxAuthors) {
					mFragments.add(WxDetailArticleFragment.getInstance(wxAuthor.getId(), wxAuthor.getName()));
				}
				initViewPagerAndTabLayout(wxAuthors);
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
		
		mViewModel.requestWxAuthorListData();
		
		if (CommonUtils.isNetworkConnected()) {
			showLoading();
		}
	}
	
	@Override
	public void reload() {
		if (mViewModel != null && mTabLayout.getVisibility() == View.INVISIBLE) {
			mViewModel.requestWxAuthorListData();
		}
	}
	
	@Override
	public void showError() {
		setChildViewVisibility(View.INVISIBLE);
		super.showError();
	}
	
	private void initViewPagerAndTabLayout(List<WxAuthor> wxAuthors) {
		mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
			@NotNull
			@Override
			public BaseFragment getItem(int position) {
				return mFragments.get(position);
			}
			
			@Override
			public int getCount() {
				return mFragments.size();
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				return wxAuthors.get(position).getName();
			}
		});
		mTabLayout.setViewPager(mViewPager);
	}
	
	private void setChildViewVisibility(int visible) {
		mTabLayout.setVisibility(visible);
		mViewPager.setVisibility(visible);
	}
}