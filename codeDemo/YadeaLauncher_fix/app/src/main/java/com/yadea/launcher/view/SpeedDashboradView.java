package com.yadea.launcher.view;

import android.animation.Animator;
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
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yadea.launcher.R;
import com.yadea.launcher.util.FontSettingUtil;

public class SpeedDashboradView extends ConstraintLayout {
    private static final String TAG = "SpeedDashborad";

    private Bitmap mBackgroundBitmap;

     /**
     * 画笔
     */
    private Paint mPaint;
    private TextPaint mTextPaint;

    /**
     * viewgrpoup坐标
     */
    private float mCenterX;
    private float mCenterY;
    /**
     * 圆心
     */
    private float mCircleCenterX;

    private float mCircleCenterY;

    private float offsetY = 7.0f;
    /**
     * 半径
     */
    private float mRadius;
    /**
     * 当前速度
     */
    private int mSpeed = 0;

    private ImageView mDashboardView;

    public int getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(int mSpeed) {
        Log.d(TAG, "setmSpeed: "+ mSpeed);
        this.mSpeed = mSpeed;
        invalidate();
    }

    /**
     * 最大进度
     */
    private int mSpeedMax = 100;

    /**
     * 当前进度
     */
    private int mProgress = 0;

    /**
     * 动画持续的时间
     */
    private int mDuration = 3000;

    /**
     * 着色器
     */
    private Shader mShader;
    private int[] mShaderColors = new int[]{0xFF5FB0FF,0xFF0093FF,0xFF022652,0xFF0093FF};


    private OnChangeListener mOnChangeListener;


    /**
     * 文字相关
     */
    private Shader mNumShader;
    private int[] mTextColors = new int[]{0xFFFFFFFF, 0xFFFFFFFF, 0xFF4D779D};

    public SpeedDashboradView(@NonNull Context context) {
        this(context, null);
    }

    public SpeedDashboradView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeedDashboradView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setWillNotDraw(false);
    }


    public void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
       // mTextfont = Typeface.createFromAsset(getContext().getAssets(), "fonts/MicroTechnic.otf");
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ele_panel_blue, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDashboardView = (ImageView) getChildAt(0);

    }


    /**
    * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
    */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //viewgroup中心坐标
        mCenterX = (width + getPaddingLeft() - getPaddingRight()) / 2.0f;

        mCenterY = (height + getPaddingTop() - getPaddingBottom()) / 2.0f;

        //圆心坐标
        mCircleCenterX = mCenterX;
        mCircleCenterY = mCenterY + offsetY;

        //半径
        mRadius = width / 2.0f;
    }

    /**
     * 为子View摆放位置
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawText(canvas);
        drawArc(canvas);
    }


    private void drawBackground(Canvas canvas) {
        // 绘制背景
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);

    }

    /**
     * 绘制数字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        mTextPaint.reset();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //设置字体
        FontSettingUtil.setPaintFont(this.getContext(), mTextPaint, FontSettingUtil.MT_REGULAR);
        mTextPaint.setTextSize(158);
        // 计算文字高度 
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;

        //设置数字渐变效果
        mNumShader = new LinearGradient(mCircleCenterX, 0, mCircleCenterX, fontHeight, mTextColors, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
        mTextPaint.setShader(mNumShader);
        int textCenterX = (int) mCircleCenterX;
        int textCenterY = (int) (mCircleCenterY + fontHeight / 2 - fontMetrics.bottom) ;



        canvas.drawText(getmSpeed()+"", textCenterX, textCenterY - 20 , mTextPaint);

    }

    //绘制进度条
    private void drawArc(Canvas canvas){
        float ringWidth = 38.0f;
        mShader =  new RadialGradient(mCircleCenterX, mCircleCenterY, mRadius, mShaderColors, new float[]{0.1f, 0.6f, 0.99f, 1f}, Shader.TileMode.CLAMP);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setShader(mShader);
        float radius = mRadius - ringWidth / 2 -2;

        // 绘制进度条
        RectF rectF1 = new RectF(mCircleCenterX - radius, mCircleCenterY - radius,
                mCircleCenterX + radius, mCircleCenterY + radius);

        float ratio = getRatio();
        if (ratio > 0) {
            canvas.drawArc(rectF1, 140, 260 * ratio, false, mPaint);
        }



    }



    //获取进度条进度
    private float getRatio() {
        return mProgress * 1.0f / mSpeedMax;
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        setProgress(progress, false);
    }

    private void setProgress(int progress, boolean fromUser) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > mSpeedMax) {
            progress = mSpeedMax;
        }
        this.mProgress = progress;
//        mProgressPercent = (int) (mProgress * 100.0f / mMax);
        invalidate();

        if (mOnChangeListener != null) {
            mOnChangeListener.onProgressChanged(mProgress, mSpeedMax, fromUser);
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public interface OnChangeListener {

        void onProgressChanged(float progress, float max, boolean fromUser);
    }

    /**
     * 显示进度动画效果（根据当前已有进度开始）
     *
     * @param progress
     */
    public void showAppendAnimation(int progress) {
        showAnimation(mProgress, progress, mDuration);
    }

    /**
     * 显示进度动画效果
     *
     * @param progress
     */
    public void showAnimation(int progress) {
        showAnimation(progress, mDuration);
    }

    /**
     * 显示进度动画效果
     *
     * @param progress
     * @param duration 动画时长
     */
    public void showAnimation(int progress, int duration) {
        showAnimation(0, progress, duration);
    }

    /**
     * 显示进度动画效果，从from到to变化
     *
     * @param from
     * @param to
     * @param duration 动画时长
     */
    public void showAnimation(int from, int to, int duration) {
        showAnimation(from, to, duration, null);
    }

    /**
     * 显示进度动画效果，从from到to变化
     *
     * @param from
     * @param to
     * @param duration 动画时长
     * @param listener
     */
    public void showAnimation(int from, int to, int duration, Animator.AnimatorListener listener) {
        this.mDuration = duration;
        this.mProgress = from;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setProgress((int) animation.getAnimatedValue());
            }
        });

        if (listener != null) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.addListener(listener);
        }

        valueAnimator.start();
    }






}
