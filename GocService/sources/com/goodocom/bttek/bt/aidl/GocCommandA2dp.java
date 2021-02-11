package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackA2dp;

public interface GocCommandA2dp extends IInterface {
    String getA2dpConnectedAddress() throws RemoteException;

    int getA2dpConnectionState() throws RemoteException;

    int getA2dpStreamType() throws RemoteException;

    byte getPlayStatus() throws RemoteException;

    boolean isA2dpConnected() throws RemoteException;

    boolean isA2dpServiceReady() throws RemoteException;

    void pauseA2dpRender() throws RemoteException;

    boolean registerA2dpCallback(GocCallbackA2dp gocCallbackA2dp) throws RemoteException;

    boolean reqA2dpConnect(String str) throws RemoteException;

    boolean reqA2dpDisconnect(String str) throws RemoteException;

    boolean setA2dpLocalVolume(float f) throws RemoteException;

    boolean setA2dpStreamType(int i) throws RemoteException;

    void startA2dpRender() throws RemoteException;

    boolean unregisterA2dpCallback(GocCallbackA2dp gocCallbackA2dp) throws RemoteException;

    public static class Default implements GocCommandA2dp {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean isA2dpServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean registerA2dpCallback(GocCallbackA2dp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean unregisterA2dpCallback(GocCallbackA2dp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public int getA2dpConnectionState() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean isA2dpConnected() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public String getA2dpConnectedAddress() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean reqA2dpConnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean reqA2dpDisconnect(String address) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public void pauseA2dpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public void startA2dpRender() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean setA2dpLocalVolume(float vol) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public boolean setA2dpStreamType(int type) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public int getA2dpStreamType() throws RemoteException {
            return 0;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
        public byte getPlayStatus() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandA2dp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandA2dp";
        static final int TRANSACTION_getA2dpConnectedAddress = 6;
        static final int TRANSACTION_getA2dpConnectionState = 4;
        static final int TRANSACTION_getA2dpStreamType = 13;
        static final int TRANSACTION_getPlayStatus = 14;
        static final int TRANSACTION_isA2dpConnected = 5;
        static final int TRANSACTION_isA2dpServiceReady = 1;
        static final int TRANSACTION_pauseA2dpRender = 9;
        static final int TRANSACTION_registerA2dpCallback = 2;
        static final int TRANSACTION_reqA2dpConnect = 7;
        static final int TRANSACTION_reqA2dpDisconnect = 8;
        static final int TRANSACTION_setA2dpLocalVolume = 11;
        static final int TRANSACTION_setA2dpStreamType = 12;
        static final int TRANSACTION_startA2dpRender = 10;
        static final int TRANSACTION_unregisterA2dpCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandA2dp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandA2dp)) {
                return new Proxy(obj);
            }
            return (GocCommandA2dp) iin;
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
                        boolean isA2dpServiceReady = isA2dpServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isA2dpServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerA2dpCallback = registerA2dpCallback(GocCallbackA2dp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerA2dpCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterA2dpCallback = unregisterA2dpCallback(GocCallbackA2dp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterA2dpCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        int _result = getA2dpConnectionState();
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        boolean isA2dpConnected = isA2dpConnected();
                        reply.writeNoException();
                        reply.writeInt(isA2dpConnected ? 1 : 0);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _result2 = getA2dpConnectedAddress();
                        reply.writeNoException();
                        reply.writeString(_result2);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqA2dpConnect = reqA2dpConnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqA2dpConnect ? 1 : 0);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqA2dpDisconnect = reqA2dpDisconnect(data.readString());
                        reply.writeNoException();
                        reply.writeInt(reqA2dpDisconnect ? 1 : 0);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        pauseA2dpRender();
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        startA2dpRender();
                        reply.writeNoException();
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean a2dpLocalVolume = setA2dpLocalVolume(data.readFloat());
                        reply.writeNoException();
                        reply.writeInt(a2dpLocalVolume ? 1 : 0);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        boolean a2dpStreamType = setA2dpStreamType(data.readInt());
                        reply.writeNoException();
                        reply.writeInt(a2dpStreamType ? 1 : 0);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        int _result3 = getA2dpStreamType();
                        reply.writeNoException();
                        reply.writeInt(_result3);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        byte _result4 = getPlayStatus();
                        reply.writeNoException();
                        reply.writeByte(_result4);
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
        public static class Proxy implements GocCommandA2dp {
            public static GocCommandA2dp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean isA2dpServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean registerA2dpCallback(GocCallbackA2dp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean unregisterA2dpCallback(GocCallbackA2dp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public int getA2dpConnectionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean isA2dpConnected() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isA2dpConnected();
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public String getA2dpConnectedAddress() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean reqA2dpConnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean reqA2dpDisconnect(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    boolean _result = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public void pauseA2dpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public void startA2dpRender() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean setA2dpLocalVolume(float vol) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(vol);
                    boolean _result = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public boolean setA2dpStreamType(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _result = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public int getA2dpStreamType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
            public byte getPlayStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
        }

        public static boolean setDefaultImpl(GocCommandA2dp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandA2dp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
