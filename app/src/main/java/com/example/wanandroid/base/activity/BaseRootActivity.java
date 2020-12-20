package com.example.wanandroid.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.wanandroid.util.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author zhaohw
 * @date 2020/12/16
 */
public abstract class BaseRootActivity extends SupportActivity {
	private Unbinder unBinder;
	protected BaseRootActivity mActivity;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(getLayoutId());
		unBinder = ButterKnife.bind(this);
		mActivity = this;
		ActivityCollector.getInstance().addActivity(this);
		onViewCreated();
		initToolbar();
		initEventAndData();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.getInstance().removeActivity(this);
		if (unBinder != null && unBinder != Unbinder.EMPTY) {
			unBinder.unbind();
			unBinder = null;
		}
	}
	
	/* 获取当前Activity的UI布局 */
	protected abstract int getLayoutId();
	
	/* 在initEventAndData()之前执行 */
	protected abstract void onViewCreated();
	
	/* 初始化ToolBar */
	protected abstract void initToolbar();
	
	/* 初始化数据 */
	protected abstract void initEventAndData();
}
