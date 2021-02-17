package com.goodocom.bttek.bt.base.listener;

public interface GocBluetoothOtherFunChangeListener {
    void onHfpMissedCall(String str, int i);

    void onHfpRemoteBatteryIndicator(String str, int i, int i2, int i3);

    void onHfpRemoteSignalStrength(String str, int i, int i2, int i3);
}
