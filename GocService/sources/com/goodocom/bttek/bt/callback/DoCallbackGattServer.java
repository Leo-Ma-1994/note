package com.goodocom.bttek.bt.callback;

import android.os.Bundle;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.goodocom.bttek.bt.aidl.UiCallbackGattServer;

public final class DoCallbackGattServer extends DoCallback<UiCallbackGattServer> {
    private final int onGattServerCharacteristicReadRequest = 5;
    private final int onGattServerCharacteristicWriteRequest = 7;
    private final int onGattServerDescriptorReadRequest = 6;
    private final int onGattServerDescriptorWriteRequest = 8;
    private final int onGattServerExecuteWrite = 9;
    private final int onGattServerServiceAdded = 3;
    private final int onGattServerServiceDeleted = 4;
    private final int onGattServerStateChanged = 2;
    private final int onGattServiceReady = 0;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public String getLogTag() {
        return "DoCallbackGattServer";
    }

    public void onGattServiceReady() {
        Log.d(this.TAG, "onGattServiceReady()");
        enqueueMessage(Message.obtain(this.mHandler, 0));
    }

    public void onGattServerStateChanged(String address, int state) {
        Log.d(this.TAG, "onGattServerStateChanged()");
        Message m = Message.obtain(this.mHandler, 2);
        Bundle b = new Bundle();
        m.obj = address;
        b.putInt("state", state);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcId) {
        Log.d(this.TAG, "onGattServerServiceAdded()");
        Message m = Message.obtain(this.mHandler, 3);
        Bundle b = new Bundle();
        b.putInt(NotificationCompat.CATEGORY_STATUS, status);
        b.putParcelable("srvcId", srvcId);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcId) {
        Log.d(this.TAG, "onGattServerServiceDeleted()");
        Message m = Message.obtain(this.mHandler, 4);
        Bundle b = new Bundle();
        b.putInt(NotificationCompat.CATEGORY_STATUS, status);
        b.putParcelable("srvcId", srvcId);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerCharacteristicReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId) {
        Log.d(this.TAG, "onGattServerCharacteristicReadRequest()");
        Message m = Message.obtain(this.mHandler, 5);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("transId", transId);
        b.putInt("offset", offset);
        b.putBoolean("isLong", isLong);
        b.putInt("srvcType", srvcType);
        b.putParcelable("srvcId", srvcId);
        b.putParcelable("charId", charId);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerDescriptorReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId) {
        Log.d(this.TAG, "onGattServerDescriptorReadRequest()");
        Message m = Message.obtain(this.mHandler, 6);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("transId", transId);
        b.putInt("offset", offset);
        b.putBoolean("isLong", isLong);
        b.putInt("srvcType", srvcType);
        b.putParcelable("srvcId", srvcId);
        b.putParcelable("charId", charId);
        b.putParcelable("descrId", descrId);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerCharacteristicWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, byte[] value) {
        Log.d(this.TAG, "onGattServerCharacteristicWriteRequest()");
        Message m = Message.obtain(this.mHandler, 7);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("transId", transId);
        b.putInt("offset", offset);
        b.putBoolean("isPrep", isPrep);
        b.putBoolean("needRsp", needRsp);
        b.putInt("srvcType", srvcType);
        b.putParcelable("srvcId", srvcId);
        b.putParcelable("charId", charId);
        b.putByteArray("value", value);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerDescriptorWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId, byte[] value) {
        Log.d(this.TAG, "onGattServerDescriptorWriteRequest()");
        Message m = Message.obtain(this.mHandler, 8);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("transId", transId);
        b.putInt("offset", offset);
        b.putBoolean("isPrep", isPrep);
        b.putBoolean("needRsp", needRsp);
        b.putInt("srvcType", srvcType);
        b.putParcelable("srvcId", srvcId);
        b.putParcelable("charId", charId);
        b.putParcelable("descrId", descrId);
        b.putByteArray("value", value);
        m.setData(b);
        enqueueMessage(m);
    }

