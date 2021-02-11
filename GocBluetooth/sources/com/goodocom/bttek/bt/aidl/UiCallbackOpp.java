package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface UiCallbackOpp extends IInterface {
    void onOppReceiveFileInfo(String str, int i, String str2, String str3) throws RemoteException;

    void onOppReceiveProgress(int i) throws RemoteException;

    void onOppServiceReady() throws RemoteException;

    void onOppStateChanged(String str, int i, int i2, int i3) throws RemoteException;

    public static class Default implements UiCallbackOpp {
        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppStateChanged(String address, int preState, int currentState, int reason) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
        public void onOppReceiveProgress(int receivedOffset) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements UiCallbackOpp {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.UiCallbackOpp";
        static final int TRANSACTION_onOppReceiveFileInfo = 3;
        static final int TRANSACTION_onOppReceiveProgress = 4;
        static final int TRANSACTION_onOppServiceReady = 1;
        static final int TRANSACTION_onOppStateChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCallbackOpp asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof UiCallbackOpp)) {
                return new Proxy(obj);
            }
            return (UiCallbackOpp) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onOppServiceReady();
                reply.writeNoException();
                return true;
            } else if (code == 2) {
                data.enforceInterface(DESCRIPTOR);
                onOppStateChanged(data.readString(), data.readInt(), data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            } else if (code == 3) {
                data.enforceInterface(DESCRIPTOR);
                onOppReceiveFileInfo(data.readString(), data.readInt(), data.readString(), data.readString());
                reply.writeNoException();
                return true;
            } else if (code == 4) {
                data.enforceInterface(DESCRIPTOR);
                onOppReceiveProgress(data.readInt());
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
        public static class Proxy implements UiCallbackOpp {
            public static UiCallbackOpp sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
            public void onOppServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onOppServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
            public void onOppStateChanged(String address, int preState, int currentState, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(preState);
                    _data.writeInt(currentState);
                    _data.writeInt(reason);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onOppStateChanged(address, preState, currentState, reason);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
            public void onOppReceiveFileInfo(String fileName, int fileSize, String deviceName, String savePath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fileName);
                    _data.writeInt(fileSize);
                    _data.writeString(deviceName);
                    _data.writeString(savePath);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onOppReceiveFileInfo(fileName, fileSize, deviceName, savePath);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.UiCallbackOpp
            public void onOppReceiveProgress(int receivedOffset) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(receivedOffset);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onOppReceiveProgress(receivedOffset);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(UiCallbackOpp impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static UiCallbackOpp getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
