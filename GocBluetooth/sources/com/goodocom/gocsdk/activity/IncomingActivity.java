package com.goodocom.gocsdk.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.db.GocDatabase;
import com.goodocom.gocsdk.event.CurrentNumberEvent;
import com.goodocom.gocsdk.event.HfpStatusEvent;
import com.goodocom.gocsdk.manager.GocThreadPoolFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class IncomingActivity extends HfpCallBaseActivity implements View.OnClickListener {
    public static final String TAG = IncomingActivity.class.getSimpleName();
    public static boolean running = false;
    private String currentNumber = "";
    private int currentState = -1;
    private boolean isMute = true;
    private ImageView iv_connect;
    private ImageView iv_hangup;
    private ImageView iv_hangup_active;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        /* class com.goodocom.gocsdk.activity.IncomingActivity.AnonymousClass2 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("calling", "OnCallStateChangedListener_STATE_IDLE" + action);
            if (!TextUtils.isEmpty(action) && action.equals("call_idle")) {
                IncomingActivity.this.finish();
            }
        }
    };
    private ImageView mMuteView;
    private RelativeLayout macc_hang;
    private ImageView mswitchImage;
    private TextView mtime;
    private TextView tv_incoming_name;
    private TextView tv_incoming_number;
    private TextView tv_incoming_status;
    private boolean volume_flag = false;

    public void updateNumber() {
        if (!TextUtils.isEmpty(this.currentNumber)) {
            this.tv_incoming_number.setText(this.currentNumber);
            String name = GocDatabase.getDefault().getNameByNumber(this.currentNumber);
            if (!TextUtils.isEmpty(name)) {
                this.tv_incoming_name.setText(name);
            } else {
                this.tv_incoming_name.setText("未知联系人");
            }
        }
    }

    public void regist() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("call_idle");
        registerReceiver(this.mBroadcastReceiver, filter);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);
        Log.d("app", "IncomingActivity onCreate");
        this.currentNumber = getIntent().getStringExtra(GocDatabase.COL_NUMBER);
        this.tv_incoming_name = (TextView) findViewById(R.id.tv_incoming_name);
        this.tv_incoming_number = (TextView) findViewById(R.id.tv_incoming_number);
        this.iv_connect = (ImageView) findViewById(R.id.iv_connect);
        this.iv_hangup = (ImageView) findViewById(R.id.iv_hangup);
        this.iv_hangup_active = (ImageView) findViewById(R.id.iv_hangup_avtive);
        this.macc_hang = (RelativeLayout) findViewById(R.id.acc_hang);
        this.tv_incoming_status = (TextView) findViewById(R.id.tv_incoming_status);
        this.mtime = (TextView) findViewById(R.id.time);
        this.mswitchImage = (ImageView) findViewById(R.id.iv_qieshengdao_incoming);
        this.mMuteView = (ImageView) findViewById(R.id.iv_bujingyin_incoming);
        this.mtime.setVisibility(8);
        this.iv_connect.setOnClickListener(this);
        this.iv_hangup.setOnClickListener(this);
        this.iv_hangup_active.setOnClickListener(this);
        this.mMuteView.setOnClickListener(this);
        this.mswitchImage.setOnClickListener(this);
        regist();
        updateNumber();
        EventBus.getDefault().register(this);
        running = true;
        this.currentNumber = getIntent().getStringExtra(GocDatabase.COL_NUMBER);
        Log.d(NotificationCompat.CATEGORY_CALL, "initData currentNumber:" + this.currentNumber);
        String str = this.currentNumber;
        if (str != null) {
            this.tv_incoming_number.setText(str);
        }
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdk.activity.IncomingActivity.AnonymousClass1 */

            @Override // java.lang.Runnable
            public void run() {
                final String name = HfpCallBaseActivity.getContactName(IncomingActivity.this.currentNumber, IncomingActivity.this.getContentResolver());
                IncomingActivity.this.mHanlder.post(new Runnable() {
                    /* class com.goodocom.gocsdk.activity.IncomingActivity.AnonymousClass1.AnonymousClass1 */

                    @Override // java.lang.Runnable
                    public void run() {
                        IncomingActivity.this.tv_incoming_name.setText(name);
                    }
                });
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(HfpStatusEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CurrentNumberEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity, android.app.Activity
    public void onDestroy() {
        Log.d("app", "IncomingActivity onDestroy!");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        unregisterReceiver(this.mBroadcastReceiver);
        running = false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bujingyin_incoming /* 2131165349 */:
                switchMute();
                return;
            case R.id.iv_connect /* 2131165355 */:
                try {
                    GocJar.requestHfpAnswerCall(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress(), 0);
                    return;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.iv_hangup /* 2131165362 */:
                hangupInComing();
                return;
            case R.id.iv_hangup_avtive /* 2131165363 */:
                try {
                    GocJar.requestHfpTerminateCurrentCall(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress());
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
                finish();
                return;
            case R.id.iv_qieshengdao_incoming /* 2131165378 */:
                switchCarAndphone();
                return;
            default:
                return;
        }
    }

    private void switchMute() {
        Handler handler = MainActivity.getHandler();
        this.isMute = !this.isMute;
        if (this.isMute) {
            handler.sendEmptyMessage(20);
            this.mMuteView.setImageResource(R.drawable.btn_jianpan_bujingyin_selector);
            try {
                GocJar.muteHfpMic(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            handler.sendEmptyMessage(19);
            this.mMuteView.setImageResource(R.drawable.btn_jianpan_jingyin_selector);
            try {
                GocJar.muteHfpMic(false);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void switchCarAndphone() {
        this.volume_flag = !this.volume_flag;
        if (this.volume_flag) {
            try {
                GocJar.requestHfpAudioTransferToPhone(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            Toast.makeText(this, "手机端", 0).show();
            return;
        }
        try {
            GocJar.requestHfpAudioTransferToCarkit(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress());
        } catch (RemoteException e12) {
            e12.printStackTrace();
        }
        Toast.makeText(this, "车机端", 0).show();
    }

    private void hangupInComing() {
        try {
            GocJar.requestHfpRejectIncomingCall(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
    }

    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity
    public void onCallStateChanged(String s, final GocHfpClientCall gocHfpClientCall) {
        super.onCallStateChanged(s, gocHfpClientCall);
        String str = TAG;
        Log.e(str, "s : " + s + "   gocHfpClientCall  " + gocHfpClientCall.getNumber() + "    " + gocHfpClientCall.isOutgoing() + "   " + gocHfpClientCall.getState() + "    " + gocHfpClientCall.isMultiParty());
        this.currentState = gocHfpClientCall.getState();
        this.currentNumber = gocHfpClientCall.getNumber();
        int state = gocHfpClientCall.getState();
        if (state == 0) {
            this.mHanlder.post(new Runnable() {
                /* class com.goodocom.gocsdk.activity.IncomingActivity.AnonymousClass3 */

                @Override // java.lang.Runnable
                public void run() {
                    IncomingActivity.this.tv_incoming_status.setText("接通");
                    IncomingActivity.this.macc_hang.setVisibility(8);
                    IncomingActivity.this.iv_hangup_active.setVisibility(0);
                    IncomingActivity.this.tv_incoming_number.setText(gocHfpClientCall.getNumber());
                    IncomingActivity.this.mMuteView.setVisibility(0);
                    IncomingActivity.this.mswitchImage.setVisibility(0);
                }
            });
        } else if (state == 4) {
            this.mHanlder.post(new Runnable() {
                /* class com.goodocom.gocsdk.activity.IncomingActivity.AnonymousClass4 */

                @Override // java.lang.Runnable
                public void run() {
                    String str = IncomingActivity.TAG;
                    Log.e(str, "-----------------gocHfpClientCall.getNumber() " + gocHfpClientCall.getNumber());
                    IncomingActivity.this.tv_incoming_number.setText(gocHfpClientCall.getNumber());
                }
            });
        } else if (state == 7) {
            finish();
        }
    }

    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity
    public void onHfpCallingTime(final String s) {
        super.onHfpCallingTime(s);
        this.mHanlder.post(new Runnable() {
            /* class com.goodocom.gocsdk.activity.IncomingActivity.AnonymousClass5 */

            @Override // java.lang.Runnable
            public void run() {
                if (IncomingActivity.this.mtime != null) {
                    IncomingActivity.this.mtime.setVisibility(0);
                    IncomingActivity.this.mtime.setText(s);
                }
            }
        });
    }
}
