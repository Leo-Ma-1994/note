package com.goodocom.bttek.bt;

import android.app.Application;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.res.NfDef;

public class GocApplication extends Application {
    public static GocApplication INSTANCE;
    public boolean mAnswerToPhoneOrToCar;
    public String mCurrentA2dpAddress = BuildConfig.FLAVOR;
    public String mMainAddress = NfDef.DEFAULT_ADDRESS;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
