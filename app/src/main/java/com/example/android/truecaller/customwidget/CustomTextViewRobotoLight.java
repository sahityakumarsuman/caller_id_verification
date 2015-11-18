package com.example.android.truecaller.customwidget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Duke on 7/22/2015.
 */
public class CustomTextViewRobotoLight extends TextView {

    public CustomTextViewRobotoLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewRobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewRobotoLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Roboto-Light.ttf");
        setTypeface(tf);
    }

}
