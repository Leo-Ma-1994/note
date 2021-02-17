package com.goodocom.bttek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface GocCallbackPbap extends IInterface {
    void onPbapDownloadNotify(String str, int i, int i2, int i3) throws RemoteException;

    void onPbapServiceReady() throws RemoteException;

    void onPbapStateChanged(String str, int i, int i2, int i3, int i4) throws RemoteException;

    void retPbapCleanDatabaseCompleted(boolean z) throws RemoteException;

    void retPbapDatabaseAvailable(String str) throws RemoteException;

    void retPbapDatabaseQueryNameByNumber(String str, String str2, String str3, boolean z) throws RemoteException;

    void retPbapDatabaseQueryNameByPartialNumber(String str, String str2, String[] strArr, String[] strArr2, boolean z) throws RemoteException;

    void retPbapDeleteDatabaseByAddressCompleted(String str, boolean z) throws RemoteException;

    void retPbapDownloadedCallLog(String str, String str2, String str3, String str4, String str5, int i, String str6) throws RemoteException;

    void retPbapDownloadedContact(GocPbapContact gocPbapContact) throws RemoteException;

    public static class Default implements GocCallbackPbap {
        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void onPbapServiceReady() throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void onPbapStateChanged(String address, int prevState, int newState, int reason, int counts) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDownloadedContact(GocPbapContact contact) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void onPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDatabaseAvailable(String address) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
        }

        @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
        public void retPbapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements GocCallbackPbap {
        private static final String DESCRIPTOR = "com.goodocom.bttek.bt.aidl.GocCallbackPbap";
        static final int TRANSACTION_onPbapDownloadNotify = 5;
        static final int TRANSACTION_onPbapServiceReady = 1;
        static final int TRANSACTION_onPbapStateChanged = 2;
        static final int TRANSACTION_retPbapCleanDatabaseCompleted = 10;
        static final int TRANSACTION_retPbapDatabaseAvailable = 8;
        static final int TRANSACTION_retPbapDatabaseQueryNameByNumber = 6;
        static final int TRANSACTION_retPbapDatabaseQueryNameByPartialNumber = 7;
        static final int TRANSACTION_retPbapDeleteDatabaseByAddressCompleted = 9;
        static final int TRANSACTION_retPbapDownloadedCallLog = 4;
        static final int TRANSACTION_retPbapDownloadedContact = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static GocCallbackPbap asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof GocCallbackPbap)) {
                return new Proxy(obj);
            }
            return (GocCallbackPbap) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            GocPbapContact _arg0;
            if (code != 1598968902) {
                boolean _arg02 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        onPbapServiceReady();
                        reply.writeNoException();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onPbapStateChanged(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg0 = GocPbapContact.CREATOR.createFromParcel(data);
                        } else {
                            _arg0 = null;
                        }
                        retPbapDownloadedContact(_arg0);
                        reply.writeNoException();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        retPbapDownloadedCallLog(data.readString(), data.readString(), data.readString(), data.readString(), data.readString(), data.readInt(), data.readString());
                        reply.writeNoException();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        onPbapDownloadNotify(data.readString(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        String _arg1 = data.readString();
                        String _arg2 = data.readString();
                        if (data.readInt() != 0) {
                            _arg02 = true;
                        }
                        retPbapDatabaseQueryNameByNumber(_arg03, _arg1, _arg2, _arg02);
                        reply.writeNoException();
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        retPbapDatabaseQueryNameByPartialNumber(data.readString(), data.readString(), data.createStringArray(), data.createStringArray(), data.readInt() != 0);
                        reply.writeNoException();
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        retPbapDatabaseAvailable(data.readString());
                        reply.writeNoException();
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        if (data.readInt() != 0) {
                            _arg02 = true;
                        }
                        retPbapDeleteDatabaseByAddressCompleted(_arg04, _arg02);
                        reply.writeNoException();
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        if (data.readInt() != 0) {
                            _arg02 = true;
                        }
                        retPbapCleanDatabaseCompleted(_arg02);
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
        public static class Proxy implements GocCallbackPbap {
            public static GocCallbackPbap sDefaultImpl;
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

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void onPbapServiceReady() throws RemoteException {
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
                    Stub.getDefaultImpl().onPbapServiceReady();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void onPbapStateChanged(String address, int prevState, int newState, int reason, int counts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(prevState);
                    _data.writeInt(newState);
                    _data.writeInt(reason);
                    _data.writeInt(counts);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onPbapStateChanged(address, prevState, newState, reason, counts);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapDownloadedContact(GocPbapContact contact) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (contact != null) {
                        _data.writeInt(1);
                        contact.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPbapDownloadedContact(contact);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapDownloadedCallLog(String address, String firstName, String middleName, String lastName, String number, int type, String timestamp) throws RemoteException {
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
                        _data.writeString(firstName);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(middleName);
                        try {
                            _data.writeString(lastName);
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(number);
                            _data.writeInt(type);
                            _data.writeString(timestamp);
                            if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().retPbapDownloadedCallLog(address, firstName, middleName, lastName, number, type, timestamp);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th5) {
                            th = th5;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void onPbapDownloadNotify(String address, int storage, int totalContacts, int downloadedContacts) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(storage);
                    _data.writeInt(totalContacts);
                    _data.writeInt(downloadedContacts);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onPbapDownloadNotify(address, storage, totalContacts, downloadedContacts);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapDatabaseQueryNameByNumber(String address, String target, String name, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(target);
                    _data.writeString(name);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
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

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapDatabaseQueryNameByPartialNumber(String address, String target, String[] names, String[] numbers, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(target);
                    _data.writeStringArray(names);
                    _data.writeStringArray(numbers);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPbapDatabaseQueryNameByPartialNumber(address, target, names, numbers, isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapDatabaseAvailable(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPbapDatabaseAvailable(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapDeleteDatabaseByAddressCompleted(String address, boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPbapDeleteDatabaseByAddressCompleted(address, isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.goodocom.bttek.bt.aidl.GocCallbackPbap
            public void retPbapCleanDatabaseCompleted(boolean isSuccess) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isSuccess ? 1 : 0);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().retPbapCleanDatabaseCompleted(isSuccess);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(GocCallbackPbap impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static GocCallbackPbap getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
