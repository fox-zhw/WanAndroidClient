package com.example.wanandroid.ui.hierarchy.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.data.bean.hierarchy.KnowledgeHierarchyData;
import com.example.wanandroid.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 52512
 * @date 2021/1/20
 */
public class KnowledgeHierarchyAdapter extends BaseQuickAdapter<KnowledgeHierarchyData, KnowledgeHierarchyAdapter.KnowledgeHierarchyViewHolder> {
	
	public KnowledgeHierarchyAdapter(int layoutResId, @Nullable List<KnowledgeHierarchyData> data) {
		super(layoutResId, data);
	}
	
	@Override
	protected void convert(KnowledgeHierarchyViewHolder helper, KnowledgeHierarchyData item) {
		if(item.getName() == null) {
			return;
		}
		helper.setText(R.id.item_knowledge_hierarchy_title, item.getName());
		helper.setTextColor(R.id.item_knowledge_hierarchy_title, CommonUtils.randomColor());
		if (item.getChildren() == null) {
			return;
		}
		StringBuilder content = new StringBuilder();
		for (KnowledgeHierarchyData data: item.getChildren()) {
			content.append(data.getName()).append("   ");
		}
		helper.setText(R.id.item_knowledge_hierarchy_content, content.toString());
	}
	
	public class KnowledgeHierarchyViewHolder extends BaseViewHolder {
		
		@BindView(R.id.item_knowledge_hierarchy_title)
		TextView mTitle;
		@BindView(R.id.item_knowledge_hierarchy_content)
		TextView mContent;
		
		public KnowledgeHierarchyViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}