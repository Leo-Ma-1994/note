package com.goodocom.gocsdkserver;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.aidl.GocCallbackMap;
import com.goodocom.bttek.bt.aidl.GocCommandMap;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.bt.bean.GocMessages;
import java.util.ArrayList;
import java.util.List;

public class CommandMapImp extends GocCommandMap.Stub {
    public static final int MAP_CONNECTED = 2;
    public static final int MAP_DISCONNECTED = 1;
    public static final int MAP_DOWNLOADING = 3;
    public static final int MAP_FOLDER_STRUCTURE_DELETED = 2;
    public static final int MAP_FOLDER_STRUCTURE_DRAFT = 4;
    public static final int MAP_FOLDER_STRUCTURE_INBOX = 0;
    public static final int MAP_FOLDER_STRUCTURE_OUTBOX = 3;
    public static final int MAP_FOLDER_STRUCTURE_SENT = 1;
    public static final int MAP_REASON_BAD_PARAMS = 1;
    public static final int MAP_REASON_DISCONNECT_BY_PEER = 3;
    public static final int MAP_REASON_DISCONNECT_FROM_LOCAL = 2;
    public static final int MAP_REASON_DOWNLOAD_FAIL = 8;
    public static final int MAP_REASON_DOWNLOAD_FINISH = 5;
    public static final int MAP_REASON_INTERRUPT = 4;
    public static final int MAP_REASON_NONE = 0;
    public static final int MAP_REASON_PEER_NO_MAP_SERVICE = 6;
    public static final int MAP_REASON_TIMEOUT = 7;
    public static final int MAP_TYPE_ALL = 0;
    public static final int MAP_TYPE_SMS_CDMA = 2;
    public static final int MAP_TYPE_SMS_EMAIL = 8;
    public static final int MAP_TYPE_SMS_GSM = 1;
    public static final int MAP_TYPE_SMS_MMS = 4;
    public static final int STATE_CONNECTED_REGISTERED = 150;
    public static final int STATE_DOWNLOADING = 160;
    public static final int STATE_NOT_INITIALIZED = 100;
    public static final int STATE_READY = 110;
    private static final String TAG = "GoodocomMapImp";
    private RemoteCallbackList<GocCallbackMap> callbacks;
    private Object callbacksLock = new Object();
    private int currentFolder = 0;
    private String deleteMessageHandle = BuildConfig.FLAVOR;
    private int mCurrentState = 110;
    private List<GocMessages> mGocMessages = new ArrayList();
    private int mPreState = 110;
    private GocsdkService service;
    private String targetNumber = BuildConfig.FLAVOR;

    public CommandMapImp(GocsdkService service2) {
        this.service = service2;
        this.callbacks = new RemoteCallbackList<>();
    }

