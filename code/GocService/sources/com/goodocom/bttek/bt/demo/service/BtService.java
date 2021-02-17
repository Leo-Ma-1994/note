package com.goodocom.bttek.bt.demo.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.aidl.GocCallbackA2dp;
import com.goodocom.bttek.bt.aidl.GocCallbackAvrcp;
import com.goodocom.bttek.bt.aidl.GocCallbackBluetooth;
import com.goodocom.bttek.bt.aidl.GocCallbackGattServer;
import com.goodocom.bttek.bt.aidl.GocCallbackHfp;
import com.goodocom.bttek.bt.aidl.GocCallbackHid;
import com.goodocom.bttek.bt.aidl.GocCallbackMap;
import com.goodocom.bttek.bt.aidl.GocCallbackOpp;
import com.goodocom.bttek.bt.aidl.GocCallbackPbap;
import com.goodocom.bttek.bt.aidl.GocCallbackSpp;
import com.goodocom.bttek.bt.aidl.GocCommandA2dp;
import com.goodocom.bttek.bt.aidl.GocCommandAvrcp;
import com.goodocom.bttek.bt.aidl.GocCommandBluetooth;
import com.goodocom.bttek.bt.aidl.GocCommandGattServer;
import com.goodocom.bttek.bt.aidl.GocCommandHfp;
import com.goodocom.bttek.bt.aidl.GocCommandHid;
import com.goodocom.bttek.bt.aidl.GocCommandMap;
import com.goodocom.bttek.bt.aidl.GocCommandOpp;
import com.goodocom.bttek.bt.aidl.GocCommandPbap;
import com.goodocom.bttek.bt.aidl.GocCommandSpp;
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
import com.goodocom.bttek.bt.bean.CallLogs;
import com.goodocom.bttek.bt.bean.Collection;
import com.goodocom.bttek.bt.bean.Contacts;
import com.goodocom.bttek.bt.callback.DoCallbackA2dp;
import com.goodocom.bttek.bt.callback.DoCallbackAvrcp;
import com.goodocom.bttek.bt.callback.DoCallbackBluetooth;
import com.goodocom.bttek.bt.callback.DoCallbackGattServer;
import com.goodocom.bttek.bt.callback.DoCallbackHfp;
import com.goodocom.bttek.bt.callback.DoCallbackHid;
import com.goodocom.bttek.bt.callback.DoCallbackMap;
import com.goodocom.bttek.bt.callback.DoCallbackOpp;
import com.goodocom.bttek.bt.callback.DoCallbackPbap;
import com.goodocom.bttek.bt.callback.DoCallbackSpp;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.gocsdkserver.CommandPbapImp;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BtService extends Service {
    private static final String TAG = "GoodocomAPI_BtService";
    String album = BuildConfig.FLAVOR;
    String artist = BuildConfig.FLAVOR;
    private UiCommand.Stub mBinder = new UiCommand.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass2 */

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcpServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isAvrcpServiceReady()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.isAvrcpServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isA2dpServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isA2dpServiceReady()");
            if (BtService.this.mCommandA2dp == null) {
                return false;
            }
            return BtService.this.mCommandA2dp.isA2dpServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isSppServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isSppServiceReady()");
            if (BtService.this.mCommandSpp == null) {
                return false;
            }
            return BtService.this.mCommandSpp.isSppServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBluetoothServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isBluetoothServiceReady()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.isBluetoothServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isHfpServiceReady()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHidServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isHidServiceReady()");
            if (BtService.this.mCommandHid == null) {
                return false;
            }
            return BtService.this.mCommandHid.isHidServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isPbapServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isPbapServiceReady()");
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.isPbapServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isOppServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isOppServiceReady()");
            if (BtService.this.mCommandOpp == null) {
                return false;
            }
            return BtService.this.mCommandOpp.isOppServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isMapServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isMapServiceReady()");
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.isMapServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getUiServiceVersionName() throws RemoteException {
            Log.d(BtService.TAG, "getUiServiceVersionName ");
            if (BtService.this.mCommandBluetooth == null) {
                return BuildConfig.FLAVOR;
            }
            return BtService.this.mCommandBluetooth.getUiServiceVersionName();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerA2dpCallback(UiCallbackA2dp cb) throws RemoteException {
            Log.d(BtService.TAG, "registerA2dpCallback()");
            return BtService.this.mDoCallbackA2dp.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterA2dpCallback(UiCallbackA2dp cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterA2dpCallback()");
            return BtService.this.mDoCallbackA2dp.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getA2dpConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getA2dpConnectionState()");
            if (BtService.this.mCommandA2dp == null) {
                return 100;
            }
            return BtService.this.mCommandA2dp.getA2dpConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isA2dpConnected(String address) throws RemoteException {
            if (BtService.this.mCommandA2dp == null || BtService.this.mCommandBluetooth == null) {
                return false;
            }
            if (address == null) {
                List<String> addrList = BtService.this.mCommandBluetooth.getProfileConnectedAddrByProile("A2DP");
                for (int i = 0; i < addrList.size(); i++) {
                    if (BtService.this.mCommandBluetooth.isProfileConnectedByAddr(addrList.get(i), "A2DP")) {
                        return true;
                    }
                }
                return false;
            }
            boolean isA2dpConnected = BtService.this.mCommandBluetooth.isProfileConnectedByAddr(address, "A2DP");
            Log.d(BtService.TAG, "isA2dpConnected():" + address + " isA2dpConnected:" + isA2dpConnected);
            return isA2dpConnected;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getA2dpConnectedAddress() throws RemoteException {
            String address = NfDef.DEFAULT_ADDRESS;
            if (BtService.this.mCommandA2dp != null) {
                address = BtService.this.mCommandA2dp.getA2dpConnectedAddress();
            }
            Log.d(BtService.TAG, "getA2dpConnectedAddress():" + address);
            return address;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqA2dpConnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqA2dpConnect() " + address);
            if (BtService.this.mCommandA2dp == null) {
                return false;
            }
            return BtService.this.mCommandA2dp.reqA2dpConnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqA2dpDisconnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqA2dpDisconnect() " + address);
            if (BtService.this.mCommandA2dp == null) {
                return false;
            }
            return BtService.this.mCommandA2dp.reqA2dpDisconnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void pauseA2dpRender() throws RemoteException {
            Log.d(BtService.TAG, "pauseA2dpRender()");
            if (BtService.this.mCommandA2dp != null) {
                BtService.this.mCommandA2dp.pauseA2dpRender();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void startA2dpRender() throws RemoteException {
            Log.d(BtService.TAG, "startA2dpRender()");
            if (BtService.this.mCommandA2dp != null) {
                BtService.this.mCommandA2dp.startA2dpRender();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setA2dpLocalVolume(float vol) throws RemoteException {
            Log.d(BtService.TAG, "setA2dpLocalVolume() " + vol);
            if (BtService.this.mCommandA2dp == null) {
                return false;
            }
            return BtService.this.mCommandA2dp.setA2dpLocalVolume(vol);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setA2dpStreamType(int type) throws RemoteException {
            Log.d(BtService.TAG, "setA2dpStreamType() " + type);
            if (BtService.this.mCommandA2dp == null) {
                return false;
            }
            return BtService.this.mCommandA2dp.setA2dpStreamType(type);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getA2dpStreamType() throws RemoteException {
            Log.d(BtService.TAG, "getA2dpStreamType()");
            if (BtService.this.mCommandA2dp == null) {
                return -1;
            }
            return BtService.this.mCommandA2dp.getA2dpStreamType();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerAvrcpCallback(UiCallbackAvrcp cb) throws RemoteException {
            Log.d(BtService.TAG, "registerAvrcpCallback()");
            return BtService.this.mDoCallbackAvrcp.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterAvrcpCallback(UiCallbackAvrcp cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterAvrcpCallback()");
            return BtService.this.mDoCallbackAvrcp.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getAvrcpConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getAvrcpConnectionState()");
            if (BtService.this.mCommandAvrcp == null) {
                return 100;
            }
            return BtService.this.mCommandAvrcp.getAvrcpConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcpConnected(String address) throws RemoteException {
            if (BtService.this.mCommandAvrcp == null || BtService.this.mCommandBluetooth == null) {
                return false;
            }
            if (address != null) {
                return BtService.this.mCommandBluetooth.isProfileConnectedByAddr(address, "AVRCP");
            }
            List<String> addrList = BtService.this.mCommandBluetooth.getProfileConnectedAddrByProile("AVRCP");
            for (int i = 0; i < addrList.size(); i++) {
                if (BtService.this.mCommandBluetooth.isProfileConnectedByAddr(addrList.get(i), "AVRCP")) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getAvrcpConnectedAddress() throws RemoteException {
            Log.d(BtService.TAG, "getAvrcpConnectedAddress()");
            if (BtService.this.mCommandAvrcp == null) {
                return NfDef.DEFAULT_ADDRESS;
            }
            return BtService.this.mCommandAvrcp.getAvrcpConnectedAddress();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpConnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpConnect() " + address);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpConnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpDisconnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpDisconnect() " + address);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpDisconnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcp13Supported(String address) throws RemoteException {
            Log.d(BtService.TAG, "isAvrcp13Supported() " + address);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.isAvrcp13Supported(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcp14Supported(String address) throws RemoteException {
            Log.d(BtService.TAG, "isAvrcp14Supported() " + address);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.isAvrcp14Supported(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpPlay() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpPlay()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpPlay();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStop() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpStop()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpStop();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpPause() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpPause()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpPause();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpForward() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpForward()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpForward();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpBackward() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpBackward()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpBackward();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpVolumeUp() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpVolumeUp()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpVolumeUp();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpVolumeDown() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpVolumeDown()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpVolumeDown();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStartFastForward() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpStartFastForward()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpStartFastForward();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStopFastForward() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpStopFastForward()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpStopFastForward();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStartRewind() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpStartRewind()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpStartRewind();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpStopRewind() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpStopRewind()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpStopRewind();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13GetCapabilitiesSupportEvent()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13GetCapabilitiesSupportEvent();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13GetPlayerSettingAttributesList()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13GetPlayerSettingAttributesList();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13GetPlayerSettingValuesList() attributeId: " + ((int) attributeId));
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13GetPlayerSettingValuesList(attributeId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13GetPlayerSettingCurrentValues()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13GetPlayerSettingCurrentValues();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13SetPlayerSettingValue() attributeId: " + ((int) attributeId) + " valueId: " + ((int) valueId));
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13SetPlayerSettingValue(attributeId, valueId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13GetElementAttributesPlaying()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13GetElementAttributesPlaying();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13GetPlayStatus()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13GetPlayStatus();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpRegisterEventWatcher() eventId: " + ((int) eventId) + " interval: " + interval);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpRegisterEventWatcher(eventId, interval);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpUnregisterEventWatcher() eventId: " + ((int) eventId));
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcpUnregisterEventWatcher(eventId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13NextGroup() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13NextGroup()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13NextGroup();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp13PreviousGroup() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp13PreviousGroup()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp13PreviousGroup();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
            Log.d(BtService.TAG, "isAvrcp14BrowsingChannelEstablished()");
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.isAvrcp14BrowsingChannelEstablished();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14SetAddressedPlayer() playerId: " + playerId);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14SetAddressedPlayer(playerId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14SetBrowsedPlayer() playerId: " + playerId);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14SetBrowsedPlayer(playerId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14GetFolderItems() scopeId: " + ((int) scopeId));
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14GetFolderItems(scopeId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14ChangePath() uidCounter: " + uidCounter + " uid: " + uid + " direction: " + ((int) direction));
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14ChangePath(uidCounter, uid, direction);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14GetItemAttributes() scopeId: " + ((int) scopeId) + " uidCounter: " + uidCounter);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14GetItemAttributes(scopeId, uidCounter, uid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14PlaySelectedItem() scopeId: " + ((int) scopeId) + " uidCounter: " + uidCounter + " uid: " + uid);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14PlaySelectedItem(scopeId, uidCounter, uid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14Search(String text) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14Search() text: " + text);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14Search(text);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14AddToNowPlaying() scopeId: " + ((int) scopeId) + " uidCounter: " + uidCounter + " uid: " + uid);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14AddToNowPlaying(scopeId, uidCounter, uid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcp14SetAbsoluteVolume() volume: " + ((int) volume));
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.reqAvrcp14SetAbsoluteVolume(volume);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setPlayMode(int mode) throws RemoteException {
            Log.d(BtService.TAG, "setPlayMode() volume: " + mode);
            if (BtService.this.mCommandAvrcp == null) {
                return false;
            }
            return BtService.this.mCommandAvrcp.setPlayMode(mode);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void onQueryBluetoothConnect(int wh) throws RemoteException {
            BtService.this.mCommandBluetooth.onQueryBluetoothConnect(wh);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getPlayMode() throws RemoteException {
            Log.d(BtService.TAG, "getPlayMode()");
            if (BtService.this.mCommandAvrcp == null) {
                return 0;
            }
            return BtService.this.mCommandAvrcp.getPlayMode();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerBtCallback(UiCallbackBluetooth cb) throws RemoteException {
            Log.d(BtService.TAG, "registerBtCallback()");
            if (BtService.this.mDoCallbackBluetooth == null) {
                return false;
            }
            return BtService.this.mDoCallbackBluetooth.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterBtCallback(UiCallbackBluetooth cb) throws RemoteException {
            Log.d(BtService.TAG, "registerBtCallback()");
            if (BtService.this.mDoCallbackBluetooth == null) {
                return false;
            }
            return BtService.this.mDoCallbackBluetooth.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getNfServiceVersionName() throws RemoteException {
            Log.e(BtService.TAG, "getNfServiceVersionName()");
            if (BtService.this.mCommandBluetooth == null) {
                return BuildConfig.FLAVOR;
            }
            return BtService.this.mCommandBluetooth.getNfServiceVersionName();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setBtEnable(boolean enable) throws RemoteException {
            Log.d(BtService.TAG, "setBtEnable() enable: " + enable + "      " + BtService.this.mCommandBluetooth);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.setBtEnable(enable);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setBtDiscoverableTimeout(int timeout) throws RemoteException {
            Log.d(BtService.TAG, "setBtDiscoverableTimeout() timeout: " + timeout);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.setBtDiscoverableTimeout(timeout);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean startBtDiscovery() throws RemoteException {
            Log.d(BtService.TAG, "startBtDiscovery()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.startBtDiscovery();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean cancelBtDiscovery() throws RemoteException {
            Log.d(BtService.TAG, "cancelBtDiscovery()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.cancelBtDiscovery();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtPair(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqBtPair() " + address);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.reqBtPair(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtUnpair(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqBtUnpair() " + address);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.reqBtUnpair(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtPairedDevices(int opt) throws RemoteException {
            Log.d(BtService.TAG, "reqBtPairedDevices():" + opt);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.reqBtPairedDevices(opt);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtLocalName() throws RemoteException {
            Log.d(BtService.TAG, "getBtLocalName()");
            if (BtService.this.mCommandBluetooth == null) {
                return null;
            }
            return BtService.this.mCommandBluetooth.getBtLocalName();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtRemoteDeviceName(String address) throws RemoteException {
            Log.d(BtService.TAG, "getBtRemoteDeviceName() " + address);
            if (BtService.this.mCommandBluetooth != null) {
                return BtService.this.mCommandBluetooth.getBtRemoteDeviceName(address);
            }
            Log.d(BtService.TAG, "getBtRemoteDeviceName()  mCommandBluetooth is null");
            return BuildConfig.FLAVOR;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtLocalAddress() throws RemoteException {
            Log.d(BtService.TAG, "getBtLocalAddress()");
            if (BtService.this.mCommandBluetooth == null) {
                return NfDef.DEFAULT_ADDRESS;
            }
            return BtService.this.mCommandBluetooth.getBtLocalAddress();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setBtLocalName(String name) throws RemoteException {
            Log.d(BtService.TAG, "setBtLocalName() name: " + name);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.setBtLocalName(name);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtEnabled() throws RemoteException {
            Log.d(BtService.TAG, "isBtEnabled()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.isBtEnabled();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtState() throws RemoteException {
            Log.d(BtService.TAG, "getBtState()");
            if (BtService.this.mCommandBluetooth == null) {
                return 100;
            }
            return BtService.this.mCommandBluetooth.getBtState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtDiscovering() throws RemoteException {
            Log.d(BtService.TAG, "isBtDiscovering()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.isBtDiscovering();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtDiscoverable() throws RemoteException {
            Log.d(BtService.TAG, "isBtDiscoverable()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.isBtDiscoverable();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isBtAutoConnectEnable() throws RemoteException {
            Log.d(BtService.TAG, "isBtAutoConnectEnable()");
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.isBtAutoConnectEnable();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int reqBtConnectHfpA2dp(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqBtConnectHfpA2dp() " + address);
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.reqBtConnectHfpA2dp(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int reqBtDisconnectAll(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqBtDisconnectAll()");
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.reqBtDisconnectAll(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtRemoteUuids(String address) throws RemoteException {
            Log.d(BtService.TAG, "getBtRemoteUuids() " + address);
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.getBtRemoteUuids(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean switchBtRoleMode(int mode) throws RemoteException {
            Log.d(BtService.TAG, "switchBtRoleMode() " + mode);
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandBluetooth.switchBtRoleMode(mode);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtRoleMode() throws RemoteException {
            Log.d(BtService.TAG, "getBtRoleMode()");
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.getBtRoleMode();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoConnect(int condition, int period) throws RemoteException {
            Log.d(BtService.TAG, "setBtAutoConnect() condition: " + condition + " period: " + period);
            if (BtService.this.mCommandBluetooth != null) {
                BtService.this.mCommandBluetooth.setBtAutoConnect(condition, period);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtAutoConnectCondition() throws RemoteException {
            Log.d(BtService.TAG, "getBtAutoConnectCondition()");
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.getBtAutoConnectCondition();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtAutoConnectPeriod() throws RemoteException {
            Log.d(BtService.TAG, "getBtAutoConnectPeriod()");
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.getBtAutoConnectPeriod();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getBtAutoConnectState() throws RemoteException {
            Log.d(BtService.TAG, "getBtAutoConnectState()");
            if (BtService.this.mCommandBluetooth == null) {
                return -1;
            }
            return BtService.this.mCommandBluetooth.getBtAutoConnectState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtAutoConnectingAddress() throws RemoteException {
            Log.d(BtService.TAG, "getBtAutoConnectingAddress()");
            if (BtService.this.mCommandBluetooth == null) {
                return NfDef.DEFAULT_ADDRESS;
            }
            return BtService.this.mCommandBluetooth.getBtAutoConnectingAddress();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerHfpCallback(UiCallbackHfp cb) throws RemoteException {
            Log.d(BtService.TAG, "registerHfpCallback()");
            return BtService.this.mDoCallbackHfp.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterHfpCallback(UiCallbackHfp cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterHfpCallback()");
            return BtService.this.mDoCallbackHfp.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getHfpConnectionState()");
            if (BtService.this.mCommandHfp == null) {
                return 100;
            }
            return BtService.this.mCommandHfp.getHfpConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpConnected(String address) throws RemoteException {
            Log.d(BtService.TAG, "isHfpConnected():" + address + "  mCommandHfp " + BtService.this.mCommandHfp + "  mCommandBluetooth " + BtService.this.mCommandBluetooth);
            if (BtService.this.mCommandHfp == null || BtService.this.mCommandBluetooth == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpConnected(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHfpConnectedAddress() throws RemoteException {
            Log.d(BtService.TAG, "getHfpConnectedAddress()");
            if (BtService.this.mCommandHfp == null) {
                return NfDef.DEFAULT_ADDRESS;
            }
            return BtService.this.mCommandHfp.getHfpConnectedAddress();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpAudioConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getHfpAudioConnectionState()");
            if (BtService.this.mCommandHfp == null) {
                return 100;
            }
            return BtService.this.mCommandHfp.getHfpAudioConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpConnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpConnect() " + address);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpConnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpDisconnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpDisconnect() " + address);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpDisconnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpRemoteSignalStrength() throws RemoteException {
            Log.d(BtService.TAG, "getHfpRemoteSignalStrength()");
            if (BtService.this.mCommandHfp == null) {
                return 0;
            }
            return BtService.this.mCommandHfp.getHfpRemoteSignalStrength();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<GocHfpClientCall> getHfpCallList() throws RemoteException {
            if (BtService.this.mCommandHfp != null) {
                return BtService.this.mCommandHfp.getHfpCallList();
            }
            Log.e(BtService.TAG, "getHfpCallList mCommandHfp is null");
            return new ArrayList();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
            Log.e("hfp_call", "mCommandHfp>>>>>()" + BtService.this.mCommandHfp);
            if (BtService.this.mCommandHfp != null) {
                return BtService.this.mCommandHfp.getHfpCallList2();
            }
            Log.e(BtService.TAG, "getHfpCallList2 mCommandHfp is null");
            return new ArrayList();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpRemoteOnRoaming() throws RemoteException {
            Log.e(BtService.TAG, "isHfpRemoteOnRoaming()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpRemoteOnRoaming();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHfpRemoteBatteryIndicator() throws RemoteException {
            Log.d(BtService.TAG, "getHfpRemoteBatteryIndicator()");
            if (BtService.this.mCommandHfp == null) {
                return -1;
            }
            return BtService.this.mCommandHfp.getHfpRemoteBatteryIndicator();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
            Log.d(BtService.TAG, "isHfpRemoteTelecomServiceOn()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpRemoteTelecomServiceOn();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
            Log.d(BtService.TAG, "isHfpRemoteVoiceDialOn()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpRemoteVoiceDialOn();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpDialCall(String number) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpDialCall() number: " + number);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpDialCall(number);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpReDial() throws RemoteException {
            Log.d(BtService.TAG, "reqHfpReDial()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpReDial();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpMemoryDial(String index) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpMemoryDial() index: " + index);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpMemoryDial(index);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpAnswerCall(String address, int flag) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpAnswerCall() " + flag);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpAnswerCall(flag);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpRejectIncomingCall(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpRejectIncomingCall()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpRejectIncomingCall(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpTerminateCurrentCall(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpTerminateCurrentCall()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpTerminateCurrentCall(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpSendDtmf(String address, String number) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpSendDtmf() number: " + number);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpSendDtmf(address, number);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpAudioTransferToCarkit(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpAudioTransfer()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpAudioTransferToCarkit(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpAudioTransferToPhone(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpAudioTransferToPhone()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpAudioTransferToPhone(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHfpRemoteNetworkOperator() throws RemoteException {
            Log.d(BtService.TAG, "reqHfpRemoteNetworkOperator()");
            if (BtService.this.mCommandHfp == null) {
                return null;
            }
            return BtService.this.mCommandHfp.getHfpRemoteNetworkOperator();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHfpRemoteSubscriberNumber() throws RemoteException {
            Log.d(BtService.TAG, "getHfpRemoteSubscriberNumber()");
            if (BtService.this.mCommandHfp == null) {
                return null;
            }
            return BtService.this.mCommandHfp.getHfpRemoteSubscriberNumber();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpVoiceDial(boolean enable) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpVoiceDial() enable: " + enable);
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.reqHfpVoiceDial(enable);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void pauseHfpRender() throws RemoteException {
            Log.d(BtService.TAG, "pauseHfpRender()");
            if (BtService.this.mCommandHfp != null) {
                BtService.this.mCommandHfp.pauseHfpRender();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void startHfpRender() throws RemoteException {
            Log.d(BtService.TAG, "startHfpRender()");
            if (BtService.this.mCommandHfp != null) {
                BtService.this.mCommandHfp.startHfpRender();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpMicMute() throws RemoteException {
            Log.d(BtService.TAG, "isHfpMicMute()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpMicMute();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void muteHfpMic(boolean mute) throws RemoteException {
            Log.d(BtService.TAG, "muteHfpMic() " + mute);
            if (BtService.this.mCommandHfp != null) {
                BtService.this.mCommandHfp.muteHfpMic(mute);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHfpInBandRingtoneSupport() throws RemoteException {
            Log.d(BtService.TAG, "isHfpInbandRingtoneSupport()");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.isHfpInBandRingtoneSupport();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerPbapCallback(UiCallbackPbap cb) throws RemoteException {
            Log.d(BtService.TAG, "registerPbapCallback()");
            return BtService.this.mDoCallbackPbap.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterPbapCallback(UiCallbackPbap cb) throws RemoteException {
            Log.d(BtService.TAG, "registerPbapCallback()");
            return BtService.this.mDoCallbackPbap.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getPbapConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getPbapConnectionState()");
            if (BtService.this.mCommandPbap == null) {
                return 100;
            }
            return BtService.this.mCommandPbap.getPbapConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isPbapDownloading() throws RemoteException {
            Log.d(BtService.TAG, "isPbapDownloading()");
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.isPbapDownloading();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getPbapDownloadingAddress() throws RemoteException {
            Log.d(BtService.TAG, "getPbapDownloadingAddress()");
            if (BtService.this.mCommandPbap == null) {
                return NfDef.DEFAULT_ADDRESS;
            }
            return BtService.this.mCommandPbap.getPbapDownloadingAddress();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownload(String address, int storage, int property) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownload() " + address + " (" + storage + ")");
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownload(address, storage, property);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadRange(String address, int storage, int property, int startPos, int offset) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownloadRange()");
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownloadRange(address, storage, property, startPos, offset);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadToDatabase(String address, int storage, int property) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownloadToDatabase() " + address + " storage: " + storage + " property: " + property);
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownloadToDatabase(address, storage, property);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadRangeToDatabase(String address, int storage, int property, int startPos, int offset) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownloadRangeToDatabase() " + address + " storage: " + storage + " isPhoteRequire: " + property + " startPos: " + startPos + " offset: " + offset);
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownloadRangeToDatabase(address, storage, property, startPos, offset);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadToContactsProvider(String address, int storage, int property) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownloadToContactsProvider() " + address + " storage: " + storage + " isPhoteRequire: " + property);
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownloadToContactsProvider(address, storage, property);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadRangeToContactsProvider(String address, int storage, int property, int startPos, int offset) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownloadRangeToContactsProvider() " + address + " storage: " + storage + " isPhoteRequire: " + property + " startPos: " + startPos + " offset: " + offset);
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownloadRangeToContactsProvider(address, storage, property, startPos, offset);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDatabaseQueryNameByNumber(String address, String target) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDatabaseQueryNameByNumber() " + address + " target: " + target);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.reqPbapDatabaseQueryNameByNumber(address, target);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDatabaseQueryNameByPartialNumber(String address, String target, int findPosition) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDatabaseQueryNameByPartialNumber() " + address + " target: " + target + " findPosition: " + findPosition);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.reqPbapDatabaseQueryNameByPartialNumber(address, target, findPosition);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDatabaseAvailable(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDatabaseAvailable() " + address);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.reqPbapDatabaseAvailable(address);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapDeleteDatabaseByAddress(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDeleteDatabaseByAddress() " + address);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.reqPbapDeleteDatabaseByAddress(address);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqPbapCleanDatabase() throws RemoteException {
            Log.d(BtService.TAG, "reqPbapCleanDatabase()");
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.reqPbapCleanDatabase();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqPbapDownloadInterrupt(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqPbapDownloadInterrupt() " + address);
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.reqPbapDownloadInterrupt(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setPbapDownloadNotify(int frequency) throws RemoteException {
            Log.d(BtService.TAG, "setPbapDownloadNotify() " + frequency);
            if (BtService.this.mCommandPbap == null) {
                return false;
            }
            return BtService.this.mCommandPbap.setPbapDownloadNotify(frequency);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setTargetAddress(String address) throws RemoteException {
            Log.e(BtService.TAG, "setTargetAddress(): " + address);
            if (address != null) {
                BtService.this.targetAddress = address;
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getTargetAddress() throws RemoteException {
            Log.e(BtService.TAG, "getTargetAddress(): " + BtService.this.targetAddress);
            return BtService.this.targetAddress;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqAvrcpUpdateSongStatus() throws RemoteException {
            Log.d(BtService.TAG, "reqAvrcpUpdateSongStatus()");
            BtService.this.mDoCallbackAvrcp.retAvrcpUpdateSongStatus(BtService.this.artist, BtService.this.album, BtService.this.title);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerHidCallback(UiCallbackHid cb) throws RemoteException {
            Log.d(BtService.TAG, "registerHidCallback()");
            return BtService.this.mDoCallbackHid.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterHidCallback(UiCallbackHid cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterHidCallback()");
            return BtService.this.mDoCallbackHid.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getHidConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getHidConnectionState()");
            if (BtService.this.mCommandHid == null) {
                return -1;
            }
            return BtService.this.mCommandHid.getHidConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isHidConnected() throws RemoteException {
            Log.d(BtService.TAG, "isHidConnected()");
            if (BtService.this.mCommandHid == null) {
                return false;
            }
            return BtService.this.mCommandHid.isHidConnected();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getHidConnectedAddress() throws RemoteException {
            Log.d(BtService.TAG, "getHidConnectedAddress()");
            if (BtService.this.mCommandHid == null) {
                return NfDef.DEFAULT_ADDRESS;
            }
            return BtService.this.mCommandHid.getHidConnectedAddress();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHidConnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHidConnect() " + address);
            if (BtService.this.mCommandHid == null) {
                return false;
            }
            return BtService.this.mCommandHid.reqHidConnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHidDisconnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHidDisconnect() " + address);
            if (BtService.this.mCommandHid == null) {
                return false;
            }
            return BtService.this.mCommandHid.reqHidDisconnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSendHidMouseCommand(int button, int offset_x, int offset_y, int wheel) throws RemoteException {
            Log.d(BtService.TAG, "reqSendHidMouseCommand - button: " + button + ", offset_x: " + offset_x + ", offset_y: " + offset_y + ", wheel: " + wheel);
            if (BtService.this.mCommandHid == null) {
                return false;
            }
            return BtService.this.mCommandHid.reqSendHidMouseCommand(button, offset_x, offset_y, wheel);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSendHidVirtualKeyCommand(int key_1, int key_2) throws RemoteException {
            Log.d(BtService.TAG, "reqSendHidMouseCommand - key_1: " + key_1 + ", key_2: " + key_2);
            if (BtService.this.mCommandHid == null) {
                return false;
            }
            return BtService.this.mCommandHid.reqSendHidVirtualKeyCommand(key_1, key_2);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerSppCallback(UiCallbackSpp cb) throws RemoteException {
            Log.d(BtService.TAG, "UiCallbackSpp()");
            return BtService.this.mDoCallbackSpp.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterSppCallback(UiCallbackSpp cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterSppCallback()");
            return BtService.this.mDoCallbackSpp.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSppConnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqSppConnect() " + address);
            if (BtService.this.mCommandSpp == null) {
                return false;
            }
            return BtService.this.mCommandSpp.reqSppConnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqSppDisconnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqSppDisconnect() " + address);
            if (BtService.this.mCommandSpp == null) {
                return false;
            }
            return BtService.this.mCommandSpp.reqSppDisconnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqSppConnectedDeviceAddressList() throws RemoteException {
            Log.d(BtService.TAG, "reqSppConnectedDeviceAddressList()");
            if (BtService.this.mCommandSpp != null) {
                BtService.this.mCommandSpp.reqSppConnectedDeviceAddressList();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isSppConnected(String address) throws RemoteException {
            Log.d(BtService.TAG, "isSppConnected() " + address);
            if (BtService.this.mCommandSpp == null) {
                return false;
            }
            return BtService.this.mCommandSpp.isSppConnected(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqSppSendData(String address, byte[] sppData) throws RemoteException {
            Log.d(BtService.TAG, "reqSppSendData() " + address);
            if (BtService.this.mCommandSpp != null) {
                BtService.this.mCommandSpp.reqSppSendData(address, sppData);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerMapCallback(UiCallbackMap cb) throws RemoteException {
            Log.d(BtService.TAG, "registerMapCallback()");
            return BtService.this.mDoCallbackMap.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterMapCallback(UiCallbackMap cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterMapCallback()");
            return BtService.this.mDoCallbackMap.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDownloadSingleMessage(String address, int folder, String handle, int storage) throws RemoteException {
            Log.d(BtService.TAG, "reqMapDownloadSingleMessage() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapDownloadSingleMessage(address, folder, handle, storage);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDownloadMessage(String address, int folder, boolean isContentDownload, int count, int startPos, int storage, String periodBegin, String periodEnd, String sender, String recipient, int readStatus, int typeFilter) throws RemoteException {
            Log.d(BtService.TAG, "reqMapDownloadAllMessages() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapDownloadMessage(address, folder, isContentDownload, count, startPos, storage, periodBegin, periodEnd, sender, recipient, readStatus, typeFilter);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapRegisterNotification(String address, boolean downloadNewMessage) throws RemoteException {
            Log.d(BtService.TAG, "reqMapRegisterNotification() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapRegisterNotification(address, downloadNewMessage);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapUnregisterNotification(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqMapUnregisterNotification() " + address);
            if (BtService.this.mCommandMap != null) {
                BtService.this.mCommandMap.reqMapUnregisterNotification(address);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isMapNotificationRegistered(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqMapDownloadInterrupt() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.isMapNotificationRegistered(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDownloadInterrupt(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqMapDownloadInterrupt() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapDownloadInterrupt(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapDatabaseAvailable() throws RemoteException {
            Log.d(BtService.TAG, "reqMapDatabaseAvailable()");
            if (BtService.this.mCommandMap != null) {
                BtService.this.mCommandMap.reqMapDatabaseAvailable();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapDeleteDatabaseByAddress(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqMapDeleteDatabaseByAddress() " + address);
            if (BtService.this.mCommandMap != null) {
                BtService.this.mCommandMap.reqMapDeleteDatabaseByAddress(address);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void reqMapCleanDatabase() throws RemoteException {
            Log.d(BtService.TAG, "reqMapCleanDatabase()");
            if (BtService.this.mCommandMap != null) {
                BtService.this.mCommandMap.reqMapCleanDatabase();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMapCurrentState(String address) throws RemoteException {
            Log.d(BtService.TAG, "getMapCurrentState() " + address);
            if (BtService.this.mCommandMap == null) {
                return 100;
            }
            return BtService.this.mCommandMap.getMapCurrentState(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMapRegisterState(String address) throws RemoteException {
            Log.d(BtService.TAG, "getMapRegisterState() " + address);
            if (BtService.this.mCommandMap == null) {
                return 100;
            }
            return BtService.this.mCommandMap.getMapRegisterState(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapSendMessage(String address, String message, String target) throws RemoteException {
            Log.d(BtService.TAG, "reqMapSendMessage() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapSendMessage(address, message, target);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapDeleteMessage(String address, int folder, String handle) throws RemoteException {
            Log.d(BtService.TAG, "reqMapDeleteMessage() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapDeleteMessage(address, folder, handle);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqMapChangeReadStatus(String address, int folder, String handle, boolean isReadStatus) throws RemoteException {
            Log.d(BtService.TAG, "reqMapChangeReadStatus() " + address);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.reqMapChangeReadStatus(address, folder, handle, isReadStatus);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setMapDownloadNotify(int frequency) throws RemoteException {
            Log.d(BtService.TAG, "setMapDownloadNotify() " + frequency);
            if (BtService.this.mCommandMap == null) {
                return false;
            }
            return BtService.this.mCommandMap.setMapDownloadNotify(frequency);
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
            Log.d(BtService.TAG, "registerOppCallback()");
            return BtService.this.mDoCallbackOpp.register(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterOppCallback(UiCallbackOpp cb) throws RemoteException {
            Log.d(BtService.TAG, "unregisterOppCallback()");
            return BtService.this.mDoCallbackOpp.unregister(cb);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean setOppFilePath(String path) throws RemoteException {
            Log.d(BtService.TAG, "setOppFilePath()");
            if (BtService.this.mCommandOpp == null) {
                return false;
            }
            return BtService.this.mCommandOpp.setOppFilePath(path);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getOppFilePath() throws RemoteException {
            Log.d(BtService.TAG, "getOppFilePath()");
            if (BtService.this.mCommandOpp == null) {
                return BuildConfig.FLAVOR;
            }
            return BtService.this.mCommandOpp.getOppFilePath();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqOppAcceptReceiveFile(boolean accept) throws RemoteException {
            Log.d(BtService.TAG, "reqOppAcceptReceiveFile()");
            if (BtService.this.mCommandOpp == null) {
                return false;
            }
            return BtService.this.mCommandOpp.reqOppAcceptReceiveFile(accept);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqOppInterruptReceiveFile() throws RemoteException {
            Log.d(BtService.TAG, "reqOppInterruptReceiveFile()");
            if (BtService.this.mCommandOpp == null) {
                return false;
            }
            return BtService.this.mCommandOpp.reqOppInterruptReceiveFile();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean isGattServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "isGattServiceReady()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.isGattServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean registerGattServerCallback(UiCallbackGattServer callback) throws RemoteException {
            Log.d(BtService.TAG, "registerGattServerCallback()");
            if (BtService.this.mDoCallbackGattServer != null) {
                return BtService.this.mDoCallbackGattServer.register(callback);
            }
            Log.d(BtService.TAG, "DoCallbackGattServer is null!!");
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean unregisterGattServerCallback(UiCallbackGattServer callback) throws RemoteException {
            Log.d(BtService.TAG, "unregisterGattServerCallback()");
            if (BtService.this.mDoCallbackGattServer != null) {
                return BtService.this.mDoCallbackGattServer.unregister(callback);
            }
            Log.d(BtService.TAG, "DoCallbackGattServer is null!!");
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getGattServerConnectionState() throws RemoteException {
            Log.d(BtService.TAG, "getGattServerConnectionState()");
            if (BtService.this.mCommandGattServer == null) {
                return -1;
            }
            return BtService.this.mCommandGattServer.getGattServerConnectionState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerDisconnect(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerDisconnect() " + address);
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerDisconnect(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerListen(boolean listen) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerListen()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerListen(listen);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerBeginServiceDeclaration(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerBeginServiceDeclaration()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerBeginServiceDeclaration(srvcType, srvcUuid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerAddCharacteristic(ParcelUuid charUuid, int properties, int permissions) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerAddCharacteristic()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerAddCharacteristic(charUuid, properties, permissions);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerAddDescriptor(ParcelUuid descUuid, int permissions) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerAddDescriptor()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerAddDescriptor(descUuid, permissions);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerEndServiceDeclaration()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerEndServiceDeclaration();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerRemoveService(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerRemoveService()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerRemoveService(srvcType, srvcUuid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerClearServices() throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerClearServices()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerClearServices();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerSendResponse(String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerSendResponse()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerSendResponse(address, requestId, status, offset, value);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqGattServerSendNotification(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm, byte[] value) throws RemoteException {
            Log.d(BtService.TAG, "reqGattServerSendNotification()");
            if (BtService.this.mCommandGattServer == null) {
                return false;
            }
            return BtService.this.mCommandGattServer.reqGattServerSendNotification(address, srvcType, srvcUuid, charUuid, confirm, value);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
            Log.d(BtService.TAG, "getGattAddedGattServiceUuidList()");
            return BtService.this.mCommandGattServer.getGattAddedGattServiceUuidList();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid srvcUuid) throws RemoteException {
            Log.d(BtService.TAG, "getGattAddedGattCharacteristicUuidList()");
            return BtService.this.mCommandGattServer.getGattAddedGattCharacteristicUuidList(srvcUuid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
            Log.d(BtService.TAG, "getGattAddedGattDescriptorUuidList()");
            return BtService.this.mCommandGattServer.getGattAddedGattDescriptorUuidList(srvcUuid, charUuid);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoDownEnable(boolean isBtAutoDownEnable, int opt) throws RemoteException {
            Log.d(BtService.TAG, "setBtAutoDownEnable isBtAutoDownEnable:" + isBtAutoDownEnable + " opt:" + opt);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getBtAutoDownState(int opt) throws RemoteException {
            Log.d(BtService.TAG, "getBtAutoDownState open:" + opt);
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) throws RemoteException {
            Log.d(BtService.TAG, "setBtAutoAnswerEnable isBtAutoAnswerEnable:" + isBtAutoAnswerEnable + " time:" + time);
            BtService.this.mCommandHfp.setBtAutoAnswerEnable(isBtAutoAnswerEnable, time);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getBtAutoAnswerState() throws RemoteException {
            Log.d(BtService.TAG, "getBtAutoAnswerState");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.getBtAutoAnswerState();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) throws RemoteException {
            Log.d(BtService.TAG, "setBtAnswerTypeEnable");
            BtService.this.mCommandHfp.setBtAnswerTypeEnable(isBtAnswerTypeEnable);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getBtAnswerType() throws RemoteException {
            Log.d(BtService.TAG, "getBtAnswerType");
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.getBtAnswerType();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtDevConnAddr(String btDevConnAddr) throws RemoteException {
            Log.d(BtService.TAG, "setBtDevConnAddr btDevConnAddr:" + btDevConnAddr);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtDevConnAddr() throws RemoteException {
            Log.d(BtService.TAG, "getBtDevConnAddr");
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setContactsToDB(List<Contacts> list) throws RemoteException {
            Log.d(BtService.TAG, "setContactsToDB");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<Contacts> getContactsListForDB() throws RemoteException {
            Log.d(BtService.TAG, "getContactsListForDB");
            if (BtService.this.mCommandPbap == null) {
                return new ArrayList(1);
            }
            return BtService.this.mCommandPbap.getContactsListForDB();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setCallLogsToDB(List<CallLogs> list) throws RemoteException {
            Log.d(BtService.TAG, "setCallLogsToDB");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setUiCallLogsToDB(String name, String number, int type, String time) throws RemoteException {
            Log.d(BtService.TAG, "setUiCallLogsToDB");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<CallLogs> getCallLogsListForDB(int opt) throws RemoteException {
            Log.d(BtService.TAG, "getCallLogsListForDB:" + opt);
            return BtService.this.mCommandPbap.getCallLogsListForDB(opt);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean deleteDatabase() throws RemoteException {
            Log.d(BtService.TAG, "deleteDatabase");
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean cleanTable(int options) throws RemoteException {
            Log.d(BtService.TAG, "cleanTable:" + options);
            return BtService.this.mCommandPbap.cleanTable(options);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getCallName(String number) throws RemoteException {
            Log.d(BtService.TAG, "getCallName");
            return BtService.this.mCommandPbap.getCallName(number);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getCallPhoto(String number) throws RemoteException {
            Log.d(BtService.TAG, "getCallPhoto");
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setPairingConfirmation() throws RemoteException {
            Log.d(BtService.TAG, "setPairingConfirmation");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void cancelPairingUserInput() throws RemoteException {
            Log.d(BtService.TAG, "cancelPairingUserInput");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMissedCallSize() throws RemoteException {
            Log.d(BtService.TAG, "getMissedCallSize");
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void initMissedCallSize() throws RemoteException {
            Log.d(BtService.TAG, "initMissedCallSize");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void saveBellPath(String path) throws RemoteException {
            if (BtService.this.mCommandBluetooth != null) {
                BtService.this.mCommandBluetooth.saveBellPath(path);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBellPath() throws RemoteException {
            if (BtService.this.mCommandBluetooth == null) {
                return BuildConfig.FLAVOR;
            }
            return BtService.this.mCommandBluetooth.getBellPath();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void delCallLogsForDB(int id) throws RemoteException {
            Log.d(BtService.TAG, "delCallLogsForDB id:" + id);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.delCallLogsForDB(id);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void delContactsForDB(int id) throws RemoteException {
            Log.d(BtService.TAG, "delContactsForDB id:" + id);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.delContactsForDB(id);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setCollectionsToDB(List<Collection> collections) throws RemoteException {
            Log.d(BtService.TAG, "setCollectionsToDB:" + collections.size());
            for (int i = 0; i < collections.size(); i++) {
                Collection s = collections.get(i);
                Log.d(BtService.TAG, "id:" + s.getId() + " cid:" + s.getCid());
                if (BtService.this.mCommandPbap != null) {
                    BtService.this.mCommandPbap.setCollectionsToDB(s.getCid(), "1");
                }
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void delCollectionsForDB(int id) throws RemoteException {
            Log.d(BtService.TAG, "delCollectionsForDB id:" + id);
            if (BtService.this.mCommandPbap != null) {
                BtService.this.mCommandPbap.setCollectionsToDB(id, "0");
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<Collection> getCollectionsForDB() throws RemoteException {
            Log.d(BtService.TAG, "getCollectionsForDB");
            if (BtService.this.mCommandPbap == null) {
                return new ArrayList(1);
            }
            return BtService.this.mCommandPbap.getCollectionsForDB();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public byte getPlayStatus() throws RemoteException {
            Log.d(BtService.TAG, "getPlayStatus");
            if (BtService.this.mCommandA2dp != null) {
                return BtService.this.mCommandA2dp.getPlayStatus();
            }
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setBtMainDevices(String address) throws RemoteException {
            Log.d(BtService.TAG, "setBtMainDevices:" + address);
            if (BtService.this.mCommandBluetooth != null) {
                BtService.this.mCommandBluetooth.setBtMainDevices(address);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getBtMainDevices() throws RemoteException {
            Log.d(BtService.TAG, "getBtMainDevices addr");
            if (BtService.this.mCommandBluetooth == null) {
                return BuildConfig.FLAVOR;
            }
            return BtService.this.mCommandBluetooth.getBtMainDevices();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void switchingMainDevices(String address) throws RemoteException {
            Log.d(BtService.TAG, "switchingMainDevices:" + address);
            if (BtService.this.mCommandBluetooth != null) {
                BtService.this.mCommandBluetooth.switchingMainDevices(address);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getUnreadSMS() throws RemoteException {
            Log.d(BtService.TAG, "getUnreadSMS");
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void initUnreadSMS() throws RemoteException {
            Log.d(BtService.TAG, "initUnreadSMS");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setThreePartyCallEnable(boolean enable) throws RemoteException {
            if (BtService.this.mCommandHfp != null) {
                BtService.this.mCommandHfp.setThreePartyCallEnable(enable);
            }
            Log.d(BtService.TAG, "setThreePartyCallEnable:" + enable);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getThreePartyCallEnable() throws RemoteException {
            if (BtService.this.mCommandHfp == null) {
                return false;
            }
            return BtService.this.mCommandHfp.getThreePartyCallEnable();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String[] getBtVersionInfoArr() throws RemoteException {
            Log.d(BtService.TAG, "getBtVersionInfoArr");
            return new String[]{"4.0edr", "f20", "s21", "p16", "v25"};
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getMaxDownCount(int opt) throws RemoteException {
            Log.d(BtService.TAG, "getMaxDownCount");
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getStartBtMusicType() throws RemoteException {
            Log.d(BtService.TAG, "getStartBtMusicType");
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setDualDeviceEnable(boolean enable) throws RemoteException {
            Log.d(BtService.TAG, "setDualDeviceEnable:" + enable);
            if (BtService.this.mCommandBluetooth != null) {
                BtService.this.mCommandBluetooth.setDualDeviceEnable(enable);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getDualDeviceEnable() throws RemoteException {
            if (BtService.this.mCommandBluetooth == null) {
                return false;
            }
            boolean _enable = BtService.this.mCommandBluetooth.getDualDeviceEnable();
            Log.d(BtService.TAG, "getDualDeviceEnable:" + _enable);
            return _enable;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public List<String> getHfpConnectedAddressList() throws RemoteException {
            if (BtService.this.mCommandBluetooth == null) {
                return new ArrayList(1);
            }
            List<String> list = BtService.this.mCommandBluetooth.getProfileConnectedAddrByProile("HFP");
            Log.d(BtService.TAG, "getHfpConnectedAddressList:" + list);
            if (list == null) {
                return null;
            }
            return list;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean getDebugMode() throws RemoteException {
            return true;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqBtUnpairAllPairedDevices() throws RemoteException {
            Log.d(BtService.TAG, "reqBtUnpairAllPairedDevices");
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public void setRejectMapMsg(String msg) throws RemoteException {
            Log.d(BtService.TAG, "setRejectMapMsg:" + msg);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public String getRejectMapMsg() throws RemoteException {
            Log.d(BtService.TAG, "getRejectMapMsg");
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public boolean reqHfpReply(String address) throws RemoteException {
            Log.d(BtService.TAG, "reqHfpReply:" + address);
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCommand
        public int getPbapDownLoadState() throws RemoteException {
            int resault = 0;
            if (BtService.this.mCommandPbap != null) {
                resault = BtService.this.mCommandPbap.getPbapDownLoadState();
            }
            Log.d(BtService.TAG, "getPbapDownLoadState:" + resault);
            return resault;
        }
    };
    private GocCallbackA2dp mCallbackA2dp = new GocCallbackA2dp.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass4 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackA2dp
        public void onA2dpServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onA2dpServiceReady()");
            BtService.this.mDoCallbackA2dp.onA2dpServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackA2dp
        public void onA2dpStateChanged(String address, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onA2dpStateChanged() " + address + " state: " + prevState + "->" + newState);
            BtService.this.mDoCallbackA2dp.onA2dpStateChanged(address, prevState, newState);
            BtService.this.mDoCallbackBluetooth.onA2dpStateChanged(address, prevState, newState);
        }
    };
    private GocCallbackAvrcp mCallbackAvrcp = new GocCallbackAvrcp.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass5 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcpServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onAvrcpServiceReady()");
            BtService.this.mDoCallbackAvrcp.onAvrcpServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcpStateChanged() " + address + " state: " + prevState + "->" + newState);
            if (newState >= 140 && prevState < 140) {
                Log.e(BtService.TAG, "reqAvrcpCtRegisterEventWatcher");
                BtService.this.mCommandAvrcp.reqAvrcpRegisterEventWatcher((byte) 2, 0);
                BtService.this.mCommandAvrcp.reqAvrcpRegisterEventWatcher((byte) 1, 0);
            } else if (newState == 110) {
                BtService btService = BtService.this;
                btService.title = BuildConfig.FLAVOR;
                btService.artist = BuildConfig.FLAVOR;
                btService.album = BuildConfig.FLAVOR;
            }
            BtService.this.mDoCallbackAvrcp.onAvrcpStateChanged(address, prevState, newState);
            BtService.this.mDoCallbackBluetooth.onAvrcpStateChanged(address, prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13CapabilitiesSupportEvent()");
            BtService.this.mDoCallbackAvrcp.retAvrcp13CapabilitiesSupportEvent(eventIds);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcpPlayModeChanged(String address, int mode) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcpPlayModeChanged()");
            BtService.this.mDoCallbackAvrcp.retAvrcpPlayModeChanged(address, mode);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13PlayerSettingAttributesList()");
            BtService.this.mDoCallbackAvrcp.retAvrcp13PlayerSettingAttributesList(attributeIds);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13PlayerSettingValuesList() attributeId: " + ((int) attributeId));
            BtService.this.mDoCallbackAvrcp.retAvrcp13PlayerSettingValuesList(attributeId, valueIds);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13PlayerSettingCurrentValues()");
            BtService.this.mDoCallbackAvrcp.retAvrcp13PlayerSettingCurrentValues(attributeIds, valueIds);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13SetPlayerSettingValueSuccess()");
            BtService.this.mDoCallbackAvrcp.retAvrcp13SetPlayerSettingValueSuccess();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13ElementAttributesPlaying()");
            BtService btService = BtService.this;
            btService.title = BuildConfig.FLAVOR;
            btService.album = BuildConfig.FLAVOR;
            btService.artist = BuildConfig.FLAVOR;
            for (int i = 0; i < metadataAtrributeIds.length; i++) {
                if (metadataAtrributeIds[i] == 1) {
                    BtService.this.title = texts[i];
                    Log.d(BtService.TAG, "retAvrcp13ElementAttributesPlaying() title: " + BtService.this.title);
                } else if (metadataAtrributeIds[i] == 3) {
                    BtService.this.album = texts[i];
                    Log.d(BtService.TAG, "retAvrcp13ElementAttributesPlaying() album: " + BtService.this.album);
                } else if (metadataAtrributeIds[i] == 2) {
                    BtService.this.artist = texts[i];
                    Log.d(BtService.TAG, "retAvrcp13ElementAttributesPlaying() artist: " + BtService.this.artist);
                }
            }
            BtService.this.mDoCallbackAvrcp.retAvrcp13ElementAttributesPlaying(metadataAtrributeIds, texts);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp13PlayStatus() songLen: " + songLen + " songPos: " + songPos + " statusId: " + ((int) statusId));
            BtService.this.mDoCallbackAvrcp.retAvrcp13PlayStatus(songLen, songPos, statusId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventPlaybackStatusChanged(byte statusId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventPlaybackStatusChanged() statusId: " + ((int) statusId));
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventPlaybackStatusChanged(statusId);
            if (statusId == -1) {
                Log.d(BtService.TAG, "[AVRCP_PLAYING_STATUS_ID_ERROR]");
            } else if (statusId == 0) {
                Log.d(BtService.TAG, "[AVRCP_PLAYING_STATUS_ID_STOPPED]");
            } else if (statusId == 1) {
                Log.d(BtService.TAG, "[AVRCP_PLAYING_STATUS_ID_PLAYING]");
            } else if (statusId == 2) {
                Log.d(BtService.TAG, "[AVRCP_PLAYING_STATUS_ID_PAUSED]");
            } else if (statusId == 3) {
                Log.d(BtService.TAG, "[AVRCP_PLAYING_STATUS_ID_FWD_SEEK]");
            } else if (statusId == 4) {
                Log.d(BtService.TAG, "[AVRCP_PLAYING_STATUS_ID_REW_SEEK]");
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventTrackChanged(long elementId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventTrackChanged() elemendId: " + elementId);
            BtService.this.mCommandAvrcp.reqAvrcp13GetElementAttributesPlaying();
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventTrackChanged(elementId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventTrackReachedEnd()");
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventTrackReachedEnd();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventTrackReachedStart() throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventTrackReachedStart()");
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventTrackReachedStart();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventPlaybackPosChanged(long songPos) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventPlaybackPosChanged() songPos: " + songPos);
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventPlaybackPosChanged(songPos);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventBatteryStatusChanged(byte statusId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventBatteryStatusChanged() statusId: " + ((int) statusId));
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventBatteryStatusChanged(statusId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventSystemStatusChanged(byte statusId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventSystemStatusChanged() statusId: " + ((int) statusId));
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventSystemStatusChanged((long) statusId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13EventPlayerSettingChanged()");
            BtService.this.mDoCallbackAvrcp.onAvrcp13EventPlayerSettingChanged(attributeIds, valueIds);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp14EventNowPlayingContentChanged()");
            BtService.this.mDoCallbackAvrcp.onAvrcp14EventNowPlayingContentChanged();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp14EventAvailablePlayerChanged()");
            BtService.this.mDoCallbackAvrcp.onAvrcp14EventAvailablePlayerChanged();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp14EventAddressedPlayerChanged() playerId: " + playerId + " uidCounter: " + uidCounter);
            BtService.this.mDoCallbackAvrcp.onAvrcp14EventAddressedPlayerChanged(playerId, uidCounter);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp14EventUidsChanged(int uidCounter) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp14EventUidsChanged() uidCounter: " + uidCounter);
            BtService.this.mDoCallbackAvrcp.onAvrcp14EventUidsChanged(uidCounter);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp14EventVolumeChanged(byte volume) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp14EventVolumeChanged() volume: " + ((int) volume));
            BtService.this.mDoCallbackAvrcp.onAvrcp14EventVolumeChanged(volume);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14SetAddressedPlayerSuccess()");
            BtService.this.mDoCallbackAvrcp.retAvrcp14SetAddressedPlayerSuccess();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) throws RemoteException {
            String p = BuildConfig.FLAVOR;
            for (int i = 0; i < path.length; i++) {
                p = p + path[i];
            }
            Log.d(BtService.TAG, "retAvrcp14SetBrowsedPlayerSuccess() path: " + p + " uidCounter: " + uidCounter + " itemCount: " + itemCount);
            BtService.this.mDoCallbackAvrcp.retAvrcp14SetBrowsedPlayerSuccess(path, uidCounter, itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14FolderItems(int uidCounter, long itemCount) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14FolderItems() uidCounter: " + uidCounter + " itemCount: " + itemCount);
            BtService.this.mDoCallbackAvrcp.retAvrcp14FolderItems(uidCounter, itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14MediaItems(int uidCounter, long itemCount) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14MediaItems() uidCounter: " + uidCounter + " itemCount: " + itemCount);
            BtService.this.mDoCallbackAvrcp.retAvrcp14MediaItems(uidCounter, itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14ChangePathSuccess(long itemCount) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14ChangePathSuccess() itemCount: " + itemCount);
            BtService.this.mDoCallbackAvrcp.retAvrcp14ChangePathSuccess(itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14ItemAttributes()");
            BtService.this.mDoCallbackAvrcp.retAvrcp14ItemAttributes(metadataAtrributeIds, texts);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14PlaySelectedItemSuccess()");
            BtService.this.mDoCallbackAvrcp.retAvrcp14PlaySelectedItemSuccess();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14SearchResult(int uidCounter, long itemCount) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14SearchResult() uidCounter: " + uidCounter + " itemCount: " + itemCount);
            BtService.this.mDoCallbackAvrcp.retAvrcp14SearchResult(uidCounter, itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14AddToNowPlayingSuccess()");
            BtService.this.mDoCallbackAvrcp.retAvrcp14AddToNowPlayingSuccess();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) throws RemoteException {
            Log.d(BtService.TAG, "retAvrcp14SetAbsoluteVolumeSuccess() volume: " + ((int) volume));
            BtService.this.mDoCallbackAvrcp.retAvrcp14SetAbsoluteVolumeSuccess(volume);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcpErrorResponse(int opId, int reason, byte eventId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcpErrorResponse() opId: " + opId + " reason: " + reason + " eventId: " + ((int) eventId));
            BtService.this.mDoCallbackAvrcp.onAvrcpErrorResponse(opId, reason, eventId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherSuccess(byte eventId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13RegisterEventWatcherSuccess() eventId: " + ((int) eventId));
            BtService.this.mDoCallbackAvrcp.onAvrcp13RegisterEventWatcherSuccess(eventId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherFail(byte eventId) throws RemoteException {
            Log.d(BtService.TAG, "onAvrcp13RegisterEventWatcherFail() eventId: " + ((int) eventId));
            BtService.this.mDoCallbackAvrcp.onAvrcp13RegisterEventWatcherFail(eventId);
        }
    };
    private GocCallbackBluetooth mCallbackBluetooth = new GocCallbackBluetooth.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass8 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onBluetoothServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onBluetoothServiceReady()");
            BtService.this.mDoCallbackBluetooth.onBluetoothServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterStateChanged(int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onAdapterStateChanged() state: " + prevState + "->" + newState);
            BtService.this.mDoCallbackBluetooth.onAdapterStateChanged(prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterDiscoverableModeChanged(int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onAdapterDiscoverableModeChanged() state: " + prevState + "->" + newState);
            BtService.this.mDoCallbackBluetooth.onAdapterDiscoverableModeChanged(prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterDiscoveryStarted() throws RemoteException {
            Log.d(BtService.TAG, "onAdapterDiscoveryStarted()");
            BtService.this.mDoCallbackBluetooth.onAdapterDiscoveryStarted();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAdapterDiscoveryFinished() throws RemoteException {
            Log.d(BtService.TAG, "onAdapterDiscoveryFinished()");
            BtService.this.mDoCallbackBluetooth.onAdapterDiscoveryFinished();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void retPairedDevices(int elements, String[] address, String[] name, int[] supportProfile, byte[] category) throws RemoteException {
            Log.d(BtService.TAG, "retPairedDevices() elements: " + elements);
            BtService.this.mDoCallbackBluetooth.retPairedDevices(elements, address, name, supportProfile, category);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceFound(String address, String name, byte category) throws RemoteException {
            Log.d(BtService.TAG, "onDeviceFound() " + address + " name: " + name);
            BtService.this.mDoCallbackBluetooth.onDeviceFound(address, name, category);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceBondStateChanged(String address, String name, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onDeviceBondStateChanged() " + address + " name: " + name + " state: " + prevState + "->" + newState);
            BtService.this.mDoCallbackBluetooth.onDeviceBondStateChanged(address, name, prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceUuidsUpdated(String address, String name, int supportProfile) throws RemoteException {
            Log.d(BtService.TAG, "onDeviceUuidsUpdated() " + address + " name: " + name + " supportProfile: " + supportProfile);
            BtService.this.mDoCallbackBluetooth.onDeviceUuidsUpdated(address, name, supportProfile);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onLocalAdapterNameChanged(String name) throws RemoteException {
            Log.d(BtService.TAG, "onLocalAdapterNameChanged() " + name);
            BtService.this.mDoCallbackBluetooth.onLocalAdapterNameChanged(name);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceOutOfRange(String address) throws RemoteException {
            Log.d(BtService.TAG, "onDeviceOutOfRange() " + address);
            BtService.this.mDoCallbackBluetooth.onDeviceOutOfRange(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onDeviceAclDisconnected(String address) throws RemoteException {
            Log.d(BtService.TAG, "onDeviceAclDisconnected() " + address);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onBtRoleModeChanged(int mode) throws RemoteException {
            Log.d(BtService.TAG, "onBtRoleModeChanged() " + mode);
            BtService.this.mDoCallbackBluetooth.onBtRoleModeChanged(mode);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onBtAutoConnectStateChanged(String address, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onBtAutoConnectStateChanged() " + address + " state: " + prevState + " -> " + newState);
            BtService.this.mDoCallbackBluetooth.onBtAutoConnectStateChanged(address, prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onMainDevicesChanged(String addr, String name) throws RemoteException {
            BtService.this.mDoCallbackBluetooth.onMainDevicesChanged(addr, name);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAutoConnect(int state) throws RemoteException {
            BtService.this.mDoCallbackBluetooth.setOnAutoConnect(state);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onAutoAnwer(int state) throws RemoteException {
            BtService.this.mDoCallbackBluetooth.setOnAutoAnwer(state);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackBluetooth
        public void onConnectedDevice(String mainDevice, String subDevice) throws RemoteException {
            BtService.this.mDoCallbackBluetooth.setonConnectedDevice(mainDevice, subDevice);
        }
    };
    private GocCallbackGattServer mCallbackGattServer = new GocCallbackGattServer.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass12 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onGattServiceReady()");
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerStateChanged(String address, int state) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerStateChanged()");
            BtService.this.mDoCallbackGattServer.onGattServerStateChanged(address, state);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerServiceAdded()");
            BtService.this.mDoCallbackGattServer.onGattServerServiceAdded(status, srvcType, srvcUuid);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerServiceDeleted()");
            BtService.this.mDoCallbackGattServer.onGattServerServiceDeleted(status, srvcType, srvcUuid);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerCharacteristicReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerCharacteristicReadRequest()");
            BtService.this.mDoCallbackGattServer.onGattServerCharacteristicReadRequest(address, transId, offset, isLong, srvcType, srvcId, charId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerDescriptorReadRequest(String address, int transId, int offset, boolean isLong, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerDescriptorReadRequest()");
            BtService.this.mDoCallbackGattServer.onGattServerDescriptorReadRequest(address, transId, offset, isLong, srvcType, srvcId, charId, descrId);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerCharacteristicWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, byte[] value) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerCharacteristicWriteRequest()");
            BtService.this.mDoCallbackGattServer.onGattServerCharacteristicWriteRequest(address, transId, offset, isPrep, needRsp, srvcType, srvcId, charId, value);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerDescriptorWriteRequest(String address, int transId, int offset, boolean isPrep, boolean needRsp, int srvcType, ParcelUuid srvcId, ParcelUuid charId, ParcelUuid descrId, byte[] value) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerDescriptorWriteRequest()");
            BtService.this.mDoCallbackGattServer.onGattServerDescriptorWriteRequest(address, transId, offset, isPrep, needRsp, srvcType, srvcId, charId, descrId, value);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerExecuteWrite(String address, int transId, boolean execWrite) throws RemoteException {
            Log.d(BtService.TAG, "onGattServerExecuteWrite()");
            BtService.this.mDoCallbackGattServer.onGattServerExecuteWrite(address, transId, execWrite);
        }
    };
    private GocCallbackHfp mCallbackHfp = new GocCallbackHfp.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass3 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onHfpServiceReady()");
            BtService.this.mDoCallbackHfp.onHfpServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onHfpStateChanged() " + address + " state: " + prevState + "->" + newState);
            BtService.this.mDoCallbackHfp.onHfpStateChanged(address, prevState, newState);
            BtService.this.mDoCallbackBluetooth.onHfpStateChanged(address, prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpCallingTimeChanged(String var1) throws RemoteException {
            Log.d(BtService.TAG, "onHfpCallingTimeChanged() " + var1);
            BtService.this.mDoCallbackHfp.onHfpCallingTimeChanged(var1);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpAudioStateChanged(String address, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onHfpAudioStateChanged() " + address + " state: " + prevState + "->" + newState);
            BtService.this.mDoCallbackHfp.onHfpAudioStateChanged(address, prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpVoiceDial(String address, boolean isVoiceDialOn) throws RemoteException {
            Log.d(BtService.TAG, "onHfpVoiceDial() " + address + " isVoiceDialOn: " + isVoiceDialOn);
            BtService.this.mDoCallbackHfp.onHfpVoiceDial(address, isVoiceDialOn);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpErrorResponse(String address, int code) throws RemoteException {
            Log.d(BtService.TAG, "onHfpErrorResponse() " + address + " code: " + code);
            BtService.this.mDoCallbackHfp.onHfpErrorResponse(address, code);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) throws RemoteException {
            Log.d(BtService.TAG, "onHfpRemoteTelecomService() " + address + " isTelecomServiceOn: " + isTelecomServiceOn);
            BtService.this.mDoCallbackHfp.onHfpRemoteTelecomService(address, isTelecomServiceOn);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpRemoteRoamingStatus(String address, boolean isRoamingOn) throws RemoteException {
            Log.d(BtService.TAG, "onHfpRemoteRoamingStatus() " + address + " isRoamingOn: " + isRoamingOn);
            BtService.this.mDoCallbackHfp.onHfpRemoteRoamingStatus(address, isRoamingOn);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) throws RemoteException {
            Log.d(BtService.TAG, "onHfpRemoteBatteryIndicator() " + address + " value: " + currentValue + " (" + minValue + "-" + maxValue + ")");
            BtService.this.mDoCallbackHfp.onHfpRemoteBatteryIndicator(address, currentValue, maxValue, minValue);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) throws RemoteException {
            Log.d(BtService.TAG, "onHfpRemoteSignalStrength() " + address + " strength: " + currentStrength + " (" + minStrength + "-" + maxStrength + ")");
            BtService.this.mDoCallbackHfp.onHfpRemoteSignalStrength(address, currentStrength, maxStrength, minStrength);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHfp
        public void onHfpCallChanged(String address, GocHfpClientCall call) throws RemoteException {
            Log.d(BtService.TAG, "onHfpCallChanged() " + address);
            BtService.this.mDoCallbackHfp.onHfpCallChanged(address, call);
        }
    };
    private GocCallbackHid mCallbackHid = new GocCallbackHid.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass7 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHid
        public void onHidServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onHidServiceReady()");
            BtService.this.mDoCallbackHid.onHidServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHid
        public void onHidStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
            Log.d(BtService.TAG, "onHidStateChanged() " + address + " state: " + prevState + "->" + newState + " ,reason:" + reason);
            BtService.this.mDoCallbackHid.onHidStateChanged(address, prevState, newState, reason);
        }
    };
    private GocCallbackMap mCallbackMap = new GocCallbackMap.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass10 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onMapServiceReady()");
            BtService.this.mDoCallbackMap.onMapServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
            Log.d(BtService.TAG, "onMapStateChanged()");
            BtService.this.mDoCallbackMap.onMapStateChanged(address, prevState, newState, reason);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapDownloadedMessage(String address, String handle, String senderName, String senderNumber, String recipientNumber, String date, int type, int folder, boolean isReadStatus, String subject, String message) throws RemoteException {
            Log.d(BtService.TAG, "retMapDownloadedMessage()");
            BtService.this.mDoCallbackMap.retMapDownloadedMessage(address, handle, senderName, senderNumber, date, recipientNumber, type, folder, isReadStatus, subject, message);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapNewMessageReceivedEvent(String address, String handle, String sender, String message) throws RemoteException {
            Log.d(BtService.TAG, "onMapNewMessageReceivedEvent()");
            BtService.this.mDoCallbackMap.onMapNewMessageReceivedEvent(address, handle, sender, message);
            BtService.this.mCommandMap.reqMapDownloadSingleMessage(address, 0, handle, 0);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapDownloadNotify(String address, int folder, int totalMessages, int currentMessages) throws RemoteException {
            Log.d(BtService.TAG, "onMapDownloadNotify()");
            BtService.this.mDoCallbackMap.onMapDownloadNotify(address, folder, totalMessages, currentMessages);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapDatabaseAvailable() throws RemoteException {
            Log.d(BtService.TAG, "retMapDatabaseAvailable()");
            BtService.this.mDoCallbackMap.retMapDatabaseAvailable();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
            Log.d(BtService.TAG, "retMapDeleteDatabaseByAddressCompleted()");
            BtService.this.mDoCallbackMap.retMapDeleteDatabaseByAddressCompleted(address, isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
            Log.d(BtService.TAG, "retMapCleanDatabaseCompleted()");
            BtService.this.mDoCallbackMap.retMapCleanDatabaseCompleted(isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapSendMessageCompleted(String address, String target, int state) throws RemoteException {
            Log.d(BtService.TAG, "retMapSendMessageCompleted()");
            BtService.this.mDoCallbackMap.retMapSendMessageCompleted(address, target, state);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapDeleteMessageCompleted(String address, String handle, int reason) throws RemoteException {
            Log.d(BtService.TAG, "retMapDeleteMessageCompleted()");
            BtService.this.mDoCallbackMap.retMapDeleteMessageCompleted(address, handle, reason);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void retMapChangeReadStatusCompleted(String address, String handle, int reason) throws RemoteException {
            Log.d(BtService.TAG, "retMapReadStatusCompleted()");
            BtService.this.mDoCallbackMap.retMapChangeReadStatusCompleted(address, handle, reason);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapMemoryAvailableEvent(String address, int structure, boolean available) {
            Log.d(BtService.TAG, "onJniMapMemoryAvailableEvent()");
            BtService.this.mDoCallbackMap.onMapMemoryAvailableEvent(address, structure, available);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapMessageSendingStatusEvent(String address, String handle, boolean isSuccess) {
            Log.d(BtService.TAG, "onJniMapMessageSendingStatusEvent()");
            BtService.this.mDoCallbackMap.onMapMessageSendingStatusEvent(address, handle, isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapMessageDeliverStatusEvent(String address, String handle, boolean isSuccess) {
            Log.d(BtService.TAG, "onJniMapMessageDeliverStatusEvent()");
            BtService.this.mDoCallbackMap.onMapMessageDeliverStatusEvent(address, handle, isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapMessageShiftedEvent(String address, String handle, int type, int newFolder, int oldFolder) {
            Log.d(BtService.TAG, "onJniMapMessageShiftedEvent()");
            BtService.this.mDoCallbackMap.onMapMessageShiftedEvent(address, handle, type, newFolder, oldFolder);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackMap
        public void onMapMessageDeletedEvent(String address, String handle, int type, int folder) {
            Log.d(BtService.TAG, "onJniMapMessageDeletedEvent()");
            BtService.this.mDoCallbackMap.onMapMessageDeletedEvent(address, handle, type, folder);
        }
    };
    private GocCallbackOpp mCallbackOpp = new GocCallbackOpp.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass11 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackOpp
        public void onOppServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onOppServiceReady()");
            BtService.this.mDoCallbackOpp.onOppServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackOpp
        public void onOppStateChanged(String address, int preState, int currentState, int reason) throws RemoteException {
            Log.d(BtService.TAG, "onOppStateChanged()");
            BtService.this.mDoCallbackOpp.onOppStateChanged(address, preState, currentState, reason);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackOpp
        public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) throws RemoteException {
            Log.d(BtService.TAG, "onOppReceiveFileInfo()");
            BtService.this.mDoCallbackOpp.onOppReceiveFileInfo(fileName, fileSize, deviceName, savePath);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackOpp
        public void onOppReceiveProgress(int receivedOffset) throws RemoteException {
            Log.d(BtService.TAG, "onOppReceiveProgress()");
            BtService.this.mDoCallbackOpp.onOppReceiveProgress(receivedOffset);
        }
    };
    private GocCallbackPbap mCallbackPbap = new GocCallbackPbap.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass6 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void onPbapServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onPbapServiceReady()");
            BtService.this.mDoCallbackPbap.onPbapServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void onPbapStateChanged(String address, int prevState, int newState, int reason, int counts) throws RemoteException {
            Log.d(BtService.TAG, "onPbapStateChanged() " + address + " state: " + prevState + "->" + newState + " reason: " + reason + " counts: " + counts);
            if (newState == 110) {
                Log.e(BtService.TAG, "Piggy Check testCount: " + BtService.this.testCount);
                BtService.this.testCount = 0;
            }
            BtService.this.mDoCallbackPbap.onPbapStateChanged(address, prevState, newState, reason, counts);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDownloadedContact(GocPbapContact contact) throws RemoteException {
            Log.d(BtService.TAG, "retPbapDownloadedContact()");
            BtService.access$3208(BtService.this);
            BtService.this.mDoCallbackPbap.retPbapDownloadedContact(contact);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) throws RemoteException {
            Log.d(BtService.TAG, "retPbapDownloadedCallLog() " + address + " lastName: " + lastName + " (" + type + ")");
            BtService.this.mDoCallbackPbap.retPbapDownloadedCallLog(address, firstName, middleName, lastName, number, type, timestamp);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void onPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) throws RemoteException {
            Log.d(BtService.TAG, "onPbapDownloadNotify() " + address + " storage: " + storage + " downloaded: " + downloadedContacts + "/" + totalContacts);
            BtService.this.mDoCallbackPbap.onPbapDownloadNotify(address, storage, totalContacts, downloadedContacts);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
            Log.d(BtService.TAG, "retPbapDatabaseQueryNameByNumber() " + address + " target: " + target + " name: " + name + " isSuccess: " + isSuccess);
            BtService.this.mDoCallbackPbap.retPbapDatabaseQueryNameByNumber(address, target, name, isSuccess);
            BtService.this.mDoCallbackHfp.retPbapDatabaseQueryNameByNumber(address, target, name, isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) throws RemoteException {
            Log.d(BtService.TAG, "retPbapDatabaseQueryNameByPartialNumber() " + address + " target: " + target + " isSuccess: " + isSuccess);
            BtService.this.mDoCallbackPbap.retPbapDatabaseQueryNameByPartialNumber(address, target, names, numbers, isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDatabaseAvailable(String address) throws RemoteException {
            Log.d(BtService.TAG, "retPbapDatabaseAvailable() " + address);
            BtService.this.mDoCallbackPbap.retPbapDatabaseAvailable(address);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
            Log.d(BtService.TAG, "retPbapDeleteDatabaseByAddressCompleted() " + address + " isSuccess: " + isSuccess);
            BtService.this.mDoCallbackPbap.retPbapDeleteDatabaseByAddressCompleted(address, isSuccess);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
            Log.d(BtService.TAG, "retPbapCleanDatabaseCompleted() isSuccess: " + isSuccess);
            BtService.this.mDoCallbackPbap.retPbapCleanDatabaseCompleted(isSuccess);
        }
    };
    private GocCallbackSpp mCallbackSpp = new GocCallbackSpp.Stub() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass9 */

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppServiceReady() throws RemoteException {
            Log.d(BtService.TAG, "onSppServiceReady()");
            BtService.this.mDoCallbackSpp.onSppServiceReady();
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppStateChanged(String address, String deviceName, int prevState, int newState) throws RemoteException {
            Log.d(BtService.TAG, "onSppStateChanged() " + address + " name: " + deviceName + " state: " + prevState + " -> " + newState);
            BtService.this.mDoCallbackSpp.onSppStateChanged(address, deviceName, prevState, newState);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppErrorResponse(String address, int errorCode) throws RemoteException {
            Log.d(BtService.TAG, "onSppErrorResponse() " + address + " error: " + errorCode);
            BtService.this.mDoCallbackSpp.onSppErrorResponse(address, errorCode);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void retSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) throws RemoteException {
            Log.d(BtService.TAG, "retSppConnectedDeviceAddressList() total: " + totalNum);
            BtService.this.mDoCallbackSpp.retSppConnectedDeviceAddressList(totalNum, addressList, nameList);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppDataReceived(String address, byte[] receivedData) throws RemoteException {
            Log.d(BtService.TAG, "onSppDataReceived() " + address);
            BtService.this.mDoCallbackSpp.onSppDataReceived(address, receivedData);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppSendData(String address, int length) throws RemoteException {
            Log.d(BtService.TAG, "onSppsentData() " + address);
            BtService.this.mDoCallbackSpp.onSppSendData(address, length);
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppAppleIapAuthenticationRequest(String address) throws RemoteException {
            Log.d(BtService.TAG, "onSppAppleIapAuthenticationRequest() " + address);
            byte[] data = {85, 4, 0, 56, 0, 1, -61};
            if (BtService.this.mCommandSpp != null) {
                BtService.this.mCommandSpp.reqSppSendData(address, data);
            }
        }
    };
    private GocCommandA2dp mCommandA2dp;
    private GocCommandAvrcp mCommandAvrcp;
    private GocCommandBluetooth mCommandBluetooth;
    private GocCommandGattServer mCommandGattServer;
    private GocCommandHfp mCommandHfp;
    private GocCommandHid mCommandHid;
    private GocCommandMap mCommandMap;
    private GocCommandOpp mCommandOpp;
    private GocCommandPbap mCommandPbap;
    private GocCommandSpp mCommandSpp;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.service.BtService.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(BtService.TAG, "ready onServiceConnected");
            Log.d(BtService.TAG, "Piggy Check className : " + className);
            Log.e(BtService.TAG, "IBinder service: " + service.hashCode());
            try {
                Log.d(BtService.TAG, "Piggy Check service : " + service.getInterfaceDescriptor());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_HFP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceHfp)");
                BtService.this.mCommandHfp = GocCommandHfp.Stub.asInterface(service);
                if (BtService.this.mCommandHfp == null) {
                    Log.e(BtService.TAG, "mCommandHfp is null!!");
                    return;
                }
                BtService btService = BtService.this;
                btService.dumpClassMethod(btService.mCommandHfp.getClass());
                try {
                    BtService.this.mCommandHfp.registerHfpCallback(BtService.this.mCallbackHfp);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_A2DP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceA2dp)");
                BtService.this.mCommandA2dp = GocCommandA2dp.Stub.asInterface(service);
                if (BtService.this.mCommandA2dp == null) {
                    Log.e(BtService.TAG, "mCommandA2dp is null !!");
                    return;
                }
                BtService btService2 = BtService.this;
                btService2.dumpClassMethod(btService2.mCommandA2dp.getClass());
                try {
                    BtService.this.mCommandA2dp.registerA2dpCallback(BtService.this.mCallbackA2dp);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_AVRCP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceAvrcp)");
                BtService.this.mCommandAvrcp = GocCommandAvrcp.Stub.asInterface(service);
                if (BtService.this.mCommandAvrcp == null) {
                    Log.e(BtService.TAG, "mCommandAvrcp is null !!");
                    return;
                }
                BtService btService3 = BtService.this;
                btService3.dumpClassMethod(btService3.mCommandAvrcp.getClass());
                try {
                    BtService.this.mCommandAvrcp.registerAvrcpCallback(BtService.this.mCallbackAvrcp);
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_PBAP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServicePbap)");
                BtService.this.mCommandPbap = GocCommandPbap.Stub.asInterface(service);
                if (BtService.this.mCommandPbap == null) {
                    Log.e(BtService.TAG, "mCommandPbap is null !!");
                    return;
                }
                BtService btService4 = BtService.this;
                btService4.dumpClassMethod(btService4.mCommandPbap.getClass());
                try {
                    BtService.this.mCommandPbap.registerPbapCallback(BtService.this.mCallbackPbap);
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_HID))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceHid)");
                BtService.this.mCommandHid = GocCommandHid.Stub.asInterface(service);
                if (BtService.this.mCommandHid == null) {
                    Log.e(BtService.TAG, "mCommandHid is null !!");
                    return;
                }
                BtService btService5 = BtService.this;
                btService5.dumpClassMethod(btService5.mCommandHid.getClass());
                try {
                    BtService.this.mCommandHid.registerHidCallback(BtService.this.mCallbackHid);
                } catch (RemoteException e5) {
                    e5.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_BLUETOOTH))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceBluetooth)");
                BtService.this.mCommandBluetooth = GocCommandBluetooth.Stub.asInterface(service);
                if (BtService.this.mCommandBluetooth == null) {
                    Log.e(BtService.TAG, "mCommandBluetooth is null !!");
                    return;
                }
                BtService btService6 = BtService.this;
                btService6.dumpClassMethod(btService6.mCommandBluetooth.getClass());
                try {
                    BtService.this.mCommandBluetooth.registerBtCallback(BtService.this.mCallbackBluetooth);
                } catch (RemoteException e6) {
                    e6.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_SPP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceSpp)");
                BtService.this.mCommandSpp = GocCommandSpp.Stub.asInterface(service);
                if (BtService.this.mCommandSpp == null) {
                    Log.e(BtService.TAG, "mCommandSpp is null !!");
                    return;
                }
                BtService btService7 = BtService.this;
                btService7.dumpClassMethod(btService7.mCommandSpp.getClass());
                try {
                    BtService.this.mCommandSpp.registerSppCallback(BtService.this.mCallbackSpp);
                } catch (RemoteException e7) {
                    e7.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_MAP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceMap)");
                BtService.this.mCommandMap = GocCommandMap.Stub.asInterface(service);
                if (BtService.this.mCommandMap == null) {
                    Log.e(BtService.TAG, "mCommandMap is null !!");
                    return;
                }
                BtService btService8 = BtService.this;
                btService8.dumpClassMethod(btService8.mCommandMap.getClass());
                try {
                    BtService.this.mCommandMap.registerMapCallback(BtService.this.mCallbackMap);
                } catch (RemoteException e8) {
                    e8.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_OPP))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceOpp)");
                BtService.this.mCommandOpp = GocCommandOpp.Stub.asInterface(service);
                if (BtService.this.mCommandOpp == null) {
                    Log.e(BtService.TAG, "mCommandOpp is null !!");
                    return;
                }
                BtService btService9 = BtService.this;
                btService9.dumpClassMethod(btService9.mCommandOpp.getClass());
                try {
                    BtService.this.mCommandOpp.registerOppCallback(BtService.this.mCallbackOpp);
                } catch (RemoteException e9) {
                    e9.printStackTrace();
                }
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_GATT_SERVER))) {
                Log.e(BtService.TAG, "ComponentName(com.goodocom.bttek.bt.service.GocServiceGattServer)");
                BtService.this.mCommandGattServer = GocCommandGattServer.Stub.asInterface(service);
                if (BtService.this.mCommandGattServer == null) {
                    Log.e(BtService.TAG, "mCommandGattServer is null !!");
                    return;
                }
                BtService btService10 = BtService.this;
                btService10.dumpClassMethod(btService10.mCommandGattServer.getClass());
                try {
                    BtService.this.mCommandGattServer.registerGattServerCallback(BtService.this.mCallbackGattServer);
                } catch (RemoteException e10) {
                    e10.printStackTrace();
                }
            }
            Log.e(BtService.TAG, "end onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(BtService.TAG, "ready onServiceDisconnected: " + className);
            if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_HFP))) {
                BtService.this.mCommandHfp = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_A2DP))) {
                BtService.this.mCommandA2dp = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_AVRCP))) {
                BtService.this.mCommandAvrcp = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_PBAP))) {
                BtService.this.mCommandPbap = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_HID))) {
                BtService.this.mCommandHid = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_BLUETOOTH))) {
                BtService.this.mCommandBluetooth = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_SPP))) {
                BtService.this.mCommandSpp = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_MAP))) {
                BtService.this.mCommandMap = null;
            } else if (className.equals(new ComponentName("com.goodocom.bttek", NfDef.CLASS_SERVICE_OPP))) {
                BtService.this.mCommandOpp = null;
            }
            Log.e(BtService.TAG, "end onServiceDisconnected");
        }
    };
    private DoCallbackA2dp mDoCallbackA2dp;
    private DoCallbackAvrcp mDoCallbackAvrcp;
    private DoCallbackBluetooth mDoCallbackBluetooth;
    private DoCallbackGattServer mDoCallbackGattServer;
    private DoCallbackHfp mDoCallbackHfp;
    private DoCallbackHid mDoCallbackHid;
    private DoCallbackMap mDoCallbackMap;
    private DoCallbackOpp mDoCallbackOpp;
    private DoCallbackPbap mDoCallbackPbap;
    private DoCallbackSpp mDoCallbackSpp;
    private String mVersionName = "API20210208";
    private String targetAddress = NfDef.DEFAULT_ADDRESS;
    private int testCount = 0;
    String title = BuildConfig.FLAVOR;

    static /* synthetic */ int access$3208(BtService x0) {
        int i = x0.testCount;
        x0.testCount = i + 1;
        return i;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "BtService +++ onCreate +++ ");
        this.mDoCallbackA2dp = new DoCallbackA2dp();
        this.mDoCallbackAvrcp = new DoCallbackAvrcp();
        this.mDoCallbackHfp = new DoCallbackHfp();
        this.mDoCallbackPbap = new DoCallbackPbap();
        this.mDoCallbackHid = new DoCallbackHid();
        this.mDoCallbackBluetooth = new DoCallbackBluetooth();
        this.mDoCallbackSpp = new DoCallbackSpp();
        this.mDoCallbackMap = new DoCallbackMap();
        this.mDoCallbackOpp = new DoCallbackOpp();
        this.mDoCallbackGattServer = new DoCallbackGattServer();
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        Log.d(TAG, "bindA2dpService");
        intent.setAction(NfDef.CLASS_SERVICE_A2DP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindAvrcpService");
        intent.setAction(NfDef.CLASS_SERVICE_AVRCP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindHfpService");
        intent.setAction(NfDef.CLASS_SERVICE_HFP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindPbapService");
        intent.setAction(NfDef.CLASS_SERVICE_PBAP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindHidService");
        intent.setAction(NfDef.CLASS_SERVICE_HID);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindBluetoothService");
        intent.setAction(NfDef.CLASS_SERVICE_BLUETOOTH);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindSppService");
        intent.setAction(NfDef.CLASS_SERVICE_SPP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindMapService");
        intent.setAction(NfDef.CLASS_SERVICE_MAP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindOppService");
        intent.setAction(NfDef.CLASS_SERVICE_OPP);
        bindService(intent, this.mConnection, 1);
        Log.d(TAG, "bindGattService");
        intent.setAction(NfDef.CLASS_SERVICE_GATT_SERVER);
        bindService(intent, this.mConnection, 1);
        try {
            getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception when getting package information !!!");
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand Received start id " + startId + " : " + intent);
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        try {
            if (this.mCommandHfp != null) {
                this.mCommandHfp.unregisterHfpCallback(this.mCallbackHfp);
            }
            if (this.mCommandA2dp != null) {
                this.mCommandA2dp.unregisterA2dpCallback(this.mCallbackA2dp);
            }
            if (this.mCommandAvrcp != null) {
                this.mCommandAvrcp.unregisterAvrcpCallback(this.mCallbackAvrcp);
            }
            if (this.mCommandPbap != null) {
                this.mCommandPbap.unregisterPbapCallback(this.mCallbackPbap);
            }
            if (this.mCommandHid != null) {
                this.mCommandHid.unregisterHidCallback(this.mCallbackHid);
            }
            if (this.mCommandBluetooth != null) {
                this.mCommandBluetooth.unregisterBtCallback(this.mCallbackBluetooth);
            }
            if (this.mCommandSpp != null) {
                this.mCommandSpp.unregisterSppCallback(this.mCallbackSpp);
            }
            if (this.mCommandMap != null) {
                this.mCommandMap.unregisterMapCallback(this.mCallbackMap);
            }
            if (this.mCommandOpp != null) {
                this.mCommandOpp.unregisterOppCallback(this.mCallbackOpp);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "start unbind service");
        unbindService(this.mConnection);
        Log.e(TAG, "end unbind service");
        DoCallbackHfp doCallbackHfp = this.mDoCallbackHfp;
        if (doCallbackHfp != null) {
            doCallbackHfp.kill();
        }
        DoCallbackA2dp doCallbackA2dp = this.mDoCallbackA2dp;
        if (doCallbackA2dp != null) {
            doCallbackA2dp.kill();
        }
        DoCallbackAvrcp doCallbackAvrcp = this.mDoCallbackAvrcp;
        if (doCallbackAvrcp != null) {
            doCallbackAvrcp.kill();
        }
        DoCallbackPbap doCallbackPbap = this.mDoCallbackPbap;
        if (doCallbackPbap != null) {
            doCallbackPbap.kill();
        }
        DoCallbackHid doCallbackHid = this.mDoCallbackHid;
        if (doCallbackHid != null) {
            doCallbackHid.kill();
        }
        DoCallbackBluetooth doCallbackBluetooth = this.mDoCallbackBluetooth;
        if (doCallbackBluetooth != null) {
            doCallbackBluetooth.kill();
        }
        DoCallbackMap doCallbackMap = this.mDoCallbackMap;
        if (doCallbackMap != null) {
            doCallbackMap.kill();
        }
        DoCallbackOpp doCallbackOpp = this.mDoCallbackOpp;
        if (doCallbackOpp != null) {
            doCallbackOpp.kill();
        }
        DoCallbackGattServer doCallbackGattServer = this.mDoCallbackGattServer;
        if (doCallbackGattServer != null) {
            doCallbackGattServer.kill();
        }
        Log.d(TAG, " --- onDestroy --- ");
        if (CommandPbapImp.mdb != null && CommandPbapImp.mdb.isOpen()) {
            CommandPbapImp.mdb.close();
            CommandPbapImp.mdb = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() Intent : " + intent);
        return this.mBinder;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void dumpClassMethod(Class c) {
        Method[] declaredMethods = c.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Log.e(TAG, "Method name: " + method.getName());
        }
    }
}
