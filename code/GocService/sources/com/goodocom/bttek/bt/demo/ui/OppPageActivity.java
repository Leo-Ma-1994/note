package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
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
import com.goodocom.bttek.bt.aidl.UiCallbackOpp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;

public class OppPageActivity extends Activity {
    private static boolean D = true;
    private static String DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private static String DEVICE_NAME = "DEVICE_NAME";
    private static final int HANDLER_EVENT_SHOW_TOAST = 1;
    private static final int HANDLER_EVENT_UPDATE_DEVICE_ADDRESS = 2;
    private static final int HANDLER_EVENT_UPDATE_DEVICE_NAME = 3;
    private static final int HANDLER_EVENT_UPDATE_RECEIVE_FILE_NAML = 6;
    private static final int HANDLER_EVENT_UPDATE_RECEIVE_STATUS = 5;
    private static final int HANDLER_EVENT_UPDATE_STATE_OPP = 4;
    private static String RECEIVE_FILE_NAME = "RECEIVE_FILE_NAME";
    private static String RECEIVE_STATUS = "RECEIVE_STATUS";
    private static String STATE_OPP = "STATE_OPP";
    private static String TAG = "NfDemo_OppPage";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    static UiCommand mCommand;
    static int mOppDownloadProgress = 0;
    private Button button_back;
    private Button button_opp_file_path;
    private Button button_opp_receive_cancel;
    private Button button_opp_receive_confirm;
    private String filePathString = BuildConfig.FLAVOR;
    private Toast infoToast;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass3 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(OppPageActivity.TAG, "ready  onServiceConnected");
            OppPageActivity.mCommand = UiCommand.Stub.asInterface(service);
            if (OppPageActivity.mCommand == null) {
                Log.e(OppPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(OppPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                OppPageActivity.this.finish();
            }
            try {
                OppPageActivity.mCommand.registerOppCallback(OppPageActivity.this.mOppCallback);
                String str = "Ready";
                OppPageActivity.this.sendHandlerMessage(4, OppPageActivity.STATE_OPP, OppPageActivity.this.state_opp == 140 ? "Connected" : str);
                OppPageActivity oppPageActivity = OppPageActivity.this;
                String str2 = OppPageActivity.RECEIVE_STATUS;
                if (OppPageActivity.this.state_opp == 140) {
                    str = "RECEIVE";
                }
                oppPageActivity.sendHandlerMessage(5, str2, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(OppPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(OppPageActivity.TAG, "onServiceDisconnected");
            OppPageActivity.mCommand = null;
        }
    };
    private int mFileSize = 0;
    private Handler mHandler = null;
    private UiCallbackOpp mOppCallback = new UiCallbackOpp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass9 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppServiceReady() throws RemoteException {
            Log.i(OppPageActivity.TAG, "onOppServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppStateChanged(String address, int preState, int currentState, int reason) throws RemoteException {
            if (OppPageActivity.D) {
                String str = OppPageActivity.TAG;
                Log.d(str, "onOppStateChanged " + address + " deviceName : " + address + "  State : " + preState + " -> " + currentState);
            }
            OppPageActivity.this.opp_connected_device_name = address;
            if (currentState == 140) {
                OppPageActivity.this.sendHandlerMessage(4, OppPageActivity.STATE_OPP, "Connected");
                OppPageActivity.this.sendHandlerMessage(5, OppPageActivity.RECEIVE_STATUS, "Receiving");
                OppPageActivity.this.sendHandlerMessage(2, OppPageActivity.DEVICE_ADDRESS, address);
            } else if (currentState < 140) {
                OppPageActivity.this.sendHandlerMessage(4, OppPageActivity.STATE_OPP, "Ready");
                OppPageActivity.this.sendHandlerMessage(5, OppPageActivity.RECEIVE_STATUS, "Ready");
                OppPageActivity.this.sendHandlerMessage(3, OppPageActivity.DEVICE_NAME, BuildConfig.FLAVOR);
                OppPageActivity.this.sendHandlerMessage(2, OppPageActivity.DEVICE_ADDRESS, BuildConfig.FLAVOR);
            }
            OppPageActivity.this.state_opp = currentState;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) throws RemoteException {
            String str = OppPageActivity.TAG;
            Log.v(str, "in onOppReceiveFileInfo : fileSize = " + fileSize);
            OppPageActivity.this.mFileSize = fileSize;
            OppPageActivity.this.sendHandlerMessage(6, OppPageActivity.RECEIVE_FILE_NAME, fileName);
            OppPageActivity.this.sendHandlerMessage(3, OppPageActivity.DEVICE_NAME, deviceName);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveProgress(int receivedOffset) throws RemoteException {
            String str = OppPageActivity.TAG;
            Log.v(str, "in onOppReceiveProgress : receivedOffset = " + receivedOffset);
            OppPageActivity.this.mReceivedOffset = (double) receivedOffset;
            OppPageActivity.mOppDownloadProgress = (int) ((OppPageActivity.this.mReceivedOffset / ((double) OppPageActivity.this.mFileSize)) * 100.0d);
            String str2 = OppPageActivity.TAG;
            Log.v(str2, "in onOppReceiveProgress : mOppDownloadProgress = " + OppPageActivity.mOppDownloadProgress);
        }
    };
    private double mReceivedOffset = 0.0d;
    private String opp_connected_device_name = BuildConfig.FLAVOR;
    private int state_opp = -1;
    private TextView text_opp_connected_address;
    private TextView text_opp_device_name;
    private TextView text_opp_receive_file;
    private TextView text_received_file_name;
    private TextView text_state_opp;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opp_page);
        initView();
        initHandler();
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
            mCommand.unregisterOppCallback(this.mOppCallback);
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

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showInputFilePathDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        try {
            alert.setTitle("Path: " + mCommand.getOppFilePath());
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        View inputView = inflater.inflate(R.layout.activity_opp_page_edittext, (ViewGroup) null);
        alert.setView(inputView);
        final EditText filePath = (EditText) inputView.findViewById(R.id.edittext_opp_file_path);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass1 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                OppPageActivity.this.filePathString = filePath.getText().toString();
                if (OppPageActivity.this.filePathString.length() > 0) {
                    String str = OppPageActivity.TAG;
                    Log.v(str, "in OPP Set File path : " + OppPageActivity.this.filePathString);
                    try {
                        OppPageActivity.mCommand.setOppFilePath(OppPageActivity.this.filePathString);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    OppPageActivity.this.showToast("Please input file path.");
                    Log.v(OppPageActivity.TAG, "in OPP Set File path : didn't input file path");
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass2 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    public void initView() {
        this.text_state_opp = (TextView) findViewById(R.id.text_state_opp);
        this.button_opp_file_path = (Button) findViewById(R.id.button_opp_file_path);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.button_opp_receive_confirm = (Button) findViewById(R.id.button_opp_receive_confirm);
        this.button_opp_receive_cancel = (Button) findViewById(R.id.button_opp_receive_cancel);
        this.text_opp_device_name = (TextView) findViewById(R.id.text_opp_device_name);
        this.text_opp_connected_address = (TextView) findViewById(R.id.text_opp_connected_address);
        this.text_opp_receive_file = (TextView) findViewById(R.id.text_opp_receive_file);
        this.text_received_file_name = (TextView) findViewById(R.id.text_received_file_name);
        this.button_opp_file_path.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(OppPageActivity.TAG, "button_opp_file_path on click");
                OppPageActivity.this.showInputFilePathDialog();
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(OppPageActivity.TAG, "button_back onClicked");
                OppPageActivity.this.finish();
            }
        });
        this.button_opp_receive_confirm.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(OppPageActivity.TAG, "button_opp_receive_confirm on click");
                if (OppPageActivity.this.state_opp == 140) {
                    try {
                        OppPageActivity.mCommand.reqOppAcceptReceiveFile(true);
                        Log.d(OppPageActivity.TAG, "reqOppAcceptReceiveFile : true");
                        Intent newAct = new Intent();
                        newAct.setClass(OppPageActivity.this, OppDownloadProgressActivity.class);
                        OppPageActivity.this.startActivity(newAct);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(OppPageActivity.TAG, "in button_opp_receive_confirm : OPP not connected");
                }
            }
        });
        this.button_opp_receive_cancel.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(OppPageActivity.TAG, "button_opp_receive_cancel on click");
                if (OppPageActivity.this.state_opp == 140) {
                    try {
                        OppPageActivity.mCommand.reqOppAcceptReceiveFile(false);
                        Log.d(OppPageActivity.TAG, "reqOppAcceptReceiveFile : false");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(OppPageActivity.TAG, "in button_opp_receive_cancel : OPP not connected");
                }
            }
        });
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.OppPageActivity.AnonymousClass8 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String str = OppPageActivity.TAG;
                Log.v(str, "handleMessage : " + OppPageActivity.this.getHandlerEventName(msg.what));
                switch (msg.what) {
                    case 1:
                        if (OppPageActivity.this.infoToast != null) {
                            OppPageActivity.this.infoToast.setText(bundle.getString(OppPageActivity.TOAST_MESSAGE));
                            OppPageActivity.this.infoToast.setDuration(0);
                        } else {
                            OppPageActivity.this.infoToast = Toast.makeText(OppPageActivity.this.getApplicationContext(), bundle.getString(OppPageActivity.TOAST_MESSAGE), 0);
                            String str2 = OppPageActivity.TAG;
                            Log.v(str2, "in Handler HANDLER_EVENT_SHOW_TOAST, bundle.getString(TOAST_MESSAGE)" + bundle.getString(OppPageActivity.TOAST_MESSAGE));
                        }
                        OppPageActivity.this.infoToast.show();
                        break;
                    case 2:
                        OppPageActivity.this.text_opp_connected_address.setText(bundle.getString(OppPageActivity.DEVICE_ADDRESS));
                        break;
                    case 3:
                        OppPageActivity.this.text_opp_device_name.setText(bundle.getString(OppPageActivity.DEVICE_NAME));
                        break;
                    case 4:
                        OppPageActivity.this.text_state_opp.setText(bundle.getString(OppPageActivity.STATE_OPP));
                        break;
                    case 5:
                        OppPageActivity.this.text_opp_receive_file.setText(bundle.getString(OppPageActivity.RECEIVE_STATUS));
                        break;
                    case 6:
                        OppPageActivity.this.text_received_file_name.setText(bundle.getString(OppPageActivity.RECEIVE_FILE_NAME));
                        break;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String message) {
        String str = TAG;
        Log.e(str, "showToast : " + message);
        sendHandlerMessage(1, TOAST_MESSAGE, message);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendHandlerMessage(int what, String key, String value) {
        Message msg = Message.obtain(this.mHandler, what);
        if (!(key == null || value == null)) {
            Bundle b = new Bundle();
            b.putString(key, value);
            msg.setData(b);
        }
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getHandlerEventName(int what) {
        switch (what) {
            case 1:
                return "HANDLER_EVENT_SHOW_TOAST";
            case 2:
                return "HANDLER_EVENT_UPDATE_DEVICE_ADDRESS";
            case 3:
                return "HANDLER_EVENT_UPDATE_DEVICE_NAME";
            case 4:
                return "HANDLER_EVENT_UPDATE_STATE_OPP";
            case 5:
                return "HANDLER_EVENT_UPDATE_RECEIVE_STATUS";
            case 6:
                return "HANDLER_EVENT_UPDATE_RECEIVE_FILE_NAML";
            default:
                return "Unknown Event !!";
        }
    }
}
