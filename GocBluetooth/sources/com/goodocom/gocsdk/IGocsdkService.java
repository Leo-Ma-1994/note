package com.goodocom.gocsdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.gocsdk.IGocsdkCallback;

public interface IGocsdkService extends IInterface {
    void callLogstartUpdate(int i) throws RemoteException;

    void cancelAutoAnswer() throws RemoteException;

    void cancelAutoConnect() throws RemoteException;

    void cancelPairMode() throws RemoteException;

    void cancelPhoneVoiceDail() throws RemoteException;

    void closeBlueTooth() throws RemoteException;

    void connectA2dp(String str) throws RemoteException;

    void connectA2dpp() throws RemoteException;

    void connectDevice(String str) throws RemoteException;

    void connectHFP(String str) throws RemoteException;

    void connectHid(String str) throws RemoteException;

    void connectLast() throws RemoteException;

    void connectSpp(String str) throws RemoteException;

    void deletePair(String str) throws RemoteException;

    void disconnect() throws RemoteException;

    void disconnectA2DP() throws RemoteException;

    void disconnectHFP() throws RemoteException;

    void disconnectHid() throws RemoteException;

    void disconnectSpp() throws RemoteException;

    void getAutoConnectAnswer() throws RemoteException;

    void getBtXchange(String str) throws RemoteException;

    void getCurrentDeviceAddr() throws RemoteException;

    void getCurrentDeviceName() throws RemoteException;

    void getLocalAddress() throws RemoteException;

    void getLocalName() throws RemoteException;

    void getMessageDeletedList() throws RemoteException;

    void getMessageInboxList() throws RemoteException;

    void getMessageSentList() throws RemoteException;

    void getMessageText(String str) throws RemoteException;

    void getMusicInfo() throws RemoteException;

    void getOnMusicCover(String str) throws RemoteException;

    void getPairList() throws RemoteException;

    void getPinCode() throws RemoteException;

    void getProfileEnabled() throws RemoteException;

    void getVersion() throws RemoteException;

    void hidBackClick() throws RemoteException;

    void hidHomeClick() throws RemoteException;

    void hidMenuClick() throws RemoteException;

    void hidMousDown(String str) throws RemoteException;

    void hidMouseMove(String str) throws RemoteException;

    void hidMouseUp(String str) throws RemoteException;

    void inqueryA2dpStatus() throws RemoteException;

    void inqueryHfpStatus() throws RemoteException;

    void musicBackground() throws RemoteException;

    void musicIntoList(String str) throws RemoteException;

    void musicIntoPre() throws RemoteException;

    void musicMute() throws RemoteException;

    void musicNext() throws RemoteException;

    void musicNormal() throws RemoteException;

    void musicPause() throws RemoteException;

    void musicPlay() throws RemoteException;

    void musicPlayNow(String str) throws RemoteException;

    void musicPlayOrPause() throws RemoteException;

    void musicPrevious() throws RemoteException;

    void musicStop() throws RemoteException;

    void musicUnmute() throws RemoteException;

    void musiclist() throws RemoteException;

    void muteOpenAndClose(int i) throws RemoteException;

    void openBlueTooth() throws RemoteException;

    void pairedDevice(String str) throws RemoteException;

    void pauseDownLoadContact() throws RemoteException;

    void phoneAnswer() throws RemoteException;

    void phoneBookStartUpdate() throws RemoteException;

    void phoneDail(String str) throws RemoteException;

    void phoneHangUp() throws RemoteException;

    void phoneTransfer() throws RemoteException;

    void phoneTransferBack() throws RemoteException;

    void phoneTransmitDTMFCode(char c) throws RemoteException;

    void phoneVoiceDail() throws RemoteException;

    void registerCallback(IGocsdkCallback iGocsdkCallback) throws RemoteException;

    void restBluetooth() throws RemoteException;

    void setAutoAnswer() throws RemoteException;

    void setAutoConnect() throws RemoteException;

    void setLocalName(String str) throws RemoteException;

    void setPairMode() throws RemoteException;

    void setPinCode(String str) throws RemoteException;

    void setProfileEnabled(boolean[] zArr) throws RemoteException;

    void sppSendData(String str, String str2) throws RemoteException;

    void startDiscovery() throws RemoteException;

