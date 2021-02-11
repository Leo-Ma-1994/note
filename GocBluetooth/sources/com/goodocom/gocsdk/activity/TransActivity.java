package com.goodocom.gocsdk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.goodocom.gocsdk.R;

public class TransActivity extends Activity {
    public static final int MSG_FINISH = 0;
    private static Handler hand;
    private Handler handler = new Handler() {
        /* class com.goodocom.gocsdk.activity.TransActivity.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                TransActivity.this.finish();
            }
        }
    };

    public static Handler getHandler() {
        return hand;
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        hand = this.handler;
    }
}
