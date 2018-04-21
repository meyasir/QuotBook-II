package com.samirk433.quotebook.data.entity;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class QuoteEntity {
    public static final String TAG = QuoteEntity.class.getSimpleName();

    public static final int ROW_COUNT = 9264;

    public static final String TABLE_NAME = "quote";
    public static final String COL_ID = "id";
    public static final String COL_TEXT = "quote_text";
    public static final String COL_AUTHOR_ID = "author_id";
    public static final String COL_IS_FAV = "is_fav";

    public static final String TABLE_NAME_ALIAS = "q";
    public static final String COL_ID_ALIAS = "quote_id";
    public static final String COL_TEXT_ALIAS = "quote_text";
    public static final String COL_AUTHOR_ID_ALIAS = "author_id";
    public static final String COL_IS_FAV_ALIAS = "quote_is_fav";

    public static String getCreateQuery() {
        Log.d(TAG, "getCreateQuery()");

        String query = String.format("CREATE TABLE %s ( `%s` INTEGER PRIMARY KEY AUTOINCREMENT, `%s` TEXT, `%s` INTEGER )",
                TABLE_NAME, COL_ID, COL_TEXT, COL_AUTHOR_ID);
        return query;
    }


    public static String getSelectQuery(String colName, int colValue, int... quoteIds) {
        Log.d(TAG, String.format("getSelectQuery()"));

        String whereClause;
        if (colName == null || colValue < 0) {
            if (quoteIds.length == 1)
                whereClause = String.format("WHERE %s <= %d ", COL_ID_ALIAS, quoteIds[0]);
            else {
                whereClause = String.format("WHERE " + COL_ID_ALIAS + " IN ("
                        + Arrays.toString(quoteIds) + ")");
                //Arrays.toString() also adds [] to strings
                whereClause = whereClause.replace("[", "");
                whereClause = whereClause.replace("]", "");
            }
        } else
            whereClause = String.format("WHERE %s = %d ", colName, colValue);


        return createQuery(whereClause);
    }

    public static String getSelectFavQuery() {
        Log.d(TAG, String.format("getSelectFavQuery()"));

        String whereClause = String.format("WHERE %s = 1 ", COL_IS_FAV_ALIAS);
        return createQuery(whereClause);
    }

    public static String getAddtoFavQuery(int catId) {
        Log.d(TAG, String.format("getAddFavQuery()"));

        String query = "UPDATE " + TABLE_NAME + " SET " + COL_IS_FAV + " = 1 WHERE " + COL_ID + " =" + catId;
        return query;
    }

    //get tab_alias.col
    public static String getRawAlias(String col) {
        Log.d(TAG, String.format("getRawAlias(%s)", col));

        String strAlias = null;
        switch (col) {
            case COL_ID:
                strAlias = TABLE_NAME_ALIAS + "." + COL_ID;
                break;
            case COL_TEXT:
                strAlias = TABLE_NAME_ALIAS + "." + COL_TEXT;
                break;
            case COL_AUTHOR_ID:
                strAlias = TABLE_NAME_ALIAS + "." + COL_AUTHOR_ID;
                break;
            case COL_IS_FAV:
                strAlias = TABLE_NAME_ALIAS + "." + COL_IS_FAV;
        }
        return strAlias;
    }

    private static String createQuery(String whereClause) {
        Log.d(TAG, String.format("createQuery()"));

        String colQuoteId, colQuoteText, colQuoteAuthId, colQuoteIsFav, colCatId, colCatTitle, colCatIsFav,
                colAuthId, colAuthName, colAuthSummary, colAuthImgUrl, colAuthDob,
                colAuthBirthPlace, colAuthDod, colAuthWebsite, colAuthIsFav,
                colQcQuoteId, colQcCatId;

        colQuoteId = getRawAlias(COL_ID);
        colQuoteText = getRawAlias(COL_TEXT);
        colQuoteAuthId = getRawAlias(COL_AUTHOR_ID);
        colQuoteIsFav = getRawAlias(COL_IS_FAV);

        colCatId = CategoryEntity.getRawAlias(CategoryEntity.COL_ID);
        colCatTitle = CategoryEntity.getRawAlias(CategoryEntity.COL_TITLE);
        colCatIsFav = CategoryEntity.getRawAlias(CategoryEntity.COL_IS_FAV);

        colAuthId = AuthorEntity.getRawAlias(AuthorEntity.COL_ID);
        colAuthName = AuthorEntity.getRawAlias(AuthorEntity.COL_NAME);
        colAuthSummary = AuthorEntity.getRawAlias(AuthorEntity.COL_SUMMARY);
        colAuthImgUrl = AuthorEntity.getRawAlias(AuthorEntity.COL_IMG_URL);
        colAuthDob = AuthorEntity.getRawAlias(AuthorEntity.COL_DOB);
        colAuthBirthPlace = AuthorEntity.getRawAlias(AuthorEntity.COL_BIRTH_PLACE);
        colAuthDod = AuthorEntity.getRawAlias(AuthorEntity.COL_DOD);
        colAuthWebsite = AuthorEntity.getRawAlias(AuthorEntity.COL_WEBSITE);
        colAuthIsFav = CategoryEntity.getRawAlias(AuthorEntity.COL_IS_FAV);

        colQcCatId = QuoteCategoryEntity.getRawAlias(QuoteCategoryEntity.COL_CAT_ID);
        colQcQuoteId = QuoteCategoryEntity.getRawAlias(QuoteCategoryEntity.COL_QUOTE_ID);

        /*
        * select q.id, q.quote_text, q._is_fav, c.id as cat_id, c.title as cat_title, c.is_fav as cat_as_fav
        * a.id as auth_id, a.name as auth_name, a.summary as auth_summary, a.img_url as auth_img_url, a.dob as auth_dob,
        * a.birth_place as auth_birth_place , a.dod as auth_dod, a.website as auth_website, a.is_fav as auth_is_fav
        * from quote as q
        * left join quote_category as qc on q.id = qc.quote_id
        * left join category as c on  c.id  = qc.cat_id
        * left join author as a on q.author_id = a.id
                    where q.id <3
                    group by c.id
					order by q.id
        * */


        String query = String.format("SELECT %s AS %s, %s AS %s, %s AS %s, %s AS %s, %s AS %s, %s AS %s, " +
                        "%s AS %s, %s AS %s, %s AS %s, %s AS %s, %s AS %s, " +
                        "%s AS %s, %s AS %s, %s AS %s, %s AS %s " +
                        "FROM %s AS %s " +
                        "LEFT JOIN %s AS %s ON %s = %s " +
                        "LEFT JOIN %s AS %s ON %s = %s " +
                        "LEFT JOIN %s AS %s ON %s = %s " +
                        whereClause +
                        "group by %s " +
                        "order by %s ",
                colQuoteId, COL_ID_ALIAS, colQuoteText, COL_TEXT_ALIAS, colQuoteIsFav, COL_IS_FAV_ALIAS,
                colCatId, CategoryEntity.COL_ID_ALIAS, colCatTitle, CategoryEntity.COL_TITLE_ALIAS,
                colCatIsFav, CategoryEntity.COL_IS_FAV_ALIAS,

                colAuthId, AuthorEntity.COL_ID_ALIAS, colAuthName, AuthorEntity.COL_NAME_ALIAS,
                colAuthSummary, AuthorEntity.COL_SUMMARY_ALIAS, colAuthImgUrl, AuthorEntity.COL_IMG_URL_ALIAS,
                colAuthDob, AuthorEntity.COL_DOB_ALIAS,

                colAuthBirthPlace, AuthorEntity.COL_BIRTH_PLACE_ALIAS,
                colAuthDod, AuthorEntity.COL_DOD_ALIAS, colAuthWebsite, AuthorEntity.COL_WEBSITE_ALIAS,
                colAuthIsFav, AuthorEntity.COL_IS_FAV_ALIAS,

                TABLE_NAME, TABLE_NAME_ALIAS,
                QuoteCategoryEntity.TABLE_NAME, QuoteCategoryEntity.TABLE_NAME_ALIAS, colQuoteId, colQcQuoteId,
                CategoryEntity.TABLE_NAME, CategoryEntity.TABLE_NAME_ALIAS, colCatId, colQcCatId,
                AuthorEntity.TABLE_NAME, AuthorEntity.TABLE_NAME_ALIAS, colQuoteAuthId, colAuthId,
                /*colQuoteId, number,*/
                colQuoteId,
                colQcQuoteId);
        return query;
    }
}
