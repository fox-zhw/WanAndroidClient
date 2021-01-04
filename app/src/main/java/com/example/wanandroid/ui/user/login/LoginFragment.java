package com.example.wanandroid.ui.user.login;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.wanandroid.Constants;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.util.CommonUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;

@AndroidEntryPoint
public class LoginFragment extends BaseRootFragment {
	@BindView(R.id.login_group)
	RelativeLayout mLoginGroup;
	@BindView(R.id.login_toolbar)
	Toolbar mToolbar;
	@BindView(R.id.login_account_edit)
	EditText mAccountEdit;
	@BindView(R.id.login_password_edit)
	EditText mPasswordEdit;
	@BindView(R.id.login_btn)
	Button mLoginBtn;
	@BindView(R.id.login_register_btn)
	Button mRegisterBtn;
	private LoginViewModel mViewModel;
	
	public static LoginFragment newInstance() {
		return new LoginFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.login_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressedSupport();
			}
		});
	}
	
	@Override
	protected void initEventAndData() {
		super.initEventAndData();
		mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
		
		mViewModel.mLoginSuccessLiveEvent.observe(this, new Event.EventObserver<Object>() {
			@Override
			public void onEventChanged(@NonNull Object o) {
				CommonUtils.showMessage(_mActivity, getString(R.string.login_success));
				pop();
			}
		});
		
		mViewModel.mShowErrorEvent.observe(this, new Event.EventObserver<Object>() {
			@Override
			public void onEventChanged(@NonNull Object o) {
				showError();
			}
		});
		
		mViewModel.mSnackBarMsgEvent.observe(this, new Event.EventObserver<String>() {
			@Override
			public void onEventChanged(@NonNull String s) {
				showSnackBar(s);
			}
		});
		
		mViewModel.mToastMsgEvent.observe(this, new Event.EventObserver<String>() {
			@Override
			public void onEventChanged(@NonNull String s) {
				showToast(s);
			}
		});
		
		mViewModel.addDisposable(RxView.clicks(mLoginBtn)
				.throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
				.subscribe(new Consumer<Object>() {
					@Override
					public void accept(Object o) throws Exception {
						mViewModel.getLoginData(
								mAccountEdit.getText().toString().trim(),
								mPasswordEdit.getText().toString().trim()
						);
					}
				}));
	}
	
	@OnClick({R.id.login_register_btn})
	void onClick(View v) {
		switch (v.getId()) {
			case R.id.login_register_btn:
				startRegisterPager();
				break;
			default:
				break;
		}
	}
	
	private void startRegisterPager() {
		// TODO: 2021/1/4 注册
	}
}