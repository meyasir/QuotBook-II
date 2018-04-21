package com.samirk433.quotebook.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Samir Khan on 4/15/2018.
 */

/*
*   SINGLETON FOR DATABASE OBJECTS
* */
public class DbHelper extends SQLiteOpenHelper {
    public static final String TAG = DbHelper.class.getSimpleName();

    private static final String DB_NAME = "quotes.db";
    public static final int DB_VERSION = 1;
    private static String PATH = "";

    private static DbHelper sHelper;

    private Context mContext;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        Log.d(TAG, "DbHelper()");
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade()");
    }

    public boolean createDatabaseIfNotExist() {
        Log.d(TAG, "createDatabaseIfNotExist()");

        try {
            if (!exists() && !isDataLoaded()) {
                this.getReadableDatabase();
                init();
                return true;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while creating database.", e);
        } finally {
            this.close();
        }
        return false;
    }

    private boolean exists() throws IOException {
        Log.d(TAG, "exists()");
        File file = new File((PATH + DB_NAME));
        return file.exists();
    }

    private boolean isDataLoaded(){
        Log.d(TAG,"isDataLoaded()");
        String query = "SELECT * from quote";
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor c = database.rawQuery(query, null);
            if(c == null)
                throw  new SQLiteException("Cursor is NULL");
            return true;
        }catch (SQLiteException e){
        }
        return false;
    }
    private void init() throws IOException {
        Log.d(TAG, "init()");


        InputStream mInputStream = mContext.getAssets().open(DB_NAME);
        OutputStream mOutputStream = new FileOutputStream((PATH + DB_NAME));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = mInputStream.read(buffer)) > 0) {
            mOutputStream.write(buffer, 0, length);
        }

        mOutputStream.flush();
        mOutputStream.close();
        mInputStream.close();
    }

    public static DbHelper getInstance(final Context context) {
        Log.d(TAG, "hit getInstance()");

        PATH = String.format("/data/data/%s/databases/", context.getPackageName());
        if (sHelper == null)
            synchronized (DbHelper.class) {
                if (sHelper == null)
                    sHelper = new DbHelper(context);
            }
        return sHelper;
    }
}
