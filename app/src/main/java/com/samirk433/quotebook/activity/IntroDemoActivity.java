package com.samirk433.quotebook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.samirk433.quotebook.R;

import java.util.ArrayList;
import java.util.List;

public class IntroDemoActivity extends OnboarderActivity {

    List<OnboarderPage> onboarderPages;
    String finishButton;
    String skipButton;
    int titleTextSize;
    int descriptionTextSize;
    boolean multilineDescriptionCentered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<OnboarderPage>();

        // Create your first page
        OnboarderPage onboarderPage1 = new OnboarderPage("App introduction!!", "This app provides all the best quotes from your favorite authors\n  \n The largest quote-hub on play store", R.drawable.ic_hand_wave_skin);
        OnboarderPage onboarderPage2 = new OnboarderPage("Reminder Alarm", "Anyway, this is to remind you there's always a recommended quote for you, whenever you're free", R.drawable.ic_reminder_alarm_green_1);
        OnboarderPage onboarderPage3 = new OnboarderPage("Favorite author", "You'll get all of the quotes from your favorite author(s)", R.drawable.ic_heart_white);
        OnboarderPage onboarderPage4 = new OnboarderPage("Customize Everything!!", "The app lets you change the 'Background picture, Color, Theme, Font size, and Font family' of your best quote and share it with Friends&Family", R.drawable.ic_customize_everything_multi_color);
        OnboarderPage onboarderPage5 = new OnboarderPage("Save Quotes", "You can save your favorite Quote", R.drawable.ic_save_green_white);
        // You can define title and description colors (by default white)

        //page 1 title & description color & size
        onboarderPage1.setTitleColor(R.color.white);
        onboarderPage1.setDescriptionColor(R.color.colorWhite);
        onboarderPage1.setTitleTextSize(30);
        onboarderPage1.setDescriptionTextSize(16);

        //page 2 title & description color & size
        onboarderPage2.setTitleColor(R.color.white);
        onboarderPage2.setDescriptionColor(R.color.colorWhite);
        onboarderPage2.setTitleTextSize(28);
        onboarderPage2.setDescriptionTextSize(18);

        //page 3 title & description color & size
        onboarderPage3.setTitleColor(R.color.white);
        onboarderPage3.setDescriptionColor(R.color.colorWhite);
        onboarderPage3.setTitleTextSize(25);
        onboarderPage3.setDescriptionTextSize(18);

        //page 4 title & description color & size
        onboarderPage4.setTitleColor(R.color.white);
        onboarderPage4.setDescriptionColor(R.color.colorWhite);
        onboarderPage4.setTitleTextSize(23);
        onboarderPage4.setDescriptionTextSize(16);

        //page 5 title & description color & size
        onboarderPage5.setTitleColor(R.color.white);
        onboarderPage5.setDescriptionColor(R.color.colorWhite);
        onboarderPage5.setTitleTextSize(23);
        onboarderPage5.setDescriptionTextSize(18);


        // Don't forget to set background color for your page
        onboarderPage1.setBackgroundColor(R.color.google_blue);

        onboarderPage2.setBackgroundColor(R.color.google_green_cyan);

        onboarderPage3.setBackgroundColor(R.color.google_green_touch);

        onboarderPage4.setBackgroundColor(R.color.google_cyan);

        onboarderPage5.setBackgroundColor(R.color.RedLight);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);
        onboarderPages.add(onboarderPage4);
        onboarderPages.add(onboarderPage5);

        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);

        //general attributes for all pages
        setActiveIndicatorColor(android.R.color.white);
        setInactiveIndicatorColor(android.R.color.holo_red_light);
        shouldDarkenButtonsLayout(true);
        setDividerColor(Color.WHITE);
        setDividerHeight(3);
        setDividerVisibility(View.VISIBLE);
        shouldUseFloatingActionButton(true);
        setSkipButtonTitle("Skip");
        setFinishButton("got it");
        setFinishButtonTitle("got it");
        setSkipButton("Skip");
        //setSkipButtonHidden();
        //setTitleTextSize(18);
        //setDescriptionTextSize(13);

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

    public void setSkipButton(String skipButton) {
        this.skipButton = skipButton;
    }

   /* public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public void setDescriptionTextSize(int descriptionTextSize) {
        this.descriptionTextSize = descriptionTextSize;
    }*/

    public void setMultilineDescriptionCentered(boolean multilineDescriptionCentered) {
        this.multilineDescriptionCentered = multilineDescriptionCentered;
    }
}