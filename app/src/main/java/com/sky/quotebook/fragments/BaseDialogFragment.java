package com.sky.quotebook.fragments;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

/**
 * Created by Yasir on 24-Apr-18.
 */

public abstract class BaseDialogFragment<T> extends DialogFragment {
    private T mActivityInstance;

    public final T getActivityInstance() {
        return mActivityInstance;
    }

    @Override
    public void onAttach(Activity activity) {
        mActivityInstance = (T) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityInstance = null;
    }
}
