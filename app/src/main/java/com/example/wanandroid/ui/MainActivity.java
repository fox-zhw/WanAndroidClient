package com.example.wanandroid.ui;

import com.example.wanandroid.App;
import com.example.wanandroid.R;
import com.example.wanandroid.base.activity.BaseRootActivity;
import com.example.wanandroid.ui.main.home.HomeFragment;
import com.example.wanandroid.ui.main.launch.LaunchFragment;

public class MainActivity extends BaseRootActivity {
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_main;
	}
	
	@Override
	protected void onViewCreated() {
	
	}
	
	@Override
	protected void initFragment() {
		if (App.isFirstRun) {
			loadRootFragment(R.id.fl_container, LaunchFragment.newInstance());
		} else {
			loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
		}
	}
	
	@Override
	protected void initEventAndData() {
	
	}
	
	@Override
	public void onBackPressedSupport() {
		super.onBackPressedSupport();
	}
}