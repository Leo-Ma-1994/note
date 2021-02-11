package android.support.constraint.motion;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintHelper;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.R;
import android.support.constraint.motion.MotionLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MotionHelper extends ConstraintHelper implements Animatable, MotionLayout.TransitionListener {
    private float mProgress;
    private boolean mUseOnHide = false;
    private boolean mUseOnShow = false;
    protected View[] views;

    public MotionHelper(Context context) {
        super(context);
    }

    public MotionHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MotionHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.constraint.ConstraintHelper
    public void init(AttributeSet attrs) {
        super.init(attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MotionHelper);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.MotionHelper_onShow) {
                    this.mUseOnShow = a.getBoolean(attr, this.mUseOnShow);
                } else if (attr == R.styleable.MotionHelper_onHide) {
                    this.mUseOnHide = a.getBoolean(attr, this.mUseOnHide);
                }
            }
            a.recycle();
        }
    }

    public boolean isUsedOnShow() {
        return this.mUseOnShow;
    }

    public boolean isUseOnHide() {
        return this.mUseOnHide;
    }

    @Override // android.support.constraint.motion.Animatable
    public float getProgress() {
        return this.mProgress;
    }

    @Override // android.support.constraint.motion.Animatable
    public void setProgress(float progress) {
        this.mProgress = progress;
        if (this.mCount > 0) {
            this.views = getViews((ConstraintLayout) getParent());
            for (int i = 0; i < this.mCount; i++) {
                setProgress(this.views[i], progress);
            }
            return;
        }
        ViewGroup group = (ViewGroup) getParent();
        int count = group.getChildCount();
        for (int i2 = 0; i2 < count; i2++) {
            View view = group.getChildAt(i2);
            if (!(view instanceof MotionHelper)) {
                setProgress(view, progress);
            }
        }
    }

    public void setProgress(View view, float progress) {
    }

    @Override // android.support.constraint.motion.MotionLayout.TransitionListener
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
    }

    @Override // android.support.constraint.motion.MotionLayout.TransitionListener
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
    }

    @Override // android.support.constraint.motion.MotionLayout.TransitionListener
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
    }

    @Override // android.support.constraint.motion.MotionLayout.TransitionListener
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
    }
}
