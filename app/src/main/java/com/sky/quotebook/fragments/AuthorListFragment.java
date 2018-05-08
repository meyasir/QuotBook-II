package com.sky.quotebook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.quotebook.R;
import com.sky.quotebook.adapter.CustomAuthorListAdapter;

public class AuthorListFragment extends Fragment {
    CustomAuthorListAdapter fragmentTestAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerView;

    public AuthorListFragment() {
        // Required empty public constructor
    }

    public static AuthorListFragment newInstance() {
        AuthorListFragment fragment = new AuthorListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_list, null);

        String[] data = {"Martin Luther King Jr.", "Lao-tzu", "Fran Lebowitz ", "C. S. Lewis ", "Abraham Lincoln ",
                "Groucho Marx ", "W. Somerset Maugham", "H. L. Mencken", "Mother Teresa", "Friedrich Nietzsche", "Auth_11"
                , "Auth_12", "Auth_13", "Auth_14", "Auth_15", "Auth_16", "Auth_17", "Auth_18"};

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rec_fragment_try_test_list);

        //animation applied
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        fragmentTestAdapter = new CustomAuthorListAdapter(data);
        mRecyclerView.setAdapter(fragmentTestAdapter);


        //To show Bottom Navigation view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
