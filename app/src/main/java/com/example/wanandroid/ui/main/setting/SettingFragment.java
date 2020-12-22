package com.example.wanandroid.ui.main.setting;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.event.NightModeEvent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseRootFragment implements CompoundButton.OnCheckedChangeListener{
	
	private SettingViewModel mViewModel;
	
	@BindView(R.id.cb_setting_cache)
	AppCompatCheckBox mCbSettingCache;
	@BindView(R.id.cb_setting_image)
	AppCompatCheckBox mCbSettingImage;
	@BindView(R.id.cb_setting_night)
	AppCompatCheckBox mCbSettingNight;
	@BindView(R.id.ll_setting_feedback)
	TextView mLlSettingFeedback;
	@BindView(R.id.ll_setting_clear)
	LinearLayout mLlSettingClear;
	@BindView(R.id.tv_setting_clear)
	TextView mTvSettingClear;
	
	private File cacheFile;
	
	public static SettingFragment newInstance() {
		return new SettingFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.setting_fragment;
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
			case R.id.cb_setting_night:
				mViewModel.setNightModeState(isChecked);
//				RxBus.getDefault().post(new NightModeEvent(b));
				break;
			case R.id.cb_setting_image:
				mViewModel.setNoImageState(isChecked);
				break;
			case R.id.cb_setting_cache:
				mViewModel.setAutoCacheState(isChecked);
				break;
			default:
				break;
		}
	}
	
	@OnClick({R.id.ll_setting_feedback, R.id.ll_setting_clear})
	void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_setting_feedback:
//				ShareUtil.sendEmail(_mActivity, getString(R.string.send_email));
				break;
			case R.id.ll_setting_clear:
				clearCache();
				break;
			default:
				break;
		}
	}
	
	private void clearCache() {
//		ACache.deleteDir(cacheFile);
//		mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
	}
}