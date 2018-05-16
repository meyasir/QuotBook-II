package com.samirk433.quotebook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.samirk433.quotebook.R;

public class CustomCategoryListAdapter extends RecyclerView.Adapter<CustomCategoryListAdapter.CustomViewHolderCat> {

    String[] data;

    public CustomCategoryListAdapter(String[] data) {
        this.data = data;
    }

    @Override
    public CustomViewHolderCat onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_category_list, parent, false);
        CustomViewHolderCat customViewHolderCat = new CustomViewHolderCat(view);
        return customViewHolderCat;
    }

    @Override
    public void onBindViewHolder(CustomViewHolderCat holder, int position) {

        String Filled = data[position];
        holder.textView.setText(Filled);
        //  holder.catImageView.setImageResource(R.drawable.ic_person_red_24dp);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomViewHolderCat extends RecyclerView.ViewHolder {

        TextView textView;

        // ImageView catImageView;
        public CustomViewHolderCat(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_cat_list);
            // catImageView = itemView.findViewById(R.id.img_view_category_list);
        }
    }
}
