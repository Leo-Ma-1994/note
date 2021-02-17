package com.goodocom.gocsdk.key;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class HomeKey {
    static final String TAG = "HomeWatcher";
    private boolean isRegister = false;
    private Context mContext;
    private IntentFilter mFilter;
    private OnHomePressedListener mListener;
    private InnerRecevier mRecevier;

    public interface OnHomePressedListener {
        void onHomeLongPressed();

        void onHomePressed();
    }

    public HomeKey(Context context) {
        this.mContext = context;
        this.mFilter = new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    public void setOnHomePressedListener(OnHomePressedListener listener) {
        this.mListener = listener;
        this.mRecevier = new InnerRecevier();
    }

    public void startWatch() {
        InnerRecevier innerRecevier = this.mRecevier;
        if (innerRecevier != null) {
            this.isRegister = true;
            this.mContext.registerReceiver(innerRecevier, this.mFilter);
        }
    }

    public void stopWatch() {
        InnerRecevier innerRecevier = this.mRecevier;
        if (innerRecevier != null && this.isRegister) {
            this.isRegister = false;
            this.mContext.unregisterReceiver(innerRecevier);
        }
    }

    /* access modifiers changed from: package-private */
    public class InnerRecevier extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        InnerRecevier() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String reason;
            String action = intent.getAction();
            if (action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS") && (reason = intent.getStringExtra("reason")) != null) {
                Log.e(HomeKey.TAG, "action:" + action + ",reason:" + reason);
                if (HomeKey.this.mListener == null) {
                    return;
                }
                if (reason.equals("homekey")) {
                    HomeKey.this.mListener.onHomePressed();
                } else if (reason.equals("recentapps")) {
                    HomeKey.this.mListener.onHomeLongPressed();
                }
            }
        }
    }
}
