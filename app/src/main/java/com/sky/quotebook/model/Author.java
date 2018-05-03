package com.sky.quotebook.model;

import android.content.Context;

/**
 * created for the purpose to RecyclerViewItemClickListener, Not used in this code
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
