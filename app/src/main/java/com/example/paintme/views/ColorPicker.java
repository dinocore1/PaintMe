package com.example.paintme.views;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.paintme.R;

public class ColorPicker extends DialogFragment {

    private OnColorPick mColorPicker;

    public interface OnColorPick {
        void onColor(int color);
    }

    private final int[] COLORS = new int[]{
            Color.BLACK, Color.WHITE, Color.GRAY,
            Color.RED, Color.GREEN, Color.BLUE,
    };

    public static ColorPicker newInstance() {
        ColorPicker f = new ColorPicker();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colorpicker, container, false);
        GridLayout grid = v.findViewById(R.id.grid);

        for(int color : COLORS) {
            ColorDrop drop = (ColorDrop) inflater.inflate(R.layout.color_drop, grid, false);
            drop.setColor(color);
            grid.addView(drop);
            drop.setOnClickListener(mOnColorClick);
        }

        return v;
    }

    private final View.OnClickListener mOnColorClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(mColorPicker != null) {
                ColorDrop colorDrop = (ColorDrop)v;
                mColorPicker.onColor(colorDrop.getColor());
            }
            dismiss();

        }
    };

    public void setOnColor(OnColorPick colorPick) {
        mColorPicker = colorPick;
    }
}
