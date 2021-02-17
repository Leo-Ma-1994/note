package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface UiCallbackHfp extends IInterface {
    void onHfpAudioStateChanged(String str, int i, int i2) throws RemoteException;

    void onHfpCallChanged(String str, GocHfpClientCall gocHfpClientCall) throws RemoteException;

    void onHfpCallingTimeChanged(String str) throws RemoteException;

    void onHfpErrorResponse(String str, int i) throws RemoteException;

    void onHfpRemoteBatteryIndicator(String str, int i, int i2, int i3) throws RemoteException;

    void onHfpRemoteRoamingStatus(String str, boolean z) throws RemoteException;

    void onHfpRemoteSignalStrength(String str, int i, int i2, int i3) throws RemoteException;

    void onHfpRemoteTelecomService(String str, boolean z) throws RemoteException;

    void onHfpServiceReady() throws RemoteException;

    void onHfpStateChanged(String str, int i, int i2) throws RemoteException;

    void onHfpVoiceDial(String str, boolean z) throws RemoteException;

    void retPbapDatabaseQueryNameByNumber(String str, String str2, String str3, boolean z) throws RemoteException;

    public static class Default implements UiCallbackHfp {
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpAudioStateChanged(String address, int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpVoiceDial(String address, boolean isVoiceDialOn) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpErrorResponse(String address, int code) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteRoamingStatus(String address, boolean isRoamingOn) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpCallChanged(String address, GocHfpClientCall call) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void onHfpCallingTimeChanged(String time) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
        public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements UiCallbackHfp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.UiCallbackHfp";
        static final int TRANSACTION_onHfpAudioStateChanged = 3;
        static final int TRANSACTION_onHfpCallChanged = 10;
        static final int TRANSACTION_onHfpCallingTimeChanged = 11;
        static final int TRANSACTION_onHfpErrorResponse = 5;
        static final int TRANSACTION_onHfpRemoteBatteryIndicator = 8;
        static final int TRANSACTION_onHfpRemoteRoamingStatus = 7;
        static final int TRANSACTION_onHfpRemoteSignalStrength = 9;
        static final int TRANSACTION_onHfpRemoteTelecomService = 6;
        static final int TRANSACTION_onHfpServiceReady = 1;
        static final int TRANSACTION_onHfpStateChanged = 2;
        static final int TRANSACTION_onHfpVoiceDial = 4;
        static final int TRANSACTION_retPbapDatabaseQueryNameByNumber = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCallbackHfp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof UiCallbackHfp)) {
                return new Proxy(obj);
            }
            return (UiCallbackHfp) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            GocHfpClientCall _arg1;
            if (code != 1598968902) {
                boolean _arg3 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpStateChanged(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpAudioStateChanged(data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        onHfpVoiceDial(_arg0, _arg3);
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpErrorResponse(data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        onHfpRemoteTelecomService(_arg02, _arg3);
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        onHfpRemoteRoamingStatus(_arg03, _arg3);
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpRemoteBatteryIndicator(data.readString(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpRemoteSignalStrength(data.readString(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = GocHfpClientCall.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        onHfpCallChanged(_arg04, _arg1);
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        onHfpCallingTimeChanged(data.readString());
                        reply.writeNoException();
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg05 = data.readString();
                        String _arg12 = data.readString();
                        String _arg2 = data.readString();
                        if (data.readInt() != 0) {
                            _arg3 = true;
                        }
                        retPbapDatabaseQueryNameByNumber(_arg05, _arg12, _arg2, _arg3);
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
        public static class Proxy implements UiCallbackHfp {
            public static UiCallbackHfp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onHfpServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpStateChanged(String address, int prevState, int newState) throws RemoteException {
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
                    Stub.getDefaultImpl().onHfpStateChanged(address, prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpAudioStateChanged(String address, int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpAudioStateChanged(address, prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpVoiceDial(String address, boolean isVoiceDialOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(isVoiceDialOn ? 1 : 0);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpVoiceDial(address, isVoiceDialOn);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpErrorResponse(String address, int code) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(code);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpErrorResponse(address, code);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpRemoteTelecomService(String address, boolean isTelecomServiceOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(isTelecomServiceOn ? 1 : 0);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpRemoteTelecomService(address, isTelecomServiceOn);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpRemoteRoamingStatus(String address, boolean isRoamingOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(isRoamingOn ? 1 : 0);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpRemoteRoamingStatus(address, isRoamingOn);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpRemoteBatteryIndicator(String address, int currentValue, int maxValue, int minValue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(currentValue);
                    _data.writeInt(maxValue);
                    _data.writeInt(minValue);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpRemoteBatteryIndicator(address, currentValue, maxValue, minValue);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpRemoteSignalStrength(String address, int currentStrength, int maxStrength, int minStrength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(currentStrength);
                    _data.writeInt(maxStrength);
                    _data.writeInt(minStrength);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpRemoteSignalStrength(address, currentStrength, maxStrength, minStrength);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpCallChanged(String address, GocHfpClientCall call) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (call != null) {
                        _data.writeInt(1);
                        call.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpCallChanged(address, call);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void onHfpCallingTimeChanged(String time) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(time);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHfpCallingTimeChanged(time);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackHfp
            public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(target);
                    _data.writeString(name);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPbapDatabaseQueryNameByNumber(address, target, name, isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(UiCallbackHfp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static UiCallbackHfp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
