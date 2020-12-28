package com.example.wanandroid.ui.mainpager;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.data.bean.main.collect.FeedArticleData;
import com.example.wanandroid.util.LogHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 52512
 * @date 2020/12/28
 */
public class ArticleListAdapter extends BaseQuickAdapter<FeedArticleData, ArticleListAdapter.KnowledgeHierarchyListViewHolder> {
	
	
	private boolean isCollectPage;
	private boolean isSearchPage;
	private boolean isNightMode;
	private boolean mHasRecorded;
	
	public ArticleListAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
		super(layoutResId, data);
	}
	
	public void isCollectPage() {
		isCollectPage = true;
		notifyDataSetChanged();
	}
	
	public void isSearchPage() {
		isSearchPage = true;
		notifyDataSetChanged();
	}
	
	public void isNightMode(boolean isNightMode) {
		this.isNightMode = isNightMode;
		notifyDataSetChanged();
	}
	
	@Override
	protected void convert(KnowledgeHierarchyListViewHolder helper, FeedArticleData article) {
		if (!TextUtils.isEmpty(article.getTitle())) {
			helper.setText(R.id.item_search_pager_title, Html.fromHtml(article.getTitle()));
		}
		if (article.isCollect() || isCollectPage) {
			helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like);
		} else {
			helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like_article_not_selected);
		}
		if (!TextUtils.isEmpty(article.getAuthor())) {
			helper.setText(R.id.item_search_pager_author, article.getAuthor());
		}
		setTag(helper, article);
		if (!TextUtils.isEmpty(article.getChapterName())) {
			String classifyName = article.getSuperChapterName() + " / " + article.getChapterName();
			if (isCollectPage) {
				helper.setText(R.id.item_search_pager_chapterName, article.getChapterName());
			} else {
				helper.setText(R.id.item_search_pager_chapterName, classifyName);
			}
		}
		if (!TextUtils.isEmpty(article.getNiceDate())) {
			helper.setText(R.id.item_search_pager_niceDate, article.getNiceDate());
		}
		if (isSearchPage) {
			CardView cardView = helper.getView(R.id.item_search_pager_group);
			cardView.setForeground(null);
			if (isNightMode) {
				cardView.setBackground(ContextCompat.getDrawable(mContext, R.color.card_color));
			} else {
				cardView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_search_item_bac));
			}
		}
		
		if (helper.getLayoutPosition() == 1 && !mHasRecorded) {
			mHasRecorded = true;
			helper.getView(R.id.item_search_pager_group).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@Override
				public boolean onPreDraw() {
					helper.getView(R.id.item_search_pager_group).getViewTreeObserver().removeOnPreDrawListener(this);
					LogHelper.d("FeedShow");
					return true;
				}
			});
		}
		
		helper.addOnClickListener(R.id.item_search_pager_chapterName);
		helper.addOnClickListener(R.id.item_search_pager_like_iv);
		helper.addOnClickListener(R.id.item_search_pager_tag_red_tv);
	}
	
	private void setTag(KnowledgeHierarchyListViewHolder helper, FeedArticleData article) {
		helper.getView(R.id.item_search_pager_tag_green_tv).setVisibility(View.GONE);
		helper.getView(R.id.item_search_pager_tag_red_tv).setVisibility(View.GONE);
		if (isCollectPage) {
			return;
		}
		if (article.getSuperChapterName().contains(mContext.getString(R.string.open_project))) {
			setRedTag(helper, R.string.project);
		}
		
		if (article.getSuperChapterName().contains(mContext.getString(R.string.navigation))) {
			setRedTag(helper, R.string.navigation);
		}
		
		if (article.getNiceDate().contains(mContext.getString(R.string.minute))
				|| article.getNiceDate().contains(mContext.getString(R.string.hour))
				|| article.getNiceDate().contains(mContext.getString(R.string.one_day))) {
			helper.getView(R.id.item_search_pager_tag_green_tv).setVisibility(View.VISIBLE);
			helper.setText(R.id.item_search_pager_tag_green_tv, R.string.text_new);
			helper.setTextColor(R.id.item_search_pager_tag_green_tv, ContextCompat.getColor(mContext, R.color.light_green));
			helper.setBackgroundRes(R.id.item_search_pager_tag_green_tv, R.drawable.shape_tag_green_background);
		}
	}
	
	private void setRedTag(KnowledgeHierarchyListViewHolder helper, @StringRes int tagName) {
		helper.getView(R.id.item_search_pager_tag_red_tv).setVisibility(View.VISIBLE);
		helper.setText(R.id.item_search_pager_tag_red_tv, tagName);
		helper.setTextColor(R.id.item_search_pager_tag_red_tv, ContextCompat.getColor(mContext, R.color.light_deep_red));
		helper.setBackgroundRes(R.id.item_search_pager_tag_red_tv, R.drawable.selector_tag_red_background);
	}
	
	
	public class KnowledgeHierarchyListViewHolder extends BaseViewHolder {
		
		@BindView(R.id.item_search_pager_group)
		CardView mItemSearchPagerGroup;
		@BindView(R.id.item_search_pager_like_iv)
		ImageView mItemSearchPagerLikeIv;
		@BindView(R.id.item_search_pager_title)
		TextView mItemSearchPagerTitle;
		@BindView(R.id.item_search_pager_author)
		TextView mItemSearchPagerAuthor;
		@BindView(R.id.item_search_pager_tag_green_tv)
		TextView mTagGreenTv;
		@BindView(R.id.item_search_pager_tag_red_tv)
		TextView mTagRedTv;
		@BindView(R.id.item_search_pager_chapterName)
		TextView mItemSearchPagerChapterName;
		@BindView(R.id.item_search_pager_niceDate)
		TextView mItemSearchPagerNiceDate;
		
		public KnowledgeHierarchyListViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
