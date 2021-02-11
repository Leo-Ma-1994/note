package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

public interface GocCallbackGattServer extends IInterface {
    void onGattServerCharacteristicReadRequest(String str, int i, int i2, boolean z, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2) throws RemoteException;

    void onGattServerCharacteristicWriteRequest(String str, int i, int i2, boolean z, boolean z2, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, byte[] bArr) throws RemoteException;

    void onGattServerDescriptorReadRequest(String str, int i, int i2, boolean z, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3) throws RemoteException;

    void onGattServerDescriptorWriteRequest(String str, int i, int i2, boolean z, boolean z2, int i3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr) throws RemoteException;

    void onGattServerExecuteWrite(String str, int i, boolean z) throws RemoteException;

    void onGattServerServiceAdded(int i, int i2, ParcelUuid parcelUuid) throws RemoteException;

    void onGattServerServiceDeleted(int i, int i2, ParcelUuid parcelUuid) throws RemoteException;

    void onGattServerStateChanged(String str, int i) throws RemoteException;

    void onGattServiceReady() throws RemoteException;

    public static class Default implements GocCallbackGattServer {
        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerStateChanged(String address, int state) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerCharacteristicReadRequest(String address, int requestId, int offset, boolean isLong, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerCharacteristicWriteRequest(String address, int requestId, int offset, boolean preparedWrite, boolean responseNeeded, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, byte[] value) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerDescriptorReadRequest(String address, int requestId, int offset, boolean isLong, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, ParcelUuid descrUuid) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerDescriptorWriteRequest(String address, int requestId, int offset, boolean isPrep, boolean responseNeeded, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, ParcelUuid descrUuid, byte[] value) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
        public void onGattServerExecuteWrite(String address, int requestId, boolean execute) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCallbackGattServer {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCallbackGattServer";
        static final int TRANSACTION_onGattServerCharacteristicReadRequest = 5;
        static final int TRANSACTION_onGattServerCharacteristicWriteRequest = 6;
        static final int TRANSACTION_onGattServerDescriptorReadRequest = 7;
        static final int TRANSACTION_onGattServerDescriptorWriteRequest = 8;
        static final int TRANSACTION_onGattServerExecuteWrite = 9;
        static final int TRANSACTION_onGattServerServiceAdded = 3;
        static final int TRANSACTION_onGattServerServiceDeleted = 4;
        static final int TRANSACTION_onGattServerStateChanged = 2;
        static final int TRANSACTION_onGattServiceReady = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCallbackGattServer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCallbackGattServer)) {
                return new Proxy(obj);
            }
            return (GocCallbackGattServer) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParcelUuid _arg2;
            ParcelUuid _arg22;
            ParcelUuid _arg5;
            ParcelUuid _arg6;
            ParcelUuid _arg62;
            ParcelUuid _arg7;
            ParcelUuid _arg52;
            ParcelUuid _arg63;
            ParcelUuid _arg72;
            ParcelUuid _arg64;
            ParcelUuid _arg73;
            ParcelUuid _arg8;
            if (code != 1598968902) {
                boolean _arg23 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onGattServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onGattServerStateChanged(data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg0 = data.readInt();
                        int _arg1 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg2 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg2 = null;
                        }
                        onGattServerServiceAdded(_arg0, _arg1, _arg2);
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg02 = data.readInt();
                        int _arg12 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg22 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg22 = null;
                        }
                        onGattServerServiceDeleted(_arg02, _arg12, _arg22);
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        int _arg13 = data.readInt();
                        int _arg24 = data.readInt();
                        boolean _arg3 = data.readInt() != 0;
                        int _arg4 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg5 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg5 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg6 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg6 = null;
                        }
                        onGattServerCharacteristicReadRequest(_arg03, _arg13, _arg24, _arg3, _arg4, _arg5, _arg6);
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        int _arg14 = data.readInt();
                        int _arg25 = data.readInt();
                        boolean _arg32 = data.readInt() != 0;
                        boolean _arg42 = data.readInt() != 0;
                        int _arg53 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg62 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg62 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg7 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg7 = null;
                        }
                        onGattServerCharacteristicWriteRequest(_arg04, _arg14, _arg25, _arg32, _arg42, _arg53, _arg62, _arg7, data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg05 = data.readString();
                        int _arg15 = data.readInt();
                        int _arg26 = data.readInt();
                        boolean _arg33 = data.readInt() != 0;
                        int _arg43 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg52 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg52 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg63 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg63 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg72 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg72 = null;
                        }
                        onGattServerDescriptorReadRequest(_arg05, _arg15, _arg26, _arg33, _arg43, _arg52, _arg63, _arg72);
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg06 = data.readString();
                        int _arg16 = data.readInt();
                        int _arg27 = data.readInt();
                        boolean _arg34 = data.readInt() != 0;
                        boolean _arg44 = data.readInt() != 0;
                        int _arg54 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg64 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg64 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg73 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg73 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg8 = (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(data);
                        } else {
                            _arg8 = null;
                        }
                        onGattServerDescriptorWriteRequest(_arg06, _arg16, _arg27, _arg34, _arg44, _arg54, _arg64, _arg73, _arg8, data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg07 = data.readString();
                        int _arg17 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg23 = true;
                        }
                        onGattServerExecuteWrite(_arg07, _arg17, _arg23);
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
        public static class Proxy implements GocCallbackGattServer {
            public static GocCallbackGattServer sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onGattServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerStateChanged(String address, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(state);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onGattServerStateChanged(address, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerServiceAdded(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(srvcType);
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onGattServerServiceAdded(status, srvcType, srvcUuid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerServiceDeleted(int status, int srvcType, ParcelUuid srvcUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(srvcType);
                    if (srvcUuid != null) {
                        _data.writeInt(1);
                        srvcUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onGattServerServiceDeleted(status, srvcType, srvcUuid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerCharacteristicReadRequest(String address, int requestId, int offset, boolean isLong, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
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
                            _data.writeInt(offset);
                            _data.writeInt(isLong ? 1 : 0);
                            _data.writeInt(srvcType);
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
                            if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().onGattServerCharacteristicReadRequest(address, requestId, offset, isLong, srvcType, srvcUuid, charUuid);
                            _reply.recycle();
                            _data.recycle();
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
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerCharacteristicWriteRequest(String address, int requestId, int offset, boolean preparedWrite, boolean responseNeeded, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, byte[] value) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                        _data.writeInt(requestId);
                        _data.writeInt(offset);
                        _data.writeInt(preparedWrite ? 1 : 0);
                        _data.writeInt(responseNeeded ? 1 : 0);
                        _data.writeInt(srvcType);
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
                        _data.writeByteArray(value);
                        if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().onGattServerCharacteristicWriteRequest(address, requestId, offset, preparedWrite, responseNeeded, srvcType, srvcUuid, charUuid, value);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerDescriptorReadRequest(String address, int requestId, int offset, boolean isLong, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, ParcelUuid descrUuid) throws RemoteException {
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(address);
                        _data.writeInt(requestId);
                        _data.writeInt(offset);
                        _data.writeInt(isLong ? 1 : 0);
                        _data.writeInt(srvcType);
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
                        if (descrUuid != null) {
                            _data.writeInt(1);
                            descrUuid.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().onGattServerDescriptorReadRequest(address, requestId, offset, isLong, srvcType, srvcUuid, charUuid, descrUuid);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerDescriptorWriteRequest(String address, int requestId, int offset, boolean isPrep, boolean responseNeeded, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, ParcelUuid descrUuid, byte[] value) throws RemoteException {
                Parcel _reply;
                Throwable th;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(requestId);
                    _data.writeInt(offset);
                    _data.writeInt(isPrep ? 1 : 0);
                    _data.writeInt(responseNeeded ? 1 : 0);
                    _data.writeInt(srvcType);
                    if (srvcUuid != null) {
                        try {
                            _data.writeInt(1);
                            srvcUuid.writeToParcel(_data, 0);
                        } catch (Throwable th2) {
                            th = th2;
                            _reply = _reply2;
                        }
                    } else {
                        _data.writeInt(0);
                    }
                    if (charUuid != null) {
                        _data.writeInt(1);
                        charUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (descrUuid != null) {
                        _data.writeInt(1);
                        descrUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeByteArray(value);
                    if (this.mRemote.transact(8, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply2.readException();
                        _reply2.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply = _reply2;
                    try {
                        Stub.getDefaultImpl().onGattServerDescriptorWriteRequest(address, requestId, offset, isPrep, responseNeeded, srvcType, srvcUuid, charUuid, descrUuid, value);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackGattServer
            public void onGattServerExecuteWrite(String address, int requestId, boolean execute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(requestId);
                    _data.writeInt(execute ? 1 : 0);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onGattServerExecuteWrite(address, requestId, execute);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCallbackGattServer impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCallbackGattServer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
