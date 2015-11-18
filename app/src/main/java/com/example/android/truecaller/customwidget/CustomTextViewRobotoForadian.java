package com.example.android.truecaller.customwidget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Duke on 7/22/2015.
 */
public class CustomTextViewRobotoForadian extends TextView {


    public CustomTextViewRobotoForadian(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewRobotoForadian(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewRobotoForadian(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "Rupee_Foradian.ttf");
        setTypeface(tf);
    }

}
