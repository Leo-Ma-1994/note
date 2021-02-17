package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface GocCallbackSpp extends IInterface {
    void onSppAppleIapAuthenticationRequest(String str) throws RemoteException;

    void onSppDataReceived(String str, byte[] bArr) throws RemoteException;

    void onSppErrorResponse(String str, int i) throws RemoteException;

    void onSppSendData(String str, int i) throws RemoteException;

    void onSppServiceReady() throws RemoteException;

    void onSppStateChanged(String str, String str2, int i, int i2) throws RemoteException;

    void retSppConnectedDeviceAddressList(int i, String[] strArr, String[] strArr2) throws RemoteException;

    public static class Default implements GocCallbackSpp {
        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppStateChanged(String address, String deviceName, int prevState, int newState) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppErrorResponse(String address, int errorCode) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void retSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppDataReceived(String address, byte[] receivedData) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppSendData(String address, int length) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
        public void onSppAppleIapAuthenticationRequest(String address) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCallbackSpp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCallbackSpp";
        static final int TRANSACTION_onSppAppleIapAuthenticationRequest = 7;
        static final int TRANSACTION_onSppDataReceived = 5;
        static final int TRANSACTION_onSppErrorResponse = 3;
        static final int TRANSACTION_onSppSendData = 6;
        static final int TRANSACTION_onSppServiceReady = 1;
        static final int TRANSACTION_onSppStateChanged = 2;
        static final int TRANSACTION_retSppConnectedDeviceAddressList = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCallbackSpp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCallbackSpp)) {
                return new Proxy(obj);
            }
            return (GocCallbackSpp) iin;
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
                        onSppServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onSppStateChanged(data.readString(), data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onSppErrorResponse(data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        retSppConnectedDeviceAddressList(data.readInt(), data.createStringArray(), data.createStringArray());
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        onSppDataReceived(data.readString(), data.createByteArray());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        onSppSendData(data.readString(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        onSppAppleIapAuthenticationRequest(data.readString());
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
        public static class Proxy implements GocCallbackSpp {
            public static GocCallbackSpp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void onSppServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onSppServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void onSppStateChanged(String address, String deviceName, int prevState, int newState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(deviceName);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSppStateChanged(address, deviceName, prevState, newState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void onSppErrorResponse(String address, int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(errorCode);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSppErrorResponse(address, errorCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void retSppConnectedDeviceAddressList(int totalNum, String[] addressList, String[] nameList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(totalNum);
                    _data.writeStringArray(addressList);
                    _data.writeStringArray(nameList);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retSppConnectedDeviceAddressList(totalNum, addressList, nameList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void onSppDataReceived(String address, byte[] receivedData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeByteArray(receivedData);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSppDataReceived(address, receivedData);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void onSppSendData(String address, int length) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(length);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSppSendData(address, length);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackSpp
            public void onSppAppleIapAuthenticationRequest(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onSppAppleIapAuthenticationRequest(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCallbackSpp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCallbackSpp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
