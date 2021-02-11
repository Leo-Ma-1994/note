package com.goodocom.gocsdkserver;

import android.os.RemoteException;
import com.goodocom.bttek.bt.aidl.GocCallbackSpp;
import com.goodocom.bttek.bt.aidl.GocCommandSpp;

public class CommandSppImp extends GocCommandSpp.Stub {
    private GocsdkService service;

    public CommandSppImp(GocsdkService service2) {
        this.service = service2;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public boolean isSppServiceReady() throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public boolean registerSppCallback(GocCallbackSpp cb) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public boolean unregisterSppCallback(GocCallbackSpp cb) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public boolean reqSppConnect(String address) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public boolean reqSppDisconnect(String address) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public void reqSppConnectedDeviceAddressList() throws RemoteException {
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public boolean isSppConnected(String address) throws RemoteException {
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandSpp
    public void reqSppSendData(String address, byte[] sppData) throws RemoteException {
    }
}
