package com.goodocom.gocsdk.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.lang.reflect.Field;

public class ExtendedViewPager extends ViewPager {
    private boolean scrollble = true;

    public ExtendedViewPager(Context context) {
        super(context);
    }

    public ExtendedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setViewPagerScrollSpeed();
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.scrollble) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.scrollble) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean isScrollble() {
        return this.scrollble;
    }

    public void setScrollble(boolean scrollble2) {
        this.scrollble = scrollble2;
    }

    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(this, new FixedSpeedScroller(getContext()));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
        }
    }
}
