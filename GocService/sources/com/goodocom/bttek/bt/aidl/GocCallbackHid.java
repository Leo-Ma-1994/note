package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface GocCallbackHid extends IInterface {
    void onHidServiceReady() throws RemoteException;

    void onHidStateChanged(String str, int i, int i2, int i3) throws RemoteException;

    public static class Default implements GocCallbackHid {
        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHid
        public void onHidServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackHid
        public void onHidStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCallbackHid {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCallbackHid";
        static final int TRANSACTION_onHidServiceReady = 1;
        static final int TRANSACTION_onHidStateChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCallbackHid asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCallbackHid)) {
                return new Proxy(obj);
            }
            return (GocCallbackHid) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onHidServiceReady();
                reply.writeNoException();
                return true;
            } else if (code == 2) {
                data.enforceInterface(DESCRIPTOR);
                onHidStateChanged(data.readString(), data.readInt(), data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            } else if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public static class Proxy implements GocCallbackHid {
            public static GocCallbackHid sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackHid
            public void onHidServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onHidServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackHid
            public void onHidStateChanged(String address, int prevState, int newState, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onHidStateChanged(address, prevState, newState, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCallbackHid impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCallbackHid getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
