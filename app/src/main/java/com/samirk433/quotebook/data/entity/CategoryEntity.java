package com.samirk433.quotebook.data.entity;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class CategoryEntity {
    public static final String TAG = CategoryEntity.class.getSimpleName();

    public static final int ROW_COUNT = 11824;

    public static final String TABLE_NAME = "category";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_IS_FAV = "is_fav";


    public static final String TABLE_NAME_ALIAS = "c";
    public static final String COL_ID_ALIAS = "cat_id";
    public static final String COL_TITLE_ALIAS = "cat_title";
    public static final String COL_IS_FAV_ALIAS = "cat_is_fav";

    public static String getCreateQuery() {
        Log.d(TAG, "getCreateQuery()");

        String query = String.format("CREATE TABLE %s ( `%s` INTEGER PRIMARY KEY AUTOINCREMENT, `%s` TEXT )",
                TABLE_NAME, COL_ID, COL_TITLE);
        return query;
    }

    public static String getSelectQuery(int... catId) {
        Log.d(TAG, String.format("getSelectQuery()"));

        String whereClaus;
        if (catId.length == 1)
            whereClaus = String.format("WHERE %s = %d ", CategoryEntity.COL_ID, catId[0]);
        else {
            whereClaus = "WHERE " + CategoryEntity.COL_ID + " IN (" +
                    Arrays.toString(catId) + ")";
            whereClaus = whereClaus.replace("[", "");
            whereClaus = whereClaus.replace("]", "");
        }

        return createQuery(whereClaus);
    }

    public static String getSelectFavQuery() {
        Log.d(TAG, String.format("getSelectFavQuery()"));

        String whereClause = String.format("WHERE is_fav = 1 ");
        return createQuery(whereClause);
    }

    public static String getAddtoFavQuery(int catId){
        Log.d(TAG, String.format("getAddFavQuery()"));

        String query = "UPDATE " + TABLE_NAME + " SET " + COL_IS_FAV + " = 1 WHERE " + COL_ID + " =" +catId;
        return query;
    }

    public static String getRawAlias(String col) {
        Log.d(TAG, String.format("getRawAlias(%s)", col));

        String strAlias = null;
        switch (col) {
            case COL_ID:
                strAlias = TABLE_NAME_ALIAS + "." + COL_ID;
                break;
            case COL_TITLE:
                strAlias = TABLE_NAME_ALIAS + "." + COL_TITLE;
                break;
            case COL_IS_FAV:
                strAlias = TABLE_NAME_ALIAS + "." + COL_IS_FAV;
                break;
        }
        return strAlias;
    }

    private static String createQuery(String whereClause) {
        Log.d(TAG, String.format("createQuery()"));

        String selectClause = String.format("SELECT * FROM %s", TABLE_NAME);
        return selectClause + " " + whereClause;
    }
}
