package com.sky.quotebook.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.quotebook.fragments.NoteFragment;
import com.sky.quotebook.R;
import com.sky.quotebook.adapter.RecyclerViewAdapter;
import com.sky.quotebook.fragments.AuthorListFragment;
import com.sky.quotebook.fragments.CategoryListFragment;
import com.sky.quotebook.fragments.SetupFragment;
import com.sky.quotebook.interfaces.ItemClickListener;
import com.sky.quotebook.model.Author;
import com.sky.quotebook.view.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private List<String> data;
    private String insertData;
    private boolean loading;
    private int loadTimes;
    private List<Author> authors;
    private ItemClickListener mListener;

    BottomNavigationView bottomNavigation;
    Fragment fragment;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("main activity");
        //display back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setGravity(toolbar.TEXT_ALIGNMENT_GRAVITY);

        initData();
        initView();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_author:
                        fragment = new AuthorListFragment();
                        break;
                    case R.id.action_category:
                        fragment = new CategoryListFragment();
                        break;
                    case R.id.action_note:
                        fragment = new NoteFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit);

                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }
        });

    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add(i + "");
        }

        insertData = "0";
        loadTimes = 0;
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setItems(data);
        adapter.addFooter();

        //support swapping left/Right, Move above/below
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


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
                                adapter.removeFooter();
                                loading = false;
                                adapter.addItems(data);
                                adapter.addFooter();
                                loadTimes++;
                            } else {
                                adapter.removeFooter();
                                Snackbar.make(mRecyclerView, ("no more data"), Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        loading = false;
                                        adapter.addFooter();
                                    }
                                }).show();
                            }
                        }
                    }, 1500);
                    loading = true;
                }
            }
        };
        //add new items on scrolling
        mRecyclerView.addOnScrollListener(scrollListener);
    }
    //end here

//commented because app crashes when back pressed two time
  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);

                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.action_about: {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                //animation applied
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }

            case R.id.action_save: {
                Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
            }
            case R.id.action_share: {
                Toast.makeText(getApplicationContext(), "share", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return false;
    }


}