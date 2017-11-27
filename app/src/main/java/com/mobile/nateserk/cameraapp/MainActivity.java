package com.mobile.nateserk.cameraapp;

import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private final String TEMP_PHOTO_URI = "/testPhoto.png";

    private final String SAVE_FILENAME_URI = "/finalTestPhoto.png";

    private PreviewFragment mPreviewFragment;

    private Uri mHqImageUri;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_camera:
                    TakePhoto();
                    return true;
                case R.id.navigation_save:
                    SavePhotoFromUri();
                    return true;
            }
            return false;
        }
    };

    private void SavePhotoFromBitmap()
    {
        Bitmap bitmap = mPreviewFragment.GetBitmap();

        if (bitmap != null)
        {
            Log.d("MainActivity", "Bitmap Exists! Need to test actual image.");
            // TODO: Write to file and check if bitmap contains the image.

            String path = GetStoragePathForCapturedPhoto();
            File newFile = new File(path, SAVE_FILENAME_URI);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(newFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // TODO: Show Error message
            Log.d("MainActivity","NO BITMAP! ABORT!!!!");
        }
    }

    private void SavePhotoFromUri()
    {
        File tempPhoto = new File(GetStoragePathForCapturedPhoto(), TEMP_PHOTO_URI);
        if (tempPhoto != null && tempPhoto.exists())
        {
            // TODO: Do we need to retain a preview?
            tempPhoto.renameTo(new File(GetStoragePathForCapturedPhoto(), SAVE_FILENAME_URI));
        }
    }

    private String GetStoragePathForCapturedPhoto()
    {
        // TODO: This could be internal storage instead of external.
        return Environment.getExternalStorageDirectory().toString();
    }

    private void TakePhoto()
    {

        if (!this.mPreviewFragment.IsAttached())
        {
            Log.d("MainActivity", "Attach Preview Fragment!");

            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.add(R.id.view_placeholder, this.mPreviewFragment);
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        }



        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File capturePhoto = new File(GetStoragePathForCapturedPhoto(), TEMP_PHOTO_URI);
        mHqImageUri = Uri.fromFile(capturePhoto);
        // Ensure high quality
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHqImageUri);

        Log.d("MainActivity", "Take A photo Intent and temporarily save to " + capturePhoto.getPath());

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // This is for thumbnail
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");

            PreviewPhotoFromUri(this.mHqImageUri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d("MainAcitivty","****** onSaveInstanceState!");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void PreviewPhotoFromUri(Uri imageUri)
    {
        this.mPreviewFragment.SetImageUri(mHqImageUri);
    }

    private void PreviewPhotoFromBitmap(Bitmap bitmap)
    {
        this.mPreviewFragment.SetBitmap(bitmap, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","OnCreate is called!");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.mPreviewFragment = new PreviewFragment();
    }

}
