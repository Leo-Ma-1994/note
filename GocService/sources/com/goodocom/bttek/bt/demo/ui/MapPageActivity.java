package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackMap;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.bttek.util.db.DbHelperMap;
import java.util.Date;
import java.util.Random;

public class MapPageActivity extends Activity {
    private static boolean D = true;
    private static String DATA_MESSAGE = "DATA_MESSAGE";
    private static String ENABLE_BUTTON = "ENABLE_BUTTON";
    private static final int HANDLER_EVENT_ADD_MESSAGE_TO_LIST = 3;
    private static final int HANDLER_EVENT_CLEAR_LIST = 6;
    private static final int HANDLER_EVENT_ENABLE_BUTTON = 7;
    private static final int HANDLER_EVENT_HIDE_TITLE = 5;
    private static final int HANDLER_EVENT_SET_TITLE = 4;
    private static final int HANDLER_EVENT_SHOW_TOAST = 8;
    private static final int HANDLER_EVENT_UPDATE_STATE_MAP = 1;
    private static final int HANDLER_EVENT_UPDATE_STATE_REGISTER = 2;
    private static String STATE_MAP = "STATE_MAP";
    private static String STATE_REGISTER = "STATE_REGISTER";
    private static String TAG = "nFore_MapPage";
    private static String TITLE_NAME = "TITLE_NAME";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    private String AT = "AT ";
    private String INBOX = "Inbox";
    private String OUTBOX = "Sent";
    private final String SQL_QUERY_MESSAGE_BY_ADDRESS_AND_FOLDER = "select datetime,sender_name,sender_addressing,subject,message,recipient_addressing,read from messagecontent where macAddress = ? and folder = ?order by datetime desc;";
    private String TARGET_NUMBER = null;
    private Button button_back;
    private Button button_delete;
    private Button button_delete_message;
    private Button button_inbox;
    private Button button_map_interrupt;
    private Button button_read_status;
    private Button button_register;
    private Button button_send_message;
    private Button button_sent;
    private Button button_switch_storage;
    private Button button_unregister;
    private boolean checkReadStatus = false;
    SQLiteDatabase db;
    DbHelperMap dbHelper;
    private EditText editText_target_phone_number;
    private String getHandle;
    private int handleNum = 0;
    private String hfp_connected_address;
    private Toast infoToast;
    private boolean isDownloadToDatabase = true;
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(MapPageActivity.TAG, "ready  onServiceConnected");
            MapPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (MapPageActivity.this.mCommand == null) {
                Log.e(MapPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(MapPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                MapPageActivity.this.finish();
            }
            try {
                MapPageActivity.this.mCommand.registerMapCallback(MapPageActivity.this.mMapCallback);
                MapPageActivity.this.hfp_connected_address = MapPageActivity.this.mCommand.getTargetAddress();
                if (MapPageActivity.this.hfp_connected_address.equals(NfDef.DEFAULT_ADDRESS)) {
                    MapPageActivity.this.showToast("Choose a device first !");
                }
                MapPageActivity.this.mCommand.reqMapDatabaseAvailable();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(MapPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(MapPageActivity.TAG, "onServiceDisconnected");
            MapPageActivity.this.mCommand = null;
        }
    };
    Cursor mCursor;
    private Handler mHandler = null;
    private UiCallbackMap mMapCallback = new UiCallbackMap.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass20 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapServiceReady() throws RemoteException {
            Log.i(MapPageActivity.TAG, "onMapServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
            String map_state;
            String str = MapPageActivity.TAG;
            Log.v(str, "onMapStateChanged() " + address + " " + prevState + "->" + newState + " reason: " + reason);
            if (newState == 160) {
                MapPageActivity.this.lockButton();
                map_state = "Downloading...";
            } else if (newState == 120) {
                MapPageActivity.this.unlockButton();
                map_state = "Connecting";
            } else if (newState == 140) {
                MapPageActivity.this.unlockButton();
                map_state = "Connected";
            } else if (newState == 150) {
                MapPageActivity.this.unlockButton();
                map_state = "Registered";
            } else if (newState == 170) {
                MapPageActivity.this.unlockButton();
                map_state = "Uploading...";
            } else {
                MapPageActivity.this.unlockButton();
                map_state = "Disconnected";
            }
            if (reason == 5) {
                MapPageActivity.this.showToast("Complete");
                MapPageActivity.this.mCommand.reqMapDatabaseAvailable();
            }
            MapPageActivity.this.runOnUiThread(new UiRunnable(map_state) {
                /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass20.AnonymousClass1 */

                @Override // java.lang.Runnable
                public void run() {
                    MapPageActivity.this.text_state_map.setText(this.data);
                }
            });
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String recipientNumber, String date, int type, int folder, boolean isReadStatus, String subject, String message) throws RemoteException {
            String str = MapPageActivity.TAG;
            Log.v(str, "retMapDownloadedMessage() " + address);
            String str2 = MapPageActivity.TAG;
            Log.v(str2, "handle: " + handle);
            String str3 = MapPageActivity.TAG;
            Log.v(str3, "senderName: " + senderName);
            String str4 = MapPageActivity.TAG;
            Log.v(str4, "senderNumber: " + senderNumber);
            String str5 = MapPageActivity.TAG;
            Log.v(str5, "recipientNumber: " + recipientNumber);
            String str6 = MapPageActivity.TAG;
            Log.v(str6, "date: " + date);
            String str7 = MapPageActivity.TAG;
            Log.v(str7, "type: " + type);
            String str8 = MapPageActivity.TAG;
            Log.v(str8, "folder: " + folder);
            String str9 = MapPageActivity.TAG;
            Log.v(str9, "isReadStatus: " + isReadStatus);
            String str10 = MapPageActivity.TAG;
            Log.v(str10, "subject: " + subject);
            String str11 = MapPageActivity.TAG;
            Log.v(str11, "message: " + message);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapNewMessageReceivedEvent(String address, String handle, String sender, String message) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapNewMessageReceived()");
            MapPageActivity mapPageActivity = MapPageActivity.this;
            mapPageActivity.showToast("Message Received: " + message);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapDownloadNotify()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDatabaseAvailable() throws RemoteException {
            Log.v(MapPageActivity.TAG, "retMapDatabaseAvailable()");
            if (MapPageActivity.D) {
                String str = MapPageActivity.TAG;
                Log.i(str, "retMapDatabaseAvailable " + MapPageActivity.this.hfp_connected_address);
            }
            if (MapPageActivity.this.type == -1) {
                Log.e(MapPageActivity.TAG, "Download fail ! do nothing.");
                return;
            }
            MapPageActivity mapPageActivity = MapPageActivity.this;
            mapPageActivity.updateMessageList(mapPageActivity.hfp_connected_address, MapPageActivity.this.type);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
            Log.v(MapPageActivity.TAG, "retMapDeleteDatabaseByAddressCompleted()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
            Log.v(MapPageActivity.TAG, "retMapCleanDatabaseCompleted()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapSendMessageCompleted(String address, String target, int state) throws RemoteException {
            Log.v(MapPageActivity.TAG, "retMapSendMessageCompleted()");
            String str = MapPageActivity.TAG;
            Log.v(str, "retMapSendMessageCompleted : address = " + address);
            String str2 = MapPageActivity.TAG;
            Log.v(str2, "retMapSendMessageCompleted : target = " + target);
            String str3 = MapPageActivity.TAG;
            Log.v(str3, "retMapSendMessageCompleted : state = " + state);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDeleteMessageCompleted(String address, String handle, int reason) throws RemoteException {
            Log.v(MapPageActivity.TAG, "retMapDeleteMessageCompleted()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapChangeReadStatusCompleted(String address, String handle, int reason) throws RemoteException {
            Log.v(MapPageActivity.TAG, "retMapChangeReadStatusCompleted()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMemoryAvailableEvent(String address, int structure, boolean available) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapMemoryAvailableEvent()");
            MapPageActivity mapPageActivity = MapPageActivity.this;
            StringBuilder sb = new StringBuilder();
            sb.append("Memory Available ");
            sb.append(MapPageActivity.this.getFolderString(structure));
            sb.append(" ");
            sb.append(available ? "true" : "false");
            mapPageActivity.showToast(sb.toString());
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapMessageSendingStatusEvent()");
            MapPageActivity mapPageActivity = MapPageActivity.this;
            StringBuilder sb = new StringBuilder();
            sb.append("Message Sending ");
            sb.append(isSuccess ? "success" : "fail");
            mapPageActivity.showToast(sb.toString());
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapMessageDeliverStatusEvent()");
            MapPageActivity mapPageActivity = MapPageActivity.this;
            StringBuilder sb = new StringBuilder();
            sb.append("Message Delivery ");
            sb.append(isSuccess ? "success" : "fail");
            mapPageActivity.showToast(sb.toString());
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapMessageShiftedEvent()");
            MapPageActivity mapPageActivity = MapPageActivity.this;
            mapPageActivity.showToast("Message Shifted from " + MapPageActivity.this.getFolderString(oldFolder) + " to " + MapPageActivity.this.getFolderString(newFolder));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageDeletedEvent(String address, String handle, int type, int folder) throws RemoteException {
            Log.v(MapPageActivity.TAG, "onMapMessageDeletedEvent()");
            MapPageActivity mapPageActivity = MapPageActivity.this;
            mapPageActivity.showToast("Message Deleted from " + MapPageActivity.this.getFolderString(folder));
        }
    };
    private ArrayAdapter<String> mMessagesArrayAdapter;
    private ViewGroup mViewGroup;
    String packageName;
    private TextView text_state_map;
    private TextView text_state_register;
    private TextView title_source;
    private int type = 0;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.map_page);
        this.mMessagesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        initHandler();
        initView();
        bindService(new Intent(this, BtService.class), this.mConnection, 1);
        ListView messagesListView = (ListView) findViewById(R.id.list_messages);
        messagesListView.setAdapter((ListAdapter) this.mMessagesArrayAdapter);
        messagesListView.setSelected(true);
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
            this.mCommand.unregisterMapCallback(this.mMapCallback);
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
        this.title_source = (TextView) findViewById(R.id.title_map_source);
        this.text_state_map = (TextView) findViewById(R.id.text_state_map);
        this.text_state_register = (TextView) findViewById(R.id.text_state_register);
        this.button_inbox = (Button) findViewById(R.id.button_inbox);
        this.button_sent = (Button) findViewById(R.id.button_sent);
        this.button_send_message = (Button) findViewById(R.id.button_sendMessage);
        this.button_register = (Button) findViewById(R.id.button_map_register);
        this.button_unregister = (Button) findViewById(R.id.button_map_unregister);
        this.button_delete = (Button) findViewById(R.id.button_map_delete);
        this.button_map_interrupt = (Button) findViewById(R.id.button_map_interrupt);
        this.button_switch_storage = (Button) findViewById(R.id.button_map_switch_storage);
        this.button_delete_message = (Button) findViewById(R.id.button_delete_message);
        this.button_read_status = (Button) findViewById(R.id.button_read_status);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.button_inbox.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_inbox onClicked");
                try {
                    if (MapPageActivity.this.isDownloadToDatabase) {
                        MapPageActivity.this.mCommand.reqMapDownloadMessage(MapPageActivity.this.hfp_connected_address, 0, true, -1, -1, 1, null, null, null, null, 0, 0);
                    } else {
                        MapPageActivity.this.mCommand.reqMapDownloadMessage(MapPageActivity.this.hfp_connected_address, 0, true, -1, -1, 0, null, null, null, null, 0, 0);
                    }
                    MapPageActivity.this.type = 0;
                    MapPageActivity.this.setTitleSource("Inbox");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_sent.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_sent onClicked");
                try {
                    if (MapPageActivity.this.isDownloadToDatabase) {
                        MapPageActivity.this.mCommand.reqMapDownloadMessage(MapPageActivity.this.hfp_connected_address, 1, true, -1, -1, 1, null, null, null, null, 0, 0);
                    } else {
                        MapPageActivity.this.mCommand.reqMapDownloadMessage(MapPageActivity.this.hfp_connected_address, 1, true, -1, -1, 0, null, null, null, null, 0, 0);
                    }
                    MapPageActivity.this.type = 1;
                    MapPageActivity.this.setTitleSource("Outbox");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_send_message.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MapPageActivity.this.showDialog();
            }
        });
        this.button_register.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_register onClicked");
                try {
                    MapPageActivity.this.mCommand.reqMapRegisterNotification(MapPageActivity.this.hfp_connected_address, true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_unregister.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_unregister onClicked");
                try {
                    MapPageActivity.this.mCommand.reqMapUnregisterNotification(MapPageActivity.this.hfp_connected_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_delete.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_delete onClicked");
                try {
                    MapPageActivity.this.mCommand.reqMapDeleteDatabaseByAddress(MapPageActivity.this.hfp_connected_address);
                    MapPageActivity.this.mCommand.reqMapDatabaseAvailable();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_map_interrupt.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_map_interrupt onClicked");
                try {
                    MapPageActivity.this.mCommand.reqMapDownloadInterrupt(MapPageActivity.this.hfp_connected_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_switch_storage.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_switch_storage onClicked");
                MapPageActivity mapPageActivity = MapPageActivity.this;
                mapPageActivity.isDownloadToDatabase = !mapPageActivity.isDownloadToDatabase;
                ((Button) v).setText(MapPageActivity.this.isDownloadToDatabase ? "Database" : "By Pass");
            }
        });
        this.button_delete_message.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass10 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_delete_message onClicked");
                MapPageActivity.this.showDeleteMessageDialog();
            }
        });
        this.button_read_status.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass11 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_read_status onClicked");
                MapPageActivity.this.showReadStatusDialog();
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass12 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(MapPageActivity.TAG, "button_back onClicked");
                MapPageActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setTitleSource(String source) {
        runOnUiThread(new UiRunnable(source) {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass13 */

            @Override // java.lang.Runnable
            public void run() {
                MapPageActivity.this.title_source.setVisibility(0);
                MapPageActivity.this.title_source.setText(this.data);
            }
        });
    }

    private void hideTitle() {
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass14 */

            @Override // java.lang.Runnable
            public void run() {
                MapPageActivity.this.title_source.setVisibility(8);
                MapPageActivity.this.title_source.setText(BuildConfig.FLAVOR);
            }
        });
    }

    private void clearList() {
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass15 */

            @Override // java.lang.Runnable
            public void run() {
                MapPageActivity.this.mMessagesArrayAdapter.clear();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void lockButton() {
        Log.e(TAG, "lockButton");
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass16 */

            @Override // java.lang.Runnable
            public void run() {
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void unlockButton() {
        Log.e(TAG, "unlockButton");
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass17 */

            @Override // java.lang.Runnable
            public void run() {
            }
        });
    }

    private abstract class UiRunnable implements Runnable {
        protected String data;

        public UiRunnable(String input) {
            this.data = input;
        }
    }

    private abstract class UiRunnableNum implements Runnable {
        protected int data;

        public UiRunnableNum(int input) {
            this.data = input;
        }
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass18 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String str = MapPageActivity.TAG;
                Log.v(str, "handleMessage : " + MapPageActivity.this.getHandlerEventName(msg.what));
                switch (msg.what) {
                    case 1:
                        MapPageActivity.this.text_state_map.setText(bundle.getString(MapPageActivity.STATE_MAP));
                        break;
                    case 2:
                        MapPageActivity.this.text_state_register.setText(bundle.getString(MapPageActivity.STATE_REGISTER));
                        break;
                    case 3:
                        MapPageActivity.this.mMessagesArrayAdapter.add(bundle.getString(MapPageActivity.DATA_MESSAGE));
                        break;
                    case 4:
                        MapPageActivity.this.title_source.setVisibility(0);
                        MapPageActivity.this.title_source.setText(bundle.getString(MapPageActivity.TITLE_NAME));
                        break;
                    case 5:
                        MapPageActivity.this.title_source.setVisibility(8);
                        MapPageActivity.this.title_source.setText(BuildConfig.FLAVOR);
                        break;
                    case 6:
                        MapPageActivity.this.mMessagesArrayAdapter.clear();
                        break;
                    case 7:
                        boolean enable = bundle.getString(MapPageActivity.ENABLE_BUTTON).equals("true");
                        MapPageActivity.this.button_inbox.setEnabled(enable);
                        MapPageActivity.this.button_sent.setEnabled(enable);
                        break;
                    case 8:
                        if (MapPageActivity.this.infoToast != null) {
                            MapPageActivity.this.infoToast.cancel();
                            MapPageActivity.this.infoToast.setText(bundle.getString(MapPageActivity.TOAST_MESSAGE));
                            MapPageActivity.this.infoToast.setDuration(0);
                        } else {
                            MapPageActivity.this.infoToast = Toast.makeText(MapPageActivity.this.getApplicationContext(), bundle.getString(MapPageActivity.TOAST_MESSAGE), 0);
                        }
                        MapPageActivity.this.infoToast.show();
                        break;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String message) {
        String str = TAG;
        Log.e(str, "showToast: " + message);
        runOnUiThread(new UiRunnable(message) {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass19 */

            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(MapPageActivity.this.getApplicationContext(), this.data, 1).show();
            }
        });
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
                return "HANDLER_EVENT_UPDATE_STATE_MAP";
            case 2:
                return "HANDLER_EVENT_UPDATE_STATE_REGISTER";
            case 3:
                return "HANDLER_EVENT_ADD_MESSAGE_TO_LIST";
            case 4:
                return "HANDLER_EVENT_SET_TITLE";
            case 5:
                return "HANDLER_EVENT_HIDE_TITLE";
            case 6:
                return "HANDLER_EVENT_CLEAR_LIST";
            case 7:
                return "HANDLER_EVENT_ENABLE_BUTTON";
            case 8:
                return "HANDLER_EVENT_SHOW_TOAST";
            default:
                return "Unknown Event !!";
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getFolderString(int folder) {
        if (folder == 0) {
            return "Inbox";
        }
        if (folder == 1) {
            return "Sent";
        }
        if (folder == 2) {
            return "Deleted";
        }
        if (folder == 3) {
            return "Outbox";
        }
        if (folder != 4) {
            return "Unknown";
        }
        return "Draft";
    }

    public boolean queryMessageByAddressAndFolder(String macAddress, int folder) {
        String str = TAG;
        Log.e(str, "queryMessageByAddressAndFolder " + macAddress + " folder : " + folder);
        DbHelperMap dbHelperMap = this.dbHelper;
        if (dbHelperMap != null) {
            dbHelperMap.close();
        }
        try {
            this.packageName = getContentResolver().acquireContentProviderClient(NfDef.DEFAULT_AUTHORITIES).getType(Uri.parse("content://nfore.db.provider"));
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        String str2 = TAG;
        Log.d(str2, "Package Name : " + this.packageName);
        try {
            Log.d(TAG, "start createPackageContext");
            Context _context = createPackageContext(this.packageName, 2);
            Log.d(TAG, "start new helper");
            this.dbHelper = new DbHelperMap(_context);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        this.mCursor = this.db.rawQuery("select datetime,sender_name,sender_addressing,subject,message,recipient_addressing,read from messagecontent where macAddress = ? and folder = ?order by datetime desc;", new String[]{macAddress, BuildConfig.FLAVOR + folder});
        if (this.mCursor.getCount() > 0) {
            return true;
        }
        this.mCursor.close();
        this.db.close();
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean updateMessageList(String macAddress, int folder) {
        String str = TAG;
        Log.i(str, "updateMessageList " + macAddress + " folder : " + folder);
        clearList();
        if (!queryMessageByAddressAndFolder(this.hfp_connected_address, folder)) {
            Log.e(TAG, "queryMessageByAddressAndFolder return false");
            return false;
        }
        this.mCursor.moveToFirst();
        if (this.mCursor.getCount() != 0) {
            do {
                String result_datetime = this.mCursor.getString(this.mCursor.getColumnIndex("datetime"));
                String result_name = this.mCursor.getString(this.mCursor.getColumnIndex("sender_name"));
                String result_number = this.mCursor.getString(this.mCursor.getColumnIndex("sender_addressing"));
                String result_subject = this.mCursor.getString(this.mCursor.getColumnIndex("message"));
                sendHandlerMessage(3, DATA_MESSAGE, result_datetime + "\n" + result_number + " " + result_name + "\n" + result_subject);
            } while (this.mCursor.moveToNext());
        }
        this.mCursor.close();
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        alert.setTitle("Message");
        View inputView = inflater.inflate(R.layout.map_page_edittext, (ViewGroup) null);
        alert.setView(inputView);
        final EditText message = (EditText) inputView.findViewById(R.id.edittext_message);
        final EditText target = (EditText) inputView.findViewById(R.id.edittext_target);
        alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass21 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v(MapPageActivity.TAG, "button_send_message onClicked");
                Log.v(MapPageActivity.TAG, "---- before send message ----");
                try {
                    MapPageActivity.this.mCommand.reqMapSendMessage(MapPageActivity.this.hfp_connected_address, message.getText().toString(), target.getText().toString());
                    Log.v(MapPageActivity.TAG, "****** reqMapSendMessage DONE ******");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass22 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDeleteMessageDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        alert.setTitle("Delete Message");
        this.mViewGroup = (ViewGroup) inflater.inflate(R.layout.map_page_delete_message, (ViewGroup) null);
        alert.setView(this.mViewGroup);
        final EditText handle = (EditText) this.mViewGroup.findViewById(R.id.edit_delete_message_input_handle);
        ((RadioGroup) this.mViewGroup.findViewById(R.id.radioGroup_delete_message)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass23 */

            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_inbox) {
                    MapPageActivity.this.handleNum = 0;
                    String str = MapPageActivity.TAG;
                    Log.e(str, "in showDeleteMessageDialog, handleNum = " + MapPageActivity.this.handleNum);
                } else if (checkedId == R.id.radio_sent) {
                    MapPageActivity.this.handleNum = 1;
                    String str2 = MapPageActivity.TAG;
                    Log.e(str2, "in showDeleteMessageDialog, handleNum = " + MapPageActivity.this.handleNum);
                }
            }
        });
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass24 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v(MapPageActivity.TAG, BuildConfig.FLAVOR);
                MapPageActivity.this.getHandle = handle.getText().toString();
                if (MapPageActivity.this.getHandle.length() > 0) {
                    try {
                        MapPageActivity.this.mCommand.reqMapDeleteMessage(MapPageActivity.this.hfp_connected_address, MapPageActivity.this.handleNum, MapPageActivity.this.getHandle);
                        MapPageActivity.this.handleNum = 0;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    MapPageActivity.this.sendHandlerMessage(8, MapPageActivity.TOAST_MESSAGE, "Please input handle");
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass25 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v(MapPageActivity.TAG, BuildConfig.FLAVOR);
            }
        });
        alert.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showReadStatusDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        alert.setTitle("Read Status");
        this.mViewGroup = (ViewGroup) inflater.inflate(R.layout.map_page_read_status, (ViewGroup) null);
        alert.setView(this.mViewGroup);
        final EditText handle = (EditText) this.mViewGroup.findViewById(R.id.edit_read_status_input_handle);
        ((CheckBox) this.mViewGroup.findViewById(R.id.check_read_status)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass26 */

            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MapPageActivity.this.checkReadStatus = true;
                    Log.d(MapPageActivity.TAG, "check_read_status is: checked");
                    return;
                }
                MapPageActivity.this.checkReadStatus = false;
                Log.d(MapPageActivity.TAG, "check_read_status is: unchecked");
            }
        });
        ((RadioGroup) this.mViewGroup.findViewById(R.id.radioGroup_read_status)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass27 */

            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_inbox1) {
                    MapPageActivity.this.handleNum = 0;
                } else if (checkedId == R.id.radio_sent1) {
                    MapPageActivity.this.handleNum = 1;
                }
            }
        });
        alert.setPositiveButton("Read Status", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass28 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v(MapPageActivity.TAG, BuildConfig.FLAVOR);
                MapPageActivity.this.getHandle = handle.getText().toString();
                if (MapPageActivity.this.getHandle.length() > 0) {
                    try {
                        MapPageActivity.this.mCommand.reqMapChangeReadStatus(MapPageActivity.this.hfp_connected_address, MapPageActivity.this.handleNum, MapPageActivity.this.getHandle, MapPageActivity.this.checkReadStatus);
                        MapPageActivity.this.checkReadStatus = false;
                        MapPageActivity.this.handleNum = 0;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    MapPageActivity.this.sendHandlerMessage(8, MapPageActivity.TOAST_MESSAGE, "Please input handle");
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.MapPageActivity.AnonymousClass29 */

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v(MapPageActivity.TAG, BuildConfig.FLAVOR);
            }
        });
        alert.show();
    }

    public String getRandomString(int downCase) {
        String randString = "Mars";
        Random r = new Random(new Date().getTime());
        while (true) {
            int length = downCase - 1;
            if (downCase > 0) {
                randString = randString + String.valueOf((char) (r.nextInt(26) + 97));
                downCase = length;
            } else {
                String randString2 = randString + "Mars";
                Log.e(TAG, "randString: " + randString2);
                return randString2;
            }
        }
    }
}
