package com.samirk433.quotebook.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.quotebook.samirk433.quotebook.R;
import com.samirk433.quotebook.utils.constant.AppConstant;

/**
 * Created by samirk433 on 4/22/2018.
 */

public class TypefaceUtils {
    private static final String TAG = TypefaceUtils.class.getSimpleName();

    private static final String FONT_BASE_PATH = "fonts/";

    public static String[] getTypefaceNames() {
        Log.d(TAG, "getTypeNames");

        String[] typefaces = new String[]{
                AppConstant.FONT_ANTON, AppConstant.FONT_ARCHIVO, AppConstant.FONT_COURGETTE,
                AppConstant.FONT_DANCING_SCRIPT, AppConstant.FONT_FASTER_ONE,
                AppConstant.FONT_KRONA_ONE, AppConstant.FONT_MERRIWEATHER, AppConstant.FONT_NOSIFER,
                AppConstant.FONT_SOURCE_SANS_PRO, AppConstant.FONT_SOUrCE_SANS_PRO_LIGHT
        };

        return typefaces;
    }

    public static Typeface getTypeface(Context context, String name) {
        return getTypefaceFromAssets(context, name);
    }

    public static Typeface getAnton(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_ANTON);
    }

    public static Typeface getArchivo(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_ARCHIVO);
    }

    public static Typeface getCourgette(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_COURGETTE);
    }

    public static Typeface getDancingScript(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_DANCING_SCRIPT);
    }

    public static Typeface getFasterOne(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_FASTER_ONE);
    }

    public static Typeface getKronaOne(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_KRONA_ONE);
    }

    public static Typeface getMerriweather(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_MERRIWEATHER);
    }

    public static Typeface getNosifer(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_NOSIFER);
    }

    public static Typeface getSourceSans(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_SOURCE_SANS_PRO);
    }

    public static Typeface getSourceSansLight(Context context) {
        return getTypefaceFromAssets(context, AppConstant.FONT_SOUrCE_SANS_PRO_LIGHT);
    }


    private static Typeface getTypefaceFromAssets(Context context, String fontName) {
        String st =  getFontPath(fontName);
        return Typeface.createFromAsset(context.getAssets(), st);
    }

    private static String getFontPath(String name) {
        return FONT_BASE_PATH + name;
    }
}
