package com.example.library.verticaltablayout.adapter;

import com.example.library.verticaltablayout.widget.TabView;

/**
 * @author zhaohw
 * @date 2021/1/18
 */
public abstract class SimpleTabAdapter implements TabAdapter {
	@Override
	public abstract int getCount();
	
	@Override
	public TabView.TabBadge getBadge(int position) {
		return null;
	}
	
	@Override
	public TabView.TabIcon getIcon(int position) {
		return null;
	}
	
	@Override
	public TabView.TabTitle getTitle(int position) {
		return null;
	}
	
	@Override
	public int getBackground(int position) {
		return 0;
	}
}
