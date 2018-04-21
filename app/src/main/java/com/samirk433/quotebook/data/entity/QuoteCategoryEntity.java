package com.samirk433.quotebook.data.entity;

import android.util.Log;

/**
 * Created by Samir Khan on 4/18/2018.
 */

public class QuoteCategoryEntity {
    private static final String TAG = QuoteCategoryEntity.class.getSimpleName();

    public static final String TABLE_NAME = "quote_category";
    public static final String COL_ID = "id";
    public static final String COL_QUOTE_ID = "quote_id";
    public static final String COL_CAT_ID = "cat_id";

    public static final String TABLE_NAME_ALIAS = "qc";

    public static String getRawAlias(String col) {
        Log.d(TAG, String.format("getRawAlias(%s)", col));

        String strAlias = null;
        switch (col) {
            case COL_ID:
                strAlias = TABLE_NAME_ALIAS + "." + COL_ID;
                break;
            case COL_QUOTE_ID:
                strAlias = TABLE_NAME_ALIAS + "." + COL_QUOTE_ID;
                break;
            case COL_CAT_ID:
                strAlias = TABLE_NAME_ALIAS + "." + COL_CAT_ID;
                break;
        }
        return strAlias;
    }
}
