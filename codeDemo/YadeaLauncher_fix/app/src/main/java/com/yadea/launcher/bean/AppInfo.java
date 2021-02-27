package com.yadea.launcher.bean;

import android.content.Intent;
import android.util.Log;

public class AppInfo {
    private static final String TAG = "AppInfo";

    private String appName;
    private String pkgName;
    private Intent appIntent;

    public AppInfo() {
        super();
    }

    public AppInfo(String appName, Intent appIntent) {
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

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName+"}";
    }
}
