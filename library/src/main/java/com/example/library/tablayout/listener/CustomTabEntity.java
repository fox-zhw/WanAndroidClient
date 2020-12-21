package com.example.library.tablayout.listener;

import androidx.annotation.DrawableRes;

/**
 * @author 52512
 * @date 2020/12/21
 */
public interface CustomTabEntity {
	String getTabTitle();
	
	@DrawableRes
	int getTabSelectedIcon();
	
	@DrawableRes
	int getTabUnselectedIcon();
}
