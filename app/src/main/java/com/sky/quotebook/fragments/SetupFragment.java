package com.sky.quotebook.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.quotebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupFragment extends Fragment {


    public SetupFragment() {
        // Required empty public constructor
    }

    public static SetupFragment newInstance() {
        SetupFragment setupFragment = new SetupFragment();
        return setupFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

}
