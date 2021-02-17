package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.aidl.UiCallbackHfp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.List;

public class HfpPageActivity extends Activity {
    private static boolean D = true;
    private static final int HANDLER_EVENT_CLEAN_UP_INFO = 18;
    private static final int HANDLER_EVENT_SHOW_TOAST = 19;
    private static final int HANDLER_EVENT_UPDATE_ALL_STATE = 1;
    private static final int HANDLER_EVENT_UPDATE_INFO_ACTIVE = 16;
    private static final int HANDLER_EVENT_UPDATE_INFO_DEVICE_NAME = 10;
    private static final int HANDLER_EVENT_UPDATE_INFO_HOLD = 15;
    private static final int HANDLER_EVENT_UPDATE_INFO_INPUT = 13;
    private static final int HANDLER_EVENT_UPDATE_INFO_LOCAL_NUMBER = 12;
    private static final int HANDLER_EVENT_UPDATE_INFO_NAME = 14;
    private static final int HANDLER_EVENT_UPDATE_INFO_NUMBER = 17;
    private static final int HANDLER_EVENT_UPDATE_INFO_OPERATOR = 11;
    private static final int HANDLER_EVENT_UPDATE_STATE_BATTERY = 5;
    private static final int HANDLER_EVENT_UPDATE_STATE_HFP = 6;
    private static final int HANDLER_EVENT_UPDATE_STATE_HFP_AUDIO = 7;
    private static final int HANDLER_EVENT_UPDATE_STATE_MIC_MUTE = 9;
    private static final int HANDLER_EVENT_UPDATE_STATE_ROAM = 3;
    private static final int HANDLER_EVENT_UPDATE_STATE_SERVICE = 2;
    private static final int HANDLER_EVENT_UPDATE_STATE_SIGNAL = 4;
    private static final int HANDLER_EVENT_UPDATE_STATE_VOICE_CONTROL = 8;
    private static String INBAND_RINGTONE = "INBAND_RINGTONE";
    private static String INFO_ACTIVE = "INFO_ACTIVE";
    private static String INFO_DEVICE_LOCAL_NUMBER = "INFO_DEVICE_LOCAL_NUMBER";
    private static String INFO_DEVICE_NAME = "INFO_DEVICE_NAME";
    private static String INFO_DEVICE_OPERATOR = "INFO_DEVICE_OPERATOR";
    private static String INFO_HOLD = "INFO_HOLD";
    private static String INFO_INPUT = "INFO_INPUT";
    private static String INFO_NAME = "INFO_NAME";
    private static String INFO_NUMBER = "INFO_NUMBER";
    private static String STATE_BATTERY = "STATE_BATTERY";
    private static String STATE_HFP = "STATE_HFP";
    private static String STATE_HFP_AUDIO = "STATE_HFP_AUDIO";
    private static String STATE_ROAM = "STATE_ROAM";
    private static String STATE_SERVICE = "STATE_SERVICE";
    private static String STATE_SIGNAL = "STATE_SIGNAL";
    private static String STATE_VOICE_CONTROL = "STATE_VOICE_CONTROL";
    private static String TAG = "NfDemo_HfpPage";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    private Button button_Back;
    private Button button_asterisk;
    private Button button_delete;
    private Button button_dial;
    private Button button_mic_mute;
    private Button button_mic_unmute;
    private Button button_num0;
    private Button button_num1;
    private Button button_num2;
    private Button button_num3;
    private Button button_num4;
    private Button button_num5;
    private Button button_num6;
    private Button button_num7;
    private Button button_num8;
    private Button button_num9;
    private Button button_pickup;
    private Button button_pickup0;
    private Button button_pickup1;
    private Button button_pickup2;
    private Button button_pound;
    private Button button_redial;
    private Button button_reject;
    private Button button_sco_ag;
    private Button button_sco_hf;
    private Button button_terminate;
    private Button button_voice_call_off;
    private Button button_voice_call_on;
    private String call_number;
    private String hfp_connected_address;
    private int hfp_state;
    private Toast infoToast;
    private String input_number = BuildConfig.FLAVOR;
    private boolean isCallIncoming = false;
    private List<GocHfpClientCall> mCallList;
    private ArrayAdapter<String> mCallListArrayAdapter;
    private UiCallbackHfp mCallbackHfp = new UiCallbackHfp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass32 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpServiceReady() throws RemoteException {
            Log.i(HfpPageActivity.TAG, "onHfpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpStateChanged() " + address + " state: " + prevState + "->" + newState);
            String state = BuildConfig.FLAVOR;
            if (newState == 140) {
                state = "Connected";
                HfpPageActivity.this.sendHandlerMessage(18, null, null);
            } else if (newState == 110) {
                state = "Disconnected";
                HfpPageActivity.this.sendHandlerMessage(18, null, null);
            } else if (newState == 120) {
                state = "Connecting";
                HfpPageActivity.this.sendHandlerMessage(18, null, null);
            } else if (newState == 100) {
                state = "Not init";
                HfpPageActivity.this.sendHandlerMessage(18, null, null);
            }
            HfpPageActivity.this.sendHandlerMessage(6, new String[]{HfpPageActivity.STATE_HFP}, new String[]{state});
            HfpPageActivity.this.hfp_state = newState;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpCallingTimeChanged(String time) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpCallingTimeChanged() " + time);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpAudioStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpAudioStateChanged() " + address + " state: " + prevState + "->" + newState);
            HfpPageActivity.this.sendHandlerMessage(7, new String[]{HfpPageActivity.STATE_HFP_AUDIO}, new String[]{newState == 140 ? "At Speaker" : "At Phone"});
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpVoiceDial(String address, boolean isVoiceDialOn) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpVoiceDial() " + address + " isVoiceDialOn: " + isVoiceDialOn);
            HfpPageActivity.this.sendHandlerMessage(8, new String[]{HfpPageActivity.STATE_VOICE_CONTROL}, new String[]{isVoiceDialOn ? "On" : "Off"});
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpErrorResponse(String address, int code) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpErrorResponse() " + address + " code: " + code);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpRemoteTelecomService() " + address + " isTelecomServiceOn: " + isTelecomServiceOn);
            StringBuilder sb = new StringBuilder();
            sb.append("Service : ");
            sb.append(isTelecomServiceOn ? "On" : "Off");
            HfpPageActivity.this.sendHandlerMessage(2, new String[]{HfpPageActivity.STATE_SERVICE}, new String[]{sb.toString()});
            if (isTelecomServiceOn) {
                new Thread() {
                    /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass32.AnonymousClass1 */

                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            String operator = HfpPageActivity.this.mCommand.getHfpRemoteNetworkOperator();
                            String localNumber = HfpPageActivity.this.mCommand.getHfpRemoteSubscriberNumber();
                            HfpPageActivity.this.sendHandlerMessage(11, new String[]{HfpPageActivity.INFO_DEVICE_OPERATOR}, new String[]{operator});
                            HfpPageActivity.this.sendHandlerMessage(12, new String[]{HfpPageActivity.INFO_DEVICE_LOCAL_NUMBER}, new String[]{localNumber});
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteRoamingStatus(String address, boolean isRoamingOn) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpRemoteRoamingStatus() " + address + " isRoamingOn: " + isRoamingOn);
            StringBuilder sb = new StringBuilder();
            sb.append("Roam : ");
            sb.append(isRoamingOn ? "On" : "Off");
            HfpPageActivity.this.sendHandlerMessage(3, new String[]{HfpPageActivity.STATE_ROAM}, new String[]{sb.toString()});
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpRemoteBatteryIndicator() " + address + " value: " + currentValue + " (" + minValue + "-" + maxValue + ")");
            StringBuilder sb = new StringBuilder();
            sb.append("Battery : ");
            sb.append(String.valueOf(currentValue));
            HfpPageActivity.this.sendHandlerMessage(5, new String[]{HfpPageActivity.STATE_BATTERY}, new String[]{sb.toString()});
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpRemoteSignalStrength() " + address + " strength: " + currentStrength + " (" + minStrength + "-" + maxStrength);
            StringBuilder sb = new StringBuilder();
            sb.append("Signal : ");
            sb.append(String.valueOf(currentStrength));
            HfpPageActivity.this.sendHandlerMessage(4, new String[]{HfpPageActivity.STATE_SIGNAL}, new String[]{sb.toString()});
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpCallChanged(String address, GocHfpClientCall call) throws RemoteException {
            String str = HfpPageActivity.TAG;
            Log.i(str, "onHfpCallChanged() " + address + " call: " + call);
            String number = null;
            String str2 = BuildConfig.FLAVOR;
            if (0 != 0) {
                str2 = number.equals(str2) ? "PrivateNumber" : null;
            }
            HfpPageActivity.this.sendHandlerMessage(16, new String[]{HfpPageActivity.INFO_ACTIVE}, new String[]{String.valueOf(str2)});
            HfpPageActivity.this.updateCallList();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void retPbapDatabaseQueryNameByNumber(String address, String phoneNumber, String fullName, boolean isSuccessed) throws RemoteException {
            if (HfpPageActivity.D) {
                String str = HfpPageActivity.TAG;
                Log.i(str, "retPbapDatabaseQueryNameByNumber() : " + address + " phoneNumber : " + phoneNumber + " fullName : " + fullName + " isSuccessed : " + isSuccessed);
            }
            if (isSuccessed) {
                HfpPageActivity.this.sendHandlerMessage(14, new String[]{HfpPageActivity.INFO_HOLD}, new String[]{fullName});
            }
        }
    };
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(HfpPageActivity.TAG, "ready  onServiceConnected");
            HfpPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (HfpPageActivity.this.mCommand == null) {
                Log.e(HfpPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(HfpPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                HfpPageActivity.this.finish();
            }
            try {
                HfpPageActivity.this.mCommand.registerHfpCallback(HfpPageActivity.this.mCallbackHfp);
                HfpPageActivity.this.hfp_connected_address = HfpPageActivity.this.mCommand.getTargetAddress();
                if (HfpPageActivity.this.hfp_connected_address.equals(NfDef.DEFAULT_ADDRESS)) {
                    HfpPageActivity.this.showToast("Choose a device first !");
                }
                HfpPageActivity.this.hfp_state = HfpPageActivity.this.mCommand.getHfpConnectionState();
                HfpPageActivity.this.initStatus();
                if (HfpPageActivity.this.call_number != null) {
                    HfpPageActivity.this.mCommand.reqPbapDatabaseQueryNameByNumber(HfpPageActivity.this.hfp_connected_address, HfpPageActivity.this.call_number);
                }
                int s = HfpPageActivity.this.mCommand.getHfpConnectionState();
                String state = BuildConfig.FLAVOR;
                if (s == 110) {
                    state = "Ready";
                } else if (s == 140) {
                    state = "Connected";
                } else if (s == 100) {
                    state = "No init";
                }
                HfpPageActivity.this.sendHandlerMessage(6, new String[]{HfpPageActivity.STATE_HFP}, new String[]{state});
                String operator = HfpPageActivity.this.mCommand.getHfpRemoteNetworkOperator();
                String subsNumber = HfpPageActivity.this.mCommand.getHfpRemoteSubscriberNumber();
                String str = HfpPageActivity.TAG;
                Log.v(str, "Piggy Check operator: " + operator);
                String str2 = HfpPageActivity.TAG;
                Log.v(str2, "Piggy Check local number: " + subsNumber);
                HfpPageActivity.this.updateCallList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(HfpPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(HfpPageActivity.TAG, "onServiceDisconnected");
            HfpPageActivity.this.mCommand = null;
        }
    };
    private Handler mHandler = null;
    private int mLastIncomingCallIndex = -1;
    private AdapterView.OnItemClickListener mListClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass30 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            String info = ((TextView) v).getText().toString();
            HfpPageActivity.this.selected_callId = Integer.valueOf(info.substring(0, 1)).intValue();
        }
    };
    private int selected_callId = -1;
    private TextView text_InBandRingtone;
    private TextView text_active;
    private TextView text_device_name;
    private TextView text_hold;
    private TextView text_input;
    private TextView text_local_number;
    private TextView text_name;
    private TextView text_number;
    private TextView text_operator;
    private TextView text_state_battery;
    private TextView text_state_hfp;
    private TextView text_state_roam;
    private TextView text_state_sco;
    private TextView text_state_service;
    private TextView text_state_signal;
    private TextView text_state_voice_control;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.hfp_page);
        initHandler();
        initView();
        bindService(new Intent(this, BtService.class), this.mConnection, 1);
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
            Log.e(TAG, "++ ON RESUME ++");
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
            this.mCommand.unregisterHfpCallback(this.mCallbackHfp);
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
    private void initStatus() throws RemoteException {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Service : ");
        String stateVoiceControl = "On";
        sb.append(this.mCommand.isHfpRemoteTelecomServiceOn() ? stateVoiceControl : "Off");
        String stateService = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Roam : ");
        if (this.mCommand.isHfpRemoteOnRoaming()) {
            str = stateVoiceControl;
        } else {
            str = "Off";
        }
        sb2.append(str);
        String stateRoam = sb2.toString();
        String stateSignal = "Signal : " + String.valueOf(this.mCommand.getHfpRemoteSignalStrength());
        String stateBattery = "Battery : " + String.valueOf(this.mCommand.getHfpRemoteBatteryIndicator());
        String deviceName = this.mCommand.getBtRemoteDeviceName(this.hfp_connected_address);
        String stateHfp = BuildConfig.FLAVOR;
        int i = this.hfp_state;
        if (i == 140) {
            stateHfp = "Connected";
            sendHandlerMessage(18, null, null);
        } else if (i == 110) {
            stateHfp = "Disconnected";
            sendHandlerMessage(18, null, null);
        } else if (i == 120) {
            stateHfp = "Connecting";
            sendHandlerMessage(18, null, null);
        } else if (i == 100) {
            stateHfp = "Not init";
            sendHandlerMessage(18, null, null);
        }
        String stateSco = this.mCommand.getHfpAudioConnectionState() == 140 ? "At Speaker" : "At Phone";
        if (!this.mCommand.isHfpRemoteVoiceDialOn()) {
            stateVoiceControl = "Off";
        }
        sendHandlerMessage(1, new String[]{STATE_SERVICE, STATE_ROAM, STATE_SIGNAL, STATE_BATTERY, INFO_DEVICE_NAME, INFO_DEVICE_OPERATOR, INFO_DEVICE_LOCAL_NUMBER, STATE_HFP, STATE_HFP_AUDIO, STATE_VOICE_CONTROL, INBAND_RINGTONE}, new String[]{stateService, stateRoam, stateSignal, stateBattery, deviceName, this.mCommand.getHfpRemoteNetworkOperator(), this.mCommand.getHfpRemoteSubscriberNumber(), stateHfp, stateSco, stateVoiceControl, this.mCommand.isHfpInBandRingtoneSupport() ? "Yes" : "No"});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateInputNumber() {
        sendHandlerMessage(13, new String[]{INFO_INPUT}, new String[]{this.input_number});
    }

    private void initView() {
        this.text_state_service = (TextView) findViewById(R.id.text_state_service);
        this.text_state_roam = (TextView) findViewById(R.id.text_state_roam);
        this.text_state_signal = (TextView) findViewById(R.id.text_state_signal);
        this.text_state_battery = (TextView) findViewById(R.id.text_state_battery);
        this.text_InBandRingtone = (TextView) findViewById(R.id.text_state_inband_ringtone);
        this.text_device_name = (TextView) findViewById(R.id.text_device_name);
        this.text_input = (TextView) findViewById(R.id.text_input);
        this.text_operator = (TextView) findViewById(R.id.text_operator);
        this.text_local_number = (TextView) findViewById(R.id.text_local_number);
        this.text_name = (TextView) findViewById(R.id.text_name);
        this.text_hold = (TextView) findViewById(R.id.text_hold);
        this.text_active = (TextView) findViewById(R.id.text_active);
        this.text_number = (TextView) findViewById(R.id.text_number);
        this.text_state_hfp = (TextView) findViewById(R.id.text_state_hfp);
        this.text_state_sco = (TextView) findViewById(R.id.text_state_sco);
        this.text_state_voice_control = (TextView) findViewById(R.id.text_state_voice_control);
        this.button_Back = (Button) findViewById(R.id.button_back);
        this.button_num0 = (Button) findViewById(R.id.button_0);
        this.button_num1 = (Button) findViewById(R.id.button_1);
        this.button_num2 = (Button) findViewById(R.id.button_2);
        this.button_num3 = (Button) findViewById(R.id.button_3);
        this.button_num4 = (Button) findViewById(R.id.button_4);
        this.button_num5 = (Button) findViewById(R.id.button_5);
        this.button_num6 = (Button) findViewById(R.id.button_6);
        this.button_num7 = (Button) findViewById(R.id.button_7);
        this.button_num8 = (Button) findViewById(R.id.button_8);
        this.button_num9 = (Button) findViewById(R.id.button_9);
        this.button_asterisk = (Button) findViewById(R.id.button_asterisk);
        this.button_pound = (Button) findViewById(R.id.button_pound);
        this.button_dial = (Button) findViewById(R.id.button_dial);
        this.button_redial = (Button) findViewById(R.id.button_redial);
        this.button_pickup = (Button) findViewById(R.id.button_pickup);
        this.button_pickup0 = (Button) findViewById(R.id.button_pickup0);
        this.button_pickup1 = (Button) findViewById(R.id.button_pickup1);
        this.button_pickup2 = (Button) findViewById(R.id.button_pickup2);
        this.button_reject = (Button) findViewById(R.id.button_reject);
        this.button_terminate = (Button) findViewById(R.id.button_terminate);
        this.button_sco_hf = (Button) findViewById(R.id.button_sco_hf);
        this.button_sco_ag = (Button) findViewById(R.id.button_sco_ag);
        this.button_voice_call_on = (Button) findViewById(R.id.button_voice_call_on);
        this.button_voice_call_off = (Button) findViewById(R.id.button_voice_call_off);
        this.button_delete = (Button) findViewById(R.id.button_delete);
        this.button_mic_mute = (Button) findViewById(R.id.button_mic_mute);
        this.button_mic_unmute = (Button) findViewById(R.id.button_mic_unmute);
        this.button_Back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "BackButton onClicked");
                HfpPageActivity.this.finish();
            }
        });
        this.button_num0.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num0 onClicked");
                HfpPageActivity.this.onNumberClick("0");
            }
        });
        this.button_num1.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num1 onClicked");
                HfpPageActivity.this.onNumberClick("1");
            }
        });
        this.button_num2.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num2 onClicked");
                HfpPageActivity.this.onNumberClick("2");
            }
        });
        this.button_num3.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num3 onClicked");
                HfpPageActivity.this.onNumberClick("3");
            }
        });
        this.button_num4.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num4 onClicked");
                HfpPageActivity.this.onNumberClick("4");
            }
        });
        this.button_num5.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num5 onClicked");
                HfpPageActivity.this.onNumberClick("5");
            }
        });
        this.button_num6.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num6 onClicked");
                HfpPageActivity.this.onNumberClick("6");
            }
        });
        this.button_num7.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass10 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num7 onClicked");
                HfpPageActivity.this.onNumberClick("7");
            }
        });
        this.button_num8.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass11 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num8 onClicked");
                HfpPageActivity.this.onNumberClick("8");
            }
        });
        this.button_num9.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass12 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_num9 onClicked");
                HfpPageActivity.this.onNumberClick("9");
            }
        });
        this.button_asterisk.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass13 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_asterisk onClicked");
                HfpPageActivity.this.onNumberClick("*");
            }
        });
        this.button_pound.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass14 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_pound onClicked");
                HfpPageActivity.this.onNumberClick("#");
            }
        });
        this.button_dial.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass15 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_dial onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpDialCall(HfpPageActivity.this.input_number);
                    HfpPageActivity.this.text_number.setText(HfpPageActivity.this.input_number);
                    HfpPageActivity.this.mCommand.reqPbapDatabaseQueryNameByNumber(HfpPageActivity.this.hfp_connected_address, HfpPageActivity.this.input_number);
                    HfpPageActivity.this.input_number = BuildConfig.FLAVOR;
                    HfpPageActivity.this.updateInputNumber();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_redial.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass16 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_redial onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpReDial();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_pickup.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass17 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_pickup onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpAnswerCall(BuildConfig.FLAVOR, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_pickup0.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass18 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_pickup0 onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpAnswerCall(BuildConfig.FLAVOR, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_pickup1.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass19 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_pickup1 onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpAnswerCall(BuildConfig.FLAVOR, 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_pickup2.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass20 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_pickup2 onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpAnswerCall(BuildConfig.FLAVOR, 2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_reject.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass21 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_reject onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpRejectIncomingCall(BuildConfig.FLAVOR);
                    HfpPageActivity.this.input_number = BuildConfig.FLAVOR;
                    HfpPageActivity.this.updateInputNumber();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_terminate.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass22 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_terminate onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpTerminateCurrentCall(BuildConfig.FLAVOR);
                    HfpPageActivity.this.input_number = BuildConfig.FLAVOR;
                    HfpPageActivity.this.updateInputNumber();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_sco_hf.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass23 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_sco_hg onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpAudioTransferToCarkit(BuildConfig.FLAVOR);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_sco_ag.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass24 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_sco_ag onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpAudioTransferToPhone(BuildConfig.FLAVOR);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_delete.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass25 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_delete onClicked");
                if (HfpPageActivity.this.hfp_state == 140) {
                    if (HfpPageActivity.this.input_number.length() < 15 && HfpPageActivity.this.input_number.length() > 0) {
                        HfpPageActivity hfpPageActivity = HfpPageActivity.this;
                        hfpPageActivity.input_number = hfpPageActivity.input_number.substring(0, HfpPageActivity.this.input_number.length() - 1);
                    }
                    HfpPageActivity.this.updateInputNumber();
                }
            }
        });
        this.button_voice_call_on.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass26 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_voice_call_on onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpVoiceDial(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_voice_call_off.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass27 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_voice_call_off onClicked");
                try {
                    HfpPageActivity.this.mCommand.reqHfpVoiceDial(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_mic_mute.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass28 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_mic_mute onClicked");
                try {
                    HfpPageActivity.this.mCommand.muteHfpMic(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_mic_unmute.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass29 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(HfpPageActivity.TAG, "button_mic_unmute onClicked");
                try {
                    HfpPageActivity.this.mCommand.muteHfpMic(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.mCallListArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        ListView callListView = (ListView) findViewById(R.id.call_list);
        callListView.setAdapter((ListAdapter) this.mCallListArrayAdapter);
        callListView.setSelected(true);
        callListView.setOnItemClickListener(this.mListClickListener);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onNumberClick(String input) {
        if (this.hfp_state == 140) {
            if (this.input_number.length() < 15) {
                this.input_number += input;
                updateInputNumber();
            }
            List<GocHfpClientCall> list = this.mCallList;
            if (list != null && list.size() > 0) {
                try {
                    this.mCommand.reqHfpSendDtmf(BuildConfig.FLAVOR, input);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass31 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String str = HfpPageActivity.TAG;
                Log.v(str, "handleMessage : " + HfpPageActivity.this.getHandlerEventName(msg.what));
                switch (msg.what) {
                    case 1:
                        HfpPageActivity.this.text_state_service.setText(b.getString(HfpPageActivity.STATE_SERVICE));
                        HfpPageActivity.this.text_state_roam.setText(b.getString(HfpPageActivity.STATE_ROAM));
                        HfpPageActivity.this.text_state_signal.setText(b.getString(HfpPageActivity.STATE_SIGNAL));
                        HfpPageActivity.this.text_state_battery.setText(b.getString(HfpPageActivity.STATE_BATTERY));
                        HfpPageActivity.this.text_device_name.setText(b.getString(HfpPageActivity.INFO_DEVICE_NAME));
                        HfpPageActivity.this.text_operator.setText(b.getString(HfpPageActivity.INFO_DEVICE_OPERATOR));
                        HfpPageActivity.this.text_local_number.setText(b.getString(HfpPageActivity.INFO_DEVICE_LOCAL_NUMBER));
                        HfpPageActivity.this.text_state_hfp.setText(b.getString(HfpPageActivity.STATE_HFP));
                        HfpPageActivity.this.text_state_sco.setText(b.getString(HfpPageActivity.STATE_HFP_AUDIO));
                        HfpPageActivity.this.text_state_voice_control.setText(b.getString(HfpPageActivity.STATE_VOICE_CONTROL));
                        HfpPageActivity.this.text_InBandRingtone.setText(b.getString(HfpPageActivity.INBAND_RINGTONE));
                        break;
                    case 2:
                        HfpPageActivity.this.text_state_service.setText(b.getString(HfpPageActivity.STATE_SERVICE));
                        break;
                    case 3:
                        HfpPageActivity.this.text_state_roam.setText(b.getString(HfpPageActivity.STATE_ROAM));
                        break;
                    case 4:
                        HfpPageActivity.this.text_state_signal.setText(b.getString(HfpPageActivity.STATE_SIGNAL));
                        break;
                    case 5:
                        HfpPageActivity.this.text_state_battery.setText(b.getString(HfpPageActivity.STATE_BATTERY));
                        break;
                    case 6:
                        HfpPageActivity.this.text_state_hfp.setText(b.getString(HfpPageActivity.STATE_HFP));
                        break;
                    case 7:
                        HfpPageActivity.this.text_state_sco.setText(b.getString(HfpPageActivity.STATE_HFP_AUDIO));
                        break;
                    case 8:
                        HfpPageActivity.this.text_state_voice_control.setText(b.getString(HfpPageActivity.STATE_VOICE_CONTROL));
                        break;
                    case 10:
                        HfpPageActivity.this.text_device_name.setText(b.getString(HfpPageActivity.INFO_DEVICE_NAME));
                        break;
                    case 11:
                        HfpPageActivity.this.text_operator.setText(b.getString(HfpPageActivity.INFO_DEVICE_OPERATOR));
                        break;
                    case 12:
                        HfpPageActivity.this.text_local_number.setText(b.getString(HfpPageActivity.INFO_DEVICE_LOCAL_NUMBER));
                        break;
                    case 13:
                        HfpPageActivity.this.text_input.setText(b.getString(HfpPageActivity.INFO_INPUT));
                        break;
                    case 14:
                        HfpPageActivity.this.text_name.setText(b.getString(HfpPageActivity.INFO_NAME));
                        break;
                    case 15:
                        HfpPageActivity.this.text_hold.setText(b.getString(HfpPageActivity.INFO_HOLD));
                        break;
                    case 16:
                        HfpPageActivity.this.text_active.setText(b.getString(HfpPageActivity.INFO_ACTIVE));
                        break;
                    case 17:
                        HfpPageActivity.this.text_number.setText(b.getString(HfpPageActivity.INFO_NUMBER));
                        break;
                    case 18:
                        HfpPageActivity.this.text_name.setText(BuildConfig.FLAVOR);
                        HfpPageActivity.this.text_hold.setText(BuildConfig.FLAVOR);
                        HfpPageActivity.this.text_active.setText(BuildConfig.FLAVOR);
                        HfpPageActivity.this.text_number.setText(BuildConfig.FLAVOR);
                        HfpPageActivity.this.text_state_sco.setText(BuildConfig.FLAVOR);
                        break;
                    case 19:
                        if (HfpPageActivity.this.infoToast != null) {
                            HfpPageActivity.this.infoToast.cancel();
                            HfpPageActivity.this.infoToast.setText(b.getString(HfpPageActivity.TOAST_MESSAGE));
                            HfpPageActivity.this.infoToast.setDuration(0);
                        } else {
                            HfpPageActivity.this.infoToast = Toast.makeText(HfpPageActivity.this.getApplicationContext(), b.getString(HfpPageActivity.TOAST_MESSAGE), 0);
                        }
                        HfpPageActivity.this.infoToast.show();
                        break;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
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

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getHandlerEventName(int what) {
        switch (what) {
            case 1:
                return "HANDLER_EVENT_UPDATE_ALL_STATE";
            case 2:
                return "HANDLER_EVENT_UPDATE_STATE_SERVICE";
            case 3:
                return "HANDLER_EVENT_UPDATE_STATE_ROAM";
            case 4:
                return "HANDLER_EVENT_UPDATE_STATE_SIGNAL";
            case 5:
                return "HANDLER_EVENT_UPDATE_STATE_BATTERY";
            case 6:
                return "HANDLER_EVENT_UPDATE_STATE_HFP";
            case 7:
                return "HANDLER_EVENT_UPDATE_STATE_HFP_AUDIO";
            case 8:
                return "HANDLER_EVENT_UPDATE_STATE_VOICE_CONTROL";
            case 9:
                return "HANDLER_EVENT_UPDATE_STATE_MIC_MUTE";
            case 10:
                return "HANDLER_EVENT_UPDATE_INFO_DEVICE_NAME";
            case 11:
                return "HANDLER_EVENT_UPDATE_INFO_DEVICE_OPERATOR";
            case 12:
                return "HANDLER_EVENT_UPDATE_INFO_DEVICE_LOCAL_NUMBER";
            case 13:
                return "HANDLER_EVENT_UPDATE_INFO_INPUT";
            case 14:
                return "HANDLER_EVENT_UPDATE_INFO_NAME";
            case 15:
                return "HANDLER_EVENT_UPDATE_INFO_HOLD";
            case 16:
                return "HANDLER_EVENT_UPDATE_INFO_ACTIVE";
            case 17:
                return "HANDLER_EVENT_UPDATE_INFO_NUMBER";
            case 18:
                return "HANDLER_EVENT_CLEAN_UP_INFO";
            case 19:
                return "HANDLER_EVENT_SHOW_TOAST";
            default:
                return "Unknown Event !!";
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String message) {
        String str = TAG;
        Log.e(str, "showToast : " + message);
        sendHandlerMessage(19, new String[]{TOAST_MESSAGE}, new String[]{message});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateCallList() {
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.HfpPageActivity.AnonymousClass33 */

            @Override // java.lang.Runnable
            public void run() {
                List<GocHfpClientCall> list = null;
                int terminateCount = 0;
                try {
                    list = HfpPageActivity.this.mCommand.getHfpCallList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (list == null) {
                    Log.e(HfpPageActivity.TAG, "Can't get current call list.");
                    return;
                }
                HfpPageActivity.this.mCallList = null;
                HfpPageActivity.this.mCallList = list;
                HfpPageActivity.this.mCallListArrayAdapter.clear();
                for (int i = 0; i < list.size(); i++) {
                    GocHfpClientCall call = list.get(i);
                    Log.d(HfpPageActivity.TAG, "state:" + call.getState());
                    String s = list.get(i).isMultiParty() ? call.getId() + " " + call.getNumber() + " MP\n" + HfpPageActivity.this.getStateString(call.getState()) : call.getId() + " " + call.getNumber() + "\n" + HfpPageActivity.this.getStateString(call.getState());
                    if (call.getState() == 4 || call.getState() == 5) {
                        HfpPageActivity.this.mLastIncomingCallIndex = i;
                        HfpPageActivity.this.isCallIncoming = true;
                    } else if (HfpPageActivity.this.mLastIncomingCallIndex == i) {
                        HfpPageActivity.this.mLastIncomingCallIndex = -1;
                        HfpPageActivity.this.isCallIncoming = false;
                    }
                    if (call.getState() == 7) {
                        terminateCount++;
                    }
                    HfpPageActivity.this.mCallListArrayAdapter.add(s);
                }
                if (list.size() == 0) {
                    HfpPageActivity.this.mCallListArrayAdapter.add("None");
                } else if (terminateCount == list.size()) {
                    Log.e(HfpPageActivity.TAG, "Piggy Check All Call is terminated.");
                    HfpPageActivity.this.updateCallList();
                    Log.e(HfpPageActivity.TAG, "Piggy Check All Call is terminated. updateCallList()");
                }
                Log.e(HfpPageActivity.TAG, "Piggy Check updateCallList finished.");
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getStateString(int state) {
        switch (state) {
            case 0:
                return "ACTIVE";
            case 1:
                return "HELD";
            case 2:
                return "DIALING";
            case 3:
                return "ALERTING";
            case 4:
                return "INCOMING";
            case 5:
                return "WAITING";
            case 6:
                return "HELD_BY_RESPONSE_AND_HOLD";
            case 7:
                return "TERMINATED";
            default:
                return BuildConfig.FLAVOR + state;
        }
    }
}
