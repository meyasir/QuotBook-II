package com.samirk433.quotebook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.samirk433.quotebook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Yasir on 10-May-18.
 */

public class AuthorDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> mItems;
    private View parentView;

    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final String FOOTER = "footer";

  /*  public AuthorDetailsAdapter(String[] data) {
        this.data = data;
    }*/

    public AuthorDetailsAdapter(Context context) {
        this.context = context;
        mItems = new ArrayList<>();
    }


    public void setItems(List<String> data) {
        data = new ArrayList<>();
        this.mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, String insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<String> data) {
        mItems.addAll(data);
        //(mItems.size() - 1) Used to get the last item/index of the (array)list
        notifyItemInserted(mItems.size() - 1);
    }

    public void addFooter() {
        mItems.add(FOOTER);
        notifyItemInserted(mItems.size() - 1);
    }

    public void removeFooter() {
        mItems.remove(mItems.size() - 1);
        notifyItemRemoved(mItems.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_author_details, parent, false);
            return new AuthorDetailsViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
            return new FooterViewHolderDetails(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AuthorDetailsViewHolder) {
            final AuthorDetailsAdapter.AuthorDetailsViewHolder recyclerViewHolder = (AuthorDetailsAdapter.AuthorDetailsViewHolder) holder;

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
            recyclerViewHolder.mView.startAnimation(animation);

            AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
            aa1.setDuration(400);
            AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
            aa.setDuration(400);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String s = mItems.get(position);
        if (s.equals(FOOTER)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class AuthorDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        private View mView;

        public AuthorDetailsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textView = mView.findViewById(R.id.tx_view_author_details);
            imageView = mView.findViewById(R.id.img_view_author_details);
        }
    }

    private class FooterViewHolderDetails extends RecyclerView.ViewHolder {
        private ProgressBar progress_bar_load_more;

        public FooterViewHolderDetails(View itemView) {
            super(itemView);
            progress_bar_load_more = itemView.findViewById(R.id.progress_bar_load_more);
        }
    }

}

