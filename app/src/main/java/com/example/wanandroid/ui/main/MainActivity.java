package com.example.wanandroid.ui.main;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseRootActivity;
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