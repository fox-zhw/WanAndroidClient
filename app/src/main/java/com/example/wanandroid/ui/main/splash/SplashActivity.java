package com.example.wanandroid.ui.main.splash;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.wanandroid.R;
import com.example.wanandroid.event.Event;
import com.example.wanandroid.base.activity.BaseRootActivity;
import com.example.wanandroid.ui.main.MainActivity;
import com.example.wanandroid.util.StatusBarUtil;

import java.util.List;

import butterknife.BindViews;

public class SplashActivity extends BaseRootActivity {
	@BindViews({
			R.id.one_animation,
			R.id.two_animation,
			R.id.three_animation,
			R.id.four_animation,
			R.id.five_animation,
			R.id.six_animation,
			R.id.seven_animation,
			R.id.eight_animation,
			R.id.nine_animation,
			R.id.ten_animation})
	List<LottieAnimationView> mLottieAnimationViews;
	
	String[] resJson = new String[]{
			"W.json",
			"A.json",
			"N.json",
			"A.json",
			"N.json",
			"D.json",
			"R.json",
			"I.json",
			"O.json",
			"D.json"
	};
	
	private SplashViewModel mViewModel;
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_splash;
	}
	
	@Override
	protected void onViewCreated() {
	
	}
	
	@Override
	protected void initToolbar() {
		StatusBarUtil.immersive(SplashActivity.this);
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
		
		mViewModel.startAnimationEvent.observe(this, new Event.EventObserver<Object>() {
			@Override
			public void onEventChanged(@NonNull Object o) {
				if (mLottieAnimationViews != null) {
					for (int i = 0; i < mLottieAnimationViews.size(); i++) {
						LottieAnimationView lottieAnimationView = mLottieAnimationViews.get(i);
						lottieAnimationView.setAnimation(resJson[i]);
						lottieAnimationView.playAnimation();
					}
				}
			}
		});
		
		mViewModel.jumpToMain.observe(this, new Event.EventObserver<Object>() {
			@Override
			public void onEventChanged(@NonNull Object o) {
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				SplashActivity.this.finish();
				SplashActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
	}
	
	@Override
	protected void initFragment() {
	
	}
	
	@Override
	protected void onDestroy() {
		if (mLottieAnimationViews != null) {
			for (int i = 0; i < mLottieAnimationViews.size(); i++) {
				mLottieAnimationViews.get(i).cancelAnimation();
			}
		}
		super.onDestroy();
	}
}