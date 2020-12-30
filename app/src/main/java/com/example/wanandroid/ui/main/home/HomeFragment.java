package com.example.wanandroid.ui.main.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.ui.abort.AbortUsFragment;
import com.example.wanandroid.ui.hierarchy.KnowledgeHierarchyFragment;
import com.example.wanandroid.ui.mainpager.MainPagerFragment;
import com.example.wanandroid.ui.navigation.NavigationFragment;
import com.example.wanandroid.ui.project.ProjectFragment;
import com.example.wanandroid.ui.wx.article.WxArticleFragment;
import com.example.wanandroid.util.StatusBarUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;
import me.yokeyword.fragmentation.SupportFragment;

@AndroidEntryPoint
public class HomeFragment extends BaseRootFragment {
	public static final String TAG = "HomeFragment";
	@BindView(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;
	@BindView(R.id.common_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.common_toolbar_title_tv)
	TextView mTitleTv;
	//	@BindView(R.id.main_floating_action_btn)
//	FloatingActionButton mFloatingActionButton;
	@BindView(R.id.bottom_navigation_view)
	BottomNavigationView mBottomNavigationView;
	@BindView(R.id.nav_view)
	NavigationView mNavigationView;
	@BindView(R.id.fragment_group)
	FrameLayout mFrameGroup;
	
	private HomeViewModel mViewModel;
	
	public static final int MAIN_PAGER = 0;
	public static final int HIERARCHY = 1;
	public static final int WX = 2;
	public static final int NAVIGATION = 3;
	public static final int PROJECT = 4;
	private int mCurrentFragment;
	
	private SupportFragment[] mFragments = new SupportFragment[5];
	
	public static HomeFragment newInstance() {
		return new HomeFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.home_fragment;
	}
	
	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_activity_main, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_usage:
				// TODO: 2020/12/20 usage
				break;
			case R.id.action_search:
				// TODO: 2020/12/20 search
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void initView() {
		setHasOptionsMenu(true);
		initFragment();
		initToolbar();
		initNavigationView();
		initBottomNavigationView();
		initDrawerLayout();
	}
	
	protected void initToolbar() {
		((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
		ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
		}
		mTitleTv.setText(getString(R.string.home_pager));
		StatusBarUtil.setStatusColor(getActivity().getWindow(), ContextCompat.getColor(getActivity(), R.color.main_status_bar_blue), 1f);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				onBackPressedSupport();
				mDrawerLayout.openDrawer(mNavigationView);
			}
		});
	}
	
	private void initFragment() {
		SupportFragment firstFragment = findFragment(MainPagerFragment.class);
		if (firstFragment == null) {
			mFragments[MAIN_PAGER] = MainPagerFragment.newInstance();
			mFragments[HIERARCHY] = KnowledgeHierarchyFragment.newInstance();
			mFragments[WX] = WxArticleFragment.newInstance();
			mFragments[NAVIGATION] = NavigationFragment.newInstance();
			mFragments[PROJECT] = ProjectFragment.newInstance();
			
			loadMultipleRootFragment(R.id.fragment_group, MAIN_PAGER,
					mFragments[MAIN_PAGER],
					mFragments[HIERARCHY],
					mFragments[WX],
					mFragments[NAVIGATION],
					mFragments[PROJECT]);
			
		} else {
			// 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
			
			// 这里我们需要拿到mFragments的引用
			mFragments[MAIN_PAGER] = firstFragment;
			mFragments[HIERARCHY] = findFragment(KnowledgeHierarchyFragment.class);
			mFragments[WX] = findFragment(WxArticleFragment.class);
			mFragments[NAVIGATION] = findFragment(NavigationFragment.class);
			mFragments[PROJECT] = findFragment(ProjectFragment.class);
			
		}
		mCurrentFragment = MAIN_PAGER;
	}
	
	private void initNavigationView() {
		mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
		
		mNavigationView.getMenu().findItem(R.id.nav_item_wan_android)
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		
		mNavigationView.getMenu().findItem(R.id.nav_item_about_us)
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						start(AbortUsFragment.newInstance(), SINGLETASK);
						return true;
					}
				});
	}
	
	private void initDrawerLayout() {
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				getActivity(), mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				//获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
				//获取抽屉的view
				View mContent = mDrawerLayout.getChildAt(0);
				float scale = 1 - slideOffset;
				float endScale = 0.8f + scale * 0.2f;
				float startScale = 1 - 0.3f * scale;
				
				//设置左边菜单滑动后的占据屏幕大小
				drawerView.setScaleX(startScale);
				drawerView.setScaleY(startScale);
				//设置菜单透明度
				drawerView.setAlpha(0.6f + 0.4f * (1 - scale));
				
				//设置内容界面水平和垂直方向偏转量
				//在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
				mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
				//设置内容界面操作无效（比如有button就会点击无效）
				mContent.invalidate();
				//设置右边菜单滑动后的占据屏幕大小
				mContent.setScaleX(endScale);
				mContent.setScaleY(endScale);
			}
		};
		toggle.syncState();
		mDrawerLayout.addDrawerListener(toggle);
	}
	
	private void initBottomNavigationView() {
//		BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
		mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				int prePosition = mCurrentFragment;
				switch (item.getItemId()) {
					case R.id.tab_main_pager:
						switchPager(MAIN_PAGER, prePosition);
						break;
					case R.id.tab_knowledge_hierarchy:
						switchPager(HIERARCHY, prePosition);
						break;
					case R.id.tab_wx_article:
						switchPager(WX, prePosition);
						break;
					case R.id.tab_navigation:
						switchPager(NAVIGATION, prePosition);
						break;
					case R.id.tab_project:
						switchPager(PROJECT, prePosition);
						break;
					default:
						break;
				}
				return true;
			}
		});
	}
	
	private void switchPager(int curfragment, int preFragment) {
		mCurrentFragment = curfragment;
		showHideFragment(mFragments[curfragment], mFragments[preFragment]);
		mViewModel.setCurrentPage(curfragment);
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
	}
	
	@Override
	public boolean onBackPressedSupport() {
		return super.onBackPressedSupport();
	}
}