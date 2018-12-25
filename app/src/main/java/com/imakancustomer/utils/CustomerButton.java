package com.imakancustomer.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomerButton extends android.support.v7.widget.AppCompatButton {


    public CustomerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomerButton(Context context) {
        super(context);
        init();
    }

    public CustomerButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface font_type = Typeface.createFromAsset(getContext().getAssets(), "Muli-Regular.ttf");
        setTypeface(font_type);
    }
}