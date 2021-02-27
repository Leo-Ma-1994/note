package com.yadea.launcher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.yadea.launcher.service.CanServiceListener;
import com.yadea.launcher.util.LogUtils;
import com.yadea.yadeaserver.ICanService;

import java.util.ArrayList;
import java.util.List;

public class LauncherApplication extends Application {
    private static final String TAG = LauncherApplication.class.getSimpleName();

    private Status status = Status.GEAR_PARK;
    private boolean isWifiEnabled = false;
    private boolean isBtEnabled = false;
    private boolean isGpsEnabled = false;
    private boolean isSignalEnabled = false;

    /**
     * 仪表开始动画标识
     */
    private boolean isStartAnim = false;

    private boolean isComingPhone = false;

    private List<InformationListener> mInformationListeners = new ArrayList<>();

    public boolean getComingPhone() {
        return isComingPhone;
    }

    public void setComingPhone(boolean comingPhone) {
        isComingPhone = comingPhone;
    }

    public boolean getStartAnim() {
        return isStartAnim;
    }

    public void setStartAnim(boolean startAnim) {
        isStartAnim = startAnim;
    }

    public enum Status {

        GEAR_PARK, //驻车档
        GEAR_REVERSE, //倒车档位
        GEAR_ECO,  //eco档
        GEAR_SPORT, //运动档位
        GEAR_LOCKED, //设防状态
        GEAR_ALARM//报警状态

    }

    private ICanService mCanService;
    private CanServiceListener mCanServiceListener;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCanService = ICanService.Stub.asInterface(service);
            LogUtils.d(TAG, "CanService: onServiceConnected >>>>>");
            try {
                mCanService.registerListener(mCanServiceListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d(TAG, "CanService: onServiceDisconnected <<<<<");
        }
    };


    @Override
    public void onCreate() {


        super.onCreate();
        mCanServiceListener = new CanServiceListener();

        Intent intent = new Intent();
        intent.setAction("com.yadea.yadeaserver.CanService");
        intent.setPackage("com.yadea.yadeaserver");
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    public int getProp(int propId, int defaultVal) {
        int result = defaultVal;
        try {
            result = mCanService.getProp(propId, defaultVal);
        } catch (RemoteException e) {
            LogUtils.w(TAG, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void registerHandler(Handler handler) {
        mCanServiceListener.addHandler(handler);
    }

    public void unregisterHandler(Handler handler) {
        mCanServiceListener.removeHandler(handler);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setInformationListener(InformationListener listener) {
        if (null == listener) return;
        mInformationListeners.add(listener);
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        isWifiEnabled = wifiEnabled;
        notifyWifiStatus();
    }

    public void setBtEnabled(boolean btEnabled) {
        isBtEnabled = btEnabled;
        notifyBlueToothStatus();
    }

    public void setGpsEnabled(boolean gpsEnabled) {
        isGpsEnabled = gpsEnabled;
        notifyGpsStatus();
    }

    public void setSignalEnabled(boolean signalEnabled) {
        isSignalEnabled = signalEnabled;
        notifySignalStatus();
    }


    private void notifyWifiStatus() {
        for (InformationListener listener: mInformationListeners) {
            listener.onWifiStatusChanged(isWifiEnabled);
        }
    }

    private void notifyBlueToothStatus() {
        for (InformationListener listener: mInformationListeners) {
            listener.onBlueToothStatusChanged(isBtEnabled);
        }
    }

    private void notifyGpsStatus() {
        for (InformationListener listener: mInformationListeners) {
            listener.onGpsStatusChanged(isGpsEnabled);
        }
    }

    private void notifySignalStatus() {
        for (InformationListener listener: mInformationListeners) {
            listener.onSignalStatusChanged(isSignalEnabled);
        }
    }

    public void notifyAppStatus(int status , String pkgName){
        for(InformationListener listener: mInformationListeners){
            listener.onAppStatusChanged(status , pkgName);
        }
    }

    public interface InformationListener {
        void onWifiStatusChanged(boolean status);
        void onBlueToothStatusChanged(boolean status);
        void onGpsStatusChanged(boolean status);
        void onSignalStatusChanged(boolean status);
        void onAppStatusChanged(int status, String pkgName);
    }
}
