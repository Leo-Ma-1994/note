package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.UiCallbackOpp;

public final class DoCallbackOpp extends DoCallback<UiCallbackOpp> {
    private final int onOppReceiveFileInfo = 2;
    private final int onOppReceiveProgress = 3;
    private final int onOppServiceReady = 0;
    private final int onOppStateChanged = 1;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "NfDoCallbackOpp";
    }

    public void onOppServiceReady() {
        Log.v(this.TAG, "onOppServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public void onOppStateChanged(String address, int preState, int currentState, int reason) {
        String str = this.TAG;
        Log.v(str, "onOppStateChanged() " + address);
        Message m = getMessage(1);
        m.obj = address;
        m.arg1 = preState;
        m.arg2 = currentState;
        Bundle b = new Bundle();
        b.putInt("reason", reason);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) {
        String str = this.TAG;
        Log.v(str, "onOppReceiveFileInfo() fileName: " + fileName + "fileSize: " + fileSize + "deviceName: " + deviceName + "savePath: " + savePath);
        Message m = getMessage(2);
        Bundle b = new Bundle();
        b.putString("fileName", fileName);
        b.putInt("fileSize", fileSize);
        b.putString("deviceName", deviceName);
        b.putString("savePath", savePath);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onOppReceiveProgress(int receivedOffset) {
        String str = this.TAG;
        Log.v(str, "onOppReceiveProgress() receivedOffset: " + receivedOffset);
        Message m = getMessage(3);
        Bundle b = new Bundle();
        b.putInt("receivedOffset", receivedOffset);
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
            callbackOnOppServiceReady();
        } else if (i == 1) {
            callbackOnOppStateChanged(address, prevState, newState, b.getInt("reason"));
        } else if (i == 2) {
            callbackOnOppReceiveFileInfo(b.getString("fileName"), b.getInt("fileSize"), b.getString("deviceName"), b.getString("savePath"));
        } else if (i != 3) {
            String str = this.TAG;
            Log.e(str, "unhandle Message : " + msg.what);
        } else {
            callbackOnOppReceiveProgress(b.getInt("receivedOffset"));
        }
    }

    private synchronized void callbackOnOppServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnOppServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onOppServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackOpp) this.mRemoteCallbacks.getBroadcastItem(i)).onOppServiceReady();
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
                Log.v(this.TAG, "onOppServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnOppStateChanged(String address, int preState, int currentState, int reason) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnOppStateChanged() " + address + " state: " + preState + "->" + currentState + ", reason: " + reason);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onOppStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackOpp) this.mRemoteCallbacks.getBroadcastItem(i)).onOppStateChanged(address, preState, currentState, reason);
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

    private synchronized void callbackOnOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnOppReceiveFileInfo() fileName: " + fileName + "fileSize: " + fileSize + "deviceName: " + deviceName + "savePath: " + savePath);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onOppReceiveFileInfo beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackOpp) this.mRemoteCallbacks.getBroadcastItem(i)).onOppReceiveFileInfo(fileName, fileSize, deviceName, savePath);
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
                Log.v(this.TAG, "onOppReceiveFileInfo CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnOppReceiveProgress(int receivedOffset) {
        Throwable th;
        String str = this.TAG;
        Log.d(str, "callbackOnOppReceiveProgres() receivedOffset: " + receivedOffset);
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onOppReceiveProgress beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackOpp) this.mRemoteCallbacks.getBroadcastItem(i)).onOppReceiveProgress(receivedOffset);
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
                Log.v(this.TAG, "onOppReceiveProgress CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
