package com.example.library.verticaltablayout.adapter;

import com.example.library.verticaltablayout.widget.TabView;

/**
 * @author zhaohw
 * @date 2021/1/18
 */
public interface TabAdapter {
	int getCount();
	
	TabView.TabBadge getBadge(int position);
	
	TabView.TabIcon getIcon(int position);
	
	TabView.TabTitle getTitle(int position);
	
	int getBackground(int position);
}
