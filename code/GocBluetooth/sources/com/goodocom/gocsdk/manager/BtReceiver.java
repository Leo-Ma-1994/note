package com.goodocom.gocsdk.manager;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.goodocom.gocsdk.fragment.FragmentSearch;

public class BtReceiver extends BroadcastReceiver {
    private static final String TAG = "search";
    private FragmentSearch mFragmentSearch;
    private final Listener mListener;

    public interface Listener {
        void finishFound();

        void foundDev(BluetoothDevice bluetoothDevice);
    }

    public BtReceiver(Context cxt, Listener listener, FragmentSearch fragmentSearch) {
        this.mListener = listener;
        this.mFragmentSearch = fragmentSearch;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        filter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        filter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        filter.addAction("android.bluetooth.device.action.FOUND");
        filter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        filter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        filter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        filter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        filter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        filter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        cxt.registerReceiver(this, filter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Log.i(TAG, "===" + action);
            BluetoothDevice dev = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            if (dev != null) {
                Log.i(TAG, "BluetoothDevice: " + dev.getName() + ", " + dev.getAddress());
            }
            char c = 65535;
            switch (action.hashCode()) {
                case -1780914469:
                    if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1530327060:
                    if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        c = 0;
                        break;
                    }
                    break;
                case -301431627:
                    if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                        c = 6;
                        break;
                    }
                    break;
                case -223687943:
                    if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                        c = 4;
                        break;
                    }
                    break;
                case 6759640:
                    if (action.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
                        c = 1;
                        break;
                    }
                    break;
                case 545516589:
                    if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = '\t';
                        break;
                    }
                    break;
                case 1123270207:
                    if (action.equals("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED")) {
                        c = '\b';
                        break;
                    }
                    break;
                case 1167529923:
                    if (action.equals("android.bluetooth.device.action.FOUND")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1244161670:
                    if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = '\n';
                        break;
                    }
                    break;
                case 1821585647:
                    if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                        c = 7;
                        break;
                    }
                    break;
                case 2116862345:
                    if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                        c = 5;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                    Log.i(TAG, "STATE+++++: " + state);
                    return;
                case 1:
                case 4:
                default:
                    return;
                case 2:
                    this.mListener.finishFound();
                    return;
                case 3:
                    short rssi = intent.getShortExtra("android.bluetooth.device.extra.RSSI", Short.MAX_VALUE);
                    Log.i(TAG, "EXTRA_RSSI:" + ((int) rssi));
                    this.mListener.foundDev(dev);
                    return;
                case 5:
                    Log.i(TAG, "BOND_STATE: " + intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", 0));
                    return;
                case 6:
                    Log.e(TAG, "connectte>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return;
                case 7:
                    Log.e(TAG, "dicconnectte>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return;
                case '\b':
                    Log.i(TAG, "CONN_STATE..................: " + intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", 0));
                    return;
                case '\t':
                    Log.i(TAG, "CONN_STATE---------: " + intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
                    return;
                case '\n':
                    Log.i(TAG, "CONN_STATE=====: " + intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
                    return;
            }
        }
    }
}
