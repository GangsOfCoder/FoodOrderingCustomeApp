package com.imakancustomer.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomerTextViewRegular extends android.support.v7.widget.AppCompatTextView {

    public CustomerTextViewRegular(Context context) {
        super(context);
        setFont();
    }

    public CustomerTextViewRegular(Context context, AttributeSet set) {
        super(context, set);
        setFont();
    }

    public CustomerTextViewRegular(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Muli-Regular.ttf");
        setTypeface(typeface);
    }
}
