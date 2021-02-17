package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelUuid;
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
import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackGattServer;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.Random;

public class GattServerPageActivity extends Activity {
    private static boolean D = true;
    private static String TAG = "NfDemo_GattServerPage";
    ParcelUuid Body_Sensor_Location = ParcelUuid.fromString("00002A38-0000-1000-8000-00805f9b34fb");
    ParcelUuid Client_Characteristic_Configuration = ParcelUuid.fromString("00002902-0000-1000-8000-00805f9b34fb");
    ParcelUuid Heart_Rate = ParcelUuid.fromString("0000180d-0000-1000-8000-00805f9b34fb");
    ParcelUuid Heart_Rate_Control_Point = ParcelUuid.fromString("00002A39-0000-1000-8000-00805f9b34fb");
    ParcelUuid Heart_Rate_Measurement = ParcelUuid.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    private Button button_add_service;
    private Button button_back;
    private Button button_clean_log;
    private Button button_clean_service;
    private Button button_listen;
    private Button button_stop_listen;
    private int[] listViewIdTable;
    private UiCallbackGattServer mCallbackGattServer = new UiCallbackGattServer.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServiceReady() throws RemoteException {
            Log.v(GattServerPageActivity.TAG, "onGattServiceReady()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerStateChanged(String address, int s) throws RemoteException {
            String str = GattServerPageActivity.TAG;
            Log.v(str, "onGattServerStateChanged() " + address + " state: " + s);
            String state = BuildConfig.FLAVOR;
            if (s == 110) {
                state = "Ready";
            } else if (s == 130) {
                state = "Listening";
            } else if (s == 140) {
                state = "Connected";
            } else if (s == 100) {
                state = "No init";
            }
            GattServerPageActivity.this.runOnUiThread(new RunnableS(state) {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass1 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.text_state_gatt.setText(this.mString);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcId) throws RemoteException {
            String str = GattServerPageActivity.TAG;
            Log.v(str, "onGattServerServiceAdded() " + srvcId);
            GattServerPageActivity.this.runOnUiThread(new RunnableS("Service: " + srvcId.toString() + " added") {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass2 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                    String str = GattServerPageActivity.TAG;
                    Log.e(str, BuildConfig.FLAVOR + this.mString);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcId) throws RemoteException {
            String str = GattServerPageActivity.TAG;
            Log.v(str, "onGattServerServiceDeleted() " + srvcId);
            GattServerPageActivity.this.runOnUiThread(new RunnableS("Service: " + srvcId.toString() + " deleted") {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass3 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                    String str = GattServerPageActivity.TAG;
                    Log.e(str, BuildConfig.FLAVOR + this.mString);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerCharacteristicReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId) throws RemoteException {
            Log.v(GattServerPageActivity.TAG, "onGattServerCharacteristicReadRequest()");
            GattServerPageActivity.this.mCommand.reqGattServerSendResponse(address, transId, 0, 0, GattServerPageActivity.this.getRandomIntegerByteArray());
            GattServerPageActivity.this.runOnUiThread(new RunnableS("ReadRequest(C): " + charId.toString()) {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass4 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerDescriptorReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId) throws RemoteException {
            Log.v(GattServerPageActivity.TAG, "onGattServerDescriptorReadRequest() " + address);
            Log.v(GattServerPageActivity.TAG, "transId: " + transId);
            Log.v(GattServerPageActivity.TAG, "offset: " + offset);
            Log.v(GattServerPageActivity.TAG, "isLong: " + isLong);
            Log.v(GattServerPageActivity.TAG, "srvcType: " + srvcType);
            Log.v(GattServerPageActivity.TAG, "srvcId: " + srvcId);
            Log.v(GattServerPageActivity.TAG, "charId: " + charId);
            Log.v(GattServerPageActivity.TAG, "descrId: " + descrId);
            GattServerPageActivity.this.mCommand.reqGattServerSendResponse(address, transId, 0, 0, new byte[]{0});
            GattServerPageActivity.this.runOnUiThread(new RunnableS("ReadRequest(D): " + descrId) {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass5 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerCharacteristicWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, byte[] value) throws RemoteException {
            Log.v(GattServerPageActivity.TAG, "onGattServerCharacteristicWriteRequest()");
            GattServerPageActivity.this.mCommand.reqGattServerSendResponse(address, transId, 0, 0, new byte[0]);
            GattServerPageActivity.this.runOnUiThread(new RunnableS("WriteRequest(C): " + charId + " v: " + new String(value)) {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass6 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerDescriptorWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId, byte[] value) throws RemoteException {
            Log.v(GattServerPageActivity.TAG, "onGattServerDescriptorWriteRequest()");
            GattServerPageActivity.this.mCommand.reqGattServerSendResponse(address, transId, 0, 0, value);
            GattServerPageActivity.this.runOnUiThread(new RunnableS("WriteRequest(D): " + descrId + " v: " + new String(value)) {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass7 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                }
            });
            if (value != null) {
                Log.d(GattServerPageActivity.TAG, "value length: " + value.length);
                Log.d(GattServerPageActivity.TAG, "byte[0]: 0x" + Integer.toHexString(value[0]));
                if (descrId.equals(GattServerPageActivity.this.Client_Characteristic_Configuration)) {
                    if ((value[0] & NfDef.AVRCP_BROWSING_STATUS_SEARCH_NOT_SUPPORT) == 16) {
                        Log.d(GattServerPageActivity.TAG, "Indication enabled");
                    } else {
                        Log.d(GattServerPageActivity.TAG, "Indication disabled");
                    }
                    if ((value[0] & 1) == 1) {
                        Log.d(GattServerPageActivity.TAG, "Notification enabled");
                        GattServerPageActivity.this.mHeartRateNotifyThread.update(address, srvcType, srvcId, charId, false);
                        GattServerPageActivity.this.mHeartRateNotifyThread.setNeedNotify(true);
                        return;
                    }
                    Log.d(GattServerPageActivity.TAG, "Notification disabled");
                    GattServerPageActivity.this.mHeartRateNotifyThread.setNeedNotify(false);
                }
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerExecuteWrite(String address, int transId, boolean execWrite) throws RemoteException {
            Log.v(GattServerPageActivity.TAG, "onGattServerExecuteWrite()");
            GattServerPageActivity.this.mCommand.reqGattServerSendResponse(address, transId, 0, 0, new byte[]{80});
            GattServerPageActivity.this.runOnUiThread(new RunnableS("Execute write: " + address + " execWrite: " + execWrite) {
                /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass9.AnonymousClass8 */

                @Override // java.lang.Runnable
                public void run() {
                    GattServerPageActivity.this.mLogArrayAdapter.add(this.mString);
                }
            });
        }
    };
    private AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass8 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            int contacts_id = GattServerPageActivity.this.listViewIdTable[position];
            String str = GattServerPageActivity.TAG;
            Log.d(str, "Piggy Check: position: " + position + " contacts_id: " + contacts_id);
        }
    };
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(GattServerPageActivity.TAG, "ready  onServiceConnected");
            GattServerPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (GattServerPageActivity.this.mCommand == null) {
                Log.e(GattServerPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(GattServerPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
            }
            try {
                GattServerPageActivity.this.mCommand.registerGattServerCallback(GattServerPageActivity.this.mCallbackGattServer);
                int s = GattServerPageActivity.this.mCommand.getGattServerConnectionState();
                String state = BuildConfig.FLAVOR;
                if (s == 110) {
                    state = "Ready";
                } else if (s == 130) {
                    state = "Listening";
                } else if (s == 140) {
                    state = "Connected";
                } else if (s == 100) {
                    state = "No init";
                }
                GattServerPageActivity.this.runOnUiThread(new RunnableS(state) {
                    /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass1.AnonymousClass1 */

                    @Override // java.lang.Runnable
                    public void run() {
                        GattServerPageActivity.this.text_state_gatt.setText(this.mString);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(GattServerPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(GattServerPageActivity.TAG, "onServiceDisconnected");
            GattServerPageActivity.this.mCommand = null;
        }
    };
    HeartRateNotifyThread mHeartRateNotifyThread;
    private ListView mListView;
    private ArrayAdapter<String> mLogArrayAdapter;
    private TextView text_state_gatt;

    class HeartRateNotifyThread extends Thread {
        private boolean isNeedBreak = false;
        private boolean isNeedNotify = false;
        private String mAddress = NfDef.DEFAULT_ADDRESS;
        private ParcelUuid mCharUuid = null;
        private boolean mConfirm = false;
        private int mSrvcType = -1;
        private ParcelUuid mSrvcUuid = null;

        HeartRateNotifyThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (!this.isNeedBreak) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (GattServerPageActivity.this.mCommand != null && this.isNeedNotify) {
                    try {
                        GattServerPageActivity.this.mCommand.reqGattServerSendNotification(this.mAddress, this.mSrvcType, this.mSrvcUuid, this.mCharUuid, this.mConfirm, GattServerPageActivity.this.getRandomIntegerByteArray());
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    } catch (NullPointerException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }

        public void setNeedNotify(boolean need) {
            this.isNeedNotify = need;
        }

        public void update(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm) {
            this.mAddress = address;
            this.mSrvcType = srvcType;
            this.mSrvcUuid = srvcUuid;
            this.mCharUuid = charUuid;
            this.mConfirm = confirm;
        }

        public void stopThread() {
            this.isNeedBreak = true;
            interrupt();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.gatt_server_page);
        this.mLogArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        initView();
        Intent intent = new Intent(this, BtService.class);
        startService(intent);
        bindService(intent, this.mConnection, 1);
        this.mListView.setBackgroundColor(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        this.mHeartRateNotifyThread = new HeartRateNotifyThread();
        this.mHeartRateNotifyThread.start();
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
        this.mHeartRateNotifyThread.stopThread();
        try {
            this.mCommand.unregisterGattServerCallback(this.mCallbackGattServer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(this.mConnection);
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        super.onDestroy();
    }

    public void initView() {
        this.mListView = (ListView) findViewById(R.id.list_log);
        this.mListView.setOnItemClickListener(this.mClickListener);
        this.mListView.setAdapter((ListAdapter) this.mLogArrayAdapter);
        this.mListView.setSelected(true);
        this.text_state_gatt = (TextView) findViewById(R.id.text_state_gatt_server);
        this.button_add_service = (Button) findViewById(R.id.button_add_service);
        this.button_clean_service = (Button) findViewById(R.id.button_clean_service);
        this.button_listen = (Button) findViewById(R.id.button_listen);
        this.button_stop_listen = (Button) findViewById(R.id.button_unlisten);
        this.button_clean_log = (Button) findViewById(R.id.button_clean_log);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.button_add_service.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(GattServerPageActivity.TAG, "button_add_service onClicked");
                try {
                    GattServerPageActivity.this.mCommand.reqGattServerBeginServiceDeclaration(0, GattServerPageActivity.this.Heart_Rate);
                    GattServerPageActivity.this.mCommand.reqGattServerAddCharacteristic(GattServerPageActivity.this.Heart_Rate_Measurement, 16, 16);
                    GattServerPageActivity.this.mCommand.reqGattServerAddDescriptor(GattServerPageActivity.this.Client_Characteristic_Configuration, 17);
                    GattServerPageActivity.this.mCommand.reqGattServerAddCharacteristic(GattServerPageActivity.this.Body_Sensor_Location, 2, 1);
                    GattServerPageActivity.this.mCommand.reqGattServerAddCharacteristic(GattServerPageActivity.this.Heart_Rate_Control_Point, 8, 16);
                    GattServerPageActivity.this.mCommand.reqGattServerEndServiceDeclaration();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_clean_service.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(GattServerPageActivity.TAG, "button_clean_service onClicked");
                try {
                    GattServerPageActivity.this.mCommand.reqGattServerClearServices();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_listen.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(GattServerPageActivity.TAG, "button_listen onClicked");
                try {
                    GattServerPageActivity.this.mCommand.reqGattServerListen(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_stop_listen.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(GattServerPageActivity.TAG, "button_stop_listen onClicked");
                try {
                    GattServerPageActivity.this.mCommand.reqGattServerListen(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_clean_log.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(GattServerPageActivity.TAG, "button_clean_log onClicked");
                GattServerPageActivity.this.runOnUiThread(new Runnable() {
                    /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass6.AnonymousClass1 */

                    @Override // java.lang.Runnable
                    public void run() {
                        GattServerPageActivity.this.mLogArrayAdapter.clear();
                    }
                });
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.GattServerPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(GattServerPageActivity.TAG, "button_back onClicked");
                GattServerPageActivity.this.finish();
            }
        });
    }

    public abstract class RunnableS implements Runnable {
        protected String mString = BuildConfig.FLAVOR;

        public RunnableS(String v1) {
            this.mString = v1;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private byte[] getRandomIntegerByteArray() {
        return new byte[]{(byte) new Random().nextInt(Opcodes.GETFIELD)};
    }
}
