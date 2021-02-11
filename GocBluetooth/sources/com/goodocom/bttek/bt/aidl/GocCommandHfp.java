package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackHfp;
import java.util.List;

public interface GocCommandHfp extends IInterface {
    boolean getBtAnswerType() throws RemoteException;

    boolean getBtAutoAnswerState() throws RemoteException;

    int getHfpAudioConnectionState() throws RemoteException;

    List<GocHfpClientCall> getHfpCallList() throws RemoteException;

    List<GocHfpClientCall> getHfpCallList2() throws RemoteException;

    String getHfpConnectedAddress() throws RemoteException;

    int getHfpConnectionState() throws RemoteException;

    int getHfpRemoteBatteryIndicator() throws RemoteException;

    String getHfpRemoteNetworkOperator() throws RemoteException;

    int getHfpRemoteSignalStrength() throws RemoteException;

    String getHfpRemoteSubscriberNumber() throws RemoteException;

    boolean getThreePartyCallEnable() throws RemoteException;

    boolean isHfpConnected(String str) throws RemoteException;

    boolean isHfpInBandRingtoneSupport() throws RemoteException;

    boolean isHfpMicMute() throws RemoteException;

    boolean isHfpRemoteOnRoaming() throws RemoteException;

    boolean isHfpRemoteTelecomServiceOn() throws RemoteException;

    boolean isHfpRemoteVoiceDialOn() throws RemoteException;

    boolean isHfpServiceReady() throws RemoteException;

    void muteHfpMic(boolean z) throws RemoteException;

    void pauseHfpRender() throws RemoteException;

    boolean registerHfpCallback(GocCallbackHfp gocCallbackHfp) throws RemoteException;

    boolean reqHfpAnswerCall(int i) throws RemoteException;

    boolean reqHfpAudioTransferToCarkit(String str) throws RemoteException;

    boolean reqHfpAudioTransferToPhone(String str) throws RemoteException;

    boolean reqHfpConnect(String str) throws RemoteException;

    boolean reqHfpDialCall(String str) throws RemoteException;

    boolean reqHfpDisconnect(String str) throws RemoteException;

    boolean reqHfpMemoryDial(String str) throws RemoteException;

    boolean reqHfpReDial() throws RemoteException;

    boolean reqHfpRejectIncomingCall(String str) throws RemoteException;

    boolean reqHfpSendDtmf(String str, String str2) throws RemoteException;

    boolean reqHfpTerminateCurrentCall(String str) throws RemoteException;

    boolean reqHfpVoiceDial(boolean z) throws RemoteException;

    void setBtAnswerTypeEnable(boolean z) throws RemoteException;

    void setBtAutoAnswerEnable(boolean z, int i) throws RemoteException;

    void setThreePartyCallEnable(boolean z) throws RemoteException;

    void startHfpRender() throws RemoteException;

    boolean unregisterHfpCallback(GocCallbackHfp gocCallbackHfp) throws RemoteException;

