package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackHid;

public final class DoCallbackHid extends DoCallback<UiCallbackHid> {
    private final int onHidServiceReady = 0;
    private final int onHidStateChanged = 1;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackHid";
    }

    public void onHidServiceReady() {
        Log.v(this.TAG, "onHidServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public synchronized void onHidStateChanged(String address, int prevState, int newState, int reason) {
        String str = this.TAG;
        Log.v(str, "onHidStateChanged() : " + prevState + " -> " + newState + " ,reason:" + reason);
        Message m = getMessage(1);
        Bundle b = new Bundle();
        b.putString("address", address);
        b.putInt("prevState", prevState);
        b.putInt("newState", newState);
        b.putInt("reason", reason);
        m.setData(b);
        enqueueMessage(m);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public void dequeueMessage(Message msg) {
        Bundle b = msg.getData();
        String str = (String) msg.obj;
        int i = msg.what;
        if (i == 0) {
            callbackOnHidServiceReady();
        } else if (i != 1) {
            String str2 = this.TAG;
            Log.e(str2, "unhandle Message : " + msg.what);
        } else {
            callbackOnHidStateChanged(b.getString("address"), b.getInt("prevState"), b.getInt("newState"), b.getInt("reason"));
        }
    }

    private synchronized void callbackOnHidServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnHidServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHidServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHid) this.mRemoteCallbacks.getBroadcastItem(i)).onHidServiceReady();
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
                Log.v(this.TAG, "onHidServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnHidStateChanged(String address, int prevState, int newState, int reason) {
        Throwable th;
        String str = this.TAG;
        Log.v(str, "callbackOnHidStateChanged() : " + prevState + " -> " + newState + " ,reason: " + reason);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onHidStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackHid) this.mRemoteCallbacks.getBroadcastItem(i)).onHidStateChanged(address, prevState, newState, reason);
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
                Log.v(this.TAG, "onHidStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
