package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackOpp;

public interface GocCommandOpp extends IInterface {
    String getOppFilePath() throws RemoteException;

    boolean isOppServiceReady() throws RemoteException;

    boolean registerOppCallback(GocCallbackOpp gocCallbackOpp) throws RemoteException;

    boolean reqOppAcceptReceiveFile(boolean z) throws RemoteException;

    boolean reqOppInterruptReceiveFile() throws RemoteException;

    boolean setOppFilePath(String str) throws RemoteException;

    boolean unregisterOppCallback(GocCallbackOpp gocCallbackOpp) throws RemoteException;

    public static class Default implements GocCommandOpp {
        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public boolean isOppServiceReady() throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public boolean registerOppCallback(GocCallbackOpp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public boolean unregisterOppCallback(GocCallbackOpp cb) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public boolean setOppFilePath(String path) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public String getOppFilePath() throws RemoteException {
            return null;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public boolean reqOppAcceptReceiveFile(boolean accept) throws RemoteException {
            return false;
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
        public boolean reqOppInterruptReceiveFile() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCommandOpp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCommandOpp";
        static final int TRANSACTION_getOppFilePath = 5;
        static final int TRANSACTION_isOppServiceReady = 1;
        static final int TRANSACTION_registerOppCallback = 2;
        static final int TRANSACTION_reqOppAcceptReceiveFile = 6;
        static final int TRANSACTION_reqOppInterruptReceiveFile = 7;
        static final int TRANSACTION_setOppFilePath = 4;
        static final int TRANSACTION_unregisterOppCallback = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCommandOpp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCommandOpp)) {
                return new Proxy(obj);
            }
            return (GocCommandOpp) iin;
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
                        boolean isOppServiceReady = isOppServiceReady();
                        reply.writeNoException();
                        reply.writeInt(isOppServiceReady ? 1 : 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean registerOppCallback = registerOppCallback(GocCallbackOpp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(registerOppCallback ? 1 : 0);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean unregisterOppCallback = unregisterOppCallback(GocCallbackOpp.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(unregisterOppCallback ? 1 : 0);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        boolean oppFilePath = setOppFilePath(data.readString());
                        reply.writeNoException();
                        reply.writeInt(oppFilePath ? 1 : 0);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        String _result = getOppFilePath();
                        reply.writeNoException();
                        reply.writeString(_result);
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqOppAcceptReceiveFile = reqOppAcceptReceiveFile(data.readInt() != 0);
                        reply.writeNoException();
                        reply.writeInt(reqOppAcceptReceiveFile ? 1 : 0);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        boolean reqOppInterruptReceiveFile = reqOppInterruptReceiveFile();
                        reply.writeNoException();
                        reply.writeInt(reqOppInterruptReceiveFile ? 1 : 0);
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
        public static class Proxy implements GocCommandOpp {
            public static GocCommandOpp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public boolean isOppServiceReady() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public boolean registerOppCallback(GocCallbackOpp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public boolean unregisterOppCallback(GocCallbackOpp cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    boolean _result = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public boolean setOppFilePath(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    boolean _result = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public String getOppFilePath() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public boolean reqOppAcceptReceiveFile(boolean accept) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    _data.writeInt(accept ? 1 : 0);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCommandOpp
            public boolean reqOppInterruptReceiveFile() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
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
        }

        public static boolean setDefaultImpl(GocCommandOpp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCommandOpp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
