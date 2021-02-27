package com.yadea.launcher.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class StatusBar extends GridView {


    public StatusBar(Context context) {
        super(context);
    }

    public StatusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

}