    void stopDiscovery() throws RemoteException;

    void unregisterCallback(IGocsdkCallback iGocsdkCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IGocsdkService {
        private static final String DESCRIPTOR = "com.goodocom.gocsdk.IGocsdkService";
        static final int TRANSACTION_callLogstartUpdate = 43;
        static final int TRANSACTION_cancelAutoAnswer = 13;
        static final int TRANSACTION_cancelAutoConnect = 11;
        static final int TRANSACTION_cancelPairMode = 16;
        static final int TRANSACTION_cancelPhoneVoiceDail = 41;
        static final int TRANSACTION_closeBlueTooth = 75;
        static final int TRANSACTION_connectA2dp = 20;
        static final int TRANSACTION_connectA2dpp = 21;
        static final int TRANSACTION_connectDevice = 19;
        static final int TRANSACTION_connectHFP = 22;
        static final int TRANSACTION_connectHid = 23;
        static final int TRANSACTION_connectLast = 17;
        static final int TRANSACTION_connectSpp = 24;
        static final int TRANSACTION_deletePair = 30;
        static final int TRANSACTION_disconnect = 25;
        static final int TRANSACTION_disconnectA2DP = 26;
        static final int TRANSACTION_disconnectHFP = 27;
        static final int TRANSACTION_disconnectHid = 28;
        static final int TRANSACTION_disconnectSpp = 29;
        static final int TRANSACTION_getAutoConnectAnswer = 9;
        static final int TRANSACTION_getBtXchange = 81;
        static final int TRANSACTION_getCurrentDeviceAddr = 62;
        static final int TRANSACTION_getCurrentDeviceName = 63;
        static final int TRANSACTION_getLocalAddress = 8;
        static final int TRANSACTION_getLocalName = 4;
        static final int TRANSACTION_getMessageDeletedList = 71;
        static final int TRANSACTION_getMessageInboxList = 72;
        static final int TRANSACTION_getMessageSentList = 70;
        static final int TRANSACTION_getMessageText = 73;
        static final int TRANSACTION_getMusicInfo = 59;
        static final int TRANSACTION_getOnMusicCover = 80;
        static final int TRANSACTION_getPairList = 32;
        static final int TRANSACTION_getPinCode = 6;
        static final int TRANSACTION_getProfileEnabled = 69;
        static final int TRANSACTION_getVersion = 14;
        static final int TRANSACTION_hidBackClick = 56;
        static final int TRANSACTION_hidHomeClick = 55;
        static final int TRANSACTION_hidMenuClick = 57;
        static final int TRANSACTION_hidMousDown = 54;
        static final int TRANSACTION_hidMouseMove = 52;
        static final int TRANSACTION_hidMouseUp = 53;
        static final int TRANSACTION_inqueryA2dpStatus = 61;
        static final int TRANSACTION_inqueryHfpStatus = 60;
        static final int TRANSACTION_musicBackground = 50;
        static final int TRANSACTION_musicIntoList = 77;
        static final int TRANSACTION_musicIntoPre = 78;
        static final int TRANSACTION_musicMute = 48;
        static final int TRANSACTION_musicNext = 47;
        static final int TRANSACTION_musicNormal = 51;
        static final int TRANSACTION_musicPause = 66;
        static final int TRANSACTION_musicPlay = 65;
        static final int TRANSACTION_musicPlayNow = 79;
        static final int TRANSACTION_musicPlayOrPause = 44;
        static final int TRANSACTION_musicPrevious = 46;
        static final int TRANSACTION_musicStop = 45;
        static final int TRANSACTION_musicUnmute = 49;
        static final int TRANSACTION_musiclist = 76;
        static final int TRANSACTION_muteOpenAndClose = 67;
        static final int TRANSACTION_openBlueTooth = 74;
        static final int TRANSACTION_pairedDevice = 18;
        static final int TRANSACTION_pauseDownLoadContact = 64;
        static final int TRANSACTION_phoneAnswer = 34;
        static final int TRANSACTION_phoneBookStartUpdate = 42;
        static final int TRANSACTION_phoneDail = 36;
        static final int TRANSACTION_phoneHangUp = 35;
        static final int TRANSACTION_phoneTransfer = 38;
        static final int TRANSACTION_phoneTransferBack = 39;
        static final int TRANSACTION_phoneTransmitDTMFCode = 37;
        static final int TRANSACTION_phoneVoiceDail = 40;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_restBluetooth = 3;
        static final int TRANSACTION_setAutoAnswer = 12;
        static final int TRANSACTION_setAutoConnect = 10;
        static final int TRANSACTION_setLocalName = 5;
        static final int TRANSACTION_setPairMode = 15;
        static final int TRANSACTION_setPinCode = 7;
        static final int TRANSACTION_setProfileEnabled = 68;
        static final int TRANSACTION_sppSendData = 58;
        static final int TRANSACTION_startDiscovery = 31;
        static final int TRANSACTION_stopDiscovery = 33;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGocsdkService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IGocsdkService)) {
                return new Proxy(obj);
            }
            return (IGocsdkService) iin;
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
                        registerCallback(IGocsdkCallback.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        unregisterCallback(IGocsdkCallback.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        restBluetooth();
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        getLocalName();
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        setLocalName(data.readString());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        getPinCode();
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        setPinCode(data.readString());
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        getLocalAddress();
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        getAutoConnectAnswer();
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        setAutoConnect();
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        cancelAutoConnect();
                        reply.writeNoException();
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        setAutoAnswer();
                        reply.writeNoException();
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        cancelAutoAnswer();
                        reply.writeNoException();
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        getVersion();
                        reply.writeNoException();
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        setPairMode();
                        reply.writeNoException();
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        cancelPairMode();
                        reply.writeNoException();
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        connectLast();
                        reply.writeNoException();
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        pairedDevice(data.readString());
                        reply.writeNoException();
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        connectDevice(data.readString());
                        reply.writeNoException();
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        connectA2dp(data.readString());
                        reply.writeNoException();
                        return true;
                    case 21:
                        data.enforceInterface(DESCRIPTOR);
                        connectA2dpp();
                        reply.writeNoException();
                        return true;
                    case 22:
                        data.enforceInterface(DESCRIPTOR);
                        connectHFP(data.readString());
                        reply.writeNoException();
                        return true;
                    case 23:
                        data.enforceInterface(DESCRIPTOR);
                        connectHid(data.readString());
                        reply.writeNoException();
                        return true;
                    case 24:
                        data.enforceInterface(DESCRIPTOR);
                        connectSpp(data.readString());
                        reply.writeNoException();
                        return true;
                    case 25:
                        data.enforceInterface(DESCRIPTOR);
                        disconnect();
                        reply.writeNoException();
                        return true;
                    case 26:
                        data.enforceInterface(DESCRIPTOR);
                        disconnectA2DP();
                        reply.writeNoException();
                        return true;
                    case 27:
                        data.enforceInterface(DESCRIPTOR);
                        disconnectHFP();
                        reply.writeNoException();
                        return true;
                    case 28:
                        data.enforceInterface(DESCRIPTOR);
                        disconnectHid();
                        reply.writeNoException();
                        return true;
                    case 29:
                        data.enforceInterface(DESCRIPTOR);
                        disconnectSpp();
                        reply.writeNoException();
                        return true;
                    case 30:
                        data.enforceInterface(DESCRIPTOR);
                        deletePair(data.readString());
                        reply.writeNoException();
                        return true;
                    case 31:
                        data.enforceInterface(DESCRIPTOR);
                        startDiscovery();
                        reply.writeNoException();
                        return true;
                    case 32:
                        data.enforceInterface(DESCRIPTOR);
                        getPairList();
                        reply.writeNoException();
                        return true;
                    case 33:
                        data.enforceInterface(DESCRIPTOR);
                        stopDiscovery();
                        reply.writeNoException();
                        return true;
                    case 34:
                        data.enforceInterface(DESCRIPTOR);
                        phoneAnswer();
                        reply.writeNoException();
                        return true;
                    case 35:
                        data.enforceInterface(DESCRIPTOR);
                        phoneHangUp();
                        reply.writeNoException();
                        return true;
                    case 36:
                        data.enforceInterface(DESCRIPTOR);
                        phoneDail(data.readString());
                        reply.writeNoException();
                        return true;
                    case 37:
                        data.enforceInterface(DESCRIPTOR);
                        phoneTransmitDTMFCode((char) data.readInt());
                        reply.writeNoException();
                        return true;
                    case 38:
                        data.enforceInterface(DESCRIPTOR);
                        phoneTransfer();
                        reply.writeNoException();
                        return true;
                    case 39:
                        data.enforceInterface(DESCRIPTOR);
                        phoneTransferBack();
                        reply.writeNoException();
                        return true;
                    case 40:
                        data.enforceInterface(DESCRIPTOR);
                        phoneVoiceDail();
                        reply.writeNoException();
                        return true;
                    case 41:
                        data.enforceInterface(DESCRIPTOR);
                        cancelPhoneVoiceDail();
                        reply.writeNoException();
                        return true;
                    case 42:
                        data.enforceInterface(DESCRIPTOR);
                        phoneBookStartUpdate();
                        reply.writeNoException();
                        return true;
                    case 43:
                        data.enforceInterface(DESCRIPTOR);
                        callLogstartUpdate(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 44:
                        data.enforceInterface(DESCRIPTOR);
                        musicPlayOrPause();
                        reply.writeNoException();
                        return true;
                    case 45:
                        data.enforceInterface(DESCRIPTOR);
                        musicStop();
                        reply.writeNoException();
                        return true;
                    case 46:
                        data.enforceInterface(DESCRIPTOR);
                        musicPrevious();
                        reply.writeNoException();
                        return true;
                    case 47:
                        data.enforceInterface(DESCRIPTOR);
                        musicNext();
                        reply.writeNoException();
                        return true;
                    case 48:
                        data.enforceInterface(DESCRIPTOR);
                        musicMute();
                        reply.writeNoException();
                        return true;
                    case 49:
                        data.enforceInterface(DESCRIPTOR);
                        musicUnmute();
                        reply.writeNoException();
                        return true;
                    case 50:
                        data.enforceInterface(DESCRIPTOR);
                        musicBackground();
                        reply.writeNoException();
                        return true;
                    case 51:
                        data.enforceInterface(DESCRIPTOR);
                        musicNormal();
                        reply.writeNoException();
                        return true;
                    case 52:
                        data.enforceInterface(DESCRIPTOR);
                        hidMouseMove(data.readString());
                        reply.writeNoException();
                        return true;
                    case 53:
                        data.enforceInterface(DESCRIPTOR);
                        hidMouseUp(data.readString());
                        reply.writeNoException();
                        return true;
                    case 54:
                        data.enforceInterface(DESCRIPTOR);
                        hidMousDown(data.readString());
                        reply.writeNoException();
                        return true;
                    case 55:
                        data.enforceInterface(DESCRIPTOR);
                        hidHomeClick();
                        reply.writeNoException();
                        return true;
                    case 56:
                        data.enforceInterface(DESCRIPTOR);
                        hidBackClick();
                        reply.writeNoException();
                        return true;
                    case 57:
                        data.enforceInterface(DESCRIPTOR);
                        hidMenuClick();
                        reply.writeNoException();
                        return true;
                    case 58:
                        data.enforceInterface(DESCRIPTOR);
                        sppSendData(data.readString(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 59:
                        data.enforceInterface(DESCRIPTOR);
                        getMusicInfo();
                        reply.writeNoException();
                        return true;
                    case 60:
                        data.enforceInterface(DESCRIPTOR);
                        inqueryHfpStatus();
                        reply.writeNoException();
                        return true;
                    case 61:
                        data.enforceInterface(DESCRIPTOR);
                        inqueryA2dpStatus();
                        reply.writeNoException();
                        return true;
                    case 62:
                        data.enforceInterface(DESCRIPTOR);
                        getCurrentDeviceAddr();
                        reply.writeNoException();
                        return true;
                    case 63:
                        data.enforceInterface(DESCRIPTOR);
                        getCurrentDeviceName();
                        reply.writeNoException();
                        return true;
                    case 64:
                        data.enforceInterface(DESCRIPTOR);
                        pauseDownLoadContact();
                        reply.writeNoException();
                        return true;
                    case 65:
                        data.enforceInterface(DESCRIPTOR);
                        musicPlay();
                        reply.writeNoException();
                        return true;
                    case 66:
                        data.enforceInterface(DESCRIPTOR);
                        musicPause();
                        reply.writeNoException();
                        return true;
                    case 67:
                        data.enforceInterface(DESCRIPTOR);
                        muteOpenAndClose(data.readInt());
                        reply.writeNoException();
                        return true;
                    case 68:
                        data.enforceInterface(DESCRIPTOR);
                        setProfileEnabled(data.createBooleanArray());
                        reply.writeNoException();
                        return true;
                    case 69:
                        data.enforceInterface(DESCRIPTOR);
                        getProfileEnabled();
                        reply.writeNoException();
                        return true;
                    case 70:
                        data.enforceInterface(DESCRIPTOR);
                        getMessageSentList();
                        reply.writeNoException();
                        return true;
                    case 71:
                        data.enforceInterface(DESCRIPTOR);
                        getMessageDeletedList();
                        reply.writeNoException();
                        return true;
                    case 72:
                        data.enforceInterface(DESCRIPTOR);
                        getMessageInboxList();
                        reply.writeNoException();
                        return true;
                    case 73:
                        data.enforceInterface(DESCRIPTOR);
                        getMessageText(data.readString());
                        reply.writeNoException();
                        return true;
                    case 74:
                        data.enforceInterface(DESCRIPTOR);
                        openBlueTooth();
                        reply.writeNoException();
                        return true;
                    case 75:
                        data.enforceInterface(DESCRIPTOR);
                        closeBlueTooth();
                        reply.writeNoException();
                        return true;
                    case 76:
                        data.enforceInterface(DESCRIPTOR);
                        musiclist();
                        reply.writeNoException();
                        return true;
                    case 77:
                        data.enforceInterface(DESCRIPTOR);
                        musicIntoList(data.readString());
                        reply.writeNoException();
                        return true;
                    case 78:
                        data.enforceInterface(DESCRIPTOR);
                        musicIntoPre();
                        reply.writeNoException();
                        return true;
                    case 79:
                        data.enforceInterface(DESCRIPTOR);
                        musicPlayNow(data.readString());
                        reply.writeNoException();
                        return true;
                    case 80:
                        data.enforceInterface(DESCRIPTOR);
                        getOnMusicCover(data.readString());
                        reply.writeNoException();
                        return true;
                    case 81:
                        data.enforceInterface(DESCRIPTOR);
                        getBtXchange(data.readString());
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

        private static class Proxy implements IGocsdkService {
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

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void registerCallback(IGocsdkCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void unregisterCallback(IGocsdkCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void restBluetooth() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getLocalName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void setLocalName(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getPinCode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void setPinCode(String pincode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pincode);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getLocalAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getAutoConnectAnswer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void setAutoConnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void cancelAutoConnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void setAutoAnswer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void cancelAutoAnswer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void setPairMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void cancelPairMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectLast() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void pairedDevice(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectDevice(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectA2dp(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectA2dpp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectHFP(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectHid(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void connectSpp(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void disconnect() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void disconnectA2DP() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void disconnectHFP() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void disconnectHid() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void disconnectSpp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void deletePair(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void startDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getPairList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void stopDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneAnswer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneHangUp() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneDail(String phonenum) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(phonenum);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneTransmitDTMFCode(char code) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneTransfer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneTransferBack() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneVoiceDail() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void cancelPhoneVoiceDail() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void phoneBookStartUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void callLogstartUpdate(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicPlayOrPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicStop() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicPrevious() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicNext() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicMute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicUnmute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicBackground() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicNormal() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void hidMouseMove(String point) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(point);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void hidMouseUp(String point) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(point);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void hidMousDown(String point) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(point);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void hidHomeClick() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void hidBackClick() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void hidMenuClick() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void sppSendData(String addr, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    _data.writeString(data);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getMusicInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void inqueryHfpStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void inqueryA2dpStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getCurrentDeviceAddr() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getCurrentDeviceName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void pauseDownLoadContact() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicPlay() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicPause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void muteOpenAndClose(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void setProfileEnabled(boolean[] enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBooleanArray(enabled);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getProfileEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getMessageSentList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getMessageDeletedList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getMessageInboxList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getMessageText(String handle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(handle);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void openBlueTooth() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void closeBlueTooth() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musiclist() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicIntoList(String index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(index);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicIntoPre() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void musicPlayNow(String index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(index);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getOnMusicCover(String index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(index);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.gocsdk.IGocsdkService
            public void getBtXchange(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
