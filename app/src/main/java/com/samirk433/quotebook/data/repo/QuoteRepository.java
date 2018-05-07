package com.samirk433.quotebook.data.repo;

import android.util.Log;

import com.samirk433.quotebook.data.model.AuthorModel;
import com.samirk433.quotebook.data.model.CategoryModel;
import com.samirk433.quotebook.data.model.QuoteModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Samir Khan on 4/17/2018.
 */

public class QuoteRepository implements Serializable {
    public static final String TAG = QuoteRepository.class.getSimpleName();

    private AuthorModel mAuthorModel;
    private QuoteModel mQuoteModel;
    private List<CategoryModel> mCategoryModels;

    public QuoteRepository(QuoteModel quoteModel, AuthorModel authorModel,
                           List<CategoryModel> categoryModels){
        Log.d(TAG, "QuoteRepository()");

        this.mQuoteModel = quoteModel;
        this.mAuthorModel = authorModel;
        this.mCategoryModels= categoryModels;
    }
    public AuthorModel getAuthorModel() {
        return mAuthorModel;
    }

    public void setAuthorModel(AuthorModel authorModel) {
        this.mAuthorModel = authorModel;
    }

    public QuoteModel getQuoteModel() {
        return mQuoteModel;
    }

    public void setQuoteModel(QuoteModel quoteModel) {
        this.mQuoteModel = quoteModel;
    }

    public List<CategoryModel> getCategoryModels() {
        return mCategoryModels;
    }

    public void setCategoryModels(List<CategoryModel> categoryModels) {
        this.mCategoryModels = categoryModels;
    }
}
