package com.yadea.launcher.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.yadea.launcher.R;
import com.yadea.launcher.util.FontSettingUtil;


public class BatteryDashboardView extends ConstraintLayout {

    private static final String TAG = "BatteryDashboard";

    private Paint mPaint;

    private TextPaint mTextPaint;

    private Shader mTextShader;

    private int[] mTextColors = new int[]{0xFFA0EDC0, 0xFF18FFB7, 0xFFABFFCE, 0xFF18FFB7};

    private float mCenterX;

    private float mCenterY;

    private float mCircleCenterX;

    private float mCircleCenterY;

    private float offsetY = 7.0f;

    private Shader mShader;

//    private int[] mShaderColors = new int[]{0xFFA0EDC0, 0xFF18FFB7, 0xFFABFFCE, 0xFF18FFB7};
     private int[] mShaderColors = new int[]{0xFF6DEAE5, 0xFF00FFFF, 0xFF4A9F99, 0xFF4A9F99};
   //  private int[] mShaderColors = new int[]{0xFF33FFF, 0xFF4A9F99};

    private Shader mOtherShader;

//    private int[] mOtherShaderColors = new int[]{0xFFA0EDC0, 0xFF18FFB7, 0xFFABFFCE, 0xFF18FFB7};
   private int[] mOtherShaderColors = new int[]{0xFF04df5c, 0xFF4df5c, 0xFF039E42, 0xFF039E42};

//     private int[] mOtherShaderColors = new int[]{0xFF33FF99, 0xFF4A9F99};

    private float mRadius;

    private int CURRENT_COST_MAX = 80;

    private int CURRENT_FEEDBACK_MAX = 8;

    private int mPrimaryProgress = 0;

    private float mBackupProgress = 0;

    private int mPrimaryMax = 100;

    private int mBatterySoc = 0;

    private int mBatterySocSub = 0;

    private int mBackupMax = 100;

    private int mDuration = 500;

    public enum PanelStatus {
        BATTERY, NO_BATTERY, PHONE, SINGLE_BATTERY, SINGLE_BATTERY_SUB, INIT
    }

    private PanelStatus mPanelStatus = PanelStatus.BATTERY;

    private Bitmap mBackgroundBitmap;

    private Bitmap mPanelBitmap;

    private Bitmap mRingitmap;

    private Bitmap mBatteryRight;

    private Bitmap mBatteryLeft;

    private Bitmap mUnlimit;

    private ImageView mPanelView;

    private TextView mPrimaryBatteryText;

    private TextView mBackupBatteryText;

    private ImageView mPrimaryBatteryIcon;

    private ImageView mUnlimitIcon;

    private ImageView mBackupBatteryIcon;


    private TextView mPrimaryPersentText;

    private TextView mBackupPersentText;

    private PhonePanelView mPhonePanel;

    private OnChangeListener mOnChangeListener;

    public BatteryDashboardView(Context context) {
        this(context, null);
    }

    public BatteryDashboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryDashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setWillNotDraw(false);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mRingitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ele_ring);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPanelView = (ImageView) getChildAt(0);
