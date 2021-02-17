package android.support.v4.widget;

import android.os.Build;

public interface AutoSizeableTextView {
    public static final boolean PLATFORM_SUPPORTS_AUTOSIZE = (Build.VERSION.SDK_INT >= 27);

    @Override // android.support.v4.widget.AutoSizeableTextView
    int getAutoSizeMaxTextSize();

    @Override // android.support.v4.widget.AutoSizeableTextView
    int getAutoSizeMinTextSize();

    @Override // android.support.v4.widget.AutoSizeableTextView
    int getAutoSizeStepGranularity();

    @Override // android.support.v4.widget.AutoSizeableTextView
    int[] getAutoSizeTextAvailableSizes();

    @Override // android.support.v4.widget.AutoSizeableTextView
    int getAutoSizeTextType();

    @Override // android.support.v4.widget.AutoSizeableTextView
    void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) throws IllegalArgumentException;

    @Override // android.support.v4.widget.AutoSizeableTextView
    void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) throws IllegalArgumentException;

    @Override // android.support.v4.widget.AutoSizeableTextView
    void setAutoSizeTextTypeWithDefaults(int i);
}
