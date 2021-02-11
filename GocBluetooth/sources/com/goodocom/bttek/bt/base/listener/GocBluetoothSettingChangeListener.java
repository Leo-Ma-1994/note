package com.goodocom.bttek.bt.base.listener;

public interface GocBluetoothSettingChangeListener {
    void onA2dpStateChanged(String str, int i);

    void onAdapterDiscoveryFinished();

    void onAdapterDiscoveryStarted();

    void onAdapterStateChanged(int i, int i2);

    void onAutoAnwer(int i);

    void onAutoConnect(int i);

    void onAvrcpStateChanged(String str, int i);

    void onConnectDevice(String str, String str2);

    void onConnectedChanged(String str, int i);

    void onDeviceBondStateChanged(String str, String str2, int i);

    void onDeviceFound(String str, String str2);

    void onEnableChanged(boolean z);

    void onHfpAudioStateChanged(String str, int i, int i2);

    void onHfpStateChanged(String str, int i);

    void onLocalAdapterNameChanged(String str);

    void onMainDevicesChanged(String str, String str2);

    void onPairStateChanged(String str, String str2, int i, int i2);

    void retPairedDevices(int i, String[] strArr, String[] strArr2, int[] iArr, byte[] bArr);
}
