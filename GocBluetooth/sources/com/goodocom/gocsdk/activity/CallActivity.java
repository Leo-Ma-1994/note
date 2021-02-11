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
import android.widget.LinearLayout;
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

public class CallActivity extends HfpCallBaseActivity implements View.OnClickListener {
    public static final String TAG = CallActivity.class.getSimpleName();
    public static boolean running = false;
    private String currentNumber = " ";
    private int currentStatus = 1;
    private boolean isMute = true;
    private boolean isShowNumber = false;
    private ImageView iv_bujingyin;
    private ImageView iv_guaduan;
    private ImageView iv_number;
    private ImageView iv_qieshengdao;
    private LinearLayout ll_number;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        /* class com.goodocom.gocsdk.activity.CallActivity.AnonymousClass4 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("calling", "OnCallStateChangedListener_STATE_IDLE" + action);
            if (!TextUtils.isEmpty(action)) {
            }
        }
    };
    private TextView mCallName;
    private TextView mCallNumber;
    private TextView mCallTime;
    private boolean volume_flag = false;

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        regist();
        initView();
        initData();
        EventBus.getDefault().register(this);
        running = true;
    }

    public void regist() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("call_idle");
        registerReceiver(this.mBroadcastReceiver, filter);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity, android.app.Activity
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        unregisterReceiver(this.mBroadcastReceiver);
        running = false;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CurrentNumberEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(HfpStatusEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    private void onTalking() {
        this.mHanlder.post(new Runnable() {
            /* class com.goodocom.gocsdk.activity.CallActivity.AnonymousClass1 */

