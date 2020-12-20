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

public class KnowledgeHierarchyListFragment extends Fragment {
	
	private KnowledgeHierarchyListViewModel mViewModel;
	
	public static KnowledgeHierarchyListFragment newInstance() {
		return new KnowledgeHierarchyListFragment();
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.knowledge_hierarchy_list_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = ViewModelProviders.of(this).get(KnowledgeHierarchyListViewModel.class);
		// TODO: Use the ViewModel
	}
	
}