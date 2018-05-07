package com.samirk433.quotebook.data.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class AuthorModel implements Serializable {
    private static final String TAG = AuthorModel.class.getSimpleName();

    private int mId;
    private String mName, mWebsite, mDob, mDod, mBirthPlace, mSummary, mImgUrl;
    private boolean mIsFav;

    public AuthorModel() {
        Log.d(TAG, "AuthorModel()");
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        this.mWebsite = website;
    }

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        this.mDob = dob;
    }

    public String getDod() {
        return mDod;
    }

    public void setDod(String dod) {
        this.mDod = dod;
    }

    public String getBirthPlace() {
        return mBirthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.mBirthPlace = birthPlace;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.mImgUrl = imgUrl;
    }

    public boolean isFav() {
        return mIsFav;
    }

    public void setFav(boolean fav) {
        mIsFav = fav;
    }
}
