package com.samirk433.quotebook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by samirk433 on 5/7/2018.
 */

public class PreferenceUtils {
    private static final String TAG = PreferenceUtils.class.getSimpleName();

    private final String PREFS_FILE = "com.samirk433.quotebook.prefs";

    private Context mContext;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public PreferenceUtils(Context context) {
        Log.d(TAG, "PreferenceUtils()");

        this.mContext = context;
        mPrefs = mContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }
}
