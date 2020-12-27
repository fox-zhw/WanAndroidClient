package com.example.wanandroid.ui.hierarchy;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.base.fragment.BaseRootFragment;

import me.yokeyword.fragmentation.SupportFragment;

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