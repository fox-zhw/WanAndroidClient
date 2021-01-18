package com.example.wanandroid.ui.project;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.example.wanandroid.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 52512
 * @date 2021/1/18
 */
public class ProjectListAdapter extends BaseQuickAdapter<FeedArticleData, ProjectListAdapter.ProjectListViewHolder> {
	
	public ProjectListAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
		super(layoutResId, data);
	}
	
	@Override
	protected void convert(ProjectListViewHolder helper, FeedArticleData item) {
		if (!TextUtils.isEmpty(item.getEnvelopePic())) {
			ImageLoader.load(mContext, item.getEnvelopePic(), helper.getView(R.id.item_project_list_iv));
		}
		if (!TextUtils.isEmpty(item.getTitle())) {
			helper.setText(R.id.item_project_list_title_tv, item.getTitle());
		}
		if (!TextUtils.isEmpty(item.getDesc())) {
			helper.setText(R.id.item_project_list_content_tv, item.getDesc());
		}
		if (!TextUtils.isEmpty(item.getNiceDate())) {
			helper.setText(R.id.item_project_list_time_tv, item.getNiceDate());
		}
		if (!TextUtils.isEmpty(item.getAuthor())) {
			helper.setText(R.id.item_project_list_author_tv, item.getAuthor());
		}
		if (!TextUtils.isEmpty(item.getApkLink())) {
			helper.getView(R.id.item_project_list_install_tv).setVisibility(View.VISIBLE);
		} else {
			helper.getView(R.id.item_project_list_install_tv).setVisibility(View.GONE);
		}
		
		helper.addOnClickListener(R.id.item_project_list_install_tv);
	}
	
	public class ProjectListViewHolder extends BaseViewHolder {
		
		@BindView(R.id.item_project_list_iv)
		ImageView mProjectIv;
		@BindView(R.id.item_project_list_title_tv)
		TextView mTitleTv;
		@BindView(R.id.item_project_list_content_tv)
		TextView mContentTv;
		@BindView(R.id.item_project_list_time_tv)
		TextView mTimeTv;
		@BindView(R.id.item_project_list_author_tv)
		TextView mAuthorTv;
		@BindView(R.id.item_project_list_install_tv)
		TextView mInstallTv;
		
		public ProjectListViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
