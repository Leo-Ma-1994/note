package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface UiCallbackAvrcp extends IInterface {
    void onAvrcp13EventBatteryStatusChanged(byte b) throws RemoteException;

    void onAvrcp13EventPlaybackPosChanged(long j) throws RemoteException;

    void onAvrcp13EventPlaybackStatusChanged(byte b) throws RemoteException;

    void onAvrcp13EventPlayerSettingChanged(byte[] bArr, byte[] bArr2) throws RemoteException;

    void onAvrcp13EventSystemStatusChanged(byte b) throws RemoteException;

    void onAvrcp13EventTrackChanged(long j) throws RemoteException;

    void onAvrcp13EventTrackReachedEnd() throws RemoteException;

    void onAvrcp13EventTrackReachedStart() throws RemoteException;

    void onAvrcp13RegisterEventWatcherFail(byte b) throws RemoteException;

    void onAvrcp13RegisterEventWatcherSuccess(byte b) throws RemoteException;

    void onAvrcp14EventAddressedPlayerChanged(int i, int i2) throws RemoteException;

    void onAvrcp14EventAvailablePlayerChanged() throws RemoteException;

    void onAvrcp14EventNowPlayingContentChanged() throws RemoteException;

    void onAvrcp14EventUidsChanged(int i) throws RemoteException;

    void onAvrcp14EventVolumeChanged(byte b) throws RemoteException;

    void onAvrcpErrorResponse(int i, int i2, byte b) throws RemoteException;

    void onAvrcpServiceReady() throws RemoteException;

    void onAvrcpStateChanged(String str, int i, int i2) throws RemoteException;

    void retAvrcp13CapabilitiesSupportEvent(byte[] bArr) throws RemoteException;

    void retAvrcp13ElementAttributesPlaying(int[] iArr, String[] strArr) throws RemoteException;

    void retAvrcp13PlayStatus(long j, long j2, byte b) throws RemoteException;

    void retAvrcp13PlayerSettingAttributesList(byte[] bArr) throws RemoteException;

    void retAvrcp13PlayerSettingCurrentValues(byte[] bArr, byte[] bArr2) throws RemoteException;

    void retAvrcp13PlayerSettingValuesList(byte b, byte[] bArr) throws RemoteException;

    void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException;

    void retAvrcp14AddToNowPlayingSuccess() throws RemoteException;

    void retAvrcp14ChangePathSuccess(long j) throws RemoteException;

    void retAvrcp14FolderItems(int i, long j) throws RemoteException;

    void retAvrcp14ItemAttributes(int[] iArr, String[] strArr) throws RemoteException;

    void retAvrcp14MediaItems(int i, long j) throws RemoteException;

    void retAvrcp14PlaySelectedItemSuccess() throws RemoteException;

    void retAvrcp14SearchResult(int i, long j) throws RemoteException;

    void retAvrcp14SetAbsoluteVolumeSuccess(byte b) throws RemoteException;

    void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException;

    void retAvrcp14SetBrowsedPlayerSuccess(String[] strArr, int i, long j) throws RemoteException;

    void retAvrcpPlayModeChanged(String str, int i) throws RemoteException;

    void retAvrcpUpdateSongStatus(String str, String str2, String str3) throws RemoteException;

    public static class Default implements UiCallbackAvrcp {
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherSuccess(byte eventId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherFail(byte eventId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackStatusChanged(byte statusId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackChanged(long elementId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedStart() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackPosChanged(long songPos) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventBatteryStatusChanged(byte statusId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventSystemStatusChanged(byte statusId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventUidsChanged(int uidCounter) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventVolumeChanged(byte volume) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14FolderItems(int uidCounter, long itemCount) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14MediaItems(int uidCounter, long itemCount) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ChangePathSuccess(long itemCount) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SearchResult(int uidCounter, long itemCount) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpErrorResponse(int opId, int reason, byte eventId) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpUpdateSongStatus(String artist, String album, String title) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpPlayModeChanged(String address, int mode) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements UiCallbackAvrcp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.UiCallbackAvrcp";
        static final int TRANSACTION_onAvrcp13EventBatteryStatusChanged = 17;
        static final int TRANSACTION_onAvrcp13EventPlaybackPosChanged = 16;
        static final int TRANSACTION_onAvrcp13EventPlaybackStatusChanged = 12;
        static final int TRANSACTION_onAvrcp13EventPlayerSettingChanged = 19;
        static final int TRANSACTION_onAvrcp13EventSystemStatusChanged = 18;
        static final int TRANSACTION_onAvrcp13EventTrackChanged = 13;
        static final int TRANSACTION_onAvrcp13EventTrackReachedEnd = 14;
        static final int TRANSACTION_onAvrcp13EventTrackReachedStart = 15;
        static final int TRANSACTION_onAvrcp13RegisterEventWatcherFail = 11;
        static final int TRANSACTION_onAvrcp13RegisterEventWatcherSuccess = 10;
        static final int TRANSACTION_onAvrcp14EventAddressedPlayerChanged = 22;
        static final int TRANSACTION_onAvrcp14EventAvailablePlayerChanged = 21;
        static final int TRANSACTION_onAvrcp14EventNowPlayingContentChanged = 20;
        static final int TRANSACTION_onAvrcp14EventUidsChanged = 23;
        static final int TRANSACTION_onAvrcp14EventVolumeChanged = 24;
        static final int TRANSACTION_onAvrcpErrorResponse = 35;
        static final int TRANSACTION_onAvrcpServiceReady = 1;
        static final int TRANSACTION_onAvrcpStateChanged = 2;
        static final int TRANSACTION_retAvrcp13CapabilitiesSupportEvent = 3;
        static final int TRANSACTION_retAvrcp13ElementAttributesPlaying = 8;
        static final int TRANSACTION_retAvrcp13PlayStatus = 9;
        static final int TRANSACTION_retAvrcp13PlayerSettingAttributesList = 4;
        static final int TRANSACTION_retAvrcp13PlayerSettingCurrentValues = 6;
        static final int TRANSACTION_retAvrcp13PlayerSettingValuesList = 5;
        static final int TRANSACTION_retAvrcp13SetPlayerSettingValueSuccess = 7;
        static final int TRANSACTION_retAvrcp14AddToNowPlayingSuccess = 33;
        static final int TRANSACTION_retAvrcp14ChangePathSuccess = 29;
        static final int TRANSACTION_retAvrcp14FolderItems = 27;
        static final int TRANSACTION_retAvrcp14ItemAttributes = 30;
        static final int TRANSACTION_retAvrcp14MediaItems = 28;
        static final int TRANSACTION_retAvrcp14PlaySelectedItemSuccess = 31;
        static final int TRANSACTION_retAvrcp14SearchResult = 32;
        static final int TRANSACTION_retAvrcp14SetAbsoluteVolumeSuccess = 34;
        static final int TRANSACTION_retAvrcp14SetAddressedPlayerSuccess = 25;
        static final int TRANSACTION_retAvrcp14SetBrowsedPlayerSuccess = 26;
        static final int TRANSACTION_retAvrcpPlayModeChanged = 37;
        static final int TRANSACTION_retAvrcpUpdateSongStatus = 36;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCallbackAvrcp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof UiCallbackAvrcp)) {
                return new Proxy(obj);
            }
            return (UiCallbackAvrcp) iin;
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
                        onAvrcpServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcpStateChanged(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13CapabilitiesSupportEvent(data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13PlayerSettingAttributesList(data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13PlayerSettingValuesList(data.readByte(), data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13PlayerSettingCurrentValues(data.createByteArray(), data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13SetPlayerSettingValueSuccess();
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13ElementAttributesPlaying(data.createIntArray(), data.createStringArray());
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp13PlayStatus(data.readLong(), data.readLong(), data.readByte());
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13RegisterEventWatcherSuccess(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13RegisterEventWatcherFail(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventPlaybackStatusChanged(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventTrackChanged(data.readLong());
                        reply.writeNoException();
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventTrackReachedEnd();
                        reply.writeNoException();
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventTrackReachedStart();
                        reply.writeNoException();
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventPlaybackPosChanged(data.readLong());
                        reply.writeNoException();
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventBatteryStatusChanged(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventSystemStatusChanged(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp13EventPlayerSettingChanged(data.createByteArray(), data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp14EventNowPlayingContentChanged();
                        reply.writeNoException();
                        return true;
                    case 21:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp14EventAvailablePlayerChanged();
                        reply.writeNoException();
                        return true;
                    case 22:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp14EventAddressedPlayerChanged(data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 23:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp14EventUidsChanged(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 24:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcp14EventVolumeChanged(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 25:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14SetAddressedPlayerSuccess();
                        reply.writeNoException();
                        return true;
                    case 26:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14SetBrowsedPlayerSuccess(data.createStringArray(), data.readInt(), data.readLong());
                        reply.writeNoException();
                        return true;
                    case 27:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14FolderItems(data.readInt(), data.readLong());
                        reply.writeNoException();
                        return true;
                    case 28:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14MediaItems(data.readInt(), data.readLong());
                        reply.writeNoException();
                        return true;
                    case 29:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14ChangePathSuccess(data.readLong());
                        reply.writeNoException();
                        return true;
                    case 30:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14ItemAttributes(data.createIntArray(), data.createStringArray());
                        reply.writeNoException();
                        return true;
                    case 31:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14PlaySelectedItemSuccess();
                        reply.writeNoException();
                        return true;
                    case 32:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14SearchResult(data.readInt(), data.readLong());
                        reply.writeNoException();
                        return true;
                    case 33:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14AddToNowPlayingSuccess();
                        reply.writeNoException();
                        return true;
                    case 34:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcp14SetAbsoluteVolumeSuccess(data.readByte());
                        reply.writeNoException();
                        return true;
                    case 35:
                        data.enforceInterface(DESCRIPTOR);
                        onAvrcpErrorResponse(data.readInt(), data.readInt(), data.readByte());
                        reply.writeNoException();
                        return true;
                    case 36:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcpUpdateSongStatus(data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 37:
                        data.enforceInterface(DESCRIPTOR);
                        retAvrcpPlayModeChanged(data.readString(), data.readInt());
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
        public static class Proxy implements UiCallbackAvrcp {
            public static UiCallbackAvrcp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcpServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcpStateChanged(address, prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(eventIds);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13CapabilitiesSupportEvent(eventIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(attributeIds);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13PlayerSettingAttributesList(attributeIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(attributeId);
                    _data.writeByteArray(valueIds);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13PlayerSettingValuesList(attributeId, valueIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(attributeIds);
                    _data.writeByteArray(valueIds);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13PlayerSettingCurrentValues(attributeIds, valueIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13SetPlayerSettingValueSuccess();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(metadataAtrributeIds);
                    _data.writeStringArray(texts);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13ElementAttributesPlaying(metadataAtrributeIds, texts);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(songLen);
                    _data.writeLong(songPos);
                    _data.writeByte(statusId);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp13PlayStatus(songLen, songPos, statusId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13RegisterEventWatcherSuccess(byte eventId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(eventId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13RegisterEventWatcherSuccess(eventId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13RegisterEventWatcherFail(byte eventId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(eventId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13RegisterEventWatcherFail(eventId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventPlaybackStatusChanged(byte statusId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(statusId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventPlaybackStatusChanged(statusId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventTrackChanged(long elementId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(elementId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventTrackChanged(elementId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventTrackReachedEnd();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventTrackReachedStart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventTrackReachedStart();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventPlaybackPosChanged(long songPos) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(songPos);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventPlaybackPosChanged(songPos);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventBatteryStatusChanged(byte statusId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(statusId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventBatteryStatusChanged(statusId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventSystemStatusChanged(byte statusId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(statusId);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventSystemStatusChanged(statusId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(attributeIds);
                    _data.writeByteArray(valueIds);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp13EventPlayerSettingChanged(attributeIds, valueIds);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp14EventNowPlayingContentChanged();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp14EventAvailablePlayerChanged();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(playerId);
                    _data.writeInt(uidCounter);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp14EventAddressedPlayerChanged(playerId, uidCounter);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventUidsChanged(int uidCounter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uidCounter);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp14EventUidsChanged(uidCounter);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventVolumeChanged(byte volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(volume);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcp14EventVolumeChanged(volume);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14SetAddressedPlayerSuccess();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(path);
                    _data.writeInt(uidCounter);
                    _data.writeLong(itemCount);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14SetBrowsedPlayerSuccess(path, uidCounter, itemCount);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14FolderItems(int uidCounter, long itemCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uidCounter);
                    _data.writeLong(itemCount);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14FolderItems(uidCounter, itemCount);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14MediaItems(int uidCounter, long itemCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uidCounter);
                    _data.writeLong(itemCount);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14MediaItems(uidCounter, itemCount);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14ChangePathSuccess(long itemCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(itemCount);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14ChangePathSuccess(itemCount);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(metadataAtrributeIds);
                    _data.writeStringArray(texts);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14ItemAttributes(metadataAtrributeIds, texts);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14PlaySelectedItemSuccess();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SearchResult(int uidCounter, long itemCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uidCounter);
                    _data.writeLong(itemCount);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14SearchResult(uidCounter, itemCount);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14AddToNowPlayingSuccess();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte(volume);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcp14SetAbsoluteVolumeSuccess(volume);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void onAvrcpErrorResponse(int opId, int reason, byte eventId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(opId);
                    _data.writeInt(reason);
                    _data.writeByte(eventId);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAvrcpErrorResponse(opId, reason, eventId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcpUpdateSongStatus(String artist, String album, String title) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(artist);
                    _data.writeString(album);
                    _data.writeString(title);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcpUpdateSongStatus(artist, album, title);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
            public void retAvrcpPlayModeChanged(String address, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retAvrcpPlayModeChanged(address, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(UiCallbackAvrcp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static UiCallbackAvrcp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
