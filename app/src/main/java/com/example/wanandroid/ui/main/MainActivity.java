package com.example.wanandroid.ui.main;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseRootActivity;
import com.example.wanandroid.ui.hierarchy.KnowledgeHierarchyFragment;
import com.example.wanandroid.ui.mainpager.MainPagerFragment;
import com.example.wanandroid.ui.navigation.NavigationFragment;
import com.example.wanandroid.ui.project.ProjectFragment;
import com.example.wanandroid.ui.wx.WxArticleFragment;
import com.example.wanandroid.util.StatusBarUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;

public class MainActivity extends BaseRootActivity {
	
	@BindView(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;
	@BindView(R.id.common_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.common_toolbar_title_tv)
	TextView mTitleTv;
	@BindView(R.id.main_floating_action_btn)
	FloatingActionButton mFloatingActionButton;
	@BindView(R.id.bottom_navigation_view)
	BottomNavigationView mBottomNavigationView;
	@BindView(R.id.nav_view)
	NavigationView mNavigationView;
	@BindView(R.id.fragment_group)
	FrameLayout mFrameGroup;

	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_main;
	}
	
	@Override
	protected void onViewCreated() {
	
	}
	
	@Override
	protected void initToolbar() {
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setDisplayShowTitleEnabled(false);
		mTitleTv.setText(getString(R.string.home_pager));
		StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_blue), 1f);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressedSupport();
			}
		});
	}
	
	@Override
	protected void initEventAndData() {
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
	public void onBackPressedSupport() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			pop();
		} else {
			ActivityCompat.finishAfterTransition(this);
		}
	}
}