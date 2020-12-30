package com.example.wanandroid.ui.hierarchy;

import androidx.lifecycle.ViewModelProviders;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseFragment;
import com.example.wanandroid.base.fragment.BaseRootFragment;

public class KnowledgeHierarchyFragment extends BaseRootFragment {
	
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