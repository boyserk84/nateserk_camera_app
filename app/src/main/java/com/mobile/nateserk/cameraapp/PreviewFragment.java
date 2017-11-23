package com.mobile.nateserk.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by natek on 11/23/17.
 */

public class PreviewFragment extends Fragment {

    private ImageView mImageView;

    private Bitmap mBitmap;

    private Boolean mIsAttach = false;

    public Boolean IsAttached()
    {
        return mIsAttach;
    }

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
        mImageView = (ImageView) view.findViewById(R.id.img_preview);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIsAttach = true;
    }

    @Override
    public void onDetach() {
        mIsAttach = false;
        super.onDetach();
    }

    public void SetBitmap(Bitmap bitmap, boolean showImmediately)
    {
        mBitmap = bitmap;
        if (showImmediately)
        {
            PreviewBitmap();
        }
    }

    public void PreviewBitmap()
    {
        if (mImageView != null && mBitmap != null)
        {
            mImageView.setImageBitmap(mBitmap);
        } else {
            Log.d("PreviewFragment", "Unable to preview Bitmap! mImageView=" + (mImageView!=null));
        }
    }
}
