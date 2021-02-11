package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackSpp;

public final class DoCallbackSpp extends DoCallback<UiCallbackSpp> {
    private final int onSppAppleIapAuthenticationRequest = 6;
    private final int onSppDataReceived = 4;
    private final int onSppErrorResponse = 2;
    private final int onSppSendData = 5;
    private final int onSppServiceReady = 0;
    private final int onSppStateChanged = 1;
    private final int retSppConnectedDeviceAddressList = 3;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "NfDoCallbackSpp";
    }

    public void onSppServiceReady() {
        Log.v(this.TAG, "onSppServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public void onSppStateChanged(String address, String deviceName, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onSppStateChanged() " + address);
        Message m = getMessage(1);
        m.obj = address;
        m.arg1 = prevState;
        m.arg2 = newState;
        Bundle b = new Bundle();
        b.putString("deviceName", deviceName);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onSppErrorResponse(String address, int errorCode) {
        String str = this.TAG;
        Log.v(str, "onSppErrorResponse() " + address);
        Message m = getMessage(2);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("errorCode", errorCode);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) {
        Log.v(this.TAG, "retPbapDatabaseQueryNameByPartialNumber()");
        Message m = getMessage(3);
        Bundle b = new Bundle();
        b.putInt("totalNum", totalNum);
        b.putStringArray("addressList", addressList);
        b.putStringArray("nameList", nameList);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onSppDataReceived(String address, byte[] receivedData) {
        String str = this.TAG;
        Log.v(str, "onSppDataReceived() " + address);
        Message m = getMessage(4);
        m.obj = address;
        Bundle b = new Bundle();
        b.putByteArray("receivedData", receivedData);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onSppSendData(String address, int length) {
        String str = this.TAG;
        Log.v(str, "onSppSendData() " + address);
        Message m = getMessage(5);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("sendDataLength", length);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onSppAppleIapAuthenticationRequest(String address) {
        String str = this.TAG;
        Log.v(str, "onSppAppleIapAuthenticationRequest() " + address);
        Message m = getMessage(6);
        m.obj = address;
        m.setData(new Bundle());
        enqueueMessage(m);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public void dequeueMessage(Message msg) {
        Bundle b = msg.getData();
        String address = (String) msg.obj;
        int prevState = msg.arg1;
        int newState = msg.arg2;
        switch (msg.what) {
            case 0:
                callbackOnSppServiceReady();
                return;
            case 1:
                callbackOnSppStateChanged(address, b.getString("deviceName"), prevState, newState);
                return;
            case 2:
                callbackOnSppErrorResponse(address, b.getInt("errorCode"));
                return;
            case 3:
                callbackRetSppConnectedDeviceAddressList(b.getInt("totalNum"), b.getStringArray("addressList"), b.getStringArray("nameList"));
                return;
            case 4:
                callbackOnSppDataReceived(address, b.getByteArray("receivedData"));
                return;
            case 5:
                callbackOnSppSendData(address, b.getInt("sendDataLength"));
                return;
            case 6:
                callbackOnSppAppleIapAuthenticationRequest(address);
                return;
            default:
                String str = this.TAG;
                Log.e(str, "unhandle Message : " + msg.what);
                return;
        }
    }

    private synchronized void callbackOnSppServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnSppServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onSppServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).onSppServiceReady();
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
                Log.v(this.TAG, "onSppServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnSppStateChanged(String address, String deviceName, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnSppStateChanged() " + address + " state: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onSppStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).onSppStateChanged(address, deviceName, prevState, newState);
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
                Log.v(this.TAG, "onSppStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnSppErrorResponse(String address, int errorCode) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnSppErrorResponse() " + address + " errorCode: " + errorCode);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onSppErrorResponse beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).onSppErrorResponse(address, errorCode);
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
                Log.v(this.TAG, "onSppErrorResponse CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetSppConnectedDeviceAddressList() totalNum: " + totalNum);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retSppConnectedDeviceAddressList beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).retSppConnectedDeviceAddressList(totalNum, addressList, nameList);
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
                Log.v(this.TAG, "retSppConnectedDeviceAddressList CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnSppDataReceived(String address, byte[] receivedData) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnSppDataReceived() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onSppDataReceived beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).onSppDataReceived(address, receivedData);
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
                Log.v(this.TAG, "onSppDataReceived CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnSppSendData(String address, int length) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnSppSendData() " + address + length);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackOnSppSendData beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).onSppSendData(address, length);
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
                Log.v(this.TAG, "callbackOnSppSendData CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnSppAppleIapAuthenticationRequest(String address) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnSppAppleIapAuthenticationRequest() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onSppAppleIapAuthenticationRequest beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackSpp) this.mRemoteCallbacks.getBroadcastItem(i)).onSppAppleIapAuthenticationRequest(address);
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
                Log.v(this.TAG, "onSppAppleIapAuthenticationRequest CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
