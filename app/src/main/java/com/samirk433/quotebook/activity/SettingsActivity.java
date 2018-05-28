package com.samirk433.quotebook.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.ClipboardManager;
import android.os.Build;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.balysv.materialripple.MaterialRippleLayout;
import com.samirk433.quotebook.R;
import com.samirk433.quotebook.model.LocalData;
import com.samirk433.quotebook.model.NotificationScheduler;
import com.samirk433.quotebook.receiver.AlarmReceiver;
import com.samirk433.quotebook.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    private MaterialRippleLayout mLayoutGoogleFonts, mLayoutUnsplash;
    private LinearLayout mLayoutTime, mLayoutHelp;
    private SwitchCompat mSwitchNotification;

    private LocalData localData;
    private TextView mTvTime;
    private int mHour, mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();

        localData = new LocalData(getApplicationContext());
        mHour = localData.get_hour();
        mMin = localData.get_min();

        mTvTime.setText(getFormatedTime(mHour, mMin));
        mSwitchNotification.setChecked(localData.getReminderStatus());

        if (!localData.getReminderStatus())
            mLayoutTime.setAlpha(0.4f);

    }


    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setGravity(toolbar.TEXT_ALIGNMENT_GRAVITY);


        mLayoutUnsplash = findViewById(R.id.rippleLayoutUnsplashLicence);
        mLayoutGoogleFonts = findViewById(R.id.rippleLayoutGoogleFonts);
        mLayoutHelp = findViewById(R.id.layout_help);
        mLayoutTime = findViewById(R.id.layout_time);
        mTvTime = findViewById(R.id.tv_notification_time);
        mSwitchNotification = findViewById(R.id.timerSwitch);

        mLayoutGoogleFonts.setOnClickListener(this);
        mLayoutUnsplash.setOnClickListener(this);
        mLayoutHelp.setOnClickListener(this);
        mLayoutTime.setOnClickListener(this);
        mSwitchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                localData.setReminderStatus(isChecked);
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: true");
                    NotificationScheduler.setReminder(SettingsActivity.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());
                    mLayoutTime.setAlpha(1f);
                } else {
                    Log.d(TAG, "onCheckedChanged: false");
                    NotificationScheduler.cancelReminder(SettingsActivity.this, AlarmReceiver.class);
                    mLayoutTime.setAlpha(0.4f);
                }
            }
        });

        //scroll animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        ScrollView scrollView = findViewById(R.id.scroll_settings);
        scrollView.startAnimation(animation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setStartOffset(500);

        findViewById(R.id.tv_daily_notification).startAnimation(alphaAnimation);
        findViewById(R.id.tv_daily_notification_desc).startAnimation(alphaAnimation);
        findViewById(R.id.tv_help).startAnimation(alphaAnimation);
        findViewById(R.id.tv_unsplash).startAnimation(alphaAnimation);
        findViewById(R.id.tv_google_fonts).startAnimation(alphaAnimation);

        mTvTime = findViewById(R.id.tv_notification_time);
        mTvTime.setAnimation(alphaAnimation);

        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(AppUtils.getVersionName(this));
        tvVersion.setAnimation(alphaAnimation);
    }

    private void showTimePickerDialog(int h, int m) {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.timepicker_header, null);

        TimePickerDialog builder = new TimePickerDialog(this, R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Log.d(TAG, "onTimeSet: mHour " + hour);
                        Log.d(TAG, "onTimeSet: mMin " + min);
                        localData.set_hour(hour);
                        localData.set_min(min);
                        mTvTime.setText(getFormatedTime(hour, min));
                        NotificationScheduler.setReminder(SettingsActivity.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());

                    }
                }, h, m, false);

        builder.setCustomTitle(view);
        builder.show();

    }

    public String getFormatedTime(int h, int m) {
        final String OLD_FORMAT = "HH:mm";
        final String NEW_FORMAT = "hh:mm a";

        String oldDateString = h + ":" + m;
        String newDateString = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDateString;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_help:
                Intent intent = new Intent(SettingsActivity.this, IntroDemoActivity.class);
                startActivity(intent);
                break;

            case R.id.layout_time:
                if (localData.getReminderStatus())
                    showTimePickerDialog(localData.get_hour(), localData.get_min());
                break;

            case R.id.rippleLayoutGoogleFonts:
                break;
            case R.id.rippleLayoutUnsplashLicence:
                break;
        }
    }
}