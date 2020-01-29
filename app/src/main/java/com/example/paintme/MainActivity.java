package com.example.paintme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.devsmart.android.BackgroundTask;
import com.example.paintme.views.ColorPicker;
import com.example.paintme.views.TouchPaintView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TouchPaintView mPaintView;
    private ColorPicker mColorPickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPaintView = findViewById(R.id.paintview);
        mColorPickerFragment = ColorPicker.newInstance();
        mColorPickerFragment.setOnColor(mOnSetColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.clear:
                mPaintView.clear();
                return true;
            case R.id.setcolor:
                showColorPicker();
                return true;
            case R.id.share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ColorPicker.OnColorPick mOnSetColor = new ColorPicker.OnColorPick() {
        @Override
        public void onColor(int color) {
            mPaintView.setColor(color);
        }
    };

    private void showColorPicker() {



        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.

        mColorPickerFragment.show(ft, "dialog");
    }

    private void share() {
        final Bitmap image = mPaintView.getImage();

        BackgroundTask.runBackgroundTask(new BackgroundTask() {
            private Uri mUri;

            @Override
            public void onBackground() {
                mUri = saveImage(image);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, mUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/png");
                startActivity(intent);
            }
        });


    }



    private Uri saveImage(Bitmap image) {
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "output.png");

            FileOutputStream stream = new FileOutputStream(file);
            try {
                image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            } finally {
                stream.close();
            }
            uri = FileProvider.getUriForFile(this, "com.example.fileprovider", file);


        } catch (IOException e) {
            Log.d("MAIN", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }


}
