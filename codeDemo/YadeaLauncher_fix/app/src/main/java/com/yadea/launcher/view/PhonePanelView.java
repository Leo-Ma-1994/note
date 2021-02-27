package com.yadea.launcher.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.customview.widget.ViewDragHelper;

import com.yadea.launcher.R;

public class PhonePanelView extends ConstraintLayout {

    private Bitmap mBg;

    private int mViewWidth;

    private int mViewHeight;

    private Paint mPaint;

    private ViewDragHelper mDragHelper;

    private ImageView mPhoneIcon;

    private ImageView mPhoneHangupIcon;

    private Pair<Integer, Integer> mPhoneIconOriginalLocation;

    private Pair<Integer, Integer> mPhoneIconDragRegion;

    private Pair<Integer, Integer> mPhoneHangupIconOriginalLocation;

    private Pair<Integer, Integer> mPhoneHangupIconDragRegion;

    private OnPhoneCallListener mListener;

    public PhonePanelView(Context context) {
        this(context, null);
    }

    public PhonePanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhonePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initViews(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPhoneIcon = (ImageView) getChildAt(3);
        mPhoneHangupIcon = (ImageView) getChildAt(4);
    }

    private void init(Context context, AttributeSet attrs) {
        mBg = BitmapFactory.decodeResource(getResources(), R.drawable.ele_panel_green);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void initViews(Context context, AttributeSet attrs) {


        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mPhoneIcon || child == mPhoneHangupIcon;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx)
            {
                if (child == mPhoneIcon) {
                    if (left <= mPhoneIconDragRegion.first)
                        return mPhoneIconDragRegion.first;
                    if (left >= mPhoneIconDragRegion.second){
                        answerTheCall(child);
                        return mPhoneIconDragRegion.second;
                    }

                }

                if (child == mPhoneHangupIcon) {
                    if (left <= mPhoneHangupIconDragRegion.first){
                        hangupTheCall(child);
                        return mPhoneHangupIconDragRegion.first;
                    }

                    if (left >= mPhoneHangupIconDragRegion.second)
                        return mPhoneHangupIconDragRegion.second;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy)
            {
                if (child == mPhoneIcon) {
                    return mPhoneIconOriginalLocation.second;
                }

                if (child == mPhoneHangupIcon) {
                    return mPhoneHangupIconOriginalLocation.second;
                }
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (releasedChild == mPhoneIcon) {
                    mDragHelper.settleCapturedViewAt(mPhoneIconOriginalLocation.first, mPhoneIconOriginalLocation.second);
                }

                if (releasedChild == mPhoneHangupIcon) {
                    mDragHelper.settleCapturedViewAt(mPhoneHangupIconOriginalLocation.first, mPhoneHangupIconOriginalLocation.second);
                }

                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = measureHandler(widthMeasureSpec);
        mViewHeight = measureHandler(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPhoneIconOriginalLocation = new Pair<>(mPhoneIcon.getLeft(), mPhoneIcon.getTop());
        mPhoneHangupIconOriginalLocation = new Pair<>(mPhoneHangupIcon.getLeft(), mPhoneHangupIcon.getTop());
        mPhoneIconDragRegion = new Pair<>(mPhoneIcon.getLeft(), mViewWidth / 2 - mPhoneIcon.getMeasuredWidth() / 2);
        mPhoneHangupIconDragRegion = new Pair<>(mViewWidth / 2 - mPhoneHangupIcon.getMeasuredWidth() / 2, mPhoneHangupIcon.getLeft());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawGuideline(canvas);
    }

    private void drawGuideline(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(0, 0, mViewWidth, mViewHeight);
        canvas.drawRect(rect, mPaint);
    }

    private int measureHandler(int measureSpec) {
        int result;
        int measureSize = MeasureSpec.getSize(measureSpec);
        result = measureSize;

        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll()
    {
        if(mDragHelper.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            child.setVisibility(visibility);
        }
    }

    public void setListener(OnPhoneCallListener listener) {
        this.mListener = listener;
    }

    private void answerTheCall(View view) {
        if (mListener != null)
            mListener.answer();
        ((BatteryDashboardView) getParent()).onPhoneExit();
    }

    private void hangupTheCall(View view) {
        if (mListener != null)
            mListener.hangup();
        ((BatteryDashboardView) getParent()).onPhoneExit();
    }

    public interface OnPhoneCallListener {
        void answer();
        void hangup();
    }
}
