package com.example.library.verticaltablayout.widget;

import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.badgeview.Badge;

/**
 * @author zhaohw
 * @date 2021/1/18
 */
public abstract class TabView extends FrameLayout implements Checkable, ITabView {
	
	public TabView(Context context) {
		super(context);
	}
	
	@Override
	public TabView getTabView() {
		return this;
	}
	
	@Deprecated
	public abstract ImageView getIconView();
	
	public abstract TextView getTitleView();
	
	public abstract Badge getBadgeView();
}
