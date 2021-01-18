package com.example.wanandroid.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.util.ActivityCollector;
import com.example.wanandroid.util.CommonUtils;

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
		initFragment();
		onViewCreated();
		initEventAndData();
	}
	
	protected abstract void initFragment();
	
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
	
	/* 初始化数据 */
	protected abstract void initEventAndData();
	
	private long clickTime;
	@Override
	public void onBackPressedSupport() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			pop();
		} else {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
				CommonUtils.showMessage(this, getString(R.string.double_click_exit_tint));
				clickTime = System.currentTimeMillis();
			} else {
				ActivityCompat.finishAfterTransition(this);
			}
		}
	}
}
