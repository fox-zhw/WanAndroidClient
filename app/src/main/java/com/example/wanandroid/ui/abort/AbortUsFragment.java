package com.example.wanandroid.ui.abort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;

import androidx.annotation.NonNull;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.util.DensityUtil;
import com.example.wanandroid.util.StatusBarUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import butterknife.BindView;

public class AbortUsFragment extends BaseRootFragment {
	public static final String TAG = "AbortUsFragment";
	@BindView(R.id.about_us_mountain)
	MountainSceneView mAboutUsMountain;
	@BindView(R.id.about_us_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.about_us_toolbar_layout)
	CollapsingToolbarLayout mAboutUsToolbarLayout;
	@BindView(R.id.about_us_app_bar)
	AppBarLayout mAboutUsAppBar;
	@BindView(R.id.about_us_fly_refresh)
	FlyRefreshHeader mFlyRefreshHeader;
	@BindView(R.id.about_us_refresh_layout)
	SmartRefreshLayout mAboutUsRefreshLayout;
	@BindView(R.id.about_us_fab)
	FloatingActionButton mAboutUsFab;
	@BindView(R.id.about_us_fly_view)
	FlyView mAboutUsFlyView;
	@BindView(R.id.about_us_content)
	NestedScrollView mScrollView;
	@BindView(R.id.aboutContent)
	TextView mAboutContent;
	@BindView(R.id.aboutVersion)
	TextView mAboutVersion;
	private View.OnClickListener mThemeListener;
	private AbortUsViewModel mViewModel;
	
	public static AbortUsFragment newInstance() {
		return new AbortUsFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.abort_fragment;
	}
	
	@Override
	protected void initView() {
		((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
		StatusBarUtil.immersive(getActivity());
		StatusBarUtil.setPaddingSmart(getActivity(), mToolbar);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop();
			}
		});
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(AbortUsViewModel.class);
		
		showAboutContent();
		setSmartRefreshLayout();
		
		//进入界面时自动刷新
		mAboutUsRefreshLayout.autoRefresh();
		
		//点击悬浮按钮时自动刷新
		mAboutUsFab.setOnClickListener(v -> mAboutUsRefreshLayout.autoRefresh());
		
		//监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 ActionButton 设置关闭隐藏动画
		mAboutUsAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			boolean misAppbarExpand = true;
			
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				int scrollRange = appBarLayout.getTotalScrollRange();
				float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
				double minFraction = 0.1;
				double maxFraction = 0.8;
				if (mScrollView == null || mAboutUsFab == null || mAboutUsFlyView == null) {
					return;
				}
				if (fraction < minFraction && misAppbarExpand) {
					misAppbarExpand = false;
					mAboutUsFab.animate().scaleX(0).scaleY(0);
					mAboutUsFlyView.animate().scaleX(0).scaleY(0);
					ValueAnimator animator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), 0);
					animator.setDuration(300);
					animator.addUpdateListener(animation -> {
						if (mScrollView != null) {
							mScrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
						}
					});
					animator.start();
				}
				if (fraction > maxFraction && !misAppbarExpand) {
					misAppbarExpand = true;
					mAboutUsFab.animate().scaleX(1).scaleY(1);
					mAboutUsFlyView.animate().scaleX(1).scaleY(1);
					ValueAnimator animator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), DensityUtil.dp2px(25));
					animator.setDuration(300);
					animator.addUpdateListener(animation -> {
						if (mScrollView != null) {
							mScrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
						}
					});
					animator.start();
				}
			}
		});
	}
	
	private void setSmartRefreshLayout() {
		//绑定场景和纸飞机
		mFlyRefreshHeader.setUp(mAboutUsMountain, mAboutUsFlyView);
		mAboutUsRefreshLayout.setReboundInterpolator(new Interpolator(){
			@Override
			public float getInterpolation(float input) {
				if (input == 0) {
					return 0;
				}
				if (input >= 1) {
					return 1;
				}
				float p = .3f;
				float s = p/4;
				return ((float) Math.pow(2, -10 * input) * (float)Math.sin((input - s) * (2 * (float)Math.PI) / p) + 1);
			}
		});
		mAboutUsRefreshLayout.setReboundDuration(800);
		mAboutUsRefreshLayout.setOnRefreshListener(refreshLayout -> {
			updateTheme();
			refreshLayout.finishRefresh(1000);
		});
		
		//设置让Toolbar和AppBarLayout的滚动同步
		mAboutUsRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				refreshLayout.finishRefresh(2000);
			}
			
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				super.onLoadMore(refreshLayout);
				refreshLayout.finishLoadMore(3000);
			}
			
			@Override
			public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
				super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
				if (mAboutUsAppBar == null || mToolbar == null) {
					return;
				}
				mAboutUsAppBar.setTranslationY(offset);
				mToolbar.setTranslationY(-offset);
			}
		});
	}
	
	private void showAboutContent() {
		mAboutContent.setText(Html.fromHtml(getString(R.string.about_content)));
		mAboutContent.setMovementMethod(LinkMovementMethod.getInstance());
		try {
			String versionStr = getString(R.string.awesome_wan_android)
					+ " V" + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
			mAboutVersion.setText(versionStr);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update appbar theme
	 */
	private void updateTheme() {
		if (mThemeListener == null) {
			mThemeListener = new View.OnClickListener() {
				int index = 0;
				int[] ids = new int[]{
						R.color.colorPrimary,
						android.R.color.holo_green_light,
						android.R.color.holo_red_light,
						android.R.color.holo_orange_light,
						android.R.color.holo_blue_bright,
				};
				
				@Override
				public void onClick(View v) {
					int color = ContextCompat.getColor(App.getContext(), ids[index % ids.length]);
					mAboutUsRefreshLayout.setPrimaryColors(color);
					mAboutUsFab.setBackgroundColor(color);
					mAboutUsFab.setBackgroundTintList(ColorStateList.valueOf(color));
					mAboutUsToolbarLayout.setContentScrimColor(color);
					index++;
				}
			};
		}
		mThemeListener.onClick(null);
	}
}