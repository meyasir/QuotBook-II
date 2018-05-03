package com.sky.quotebook.model;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.sky.quotebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created for the purpose to RecyclerViewItemClickListener, Not used in this app up-till now
 */

public class AuthorManager extends Application {


    public final static String NAME_PREFIX = "name_";
    public final static String DESC_PREFIX = "desc_";

    private Context context;
    private static String[] authorkeyArray = {""};
    private static AuthorManager mInstance;
    private List<Author> authors;

    public AuthorManager(Context c) {
        this.context = c;
        this.authorkeyArray = c.getResources().getStringArray(R.array.author_keys);
    }

    public static AuthorManager getmInstance(Context c) {
        if (mInstance == null) {mInstance = new AuthorManager(c);}
        return mInstance;
    }

    public List<Author> getAuthors() {
        if (authors == null) {
            authors = new ArrayList<Author>();
            for (String AuthorKey : authorkeyArray) {
                Author city = new Author();

                Resources res = context.getResources();
                String packageName = context.getPackageName();
                int id = res.getIdentifier(NAME_PREFIX + AuthorKey, "string", packageName);
                city.name = context.getString(id);
                city.description = context.getString(context.getResources().getIdentifier(DESC_PREFIX + AuthorKey, "string", context.getPackageName()));
                city.imageName = AuthorKey.toLowerCase();
                authors.add(city);
            }
        }
        return  authors;
    }

}
