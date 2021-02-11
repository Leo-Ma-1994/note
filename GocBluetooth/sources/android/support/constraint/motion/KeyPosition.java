package android.support.constraint.motion;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.support.constraint.R;
import android.support.constraint.motion.utils.Easing;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;

public class KeyPosition extends KeyPositionBase {
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    private static final String PERCENT_X = "percentX";
    private static final String PERCENT_Y = "percentY";
    private static final String TAG = "KeyPosition";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;
    int mDrawPath = 0;
    int mPathMotionArc = UNSET;
    float mPercentHeight = Float.NaN;
    float mPercentWidth = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    int mPositionType = 0;
    String mTransitionEasing = null;

    public KeyPosition() {
        this.mType = 2;
    }

    @Override // android.support.constraint.motion.Key
    public void load(Context context, AttributeSet attrs) {
        Loader.read(this, context.obtainStyledAttributes(attrs, R.styleable.KeyPosition));
    }

    @Override // android.support.constraint.motion.Key
    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.motion.KeyPositionBase
    public void calcPosition(int layoutWidth, int layoutHeight, float start_x, float start_y, float end_x, float end_y) {
        int i = this.mPositionType;
        if (i == 1) {
            calcPathPosition(start_x, start_y, end_x, end_y);
        } else if (i != 2) {
            calcCartesianPosition(start_x, start_y, end_x, end_y);
        } else {
            calcScreenPosition(layoutWidth, layoutHeight);
        }
    }

    private void calcScreenPosition(int layoutWidth, int layoutHeight) {
        float f = this.mPercentX;
        this.mCalculatedPositionX = (((float) (layoutWidth - 0)) * f) + ((float) (0 / 2));
        this.mCalculatedPositionY = (((float) (layoutHeight - 0)) * f) + ((float) (0 / 2));
    }

    private void calcPathPosition(float start_x, float start_y, float end_x, float end_y) {
        float pathVectorX = end_x - start_x;
        float pathVectorY = end_y - start_y;
        float f = this.mPercentX;
        float f2 = this.mPercentY;
        this.mCalculatedPositionX = (pathVectorX * f) + start_x + ((-pathVectorY) * f2);
        this.mCalculatedPositionY = (f * pathVectorY) + start_y + (f2 * pathVectorX);
    }

    private void calcCartesianPosition(float start_x, float start_y, float end_x, float end_y) {
        float pathVectorX = end_x - start_x;
        float pathVectorY = end_y - start_y;
        float dxdy = 0.0f;
        float dxdx = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float dydx = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float dydy = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        if (!Float.isNaN(this.mAltPercentX)) {
            dxdy = this.mAltPercentX;
        }
        this.mCalculatedPositionX = (float) ((int) ((pathVectorX * dxdx) + start_x + (pathVectorY * dxdy)));
        this.mCalculatedPositionY = (float) ((int) ((pathVectorX * dydx) + start_y + (pathVectorY * dydy)));
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.motion.KeyPositionBase
    public float getPositionX() {
        return this.mCalculatedPositionX;
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.constraint.motion.KeyPositionBase
    public float getPositionY() {
        return this.mCalculatedPositionY;
    }

    @Override // android.support.constraint.motion.KeyPositionBase
    public void positionAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        int i = this.mPositionType;
        if (i == 1) {
            positionPathAttributes(start, end, x, y, attribute, value);
        } else if (i != 2) {
            positionCartAttributes(start, end, x, y, attribute, value);
        } else {
            positionScreenAttributes(view, start, end, x, y, attribute, value);
        }
    }

    /* access modifiers changed from: package-private */
    public void positionPathAttributes(RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float pathVectorX = end.centerX() - startCenterX;
        float pathVectorY = end.centerY() - startCenterY;
        float distance = (float) Math.hypot((double) pathVectorX, (double) pathVectorY);
        if (((double) distance) < 1.0E-4d) {
            System.out.println("distance ~ 0");
            value[0] = 0.0f;
            value[1] = 0.0f;
            return;
        }
        float dx = pathVectorX / distance;
        float dy = pathVectorY / distance;
        float perpendicular = (((y - startCenterY) * dx) - ((x - startCenterX) * dy)) / distance;
        float dist = (((x - startCenterX) * dx) + ((y - startCenterY) * dy)) / distance;
        if (attribute[0] == null) {
            attribute[0] = PERCENT_X;
            attribute[1] = PERCENT_Y;
            value[0] = dist;
            value[1] = perpendicular;
        } else if (PERCENT_X.equals(attribute[0])) {
            value[0] = dist;
            value[1] = perpendicular;
        }
    }

    /* access modifiers changed from: package-private */
    public void positionScreenAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float centerX = end.centerX() - startCenterX;
        float centerY = end.centerY() - startCenterY;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (attribute[0] == null) {
            attribute[0] = PERCENT_X;
            value[0] = x / ((float) width);
            attribute[1] = PERCENT_Y;
            value[1] = y / ((float) height);
        } else if (PERCENT_X.equals(attribute[0])) {
            value[0] = x / ((float) width);
            value[1] = y / ((float) height);
        } else {
            value[1] = x / ((float) width);
            value[0] = y / ((float) height);
        }
    }

