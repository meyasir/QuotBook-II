package com.samirk433.quotebook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samirk433.quotebook.R;
import com.samirk433.quotebook.adapter.CustomCategoryListAdapter;

public class CategoryListFragment extends
        Fragment {

    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerView;
    CustomCategoryListAdapter customCategoryListAdapter;


    public CategoryListFragment() {
        // Required empty public constructor
    }

    public static CategoryListFragment newInstance() {
        CategoryListFragment fragment = new CategoryListFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, null);

        String[] data = {"Love", "Romantic", "Universal", "Motivational", "Craze", "Care", "Funny", "Inspirational", "Risk",
                "Relation", "Boyhood", "Serious", "Moody", "Combo", "cat_15", "cat_16", "cat_17", "cat_18"};

        mRecyclerView = view.findViewById(R.id.rec_category_list_fragment);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        customCategoryListAdapter = new CustomCategoryListAdapter(data);
        mRecyclerView.setAdapter(customCategoryListAdapter);

        getActivity().setTitle("Team B");

        //To show Bottom Navigation view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

}