//        mPhoneView = (ImageView) getChildAt(1);
        mPrimaryBatteryText = (TextView) getChildAt(2);
        mBackupBatteryText = (TextView) getChildAt(3);
        mPrimaryBatteryIcon = (ImageView) getChildAt(4);
        mUnlimitIcon = (ImageView) getChildAt(5);
        mBackupBatteryIcon = (ImageView) getChildAt(6);
        mPrimaryPersentText = (TextView) getChildAt(7);
        mBackupPersentText = (TextView) getChildAt(8);
        mPhonePanel = (PhonePanelView) getChildAt(10);

        //设置字体
        FontSettingUtil.setTvFont(this.getContext(), mPrimaryBatteryText, FontSettingUtil.MT_REGULAR);
        FontSettingUtil.setTvFont(this.getContext(), mBackupBatteryText, FontSettingUtil.MT_REGULAR);
        FontSettingUtil.setTvFont(this.getContext(), mPrimaryPersentText, FontSettingUtil.MT_REGULAR);
        FontSettingUtil.setTvFont(this.getContext(), mBackupPersentText, FontSettingUtil.MT_REGULAR);


        mTextShader = new LinearGradient(0, 0, 0, 60, mTextColors, null, Shader.TileMode.CLAMP);
        mPrimaryBatteryText.getPaint().setShader(mTextShader);
        mBackupBatteryText.getPaint().setShader(mTextShader);
        mPrimaryPersentText.getPaint().setShader(mTextShader);
        mBackupPersentText.getPaint().setShader(mTextShader);

        mPhonePanel.setVisibility(GONE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mPhonePanel.layout(43, 52, 43 + mPhonePanel.getMeasuredWidth(), 52 + mPhonePanel.getMeasuredHeight());
        mPanelView.layout(-1, 52, -1 + mPanelView.getMeasuredWidth(), 52 + mPanelView.getMeasuredHeight());
        switchPanelStatus(mPanelStatus);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureHandler(widthMeasureSpec);
        int height = measureHandler(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //圆心坐标
        mCenterX = (width + getPaddingLeft() - getPaddingRight()) / 2.0f;
        mCenterY = (height + getPaddingTop() - getPaddingBottom()) / 2.0f;

        mCircleCenterX = mCenterX;
        mCircleCenterY = mCenterY + offsetY;
        mRadius = width / 2.0f;

        //默认着色器
        mShader = new RadialGradient(mCircleCenterX, mCircleCenterY, mRadius, mShaderColors, new float[]{ 0f, 0.6f, 0.99f,1f}, Shader.TileMode.CLAMP);
        mOtherShader = new RadialGradient(mCircleCenterX, mCircleCenterY, mRadius, mOtherShaderColors, new float[]{0f, 0.6f, 0.99f, 1f}, Shader.TileMode.CLAMP);

        setMeasuredDimension(width, height);
    }

    private int measureHandler(int measureSpec) {
        int result;
        int measureSize = MeasureSpec.getSize(measureSpec);
        result = measureSize;

        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawArc(canvas);
    }

    private void drawBackground(Canvas canvas) {
        // 绘制背景
        canvas.drawBitmap(mRingitmap, -3, 8, mPaint);
    }

    private void drawArc(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setShader(mShader);

        float ringWidth = 38.0f;
        float intervalWidth = 2.0f;

        float radius = mRadius - ringWidth / 2 - intervalWidth;
        float strokeWidth = ringWidth;

        // 绘制进度条
        RectF rectF = new RectF(mCircleCenterX - radius, mCircleCenterY - radius,
                mCircleCenterX + radius, mCircleCenterY + radius);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setShader(mShader);
        mPaint.setAlpha(120);
        float ratio = getRatio();
        if (ratio > 0) {
            canvas.drawArc(rectF, 205, 200 * ratio, false, mPaint);
        }


        mPaint.setShader(mOtherShader);
        ratio = getOtherRatio();
        if (ratio > 0) {
            canvas.drawArc(rectF, 135 + 70 * (1 - ratio), 70 * ratio, false, mPaint);
        }

        // 绘制两个进度条之间的分割线
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, 204, 1.0f, false, mPaint);
    }

    public void setPrimaryProgress(int progress) {
        setPrimaryProgress(progress, false);
    }

    public void setBackupProgress(float progress) {
        setBackupProgress(progress, false);
    }

    public void setBatterySoc(int value) {
        if (value < 0) {
            value = 0;
        } else if (value > mPrimaryMax) {
            value = 0;
        }
        mBatterySoc = value;
        if (mBatterySoc == 0) {
            mPrimaryBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_yellow_0));
        } else if (mBatterySoc <= 25) {
            mPrimaryBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_yellow_1));
        } else if (mBatterySoc <= 50) {
            mPrimaryBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_yellow_2));
        } else if (mBatterySoc <= 75) {
            mPrimaryBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_green_3));
        } else if (mBatterySoc <= mPrimaryMax) {
            mPrimaryBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_green_4));
        }
        mPrimaryBatteryText.setText(String.valueOf(mBatterySoc));
    }

    public void setBatterySocSub(int value) {
        if (value < 0) {
            value = 0;
        } else if (value > mBackupMax) {
            value = 0;
        }
        mBatterySocSub = value;
        if (mBatterySocSub == 0) {
            mBackupBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_yellow_0));
        } else if (mBatterySocSub <= 25) {
            mBackupBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_yellow_1));
        } else if (mBatterySocSub <= 50) {
            mBackupBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_yellow_2));
        } else if (mBatterySocSub <= 75) {
            mBackupBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_green_3));
        } else if (mBatterySocSub <= mPrimaryMax) {
            mBackupBatteryIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery_green_4));
        }
        mBackupBatteryText.setText(String.valueOf(mBatterySocSub));
    }

    private void setPrimaryProgress(int progress, boolean fromUser) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > CURRENT_COST_MAX) {
            progress = CURRENT_COST_MAX;
        }
        mPrimaryProgress = progress;
        invalidate();
        if (mOnChangeListener != null) {
            mOnChangeListener.onProgressChanged(mPrimaryProgress, mPrimaryMax, fromUser);
        }
    }

    private void setBackupProgress(float progress, boolean fromUser) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > CURRENT_FEEDBACK_MAX) {
            progress = CURRENT_FEEDBACK_MAX;
        }
        mBackupProgress = progress;
        invalidate();
        if (mOnChangeListener != null) {
            mOnChangeListener.onProgressChanged(mBackupProgress, mBackupMax, fromUser);
        }
    }

    public int getPrimaryProgress() {
        return mPrimaryProgress;
    }

    public float getBackupProgress() {
        return mBackupProgress;
    }

    private float getRatio() {
        return mPrimaryProgress * 1.0f / CURRENT_COST_MAX;
    }

    private float getOtherRatio() {
        return mBackupProgress * 1.0f / CURRENT_FEEDBACK_MAX;
    }

    public interface OnChangeListener {
        void onStartTrackingTouch(boolean isCanDrag);

        void onProgressChanged(float progress, float max, boolean fromUser);

        void onStopTrackingTouch(boolean isCanDrag);

        void onSingleTapUp();
    }

    public void showAppendAnimation(int progress) {
        showAnimation(mPrimaryProgress, progress, mDuration);
    }

    public void mainBatteryLight() {
        // 主电池常亮
    }

    public void mainBatteryTwinkle() {
        // 主电池闪烁
    }

    public void subBatteryLight() {
        // 副电池常亮

    }

    public void subBatteryTwinkle() {
        // 副电池闪烁

    }

    public void showAnimation(int progress) {
        showAnimation(progress, mDuration);
    }

    public void showAnimation(int progress, int duration) {
        showAnimation(0, progress, duration);
    }

    public void showAnimation(int from, int to, int duration) {
        showAnimation(from, to, duration, null);
    }

    public void showAnimation2(int from, int to, int duration) {
        showAnimation2(from, to, duration, null);
    }

    public void showAnimation(int from, int to, int duration, Animator.AnimatorListener listener) {
        this.mDuration = duration;
        this.mPrimaryProgress = from;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setPrimaryProgress((int) animation.getAnimatedValue());
            }
        });

        if (listener != null) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.addListener(listener);
        }

        valueAnimator.start();
    }

    public void showAnimation2(int from, int to, int duration, Animator.AnimatorListener listener) {
        this.mDuration = duration;
        this.mBackupProgress = from;
        float fFrom = from;
        float fTo = to;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fFrom, fTo);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setBackupProgress((Float) animation.getAnimatedValue());
            }
        });

        if (listener != null) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.addListener(listener);
        }

        valueAnimator.start();
    }

    public void onPhoneEnter() {
        if (mPanelStatus == PanelStatus.PHONE) return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mPanelView, "rotationY", 0, 90);
        final ObjectAnimator animatorNewY = ObjectAnimator.ofFloat(mPhonePanel, "rotationY", -90, 0);
        animatorNewY.setInterpolator(new OvershootInterpolator(2.0f));
        animatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setChildrenVisibility(GONE);
                animatorNewY.setDuration(300).start();
                mPhonePanel.setVisibility(VISIBLE);
                mPanelStatus = PanelStatus.PHONE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorY.setDuration(300).start();
    }

    public void onPhoneExit() {
        if (mPanelStatus == PanelStatus.BATTERY) return;

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mPhonePanel, "rotationY", 0, 90);
        final ObjectAnimator animatorNewY = ObjectAnimator.ofFloat(mPanelView, "rotationY", -90, 0);
        animatorNewY.setInterpolator(new OvershootInterpolator(2.0f));

        animatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mPhonePanel.setVisibility(GONE);
                animatorNewY.setDuration(300).start();
                setChildrenVisibility(VISIBLE);
                mPanelStatus = PanelStatus.BATTERY;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorY.setDuration(300).start();
    }

    public void onSingleBatteryEnter(PanelStatus status) {
        if (status == PanelStatus.SINGLE_BATTERY || status == PanelStatus.SINGLE_BATTERY_SUB) {
            switchPanelStatus(status);
        }
    }

    public void onSingleBatteryExit() {
        switchPanelStatus(PanelStatus.BATTERY);
    }

    public void setPhoneListener(PhonePanelView.OnPhoneCallListener listener) {
        mPhonePanel.setListener(listener);
    }

    public void setChildrenVisibility(int visibility) {
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            child.setVisibility(visibility);
        }
    }

    private void switchPanelStatus(PanelStatus status) {
        switch (status) {
            case BATTERY:
                mPanelStatus = PanelStatus.BATTERY;
                doSwitchToBattery();
                break;
            case SINGLE_BATTERY:
                mPanelStatus = PanelStatus.SINGLE_BATTERY;
                doSwitchToSingleBattery();
                break;
            case SINGLE_BATTERY_SUB:
                mPanelStatus = PanelStatus.SINGLE_BATTERY_SUB;
                doSwitchToSingleBatterySub();
                break;
            default:
                break;
        }
    }

    private void doSwitchToBattery() {
        mPrimaryBatteryIcon.setVisibility(VISIBLE);
        mPrimaryBatteryText.setVisibility(VISIBLE);
        mPrimaryPersentText.setVisibility(VISIBLE);
        mBackupBatteryIcon.setVisibility(VISIBLE);
        mBackupBatteryText.setVisibility(VISIBLE);
        mBackupPersentText.setVisibility(VISIBLE);
        mUnlimitIcon.setVisibility(VISIBLE);

        int childL = (int) (mCircleCenterX - mPrimaryBatteryText.getMeasuredWidth() / 2);
        int childR = (int) (mCircleCenterX + mPrimaryBatteryText.getMeasuredWidth() / 2);
        int childT = (int) (mCircleCenterY - mPrimaryBatteryText.getMeasuredHeight() - 10);
        int childB = childT + mPrimaryBatteryText.getMeasuredHeight();
        mPrimaryBatteryText.layout(childL, childT, childR, childB);

        childL = childR + 5;
        childR = childL + mPrimaryPersentText.getMeasuredWidth();
        childT = childB - mPrimaryBatteryText.getMeasuredHeight() / 2 - mPrimaryPersentText.getMeasuredHeight() / 2;
        childB = childT + mPrimaryPersentText.getMeasuredHeight();
        mPrimaryPersentText.layout(childL, childT, childR, childB);

        childL = (int) (mCircleCenterX - mBackupBatteryText.getMeasuredWidth() / 2);
        childR = (int) (mCircleCenterX + mBackupBatteryText.getMeasuredWidth() / 2);
        childT = (int) (mCircleCenterY + 10);
        childB = childT + mBackupBatteryText.getMeasuredHeight();
        mBackupBatteryText.layout(childL, childT, childR, childB);

        childL = childR + 5;
        childR = childL + mBackupPersentText.getMeasuredWidth();
        childT = childB - mBackupBatteryText.getMeasuredHeight() / 2 - mBackupPersentText.getMeasuredHeight() / 2;
        childB = childT + mBackupPersentText.getMeasuredHeight();
        mBackupPersentText.layout(childL, childT, childR, childB);

        childL = (int) (mCircleCenterX - mUnlimitIcon.getMeasuredWidth() / 2 - mPrimaryBatteryIcon.getMeasuredWidth() - 10);
        childR = (int) (mCircleCenterX - mUnlimitIcon.getMeasuredWidth() / 2 - 10);
        childT = (int) (mCircleCenterY - mPrimaryBatteryIcon.getMeasuredHeight() / 2);
        childB = (int) (mCircleCenterY + mPrimaryBatteryIcon.getMeasuredHeight() / 2);
        mPrimaryBatteryIcon.layout(childL, childT, childR, childB);

        childL = (int) (mCircleCenterX - mUnlimitIcon.getMeasuredWidth() / 2);
        childR = (int) (mCircleCenterX + mUnlimitIcon.getMeasuredWidth() / 2);
        childT = (int) (mCircleCenterY - mUnlimitIcon.getMeasuredHeight() / 2);
        childB = (int) (mCircleCenterY + mUnlimitIcon.getMeasuredHeight() / 2);
        mUnlimitIcon.layout(childL, childT, childR, childB);

        childL = (int) (mCircleCenterX + mUnlimitIcon.getMeasuredWidth() / 2 + 10);
        childR = (int) (mCircleCenterX + mUnlimitIcon.getMeasuredWidth() / 2 + mBackupBatteryIcon.getMeasuredWidth() + 10);
        childT = (int) (mCircleCenterY - mBackupBatteryIcon.getMeasuredHeight() / 2);
        childB = (int) (mCircleCenterY + mBackupBatteryIcon.getMeasuredHeight() / 2);
        mBackupBatteryIcon.layout(childL, childT, childR, childB);
    }

    private void doSwitchToSingleBattery() {
        // 主电池有效
        mUnlimitIcon.setVisibility(INVISIBLE);
        mPrimaryBatteryIcon.setVisibility(VISIBLE);
        mPrimaryBatteryText.setVisibility(VISIBLE);
        mPrimaryPersentText.setVisibility(VISIBLE);
        mBackupBatteryIcon.setVisibility(INVISIBLE);
        mBackupBatteryText.setVisibility(INVISIBLE);
        mBackupPersentText.setVisibility(INVISIBLE);

        int l = (int) (mCircleCenterX - mPrimaryBatteryIcon.getMeasuredWidth() / 2);
        int r = (int) (mCircleCenterX + mPrimaryBatteryIcon.getMeasuredWidth() / 2);
        int t = (int) (mCircleCenterY - mPrimaryBatteryIcon.getMeasuredHeight() - 50);
        int b = t + mPrimaryBatteryIcon.getMeasuredHeight();
        mPrimaryBatteryIcon.layout(l, t, r, b);

        l = (int) (mCircleCenterX - mPrimaryBatteryText.getMeasuredWidth() / 2);
        r = (int) (mCircleCenterX + mPrimaryBatteryText.getMeasuredWidth() / 2);
        t = (int) mCircleCenterY - 50;
        b = t + mPrimaryBatteryText.getMeasuredHeight();
        mPrimaryBatteryText.layout(l, t, r, b);

        l = r + 5;
        r = l + mPrimaryPersentText.getMeasuredWidth();
        t = b - mPrimaryPersentText.getMeasuredHeight() / 2 - mPrimaryPersentText.getMeasuredHeight() / 2;
        b = t + mPrimaryPersentText.getMeasuredHeight();
        mPrimaryPersentText.layout(l, t, r, b);
    }

    private void doSwitchToSingleBatterySub() {
        // 副电池有效
        mUnlimitIcon.setVisibility(INVISIBLE);
        mBackupBatteryIcon.setVisibility(VISIBLE);
        mBackupBatteryText.setVisibility(VISIBLE);
        mBackupPersentText.setVisibility(VISIBLE);
        mPrimaryBatteryIcon.setVisibility(INVISIBLE);
        mPrimaryBatteryText.setVisibility(INVISIBLE);
        mPrimaryPersentText.setVisibility(INVISIBLE);

        int l = (int) (mCircleCenterX - mBackupBatteryIcon.getMeasuredWidth() / 2);
        int r = (int) (mCircleCenterX + mBackupBatteryIcon.getMeasuredWidth() / 2);
        int t = (int) (mCircleCenterY - mBackupBatteryIcon.getMeasuredHeight() - 50);
        int b = t + mBackupBatteryIcon.getMeasuredHeight();
        mBackupBatteryIcon.layout(l, t, r, b);

        l = (int) (mCircleCenterX - mBackupBatteryText.getMeasuredWidth() / 2);
        r = (int) (mCircleCenterX + mBackupBatteryText.getMeasuredWidth() / 2);
        t = (int) mCircleCenterY - 50;
        b = t + mBackupBatteryText.getMeasuredHeight();
        mBackupBatteryText.layout(l, t, r, b);

        l = r + 5;
        r = l + mBackupPersentText.getMeasuredWidth();
        t = b - mBackupBatteryText.getMeasuredHeight() / 2 - mBackupPersentText.getMeasuredHeight() / 2;
        b = t + mBackupPersentText.getMeasuredHeight();
        mBackupPersentText.layout(l, t, r, b);
    }
}