    /* access modifiers changed from: package-private */
    public void positionCartAttributes(RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float pathVectorX = end.centerX() - startCenterX;
        float pathVectorY = end.centerY() - startCenterY;
        if (attribute[0] == null) {
            attribute[0] = PERCENT_X;
            value[0] = (x - startCenterX) / pathVectorX;
            attribute[1] = PERCENT_Y;
            value[1] = (y - startCenterY) / pathVectorY;
        } else if (PERCENT_X.equals(attribute[0])) {
            value[0] = (x - startCenterX) / pathVectorX;
            value[1] = (y - startCenterY) / pathVectorY;
        } else {
            value[1] = (x - startCenterX) / pathVectorX;
            value[0] = (y - startCenterY) / pathVectorY;
        }
    }

    @Override // android.support.constraint.motion.KeyPositionBase
    public boolean intersects(int layoutWidth, int layoutHeight, RectF start, RectF end, float x, float y) {
        calcPosition(layoutWidth, layoutHeight, start.centerX(), start.centerY(), end.centerX(), end.centerY());
        if (Math.abs(x - this.mCalculatedPositionX) >= 20.0f || Math.abs(y - this.mCalculatedPositionY) >= 20.0f) {
            return false;
        }
        return true;
    }

    private static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap = new SparseIntArray();

        private Loader() {
        }

        static {
            mAttrMap.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        /* access modifiers changed from: private */
        public static void read(KeyPosition c, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mAttrMap.get(attr)) {
                    case 1:
                        if (!MotionLayout.IS_IN_EDIT_MODE) {
                            if (a.peekValue(attr).type == 3) {
                                c.mTargetString = a.getString(attr);
                                break;
                            } else {
                                c.mTargetId = a.getResourceId(attr, c.mTargetId);
                                break;
                            }
                        } else {
                            c.mTargetId = a.getResourceId(attr, c.mTargetId);
                            if (c.mTargetId == -1) {
                                c.mTargetString = a.getString(attr);
                                break;
                            } else {
                                break;
                            }
                        }
                    case 2:
                        c.mFramePosition = a.getInt(attr, c.mFramePosition);
                        break;
                    case 3:
                        if (a.peekValue(attr).type == 3) {
                            c.mTransitionEasing = a.getString(attr);
                            break;
                        } else {
                            c.mTransitionEasing = Easing.NAMED_EASING[a.getInteger(attr, 0)];
                            break;
                        }
                    case 4:
                        c.mCurveFit = a.getInteger(attr, c.mCurveFit);
                        break;
                    case 5:
                        c.mDrawPath = a.getInt(attr, c.mDrawPath);
                        break;
                    case 6:
                        c.mPercentX = a.getFloat(attr, c.mPercentX);
                        break;
                    case 7:
                        c.mPercentY = a.getFloat(attr, c.mPercentY);
                        break;
                    case 8:
                        float f = a.getFloat(attr, c.mPercentHeight);
                        c.mPercentWidth = f;
                        c.mPercentHeight = f;
                        break;
                    case 9:
                        c.mPositionType = a.getInt(attr, c.mPositionType);
                        break;
                    case 10:
                        c.mPathMotionArc = a.getInt(attr, c.mPathMotionArc);
                        break;
                    case 11:
                        c.mPercentWidth = a.getFloat(attr, c.mPercentWidth);
                        break;
                    case 12:
                        c.mPercentHeight = a.getFloat(attr, c.mPercentHeight);
                        break;
                    default:
                        Log.e("KeyPosition", "unused attribute 0x" + Integer.toHexString(attr) + "   " + mAttrMap.get(attr));
                        break;
                }
            }
            if (c.mFramePosition == -1) {
                Log.e("KeyPosition", "no frame position");
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.support.constraint.motion.Key
    public void setValue(String tag, Object value) {
        char c;
        switch (tag.hashCode()) {
            case -1812823328:
                if (tag.equals("transitionEasing")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1127236479:
                if (tag.equals("percentWidth")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1017587252:
                if (tag.equals("percentHeight")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -827014263:
                if (tag.equals("drawPath")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -200259324:
                if (tag.equals("sizePercent")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 428090547:
                if (tag.equals(PERCENT_X)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 428090548:
                if (tag.equals(PERCENT_Y)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTransitionEasing = value.toString();
                return;
            case 1:
                this.mDrawPath = toInt(value);
                return;
            case 2:
                this.mPercentWidth = toFloat(value);
                return;
            case 3:
                this.mPercentHeight = toFloat(value);
                return;
            case 4:
                float f = toFloat(value);
                this.mPercentWidth = f;
                this.mPercentHeight = f;
                return;
            case 5:
                this.mPercentX = toFloat(value);
                return;
            case 6:
                this.mPercentY = toFloat(value);
                return;
            default:
                return;
        }
    }
}
