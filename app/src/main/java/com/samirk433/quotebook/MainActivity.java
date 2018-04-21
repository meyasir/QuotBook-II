package com.samirk433.quotebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.quotebook.samirk433.quotebook.R;
import com.samirk433.quotebook.data.DatabaseManager;
import com.samirk433.quotebook.data.repo.QuoteRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.createDatabaseIfNotExist();
        List l2 = databaseManager.getQuotesByCatId(10);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_quote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new CustomRecyclerAdapter(l2));

    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textQuote;

        public CustomViewHolder(View itemView) {
            super(itemView);

            textQuote = itemView.findViewById(R.id.text_quote);
        }
    }

    private class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        private List<QuoteRepository> mList;

        public CustomRecyclerAdapter(List<QuoteRepository> list) {
            this.mList = list;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_quote, null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, final int position) {
            holder.textQuote.setText(mList.get(position).getQuoteModel().getText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, mList.get(position).getQuoteModel().getText());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }
    }
}
