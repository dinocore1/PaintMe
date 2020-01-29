package com.example.paintme;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.paintme.views.ColorPicker;
import com.example.paintme.views.TouchPaintView;

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


}
