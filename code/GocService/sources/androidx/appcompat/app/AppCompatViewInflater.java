package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import com.goodocom.bttek.BuildConfig;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {16843375};
    private final Object[] mConstructorArgs = new Object[2];

    /* access modifiers changed from: package-private */
    public final View createView(View parent, String name, Context context, AttributeSet attrs, boolean inheritContext, boolean readAndroidTheme, boolean readAppTheme, boolean wrapContext) {
        View view;
        if (inheritContext && parent != null) {
            context = parent.getContext();
        }
        if (readAndroidTheme || readAppTheme) {
            context = themifyContext(context, attrs, readAndroidTheme, readAppTheme);
        }
        if (wrapContext) {
            context = TintContextWrapper.wrap(context);
        }
        char c = 65535;
        switch (name.hashCode()) {
            case -1946472170:
                if (name.equals("RatingBar")) {
                    c = 11;
                    break;
                }
                break;
            case -1455429095:
                if (name.equals("CheckedTextView")) {
                    c = '\b';
                    break;
                }
                break;
            case -1346021293:
                if (name.equals("MultiAutoCompleteTextView")) {
                    c = '\n';
                    break;
                }
                break;
            case -938935918:
                if (name.equals("TextView")) {
                    c = 0;
                    break;
                }
                break;
            case -937446323:
                if (name.equals("ImageButton")) {
                    c = 5;
                    break;
                }
                break;
            case -658531749:
                if (name.equals("SeekBar")) {
                    c = '\f';
                    break;
                }
                break;
            case -339785223:
                if (name.equals("Spinner")) {
                    c = 4;
                    break;
                }
                break;
            case 776382189:
                if (name.equals("RadioButton")) {
                    c = 7;
                    break;
                }
                break;
            case 799298502:
                if (name.equals("ToggleButton")) {
                    c = '\r';
                    break;
                }
                break;
            case 1125864064:
                if (name.equals("ImageView")) {
                    c = 1;
                    break;
                }
                break;
            case 1413872058:
                if (name.equals("AutoCompleteTextView")) {
                    c = '\t';
                    break;
                }
                break;
            case 1601505219:
                if (name.equals("CheckBox")) {
                    c = 6;
                    break;
                }
                break;
            case 1666676343:
                if (name.equals("EditText")) {
                    c = 3;
                    break;
                }
                break;
            case 2001146706:
                if (name.equals("Button")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                view = createTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 1:
                view = createImageView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 2:
                view = createButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case 3:
                view = createEditText(context, attrs);
                verifyNotNull(view, name);
                break;
            case 4:
                view = createSpinner(context, attrs);
                verifyNotNull(view, name);
                break;
            case 5:
                view = createImageButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case 6:
                view = createCheckBox(context, attrs);
                verifyNotNull(view, name);
                break;
            case 7:
                view = createRadioButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case '\b':
                view = createCheckedTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case '\t':
                view = createAutoCompleteTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case '\n':
                view = createMultiAutoCompleteTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 11:
                view = createRatingBar(context, attrs);
                verifyNotNull(view, name);
                break;
            case '\f':
                view = createSeekBar(context, attrs);
                verifyNotNull(view, name);
                break;
            case '\r':
                view = createToggleButton(context, attrs);
                verifyNotNull(view, name);
                break;
            default:
                view = createView(context, name, attrs);
                break;
        }
        if (view == null && context != context) {
            view = createViewFromTag(context, name, attrs);
        }
        if (view != null) {
            checkOnClickListener(view, attrs);
        }
        return view;
    }

    /* access modifiers changed from: protected */
    public AppCompatTextView createTextView(Context context, AttributeSet attrs) {
        return new AppCompatTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatImageView createImageView(Context context, AttributeSet attrs) {
        return new AppCompatImageView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatButton createButton(Context context, AttributeSet attrs) {
        return new AppCompatButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatEditText createEditText(Context context, AttributeSet attrs) {
        return new AppCompatEditText(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatSpinner createSpinner(Context context, AttributeSet attrs) {
        return new AppCompatSpinner(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatImageButton createImageButton(Context context, AttributeSet attrs) {
        return new AppCompatImageButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new AppCompatCheckBox(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new AppCompatRadioButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attrs) {
        return new AppCompatCheckedTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatAutoCompleteTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatMultiAutoCompleteTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRatingBar createRatingBar(Context context, AttributeSet attrs) {
        return new AppCompatRatingBar(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatSeekBar createSeekBar(Context context, AttributeSet attrs) {
        return new AppCompatSeekBar(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatToggleButton createToggleButton(Context context, AttributeSet attrs) {
        return new AppCompatToggleButton(context, attrs);
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }

    /* access modifiers changed from: protected */
    public View createView(Context context, String name, AttributeSet attrs) {
        return null;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        Object obj = null;
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        char c = 1;
        char c2 = 0;
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            if (-1 == name.indexOf(46)) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    View view = createViewByPrefix(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                Object[] objArr = this.mConstructorArgs;
                objArr[0] = null;
                objArr[1] = null;
                return null;
            }
            View createViewByPrefix = createViewByPrefix(context, name, null);
            Object[] objArr2 = this.mConstructorArgs;
            objArr2[0] = null;
            objArr2[1] = null;
            return createViewByPrefix;
        } catch (Exception e) {
            return null;
        } finally {
            Object[] objArr3 = this.mConstructorArgs;
            objArr3[c2] = obj;
            objArr3[c] = obj;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attrs) {
        Context context = view.getContext();
        if (!(context instanceof ContextWrapper)) {
            return;
        }
        if (Build.VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
            TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
            String handlerName = a.getString(0);
            if (handlerName != null) {
                view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
            }
            a.recycle();
        }
    }

    private View createViewByPrefix(Context context, String name, String prefix) throws ClassNotFoundException, InflateException {
        String str;
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (constructor == null) {
            if (prefix != null) {
                try {
                    str = prefix + name;
                } catch (Exception e) {
                    return null;
                }
            } else {
                str = name;
            }
            constructor = Class.forName(str, false, context.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
            sConstructorMap.put(name, constructor);
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0 && (themeId = a.getResourceId(R.styleable.View_theme, 0)) != 0) {
            Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        a.recycle();
        if (themeId == 0) {
            return context;
        }
        if (!(context instanceof ContextThemeWrapper) || ((ContextThemeWrapper) context).getThemeResId() != themeId) {
            return new ContextThemeWrapper(context, themeId);
        }
        return context;
    }

    /* access modifiers changed from: private */
    public static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context, String name) {
            String idText;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                idText = BuildConfig.FLAVOR;
            } else {
                idText = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + idText);
        }
    }
}