package com.goodocom.sharedata;

import android.os.RemoteException;
import android.util.Log;
import com.goodocom.gocsdkserver.GocsdkService;
import java.util.Timer;
import java.util.TimerTask;

public class AdayoSystemManager {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    private final int ADAYO_SYSTEM_MANAGER_BT_MODE1 = 1;
    private final int ADAYO_SYSTEM_MANAGER_BT_MODE2 = 2;
    private final String TAG = "GoodocomSystemManager";
    private int currentMode = 1;
    private boolean isAccOn = false;
    private GocsdkService mGocsdkService;
    SystemManagerTask mSystemManagerTask = null;
    Timer mTimer = null;

    public AdayoSystemManager(GocsdkService service) {
        this.mGocsdkService = service;
    }

    public void notifyAdayoSystemServiceStartFinished() {
        Log.d("GoodocomSystemManager", "notifyAdayoSystemServiceStartFinished start");
        registerSystemServiceListener();
    }

    private void registerSystemServiceListener() {
    }

    private void adayoSystemStatusChange(byte changeStatus) {
    }

    private void startAutoConnect() {
        Log.d("GoodocomSystemManager", "startAutoConnect");
        this.mTimer = new Timer(true);
        this.mSystemManagerTask = new SystemManagerTask();
        this.mTimer.schedule(this.mSystemManagerTask, 200, 3000);
    }

    private class SystemManagerTask extends TimerTask {
        private SystemManagerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            try {
                if (AdayoSystemManager.this.mGocsdkService.bluetooth.autoConnectFunc() || (AdayoSystemManager.this.mGocsdkService.bluetooth.isBtEnabled() && !AdayoSystemManager.this.mGocsdkService.bluetooth.autoConnect)) {
                    Log.d("GoodocomSystemManager", "Timer cancel");
                    AdayoSystemManager.this.mTimer.cancel();
                    AdayoSystemManager.this.mTimer.purge();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime <= 1000) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public void modeAccOfToAccOn() {
        Log.d("GoodocomSystemManager", "modeAccOfToAccOn");
    }

    public void modeAccOnToAccOff() {
        Log.d("GoodocomSystemManager", "modeAccOnToAccOff");
    }

    public boolean isAccOn() {
        return this.isAccOn;
    }
}
