package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackHid;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;

public class HidPageActivity extends Activity {
    private static boolean D = true;
    private static final int HANDLER_EVENT_SHOW_TOAST = 2;
    private static final int HANDLER_EVENT_UPDATE_STATE_HID = 1;
    private static String STATE_HID = "STATE_HID";
    private static String TAG = "NfDemo_HidPage";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    final int DEFAULT_MAX_SCREEN_HEIGHT = 1980;
    final int DEFAULT_MAX_SCREEN_WIDTH = 1080;
    final int MAX_CLICK_INTERVAL = 600;
    final int MIN_MOUSE_MOVE_INTERVAL = 200;
    boolean btnLeft = false;
    boolean btnRight = false;
    private HidDialogVirtualKeys dialog;
    private String hid_address;
    private Toast infoToast;
    private UiCallbackHid mCallbackHid = new UiCallbackHid.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass11 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHid
        public void onHidServiceReady() throws RemoteException {
            Log.i(HidPageActivity.TAG, "onHidServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHid
        public void onHidStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
            String str = HidPageActivity.TAG;
            Log.i(str, "onHidStateChanged " + address + " state: " + prevState + "->" + newState + " ,reason: " + HidPageActivity.this.dumpReasonName(reason));
            Message msg = Message.obtain(HidPageActivity.this.mHandler, 1);
            Bundle b = new Bundle();
            b.putInt("STATE_HID", HidPageActivity.this.mCommand.getHidConnectionState());
            msg.setData(b);
            HidPageActivity.this.mHandler.sendMessage(msg);
        }
    };
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass12 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(HidPageActivity.TAG, "ready  onServiceConnected");
            HidPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (HidPageActivity.this.mCommand == null) {
                Log.e(HidPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(HidPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                HidPageActivity.this.finish();
            }
            try {
                HidPageActivity.this.mCommand.registerHidCallback(HidPageActivity.this.mCallbackHid);
                String str = HidPageActivity.TAG;
                Log.e(str, "Piggy Check Hid getState() : " + HidPageActivity.this.mCommand.getHidConnectionState());
                String str2 = HidPageActivity.TAG;
                Log.e(str2, "get target address: " + HidPageActivity.this.mCommand.getTargetAddress());
                HidPageActivity.this.hid_address = HidPageActivity.this.mCommand.getTargetAddress();
                if (HidPageActivity.this.hid_address.equals(NfDef.DEFAULT_ADDRESS)) {
                    HidPageActivity.this.showToast("Choose a device first !");
                }
                String str3 = HidPageActivity.TAG;
                Log.e(str3, "hid current state: " + HidPageActivity.this.mCommand.getHidConnectionState());
                Message msg = Message.obtain(HidPageActivity.this.mHandler, 1);
                Bundle b = new Bundle();
                b.putInt(HidPageActivity.STATE_HID, HidPageActivity.this.mCommand.getHidConnectionState());
                msg.setData(b);
                HidPageActivity.this.mHandler.sendMessage(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(HidPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(HidPageActivity.TAG, "onServiceDisconnected");
            HidPageActivity.this.mCommand = null;
        }
    };
    private Context mContext;
    private Handler mHandler = null;
    long mLastDownTime = 0;
    long mLastTime = 0;
    MyTouchPad mPad;
    int mX = 0;
    int mY = 0;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.hid_page);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initUi();
        getIntent();
        initHandler();
        this.mContext = this;
        setTitle("HID");
        Intent intent = new Intent(this, BtService.class);
        startService(intent);
        bindService(intent, this.mConnection, 1);
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        if (D) {
            Log.e(TAG, "++ ON START ++");
        }
    }

    @Override // android.app.Activity
    public synchronized void onResume() {
        super.onResume();
        if (D) {
            Log.e(TAG, "++ ON Resume ++");
        }
    }

    /* access modifiers changed from: package-private */
    public String stateToString(int state) {
        if (state == 110) {
            return "disconnected";
        }
        if (state == 120) {
            return "connecting";
        }
        if (state != 140) {
            return "disconnecting";
        }
        return "connected";
    }

    /* access modifiers changed from: package-private */
    public void deviceStateChanged(String address, int state) {
        String str = TAG;
        Log.e(str, "Mars check deviceStateChanged: " + state);
        String text = stateToString(state);
        ((TextView) findViewById(R.id.tvStatus)).setText(address + " is " + text);
        invalidateOptionsMenu();
    }

    @Override // android.app.Activity
    public synchronized void onPause() {
        super.onPause();
        if (D) {
            Log.e(TAG, "- ON PAUSE -");
        }
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        if (D) {
            Log.e(TAG, "-- ON STOP --");
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        try {
            this.mCommand.unregisterHidCallback(this.mCallbackHid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(this.mConnection);
        this.mHandler = null;
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        super.onDestroy();
    }

    public void setCoordinates(int x, int y, int action) {
        String text;
        TextView tvx = (TextView) findViewById(R.id.xCoord);
        TextView tvy = (TextView) findViewById(R.id.yCoord);
        tvx.setText(Integer.toString(x));
        tvy.setText(Integer.toString(y));
        TextView tva = (TextView) findViewById(R.id.tvAction);
        if (action == 0) {
            text = "down";
        } else if (action == 1) {
            text = "up";
        } else if (action != 2) {
            text = action != 8 ? BuildConfig.FLAVOR : "scroll";
        } else {
            text = "move";
        }
        long now = SystemClock.elapsedRealtime();
        if (action == 2) {
            if (now - this.mLastTime < 200) {
                return;
            }
        } else if (action == 0) {
            this.dialog.dismiss();
            if (now - this.mLastDownTime < 600) {
                this.btnLeft = true;
                updateButtonsUi();
            } else {
                try {
                    if (!this.mCommand.reqSendHidMouseCommand(0, -3000, -3000, 0)) {
                        Log.e(TAG, "reqSendHidMouseCommand failed");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                this.mX = 0;
                this.mY = 0;
                this.mLastDownTime = now;
                return;
            }
        } else if (action == 1 && this.btnLeft) {
            this.btnLeft = false;
            updateButtonsUi();
        }
        this.mLastTime = now;
        tva.setText(text);
        int btn = 0;
        if (this.btnLeft) {
            btn = 0 + 1;
        }
        if (this.btnRight) {
            btn += 2;
        }
        try {
            if (!this.mCommand.reqSendHidMouseCommand(btn, x - this.mX, y - this.mY, 0)) {
                Log.e(TAG, "reqSendHidMouseCommand failed");
            }
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
        this.mX = x;
        this.mY = y;
    }

    /* access modifiers changed from: package-private */
    public void changeScreenMap() {
        final TextView tvx = (TextView) findViewById(R.id.tvXmax);
        final TextView tvy = (TextView) findViewById(R.id.tvYmax);
        View promptView = LayoutInflater.from(this).inflate(R.layout.prompt_change_map, (ViewGroup) null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Change remote screen coordinates");
        alertDialogBuilder.setView(promptView);
        final EditText inputX = (EditText) promptView.findViewById(R.id.xmax);
        inputX.setText(Integer.toString(this.mPad.getXmax()));
        final EditText inputY = (EditText) promptView.findViewById(R.id.ymax);
        inputY.setText(Integer.toString(this.mPad.getYmax()));
        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass1 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id) {
                TextView textView = tvx;
                textView.setText("X-Max: " + ((Object) inputX.getText()));
                TextView textView2 = tvy;
                textView2.setText("Y-Max: " + ((Object) inputY.getText()));
                HidPageActivity.this.mPad.setMappedScreenDimension(Integer.valueOf(inputX.getText().toString()).intValue(), Integer.valueOf(inputY.getText().toString()).intValue());
            }
        });
        alertDialogBuilder.create().show();
    }

    public void startVirtualKeys() {
        this.dialog.show();
    }

    /* access modifiers changed from: package-private */
    public void updateButtonsUi() {
        Button bt1 = (Button) findViewById(R.id.keyLeft);
        Button bt2 = (Button) findViewById(R.id.keyRight);
        bt1.setText(this.btnLeft ? "<<Left>>" : "Left");
        bt2.setText(this.btnRight ? "<<Right>>" : "Right");
    }

    /* access modifiers changed from: package-private */
    public void initUi() {
        this.mPad = (MyTouchPad) findViewById(R.id.canvas);
        MyTouchPad myTouchPad = this.mPad;
        myTouchPad.mParent = this;
        myTouchPad.setMappedScreenDimension(1080, 1980);
        TextView tvx = (TextView) findViewById(R.id.tvXmax);
        tvx.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                HidPageActivity.this.dialog.dismiss();
                HidPageActivity.this.changeScreenMap();
            }
        });
        TextView tvy = (TextView) findViewById(R.id.tvYmax);
        tvy.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                HidPageActivity.this.dialog.dismiss();
                HidPageActivity.this.changeScreenMap();
            }
        });
        tvx.setText("X-Max: " + Integer.toString(this.mPad.getXmax()));
        tvy.setText("Y-Max: " + Integer.toString(this.mPad.getYmax()));
        ((Button) findViewById(R.id.keyLeft)).setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                HidPageActivity.this.dialog.dismiss();
                HidPageActivity hidPageActivity = HidPageActivity.this;
                hidPageActivity.btnLeft = !hidPageActivity.btnLeft;
                HidPageActivity.this.updateButtonsUi();
                HidPageActivity hidPageActivity2 = HidPageActivity.this;
                hidPageActivity2.setCoordinates(hidPageActivity2.mX, HidPageActivity.this.mY, 2);
            }
        });
        ((Button) findViewById(R.id.keyRight)).setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                HidPageActivity.this.dialog.dismiss();
                HidPageActivity hidPageActivity = HidPageActivity.this;
                hidPageActivity.btnRight = !hidPageActivity.btnRight;
                HidPageActivity.this.updateButtonsUi();
                HidPageActivity hidPageActivity2 = HidPageActivity.this;
                hidPageActivity2.setCoordinates(hidPageActivity2.mX, HidPageActivity.this.mY, 2);
            }
        });
        ((Button) findViewById(R.id.keyVirtual)).setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                HidPageActivity.this.startVirtualKeys();
            }
        });
        ((Button) findViewById(R.id.keyConnect)).setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HidPageActivity.TAG, "bt_connect clicked");
                try {
                    HidPageActivity.this.mCommand.reqHidConnect(HidPageActivity.this.hid_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) findViewById(R.id.keyDisconnect)).setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HidPageActivity.TAG, "bt_disconnect clicked");
                try {
                    HidPageActivity.this.mCommand.reqHidDisconnect(HidPageActivity.this.hid_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) findViewById(R.id.keyBack)).setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HidPageActivity.TAG, "bt_back clicked");
                HidPageActivity.this.finish();
            }
        });
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.HidPageActivity.AnonymousClass10 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String str = HidPageActivity.TAG;
                Log.v(str, "handleMessage : " + HidPageActivity.this.getHandlerEventName(msg.what));
                int i = msg.what;
                if (i == 1) {
                    String str2 = HidPageActivity.TAG;
                    Log.e(str2, "STATE_HID: " + bundle.getInt(HidPageActivity.STATE_HID));
                    HidPageActivity.this.deviceStateChanged(HidPageActivity.this.hid_address, bundle.getInt(HidPageActivity.STATE_HID));
                    HidPageActivity.this.dialog = new HidDialogVirtualKeys(HidPageActivity.this.mContext, HidPageActivity.this.mCommand);
                } else if (i == 2) {
                    if (HidPageActivity.this.infoToast != null) {
                        HidPageActivity.this.infoToast.cancel();
                        HidPageActivity.this.infoToast.setText(bundle.getString(HidPageActivity.TOAST_MESSAGE));
                        HidPageActivity.this.infoToast.setDuration(0);
                    } else {
                        HidPageActivity.this.infoToast = Toast.makeText(HidPageActivity.this.getApplicationContext(), bundle.getString(HidPageActivity.TOAST_MESSAGE), 0);
                    }
                    HidPageActivity.this.infoToast.show();
                }
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String dumpReasonName(int reason) {
        if (reason == 0) {
            return "NO Problem";
        }
        if (reason == 904) {
            return "HID_DISCONNECT_BY_REMOTE";
        }
        switch (reason) {
            case NfDef.ERROR_LOCAL_ADDRESS_NULL /* 706 */:
                return "ERROR_LOCAL_ADDRESS_NULL";
            case NfDef.ERROR_REMOTE_ADDRESS_NULL /* 707 */:
                return "ERROR_REMOTE_ADDRESS_NULL";
            case NfDef.ERROR_HID_CONNECT_FAIL /* 708 */:
                return "ERROR_HID_CONNECT_FAIL";
            case NfDef.ERROR_HID_ACCEPT_FAIL /* 709 */:
                return "ERROR_HID_ACCEPT_FAIL";
            case NfDef.ERROR_HID_DISCONNECT_FAIL /* 710 */:
                return "ERROR_HID_DISCONNECT_FAIL";
            default:
                return "unknown Reason";
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String message) {
        String str = TAG;
        Log.e(str, "showToast : " + message);
        sendHandlerMessage(2, new String[]{TOAST_MESSAGE}, new String[]{message});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getHandlerEventName(int what) {
        if (what != 2) {
            return "Unknown Event !!";
        }
        return "HANDLER_EVENT_SHOW_TOAST";
    }

    private void sendHandlerMessage(int what, String[] keys, String[] values) {
        Message msg = Message.obtain(this.mHandler, what);
        if (!(keys == null || values == null)) {
            Bundle b = new Bundle();
            for (int i = 0; i < keys.length; i++) {
                b.putString(keys[i], values[i]);
            }
            msg.setData(b);
        }
        this.mHandler.sendMessage(msg);
    }
}
