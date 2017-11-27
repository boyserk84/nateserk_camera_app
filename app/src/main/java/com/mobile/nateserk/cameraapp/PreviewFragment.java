package com.mobile.nateserk.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * PreviewFragment.java
 *
 * This fragment handles displaying photo from either bitmap or uri objects.
 *
 */

public class PreviewFragment extends Fragment {

    private final String TEMP_IMAGE_KEY = "tmpImage";
    private final String TEMP_IMAGE_URI_KEY = "tmpUriImage";

    private ImageView mImageView;

    private Bitmap mBitmap;

    private Uri mImageUri;

    private Boolean mIsAttach = false;

    public Boolean IsAttached()
    {
        return this.mIsAttach;
    }

    public Bitmap GetBitmap() { return this.mBitmap; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("PreviewFragment", "onCreate is called!");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("PreviewFragment","onCreateView is called!");
        return inflater.inflate(R.layout.preview_view, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("PreviewFragment", "onViewCreated is called!");
        super.onViewCreated(view, savedInstanceState);
        this.mImageView = (ImageView) view.findViewById(R.id.img_preview);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mIsAttach = true;
    }

    @Override
    public void onDetach() {
        Log.d("PreviewFragment", "onDetach is called!");
        this.mIsAttach = false;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("PreviewFragment", "onSaveInstanceState is called!");
        super.onSaveInstanceState(outState);
        if (mBitmap == null)
        {
            Log.d("PreviewFragment", "Bitmap is NULL. create a new bitmap.");
            BitmapDrawable drawable = (BitmapDrawable) this.mImageView.getDrawable();
            mBitmap = drawable.getBitmap();
        }

        if (mImageUri != null)
        {
            outState.putParcelable(TEMP_IMAGE_URI_KEY, mImageUri);
        }

        outState.putParcelable(TEMP_IMAGE_KEY, mBitmap);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("PreviewFragment","onViewStateRestored is called!");
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState!=null)
        {
            Log.d("PreviewFragment","savedInstanceState is restoring data during onViewStateRestored!");
            Bitmap bitmap = (Bitmap) savedInstanceState.getParcelable(TEMP_IMAGE_KEY);

            if (bitmap != null)
            {
                Log.d("PreviewFragment","SetBitamp durign onViewStateRestored!");
                SetBitmap(bitmap, true);
            }

            Uri imgUri = (Uri) savedInstanceState.getParcelable(TEMP_IMAGE_URI_KEY);
            if (imgUri != null)
            {
                SetImageUri(imgUri);
            }
        }

    }

    public void SetBitmap(Bitmap bitmap, boolean showImmediately)
    {
        this.mBitmap = bitmap;
        if (showImmediately)
        {
            PreviewBitmap();
        }
    }

    public void SetImageUri(Uri uri)
    {
        if (uri != null)
        {
            this.mImageUri = uri;
            this.mImageView.setImageURI(this.mImageUri);
        }
    }

    public void PreviewBitmap()
    {
        if (this.mImageView != null && this.mBitmap != null)
        {
            this.mImageView.setImageBitmap(this.mBitmap);
        } else {
            Log.d("PreviewFragment", "Unable to preview Bitmap! mImageView=" + (this.mImageView!=null));
        }
    }
}
