package com.samirk433.quotebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.samirk433.quotebook.R;
import com.samirk433.quotebook.data.model.PhotoUnsplash;
import com.samirk433.quotebook.data.repo.QuoteRepository;
import com.samirk433.quotebook.utils.FileUtils;
import com.samirk433.quotebook.utils.StringUtils;
import com.samirk433.quotebook.utils.TypefaceUtils;
import com.samirk433.quotebook.utils.constant.AppConstant;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class QuoteActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, ThumbnailDialog.OnPhotoSelection {
    private static final String TAG = QuoteActivity.class.getSimpleName();

    private FrameLayout mLayoutBackground;
    private LinearLayout mLayoutBottomInfo, mLayoutBottomTools;
    private ImageView mImgFont, mImgTextSize, mImgTheme, mImgShowThumbnail,
            mImgBackground;
    private ProgressBar mProgressBar;
    private TextView mTvQuote, mTvCurrSizeTitle, mTvCurrFont, mTvImgShowThumbnail,
            mTvCurrTheme, mTvAuthor, mTvPhotographer;

    private QuoteRepository quoteRepository;
    private String[] mColorThemes, mTextColors, mColorThemesTitles, mTextSizesTitles, mFonts;
    private int[] mTextSizes;
    private int mCurrColorThemeIndex = -1, mCurrTextSizeIndex = -1, mCurrFontIndex = -1;
    private boolean mIsImgSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Log.d(TAG, "onCreate()");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            quoteRepository = (QuoteRepository) bundle.getSerializable(AppConstant.EXTRA_QUOTE);

        if (quoteRepository == null)
            return;

        initViews();

        mColorThemes = getResources().getStringArray(R.array.arr_themes_codes);
        mTextColors = getResources().getStringArray(R.array.arr_text_colors_codes);
        mColorThemesTitles = getResources().getStringArray(R.array.arr_themes_titles);
        mTextSizes = getResources().getIntArray(R.array.arr_text_sizes);
        mTextSizesTitles = getResources().getStringArray(R.array.arr_text_size_titles);
        mFonts = TypefaceUtils.getTypefaceNames();

        mTvQuote.setText(quoteRepository.getQuoteModel().getText());
        mTvAuthor.setText(getResources().getString(R.string.msg_author,
                quoteRepository.getAuthorModel().getName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.menu_activity_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected()");

        switch (item.getItemId()) {
            case R.id.menu_save:
                saveImage();
                break;
            case R.id.menu_share:
                Log.d(TAG, "menu_share is clicked");
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_font:
                changeFont();
                break;

            case R.id.layout_text_size:
                changeTextSize();
                break;

            case R.id.layout_theme:
                changeBackgroundColor();
                break;

            case R.id.layout_show_thumbnail:

                int errorCode = -1000;
                GoogleApiAvailability googleApiAvailability = null;
                try {
                    googleApiAvailability = GoogleApiAvailability.getInstance();
                    errorCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
                    ProviderInstaller.installIfNeeded(this);
                    showThumbnailDialog();
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "Google Play Exception", e);
                    if (googleApiAvailability != null)
                        googleApiAvailability.getErrorDialog(this, errorCode, 1);

                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "Google Play Exception", e);
                    if (googleApiAvailability != null)
                        googleApiAvailability.getErrorDialog(this, errorCode, 1);
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d(TAG, "onTouch()");

        switch (view.getId()) {
            case R.id.layout_font:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mImgFont.setImageResource(R.drawable.ic_font_focused);
                    mTvCurrFont.setTextColor(getResources().getColor(R.color.colorBackRed));
                } else {
                    mImgFont.setImageResource(R.drawable.ic_font);
                    mTvCurrFont.setTextColor(Color.WHITE);
                }
                break;

            case R.id.layout_text_size:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mImgTextSize.setImageResource(R.drawable.ic_text_focused);
                    mTvCurrSizeTitle.setTextColor(getResources().getColor(R.color.colorBackRed));
                } else {
                    mImgTextSize.setImageResource(R.drawable.ic_text);
                    mTvCurrSizeTitle.setTextColor(Color.WHITE);
                }
                break;

            case R.id.layout_theme:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mImgTheme.setImageResource(R.drawable.ic_theme_focused);
                    mTvCurrTheme.setTextColor(getResources().getColor(R.color.colorBackRed));
                } else {
                    mImgTheme.setImageResource(R.drawable.ic_theme);
                    mTvCurrTheme.setTextColor(Color.WHITE);
                }
                break;

            case R.id.layout_show_thumbnail:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mImgShowThumbnail.setImageResource(R.drawable.ic_camera_focused);
                    mTvImgShowThumbnail.setTextColor(getResources().getColor(R.color.colorBackRed));
                } else {
                    mImgShowThumbnail.setImageResource(R.drawable.ic_camera);
                    mTvImgShowThumbnail.setTextColor(Color.WHITE);
                }
                break;

        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProgressBar != null)
            mLayoutBackground.removeView(mProgressBar);
    }

    @Override
    public void photoSelected(PhotoUnsplash photoUnsplash) {
        Log.d(TAG, "photoSelected()");

        if (photoUnsplash == null) {
            mIsImgSelected = false;
            mImgBackground.setImageDrawable(null);
            mTvPhotographer.setText("");
            return;
        }

        mIsImgSelected = true;
        mLayoutBackground.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        //show progressbar while image is downloading..
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 120);
        params.gravity = Gravity.CENTER;
        mProgressBar = new ProgressBar(this);
        mLayoutBackground.addView(mProgressBar, params);

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(this).load(photoUnsplash.urls.regular)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mLayoutBackground.removeView(mProgressBar);
                        Toast.makeText(getApplicationContext(),
                                "Error while loading photo, please try again",
                                Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mLayoutBackground.removeView(mProgressBar);
                        return false;
                    }
                })
                .into(mImgBackground);

        //set photographer name
        mTvPhotographer.setText(getResources().getString(R.string.msg_photographer,
                photoUnsplash.userInfo.name));
    }

    private void initViews() {
        mLayoutBackground = findViewById(R.id.layout_background);
        mLayoutBottomInfo = findViewById(R.id.layout_bottom_info);
        mLayoutBottomTools = findViewById(R.id.layout_bottom_tools);
        mImgBackground = findViewById(R.id.img_background);
        mImgFont = findViewById(R.id.img_font);
        mImgTextSize = findViewById(R.id.img_text_size);
        mImgTheme = findViewById(R.id.img_theme);
        mImgShowThumbnail = findViewById(R.id.img_show_thumbnail);
        mTvQuote = findViewById(R.id.tv_quote);
        mTvCurrSizeTitle = findViewById(R.id.tv_curr_text_size_title);
        mTvCurrFont = findViewById(R.id.tv_curr_font);
        mTvCurrTheme = findViewById(R.id.tv_curr_theme);
        mTvImgShowThumbnail = findViewById(R.id.tv_show_thumbnail);
        mTvAuthor = findViewById(R.id.tv_author);
        mTvPhotographer = findViewById(R.id.tv_photographer);

        TextViewCompat.setAutoSizeTextTypeWithDefaults(mTvQuote,
                TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        showBottomInfoLayout(false);

        LinearLayout layoutFont = findViewById(R.id.layout_font);
        layoutFont.setOnClickListener(this);
        layoutFont.setOnTouchListener(this);

        LinearLayout layoutTextSize = findViewById(R.id.layout_text_size);
        layoutTextSize.setOnClickListener(this);
        layoutTextSize.setOnTouchListener(this);

        LinearLayout layoutTheme = findViewById(R.id.layout_theme);
        layoutTheme.setOnClickListener(this);
        layoutTheme.setOnTouchListener(this);


        LinearLayout layoutThumbnail = findViewById(R.id.layout_show_thumbnail);
        layoutThumbnail.setOnClickListener(this);
        layoutThumbnail.setOnTouchListener(this);
    }

    private void showBottomInfoLayout(boolean show) {
        if (show) {
            mLayoutBottomInfo.setVisibility(View.VISIBLE);
        } else {
            mLayoutBottomInfo.setVisibility(View.INVISIBLE);
        }
    }

    private void changeFont() {
        Log.d(TAG, "changeFont()");

        if (mFonts == null || mTvCurrFont == null) {
            Log.d(TAG, "mFonts || mTvCurrFont is NULL");
            return;
        }

        if (mCurrFontIndex == -1)
            mCurrFontIndex = 1;
        else if (mCurrFontIndex == mFonts.length - 1)
            mCurrFontIndex = 0;
        else
            ++mCurrFontIndex;

        Typeface typeface = TypefaceUtils.getTypeface(this, mFonts[mCurrFontIndex]);

        if (typeface == null)
            return;

        mTvQuote.setTypeface(typeface);

        String simpleTitle = StringUtils.getSampleFontName(mFonts[mCurrFontIndex]);
        mTvCurrFont.setText(simpleTitle);
    }

    private void changeBackgroundColor() {
        Log.d(TAG, "changeBackgroundColor()");

        if (mColorThemes == null || mLayoutBackground == null) {
            Log.d(TAG, "mColorThemes || mLayoutBackground is NULL");
            return;
        }

        if (mCurrColorThemeIndex == -1)
            mCurrColorThemeIndex = 1;
        else if (mCurrColorThemeIndex == mColorThemes.length - 1)
            mCurrColorThemeIndex = 0;
        else
            ++mCurrColorThemeIndex;

        int color = -1;

        if (mIsImgSelected) {
            color = Color.parseColor(mTextColors[mCurrColorThemeIndex]);
            mTvQuote.setTextColor(color);
        } else {
            color = Color.parseColor(mColorThemes[mCurrColorThemeIndex]);
            mLayoutBackground.setBackgroundColor(color);
        }

        mTvCurrTheme.setText(mColorThemesTitles[mCurrColorThemeIndex]);
    }

    private void changeTextSize() {
        Log.d(TAG, "changeTextSize()");

        if (mTextSizes == null || mTextSizesTitles == null || mTvQuote == null) {
            Log.d(TAG, "mTextSizes || mTextSizesTitles || mTvQuote is NULL");
            return;
        }

        if (mCurrTextSizeIndex == -1)
            mCurrTextSizeIndex = 1;
        else if (mCurrTextSizeIndex == mTextSizes.length - 1)
            mCurrTextSizeIndex = 0;
        else
            ++mCurrTextSizeIndex;

        //auto text resizing
        if (mCurrTextSizeIndex == 0)
            TextViewCompat.setAutoSizeTextTypeWithDefaults(mTvQuote,
                    TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        else {
            TextViewCompat.setAutoSizeTextTypeWithDefaults(mTvQuote,
                    TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
            mTvQuote.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSizes[mCurrTextSizeIndex]);
        }
        mTvCurrSizeTitle.setText(mTextSizesTitles[mCurrTextSizeIndex]);

    }

    private void showThumbnailDialog() {
        Log.d(TAG, "showThumbnailDialog()");

        ThumbnailDialog dialog = new ThumbnailDialog();
        dialog.show(getSupportFragmentManager(), "image_dialog");
    }

    private void saveImage() {
        Log.d(TAG, "saveImage()");

        showBottomInfoLayout(true);

        int color = mTvQuote.getTextColors().getDefaultColor();
        mTvAuthor.setTextColor(color);
        mTvPhotographer.setTextColor(color);

        Bitmap b = viewToBitmap(mLayoutBackground);
        if (b == null) {
            Log.e(TAG, "Bitmap is NULL");
            return;
        }

        if (!checkStoragePermission()) {
            askForStoragePermission();
            return;
        }

        try {
            FileOutputStream output = new FileOutputStream(FileUtils.getFulFileName(this));
            b.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            showBottomInfoLayout(false);
        }
    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private boolean checkStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void askForStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

}

