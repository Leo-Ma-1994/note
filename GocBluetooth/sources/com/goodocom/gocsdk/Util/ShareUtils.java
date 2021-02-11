package com.goodocom.gocsdk.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    private static ShareUtils prefUtils;
    private String path = "share_data";
    private final SharedPreferences sp;

    public ShareUtils(Context context) {
        this.sp = context.getSharedPreferences(this.path, 0);
    }

    public static ShareUtils getInstance(Context context) {
        if (prefUtils == null) {
            synchronized (ShareUtils.class) {
                if (prefUtils == null) {
                    prefUtils = new ShareUtils(context);
                }
            }
        }
        return prefUtils;
    }

    public void setPath(String path2) {
        this.path = path2;
    }

    public void putBoolean(String key, boolean value) {
        this.sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.sp.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        this.sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return this.sp.getString(key, defValue);
    }

    public void putInt(String key, int value) {
        this.sp.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return this.sp.getInt(key, defValue);
    }

    public void remove(String key) {
        this.sp.edit().remove(key).apply();
    }

    public void clear() {
        SharedPreferences sharedPreferences = this.sp;
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().apply();
        }
    }
}
