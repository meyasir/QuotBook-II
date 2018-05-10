package com.sky.quotebook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.sky.quotebook.R;

import java.util.ArrayList;
import java.util.List;

public class IntroDemoActivity extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;
    String finishButton;
    int titleTextSize;
    int descriptionTextSize;
    boolean multilineDescriptionCentered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("Title 1", "Description 1");
        OnboarderPage onboarderPage2 = new OnboarderPage(R.string.app_name, R.string.app_message, R.drawable.googleg_standard_color_18);
        OnboarderPage onboarderPage3 = new OnboarderPage("third", "Description");
        // You can define title and description colors (by default white)
        onboarderPage1.setTitleColor(R.color.black);
        onboarderPage1.setDescriptionColor(R.color.white);
        onboarderPage3.setTitleColor(R.color.black);
        onboarderPage3.setDescriptionTextSize(34);
        onboarderPage3.setImageResourceId(R.drawable.ic_person_red_24dp);
        onboarderPage3.setDescriptionColor(R.color.google_green);
        onboarderPage3.setBackgroundColor(R.color.google_yellow);

        // Don't forget to set background color for your page
        onboarderPage1.setBackgroundColor(R.color.google_blue);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);

        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);


        setActiveIndicatorColor(android.R.color.white);
        setInactiveIndicatorColor(android.R.color.holo_green_light);
        shouldDarkenButtonsLayout(true);
        setDividerColor(Color.WHITE);
        setDividerHeight(2);
        setDividerVisibility(View.GONE);
        shouldUseFloatingActionButton(true);
        setSkipButtonTitle("Skip");
        setFinishButton("Finish");
        setSkipButtonHidden();
        setTitleTextSize(12);
        setDescriptionTextSize(12);
        setMultilineDescriptionCentered(true);
    }

    @Override
    public void onSkipButtonPressed() {
        // Optional: by default it skips onboarder to the end
        super.onSkipButtonPressed();

        Toast.makeText(getApplicationContext(), "Skipped", Toast.LENGTH_SHORT).show();
        // Define your actions when the user press 'Skip' button
    }

    @Override
    public void onFinishButtonPressed() {

        //intent will only call for the first time when user click on finish button, other times clicking on finish it'll call finish() method
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            Intent intent = new Intent(IntroDemoActivity.this, MainActivity.class);
            startActivity(intent);
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        //finishes main activity
        finish();
        Toast.makeText(getApplicationContext(), "app tour ended", Toast.LENGTH_SHORT).show();
        // Define your actions when the user press 'Finish' button
    }

    public void setFinishButton(String finishButton) {
        this.finishButton = finishButton;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public void setDescriptionTextSize(int descriptionTextSize) {
        this.descriptionTextSize = descriptionTextSize;
    }

    public void setMultilineDescriptionCentered(boolean multilineDescriptionCentered) {
        this.multilineDescriptionCentered = multilineDescriptionCentered;
    }
}