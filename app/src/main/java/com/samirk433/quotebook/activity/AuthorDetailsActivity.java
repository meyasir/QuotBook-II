package com.samirk433.quotebook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.samirk433.quotebook.R;
import com.samirk433.quotebook.adapter.AuthorDetailsAdapter;

public class AuthorDetailsActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerViewAuthorDetails;
    AuthorDetailsAdapter authorDetailsAdapter;

    public AuthorDetailsActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        String[] data = {"Love", "Romantic", "Universal", "Motivational", "Craze", "Care", "Funny", "Inspirational", "Risk",
                "Relation", "Boyhood", "Serious", "Moody", "Combo", "cat_15", "cat_16", "cat_17", "cat_18"};

        mRecyclerViewAuthorDetails = findViewById(R.id.recycler_view_author_details);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerViewAuthorDetails.setLayoutManager(linearLayoutManager);

        authorDetailsAdapter = new AuthorDetailsAdapter(data);

        mRecyclerViewAuthorDetails.setAdapter(authorDetailsAdapter);

    }
}
