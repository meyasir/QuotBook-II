package com.samirk433.quotebook.data.entity;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Samir Khan on 4/15/2018.
 */

public class AuthorEntity {
    public static final String TAG = AuthorEntity.class.getSimpleName();

    public static final int ROW_COUNT = 2668;

    public static final String TABLE_NAME = "author";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_SUMMARY = "summary";
    public static final String COL_IMG_URL = "img_url";
    public static final String COL_DOB = "dob";
    public static final String COL_BIRTH_PLACE = "birth_place";
    public static final String COL_DOD = "dod";
    public static final String COL_WEBSITE = "website";
    public static final String COL_IS_FAV = "is_fav";


    public static final String TABLE_NAME_ALIAS = "a";
    public static final String COL_ID_ALIAS = "auth_id";
    public static final String COL_NAME_ALIAS = "auth_name";
    public static final String COL_SUMMARY_ALIAS = "auth_summary";
    public static final String COL_IMG_URL_ALIAS = "auth_img_url";
    public static final String COL_DOB_ALIAS = "auth_dob";
    public static final String COL_BIRTH_PLACE_ALIAS = "auth_birth_place";
    public static final String COL_DOD_ALIAS = "auth_dod";
    public static final String COL_WEBSITE_ALIAS = "auth_website";
    public static final String COL_IS_FAV_ALIAS = "auth_is_fav";


    public static String getCreateQuery() {
        Log.d(TAG, "getCreateQuery()");

        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT", TABLE_NAME,
                COL_ID, COL_NAME, COL_SUMMARY, COL_IMG_URL, COL_DOB, COL_BIRTH_PLACE, COL_DOD,
                COL_WEBSITE);
        return query;
    }

    public static String getSelectQuery(int... quoteIds) {
        Log.d(TAG, String.format("getSelectQuery()"));

        String whereClause;
        if (quoteIds.length == 1)
            whereClause = String.format("WHERE %s = %d ", AuthorEntity.COL_ID, quoteIds[0]);
        else {
            whereClause = "WHERE " + COL_ID + " IN (" +
                    Arrays.toString(quoteIds) + ")";
            whereClause = whereClause.replace("[", "");
            whereClause = whereClause.replace("]", "");
        }

        return createQuery(whereClause);
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
            case COL_NAME:
                strAlias = TABLE_NAME_ALIAS + "." + COL_NAME;
                break;
            case COL_SUMMARY:
                strAlias = TABLE_NAME_ALIAS + "." + COL_SUMMARY;
                break;
            case COL_IMG_URL:
                strAlias = TABLE_NAME_ALIAS + "." + COL_IMG_URL;
                break;
            case COL_DOB:
                strAlias = TABLE_NAME_ALIAS + "." + COL_DOB;
                break;
            case COL_BIRTH_PLACE:
                strAlias = TABLE_NAME_ALIAS + "." + COL_BIRTH_PLACE;
                break;
            case COL_DOD:
                strAlias = TABLE_NAME_ALIAS + "." + COL_DOD;
                break;
            case COL_WEBSITE:
                strAlias = TABLE_NAME_ALIAS + "." + COL_WEBSITE;
                break;
        }
        return strAlias;
    }

    private static String createQuery(String whereClaus) {
        Log.d(TAG, String.format("createQuery()"));

        String selectClause = String.format("SELECT * FROM %s", TABLE_NAME);
        return selectClause + " " + whereClaus;
    }
}
