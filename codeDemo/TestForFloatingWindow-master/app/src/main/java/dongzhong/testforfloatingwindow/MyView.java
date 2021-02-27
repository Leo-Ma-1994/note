package dongzhong.testforfloatingwindow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    private String mText;

    private int mTextColor;
    /**
     * 文本的大小
     */
    private int mTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public MyView(Context context) {
        this(context, null);
    }
    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化
        mText = "LEO MA";
        mTextColor = Color.BLACK;
        mTextSize = 100;

        setAlpha(0.4f);
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //获得绘制文本的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制文字
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
    



}
