package com.goodocom.sharedata;

import android.util.Log;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.gocsdkserver.CommandBluetoothImp;
import com.goodocom.gocsdkserver.CommandHfpImp;
import com.goodocom.gocsdkserver.GocsdkService;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AdayoAutoConnect {
    private final int BT_AUTO_CONNECT_MODE_0 = 0;
    private final int BT_AUTO_CONNECT_MODE_1 = 1;
    private final int BT_AUTO_CONNECT_MODE_2 = 2;
    private final String TAG = "GoodocomAutoConnectImp";
    private int accConnectIndex = 0;
    private int autoConnectCount = 5;
    private AutoConnectTask autoConnectTask = null;
    private long autoConnectTime = 300000;
    private Timer autoConnectTimer = null;
    public LinkedHashMap<String, DeviceInfo> connectDeviceList = new LinkedHashMap<>();
    private long connectTimeStart = 0;
    private int currentAutoConnectMode = 0;
    private int currentConnectCount = 0;
    private int currentconnectDeviceIndex = 0;
    private boolean isRangeDisconnect = false;
    private DeviceInfo[] saveDeviceInfos = new DeviceInfo[2];
    private GocsdkService service;

    public AdayoAutoConnect(GocsdkService service2) {
        this.service = service2;
    }

    public void autoConnectStart() {
        if (this.service.bluetooth.autoConnect) {
            this.connectDeviceList.putAll(getDeviceList());
            autoConnectTimerStart();
        }
    }

    public void autoConnectCancel() {
        Log.d("GoodocomAutoConnectImp", "autoConnectCancel 1 : connectDeviceList.size : " + this.connectDeviceList.size() + " , DeviceList : " + getDeviceList().size());
        this.connectDeviceList.clear();
        Log.d("GoodocomAutoConnectImp", "autoConnectCancel 2 : connectDeviceList.size : " + this.connectDeviceList.size() + " , DeviceList : " + getDeviceList().size());
        autoConnectTimerEnd();
    }

    /* access modifiers changed from: private */
    public class AutoConnectTask extends TimerTask {
        private AutoConnectTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            CommandBluetoothImp commandBluetoothImp = AdayoAutoConnect.this.service.bluetooth;
            if (!CommandBluetoothImp.currentEnable || AdayoAutoConnect.this.service.bluetooth.btenalbe_state == 303 || AdayoAutoConnect.this.service.bluetooth.btenalbe_state == 301) {
                StringBuilder sb = new StringBuilder();
                sb.append("------------>>currentEnable : ");
                CommandBluetoothImp commandBluetoothImp2 = AdayoAutoConnect.this.service.bluetooth;
                sb.append(CommandBluetoothImp.currentEnable);
                Log.d("GoodocomAutoConnectImp", sb.toString());
                AdayoAutoConnect.this.autoConnectCancel();
            } else if (AdayoAutoConnect.this.connectDeviceList.size() == 0) {
                Log.d("GoodocomAutoConnectImp", "------------>>connectDeviceList  : " + AdayoAutoConnect.this.connectDeviceList.size());
                AdayoAutoConnect.this.autoConnectCancel();
            } else if (!AdayoAutoConnect.this.isConnectBusy()) {
                if (AdayoAutoConnect.this.service.adayoSystemManager.isAccOn()) {
                    AdayoAutoConnect.this.accOnAutoConnect();
                } else {
                    AdayoAutoConnect.this.startUpAutoConnect();
                }
            }
        }
    }

    public void autoConnectTimerStart() {
        if (this.autoConnectTimer == null) {
            Log.e("GoodocomAutoConnectImp", "autoConnectTimerStart current mode " + this.currentAutoConnectMode);
            if (this.service.bluetooth.disconnectException) {
                this.isRangeDisconnect = true;
            } else {
                this.isRangeDisconnect = false;
            }
            this.autoConnectTimer = new Timer(true);
            this.autoConnectTask = new AutoConnectTask();
            this.autoConnectTimer.schedule(this.autoConnectTask, 2000, 10000);
        }
    }

    public void autoConnectTimerEnd() {
        Log.e("GoodocomAutoConnectImp", "autoConnectTimerEnd");
        Timer timer = this.autoConnectTimer;
        if (timer != null) {
            timer.cancel();
            this.autoConnectTimer.purge();
            this.autoConnectTimer = null;
            this.autoConnectTask = null;
            this.currentconnectDeviceIndex = 0;
            this.currentConnectCount = 0;
            this.connectTimeStart = 0;
        }
    }

    public boolean setConnectDeviceBeforeAccOff() {
        Log.e("GoodocomAutoConnectImp", "setconnectDeviceByAddressBeforeAccOff");
        this.saveDeviceInfos = this.service.bluetooth.loopFindConnectDevices();
        DeviceInfo[] deviceInfoArr = this.saveDeviceInfos;
        return (deviceInfoArr[0] == null && deviceInfoArr[1] == null) ? false : true;
    }

    public DeviceInfo[] getconnectDeviceByAddressBeforeAccOff() {
        return this.saveDeviceInfos;
    }

    public int getConnectCount() {
        int connectCount = 0;
        if (this.connectDeviceList.size() <= 0) {
            return -1;
        }
        for (String addr : this.connectDeviceList.keySet()) {
            if (this.service.bluetooth.isProfileConnectedByAddr(addr, "HFP") || this.service.bluetooth.isProfileConnectedByAddr(addr, "A2DP")) {
                connectCount++;
            }
        }
        return connectCount;
    }

    public boolean isConnectBusy() {
        for (String addr : this.connectDeviceList.keySet()) {
            DeviceInfo info = this.connectDeviceList.get(addr);
            if (info != null) {
                if (!this.service.bluetooth.isPairing) {
                    int i = info.hf_state;
                    CommandHfpImp commandHfpImp = this.service.hfp;
                    if (i == 2) {
                    }
                }
                Log.e("GoodocomAutoConnectImp", "autoConnect return : " + this.service.bluetooth.isPairing + " , " + info.hf_state + " , " + info.addr);
                return true;
            }
        }
        return false;
    }

    public void accOnAutoConnect() {
        Log.e("GoodocomAutoConnectImp", "accOnBtAutoConnect");
        DeviceInfo[] deviceInfoArr = this.saveDeviceInfos;
        if (deviceInfoArr[0] == null || deviceInfoArr[1] == null) {
            DeviceInfo[] deviceInfoArr2 = this.saveDeviceInfos;
            if (deviceInfoArr2[0] == null || deviceInfoArr2[1] != null) {
                Log.e("GoodocomAutoConnectImp", "don't need a connection");
                autoConnectCancel();
            } else if (getConnectCount() == 0) {
                connectDeviceByAddress(this.saveDeviceInfos[0].addr);
            } else {
                Log.e("GoodocomAutoConnectImp", "device is connected");
                autoConnectCancel();
            }
        } else if (getConnectCount() < 2) {
            if (this.accConnectIndex >= 2) {
                this.accConnectIndex = 0;
            }
            connectDeviceByAddress(this.saveDeviceInfos[this.accConnectIndex].addr);
            this.accConnectIndex++;
        } else {
            Log.e("GoodocomAutoConnectImp", "two device has connected");
            autoConnectCancel();
        }
    }

    public void startUpAutoConnect() {
        int i = this.currentAutoConnectMode;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    if (this.connectTimeStart == 0) {
                        this.connectTimeStart = System.currentTimeMillis();
                    }
                    if (loopConnectDeviceByPairList()) {
                        if (this.autoConnectTime <= System.currentTimeMillis() - this.connectTimeStart) {
                            Log.d("GoodocomAutoConnectImp", "connect time is up");
                            autoConnectCancel();
                            return;
                        }
                    } else {
                        return;
                    }
                }
            } else if (this.currentConnectCount == this.autoConnectCount) {
                Log.e("GoodocomAutoConnectImp", "connect count max");
                autoConnectCancel();
                return;
            } else if (loopConnectDeviceByPairList()) {
                this.currentConnectCount++;
            } else {
                return;
            }
        } else if (!loopConnectDeviceByPairList()) {
            Log.d("GoodocomAutoConnectImp", "connect invaild");
            return;
        }
        this.currentconnectDeviceIndex++;
        if (this.currentconnectDeviceIndex >= this.connectDeviceList.size() || getConnectCount() >= 2) {
            this.currentconnectDeviceIndex = 0;
        }
        Log.d("GoodocomAutoConnectImp", "--------------------->>index : " + this.currentconnectDeviceIndex);
    }

    private void connectDeviceByAddress(String address) {
        this.service.getCommand().connectHFP(address);
    }

    private LinkedHashMap<String, DeviceInfo> getDeviceList() {
        return this.service.bluetooth.DevicesList;
    }

    private boolean deviceIsConnected(String address) {
        return this.service.bluetooth.isBtConnectedByAddress(address);
    }

    public void onAutoConnectMode(int mode) {
        Log.d("GoodocomAutoConnectImp", "onAutoConnectMode : " + mode);
        this.currentAutoConnectMode = mode;
    }

    public void onAutoConnectNumber(int count) {
        Log.d("GoodocomAutoConnectImp", "onAutoConnectNumber : " + count);
        this.autoConnectCount = count;
    }

    public void onAutoConnectTime(long time) {
        Log.d("GoodocomAutoConnectImp", "onAutoConnectTime : " + time);
        this.autoConnectTime = 1000 * time;
    }

    public boolean loopConnectDeviceByPairList() {
        int index = 0;
        int i = 2;
        if (this.connectDeviceList.size() < 2 || this.isRangeDisconnect) {
            Log.d("GoodocomAutoConnectImp", "connectDeviceList size : " + this.connectDeviceList.size());
            if (getConnectCount() == 0) {
                Iterator<String> it = this.connectDeviceList.keySet().iterator();
                if (it.hasNext()) {
                    DeviceInfo info = this.connectDeviceList.get(it.next());
                    if (info == null) {
                        Log.d("GoodocomAutoConnectImp", "loopConnectDeviceByPairList info is null");
                        return false;
                    }
                    connectDeviceByAddress(info.addr);
                    return true;
                }
            } else {
                autoConnectCancel();
                this.isRangeDisconnect = false;
                return false;
            }
        } else {
            int connectCount = getConnectCount();
            if (!this.service.bluetooth.is_DualDeviceEnable) {
                i = 1;
            }
            if (connectCount < i) {
                for (String addr : this.connectDeviceList.keySet()) {
                    if (index == this.currentconnectDeviceIndex) {
                        DeviceInfo info2 = this.connectDeviceList.get(addr);
                        if (info2 == null) {
                            Log.d("GoodocomAutoConnectImp", "loopConnectDeviceByPairList deviceinfo is null");
                            return false;
                        } else if (!deviceIsConnected(info2.addr)) {
                            Log.e("GoodocomAutoConnectImp", "auto connect index : " + this.currentconnectDeviceIndex + " , " + info2.addr);
                            connectDeviceByAddress(info2.addr);
                            return true;
                        } else {
                            this.currentconnectDeviceIndex++;
                            return false;
                        }
                    } else {
                        index++;
                    }
                }
            } else {
                autoConnectCancel();
                return false;
            }
        }
        return false;
    }

    public void setAutoConnectIndexZero() {
        Log.d("GoodocomAutoConnectImp", "setAutoConnectIndexZero");
        this.currentconnectDeviceIndex = 0;
    }
}
