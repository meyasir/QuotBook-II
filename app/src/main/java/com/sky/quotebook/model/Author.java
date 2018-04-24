package com.sky.quotebook.model;

import android.content.Context;

/**
 * Created by Yasir on 17-Apr-18.
 */

public class Author {
    public String name;
    public String description;
    public String imageName;

    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
