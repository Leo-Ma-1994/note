package com.goodocom.gocsdkserver;

import android.os.ParcelUuid;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackGattServer;
import com.goodocom.bttek.bt.aidl.GocCommandGattServer;
import java.util.List;

public class CommandGattServerImp extends GocCommandGattServer.Stub {
    public static final int STATE_CONNECTED = 140;
    public static final int STATE_LISTENING = 130;
    public static final int STATE_NOT_INITIALIZED = 100;
    public static final int STATE_READY = 110;
    static RemoteCallbackList<GocCallbackGattServer> callbacks;
    private GocsdkService service;

    public CommandGattServerImp(GocsdkService service2) {
        this.service = service2;
        callbacks = new RemoteCallbackList<>();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean isGattServiceReady() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean registerGattServerCallback(GocCallbackGattServer cb) throws RemoteException {
        return callbacks.register(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean unregisterGattServerCallback(GocCallbackGattServer cb) throws RemoteException {
        return callbacks.unregister(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public int getGattServerConnectionState() throws RemoteException {
        return 100;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerDisconnect(String address) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerBeginServiceDeclaration(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerAddCharacteristic(ParcelUuid charUuid, int properties, int permissions) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerAddDescriptor(ParcelUuid descUuid, int permissions) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerRemoveService(int srvcType, ParcelUuid srvcUuid) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerClearServices() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerListen(boolean listen) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerSendResponse(String address, int requestId, int status, int offset, byte[] value) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public boolean reqGattServerSendNotification(String address, int srvcType, ParcelUuid srvcUuid, ParcelUuid charUuid, boolean confirm, byte[] value) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
        return null;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid srvcUuid) throws RemoteException {
        return null;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandGattServer
    public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid srvcUuid, ParcelUuid charUuid) throws RemoteException {
        return null;
    }
}
