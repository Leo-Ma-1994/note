package com.goodocom.gocsdk.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.gocsdk.IGocsdkService;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.db.GocDatabase;
import com.goodocom.gocsdk.event.BackgroundCurrentNumberEvent;
import com.goodocom.gocsdk.event.BackgroundHfpStatusEvent;
import com.goodocom.gocsdk.service.GocsdkService;
import com.goodocom.gocsdk.view.TransparentCallDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TransparentActivity extends Activity implements View.OnClickListener {
    public static final int MSG_BLUETOOTH_DISCONNECTED = 2;
    public static final int MSG_CONNECTION_PHONE = 1;
    public static final int MSG_HANGUP_PHONE = 0;
    public static final int MSG_HFP_STATUS = 3;
    public static volatile boolean running = false;
    private Chronometer chronometer;
    private MyConn conn;
    private String currentNumber;
    private int currentStatus;
    private TransparentCallDialog dialog;
    private Intent gocsdkService;
    private IGocsdkService iGocsdkService;
    private ImageButton ibt_accept;
    private ImageButton ibt_reject;
    private TextView tv_name;
    private TextView tv_number;
    private TextView tv_status;
    private boolean volume_flag = false;

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notallhas);
        Intent intent = getIntent();
        this.currentStatus = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, 0);
        this.currentNumber = intent.getStringExtra(GocDatabase.COL_NUMBER);
        createDialog();
        this.gocsdkService = new Intent(this, GocsdkService.class);
        this.conn = new MyConn();
        bindService(this.gocsdkService, this.conn, 1);
        EventBus.getDefault().register(this);
        running = true;
    }

    public static void start(Context context, int status, String number) {
        if (!running) {
            running = true;
            Intent intent = new Intent(context, TransparentActivity.class);
            intent.putExtra(NotificationCompat.CATEGORY_STATUS, status);
            intent.putExtra(GocDatabase.COL_NUMBER, number);
            intent.setFlags(270532608);
            context.startActivity(intent);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(BackgroundHfpStatusEvent event) {
        if (event.status <= 3) {
            this.chronometer.stop();
            this.chronometer.setVisibility(8);
            this.dialog.dismiss();
            finish();
        } else if (event.status == 6) {
            this.ibt_accept.setImageResource(R.drawable.btn_little_qieshengdao_selector);
            this.tv_status.setText("正在通话：");
            this.chronometer.setVisibility(0);
            this.chronometer.setFormat("%s");
            this.chronometer.setBase(SystemClock.elapsedRealtime());
            this.chronometer.start();
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(BackgroundCurrentNumberEvent event) {
        this.currentNumber = event.number;
        updateNumber();
        EventBus.getDefault().removeStickyEvent(event);
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onDestroy() {
        unbindService(this.conn);
        EventBus.getDefault().unregister(this);
        running = false;
        super.onDestroy();
    }

    private void createDialog() {
        this.dialog = new TransparentCallDialog(this, R.style.transparentdialog);
        this.dialog.setCanceledOnTouchOutside(false);
        initDialogView();
        initDialogData();
        this.dialog.show();
    }

    private void updateNumber() {
        if (!TextUtils.isEmpty(this.currentNumber)) {
            String name = GocDatabase.getDefault().getNameByNumber(this.currentNumber);
            if (TextUtils.isEmpty(name)) {
                this.tv_name.setText("未知联系人");
            } else {
                this.tv_name.setText(name);
            }
            this.tv_number.setText(this.currentNumber);
        }
    }

    private void initDialogData() {
        int i = this.currentStatus;
        if (i == 4) {
            this.tv_status.setText("去电...");
        } else if (i == 5) {
            this.tv_status.setText("来电...");
        } else if (i == 6) {
            this.ibt_accept.setImageResource(R.drawable.btn_little_qieshengdao_selector);
            this.tv_status.setText("正在通话：");
            this.chronometer.setVisibility(0);
            this.chronometer.setFormat("%s");
            this.chronometer.setBase(SystemClock.elapsedRealtime());
            this.chronometer.start();
        }
        updateNumber();
    }

    private void initDialogView() {
        View customView = this.dialog.getCustomView();
        this.tv_name = (TextView) customView.findViewById(R.id.incoming_name);
        this.tv_number = (TextView) customView.findViewById(R.id.incoming_number);
        this.tv_status = (TextView) customView.findViewById(R.id.tv_phone_incoming);
        this.ibt_accept = (ImageButton) customView.findViewById(R.id.ibt_accept_or_switch);
        this.ibt_reject = (ImageButton) customView.findViewById(R.id.ibt_reject);
        this.chronometer = (Chronometer) customView.findViewById(R.id.chronometer_incoming);
        this.ibt_accept.setOnClickListener(this);
        this.ibt_reject.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibt_accept_or_switch /* 2131165332 */:
                if (this.currentStatus == 6) {
                    switchCarAndphone();
                    return;
                }
                try {
                    this.iGocsdkService.phoneAnswer();
                    return;
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                    return;
                }
            case R.id.ibt_reject /* 2131165333 */:
                try {
                    this.iGocsdkService.phoneHangUp();
                    return;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    private void switchCarAndphone() {
        this.volume_flag = !this.volume_flag;
        if (this.volume_flag) {
            try {
                this.iGocsdkService.phoneTransfer();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            Toast.makeText(this, "手机端", 0).show();
            return;
        }
        try {
            this.iGocsdkService.phoneTransferBack();
        } catch (RemoteException e12) {
            e12.printStackTrace();
        }
        Toast.makeText(this, "车机端", 0).show();
    }

    private class MyConn implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public /* synthetic */ void onBindingDied(ComponentName componentName) {
            ServiceConnection.-CC.$default$onBindingDied(this, componentName);
        }

        @Override // android.content.ServiceConnection
        public /* synthetic */ void onNullBinding(ComponentName componentName) {
            ServiceConnection.-CC.$default$onNullBinding(this, componentName);
        }

        private MyConn() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            TransparentActivity.this.iGocsdkService = IGocsdkService.Stub.asInterface(service);
            try {
                TransparentActivity.this.iGocsdkService.inqueryHfpStatus();
                TransparentActivity.this.iGocsdkService.getLocalName();
                TransparentActivity.this.iGocsdkService.getPinCode();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
    }
}
