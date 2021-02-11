package com.goodocom.bttek.bt.base.listener;

public interface GocBluetoothOppChangeListener {
    void onOppReceiveFileInfo(String str, int i, String str2, String str3);

    void onOppReceiveProgress(int i);

    void onOppServiceReady();

    void onOppStateChanged(String str, int i, int i2, int i3);
}
