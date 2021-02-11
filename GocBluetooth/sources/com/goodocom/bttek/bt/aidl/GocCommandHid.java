package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackHid;

public interface GocCommandHid extends IInterface {
    String getHidConnectedAddress() throws RemoteException;

    int getHidConnectionState() throws RemoteException;

    boolean isHidConnected() throws RemoteException;

    boolean isHidServiceReady() throws RemoteException;

    boolean registerHidCallback(GocCallbackHid gocCallbackHid) throws RemoteException;

    boolean reqHidConnect(String str) throws RemoteException;

    boolean reqHidDisconnect(String str) throws RemoteException;

    boolean reqSendHidMouseCommand(int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqSendHidVirtualKeyCommand(int i, int i2) throws RemoteException;

    boolean unregisterHidCallback(GocCallbackHid gocCallbackHid) throws RemoteException;

    public static class Default implements GocCommandHid {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean isHidServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean registerHidCallback(GocCallbackHid cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean unregisterHidCallback(GocCallbackHid cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean reqHidConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean reqHidDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean reqSendHidMouseCommand(int button, int offset_x, int offset_y, int wheel) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean reqSendHidVirtualKeyCommand(int key_1, int key_2) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public int getHidConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public boolean isHidConnected() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
        public String getHidConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandHid {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandHid";
        static final int TRANSACTION_getHidConnectedAddress = 10;
        static final int TRANSACTION_getHidConnectionState = 8;
        static final int TRANSACTION_isHidConnected = 9;
        static final int TRANSACTION_isHidServiceReady = 1;
        static final int TRANSACTION_registerHidCallback = 2;
        static final int TRANSACTION_reqHidConnect = 4;
        static final int TRANSACTION_reqHidDisconnect = 5;
        static final int TRANSACTION_reqSendHidMouseCommand = 6;
        static final int TRANSACTION_reqSendHidVirtualKeyCommand = 7;
        static final int TRANSACTION_unregisterHidCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandHid asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandHid)) {
                return new Proxy(obj);
            }
            return (GocCommandHid) iin;
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
                        boolean isHidServiceReady = isHidServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isHidServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerHidCallback = registerHidCallback(GocCallbackHid.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerHidCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterHidCallback = unregisterHidCallback(GocCallbackHid.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterHidCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHidConnect = reqHidConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHidConnect ? 1 : 0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqHidDisconnect = reqHidDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqHidDisconnect ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqSendHidMouseCommand = reqSendHidMouseCommand(data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqSendHidMouseCommand ? 1 : 0);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqSendHidVirtualKeyCommand = reqSendHidVirtualKeyCommand(data.readInt(), data.readInt());
                        reply.writeNoException();
                        reply.writeInt(reqSendHidVirtualKeyCommand ? 1 : 0);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        int _result = getHidConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isHidConnected = isHidConnected();
                        reply.writeNoException();
                        reply.writeInt(isHidConnected ? 1 : 0);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        String _result2 = getHidConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result2);
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
        public static class Proxy implements GocCommandHid {
            public static GocCommandHid sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean isHidServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean registerHidCallback(GocCallbackHid cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean unregisterHidCallback(GocCallbackHid cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean reqHidConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean reqHidDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
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
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean reqSendHidVirtualKeyCommand(int key_1, int key_2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key_1);
                    _data.writeInt(key_2);
                    boolean _result = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public int getHidConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public boolean isHidConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandHid
            public String getHidConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
        }

        public static boolean setDefaultImpl(GocCommandHid impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandHid getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
