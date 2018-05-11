package com.samirk433.quotebook.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samirk433.quotebook.R;

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
        holder.authorImage.setImageResource(R.drawable.ic_person_red_24dp);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class FragmentCustomViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAuthorBio;
        LinearLayout linearLayout;
        ImageView authorImage;

        public FragmentCustomViewHolder(View itemView) {
            super(itemView);

            textViewAuthorBio = (TextView) itemView.findViewById(R.id.text_author_bio);
            authorImage = (ImageView) itemView.findViewById(R.id.img_view_author_list);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.card_view_item_recycler_view_author_list);
            itemView.setTag(itemView);
        }
    }
}
