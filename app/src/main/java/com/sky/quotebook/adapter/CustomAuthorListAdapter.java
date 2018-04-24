package com.sky.quotebook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.quotebook.R;
import com.sky.quotebook.interfaces.ItemClickListener;
import com.sky.quotebook.model.Author;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasir on 16-Apr-18.
 */

public class CustomAuthorListAdapter extends RecyclerView.Adapter<CustomAuthorListAdapter.FragmentCustomViewHolder> {

    String[] data;

    public CustomAuthorListAdapter(String[] data) {
        this.data = data;
    }

    @Override
    public FragmentCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_author_list, parent, false);
        FragmentCustomViewHolder fragmentCustomViewHolder = new FragmentCustomViewHolder(view);
        return fragmentCustomViewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentCustomViewHolder holder, int position) {

        String Filled = data[position];
        holder.textViewAuthorBio.setText(Filled);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class FragmentCustomViewHolder extends RecyclerView.ViewHolder  {

        TextView textViewAuthorBio;
        CardView cardView;
        ImageView authorImage;

        public FragmentCustomViewHolder(View itemView) {
            super(itemView);

            textViewAuthorBio = (TextView) itemView.findViewById(R.id.text_author_bio);
            authorImage = (ImageView) itemView.findViewById(R.id.imgView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_item_recycler_view_author_list);
            itemView.setTag(itemView);
        }
    }
}
