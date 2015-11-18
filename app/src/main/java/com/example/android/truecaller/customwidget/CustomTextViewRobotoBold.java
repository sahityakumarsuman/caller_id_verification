package com.example.android.truecaller.customwidget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Duke on 7/22/2015.
 */
public class CustomTextViewRobotoBold extends TextView {

    public CustomTextViewRobotoBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewRobotoBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewRobotoBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Roboto-Bold.ttf");
        setTypeface(tf);
    }
}
