package com.samirk433.quotebook.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.samirk433.quotebook.R;
import com.samirk433.quotebook.utils.constant.AppConstant;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by samirk433 on 5/7/2018.
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();


    public static String getFulFileName(Context context) {
        Log.d(TAG, "getFulFileName()");
        return getDirectory(context) + "/" + getFileName();
    }

    private static String getFileName() {
        Log.d(TAG, "getFileName");

        long millis = System.currentTimeMillis();
        return millis + AppConstant.IMAGE_FILE_EXTENTION;
    }

    private static String getDirectory(Context context) {
        Log.d(TAG, "createDirectory");

        String path = Environment.getExternalStorageDirectory() + "/" +
                context.getResources().getString(R.string.app_name);
        File file = new File(path);
        file.mkdirs();
        return path;
    }
}
