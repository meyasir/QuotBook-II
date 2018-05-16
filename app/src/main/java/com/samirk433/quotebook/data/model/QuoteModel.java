package com.samirk433.quotebook.data.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class QuoteModel implements Serializable {
    private static final String TAG = QuoteModel.class.getSimpleName();

    private int mId = -1, mAuthorId = -1;
    private String mText;
    private boolean mIsFav;

    public QuoteModel() {
        Log.d(TAG, "QuoteModel");
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(int authorId) {
        this.mAuthorId = authorId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public boolean isFav() {
        return mIsFav;
    }

    public void setFav(boolean fav) {
        mIsFav = fav;
    }
}