    public void onGattServerExecuteWrite(String address, int transId, boolean execWrite) {
        Log.d(this.TAG, "onGattServerExecuteWrite()");
        Message m = Message.obtain(this.mHandler, 9);
        m.obj = address;
        Bundle b = new Bundle();
        b.putInt("transId", transId);
        b.putBoolean("execWrite", execWrite);
        m.setData(b);
        enqueueMessage(m);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.bttek.bt.callback.DoCallback
    public void dequeueMessage(Message msg) {
        Bundle b = msg.getData();
        String address = (String) msg.obj;
        int i = msg.what;
        if (i != 0) {
            switch (i) {
                case 2:
                    callbackOnGattServerStateChanged(address, b.getInt("state"));
                    return;
                case 3:
                    callbackOnGattServerServiceAdded(b.getInt(NotificationCompat.CATEGORY_STATUS), b.getInt("srvcType"), (ParcelUuid) b.getParcelable("srvcId"));
                    return;
                case 4:
                    callbackOnGattServerServiceDeleted(b.getInt(NotificationCompat.CATEGORY_STATUS), b.getInt("srvcType"), (ParcelUuid) b.getParcelable("srvcId"));
                    return;
                case 5:
                    callbackOnGattServerCharacteristicReadRequest(address, b.getInt("transId"), b.getInt("offset"), b.getBoolean("isLong"), b.getInt("srvcType"), (ParcelUuid) b.getParcelable("srvcId"), (ParcelUuid) b.getParcelable("charId"));
                    return;
                case 6:
                    callbackOnGattServerDescriptorReadRequest(address, b.getInt("transId"), b.getInt("offset"), b.getBoolean("isLong"), b.getInt("srvcType"), (ParcelUuid) b.getParcelable("srvcId"), (ParcelUuid) b.getParcelable("charId"), (ParcelUuid) b.getParcelable("descrId"));
                    return;
                case 7:
                    callbackOnGattServerCharacteristicWriteRequest(address, b.getInt("transId"), b.getInt("offset"), b.getBoolean("isPrep"), b.getBoolean("needRsp"), b.getInt("srvcType"), (ParcelUuid) b.getParcelable("srvcId"), (ParcelUuid) b.getParcelable("charId"), b.getByteArray("value"));
                    return;
                case 8:
                    callbackOnGattServerDescriptorWriteRequest(address, b.getInt("transId"), b.getInt("offset"), b.getBoolean("isPrep"), b.getBoolean("needRsp"), b.getInt("srvcType"), (ParcelUuid) b.getParcelable("srvcId"), (ParcelUuid) b.getParcelable("charId"), (ParcelUuid) b.getParcelable("descrId"), b.getByteArray("value"));
                    return;
                case 9:
                    callbackOnGattServerExecuteWrite(address, b.getInt("transId"), b.getBoolean("execWrite"));
                    return;
                default:
                    String str = this.TAG;
                    Log.e(str, "unhandle Message : " + msg.what);
                    return;
            }
        } else {
            callbackOnGattServiceReady();
        }
    }

    private synchronized void callbackOnGattServiceReady() {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServiceReady()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServiceReady beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServiceReady();
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
                Log.v(this.TAG, "onGattServiceReady CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerStateChanged(String address, int state) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerStateChanged()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerStateChanged beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerStateChanged(address, state);
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
                Log.v(this.TAG, "onGattServerStateChanged CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcId) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerServiceAdded()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerServiceAdded beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerServiceAdded(status, srvcType, srvcId);
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
                Log.v(this.TAG, "onGattServerServiceAdded CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcId) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerServiceDeleted()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerServiceDeleted beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerServiceDeleted(status, srvcType, srvcId);
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
                Log.v(this.TAG, "onGattServerServiceDeleted CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerCharacteristicReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerCharacteristicReadRequest()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerCharacteristicReadRequest beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerCharacteristicReadRequest(address, transId, offset, isLong, srvcType, srvcId, charId);
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
                Log.v(this.TAG, "onGattServerCharacteristicReadRequest CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerDescriptorReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerDescriptorReadRequest()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerDescriptorReadRequest beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerDescriptorReadRequest(address, transId, offset, isLong, srvcType, srvcId, charId, descrId);
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
                Log.v(this.TAG, "onGattServerDescriptorReadRequest CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerCharacteristicWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, byte[] value) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerCharacteristicWriteRequest()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerCharacteristicWriteRequest beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerCharacteristicWriteRequest(address, transId, offset, isPrep, needRsp, srvcType, srvcId, charId, value);
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
                Log.v(this.TAG, "onGattServerCharacteristicWriteRequest CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerDescriptorWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId, byte[] value) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerDescriptorWriteRequest()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerDescriptorWriteRequest beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerDescriptorWriteRequest(address, transId, offset, isPrep, needRsp, srvcType, srvcId, charId, descrId, value);
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
                Log.v(this.TAG, "onGattServerDescriptorWriteRequest CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private synchronized void callbackOnGattServerExecuteWrite(String address, int transId, boolean execWrite) {
        Throwable th;
        Log.v(this.TAG, "callbackOnGattServerExecuteWrite()");
        synchronized (this.mRemoteCallbacks) {
            try {
                Log.v(this.TAG, "onGattServerExecuteWrite beginBroadcast()");
                int n = this.mRemoteCallbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    try {
                        ((UiCallbackGattServer) this.mRemoteCallbacks.getBroadcastItem(i)).onGattServerExecuteWrite(address, transId, execWrite);
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
                Log.v(this.TAG, "onGattServerExecuteWrite CallBack Finish.");
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }
}
