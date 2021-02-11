package com.goodocom.bttek.bt.base.listener;

import com.goodocom.bttek.bt.aidl.GocHfpClientCall;

public interface GocBluetoothPhoneChangeListener {
    void onHfpCallChanged(String str, GocHfpClientCall gocHfpClientCall);

    void onHfpCallingTimeChanged(String str);

    void onPbapStateChanged(int i);
}
