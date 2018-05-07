package com.samirk433.quotebook.utils;

import android.util.Log;

import com.samirk433.quotebook.utils.constant.AppConstant;

/**
 * Created by samirk433 on 4/22/2018.
 */

public class StringUtils {
    private static final String TAG = StringUtils.class.getSimpleName();

    public static String getSampleFontName(String fontName) {
        Log.d(TAG, "getSampleFontName()");

        if (fontName == null)
            return "Default";

        String[] pathParts = fontName.split("/");
        if (pathParts.length < 2)
            return "Default";

        String[] nameParts = pathParts[1].split("-");

        if (nameParts.length == 0)
            return "Default";

        if (nameParts[0].contains("_"))
            return nameParts[0].replace("_", " ");
        return nameParts[0];
    }
}
