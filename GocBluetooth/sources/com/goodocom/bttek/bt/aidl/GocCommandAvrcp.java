package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackAvrcp;

public interface GocCommandAvrcp extends IInterface {
    String getAvrcpConnectedAddress() throws RemoteException;

    int getAvrcpConnectionState() throws RemoteException;

    int getPlayMode() throws RemoteException;

    boolean isAvrcp13Supported(String str) throws RemoteException;

    boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException;

    boolean isAvrcp14Supported(String str) throws RemoteException;

    boolean isAvrcpConnected() throws RemoteException;

    boolean isAvrcpServiceReady() throws RemoteException;

    boolean registerAvrcpCallback(GocCallbackAvrcp gocCallbackAvrcp) throws RemoteException;

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

    boolean reqAvrcpQueryVersion(String str) throws RemoteException;

    boolean reqAvrcpRegisterEventWatcher(byte b, long j) throws RemoteException;

    boolean reqAvrcpStartFastForward() throws RemoteException;

    boolean reqAvrcpStartRewind() throws RemoteException;

    boolean reqAvrcpStop() throws RemoteException;

    boolean reqAvrcpStopFastForward() throws RemoteException;

    boolean reqAvrcpStopRewind() throws RemoteException;

    boolean reqAvrcpUnregisterEventWatcher(byte b) throws RemoteException;

    boolean reqAvrcpVolumeDown() throws RemoteException;

    boolean reqAvrcpVolumeUp() throws RemoteException;

    boolean setPlayMode(int i) throws RemoteException;

    boolean unregisterAvrcpCallback(GocCallbackAvrcp gocCallbackAvrcp) throws RemoteException;

