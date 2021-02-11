package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackBluetooth;

public final class DoCallbackBluetooth extends DoCallback<UiCallbackBluetooth> {
    private final int onA2dpStateChanged = 102;
    private final int onAdapterDiscoverableModeChanged = 2;
    private final int onAdapterDiscoveryFinished = 4;
    private final int onAdapterDiscoveryStarted = 3;
    private final int onAdapterStateChanged = 1;
    private final int onAutoAnwer = 205;
    private final int onAutoConnect = 204;
    private final int onAvrcpStateChanged = 103;
    private final int onBluetoothServiceReady = 0;
    private final int onBtAutoConnectStateChanged = 202;
    private final int onBtRoleModeChanged = 201;
    private final int onConnectDevice = 206;
    private final int onDeviceBondStateChanged = 7;
    private final int onDeviceFound = 6;
    private final int onDeviceOutOfRange = 10;
    private final int onDeviceUuidsUpdated = 8;
    private final int onHfpStateChanged = 101;
    private final int onLocalAdapterNameChanged = 9;
    private final int onLocalAdapterPinCodeChanged = 17;
    private final int onMainDevicesChanged = 203;
    private final int retPairedDevices = 5;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackBluetooth";
    }

    public void onBluetoothServiceReady() {
        Log.v(this.TAG, "onBluetoothServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public synchronized void onHfpStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onHfpStateChanged() : " + prevState + " -> " + newState);
        Message m = getMessage(101);
        m.arg1 = prevState;
        m.arg2 = newState;
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void onA2dpStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onA2dpStateChanged() : " + prevState + " -> " + newState);
        Message m = Message.obtain(this.mHandler, 102);
        m.arg1 = prevState;
        m.arg2 = newState;
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void onAvrcpStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onAvrcpStateChanged(): " + prevState + " -> " + newState);
        Message m = getMessage(103);
        m.arg1 = prevState;
        m.arg2 = newState;
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void onBtRoleModeChanged(int mode) {
        String str = this.TAG;
        Log.v(str, "onBtRoleModeChanged(): " + mode);
        Message m = getMessage(201);
        Bundle b = new Bundle();
        b.putInt("mode", mode);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onBtAutoConnectStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onBtAutoConnectStateChanged(): " + address);
        Message m = getMessage(202);
        m.arg1 = prevState;
        m.arg2 = newState;
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void onMainDevicesChanged(String address, String name) {
        String str = this.TAG;
        Log.v(str, "onMainDevicesChanged(): " + address + " name:" + name);
        Message m = getMessage(203);
        Bundle b = new Bundle();
        b.putString("name", name);
        m.setData(b);
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void setOnAutoConnect(int state) {
        Message m = getMessage(204);
        Bundle b = new Bundle();
        b.putInt("state", state);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void setOnAutoAnwer(int state) {
        Message m = getMessage(205);
        Bundle b = new Bundle();
        b.putInt("state", state);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void setonConnectedDevice(String mainDevice, String subDevice) {
        Message m = getMessage(206);
        Bundle b = new Bundle();
        b.putString("mainDevice", mainDevice);
        b.putString("subDevice", subDevice);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onAdapterStateChanged(int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onAdapterStateChanged(): " + prevState + "->" + newState);
        Message m = getMessage(1);
        m.arg1 = prevState;
        m.arg2 = newState;
        enqueueMessage(m);
    }

    public synchronized void onAdapterDiscoverableModeChanged(int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onAdapterDiscoverableModeChanged() State: " + prevState + "->" + newState);
        Message m = getMessage(2);
        m.arg1 = prevState;
        m.arg2 = newState;
        enqueueMessage(m);
    }

    public synchronized void onAdapterDiscoveryStarted() {
        Log.v(this.TAG, "onAdapterDiscoveryStarted()");
        enqueueMessage(getMessage(3));
    }

    public synchronized void onAdapterDiscoveryFinished() {
        Log.v(this.TAG, "onAdapterDiscoveryFinished()");
        enqueueMessage(getMessage(4));
    }

    public synchronized void retPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) {
        String str = this.TAG;
        Log.v(str, "retPairedDevices() " + address);
        Message m = getMessage(5);
        Bundle b = new Bundle();
        b.putInt("elements", elements);
        b.putStringArray("address", address);
        b.putStringArray("name", name);
        b.putIntArray("supportProfile", supportProfile);
        b.putByteArray("category", category);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onDeviceFound(String address, String name, byte category) {
        String str = this.TAG;
        Log.v(str, "onDeviceFound() " + address);
        Message m = getMessage(6);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putByte("category", category);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onDeviceBondStateChanged(String address, String name, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onDeviceBondStateChanged() " + address);
        Message m = getMessage(7);
        m.obj = address;
        m.arg1 = prevState;
        m.arg2 = newState;
        Bundle b = new Bundle();
        b.putString("name", name);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onDeviceUuidsUpdated(String address, String name, int supportProfile) {
        String str = this.TAG;
        Log.v(str, "onDeviceUuidsUpdated() " + address);
        Message m = getMessage(8);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putInt("supportProfile", supportProfile);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onLocalAdapterNameChanged(String name) {
        String str = this.TAG;
        Log.v(str, "onLocalAdapterNameChanged() name: " + name);
        Message m = getMessage(9);
        Bundle b = new Bundle();
        b.putString("name", name);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onLocalAdapterPinCodeChanged(String key) {
        String str = this.TAG;
        Log.v(str, "onLocalAdapterPinCodeChanged() name: " + key);
        Message m = getMessage(17);
        Bundle b = new Bundle();
        b.putString("key", key);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onDeviceOutOfRange(String address) {
        String str = this.TAG;
        Log.v(str, "onDeviceOutOfRange() " + address);
        Message m = getMessage(10);
        Bundle b = new Bundle();
        b.putString("address", address);
        m.setData(b);
        enqueueMessage(m);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public void dequeueMessage(Message msg) {
        Bundle b = msg.getData();
        String address = (String) msg.obj;
        int prevState = msg.arg1;
        int newState = msg.arg2;
        int i = msg.what;
        if (i != 17) {
            switch (i) {
                case 0:
                    callbackOnBluetoothServiceReady();
                    return;
                case 1:
                    callbackOnAdapterStateChanged(prevState, newState);
                    return;
                case 2:
                    callbackOnAdapterDiscoverableModeChanged(prevState, newState);
                    return;
                case 3:
                    callbackOnAdapterDiscoveryStarted();
                    return;
                case 4:
                    callbackOnAdapterDiscoveryFinished();
                    return;
                case 5:
                    callbackRetPairedDevices(b.getInt("elements"), b.getStringArray("address"), b.getStringArray("name"), b.getIntArray("supportProfile"), b.getByteArray("category"));
                    return;
                case 6:
                    callbackOnDeviceFound(address, b.getString("name"), b.getByte("category"));
                    return;
                case 7:
                    callbackOnDeviceBondStateChanged(address, b.getString("name"), prevState, newState);
                    return;
                case 8:
                    callbackOnDeviceUuidsUpdated(address, b.getString("name"), b.getInt("supportProfile"));
                    return;
                case 9:
                    callbackOnLocalAdapterNameChanged(b.getString("name"));
                    return;
                case 10:
                    callbackOnDeviceOutOfRange(b.getString("address"));
                    return;
                default:
                    switch (i) {
                        case 101:
                            callbackOnHfpStateChanged(address, prevState, newState);
                            return;
                        case 102:
                            callbackOnA2dpStateChanged(address, prevState, newState);
                            return;
                        case 103:
                            callbackOnAvrcpStateChanged(address, prevState, newState);
                            return;
                        default:
                            switch (i) {
                                case 201:
                                    callbackOnBtRoleModeChanged(b.getInt("mode"));
                                    return;
                                case 202:
                                    callbackOnBtAutoConnectStateChanged(address, prevState, newState);
                                    return;
                                case 203:
                                    callbackonMainDevicesChanged(address, b.getString("name"));
                                    return;
                                case 204:
                                    callbackonAutoConnect(b.getInt("state"));
                                    return;
                                case 205:
                                    callbackonAutoAnwer(b.getInt("state"));
                                    return;
                                case 206:
                                    callbackonConnectDevice(b.getString("mainDevice"), b.getString("subDevice"));
                                    return;
                                default:
                                    String str = this.TAG;
                                    Log.e(str, "unhandle Message : " + msg.what);
                                    return;
                            }
                    }
            }
        } else {
            callbackOnLocalAdapterPinCodeChanged(b.getString("key"));
        }
    }

    private synchronized void callbackOnBluetoothServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnBluetoothServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onBluetoothServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onBluetoothServiceReady();
                    } catch (RemoteException e) {
                        String str = this.TAG;
                        Log.e(str, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onBluetoothServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAdapterStateChanged(int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnAdapterStateChanged() State: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAdapterStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onAdapterStateChanged broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAdapterStateChanged(prevState, newState);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAdapterStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAdapterDiscoverableModeChanged(int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnAdapterDiscoverableModeChanged() State: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAdapterDiscoverableModeChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onAdapterDiscoverableModeChanged broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAdapterDiscoverableModeChanged(prevState, newState);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAdapterStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAdapterDiscoveryStarted() {
        Throwable th;
        Log.d(this.TAG, "callbackOnAdapterDiscoveryStarted()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAdapterDiscoveryStarted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str = this.TAG;
                Log.v(str, "onAdapterDiscoveryStarted broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAdapterDiscoveryStarted();
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAdapterDiscoveryStarted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAdapterDiscoveryFinished() {
        Throwable th;
        Log.d(this.TAG, "callbackOnAdapterDiscoveryFinished()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAdapterDiscoveryFinished beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str = this.TAG;
                Log.v(str, "onAdapterDiscoveryFinished broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAdapterDiscoveryFinished();
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAdapterDiscoveryFinished CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetPairedDevices() elements: " + elements);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPairedDevices beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "retPairedDevices broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).retPairedDevices(elements, address, name, supportProfile, category);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retPairedDevices CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnDeviceFound(String address, String name, byte category) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnDeviceFound() " + address + " name: " + name + " category: " + ((int) category));
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onDeviceFound beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onDeviceFound broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onDeviceFound(address, name, category);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onDeviceFound CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnDeviceBondStateChanged(String address, String name, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnDeviceBondStateChanged() " + address + " name: " + name + " State: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onDeviceBondStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onDeviceBondStateChanged broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onDeviceBondStateChanged(address, name, prevState, newState);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onDeviceBondStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnDeviceUuidsUpdated(String address, String name, int supportProfile) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnDeviceUuidsUpdated() " + address + " name: " + name + " supportProfile: " + supportProfile);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onDeviceUuidsUpdated beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onDeviceUuidsUpdated broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onDeviceUuidsUpdated(address, name, supportProfile);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onDeviceUuidsUpdated CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnLocalAdapterNameChanged(String name) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnLocalAdapterNameChanged() name: " + name);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onLocalAdapterNameChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onLocalAdapterNameChanged broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onLocalAdapterNameChanged(name);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onLocalAdapterNameChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnLocalAdapterPinCodeChanged(String key) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnLocalAdapterPinCodeChanged() name: " + key);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onLocalAdapterPinCodeChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onLocalAdapterPinCodeChanged broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onLocalAdapterPinCodeChanged(key);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onLocalAdapterPinCodeChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnDeviceOutOfRange(String address) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnDeviceOutOfRange() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onDeviceOutOfRange beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                String str2 = this.TAG;
                Log.v(str2, "onDeviceOutOfRange broadcast count : " + n);
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onDeviceOutOfRange(address);
                    } catch (RemoteException e) {
                        String str3 = this.TAG;
                        Log.e(str3, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onDeviceOutOfRange CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpStateChanged(String address, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpStateChanged() " + address + " state: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpStateChanged(address, prevState, newState);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onHfpStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnA2dpStateChanged(String address, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnA2dpStateChanged() : " + prevState + " -> " + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onA2dpStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onA2dpStateChanged(address, prevState, newState);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onA2dpStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnAvrcpStateChanged(String address, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnAvrcpStateChanged() " + address + " state: " + prevState + " -> " + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onAvrcpStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAvrcpStateChanged(address, prevState, newState);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onAvrcpStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnBtRoleModeChanged(int mode) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnBtRoleModeChanged() " + mode);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onBtRoleModeChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onBtRoleModeChanged(mode);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onBtRoleModeChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnBtAutoConnectStateChanged(String address, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnBtAutoConnectStateChanged() " + address + " state: " + prevState + " -> " + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onBtAutoConnectStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onBtAutoConnectStateChanged(address, prevState, newState);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onBtAutoConnectStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackonMainDevicesChanged(String address, String name) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackonMainDevicesChanged() " + address + " name: " + name);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMainDevicesChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onMainDevicesChanged(address, name);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "onMainDevicesChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackonAutoConnect(int state) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackonAutoConnect() " + state);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackonAutoConnect beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAutoConnect(state);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "callbackonAutoConnect CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackonAutoAnwer(int state) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackonAutoAnwer() " + state);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackonAutoAnwer beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onAutoAnwer(state);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "callbackonAutoAnwer CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackonConnectDevice(String mainDevice, String subDevice) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackonConnectDevice() " + mainDevice + "  subDevice  " + subDevice);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackonConnectDevice beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackBluetooth) this.mRemoteCallbacks.getBroadcastItem(i)).onConnectedDevice(mainDevice, subDevice);
                    } catch (RemoteException e) {
                        String str2 = this.TAG;
                        Log.e(str2, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "callbackonConnectDevice CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
