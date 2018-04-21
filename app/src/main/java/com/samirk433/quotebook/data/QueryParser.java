package com.samirk433.quotebook.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.samirk433.quotebook.data.entity.AuthorEntity;
import com.samirk433.quotebook.data.entity.CategoryEntity;
import com.samirk433.quotebook.data.entity.QuoteEntity;
import com.samirk433.quotebook.data.model.AuthorModel;
import com.samirk433.quotebook.data.model.CategoryModel;
import com.samirk433.quotebook.data.model.QuoteModel;
import com.samirk433.quotebook.data.repo.QuoteRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samir Khan on 4/18/2018.
 */

public class QueryParser {
    private static final String TAG = QueryParser.class.getSimpleName();

    public static List<QuoteRepository> parseQuotes(Cursor cursor) throws SQLiteException,
            NullPointerException {
        Log.d(TAG, "parseQuotes");

        if (cursor == null)
            throw new NullPointerException("Cursor is NULL");

        if (cursor.getCount() < 1)
            return null;

        List<QuoteRepository> quoteRepos = new ArrayList<>();
        List<CategoryModel> catModels = new ArrayList<>();
        QuoteModel quoteModel = null;
        AuthorModel authorModel;

        int quoteId = -1, catId = -1, authId = -1, isQuoteFav = -1,
                isCatFav = -1, isAuthFav = -1;
        String catTitle, quoteText;
        String authName, authDob, authBirthPlace, authDod,
                authSummary, authWebsite, authImgUrl;

        cursor.moveToFirst();
        do {
            quoteId = cursor.getInt(cursor.getColumnIndex(QuoteEntity.COL_ID_ALIAS));
            catId = cursor.getInt(cursor.getColumnIndex(CategoryEntity.COL_ID_ALIAS));
            catTitle = cursor.getString(cursor.getColumnIndex(CategoryEntity.COL_TITLE_ALIAS));
            isCatFav = cursor.getInt(cursor.getColumnIndex(CategoryEntity.COL_IS_FAV_ALIAS));

            //Keep in mind that, 1st time quoteModel is NULL
                    /*
                    *   Quotes are retrieving from db with respect to categories
                    *   so, if a quote belongs to 10 cat, it will be retrieving
                    *   10 times.
                    *
                    *   Here, we will either add full quote or only cat to the list
                    *   (if quote already exists)
                    */
            if (quoteModel != null)
                if (quoteModel.getId() != -1 && quoteId == quoteModel.getId()) {
                    Log.d(TAG, quoteId + " quote skip");
                    catModels.add(new CategoryModel(catId, catTitle, isCatFav == 1 ? true : false));
                    continue;
                }
            Log.d(TAG, quoteId + " quote added");
            quoteModel = new QuoteModel();
            authorModel = new AuthorModel();
            catModels = new ArrayList<>();

            quoteText = cursor.getString(cursor.getColumnIndex(QuoteEntity.COL_TEXT_ALIAS));
            isQuoteFav = cursor.getInt(cursor.getColumnIndex(QuoteEntity.COL_IS_FAV_ALIAS));

            authId = cursor.getInt(cursor.getColumnIndex(AuthorEntity.COL_ID_ALIAS));
            authName = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_NAME_ALIAS));
            authSummary = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_SUMMARY_ALIAS));
            authImgUrl = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_IMG_URL_ALIAS));
            authDob = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_DOB_ALIAS));
            authBirthPlace = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_BIRTH_PLACE_ALIAS));
            authDod = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_DOB_ALIAS));
            authWebsite = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_WEBSITE_ALIAS));
            isAuthFav = cursor.getInt(cursor.getColumnIndex(AuthorEntity.COL_IS_FAV_ALIAS));

            quoteModel.setText(quoteText);
            quoteModel.setAuthorId(authId);
            quoteModel.setId(quoteId);
            quoteModel.setFav(isQuoteFav == 1 ? true : false);

            authorModel.setId(authId);
            authorModel.setName(authName);
            authorModel.setDob(authDob);
            authorModel.setBirthPlace(authBirthPlace);
            authorModel.setDod(authDod);
            authorModel.setSummary(authSummary);
            authorModel.setImgUrl(authImgUrl);
            authorModel.setWebsite(authWebsite);
            quoteModel.setFav(isAuthFav == 1 ? true : false);

            catModels.add(new CategoryModel(catId, catTitle, isCatFav == 1 ? true : false));
            quoteRepos.add(new QuoteRepository(quoteModel, authorModel, catModels));
        } while (cursor.moveToNext());

        return quoteRepos;
    }

    public static List<CategoryModel> parseCategories(Cursor cursor) throws SQLiteException,
            NullPointerException {
        Log.d(TAG, "parseCategories()");

        if (cursor == null)
            throw new NullPointerException("Cursor is NULL");

        if (cursor.getCount() < 1)
            return null;

        List<CategoryModel> list = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(cursor.getColumnIndex(CategoryEntity.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(CategoryEntity.COL_TITLE));
            int isFav = cursor.getInt(cursor.getColumnIndex(CategoryEntity.COL_IS_FAV));
            list.add(new CategoryModel(id, title, (isFav == 1 ? true : false)));
        } while (cursor.moveToNext());
        return list;
    }

    public static List<AuthorModel> parseAuthors(Cursor cursor) throws SQLiteException,
            NullPointerException {
        Log.d(TAG, "parseAuthors()");

        if (cursor == null)
            throw new NullPointerException("Cursor is NULL");

        if (cursor.getCount() < 1)
            return null;

        List<AuthorModel> list = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(cursor.getColumnIndex(AuthorEntity.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_NAME));
            String summary = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_SUMMARY));
            String imgUrl = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_IMG_URL));
            String dob = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_DOB));
            String birthPlace = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_BIRTH_PLACE));
            String dod = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_DOD));
            String website = cursor.getString(cursor.getColumnIndex(AuthorEntity.COL_WEBSITE));
            int isFav = cursor.getInt(cursor.getColumnIndex(AuthorEntity.COL_IS_FAV));

            AuthorModel authorModel = new AuthorModel();
            authorModel.setId(id);
            authorModel.setName(name);
            authorModel.setSummary(summary);
            authorModel.setImgUrl(imgUrl);
            authorModel.setDob(dob);
            authorModel.setBirthPlace(birthPlace);
            authorModel.setDod(dod);
            authorModel.setWebsite(website);
            authorModel.setFav(isFav == 1 ? true : false);

            list.add(authorModel);
        } while (cursor.moveToNext());
        return list;
    }
}



