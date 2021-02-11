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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackSpp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.Arrays;
import java.util.Random;

public class SppPageActivity extends Activity {
    private static boolean D = true;
    private static String DATA_MESSAGE = "DATA_MESSAGE";
    private static String DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private static String DEVICE_NAME = "DEVICE_NAME";
    private static final int HANDLER_EVENT_ADD_MESSAGE_TO_LIST = 4;
    private static final int HANDLER_EVENT_CLEAR_LIST = 5;
    private static final int HANDLER_EVENT_SHOW_TOAST = 6;
    private static final int HANDLER_EVENT_UPDATE_DEVICE_ADDRESS = 3;
    private static final int HANDLER_EVENT_UPDATE_DEVICE_NAME = 2;
    private static final int HANDLER_EVENT_UPDATE_STATE_SPP = 1;
    private static String STATE_SPP = "STATE_SPP";
    private static String TAG = "nFore_Demo_SppPage";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    private Button button_back;
    private Button button_spp_clear_list;
    private Button button_spp_connect;
    private Button button_spp_disconnect;
    private Button button_spp_send_delay_decrease;
    private Button button_spp_send_delay_increase;
    private Button button_spp_send_random_data;
    private Button button_spp_send_test_data;
    int count_D = 0;
    int count_E = 0;
    int count_I = 0;
    int count_N = 0;
    int count_X = 0;
    private String hfp_connected_address = NfDef.DEFAULT_ADDRESS;
    private Toast infoToast;
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(SppPageActivity.TAG, "ready  onServiceConnected");
            SppPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (SppPageActivity.this.mCommand == null) {
                Log.e(SppPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(SppPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                SppPageActivity.this.finish();
            }
            try {
                SppPageActivity.this.mCommand.registerSppCallback(SppPageActivity.this.mSppCallback);
                SppPageActivity.this.hfp_connected_address = SppPageActivity.this.mCommand.getTargetAddress();
                SppPageActivity.this.sendHandlerMessage(1, SppPageActivity.STATE_SPP, SppPageActivity.this.mCommand.isSppConnected(SppPageActivity.this.hfp_connected_address) ? "Connected" : "Ready");
                if (SppPageActivity.this.mCommand.isSppConnected(SppPageActivity.this.hfp_connected_address)) {
                    SppPageActivity.this.sendHandlerMessage(2, SppPageActivity.DEVICE_NAME, SppPageActivity.this.mCommand.getBtRemoteDeviceName(SppPageActivity.this.hfp_connected_address));
                }
                if (SppPageActivity.this.hfp_connected_address.equals(NfDef.DEFAULT_ADDRESS)) {
                    SppPageActivity.this.showToast("Choose a device first !");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(SppPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(SppPageActivity.TAG, "onServiceDisconnected");
            SppPageActivity.this.mCommand = null;
        }
    };
    private Handler mHandler = null;
    private ArrayAdapter<String> mMessagesArrayAdapter;
    private Thread mSendDelayThread = null;
    private UiCallbackSpp mSppCallback = new UiCallbackSpp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass11 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppServiceReady() throws RemoteException {
            Log.i(SppPageActivity.TAG, "onSppServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppStateChanged(String address, String deviceName, int prevState, int newState) throws RemoteException {
            if (SppPageActivity.D) {
                String str = SppPageActivity.TAG;
                Log.d(str, "onSppStateChanged " + address + " deviceName : " + deviceName + "  State : " + prevState + " -> " + newState);
            }
            SppPageActivity.this.spp_connected_device_name = deviceName;
            String mState = BuildConfig.FLAVOR;
            String mAddress = BuildConfig.FLAVOR;
            String mName = BuildConfig.FLAVOR;
            if (newState == 140) {
                mState = "Connected";
                mAddress = address;
                mName = deviceName;
            } else if (newState < 140) {
                mState = "Ready";
            }
            SppPageActivity.this.sendHandlerMessage(1, SppPageActivity.STATE_SPP, mState);
            SppPageActivity.this.sendHandlerMessage(3, SppPageActivity.DEVICE_ADDRESS, mAddress);
            SppPageActivity.this.sendHandlerMessage(2, SppPageActivity.DEVICE_NAME, mName);
            SppPageActivity.this.state_spp = newState;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppErrorResponse(String address, int errorCode) throws RemoteException {
            if (SppPageActivity.D) {
                String str = SppPageActivity.TAG;
                Log.d(str, "onSppErrorResponse " + address + " errorCode : " + errorCode);
            }
            SppPageActivity sppPageActivity = SppPageActivity.this;
            sppPageActivity.showToast("Error : " + errorCode);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void retSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) throws RemoteException {
            if (SppPageActivity.D) {
                String str = SppPageActivity.TAG;
                Log.d(str, "retSppConnectedDeviceAddressList totalNum : " + totalNum);
            }
            Log.i(SppPageActivity.TAG, "Spp Connected Device Address List");
            for (int i = 0; i < totalNum; i++) {
                String str2 = SppPageActivity.TAG;
                Log.i(str2, "Address : " + addressList[i] + " name : " + nameList[i]);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppDataReceived(String address, byte[] receivedData) throws RemoteException {
            String tempResult = new String(receivedData);
            if (SppPageActivity.D) {
                String str = SppPageActivity.TAG;
                Log.d(str, "onSppDataReceived " + address + " Spp Data : " + tempResult);
            }
            SppPageActivity sppPageActivity = SppPageActivity.this;
            sppPageActivity.addMessgeToList(sppPageActivity.spp_connected_device_name, tempResult);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppSendData(String address, int length) throws RemoteException {
            if (SppPageActivity.D) {
                String str = SppPageActivity.TAG;
                Log.d(str, "onSppSendData " + address + " length : " + length);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppAppleIapAuthenticationRequest(String address) throws RemoteException {
            if (SppPageActivity.D) {
                String str = SppPageActivity.TAG;
                Log.d(str, "onSppAppleIapAuthenticationRequest " + address);
            }
        }
    };
    private int send_delay = 0;
    private String spp_connected_device_name = BuildConfig.FLAVOR;
    private int state_spp = -1;
    private TextView text_D;
    private TextView text_E;
    private TextView text_I;
    private TextView text_N;
    private TextView text_X;
    private TextView text_spp_connected_address;
    private TextView text_spp_count;
    private TextView text_spp_device_name;
    private TextView text_spp_send_delay;
    private TextView text_state_spp;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.spp_page);
        this.mMessagesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
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
        Thread thread = this.mSendDelayThread;
        if (thread != null) {
            thread.interrupt();
            this.mSendDelayThread = null;
        }
        try {
            this.mCommand.unregisterSppCallback(this.mSppCallback);
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

    public void initView() {
        this.text_state_spp = (TextView) findViewById(R.id.text_state_spp);
        this.text_spp_device_name = (TextView) findViewById(R.id.text_spp_device_name);
        this.text_spp_connected_address = (TextView) findViewById(R.id.text_spp_connected_address);
        this.text_spp_send_delay = (TextView) findViewById(R.id.text_spp_send_delay);
        TextView textView = this.text_spp_send_delay;
        textView.setText(BuildConfig.FLAVOR + this.send_delay);
        this.text_spp_count = (TextView) findViewById(R.id.text_spp_count);
        this.text_I = (TextView) findViewById(R.id.text_I);
        this.text_N = (TextView) findViewById(R.id.text_N);
        this.text_D = (TextView) findViewById(R.id.text_D);
        this.text_E = (TextView) findViewById(R.id.text_E);
        this.text_X = (TextView) findViewById(R.id.text_X);
        this.button_spp_connect = (Button) findViewById(R.id.button_spp_connect);
        this.button_spp_disconnect = (Button) findViewById(R.id.button_spp_disconnect);
        this.button_spp_send_test_data = (Button) findViewById(R.id.button_spp_send_test_data);
        this.button_spp_send_random_data = (Button) findViewById(R.id.button_spp_send_random_data);
        this.button_spp_clear_list = (Button) findViewById(R.id.button_spp_clear_list);
        this.button_spp_send_delay_increase = (Button) findViewById(R.id.button_spp_send_delay_increase);
        this.button_spp_send_delay_decrease = (Button) findViewById(R.id.button_spp_send_delay_decrease);
        this.button_back = (Button) findViewById(R.id.button_back);
        ListView messagesListView = (ListView) findViewById(R.id.list_spp_messages);
        messagesListView.setAdapter((ListAdapter) this.mMessagesArrayAdapter);
        messagesListView.setSelected(true);
        if (this.button_spp_connect == null) {
            Log.e(TAG, "button_spp_connect is null");
        }
        this.button_spp_connect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_spp_connect onClicked");
                try {
                    SppPageActivity.this.mCommand.reqSppConnect(SppPageActivity.this.hfp_connected_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_spp_disconnect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_spp_disconnect onClicked");
                try {
                    SppPageActivity.this.mCommand.reqSppDisconnect(SppPageActivity.this.hfp_connected_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_spp_send_test_data.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_spp_send_test_data onClicked");
                if (SppPageActivity.this.mSendDelayThread == null) {
                    SppPageActivity.this.mSendDelayThread = new Thread(new Runnable() {
                        /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass4.AnonymousClass1 */

                        @Override // java.lang.Runnable
                        public void run() {
                            for (int i = 0; i < 10000; i++) {
                                try {
                                    Thread.sleep((long) SppPageActivity.this.send_delay);
                                    byte[] output = SppPageActivity.this.getTestData(i, 512);
                                    SppPageActivity.this.mCommand.reqSppSendData(SppPageActivity.this.hfp_connected_address, output);
                                    if (SppPageActivity.this.state_spp == 140) {
                                        SppPageActivity.this.addMessgeToList("nFore", new String(output));
                                    }
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            SppPageActivity.this.mSendDelayThread = null;
                        }
                    });
                    SppPageActivity.this.mSendDelayThread.start();
                    return;
                }
                Log.e(SppPageActivity.TAG, "mSendDelayThread is not null !");
                SppPageActivity.this.mSendDelayThread.interrupt();
            }
        });
        this.button_spp_send_random_data.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_spp_send_random_data onClicked");
                try {
                    byte[] output = SppPageActivity.this.getRandomData();
                    if (SppPageActivity.this.state_spp == 140) {
                        SppPageActivity.this.mCommand.reqSppSendData(SppPageActivity.this.hfp_connected_address, output);
                        SppPageActivity.this.addMessgeToList("nFore", new String(output));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_spp_send_delay_increase.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_spp_send_delay_increase onClicked");
                SppPageActivity.this.send_delay += 10;
                SppPageActivity.this.text_spp_send_delay.setText(BuildConfig.FLAVOR + SppPageActivity.this.send_delay);
            }
        });
        this.button_spp_send_delay_decrease.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_spp_send_delay_decrease onClicked");
                if (SppPageActivity.this.send_delay != 0) {
                    SppPageActivity sppPageActivity = SppPageActivity.this;
                    sppPageActivity.send_delay -= 10;
                    SppPageActivity.this.text_spp_send_delay.setText(BuildConfig.FLAVOR + SppPageActivity.this.send_delay);
                }
            }
        });
        this.button_spp_clear_list.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SppPageActivity.this.clearList();
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(SppPageActivity.TAG, "button_back onClicked");
                SppPageActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void addMessgeToList(String sender, String message) {
        sendHandlerMessage(4, DATA_MESSAGE, message);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearList() {
        sendHandlerMessage(5, null, null);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private byte[] getTestData(int index, int length) {
        Log.e(TAG, "length : " + length);
        String dataStart = "Index: " + index + " |";
        byte[] temp = new byte[(length - (dataStart.length() + "|\n".length()))];
        Arrays.fill(temp, (byte) 42);
        String result = dataStart + new String(temp) + "|\n";
        Log.e(TAG, "random data : " + new String(result));
        return result.getBytes();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private byte[] getRandomData() {
        int randomLength = new Random().nextInt(200) + 55;
        Log.e(TAG, "randomLength : " + randomLength);
        String dataStart = "| Random Data Length : " + randomLength + "|";
        String dataEnd = "| End Of Data Length : " + randomLength + "|";
        byte[] temp = new byte[(randomLength - (dataStart.length() + dataEnd.length()))];
        Arrays.fill(temp, (byte) 42);
        String result = dataStart + new String(temp) + dataEnd;
        Log.e(TAG, "random data : " + new String(result));
        return result.getBytes();
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.SppPageActivity.AnonymousClass10 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Log.v(SppPageActivity.TAG, "handleMessage : " + SppPageActivity.this.getHandlerEventName(msg.what));
                switch (msg.what) {
                    case 1:
                        SppPageActivity.this.text_state_spp.setText(bundle.getString(SppPageActivity.STATE_SPP));
                        break;
                    case 2:
                        SppPageActivity.this.text_spp_device_name.setText(bundle.getString(SppPageActivity.DEVICE_NAME));
                        break;
                    case 3:
                        SppPageActivity.this.text_spp_connected_address.setText(bundle.getString(SppPageActivity.DEVICE_ADDRESS));
                        break;
                    case 4:
                        String readMessage = bundle.getString(SppPageActivity.DATA_MESSAGE);
                        SppPageActivity.this.mMessagesArrayAdapter.add(readMessage);
                        SppPageActivity.this.text_spp_count.setText(BuildConfig.FLAVOR + SppPageActivity.this.mMessagesArrayAdapter.getCount());
                        if (SppPageActivity.this.mMessagesArrayAdapter.getCount() > 100) {
                            SppPageActivity.this.mMessagesArrayAdapter.clear();
                        }
                        SppPageActivity.this.count_I += SppPageActivity.this.containsCount(readMessage, "I");
                        SppPageActivity.this.text_I.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_I);
                        SppPageActivity sppPageActivity = SppPageActivity.this;
                        sppPageActivity.count_N = sppPageActivity.count_N + SppPageActivity.this.containsCount(readMessage, "n");
                        SppPageActivity.this.text_N.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_N);
                        SppPageActivity sppPageActivity2 = SppPageActivity.this;
                        sppPageActivity2.count_D = sppPageActivity2.count_D + SppPageActivity.this.containsCount(readMessage, "d");
                        SppPageActivity.this.text_D.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_D);
                        SppPageActivity sppPageActivity3 = SppPageActivity.this;
                        sppPageActivity3.count_E = sppPageActivity3.count_E + SppPageActivity.this.containsCount(readMessage, "e");
                        SppPageActivity.this.text_E.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_E);
                        SppPageActivity sppPageActivity4 = SppPageActivity.this;
                        sppPageActivity4.count_X = sppPageActivity4.count_X + SppPageActivity.this.containsCount(readMessage, "x");
                        SppPageActivity.this.text_X.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_X);
                        break;
                    case 5:
                        SppPageActivity.this.mMessagesArrayAdapter.clear();
                        SppPageActivity.this.text_spp_count.setText(BuildConfig.FLAVOR + SppPageActivity.this.mMessagesArrayAdapter.getCount());
                        SppPageActivity.this.count_I = 0;
                        SppPageActivity.this.count_N = 0;
                        SppPageActivity.this.count_D = 0;
                        SppPageActivity.this.count_E = 0;
                        SppPageActivity.this.count_X = 0;
                        SppPageActivity.this.text_I.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_I);
                        SppPageActivity.this.text_N.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_N);
                        SppPageActivity.this.text_D.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_D);
                        SppPageActivity.this.text_E.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_E);
                        SppPageActivity.this.text_X.setText(BuildConfig.FLAVOR + SppPageActivity.this.count_X);
                        break;
                    case 6:
                        if (SppPageActivity.this.infoToast != null) {
                            SppPageActivity.this.infoToast.cancel();
                            SppPageActivity.this.infoToast.setText(bundle.getString(SppPageActivity.TOAST_MESSAGE));
                            SppPageActivity.this.infoToast.setDuration(0);
                        } else {
                            SppPageActivity.this.infoToast = Toast.makeText(SppPageActivity.this.getApplicationContext(), bundle.getString(SppPageActivity.TOAST_MESSAGE), 0);
                        }
                        SppPageActivity.this.infoToast.show();
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
        sendHandlerMessage(6, TOAST_MESSAGE, message);
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
                return "HANDLER_EVENT_UPDATE_STATE_SPP";
            case 2:
                return "HANDLER_EVENT_UPDATE_DEVICE_NAME";
            case 3:
                return "HANDLER_EVENT_UPDATE_DEVICE_ADDRESS";
            case 4:
                return "HANDLER_EVENT_ADD_MESSAGE_TO_LIST";
            case 5:
                return "HANDLER_EVENT_CLEAR_LIST";
            case 6:
                return "HANDLER_EVENT_SHOW_TOAST";
            default:
                return "Unknown Event !!";
        }
    }

    public int containsCount(String string, String substring) {
        int count = 0;
        int idx = 0;
        while (true) {
            int idx2 = string.indexOf(substring, idx);
            if (idx2 == -1) {
                return count;
            }
            idx = idx2 + 1;
            count++;
        }
    }
}