    public static class Default implements GocCommandAvrcp {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean isAvrcpServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean registerAvrcpCallback(GocCallbackAvrcp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean unregisterAvrcpCallback(GocCallbackAvrcp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public int getAvrcpConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean isAvrcpConnected() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public String getAvrcpConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean isAvrcp13Supported(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean isAvrcp14Supported(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpPlay() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpStop() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpPause() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpForward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpBackward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpVolumeUp() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpVolumeDown() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpStartFastForward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpStopFastForward() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpStartRewind() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpStopRewind() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13NextGroup() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp13PreviousGroup() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14Search(String text) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean reqAvrcpQueryVersion(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public int getPlayMode() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
        public boolean setPlayMode(int mode) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandAvrcp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandAvrcp";
        static final int TRANSACTION_getAvrcpConnectedAddress = 6;
        static final int TRANSACTION_getAvrcpConnectionState = 4;
        static final int TRANSACTION_getPlayMode = 44;
        static final int TRANSACTION_isAvrcp13Supported = 9;
        static final int TRANSACTION_isAvrcp14BrowsingChannelEstablished = 33;
        static final int TRANSACTION_isAvrcp14Supported = 10;
        static final int TRANSACTION_isAvrcpConnected = 5;
        static final int TRANSACTION_isAvrcpServiceReady = 1;
        static final int TRANSACTION_registerAvrcpCallback = 2;
        static final int TRANSACTION_reqAvrcp13GetCapabilitiesSupportEvent = 22;
        static final int TRANSACTION_reqAvrcp13GetElementAttributesPlaying = 27;
        static final int TRANSACTION_reqAvrcp13GetPlayStatus = 28;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingAttributesList = 23;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingCurrentValues = 25;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingValuesList = 24;
        static final int TRANSACTION_reqAvrcp13NextGroup = 31;
        static final int TRANSACTION_reqAvrcp13PreviousGroup = 32;
        static final int TRANSACTION_reqAvrcp13SetPlayerSettingValue = 26;
        static final int TRANSACTION_reqAvrcp14AddToNowPlaying = 41;
        static final int TRANSACTION_reqAvrcp14ChangePath = 37;
        static final int TRANSACTION_reqAvrcp14GetFolderItems = 36;
        static final int TRANSACTION_reqAvrcp14GetItemAttributes = 38;
        static final int TRANSACTION_reqAvrcp14PlaySelectedItem = 39;
        static final int TRANSACTION_reqAvrcp14Search = 40;
        static final int TRANSACTION_reqAvrcp14SetAbsoluteVolume = 42;
        static final int TRANSACTION_reqAvrcp14SetAddressedPlayer = 34;
        static final int TRANSACTION_reqAvrcp14SetBrowsedPlayer = 35;
        static final int TRANSACTION_reqAvrcpBackward = 15;
        static final int TRANSACTION_reqAvrcpConnect = 7;
        static final int TRANSACTION_reqAvrcpDisconnect = 8;
        static final int TRANSACTION_reqAvrcpForward = 14;
        static final int TRANSACTION_reqAvrcpPause = 13;
        static final int TRANSACTION_reqAvrcpPlay = 11;
        static final int TRANSACTION_reqAvrcpQueryVersion = 43;
        static final int TRANSACTION_reqAvrcpRegisterEventWatcher = 29;
        static final int TRANSACTION_reqAvrcpStartFastForward = 18;
        static final int TRANSACTION_reqAvrcpStartRewind = 20;
        static final int TRANSACTION_reqAvrcpStop = 12;
        static final int TRANSACTION_reqAvrcpStopFastForward = 19;
        static final int TRANSACTION_reqAvrcpStopRewind = 21;
        static final int TRANSACTION_reqAvrcpUnregisterEventWatcher = 30;
        static final int TRANSACTION_reqAvrcpVolumeDown = 17;
        static final int TRANSACTION_reqAvrcpVolumeUp = 16;
        static final int TRANSACTION_setPlayMode = 45;
        static final int TRANSACTION_unregisterAvrcpCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandAvrcp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandAvrcp)) {
                return new Proxy(obj);
            }
            return (GocCommandAvrcp) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcpServiceReady = isAvrcpServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isAvrcpServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerAvrcpCallback = registerAvrcpCallback(GocCallbackAvrcp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerAvrcpCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterAvrcpCallback = unregisterAvrcpCallback(GocCallbackAvrcp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterAvrcpCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        int _result = getAvrcpConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcpConnected = isAvrcpConnected();
                        reply.writeNoException();
                        reply.writeInt(isAvrcpConnected ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _result2 = getAvrcpConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result2);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpConnect = reqAvrcpConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpConnect ? 1 : 0);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpDisconnect = reqAvrcpDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpDisconnect ? 1 : 0);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcp13Supported = isAvrcp13Supported(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isAvrcp13Supported ? 1 : 0);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcp14Supported = isAvrcp14Supported(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isAvrcp14Supported ? 1 : 0);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpPlay = reqAvrcpPlay();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpPlay ? 1 : 0);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStop = reqAvrcpStop();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStop ? 1 : 0);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpPause = reqAvrcpPause();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpPause ? 1 : 0);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpForward = reqAvrcpForward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpForward ? 1 : 0);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpBackward = reqAvrcpBackward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpBackward ? 1 : 0);
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpVolumeUp = reqAvrcpVolumeUp();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpVolumeUp ? 1 : 0);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpVolumeDown = reqAvrcpVolumeDown();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpVolumeDown ? 1 : 0);
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStartFastForward = reqAvrcpStartFastForward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStartFastForward ? 1 : 0);
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStopFastForward = reqAvrcpStopFastForward();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStopFastForward ? 1 : 0);
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStartRewind = reqAvrcpStartRewind();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStartRewind ? 1 : 0);
                        return true;
                    case 21:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpStopRewind = reqAvrcpStopRewind();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpStopRewind ? 1 : 0);
                        return true;
                    case 22:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetCapabilitiesSupportEvent = reqAvrcp13GetCapabilitiesSupportEvent();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetCapabilitiesSupportEvent ? 1 : 0);
                        return true;
                    case 23:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayerSettingAttributesList = reqAvrcp13GetPlayerSettingAttributesList();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayerSettingAttributesList ? 1 : 0);
                        return true;
                    case 24:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayerSettingValuesList = reqAvrcp13GetPlayerSettingValuesList(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayerSettingValuesList ? 1 : 0);
                        return true;
                    case 25:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayerSettingCurrentValues = reqAvrcp13GetPlayerSettingCurrentValues();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayerSettingCurrentValues ? 1 : 0);
                        return true;
                    case 26:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13SetPlayerSettingValue = reqAvrcp13SetPlayerSettingValue(data.readByte(), data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13SetPlayerSettingValue ? 1 : 0);
                        return true;
                    case 27:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetElementAttributesPlaying = reqAvrcp13GetElementAttributesPlaying();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetElementAttributesPlaying ? 1 : 0);
                        return true;
                    case 28:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13GetPlayStatus = reqAvrcp13GetPlayStatus();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13GetPlayStatus ? 1 : 0);
                        return true;
                    case 29:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpRegisterEventWatcher = reqAvrcpRegisterEventWatcher(data.readByte(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpRegisterEventWatcher ? 1 : 0);
                        return true;
                    case 30:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpUnregisterEventWatcher = reqAvrcpUnregisterEventWatcher(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpUnregisterEventWatcher ? 1 : 0);
                        return true;
                    case 31:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13NextGroup = reqAvrcp13NextGroup();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13NextGroup ? 1 : 0);
                        return true;
                    case 32:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp13PreviousGroup = reqAvrcp13PreviousGroup();
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp13PreviousGroup ? 1 : 0);
                        return true;
                    case 33:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isAvrcp14BrowsingChannelEstablished = isAvrcp14BrowsingChannelEstablished();
                        reply.writeNoException();
                        reply.writeInt(isAvrcp14BrowsingChannelEstablished ? 1 : 0);
                        return true;
                    case 34:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14SetAddressedPlayer = reqAvrcp14SetAddressedPlayer(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14SetAddressedPlayer ? 1 : 0);
                        return true;
                    case 35:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14SetBrowsedPlayer = reqAvrcp14SetBrowsedPlayer(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14SetBrowsedPlayer ? 1 : 0);
                        return true;
                    case 36:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14GetFolderItems = reqAvrcp14GetFolderItems(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14GetFolderItems ? 1 : 0);
                        return true;
                    case 37:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14ChangePath = reqAvrcp14ChangePath(data.readInt(), data.readLong(), data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14ChangePath ? 1 : 0);
                        return true;
                    case 38:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14GetItemAttributes = reqAvrcp14GetItemAttributes(data.readByte(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14GetItemAttributes ? 1 : 0);
                        return true;
                    case 39:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14PlaySelectedItem = reqAvrcp14PlaySelectedItem(data.readByte(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14PlaySelectedItem ? 1 : 0);
                        return true;
                    case 40:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14Search = reqAvrcp14Search(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14Search ? 1 : 0);
                        return true;
                    case 41:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14AddToNowPlaying = reqAvrcp14AddToNowPlaying(data.readByte(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14AddToNowPlaying ? 1 : 0);
                        return true;
                    case 42:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcp14SetAbsoluteVolume = reqAvrcp14SetAbsoluteVolume(data.readByte());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcp14SetAbsoluteVolume ? 1 : 0);
                        return true;
                    case 43:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqAvrcpQueryVersion = reqAvrcpQueryVersion(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqAvrcpQueryVersion ? 1 : 0);
                        return true;
                    case 44:
                        data.enforceInterface(DESCRIPTOR);
                        int _result3 = getPlayMode();
                        reply.writeNoException();
                        reply.writeInt(_result3);
                        return true;
                    case 45:
                        data.enforceInterface(DESCRIPTOR);
                        boolean playMode = setPlayMode(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(playMode ? 1 : 0);
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
        public static class Proxy implements GocCommandAvrcp {
            public static GocCommandAvrcp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean isAvrcpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean registerAvrcpCallback(GocCallbackAvrcp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean unregisterAvrcpCallback(GocCallbackAvrcp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public int getAvrcpConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean isAvrcpConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvrcpConnected();
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public String getAvrcpConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean isAvrcp13Supported(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean isAvrcp14Supported(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpPlay() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpStop() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpBackward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpVolumeUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpVolumeDown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpStartFastForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpStopFastForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpStartRewind() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpStopRewind() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13GetPlayerSettingValuesList(byte attributeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(attributeId);
                    boolean _result = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13SetPlayerSettingValue(byte attributeId, byte valueId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(attributeId);
                    _data.writeByte(valueId);
                    boolean _result = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpRegisterEventWatcher(byte eventId, long interval) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(eventId);
                    _data.writeLong(interval);
                    boolean _result = false;
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpUnregisterEventWatcher(byte eventId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(eventId);
                    boolean _result = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13NextGroup() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp13PreviousGroup() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14SetAddressedPlayer(int playerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(playerId);
                    boolean _result = false;
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14SetBrowsedPlayer(int playerId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(playerId);
                    boolean _result = false;
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14GetFolderItems(byte scopeId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    boolean _result = false;
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14ChangePath(int uidCounter, long uid, byte direction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    _data.writeByte(direction);
                    boolean _result = false;
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14GetItemAttributes(byte scopeId, int uidCounter, long uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    boolean _result = false;
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14PlaySelectedItem(byte scopeId, int uidCounter, long uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    boolean _result = false;
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14Search(String text) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(text);
                    boolean _result = false;
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14AddToNowPlaying(byte scopeId, int uidCounter, long uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(scopeId);
                    _data.writeInt(uidCounter);
                    _data.writeLong(uid);
                    boolean _result = false;
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcp14SetAbsoluteVolume(byte volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(volume);
                    boolean _result = false;
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean reqAvrcpQueryVersion(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqAvrcpQueryVersion(address);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public int getPlayMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandAvrcp
            public boolean setPlayMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    boolean _result = false;
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
        }

        public static boolean setDefaultImpl(GocCommandAvrcp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandAvrcp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
