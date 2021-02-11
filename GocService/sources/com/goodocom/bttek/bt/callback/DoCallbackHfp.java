package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.aidl.UiCallbackHfp;

public final class DoCallbackHfp extends DoCallback<UiCallbackHfp> {
    private final int onHfpAudioStateChanged = 2;
    private final int onHfpCallChanged = 11;
    private final int onHfpCallingTimeChanged = 12;
    private final int onHfpErrorResponse = 4;
    private final int onHfpRemoteBatteryIndicator = 9;
    private final int onHfpRemoteRoamingStatus = 8;
    private final int onHfpRemoteSignalStrength = 10;
    private final int onHfpRemoteTelecomService = 7;
    private final int onHfpServiceReady = 0;
    private final int onHfpStateChanged = 1;
    private final int onHfpVoiceDial = 3;
    private final int retHfpRemoteNetworkOperator = 5;
    private final int retHfpRemoteSubscriberNumber = 6;
    private final int retPbapDatabaseQueryNameByNumber = 101;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackHfp";
    }

    public void onHfpServiceReady() {
        Log.v(this.TAG, "onHfpServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public synchronized void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) {
        String str = this.TAG;
        Log.v(str, "retPbapDatabaseQueryNameByNumber() " + address);
        Message m = getMessage(101);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("target", target);
        b.putString("name", name);
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onHfpStateChanged() : " + prevState + " -> " + newState);
        Message m = getMessage(1);
        m.arg1 = prevState;
        m.arg2 = newState;
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void onHfpCallingTimeChanged(String time) {
        String str = this.TAG;
        Log.v(str, "onHfpCallingTimeChanged() : " + time);
        Message m = getMessage(12);
        m.obj = time;
        enqueueMessage(m);
    }

    public synchronized void onHfpAudioStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onHfpScoStateChanged() " + address);
        Message m = getMessage(2);
        m.obj = address;
        m.arg1 = prevState;
        m.arg2 = newState;
        enqueueMessage(m);
    }

    public synchronized void onHfpVoiceDial(String address, boolean isVoiceDialOn) {
        String str = this.TAG;
        Log.v(str, "onHfpVoiceDial() " + address);
        Message m = getMessage(3);
        m.obj = address;
        Bundle b = new Bundle();
        b.putBoolean("isVoiceDialOn", isVoiceDialOn);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpErrorResponse(String address, int code) {
        String str = this.TAG;
        Log.v(str, "onHfpErrorResponse() " + address);
        Message m = getMessage(4);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("code", code);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) {
        String str = this.TAG;
        Log.v(str, "onHfpRemoteTelecomService() " + address);
        Message m = getMessage(7);
        m.obj = address;
        Bundle b = new Bundle();
        b.putBoolean("isTelecomServiceOn", isTelecomServiceOn);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpRemoteRoamingStatus(String address, boolean isRoamingOn) {
        String str = this.TAG;
        Log.v(str, "onHfpRemoteRoamingStatus() " + address);
        Message m = getMessage(8);
        m.obj = address;
        Bundle b = new Bundle();
        b.putBoolean("isRoamingOn", isRoamingOn);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) {
        String str = this.TAG;
        Log.v(str, "onHfpRemoteBatteryIndicator() " + address);
        Message m = getMessage(9);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("currentValue", currentValue);
        b.putInt("maxValue", maxValue);
        b.putInt("minValue", minValue);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) {
        String str = this.TAG;
        Log.v(str, "onHfpRemoteSignalStrength() " + address);
        Message m = getMessage(10);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("currentStrength", currentStrength);
        b.putInt("maxStrength", maxStrength);
        b.putInt("minStrength", minStrength);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void onHfpCallChanged(String address, GocHfpClientCall call) {
        String str = this.TAG;
        Log.v(str, "onHfpCallChanged() " + address);
        Message m = getMessage(11);
        m.obj = address;
        Bundle b = new Bundle();
        b.putParcelable("list", call);
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
        if (i == 0) {
            callbackOnHfpServiceReady();
        } else if (i == 1) {
            callbackOnHfpStateChanged(address, prevState, newState);
        } else if (i == 2) {
            callbackOnHfpAudioStateChanged(address, prevState, newState);
        } else if (i == 3) {
            callbackOnHfpVoiceDial(address, b.getBoolean("isVoiceDialOn"));
        } else if (i == 4) {
            callbackOnHfpErrorResponse(address, b.getInt("code"));
        } else if (i != 101) {
            switch (i) {
                case 7:
                    callbackOnHfpRemoteTelecomService(address, b.getBoolean("isTelecomServiceOn"));
                    return;
                case 8:
                    callbackOnHfpRemoteRoamingStatus(address, b.getBoolean("isRoamingOn"));
                    return;
                case 9:
                    callbackOnHfpRemoteBatteryIndicator(address, b.getInt("currentValue"), b.getInt("maxValue"), b.getInt("minValue"));
                    return;
                case 10:
                    callbackOnHfpRemoteSignalStrength(address, b.getInt("currentStrength"), b.getInt("maxStrength"), b.getInt("minStrength"));
                    return;
                case 11:
                    callbackOnHfpCallChanged(address, (GocHfpClientCall) b.getParcelable("list"));
                    return;
                case 12:
                    callbackonHfpCallingTimeChanged((String) msg.obj);
                    return;
                default:
                    String str = this.TAG;
                    Log.e(str, "unhandle Message : " + msg.what);
                    return;
            }
        } else {
            callbackRetPbapDatabaseQueryNameByNumber(address, b.getString("target"), b.getString("name"), b.getBoolean("isSuccess"));
        }
    }

    private synchronized void callbackOnHfpServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnHfpServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpServiceReady();
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
                Log.v(this.TAG, "onHfpServiceReady CallBack Finish.");
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
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpStateChanged(address, prevState, newState);
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

    private synchronized void callbackonHfpCallingTimeChanged(String time) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackonHfpCallingTimeChanged() " + time);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpCallingTimeChanged(time);
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

    private synchronized void callbackOnHfpAudioStateChanged(String address, int prevState, int newState) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpAudioStateChanged() " + address + " state: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpAudioStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpAudioStateChanged(address, prevState, newState);
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
                Log.v(this.TAG, "onHfpAudioStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpVoiceDial(String address, boolean isVoiceDialOn) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpVoiceDial() " + address + " isVoiceDialOn: " + isVoiceDialOn);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpVoiceDial beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpVoiceDial(address, isVoiceDialOn);
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

    private synchronized void callbackOnHfpErrorResponse(String address, int code) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpErrorResponse() " + address + " code: " + code);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpErrorResponse beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpErrorResponse(address, code);
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
                Log.v(this.TAG, "onHfpErrorResponse CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpRemoteTelecomService() " + address + " isTelecomServiceOn: " + isTelecomServiceOn);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpRemoteTelecomService beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpRemoteTelecomService(address, isTelecomServiceOn);
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
                Log.v(this.TAG, "onHfpRemoteTelecomService CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpRemoteRoamingStatus(String address, boolean isRoamingOn) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpRemoteRoamingStatus() " + address + " isRoamingOn: " + isRoamingOn);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpRemoteRoamingStatus beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpRemoteRoamingStatus(address, isRoamingOn);
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
                Log.v(this.TAG, "onHfpRemoteRoamingStatus CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpRemoteBatteryIndicator() " + address + " currentValue: " + currentValue + " (" + minValue + "-" + maxValue + ")");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpRemoteBatteryIndicator beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpRemoteBatteryIndicator(address, currentValue, maxValue, minValue);
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
                Log.v(this.TAG, "onHfpRemoteBatteryIndicator CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpRemoteSignalStrength() " + address + " currentStrength: " + currentStrength + " (" + maxStrength + "-" + minStrength + ")");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpRemoteSignalStrength beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpRemoteSignalStrength(address, currentStrength, maxStrength, minStrength);
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
                Log.v(this.TAG, "onHfpRemoteSignalStrength CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHfpCallChanged(String address, GocHfpClientCall call) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnHfpCallChanged() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHfpCallChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).onHfpCallChanged(address, call);
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
                Log.v(this.TAG, "onHfpCallChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetPbapDatabaseQueryNameByNumber() " + address + " target: " + target + " name: " + name + " isSuccess: " + isSuccess);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPbapDatabaseQueryNameByNumber beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHfp) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapDatabaseQueryNameByNumber(address, target, name, isSuccess);
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
                Log.v(this.TAG, "retPbapDatabaseQueryNameByNumber CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
