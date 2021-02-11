package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;
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
import com.goodocom.bttek.bt.bean.CallLogs;
import com.goodocom.bttek.bt.bean.Collection;
import com.goodocom.bttek.bt.bean.Contacts;
import java.util.List;

public interface UiCommand extends IInterface {
    boolean cancelBtDiscovery() throws RemoteException;

    void cancelPairingUserInput() throws RemoteException;

    boolean cleanTable(int i) throws RemoteException;

    void delCallLogsForDB(int i) throws RemoteException;

    void delCollectionsForDB(int i) throws RemoteException;

    void delContactsForDB(int i) throws RemoteException;

    boolean deleteDatabase() throws RemoteException;

    String getA2dpConnectedAddress() throws RemoteException;

    int getA2dpConnectionState() throws RemoteException;

    int getA2dpStreamType() throws RemoteException;

    String getAvrcpConnectedAddress() throws RemoteException;

    int getAvrcpConnectionState() throws RemoteException;

    String getBellPath() throws RemoteException;

    boolean getBtAnswerType() throws RemoteException;

    boolean getBtAutoAnswerState() throws RemoteException;

    int getBtAutoConnectCondition() throws RemoteException;

    int getBtAutoConnectPeriod() throws RemoteException;

    int getBtAutoConnectState() throws RemoteException;

    String getBtAutoConnectingAddress() throws RemoteException;

    boolean getBtAutoDownState(int i) throws RemoteException;

    String getBtDevConnAddr() throws RemoteException;

    String getBtLocalAddress() throws RemoteException;

    String getBtLocalName() throws RemoteException;

    String getBtMainDevices() throws RemoteException;

    String getBtRemoteDeviceName(String str) throws RemoteException;

    int getBtRemoteUuids(String str) throws RemoteException;

    int getBtRoleMode() throws RemoteException;

    int getBtState() throws RemoteException;

    String[] getBtVersionInfoArr() throws RemoteException;

    List<CallLogs> getCallLogsListForDB(int i) throws RemoteException;

    String getCallName(String str) throws RemoteException;

    String getCallPhoto(String str) throws RemoteException;

    List<Collection> getCollectionsForDB() throws RemoteException;

    List<Contacts> getContactsListForDB() throws RemoteException;

    boolean getDebugMode() throws RemoteException;

    String getDefaultPinCode() throws RemoteException;

    boolean getDualDeviceEnable() throws RemoteException;

