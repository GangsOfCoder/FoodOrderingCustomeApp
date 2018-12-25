package com.imakancustomer.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomerTextViewBold extends android.support.v7.widget.AppCompatTextView {

    public CustomerTextViewBold(Context context) {
        super(context);
        setFont();
    }

    public CustomerTextViewBold(Context context, AttributeSet set) {
        super(context, set);
        setFont();
    }

    public CustomerTextViewBold(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        setFont();
    }

    private void setFont() {

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Muli-Bold.ttf");
        setTypeface(typeface);
    }
}