            @Override // java.lang.Runnable
            public void run() {
                CallActivity.this.mCallNumber.setText(CallActivity.this.currentNumber);
                CallActivity.this.mCallTime.setVisibility(0);
            }
        });
    }

    private void onCalling() {
        String str = TAG;
        Log.e(str, "onCalling : currentNumber " + this.currentNumber);
        this.mHanlder.post(new Runnable() {
            /* class com.goodocom.gocsdk.activity.CallActivity.AnonymousClass2 */

            @Override // java.lang.Runnable
            public void run() {
                CallActivity.this.mCallTime.setVisibility(8);
                CallActivity.this.mCallName.setText("unknow");
                CallActivity.this.mCallNumber.setText(CallActivity.this.currentNumber);
            }
        });
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdk.activity.CallActivity.AnonymousClass3 */

            @Override // java.lang.Runnable
            public void run() {
                final String name = HfpCallBaseActivity.getContactName(CallActivity.this.currentNumber, CallActivity.this.getContentResolver());
                CallActivity.this.mHanlder.post(new Runnable() {
                    /* class com.goodocom.gocsdk.activity.CallActivity.AnonymousClass3.AnonymousClass1 */

                    @Override // java.lang.Runnable
                    public void run() {
                        CallActivity.this.mCallName.setText(name);
                    }
                });
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        this.currentNumber = intent.getStringExtra(GocDatabase.COL_NUMBER);
        Log.d(NotificationCompat.CATEGORY_CALL, "initData currentNumber:" + this.currentNumber);
        this.currentStatus = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, 0);
        onCalling();
    }

    private void initView() {
        this.ll_number = (LinearLayout) findViewById(R.id.ll_numpad);
        this.iv_number = (ImageView) findViewById(R.id.iv_number);
        this.iv_guaduan = (ImageView) findViewById(R.id.iv_guaduan);
        this.iv_qieshengdao = (ImageView) findViewById(R.id.iv_qieshengdao);
        this.iv_bujingyin = (ImageView) findViewById(R.id.iv_bujingyin);
        this.mCallName = (TextView) findViewById(R.id.call_name);
        this.mCallNumber = (TextView) findViewById(R.id.call_number);
        this.mCallTime = (TextView) findViewById(R.id.call_time);
        findViewById(R.id.iv_one).setOnClickListener(this);
        findViewById(R.id.iv_two).setOnClickListener(this);
        findViewById(R.id.iv_three).setOnClickListener(this);
        findViewById(R.id.iv_four).setOnClickListener(this);
        findViewById(R.id.iv_five).setOnClickListener(this);
        findViewById(R.id.iv_six).setOnClickListener(this);
        findViewById(R.id.iv_seven).setOnClickListener(this);
        findViewById(R.id.iv_eight).setOnClickListener(this);
        findViewById(R.id.iv_nine).setOnClickListener(this);
        findViewById(R.id.iv_xinghao).setOnClickListener(this);
        findViewById(R.id.iv_zero).setOnClickListener(this);
        findViewById(R.id.iv_jinghao).setOnClickListener(this);
        this.iv_number.setOnClickListener(this);
        this.iv_guaduan.setOnClickListener(this);
        this.iv_qieshengdao.setOnClickListener(this);
        this.iv_bujingyin.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bujingyin /* 2131165348 */:
                switchMute();
                return;
            case R.id.iv_eight /* 2131165357 */:
                phoneDTMFCode('8');
                return;
            case R.id.iv_five /* 2131165358 */:
                phoneDTMFCode('5');
                return;
            case R.id.iv_four /* 2131165360 */:
                phoneDTMFCode('4');
                return;
            case R.id.iv_guaduan /* 2131165361 */:
                if (GocAppData.getInstance().mCurrentBluetoothDevice != null) {
                    try {
                        String str = TAG;
                        Log.e(str, "hangup : " + GocAppData.getInstance().mCurrentBluetoothDevice.getAddress());
                        GocJar.requestHfpTerminateCurrentCall(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                running = false;
                finish();
                return;
            case R.id.iv_jinghao /* 2131165364 */:
                phoneDTMFCode('#');
                return;
            case R.id.iv_nine /* 2131165368 */:
                phoneDTMFCode('9');
                return;
            case R.id.iv_number /* 2131165369 */:
                showNumber();
                return;
            case R.id.iv_one /* 2131165370 */:
                phoneDTMFCode('1');
                return;
            case R.id.iv_qieshengdao /* 2131165377 */:
                switchCarAndphone();
                return;
            case R.id.iv_seven /* 2131165383 */:
                phoneDTMFCode('7');
                return;
            case R.id.iv_six /* 2131165384 */:
                phoneDTMFCode('6');
                return;
            case R.id.iv_three /* 2131165385 */:
                phoneDTMFCode('3');
                return;
            case R.id.iv_two /* 2131165386 */:
                phoneDTMFCode('2');
                return;
            case R.id.iv_xinghao /* 2131165389 */:
                phoneDTMFCode('*');
                return;
            case R.id.iv_zero /* 2131165390 */:
                phoneDTMFCode('0');
                return;
            default:
                return;
        }
    }

    private void phoneDTMFCode(char code) {
        try {
            GocJar.requestHfpSendDtmf(GocAppData.getInstance().mCurrentBluetoothDevice.getAddress(), String.valueOf(code));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void switchMute() {
        Handler handler = MainActivity.getHandler();
        this.isMute = !this.isMute;
        if (this.isMute) {
            handler.sendEmptyMessage(20);
            this.iv_bujingyin.setImageResource(R.drawable.btn_jianpan_bujingyin_selector);
            try {
                GocJar.muteHfpMic(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            handler.sendEmptyMessage(19);
            this.iv_bujingyin.setImageResource(R.drawable.btn_jianpan_jingyin_selector);
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

    private void showNumber() {
        this.isShowNumber = !this.isShowNumber;
        if (this.isShowNumber) {
            this.ll_number.setVisibility(0);
        } else {
            this.ll_number.setVisibility(8);
        }
    }

    private void hangUp() {
    }

    @Override // android.app.Activity
    public void onBackPressed() {
    }

    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity
    public void onCallStateChanged(String s, GocHfpClientCall gocHfpClientCall) {
        super.onCallStateChanged(s, gocHfpClientCall);
        if (gocHfpClientCall != null) {
            String str = TAG;
            Log.e(str, "s : " + s + "   gocHfpClientCall  " + gocHfpClientCall.getNumber() + "    " + gocHfpClientCall.isOutgoing() + "   " + gocHfpClientCall.getState() + "    " + gocHfpClientCall.isMultiParty());
            this.currentNumber = gocHfpClientCall.getNumber();
            int state = gocHfpClientCall.getState();
            if (state == 0) {
                Log.e(TAG, "onTalking ");
                onTalking();
            } else if (state == 2) {
                Log.e(TAG, "CALL_STATE_DIALING ");
                this.currentNumber = gocHfpClientCall.getNumber();
                String str2 = TAG;
                Log.e(str2, "CALL_STATE_DIALING " + this.currentNumber);
                onCalling();
            } else if (state == 7) {
                finish();
            }
        }
    }

    @Override // com.goodocom.gocsdk.activity.HfpCallBaseActivity
    public void onHfpCallingTime(final String s) {
        super.onHfpCallingTime(s);
        this.mHanlder.post(new Runnable() {
            /* class com.goodocom.gocsdk.activity.CallActivity.AnonymousClass5 */

            @Override // java.lang.Runnable
            public void run() {
                if (CallActivity.this.mCallTime != null) {
                    CallActivity.this.mCallTime.setText(s);
                }
            }
        });
    }
}
