package com.samirk433.quotebook.activity;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.samirk433.quotebook.R;
import com.samirk433.quotebook.adapter.AuthorDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AuthorDetailsActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerViewAuthorDetails;
    AuthorDetailsAdapter authorDetailsAdapter;
    private List<String> data;
    private boolean loading;
    private int loadTimes;

    public AuthorDetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);


        mRecyclerViewAuthorDetails = findViewById(R.id.recycler_view_author_details);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerViewAuthorDetails.setLayoutManager(linearLayoutManager);

        initData();

        authorDetailsAdapter = new AuthorDetailsAdapter(this);
        mRecyclerViewAuthorDetails.setAdapter(authorDetailsAdapter);
        authorDetailsAdapter.setItems(data);
        authorDetailsAdapter.addFooter();


        //onScrolling, Remove and add footer as required
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadTimes <= 5) {
                                authorDetailsAdapter.removeFooter();
                                loading = false;
                                authorDetailsAdapter.addItems(data);
                                authorDetailsAdapter.addFooter();
                                loadTimes++;
                            } else {
                                authorDetailsAdapter.removeFooter();
                                Snackbar.make(mRecyclerViewAuthorDetails, ("no more data"), Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                }).show();
                            }
                        }
                    }, 1500);
                    loading = true;
                }
            }
        };

        mRecyclerViewAuthorDetails.addOnScrollListener(scrollListener);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add(i + "");
        }
       String insertData = "0";
        loadTimes = 0;
    }
}
