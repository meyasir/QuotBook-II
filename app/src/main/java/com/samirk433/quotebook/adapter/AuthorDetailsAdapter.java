package com.samirk433.quotebook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samirk433.quotebook.R;

import java.util.zip.Inflater;

/**
 * Created by Yasir on 10-May-18.
 */

public class AuthorDetailsAdapter extends RecyclerView.Adapter<AuthorDetailsAdapter.AuthorDetailsViewHolder>{

    String [] data;

    public AuthorDetailsAdapter(String [] data){this.data = data;}


    @Override
    public AuthorDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_author_details, parent, false);
        AuthorDetailsViewHolder authorDetailsViewHolder = new AuthorDetailsViewHolder(view);
        return authorDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(AuthorDetailsViewHolder holder, int position) {
        String Filled = data[position];
        holder.textView.setText(Filled);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class AuthorDetailsViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public AuthorDetailsViewHolder(View itemView) {
            super(itemView);
             textView = itemView.findViewById(R.id.tx_view_author_details);
             imageView = itemView.findViewById(R.id.img_view_author_details);
        }
    }
}