    public static class Default implements GocCommandHfp {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean registerHfpCallback(GocCallbackHfp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean unregisterHfpCallback(GocCallbackHfp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public int getHfpConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpConnected(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public String getHfpConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public int getHfpAudioConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public int getHfpRemoteSignalStrength() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public List<GocHfpClientCall> getHfpCallList() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpRemoteOnRoaming() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public int getHfpRemoteBatteryIndicator() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpDialCall(String number) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpReDial() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpMemoryDial(String index) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpAnswerCall(int flag) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpRejectIncomingCall(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpTerminateCurrentCall(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpSendDtmf(String address, String number) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpAudioTransferToCarkit(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpAudioTransferToPhone(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public String getHfpRemoteNetworkOperator() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public String getHfpRemoteSubscriberNumber() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean reqHfpVoiceDial(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public void pauseHfpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public void startHfpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpMicMute() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public void muteHfpMic(boolean mute) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean isHfpInBandRingtoneSupport() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean getBtAnswerType() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean getBtAutoAnswerState() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public boolean getThreePartyCallEnable() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
        public void setThreePartyCallEnable(boolean enable) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandHfp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandHfp";
        static final int TRANSACTION_getBtAnswerType = 34;
        static final int TRANSACTION_getBtAutoAnswerState = 36;
        static final int TRANSACTION_getHfpAudioConnectionState = 7;
        static final int TRANSACTION_getHfpCallList = 11;
        static final int TRANSACTION_getHfpCallList2 = 12;
        static final int TRANSACTION_getHfpConnectedAddress = 6;
        static final int TRANSACTION_getHfpConnectionState = 4;
        static final int TRANSACTION_getHfpRemoteBatteryIndicator = 14;
        static final int TRANSACTION_getHfpRemoteNetworkOperator = 26;
        static final int TRANSACTION_getHfpRemoteSignalStrength = 10;
        static final int TRANSACTION_getHfpRemoteSubscriberNumber = 27;
        static final int TRANSACTION_getThreePartyCallEnable = 38;
        static final int TRANSACTION_isHfpConnected = 5;
        static final int TRANSACTION_isHfpInBandRingtoneSupport = 33;
        static final int TRANSACTION_isHfpMicMute = 31;
        static final int TRANSACTION_isHfpRemoteOnRoaming = 13;
        static final int TRANSACTION_isHfpRemoteTelecomServiceOn = 15;
        static final int TRANSACTION_isHfpRemoteVoiceDialOn = 16;
        static final int TRANSACTION_isHfpServiceReady = 1;
        static final int TRANSACTION_muteHfpMic = 32;
        static final int TRANSACTION_pauseHfpRender = 29;
        static final int TRANSACTION_registerHfpCallback = 2;
        static final int TRANSACTION_reqHfpAnswerCall = 20;
        static final int TRANSACTION_reqHfpAudioTransferToCarkit = 24;
        static final int TRANSACTION_reqHfpAudioTransferToPhone = 25;
        static final int TRANSACTION_reqHfpConnect = 8;
        static final int TRANSACTION_reqHfpDialCall = 17;
        static final int TRANSACTION_reqHfpDisconnect = 9;
        static final int TRANSACTION_reqHfpMemoryDial = 19;
        static final int TRANSACTION_reqHfpReDial = 18;
        static final int TRANSACTION_reqHfpRejectIncomingCall = 21;
        static final int TRANSACTION_reqHfpSendDtmf = 23;
        static final int TRANSACTION_reqHfpTerminateCurrentCall = 22;
        static final int TRANSACTION_reqHfpVoiceDial = 28;
        static final int TRANSACTION_setBtAnswerTypeEnable = 37;
        static final int TRANSACTION_setBtAutoAnswerEnable = 35;
        static final int TRANSACTION_setThreePartyCallEnable = 39;
        static final int TRANSACTION_startHfpRender = 30;
        static final int TRANSACTION_unregisterHfpCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandHfp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandHfp)) {
                return new Proxy(obj);
            }
            return (GocCommandHfp) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                boolean _arg0 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpServiceReady = isHfpServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isHfpServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerHfpCallback = registerHfpCallback(GocCallbackHfp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerHfpCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterHfpCallback = unregisterHfpCallback(GocCallbackHfp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterHfpCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        int _result = getHfpConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpConnected = isHfpConnected(data.readString());
                        reply.writeNoException();
                        reply.writeInt(isHfpConnected ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _result2 = getHfpConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result2);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        int _result3 = getHfpAudioConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result3);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpConnect = reqHfpConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpConnect ? 1 : 0);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpDisconnect = reqHfpDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpDisconnect ? 1 : 0);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        int _result4 = getHfpRemoteSignalStrength();
                        reply.writeNoException();
                        reply.writeInt(_result4);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        List<GocHfpClientCall> _result5 = getHfpCallList();
                        reply.writeNoException();
                        reply.writeTypedList(_result5);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        List<GocHfpClientCall> _result6 = getHfpCallList2();
                        reply.writeNoException();
                        reply.writeTypedList(_result6);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpRemoteOnRoaming = isHfpRemoteOnRoaming();
                        reply.writeNoException();
                        reply.writeInt(isHfpRemoteOnRoaming ? 1 : 0);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        int _result7 = getHfpRemoteBatteryIndicator();
                        reply.writeNoException();
                        reply.writeInt(_result7);
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpRemoteTelecomServiceOn = isHfpRemoteTelecomServiceOn();
                        reply.writeNoException();
                        reply.writeInt(isHfpRemoteTelecomServiceOn ? 1 : 0);
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpRemoteVoiceDialOn = isHfpRemoteVoiceDialOn();
                        reply.writeNoException();
                        reply.writeInt(isHfpRemoteVoiceDialOn ? 1 : 0);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpDialCall = reqHfpDialCall(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpDialCall ? 1 : 0);
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpReDial = reqHfpReDial();
                        reply.writeNoException();
                        reply.writeInt(reqHfpReDial ? 1 : 0);
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpMemoryDial = reqHfpMemoryDial(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpMemoryDial ? 1 : 0);
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpAnswerCall = reqHfpAnswerCall(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqHfpAnswerCall ? 1 : 0);
                        return true;
                    case 21:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpRejectIncomingCall = reqHfpRejectIncomingCall(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpRejectIncomingCall ? 1 : 0);
                        return true;
                    case 22:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpTerminateCurrentCall = reqHfpTerminateCurrentCall(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpTerminateCurrentCall ? 1 : 0);
                        return true;
                    case 23:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpSendDtmf = reqHfpSendDtmf(data.readString(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpSendDtmf ? 1 : 0);
                        return true;
                    case 24:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpAudioTransferToCarkit = reqHfpAudioTransferToCarkit(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpAudioTransferToCarkit ? 1 : 0);
                        return true;
                    case 25:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHfpAudioTransferToPhone = reqHfpAudioTransferToPhone(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHfpAudioTransferToPhone ? 1 : 0);
                        return true;
                    case 26:
                        data.enforceInterface(DESCRIPTOR);
                        String _result8 = getHfpRemoteNetworkOperator();
                        reply.writeNoException();
                        reply.writeString(_result8);
                        return true;
                    case 27:
                        data.enforceInterface(DESCRIPTOR);
                        String _result9 = getHfpRemoteSubscriberNumber();
                        reply.writeNoException();
                        reply.writeString(_result9);
                        return true;
                    case 28:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = true;
                        }
                        boolean reqHfpVoiceDial = reqHfpVoiceDial(_arg0);
                        reply.writeNoException();
                        reply.writeInt(reqHfpVoiceDial ? 1 : 0);
                        return true;
                    case 29:
                        data.enforceInterface(DESCRIPTOR);
                        pauseHfpRender();
                        reply.writeNoException();
                        return true;
                    case 30:
                        data.enforceInterface(DESCRIPTOR);
                        startHfpRender();
                        reply.writeNoException();
                        return true;
                    case 31:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpMicMute = isHfpMicMute();
                        reply.writeNoException();
                        reply.writeInt(isHfpMicMute ? 1 : 0);
                        return true;
                    case 32:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = true;
                        }
                        muteHfpMic(_arg0);
                        reply.writeNoException();
                        return true;
                    case 33:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHfpInBandRingtoneSupport = isHfpInBandRingtoneSupport();
                        reply.writeNoException();
                        reply.writeInt(isHfpInBandRingtoneSupport ? 1 : 0);
                        return true;
                    case 34:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btAnswerType = getBtAnswerType();
                        reply.writeNoException();
                        reply.writeInt(btAnswerType ? 1 : 0);
                        return true;
                    case 35:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = true;
                        }
                        setBtAutoAnswerEnable(_arg0, data.readInt());
                        reply.writeNoException();
                        return true;
                    case 36:
                        data.enforceInterface(DESCRIPTOR);
                        boolean btAutoAnswerState = getBtAutoAnswerState();
                        reply.writeNoException();
                        reply.writeInt(btAutoAnswerState ? 1 : 0);
                        return true;
                    case 37:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = true;
                        }
                        setBtAnswerTypeEnable(_arg0);
                        reply.writeNoException();
                        return true;
                    case 38:
                        data.enforceInterface(DESCRIPTOR);
                        boolean threePartyCallEnable = getThreePartyCallEnable();
                        reply.writeNoException();
                        reply.writeInt(threePartyCallEnable ? 1 : 0);
                        return true;
                    case 39:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = true;
                        }
                        setThreePartyCallEnable(_arg0);
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
        public static class Proxy implements GocCommandHfp {
            public static GocCommandHfp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean registerHfpCallback(GocCallbackHfp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean unregisterHfpCallback(GocCallbackHfp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public int getHfpConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpConnected(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public String getHfpConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public int getHfpAudioConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public int getHfpRemoteSignalStrength() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public List<GocHfpClientCall> getHfpCallList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public List<GocHfpClientCall> getHfpCallList2() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpRemoteOnRoaming() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public int getHfpRemoteBatteryIndicator() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpDialCall(String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    boolean _result = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpReDial() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpMemoryDial(String index) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(index);
                    boolean _result = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpAnswerCall(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    boolean _result = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reqHfpAnswerCall(flag);
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpRejectIncomingCall(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpTerminateCurrentCall(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpSendDtmf(String address, String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(number);
                    boolean _result = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpAudioTransferToCarkit(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpAudioTransferToPhone(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public String getHfpRemoteNetworkOperator() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public String getHfpRemoteSubscriberNumber() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean reqHfpVoiceDial(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(enable ? 1 : 0);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public void pauseHfpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public void startHfpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpMicMute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public void muteHfpMic(boolean mute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mute ? 1 : 0);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean isHfpInBandRingtoneSupport() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean getBtAnswerType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public void setBtAutoAnswerEnable(boolean isBtAutoAnswerEnable, int time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isBtAutoAnswerEnable ? 1 : 0);
                    _data.writeInt(time);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean getBtAutoAnswerState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public void setBtAnswerTypeEnable(boolean isBtAnswerTypeEnable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isBtAnswerTypeEnable ? 1 : 0);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public boolean getThreePartyCallEnable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHfp
            public void setThreePartyCallEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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
        }

        public static boolean setDefaultImpl(GocCommandHfp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandHfp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
