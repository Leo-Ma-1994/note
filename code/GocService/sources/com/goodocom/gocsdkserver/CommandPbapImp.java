package com.goodocom.gocsdkserver;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.aidl.GocCallbackPbap;
import com.goodocom.bttek.bt.aidl.GocCommandPbap;
import com.goodocom.bttek.bt.aidl.GocPbapContact;
import com.goodocom.bttek.bt.bean.CallLogs;
import com.goodocom.bttek.bt.bean.Collection;
import com.goodocom.bttek.bt.bean.Contacts;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.util.normal.NfUtils;
import com.goodocom.gocDataBase.GocPbapHelper;
import com.goodocom.gocDataBase.PhoneInfo;
import com.goodocom.gocDataBase.VCardPack;
import com.goodocom.utils.GocThreadPoolFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandPbapImp extends GocCommandPbap.Stub {
    public static final int BT_SYNC_CALLLOGS = 2;
    public static final int BT_SYNC_COMPLETE_CALLLOGS = 4;
    public static final int BT_SYNC_COMPLETE_CONTACT = 3;
    public static final int BT_SYNC_CONTACT = 1;
    public static final int BT_SYNC_IDLE = 0;
    public static final int BT_SYNC_INTERRUPITED = 5;
    public static final int CLEAN_TABLE_ALL = 0;
    public static final int CLEAN_TABLE_CALLLOGS = 2;
    public static final int CLEAN_TABLE_COLLECTION = 3;
    public static final int CLEAN_TABLE_CONTACT = 1;
    public static final int GOCSDK_TEL_TYPE_CELL = 1;
    public static final int GOCSDK_TEL_TYPE_FAX = 4;
    public static final int GOCSDK_TEL_TYPE_HOME = 2;
    public static final int GOCSDK_TEL_TYPE_MSG = 5;
    public static final int GOCSDK_TEL_TYPE_PREF = 7;
    public static final int GOCSDK_TEL_TYPE_UNKNOWN = 0;
    public static final int GOCSDK_TEL_TYPE_VOICE = 6;
    public static final int GOCSDK_TEL_TYPE_WORK = 3;
    public static final int MSG_NOTIFY_UI = 0;
    public static final int PBAP_NUMBER_TYPE_CELL = 7;
    public static final int PBAP_NUMBER_TYPE_FAX = 5;
    public static final int PBAP_NUMBER_TYPE_HOME = 3;
    public static final int PBAP_NUMBER_TYPE_MSG = 6;
    public static final int PBAP_NUMBER_TYPE_NULL = 0;
    public static final int PBAP_NUMBER_TYPE_PAGER = 8;
    public static final int PBAP_NUMBER_TYPE_PREF = 1;
    public static final int PBAP_NUMBER_TYPE_VOICE = 4;
    public static final int PBAP_NUMBER_TYPE_WORK = 2;
    public static final int PBAP_STORAGE_CALL_LOGS = 8;
    public static final int PBAP_STORAGE_DIALED_CALLS = 7;
    public static final int PBAP_STORAGE_FAVORITE = 4;
    public static final int PBAP_STORAGE_MISSED_CALLS = 5;
    public static final int PBAP_STORAGE_PHONE_MEMORY = 2;
    public static final int PBAP_STORAGE_RECEIVED_CALLS = 6;
    public static final int PBAP_STORAGE_SIM = 1;
    public static final int PBAP_STORAGE_SPEEDDIAL = 3;
    public static final int REASON_DOWNLOAD_FAILED = 2;
    public static final int REASON_DOWNLOAD_FULL_CONTENT_COMPLETED = 1;
    public static final int REASON_DOWNLOAD_TIMEOUT = 3;
    public static final int REASON_DOWNLOAD_USER_REJECT = 4;
    public static final int STATE_DOWNLOADING = 160;
    public static final int STATE_NOT_INITIALIZED = 100;
    public static final int STATE_READY = 110;
    private static final String TAG = "GoodocomPbapImp";
    public static SQLiteDatabase mdb = null;
    public boolean autoDownCalllog = true;
    private boolean autoDownContact = true;
    private RemoteCallbackList<GocCallbackPbap> callbacks;
    private Object callbacksLock = new Object();
    public boolean calllogDownopt = true;
    public boolean calllongDowning = false;
    private int count = 0;
    public int currentState = 0;
    private int currentStorage = 2;
    public boolean downCalllogSaveLocal = true;
    public boolean downContactSaveLocal = true;
    private List<VCardPack> mCalllogLisst = new ArrayList();
    private volatile boolean mDeleteComplete;
    private Handler mHanlder = new Handler(Looper.getMainLooper()) {
        /* class com.goodocom.gocsdkserver.CommandPbapImp.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                CommandPbapImp.this.phoneBookonDownOver(msg.arg1, msg.arg2);
            }
        }
    };
    private List<VCardPack> mPbapList = new ArrayList();
    VCardPack mVcard = null;
    public int maxDownCalllogSize = 100;
    private int maxDownContactSize = 1000;
    public boolean phonebookDowning = false;
    private int phonebookSize = 0;
    private GocsdkService service;
    private boolean startDownLoadPhonebook = false;

    public void onInitSucceed() {
        this.phonebookDowning = false;
        this.calllongDowning = false;
        this.count = 0;
        this.currentState = 0;
    }

    public CommandPbapImp(GocsdkService service2) {
        Log.e(TAG, "CommandPbapImp");
        this.service = service2;
        this.callbacks = new RemoteCallbackList<>();
        mdb = GocPbapHelper.getInstance().getWritableDatabase();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean isPbapServiceReady() throws RemoteException {
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean registerPbapCallback(GocCallbackPbap cb) throws RemoteException {
        Log.e(TAG, "registerPbapCallback");
        return this.callbacks.register(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean unregisterPbapCallback(GocCallbackPbap cb) throws RemoteException {
        Log.e(TAG, "unregisterPbapCallback");
        return this.callbacks.unregister(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public int getPbapConnectionState() throws RemoteException {
        Log.e(TAG, "getPbapConnectionState");
        return 0;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean isPbapDownloading() {
        Log.e(TAG, "isPbapDownloading:" + this.phonebookDowning);
        return this.phonebookDowning;
    }

    public void pbapStopDownload(String address) {
        Log.d("stop", "pbapStopDownload>>>>>>> " + address + "   phonebookDowning:" + this.phonebookDowning);
        if (!TextUtils.isEmpty(address) && this.phonebookDowning) {
            this.phonebookDowning = false;
            this.service.getCommand().stopPhonebookDown(address);
            Log.e("dela", "pbapStopDownload>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            deleteContactsByAddress(address);
            this.service.bluetooth.stopLoadPbapAddress = null;
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public int getPbapDownLoadState() throws RemoteException {
        return this.currentState;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public String getPbapDownloadingAddress() throws RemoteException {
        Log.e(TAG, "isPbapDownloading : " + this.service.bluetooth.cmdMainAddr);
        return this.service.bluetooth.cmdMainAddr;
    }

    public String getPbapDownloadAddress() {
        Log.e(TAG, "getPbapDownloadAddress : " + this.service.bluetooth.cmdMainAddr);
        return this.service.bluetooth.cmdMainAddr;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownload(String address, int storage, int property) throws RemoteException {
        Log.d(TAG, "reqPbapDownload address:" + address + " storate:" + storage + " property:0x" + Integer.toHexString(property) + "    main : " + this.service.bluetooth.cmdMainAddr);
        int i = this.currentState;
        if (i == 1 || i == 2) {
            Log.d(TAG, "reqPbapDownload downloading");
            return false;
        }
        this.currentStorage = storage;
        this.count = 0;
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        switch (storage) {
            case 1:
                this.currentState = 1;
                this.phonebookDowning = true;
                if (!TextUtils.isEmpty(this.service.bluetooth.cmdMainAddr)) {
                    GocPbapHelper.getInstance().deleteVcardInfo(mdb, this.service.bluetooth.cmdMainAddr, 1);
                }
                this.service.getCommand().phoneBookStartUpdate_sim();
                break;
            case 2:
                this.currentState = 1;
                this.phonebookDowning = true;
                syncContacts(2, this.service.bluetooth.cmdMainAddr);
                break;
            case 5:
                this.calllongDowning = true;
                this.currentState = 2;
                GocPbapHelper.getInstance().deleteCallHistoryInfo(mdb, address, 5);
                this.service.getCommand().callogStartUpdateMissed();
                break;
            case 6:
                this.currentState = 2;
                this.calllongDowning = true;
                GocPbapHelper.getInstance().deleteCallHistoryInfo(mdb, this.service.bluetooth.cmdMainAddr, 6);
                this.service.getCommand().callogStartUpdateIncoming();
                break;
            case 7:
                this.calllongDowning = true;
                this.currentState = 2;
                GocPbapHelper.getInstance().deleteCallHistoryInfo(mdb, this.service.bluetooth.cmdMainAddr, 7);
                this.service.getCommand().callogStartUpdateOutGoing();
                break;
            case 8:
                this.currentState = 2;
                this.calllongDowning = true;
                GocPbapHelper.getInstance().deleteAllCallHistoryInfo(mdb, this.service.bluetooth.cmdMainAddr);
                this.service.getCommand().callogStartUpdateALL(this.maxDownCalllogSize);
                break;
        }
        return true;
    }

    private void syncContacts(final int type, final String address) {
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandPbapImp.AnonymousClass2 */

            @Override // java.lang.Runnable
            public void run() {
                if (CommandPbapImp.mdb == null) {
                    CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
                }
                Log.e("syncContacts", "address:: " + address);
                if (!TextUtils.isEmpty(address)) {
                    GocPbapHelper.getInstance().deleteVcardInfo(CommandPbapImp.mdb, address, type);
                    CommandPbapImp.this.mDeleteComplete = true;
                }
            }
        });
        this.service.getCommand().phoneBookStartUpdate_memory();
    }

    public void downloadAllCallLog() {
        Log.e("main", "downloadAllCallLog?>>>>> " + this.autoDownCalllog + "   maxDownCalllogSize  " + this.maxDownCalllogSize);
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        GocPbapHelper.getInstance().deleteAllCallHistoryInfo(mdb, this.service.bluetooth.cmdMainAddr);
        if (this.autoDownCalllog) {
            this.currentState = 2;
            this.service.getCommand().callogStartUpdateALL(this.maxDownCalllogSize);
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownloadRange(String address, int storage, int property, int startPos, int offset) throws RemoteException {
        Log.e(TAG, "reqPbapDownloadRange address:" + address + " storage:" + storage + " property:" + property + "startPos:" + startPos + " offset:" + offset);
        this.currentStorage = storage;
        this.count = 0;
        switch (storage) {
            case 1:
                this.service.getCommand().phoneBookStartUpdate_sim();
                return true;
            case 2:
                this.service.getCommand().phoneBookStartUpdate_memory();
                return true;
            case 3:
            case 4:
            default:
                return true;
            case 5:
                this.service.getCommand().callogStartUpdateMissed();
                return true;
            case 6:
                this.service.getCommand().callogStartUpdateIncoming();
                return true;
            case 7:
                this.service.getCommand().callogStartUpdateOutGoing();
                return true;
            case 8:
                this.service.getCommand().callogStartUpdateALL(offset);
                return true;
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownloadToDatabase(String address, int storage, int property) throws RemoteException {
        Log.e(TAG, "reqPbapDownloadToDatabase address:" + address + " storage:" + storage + " property:0x" + Integer.toHexString(property));
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownloadRangeToDatabase(String address, int storage, int property, int startPos, int offset) throws RemoteException {
        Log.e(TAG, "reqPbapDownloadRangeToDatabase address:" + address + " storage:" + storage + " property:0x" + Integer.toHexString(property) + " startPos:" + startPos + " offset:" + offset);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownloadToContactsProvider(String address, int storage, int property) throws RemoteException {
        Log.e(TAG, "reqPbapDownloadToContactsProvider address:" + address + " storage:" + storage + " property:0x" + Integer.toHexString(property));
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownloadRangeToContactsProvider(String address, int storage, int property, int startPos, int offset) throws RemoteException {
        Log.e(TAG, "reqPbapDownloadRangeToContactsProvider address:" + address + " storage:" + storage + " property:" + property + " startPos:" + startPos + " offset:" + offset);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void reqPbapDatabaseQueryNameByNumber(String address, String target) throws RemoteException {
        Log.e(TAG, "reqPbapDatabaseQueryNameByNumber address:" + address + " target:" + target);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void reqPbapDatabaseQueryNameByPartialNumber(String address, String target, int findPosition) throws RemoteException {
        Log.e(TAG, "reqPbapDatabaseQueryNameByPartialNumber address:" + address + " target:" + target + " findPosition:" + findPosition);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void reqPbapDatabaseAvailable(String address) throws RemoteException {
        Log.e(TAG, "reqPbapDatabaseAvailable address:" + address);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void reqPbapDeleteDatabaseByAddress(String address) throws RemoteException {
        Log.e(TAG, "reqPbapDeleteDatabaseByAddress address:" + address);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void reqPbapCleanDatabase() throws RemoteException {
        Log.e(TAG, "reqPbapCleanDatabase");
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean reqPbapDownloadInterrupt(String address) throws RemoteException {
        Log.e(TAG, "reqPbapDownloadInterrupt address:" + address);
        if (TextUtils.isEmpty(address)) {
            return false;
        }
        this.service.getCommand().stopPhonebookDown(address);
        this.phonebookDowning = false;
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean setPbapDownloadNotify(int frequency) throws RemoteException {
        Log.e(TAG, "setPbapDownloadNotify frequency:" + frequency);
        return false;
    }

    public void onAutoDownCalllog(int isDown) {
        Log.e("autoload", "onAutoDownCalllog>>>>>>>>>>>>>>>>>>>> " + isDown);
        if (isDown == 1) {
            this.autoDownCalllog = true;
        } else {
            this.autoDownCalllog = false;
        }
    }

    public void onAutoDownContact(int isDown) {
        Log.e("autoload", "onAutoDownContact>>>>>>>>>>>>>>>>>>>>> " + isDown);
        if (isDown == 1) {
            this.autoDownContact = true;
        } else {
            this.autoDownContact = false;
        }
    }

    public void onCalllogDownopt(int opt) {
        Log.e("downopt", "opt>>>>>>>>" + opt);
        if (opt == 1) {
            this.calllogDownopt = true;
        } else {
            this.calllogDownopt = false;
        }
    }

    public void onCalllogSaveLocal(int status) {
        Log.e("savelocal", "onCalllogSaveLocal>>>>>>>>>>>>>>>>>" + status);
        if (status == 1) {
            this.downCalllogSaveLocal = true;
        } else {
            this.downCalllogSaveLocal = false;
        }
    }

    public void onContactSaveLocal(int status) {
        Log.e("savelocal", "onContactSaveLocal >>>>>>>>>>>>>>>>>" + status);
        if (status == 1) {
            this.downContactSaveLocal = true;
        } else {
            this.downContactSaveLocal = false;
        }
    }

    public void onMaxDownCalllogSize(int size) {
        Log.d(TAG, "onMaxDownCalllogSize : " + size);
        this.maxDownCalllogSize = size;
    }

    public void onMaxDownContactSize(int size) {
        Log.d(TAG, "onMaxDownContactSize : " + size);
        this.maxDownContactSize = size;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public List<Contacts> getContactsListForDB() throws RemoteException {
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        Log.e("test", "service.bluetooth.cmdMainAddr: " + this.service.bluetooth.cmdMainAddr);
        List<Contacts> _list = GocPbapHelper.getInstance().queryAll(mdb, this.service.bluetooth.cmdMainAddr, "2");
        Log.d(TAG, "addr:" + this.service.bluetooth.rspAddr + " contact size:" + _list.size());
        return _list;
    }

    public String getLastImcomingNumber() {
        Log.d(TAG, "getLastImcomingNumber");
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        if (TextUtils.isEmpty(this.service.bluetooth.cmdMainAddr)) {
            this.service.bluetooth.loopFindSetMainDev();
        }
        List<CallLogs> _list = GocPbapHelper.getInstance().queryAllCalllog(mdb, this.service.bluetooth.cmdMainAddr);
        Log.e(TAG, "addr:" + this.service.bluetooth.cmdMainAddr + " calllog size:" + _list.size());
        _list.sort(Comparator.comparing($$Lambda$cdRDUDNZrXY5eh6M4Kk9B8e8R14.INSTANCE).reversed());
        for (int i = 0; i < _list.size(); i++) {
            if (_list.get(i).getType() == 5 || _list.get(i).getType() == 6) {
                Log.d(TAG, "getLastImcomingNumber " + _list.get(i).getNumber());
                return _list.get(i).getNumber();
            }
        }
        return BuildConfig.FLAVOR;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public List<CallLogs> getCallLogsListForDB(int opt) throws RemoteException {
        Log.d(TAG, "getCallLogsListForDB:" + this.service.bluetooth.cmdMainAddr + " opt : " + opt);
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        if (TextUtils.isEmpty(this.service.bluetooth.cmdMainAddr)) {
            this.service.bluetooth.loopFindSetMainDev();
        }
        List<CallLogs> _list = GocPbapHelper.getInstance().queryAllCalllog(mdb, this.service.bluetooth.cmdMainAddr);
        Log.e(TAG, "addr:" + this.service.bluetooth.cmdMainAddr + " calllog size:" + _list.size());
        _list.sort(Comparator.comparing($$Lambda$cdRDUDNZrXY5eh6M4Kk9B8e8R14.INSTANCE).reversed());
        return _list;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void delContactsForDB(int id) throws RemoteException {
        Log.d("GoodocomPbapDb", "delContactsForDB : " + id);
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        GocPbapHelper.getInstance().deleteContacterById(mdb, id);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void delCallLogsForDB(int id) throws RemoteException {
        Log.d(TAG, "delCallLogsForDB");
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        GocPbapHelper.getInstance().deleteCallHistoryById(mdb, id);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public void setCollectionsToDB(int id, String value) {
        Log.d(TAG, "setCollectionsToDB:" + id + " value:" + value);
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        GocPbapHelper.getInstance().setCollectByID(mdb, id, value);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public List<Collection> getCollectionsForDB() {
        Log.d(TAG, "getCollectionsForDB");
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        return GocPbapHelper.getInstance().queryAllCollections(mdb, this.service.bluetooth.cmdMainAddr);
    }

    public void onPhoneBookVcardStart(int index) {
        if (this.service.bluetooth.rspAddr == null) {
            Log.d(TAG, "**********************************");
            Log.d(TAG, "*                                *");
            Log.d(TAG, "*                                *");
            Log.d(TAG, "*            db addr is null     *");
            Log.d(TAG, "*                                *");
            Log.d(TAG, "*                                *");
            Log.d(TAG, "**********************************");
            return;
        }
        this.mVcard = new VCardPack();
        this.mVcard.setPhoneNumbers(new HashSet());
        this.mVcard.set_id(index);
        Log.e("celladdress", "service.bluetooth.rspAddr: " + this.service.bluetooth.rspAddr + "   main" + this.service.bluetooth.cmdMainAddr);
        this.mVcard.setCellPhone_Address(this.service.bluetooth.cmdMainAddr);
    }

    private void onPhoneBookNotifyUI() {
        Object obj;
        Throwable th;
        int n;
        int i;
        RemoteException e;
        int i2 = 1;
        this.count++;
        Set<PhoneInfo> phoneInfos = this.mVcard.getPhoneNumbers();
        if (phoneInfos == null) {
            Log.e(TAG, "onPhoneBookVcardEnd phoneInfos is null");
            this.mVcard = null;
            return;
        }
        int[] phonebookType = new int[phoneInfos.size()];
        String[] phoneBookNumber = new String[phoneInfos.size()];
        String strtime = BuildConfig.FLAVOR;
        for (PhoneInfo phoneInfo : phoneInfos) {
            phonebookType[0] = Integer.parseInt(phoneInfo.getPhoneType());
            phoneBookNumber[0] = phoneInfo.getPhoneNumber();
            strtime = phoneInfo.getCalledHistoryTime();
        }
        if (this.currentStorage == 8) {
            int historyType = Integer.parseInt(this.mVcard.getStorageType());
            synchronized (this.callbacksLock) {
                int n2 = this.callbacks.beginBroadcast();
                int i3 = 0;
                while (i3 < n2) {
                    try {
                        i = i3;
                        n = n2;
                        try {
                            this.callbacks.getBroadcastItem(i3).retPbapDownloadedCallLog(this.service.currentConnectedAddr, this.mVcard.getFullName(), BuildConfig.FLAVOR, BuildConfig.FLAVOR, phoneBookNumber[0], historyType, strtime);
                        } catch (RemoteException e2) {
                            e = e2;
                        }
                    } catch (RemoteException e3) {
                        e = e3;
                        i = i3;
                        n = n2;
                        e.printStackTrace();
                        i3 = i + 1;
                        n2 = n;
                    }
                    i3 = i + 1;
                    n2 = n;
                }
                this.callbacks.finishBroadcast();
            }
        } else {
            Object obj2 = this.callbacksLock;
            synchronized (obj2) {
                try {
                    int n3 = this.callbacks.beginBroadcast();
                    int i4 = 0;
                    while (i4 < n3) {
                        String str = this.service.currentConnectedAddr;
                        int i5 = this.currentStorage;
                        String fullName = this.mVcard.getFullName();
                        int[] iArr = new int[i2];
                        iArr[0] = 0;
                        int[] iArr2 = new int[i2];
                        iArr2[0] = 0;
                        obj = obj2;
                        try {
                            try {
                                this.callbacks.getBroadcastItem(i4).retPbapDownloadedContact(new GocPbapContact(str, i5, fullName, BuildConfig.FLAVOR, BuildConfig.FLAVOR, phonebookType, phoneBookNumber, 0, null, iArr, null, iArr2, null, BuildConfig.FLAVOR));
                            } catch (RemoteException e4) {
                                e4.printStackTrace();
                            }
                            i4++;
                            n3 = n3;
                            obj2 = obj;
                            phoneBookNumber = phoneBookNumber;
                            phonebookType = phonebookType;
                            i2 = 1;
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    }
                    this.callbacks.finishBroadcast();
                } catch (Throwable th3) {
                    th = th3;
                    obj = obj2;
                    throw th;
                }
            }
        }
        this.mVcard = null;
    }

    public void onPhoneBookVcardEnd(String photoPath) {
        VCardPack vCardPack = this.mVcard;
        if (vCardPack == null) {
            Log.e(TAG, "onPhoneBookVcardEnd mVcard is null");
            return;
        }
        String _storageType = vCardPack.getStorageType();
        if (_storageType == null) {
            Log.e(TAG, "mVcard _storageType is null");
            this.mVcard = null;
            return;
        }
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        int _vcardStorageType = Integer.parseInt(_storageType);
        if (_vcardStorageType == 1 || _vcardStorageType == 2) {
            Log.e("pbapsync", "service.bluetooth.cmdMainAddr: " + this.service.bluetooth.cmdMainAddr + "   service.bluetooth.rspAddr:" + this.service.bluetooth.rspAddr + "  phonebookDowning: " + this.phonebookDowning);
            if (this.service.bluetooth.cmdMainAddr != null && this.phonebookDowning) {
                this.mVcard.photoPath = photoPath;
                StringBuffer phoneBuffer = new StringBuffer();
                phoneBuffer.append("[");
                StringBuffer typeBuffer = new StringBuffer();
                typeBuffer.append("[");
                Set<PhoneInfo> phoneInfos = this.mVcard.getPhoneNumbers();
                int po = 0;
                for (PhoneInfo phoneInfo : phoneInfos) {
                    po++;
                    phoneBuffer.append("\"");
                    phoneBuffer.append(phoneInfo.getPhoneNumber());
                    phoneBuffer.append("\"");
                    typeBuffer.append(phoneInfo.getPhoneType());
                    if (phoneInfos.size() > 1 && po != phoneInfos.size()) {
                        typeBuffer.append(",");
                        phoneBuffer.append(",");
                    }
                }
                phoneBuffer.append("]");
                typeBuffer.append("]");
                this.mVcard.setNumber(phoneBuffer.toString());
                this.mVcard.setType(typeBuffer.toString());
                this.mPbapList.add(this.mVcard);
            }
        } else if (_vcardStorageType == 5 || _vcardStorageType == 6 || _vcardStorageType == 7 || _vcardStorageType == 8) {
            Log.e("calllog", "service.bluetooth.cmdMainAddr: " + this.service.bluetooth.cmdMainAddr + "   service.bluetooth.rspAddr:" + this.service.bluetooth.rspAddr);
            if (this.service.bluetooth.cmdMainAddr != null) {
                this.mCalllogLisst.add(this.mVcard);
            }
            Log.e(TAG, ">>>>>>mCalllogLisst>>>>>>>>>. " + this.mCalllogLisst.size());
        }
    }

    public void onPhoneBook(int type, String name, String number) {
        if (this.mVcard != null) {
            this.phonebookDowning = true;
            if (this.startDownLoadPhonebook) {
                Log.d(TAG, "onPhoneBook : insertDeviceAddress");
                if (mdb == null) {
                    mdb = GocPbapHelper.getInstance().getWritableDatabase();
                }
                if (!GocPbapHelper.getInstance().hasDevicePhonebookByAddress(mdb, getPbapDownloadAddress())) {
                    GocPbapHelper.getInstance().insertDeviceAddress(mdb, getPbapDownloadAddress());
                }
                this.startDownLoadPhonebook = false;
            }
            this.mVcard.setFullName(name);
            this.mVcard.setStorageType(String.valueOf(2));
            String service_type = BuildConfig.FLAVOR;
            String service_type_name = BuildConfig.FLAVOR;
            Set<PhoneInfo> phoneInfos = this.mVcard.getPhoneNumbers();
            if (type == 0) {
                service_type = String.valueOf(0);
                service_type_name = "UNKOWN";
            } else if (type == 1) {
                service_type = String.valueOf(7);
                service_type_name = "手机";
            } else if (type == 2) {
                service_type = String.valueOf(3);
                service_type_name = "住宅";
            } else if (type == 3) {
                service_type = String.valueOf(2);
                service_type_name = "工作";
            } else if (type == 4) {
                service_type = String.valueOf(5);
                service_type_name = "传真";
            } else if (type == 5) {
                service_type = String.valueOf(6);
                service_type_name = "信息";
            } else if (type == 6) {
                service_type = String.valueOf(4);
                service_type_name = "声音";
            } else if (type == 7) {
                service_type = String.valueOf(1);
                service_type_name = "其他";
            }
            PhoneInfo _phoneinfo = new PhoneInfo();
            _phoneinfo.setPhoneType(service_type);
            _phoneinfo.setPhoneTypeName(service_type_name);
            _phoneinfo.setPhoneNumber(number);
            phoneInfos.add(_phoneinfo);
            this.mVcard.setPhoneNumbers(phoneInfos);
        }
    }

    public void onPhoneBookReady() {
        Log.e(TAG, "onPhoneBookReady");
        this.startDownLoadPhonebook = true;
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onPbapServiceReady();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onDownOver(final int reason) {
        this.phonebookDowning = false;
        this.startDownLoadPhonebook = true;
        final int storage = this.currentStorage;
        if (!GocPbapHelper.getInstance().queryHasCalllog(mdb, this.service.bluetooth.cmdMainAddr) && this.mPbapList.size() > 0) {
            Log.e(TAG, "mCalllogDownOnce:::: add bywanzhicheng");
            this.service.getCommand().callogStartUpdateALL(this.maxDownCalllogSize);
        }
        final String address = this.service.bluetooth.cmdMainAddr;
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandPbapImp.AnonymousClass3 */

            @Override // java.lang.Runnable
            public void run() {
                Log.e(CommandPbapImp.TAG, "onDownOver:mDeleteComplete  " + CommandPbapImp.this.mDeleteComplete);
                if (CommandPbapImp.this.mDeleteComplete) {
                    CommandPbapImp.this.mDeleteComplete = false;
                    GocPbapHelper.getInstance().insertVcardInfo(CommandPbapImp.mdb, CommandPbapImp.this.mPbapList);
                    CommandPbapImp.this.mPbapList.clear();
                } else {
                    GocPbapHelper.getInstance().deleteVcardInfo(CommandPbapImp.mdb, address, 2);
                    GocPbapHelper.getInstance().insertVcardInfo(CommandPbapImp.mdb, CommandPbapImp.this.mPbapList);
                    CommandPbapImp.this.mPbapList.clear();
                }
                Message message = CommandPbapImp.this.mHanlder.obtainMessage();
                message.what = 0;
                message.arg1 = storage;
                message.arg2 = reason;
                CommandPbapImp.this.mHanlder.sendMessage(message);
            }
        });
    }

    public void phoneBookonDownOver(int storage, int reason) {
        Log.e(TAG, "storage: " + storage + "    reason: " + reason);
        switch (storage) {
            case 1:
                onSimDone();
                return;
            case 2:
                onPhoneBookDone(reason);
                return;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                return;
        }
    }

    public synchronized void onPhoneBookDone(int state) {
        Throwable th;
        this.phonebookDowning = false;
        Log.e("downpbap", "onPhoneBookDone:" + state);
        this.currentState = 3;
        synchronized (this.callbacksLock) {
            try {
                int n = this.callbacks.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    if (state == 0) {
                        try {
                            Log.e("downpbap", "111onPhoneBookDone:" + state);
                            this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 3, 1, 1, this.count);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } else if (state == 8) {
                        this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 3, 1, 4, this.count);
                    } else if (state == 84) {
                        this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 3, 1, 3, this.count);
                    } else {
                        this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 3, 1, 2, this.count);
                    }
                }
                this.callbacks.finishBroadcast();
                this.currentStorage = 0;
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    public void onSimDone() {
        this.phonebookDowning = false;
        this.currentState = 3;
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 3, 1, 1, this.count);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onCalllogDone(final int state) {
        this.calllongDowning = false;
        Log.e(TAG, "onCalllogDone:" + state);
        this.currentState = 4;
        GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandPbapImp.AnonymousClass4 */

            @Override // java.lang.Runnable
            public void run() {
                GocPbapHelper.getInstance().insertCallHistoryInfo(CommandPbapImp.mdb, CommandPbapImp.this.mCalllogLisst);
                CommandPbapImp.this.downloadCalllogNotifyUI(state);
                CommandPbapImp.this.mCalllogLisst.clear();
            }
        });
        phoneNeedDownloadPbap(this.service.bluetooth.cmdMainAddr);
    }

    public void downloadCalllogNotifyUI(int state) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                if (state == 8) {
                    try {
                        this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 4, 2, 4, this.count);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else if (state == 84) {
                    this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 4, 2, 3, this.count);
                } else {
                    this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, 4, 2, 1, this.count);
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onCalllog(int historyType, int numType, String name, String number, String strtime) {
        this.calllongDowning = true;
        this.count++;
        Log.e("calllog", "number : " + number + "  strtime " + strtime + "    name: " + name);
        VCardPack vCardPack = this.mVcard;
        if (vCardPack == null) {
            Log.e(TAG, "mVcard null");
            return;
        }
        vCardPack.setFullName(name);
        this.mVcard.setHistoryTime(strtime);
        String service_type = BuildConfig.FLAVOR;
        String service_type_name = BuildConfig.FLAVOR;
        Set<PhoneInfo> phoneInfos = this.mVcard.getPhoneNumbers();
        String callType = BuildConfig.FLAVOR;
        if (historyType == 4) {
            callType = String.valueOf(7);
        } else if (historyType == 5) {
            callType = String.valueOf(6);
        } else if (historyType == 6) {
            callType = String.valueOf(5);
        }
        if (numType == 0) {
            service_type = String.valueOf(0);
            service_type_name = "UNKOWN";
        } else if (numType == 1) {
            service_type = String.valueOf(7);
            service_type_name = "手机";
        } else if (numType == 2) {
            service_type = String.valueOf(3);
            service_type_name = "住宅";
        } else if (numType == 3) {
            service_type = String.valueOf(2);
            service_type_name = "工作";
        } else if (numType == 4) {
            service_type = String.valueOf(5);
            service_type_name = "传真";
        } else if (numType == 5) {
            service_type = String.valueOf(6);
            service_type_name = "信息";
        } else if (numType == 6) {
            service_type = String.valueOf(4);
            service_type_name = "声音";
        } else if (numType == 7) {
            service_type = String.valueOf(1);
            service_type_name = "其他";
        }
        this.mVcard.setStorageType(callType);
        PhoneInfo _phoneinfo = new PhoneInfo();
        _phoneinfo.setPhoneType(service_type);
        _phoneinfo.setPhoneTypeName(service_type_name);
        _phoneinfo.setPhoneNumber(number);
        _phoneinfo.setCalledHistoryTime(strtime);
        phoneInfos.add(_phoneinfo);
        this.mVcard.setPhoneNumbers(phoneInfos);
    }

    public void onPhoneBookDowning(int type) {
        int state = 0;
        switch (type) {
            case 1:
            case 2:
                state = 1;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                state = 2;
                break;
        }
        this.currentState = state;
        Log.e(TAG, "onPhoneBookDowning:" + state);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onPbapStateChanged(this.service.bluetooth.rspAddr, state, 0, 1, this.count);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public int getDataBaseNumberByAddrType(String address, String type) {
        if (address == null) {
            return 0;
        }
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        return GocPbapHelper.getInstance().queryTotalAmount(mdb, address, type);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public boolean cleanTable(int options) throws RemoteException {
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        if (options == 0) {
            GocPbapHelper.getInstance().deleteAllTableContent(mdb);
        } else if (options == 1) {
            GocPbapHelper.getInstance().deleteAllTableContentByType(mdb, "PhoneBookContent");
        } else if (options == 2) {
            GocPbapHelper.getInstance().deleteAllTableContentByType(mdb, "CallHistory");
        }
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandPbap
    public String getCallName(String number) {
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        Log.e("GoodocomPbapDb", "btservice  start getCallName>>>>.......................");
        if (TextUtils.isEmpty(this.service.currentCallAddress)) {
            GocsdkService gocsdkService = this.service;
            gocsdkService.currentCallAddress = gocsdkService.bluetooth.rspAddr;
        }
        String _name = GocPbapHelper.getInstance().queryNameByNum(mdb, this.service.currentCallAddress, number);
        Log.d(TAG, "getCallName:" + _name);
        return _name;
    }

    /* JADX DEBUG: Can't convert new array creation: APUT found in different block: 0x00d6: APUT  
      (r2v0 'deviceInfos' com.goodocom.bttek.bt.bean.DeviceInfo[] A[D('deviceInfos' com.goodocom.bttek.bt.bean.DeviceInfo[])])
      (0 ??[int, short, byte, char])
      (wrap: com.goodocom.bttek.bt.bean.DeviceInfo : 0x00d2: INVOKE  (r8v4 com.goodocom.bttek.bt.bean.DeviceInfo) = 
      (wrap: com.goodocom.gocsdkserver.CommandBluetoothImp : 0x00ce: IGET  (r8v3 com.goodocom.gocsdkserver.CommandBluetoothImp) = 
      (wrap: com.goodocom.gocsdkserver.GocsdkService : 0x00cc: IGET  (r8v2 com.goodocom.gocsdkserver.GocsdkService) = (r12v0 'this' com.goodocom.gocsdkserver.CommandPbapImp A[IMMUTABLE_TYPE, THIS]) com.goodocom.gocsdkserver.CommandPbapImp.service com.goodocom.gocsdkserver.GocsdkService)
     com.goodocom.gocsdkserver.GocsdkService.bluetooth com.goodocom.gocsdkserver.CommandBluetoothImp)
      (wrap: java.lang.String : 0x00d0: AGET  (r9v0 java.lang.String) = (r7v5 'addrs' java.lang.String[] A[D('addrs' java.lang.String[])]), (0 ??[int, short, byte, char]))
     type: VIRTUAL call: com.goodocom.gocsdkserver.CommandBluetoothImp.getDeviceByAddr(java.lang.String):com.goodocom.bttek.bt.bean.DeviceInfo)
     */
    public void phoneNeedDownloadPbap(String address) {
        Log.d(TAG, "phonebookDownLoadYesOrNo : START, " + address);
        if (!TextUtils.isEmpty(address)) {
            DeviceInfo[] deviceInfos = new DeviceInfo[2];
            if (mdb == null) {
                mdb = GocPbapHelper.getInstance().getWritableDatabase();
            }
            if (!GocPbapHelper.getInstance().alreadyDownPhoneBook(mdb, address)) {
                Log.d(TAG, "phonebookDownLoadYesOrNo : phonebookDowning " + this.phonebookDowning);
                if (this.phonebookDowning) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("phonebookDownLoadYesOrNo : pbap downing : ");
                    sb.append(!this.service.bluetooth.isBtMainDevices(address));
                    Log.d(TAG, sb.toString());
                    if (!this.service.bluetooth.isBtMainDevices(address)) {
                        try {
                            if (!address.equals(getPbapDownloadingAddress())) {
                                this.service.getCommand().stopPhonebookDown(getPbapDownloadingAddress());
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Set<String> queryHasTwoDevices = GocPbapHelper.getInstance().queryDevices(mdb);
                    Log.e("appps", "queryHasTwoDevices        <<<<<<<<<: " + queryHasTwoDevices);
                    if (queryHasTwoDevices != null && queryHasTwoDevices.size() >= 2) {
                        Log.d(TAG, "phonebookDownLoadYesOrNo : has two devices");
                        String[] addrs = NfUtils.setToString(queryHasTwoDevices);
                        deviceInfos[0] = this.service.bluetooth.getDeviceByAddr(addrs[0]);
                        deviceInfos[1] = this.service.bluetooth.getDeviceByAddr(addrs[1]);
                        if (addrs.length > 2) {
                            for (int i = 0; i < addrs.length; i++) {
                                DeviceInfo device = this.service.bluetooth.getDeviceByAddr(addrs[i]);
                                Log.e("delete_data", "delte other:" + addrs[i]);
                                if (device != null && !device.is_connected() && device.hf_state < 3) {
                                    deleteContactsByAddress(addrs[i]);
                                }
                            }
                        } else if (deviceInfos[0] != null && deviceInfos[1] != null) {
                            Log.d("delete_data", "DeviceInfos[0].connectIndex : " + deviceInfos[0].connectIndex + ", DeviceInfos[1].connectIndex : " + deviceInfos[1].connectIndex);
                            String deleteAddress = deviceInfos[0].connectIndex < deviceInfos[1].connectIndex ? addrs[0] : addrs[1];
                            DeviceInfo deviceInfo = this.service.bluetooth.getDeviceByAddr(deleteAddress);
                            Log.e("delete_data", "deviceInfo.is_connected(): " + deviceInfo.is_connected() + "  deleteAddress  " + deleteAddress + "   hfp: " + deviceInfo.hf_state);
                            if (!deviceInfo.is_connected()) {
                                deleteContactsByAddress(deleteAddress);
                            }
                        } else if (deviceInfos[0] != null && deviceInfos[1] == null) {
                            Log.e("delete_data", "delte other:" + addrs[1]);
                            deleteContactsByAddress(addrs[1]);
                        }
                    }
                    if (this.service.bluetooth.isBtMainDevices(address) && this.autoDownContact) {
                        this.currentStorage = 2;
                        this.service.getCommand().phoneBookStartUpdate_memory();
                    }
                }
            } else {
                phoneBookonDownOver(2, 0);
                Log.d("pbaps", "load ok>>>>>>>>>>>>>>>>>>>>>>>>>>>>notifyUI");
            }
            Log.d(TAG, "This device is already download phonebook");
        }
    }

    public void onPhonebookSize(int size) {
        Log.d(TAG, "onPhonebookSize : " + size);
        this.phonebookSize = size;
    }

    public synchronized void deleteContactsByAddress(final String address) {
        Log.e("delete", "deleteContactsByAddress :address   " + address + "   service.bluetooth.cmdMainAddr:" + this.service.bluetooth.cmdMainAddr);
        if (!TextUtils.isEmpty(address)) {
            GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                /* class com.goodocom.gocsdkserver.CommandPbapImp.AnonymousClass5 */

                @Override // java.lang.Runnable
                public void run() {
                    if (CommandPbapImp.mdb == null) {
                        CommandPbapImp.mdb = GocPbapHelper.getInstance().getWritableDatabase();
                    }
                    int count = CommandPbapImp.this.deleteAllContactsByAddress(address);
                    Log.e("delete", "list>>>>>>>>>>>>>>> " + count);
                }
            });
        }
    }

    public void deleteAllCalllogByAddress(final String address) {
        if (!TextUtils.isEmpty(address)) {
            if (mdb == null) {
                mdb = GocPbapHelper.getInstance().getWritableDatabase();
            }
            Log.e("delete", "address : " + address + "   service.bluetooth.cmdMainAddr:" + this.service.bluetooth.cmdMainAddr);
            GocThreadPoolFactory.getInstance().executeRequest(new Runnable() {
                /* class com.goodocom.gocsdkserver.CommandPbapImp.AnonymousClass6 */

                @Override // java.lang.Runnable
                public void run() {
                    GocPbapHelper.getInstance().deleteAllCallHistoryInfo(CommandPbapImp.mdb, address);
                }
            });
        }
    }

    public int deleteAllContactsByAddress(String address) {
        if (mdb == null) {
            mdb = GocPbapHelper.getInstance().getWritableDatabase();
        }
        return GocPbapHelper.getInstance().deleteVcardInfo(mdb, address);
    }
}
