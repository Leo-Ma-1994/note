package com.goodocom.bttek.bt.base.listener;

public interface GocBluetoothHidChangeListener {
    void onHidServiceReady();

    void onHidStateChanged(String str, int i, int i2, int i3);
}