    List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid parcelUuid) throws RemoteException;

    List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) throws RemoteException;

    List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException;

    int getGattServerConnectionState() throws RemoteException;

    int getHfpAudioConnectionState() throws RemoteException;

    List<GocHfpClientCall> getHfpCallList() throws RemoteException;

    List<GocHfpClientCall> getHfpCallList2() throws RemoteException;

    String getHfpConnectedAddress() throws RemoteException;

    List<String> getHfpConnectedAddressList() throws RemoteException;

    int getHfpConnectionState() throws RemoteException;

    int getHfpRemoteBatteryIndicator() throws RemoteException;

    String getHfpRemoteNetworkOperator() throws RemoteException;

    int getHfpRemoteSignalStrength() throws RemoteException;

    String getHfpRemoteSubscriberNumber() throws RemoteException;

    String getHidConnectedAddress() throws RemoteException;

    int getHidConnectionState() throws RemoteException;

    int getMapCurrentState(String str) throws RemoteException;

    int getMapRegisterState(String str) throws RemoteException;

    int getMaxDownCount(int i) throws RemoteException;

    int getMissedCallSize() throws RemoteException;

    String getNfServiceVersionName() throws RemoteException;

    String getOppFilePath() throws RemoteException;

    int getPbapConnectionState() throws RemoteException;

    int getPbapDownLoadState() throws RemoteException;

    String getPbapDownloadingAddress() throws RemoteException;

    int getPlayMode() throws RemoteException;

    byte getPlayStatus() throws RemoteException;

    String getRejectMapMsg() throws RemoteException;

    boolean getStartBtMusicType() throws RemoteException;

    String getTargetAddress() throws RemoteException;

    boolean getThreePartyCallEnable() throws RemoteException;

    String getUiServiceVersionName() throws RemoteException;

    int getUnreadSMS() throws RemoteException;

    void initMissedCallSize() throws RemoteException;

    void initUnreadSMS() throws RemoteException;

    boolean isA2dpConnected(String str) throws RemoteException;

    boolean isA2dpServiceReady() throws RemoteException;

    boolean isAvrcp13Supported(String str) throws RemoteException;

    boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException;

    boolean isAvrcp14Supported(String str) throws RemoteException;

    boolean isAvrcpConnected(String str) throws RemoteException;

    boolean isAvrcpServiceReady() throws RemoteException;

    boolean isBluetoothServiceReady() throws RemoteException;

    boolean isBtAutoAcceptPairingRequest() throws RemoteException;

    boolean isBtAutoConnectEnable() throws RemoteException;

    boolean isBtDiscoverable() throws RemoteException;

    boolean isBtDiscovering() throws RemoteException;

    boolean isBtEnabled() throws RemoteException;

    boolean isGattServiceReady() throws RemoteException;

    boolean isHfpConnected(String str) throws RemoteException;

    boolean isHfpInBandRingtoneSupport() throws RemoteException;

    boolean isHfpMicMute() throws RemoteException;

    boolean isHfpRemoteOnRoaming() throws RemoteException;

    boolean isHfpRemoteTelecomServiceOn() throws RemoteException;

    boolean isHfpRemoteVoiceDialOn() throws RemoteException;

    boolean isHfpServiceReady() throws RemoteException;

    boolean isHidConnected() throws RemoteException;

    boolean isHidServiceReady() throws RemoteException;

    boolean isMapNotificationRegistered(String str) throws RemoteException;

    boolean isMapServiceReady() throws RemoteException;

    boolean isOppServiceReady() throws RemoteException;

    boolean isPbapDownloading() throws RemoteException;

    boolean isPbapServiceReady() throws RemoteException;

    boolean isSppConnected(String str) throws RemoteException;

    boolean isSppServiceReady() throws RemoteException;

    void muteHfpMic(boolean z) throws RemoteException;

    void onQueryBluetoothConnect(int i) throws RemoteException;

    void pauseA2dpRender() throws RemoteException;

    void pauseHfpRender() throws RemoteException;

    boolean registerA2dpCallback(UiCallbackA2dp uiCallbackA2dp) throws RemoteException;

    boolean registerAvrcpCallback(UiCallbackAvrcp uiCallbackAvrcp) throws RemoteException;

    boolean registerBtCallback(UiCallbackBluetooth uiCallbackBluetooth) throws RemoteException;

    boolean registerGattServerCallback(UiCallbackGattServer uiCallbackGattServer) throws RemoteException;

    boolean registerHfpCallback(UiCallbackHfp uiCallbackHfp) throws RemoteException;

    boolean registerHidCallback(UiCallbackHid uiCallbackHid) throws RemoteException;

    boolean registerMapCallback(UiCallbackMap uiCallbackMap) throws RemoteException;

    boolean registerOppCallback(UiCallbackOpp uiCallbackOpp) throws RemoteException;

    boolean registerPbapCallback(UiCallbackPbap uiCallbackPbap) throws RemoteException;

    boolean registerSppCallback(UiCallbackSpp uiCallbackSpp) throws RemoteException;

    boolean reqA2dpConnect(String str) throws RemoteException;

    boolean reqA2dpDisconnect(String str) throws RemoteException;

    boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException;

    boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException;

    boolean reqAvrcp13GetPlayStatus() throws RemoteException;

    boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException;

    boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException;

    boolean reqAvrcp13GetPlayerSettingValuesList(byte b) throws RemoteException;

    boolean reqAvrcp13NextGroup() throws RemoteException;

    boolean reqAvrcp13PreviousGroup() throws RemoteException;

    boolean reqAvrcp13SetPlayerSettingValue(byte b, byte b2) throws RemoteException;

    boolean reqAvrcp14AddToNowPlaying(byte b, int i, long j) throws RemoteException;

    boolean reqAvrcp14ChangePath(int i, long j, byte b) throws RemoteException;

    boolean reqAvrcp14GetFolderItems(byte b) throws RemoteException;

    boolean reqAvrcp14GetItemAttributes(byte b, int i, long j) throws RemoteException;

    boolean reqAvrcp14PlaySelectedItem(byte b, int i, long j) throws RemoteException;

    boolean reqAvrcp14Search(String str) throws RemoteException;

    boolean reqAvrcp14SetAbsoluteVolume(byte b) throws RemoteException;

    boolean reqAvrcp14SetAddressedPlayer(int i) throws RemoteException;

    boolean reqAvrcp14SetBrowsedPlayer(int i) throws RemoteException;

    boolean reqAvrcpBackward() throws RemoteException;

    boolean reqAvrcpConnect(String str) throws RemoteException;

    boolean reqAvrcpDisconnect(String str) throws RemoteException;

    boolean reqAvrcpForward() throws RemoteException;

    boolean reqAvrcpPause() throws RemoteException;

    boolean reqAvrcpPlay() throws RemoteException;

    boolean reqAvrcpRegisterEventWatcher(byte b, long j) throws RemoteException;

    boolean reqAvrcpStartFastForward() throws RemoteException;

    boolean reqAvrcpStartRewind() throws RemoteException;

    boolean reqAvrcpStop() throws RemoteException;

    boolean reqAvrcpStopFastForward() throws RemoteException;

    boolean reqAvrcpStopRewind() throws RemoteException;

    boolean reqAvrcpUnregisterEventWatcher(byte b) throws RemoteException;

    void reqAvrcpUpdateSongStatus() throws RemoteException;

    boolean reqAvrcpVolumeDown() throws RemoteException;

    boolean reqAvrcpVolumeUp() throws RemoteException;

    int reqBtConnectHfpA2dp(String str) throws RemoteException;

    int reqBtDisconnectAll(String str) throws RemoteException;

    boolean reqBtPair(String str) throws RemoteException;

    boolean reqBtPairedDevices(int i) throws RemoteException;

    boolean reqBtUnpair(String str) throws RemoteException;

    boolean reqBtUnpairAllPairedDevices() throws RemoteException;

    boolean reqGattServerAddCharacteristic(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    boolean reqGattServerAddDescriptor(ParcelUuid parcelUuid, int i) throws RemoteException;

    boolean reqGattServerBeginServiceDeclaration(int i, ParcelUuid parcelUuid) throws RemoteException;

    boolean reqGattServerClearServices() throws RemoteException;

    boolean reqGattServerDisconnect(String str) throws RemoteException;

    boolean reqGattServerEndServiceDeclaration() throws RemoteException;

    boolean reqGattServerListen(boolean z) throws RemoteException;

    boolean reqGattServerRemoveService(int i, ParcelUuid parcelUuid) throws RemoteException;

    boolean reqGattServerSendNotification(String str, int i, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, boolean z, byte[] bArr) throws RemoteException;

    boolean reqGattServerSendResponse(String str, int i, int i2, int i3, byte[] bArr) throws RemoteException;

    boolean reqHfpAnswerCall(String str, int i) throws RemoteException;

    boolean reqHfpAudioTransferToCarkit(String str) throws RemoteException;

    boolean reqHfpAudioTransferToPhone(String str) throws RemoteException;

    boolean reqHfpConnect(String str) throws RemoteException;

    boolean reqHfpDialCall(String str) throws RemoteException;

    boolean reqHfpDisconnect(String str) throws RemoteException;

    boolean reqHfpMemoryDial(String str) throws RemoteException;

    boolean reqHfpReDial() throws RemoteException;

    boolean reqHfpRejectIncomingCall(String str) throws RemoteException;

    boolean reqHfpReply(String str) throws RemoteException;

    boolean reqHfpSendDtmf(String str, String str2) throws RemoteException;

    boolean reqHfpTerminateCurrentCall(String str) throws RemoteException;

    boolean reqHfpVoiceDial(boolean z) throws RemoteException;

    boolean reqHidConnect(String str) throws RemoteException;

    boolean reqHidDisconnect(String str) throws RemoteException;

    boolean reqMapChangeReadStatus(String str, int i, String str2, boolean z) throws RemoteException;

    void reqMapCleanDatabase() throws RemoteException;

    void reqMapDatabaseAvailable() throws RemoteException;

    void reqMapDeleteDatabaseByAddress(String str) throws RemoteException;

    boolean reqMapDeleteMessage(String str, int i, String str2) throws RemoteException;

    boolean reqMapDownloadInterrupt(String str) throws RemoteException;

    boolean reqMapDownloadMessage(String str, int i, boolean z, int i2, int i3, int i4, String str2, String str3, String str4, String str5, int i5, int i6) throws RemoteException;

    boolean reqMapDownloadSingleMessage(String str, int i, String str2, int i2) throws RemoteException;

    boolean reqMapRegisterNotification(String str, boolean z) throws RemoteException;

    boolean reqMapSendMessage(String str, String str2, String str3) throws RemoteException;

    void reqMapUnregisterNotification(String str) throws RemoteException;

    boolean reqOppAcceptReceiveFile(boolean z) throws RemoteException;

    boolean reqOppInterruptReceiveFile() throws RemoteException;

    void reqPbapCleanDatabase() throws RemoteException;

    void reqPbapDatabaseAvailable(String str) throws RemoteException;

    void reqPbapDatabaseQueryNameByNumber(String str, String str2) throws RemoteException;

    void reqPbapDatabaseQueryNameByPartialNumber(String str, String str2, int i) throws RemoteException;

    void reqPbapDeleteDatabaseByAddress(String str) throws RemoteException;

    boolean reqPbapDownload(String str, int i, int i2) throws RemoteException;

    boolean reqPbapDownloadInterrupt(String str) throws RemoteException;

    boolean reqPbapDownloadRange(String str, int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqPbapDownloadRangeToContactsProvider(String str, int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqPbapDownloadRangeToDatabase(String str, int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqPbapDownloadToContactsProvider(String str, int i, int i2) throws RemoteException;

    boolean reqPbapDownloadToDatabase(String str, int i, int i2) throws RemoteException;

    boolean reqSendHidMouseCommand(int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqSendHidVirtualKeyCommand(int i, int i2) throws RemoteException;

    boolean reqSppConnect(String str) throws RemoteException;

    void reqSppConnectedDeviceAddressList() throws RemoteException;

    boolean reqSppDisconnect(String str) throws RemoteException;

    void reqSppSendData(String str, byte[] bArr) throws RemoteException;

    void saveBellPath(String str) throws RemoteException;

    boolean setA2dpLocalVolume(float f) throws RemoteException;

    boolean setA2dpStreamType(int i) throws RemoteException;

    void setBtAnswerTypeEnable(boolean z) throws RemoteException;

    void setBtAutoAcceptPairingRequest(boolean z) throws RemoteException;

    void setBtAutoAnswerEnable(boolean z, int i) throws RemoteException;

    void setBtAutoConnect(int i, int i2) throws RemoteException;

    void setBtAutoDownEnable(boolean z, int i) throws RemoteException;

    void setBtDevConnAddr(String str) throws RemoteException;

    boolean setBtDiscoverableTimeout(int i) throws RemoteException;

    boolean setBtEnable(boolean z) throws RemoteException;

    boolean setBtLocalName(String str) throws RemoteException;

    void setBtMainDevices(String str) throws RemoteException;

    void setCallLogsToDB(List<CallLogs> list) throws RemoteException;

    void setCollectionsToDB(List<Collection> list) throws RemoteException;

    void setContactsToDB(List<Contacts> list) throws RemoteException;

    void setDefaultPinCode(String str) throws RemoteException;

    void setDualDeviceEnable(boolean z) throws RemoteException;

    boolean setMapDownloadNotify(int i) throws RemoteException;

    boolean setOppFilePath(String str) throws RemoteException;

    void setPairingConfirmation() throws RemoteException;

    boolean setPbapDownloadNotify(int i) throws RemoteException;

    boolean setPlayMode(int i) throws RemoteException;

    void setRejectMapMsg(String str) throws RemoteException;

    void setTargetAddress(String str) throws RemoteException;

    void setThreePartyCallEnable(boolean z) throws RemoteException;

    void setUiCallLogsToDB(String str, String str2, int i, String str3) throws RemoteException;

    void startA2dpRender() throws RemoteException;

    boolean startBtDiscovery() throws RemoteException;

    void startHfpRender() throws RemoteException;

    boolean switchBtRoleMode(int i) throws RemoteException;

    void switchingMainDevices(String str) throws RemoteException;

    boolean unregisterA2dpCallback(UiCallbackA2dp uiCallbackA2dp) throws RemoteException;

    boolean unregisterAvrcpCallback(UiCallbackAvrcp uiCallbackAvrcp) throws RemoteException;

    boolean unregisterBtCallback(UiCallbackBluetooth uiCallbackBluetooth) throws RemoteException;

    boolean unregisterGattServerCallback(UiCallbackGattServer uiCallbackGattServer) throws RemoteException;

    boolean unregisterHfpCallback(UiCallbackHfp uiCallbackHfp) throws RemoteException;

    boolean unregisterHidCallback(UiCallbackHid uiCallbackHid) throws RemoteException;

    boolean unregisterMapCallback(UiCallbackMap uiCallbackMap) throws RemoteException;

    boolean unregisterOppCallback(UiCallbackOpp uiCallbackOpp) throws RemoteException;

    boolean unregisterPbapCallback(UiCallbackPbap uiCallbackPbap) throws RemoteException;

    boolean unregisterSppCallback(UiCallbackSpp uiCallbackSpp) throws RemoteException;

    public static class Default implements UiCommand {
        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getUiServiceVersionName() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcpServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isA2dpServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isSppServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBluetoothServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHidServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isPbapServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isOppServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isMapServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerA2dpCallback(UiCallbackA2dp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterA2dpCallback(UiCallbackA2dp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getA2dpConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isA2dpConnected(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getA2dpConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqA2dpConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqA2dpDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void pauseA2dpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void startA2dpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setA2dpLocalVolume(float vol) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setA2dpStreamType(int type) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getA2dpStreamType() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerAvrcpCallback(UiCallbackAvrcp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterAvrcpCallback(UiCallbackAvrcp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getAvrcpConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcpConnected(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getAvrcpConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcp13Supported(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcp14Supported(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpPlay() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStop() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpPause() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpForward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpBackward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpVolumeUp() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpVolumeDown() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStartFastForward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStopFastForward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStartRewind() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStopRewind() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13NextGroup() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13PreviousGroup() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14Search(String text) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerBtCallback(UiCallbackBluetooth cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterBtCallback(UiCallbackBluetooth cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getNfServiceVersionName() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setBtEnable(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setBtDiscoverableTimeout(int timeout) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean startBtDiscovery() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean cancelBtDiscovery() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtPair(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtUnpair(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtPairedDevices(int opt) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtLocalName() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtRemoteDeviceName(String address) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtLocalAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setBtLocalName(String name) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtEnabled() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtDiscovering() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtDiscoverable() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtAutoConnectEnable() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int reqBtConnectHfpA2dp(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int reqBtDisconnectAll(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtRemoteUuids(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean switchBtRoleMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtRoleMode() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoConnect(int condition, int period) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtAutoConnectCondition() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtAutoConnectPeriod() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtAutoConnectState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtAutoConnectingAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerHfpCallback(UiCallbackHfp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterHfpCallback(UiCallbackHfp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpConnected(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHfpConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpAudioConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpRemoteSignalStrength() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<GocHfpClientCall> getHfpCallList() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpRemoteOnRoaming() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpRemoteBatteryIndicator() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpDialCall(String number) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpReDial() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpMemoryDial(String index) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpAnswerCall(String address, int flag) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpRejectIncomingCall(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpTerminateCurrentCall(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpSendDtmf(String address, String number) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpAudioTransferToCarkit(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpAudioTransferToPhone(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHfpRemoteNetworkOperator() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHfpRemoteSubscriberNumber() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpVoiceDial(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void pauseHfpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void startHfpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpMicMute() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void muteHfpMic(boolean mute) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpInBandRingtoneSupport() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerPbapCallback(UiCallbackPbap cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterPbapCallback(UiCallbackPbap cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getPbapConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isPbapDownloading() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getPbapDownloadingAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownload(String address, int storage, int property) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadRange(String address, int storage, int property, int startPos, int offset) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadToDatabase(String address, int storage, int property) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadRangeToDatabase(String address, int storage, int property, int startPos, int offset) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadToContactsProvider(String address, int storage, int property) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadRangeToContactsProvider(String address, int storage, int property, int startPos, int offset) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDatabaseQueryNameByNumber(String address, String target) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDatabaseQueryNameByPartialNumber(String address, String target, int findPosition) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDatabaseAvailable(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDeleteDatabaseByAddress(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapCleanDatabase() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadInterrupt(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setPbapDownloadNotify(int frequency) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerSppCallback(UiCallbackSpp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterSppCallback(UiCallbackSpp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSppConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSppDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqSppConnectedDeviceAddressList() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isSppConnected(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqSppSendData(String address, byte[] sppData) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerHidCallback(UiCallbackHid cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterHidCallback(UiCallbackHid cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHidConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHidConnected() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHidConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHidConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHidDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSendHidMouseCommand(int button, int offset_x, int offset_y, int wheel) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSendHidVirtualKeyCommand(int key_1, int key_2) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerMapCallback(UiCallbackMap cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterMapCallback(UiCallbackMap cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapUnregisterNotification(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isMapNotificationRegistered(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDownloadInterrupt(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapDatabaseAvailable() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapDeleteDatabaseByAddress(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapCleanDatabase() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMapCurrentState(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMapRegisterState(String address) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapSendMessage(String address, String message, String target) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setMapDownloadNotify(int frequency) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setDefaultPinCode(String pinCode) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getDefaultPinCode() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoAcceptPairingRequest(boolean auto) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtAutoAcceptPairingRequest() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerOppCallback(UiCallbackOpp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterOppCallback(UiCallbackOpp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setOppFilePath(String path) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getOppFilePath() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqOppAcceptReceiveFile(boolean accept) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqOppInterruptReceiveFile() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setTargetAddress(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getTargetAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqAvrcpUpdateSongStatus() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isGattServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerGattServerCallback(UiCallbackGattServer cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterGattServerCallback(UiCallbackGattServer cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getGattServerConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerBeginServiceDeclaration(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerAddCharacteristic(ParcelUuid charUuid, int properties, int permissions) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerAddDescriptor(ParcelUuid descUuid, int permissions) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerRemoveService(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerClearServices() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerListen(boolean listen) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerSendResponse(String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerSendNotification(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm, byte[] value) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid srvcUuid) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoDownEnable(boolean isBtAutoDownEnable, int opt) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getBtAutoDownState(int opt) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getBtAutoAnswerState() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getBtAnswerType() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtDevConnAddr(String btDevConnAddr) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtDevConnAddr() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setContactsToDB(List<Contacts> list) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<Contacts> getContactsListForDB() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setCallLogsToDB(List<CallLogs> list) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setUiCallLogsToDB(String name, String number, int type, String time) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<CallLogs> getCallLogsListForDB(int opt) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean deleteDatabase() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean cleanTable(int options) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getCallName(String number) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getCallPhoto(String number) throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setPairingConfirmation() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void cancelPairingUserInput() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMissedCallSize() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void initMissedCallSize() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void saveBellPath(String path) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBellPath() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void delCallLogsForDB(int id) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void delContactsForDB(int id) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setCollectionsToDB(List<Collection> list) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void delCollectionsForDB(int id) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<Collection> getCollectionsForDB() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public byte getPlayStatus() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtMainDevices(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtMainDevices() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void switchingMainDevices(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getUnreadSMS() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void initUnreadSMS() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setThreePartyCallEnable(boolean enable) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getThreePartyCallEnable() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String[] getBtVersionInfoArr() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMaxDownCount(int opt) throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getStartBtMusicType() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setDualDeviceEnable(boolean enable) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getDualDeviceEnable() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<String> getHfpConnectedAddressList() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getDebugMode() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtUnpairAllPairedDevices() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setRejectMapMsg(String msg) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getRejectMapMsg() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpReply(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getPbapDownLoadState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getPlayMode() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setPlayMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void onQueryBluetoothConnect(int wh) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements UiCommand {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.UiCommand";
        static final int TRANSACTION_cancelBtDiscovery = 70;
        static final int TRANSACTION_cancelPairingUserInput = 224;
        static final int TRANSACTION_cleanTable = 220;
        static final int TRANSACTION_delCallLogsForDB = 229;
        static final int TRANSACTION_delCollectionsForDB = 232;
        static final int TRANSACTION_delContactsForDB = 230;
        static final int TRANSACTION_deleteDatabase = 219;
        static final int TRANSACTION_getA2dpConnectedAddress = 15;
        static final int TRANSACTION_getA2dpConnectionState = 13;
        static final int TRANSACTION_getA2dpStreamType = 22;
        static final int TRANSACTION_getAvrcpConnectedAddress = 27;
        static final int TRANSACTION_getAvrcpConnectionState = 25;
        static final int TRANSACTION_getBellPath = 228;
        static final int TRANSACTION_getBtAnswerType = 211;
        static final int TRANSACTION_getBtAutoAnswerState = 209;
        static final int TRANSACTION_getBtAutoConnectCondition = 89;
        static final int TRANSACTION_getBtAutoConnectPeriod = 90;
        static final int TRANSACTION_getBtAutoConnectState = 91;
        static final int TRANSACTION_getBtAutoConnectingAddress = 92;
        static final int TRANSACTION_getBtAutoDownState = 207;
        static final int TRANSACTION_getBtDevConnAddr = 213;
        static final int TRANSACTION_getBtLocalAddress = 76;
        static final int TRANSACTION_getBtLocalName = 74;
        static final int TRANSACTION_getBtMainDevices = 236;
        static final int TRANSACTION_getBtRemoteDeviceName = 75;
        static final int TRANSACTION_getBtRemoteUuids = 85;
        static final int TRANSACTION_getBtRoleMode = 87;
        static final int TRANSACTION_getBtState = 79;
        static final int TRANSACTION_getBtVersionInfoArr = 242;
        static final int TRANSACTION_getCallLogsListForDB = 218;
        static final int TRANSACTION_getCallName = 221;
        static final int TRANSACTION_getCallPhoto = 222;
        static final int TRANSACTION_getCollectionsForDB = 233;
        static final int TRANSACTION_getContactsListForDB = 215;
        static final int TRANSACTION_getDebugMode = 248;
        static final int TRANSACTION_getDefaultPinCode = 177;
        static final int TRANSACTION_getDualDeviceEnable = 246;
        static final int TRANSACTION_getGattAddedGattCharacteristicUuidList = 204;
        static final int TRANSACTION_getGattAddedGattDescriptorUuidList = 205;
        static final int TRANSACTION_getGattAddedGattServiceUuidList = 203;
        static final int TRANSACTION_getGattServerConnectionState = 192;
        static final int TRANSACTION_getHfpAudioConnectionState = 98;
        static final int TRANSACTION_getHfpCallList = 102;
        static final int TRANSACTION_getHfpCallList2 = 103;
        static final int TRANSACTION_getHfpConnectedAddress = 97;
        static final int TRANSACTION_getHfpConnectedAddressList = 247;
        static final int TRANSACTION_getHfpConnectionState = 95;
        static final int TRANSACTION_getHfpRemoteBatteryIndicator = 105;
        static final int TRANSACTION_getHfpRemoteNetworkOperator = 117;
        static final int TRANSACTION_getHfpRemoteSignalStrength = 101;
        static final int TRANSACTION_getHfpRemoteSubscriberNumber = 118;
        static final int TRANSACTION_getHidConnectedAddress = 154;
        static final int TRANSACTION_getHidConnectionState = 152;
        static final int TRANSACTION_getMapCurrentState = 170;
        static final int TRANSACTION_getMapRegisterState = 171;
        static final int TRANSACTION_getMaxDownCount = 243;
        static final int TRANSACTION_getMissedCallSize = 225;
        static final int TRANSACTION_getNfServiceVersionName = 66;
        static final int TRANSACTION_getOppFilePath = 183;
        static final int TRANSACTION_getPbapConnectionState = 127;
        static final int TRANSACTION_getPbapDownLoadState = 253;
        static final int TRANSACTION_getPbapDownloadingAddress = 129;
        static final int TRANSACTION_getPlayMode = 254;
        static final int TRANSACTION_getPlayStatus = 234;
        static final int TRANSACTION_getRejectMapMsg = 251;
        static final int TRANSACTION_getStartBtMusicType = 244;
        static final int TRANSACTION_getTargetAddress = 187;
        static final int TRANSACTION_getThreePartyCallEnable = 241;
        static final int TRANSACTION_getUiServiceVersionName = 1;
        static final int TRANSACTION_getUnreadSMS = 238;
        static final int TRANSACTION_initMissedCallSize = 226;
        static final int TRANSACTION_initUnreadSMS = 239;
        static final int TRANSACTION_isA2dpConnected = 14;
        static final int TRANSACTION_isA2dpServiceReady = 3;
        static final int TRANSACTION_isAvrcp13Supported = 30;
        static final int TRANSACTION_isAvrcp14BrowsingChannelEstablished = 54;
        static final int TRANSACTION_isAvrcp14Supported = 31;
        static final int TRANSACTION_isAvrcpConnected = 26;
        static final int TRANSACTION_isAvrcpServiceReady = 2;
        static final int TRANSACTION_isBluetoothServiceReady = 5;
        static final int TRANSACTION_isBtAutoAcceptPairingRequest = 179;
        static final int TRANSACTION_isBtAutoConnectEnable = 82;
        static final int TRANSACTION_isBtDiscoverable = 81;
        static final int TRANSACTION_isBtDiscovering = 80;
        static final int TRANSACTION_isBtEnabled = 78;
        static final int TRANSACTION_isGattServiceReady = 189;
        static final int TRANSACTION_isHfpConnected = 96;
        static final int TRANSACTION_isHfpInBandRingtoneSupport = 124;
        static final int TRANSACTION_isHfpMicMute = 122;
        static final int TRANSACTION_isHfpRemoteOnRoaming = 104;
        static final int TRANSACTION_isHfpRemoteTelecomServiceOn = 106;
        static final int TRANSACTION_isHfpRemoteVoiceDialOn = 107;
        static final int TRANSACTION_isHfpServiceReady = 6;
        static final int TRANSACTION_isHidConnected = 153;
        static final int TRANSACTION_isHidServiceReady = 7;
        static final int TRANSACTION_isMapNotificationRegistered = 165;
        static final int TRANSACTION_isMapServiceReady = 10;
        static final int TRANSACTION_isOppServiceReady = 9;
        static final int TRANSACTION_isPbapDownloading = 128;
        static final int TRANSACTION_isPbapServiceReady = 8;
        static final int TRANSACTION_isSppConnected = 148;
        static final int TRANSACTION_isSppServiceReady = 4;
        static final int TRANSACTION_muteHfpMic = 123;
        static final int TRANSACTION_onQueryBluetoothConnect = 256;
        static final int TRANSACTION_pauseA2dpRender = 18;
        static final int TRANSACTION_pauseHfpRender = 120;
        static final int TRANSACTION_registerA2dpCallback = 11;
        static final int TRANSACTION_registerAvrcpCallback = 23;
        static final int TRANSACTION_registerBtCallback = 64;
        static final int TRANSACTION_registerGattServerCallback = 190;
        static final int TRANSACTION_registerHfpCallback = 93;
        static final int TRANSACTION_registerHidCallback = 150;
        static final int TRANSACTION_registerMapCallback = 159;
        static final int TRANSACTION_registerOppCallback = 180;
        static final int TRANSACTION_registerPbapCallback = 125;
        static final int TRANSACTION_registerSppCallback = 143;
        static final int TRANSACTION_reqA2dpConnect = 16;
        static final int TRANSACTION_reqA2dpDisconnect = 17;
        static final int TRANSACTION_reqAvrcp13GetCapabilitiesSupportEvent = 43;
        static final int TRANSACTION_reqAvrcp13GetElementAttributesPlaying = 48;
        static final int TRANSACTION_reqAvrcp13GetPlayStatus = 49;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingAttributesList = 44;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingCurrentValues = 46;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingValuesList = 45;
        static final int TRANSACTION_reqAvrcp13NextGroup = 52;
        static final int TRANSACTION_reqAvrcp13PreviousGroup = 53;
        static final int TRANSACTION_reqAvrcp13SetPlayerSettingValue = 47;
        static final int TRANSACTION_reqAvrcp14AddToNowPlaying = 62;
        static final int TRANSACTION_reqAvrcp14ChangePath = 58;
        static final int TRANSACTION_reqAvrcp14GetFolderItems = 57;
        static final int TRANSACTION_reqAvrcp14GetItemAttributes = 59;
        static final int TRANSACTION_reqAvrcp14PlaySelectedItem = 60;
        static final int TRANSACTION_reqAvrcp14Search = 61;
        static final int TRANSACTION_reqAvrcp14SetAbsoluteVolume = 63;
        static final int TRANSACTION_reqAvrcp14SetAddressedPlayer = 55;
        static final int TRANSACTION_reqAvrcp14SetBrowsedPlayer = 56;
        static final int TRANSACTION_reqAvrcpBackward = 36;
        static final int TRANSACTION_reqAvrcpConnect = 28;
        static final int TRANSACTION_reqAvrcpDisconnect = 29;
        static final int TRANSACTION_reqAvrcpForward = 35;
        static final int TRANSACTION_reqAvrcpPause = 34;
        static final int TRANSACTION_reqAvrcpPlay = 32;
        static final int TRANSACTION_reqAvrcpRegisterEventWatcher = 50;
        static final int TRANSACTION_reqAvrcpStartFastForward = 39;
        static final int TRANSACTION_reqAvrcpStartRewind = 41;
        static final int TRANSACTION_reqAvrcpStop = 33;
        static final int TRANSACTION_reqAvrcpStopFastForward = 40;
        static final int TRANSACTION_reqAvrcpStopRewind = 42;
        static final int TRANSACTION_reqAvrcpUnregisterEventWatcher = 51;
        static final int TRANSACTION_reqAvrcpUpdateSongStatus = 188;
        static final int TRANSACTION_reqAvrcpVolumeDown = 38;
        static final int TRANSACTION_reqAvrcpVolumeUp = 37;
        static final int TRANSACTION_reqBtConnectHfpA2dp = 83;
        static final int TRANSACTION_reqBtDisconnectAll = 84;
        static final int TRANSACTION_reqBtPair = 71;
        static final int TRANSACTION_reqBtPairedDevices = 73;
        static final int TRANSACTION_reqBtUnpair = 72;
        static final int TRANSACTION_reqBtUnpairAllPairedDevices = 249;
        static final int TRANSACTION_reqGattServerAddCharacteristic = 195;
        static final int TRANSACTION_reqGattServerAddDescriptor = 196;
        static final int TRANSACTION_reqGattServerBeginServiceDeclaration = 194;
        static final int TRANSACTION_reqGattServerClearServices = 199;
        static final int TRANSACTION_reqGattServerDisconnect = 193;
        static final int TRANSACTION_reqGattServerEndServiceDeclaration = 197;
        static final int TRANSACTION_reqGattServerListen = 200;
        static final int TRANSACTION_reqGattServerRemoveService = 198;
        static final int TRANSACTION_reqGattServerSendNotification = 202;
        static final int TRANSACTION_reqGattServerSendResponse = 201;
        static final int TRANSACTION_reqHfpAnswerCall = 111;
        static final int TRANSACTION_reqHfpAudioTransferToCarkit = 115;
        static final int TRANSACTION_reqHfpAudioTransferToPhone = 116;
        static final int TRANSACTION_reqHfpConnect = 99;
        static final int TRANSACTION_reqHfpDialCall = 108;
        static final int TRANSACTION_reqHfpDisconnect = 100;
        static final int TRANSACTION_reqHfpMemoryDial = 110;
        static final int TRANSACTION_reqHfpReDial = 109;
        static final int TRANSACTION_reqHfpRejectIncomingCall = 112;
        static final int TRANSACTION_reqHfpReply = 252;
        static final int TRANSACTION_reqHfpSendDtmf = 114;
        static final int TRANSACTION_reqHfpTerminateCurrentCall = 113;
        static final int TRANSACTION_reqHfpVoiceDial = 119;
        static final int TRANSACTION_reqHidConnect = 155;
        static final int TRANSACTION_reqHidDisconnect = 156;
        static final int TRANSACTION_reqMapChangeReadStatus = 174;
        static final int TRANSACTION_reqMapCleanDatabase = 169;
        static final int TRANSACTION_reqMapDatabaseAvailable = 167;
        static final int TRANSACTION_reqMapDeleteDatabaseByAddress = 168;
        static final int TRANSACTION_reqMapDeleteMessage = 173;
        static final int TRANSACTION_reqMapDownloadInterrupt = 166;
        static final int TRANSACTION_reqMapDownloadMessage = 162;
        static final int TRANSACTION_reqMapDownloadSingleMessage = 161;
        static final int TRANSACTION_reqMapRegisterNotification = 163;
        static final int TRANSACTION_reqMapSendMessage = 172;
        static final int TRANSACTION_reqMapUnregisterNotification = 164;
        static final int TRANSACTION_reqOppAcceptReceiveFile = 184;
        static final int TRANSACTION_reqOppInterruptReceiveFile = 185;
        static final int TRANSACTION_reqPbapCleanDatabase = 140;
        static final int TRANSACTION_reqPbapDatabaseAvailable = 138;
        static final int TRANSACTION_reqPbapDatabaseQueryNameByNumber = 136;
        static final int TRANSACTION_reqPbapDatabaseQueryNameByPartialNumber = 137;
        static final int TRANSACTION_reqPbapDeleteDatabaseByAddress = 139;
        static final int TRANSACTION_reqPbapDownload = 130;
        static final int TRANSACTION_reqPbapDownloadInterrupt = 141;
        static final int TRANSACTION_reqPbapDownloadRange = 131;
        static final int TRANSACTION_reqPbapDownloadRangeToContactsProvider = 135;
        static final int TRANSACTION_reqPbapDownloadRangeToDatabase = 133;
        static final int TRANSACTION_reqPbapDownloadToContactsProvider = 134;
        static final int TRANSACTION_reqPbapDownloadToDatabase = 132;
        static final int TRANSACTION_reqSendHidMouseCommand = 157;
        static final int TRANSACTION_reqSendHidVirtualKeyCommand = 158;
        static final int TRANSACTION_reqSppConnect = 145;
        static final int TRANSACTION_reqSppConnectedDeviceAddressList = 147;
        static final int TRANSACTION_reqSppDisconnect = 146;
        static final int TRANSACTION_reqSppSendData = 149;
        static final int TRANSACTION_saveBellPath = 227;
        static final int TRANSACTION_setA2dpLocalVolume = 20;
        static final int TRANSACTION_setA2dpStreamType = 21;
        static final int TRANSACTION_setBtAnswerTypeEnable = 210;
        static final int TRANSACTION_setBtAutoAcceptPairingRequest = 178;
        static final int TRANSACTION_setBtAutoAnswerEnable = 208;
        static final int TRANSACTION_setBtAutoConnect = 88;
        static final int TRANSACTION_setBtAutoDownEnable = 206;
        static final int TRANSACTION_setBtDevConnAddr = 212;
        static final int TRANSACTION_setBtDiscoverableTimeout = 68;
        static final int TRANSACTION_setBtEnable = 67;
        static final int TRANSACTION_setBtLocalName = 77;
        static final int TRANSACTION_setBtMainDevices = 235;
        static final int TRANSACTION_setCallLogsToDB = 216;
        static final int TRANSACTION_setCollectionsToDB = 231;
        static final int TRANSACTION_setContactsToDB = 214;
        static final int TRANSACTION_setDefaultPinCode = 176;
        static final int TRANSACTION_setDualDeviceEnable = 245;
        static final int TRANSACTION_setMapDownloadNotify = 175;
        static final int TRANSACTION_setOppFilePath = 182;
        static final int TRANSACTION_setPairingConfirmation = 223;
        static final int TRANSACTION_setPbapDownloadNotify = 142;
        static final int TRANSACTION_setPlayMode = 255;
        static final int TRANSACTION_setRejectMapMsg = 250;
        static final int TRANSACTION_setTargetAddress = 186;
        static final int TRANSACTION_setThreePartyCallEnable = 240;
        static final int TRANSACTION_setUiCallLogsToDB = 217;
        static final int TRANSACTION_startA2dpRender = 19;
        static final int TRANSACTION_startBtDiscovery = 69;
        static final int TRANSACTION_startHfpRender = 121;
        static final int TRANSACTION_switchBtRoleMode = 86;
        static final int TRANSACTION_switchingMainDevices = 237;
        static final int TRANSACTION_unregisterA2dpCallback = 12;
        static final int TRANSACTION_unregisterAvrcpCallback = 24;
        static final int TRANSACTION_unregisterBtCallback = 65;
        static final int TRANSACTION_unregisterGattServerCallback = 191;
        static final int TRANSACTION_unregisterHfpCallback = 94;
        static final int TRANSACTION_unregisterHidCallback = 151;
        static final int TRANSACTION_unregisterMapCallback = 160;
        static final int TRANSACTION_unregisterOppCallback = 181;
        static final int TRANSACTION_unregisterPbapCallback = 126;
        static final int TRANSACTION_unregisterSppCallback = 144;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCommand asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof UiCommand)) {
                return new Proxy(obj);
            }
            return (UiCommand) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParcelUuid _arg1;
            ParcelUuid _arg0;
            ParcelUuid _arg02;
            ParcelUuid _arg12;
            ParcelUuid _arg2;
            ParcelUuid _arg3;
            ParcelUuid _arg03;
            ParcelUuid _arg04;
            ParcelUuid _arg13;
            if (code != 1598968902) {
                boolean _arg05 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        String _result = getUiServiceVersionName();
                        reply.writeNoException();
                        reply.writeString(_result);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcpServiceReady = isAvrcpServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isAvrcpServiceReady ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isA2dpServiceReady = isA2dpServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isA2dpServiceReady ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isSppServiceReady = isSppServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isSppServiceReady ? 1 : 0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isBluetoothServiceReady = isBluetoothServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isBluetoothServiceReady ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpServiceReady = isHfpServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isHfpServiceReady ? 1 : 0);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHidServiceReady = isHidServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isHidServiceReady ? 1 : 0);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isPbapServiceReady = isPbapServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isPbapServiceReady ? 1 : 0);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isOppServiceReady = isOppServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isOppServiceReady ? 1 : 0);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isMapServiceReady = isMapServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isMapServiceReady ? 1 : 0);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerA2dpCallback = registerA2dpCallback(UiCallbackA2dp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerA2dpCallback ? 1 : 0);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterA2dpCallback = unregisterA2dpCallback(UiCallbackA2dp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterA2dpCallback ? 1 : 0);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        int _result2 = getA2dpConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result2);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isA2dpConnected = isA2dpConnected(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isA2dpConnected ? 1 : 0);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        String _result3 = getA2dpConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result3);
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqA2dpConnect = reqA2dpConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqA2dpConnect ? 1 : 0);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqA2dpDisconnect = reqA2dpDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqA2dpDisconnect ? 1 : 0);
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        pauseA2dpRender();
                        reply.writeNoException();
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        startA2dpRender();
                        reply.writeNoException();
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        boolean a2dpLocalVolume = setA2dpLocalVolume(data.readFloat());
                        reply.writeNoException();
                        reply.writeInt(a2dpLocalVolume ? 1 : 0);
                        return true;
                    case 21:
                        data.enforceInterface(DESCRIPTOR);
                        boolean a2dpStreamType = setA2dpStreamType(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(a2dpStreamType ? 1 : 0);
                        return true;
                    case 22:
                        data.enforceInterface(DESCRIPTOR);
                        int _result4 = getA2dpStreamType();
                        reply.writeNoException();
                        reply.writeInt(_result4);
                        return true;
                    case 23:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerAvrcpCallback = registerAvrcpCallback(UiCallbackAvrcp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerAvrcpCallback ? 1 : 0);
                        return true;
                    case 24:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterAvrcpCallback = unregisterAvrcpCallback(UiCallbackAvrcp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterAvrcpCallback ? 1 : 0);
                        return true;
                    case 25:
                        data.enforceInterface(DESCRIPTOR);
                        int _result5 = getAvrcpConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result5);
                        return true;
                    case 26:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcpConnected = isAvrcpConnected(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isAvrcpConnected ? 1 : 0);
                        return true;
                    case 27:
                        data.enforceInterface(DESCRIPTOR);
                        String _result6 = getAvrcpConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result6);
                        return true;
                    case 28:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpConnect = reqAvrcpConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpConnect ? 1 : 0);
                        return true;
                    case 29:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpDisconnect = reqAvrcpDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpDisconnect ? 1 : 0);
                        return true;
                    case 30:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcp13Supported = isAvrcp13Supported(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isAvrcp13Supported ? 1 : 0);
                        return true;
                    case 31:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcp14Supported = isAvrcp14Supported(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isAvrcp14Supported ? 1 : 0);
                        return true;
                    case 32:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpPlay = reqAvrcpPlay();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpPlay ? 1 : 0);
                        return true;
                    case 33:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStop = reqAvrcpStop();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStop ? 1 : 0);
                        return true;
                    case 34:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpPause = reqAvrcpPause();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpPause ? 1 : 0);
                        return true;
                    case 35:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpForward = reqAvrcpForward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpForward ? 1 : 0);
                        return true;
                    case 36:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpBackward = reqAvrcpBackward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpBackward ? 1 : 0);
                        return true;
                    case 37:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpVolumeUp = reqAvrcpVolumeUp();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpVolumeUp ? 1 : 0);
                        return true;
                    case 38:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpVolumeDown = reqAvrcpVolumeDown();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpVolumeDown ? 1 : 0);
                        return true;
                    case 39:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStartFastForward = reqAvrcpStartFastForward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStartFastForward ? 1 : 0);
                        return true;
                    case 40:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStopFastForward = reqAvrcpStopFastForward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStopFastForward ? 1 : 0);
                        return true;
                    case 41:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStartRewind = reqAvrcpStartRewind();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStartRewind ? 1 : 0);
                        return true;
                    case 42:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStopRewind = reqAvrcpStopRewind();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStopRewind ? 1 : 0);
                        return true;
                    case 43:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetCapabilitiesSupportEvent = reqAvrcp13GetCapabilitiesSupportEvent();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetCapabilitiesSupportEvent ? 1 : 0);
                        return true;
                    case 44:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayerSettingAttributesList = reqAvrcp13GetPlayerSettingAttributesList();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayerSettingAttributesList ? 1 : 0);
                        return true;
                    case 45:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayerSettingValuesList = reqAvrcp13GetPlayerSettingValuesList(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayerSettingValuesList ? 1 : 0);
                        return true;
                    case 46:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayerSettingCurrentValues = reqAvrcp13GetPlayerSettingCurrentValues();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayerSettingCurrentValues ? 1 : 0);
                        return true;
                    case 47:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13SetPlayerSettingValue = reqAvrcp13SetPlayerSettingValue(data.readByte(), data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13SetPlayerSettingValue ? 1 : 0);
                        return true;
                    case 48:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetElementAttributesPlaying = reqAvrcp13GetElementAttributesPlaying();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetElementAttributesPlaying ? 1 : 0);
                        return true;
                    case 49:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayStatus = reqAvrcp13GetPlayStatus();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayStatus ? 1 : 0);
                        return true;
                    case 50:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpRegisterEventWatcher = reqAvrcpRegisterEventWatcher(data.readByte(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpRegisterEventWatcher ? 1 : 0);
                        return true;
                    case 51:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpUnregisterEventWatcher = reqAvrcpUnregisterEventWatcher(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpUnregisterEventWatcher ? 1 : 0);
                        return true;
                    case 52:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13NextGroup = reqAvrcp13NextGroup();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13NextGroup ? 1 : 0);
                        return true;
                    case 53:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13PreviousGroup = reqAvrcp13PreviousGroup();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13PreviousGroup ? 1 : 0);
                        return true;
                    case 54:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcp14BrowsingChannelEstablished = isAvrcp14BrowsingChannelEstablished();
                        reply.writeNoException();
                        reply.writeInt(isAvrcp14BrowsingChannelEstablished ? 1 : 0);
                        return true;
                    case 55:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14SetAddressedPlayer = reqAvrcp14SetAddressedPlayer(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14SetAddressedPlayer ? 1 : 0);
                        return true;
                    case 56:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14SetBrowsedPlayer = reqAvrcp14SetBrowsedPlayer(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14SetBrowsedPlayer ? 1 : 0);
                        return true;
                    case 57:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14GetFolderItems = reqAvrcp14GetFolderItems(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14GetFolderItems ? 1 : 0);
                        return true;
                    case 58:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14ChangePath = reqAvrcp14ChangePath(data.readInt(), data.readLong(), data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14ChangePath ? 1 : 0);
                        return true;
                    case 59:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14GetItemAttributes = reqAvrcp14GetItemAttributes(data.readByte(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14GetItemAttributes ? 1 : 0);
                        return true;
                    case 60:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14PlaySelectedItem = reqAvrcp14PlaySelectedItem(data.readByte(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14PlaySelectedItem ? 1 : 0);
                        return true;
                    case 61:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14Search = reqAvrcp14Search(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14Search ? 1 : 0);
                        return true;
                    case 62:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14AddToNowPlaying = reqAvrcp14AddToNowPlaying(data.readByte(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14AddToNowPlaying ? 1 : 0);
                        return true;
                    case 63:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14SetAbsoluteVolume = reqAvrcp14SetAbsoluteVolume(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14SetAbsoluteVolume ? 1 : 0);
                        return true;
                    case 64:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerBtCallback = registerBtCallback(UiCallbackBluetooth.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerBtCallback ? 1 : 0);
                        return true;
                    case 65:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterBtCallback = unregisterBtCallback(UiCallbackBluetooth.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterBtCallback ? 1 : 0);
                        return true;
                    case 66:
                        data.enforceInterface(DESCRIPTOR);
                        String _result7 = getNfServiceVersionName();
                        reply.writeNoException();
                        reply.writeString(_result7);
                        return true;
                    case 67:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean btEnable = setBtEnable(_arg05);
                        reply.writeNoException();
                        reply.writeInt(btEnable ? 1 : 0);
                        return true;
                    case 68:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btDiscoverableTimeout = setBtDiscoverableTimeout(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(btDiscoverableTimeout ? 1 : 0);
                        return true;
                    case 69:
                        data.enforceInterface(DESCRIPTOR);
                        boolean startBtDiscovery = startBtDiscovery();
                        reply.writeNoException();
                        reply.writeInt(startBtDiscovery ? 1 : 0);
                        return true;
                    case 70:
                        data.enforceInterface(DESCRIPTOR);
                        boolean cancelBtDiscovery = cancelBtDiscovery();
                        reply.writeNoException();
                        reply.writeInt(cancelBtDiscovery ? 1 : 0);
                        return true;
                    case 71:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqBtPair = reqBtPair(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqBtPair ? 1 : 0);
                        return true;
                    case 72:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqBtUnpair = reqBtUnpair(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqBtUnpair ? 1 : 0);
                        return true;
                    case 73:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqBtPairedDevices = reqBtPairedDevices(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqBtPairedDevices ? 1 : 0);
                        return true;
                    case 74:
                        data.enforceInterface(DESCRIPTOR);
                        String _result8 = getBtLocalName();
                        reply.writeNoException();
                        reply.writeString(_result8);
                        return true;
                    case 75:
                        data.enforceInterface(DESCRIPTOR);
                        String _result9 = getBtRemoteDeviceName(data.readString());
                        reply.writeNoException();
                        reply.writeString(_result9);
                        return true;
                    case 76:
                        data.enforceInterface(DESCRIPTOR);
                        String _result10 = getBtLocalAddress();
                        reply.writeNoException();
                        reply.writeString(_result10);
                        return true;
                    case 77:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btLocalName = setBtLocalName(data.readString());
                        reply.writeNoException();
                        reply.writeInt(btLocalName ? 1 : 0);
                        return true;
                    case 78:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isBtEnabled = isBtEnabled();
                        reply.writeNoException();
                        reply.writeInt(isBtEnabled ? 1 : 0);
                        return true;
                    case 79:
                        data.enforceInterface(DESCRIPTOR);
                        int _result11 = getBtState();
                        reply.writeNoException();
                        reply.writeInt(_result11);
                        return true;
                    case 80:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isBtDiscovering = isBtDiscovering();
                        reply.writeNoException();
                        reply.writeInt(isBtDiscovering ? 1 : 0);
                        return true;
                    case 81:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isBtDiscoverable = isBtDiscoverable();
                        reply.writeNoException();
                        reply.writeInt(isBtDiscoverable ? 1 : 0);
                        return true;
                    case 82:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isBtAutoConnectEnable = isBtAutoConnectEnable();
                        reply.writeNoException();
                        reply.writeInt(isBtAutoConnectEnable ? 1 : 0);
                        return true;
                    case 83:
                        data.enforceInterface(DESCRIPTOR);
                        int _result12 = reqBtConnectHfpA2dp(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result12);
                        return true;
                    case 84:
                        data.enforceInterface(DESCRIPTOR);
                        int _result13 = reqBtDisconnectAll(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result13);
                        return true;
                    case 85:
                        data.enforceInterface(DESCRIPTOR);
                        int _result14 = getBtRemoteUuids(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result14);
                        return true;
                    case 86:
                        data.enforceInterface(DESCRIPTOR);
                        boolean switchBtRoleMode = switchBtRoleMode(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(switchBtRoleMode ? 1 : 0);
                        return true;
                    case 87:
                        data.enforceInterface(DESCRIPTOR);
                        int _result15 = getBtRoleMode();
                        reply.writeNoException();
                        reply.writeInt(_result15);
                        return true;
                    case 88:
                        data.enforceInterface(DESCRIPTOR);
                        setBtAutoConnect(data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 89:
                        data.enforceInterface(DESCRIPTOR);
                        int _result16 = getBtAutoConnectCondition();
                        reply.writeNoException();
                        reply.writeInt(_result16);
                        return true;
                    case 90:
                        data.enforceInterface(DESCRIPTOR);
                        int _result17 = getBtAutoConnectPeriod();
                        reply.writeNoException();
                        reply.writeInt(_result17);
                        return true;
                    case 91:
                        data.enforceInterface(DESCRIPTOR);
                        int _result18 = getBtAutoConnectState();
                        reply.writeNoException();
                        reply.writeInt(_result18);
                        return true;
                    case 92:
                        data.enforceInterface(DESCRIPTOR);
                        String _result19 = getBtAutoConnectingAddress();
                        reply.writeNoException();
                        reply.writeString(_result19);
                        return true;
                    case 93:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerHfpCallback = registerHfpCallback(UiCallbackHfp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerHfpCallback ? 1 : 0);
                        return true;
                    case 94:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterHfpCallback = unregisterHfpCallback(UiCallbackHfp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterHfpCallback ? 1 : 0);
                        return true;
                    case 95:
                        data.enforceInterface(DESCRIPTOR);
                        int _result20 = getHfpConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result20);
                        return true;
                    case 96:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpConnected = isHfpConnected(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isHfpConnected ? 1 : 0);
                        return true;
                    case 97:
                        data.enforceInterface(DESCRIPTOR);
                        String _result21 = getHfpConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result21);
                        return true;
                    case 98:
                        data.enforceInterface(DESCRIPTOR);
                        int _result22 = getHfpAudioConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result22);
                        return true;
                    case 99:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpConnect = reqHfpConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpConnect ? 1 : 0);
                        return true;
                    case 100:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpDisconnect = reqHfpDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpDisconnect ? 1 : 0);
                        return true;
                    case 101:
                        data.enforceInterface(DESCRIPTOR);
                        int _result23 = getHfpRemoteSignalStrength();
                        reply.writeNoException();
                        reply.writeInt(_result23);
                        return true;
                    case 102:
                        data.enforceInterface(DESCRIPTOR);
                        List<GocHfpClientCall> _result24 = getHfpCallList();
                        reply.writeNoException();
                        reply.writeTypedList(_result24);
                        return true;
                    case 103:
                        data.enforceInterface(DESCRIPTOR);
                        List<GocHfpClientCall> _result25 = getHfpCallList2();
                        reply.writeNoException();
                        reply.writeTypedList(_result25);
                        return true;
                    case 104:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpRemoteOnRoaming = isHfpRemoteOnRoaming();
                        reply.writeNoException();
                        reply.writeInt(isHfpRemoteOnRoaming ? 1 : 0);
                        return true;
                    case 105:
                        data.enforceInterface(DESCRIPTOR);
                        int _result26 = getHfpRemoteBatteryIndicator();
                        reply.writeNoException();
                        reply.writeInt(_result26);
                        return true;
                    case 106:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpRemoteTelecomServiceOn = isHfpRemoteTelecomServiceOn();
                        reply.writeNoException();
                        reply.writeInt(isHfpRemoteTelecomServiceOn ? 1 : 0);
                        return true;
                    case 107:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpRemoteVoiceDialOn = isHfpRemoteVoiceDialOn();
                        reply.writeNoException();
                        reply.writeInt(isHfpRemoteVoiceDialOn ? 1 : 0);
                        return true;
                    case 108:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpDialCall = reqHfpDialCall(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpDialCall ? 1 : 0);
                        return true;
                    case 109:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpReDial = reqHfpReDial();
                        reply.writeNoException();
                        reply.writeInt(reqHfpReDial ? 1 : 0);
                        return true;
                    case 110:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpMemoryDial = reqHfpMemoryDial(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpMemoryDial ? 1 : 0);
                        return true;
                    case 111:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpAnswerCall = reqHfpAnswerCall(data.readString(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqHfpAnswerCall ? 1 : 0);
                        return true;
                    case 112:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpRejectIncomingCall = reqHfpRejectIncomingCall(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpRejectIncomingCall ? 1 : 0);
                        return true;
                    case 113:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpTerminateCurrentCall = reqHfpTerminateCurrentCall(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpTerminateCurrentCall ? 1 : 0);
                        return true;
                    case 114:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpSendDtmf = reqHfpSendDtmf(data.readString(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpSendDtmf ? 1 : 0);
                        return true;
                    case 115:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpAudioTransferToCarkit = reqHfpAudioTransferToCarkit(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpAudioTransferToCarkit ? 1 : 0);
                        return true;
                    case 116:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpAudioTransferToPhone = reqHfpAudioTransferToPhone(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpAudioTransferToPhone ? 1 : 0);
                        return true;
                    case 117:
                        data.enforceInterface(DESCRIPTOR);
                        String _result27 = getHfpRemoteNetworkOperator();
                        reply.writeNoException();
                        reply.writeString(_result27);
                        return true;
                    case 118:
                        data.enforceInterface(DESCRIPTOR);
                        String _result28 = getHfpRemoteSubscriberNumber();
                        reply.writeNoException();
                        reply.writeString(_result28);
                        return true;
                    case 119:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean reqHfpVoiceDial = reqHfpVoiceDial(_arg05);
                        reply.writeNoException();
                        reply.writeInt(reqHfpVoiceDial ? 1 : 0);
                        return true;
                    case 120:
                        data.enforceInterface(DESCRIPTOR);
                        pauseHfpRender();
                        reply.writeNoException();
                        return true;
                    case 121:
                        data.enforceInterface(DESCRIPTOR);
                        startHfpRender();
                        reply.writeNoException();
                        return true;
                    case 122:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpMicMute = isHfpMicMute();
                        reply.writeNoException();
                        reply.writeInt(isHfpMicMute ? 1 : 0);
                        return true;
                    case 123:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        muteHfpMic(_arg05);
                        reply.writeNoException();
                        return true;
                    case 124:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpInBandRingtoneSupport = isHfpInBandRingtoneSupport();
                        reply.writeNoException();
                        reply.writeInt(isHfpInBandRingtoneSupport ? 1 : 0);
                        return true;
                    case 125:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerPbapCallback = registerPbapCallback(UiCallbackPbap.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerPbapCallback ? 1 : 0);
                        return true;
                    case 126:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterPbapCallback = unregisterPbapCallback(UiCallbackPbap.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterPbapCallback ? 1 : 0);
                        return true;
                    case TRANSACTION_getPbapConnectionState /* 127 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result29 = getPbapConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result29);
                        return true;
                    case 128:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isPbapDownloading = isPbapDownloading();
                        reply.writeNoException();
                        reply.writeInt(isPbapDownloading ? 1 : 0);
                        return true;
                    case 129:
                        data.enforceInterface(DESCRIPTOR);
                        String _result30 = getPbapDownloadingAddress();
                        reply.writeNoException();
                        reply.writeString(_result30);
                        return true;
                    case 130:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownload = reqPbapDownload(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownload ? 1 : 0);
                        return true;
                    case 131:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownloadRange = reqPbapDownloadRange(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownloadRange ? 1 : 0);
                        return true;
                    case 132:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownloadToDatabase = reqPbapDownloadToDatabase(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownloadToDatabase ? 1 : 0);
                        return true;
                    case 133:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownloadRangeToDatabase = reqPbapDownloadRangeToDatabase(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownloadRangeToDatabase ? 1 : 0);
                        return true;
                    case 134:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownloadToContactsProvider = reqPbapDownloadToContactsProvider(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownloadToContactsProvider ? 1 : 0);
                        return true;
                    case 135:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownloadRangeToContactsProvider = reqPbapDownloadRangeToContactsProvider(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownloadRangeToContactsProvider ? 1 : 0);
                        return true;
                    case 136:
                        data.enforceInterface(DESCRIPTOR);
                        reqPbapDatabaseQueryNameByNumber(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 137:
                        data.enforceInterface(DESCRIPTOR);
                        reqPbapDatabaseQueryNameByPartialNumber(data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 138:
                        data.enforceInterface(DESCRIPTOR);
                        reqPbapDatabaseAvailable(data.readString());
                        reply.writeNoException();
                        return true;
                    case 139:
                        data.enforceInterface(DESCRIPTOR);
                        reqPbapDeleteDatabaseByAddress(data.readString());
                        reply.writeNoException();
                        return true;
                    case 140:
                        data.enforceInterface(DESCRIPTOR);
                        reqPbapCleanDatabase();
                        reply.writeNoException();
                        return true;
                    case 141:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqPbapDownloadInterrupt = reqPbapDownloadInterrupt(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqPbapDownloadInterrupt ? 1 : 0);
                        return true;
                    case 142:
                        data.enforceInterface(DESCRIPTOR);
                        boolean pbapDownloadNotify = setPbapDownloadNotify(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(pbapDownloadNotify ? 1 : 0);
                        return true;
                    case TRANSACTION_registerSppCallback /* 143 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerSppCallback = registerSppCallback(UiCallbackSpp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerSppCallback ? 1 : 0);
                        return true;
                    case TRANSACTION_unregisterSppCallback /* 144 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterSppCallback = unregisterSppCallback(UiCallbackSpp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterSppCallback ? 1 : 0);
                        return true;
                    case 145:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqSppConnect = reqSppConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqSppConnect ? 1 : 0);
                        return true;
                    case 146:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqSppDisconnect = reqSppDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqSppDisconnect ? 1 : 0);
                        return true;
                    case 147:
                        data.enforceInterface(DESCRIPTOR);
                        reqSppConnectedDeviceAddressList();
                        reply.writeNoException();
                        return true;
                    case 148:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isSppConnected = isSppConnected(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isSppConnected ? 1 : 0);
                        return true;
                    case 149:
                        data.enforceInterface(DESCRIPTOR);
                        reqSppSendData(data.readString(), data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 150:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerHidCallback = registerHidCallback(UiCallbackHid.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerHidCallback ? 1 : 0);
                        return true;
                    case 151:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterHidCallback = unregisterHidCallback(UiCallbackHid.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterHidCallback ? 1 : 0);
                        return true;
                    case 152:
                        data.enforceInterface(DESCRIPTOR);
                        int _result31 = getHidConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result31);
                        return true;
                    case 153:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHidConnected = isHidConnected();
                        reply.writeNoException();
                        reply.writeInt(isHidConnected ? 1 : 0);
                        return true;
                    case 154:
                        data.enforceInterface(DESCRIPTOR);
                        String _result32 = getHidConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result32);
                        return true;
                    case 155:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHidConnect = reqHidConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHidConnect ? 1 : 0);
                        return true;
                    case 156:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHidDisconnect = reqHidDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHidDisconnect ? 1 : 0);
                        return true;
                    case 157:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqSendHidMouseCommand = reqSendHidMouseCommand(data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqSendHidMouseCommand ? 1 : 0);
                        return true;
                    case 158:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqSendHidVirtualKeyCommand = reqSendHidVirtualKeyCommand(data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqSendHidVirtualKeyCommand ? 1 : 0);
                        return true;
                    case 159:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerMapCallback = registerMapCallback(UiCallbackMap.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerMapCallback ? 1 : 0);
                        return true;
                    case 160:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterMapCallback = unregisterMapCallback(UiCallbackMap.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterMapCallback ? 1 : 0);
                        return true;
                    case 161:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDownloadSingleMessage = reqMapDownloadSingleMessage(data.readString(), data.readInt(), data.readString(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqMapDownloadSingleMessage ? 1 : 0);
                        return true;
                    case 162:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDownloadMessage = reqMapDownloadMessage(data.readString(), data.readInt(), data.readInt() != 0, data.readInt(), data.readInt(), data.readInt(), data.readString(), data.readString(), data.readString(), data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqMapDownloadMessage ? 1 : 0);
                        return true;
                    case 163:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg06 = data.readString();
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean reqMapRegisterNotification = reqMapRegisterNotification(_arg06, _arg05);
                        reply.writeNoException();
                        reply.writeInt(reqMapRegisterNotification ? 1 : 0);
                        return true;
                    case 164:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapUnregisterNotification(data.readString());
                        reply.writeNoException();
                        return true;
                    case 165:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isMapNotificationRegistered = isMapNotificationRegistered(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isMapNotificationRegistered ? 1 : 0);
                        return true;
                    case 166:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDownloadInterrupt = reqMapDownloadInterrupt(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqMapDownloadInterrupt ? 1 : 0);
                        return true;
                    case 167:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapDatabaseAvailable();
                        reply.writeNoException();
                        return true;
                    case 168:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapDeleteDatabaseByAddress(data.readString());
                        reply.writeNoException();
                        return true;
                    case 169:
                        data.enforceInterface(DESCRIPTOR);
                        reqMapCleanDatabase();
                        reply.writeNoException();
                        return true;
                    case 170:
                        data.enforceInterface(DESCRIPTOR);
                        int _result33 = getMapCurrentState(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result33);
                        return true;
                    case TRANSACTION_getMapRegisterState /* 171 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result34 = getMapRegisterState(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result34);
                        return true;
                    case 172:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapSendMessage = reqMapSendMessage(data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqMapSendMessage ? 1 : 0);
                        return true;
                    case TRANSACTION_reqMapDeleteMessage /* 173 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqMapDeleteMessage = reqMapDeleteMessage(data.readString(), data.readInt(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqMapDeleteMessage ? 1 : 0);
                        return true;
                    case TRANSACTION_reqMapChangeReadStatus /* 174 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg07 = data.readString();
                        int _arg14 = data.readInt();
                        String _arg22 = data.readString();
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean reqMapChangeReadStatus = reqMapChangeReadStatus(_arg07, _arg14, _arg22, _arg05);
                        reply.writeNoException();
                        reply.writeInt(reqMapChangeReadStatus ? 1 : 0);
                        return true;
                    case TRANSACTION_setMapDownloadNotify /* 175 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean mapDownloadNotify = setMapDownloadNotify(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(mapDownloadNotify ? 1 : 0);
                        return true;
                    case 176:
                        data.enforceInterface(DESCRIPTOR);
                        setDefaultPinCode(data.readString());
                        reply.writeNoException();
                        return true;
                    case 177:
                        data.enforceInterface(DESCRIPTOR);
                        String _result35 = getDefaultPinCode();
                        reply.writeNoException();
                        reply.writeString(_result35);
                        return true;
                    case 178:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        setBtAutoAcceptPairingRequest(_arg05);
                        reply.writeNoException();
                        return true;
                    case 179:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isBtAutoAcceptPairingRequest = isBtAutoAcceptPairingRequest();
                        reply.writeNoException();
                        reply.writeInt(isBtAutoAcceptPairingRequest ? 1 : 0);
                        return true;
                    case 180:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerOppCallback = registerOppCallback(UiCallbackOpp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerOppCallback ? 1 : 0);
                        return true;
                    case 181:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterOppCallback = unregisterOppCallback(UiCallbackOpp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterOppCallback ? 1 : 0);
                        return true;
                    case 182:
                        data.enforceInterface(DESCRIPTOR);
                        boolean oppFilePath = setOppFilePath(data.readString());
                        reply.writeNoException();
                        reply.writeInt(oppFilePath ? 1 : 0);
                        return true;
                    case 183:
                        data.enforceInterface(DESCRIPTOR);
                        String _result36 = getOppFilePath();
                        reply.writeNoException();
                        reply.writeString(_result36);
                        return true;
                    case 184:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean reqOppAcceptReceiveFile = reqOppAcceptReceiveFile(_arg05);
                        reply.writeNoException();
                        reply.writeInt(reqOppAcceptReceiveFile ? 1 : 0);
                        return true;
                    case 185:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqOppInterruptReceiveFile = reqOppInterruptReceiveFile();
                        reply.writeNoException();
                        reply.writeInt(reqOppInterruptReceiveFile ? 1 : 0);
                        return true;
                    case TRANSACTION_setTargetAddress /* 186 */:
                        data.enforceInterface(DESCRIPTOR);
                        setTargetAddress(data.readString());
                        reply.writeNoException();
                        return true;
                    case 187:
                        data.enforceInterface(DESCRIPTOR);
                        String _result37 = getTargetAddress();
                        reply.writeNoException();
                        reply.writeString(_result37);
                        return true;
                    case TRANSACTION_reqAvrcpUpdateSongStatus /* 188 */:
                        data.enforceInterface(DESCRIPTOR);
                        reqAvrcpUpdateSongStatus();
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_isGattServiceReady /* 189 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isGattServiceReady = isGattServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isGattServiceReady ? 1 : 0);
                        return true;
                    case TRANSACTION_registerGattServerCallback /* 190 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerGattServerCallback = registerGattServerCallback(UiCallbackGattServer.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerGattServerCallback ? 1 : 0);
                        return true;
                    case TRANSACTION_unregisterGattServerCallback /* 191 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterGattServerCallback = unregisterGattServerCallback(UiCallbackGattServer.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterGattServerCallback ? 1 : 0);
                        return true;
                    case 192:
                        data.enforceInterface(DESCRIPTOR);
                        int _result38 = getGattServerConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result38);
                        return true;
                    case 193:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerDisconnect = reqGattServerDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerDisconnect ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerBeginServiceDeclaration /* 194 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg08 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg1 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        boolean reqGattServerBeginServiceDeclaration = reqGattServerBeginServiceDeclaration(_arg08, _arg1);
                        reply.writeNoException();
                        reply.writeInt(reqGattServerBeginServiceDeclaration ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerAddCharacteristic /* 195 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        boolean reqGattServerAddCharacteristic = reqGattServerAddCharacteristic(_arg0, data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerAddCharacteristic ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerAddDescriptor /* 196 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg02 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg02 = null;
                        }
                        boolean reqGattServerAddDescriptor = reqGattServerAddDescriptor(_arg02, data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerAddDescriptor ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerEndServiceDeclaration /* 197 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerEndServiceDeclaration = reqGattServerEndServiceDeclaration();
                        reply.writeNoException();
                        reply.writeInt(reqGattServerEndServiceDeclaration ? 1 : 0);
                        return true;
                    case 198:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg09 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg12 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg12 = null;
                        }
                        boolean reqGattServerRemoveService = reqGattServerRemoveService(_arg09, _arg12);
                        reply.writeNoException();
                        reply.writeInt(reqGattServerRemoveService ? 1 : 0);
                        return true;
                    case 199:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerClearServices = reqGattServerClearServices();
                        reply.writeNoException();
                        reply.writeInt(reqGattServerClearServices ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerListen /* 200 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        boolean reqGattServerListen = reqGattServerListen(_arg05);
                        reply.writeNoException();
                        reply.writeInt(reqGattServerListen ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerSendResponse /* 201 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqGattServerSendResponse = reqGattServerSendResponse(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.createByteArray());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerSendResponse ? 1 : 0);
                        return true;
                    case TRANSACTION_reqGattServerSendNotification /* 202 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg010 = data.readString();
                        int _arg15 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg2 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg2 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg3 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg3 = null;
                        }
                        boolean reqGattServerSendNotification = reqGattServerSendNotification(_arg010, _arg15, _arg2, _arg3, data.readInt() != 0, data.createByteArray());
                        reply.writeNoException();
                        reply.writeInt(reqGattServerSendNotification ? 1 : 0);
                        return true;
                    case TRANSACTION_getGattAddedGattServiceUuidList /* 203 */:
                        data.enforceInterface(DESCRIPTOR);
                        List<ParcelUuid> _result39 = getGattAddedGattServiceUuidList();
                        reply.writeNoException();
                        reply.writeTypedList(_result39);
                        return true;
                    case TRANSACTION_getGattAddedGattCharacteristicUuidList /* 204 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg03 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg03 = null;
                        }
                        List<ParcelUuid> _result40 = getGattAddedGattCharacteristicUuidList(_arg03);
                        reply.writeNoException();
                        reply.writeTypedList(_result40);
                        return true;
                    case TRANSACTION_getGattAddedGattDescriptorUuidList /* 205 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg04 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg04 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg13 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg13 = null;
                        }
                        List<ParcelUuid> _result41 = getGattAddedGattDescriptorUuidList(_arg04, _arg13);
                        reply.writeNoException();
                        reply.writeTypedList(_result41);
                        return true;
                    case TRANSACTION_setBtAutoDownEnable /* 206 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        setBtAutoDownEnable(_arg05, data.readInt());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getBtAutoDownState /* 207 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btAutoDownState = getBtAutoDownState(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(btAutoDownState ? 1 : 0);
                        return true;
                    case TRANSACTION_setBtAutoAnswerEnable /* 208 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        setBtAutoAnswerEnable(_arg05, data.readInt());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getBtAutoAnswerState /* 209 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btAutoAnswerState = getBtAutoAnswerState();
                        reply.writeNoException();
                        reply.writeInt(btAutoAnswerState ? 1 : 0);
                        return true;
                    case TRANSACTION_setBtAnswerTypeEnable /* 210 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        setBtAnswerTypeEnable(_arg05);
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getBtAnswerType /* 211 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btAnswerType = getBtAnswerType();
                        reply.writeNoException();
                        reply.writeInt(btAnswerType ? 1 : 0);
                        return true;
                    case TRANSACTION_setBtDevConnAddr /* 212 */:
                        data.enforceInterface(DESCRIPTOR);
                        setBtDevConnAddr(data.readString());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getBtDevConnAddr /* 213 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _result42 = getBtDevConnAddr();
                        reply.writeNoException();
                        reply.writeString(_result42);
                        return true;
                    case TRANSACTION_setContactsToDB /* 214 */:
                        data.enforceInterface(DESCRIPTOR);
                        setContactsToDB(data.createTypedArrayList(Contacts.CREATOR));
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getContactsListForDB /* 215 */:
                        data.enforceInterface(DESCRIPTOR);
                        List<Contacts> _result43 = getContactsListForDB();
                        reply.writeNoException();
                        reply.writeTypedList(_result43);
                        return true;
                    case TRANSACTION_setCallLogsToDB /* 216 */:
                        data.enforceInterface(DESCRIPTOR);
                        setCallLogsToDB(data.createTypedArrayList(CallLogs.CREATOR));
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_setUiCallLogsToDB /* 217 */:
                        data.enforceInterface(DESCRIPTOR);
                        setUiCallLogsToDB(data.readString(), data.readString(), data.readInt(), data.readString());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getCallLogsListForDB /* 218 */:
                        data.enforceInterface(DESCRIPTOR);
                        List<CallLogs> _result44 = getCallLogsListForDB(data.readInt());
                        reply.writeNoException();
                        reply.writeTypedList(_result44);
                        return true;
                    case TRANSACTION_deleteDatabase /* 219 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean deleteDatabase = deleteDatabase();
                        reply.writeNoException();
                        reply.writeInt(deleteDatabase ? 1 : 0);
                        return true;
                    case TRANSACTION_cleanTable /* 220 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean cleanTable = cleanTable(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(cleanTable ? 1 : 0);
                        return true;
                    case TRANSACTION_getCallName /* 221 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _result45 = getCallName(data.readString());
                        reply.writeNoException();
                        reply.writeString(_result45);
                        return true;
                    case TRANSACTION_getCallPhoto /* 222 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _result46 = getCallPhoto(data.readString());
                        reply.writeNoException();
                        reply.writeString(_result46);
                        return true;
                    case TRANSACTION_setPairingConfirmation /* 223 */:
                        data.enforceInterface(DESCRIPTOR);
                        setPairingConfirmation();
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_cancelPairingUserInput /* 224 */:
                        data.enforceInterface(DESCRIPTOR);
                        cancelPairingUserInput();
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getMissedCallSize /* 225 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result47 = getMissedCallSize();
                        reply.writeNoException();
                        reply.writeInt(_result47);
                        return true;
                    case TRANSACTION_initMissedCallSize /* 226 */:
                        data.enforceInterface(DESCRIPTOR);
                        initMissedCallSize();
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_saveBellPath /* 227 */:
                        data.enforceInterface(DESCRIPTOR);
                        saveBellPath(data.readString());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getBellPath /* 228 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _result48 = getBellPath();
                        reply.writeNoException();
                        reply.writeString(_result48);
                        return true;
                    case TRANSACTION_delCallLogsForDB /* 229 */:
                        data.enforceInterface(DESCRIPTOR);
                        delCallLogsForDB(data.readInt());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_delContactsForDB /* 230 */:
                        data.enforceInterface(DESCRIPTOR);
                        delContactsForDB(data.readInt());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_setCollectionsToDB /* 231 */:
                        data.enforceInterface(DESCRIPTOR);
                        setCollectionsToDB(data.createTypedArrayList(Collection.CREATOR));
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_delCollectionsForDB /* 232 */:
                        data.enforceInterface(DESCRIPTOR);
                        delCollectionsForDB(data.readInt());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getCollectionsForDB /* 233 */:
                        data.enforceInterface(DESCRIPTOR);
                        List<Collection> _result49 = getCollectionsForDB();
                        reply.writeNoException();
                        reply.writeTypedList(_result49);
                        return true;
                    case TRANSACTION_getPlayStatus /* 234 */:
                        data.enforceInterface(DESCRIPTOR);
                        byte _result50 = getPlayStatus();
                        reply.writeNoException();
                        reply.writeByte(_result50);
                        return true;
                    case TRANSACTION_setBtMainDevices /* 235 */:
                        data.enforceInterface(DESCRIPTOR);
                        setBtMainDevices(data.readString());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getBtMainDevices /* 236 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _result51 = getBtMainDevices();
                        reply.writeNoException();
                        reply.writeString(_result51);
                        return true;
                    case TRANSACTION_switchingMainDevices /* 237 */:
                        data.enforceInterface(DESCRIPTOR);
                        switchingMainDevices(data.readString());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getUnreadSMS /* 238 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result52 = getUnreadSMS();
                        reply.writeNoException();
                        reply.writeInt(_result52);
                        return true;
                    case TRANSACTION_initUnreadSMS /* 239 */:
                        data.enforceInterface(DESCRIPTOR);
                        initUnreadSMS();
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_setThreePartyCallEnable /* 240 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        setThreePartyCallEnable(_arg05);
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getThreePartyCallEnable /* 241 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean threePartyCallEnable = getThreePartyCallEnable();
                        reply.writeNoException();
                        reply.writeInt(threePartyCallEnable ? 1 : 0);
                        return true;
                    case TRANSACTION_getBtVersionInfoArr /* 242 */:
                        data.enforceInterface(DESCRIPTOR);
                        String[] _result53 = getBtVersionInfoArr();
                        reply.writeNoException();
                        reply.writeStringArray(_result53);
                        return true;
                    case TRANSACTION_getMaxDownCount /* 243 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result54 = getMaxDownCount(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(_result54);
                        return true;
                    case TRANSACTION_getStartBtMusicType /* 244 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean startBtMusicType = getStartBtMusicType();
                        reply.writeNoException();
                        reply.writeInt(startBtMusicType ? 1 : 0);
                        return true;
                    case TRANSACTION_setDualDeviceEnable /* 245 */:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg05 = true;
                        }
                        setDualDeviceEnable(_arg05);
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getDualDeviceEnable /* 246 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean dualDeviceEnable = getDualDeviceEnable();
                        reply.writeNoException();
                        reply.writeInt(dualDeviceEnable ? 1 : 0);
                        return true;
                    case TRANSACTION_getHfpConnectedAddressList /* 247 */:
                        data.enforceInterface(DESCRIPTOR);
                        List<String> _result55 = getHfpConnectedAddressList();
                        reply.writeNoException();
                        reply.writeStringList(_result55);
                        return true;
                    case TRANSACTION_getDebugMode /* 248 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean debugMode = getDebugMode();
                        reply.writeNoException();
                        reply.writeInt(debugMode ? 1 : 0);
                        return true;
                    case TRANSACTION_reqBtUnpairAllPairedDevices /* 249 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqBtUnpairAllPairedDevices = reqBtUnpairAllPairedDevices();
                        reply.writeNoException();
                        reply.writeInt(reqBtUnpairAllPairedDevices ? 1 : 0);
                        return true;
                    case TRANSACTION_setRejectMapMsg /* 250 */:
                        data.enforceInterface(DESCRIPTOR);
                        setRejectMapMsg(data.readString());
                        reply.writeNoException();
                        return true;
                    case TRANSACTION_getRejectMapMsg /* 251 */:
                        data.enforceInterface(DESCRIPTOR);
                        String _result56 = getRejectMapMsg();
                        reply.writeNoException();
                        reply.writeString(_result56);
                        return true;
                    case TRANSACTION_reqHfpReply /* 252 */:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpReply = reqHfpReply(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpReply ? 1 : 0);
                        return true;
                    case TRANSACTION_getPbapDownLoadState /* 253 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result57 = getPbapDownLoadState();
                        reply.writeNoException();
                        reply.writeInt(_result57);
                        return true;
                    case TRANSACTION_getPlayMode /* 254 */:
                        data.enforceInterface(DESCRIPTOR);
                        int _result58 = getPlayMode();
                        reply.writeNoException();
                        reply.writeInt(_result58);
                        return true;
                    case 255:
                        data.enforceInterface(DESCRIPTOR);
                        boolean playMode = setPlayMode(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(playMode ? 1 : 0);
                        return true;
                    case 256:
                        data.enforceInterface(DESCRIPTOR);
                        onQueryBluetoothConnect(data.readInt());
                        reply.writeNoException();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public static class Proxy implements UiCommand {
            public static UiCommand sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getUiServiceVersionName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUiServiceVersionName();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isAvrcpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcpServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isA2dpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isA2dpServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isSppServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSppServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isBluetoothServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBluetoothServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHidServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHidServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isPbapServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPbapServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isOppServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOppServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isMapServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMapServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerA2dpCallback(UiCallbackA2dp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerA2dpCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterA2dpCallback(UiCallbackA2dp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterA2dpCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getA2dpConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getA2dpConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isA2dpConnected(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isA2dpConnected(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getA2dpConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getA2dpConnectedAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqA2dpConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqA2dpConnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqA2dpDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqA2dpDisconnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void pauseA2dpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pauseA2dpRender();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void startA2dpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startA2dpRender();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setA2dpLocalVolume(float vol) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(vol);
                    boolean _result = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setA2dpLocalVolume(vol);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setA2dpStreamType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _result = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setA2dpStreamType(type);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getA2dpStreamType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getA2dpStreamType();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerAvrcpCallback(UiCallbackAvrcp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerAvrcpCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterAvrcpCallback(UiCallbackAvrcp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterAvrcpCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getAvrcpConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAvrcpConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isAvrcpConnected(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcpConnected(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getAvrcpConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAvrcpConnectedAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpConnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpDisconnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isAvrcp13Supported(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcp13Supported(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isAvrcp14Supported(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcp14Supported(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpPlay() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpPlay();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpStop() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpStop();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpPause();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpForward();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpBackward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpBackward();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpVolumeUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpVolumeUp();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpVolumeDown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpVolumeDown();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpStartFastForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpStartFastForward();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpStopFastForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpStopFastForward();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpStartRewind() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpStartRewind();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpStopRewind() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpStopRewind();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13GetCapabilitiesSupportEvent();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13GetPlayerSettingAttributesList();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(attributeId);
                    boolean _result = false;
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13GetPlayerSettingValuesList(attributeId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13GetPlayerSettingCurrentValues();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(attributeId);
                    _data.writeByte(valueId);
                    boolean _result = false;
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13SetPlayerSettingValue(attributeId, valueId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13GetElementAttributesPlaying();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13GetPlayStatus();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(eventId);
                    _data.writeLong(interval);
                    boolean _result = false;
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpRegisterEventWatcher(eventId, interval);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(eventId);
                    boolean _result = false;
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpUnregisterEventWatcher(eventId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13NextGroup() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13NextGroup();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp13PreviousGroup() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp13PreviousGroup();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcp14BrowsingChannelEstablished();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(playerId);
                    boolean _result = false;
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14SetAddressedPlayer(playerId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(playerId);
                    boolean _result = false;
                    if (!this.mRemote.transact(56, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14SetBrowsedPlayer(playerId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    boolean _result = false;
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14GetFolderItems(scopeId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    _data.writeByte(direction);
                    boolean _result = false;
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14ChangePath(uidCounter, uid, direction);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    boolean _result = false;
                    if (!this.mRemote.transact(59, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14GetItemAttributes(scopeId, uidCounter, uid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    boolean _result = false;
                    if (!this.mRemote.transact(60, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14PlaySelectedItem(scopeId, uidCounter, uid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14Search(String text) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(text);
                    boolean _result = false;
                    if (!this.mRemote.transact(61, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14Search(text);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    boolean _result = false;
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14AddToNowPlaying(scopeId, uidCounter, uid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(volume);
                    boolean _result = false;
                    if (!this.mRemote.transact(63, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcp14SetAbsoluteVolume(volume);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerBtCallback(UiCallbackBluetooth cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(64, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerBtCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterBtCallback(UiCallbackBluetooth cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(65, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterBtCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getNfServiceVersionName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfServiceVersionName();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setBtEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(enable ? 1 : 0);
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBtEnable(enable);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setBtDiscoverableTimeout(int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeout);
                    boolean _result = false;
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBtDiscoverableTimeout(timeout);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean startBtDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startBtDiscovery();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean cancelBtDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelBtDiscovery();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqBtPair(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqBtPair(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqBtUnpair(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(72, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqBtUnpair(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqBtPairedDevices(int opt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(opt);
                    boolean _result = false;
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqBtPairedDevices(opt);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBtLocalName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtLocalName();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBtRemoteDeviceName(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtRemoteDeviceName(address);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBtLocalAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtLocalAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setBtLocalName(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    boolean _result = false;
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setBtLocalName(name);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isBtEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(78, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBtEnabled();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getBtState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(79, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isBtDiscovering() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(80, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBtDiscovering();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isBtDiscoverable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(81, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBtDiscoverable();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isBtAutoConnectEnable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBtAutoConnectEnable();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int reqBtConnectHfpA2dp(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqBtConnectHfpA2dp(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int reqBtDisconnectAll(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqBtDisconnectAll(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getBtRemoteUuids(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtRemoteUuids(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean switchBtRoleMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    boolean _result = false;
                    if (!this.mRemote.transact(86, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchBtRoleMode(mode);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getBtRoleMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(87, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtRoleMode();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtAutoConnect(int condition, int period) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(condition);
                    _data.writeInt(period);
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtAutoConnect(condition, period);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getBtAutoConnectCondition() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAutoConnectCondition();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getBtAutoConnectPeriod() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAutoConnectPeriod();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getBtAutoConnectState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(91, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAutoConnectState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBtAutoConnectingAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(92, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAutoConnectingAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerHfpCallback(UiCallbackHfp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(93, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerHfpCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterHfpCallback(UiCallbackHfp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(94, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterHfpCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getHfpConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(95, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpConnected(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpConnected(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getHfpConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpConnectedAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getHfpAudioConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(98, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpAudioConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(99, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpConnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(100, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpDisconnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getHfpRemoteSignalStrength() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(101, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpRemoteSignalStrength();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<GocHfpClientCall> getHfpCallList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(102, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpCallList();
                    }
                    _reply.readException();
                    List<GocHfpClientCall> _result = _reply.createTypedArrayList(GocHfpClientCall.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(103, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpCallList2();
                    }
                    _reply.readException();
                    List<GocHfpClientCall> _result = _reply.createTypedArrayList(GocHfpClientCall.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpRemoteOnRoaming() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(104, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpRemoteOnRoaming();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getHfpRemoteBatteryIndicator() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(105, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpRemoteBatteryIndicator();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(106, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpRemoteTelecomServiceOn();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(107, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpRemoteVoiceDialOn();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpDialCall(String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    boolean _result = false;
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpDialCall(number);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpReDial() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(109, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpReDial();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpMemoryDial(String index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(index);
                    boolean _result = false;
                    if (!this.mRemote.transact(110, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpMemoryDial(index);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpAnswerCall(String address, int flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(flag);
                    boolean _result = false;
                    if (!this.mRemote.transact(111, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpAnswerCall(address, flag);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpRejectIncomingCall(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(112, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpRejectIncomingCall(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpTerminateCurrentCall(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(113, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpTerminateCurrentCall(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpSendDtmf(String address, String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(number);
                    boolean _result = false;
                    if (!this.mRemote.transact(114, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpSendDtmf(address, number);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpAudioTransferToCarkit(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(115, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpAudioTransferToCarkit(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpAudioTransferToPhone(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(116, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpAudioTransferToPhone(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getHfpRemoteNetworkOperator() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(117, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpRemoteNetworkOperator();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getHfpRemoteSubscriberNumber() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(118, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpRemoteSubscriberNumber();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpVoiceDial(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(enable ? 1 : 0);
                    if (!this.mRemote.transact(119, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpVoiceDial(enable);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void pauseHfpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(120, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pauseHfpRender();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void startHfpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(121, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startHfpRender();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpMicMute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(122, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpMicMute();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void muteHfpMic(boolean mute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mute ? 1 : 0);
                    if (this.mRemote.transact(123, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().muteHfpMic(mute);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHfpInBandRingtoneSupport() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(124, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHfpInBandRingtoneSupport();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerPbapCallback(UiCallbackPbap cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(125, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerPbapCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterPbapCallback(UiCallbackPbap cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(126, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterPbapCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getPbapConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getPbapConnectionState, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPbapConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isPbapDownloading() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(128, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPbapDownloading();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getPbapDownloadingAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(129, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPbapDownloadingAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownload(String address, int storage, int property) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(storage);
                    _data.writeInt(property);
                    boolean _result = false;
                    if (!this.mRemote.transact(130, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqPbapDownload(address, storage, property);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownloadRange(String address, int storage, int property, int startPos, int offset) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(storage);
                        try {
                            _data.writeInt(property);
                            try {
                                _data.writeInt(startPos);
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(offset);
                        try {
                            boolean _result = false;
                            if (this.mRemote.transact(131, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = true;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqPbapDownloadRange = Stub.getDefaultImpl().reqPbapDownloadRange(address, storage, property, startPos, offset);
                            _reply.recycle();
                            _data.recycle();
                            return reqPbapDownloadRange;
                        } catch (Throwable th6) {
                            th = th6;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownloadToDatabase(String address, int storage, int property) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(storage);
                    _data.writeInt(property);
                    boolean _result = false;
                    if (!this.mRemote.transact(132, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqPbapDownloadToDatabase(address, storage, property);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownloadRangeToDatabase(String address, int storage, int property, int startPos, int offset) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(storage);
                        try {
                            _data.writeInt(property);
                            try {
                                _data.writeInt(startPos);
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(offset);
                        try {
                            boolean _result = false;
                            if (this.mRemote.transact(133, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = true;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqPbapDownloadRangeToDatabase = Stub.getDefaultImpl().reqPbapDownloadRangeToDatabase(address, storage, property, startPos, offset);
                            _reply.recycle();
                            _data.recycle();
                            return reqPbapDownloadRangeToDatabase;
                        } catch (Throwable th6) {
                            th = th6;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownloadToContactsProvider(String address, int storage, int property) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(storage);
                    _data.writeInt(property);
                    boolean _result = false;
                    if (!this.mRemote.transact(134, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqPbapDownloadToContactsProvider(address, storage, property);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownloadRangeToContactsProvider(String address, int storage, int property, int startPos, int offset) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(storage);
                        try {
                            _data.writeInt(property);
                            try {
                                _data.writeInt(startPos);
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(offset);
                        try {
                            boolean _result = false;
                            if (this.mRemote.transact(135, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = true;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqPbapDownloadRangeToContactsProvider = Stub.getDefaultImpl().reqPbapDownloadRangeToContactsProvider(address, storage, property, startPos, offset);
                            _reply.recycle();
                            _data.recycle();
                            return reqPbapDownloadRangeToContactsProvider;
                        } catch (Throwable th6) {
                            th = th6;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqPbapDatabaseQueryNameByNumber(String address, String target) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(target);
                    if (this.mRemote.transact(136, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqPbapDatabaseQueryNameByNumber(address, target);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqPbapDatabaseQueryNameByPartialNumber(String address, String target, int findPosition) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(target);
                    _data.writeInt(findPosition);
                    if (this.mRemote.transact(137, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqPbapDatabaseQueryNameByPartialNumber(address, target, findPosition);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqPbapDatabaseAvailable(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(138, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqPbapDatabaseAvailable(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqPbapDeleteDatabaseByAddress(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(139, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqPbapDeleteDatabaseByAddress(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqPbapCleanDatabase() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(140, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqPbapCleanDatabase();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqPbapDownloadInterrupt(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(141, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqPbapDownloadInterrupt(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setPbapDownloadNotify(int frequency) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(frequency);
                    boolean _result = false;
                    if (!this.mRemote.transact(142, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPbapDownloadNotify(frequency);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerSppCallback(UiCallbackSpp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_registerSppCallback, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerSppCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterSppCallback(UiCallbackSpp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_unregisterSppCallback, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterSppCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqSppConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(145, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqSppConnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqSppDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(146, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqSppDisconnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqSppConnectedDeviceAddressList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(147, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqSppConnectedDeviceAddressList();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isSppConnected(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(148, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSppConnected(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqSppSendData(String address, byte[] sppData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeByteArray(sppData);
                    if (this.mRemote.transact(149, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqSppSendData(address, sppData);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerHidCallback(UiCallbackHid cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(150, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerHidCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterHidCallback(UiCallbackHid cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(151, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterHidCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getHidConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(152, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHidConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isHidConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(153, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHidConnected();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getHidConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(154, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHidConnectedAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHidConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(155, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHidConnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHidDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(156, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHidDisconnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqSendHidMouseCommand(int button, int offset_x, int offset_y, int wheel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(button);
                    _data.writeInt(offset_x);
                    _data.writeInt(offset_y);
                    _data.writeInt(wheel);
                    boolean _result = false;
                    if (!this.mRemote.transact(157, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqSendHidMouseCommand(button, offset_x, offset_y, wheel);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqSendHidVirtualKeyCommand(int key_1, int key_2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key_1);
                    _data.writeInt(key_2);
                    boolean _result = false;
                    if (!this.mRemote.transact(158, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqSendHidVirtualKeyCommand(key_1, key_2);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerMapCallback(UiCallbackMap cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(159, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerMapCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterMapCallback(UiCallbackMap cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(160, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterMapCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeString(handle);
                    _data.writeInt(storage);
                    boolean _result = false;
                    if (!this.mRemote.transact(161, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDownloadSingleMessage(address, folder, handle, storage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    boolean _result = false;
                    _data.writeInt(isContentDownload ? 1 : 0);
                    _data.writeInt(count);
                    _data.writeInt(startPos);
                    _data.writeInt(storage);
                    _data.writeString(periodBegin);
                    _data.writeString(periodEnd);
                    _data.writeString(sender);
                    _data.writeString(recipient);
                    _data.writeInt(readStatus);
                    _data.writeInt(typeFilter);
                    if (!this.mRemote.transact(162, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDownloadMessage(address, folder, isContentDownload, count, startPos, storage, periodBegin, periodEnd, sender, recipient, readStatus, typeFilter);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = true;
                    _data.writeInt(downloadNewMessage ? 1 : 0);
                    if (!this.mRemote.transact(163, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapRegisterNotification(address, downloadNewMessage);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqMapUnregisterNotification(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(164, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapUnregisterNotification(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isMapNotificationRegistered(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(165, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMapNotificationRegistered(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapDownloadInterrupt(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(166, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDownloadInterrupt(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqMapDatabaseAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(167, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapDatabaseAvailable();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqMapDeleteDatabaseByAddress(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(168, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapDeleteDatabaseByAddress(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqMapCleanDatabase() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(169, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqMapCleanDatabase();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getMapCurrentState(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(170, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMapCurrentState(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getMapRegisterState(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getMapRegisterState, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMapRegisterState(address);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapSendMessage(String address, String message, String target) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(message);
                    _data.writeString(target);
                    boolean _result = false;
                    if (!this.mRemote.transact(172, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapSendMessage(address, message, target);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeString(handle);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqMapDeleteMessage, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapDeleteMessage(address, folder, handle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(folder);
                    _data.writeString(handle);
                    boolean _result = true;
                    _data.writeInt(isReadStatus ? 1 : 0);
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqMapChangeReadStatus, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqMapChangeReadStatus(address, folder, handle, isReadStatus);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setMapDownloadNotify(int frequency) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(frequency);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_setMapDownloadNotify, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setMapDownloadNotify(frequency);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setDefaultPinCode(String pinCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pinCode);
                    if (this.mRemote.transact(176, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDefaultPinCode(pinCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getDefaultPinCode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(177, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultPinCode();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtAutoAcceptPairingRequest(boolean auto) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(auto ? 1 : 0);
                    if (this.mRemote.transact(178, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtAutoAcceptPairingRequest(auto);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isBtAutoAcceptPairingRequest() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(179, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBtAutoAcceptPairingRequest();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerOppCallback(UiCallbackOpp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(180, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerOppCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterOppCallback(UiCallbackOpp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(181, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterOppCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setOppFilePath(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    boolean _result = false;
                    if (!this.mRemote.transact(182, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setOppFilePath(path);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getOppFilePath() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(183, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOppFilePath();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqOppAcceptReceiveFile(boolean accept) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(accept ? 1 : 0);
                    if (!this.mRemote.transact(184, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqOppAcceptReceiveFile(accept);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqOppInterruptReceiveFile() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(185, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqOppInterruptReceiveFile();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setTargetAddress(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(Stub.TRANSACTION_setTargetAddress, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTargetAddress(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getTargetAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(187, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTargetAddress();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void reqAvrcpUpdateSongStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(Stub.TRANSACTION_reqAvrcpUpdateSongStatus, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reqAvrcpUpdateSongStatus();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean isGattServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_isGattServiceReady, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isGattServiceReady();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean registerGattServerCallback(UiCallbackGattServer cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_registerGattServerCallback, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().registerGattServerCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean unregisterGattServerCallback(UiCallbackGattServer cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_unregisterGattServerCallback, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unregisterGattServerCallback(cb);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getGattServerConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(192, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattServerConnectionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(193, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerDisconnect(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerBeginServiceDeclaration(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(srvcType);
                    boolean _result = true;
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqGattServerBeginServiceDeclaration, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerBeginServiceDeclaration(srvcType, srvcUuid);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerAddCharacteristic(ParcelUuid charUuid, int properties, int permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (charUuid != null) {
                        _data.writeInt(1);
                        charUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(properties);
                    _data.writeInt(permissions);
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqGattServerAddCharacteristic, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerAddCharacteristic(charUuid, properties, permissions);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerAddDescriptor(ParcelUuid descUuid, int permissions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (descUuid != null) {
                        _data.writeInt(1);
                        descUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(permissions);
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqGattServerAddDescriptor, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerAddDescriptor(descUuid, permissions);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqGattServerEndServiceDeclaration, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerEndServiceDeclaration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerRemoveService(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(srvcType);
                    boolean _result = true;
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(198, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerRemoveService(srvcType, srvcUuid);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerClearServices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(199, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerClearServices();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerListen(boolean listen) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(listen ? 1 : 0);
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqGattServerListen, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqGattServerListen(listen);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerSendResponse(String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requestId);
                        try {
                            _data.writeInt(status);
                            try {
                                _data.writeInt(offset);
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeByteArray(value);
                        try {
                            boolean _result = false;
                            if (this.mRemote.transact(Stub.TRANSACTION_reqGattServerSendResponse, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() != 0) {
                                    _result = true;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqGattServerSendResponse = Stub.getDefaultImpl().reqGattServerSendResponse(address, requestId, status, offset, value);
                            _reply.recycle();
                            _data.recycle();
                            return reqGattServerSendResponse;
                        } catch (Throwable th6) {
                            th = th6;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqGattServerSendNotification(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm, byte[] value) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(srvcType);
                        boolean _result = true;
                        if (srvcUuid != null) {
                            _data.writeInt(1);
                            srvcUuid.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (charUuid != null) {
                            _data.writeInt(1);
                            charUuid.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(confirm ? 1 : 0);
                        try {
                            _data.writeByteArray(value);
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(Stub.TRANSACTION_reqGattServerSendNotification, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean reqGattServerSendNotification = Stub.getDefaultImpl().reqGattServerSendNotification(address, srvcType, srvcUuid, charUuid, confirm, value);
                            _reply.recycle();
                            _data.recycle();
                            return reqGattServerSendNotification;
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getGattAddedGattServiceUuidList, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattAddedGattServiceUuidList();
                    }
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(Stub.TRANSACTION_getGattAddedGattCharacteristicUuidList, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattAddedGattCharacteristicUuidList(srvcUuid);
                    }
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (charUuid != null) {
                        _data.writeInt(1);
                        charUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(Stub.TRANSACTION_getGattAddedGattDescriptorUuidList, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGattAddedGattDescriptorUuidList(srvcUuid, charUuid);
                    }
                    _reply.readException();
                    List<ParcelUuid> _result = _reply.createTypedArrayList(ParcelUuid.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtAutoDownEnable(boolean isBtAutoDownEnable, int opt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isBtAutoDownEnable ? 1 : 0);
                    _data.writeInt(opt);
                    if (this.mRemote.transact(Stub.TRANSACTION_setBtAutoDownEnable, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtAutoDownEnable(isBtAutoDownEnable, opt);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getBtAutoDownState(int opt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(opt);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBtAutoDownState, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAutoDownState(opt);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isBtAutoAnswerEnable ? 1 : 0);
                    _data.writeInt(time);
                    if (this.mRemote.transact(Stub.TRANSACTION_setBtAutoAnswerEnable, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtAutoAnswerEnable(isBtAutoAnswerEnable, time);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getBtAutoAnswerState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBtAutoAnswerState, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAutoAnswerState();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isBtAnswerTypeEnable ? 1 : 0);
                    if (this.mRemote.transact(Stub.TRANSACTION_setBtAnswerTypeEnable, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtAnswerTypeEnable(isBtAnswerTypeEnable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getBtAnswerType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBtAnswerType, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtAnswerType();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtDevConnAddr(String btDevConnAddr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(btDevConnAddr);
                    if (this.mRemote.transact(Stub.TRANSACTION_setBtDevConnAddr, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtDevConnAddr(btDevConnAddr);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBtDevConnAddr() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBtDevConnAddr, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtDevConnAddr();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setContactsToDB(List<Contacts> contactsList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(contactsList);
                    if (this.mRemote.transact(Stub.TRANSACTION_setContactsToDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setContactsToDB(contactsList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<Contacts> getContactsListForDB() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getContactsListForDB, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getContactsListForDB();
                    }
                    _reply.readException();
                    List<Contacts> _result = _reply.createTypedArrayList(Contacts.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setCallLogsToDB(List<CallLogs> callLogsList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(callLogsList);
                    if (this.mRemote.transact(Stub.TRANSACTION_setCallLogsToDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCallLogsToDB(callLogsList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setUiCallLogsToDB(String name, String number, int type, String time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(number);
                    _data.writeInt(type);
                    _data.writeString(time);
                    if (this.mRemote.transact(Stub.TRANSACTION_setUiCallLogsToDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUiCallLogsToDB(name, number, type, time);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<CallLogs> getCallLogsListForDB(int opt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(opt);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getCallLogsListForDB, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallLogsListForDB(opt);
                    }
                    _reply.readException();
                    List<CallLogs> _result = _reply.createTypedArrayList(CallLogs.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean deleteDatabase() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_deleteDatabase, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteDatabase();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean cleanTable(int options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(options);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_cleanTable, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cleanTable(options);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getCallName(String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getCallName, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallName(number);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getCallPhoto(String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getCallPhoto, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallPhoto(number);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setPairingConfirmation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(Stub.TRANSACTION_setPairingConfirmation, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPairingConfirmation();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void cancelPairingUserInput() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(Stub.TRANSACTION_cancelPairingUserInput, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelPairingUserInput();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getMissedCallSize() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getMissedCallSize, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMissedCallSize();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void initMissedCallSize() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(Stub.TRANSACTION_initMissedCallSize, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().initMissedCallSize();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void saveBellPath(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    if (this.mRemote.transact(Stub.TRANSACTION_saveBellPath, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().saveBellPath(path);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBellPath() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBellPath, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBellPath();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void delCallLogsForDB(int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (this.mRemote.transact(Stub.TRANSACTION_delCallLogsForDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().delCallLogsForDB(id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void delContactsForDB(int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (this.mRemote.transact(Stub.TRANSACTION_delContactsForDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().delContactsForDB(id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setCollectionsToDB(List<Collection> collections) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(collections);
                    if (this.mRemote.transact(Stub.TRANSACTION_setCollectionsToDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCollectionsToDB(collections);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void delCollectionsForDB(int id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (this.mRemote.transact(Stub.TRANSACTION_delCollectionsForDB, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().delCollectionsForDB(id);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<Collection> getCollectionsForDB() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getCollectionsForDB, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCollectionsForDB();
                    }
                    _reply.readException();
                    List<Collection> _result = _reply.createTypedArrayList(Collection.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public byte getPlayStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getPlayStatus, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayStatus();
                    }
                    _reply.readException();
                    byte _result = _reply.readByte();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setBtMainDevices(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(Stub.TRANSACTION_setBtMainDevices, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBtMainDevices(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getBtMainDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBtMainDevices, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtMainDevices();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void switchingMainDevices(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(Stub.TRANSACTION_switchingMainDevices, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().switchingMainDevices(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getUnreadSMS() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getUnreadSMS, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUnreadSMS();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void initUnreadSMS() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(Stub.TRANSACTION_initUnreadSMS, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().initUnreadSMS();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setThreePartyCallEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    if (this.mRemote.transact(Stub.TRANSACTION_setThreePartyCallEnable, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setThreePartyCallEnable(enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getThreePartyCallEnable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getThreePartyCallEnable, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getThreePartyCallEnable();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String[] getBtVersionInfoArr() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getBtVersionInfoArr, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBtVersionInfoArr();
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getMaxDownCount(int opt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(opt);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getMaxDownCount, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaxDownCount(opt);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getStartBtMusicType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getStartBtMusicType, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStartBtMusicType();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setDualDeviceEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    if (this.mRemote.transact(Stub.TRANSACTION_setDualDeviceEnable, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDualDeviceEnable(enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getDualDeviceEnable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getDualDeviceEnable, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDualDeviceEnable();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public List<String> getHfpConnectedAddressList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getHfpConnectedAddressList, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getHfpConnectedAddressList();
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean getDebugMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_getDebugMode, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDebugMode();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqBtUnpairAllPairedDevices() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqBtUnpairAllPairedDevices, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqBtUnpairAllPairedDevices();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void setRejectMapMsg(String msg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(msg);
                    if (this.mRemote.transact(Stub.TRANSACTION_setRejectMapMsg, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRejectMapMsg(msg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public String getRejectMapMsg() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getRejectMapMsg, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRejectMapMsg();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean reqHfpReply(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(Stub.TRANSACTION_reqHfpReply, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpReply(address);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getPbapDownLoadState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getPbapDownLoadState, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPbapDownLoadState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public int getPlayMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(Stub.TRANSACTION_getPlayMode, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPlayMode();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public boolean setPlayMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    boolean _result = false;
                    if (!this.mRemote.transact(255, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPlayMode(mode);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCommand
            public void onQueryBluetoothConnect(int wh) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(wh);
                    if (this.mRemote.transact(256, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onQueryBluetoothConnect(wh);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(UiCommand impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static UiCommand getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