    public void onMapServiceReady() {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onMapServiceReady();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapDownloadStart(String address) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onMapStateChanged(address, 150, 160, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapDownloadEnd(String address) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onMapStateChanged(address, 160, 110, 5);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapConnected(String address) {
        DeviceInfo info = this.service.bluetooth.getDeviceByAddr(address);
        if (info == null) {
            Log.d(TAG, "onMapConnected device is null");
            return;
        }
        this.mPreState = this.mCurrentState;
        this.mCurrentState = 150;
        Log.d(TAG, "onMapConnected ,pre : " + this.mPreState + " ,cur : " + this.mCurrentState);
        info.map_state = 2;
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onMapStateChanged(address, this.mPreState, this.mCurrentState, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapDisconnected(String address) {
        synchronized (this.callbacksLock) {
            DeviceInfo info = this.service.bluetooth.getDeviceByAddr(address);
            if (info == null) {
                Log.d(TAG, "onMapDisconnected device is null");
                return;
            }
            this.mPreState = this.mCurrentState;
            this.mCurrentState = 110;
            Log.d(TAG, "onMapDisconnected ,pre : " + this.mPreState + " ,cur : " + this.mCurrentState);
            info.map_state = 1;
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onMapStateChanged(address, this.mPreState, this.mCurrentState, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapMessageInfo(String handle, String senderName, String senderNumber, String recipientNumber, String date, String type, int folder, int read, String subject, String title) {
        boolean readState;
        Object obj;
        int n;
        int i;
        RemoteException e;
        if (read == 1) {
            readState = true;
        } else if (read == 0) {
            readState = false;
        } else {
            readState = false;
        }
        int typeFilter = status2State(type);
        Object obj2 = this.callbacksLock;
        synchronized (obj2) {
            try {
                int n2 = this.callbacks.beginBroadcast();
                int i2 = 0;
                while (i2 < n2) {
                    try {
                        i = i2;
                        n = n2;
                        obj = obj2;
                        try {
                            this.callbacks.getBroadcastItem(i2).retMapDownloadedMessage(this.service.bluetooth.cmdMainAddr, handle, senderName, senderNumber, recipientNumber, date, typeFilter, this.currentFolder, readState, subject, title);
                        } catch (RemoteException e2) {
                            e = e2;
                        }
                    } catch (RemoteException e3) {
                        e = e3;
                        i = i2;
                        n = n2;
                        obj = obj2;
                        e.printStackTrace();
                        i2 = i + 1;
                        n2 = n;
                        obj2 = obj;
                    }
                    i2 = i + 1;
                    n2 = n;
                    obj2 = obj;
                }
                this.callbacks.finishBroadcast();
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean isMapServiceReady() throws RemoteException {
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean registerMapCallback(GocCallbackMap cb) throws RemoteException {
        Log.d(TAG, "registerMapCallback");
        cb.onMapServiceReady();
        return this.callbacks.register(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean unregisterMapCallback(GocCallbackMap cb) throws RemoteException {
        Log.d(TAG, "unregisterMapCallback");
        return this.callbacks.unregister(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
        Log.d(TAG, "reqMapDownloadSingleMessage:" + address + " folder:" + folder + " handle:" + handle + " storage" + storage);
        this.service.getCommand().getOneMapMessage(handle);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
        Log.d(TAG, "reqMapDownloadMessage:" + address + " folder:" + folder + " isContentDownload:" + isContentDownload);
        Log.d(TAG, "count:" + count + "startPos:" + startPos + "storage:" + storage + "periodBegin:" + periodBegin + "periodEnd:" + periodEnd);
        StringBuilder sb = new StringBuilder();
        sb.append("sender:");
        sb.append(sender);
        sb.append("recipient:");
        sb.append(recipient);
        sb.append("readStatus:");
        sb.append(readStatus);
        sb.append("typeFilter:");
        sb.append(typeFilter);
        Log.d(TAG, sb.toString());
        Log.d(TAG, "service.bluetooth.cmdMainAddr : " + this.service.bluetooth.cmdMainAddr);
        String currentAddress = address;
        if (TextUtils.isEmpty(address)) {
            currentAddress = this.service.bluetooth.cmdMainAddr;
        }
        DeviceInfo info = this.service.bluetooth.getDeviceByAddr(currentAddress);
        if (info == null) {
            this.service.getCommand().connectMapMessage(currentAddress);
        } else {
            int i = info.map_state;
            CommandMapImp commandMapImp = this.service.map;
            if (i < 2) {
                this.service.getCommand().connectMapMessage(currentAddress);
            }
        }
        this.service.getCommand().getMapMessage(stateToStatus(folder));
        return true;
    }

    private String stateToStatus(int type) {
        if (type == 0) {
            this.currentFolder = 0;
            return "inbox";
        } else if (type != 1) {
            return "indox";
        } else {
            this.currentFolder = 1;
            return "sent";
        }
    }

    public void onGocMessage(GocMessages messages) {
        this.mGocMessages.add(messages);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
        Log.d(TAG, "reqMapRegisterNotification address:" + address + "downloadNewMessage:" + downloadNewMessage);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public void reqMapUnregisterNotification(String address) throws RemoteException {
        Log.d(TAG, "reqMapUnregisterNotification address:" + address);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean isMapNotificationRegistered(String address) throws RemoteException {
        Log.d(TAG, "isMapNotificationRegistered address:" + address);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapDownloadInterrupt(String address) throws RemoteException {
        Log.d(TAG, "reqMapDownloadInterrupt address:" + address);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public void reqMapDatabaseAvailable() throws RemoteException {
        Log.d(TAG, "reqMapDatabaseAvailable");
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public void reqMapDeleteDatabaseByAddress(String address) throws RemoteException {
        Log.d(TAG, "reqMapDeleteDatabaseByAddress address:" + address);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public void reqMapCleanDatabase() throws RemoteException {
        Log.d(TAG, "reqMapCleanDatabase");
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public int getMapCurrentState(String address) throws RemoteException {
        Log.d(TAG, "getMapCurrentState address:" + address);
        return 0;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public int getMapRegisterState(String address) throws RemoteException {
        Log.d(TAG, "getMapRegisterState address:" + address);
        return 0;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapSendMessage(String address, String message, String target) throws RemoteException {
        Log.d(TAG, "reqMapSendMessage address:" + address + " message:" + message + " target:" + target);
        this.targetNumber = target;
        GocsdkCommandSender command = this.service.getCommand();
        StringBuilder sb = new StringBuilder();
        sb.append(target);
        sb.append(":");
        sb.append(message);
        command.sendMapMesssage(sb.toString());
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
        Log.d(TAG, "reqMapDeleteMessage address:" + address + " folder:" + folder + " handle:" + handle);
        this.deleteMessageHandle = handle;
        this.service.getCommand().deleteMapMessage(handle);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean reqMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
        Log.d(TAG, "reqMapChangeReadStatus address:" + address + " folder:" + folder + " handle:" + handle + " isReadStatus:" + isReadStatus);
        this.service.getCommand().getOneMapMessage(handle);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).retMapChangeReadStatusCompleted(address, handle, 5);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandMap
    public boolean setMapDownloadNotify(int frequency) throws RemoteException {
        Log.d(TAG, "setMapDownloadNotify frequency:" + frequency);
        return false;
    }

    public void onMessageInfo(String content_order, String read_status, String time, String name, String num, String title) {
        Log.d(TAG, "onMessageInfo content_order:" + content_order + " read_status:" + read_status + " time:" + time + " name:" + name + " num:" + num + " title:" + title);
    }

    public void onMessageContent(String content) {
        Log.d(TAG, "onMessageContent content:" + content);
    }

    public void onMapReceiveNewMessage(String handle, String sender, String subject, String datetime) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onMapNewMessageReceivedEvent(this.service.bluetooth.rspAddr, handle, sender, subject);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapMessageSendSuccess(String address, String handle, String status) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).retMapSendMessageCompleted(address, this.targetNumber, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onMapMessageDeleteSuccess(String address, String handle, String status) {
        Log.d(TAG, "onMapMessageDeleteSuccess : " + this.deleteMessageHandle);
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).retMapDeleteMessageCompleted(address, this.deleteMessageHandle, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    private int status2State(String type) {
        if (type.equals("ALL")) {
            return 0;
        }
        if (type.equals("SMS_GSM")) {
            return 1;
        }
        if (type.equals("SMS_CDMA")) {
            return 2;
        }
        if (type.equals("SMS_MMS")) {
            return 4;
        }
        if (type.equals("SMS_EMAIL")) {
            return 8;
        }
        return 0;
    }
}
