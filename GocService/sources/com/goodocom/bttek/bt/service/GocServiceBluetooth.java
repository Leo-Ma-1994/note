package com.goodocom.bttek.bt.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.goodocom.gocsdkserver.GocsdkService;

public class GocServiceBluetooth extends Service {
    private static final String TAG = "gocsdkServiceBluetooth";

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate tid:" + Thread.currentThread().getId());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        GocsdkService.destroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind tid:" + Thread.currentThread().getId());
        return GocsdkService.getInstance().bluetooth;
    }
}
