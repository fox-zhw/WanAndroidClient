package com.example.wanandroid.ui.user.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends BaseRootFragment {
	
	@BindView(R.id.common_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.common_toolbar_title_tv)
	TextView mTitleTv;
	@BindView(R.id.register_account_edit)
	EditText mAccountEdit;
	@BindView(R.id.register_password_edit)
	EditText mPasswordEdit;
	@BindView(R.id.register_confirm_password_edit)
	EditText mConfirmPasswordEdit;
	@BindView(R.id.register_btn)
	Button mRegisterBtn;
	
	private RegisterViewModel mViewModel;
	
	public static RegisterFragment newInstance() {
		return new RegisterFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.register_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		mToolbar.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.register_bac));
		mTitleTv.setText(R.string.register);
		mTitleTv.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
		mTitleTv.setTextSize(20);
		mToolbar.setNavigationOnClickListener(v -> {
			pop();
			hideSoftInput();
		});
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
		showSoftInput(mAccountEdit);
		
		mViewModel.addDisposable(RxView.clicks(mRegisterBtn)
				.throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
				.subscribe(o -> register()));
		
		mViewModel.mSnackBarMsgEvent.observe(this, new Event.EventObserver<String>() {
			@Override
			public void onEventChanged(@NonNull String s) {
				showSnackBar(s);
			}
		});
	}
	
	private void register() {
		mViewModel.getRegisterData(mAccountEdit.getText().toString().trim(),
				mPasswordEdit.getText().toString().trim(),
				mConfirmPasswordEdit.getText().toString().trim());
	}
	
	@Override
	public boolean onBackPressedSupport() {
		hideSoftInput();
		return super.onBackPressedSupport();
	}
	
	@Override
	public void onDestroyView() {
		hideSoftInput();
		super.onDestroyView();
	}
}