package com.samirk433.quotebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.quotebook.samirk433.quotebook.R;
import com.samirk433.quotebook.data.model.PhotoUnsplash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ThumbnailDialog extends DialogFragment implements View.OnClickListener {
    private static final String TAG = ThumbnailDialog.class.getSimpleName();

    private GridView mGridView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mTvLoadMore, mTvRemove;

    private static List<PhotoUnsplash> sPhotos;
    private static int mPageNo = 1;
    private String mBaseUrl, mAccessToken;

    public OnPhotoSelection mOnPhotoSelection;

    public interface OnPhotoSelection {
        void photoSelected(PhotoUnsplash photoUnsplash);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog()");

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_thumbnail, null);
        initViews(view);

        mBaseUrl = getString(R.string.unsplash_base_url);
        mAccessToken = getString(R.string.unsplash_access_key);

        if (sPhotos != null)
            runUiThreadForLoadedPhotos();
        else
            getPhotosRequest();

        dialog.setView(view);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mOnPhotoSelection = (OnPhotoSelection) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick()");

        switch (view.getId()) {
            case R.id.tv_remove:
                mOnPhotoSelection.photoSelected(null);
                dismiss();
                break;
            case R.id.tv_load_more:
                mTvLoadMore.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                getPhotosRequest();
                break;
        }
    }


    private void initViews(View view) {
        Log.d(TAG, "initViews()");

        mGridView = view.findViewById(R.id.grid_thumbnail);
        mProgressBar = view.findViewById(R.id.progressBar);
        mTvLoadMore = view.findViewById(R.id.tv_load_more);
        mTvRemove = view.findViewById(R.id.tv_remove);

        mTvRemove.setOnClickListener(this);
        mTvLoadMore.setOnClickListener(this);
    }

    private void updateUiForLoadedPhotos() {
        Log.d(TAG, "updateUiForLoadedPhotos()");
        mTvRemove.setVisibility(View.VISIBLE);
        mTvLoadMore.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mAdapter = new ImageAdapter(sPhotos);
        mGridView.setAdapter(mAdapter);
    }

    private void runUiThreadForLoadedPhotos() {
        Log.d(TAG, "runUiThreadForLoadedPhotos()");

        if (Looper.myLooper() == Looper.getMainLooper()) {
            updateUiForLoadedPhotos();
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run()");
                    updateUiForLoadedPhotos();
                }
            });
        }
    }

    private void showToastOnUiThread(final String msg) {
        Log.d(TAG, String.format("showThreadOnUiThread(%s)", msg));

        //I don't know why sometime activity is null
        if(getActivity() !=null)
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPhotosRequest() {
        String url = mBaseUrl + "photos?page=" + mPageNo + "&client_id=" + mAccessToken;
        Log.d(TAG, url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        Log.e(TAG, "onFailure()" + e.toString());
                        showToastOnUiThread("Connectivity issue occurs while fetching images," +
                                "please try again");
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        Log.d(TAG, "onResponse() :" + res);

                        Gson gson = new Gson();
                        try {
                            PhotoUnsplash[] array = gson.fromJson(res,
                                    PhotoUnsplash[].class);

                            if (sPhotos == null)
                                sPhotos = new ArrayList<>();

                            //clear old picture first, for memory
                            sPhotos.clear();

                            sPhotos.addAll(Arrays.asList(array));
                            mPageNo++;
                            runUiThreadForLoadedPhotos();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse()", e);
                            showToastOnUiThread("Server internal error occurs while fetching images," +
                                    " please try again");
                        }

                    }
                });
    }


    /*  IMAGE ADAPTER CLASS */
    private class ImageAdapter extends BaseAdapter {
        private final String TAG = ImageAdapter.class.getSimpleName();

        public List<PhotoUnsplash> mPhotosList;

        public ImageAdapter(List list) {
            Log.d(TAG, "ImageAdapter() : " + list.size());
            this.mPhotosList = list;
        }

        @Override
        public int getCount() {
            return mPhotosList == null ? 0 : mPhotosList.size();
        }

        @Override
        public Object getItem(int i) {
            return mPhotosList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            Log.d(TAG, String.format("getView(%d)", position));
            View view;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_thumb, null);
            } else {
                view = convertView;
            }

            TextView tvPhotographer = view.findViewById(R.id.tv_photographer);
            tvPhotographer.setText("@" + mPhotosList.get(position).userInfo.name);


            ImageView img = view.findViewById(R.id.img_thumb);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.loading_placeholder);
            options.error(R.drawable.error_placeholder);
            options.centerCrop();
            Glide.with(getActivity()).load(mPhotosList.get(position).urls.thumb)
                    .apply(options).into(img);


            tvPhotographer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick()");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mPhotosList.get(position).userInfo.links.html));
                    startActivity(intent);
                    dismiss();
                }
            });

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick()");
                    mOnPhotoSelection.photoSelected(mPhotosList.get(position));
                    dismiss();
                }
            });

            return view;
        }
    }
}
