package com.yadea.launcher.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yadea.launcher.LauncherApplication;

/**
 * Launcher水平滑动滑动，继承HorizontalScrollView
 */
public class SlidingLauncherView extends HorizontalScrollView {


    private int mClickCnt = 0;
    private Handler mClickHandler = new Handler();
    private static final String TAG = "SlidingLauncherView";
    private float mOffsetX,mOffsetY;
    private float mLastPosX,mLastPosY;
    /**
     * 中控主界面
     */
    private ViewGroup mMainLayout;
    /**
     * 侧滑菜单界面
     */
    private ViewGroup mMenuLayout;
    /**
     * 侧滑菜单界面宽度
     */
    private int mMenuLayoutWidth;
    /**
     * 侧滑界面完全显示距离屏幕左边的距离
     */
    private int mRightLayoutMarginLeft = 400; //px

    //屏幕宽高
    private int mScreenWidth = 1024;
    private int mScreenHeight = 600;

    private Context mContext;

    public SlidingLauncherView(Context context) {
        this(context, null);
    }

    public SlidingLauncherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLauncherView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LinearLayout v = (LinearLayout) this.getChildAt(0);
        mMainLayout = (ViewGroup) v.getChildAt(0);
        mMenuLayout = (ViewGroup) v.getChildAt(1);

        //主界面的宽度
        mMainLayout.getLayoutParams().width = mScreenWidth;
        //菜单页的宽度
        mMenuLayout.getLayoutParams().width = mMenuLayoutWidth = mScreenWidth - mRightLayoutMarginLeft;



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mClickCnt++;
                mClickHandler.postDelayed(() -> {
                    if (mClickCnt == 1) {
                    } else if (mClickCnt == 2) {
                        Intent intent = new Intent("com.yadea.launcher.control");
                        getContext().startActivity(intent);
                    } else {
                        Log.d("jiale", "click count: " + mClickCnt);
                    }

                    mClickHandler.removeCallbacksAndMessages(null);
                    mClickCnt = 0;
                }, 300);
                break;
            case MotionEvent.ACTION_UP:
                // scrollX为水平滚动条滚动的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuLayoutWidth / 2) {
                    this.smoothScrollTo(mMenuLayoutWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                // 拦截事件
                return true;


        }
        return super.onTouchEvent(ev);
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //l为scroll偏移的长度
        float scale = l * 1.0f / mMenuLayoutWidth;// 0.0~1.0
        // 将主界面平移l（第一个参数） 实现抽屉式侧滑菜单，相对运动
        mMainLayout.setTranslationX(mMenuLayoutWidth * scale);


    }

    //上下和左右滑动冲突解决
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result=false;
        LauncherApplication application = (LauncherApplication) getContext().getApplicationContext();
        if (application.getComingPhone())
            return false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mOffsetX=0.0F;
                mOffsetY=0.0F;
                mLastPosX=ev.getX();
                mLastPosY=ev.getY();
                result= super.onInterceptTouchEvent(ev);//false手势传递给子控件
                break;
            default:
                float thisPosX=ev.getX();
                float thisPosY=ev.getY();

                mOffsetX+=Math.abs(thisPosX-mLastPosX);//x偏移
                mOffsetY+=Math.abs(thisPosY-mLastPosY);//y轴偏移

                mLastPosY=thisPosY;
                mLastPosX=thisPosX;

                if(mOffsetX<3&&mOffsetY<3)
                    result=false;//传给子控件
                else if(mOffsetY<mOffsetX)
                    result =true;//不传给子控件，自己水平滑动
                else
                    result=false;//传给子控件
                break;
        }
        return result;
    }
}
