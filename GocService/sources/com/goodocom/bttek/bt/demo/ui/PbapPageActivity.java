package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.GocPbapContact;
import com.goodocom.bttek.bt.aidl.UiCallbackPbap;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.bttek.util.db.DbHelperPbap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PbapPageActivity extends Activity {
    private static boolean D = true;
    private static String DATA_VCARD = "DATA_VCARD";
    private static final int DOWNLOAD_TYPE_BY_PASS = 0;
    private static final int DOWNLOAD_TYPE_TO_CONTACTS_PROVIDER = 2;
    private static final int DOWNLOAD_TYPE_TO_DATABASE = 1;
    private static String ENABLE_BUTTON = "ENABLE_BUTTON";
    private static final int HANDLER_EVENT_ADD_VCARD_TO_BY_PASS_LIST = 9;
    private static final int HANDLER_EVENT_ADD_VCARD_TO_LIST = 2;
    private static final int HANDLER_EVENT_CLEAR_LIST = 6;
    private static final int HANDLER_EVENT_ENABLE_BUTTON = 7;
    private static final int HANDLER_EVENT_HIDE_TITLE = 5;
    private static final int HANDLER_EVENT_IS_CONTACTS_PROVIDER = 4;
    private static final int HANDLER_EVENT_SET_TITLE = 3;
    private static final int HANDLER_EVENT_SHOW_TOAST = 8;
    private static final int HANDLER_EVENT_UPDATE_BY_PASS_VCARD_TO_LIST = 10;
    private static final int HANDLER_EVENT_UPDATE_STATE_PBAP = 1;
    private static String IS_CONTACTS_PROVIDER = "IS_CONTACTS_PROVIDER";
    private static String STATE_PBAP = "STATE_PBAP";
    private static String TAG = "NfDemo_PbapPage";
    private static String TITLE_NAME = "TITLE_NAME";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    static ArrayList<String[]> mAddressTable = new ArrayList<>();
    private static int mDownloadType = 0;
    static ArrayList<String[]> mEmailTable = new ArrayList<>();
    static int mIdCount = 0;
    static ArrayList<String[]> mNumberTable = new ArrayList<>();
    static ArrayList<String> mOrgTable = new ArrayList<>();
    private final String SQL_QUERY_ADDRESS_BY_ID = "select * from AddressDetail where Content_ID = ?;";
    private final String SQL_QUERY_CALLHISTORY_BY_ADDRESS_AND_TYPE = "select fullname , phonenumber , historydate , historytime from callhistory where CellPhone_Address = ? and storagetype = ? order by 3 desc , 4 desc;";
    private final String SQL_QUERY_EMAIL_BY_ID = "select * from EmailDetail where Content_ID = ?;";
    private final String SQL_QUERY_NUMBER_BY_ID = "select * from PhoneNumberDetail where Content_ID = ?;";
    private final String SQL_QUERY_PHONEBOOK_BY_ADDRESS_AND_TYPE = "select a._id,a.FullName,a.StorageType from PhoneBookcontent a where a.fullname in (select fullname from phonebookcontent where cellphone_address  = ? and StorageType= ? group by fullname) order by fullname;";
    private Button button_back;
    private Button button_combined;
    private Button button_dial;
    private Button button_download_range;
    private Button button_favorite;
    private Button button_memory;
    private Button button_missed;
    private Button button_pbap_interrupt;
    private Button button_received;
    private Button button_sim;
    private Button button_storage_switch;
    private AdapterView.OnItemClickListener contactsClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass14 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            int i = PbapPageActivity.mDownloadType;
            if (i == 0) {
                PbapPageActivity.this.showDetailDialog(PbapPageActivity.getNumberArrayFromTable(position), PbapPageActivity.getEmailArrayFromTable(position), PbapPageActivity.getAddressArrayFromTable(position), PbapPageActivity.getOrgFromTable(position));
            } else if (i == 1) {
                int contacts_id = PbapPageActivity.this.listViewIdTable[position];
                String str = PbapPageActivity.TAG;
                Log.d(str, "Piggy Check: position: " + position + " contacts_id: " + contacts_id);
                PbapPageActivity.this.showDetailDialog(PbapPageActivity.this.getNumberArrayFromCursor(contacts_id), PbapPageActivity.this.getEmailArrayFromCursor(contacts_id), PbapPageActivity.this.getAddressArrayFromCursor(contacts_id), PbapPageActivity.getOrgFromTable(position));
            }
        }
    };
    SQLiteDatabase db;
    DbHelperPbap dbHelper;
    private String hfp_connected_address;
    private Toast infoToast;
    private boolean isDownloadRange = false;
    private int[] listViewIdTable;
    private UiCallbackPbap mCallbackPbap = new UiCallbackPbap.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass15 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void onPbapServiceReady() throws RemoteException {
            Log.i(PbapPageActivity.TAG, "onPbapServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void onPbapStateChanged(String address, int prevState, int newState, int reason, int counts) throws RemoteException {
            Log.i(PbapPageActivity.TAG, "onPbapStateChanged() " + address + " state: " + prevState + "->" + newState + " reason: " + reason);
            PbapPageActivity.this.enableButton(newState != 140);
            String state = BuildConfig.FLAVOR;
            if (reason == 1) {
                state = "Complete(" + counts + ")";
                if (PbapPageActivity.mDownloadType == 1) {
                    PbapPageActivity.this.mCommand.reqPbapDatabaseAvailable(PbapPageActivity.this.hfp_connected_address);
                }
            } else if (reason == 2) {
                state = "Fail";
                PbapPageActivity.this.hideTitle();
                PbapPageActivity.this.type = 9;
            } else if (reason == 3) {
                PbapPageActivity.this.enableButton(true);
                state = "Timeout";
                PbapPageActivity.this.type = 9;
                PbapPageActivity.this.hideTitle();
            } else if (newState == 110) {
                state = "Ready";
            } else if (newState == 142) {
                state = "Update";
            } else if (newState == 140) {
                state = "Downloading";
            }
            if (PbapPageActivity.this.isDownloadRange && newState == 110 && PbapPageActivity.this.mDownloadRangeCount % 10 != 0) {
                Log.d(PbapPageActivity.TAG, "Piggy Check download finished. count: " + PbapPageActivity.this.mDownloadRangeCount);
                PbapPageActivity.this.isDownloadRange = false;
            }
            if (newState <= 110) {
                PbapPageActivity.this.runOnUiThread(new Runnable() {
                    /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass15.AnonymousClass1 */

                    @Override // java.lang.Runnable
                    public void run() {
                        if (PbapPageActivity.this.mProgressDialog != null) {
                            PbapPageActivity.this.mProgressDialog.hide();
                        } else {
                            Log.e(PbapPageActivity.TAG, "mProgressDialog is null !!");
                        }
                    }
                });
            }
            PbapPageActivity.this.sendHandlerMessage(1, PbapPageActivity.STATE_PBAP, state);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
            String str = PbapPageActivity.TAG;
            Log.i(str, "retPbapDatabaseQueryNameByNumber() " + address + " target: " + target);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) throws RemoteException {
            String str = PbapPageActivity.TAG;
            Log.i(str, "retPbapDatabaseQueryNameByPartialNumber() " + address + " target: " + target);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseAvailable(String address) throws RemoteException {
            String str = PbapPageActivity.TAG;
            Log.i(str, "retPbapDatabaseAvailable() " + address);
            if (PbapPageActivity.this.type == 9) {
                Log.e(PbapPageActivity.TAG, "Not download yet.");
            } else if (PbapPageActivity.this.type == 1 || PbapPageActivity.this.type == 2) {
                PbapPageActivity pbapPageActivity = PbapPageActivity.this;
                pbapPageActivity.updatePhonebookList(address, pbapPageActivity.type);
            } else if (PbapPageActivity.this.type >= 5 && PbapPageActivity.this.type <= 7) {
                PbapPageActivity pbapPageActivity2 = PbapPageActivity.this;
                pbapPageActivity2.updateCallHistoryList(address, pbapPageActivity2.type);
            }
            PbapPageActivity.this.enableButton(true);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
            String str = PbapPageActivity.TAG;
            Log.i(str, "retPbapDeleteDatabaseByAddressCompleted() " + address + " isSuccess: " + isSuccess);
            try {
                PbapPageActivity.this.updatePhonebookList(PbapPageActivity.this.hfp_connected_address, 1);
                Thread.sleep(500);
                PbapPageActivity.this.updatePhonebookList(PbapPageActivity.this.hfp_connected_address, 2);
                Thread.sleep(500);
                PbapPageActivity.this.updateCallHistoryList(PbapPageActivity.this.hfp_connected_address, 5);
                Thread.sleep(500);
                PbapPageActivity.this.updateCallHistoryList(PbapPageActivity.this.hfp_connected_address, 7);
                Thread.sleep(500);
                PbapPageActivity.this.updateCallHistoryList(PbapPageActivity.this.hfp_connected_address, 6);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
            String str = PbapPageActivity.TAG;
            Log.i(str, "retPbapCleanDatabaseCompleted() isSuccess: " + isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDownloadedContact(GocPbapContact contact) throws RemoteException {
            String number = BuildConfig.FLAVOR;
            if (contact.getNumberArray() != null && contact.getNumberArray().length > 0) {
                number = contact.getNumberArray()[0];
            }
            String str = PbapPageActivity.TAG;
            Log.d(str, "<=retPbapDownloadedContact() " + PbapPageActivity.getIdCount());
            String str2 = PbapPageActivity.TAG;
            Log.d(str2, "contact:" + contact.getFirstName());
            if (contact.getPhotoByteArray() != null && contact.getPhotoByteArray().length > 0) {
                PbapPageActivity pbapPageActivity = PbapPageActivity.this;
                pbapPageActivity.createPhotoFile("Photo_" + number, contact.getPhotoByteArray());
            }
            if (PbapPageActivity.this.isDownloadRange) {
                PbapPageActivity.access$1608(PbapPageActivity.this);
                String str3 = PbapPageActivity.TAG;
                Log.e(str3, "Piggy Check download count: " + PbapPageActivity.this.mDownloadRangeCount);
            }
            Log.d(PbapPageActivity.TAG, "->HANDLER_EVENT_UPDATE_BY_PASS_VCARD_TO_LIST");
            PbapPageActivity.this.sendHandlerMessageWithContact(10, PbapPageActivity.DATA_VCARD, contact);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) throws RemoteException {
            String str = PbapPageActivity.TAG;
            Log.i(str, "retPbapDownloadedCallLog() " + lastName + " (" + number + ") " + timestamp);
            PbapPageActivity.this.sendHandlerMessage(9, PbapPageActivity.DATA_VCARD, BuildConfig.FLAVOR + timestamp + "\n" + firstName + " " + middleName + " " + lastName + " (" + number + ")");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void onPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) throws RemoteException {
            String title;
            String str = PbapPageActivity.TAG;
            Log.i(str, "onPbapDownloadNotify ()" + address + " storage: " + storage + " downloaded: " + downloadedContacts + "/" + totalContacts);
            if (totalContacts > 0) {
                if (storage == 1) {
                    title = "SIM";
                } else if (storage == 2) {
                    title = "Memory";
                } else if (storage == 5) {
                    title = "Missed";
                } else if (storage == 6) {
                    title = "Incoming";
                } else if (storage == 7) {
                    title = "Outgoing";
                } else if (storage != 8) {
                    title = "Unknown";
                } else {
                    title = "CallLogs";
                }
                PbapPageActivity.this.runOnUiThread(new RunnableSII(title, totalContacts, downloadedContacts) {
                    /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass15.AnonymousClass2 */

                    @Override // java.lang.Runnable
                    public void run() {
                        String str = PbapPageActivity.TAG;
                        Log.e(str, "Piggy Check " + this.mString + ": " + this.mInt2 + "/" + this.mInt1);
                        if (PbapPageActivity.this.mProgressDialog != null) {
                            PbapPageActivity.this.mProgressDialog.setProgressStyle(1);
                            PbapPageActivity.this.mProgressDialog.setTitle(this.mString);
                            PbapPageActivity.this.mProgressDialog.setMax(this.mInt1);
                            PbapPageActivity.this.mProgressDialog.setProgress(this.mInt2);
                            PbapPageActivity.this.mProgressDialog.show();
                            return;
                        }
                        Log.e(PbapPageActivity.TAG, "mProgressDialog is null !!");
                    }
                });
            }
        }
    };
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(PbapPageActivity.TAG, "ready  onServiceConnected");
            PbapPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (PbapPageActivity.this.mCommand == null) {
                Log.e(PbapPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(PbapPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
            }
            try {
                PbapPageActivity.this.mCommand.registerPbapCallback(PbapPageActivity.this.mCallbackPbap);
                PbapPageActivity.this.hfp_connected_address = PbapPageActivity.this.mCommand.getTargetAddress();
                if (PbapPageActivity.this.hfp_connected_address.equals(NfDef.DEFAULT_ADDRESS)) {
                    PbapPageActivity.this.showToast("Choose a device first !");
                }
                PbapPageActivity.this.mCommand.setPbapDownloadNotify(1);
                int s = PbapPageActivity.this.mCommand.getPbapConnectionState();
                String state = BuildConfig.FLAVOR;
                if (s == 110) {
                    state = "Ready";
                } else if (s == 160) {
                    state = "Downloading";
                } else if (s == 142) {
                    state = "Updating";
                } else if (s == 100) {
                    state = "No init";
                }
                PbapPageActivity.this.sendHandlerMessage(1, PbapPageActivity.STATE_PBAP, state);
                if (PbapPageActivity.mDownloadType == 1) {
                    PbapPageActivity.this.mCommand.reqPbapDatabaseAvailable(PbapPageActivity.this.hfp_connected_address);
                }
                if (PbapPageActivity.mDownloadType == 0) {
                    PbapPageActivity.this.populateByPassList();
                } else {
                    PbapPageActivity.this.populateContactList();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(PbapPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(PbapPageActivity.TAG, "onServiceDisconnected");
            PbapPageActivity.this.mCommand = null;
        }
    };
    Cursor mCursor;
    private int mDownloadRangeCount = 0;
    private Handler mHandler = null;
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private int mProperty = 65967;
    private ArrayAdapter<String> mVCardsArrayAdapter;
    private ArrayAdapter<String> mVCardsByPassArrayAdapter;
    String packageName;
    private TextView text_state_pbap;
    private TextView title_source;
    private int type = 9;

    static /* synthetic */ int access$1608(PbapPageActivity x0) {
        int i = x0.mDownloadRangeCount;
        x0.mDownloadRangeCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$708() {
        int i = mDownloadType;
        mDownloadType = i + 1;
        return i;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.pbap_page);
        this.mVCardsArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        this.mVCardsByPassArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        initHandler();
        initView();
        bindService(new Intent(this, BtService.class), this.mConnection, 1);
        setStorageType(mDownloadType);
        getContentResolver().delete(ContactsContract.Data.CONTENT_URI, null, null);
        this.mProgressDialog = new ProgressDialog(this);
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
            if (this.mCommand != null) {
                this.mCommand.unregisterPbapCallback(this.mCallbackPbap);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(this.mConnection);
        this.mHandler = null;
        this.mProgressDialog = null;
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        super.onDestroy();
    }

    public void initView() {
        this.mListView = (ListView) findViewById(R.id.list_vcards);
        this.mListView.setOnItemClickListener(this.contactsClickListener);
        this.title_source = (TextView) findViewById(R.id.title_source);
        this.text_state_pbap = (TextView) findViewById(R.id.text_state_pbap);
        this.button_sim = (Button) findViewById(R.id.button_sim);
        this.button_memory = (Button) findViewById(R.id.button_memory);
        this.button_dial = (Button) findViewById(R.id.button_dial);
        this.button_missed = (Button) findViewById(R.id.button_missed);
        this.button_received = (Button) findViewById(R.id.button_received);
        this.button_combined = (Button) findViewById(R.id.button_combined);
        this.button_favorite = (Button) findViewById(R.id.button_favorite);
        this.button_storage_switch = (Button) findViewById(R.id.button_pbap_storage_switch);
        this.button_pbap_interrupt = (Button) findViewById(R.id.button_pbap_interrupt);
        this.button_download_range = (Button) findViewById(R.id.button_pbap_download_range);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.button_sim.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_sim onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 1, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 1, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 1, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateContactList();
                    }
                    PbapPageActivity.this.type = 1;
                    PbapPageActivity.this.setTitleSource("SIM");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_memory.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_memory onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 2, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 2, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 2, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateContactList();
                    }
                    PbapPageActivity.this.type = 2;
                    PbapPageActivity.this.setTitleSource("Memory");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_dial.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_dial onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 7, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 7, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 7, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateCallLogList();
                    }
                    PbapPageActivity.this.type = 7;
                    PbapPageActivity.this.setTitleSource("Outgoing Call");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_missed.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_missed onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 5, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 5, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 5, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateCallLogList();
                    }
                    PbapPageActivity.this.type = 5;
                    PbapPageActivity.this.setTitleSource("Missed Call");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_received.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_received onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 6, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 6, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 6, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateCallLogList();
                    }
                    PbapPageActivity.this.type = 6;
                    PbapPageActivity.this.setTitleSource("Incoming Call");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_combined.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_combined onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 8, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 8, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 8, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateCallLogList();
                    }
                    PbapPageActivity.this.type = 8;
                    PbapPageActivity.this.setTitleSource("Combine CallLog");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_favorite.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_favorite onClicked");
                try {
                    PbapPageActivity.this.clearList();
                    int i = PbapPageActivity.mDownloadType;
                    if (i == 0) {
                        PbapPageActivity.resetIdCount();
                        PbapPageActivity.this.populateByPassList();
                        PbapPageActivity.this.mCommand.reqPbapDownload(PbapPageActivity.this.hfp_connected_address, 4, PbapPageActivity.this.mProperty);
                    } else if (i == 1) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToDatabase(PbapPageActivity.this.hfp_connected_address, 4, PbapPageActivity.this.mProperty);
                    } else if (i == 2) {
                        PbapPageActivity.this.mCommand.reqPbapDownloadToContactsProvider(PbapPageActivity.this.hfp_connected_address, 4, PbapPageActivity.this.mProperty);
                        PbapPageActivity.this.populateCallLogList();
                    }
                    PbapPageActivity.this.type = 4;
                    PbapPageActivity.this.setTitleSource("Favorite");
                    PbapPageActivity.this.enableButton(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_storage_switch.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_storage_switch onClicked");
                PbapPageActivity.access$708();
                if (PbapPageActivity.mDownloadType > 2) {
                    int unused = PbapPageActivity.mDownloadType = 0;
                }
                PbapPageActivity.this.setStorageType(PbapPageActivity.mDownloadType);
                if (PbapPageActivity.mDownloadType == 1) {
                    try {
                        PbapPageActivity.this.mCommand.reqPbapDatabaseAvailable(PbapPageActivity.this.hfp_connected_address);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    PbapPageActivity.this.populateContactList();
                }
            }
        });
        this.button_pbap_interrupt.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass10 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_pbap_interrupt onClicked");
                try {
                    PbapPageActivity.this.mCommand.reqPbapDownloadInterrupt(PbapPageActivity.this.hfp_connected_address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_download_range.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass11 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_download_range onClicked");
                if (PbapPageActivity.this.isDownloadRange) {
                    Log.d(PbapPageActivity.TAG, "Download range already downloading.");
                    return;
                }
                PbapPageActivity.this.isDownloadRange = true;
                PbapPageActivity.this.mDownloadRangeCount = 0;
                try {
                    PbapPageActivity.this.mCommand.reqPbapDownloadRange(PbapPageActivity.this.hfp_connected_address, 2, PbapPageActivity.this.mProperty, 0, 10);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PbapPageActivity.this.enableButton(true);
                }
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass12 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(PbapPageActivity.TAG, "button_back onClicked");
                PbapPageActivity.this.finish();
            }
        });
    }

    public void setTitleSource(String source) {
        sendHandlerMessage(3, TITLE_NAME, source);
    }

    public void setStorageType(int downloadType) {
        String s = BuildConfig.FLAVOR;
        if (downloadType == 0) {
            s = "By Pass";
            populateDatabasesList();
        } else if (downloadType == 1) {
            s = "Databases";
            populateDatabasesList();
        } else if (downloadType == 2) {
            s = "Contacts Provider ";
            int i = this.type;
            if (i == 1 || i == 2) {
                populateContactList();
            } else if (i < 5 || i > 7) {
                String str = TAG;
                Log.e(str, "Piggy Check populate get unknow type : " + this.type);
            } else {
                populateCallLogList();
            }
        }
        sendHandlerMessage(4, IS_CONTACTS_PROVIDER, s);
    }

    public void hideTitle() {
        sendHandlerMessage(5, null, null);
    }

    public void clearList() {
        sendHandlerMessage(6, null, null);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void enableButton(boolean enable) {
        sendHandlerMessage(7, ENABLE_BUTTON, enable ? "true" : "false");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populateByPassList() {
        this.mListView.setAdapter((ListAdapter) this.mVCardsByPassArrayAdapter);
        this.mListView.setSelected(true);
    }

    private void populateDatabasesList() {
        this.mListView.setAdapter((ListAdapter) this.mVCardsArrayAdapter);
        this.mListView.setSelected(true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populateContactList() {
        this.mListView.setAdapter((ListAdapter) new SimpleCursorAdapter(this, R.layout.contact_entry, getContacts(), new String[]{"display_name", "data1"}, new int[]{R.id.contactEntryTextName, R.id.contactEntryTextNumber}));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populateCallLogList() {
        this.mListView.setAdapter((ListAdapter) new SimpleCursorAdapter(this, R.layout.contact_entry, getAllCallLog(), new String[]{"date", "name", "number"}, new int[]{R.id.contactEntryTextDate, R.id.contactEntryTextName, R.id.contactEntryTextNumber}));
    }

    private Cursor getContacts() {
        Log.d(TAG, "getContacts()");
        Cursor mCursor2 = getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{"_id", "display_name", "data1", "data2", "data5", "data3", "data1"}, "in_visible_group = '1'", null, "display_name COLLATE LOCALIZED ASC");
        Log.e(TAG, "===== Contacts list start ! =====");
        mCursor2.moveToFirst();
        if (mCursor2.getCount() != 0) {
            do {
                String result = mCursor2.getString(mCursor2.getColumnIndex("_id"));
                int i = mCursor2.getColumnIndex("display_name");
                String result2 = result + " | " + mCursor2.getString(i);
                int i2 = mCursor2.getColumnIndex("data1");
                String str = TAG;
                Log.e(str, "Contacts : " + (result2 + " | " + mCursor2.getString(i2)));
                String result3 = mCursor2.getString(mCursor2.getColumnIndex("data1"));
                Log.e(TAG, "DISPLAY_NAME: " + result3);
                String result4 = mCursor2.getString(mCursor2.getColumnIndex("data2"));
                Log.e(TAG, "GIVEN_NAME: " + result4);
                String result5 = mCursor2.getString(mCursor2.getColumnIndex("data5"));
                Log.e(TAG, "MIDDLE_NAME: " + result5);
                String result6 = mCursor2.getString(mCursor2.getColumnIndex("data3"));
                Log.e(TAG, "FAMILY_NAME: " + result6);
            } while (mCursor2.moveToNext());
        }
        Log.e(TAG, "===== Contacts list ended ! =====");
        return mCursor2;
    }

    private Cursor getAllCallLog() {
        Log.d(TAG, "getAllCallLog()");
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] strFields = {"_id", "date", "number", "type", "duration", "new", "name", "numbertype", "date", "numberlabel"};
        int resultType = 0;
        int i = this.type;
        if (i == 7) {
            resultType = 2;
        } else if (i == 5) {
            resultType = 3;
        } else if (i == 6) {
            resultType = 1;
        }
        Cursor callLogCursor = getContentResolver().query(uri, strFields, "type = '" + resultType + "'", null, "date DESC");
        Log.e(TAG, "===== Call Log list start ! =====");
        callLogCursor.moveToFirst();
        if (callLogCursor.getCount() != 0) {
            do {
                String result = callLogCursor.getString(callLogCursor.getColumnIndex("type"));
                int i2 = callLogCursor.getColumnIndex("name");
                String result2 = result + " | " + callLogCursor.getString(i2);
                int i3 = callLogCursor.getColumnIndex("date");
                String result3 = result2 + " | " + callLogCursor.getString(i3);
                int i4 = callLogCursor.getColumnIndex("number");
                String str = TAG;
                Log.e(str, "Call Log : " + (result3 + " | " + callLogCursor.getString(i4)));
            } while (callLogCursor.moveToNext());
        }
        Log.e(TAG, "===== Call Log list end ! =====");
        return callLogCursor;
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.PbapPageActivity.AnonymousClass13 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Log.v(PbapPageActivity.TAG, "handleMessage: " + PbapPageActivity.this.getHandlerEventName(msg.what));
                switch (msg.what) {
                    case 1:
                        PbapPageActivity.this.text_state_pbap.setText(bundle.getString(PbapPageActivity.STATE_PBAP));
                        break;
                    case 2:
                        PbapPageActivity.this.mVCardsArrayAdapter.add(bundle.getString(PbapPageActivity.DATA_VCARD));
                        break;
                    case 3:
                        PbapPageActivity.this.title_source.setVisibility(0);
                        PbapPageActivity.this.title_source.setText(bundle.getString(PbapPageActivity.TITLE_NAME));
                        break;
                    case 4:
                        PbapPageActivity.this.button_storage_switch.setText(bundle.getString(PbapPageActivity.IS_CONTACTS_PROVIDER));
                        break;
                    case 5:
                        PbapPageActivity.this.title_source.setVisibility(8);
                        PbapPageActivity.this.title_source.setText(BuildConfig.FLAVOR);
                        break;
                    case 6:
                        PbapPageActivity.this.mVCardsArrayAdapter.clear();
                        PbapPageActivity.this.mVCardsByPassArrayAdapter.clear();
                        break;
                    case 7:
                        boolean enable = bundle.getString(PbapPageActivity.ENABLE_BUTTON).equals("true");
                        PbapPageActivity.this.button_sim.setEnabled(enable);
                        PbapPageActivity.this.button_memory.setEnabled(enable);
                        PbapPageActivity.this.button_dial.setEnabled(enable);
                        PbapPageActivity.this.button_missed.setEnabled(enable);
                        PbapPageActivity.this.button_received.setEnabled(enable);
                    case 8:
                        if (PbapPageActivity.this.infoToast != null) {
                            PbapPageActivity.this.infoToast.cancel();
                            PbapPageActivity.this.infoToast.setText(bundle.getString(PbapPageActivity.TOAST_MESSAGE));
                            PbapPageActivity.this.infoToast.setDuration(0);
                        } else {
                            PbapPageActivity.this.infoToast = Toast.makeText(PbapPageActivity.this.getApplicationContext(), bundle.getString(PbapPageActivity.TOAST_MESSAGE), 0);
                        }
                        PbapPageActivity.this.infoToast.show();
                        break;
                    case 9:
                        PbapPageActivity.this.mVCardsByPassArrayAdapter.add(bundle.getString(PbapPageActivity.DATA_VCARD));
                        break;
                    case 10:
                        Log.d(PbapPageActivity.TAG, "HANDLER_EVENT_UPDATE_BY_PASS_VCARD_TO_LIST");
                        String resultNumber = " ";
                        GocPbapContact contact = (GocPbapContact) bundle.get(PbapPageActivity.DATA_VCARD);
                        if (contact.getNumberArray() == null || contact.getNumberArray().length <= 0) {
                            PbapPageActivity.setNumberArrayElement(PbapPageActivity.getIdCount(), new String[0]);
                        } else {
                            resultNumber = contact.getNumberArray()[0];
                            String[] numbers = new String[contact.getNumberArray().length];
                            for (int i = 0; i < contact.getNumberArray().length; i++) {
                                numbers[i] = contact.getNumberArray()[i];
                            }
                            PbapPageActivity.setNumberArrayElement(PbapPageActivity.getIdCount(), numbers);
                        }
                        if (contact.getEmailArray() == null || contact.getEmailArray().length <= 0) {
                            PbapPageActivity.setEmailArrayElement(PbapPageActivity.getIdCount(), new String[0]);
                        } else {
                            String[] emails = new String[contact.getEmailArray().length];
                            for (int i2 = 0; i2 < contact.getEmailArray().length; i2++) {
                                emails[i2] = contact.getEmailArray()[i2];
                            }
                            PbapPageActivity.setEmailArrayElement(PbapPageActivity.getIdCount(), emails);
                        }
                        if (contact.getAddressArray() == null || contact.getAddressArray().length <= 0) {
                            PbapPageActivity.setAddressArrayElement(PbapPageActivity.getIdCount(), new String[0]);
                        } else {
                            String[] address = new String[contact.getAddressArray().length];
                            for (int i3 = 0; i3 < contact.getAddressArray().length; i3++) {
                                address[i3] = contact.getAddressArray()[i3];
                            }
                            PbapPageActivity.setAddressArrayElement(PbapPageActivity.getIdCount(), address);
                        }
                        String org = null;
                        if (contact.getOrg() != null && contact.getOrg().length() > 0) {
                            org = contact.getOrg();
                        }
                        PbapPageActivity.setOrgArrayElement(PbapPageActivity.getIdCount(), org);
                        PbapPageActivity.this.sendHandlerMessage(9, PbapPageActivity.DATA_VCARD, contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName() + " (" + resultNumber + ")");
                        Log.d(PbapPageActivity.TAG, "addIdCount");
                        PbapPageActivity.addIdCount();
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
        sendHandlerMessage(8, TOAST_MESSAGE, message);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendHandlerMessage(int what, String key, String value) {
        Handler handler = this.mHandler;
        if (handler == null) {
            Log.e(TAG, "mHandler == null !");
            return;
        }
        Message msg = Message.obtain(handler, what);
        if (!(key == null || value == null)) {
            Bundle b = new Bundle();
            b.putString(key, value);
            msg.setData(b);
        }
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendHandlerMessageWithContact(int what, String key, GocPbapContact value) {
        Handler handler = this.mHandler;
        if (handler == null) {
            Log.e(TAG, "mHandler == null !");
            return;
        }
        Message msg = Message.obtain(handler, what);
        if (!(key == null || value == null)) {
            Bundle b = new Bundle();
            b.putParcelable(key, value);
            msg.setData(b);
        }
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getHandlerEventName(int what) {
        switch (what) {
            case 1:
                return "HANDLER_EVENT_UPDATE_STATE_PBAP";
            case 2:
                return "HANDLER_EVENT_ADD_VCARD_TO_LIST";
            case 3:
                return "HANDLER_EVENT_SET_TITLE";
            case 4:
                return "HANDLER_EVENT_IS_CONTACTS_PROVIDER";
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
    private boolean updatePhonebookList(String macAddress, int storageType) {
        String str = TAG;
        Log.i(str, "updatePhonebookList " + macAddress + " storageType : " + storageType);
        clearList();
        if (!queryNameAndPhoneNumberByAddressAndStorageType(this.hfp_connected_address, storageType)) {
            return false;
        }
        int index = 0;
        this.mCursor.moveToFirst();
        if (this.mCursor.getCount() != 0) {
            this.listViewIdTable = new int[this.mCursor.getCount()];
            do {
                this.listViewIdTable[index] = this.mCursor.getInt(this.mCursor.getColumnIndex("_id"));
                index++;
                sendHandlerMessage(2, DATA_VCARD, this.mCursor.getString(this.mCursor.getColumnIndex("FullName")));
            } while (this.mCursor.moveToNext());
        }
        this.db.close();
        this.mCursor.close();
        return true;
    }

    /* access modifiers changed from: private */
    public static synchronized int getIdCount() {
        int i;
        synchronized (PbapPageActivity.class) {
            i = mIdCount;
        }
        return i;
    }

    /* access modifiers changed from: private */
    public static synchronized void addIdCount() {
        synchronized (PbapPageActivity.class) {
            mIdCount++;
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void resetIdCount() {
        synchronized (PbapPageActivity.class) {
            Log.d(TAG, "resetIdCount");
            mIdCount = 0;
            mNumberTable.clear();
            mEmailTable.clear();
            mAddressTable.clear();
            mOrgTable.clear();
        }
    }

    /* access modifiers changed from: private */
    public static String[] getNumberArrayFromTable(int id) {
        String str = TAG;
        Log.i(str, "getNumberArrayFromTable(): " + id);
        try {
            if (mNumberTable != null) {
                return mNumberTable.get(id);
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /* access modifiers changed from: private */
    public static String[] getEmailArrayFromTable(int id) {
        String str = TAG;
        Log.i(str, "getEmailArrayFromTable(): " + id);
        try {
            if (mEmailTable != null) {
                return mEmailTable.get(id);
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /* access modifiers changed from: private */
    public static String[] getAddressArrayFromTable(int id) {
        String str = TAG;
        Log.i(str, "getAddressArrayFromTable(): " + id);
        try {
            if (mAddressTable != null) {
                return mAddressTable.get(id);
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /* access modifiers changed from: private */
    public static String getOrgFromTable(int id) {
        String str = TAG;
        Log.i(str, "getOrgFromTable(): " + id);
        try {
            if (mOrgTable != null) {
                return mOrgTable.get(id);
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void setNumberArrayElement(int id, String[] number) {
        ArrayList<String[]> arrayList = mNumberTable;
        if (arrayList != null) {
            arrayList.add(number);
        }
    }

    /* access modifiers changed from: private */
    public static void setEmailArrayElement(int id, String[] email) {
        ArrayList<String[]> arrayList = mEmailTable;
        if (arrayList != null) {
            arrayList.add(email);
        }
    }

    /* access modifiers changed from: private */
    public static void setAddressArrayElement(int id, String[] address) {
        ArrayList<String[]> arrayList = mAddressTable;
        if (arrayList != null) {
            arrayList.add(address);
        }
    }

    /* access modifiers changed from: private */
    public static void setOrgArrayElement(int id, String org) {
        ArrayList<String> arrayList = mOrgTable;
        if (arrayList != null) {
            arrayList.add(org);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String[] getNumberArrayFromCursor(int id) {
        String str = TAG;
        Log.i(str, "getNumberArrayFromCursor(): " + id);
        if (!queryNumberById(id)) {
            return null;
        }
        int index = 0;
        this.mCursor.moveToFirst();
        String[] resultArray = null;
        if (this.mCursor.getCount() != 0) {
            String[] resultArray2 = new String[this.mCursor.getCount()];
            do {
                resultArray2[index] = this.mCursor.getString(this.mCursor.getColumnIndex("Number"));
                index++;
            } while (this.mCursor.moveToNext());
            resultArray = resultArray2;
        }
        this.db.close();
        this.mCursor.close();
        return resultArray;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String[] getEmailArrayFromCursor(int id) {
        String str = TAG;
        Log.i(str, "getEmailArrayFromCursor(): " + id);
        if (!queryEmailById(id)) {
            return null;
        }
        int index = 0;
        this.mCursor.moveToFirst();
        String[] resultArray = null;
        if (this.mCursor.getCount() != 0) {
            String[] resultArray2 = new String[this.mCursor.getCount()];
            do {
                resultArray2[index] = this.mCursor.getString(this.mCursor.getColumnIndex("Email"));
                index++;
            } while (this.mCursor.moveToNext());
            resultArray = resultArray2;
        }
        this.db.close();
        this.mCursor.close();
        return resultArray;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String[] getAddressArrayFromCursor(int id) {
        String str = TAG;
        Log.i(str, "getAddressArrayFromCursor(): " + id);
        if (!queryAddressById(id)) {
            return null;
        }
        int index = 0;
        this.mCursor.moveToFirst();
        String[] resultArray = null;
        if (this.mCursor.getCount() != 0) {
            String[] resultArray2 = new String[this.mCursor.getCount()];
            do {
                resultArray2[index] = this.mCursor.getString(this.mCursor.getColumnIndex("Address"));
                index++;
            } while (this.mCursor.moveToNext());
            resultArray = resultArray2;
        }
        this.db.close();
        this.mCursor.close();
        return resultArray;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean updateCallHistoryList(String macAddress, int storageType) {
        String str = TAG;
        Log.i(str, "updateCallHistoryList " + macAddress + " storageType : " + storageType);
        clearList();
        if (!queryCallHistoryByAddressAndStorageType(macAddress, storageType)) {
            return false;
        }
        this.mCursor.moveToFirst();
        if (this.mCursor.getCount() != 0) {
            do {
                String result_name = this.mCursor.getString(this.mCursor.getColumnIndex("FullName"));
                String result_number = this.mCursor.getString(this.mCursor.getColumnIndex("PhoneNumber"));
                String result_date = this.mCursor.getString(this.mCursor.getColumnIndex("HistoryDate"));
                String result_time = this.mCursor.getString(this.mCursor.getColumnIndex("HistoryTime"));
                sendHandlerMessage(2, DATA_VCARD, result_date + " / " + result_time + "\n" + result_name + " (" + result_number + ")");
            } while (this.mCursor.moveToNext());
        }
        this.mCursor.close();
        return true;
    }

    public boolean queryNameAndPhoneNumberByAddressAndStorageType(String macAddress, int storageType) {
        String str = TAG;
        Log.e(str, "queryNameAndPhoneNumberByAddressAndStorageType " + macAddress + " storageType : " + storageType);
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
            this.dbHelper = new DbHelperPbap(_context);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        Log.e(TAG, "Piggy Check 1");
        this.mCursor = this.db.rawQuery("select a._id,a.FullName,a.StorageType from PhoneBookcontent a where a.fullname in (select fullname from phonebookcontent where cellphone_address  = ? and StorageType= ? group by fullname) order by fullname;", new String[]{macAddress, String.valueOf(storageType)});
        Log.e(TAG, "Piggy Check 2");
        Log.e(TAG, "Piggy Check 3");
        if (this.mCursor.getCount() > 0) {
            Log.e(TAG, "Piggy Check 4");
            return true;
        }
        this.mCursor.close();
        this.db.close();
        return false;
    }

    public boolean queryCallHistoryByAddressAndStorageType(String macAddress, int storageType) {
        String str = TAG;
        Log.e(str, "queryCallHistoryByAddressAndStorageType " + macAddress + " storageType : " + storageType);
        DbHelperPbap dbHelperPbap = this.dbHelper;
        if (dbHelperPbap != null) {
            dbHelperPbap.close();
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
            this.dbHelper = new DbHelperPbap(_context);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        this.mCursor = this.db.rawQuery("select fullname , phonenumber , historydate , historytime from callhistory where CellPhone_Address = ? and storagetype = ? order by 3 desc , 4 desc;", new String[]{macAddress, String.valueOf(storageType)});
        if (this.mCursor.getCount() > 0) {
            return true;
        }
        this.mCursor.close();
        this.db.close();
        return false;
    }

    public boolean queryEmailById(int id) {
        String str = TAG;
        Log.e(str, "queryEmailById: " + id);
        DbHelperPbap dbHelperPbap = this.dbHelper;
        if (dbHelperPbap != null) {
            dbHelperPbap.close();
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
            this.dbHelper = new DbHelperPbap(_context);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        this.mCursor = sQLiteDatabase.rawQuery("select * from EmailDetail where Content_ID = ?;", new String[]{BuildConfig.FLAVOR + id});
        if (this.mCursor.getCount() > 0) {
            return true;
        }
        this.mCursor.close();
        this.db.close();
        return false;
    }

    public boolean queryNumberById(int id) {
        String str = TAG;
        Log.e(str, "queryNumberById: " + id);
        DbHelperPbap dbHelperPbap = this.dbHelper;
        if (dbHelperPbap != null) {
            dbHelperPbap.close();
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
            this.dbHelper = new DbHelperPbap(_context);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        this.mCursor = sQLiteDatabase.rawQuery("select * from PhoneNumberDetail where Content_ID = ?;", new String[]{BuildConfig.FLAVOR + id});
        if (this.mCursor.getCount() > 0) {
            return true;
        }
        this.mCursor.close();
        this.db.close();
        return false;
    }

    public boolean queryAddressById(int id) {
        String str = TAG;
        Log.e(str, "queryNumberById: " + id);
        DbHelperPbap dbHelperPbap = this.dbHelper;
        if (dbHelperPbap != null) {
            dbHelperPbap.close();
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
            this.dbHelper = new DbHelperPbap(_context);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.db;
        this.mCursor = sQLiteDatabase.rawQuery("select * from AddressDetail where Content_ID = ?;", new String[]{BuildConfig.FLAVOR + id});
        if (this.mCursor.getCount() > 0) {
            return true;
        }
        this.mCursor.close();
        this.db.close();
        return false;
    }

    private void deleteContactsProviderAllContacts() {
        Log.d(TAG, "deleteContactsProviderAllContacts()");
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
        while (cur.moveToNext()) {
            try {
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, cur.getString(cur.getColumnIndex("lookup")));
                String str = TAG;
                Log.d(str, "The uri is " + uri.toString());
                cr.delete(uri, null, null);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void createPhotoFile(String name, byte[] photo) {
        String photoString = new String(photo);
        String str = TAG;
        Log.d(str, "Piggy Check photoString: " + photoString);
        if (photo != null && photo.length > 0) {
            String resultPath = Environment.getExternalStorageDirectory().getPath();
            File outDir = new File(resultPath + File.separator + "NfDemoPbapPhoto");
            if (!outDir.isDirectory()) {
                outDir.mkdir();
            }
            if (!outDir.isDirectory()) {
                Log.e(TAG, "Piggy Check can't create folder !!");
                return;
            }
            File f = new File(outDir, name + ".jpeg");
            Log.e(TAG, "Piggy Check Before check readable/writeable");
            f.setReadable(true);
            Log.e(TAG, "Piggy Check After set readable");
            f.setWritable(true);
            Log.e(TAG, "Piggy Check After set writeable");
            try {
                f.createNewFile();
                String str2 = TAG;
                Log.e(str2, "Piggy Check File Name : " + f.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                bos.write(photo);
                bos.flush();
                bos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDetailDialog(String[] numbers, String[] emails, String[] addresses, String org) {
        int length = 0;
        if (numbers != null) {
            length = 0 + numbers.length;
        }
        if (emails != null) {
            length += emails.length;
        }
        if (addresses != null) {
            length += addresses.length;
        }
        if (org != null) {
            length++;
        }
        String[] details = new String[length];
        int index = 0;
        if (numbers != null) {
            for (int i = 0; i < numbers.length; i++) {
                details[index] = "number: " + numbers[i];
                index++;
            }
        }
        if (emails != null) {
            for (int i2 = 0; i2 < emails.length; i2++) {
                details[index] = "email: " + emails[i2];
                index++;
            }
        }
        if (addresses != null) {
            for (int i3 = 0; i3 < addresses.length; i3++) {
                details[index] = "address: " + addresses[i3];
                index++;
            }
        }
        if (org != null) {
            details[index] = "org: " + org;
            int index2 = index + 1;
        }
        new AlertDialog.Builder(this).setTitle("Detail").setItems(details, (DialogInterface.OnClickListener) null).setNegativeButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    public abstract class RunnableSII implements Runnable {
        protected int mInt1 = 0;
        protected int mInt2 = 0;
        protected String mString = BuildConfig.FLAVOR;

        public RunnableSII(String v1, int v2, int v3) {
            this.mString = v1;
            this.mInt1 = v2;
            this.mInt2 = v3;
        }
    }
}
