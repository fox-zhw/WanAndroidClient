package com.example.wanandroid.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhaohw
 * @date 2020/11/17
 */
public abstract class AbsRecyclerAdapter<HOLDER extends AbsViewHolder<DATA>, DATA> extends RecyclerView.Adapter<HOLDER> {
	public static final String TAG = "AbsRecyclerAdapter";
	protected List<DATA> mList;
	private LayoutInflater mInflater;
	protected Context mContext;
	
	public AbsRecyclerAdapter(Context context) {
		if (context == null) {
			Log.e(TAG, "AbsRecyclerAdapter: context == null");
			return;
		}
		mContext = context;
		mList = new ArrayList<>();
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public final HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = createView(mInflater, parent, viewType);
		return createViewHolder(view, viewType);
	}
	
	protected abstract HOLDER createViewHolder(View view, int viewType);
	
	protected abstract View createView(LayoutInflater inflater, ViewGroup parent, int viewType);
	
	@Override
	public final void onBindViewHolder(HOLDER holder, int position) {
		DATA data = mList.get(position);
		holder.setData(data);
		holder.bind(data, position);
	}
	
	@Override
	public int getItemCount() {
		return mList.size();
	}
	
	public void append(DATA item) {
		if (item == null) {
			return;
		}
		mList.add(item);
		notifyDataSetChanged();
	}
	
	public void append(DATA item, int position) {
		if (item == null) {
			return;
		}
		if (position < 0) {
			position = 0;
		} else if (position > mList.size()) {
			position = mList.size();
		}
		mList.add(position, item);
		notifyDataSetChanged();
	}
	
	public final void append(Collection<DATA> items) {
		if (items == null || items.size() == 0) {
			return;
		}
		mList.addAll(items);
		notifyDataSetChanged();
	}
	
	public final void clear() {
		if (mList.isEmpty()) {
			return;
		}
		mList.clear();
		notifyDataSetChanged();
	}
	
	public final void remove(DATA item) {
		if (item == null) {
			return;
		}
		if (mList.contains(item)) {
			mList.remove(item);
			notifyDataSetChanged();
		}
	}
	
	public final void remove(int index) {
		if (index < mList.size()) {
			mList.remove(index);
			notifyItemRemoved(index);
		}
	}
	
	public final void remove(Collection<DATA> items) {
		if (items == null || items.size() == 0) {
			return;
		}
		if (mList.removeAll(items)) {
			notifyDataSetChanged();
		}
	}
	
	public void setData(Collection<DATA> items) {
		if (items == null || items.size() == 0) {
			return;
		}
		if (mList.size() > 0) {
			mList.clear();
		}
		mList.addAll(items);
		notifyDataSetChanged();
	}
	
	public List<DATA> getData() {
		return new ArrayList<>(mList);
	}
}
