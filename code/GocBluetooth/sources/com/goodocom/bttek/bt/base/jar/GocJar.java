package com.goodocom.bttek.bt.base.jar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.aidl.GocPbapContact;
import com.goodocom.bttek.bt.aidl.UiCallbackA2dp;
import com.goodocom.bttek.bt.aidl.UiCallbackAvrcp;
import com.goodocom.bttek.bt.aidl.UiCallbackBluetooth;
import com.goodocom.bttek.bt.aidl.UiCallbackGattServer;
import com.goodocom.bttek.bt.aidl.UiCallbackHfp;
import com.goodocom.bttek.bt.aidl.UiCallbackHid;
import com.goodocom.bttek.bt.aidl.UiCallbackMap;
import com.goodocom.bttek.bt.aidl.UiCallbackOpp;
import com.goodocom.bttek.bt.aidl.UiCallbackPbap;
import com.goodocom.bttek.bt.aidl.UiCallbackSpp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.base.listener.GocBluetoothGattserverChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothHidChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothMapChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothOppChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothOtherFunChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothPhoneChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothServiceConnectedListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothSettingChangeListener;
import com.goodocom.bttek.bt.base.listener.GocBluetoothSppChangeListener;
import com.goodocom.bttek.bt.bean.CallLogs;
import com.goodocom.bttek.bt.bean.Collection;
import com.goodocom.bttek.bt.bean.Contacts;
import com.goodocom.bttek.bt.bean.GocBtInfo;
import com.goodocom.bttek.bt.res.GocDef;
import com.goodocom.bttek.bt.util.GocContactsUtil;
import java.util.List;

