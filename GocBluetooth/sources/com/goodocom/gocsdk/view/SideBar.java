package com.goodocom.gocsdk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.vcard.VCardConstants;

public class SideBar extends View {
    public static String[] b = {"A", VCardConstants.PARAM_ENCODING_B, "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", VCardConstants.PROPERTY_N, "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private int choose = -1;
    private TextView mTextDialog;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private Paint paint = new Paint();

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String str);
    }

    public void setTextView(TextView mTextDialog2) {
        this.mTextDialog = mTextDialog2;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            this.paint.setColor(Color.rgb(255, 255, 255));
            this.paint.setTypeface(Typeface.DEFAULT_BOLD);
            this.paint.setAntiAlias(true);
            this.paint.setTextSize(12.0f);
            if (i == this.choose) {
                this.paint.setColor(Color.parseColor("#3399ff"));
                this.paint.setFakeBoldText(true);
            }
            canvas.drawText(b[i], ((float) (width / 2)) - (this.paint.measureText(b[i]) / 2.0f), (float) ((singleHeight * i) + singleHeight), this.paint);
            this.paint.reset();
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = this.choose;
        OnTouchingLetterChangedListener listener = this.onTouchingLetterChangedListener;
        int c = (int) ((y / ((float) getHeight())) * ((float) b.length));
        if (action != 1) {
            setBackgroundResource(R.drawable.sidebar_background);
            if (oldChoose != c && c >= 0) {
                String[] strArr = b;
                if (c < strArr.length) {
                    if (listener != null) {
                        listener.onTouchingLetterChanged(strArr[c]);
                    }
                    TextView textView = this.mTextDialog;
                    if (textView != null) {
                        textView.setText(b[c]);
                        this.mTextDialog.setVisibility(0);
                    }
                    this.choose = c;
                    invalidate();
                }
            }
        } else {
            setBackgroundDrawable(new ColorDrawable(0));
            this.choose = -1;
            invalidate();
            TextView textView2 = this.mTextDialog;
            if (textView2 != null) {
                textView2.setVisibility(4);
            }
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener2) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener2;
    }
}
