package com.leo.navigationdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AppInfoBean {
    private static final String TAG = "AppInfo";

    /**
     * 应用名称
     */
    private String appName;
    /**
     * 所在包名
     */
    private String pkgName;
    /**
     * 启动Intent
     */
    private Intent appIntent;
    /**
     * 应用图标
     */
    private Drawable icon;
    public AppInfoBean() {
        super();
    }

    public AppInfoBean(String appName, Intent appIntent) {
        this.appName = appName;
        this.appIntent = appIntent;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Intent getAppIntent() {
        return appIntent;
    }

    public void setAppIntent(Intent appIntent) {
        this.appIntent = appIntent;
    }

    public void setIcon(Drawable icon){
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName=" + appName +
                ",pkgName=" + pkgName +
                ",appIntent" + appIntent.toString() + "}";
    }
}
