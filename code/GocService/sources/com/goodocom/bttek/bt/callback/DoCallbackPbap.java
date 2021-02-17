package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.GocPbapContact;
import com.goodocom.bttek.bt.aidl.UiCallbackPbap;

public final class DoCallbackPbap extends DoCallback<UiCallbackPbap> {
    private final int onPbapDownloadNotify = 9;
    private final int onPbapServiceReady = 0;
    private final int onPbapStateChanged = 1;
    private final int retPbapCleanDatabaseCompleted = 6;
    private final int retPbapDatabaseAvailable = 4;
    private final int retPbapDatabaseQueryNameByNumber = 2;
    private final int retPbapDatabaseQueryNameByPartialNumber = 3;
    private final int retPbapDeleteDatabaseByAddressCompleted = 5;
    private final int retPbapDownloadedCallLog = 8;
    private final int retPbapDownloadedContact = 7;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackPbap";
    }

    public void onPbapServiceReady() {
        Log.v(this.TAG, "onPbapServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public synchronized void onPbapStateChanged(String address, int prevState, int newState, int reason, int counts) {
        String str = this.TAG;
        Log.v(str, "onPbapStateChanged() " + address);
        Message m = getMessage(1);
        m.obj = address;
        m.arg1 = prevState;
        m.arg2 = newState;
        Bundle b = new Bundle();
        b.putInt("reason", reason);
        b.putInt("counts", counts);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) {
        String str = this.TAG;
        Log.v(str, "retPbapDatabaseQueryNameByNumber() " + address);
        Message m = getMessage(2);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("target", target);
        b.putString("name", name);
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) {
        String str = this.TAG;
        Log.v(str, "retPbapDatabaseQueryNameByPartialNumber() " + address);
        Message m = getMessage(3);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("target", target);
        b.putStringArray("names", names);
        b.putStringArray("numbers", numbers);
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retPbapDatabaseAvailable(String address) {
        String str = this.TAG;
        Log.v(str, "retPbapDatabaseAvailable() " + address);
        Message m = getMessage(4);
        m.obj = address;
        enqueueMessage(m);
    }

    public synchronized void retPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) {
        String str = this.TAG;
        Log.v(str, "retPbapDeleteDatabaseByAddressCompleted() " + address);
        Message m = getMessage(5);
        m.obj = address;
        Bundle b = new Bundle();
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public synchronized void retPbapCleanDatabaseCompleted(boolean isSuccess) {
        String str = this.TAG;
        Log.v(str, "retPbapCleanDatabaseCompleted() isSuccess: " + isSuccess);
        Message m = getMessage(6);
        Bundle b = new Bundle();
        b.putBoolean("isSuccess", isSuccess);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retPbapDownloadedContact(GocPbapContact contact) {
        Log.v(this.TAG, "retPbapDownloadedContact()");
        Message m = getMessage(7);
        Bundle b = new Bundle();
        b.putParcelable("contact", contact);
        m.setData(b);
        enqueueMessage(m);
    }

    public void retPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) {
        Log.v(this.TAG, "retPbapDownloadedCallLog()");
        Message m = getMessage(8);
        m.obj = address;
        Bundle b = new Bundle();
        b.putString("firstName", firstName);
        b.putString("middleName", middleName);
        b.putString("lastName", lastName);
        b.putString("number", number);
        b.putString("timestamp", timestamp);
        b.putInt("type", type);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) {
        Log.v(this.TAG, "onPbapDownloadNotify()");
        Message m = getMessage(9);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("storage", storage);
        b.putInt("totalContacts", totalContacts);
        b.putInt("downloadedContacts", downloadedContacts);
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
                callbackOnPbapServiceReady();
                return;
            case 1:
                callbackOnPbapStateChanged(address, prevState, newState, b.getInt("reason"), b.getInt("counts"));
                return;
            case 2:
                callbackRetPbapDatabaseQueryNameByNumber(address, b.getString("target"), b.getString("name"), b.getBoolean("isSuccess"));
                return;
            case 3:
                callbackRetPbapDatabaseQueryNameByPartialNumber(address, b.getString("target"), b.getStringArray("names"), b.getStringArray("numbers"), b.getBoolean("isSuccess"));
                return;
            case 4:
                callbackRetPbapDatabaseAvailable(address);
                return;
            case 5:
                callbackRetPbapDeleteDatabaseByAddressCompleted(address, b.getBoolean("isSuccess"));
                return;
            case 6:
                callbackRetPbapCleanDatabaseCompleted(b.getBoolean("isSuccess"));
                return;
            case 7:
                callbackRetPbapDownloadedContact((GocPbapContact) b.getParcelable("contact"));
                return;
            case 8:
                callbackRetPbapDownloadedCallLog(address, b.getString("firstName"), b.getString("middleName"), b.getString("lastName"), b.getString("number"), b.getInt("type"), b.getString("timestamp"));
                return;
            case 9:
                callbackOnPbapDownloadNotify(address, b.getInt("storage"), b.getInt("totalContacts"), b.getInt("downloadedContacts"));
                return;
            default:
                String str = this.TAG;
                Log.e(str, "unhandle Message : " + msg.what);
                return;
        }
    }

    private synchronized void callbackOnPbapServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnPbapServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onPbapServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).onPbapServiceReady();
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
                Log.v(this.TAG, "onPbapServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnPbapStateChanged(String address, int prevState, int newState, int reason, int counts) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnPbapStateChanged() " + address + " state: " + prevState + "->" + newState);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onPbapStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).onPbapStateChanged(address, prevState, newState, reason, counts);
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
                Log.v(this.TAG, "onPbapStateChanged CallBack Finish.");
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
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapDatabaseQueryNameByNumber(address, target, name, isSuccess);
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

    private synchronized void callbackRetPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetPbapDatabaseQueryNameByPartialNumber() " + address + " target: " + target + " isSuccess: " + isSuccess);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPbapDatabaseQueryNameByPartialNumber beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapDatabaseQueryNameByPartialNumber(address, target, names, numbers, isSuccess);
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
                Log.v(this.TAG, "retPbapDatabaseQueryNameByPartialNumber CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetPbapDatabaseAvailable(String address) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetPbapDatabaseAvailable() " + address);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPbapDatabaseAvailable beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapDatabaseAvailable(address);
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
                Log.v(this.TAG, "retPbapDatabaseAvailable CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetPbapDeleteDatabaseByAddressCompleted() " + address + " isSuccess: " + isSuccess);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPbapDeleteDatabaseByAddressCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapDeleteDatabaseByAddressCompleted(address, isSuccess);
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
                Log.v(this.TAG, "retPbapDeleteDatabaseByAddressCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackRetPbapCleanDatabaseCompleted(boolean isSuccess) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackRetPbapCleanDatabaseCompleted() isSuccess: " + isSuccess);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPbapCleanDatabaseCompleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapCleanDatabaseCompleted(isSuccess);
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
                Log.v(this.TAG, "retPbapCleanDatabaseCompleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0053, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void callbackRetPbapDownloadedContact(com.goodocom.bttek.bt.aidl.GocPbapContact r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.String r0 = r7.TAG     // Catch:{ all -> 0x0055 }
            java.lang.String r1 = "callbackRetPbapDownloadedContact()"
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x0055 }
            android.os.RemoteCallbackList r0 = r7.mRemoteCallbacks     // Catch:{ all -> 0x0055 }
            monitor-enter(r0)     // Catch:{ all -> 0x0055 }
            android.os.RemoteCallbackList r1 = r7.mRemoteCallbacks     // Catch:{ all -> 0x0050 }
            int r1 = r1.beginBroadcast()     // Catch:{ all -> 0x0050 }
            r2 = 0
        L_0x0012:
            if (r2 >= r1) goto L_0x0048
            android.os.RemoteCallbackList r3 = r7.mRemoteCallbacks     // Catch:{ RemoteException -> 0x0025, NullPointerException -> 0x0020 }
            android.os.IInterface r3 = r3.getBroadcastItem(r2)     // Catch:{ RemoteException -> 0x0025, NullPointerException -> 0x0020 }
            com.goodocom.bttek.bt.aidl.UiCallbackPbap r3 = (com.goodocom.bttek.bt.aidl.UiCallbackPbap) r3     // Catch:{ RemoteException -> 0x0025, NullPointerException -> 0x0020 }
            r3.retPbapDownloadedContact(r8)     // Catch:{ RemoteException -> 0x0025, NullPointerException -> 0x0020 }
            goto L_0x0045
        L_0x0020:
            r3 = move-exception
            r7.checkCallbacksValid(r2)
            goto L_0x0045
        L_0x0025:
            r3 = move-exception
            java.lang.String r4 = r7.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Callback count: "
            r5.append(r6)
            r5.append(r1)
            java.lang.String r6 = " Current index = "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            android.util.Log.e(r4, r5)
        L_0x0045:
            int r2 = r2 + 1
            goto L_0x0012
        L_0x0048:
            android.os.RemoteCallbackList r2 = r7.mRemoteCallbacks
            r2.finishBroadcast()
            monitor-exit(r0)
            monitor-exit(r7)
            return
        L_0x0050:
            r1 = move-exception
        L_0x0051:
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            throw r1
        L_0x0053:
            r1 = move-exception
            goto L_0x0051
        L_0x0055:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.goodocom.bttek.bt.callback.DoCallbackPbap.callbackRetPbapDownloadedContact(com.goodocom.bttek.bt.aidl.GocPbapContact):void");
    }

    private synchronized void callbackRetPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) {
        Throwable th;
        Log.d(this.TAG, "callbackRetPbapDownloadedCallLog()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "retPbapDownloadedCallLog beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).retPbapDownloadedCallLog(address, firstName, middleName, lastName, number, type, timestamp);
                    } catch (RemoteException e) {
                        Log.e(this.TAG, "Callback count: " + n + " Current index = " + i);
                    } catch (NullPointerException e2) {
                        checkCallbacksValid(i);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                this.mRemoteCallbacks.finishBroadcast();
                Log.v(this.TAG, "retPbapDownloadedCallLog CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnPbapDownloadNotify() " + address + " storage: " + storage + " downloaded: " + downloadedContacts + "/" + totalContacts);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "callbackOnPbapDownloadNotify beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackPbap) this.mRemoteCallbacks.getBroadcastItem(i)).onPbapDownloadNotify(address, storage, totalContacts, downloadedContacts);
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
                Log.v(this.TAG, "callbackOnPbapDownloadNotify CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
