package com.example.wanandroid.ui.hierarchy;

import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;

public class KnowledgeHierarchyFragment extends BaseFragment {
	
	private KnowledgeHierarchyViewModel mViewModel;
	
	public static KnowledgeHierarchyFragment newInstance() {
		return new KnowledgeHierarchyFragment();
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.knowledge_hierarchy_fragment;
	}
	
	@Override
	protected void initEventAndData() {
		mViewModel = ViewModelProviders.of(this).get(KnowledgeHierarchyViewModel.class);
	}
}