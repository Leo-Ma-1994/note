package com.yadea.launcher.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.yadea.launcher.LauncherApplication;

public class InformationReceiver extends BroadcastReceiver {
    private static final String TAG = "InformationReceiver";
    private Context mContext;

    public static final String ACTION_WIFI_STATE_CHANGED = WifiManager.WIFI_STATE_CHANGED_ACTION;
    public static final String ACTION_BT_STATE_CHANGED = BluetoothAdapter.ACTION_STATE_CHANGED;
    public static final String ACTION_GPS_STATE_CHANGED = LocationManager.PROVIDERS_CHANGED_ACTION;
    public static final String PACKAGE_ADD = "android.intent.action.PACKAGE_ADDED";
    public static final String PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";


    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String action = intent.getAction();
        LauncherApplication application = (LauncherApplication) mContext.getApplicationContext();
        switch (action) {
            case ACTION_WIFI_STATE_CHANGED:
                application.setWifiEnabled(isWifiEnable(mContext));
                break;
            case ACTION_BT_STATE_CHANGED:
                application.setBtEnabled(isBluetoothEnabled());
                break;
            case ACTION_GPS_STATE_CHANGED:
                application.setGpsEnabled(isGPSEnable(mContext));
                break;
            case PACKAGE_ADD:
                Log.d(TAG, "onReceive: +应该增加了"+ intent.getDataString().substring(8));
                application.notifyAppStatus(1, intent.getDataString().substring(8));
                break;
            case PACKAGE_REMOVED:
                Log.d(TAG, "onReceive: +应用删除了" + intent.getDataString().substring(8));
                application.notifyAppStatus(-1, intent.getDataString().substring(8));
            default: break;
        }
    }

    public static boolean isGPSEnable(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isWifiEnable(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    public static boolean is3GEnable(Context context) {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                result = true;
            if (activeNetworkInfo.isConnectedOrConnecting() && activeNetworkInfo.getType() == TelephonyManager.NETWORK_TYPE_LTE)
                result = true;
        }

        return result;
    }

    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled();
        }
        return false;
    }

}
