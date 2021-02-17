package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.goodocom.bttek.bt.aidl.UiCallbackBluetooth;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import java.util.ArrayList;

public class SettingPageActivity extends Activity {
    private static String ADDRESS_A2DP = "ADDRESS_A2DP";
    private static String ADDRESS_AVRCP = "ADDRESS_AVRCP";
    private static String ADDRESS_HFP = "ADDRESS_HFP";
    private static String BT_AUTO_RECONNECT = "BT_AUTO_RECONNECT";
    private static String BT_DISCOVERABLE = "BT_DISCOVERABLE";
    private static String BT_STATE = "BT_STATE";
    private static boolean D = true;
    private static String ENABLE_BT_BUTTON = "ENABLE_BT_BUTTON";
    private static final int HANDLER_EVENT_CLEAR_ALL_ARRAY_ADAPTER = 12;
    private static final int HANDLER_EVENT_CLEAR_NEW_ARRAY_ADAPTER = 13;
    private static final int HANDLER_EVENT_CLEAR_PAIRED_ARRAY_ADAPTER = 14;
    private static final int HANDLER_EVENT_ENABLE_BT_BUTTON = 17;
    private static final int HANDLER_EVENT_SCAN_FINISHED = 11;
    private static final int HANDLER_EVENT_UPDATE_ALL_STATE = 1;
    private static final int HANDLER_EVENT_UPDATE_BT_AUTO_RECONNECT = 4;
    private static final int HANDLER_EVENT_UPDATE_BT_DISCOVERABLE = 3;
    private static final int HANDLER_EVENT_UPDATE_BT_STATE = 2;
    private static final int HANDLER_EVENT_UPDATE_NEW_DEVICE_ARRAY_ADAPTER = 15;
    private static final int HANDLER_EVENT_UPDATE_PAIRED_DEVICE_ARRAY_ADAPTER = 16;
    private static final int HANDLER_EVENT_UPDATE_STATE_A2DP = 6;
    private static final int HANDLER_EVENT_UPDATE_STATE_AVRCP = 7;
    private static final int HANDLER_EVENT_UPDATE_STATE_HFP = 5;
    private static String NEW_ADDRESSES = "NEW_ADDRESSES";
    private static String NEW_NAME = "NEW_NAME";
    private static String PAIRED_ADDRESSES = "PAIRED_ADDRESSES";
    private static String PAIRED_ELEMENTS = "PAIRED_ELEMENTS";
    private static String PAIRED_NAMES = "PAIRED_NAMES";
    private static String STATE_A2DP = "STATE_A2DP";
    private static String STATE_AVRCP = "STATE_AVRCP";
    private static String STATE_HFP = "STATE_HFP";
    private static String TAG = "NfDemo_SettingPage";
    private static final String strPsw = "0000";
    private Button button_back;
    private Button button_bt_off;
    private Button button_bt_on;
    private Button button_connect;
    private Button button_disconnect;
    private Button button_discoverable_off;
    private Button button_discoverable_on;
    private Button button_pair;
    private Button button_role_car;
    private Button button_role_tablet;
    private Button button_scan;
    private Button button_unpair;
    private ArrayList<String> device_founded_list = new ArrayList<>();
    private boolean isAutoAcceptPairingRequest = true;
    private int mBtRoleMode = -1;
    private UiCallbackBluetooth mCallbackBluetooth = new UiCallbackBluetooth.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass16 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onBluetoothServiceReady() throws RemoteException {
            Log.i(SettingPageActivity.TAG, "onBluetoothServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterStateChanged(int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onAdapterStateChanged() state: " + prevState + "->" + newState);
            String bluetoothStateString = BuildConfig.FLAVOR;
            if (newState == 300) {
                bluetoothStateString = "Off";
                SettingPageActivity.this.sendHandlerMessage(13, null, null);
            } else if (newState == 301) {
                bluetoothStateString = "Turning On";
            } else if (newState == 302) {
                bluetoothStateString = "On";
                SettingPageActivity.this.mCommand.reqBtPairedDevices(0);
            } else if (newState == 303) {
                bluetoothStateString = "Turning Off";
                SettingPageActivity.this.sendHandlerMessage(12, null, null);
            }
            if (newState == 300 || newState == 302) {
                SettingPageActivity.this.sendHandlerMessage(17, new String[]{SettingPageActivity.ENABLE_BT_BUTTON}, new String[]{"true"});
                SettingPageActivity.this.updateState();
            } else if (newState == 301 || newState == 303) {
                SettingPageActivity.this.sendHandlerMessage(17, new String[]{SettingPageActivity.ENABLE_BT_BUTTON}, new String[]{"false"});
            }
            if (newState >= 300 && newState <= 303) {
                SettingPageActivity.this.sendHandlerMessage(2, new String[]{SettingPageActivity.BT_STATE}, new String[]{bluetoothStateString});
            }
            if (newState == 321) {
                SettingPageActivity.this.sendHandlerMessage(11, null, null);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoverableModeChanged(int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onAdapterDiscoverableModeChanged() " + prevState + "->" + newState);
            String bluetoothDiscoverableString = BuildConfig.FLAVOR;
            if (newState == 310) {
                bluetoothDiscoverableString = "None";
            } else if (newState == 311) {
                bluetoothDiscoverableString = "Connectable";
            } else if (newState == 312) {
                bluetoothDiscoverableString = "Connectable\nDisconnectable";
            }
            SettingPageActivity.this.sendHandlerMessage(3, new String[]{SettingPageActivity.BT_DISCOVERABLE}, new String[]{bluetoothDiscoverableString});
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoveryStarted() throws RemoteException {
            Log.i(SettingPageActivity.TAG, "onAdapterDiscoveryStarted()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoveryFinished() throws RemoteException {
            Log.i(SettingPageActivity.TAG, "onAdapterDiscoveryFinished()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void retPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "retPairedDevices() elements: " + elements);
            for (int i = 0; i < supportProfile.length; i++) {
                String str2 = SettingPageActivity.TAG;
                Log.e(str2, "name: " + name[i] + " supportProfile: " + supportProfile[i]);
                if (supportProfile[i] > 0) {
                    name[i] = name[i] + "  (";
                }
                if ((supportProfile[i] & 1) > 0) {
                    name[i] = name[i] + " HFP";
                }
                if ((supportProfile[i] & 2) > 0) {
                    name[i] = name[i] + " A2DP";
                }
                if ((supportProfile[i] & 16) > 0) {
                    name[i] = name[i] + " AVRCP1.4";
                } else if ((supportProfile[i] & 8) > 0) {
                    name[i] = name[i] + " AVRCP1.3";
                } else if ((supportProfile[i] & 4) > 0) {
                    name[i] = name[i] + " AVRCP";
                }
                if ((supportProfile[i] & 32) > 0) {
                    name[i] = name[i] + " PBAP";
                }
                if ((supportProfile[i] & 64) > 0) {
                    name[i] = name[i] + " MAP";
                }
                if ((supportProfile[i] & 8192) > 0) {
                    name[i] = name[i] + " iAP";
                }
                if (supportProfile[i] > 0) {
                    name[i] = name[i] + " )";
                }
            }
            Message msg = Message.obtain(SettingPageActivity.this.mHandler, 16);
            Bundle b = new Bundle();
            b.putInt(SettingPageActivity.PAIRED_ELEMENTS, elements);
            b.putStringArray(SettingPageActivity.PAIRED_NAMES, name);
            b.putStringArray(SettingPageActivity.PAIRED_ADDRESSES, address);
            msg.setData(b);
            SettingPageActivity.this.mHandler.sendMessage(msg);
            SettingPageActivity.this.pairedDeviceArray = address;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceFound(String address, String name, byte category) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onDeviceFound() " + address + " name: " + name);
            boolean isPairedDevice = false;
            int i = 0;
            while (true) {
                if (i >= SettingPageActivity.this.pairedDeviceArray.length) {
                    break;
                } else if (address.equals(SettingPageActivity.this.pairedDeviceArray[i])) {
                    isPairedDevice = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!isPairedDevice && !SettingPageActivity.this.device_founded_list.contains(address)) {
                SettingPageActivity.this.device_founded_list.add(address);
                SettingPageActivity.this.sendHandlerMessage(15, new String[]{SettingPageActivity.NEW_NAME, SettingPageActivity.NEW_ADDRESSES}, new String[]{name, address});
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceBondStateChanged(String address, String name, int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onDeviceBondStateChanged() " + address + " name: " + name + " state: " + prevState + "->" + newState);
            SettingPageActivity.this.mCommand.reqBtPairedDevices(0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceUuidsUpdated(String address, String name, int supportProfile) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onDeviceUuidsUpdated() " + address + " name: " + name + " supportProfile: " + supportProfile);
            SettingPageActivity.this.mCommand.reqBtPairedDevices(0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onLocalAdapterNameChanged(String name) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onLocalAdapterNameChanged() name: " + name);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onLocalAdapterPinCodeChanged(String key) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onLocalAdapterPinCodeChanged() name: " + key);
        }

        /* JADX DEBUG: Can't convert new array creation: APUT found in different block: 0x0065: APUT  (r0v2 'valueArray' java.lang.String[]), (1 ??[boolean, int, float, short, byte, char]), (r3v1 java.lang.String) */
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onHfpStateChanged() " + address + " state: " + prevState + "->" + newState);
            String[] keyArray = {SettingPageActivity.STATE_HFP, SettingPageActivity.ADDRESS_HFP};
            String stateString = "?";
            if (newState >= 140) {
                stateString = "On";
            } else if (newState == 120 || newState == 125) {
                stateString = "Ing";
            } else if (newState == 110) {
                stateString = "Off";
            }
            String[] valueArray = new String[2];
            valueArray[0] = stateString;
            valueArray[1] = newState >= 140 ? address.substring(9, 17) : "00:00:00";
            SettingPageActivity.this.sendHandlerMessage(5, keyArray, valueArray);
            new Thread() {
                /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass16.AnonymousClass1 */

                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        SettingPageActivity.this.mCommand.reqBtPairedDevices(0);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }.start();
        }

        /* JADX DEBUG: Can't convert new array creation: APUT found in different block: 0x0065: APUT  (r0v2 'valueArray' java.lang.String[]), (1 ??[boolean, int, float, short, byte, char]), (r3v1 java.lang.String) */
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onA2dpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onA2dpStateChanged() " + address + " state: " + prevState + "->" + newState);
            String[] keyArray = {SettingPageActivity.STATE_A2DP, SettingPageActivity.ADDRESS_A2DP};
            String stateString = "?";
            if (newState >= 140) {
                stateString = "On";
            } else if (newState == 120 || newState == 125) {
                stateString = "Ing";
            } else if (newState == 110) {
                stateString = "Off";
            }
            String[] valueArray = new String[2];
            valueArray[0] = stateString;
            valueArray[1] = newState >= 140 ? address.substring(9, 17) : "00:00:00";
            SettingPageActivity.this.sendHandlerMessage(6, keyArray, valueArray);
        }

        /* JADX DEBUG: Can't convert new array creation: APUT found in different block: 0x0065: APUT  (r0v2 'valueArray' java.lang.String[]), (1 ??[boolean, int, float, short, byte, char]), (r3v1 java.lang.String) */
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onAvrcpStateChanged() " + address + " state: " + prevState + "->" + newState);
            String[] keyArray = {SettingPageActivity.STATE_AVRCP, SettingPageActivity.ADDRESS_AVRCP};
            String stateString = "?";
            if (newState >= 140) {
                stateString = "On";
            } else if (newState == 120 || newState == 125) {
                stateString = "Ing";
            } else if (newState == 110) {
                stateString = "Off";
            }
            String[] valueArray = new String[2];
            valueArray[0] = stateString;
            valueArray[1] = newState >= 140 ? address.substring(9, 17) : "00:00:00";
            SettingPageActivity.this.sendHandlerMessage(7, keyArray, valueArray);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceOutOfRange(String address) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onDeviceOutOfRange() " + address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onBtRoleModeChanged(int mode) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onBtRoleModeChanged() " + mode);
            SettingPageActivity.this.mBtRoleMode = mode;
            SettingPageActivity.this.runOnUiThread(new Runnable() {
                /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass16.AnonymousClass2 */

                @Override // java.lang.Runnable
                public void run() {
                    int i = SettingPageActivity.this.mBtRoleMode;
                    if (i == 1) {
                        SettingPageActivity.this.text_state_role_mode.setText("Car");
                    } else if (i != 2) {
                        SettingPageActivity.this.text_state_role_mode.setText("Unknown");
                    } else {
                        SettingPageActivity.this.text_state_role_mode.setText("Tablet");
                    }
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceAclDisconnected(String address) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onDeviceAclDisconnected() " + address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onBtAutoConnectStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onBtAutoConnectStateChanged() " + address + " state: " + prevState + " -> " + newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onMainDevicesChanged(String address, String name) throws RemoteException {
            String str = SettingPageActivity.TAG;
            Log.i(str, "onMainDevicesChanged() " + address + " name: " + name);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAutoConnect(int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAutoAnwer(int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onConnectedDevice(String mainDevice, String subDevice) throws RemoteException {
        }
    };
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(SettingPageActivity.TAG, "ready  onServiceConnected");
            SettingPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (SettingPageActivity.this.mCommand == null) {
                Log.e(SettingPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(SettingPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                SettingPageActivity.this.finish();
            }
            try {
                SettingPageActivity.this.mCommand.registerBtCallback(SettingPageActivity.this.mCallbackBluetooth);
                SettingPageActivity.this.mCommand.reqBtPairedDevices(0);
                SettingPageActivity.this.mCommand.getBtLocalName();
                String str = SettingPageActivity.TAG;
                Log.e(str, "nFore service version: " + SettingPageActivity.this.mCommand.getNfServiceVersionName());
                SettingPageActivity.this.updateState();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Log.e(SettingPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(SettingPageActivity.TAG, "onServiceDisconnected");
            SettingPageActivity.this.mCommand = null;
        }
    };
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass2 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            String info = ((TextView) v).getText().toString();
            SettingPageActivity.this.selected_address = info.substring(info.length() - 17);
            String str = SettingPageActivity.TAG;
            Log.e(str, "selected address : " + SettingPageActivity.this.selected_address);
            if (SettingPageActivity.this.mCommand != null) {
                try {
                    SettingPageActivity.this.mCommand.setTargetAddress(SettingPageActivity.this.selected_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler mHandler = null;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass18 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String str = SettingPageActivity.TAG;
            Log.e(str, "Piggy Check action: " + action);
            if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                int type = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
                String str2 = SettingPageActivity.TAG;
                Log.e(str2, "Piggy Check type: " + type);
                if (!SettingPageActivity.this.isAutoAcceptPairingRequest) {
                    return;
                }
                if (type == 2) {
                    Log.e(SettingPageActivity.TAG, "PAIRING_VARIANT_PASSKEY_CONFIRMATION");
                    try {
                        device.getClass().getMethod("setPairingConfirmation", Boolean.TYPE).invoke(device, true);
                        device.getClass().getMethod("cancelPairingUserInput", Boolean.TYPE).invoke(device, new Object[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (type == 0) {
                    Log.e(SettingPageActivity.TAG, "PAIRING_VARIANT_PIN");
                    try {
                        SettingPageActivity.setPin(device.getClass(), device, SettingPageActivity.strPsw);
                        SettingPageActivity.createBond(device.getClass(), device);
                        device.getClass().getMethod("cancelPairingUserInput", Boolean.TYPE).invoke(device, new Object[0]);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    String str3 = SettingPageActivity.TAG;
                    Log.e(str3, "Unkown paring type" + type);
                }
            }
        }
    };
    private String[] pairedDeviceArray = new String[0];
    private String selected_address = BuildConfig.FLAVOR;
    private TextView text_address_a2dp;
    private TextView text_address_avrcp;
    private TextView text_address_hfp;
    private TextView text_bt;
    private TextView text_discoverable;
    private TextView text_state_a2dp;
    private TextView text_state_avrcp;
    private TextView text_state_hfp;
    private TextView text_state_role_mode;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.setting_page);
        initHandler();
        initView();
        bindService(new Intent(this, BtService.class), this.mConnection, 1);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        registerReceiver(this.mReceiver, filter);
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
            this.mCommand.unregisterBtCallback(this.mCallbackBluetooth);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(this.mConnection);
        unregisterReceiver(this.mReceiver);
        this.mHandler = null;
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        super.onDestroy();
    }

    public void initView() {
        this.text_bt = (TextView) findViewById(R.id.text_bt);
        this.text_discoverable = (TextView) findViewById(R.id.text_discoverable);
        this.text_state_hfp = (TextView) findViewById(R.id.text_state_hfp);
        this.text_state_a2dp = (TextView) findViewById(R.id.text_state_a2dp);
        this.text_state_avrcp = (TextView) findViewById(R.id.text_state_avrcp);
        this.text_state_role_mode = (TextView) findViewById(R.id.text_state_role_mode);
        this.text_address_hfp = (TextView) findViewById(R.id.text_address_hfp);
        this.text_address_a2dp = (TextView) findViewById(R.id.text_address_a2dp);
        this.text_address_avrcp = (TextView) findViewById(R.id.text_address_avrcp);
        this.button_bt_on = (Button) findViewById(R.id.button_bt_on);
        this.button_bt_off = (Button) findViewById(R.id.button_bt_off);
        this.button_discoverable_on = (Button) findViewById(R.id.button_discoverable_on);
        this.button_discoverable_off = (Button) findViewById(R.id.button_discoverable_off);
        this.button_connect = (Button) findViewById(R.id.button_connect);
        this.button_disconnect = (Button) findViewById(R.id.button_disconnect);
        this.button_pair = (Button) findViewById(R.id.button_pair);
        this.button_unpair = (Button) findViewById(R.id.button_unpair);
        this.button_role_car = (Button) findViewById(R.id.button_role_car);
        this.button_role_tablet = (Button) findViewById(R.id.button_role_tablet);
        this.button_scan = (Button) findViewById(R.id.button_scan);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.button_bt_on.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_bt_on onClicked");
                try {
                    SettingPageActivity.this.mCommand.setBtEnable(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_bt_off.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_bt_off onClicked");
                try {
                    SettingPageActivity.this.mCommand.setBtEnable(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_discoverable_on.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_discoverable_on onClicked");
                try {
                    SettingPageActivity.this.mCommand.setBtDiscoverableTimeout(0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_discoverable_off.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_discoverable_off onClicked");
                try {
                    SettingPageActivity.this.mCommand.setBtDiscoverableTimeout(-1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_connect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_connect onClicked");
                try {
                    if (!SettingPageActivity.this.selected_address.equals(BuildConfig.FLAVOR)) {
                        SettingPageActivity.this.mCommand.reqBtConnectHfpA2dp(SettingPageActivity.this.selected_address);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_disconnect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_disconnect onClicked");
                try {
                    SettingPageActivity.this.mCommand.reqBtDisconnectAll(BuildConfig.FLAVOR);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_pair.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_pair onClicked");
                try {
                    if (!SettingPageActivity.this.selected_address.equals(BuildConfig.FLAVOR)) {
                        SettingPageActivity.this.mCommand.reqBtPair(SettingPageActivity.this.selected_address);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_unpair.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass10 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_unpair onClicked");
                try {
                    if (!SettingPageActivity.this.selected_address.equals(BuildConfig.FLAVOR)) {
                        SettingPageActivity.this.mCommand.reqBtUnpair(SettingPageActivity.this.selected_address);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_role_car.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass11 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_role_car onClicked");
                try {
                    SettingPageActivity.this.mCommand.switchBtRoleMode(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_role_tablet.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass12 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_role_tablet onClicked");
                try {
                    SettingPageActivity.this.mCommand.switchBtRoleMode(2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_scan.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass13 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_scan onClicked");
                SettingPageActivity.this.device_founded_list.clear();
                try {
                    SettingPageActivity.this.mCommand.startBtDiscovery();
                    SettingPageActivity.this.mNewDevicesArrayAdapter.clear();
                    SettingPageActivity.this.setProgressBarIndeterminateVisibility(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass14 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SettingPageActivity.TAG, "button_back onClicked");
                try {
                    SettingPageActivity.this.mCommand.cancelBtDiscovery();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                SettingPageActivity.this.finish();
            }
        });
        this.mPairedDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        this.mNewDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter((ListAdapter) this.mPairedDevicesArrayAdapter);
        pairedListView.setSelected(true);
        pairedListView.setOnItemClickListener(this.mDeviceClickListener);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter((ListAdapter) this.mNewDevicesArrayAdapter);
        newDevicesListView.setSelected(true);
        newDevicesListView.setOnItemClickListener(this.mDeviceClickListener);
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass15 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String str = SettingPageActivity.TAG;
                Log.v(str, "handleMessage : " + SettingPageActivity.this.getHandlerEventName(msg.what));
                int i = msg.what;
                if (i == 1) {
                    SettingPageActivity.this.text_bt.setText(bundle.getString(SettingPageActivity.BT_STATE));
                    SettingPageActivity.this.text_discoverable.setText(bundle.getString(SettingPageActivity.BT_DISCOVERABLE));
                    SettingPageActivity.this.text_state_hfp.setText(bundle.getString(SettingPageActivity.STATE_HFP));
                    SettingPageActivity.this.text_state_a2dp.setText(bundle.getString(SettingPageActivity.STATE_A2DP));
                    SettingPageActivity.this.text_state_avrcp.setText(bundle.getString(SettingPageActivity.STATE_AVRCP));
                    SettingPageActivity.this.text_address_hfp.setText(bundle.getString(SettingPageActivity.ADDRESS_HFP));
                    SettingPageActivity.this.text_address_a2dp.setText(bundle.getString(SettingPageActivity.ADDRESS_A2DP));
                    SettingPageActivity.this.text_address_avrcp.setText(bundle.getString(SettingPageActivity.ADDRESS_AVRCP));
                } else if (i == 2) {
                    SettingPageActivity.this.text_bt.setText(bundle.getString(SettingPageActivity.BT_STATE));
                } else if (i == 3) {
                    SettingPageActivity.this.text_discoverable.setText(bundle.getString(SettingPageActivity.BT_DISCOVERABLE));
                } else if (i == 5) {
                    TextView textView = SettingPageActivity.this.text_state_hfp;
                    textView.setText("HFP : " + bundle.getString(SettingPageActivity.STATE_HFP));
                    SettingPageActivity.this.text_address_hfp.setText(bundle.getString(SettingPageActivity.ADDRESS_HFP));
                } else if (i == 6) {
                    TextView textView2 = SettingPageActivity.this.text_state_a2dp;
                    textView2.setText("A2DP : " + bundle.getString(SettingPageActivity.STATE_A2DP));
                    SettingPageActivity.this.text_address_a2dp.setText(bundle.getString(SettingPageActivity.ADDRESS_A2DP));
                } else if (i != 7) {
                    switch (i) {
                        case 11:
                            SettingPageActivity.this.setProgressBarIndeterminateVisibility(false);
                            if (SettingPageActivity.this.mNewDevicesArrayAdapter.getCount() == 0) {
                                SettingPageActivity.this.mNewDevicesArrayAdapter.add("No Device");
                                break;
                            }
                            break;
                        case 12:
                            SettingPageActivity.this.mPairedDevicesArrayAdapter.clear();
                            SettingPageActivity.this.mNewDevicesArrayAdapter.clear();
                            break;
                        case 13:
                            SettingPageActivity.this.mNewDevicesArrayAdapter.clear();
                            break;
                        case 14:
                            SettingPageActivity.this.mPairedDevicesArrayAdapter.clear();
                            break;
                        case 15:
                            String name = bundle.getString(SettingPageActivity.NEW_NAME);
                            String address = bundle.getString(SettingPageActivity.NEW_ADDRESSES);
                            ArrayAdapter arrayAdapter = SettingPageActivity.this.mNewDevicesArrayAdapter;
                            arrayAdapter.add(name + "\n" + address);
                            break;
                        case 16:
                            int elements = bundle.getInt(SettingPageActivity.PAIRED_ELEMENTS);
                            String[] names = bundle.getStringArray(SettingPageActivity.PAIRED_NAMES);
                            String[] addresses = bundle.getStringArray(SettingPageActivity.PAIRED_ADDRESSES);
                            SettingPageActivity.this.mPairedDevicesArrayAdapter.clear();
                            if (elements > 0) {
                                for (int i2 = 0; i2 < elements; i2++) {
                                    ArrayAdapter arrayAdapter2 = SettingPageActivity.this.mPairedDevicesArrayAdapter;
                                    arrayAdapter2.add(names[i2] + "\n" + addresses[i2]);
                                }
                                break;
                            } else {
                                SettingPageActivity.this.mPairedDevicesArrayAdapter.add("No Device");
                                break;
                            }
                        case 17:
                            SettingPageActivity.this.button_bt_on.setEnabled(bundle.getString(SettingPageActivity.ENABLE_BT_BUTTON).equals("true"));
                            break;
                    }
                } else {
                    TextView textView3 = SettingPageActivity.this.text_state_avrcp;
                    textView3.setText("AVRCP : " + bundle.getString(SettingPageActivity.STATE_AVRCP));
                    SettingPageActivity.this.text_address_avrcp.setText(bundle.getString(SettingPageActivity.ADDRESS_AVRCP));
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
                return "HANDLER_EVENT_UPDATE_BT_STATE";
            case 3:
                return "HANDLER_EVENT_UPDATE_BT_DISCOVERABLE";
            case 4:
                return "HANDLER_EVENT_UPDATE_BT_AUTO_RECONNECT";
            case 5:
                return "HANDLER_EVENT_UPDATE_STATE_HFP";
            case 6:
                return "HANDLER_EVENT_UPDATE_STATE_A2DP";
            case 7:
                return "HANDLER_EVENT_UPDATE_STATE_AVRCP";
            case 8:
            case 9:
            case 10:
            default:
                return "Unknown Event !!";
            case 11:
                return "HANDLER_EVENT_SCAN_FINISHED";
            case 12:
                return "HANDLER_EVENT_CLEAR_ALL_ARRAY_ADAPTER";
            case 13:
                return "HANDLER_EVENT_CLEAR_NEW_ARRAY_ADAPTER";
            case 14:
                return "HANDLER_EVENT_CLEAR_PAIRED_ARRAY_ADAPTER";
            case 15:
                return "HANDLER_EVENT_UPDATE_NEW_DEVICE_ARRAY_ADAPTER";
            case 16:
                return "HANDLER_EVENT_UPDATE_PAIRED_DEVICE_ARRAY_ADAPTER";
            case 17:
                return "HANDLER_EVENT_ENABLE_BT_BUTTON";
        }
    }

    /* access modifiers changed from: private */
    /* JADX DEBUG: Can't convert new array creation: APUT found in different block: 0x01af: APUT  (r12v16 'valueArray' java.lang.String[]), (0 ??[int, short, byte, char]), (r10v10 java.lang.String) */
    /* access modifiers changed from: public */
    private void updateState() {
        String hfpStateString;
        String a2dpStateString;
        String avrcpStateString;
        String str;
        UiCommand uiCommand = this.mCommand;
        if (uiCommand != null) {
            try {
                String hfpConnectedAddress = uiCommand.getHfpConnectedAddress();
                String a2dpConnectedAddress = this.mCommand.getA2dpConnectedAddress();
                String avrcpConnectedAddress = this.mCommand.getAvrcpConnectedAddress();
                int hfpState = this.mCommand.getHfpConnectionState();
                int a2dpState = this.mCommand.getA2dpConnectionState();
                int avrcpState = this.mCommand.getAvrcpConnectionState();
                Log.e(TAG, "Piggy HFP address: " + hfpConnectedAddress);
                Log.e(TAG, "Piggy A2DP address: " + a2dpConnectedAddress);
                Log.e(TAG, "Piggy AVRCP address: " + avrcpConnectedAddress);
                Log.e(TAG, "Piggy hfpState: " + hfpState);
                Log.e(TAG, "Piggy a2dpState: " + a2dpState);
                Log.e(TAG, "Piggy avrcpState: " + avrcpState);
                if (hfpState >= 140) {
                    hfpStateString = "HFP : On";
                } else if (hfpState == 120) {
                    hfpStateString = "HFP : Ing";
                } else if (hfpState == 110) {
                    hfpStateString = "HFP : Off";
                } else {
                    hfpStateString = "HFP : " + hfpState;
                    Log.e(TAG, "Unknown HFP state: " + hfpState);
                }
                if (a2dpState >= 140) {
                    a2dpStateString = "A2DP : On";
                } else if (a2dpState == 120) {
                    a2dpStateString = "A2DP : Ing";
                } else if (a2dpState == 110) {
                    a2dpStateString = "A2DP : Off";
                } else {
                    a2dpStateString = "A2DP : " + a2dpState;
                    Log.e(TAG, "Unknown A2DP state: " + a2dpState);
                }
                if (avrcpState >= 140) {
                    avrcpStateString = "AVRCP : On";
                } else if (avrcpState == 120) {
                    avrcpStateString = "AVRCP : Ing";
                } else if (avrcpState == 110) {
                    avrcpStateString = "AVRCP : Off";
                } else {
                    avrcpStateString = "AVRCP : " + avrcpState;
                    Log.e(TAG, "Unknown AVRCP state: " + avrcpState);
                }
                String[] eventArray = {BT_STATE, BT_DISCOVERABLE, BT_AUTO_RECONNECT, STATE_HFP, ADDRESS_HFP, STATE_A2DP, ADDRESS_A2DP, STATE_AVRCP, ADDRESS_AVRCP};
                String[] valueArray = new String[9];
                String str2 = "On";
                valueArray[0] = this.mCommand.isBtEnabled() ? str2 : "Off";
                if (this.mCommand.isBtDiscoverable()) {
                    str = str2;
                } else {
                    str = "Off";
                }
                valueArray[1] = str;
                if (!this.mCommand.isBtAutoConnectEnable()) {
                    str2 = "Off";
                }
                valueArray[2] = str2;
                valueArray[3] = hfpStateString;
                valueArray[4] = hfpConnectedAddress.substring(9, 17);
                valueArray[5] = a2dpStateString;
                valueArray[6] = a2dpConnectedAddress.substring(9, 17);
                valueArray[7] = avrcpStateString;
                valueArray[8] = avrcpConnectedAddress.substring(9, 17);
                sendHandlerMessage(1, eventArray, valueArray);
                this.mBtRoleMode = this.mCommand.getBtRoleMode();
                runOnUiThread(new Runnable() {
                    /* class com.goodocom.bttek.bt.demo.ui.SettingPageActivity.AnonymousClass17 */

                    @Override // java.lang.Runnable
                    public void run() {
                        int i = SettingPageActivity.this.mBtRoleMode;
                        if (i == 1) {
                            SettingPageActivity.this.text_state_role_mode.setText("Car");
                        } else if (i != 2) {
                            SettingPageActivity.this.text_state_role_mode.setText("Unknown");
                        } else {
                            SettingPageActivity.this.text_state_role_mode.setText("Tablet");
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean setPin(Class btClass, BluetoothDevice btDevice, String str) throws Exception {
        try {
            Boolean returnValue = (Boolean) btClass.getDeclaredMethod("setPin", byte[].class).invoke(btDevice, str.getBytes());
            if (D) {
                Log.e("returnValue", BuildConfig.FLAVOR + returnValue);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return true;
    }

    public static boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        return ((Boolean) btClass.getMethod("createBond", new Class[0]).invoke(btDevice, new Object[0])).booleanValue();
    }
}
