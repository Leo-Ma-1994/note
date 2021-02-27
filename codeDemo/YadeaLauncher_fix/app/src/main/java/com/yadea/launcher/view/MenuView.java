package com.yadea.launcher.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;

import com.yadea.launcher.R;
import com.yadea.launcher.bean.AppInfo;
import com.yadea.launcher.util.FontSettingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 滚动选择器
 *
 * @author chenjing
 *
 */
public class MenuView extends View
{

    public static final String TAG = "MenuView";

    /**
     * text之间间距和minTextSize之比
     */
    public static final float MARGIN_ALPHA = 3.8f;
    /**
     * 自动回滚到中间的速度
     */
    public static final float SPEED = 2;

    private List<AppInfo> mDataList;
    /**
     * 选中的位置，这个位置是mDataList的中心位置，一直不变
     */
    private int mCurrentSelected;
    private Paint mPaint;

    private float mMaxTextSize = 34;
    private float mMinTextSize = 16;

    private float mMaxTextAlpha = 255;
    private float mMinTextAlpha = 120;
    private float paddingWidth = 25;

    private int mColorText = 0xFFFFFF;

    private int mViewHeight;
    private int mViewWidth;


    private  float mStartDownY;
    private float mLastDownY;
    private long mLastTapTime;
    /**
     * 滑动的距离
     */
    private float mMoveLen = 0;
    private boolean isInit = false;
    private onSelectListener mSelectListener;
    private onClickListener mClickListener;

    private Timer timer;
    private MyTimerTask mTask;

    Handler updateHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            if (Math.abs(mMoveLen) < SPEED)
            {
                mMoveLen = 0;
                if (mTask != null)
                {
                    mTask.cancel();
                    mTask = null;
                    performSelect();

                }
            } else
                // 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
                mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
            invalidate();
        }

    };

    public MenuView(Context context)
    {
        super(context);

        init();
    }

    public MenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public void setOnSelectListener(onSelectListener listener)
    {
        mSelectListener = listener;
    }


    private void performSelect()
    {
        if (mSelectListener != null)
            mSelectListener.onSelect(mDataList.get(mCurrentSelected).getAppName());
    }



    public void setData(List<AppInfo> datas)
    {

        mDataList = datas;
        mCurrentSelected = datas.size() / 2;
        invalidate();
    }

    public void setSelected(int selected)
    {
        mCurrentSelected = selected;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        isInit = true;
        invalidate();
    }

    private void init()
    {
        timer = new Timer();
        mDataList = new ArrayList<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.FILL);
        mPaint.setTextAlign(Align.RIGHT);
        mPaint.setColor(mColorText);
        //设置字体
//        Typeface font = ResourcesCompat.getFont(this.getContext(), R.font.micro_technic);
//        mPaint.setTypeface(font);

        FontSettingUtil.setPaintFont(this.getContext(), mPaint, FontSettingUtil.MT_REGULAR);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 根据index绘制view
        if (isInit)
            drawData(canvas);
    }

    private void drawData(Canvas canvas)
    {
        Rect rect = new Rect(0, 0, mViewWidth, mViewHeight);
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //设置矩形框背景色
        Shader bgShader = new LinearGradient(0,0,mViewWidth,0,new int[]{0x00000000,0x8F000000},new float[]{0,0.8f}, Shader.TileMode.CLAMP);
        bgPaint.setShader(bgShader);
        canvas.drawRect(rect, bgPaint);
        // 先绘制选中的text再往上往下绘制其余的text
        float scale = parabola(mViewHeight / 4.0f, mMoveLen);
        float size = 34;
        mPaint.setTextSize(size);
        mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        float x = mViewWidth-paddingWidth;
        float y = (float) (mViewHeight / 2.0 + mMoveLen);
        FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

        canvas.drawText(mDataList.get(mCurrentSelected).getAppName(), x, baseline, mPaint);

        // 绘制上方data
        for (int i = 1; (mCurrentSelected - i) >= 0; i++)
        {
            drawOtherText(canvas, i, -1);
        }
        // 绘制下方data
        for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++)
        {
            drawOtherText(canvas, i, 1);
        }

    }

    /**
     * @param canvas
     * @param position
     *            距离mCurrentSelected的差值
     * @param type
     *            1表示向下绘制，-1表示向上绘制
     */
    private void drawOtherText(Canvas canvas, int position, int type)
    {
        float size;//字体大小
        float alpha ;
        if(position ==1 ){
           size = 24;
           alpha = 0.7f;
        }else{
            size = 16;
            alpha = 0.4f;
        }

        float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type
                * mMoveLen);

        float scale = parabola(mViewHeight / 10.0f, d);

        mPaint.setTextSize(size);
        mPaint.setAlpha((int) (255*alpha));
        float x = mViewWidth - paddingWidth;
        float y = (float) (mViewHeight / 2.0 + type * d);
        FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(mDataList.get(mCurrentSelected + type * position).getAppName(),
                x, baseline, mPaint);

    }

    /**
     * 抛物线
     *
     * @param zero
     *            零点坐标
     * @param x
     *            偏移量
     * @return scale
     */
    private float parabola(float zero, float x)
    {
        float f = (float) (1 - Math.pow(x / zero, 2));
        return f < 0 ? 0 : f;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                mStartDownY = event.getY();
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event);
                break;
            case MotionEvent.ACTION_UP:

                doUp(event);
                break;
        }
        return true;
    }

    private void doDown(MotionEvent event)
    {
        if (mTask != null)
        {
            mTask.cancel();
            mTask = null;
        }
        mLastDownY = event.getY();
        mLastTapTime = System.currentTimeMillis();
    }

    private void doMove(MotionEvent event)
    {

        mMoveLen += (event.getY() - mLastDownY);


        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2 )
        {
            // 往下滑超过离开距离
            if(mCurrentSelected > 0){
                mCurrentSelected -=1;
            }
            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2)
        {
            // 往上滑超过离开距离

            if(mCurrentSelected < mDataList.size()-1){
                mCurrentSelected +=1;

            }

            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
        }

        mLastDownY = event.getY();
        invalidate();
    }

    private void doUp( MotionEvent event)
    {
        long tapInterval = System.currentTimeMillis() - mLastTapTime;
        if (tapInterval < 300) {
            /**
             * 判断点击是否在点击区域内
             */
            boolean isCenterX = event.getX() < this.getWidth() && event.getX() > this.getWidth() - 300; //屏幕右侧边缘往左300宽度
            boolean isCenterY = event.getY()<this.getHeight() / 2 + 50 && event.getY()>this.getHeight() / 2 - 50;//中间上下50
            /**
             * 点击事件跳转相关页面
             */
            if(isCenterX && isCenterY){
                mClickListener.OnClick(mDataList.get(mCurrentSelected).getAppName());
            }
            mLastTapTime = 0;
        }
        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
        if (Math.abs(mMoveLen) < 0.0001)
        {
            mMoveLen = 0;
            return;
        }

        if (mTask != null)
        {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(updateHandler);
        timer.schedule(mTask, 0, 10);

    }

    public void setOnClickListener(onClickListener listener) {
        mClickListener = listener;
    }

    class MyTimerTask extends TimerTask
    {
        Handler handler;

        public MyTimerTask(Handler handler)
        {
            this.handler = handler;
        }

        @Override
        public void run()
        {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    public interface onSelectListener
    {
        void onSelect(String text);
    }

    public interface onClickListener
    {
        void OnClick(String text);
    }







}