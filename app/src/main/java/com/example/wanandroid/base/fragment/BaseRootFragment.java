package com.example.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.util.CommonUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author zhaohw
 * @date 2020/12/16
 */
public abstract class BaseRootFragment extends SupportFragment {
	private Unbinder unBinder;
	private long clickTime;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		unBinder = ButterKnife.bind(this, view);
		initView();
		return view;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (unBinder != null && unBinder != Unbinder.EMPTY) {
			unBinder.unbind();
			unBinder = null;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
		initEventAndData();
	}
	
	@Override
	public boolean onBackPressedSupport() {
		if (getChildFragmentManager().getBackStackEntryCount() > 1) {
			popChild();
		} else {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
				CommonUtils.showSnackMessage(_mActivity, getString(R.string.double_click_exit_tint));
				clickTime = System.currentTimeMillis();
			} else {
				_mActivity.finish();
			}
		}
		return true;
	}
	
	/**
	 * 有些初始化必须在onCreateView中，例如setAdapter,
	 * 否则，会弹出 No adapter attached; skipping layout
	 */
	protected void initView() {
	
	}
	
	/* 获取当前Activity的UI布局 */
	protected abstract int getLayoutId();
	
	/* 初始化数据 */
	protected abstract void initEventAndData();
}
