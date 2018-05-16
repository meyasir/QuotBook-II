package com.samirk433.quotebook.data.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class CategoryModel implements Serializable {
    private static final String TAG = CategoryModel.class.getSimpleName();

    private int mId;
    private String mTitle;
    private boolean mIsFav;

    public CategoryModel(int id, String title, boolean isFav){
        Log.d(TAG, "CategoryModel");
        this.mId =id;
        this.mTitle = title;
        this.mIsFav = isFav;
    }
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean isFav() {
        return mIsFav;
    }

    public void setFav(boolean fav) {
        mIsFav = fav;
    }
}
