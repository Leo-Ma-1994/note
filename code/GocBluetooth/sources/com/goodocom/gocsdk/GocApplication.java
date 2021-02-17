package com.goodocom.gocsdk;

import android.app.Application;
import android.util.Log;
import com.goodocom.bttek.bt.base.jar.GocJar;

public class GocApplication extends Application {
    public static GocApplication INSTANCE;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Log.e("apps", "GocApplication>>>>>>>>>>>>>>>>>>>>>>>>>>onCreate");
        INSTANCE = this;
        GocJar.init(this);
    }
}
