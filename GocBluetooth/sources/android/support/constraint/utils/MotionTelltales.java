package android.support.constraint.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.constraint.R;
import android.support.constraint.motion.MotionLayout;
import android.util.AttributeSet;
import android.view.ViewParent;

public class MotionTelltales extends MockView {
    private static final String TAG = "MotionTelltales";
    Matrix mInvertMatrix;
    MotionLayout mMotionLayout;
    private Paint mPaintTelltales;
    int mTailColor;
    float mTailScale;
    int mVelocityMode;
    float[] velocity;

    public MotionTelltales(Context context) {
        super(context);
        this.mPaintTelltales = new Paint();
        this.velocity = new float[2];
        this.mInvertMatrix = new Matrix();
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        init(context, null);
    }

    public MotionTelltales(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaintTelltales = new Paint();
        this.velocity = new float[2];
        this.mInvertMatrix = new Matrix();
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        init(context, attrs);
    }

    public MotionTelltales(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaintTelltales = new Paint();
        this.velocity = new float[2];
        this.mInvertMatrix = new Matrix();
        this.mVelocityMode = 0;
        this.mTailColor = -65281;
        this.mTailScale = 0.25f;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MotionTelltales);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.MotionTelltales_telltales_tailColor) {
                    this.mTailColor = a.getColor(attr, this.mTailColor);
                } else if (attr == R.styleable.MotionTelltales_telltales_velocityMode) {
                    this.mVelocityMode = a.getInt(attr, this.mVelocityMode);
                } else if (attr == R.styleable.MotionTelltales_telltales_tailScale) {
                    this.mTailScale = a.getFloat(attr, this.mTailScale);
                }
            }
            a.recycle();
        }
        this.mPaintTelltales.setColor(this.mTailColor);
        this.mPaintTelltales.setStrokeWidth(5.0f);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setText(CharSequence text) {
        this.mText = text.toString();
        requestLayout();
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        postInvalidate();
    }

    @Override // android.support.constraint.utils.MockView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getMatrix().invert(this.mInvertMatrix);
        if (this.mMotionLayout == null) {
            ViewParent vp = getParent();
            if (vp instanceof MotionLayout) {
                this.mMotionLayout = (MotionLayout) vp;
                return;
            }
            return;
        }
        int width = getWidth();
        int height = getHeight();
        float[] f = {0.1f, 0.25f, 0.5f, 0.75f, 0.9f};
        for (float py : f) {
            for (float px : f) {
                this.mMotionLayout.getViewVelocity(this, px, py, this.velocity, this.mVelocityMode);
                this.mInvertMatrix.mapVectors(this.velocity);
                float sx = ((float) width) * px;
                float sy = ((float) height) * py;
                float[] fArr = this.velocity;
                float f2 = fArr[0];
                float f3 = this.mTailScale;
                float ey = sy - (fArr[1] * f3);
                this.mInvertMatrix.mapVectors(fArr);
                canvas.drawLine(sx, sy, sx - (f2 * f3), ey, this.mPaintTelltales);
            }
        }
    }
}
