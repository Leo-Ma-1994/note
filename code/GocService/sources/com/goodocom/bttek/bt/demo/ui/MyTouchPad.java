package com.goodocom.bttek.bt.demo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;

public class MyTouchPad extends View {
    static final int CURSOR_DIM = 30;
    public HidPageActivity mParent = null;
    int maxX = 1080;
    int maxY = 1980;
    float ratioX = 1.0f;
    float ratioY = 1.0f;
    float x = 0.0f;
    float y = 0.0f;

    public MyTouchPad(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMappedScreenDimension(int w, int h) {
        this.maxX = w;
        this.maxY = h;
        if (getWidth() > 0) {
            this.ratioX = ((float) this.maxX) / ((float) getWidth());
            this.ratioY = ((float) this.maxY) / ((float) getHeight());
        }
    }

    public int getXmax() {
        return this.maxX;
    }

    public int getYmax() {
        return this.maxY;
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > 0) {
            this.ratioX = ((float) this.maxX) / ((float) getWidth());
            this.ratioY = ((float) this.maxY) / ((float) getHeight());
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        this.x = event.getAxisValue(0);
        this.y = event.getAxisValue(1);
        HidPageActivity hidPageActivity = this.mParent;
        if (hidPageActivity != null) {
            hidPageActivity.setCoordinates((int) (this.x * this.ratioX), (int) (this.y * this.ratioY), event.getAction());
        }
        invalidate();
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(SupportMenu.CATEGORY_MASK);
        paint.setStrokeWidth(3.0f);
        float f = this.x;
        float f2 = this.y;
        canvas.drawLine(f - 30.0f, f2, f + 30.0f, f2, paint);
        float f3 = this.x;
        float f4 = this.y;
        canvas.drawLine(f3, f4 - 30.0f, f3, f4 + 30.0f, paint);
    }
}
