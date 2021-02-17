package com.goodocom.bttek.bt.callback;

import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackA2dp;

public final class DoCallbackA2dp extends DoCallback<UiCallbackA2dp> {
    private final int onA2dpServiceReady = 0;
    private final int onA2dpStateChanged = 1;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackA2dp";
    }

    public void onA2dpServiceReady() {
        Log.v(this.TAG, "onA2dpServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public synchronized void onA2dpStateChanged(String address, int prevState, int newState) {
        String str = this.TAG;
        Log.v(str, "onA2dpStateChanged() : " + prevState + " -> " + newState);
        Message m = Message.obtain(this.mHandler, 1);
        m.arg1 = prevState;
        m.arg2 = newState;
        m.obj = address;
        enqueueMessage(m);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public void dequeueMessage(Message msg) {
        String address = (String) msg.obj;
        int i = msg.what;
        if (i == 0) {
            callbackOnA2dpServiceReady();
        } else if (i != 1) {
            String str = this.TAG;
            Log.e(str, "unhandle Message : " + msg.what);
        } else {
            callbackOnA2dpStateChanged(address, msg.arg1, msg.arg2);
        }
    }

    private synchronized void callbackOnA2dpServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnA2dpServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onA2dpServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackA2dp) this.mRemoteCallbacks.getBroadcastItem(i)).onA2dpServiceReady();
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
                Log.v(this.TAG, "onA2dpServiceReady CallBack Finish.");
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
                        ((UiCallbackA2dp) this.mRemoteCallbacks.getBroadcastItem(i)).onA2dpStateChanged(address, prevState, newState);
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
}
