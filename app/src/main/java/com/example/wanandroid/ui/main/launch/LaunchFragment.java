package com.example.wanandroid.ui.main.launch;

import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;
import com.example.wanandroid.base.event.Event;
import com.example.wanandroid.ui.main.home.HomeFragment;
import com.example.wanandroid.util.StatusBarUtil;

import java.util.List;

import butterknife.BindViews;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LaunchFragment extends BaseRootFragment {
	
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
	private LaunchViewModel mViewModel;
	
	public static LaunchFragment newInstance() {
		return new LaunchFragment();
	}

	
	@Override
	protected int getLayoutId() {
		return R.layout.launch_fragment;
	}
	
	@Override
	protected void initView() {
		super.initView();
		StatusBarUtil.immersive(getActivity());
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(LaunchViewModel.class);
		
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
				replaceFragment(HomeFragment.newInstance(), false);
			}
		});
	}
	
	@Override
	public void onDestroyView() {
		if (mLottieAnimationViews != null) {
			for (int i = 0; i < mLottieAnimationViews.size(); i++) {
				mLottieAnimationViews.get(i).cancelAnimation();
			}
		}
		super.onDestroyView();
	}
}