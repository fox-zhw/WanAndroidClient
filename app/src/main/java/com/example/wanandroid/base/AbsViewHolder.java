package com.example.wanandroid.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author zhaohw
 * @date 2020/11/17
 */
public abstract class AbsViewHolder<DATA> extends RecyclerView.ViewHolder implements View.OnClickListener {
	private DATA data;
	private View mView;
	
	public AbsViewHolder(final View view) {
		super(view);
		mView = view;
		getViews();
//		view.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				onViewClick(view, data);
//			}
//		});
	}
	
	protected final View getView() {
		return mView;
	}
	
	protected abstract void getViews();
	
	public final <V extends View> V getView(@IdRes int id) {
		return mView.findViewById(id);
	}
	
	public abstract void bind(DATA t);
	
	public void bind(DATA t, int position) {
		bind(t);
	}
	
	@Override
	public void onClick(View v) {
		onViewClick(v, data);
	}
	
	protected void onViewClick(View view, DATA data) {
	}
	
	protected final void setData(DATA data) {
		this.data = data;
	}
	
	protected final Context getContext() {
		return mView.getContext();
	}
}
