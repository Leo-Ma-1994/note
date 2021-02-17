package com.goodocom.gocsdk.manager;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallingStateListener extends PhoneStateListener {
    private int CALL_STATE = 0;
    private boolean isListening = false;
    private OnCallStateChangedListener mOnCallStateChangedListener = null;
    private TelephonyManager mTelephonyManager = null;

    public interface OnCallStateChangedListener {
        public static final int STATE_IDLE = 0;
        public static final int STATE_IN = 1;
        public static final int STATE_OUT = 2;
        public static final int STATE_RINGING = 3;

        void onCallStateChanged(int i, String str);
    }

    public CallingStateListener(Context context) {
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public boolean startListener() {
        if (this.isListening) {
            return false;
        }
        this.isListening = true;
        this.mTelephonyManager.listen(this, 32);
        return true;
    }

    public boolean stopListener() {
        if (!this.isListening) {
            return false;
        }
        this.isListening = false;
        this.mTelephonyManager.listen(this, 0);
        return true;
    }

    @Override // android.telephony.PhoneStateListener
    public void onCallStateChanged(int state, String mobilePhone) {
        super.onCallStateChanged(state, mobilePhone);
        Log.e("calling", "calling>>>>>>>>>>>>>>>>>>>>>>>>>>mobilePhone " + mobilePhone + "   state " + state);
        if (state != 0) {
            int i = 1;
            if (state == 1) {
                OnCallStateChangedListener onCallStateChangedListener = this.mOnCallStateChangedListener;
                if (onCallStateChangedListener != null) {
                    onCallStateChangedListener.onCallStateChanged(3, mobilePhone);
                }
                this.CALL_STATE = state;
            } else if (state == 2) {
                OnCallStateChangedListener onCallStateChangedListener2 = this.mOnCallStateChangedListener;
                if (onCallStateChangedListener2 != null) {
                    if (this.CALL_STATE != 1) {
                        i = 2;
                    }
                    onCallStateChangedListener2.onCallStateChanged(i, mobilePhone);
                }
                this.CALL_STATE = state;
            }
        } else {
            OnCallStateChangedListener onCallStateChangedListener3 = this.mOnCallStateChangedListener;
            if (onCallStateChangedListener3 != null) {
                onCallStateChangedListener3.onCallStateChanged(0, mobilePhone);
            }
            this.CALL_STATE = state;
        }
    }

    public void setOnCallStateChangedListener(OnCallStateChangedListener onCallStateChangedListener) {
        this.mOnCallStateChangedListener = onCallStateChangedListener;
    }
}
