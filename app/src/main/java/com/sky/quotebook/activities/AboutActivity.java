package com.sky.quotebook.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.quotebook.R;
import com.sky.quotebook.model.Constant;
import com.sky.quotebook.util.AppUtils;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("about activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        initView();
    }

    public void initView() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        ScrollView scroll_about = findViewById(R.id.scroll_about);
        scroll_about.startAnimation(animation);

        LinearLayout ll_card_about_2_shop = findViewById(R.id.ll_card_about_2_shop);
        LinearLayout ll_card_about_2_email = findViewById(R.id.ll_card_about_2_email);
        LinearLayout ll_card_about_2_git_hub = findViewById(R.id.ll_card_about_2_git_hub);

        LinearLayout ll_card_about_3_share_app = findViewById(R.id.ll_card_about_3_share_app);

        ll_card_about_2_shop.setOnClickListener(this);
        ll_card_about_2_email.setOnClickListener(this);
        ll_card_about_2_git_hub.setOnClickListener(this);
        ll_card_about_3_share_app.setOnClickListener(this);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setStartOffset(600);

        TextView tv_about_version = findViewById(R.id.tv_about_version);
        tv_about_version.setText(AppUtils.getVersionName(this));
        tv_about_version.startAnimation(alphaAnimation);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Display custom title
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.fragment_title_grandstats);

        // Display the back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.ll_card_about_2_shop:
                intent.setData(Uri.parse(Constant.APP_URL));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;

            case R.id.ll_card_about_2_email:
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(Constant.EMAIL));
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_email_intent));
                //intent.putExtra(Intent.EXTRA_TEXT, "Hi,");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(AboutActivity.this, getString(R.string.about_not_found_email), Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.ll_card_about_2_git_hub:
                intent.setData(Uri.parse(Constant.GIT_HUB));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;

            case R.id.ll_card_about_3_share_app:
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_CONTENT);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
                break;
        }
    }

}