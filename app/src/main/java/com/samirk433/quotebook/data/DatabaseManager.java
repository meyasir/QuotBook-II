package com.samirk433.quotebook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.samirk433.quotebook.data.entity.AuthorEntity;
import com.samirk433.quotebook.data.entity.CategoryEntity;
import com.samirk433.quotebook.data.entity.QuoteEntity;
import com.samirk433.quotebook.data.model.AuthorModel;
import com.samirk433.quotebook.data.model.CategoryModel;
import com.samirk433.quotebook.data.repo.QuoteRepository;
import com.samirk433.quotebook.util.RandomNumberGenerator;


import java.util.List;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class DatabaseManager {
    public static final String TAG = DatabaseManager.class.getSimpleName();

    private Context mContext;
    private DbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public DatabaseManager(Context context) {
        Log.d(TAG, "DatabaseManager()");

        this.mContext = context;
        mDbHelper = DbHelper.getInstance(context);
    }

    public boolean createDatabaseIfNotExist() {
        Log.d(TAG, "createDatabaseIfNotExist()");
        return mDbHelper.createDatabaseIfNotExist();
    }


    /*  QUOTES DATA RETRIEVING */

    public List<QuoteRepository> getQuotes(int number) {
        Log.d(TAG, "getQuotes()");
        int[] quotesId = RandomNumberGenerator.generate(0, QuoteEntity.ROW_COUNT, number);
        return getQuotesData(null, -1, quotesId);
    }

    public List<QuoteRepository> getQuotesByAuthorId(int authorId) {
        Log.d(TAG, "getQuotesByAuthorId()");
        return getQuotesData(AuthorEntity.COL_ID_ALIAS, authorId, -1);
    }

    public List<QuoteRepository> getQuotesByCatId(int catId) {
        Log.d(TAG, "getQuotesByCatId()");
        return getQuotesData(CategoryEntity.COL_ID_ALIAS, catId, -1);
    }

    public QuoteRepository getQuoteById(int quoteId) {
        Log.d(TAG, "getQuoteById()");
        List<QuoteRepository> list = getQuotesData(QuoteEntity.COL_ID_ALIAS, quoteId, -1);
        return list == null ? null : list.get(0);
    }

    public List<QuoteRepository> getFavQuotes() {
        Log.d(TAG, "getFavQuotes()");
        return getQuotesData(null, -1, -100);
    }

    public boolean setFavQuote(int authorId) {
        Log.d(TAG, String.format("setFavQuote(%d)", authorId));
        return makeEntityFavourite(QuoteEntity.TABLE_NAME, authorId);
    }


    /*  CATEGORIES DATA RETRIEVING */
    public List<CategoryModel> getCategories(int number) {
        Log.d(TAG, String.format("getCategories(%d)", number));

        int[] catIds = RandomNumberGenerator.generate(0, CategoryEntity.ROW_COUNT, number);
        return getCategoriesData(catIds);
    }

    public CategoryModel getCategoryById(int catId) {
        Log.d(TAG, String.format("getCategoryById(%d)", catId));
        List<CategoryModel> list = getCategoriesData(catId);
        return list == null ? null : list.get(0);
    }

    public List<CategoryModel> getFavCategories() {
        Log.d(TAG, "getFavCategories()");
        //id = -100, means get favourites of entity
        return getCategoriesData(-100);
    }

    public boolean setFavCategory(int catId) {
        Log.d(TAG, String.format("setFavCategory(%d)", catId));
        return makeEntityFavourite(CategoryEntity.TABLE_NAME, catId);
    }


    /*  AUTHORS DATA RETRIEVING */
    public List<AuthorModel> getAuthors(int number) {
        Log.d(TAG, String.format("getAuthors(%d)", number));
        int[] authorIds = RandomNumberGenerator.generate(0, AuthorEntity.ROW_COUNT, number);
        return getAuthorsData(authorIds);
    }

    public AuthorModel getAuthorById(int authorId) {
        Log.d(TAG, String.format("getAuthorById(%d)", authorId));
        List<AuthorModel> list = getAuthorsData(authorId);
        return list == null ? null : list.get(0);
    }

    public List<AuthorModel> getFavAuthors() {
        Log.d(TAG, "getFavAuthors()");
        //id = -100, means get favourites of entity
        return getAuthorsData(-100);
    }

    public boolean setFavAuthor(int authorId) {
        Log.d(TAG, String.format("setFavAuthor(%d)", authorId));
        return makeEntityFavourite(AuthorEntity.TABLE_NAME, authorId);
    }


    /*  PRIVATE METHODS */
    private List<QuoteRepository> getQuotesData(String colName, int colValue, int... quoteIds) {
        Log.d(TAG, "getQuotesData()");

        List<QuoteRepository> quoteRepos = null;
        try {
            mDatabase = mDbHelper.getReadableDatabase();

            String query;
            if (quoteIds.length == 1 && quoteIds[0] == -100)
                query = QuoteEntity.getSelectFavQuery();
            else
                query = QuoteEntity.getSelectQuery(colName, colValue, quoteIds);
            Cursor cursor = mDatabase.rawQuery(query, null);
            quoteRepos = QueryParser.parseQuotes(cursor);
        } catch (SQLiteException e) {
            Log.e(TAG, "SQLiteException:", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "SQLiteException:", e);
        } finally {
            mDatabase.close();
        }
        return quoteRepos;
    }

    //if catId = -100 then it's asking for fav catId
    private List<CategoryModel> getCategoriesData(int... catId) {
        Log.d(TAG, "getCategoriesData()");

        List<CategoryModel> list = null;
        try {
           mDatabase = mDbHelper.getReadableDatabase();

            /*
            * instead of making a new method asking for favourite cat
            * we use it with int value -100, since catId can't be -ive
            * */

            String query;
            if (catId.length == 1 && catId[0] == -100)
                query = CategoryEntity.getSelectFavQuery();
            else
                query = CategoryEntity.getSelectQuery(catId);

            Cursor cursor = mDatabase.rawQuery(query, null);
            list = QueryParser.parseCategories(cursor);
        } catch (SQLiteException e) {
            Log.e(TAG, "SQLiteException:", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "SQLiteException:", e);
        } finally {
            mDatabase.close();
        }
        return list;
    }


    private List<AuthorModel> getAuthorsData(int... authorIds) {
        Log.d(TAG, "getAuthorsData()");

        List<AuthorModel> list = null;
        try {
            mDatabase = mDbHelper.getReadableDatabase();

            String query;
            if (authorIds.length == 1 && authorIds[0] == -100)
                query = AuthorEntity.getSelectFavQuery();
            else
                query = AuthorEntity.getSelectQuery(authorIds);

            Cursor cursor = mDatabase.rawQuery(query, null);
            list = QueryParser.parseAuthors(cursor);
        } catch (SQLiteException e) {
            Log.e(TAG, "SQLiteException:", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "SQLiteException:", e);
        } finally {
            mDatabase.close();
        }
        return list;
    }


    // make entity favourite
    private boolean makeEntityFavourite(String tableName, int entityId) {
        Log.d(TAG, "makeEntityFavourite()");

        List<CategoryModel> list = null;
        try {
            mDatabase = mDbHelper.getReadableDatabase();

            /*
            * Since, Column names id and is_fav same in all
            * entities, this is why we don't need any decision
            * on behalf of table-name.
            * */


            ContentValues contentValues = new ContentValues(1);
            contentValues.put(CategoryEntity.COL_IS_FAV, 1);

            String whereClause = CategoryEntity.COL_ID + " = ?";
            int rows = mDatabase.update(tableName, contentValues, whereClause, new String[]{entityId + ""});

            if (rows > 0)
                return true;
        } catch (SQLiteException e) {
            Log.e(TAG, "SQLiteException:", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "SQLiteException:", e);
        } finally {
            mDatabase.close();
        }
        return false;
    }


}