public class GocJar {
    public static final int BT_SYNC_CALLLOGS = 2;
    public static final int BT_SYNC_COMPLETE_CALLLOGS = 4;
    public static final int BT_SYNC_COMPLETE_CONTACT = 3;
    public static final int BT_SYNC_CONTACT = 1;
    public static final int BT_SYNC_IDLE = 0;
    public static final int BT_SYNC_INTERRUPTED = 5;
    public static final int CLEAN_TABLE_ALL = 0;
    public static final int CLEAN_TABLE_CALLLOGS = 2;
    public static final int CLEAN_TABLE_COLLECTION = 3;
    public static final int CLEAN_TABLE_CONTACT = 1;
    public static final int CONNECT_DISCONNECT = 3;
    public static final int CONNECT_FAILED = 2;
    public static final int CONNECT_STREAMING = 4;
    public static final int CONNECT_SUCCESSED = 1;
    public static final int SERVICE__CONNECTED = 140;
    public static final int SERVICE__CONNECTING = 120;
    public static final int SERVICE__DISCONNECTING = 125;
    public static final int SERVICE__NOT_INITIALIZED = 100;
    public static final int SERVICE__READY = 110;
    public static final int SERVICE__STREAMING = 150;
    private static String TAG = "gocJar";
    private static int a2dpConnectState = 0;
    private static int avrcpConnectState = 0;
    private static int hfpConnectState = 0;
    private static GocJar mGocJar;
    private static Intent mIntent;
    private static int mProperty = 65967;
    private static byte sAvrcpPlayStatus = 0;
    private static UiCallbackA2dp sCallbackA2dp = new UiCallbackA2dp.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass3 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackA2dp
        public void onA2dpServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onA2dpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackA2dp
        public void onA2dpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "--onA2dpStateChanged--prestate--" + prevState + "-newstate" + newState + ", address : " + address);
            if (GocJar.sSettingChangeListener == null) {
                return;
            }
            if (newState != 110) {
                if (newState == 140 && prevState == 120) {
                    String str2 = GocJar.TAG;
                    GocJar.iLog(str2, "av send to ui connected:" + address);
                    int unused = GocJar.a2dpConnectState = 1;
                    GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                    GocJar.sSettingChangeListener.onA2dpStateChanged(address, GocJar.a2dpConnectState);
                }
            } else if (prevState == 125 || prevState == 140) {
                String str3 = GocJar.TAG;
                GocJar.iLog(str3, "av send to ui disconnect:" + address);
                int unused2 = GocJar.a2dpConnectState = 3;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onA2dpStateChanged(address, GocJar.a2dpConnectState);
            } else if (prevState == 120) {
                String str4 = GocJar.TAG;
                GocJar.iLog(str4, "av send to ui connect fail:" + address);
                int unused3 = GocJar.a2dpConnectState = 2;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onA2dpStateChanged(address, GocJar.a2dpConnectState);
            }
        }
    };
    private static UiCallbackAvrcp sCallbackAvrcp = new UiCallbackAvrcp.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass4 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpPlayModeChanged(String address, int mode) {
            GocJar.iLog(GocJar.TAG, "retAvrcpPlayModeChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcpPlayModeChanged(address, mode);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "-onAvrcpStateChangedprevState" + prevState + "--newState-" + newState + ", address : " + address);
            if (GocJar.sSettingChangeListener == null) {
                return;
            }
            if (newState != 110) {
                if (newState == 140 && prevState == 120) {
                    String str2 = GocJar.TAG;
                    GocJar.iLog(str2, "avrcp send to ui connected:" + address);
                    int unused = GocJar.avrcpConnectState = 1;
                    GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                    GocJar.sSettingChangeListener.onAvrcpStateChanged(address, GocJar.avrcpConnectState);
                }
            } else if (prevState == 125 || prevState == 140) {
                String str3 = GocJar.TAG;
                GocJar.iLog(str3, "avrcp send to ui disconnect:" + address);
                int unused2 = GocJar.avrcpConnectState = 3;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onAvrcpStateChanged(address, GocJar.avrcpConnectState);
            } else if (prevState == 120) {
                String str4 = GocJar.TAG;
                GocJar.iLog(str4, "avrcp send to ui connect fail:" + address);
                int unused3 = GocJar.avrcpConnectState = 2;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onAvrcpStateChanged(address, GocJar.avrcpConnectState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp13CapabilitiesSupportEvent");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13CapabilitiesSupportEvent(eventIds);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp13PlayerSettingAttributesList");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13PlayerSettingAttributesList(attributeIds);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp13PlayerSettingValuesList");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13PlayerSettingValuesList(attributeId, valueIds);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp13PlayerSettingCurrentValues");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13PlayerSettingCurrentValues(attributeIds, valueIds);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp13SetPlayerSettingValueSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13SetPlayerSettingValueSuccess();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp13ElementAttributesPlaying");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13ElementAttributesPlaying(metadataAtrributeIds, texts);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "retAvrcp13PlayStatus()--sAvrcpPlayStatus" + ((int) GocJar.sAvrcpPlayStatus));
            byte unused = GocJar.sAvrcpPlayStatus = statusId;
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp13PlayStatus(songLen, songPos, statusId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherSuccess(byte eventId) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13RegisterEventWatcherSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13RegisterEventWatcherSuccess(eventId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherFail(byte eventId) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13RegisterEventWatcherFail");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13RegisterEventWatcherFail(eventId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackStatusChanged(byte statusId) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onAvrcp13EventPlaybackStatusChanged()--sAvrcpPlayStatus" + ((int) GocJar.sAvrcpPlayStatus));
            byte unused = GocJar.sAvrcpPlayStatus = statusId;
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventPlaybackStatusChanged(statusId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackChanged(long elementId) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventTrackChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventTrackChanged(elementId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventTrackReachedEnd");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventTrackReachedEnd();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedStart() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventTrackReachedStart");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventTrackReachedStart();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackPosChanged(long songPos) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventPlaybackPosChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventPlaybackPosChanged(songPos);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventBatteryStatusChanged(byte statusId) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventBatteryStatusChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventBatteryStatusChanged(statusId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventSystemStatusChanged(byte statusId) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventSystemStatusChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventSystemStatusChanged(statusId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp13EventPlayerSettingChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp13EventPlayerSettingChanged(attributeIds, valueIds);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp14EventNowPlayingContentChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp14EventNowPlayingContentChanged();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp14EventAvailablePlayerChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp14EventAvailablePlayerChanged();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp14EventAddressedPlayerChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp14EventAddressedPlayerChanged(playerId, uidCounter);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventUidsChanged(int uidCounter) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp14EventUidsChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp14EventUidsChanged(uidCounter);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventVolumeChanged(byte volume) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcp14EventVolumeChanged");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcp14EventVolumeChanged(volume);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14SetAddressedPlayerSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14SetAddressedPlayerSuccess();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14SetBrowsedPlayerSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14SetBrowsedPlayerSuccess(path, uidCounter, itemCount);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14FolderItems(int uidCounter, long itemCount) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14FolderItems");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14FolderItems(uidCounter, itemCount);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14MediaItems(int uidCounter, long itemCount) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14MediaItems");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14MediaItems(uidCounter, itemCount);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ChangePathSuccess(long itemCount) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14ChangePathSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14ChangePathSuccess(itemCount);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14ItemAttributes");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14ItemAttributes(metadataAtrributeIds, texts);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14PlaySelectedItemSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14PlaySelectedItemSuccess();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SearchResult(int uidCounter, long itemCount) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14SearchResult");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14SearchResult(uidCounter, itemCount);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14AddToNowPlayingSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14AddToNowPlayingSuccess();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcp14SetAbsoluteVolumeSuccess");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcp14SetAbsoluteVolumeSuccess(volume);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpErrorResponse(int opId, int reason, byte eventId) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAvrcpErrorResponse");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.onAvrcpErrorResponse(opId, reason, eventId);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpUpdateSongStatus(String artist, String album, String title) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retAvrcpUpdateSongStatus");
            if (GocJar.sMusicChangeListener != null) {
                GocJar.sMusicChangeListener.retAvrcpUpdateSongStatus(artist, album, title);
            }
        }
    };
    private static UiCallbackBluetooth sCallbackBluetooth = new UiCallbackBluetooth.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass5 */
        private int mNewState = 0;
        private int mPrevState = 0;

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onBluetoothServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onBluetoothServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterStateChanged(int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onAdapterStateChanged" + newState);
            if (this.mPrevState == prevState || this.mNewState == newState) {
                GocJar.eLog(GocJar.TAG, "onAdapterStateChanged() 过滤重复回调");
                return;
            }
            this.mPrevState = prevState;
            this.mNewState = newState;
            if (newState == 300) {
                GocJar.sSettingChangeListener.onEnableChanged(false);
            } else if (newState == 302) {
                GocJar.sSettingChangeListener.onEnableChanged(true);
            }
            GocJar.sSettingChangeListener.onAdapterStateChanged(prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoverableModeChanged(int prevState, int newState) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAdapterDiscoverableModeChanged");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoveryStarted() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAdapterDiscoveryStarted");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onAdapterDiscoveryStarted();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAdapterDiscoveryFinished() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onAdapterDiscoveryFinished");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onAdapterDiscoveryFinished();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void retPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPairedDevices");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.retPairedDevices(elements, address, name, supportProfile, category);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceFound(String address, String name, byte category) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onDeviceFound");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onDeviceFound(address, name);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceBondStateChanged(String address, String name, int prevState, int newState) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onDeviceBondStateChanged");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onDeviceBondStateChanged(address, name, newState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceUuidsUpdated(String address, String name, int supportProfile) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onDeviceUuidsUpdated");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onLocalAdapterNameChanged(String name) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onLocalAdapterNameChanged");
            GocJar.sSettingChangeListener.onLocalAdapterNameChanged(name);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceOutOfRange(String address) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onDeviceOutOfRange");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onDeviceAclDisconnected(String address) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onDeviceAclDisconnected");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onBtRoleModeChanged(int mode) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onBtRoleModeChanged");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onBtAutoConnectStateChanged(String address, int prevState, int newState) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onBtAutoConnectStateChanged");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpStateChanged prevState:" + prevState + " newState:" + newState);
            if (GocJar.sSettingChangeListener == null) {
                return;
            }
            if (newState != 110) {
                if (newState == 140 && prevState == 120) {
                    String str2 = GocJar.TAG;
                    GocJar.iLog(str2, "hf send to ui connected:" + address);
                    int unused = GocJar.hfpConnectState = 1;
                    GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                    GocJar.sSettingChangeListener.onHfpStateChanged(address, GocJar.hfpConnectState);
                }
            } else if (prevState == 125 || prevState == 140) {
                String str3 = GocJar.TAG;
                GocJar.iLog(str3, "hf send to ui disconnect:" + address);
                int unused2 = GocJar.hfpConnectState = 3;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onHfpStateChanged(address, GocJar.hfpConnectState);
            } else if (prevState == 120) {
                String str4 = GocJar.TAG;
                GocJar.iLog(str4, "hf send to ui connect fail:" + address);
                int unused3 = GocJar.hfpConnectState = 2;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onHfpStateChanged(address, GocJar.hfpConnectState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onA2dpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "--onA2dpStateChanged--prestate--" + prevState + "-newstate" + newState);
            if (GocJar.sSettingChangeListener == null) {
                return;
            }
            if (newState != 110) {
                if (newState == 140 && prevState == 120) {
                    int unused = GocJar.a2dpConnectState = 1;
                    String str2 = GocJar.TAG;
                    GocJar.iLog(str2, "av state send to ui connected:" + address);
                    GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                    GocJar.sSettingChangeListener.onA2dpStateChanged(address, GocJar.a2dpConnectState);
                }
            } else if (prevState == 125 || prevState == 140) {
                int unused2 = GocJar.a2dpConnectState = 3;
                String str3 = GocJar.TAG;
                GocJar.iLog(str3, "av state send to ui disconnect:" + address);
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onA2dpStateChanged(address, GocJar.a2dpConnectState);
            } else if (prevState == 120) {
                GocJar.iLog(GocJar.TAG, "av state send to ui connect fail");
                int unused3 = GocJar.a2dpConnectState = 2;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onA2dpStateChanged(address, GocJar.a2dpConnectState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "-onAvrcpStateChangedprevState" + prevState + "--newState-" + newState);
            if (GocJar.sSettingChangeListener == null) {
                return;
            }
            if (newState != 110) {
                if (newState == 140 && prevState == 120) {
                    int unused = GocJar.avrcpConnectState = 1;
                    GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                    GocJar.sSettingChangeListener.onAvrcpStateChanged(address, GocJar.avrcpConnectState);
                }
            } else if (prevState == 125 || prevState == 140) {
                int unused2 = GocJar.avrcpConnectState = 3;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onAvrcpStateChanged(address, GocJar.avrcpConnectState);
            } else if (prevState == 120) {
                int unused3 = GocJar.avrcpConnectState = 2;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onAvrcpStateChanged(address, GocJar.avrcpConnectState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onLocalAdapterPinCodeChanged(String key) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onLocalAdapterPinCodeChanged");
        }

        public void onPairStateChanged(String name, String address, int type, int pairingValue) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onPairStateChanged");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onPairStateChanged(name, address, type, pairingValue);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onMainDevicesChanged(String address, String name) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMainDevicesChanged");
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onMainDevicesChanged(address, name);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAutoConnect(int state) throws RemoteException {
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onAutoConnect(state);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onAutoAnwer(int state) throws RemoteException {
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onAutoAnwer(state);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackBluetooth
        public void onConnectedDevice(String mainDevice, String subDevice) throws RemoteException {
            GocJar.sSettingChangeListener.onConnectDevice(mainDevice, subDevice);
        }

        public void onBtBasicConnectStateChanged(String address, int prevState, int newState) throws RemoteException {
        }
    };
    private static UiCallbackGattServer sCallbackGattServer = new UiCallbackGattServer.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass9 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerStateChanged(String address, int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerCharacteristicReadRequest(String address, int requestuestId, int offset, boolean isLong, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerCharacteristicWriteRequest(String address, int requestuestId, int offset, boolean preparedWrite, boolean responseNeeded, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, byte[] value) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerDescriptorReadRequest(String address, int requestuestId, int offset, boolean isLong, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, ParcelUuid descrUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerDescriptorWriteRequest(String address, int requestuestId, int offset, boolean isPrep, boolean responseNeeded, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, ParcelUuid descrUuid, byte[] value) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackGattServer
        public void onGattServerExecuteWrite(String address, int requestuestId, boolean execute) throws RemoteException {
        }
    };
    private static UiCallbackHfp sCallbackHfp = new UiCallbackHfp.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass2 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onHfpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpStateChanged address:" + address + " , prevState: " + prevState + " , newState:" + newState);
            if (GocJar.sSettingChangeListener == null) {
                return;
            }
            if (newState != 110) {
                if (newState == 140 && prevState == 120) {
                    String str2 = GocJar.TAG;
                    GocJar.iLog(str2, "rev service hf send connected:" + address);
                    int unused = GocJar.hfpConnectState = 1;
                    GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                    GocJar.sSettingChangeListener.onHfpStateChanged(address, GocJar.hfpConnectState);
                }
            } else if (prevState == 125 || prevState == 140) {
                String str3 = GocJar.TAG;
                GocJar.iLog(str3, "rev service hf send to ui disconnect:" + address);
                int unused2 = GocJar.hfpConnectState = 3;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onHfpStateChanged(address, GocJar.hfpConnectState);
            } else if (prevState == 120) {
                String str4 = GocJar.TAG;
                GocJar.iLog(str4, "rev service hf send connect fail:" + address);
                int unused3 = GocJar.hfpConnectState = 2;
                GocJar.sSettingChangeListener.onConnectedChanged(address, GocJar.onBtStateChanged());
                GocJar.sSettingChangeListener.onHfpStateChanged(address, GocJar.hfpConnectState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpAudioStateChanged(String address, int prevState, int newState) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpAudioStateChanged : " + address + ", pre : " + prevState + ", new : " + newState);
            if (GocJar.sSettingChangeListener != null) {
                GocJar.sSettingChangeListener.onHfpAudioStateChanged(address, prevState, newState);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpVoiceDial(String address, boolean isVoiceDialOn) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpVoiceDial : " + address + ", " + isVoiceDialOn);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpErrorResponse(String address, int code) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpErrorResponse : " + address + ", " + code);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpRemoteTelecomService : " + address + ", " + isTelecomServiceOn);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteRoamingStatus(String address, boolean isRoamingOn) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpRemoteRoamingStatus : " + address + ", " + isRoamingOn);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpRemoteBatteryIndicator : " + address + ", cur : " + currentValue + ", max : " + maxValue + ", min : " + minValue);
            if (GocJar.sOtherFunChangeListener != null) {
                GocJar.sOtherFunChangeListener.onHfpRemoteBatteryIndicator(address, currentValue, maxValue, minValue);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpRemoteSignalStrength : " + address + ", cur : " + currentStrength + ", max : " + maxStrength + " min" + minStrength);
            if (GocJar.sOtherFunChangeListener != null) {
                GocJar.sOtherFunChangeListener.onHfpRemoteSignalStrength(address, currentStrength, maxStrength, minStrength);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpCallChanged(String address, GocHfpClientCall call) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpCallChanged : " + address + " , " + call);
            if (GocJar.sPhoneChangeListener != null) {
                GocJar.sPhoneChangeListener.onHfpCallChanged(address, call);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "retPbapDatabaseQueryNameByNumber : " + address + ", tar : " + target + ", name : " + name + ", " + isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpCallingTimeChanged(String time) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpCallingTimeChanged" + time);
            if (GocJar.sPhoneChangeListener != null) {
                GocJar.sPhoneChangeListener.onHfpCallingTimeChanged(time);
            }
        }

        public void onHfpMissedCall(String address, int size) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onHfpMissedCall--address: " + address + "size: " + size);
            if (GocJar.sOtherFunChangeListener != null) {
                GocJar.sOtherFunChangeListener.onHfpMissedCall(address, size);
            }
        }

        public void onHfpManufactureIdentificationUpdated(String address, String info) throws RemoteException {
        }

        public void onHfpModelIdentificationUpdated(String address, String info) throws RemoteException {
        }

        public void onHfpClockOfDeviceUpdated(String address, String clock) throws RemoteException {
        }
    };
    private static UiCallbackHid sCallbackHid = new UiCallbackHid.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass10 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHid
        public void onHidServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHid
        public void onHidStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
        }
    };
    private static UiCallbackMap sCallbackMap = new UiCallbackMap.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass8 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapServiceReady");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapServiceReady();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapStateChanged");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapStateChanged(address, prevState, newState, reason);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String recipientNumber, String date, int type, int folder, boolean isReadStatus, String subject, String message) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapDownloadedMessage");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapDownloadedMessage(address, handle, senderName, senderNumber, recipientNumber, date, type, folder, isReadStatus, subject, message);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapNewMessageReceivedEvent(String address, String handle, String sender, String message) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapNewMessageReceivedEvent");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapNewMessageReceivedEvent(address, handle, sender, message);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapDownloadNotify");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapDownloadNotify(address, folder, totalMessages, currentMessages);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDatabaseAvailable() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapDatabaseAvailable");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapDatabaseAvailable();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapDeleteDatabaseByAddressCompleted");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapDeleteDatabaseByAddressCompleted(address, isSuccess);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapCleanDatabaseCompleted");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapCleanDatabaseCompleted(isSuccess);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapSendMessageCompleted(String address, String target, int state) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapSendMessageCompleted");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapSendMessageCompleted(address, target, state);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapDeleteMessageCompleted(String address, String handle, int reason) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapDeleteMessageCompleted");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapDeleteMessageCompleted(address, handle, reason);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void retMapChangeReadStatusCompleted(String address, String handle, int reason) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retMapChangeReadStatusCompleted");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.retMapChangeReadStatusCompleted(address, handle, reason);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMemoryAvailableEvent(String address, int structure, boolean available) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapMemoryAvailableEvent");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapMemoryAvailableEvent(address, structure, available);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapMessageSendingStatusEvent");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapMessageSendingStatusEvent(address, handle, isSuccess);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapMessageDeliverStatusEvent");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapMessageDeliverStatusEvent(address, handle, isSuccess);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapMessageShiftedEvent");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapMessageShiftedEvent(address, handle, type, newFolder, oldFolder);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackMap
        public void onMapMessageDeletedEvent(String address, String handle, int type, int folder) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onMapMessageDeletedEvent");
            if (GocJar.sMapChangeListener != null) {
                GocJar.sMapChangeListener.onMapMessageDeletedEvent(address, handle, type, folder);
            }
        }
    };
    private static UiCallbackOpp sCallbackOpp = new UiCallbackOpp.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass11 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppStateChanged(String address, int preState, int currentState, int reason) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveProgress(int receivedOffset) throws RemoteException {
        }
    };
    private static UiCallbackPbap sCallbackPbap = new UiCallbackPbap.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass6 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void onPbapServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onPbapServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void onPbapStateChanged(String address, int prevState, int newState, int reason, int counts) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "onPbapStateChanged--newState" + prevState);
            if (GocJar.sPhoneChangeListener != null) {
                GocJar.sPhoneChangeListener.onPbapStateChanged(prevState);
            } else {
                Log.e(GocJar.TAG, "sPhoneChangeListener is null");
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDownloadedContact(GocPbapContact contact) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPbapDownloadedContact");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) throws RemoteException {
            String str = GocJar.TAG;
            GocJar.iLog(str, "retPbapDownloadedCallLog() firstName" + firstName + "--middleName--" + middleName + "--lastName--" + lastName);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void onPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onPbapDownloadNotify");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPbapDatabaseQueryNameByNumber");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPbapDatabaseQueryNameByPartialNumber");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDatabaseAvailable(String address) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPbapDatabaseAvailable");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPbapDeleteDatabaseByAddressCompleted");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackPbap
        public void retPbapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retPbapCleanDatabaseCompleted");
        }
    };
    private static UiCallbackSpp sCallbackSpp = new UiCallbackSpp.Stub() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass7 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppServiceReady() throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onSppServiceReady");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.onSppServiceReady();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppStateChanged(String address, String deviceName, int prevState, int newState) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onSppStateChanged");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.onSppStateChanged(address, deviceName, prevState, newState);
            }
        }

        public void retSppListeningUuidList(int count, String[] uuidList) throws RemoteException {
            String str = GocJar.TAG;
            Log.d(str, "retSppListeningUuidList count: " + count);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppErrorResponse(String address, int errorCode) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onSppErrorResponse");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.onSppErrorResponse(address, errorCode);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void retSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "retSppConnectedDeviceAddressList");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.retSppConnectedDeviceAddressList(totalNum, addressList, nameList);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppDataReceived(String address, byte[] receivedData) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onSppDataReceived");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.onSppDataReceived(address, receivedData);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppSendData(String address, int length) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onSppSendData");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.onSppSendData(address, length);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackSpp
        public void onSppAppleIapAuthenticationRequest(String address) throws RemoteException {
            GocJar.iLog(GocJar.TAG, "onSppAppleIapAuthenticationrequestuest");
            if (GocJar.sSppChangeListener != null) {
                GocJar.sSppChangeListener.onSppAppleIapAuthenticationRequest(address);
            }
        }
    };
    private static UiCommand sCommand;
    private static ServiceConnection sConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.base.jar.GocJar.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i(GocJar.TAG, "******************************");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "*     serviceconnected       *");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "*                            *");
            Log.i(GocJar.TAG, "******************************");
            Log.i(GocJar.TAG, "sConnection ready onServiceConnected");
            boolean unused = GocJar.sIsServiceConnected = true;
            String str = GocJar.TAG;
            Log.i(str, "sConnection className: " + className);
            String str2 = GocJar.TAG;
            Log.i(str2, "sConnection IBinder service: " + service.hashCode());
            try {
                String str3 = GocJar.TAG;
                Log.i(str3, "sConnection service: " + service.getInterfaceDescriptor());
            } catch (RemoteException var5) {
                var5.printStackTrace();
            }
            UiCommand unused2 = GocJar.sCommand = UiCommand.Stub.asInterface(service);
            if (GocJar.sCommand == null) {
                boolean unused3 = GocJar.sIsServiceConnected = false;
                Log.i(GocJar.TAG, "sConnection sCommand is null!!");
                return;
            }
            try {
                Log.i(GocJar.TAG, "******************************");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "*     service register       *");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "*                            *");
                Log.i(GocJar.TAG, "******************************");
                Log.i(GocJar.TAG, "service register");
                GocJar.sCommand.registerHfpCallback(GocJar.sCallbackHfp);
                GocJar.sCommand.registerA2dpCallback(GocJar.sCallbackA2dp);
                GocJar.sCommand.registerAvrcpCallback(GocJar.sCallbackAvrcp);
                GocJar.sCommand.registerBtCallback(GocJar.sCallbackBluetooth);
                GocJar.sCommand.registerPbapCallback(GocJar.sCallbackPbap);
                GocJar.sCommand.registerSppCallback(GocJar.sCallbackSpp);
                GocJar.sCommand.registerMapCallback(GocJar.sCallbackMap);
                GocJar.sCommand.reqAvrcp13GetPlayStatus();
            } catch (RemoteException var4) {
                var4.printStackTrace();
            }
            if (GocJar.sServiceConnectedListener != null) {
                Log.i(GocJar.TAG, "sConnection onServiceConnectedChanged(true)");
                GocJar.sServiceConnectedListener.onServiceConnectedChanged(true);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            String str = GocJar.TAG;
            Log.i(str, "sConnection ready onServiceDisconnected: " + className);
            boolean unused = GocJar.sIsServiceConnected = false;
            UiCommand unused2 = GocJar.sCommand = null;
            if (GocJar.sServiceConnectedListener != null) {
                Log.i(GocJar.TAG, "sConnection onServiceConnectedChanged(false)");
                GocJar.sServiceConnectedListener.onServiceConnectedChanged(false);
            }
            Log.i(GocJar.TAG, "sConnection end onServiceDisconnected");
        }
    };
    private static Context sContext = null;
    private static GocBluetoothGattserverChangeListener sGocBluetoothGattserverChangeListener;
    private static GocBluetoothHidChangeListener sGocBluetoothHidChangeListener;
    private static GocBluetoothOppChangeListener sGocBluetoothOppChangeListener;
    private static boolean sIsServiceConnected = false;
    private static GocBluetoothMapChangeListener sMapChangeListener;
    private static GocBluetoothMusicChangeListener sMusicChangeListener;
    private static GocBluetoothOtherFunChangeListener sOtherFunChangeListener;
    private static GocBluetoothPhoneChangeListener sPhoneChangeListener;
    private static GocBluetoothServiceConnectedListener sServiceConnectedListener;
    private static GocBluetoothSettingChangeListener sSettingChangeListener;
    private static GocBluetoothSppChangeListener sSppChangeListener;

    public static GocJar getGocJar() {
        if (mGocJar == null) {
            synchronized (GocJar.class) {
                if (mGocJar == null) {
                    mGocJar = new GocJar();
                }
            }
        }
        return mGocJar;
    }

    public static void release() {
        String str = TAG;
        Log.i(str, "release() " + sContext);
        try {
            if (sCommand != null) {
                Log.i(TAG, "unregister all Callback");
                if (sCallbackHfp != null) {
                    sCommand.unregisterHfpCallback(sCallbackHfp);
                }
                if (sCallbackA2dp != null) {
                    sCommand.unregisterA2dpCallback(sCallbackA2dp);
                }
                if (sCallbackAvrcp != null) {
                    sCommand.unregisterAvrcpCallback(sCallbackAvrcp);
                }
                if (sCallbackPbap != null) {
                    sCommand.unregisterPbapCallback(sCallbackPbap);
                }
                if (sCallbackBluetooth != null) {
                    sCommand.unregisterBtCallback(sCallbackBluetooth);
                }
                if (sCallbackSpp != null) {
                    sCommand.unregisterSppCallback(sCallbackSpp);
                }
                if (sCallbackMap != null) {
                    sCommand.unregisterMapCallback(sCallbackMap);
                }
            }
        } catch (RemoteException var1) {
            var1.printStackTrace();
        }
        if (sIsServiceConnected) {
            Log.i(TAG, "start unbind service");
            Context context = sContext;
            if (context != null) {
                context.unbindService(sConnection);
                sIsServiceConnected = false;
                sContext = null;
            }
            Log.i(TAG, "end unbind service");
        }
        Log.i(TAG, "release() finished");
    }

    public static boolean init(Context context) {
        boolean isInitSuccess;
        String str = TAG;
        Log.i(str, "++++++++++++++++init()++++++++++++++++ " + context);
        if (context == null) {
            Log.e(TAG, "init() context is null, reject init bt jar!!!");
            return false;
        }
        release();
        if (!sIsServiceConnected) {
            Log.i(TAG, "bindUiService");
            sContext = context;
            mIntent = new Intent("com.nforetek.bt.START_UI_SERVICE");
            mIntent.setComponent(new ComponentName("com.goodocom.bttek", "com.goodocom.bttek.bt.demo.service.BtService"));
            Log.i(TAG, "bindUiService incoming");
            isInitSuccess = sContext.bindService(mIntent, sConnection, 1);
            try {
                Log.i(TAG, "bindUiService finish");
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        } else {
            isInitSuccess = true;
            Log.i(TAG, "bindUiService success");
        }
        if (!isInitSuccess) {
            Log.e(TAG, "bindService is fail");
        }
        String str2 = TAG;
        Log.i(str2, "----------------init()---------------- " + context);
        return isInitSuccess;
    }

    public static void registerBluetoothServiceConnectedListener(GocBluetoothServiceConnectedListener listener) {
        iLog(TAG, "registerBluetoothServiceConnectedListener");
        sServiceConnectedListener = listener;
    }

    public static void registerBluetoothSettingChangeListener(GocBluetoothSettingChangeListener listener) {
        iLog(TAG, "registerBluetoothSettingChangeListener");
        sSettingChangeListener = listener;
    }

    public static void registerBluetoothPhoneChangeListener(GocBluetoothPhoneChangeListener listener) {
        iLog(TAG, "registerBluetoothPhoneChangeListener");
        sPhoneChangeListener = listener;
    }

    public static void registerBluetoothMusicChangeListener(GocBluetoothMusicChangeListener listener) {
        iLog(TAG, "registerBluetoothMusicChangeListener");
        sMusicChangeListener = listener;
    }

    public static void registerBluetoothSppChangeListener(GocBluetoothSppChangeListener listener) {
        iLog(TAG, "registerBluetoothSppChangeListener");
        sSppChangeListener = listener;
    }

    public static void registerBluetoothMapChangeListener(GocBluetoothMapChangeListener listener) {
        iLog(TAG, "registerBluetoothMapChangeListener");
        sMapChangeListener = listener;
    }

    public static void registerBluetoothOtherFunChangeListener(GocBluetoothOtherFunChangeListener listener) {
        iLog(TAG, "registerBluetoothOtherFunChangeListener");
        sOtherFunChangeListener = listener;
    }

    public static void registerBluetoothGattserverChangeListener(GocBluetoothGattserverChangeListener listener) {
        iLog(TAG, "registersBluetoothGattserverChangeListener");
        sGocBluetoothGattserverChangeListener = listener;
    }

    public static void registerBluetoothHidChangeListener(GocBluetoothHidChangeListener listener) {
        iLog(TAG, "registerBluetoothHidChangeListener");
        sGocBluetoothHidChangeListener = listener;
    }

    public static void registerBluetoothOppChangeListener(GocBluetoothOppChangeListener listener) {
        iLog(TAG, "registerBluetoothOppChangeListener");
        sGocBluetoothOppChangeListener = listener;
    }

    public static boolean isBtConnected(String address) {
        String str = TAG;
        iLog(str, "isBtConnected:" + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            Log.e(TAG, "sCommand is null");
            return false;
        }
        try {
            if (uiCommand.isHfpConnected(address) || sCommand.isA2dpConnected(address) || sCommand.isAvrcpConnected(address)) {
                return true;
            }
            return false;
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static int getPbapDownLoadState() {
        iLog(TAG, "getPbapDownLoadState");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            Log.e(TAG, "sCommand is null");
            return 0;
        }
        try {
            return uiCommand.getPbapDownLoadState();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return 0;
        }
    }

    public static void playNext() {
        String str = TAG;
        iLog(str, "playNext--sAvrcpPlayStatus" + ((int) sAvrcpPlayStatus));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            Log.e(TAG, "sCommand is null");
            return;
        }
        try {
            uiCommand.reqAvrcpForward();
        } catch (RemoteException var1) {
            var1.printStackTrace();
        }
    }

    public static void playPrev() {
        String str = TAG;
        iLog(str, "playPrev--sAvrcpPlayStatus" + ((int) sAvrcpPlayStatus));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            Log.e(TAG, "sCommand is null");
            return;
        }
        try {
            uiCommand.reqAvrcpBackward();
        } catch (RemoteException var1) {
            var1.printStackTrace();
        }
    }

    public static boolean isAvrcpServiceReady() throws RemoteException {
        iLog(TAG, "isAvrcpServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isAvrcpServiceReady();
    }

    public static boolean isA2dpServiceReady() throws RemoteException {
        iLog(TAG, "isA2dpServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isA2dpServiceReady();
    }

    public static boolean isSppServiceReady() throws RemoteException {
        iLog(TAG, "isSppServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isSppServiceReady();
    }

    public static boolean isBluetoothServiceReady() throws RemoteException {
        iLog(TAG, "isBluetoothServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isBluetoothServiceReady();
    }

    public static boolean isHfpServiceReady() throws RemoteException {
        iLog(TAG, "isHfpServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpServiceReady();
    }

    public static boolean isPbapServiceReady() throws RemoteException {
        iLog(TAG, "isPbapServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isPbapServiceReady();
    }

    public static String getUiServiceVersionName() throws RemoteException {
        iLog(TAG, "getUiServiceVersionName()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getUiServiceVersionName();
    }

    public static int getA2dpConnectionState() throws RemoteException {
        iLog(TAG, "getA2dpConnectionState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getA2dpConnectionState();
    }

    public static boolean isA2dpConnected(String address) throws RemoteException {
        iLog(TAG, "isA2dpConnected()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isA2dpConnected(address);
    }

    public static String getA2dpConnectedAddress() throws RemoteException {
        iLog(TAG, "getA2dpConnectedAddress()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? GocDef.DEFAULT_ADDRESS : uiCommand.getA2dpConnectedAddress();
    }

    public static boolean requestA2dpConnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestA2dpConnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            return uiCommand.reqA2dpConnect(address);
        }
        Log.e(TAG, "sCommand is null");
        return false;
    }

    public static boolean requestA2dpDisconnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestA2dpDisconnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqA2dpDisconnect(address);
    }

    public static void pauseA2dpRender() throws RemoteException {
        iLog(TAG, "pauseA2dpRender()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.pauseA2dpRender();
        }
    }

    public static void startA2dpRender() throws RemoteException {
        iLog(TAG, "startA2dpRender()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.startA2dpRender();
        }
    }

    public static boolean setA2dpLocalVolume(float vol) throws RemoteException {
        String str = TAG;
        iLog(str, "setA2dpLocalVolume() " + vol);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setA2dpLocalVolume(vol);
    }

    public static boolean setA2dpStreamType(int type) throws RemoteException {
        String str = TAG;
        iLog(str, "setA2dpStreamType() " + type);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setA2dpStreamType(type);
    }

    public static int getA2dpStreamType() throws RemoteException {
        iLog(TAG, "getA2dpStreamType()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getA2dpStreamType();
    }

    public static int getAvrcpConnectionState() throws RemoteException {
        iLog(TAG, "getAvrcpConnectionState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getAvrcpConnectionState();
    }

    public static boolean isAvrcpConnected(String address) throws RemoteException {
        iLog(TAG, "isAvrcpConnected()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isAvrcpConnected(address);
    }

    public static String getAvrcpConnectedAddress() throws RemoteException {
        iLog(TAG, "getAvrcpConnectedAddress()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? GocDef.DEFAULT_ADDRESS : uiCommand.getAvrcpConnectedAddress();
    }

    public static boolean requestAvrcpConnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcpConnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpConnect(address);
    }

    public static boolean requestAvrcpDisconnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcpDisconnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpDisconnect(address);
    }

    public static boolean isAvrcp13Supported(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "isAvrcp13Supported() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isAvrcp13Supported(address);
    }

    public static boolean isAvrcp14Supported(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "isAvrcp14Supported() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isAvrcp14Supported(address);
    }

    public static boolean requestAvrcpPlay() throws RemoteException {
        iLog(TAG, "requestAvrcpPlay()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpPlay();
    }

    public static boolean requestAvrcpStop() throws RemoteException {
        iLog(TAG, "requestAvrcpStop()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpStop();
    }

    public static boolean requestAvrcpPause() throws RemoteException {
        iLog(TAG, "requestAvrcpPause()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpPause();
    }

    public static boolean requestAvrcpVolumeUp() throws RemoteException {
        iLog(TAG, "requestAvrcpVolumeUp()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpVolumeUp();
    }

    public static boolean requestAvrcpVolumeDown() throws RemoteException {
        iLog(TAG, "requestAvrcpVolumeDown()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpVolumeDown();
    }

    public static boolean requestAvrcpStartFastForward() throws RemoteException {
        iLog(TAG, "requestAvrcpStartFastForward()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpStartFastForward();
    }

    public static boolean requestAvrcpStopFastForward() throws RemoteException {
        iLog(TAG, "requestAvrcpStopFastForward()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpStopFastForward();
    }

    public static boolean requestAvrcpStartRewind() throws RemoteException {
        iLog(TAG, "requestAvrcpStartRewind()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpStartRewind();
    }

    public static boolean requestAvrcpStopRewind() throws RemoteException {
        iLog(TAG, "requestAvrcpStopRewind()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpStopRewind();
    }

    public static boolean requestAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
        iLog(TAG, "requestAvrcp13GetCapabilitiesSupportEvent()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13GetCapabilitiesSupportEvent();
    }

    public static boolean requestAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
        iLog(TAG, "requestAvrcp13GetPlayerSettingAttributesList()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13GetPlayerSettingAttributesList();
    }

    public static boolean requestAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp13GetPlayerSettingValuesList() attributeId: " + ((int) attributeId));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13GetPlayerSettingValuesList(attributeId);
    }

    public static boolean requestAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
        iLog(TAG, "requestAvrcp13GetPlayerSettingCurrentValues()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13GetPlayerSettingCurrentValues();
    }

    public static boolean requestAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp13SetPlayerSettingValue() attributeId: " + ((int) attributeId) + " valueId: " + ((int) valueId));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13SetPlayerSettingValue(attributeId, valueId);
    }

    public static boolean requestAvrcp13GetElementAttributesPlaying() throws RemoteException {
        iLog(TAG, "requestAvrcp13GetElementAttributesPlaying()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13GetElementAttributesPlaying();
    }

    public static boolean requestAvrcp13GetPlayStatus() throws RemoteException {
        iLog(TAG, "requestAvrcp13GetPlayStatus()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13GetPlayStatus();
    }

    public static boolean requestAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcpRegisterEventWatcher() eventId: " + ((int) eventId) + " interval: " + interval);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpRegisterEventWatcher(eventId, interval);
    }

    public static boolean requestAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcpUnregisterEventWatcher() eventId: " + ((int) eventId));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcpUnregisterEventWatcher(eventId);
    }

    public static boolean requestAvrcp13NextGroup() throws RemoteException {
        iLog(TAG, "requestAvrcp13NextGroup()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13NextGroup();
    }

    public static boolean requestAvrcp13PreviousGroup() throws RemoteException {
        iLog(TAG, "requestAvrcp13PreviousGroup()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp13PreviousGroup();
    }

    public static boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
        iLog(TAG, "isAvrcp14BrowsingChannelEstablished()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isAvrcp14BrowsingChannelEstablished();
    }

    public static boolean requestAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14SetAddressedPlayer() playerId: " + playerId);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14SetAddressedPlayer(playerId);
    }

    public static boolean requestAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14SetBrowsedPlayer() playerId: " + playerId);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14SetBrowsedPlayer(playerId);
    }

    public static boolean requestAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14GetFolderItems() scopeId: " + ((int) scopeId));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14GetFolderItems(scopeId);
    }

    public static boolean requestuestAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
        String str = TAG;
        iLog(str, "requestuestAvrcp14ChangePath() uidCounter: " + uidCounter + " uid: " + uid + " direction: " + ((int) direction));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14ChangePath(uidCounter, uid, direction);
    }

    public static boolean requestuestAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
        String str = TAG;
        iLog(str, "requestuestAvrcp14GetItemAttributes() scopeId: " + ((int) scopeId) + " uidCounter: " + uidCounter);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14GetItemAttributes(scopeId, uidCounter, uid);
    }

    public static boolean requestAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14PlaySelectedItem() scopeId: " + ((int) scopeId) + " uidCounter: " + uidCounter + " uid: " + uid);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14PlaySelectedItem(scopeId, uidCounter, uid);
    }

    public static boolean requestAvrcp14Search(String text) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14Search() text: " + text);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14Search(text);
    }

    public static boolean requestAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14AddToNowPlaying() scopeId: " + ((int) scopeId) + " uidCounter: " + uidCounter + " uid: " + uid);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14AddToNowPlaying(scopeId, uidCounter, uid);
    }

    public static boolean requestAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
        String str = TAG;
        iLog(str, "requestAvrcp14SetAbsoluteVolume() volume: " + ((int) volume));
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqAvrcp14SetAbsoluteVolume(volume);
    }

    public static boolean setPlayMode(int mode) throws RemoteException {
        String str = TAG;
        iLog(str, "setPlayMode() volume: " + mode);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setPlayMode(mode);
    }

    public static int getPlayMode() throws RemoteException {
        iLog(TAG, "getPlayMode");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 0;
        }
        return uiCommand.getPlayMode();
    }

    public static String getNfServiceVersionName() throws RemoteException {
        iLog(TAG, "getNfServiceVersionName()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? "versionname:20191112" : uiCommand.getNfServiceVersionName();
    }

    public static boolean setBtEnable(boolean enable) throws RemoteException {
        String str = TAG;
        iLog(str, "setBtEnable() enable: " + enable);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setBtEnable(enable);
    }

    public static boolean setBtDiscoverableTimeout(int timeout) throws RemoteException {
        String str = TAG;
        iLog(str, "setBtDiscoverableTimeout() timeout: " + timeout);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setBtDiscoverableTimeout(timeout);
    }

    public static boolean startBtDiscovery() throws RemoteException {
        iLog(TAG, "startBtDiscovery()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.startBtDiscovery();
    }

    public static boolean cancelBtDiscovery() throws RemoteException {
        iLog(TAG, "cancelBtDiscovery()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.cancelBtDiscovery();
    }

    public static boolean requestBtPair(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestBtPair() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqBtPair(address);
    }

    public static boolean requestBtUnpair(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestBtUnpair() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqBtUnpair(address);
    }

    public static boolean requestBtPairedDevices(int opt) throws RemoteException {
        iLog(TAG, "requestBtPairedDevices()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqBtPairedDevices(opt);
    }

    public static String getBtLocalName() throws RemoteException {
        iLog(TAG, "getBtLocalName()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getBtLocalName();
    }

    public static String getBtRemoteDeviceName(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "getBtRemoteDeviceName() " + address);
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? "" : uiCommand.getBtRemoteDeviceName(address);
    }

    public static String getBtLocalAddress() throws RemoteException {
        iLog(TAG, "getBtLocalAddress()");
        if (sCommand == null) {
            return GocDef.DEFAULT_ADDRESS;
        }
        String str = TAG;
        Log.d(str, "getBtLocalAddress : " + sCommand.getBtLocalAddress());
        return sCommand.getBtLocalAddress();
    }

    public static boolean setBtLocalName(String name) throws RemoteException {
        String str = TAG;
        iLog(str, "setBtLocalName() name: " + name);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setBtLocalName(name);
    }

    public static boolean isBtEnabled() throws RemoteException {
        iLog(TAG, "isBtEnabled()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isBtEnabled();
    }

    public static int getBtState() throws RemoteException {
        iLog(TAG, "getBtState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getBtState();
    }

    public static boolean isBtDiscovering() throws RemoteException {
        iLog(TAG, "isBtDiscovering()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isBtDiscovering();
    }

    public static boolean isBtDiscoverable() throws RemoteException {
        iLog(TAG, "isBtDiscoverable()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isBtDiscoverable();
    }

    public static boolean isBtAutoConnectEnable() throws RemoteException {
        iLog(TAG, "isBtAutoConnectEnable()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isBtAutoConnectEnable();
    }

    public static int requestBtConnectHfpA2dp(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestBtConnectHfpA2dp() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.reqBtConnectHfpA2dp(address);
    }

    public static int requestBtDisconnectAll(String address) throws RemoteException {
        iLog(TAG, "requestBtDisconnectAll()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.reqBtDisconnectAll(address);
    }

    public static int getBtRemoteUuids(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "getBtRemoteUuids() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getBtRemoteUuids(address);
    }

    public static boolean switchBtRoleMode(int mode) throws RemoteException {
        String str = TAG;
        iLog(str, "switchBtRoleMode() " + mode);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.switchBtRoleMode(mode);
    }

    public static int getBtRoleMode() throws RemoteException {
        iLog(TAG, "getBtRoleMode()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getBtRoleMode();
    }

    public static void setBtAutoConnect(int condition, int period) throws RemoteException {
        String str = TAG;
        iLog(str, "setBtAutoConnect() condition: " + condition + " period: " + period);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setBtAutoConnect(condition, period);
        }
    }

    public static int getBtAutoConnectCondition() throws RemoteException {
        iLog(TAG, "getBtAutoConnectCondition()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getBtAutoConnectCondition();
    }

    public static int getBtAutoConnectPeriod() throws RemoteException {
        iLog(TAG, "getBtAutoConnectPeriod()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getBtAutoConnectPeriod();
    }

    public static int getBtAutoConnectState() throws RemoteException {
        iLog(TAG, "getBtAutoConnectState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getBtAutoConnectState();
    }

    public static String getBtAutoConnectingAddress() throws RemoteException {
        iLog(TAG, "getBtAutoConnectingAddress()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? GocDef.DEFAULT_ADDRESS : uiCommand.getBtAutoConnectingAddress();
    }

    public static int getHfpConnectionState() throws RemoteException {
        iLog(TAG, "getHfpConnectionState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getHfpConnectionState();
    }

    public static boolean isHfpConnected(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "isHfpConnected():" + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpConnected(address);
    }

    public static String getHfpConnectedAddress() throws RemoteException {
        iLog(TAG, "getHfpConnectedAddress()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? GocDef.DEFAULT_ADDRESS : uiCommand.getHfpConnectedAddress();
    }

    public static int getHfpAudioConnectionState() throws RemoteException {
        iLog(TAG, "getHfpAudioConnectionState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getHfpAudioConnectionState();
    }

    public static boolean requestHfpConnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpConnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpConnect(address);
    }

    public static boolean requestHfpDisconnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpDisconnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpDisconnect(address);
    }

    public static int getHfpRemoteSignalStrength() throws RemoteException {
        iLog(TAG, "getHfpRemoteSignalStrength()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 0;
        }
        return uiCommand.getHfpRemoteSignalStrength();
    }

    public static List<GocHfpClientCall> getHfpCallList() throws RemoteException {
        iLog(TAG, "getHfpCallList()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getHfpCallList();
    }

    public static List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
        iLog("hfp_call", "getHfpCallList2()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getHfpCallList2();
    }

    public static boolean isHfpRemoteOnRoaming() throws RemoteException {
        iLog(TAG, "isHfpRemoteOnRoaming()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpRemoteOnRoaming();
    }

    public static int getHfpRemoteBatteryIndicator() throws RemoteException {
        iLog(TAG, "getHfpRemoteBatteryIndicator()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        return uiCommand.getHfpRemoteBatteryIndicator();
    }

    public static boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
        iLog(TAG, "isHfpRemoteTelecomServiceOn()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpRemoteTelecomServiceOn();
    }

    public static boolean isHfpRemoteVoiceDialOn() throws RemoteException {
        iLog(TAG, "isHfpRemoteVoiceDialOn()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpRemoteVoiceDialOn();
    }

    public static boolean requestHfpDialCall(String number) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpDialCall() number: " + number);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpDialCall(number);
    }

    public static boolean requestHfpReDial() throws RemoteException {
        iLog(TAG, "requestHfpReDial()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpReDial();
    }

    public static boolean requestHfpMemoryDial(String index) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpMemoryDial() index: " + index);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpMemoryDial(index);
    }

    public static boolean requestHfpAnswerCall(String address, int flag) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpAnswerCall() " + flag);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpAnswerCall(address, flag);
    }

    public static boolean requestHfpRejectIncomingCall(String address) throws RemoteException {
        iLog(TAG, "requestHfpRejectIncomingCall()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpRejectIncomingCall(address);
    }

    public static boolean requestHfpTerminateCurrentCall(String address) throws RemoteException {
        iLog(TAG, "requestHfpTerminateCurrentCall()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpTerminateCurrentCall(address);
    }

    public static boolean requestHfpSendDtmf(String address, String number) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpSendDtmf() number: " + number);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpSendDtmf(address, number);
    }

    public static boolean requestHfpAudioTransferToCarkit(String address) throws RemoteException {
        iLog(TAG, "requestHfpAudioTransfer()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpAudioTransferToCarkit(address);
    }

    public static boolean requestHfpAudioTransferToPhone(String address) throws RemoteException {
        iLog(TAG, "requestHfpAudioTransferToPhone()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpAudioTransferToPhone(address);
    }

    public static String getHfpRemoteNetworkOperator() throws RemoteException {
        iLog(TAG, "requestHfpRemoteNetworkOperator()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getHfpRemoteNetworkOperator();
    }

    public static String getHfpRemoteSubscriberNumber() throws RemoteException {
        iLog(TAG, "getHfpRemoteSubscriberNumber()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getHfpRemoteSubscriberNumber();
    }

    public static boolean requestHfpVoiceDial(boolean enable) throws RemoteException {
        String str = TAG;
        iLog(str, "requestHfpVoiceDial() enable: " + enable);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqHfpVoiceDial(enable);
    }

    public static void pauseHfpRender() throws RemoteException {
        iLog(TAG, "pauseHfpRender()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.pauseHfpRender();
        }
    }

    public static void startHfpRender() throws RemoteException {
        iLog(TAG, "startHfpRender()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.startHfpRender();
        }
    }

    public static boolean isHfpMicMute() throws RemoteException {
        iLog(TAG, "isHfpMicMute()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpMicMute();
    }

    public static void muteHfpMic(boolean mute) throws RemoteException {
        String str = TAG;
        iLog(str, "muteHfpMic() " + mute);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.muteHfpMic(mute);
        }
    }

    public static boolean isHfpInBandRingtoneSupport() throws RemoteException {
        iLog(TAG, "isHfpInbandRingtoneSupport()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isHfpInBandRingtoneSupport();
    }

    public static int getPbapConnectionState() throws RemoteException {
        iLog(TAG, "getPbapConnectionState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getPbapConnectionState();
    }

    public static boolean isPbapDownloading() throws RemoteException {
        iLog(TAG, "isPbapDownloading()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isPbapDownloading();
    }

    public static String getPbapDownloadingAddress() throws RemoteException {
        iLog(TAG, "getPbapDownloadingAddress()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? GocDef.DEFAULT_ADDRESS : uiCommand.getPbapDownloadingAddress();
    }

    public static boolean requestPbapDownload(String address, int storage, int property) throws RemoteException {
        String str = TAG;
        iLog(str, "requestPbapDownload() " + address + " (" + storage + ")");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqPbapDownload(address, storage, property);
    }

    public static boolean requestPbapDownloadInterrupt(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestPbapDownloadInterrupt() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqPbapDownloadInterrupt(address);
    }

    public static boolean setPbapDownloadNotify(int frequestuency) throws RemoteException {
        String str = TAG;
        iLog(str, "setPbapDownloadNotify() " + frequestuency);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setPbapDownloadNotify(frequestuency);
    }

    public static void requestAvrcpUpdateSongStatus() throws RemoteException {
        iLog(TAG, "requestAvrcpUpdateSongStatus()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqAvrcpUpdateSongStatus();
        }
    }

    public static boolean requestSppConnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestSppConnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqSppConnect(address);
    }

    public static boolean requestSppDisconnect(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "requestSppDisconnect() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqSppDisconnect(address);
    }

    public static void requestSppConnectedDeviceAddressList() throws RemoteException {
        iLog(TAG, "requestSppConnectedDeviceAddressList()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqSppConnectedDeviceAddressList();
        }
    }

    public static boolean isSppConnected(String address) throws RemoteException {
        String str = TAG;
        iLog(str, "isSppConnected() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isSppConnected(address);
    }

    public static void requestSppSendData(String address, byte[] sppData) throws RemoteException {
        String str = TAG;
        iLog(str, "requestSppSendData() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqSppSendData(address, sppData);
        }
    }

    public static void setDefaultPinCode(String pinCode) throws RemoteException {
        String str = TAG;
        iLog(str, "setDefaultPinCode() pinCode: " + pinCode);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setDefaultPinCode(pinCode);
        }
    }

    public static String getDefaultPinCode() throws RemoteException {
        iLog(TAG, "getDefaultPinCode()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? "" : uiCommand.getDefaultPinCode();
    }

    public static void setBtAutoAcceptPairingrequestuest(boolean auto) throws RemoteException {
        iLog(TAG, "setBtAutoAcceptPairingrequestuest()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setBtAutoAcceptPairingRequest(auto);
        }
    }

    public static boolean isBtAutoAcceptPairingrequestuest() throws RemoteException {
        iLog(TAG, "isBtAutoAcceptPairingrequestuest()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isBtAutoAcceptPairingRequest();
    }

    public static void setBtAutoDownEnable(boolean isBtAutoDownEnable, int opt) throws RemoteException {
        iLog(TAG, "setBtAutoDownEnable()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setBtAutoDownEnable(isBtAutoDownEnable, opt);
        }
    }

    public static boolean getBtAutoDownState(int opt) throws RemoteException {
        iLog(TAG, "getBtAutoDownState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.getBtAutoDownState(opt);
    }

    public static void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) throws RemoteException {
        iLog(TAG, "setBtAutoAnswerEnable()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setBtAutoAnswerEnable(isBtAutoAnswerEnable, time);
        }
    }

    public static boolean getBtAutoAnswerState() throws RemoteException {
        iLog(TAG, "getBtAutoAnswerState()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.getBtAutoAnswerState();
    }

    public static void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) throws RemoteException {
        iLog(TAG, "setBtAnswerTypeEnable()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setBtAnswerTypeEnable(isBtAnswerTypeEnable);
        }
    }

    public static boolean getBtAnswerType() throws RemoteException {
        iLog(TAG, "getBtAnswerType()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.getBtAnswerType();
    }

    public static void setBtDevConnAddr(String btDevConnAddr) throws RemoteException {
        iLog(TAG, "setBtDevConnAddr()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setBtDevConnAddr(btDevConnAddr);
        }
    }

    public static String getBtDevConnAddr() throws RemoteException {
        iLog(TAG, "getBtDevConnAddr()");
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? "" : uiCommand.getBtDevConnAddr();
    }

    public static List<Contacts> getContactsList() throws RemoteException {
        iLog(TAG, "getContactsListForDB()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getContactsListForDB();
    }

    public static List<Contacts> getContactsList2() throws RemoteException {
        iLog(TAG, "getContactsListForDB()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        GocContactsUtil.getInstance().setContactsList(uiCommand.getContactsListForDB());
        return GocContactsUtil.getInstance().getContactsList();
    }

    public static List<CallLogs> getCallLogsList(int opt) throws RemoteException {
        String str = TAG;
        iLog(str, "getCallLogsListForDB() opt: " + opt);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getCallLogsListForDB(opt);
    }

    public static boolean deleteDatabase() throws RemoteException {
        iLog(TAG, "deleteDatabase()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.deleteDatabase();
    }

    public static boolean cleanTable(int options) throws RemoteException {
        String str = TAG;
        iLog(str, "cleanTable() " + options);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.cleanTable(options);
    }

    public static String getCallName(String number) throws RemoteException {
        String str = TAG;
        iLog(str, "getCallName() " + number);
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? "" : uiCommand.getCallName(number);
    }

    public static String getCallPhoto(String number) throws RemoteException {
        String str = TAG;
        iLog(str, "getCallPhoto() " + number);
        UiCommand uiCommand = sCommand;
        return uiCommand == null ? "" : uiCommand.getCallPhoto(number);
    }

    public static void setPairingConfirmation() throws RemoteException {
        iLog(TAG, "setPairingConfirmation()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.setPairingConfirmation();
        }
    }

    public static void cancelPairingUserInput() throws RemoteException {
        iLog(TAG, "cancelPairingUserInput()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.cancelPairingUserInput();
        }
    }

    public static int getMaxDownCount(int opt) {
        String str = TAG;
        iLog(str, "getMaxDownCount() opt: " + opt);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 0;
        }
        try {
            return uiCommand.getMaxDownCount(opt);
        } catch (RemoteException var2) {
            var2.printStackTrace();
            return 0;
        }
    }

    public static boolean isMapServiceReady() throws RemoteException {
        Log.v(TAG, "isMapServiceReady()");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isMapServiceReady();
    }

    public static boolean requestMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapDownloadSingleMessage() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapDownloadSingleMessage(address, folder, handle, storage);
    }

    public static boolean requestMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapDownloadAllMessages() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapDownloadMessage(address, folder, isContentDownload, count, startPos, storage, periodBegin, periodEnd, sender, recipient, readStatus, typeFilter);
    }

    public static boolean requestMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapRegisterNotification() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapRegisterNotification(address, downloadNewMessage);
    }

    public static void requestMapUnregisterNotification(String address) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapUnregisterNotification() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqMapUnregisterNotification(address);
        }
    }

    public static boolean isMapNotificationRegistered(String address) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapDownloadInterrupt() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.isMapNotificationRegistered(address);
    }

    public static boolean requestMapDownloadInterrupt(String address) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapDownloadInterrupt() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapDownloadInterrupt(address);
    }

    public static void requestMapDatabaseAvailable() throws RemoteException {
        Log.v(TAG, "requestMapDatabaseAvailable()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqMapDatabaseAvailable();
        }
    }

    public static void requestMapDeleteDatabaseByAddress(String address) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapDeleteDatabaseByAddress() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqMapDeleteDatabaseByAddress(address);
        }
    }

    public static void requestMapCleanDatabase() throws RemoteException {
        Log.v(TAG, "requestMapCleanDatabase()");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            uiCommand.reqMapCleanDatabase();
        }
    }

    public static int getMapCurrentState(String address) throws RemoteException {
        String str = TAG;
        Log.v(str, "getMapCurrentState() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getMapCurrentState(address);
    }

    public static int getMapRegisterState(String address) throws RemoteException {
        String str = TAG;
        Log.v(str, "getMapRegisterState() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 100;
        }
        return uiCommand.getMapRegisterState(address);
    }

    public static boolean requestMapSendMessage(String address, String message, String target) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapSendMessage() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapSendMessage(address, message, target);
    }

    public static boolean requestMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapDeleteMessage() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapDeleteMessage(address, folder, handle);
    }

    public static boolean requestMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
        String str = TAG;
        Log.v(str, "requestMapChangeReadStatus() " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.reqMapChangeReadStatus(address, folder, handle, isReadStatus);
    }

    public static boolean setMapDownloadNotify(int frequestuency) throws RemoteException {
        String str = TAG;
        Log.v(str, "setMapDownloadNotify() " + frequestuency);
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        return uiCommand.setMapDownloadNotify(frequestuency);
    }

    public static void requestDelPairList() {
        Log.v(TAG, "requestDelPairList() ");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.reqBtUnpairAllPairedDevices();
            } catch (RemoteException var1) {
                var1.printStackTrace();
            }
        }
    }

    public static GocBtInfo getNforeBtInfo() {
        iLog(TAG, "getNforeBtInfo");
        GocBtInfo btInfo = new GocBtInfo();
        try {
            if (sCommand != null) {
                btInfo.setAddress(sCommand.getBtLocalAddress());
                btInfo.setDriverVersion(sCommand.getNfServiceVersionName());
                btInfo.setSupplier(sCommand.getBtVersionInfoArr()[2]);
                btInfo.setFirmwareVersion(sCommand.getBtVersionInfoArr()[1]);
                btInfo.setProfiles(sCommand.getBtVersionInfoArr()[3]);
                btInfo.setStackVersion(sCommand.getBtVersionInfoArr()[0]);
                btInfo.setVersionInfo(sCommand.getBtVersionInfoArr()[4]);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }
        return btInfo;
    }

    public static int getMissedCallSize() {
        iLog(TAG, "getMissedCallSize");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 0;
        }
        try {
            return uiCommand.getMissedCallSize();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return 0;
        }
    }

    public static void initMissedCallSize() {
        iLog(TAG, "initMissedCallSize");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.initMissedCallSize();
            } catch (RemoteException var1) {
                var1.printStackTrace();
            }
        }
    }

    public static void saveBellPath(String path) {
        String str = TAG;
        iLog(str, "saveBellPath path: " + path);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.saveBellPath(path);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static String getBellPath() {
        iLog(TAG, "getBellPath");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return "";
        }
        try {
            return uiCommand.getBellPath();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return "";
        }
    }

    public static void delCallLogs(int id) {
        String str = TAG;
        iLog(str, "delCallLogsForDB id: " + id);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.delCallLogsForDB(id);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static void delContacts(int id) {
        String str = TAG;
        iLog(str, "delContactsForDB id: " + id);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.delContactsForDB(id);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static void setCollections(List<Collection> collections) {
        String str = TAG;
        iLog(str, "setCollectionsToDB collections: " + collections.toString());
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.setCollectionsToDB(collections);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static void delCollections(int id) {
        String str = TAG;
        iLog(str, "delCollectionsForDB int: " + id);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.delCollectionsForDB(id);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static List<Collection> getCollections() {
        iLog(TAG, "getCollectionsForDB");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        try {
            return uiCommand.getCollectionsForDB();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static byte getPlayStatus() {
        iLog(TAG, "getPlayStatus");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return 0;
        }
        try {
            return uiCommand.getPlayStatus();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return 0;
        }
    }

    public static void setBtMainDevices(String address) {
        String str = TAG;
        iLog(str, "setBtMainDevices address: " + address);
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.setBtMainDevices(address);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static String getBtMainDevices() {
        iLog(TAG, "getBtMainDevices");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return GocDef.DEFAULT_ADDRESS;
        }
        try {
            return uiCommand.getBtMainDevices();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return GocDef.DEFAULT_ADDRESS;
        }
    }

    public static void switchingMainDevices(String address) {
        iLog(TAG, "switchingMainDevices");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.switchingMainDevices(address);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static int getUnreadSMS() {
        iLog(TAG, "getUnreadSMS");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return -1;
        }
        try {
            return uiCommand.getUnreadSMS();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return -1;
        }
    }

    public static void initUnreadSMS() {
        iLog(TAG, "initUnreadSMS");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.initUnreadSMS();
            } catch (RemoteException var1) {
                var1.printStackTrace();
            }
        }
    }

    public static void setThreePartyCallEnable(boolean enable) {
        iLog(TAG, "setThreePartyCallEnable");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.setThreePartyCallEnable(enable);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static boolean getThreePartyCallEnable() {
        iLog(TAG, "getThreePartyCallEnable");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        try {
            return uiCommand.getThreePartyCallEnable();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return false;
        }
    }

    public static void setDualDeviceEnable(boolean enable) {
        iLog(TAG, "setDualDeviceEnable");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.setDualDeviceEnable(enable);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static boolean getDualDeviceEnable() {
        iLog(TAG, "getDualDeviceEnable");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        try {
            return uiCommand.getDualDeviceEnable();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return false;
        }
    }

    public static void onQueryBluetoothConnect(int wh) {
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.onQueryBluetoothConnect(wh);
            } catch (RemoteException var1) {
                var1.printStackTrace();
            }
        }
    }

    public static boolean getStartBtMusicType() {
        iLog(TAG, "getStartBtMusicType");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return false;
        }
        try {
            return uiCommand.getStartBtMusicType();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return false;
        }
    }

    public static void setRejectMapMsg(String msg) {
        iLog(TAG, "setRejectMapMsg");
        UiCommand uiCommand = sCommand;
        if (uiCommand != null) {
            try {
                uiCommand.setRejectMapMsg(msg);
            } catch (RemoteException var2) {
                var2.printStackTrace();
            }
        }
    }

    public static String getRejectMapMsg() {
        iLog(TAG, "getRejectMapMsg");
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return "";
        }
        try {
            return uiCommand.getRejectMapMsg();
        } catch (RemoteException var1) {
            var1.printStackTrace();
            return "";
        }
    }

    /* access modifiers changed from: private */
    public static int onBtStateChanged() {
        if (hfpConnectState == 2 && a2dpConnectState == 2 && avrcpConnectState == 2) {
            iLog(TAG, "--onBtStateChanged--CONNECT_FAILED");
            return 2;
        } else if (hfpConnectState == 1 || a2dpConnectState == 1 || avrcpConnectState == 1) {
            iLog(TAG, "--onBtStateChanged--CONNECT_SUCCESSED");
            return 1;
        } else {
            iLog(TAG, "-onBtStateChangedCONNECT_DISCONNECT");
            return 3;
        }
    }

    public static String getNumberTypeStr(int type) {
        switch (type) {
            case 0:
                return "null";
            case 1:
                return "首选号码";
            case 2:
                return "工作号码";
            case 3:
                return "家庭号码";
            case 4:
                return "语音号码";
            case 5:
                return "传真号码";
            case 6:
                return "消息号码";
            case 7:
                return "手机号码";
            case 8:
                return "寻呼机号码";
            default:
                return "";
        }
    }

    public static List<String> getHfpConnectedAddressList() throws RemoteException {
        UiCommand uiCommand = sCommand;
        if (uiCommand == null) {
            return null;
        }
        return uiCommand.getHfpConnectedAddressList();
    }

    public static void iLog(String tag, String log) {
        try {
            if (sCommand != null && sCommand.getDebugMode()) {
                Log.i(tag, log);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }
    }

    public static void wLog(String tag, String log) {
        try {
            if (sCommand != null && sCommand.getDebugMode()) {
                Log.w(tag, log);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }
    }

    public static void eLog(String tag, String log) {
        try {
            if (sCommand != null && sCommand.getDebugMode()) {
                Log.e(tag, log);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }
    }

    public static void dLog(String tag, String log) {
        try {
            if (sCommand != null && sCommand.getDebugMode()) {
                Log.d(tag, log);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }
    }
}
