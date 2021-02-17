package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackMap;

public final class DoCallbackMap extends DoCallback<UiCallbackMap> {
    private final int onMapDownloadNotify = 4;
    private final int onMapMemoryAvailableEvent = 11;
    private final int onMapMessageDeletedEvent = 15;
    private final int onMapMessageDeliverStatusEvent = 13;
    private final int onMapMessageSendingStatusEvent = 12;
    private final int onMapMessageShiftedEvent = 14;
    private final int onMapNewMessageReceivedEvent = 3;
    private final int onMapServiceReady = 0;
    private final int onMapStateChanged = 1;
    private final int retMapChangeReadStatusCompleted = 10;
    private final int retMapCleanDatabaseCompleted = 7;
    private final int retMapDatabaseAvailable = 5;
    private final int retMapDeleteDatabaseByAddressCompleted = 6;
    private final int retMapDeleteMessageCompleted = 9;
    private final int retMapDownloadedMessage = 2;
    private final int retMapSendMessageCompleted = 8;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackMap";
    }

    public void onMapServiceReady() {
        Log.d(this.TAG, "onMapServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public void onMapStateChanged(String address, int prevState, int newState, int reason) {
        String str = this.TAG;
        Log.d(str, "onMapStateChanged() " + address);
        Message m = getMessage(1);
        m.obj = address;
        m.arg1 = prevState;
        m.arg2 = newState;
        Bundle b = new Bundle();
        b.putInt("reason", reason);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String date, String recipientAddr, int type, int folder, boolean readStatus, String subject, String message) {
        String str = this.TAG;
        Log.d(str, "retMapDownloadedMessage() " + address);
        Message m = getMessage(2);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putString("senderName", senderName);
        b.putString("senderNumber", senderNumber);
        b.putString("date", date);
        b.putString("recipientAddr", recipientAddr);
        b.putInt("type", type);
        b.putInt("folder", folder);
        b.putBoolean("readStatus", readStatus);
        b.putString("subject", subject);
        b.putString("message", message);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapNewMessageReceivedEvent(String address, String handle, String sender, String message) {
        Log.d(this.TAG, "onMapNewMessageReceivedEvent()");
        Message m = getMessage(3);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putString("sender", sender);
        b.putString("message", message);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) {
        String str = this.TAG;
        Log.d(str, "onMapDownloadNotify() " + address);
        Message m = getMessage(4);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("folder", folder);
        b.putInt("totalMessages", totalMessages);
        b.putInt("currentMessages", currentMessages);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retMapDatabaseAvailable() {
        Log.d(this.TAG, "retMapDatabaseAvailable()");
        enqueueMessage(getMessage(5));
    }

    public void retMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) {
        Log.d(this.TAG, "retMapDeleteDatabaseByAddressCompleted()");
        Message m = getMessage(6);
        m.obj = address;
        Bundle b = new Bundle();
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retMapCleanDatabaseCompleted(boolean isSuccess) {
        Log.d(this.TAG, "retMapCleanDatabaseCompleted()");
        Message m = getMessage(7);
        Bundle b = new Bundle();
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retMapSendMessageCompleted(String address, String target, int state) {
        String str = this.TAG;
        Log.d(str, "retMapSendMessageCompleted() " + address);
        Message m = getMessage(8);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("target", target);
        b.putInt("message", state);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retMapDeleteMessageCompleted(String address, String handle, int reason) {
        String str = this.TAG;
        Log.d(str, "retMapDeleteMessageCompleted() " + address);
        Message m = getMessage(9);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putInt("reason", reason);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retMapChangeReadStatusCompleted(String address, String handle, int reason) {
        String str = this.TAG;
        Log.d(str, "retMapChangeReadStatusCompleted() " + address);
        Message m = getMessage(10);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putInt("reason", reason);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapMemoryAvailableEvent(String address, int structure, boolean available) {
        String str = this.TAG;
        Log.d(str, "onMapMemoryAvailableEvent() " + address);
        Message m = getMessage(11);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("structure", structure);
        b.putBoolean("available", available);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) {
        String str = this.TAG;
        Log.d(str, "onMapMessageSendingStatusEvent() " + address);
        Message m = getMessage(12);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) {
        String str = this.TAG;
        Log.d(str, "onMapMessageDeliverStatusEvent() " + address);
        Message m = getMessage(13);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) {
        String str = this.TAG;
        Log.d(str, "onMapMessageShiftedEvent() " + address);
        Message m = getMessage(14);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putInt("type", type);
        b.putInt("newFolder", newFolder);
        b.putInt("oldFolder", oldFolder);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onMapMessageDeletedEvent(String address, String handle, int type, int folder) {
        String str = this.TAG;
        Log.d(str, "onMapMessageDeletedEvent() " + address);
        Message m = getMessage(15);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("handle", handle);
        b.putInt("type", type);
        b.putInt("folder", folder);
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
        switch (msg.what) {
            case 0:
                callbackOnMapServiceReady();
                return;
            case 1:
                callbackOnMapStateChanged(address, prevState, newState, b.getInt("reason"));
                return;
            case 2:
                callbackRetMapDownloadedMessage(address, b.getString("handle"), b.getString("senderName"), b.getString("senderNumber"), b.getString("date"), b.getString("recipientAddr"), b.getInt("type"), b.getInt("folder"), b.getBoolean("readStatus"), b.getString("subject"), b.getString("message"));
                return;
            case 3:
                callbackOnMapNewMessageReceived(address, b.getString("handle"), b.getString("sender"), b.getString("message"));
                return;
            case 4:
                callbackOnMapDownloadNotify(address, b.getInt("folder"), b.getInt("totalMessages"), b.getInt("currentMessages"));
                return;
            case 5:
                callbackRetMapDatabaseAvailable();
                return;
            case 6:
                callbackRetMapDeleteDatabaseByAddressCompleted(address, b.getBoolean("isSuccess"));
                return;
            case 7:
                callbackRetMapCleanDatabaseCompleted(b.getBoolean("isSuccess"));
                return;
            case 8:
                callbackRetMapSendMessageCompleted(address, b.getString("target"), b.getInt("state"));
                return;
            case 9:
                callbackRetMapDeleteMessageCompleted(address, b.getString("handle"), b.getInt("reason"));
                return;
            case 10:
                callbackRetMapChangeReadStatusCompleted(address, b.getString("handle"), b.getInt("reason"));
                return;
            case 11:
                callbackOnMapMemoryAvailableEvent(address, b.getInt("structure"), b.getBoolean("available"));
                return;
            case 12:
                callbackOnMapMessageSendingStatusEvent(address, b.getString("handle"), b.getBoolean("isSuccess"));
                return;
            case 13:
                callbackOnMapMessageDeliverStatusEvent(address, b.getString("handle"), b.getBoolean("isSuccess"));
                return;
            case 14:
                callbackOnMapMessageShiftedEvent(address, b.getString("handle"), b.getInt("type"), b.getInt("newFolder"), b.getInt("oldFolder"));
                return;
            case 15:
                callbackOnMapMessageDeletedEvent(address, b.getString("handle"), b.getInt("type"), b.getInt("folder"));
                return;
            default:
                String str = this.TAG;
                Log.e(str, "unhandle Message : " + msg.what);
                return;
        }
    }

    private synchronized void callbackOnMapServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnMapServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapServiceReady();
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
                Log.v(this.TAG, "onMapServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapStateChanged(String address, int prevState, int newState, int reason) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapStateChanged() " + address + " state: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapStateChanged(address, prevState, newState, reason);
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
                Log.v(this.TAG, "onMapStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String date, String recipientAddr, int type, int folder, boolean isRead, String subject, String message) {
        Throwable th;
        int n;
        int i;
        int n2;
        Log.v(this.TAG, "callbackRetMapDownloadedMessage() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retMapDownloadedMessage beginBroadcast()");
                int n3 = this.mRemoteCallbacks.beginBroadcast();
                int i2 = 0;
                while (i2 < n3) {
                    try {
                        i = i2;
                        n2 = n3;
                        try {
                            ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i2)).retMapDownloadedMessage(address, handle, senderName, senderNumber, recipientAddr, date, type, folder, isRead, subject, message);
                            n = n2;
                        } catch (RemoteException e) {
                            String str = this.TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Callback count: ");
                            n = n2;
                            sb.append(n);
                            sb.append(" Current index = ");
                            sb.append(i);
                            Log.e(str, sb.toString());
                            i2 = i + 1;
                            n3 = n;
                        } catch (NullPointerException e2) {
                            checkCallbacksValid(i);
                            n = n2;
                            i2 = i + 1;
                            n3 = n;
                        }
                    } catch (RemoteException e3) {
                        i = i2;
                        n2 = n3;
                        String str = this.TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Callback count: ");
                        n = n2;
                        sb.append(n);
                        sb.append(" Current index = ");
                        sb.append(i);
                        Log.e(str, sb.toString());
                        i2 = i + 1;
                        n3 = n;
                    } catch (NullPointerException e4) {
                        i = i2;
                        n2 = n3;
                        checkCallbacksValid(i);
                        n = n2;
                        i2 = i + 1;
                        n3 = n;
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                    i2 = i + 1;
                    n3 = n;
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retMapDownloadedMessage CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapNewMessageReceived(String address, String handle, String sender, String message) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapNewMessageReceived() " + address + " handle: " + handle);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapNewMessageReceived beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapNewMessageReceivedEvent(address, handle, sender, message);
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
                Log.v(this.TAG, "onMapNewMessageReceived CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapDownloadNotify() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapDownloadNotify beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapDownloadNotify(address, folder, totalMessages, currentMessages);
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
                Log.v(this.TAG, "onMapDownloadNotify CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapDatabaseAvailable() {
        Throwable th;
        Log.v(this.TAG, "callbackRetMapDatabaseAvailable()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retMapDatabaseAvailable beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).retMapDatabaseAvailable();
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
                Log.v(this.TAG, "retMapDatabaseAvailable CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackRetMapDeleteDatabaseByAddressCompleted() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retMapDeleteDatabaseByAddressCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).retMapDeleteDatabaseByAddressCompleted(address, isSuccess);
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
                Log.v(this.TAG, "retMapDeleteDatabaseByAddressCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapCleanDatabaseCompleted(boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackRetMapCleanDatabaseCompleted() " + isSuccess);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retMapCleanDatabaseCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).retMapCleanDatabaseCompleted(isSuccess);
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
                Log.v(this.TAG, "retMapCleanDatabaseCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapSendMessageCompleted(String address, String target, int state) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackRetMapSendMessageCompleted() " + state);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackRetMapSendMessageCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).retMapSendMessageCompleted(address, target, state);
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
                Log.v(this.TAG, "retMapSendMessageCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapDeleteMessageCompleted(String address, String handle, int reason) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackRetMapDeleteMessageCompleted() " + reason);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackRetMapDeleteMessageCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).retMapDeleteMessageCompleted(address, handle, reason);
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
                Log.v(this.TAG, "retMapDeleteMessageCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetMapChangeReadStatusCompleted(String address, String handle, int reason) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackRetMapReadStatusCompleted() " + reason);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackRetMapReadStatusCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).retMapChangeReadStatusCompleted(address, handle, reason);
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
                Log.v(this.TAG, "retMapReadStatusCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapMemoryAvailableEvent(String address, int structure, boolean available) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapMemoryAvailableEvent() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapMemoryAvailableEvent beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapMemoryAvailableEvent(address, structure, available);
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
                Log.v(this.TAG, "onMapMemoryAvailableEvent CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapMessageSendingStatusEvent() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapMessageSendingStatusEvent beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapMessageSendingStatusEvent(address, handle, isSuccess);
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
                Log.v(this.TAG, "onMapMessageSendingStatusEvent CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapMessageSendingStatusEvent() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapMessageDeliverStatusEvent beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapMessageDeliverStatusEvent(address, handle, isSuccess);
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
                Log.v(this.TAG, "onMapMessageDeliverStatusEvent CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapMessageShiftedEvent() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapMessageShiftedEvent beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapMessageShiftedEvent(address, handle, type, newFolder, oldFolder);
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
                Log.v(this.TAG, "onMapMessageShiftedEvent CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnMapMessageDeletedEvent(String address, String handle, int type, int folder) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnMapMessageDeletedEvent() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onMapMessageDeletedEvent beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackMap) this.mRemoteCallbacks.getBroadcastItem(i)).onMapMessageDeletedEvent(address, handle, type, folder);
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
                Log.v(this.TAG, "onMapMessageDeletedEvent CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
