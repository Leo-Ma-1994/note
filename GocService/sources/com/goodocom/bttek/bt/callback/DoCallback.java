package com.goodocom.bttek.bt.callback;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;

public abstract class DoCallback<E extends IInterface> {
    protected String TAG = "gocsdkDoCalback";
    protected Handler mHandler;
    HandlerThread mHandlerThread = new HandlerThread(this.TAG);
    protected RemoteCallbackList<E> mRemoteCallbacks = new RemoteCallbackList<>();

    /* access modifiers changed from: protected */
    public abstract void dequeueMessage(Message message);

    /* access modifiers changed from: protected */
    public abstract String getLogTag();

    public DoCallback() {
        this.mHandlerThread.start();
        this.mHandler = initCallbackHandler();
        String str = this.TAG;
        Log.v(str, BuildConfig.FLAVOR + this.TAG + "() init");
    }

    public boolean register(E cb) {
        return this.mRemoteCallbacks.register(cb);
    }

    public boolean unregister(E cb) {
        return this.mRemoteCallbacks.unregister(cb);
    }

    public void kill() {
        this.mRemoteCallbacks.kill();
        this.mRemoteCallbacks = null;
    }

    /* access modifiers changed from: protected */
    public void enqueueMessage(Message msg) {
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: protected */
    public Message getMessage(int what) {
        return this.mHandler.obtainMessage(what);
    }

    private Handler initCallbackHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            return handler;
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            return new Handler(handlerThread.getLooper()) {
                /* class com.goodocom.bttek.bt.callback.DoCallback.AnonymousClass1 */

                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    DoCallback.this.dequeueMessage(msg);
                }
            };
        }
        Log.e(this.TAG, "mHandlerThread is null !!");
        return null;
    }

    /* access modifiers changed from: protected */
    public void checkCallbacksValid(int index) {
        E cb;
        RemoteCallbackList<E> remoteCallbackList = this.mRemoteCallbacks;
        if (remoteCallbackList != null && (cb = remoteCallbackList.getBroadcastItem(index)) == null) {
            String str = this.TAG;
            Log.e(str, "Callback " + cb + " is null !! unregister here.");
            this.mRemoteCallbacks.unregister(cb);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCallbackValid() {
        if (this.mRemoteCallbacks != null) {
            return true;
        }
        Log.e(this.TAG, "Remote Callbacks is null !!");
        return false;
    }
}
