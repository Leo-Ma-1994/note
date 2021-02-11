package com.goodocom.gocsdkserver;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.bttek.bt.aidl.GocCallbackA2dp;
import com.goodocom.bttek.bt.aidl.GocCommandA2dp;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.List;

public class CommandA2dpImp extends GocCommandA2dp.Stub {
    public static final int A2DP_CONNECTED = 2;
    private static final int A2DP_CONNECT_DELAY = 6;
    public static final int A2DP_DISCONNECTED = 1;
    public static final int A2DP_STREAMING = 3;
    private static final int BT_CONNECT_HFP = 2;
    private static final int BT_DISCONNECT_A2DP = 5;
    public static final int STATE_CONNECTED = 140;
    public static final int STATE_CONNECTING = 120;
    public static final int STATE_DISCONNECTING = 125;
    public static final int STATE_NOT_INITIALIZED = 100;
    public static final int STATE_READY = 110;
    public static final int STATE_STREAMING = 150;
    private static final String TAG = "GoodocomA2dpImp";
    private Handler a2dpHandler = new Handler() {
        /* class com.goodocom.gocsdkserver.CommandA2dpImp.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                String address = (String) msg.obj;
                DeviceInfo deviceInfo = CommandA2dpImp.this.service.bluetooth.getDeviceByAddr(address);
                DeviceInfo[] deviceInfos = CommandA2dpImp.this.service.bluetooth.loopFindConnectDevices();
                if ((deviceInfos[0] == deviceInfo || deviceInfos[1] == deviceInfo) && deviceInfo != null && CommandA2dpImp.this.service.bluetooth.isProfileConnectedByAddr(address, "A2DP") && !CommandA2dpImp.this.service.bluetooth.isProfileConnectedByAddr(address, "HFP") && deviceInfo.hf_state < 2) {
                    int i = deviceInfo.hf_state;
                    CommandHfpImp commandHfpImp = CommandA2dpImp.this.service.hfp;
                    if (i < 2) {
                        CommandA2dpImp.this.service.getCommand().connectHFP(address);
                    }
                }
            } else if (msg.what == 5) {
                String address2 = (String) msg.obj;
                if (CommandA2dpImp.this.service.bluetooth.isProfileConnectedByAddr(address2, "A2DP")) {
                    CommandA2dpImp.this.service.getCommand().disconnectA2DP(address2);
                }
            } else if (msg.what == 6) {
                CommandA2dpImp.this.service.getCommand().connectA2dp((String) msg.obj);
            }
        }
    };
    private float a2dp_volume = 1.0f;
    private RemoteCallbackList<GocCallbackA2dp> callbacks;
    private Object callbacksLock = new Object();
    public int currentStatus = 1;
    private GocsdkService service;
    private boolean switchA2dp = false;

    private int status2State(int status) {
        if (status == 1) {
            return 110;
        }
        if (status == 2) {
            return 140;
        }
        if (status != 3) {
            return 110;
        }
        return 150;
    }

    private int status2pre(int status) {
        if (status == 1) {
            return 140;
        }
        if (status == 2) {
            return 120;
        }
        if (status != 3) {
            return 110;
        }
        return 140;
    }

    public CommandA2dpImp(GocsdkService service2) {
        this.service = service2;
        this.callbacks = new RemoteCallbackList<>();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean isA2dpServiceReady() throws RemoteException {
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean registerA2dpCallback(GocCallbackA2dp cb) throws RemoteException {
        cb.onA2dpServiceReady();
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("A2DP");
        for (int i = 0; i < addrList.size(); i++) {
            String addr = addrList.get(i);
            DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(addr);
            if (_dev == null) {
                Log.d(TAG, "registerBtCallback dev is null");
            } else if (addr.length() > 0) {
                cb.onA2dpStateChanged(addr, 110, status2State(_dev.a2dp_state));
            }
        }
        return this.callbacks.register(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean unregisterA2dpCallback(GocCallbackA2dp cb) throws RemoteException {
        return this.callbacks.unregister(cb);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public int getA2dpConnectionState() throws RemoteException {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_dev == null) {
            Log.d(TAG, "getA2dpConnectionState dev is null");
            return 110;
        }
        int state = _dev.a2dp_state;
        Log.e(TAG, "getA2dpConnectionState:" + state);
        return status2State(state);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean isA2dpConnected() throws RemoteException {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.cmdMainAddr);
        if (_dev == null) {
            Log.d(TAG, "isA2dpConnected dev is null");
            return false;
        }
        boolean _isConnected = false;
        if (_dev.a2dp_state >= 140) {
            _isConnected = true;
        }
        Log.e(TAG, "isA2dpConnected:" + _isConnected);
        return _isConnected;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public String getA2dpConnectedAddress() throws RemoteException {
        List<String> addrList = this.service.bluetooth.getProfileConnectedAddrByProile("A2DP");
        if (addrList.isEmpty()) {
            return NfDef.DEFAULT_ADDRESS;
        }
        if (addrList.size() > 0) {
            return addrList.get(0);
        }
        return BuildConfig.FLAVOR;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean reqA2dpConnect(String address) throws RemoteException {
        Log.e(TAG, "reqA2dpConnect address:" + address);
        DeviceInfo info = this.service.bluetooth.getDeviceByAddr(address);
        DeviceInfo[] infos = this.service.bluetooth.loopFindConnectDevices();
        if (infos[0] != null && infos[1] != null) {
            if (info == infos[0]) {
                if (infos[1].a2dp_state > 1) {
                    this.service.getCommand().disconnectA2DP(infos[1].addr);
                    sendReqA2dpConnectDelayMessage(infos[0].addr);
                } else {
                    sendReqA2dpConnectMessage(infos[0].addr);
                }
            } else if (info == infos[1]) {
                if (infos[0].a2dp_state > 1) {
                    this.service.getCommand().disconnectA2DP(infos[0].addr);
                    sendReqA2dpConnectDelayMessage(infos[1].addr);
                } else {
                    sendReqA2dpConnectMessage(infos[1].addr);
                }
            }
            this.switchA2dp = true;
            return true;
        } else if (infos[0] == null || infos[1] != null) {
            this.service.getCommand().connectA2dp(address);
            this.switchA2dp = true;
            return true;
        } else {
            if (info == infos[0]) {
                this.service.getCommand().connectA2dp(address);
            } else if (infos[0].a2dp_state > 1) {
                this.service.getCommand().disconnectA2DP(infos[0].addr);
                sendReqA2dpConnectDelayMessage(info.addr);
            }
            this.switchA2dp = true;
            return true;
        }
    }

    public void sendReqA2dpConnectDelayMessage(String address) {
        Log.d(TAG, "sendReqA2dpConnectDelayMessage : " + address);
        Message msg = new Message();
        msg.what = 6;
        msg.obj = address;
        this.a2dpHandler.sendMessageDelayed(msg, 2000);
    }

    public void sendReqA2dpConnectMessage(String address) {
        Log.d(TAG, "sendReqA2dpConnectMessage : " + address);
        Message msg = new Message();
        msg.what = 6;
        msg.obj = address;
        this.a2dpHandler.sendMessage(msg);
    }

    private boolean changeA2dpLink(String address) {
        DeviceInfo[] deviceInfos = this.service.bluetooth.loopFindConnectDevices();
        Log.e("a2dp", "info >>>>> " + deviceInfos[0] + " , " + deviceInfos[1]);
        if (this.switchA2dp) {
            if (!(deviceInfos[0] == null || deviceInfos[1] == null)) {
                if (deviceInfos[0].addr.equals(address)) {
                    if (deviceInfos[1].is_connected_by_profile(2)) {
                        this.service.getCommand().disconnectA2DP(deviceInfos[1].addr);
                        this.switchA2dp = false;
                        return false;
                    }
                } else if (!deviceInfos[1].addr.equals(address)) {
                    Log.d(TAG, "This device not allow connect " + address);
                    this.service.getCommand().disconnectA2DP(address);
                    this.switchA2dp = false;
                    return false;
                } else if (deviceInfos[0].is_connected_by_profile(2)) {
                    this.service.getCommand().disconnectA2DP(deviceInfos[0].addr);
                    this.switchA2dp = false;
                    return false;
                }
            }
        } else if (!(deviceInfos[0] == null || deviceInfos[1] == null)) {
            if (deviceInfos[0].addr.equals(address)) {
                if (deviceInfos[1].is_connected_by_profile(2) && deviceInfos[0].is_connected_by_profile(2)) {
                    sendDisconnectA2dpMessage(address);
                    this.switchA2dp = false;
                    return false;
                }
            } else if (deviceInfos[1].addr.equals(address) && deviceInfos[0].is_connected_by_profile(2) && deviceInfos[1].is_connected_by_profile(2)) {
                sendDisconnectA2dpMessage(address);
                this.switchA2dp = false;
                return false;
            }
        }
        this.switchA2dp = false;
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean reqA2dpDisconnect(String address) throws RemoteException {
        Log.e(TAG, "reqA2dpDisconnect address:" + address);
        this.service.getCommand().disconnectA2DP(address);
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public void pauseA2dpRender() throws RemoteException {
        Log.e(TAG, "pauseA2dpRender");
        this.service.getCommand().musicMute();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public void startA2dpRender() throws RemoteException {
        Log.e(TAG, "startA2dpRender");
        this.service.getCommand().musicUnmute();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean setA2dpLocalVolume(float vol) throws RemoteException {
        Log.e(TAG, "setA2dpLocalVolume vol:" + vol);
        this.a2dp_volume = vol;
        this.service.getCommand().setA2dpVolume((int) (this.a2dp_volume * 10.0f * 2.0f));
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public boolean setA2dpStreamType(int type) throws RemoteException {
        Log.e(TAG, "setA2dpStreamType type:" + type);
        return true;
    }

    public void onInitSucceed() {
        Log.e(TAG, "onInitSucceed");
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public int getA2dpStreamType() throws RemoteException {
        return 3;
    }

    public void onA2dpConnected(String addr) {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(addr);
        Log.e("a2dp", "onA2dpConnected>>>> " + addr + "    _dev: " + _dev);
        if (_dev == null) {
            Log.d(TAG, "onA2dpConnected dev is null");
            this.service.getCommand().disconnectA2DP(addr);
            return;
        }
        Log.d(TAG, "onA2dpConnected:" + _dev.a2dp_state + ",addr:" + addr);
        this.service.bluetooth.setDeviceListProfileConnected(addr, "A2DP", true);
        _dev.a2dp_state = 2;
        _dev.sort = System.currentTimeMillis();
        Log.d(TAG, "onA2dpConnected : " + _dev.a2dp_state);
        this.service.bluetooth.updateDeviceInfo(addr, _dev);
        Log.e(TAG, "service.bluetooth.cmdMainAddr>>>> " + this.service.bluetooth.cmdMainAddr);
        if (this.service.bluetooth.cmdMainAddr == null) {
            this.service.bluetooth.setBtMainDevices(addr);
            Log.d(TAG, "IND_A2DP_CONNECTED set cmdMainAddr:" + this.service.bluetooth.cmdMainAddr);
        }
        GocApplication.INSTANCE.mCurrentA2dpAddress = addr;
    }

    private void sendConnectHfpMessage(String address) {
        Log.d(TAG, "sendConnectHfpMessage " + address);
        Message msg = new Message();
        msg.what = 2;
        msg.obj = address;
        this.a2dpHandler.sendMessageDelayed(msg, 5000);
    }

    private void sendDisconnectA2dpMessage(String address) {
        Log.d(TAG, "sendDisconnectA2dpMessage " + address);
        Message msg = new Message();
        msg.what = 5;
        msg.obj = address;
        this.a2dpHandler.sendMessageDelayed(msg, 1000);
    }

    public void onA2dpDisconnected(String address) {
        DeviceInfo _dev = this.service.bluetooth.getDeviceByAddr(address);
        if (_dev == null) {
            Log.e(TAG, "onA2dpDisconnected dev is null:" + this.service.bluetooth.rspAddr + " main:" + this.service.bluetooth.cmdMainAddr);
            if (this.service.bluetooth.cmdMainAddr != null && this.service.bluetooth.rspAddr.equals(this.service.bluetooth.cmdMainAddr)) {
                this.service.bluetooth.setBtMainDevices(null);
                Log.d(TAG, "----->onA2dpDisconnected set cmdMainAddr addr:" + this.service.bluetooth.cmdMainAddr);
                this.service.bluetooth.loopFindSetMainDev();
                return;
            }
            return;
        }
        Log.e(TAG, "onA2dpDisconnected " + address + ":" + _dev.a2dp_state);
        _dev.sort = 0;
        _dev.a2dp_state = 1;
        this.service.bluetooth.updateDeviceInfo(address, _dev);
        this.service.bluetooth.setDeviceListProfileConnected(address, "A2DP", false);
        this.service.bluetooth.loopFindSetMainDev();
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onA2dpStateChanged(address, status2pre(_dev.a2dp_state), status2State(_dev.a2dp_state));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onA2dpPlaying() {
        Log.e(TAG, "onA2dpPlaying");
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.currentStatus = 3;
                    this.callbacks.getBroadcastItem(i).onA2dpStateChanged(this.service.bluetooth.rspAddr, 140, 150);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onA2dpStop() {
        Log.e(TAG, "onA2dpStop" + this.service.bluetooth.rspAddr + "   STATE_STREAMING  150   STATE_CONNECTED 140");
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.currentStatus = 2;
                    this.callbacks.getBroadcastItem(i).onA2dpStateChanged(this.service.bluetooth.rspAddr, 150, 140);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    public void onAvStatus(int status) {
        Log.e(TAG, "onAvStatus:" + status + "   currentStatus " + this.currentStatus);
    }

    public void onA2dpClearConnectedDeviceInfo(String address) {
        synchronized (this.callbacksLock) {
            int n = this.callbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    this.callbacks.getBroadcastItem(i).onA2dpStateChanged(address, 140, 110);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.callbacks.finishBroadcast();
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandA2dp
    public byte getPlayStatus() throws RemoteException {
        Log.d(TAG, "getPlayStatus : " + this.currentStatus);
        if (this.service.playStete == 1) {
            return 1;
        }
        return 0;
    }
}
