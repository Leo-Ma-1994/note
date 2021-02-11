package com.goodocom.gocsdkserver;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.bttek.bt.aidl.GocCallbackBluetooth;
import com.goodocom.bttek.bt.aidl.GocCommandBluetooth;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandBluetoothImp extends GocCommandBluetooth.Stub {
    public static final int AUTO_CONNECT_DISABLE = 0;
    public static final int AUTO_CONNECT_WHEN_BT_ON = 1;
    public static final int AUTO_CONNECT_WHEN_OOR = 4;
    public static final int AUTO_CONNECT_WHEN_PAIRED = 2;
    private static final int BT_CANCEL_AUTO_CONNECT = 4;
    private static final int BT_CONNECT_A2DP = 1;
    private static final int BT_CONNECT_HFP = 2;
    private static final int BT_CONNECT_MAP = 7;
    private static final int BT_CONNECT_MAP_DELAY = 8;
    private static final int BT_DOWNLOAD_CALLLOG = 5;
    private static final int BT_GAP_SET_LINK_POLICY = 6;
    public static final int BT_MODE_CONNECTABLE = 311;
    public static final int BT_MODE_CONNECTABLE_DISCOVERABLE = 312;
    public static final int BT_MODE_NONE = 310;
    private static final int BT_REMOVE_A2DP_TIMER = 3;
    public static final int BT_STATE_ERROR = -1;
    public static final int BT_STATE_OFF = 300;
    public static final int BT_STATE_ON = 302;
    public static final int BT_STATE_TURNING_OFF = 303;
    public static final int BT_STATE_TURNING_ON = 301;
    public static final int PROFILE_A2DP = 2;
    public static final int PROFILE_AVRCP = 4;
    public static final int PROFILE_DUN = 2048;
    public static final int PROFILE_ERROR = -1;
    public static final int PROFILE_FTP = 4096;
    public static final int PROFILE_HFP = 1;
    public static final int PROFILE_HID = 256;
    public static final int PROFILE_MAP = 64;
    public static final int PROFILE_NAP = 1024;
    public static final int PROFILE_PANU = 512;
    public static final int PROFILE_PBAP = 32;
    public static final int PROFILE_SPP = 128;
    public static final int STATE_BOND_BONDED = 332;
    public static final int STATE_BOND_BONDING = 331;
    public static final int STATE_BOND_NONE = 330;
    private static final String TAG = "GoodocomBluetoothImp";
    public static int currentBondStatus = 330;
    public static volatile boolean currentEnable = false;
    public static volatile String ringPath = BuildConfig.FLAVOR;
    private int AutoConnectCondition = 0;
    private final int BT_AUTO_CONNECT_MODE_0 = 0;
    private final int BT_AUTO_CONNECT_MODE_1 = 1;
    private final int BT_AUTO_CONNECT_MODE_2 = 2;
    public LinkedHashMap<String, DeviceInfo> DevicesList = new LinkedHashMap<>();
    private int appSetConnectTimeOOR = 500;
    public boolean autoConnect = false;
    private int autoConnectCount = 5;
    private int autoConnectCurrentCount = 0;
    private int autoConnectDeviceIndex = 0;
    private AutoConnectTask autoConnectTask = null;
    private long autoConnectTime = 300000;
    private long autoConnectTimeStart = 0;
    private Timer autoConnectTimer = null;
    private boolean bootBtStart = false;
    public int btenalbe_state = 300;
    public GocCallbackBluetooth callback = null;
    public volatile String cmdMainAddr = null;
    private int currentAutoConnectMode = 1;
    private int delayMillOfAutoConnect = PathInterpolatorCompat.MAX_NUM_POINTS;
    public boolean disconnectDeviceByCar = false;
    public boolean disconnectException = false;
    private int firstDeviceToConnectTwo = 0;
    Handler handler = new Handler() {
        /* class com.goodocom.gocsdkserver.CommandBluetoothImp.AnonymousClass1 */

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.d(CommandBluetoothImp.TAG, "rev message connect a2dp");
                String address = (String) msg.obj;
                DeviceInfo info = CommandBluetoothImp.this.getDeviceByAddr(address);
                DeviceInfo[] infos = CommandBluetoothImp.this.loopFindConnectDevices();
                if (infos[0] == null || infos[1] == null) {
                    if (infos[0] != null && infos[1] == null && !info.is_connected_by_profile(2) && info.is_connected_by_profile(1)) {
                        CommandBluetoothImp commandBluetoothImp = CommandBluetoothImp.this.service.bluetooth;
                        if (CommandBluetoothImp.currentEnable) {
                            CommandBluetoothImp.this.service.getCommand().connectA2dp(address);
                        }
                    }
                } else if (info == infos[0]) {
                    if (!info.is_connected_by_profile(2) && info.is_connected_by_profile(1) && !infos[1].is_connected_by_profile(2)) {
                        CommandBluetoothImp commandBluetoothImp2 = CommandBluetoothImp.this.service.bluetooth;
                        if (CommandBluetoothImp.currentEnable) {
                            CommandBluetoothImp.this.service.getCommand().connectA2dp(address);
                        }
                    }
                } else if (info == infos[1] && !info.is_connected_by_profile(2) && info.is_connected_by_profile(1) && !infos[0].is_connected_by_profile(2)) {
                    CommandBluetoothImp commandBluetoothImp3 = CommandBluetoothImp.this.service.bluetooth;
                    if (CommandBluetoothImp.currentEnable) {
                        CommandBluetoothImp.this.service.getCommand().connectA2dp(address);
                    }
                }
            } else if (msg.what == 2) {
                Log.d(CommandBluetoothImp.TAG, "rev message connect hfp");
                CommandBluetoothImp.this.service.getCommand().connectHFP((String) msg.obj);
            } else if (msg.what == 4) {
                Log.d(CommandBluetoothImp.TAG, "rev message delay cancel autoconnect");
                if (CommandBluetoothImp.this.isBtConnected()) {
                    CommandBluetoothImp.this.timeOfAutoConnectStop();
                }
            } else if (msg.what == 5) {
                if (CommandBluetoothImp.this.isBtConnected() && CommandBluetoothImp.currentEnable) {
                    CommandBluetoothImp.this.hanldA2dpConnectAndCalllogUpdate();
                }
            } else if (msg.what == 6) {
                Log.d(CommandBluetoothImp.TAG, "rev message delay gap set policy");
                CommandBluetoothImp.this.gapSetLinkPolicy();
            } else if (msg.what == 7) {
                Log.d(CommandBluetoothImp.TAG, "rev message delay map connect");
                CommandBluetoothImp.this.delayConnectMap();
            } else if (msg.what == 8 && CommandBluetoothImp.currentEnable) {
                CommandBluetoothImp.this.service.getCommand().connectMapMessage((String) msg.obj);
            }
        }
    };
    public int isAutoConnect = 0;
    public boolean isLocalReqPair = false;
    public boolean isPairing = false;
    private boolean is_DualCallEnable = true;
    public boolean is_DualDeviceEnable = true;
    private String localName = BuildConfig.FLAVOR;
    public String localPin = BuildConfig.FLAVOR;
    private int maxPairList = 9;
    private String outOfRangeDeviceAddress = BuildConfig.FLAVOR;
    private Runnable pairListDoneRunnable = new Runnable() {
        /* class com.goodocom.gocsdkserver.CommandBluetoothImp.AnonymousClass2 */

        @Override // java.lang.Runnable
        public void run() {
            int elements = CommandBluetoothImp.this.pairedDevices.size();
            String[] addrs = new String[elements];
            String[] names = new String[elements];
            int[] profiles = new int[elements];
            byte[] category = new byte[elements];
            CommandBluetoothImp.this.deleteOldDevice();
            List<DeviceInfo> mList = CommandBluetoothImp.this.sortArrays();
            int j = 0;
            while (j < mList.size() && j < elements) {
                DeviceInfo _deviceinfo = mList.get(j);
                addrs[j] = _deviceinfo.addr;
                names[j] = _deviceinfo.name;
                profiles[j] = _deviceinfo.profile_connected;
                category[j] = _deviceinfo.deviceType;
                Log.e("sort", "DeviceInfo>>>>>" + _deviceinfo.name + "    " + _deviceinfo.addr + "    _deviceinfo.profile_connected::: " + _deviceinfo.profile_connected + "  _deviceinfo.deviceType : " + ((int) _deviceinfo.deviceType));
                j++;
            }
            try {
                if (CommandBluetoothImp.this.callback != null) {
                    CommandBluetoothImp.this.callback.retPairedDevices(elements, addrs, names, profiles, category);
                }
                Log.e("pair", "retPairedDevices>>>>> " + addrs.length);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private boolean pairable = true;
    public LinkedHashMap<String, String> pairedDevices = new LinkedHashMap<>();
    private boolean playPhoneBell = false;
    public volatile String rspAddr = null;
    private boolean scanning = false;
    private GocsdkService service;
    public String stopLoadPbapAddress = null;
    private ScheduledExecutorService timerconnect = Executors.newSingleThreadScheduledExecutor();
    private String uiServiceVersion = "GOCAP_v1.7";
    private String versionData = "GOCBA44xAPI.05.15.2018";

    static /* synthetic */ int access$808(CommandBluetoothImp x0) {
        int i = x0.autoConnectCurrentCount;
        x0.autoConnectCurrentCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$810(CommandBluetoothImp x0) {
        int i = x0.autoConnectCurrentCount;
        x0.autoConnectCurrentCount = i - 1;
        return i;
    }

    public DeviceInfo getDeviceByAddr(String addr) {
        if (addr == null) {
            return null;
        }
        return this.DevicesList.get(addr);
    }

    public void updateDeviceInfo(String addr, DeviceInfo _info) {
        this.DevicesList.put(addr, _info);
    }

    public int getDeviceState(String addr, String strProfile) {
        DeviceInfo _devinfo = this.DevicesList.get(addr);
        if (_devinfo == null) {
            return 1;
        }
        if (strProfile.equals("A2DP")) {
            return _devinfo.a2dp_state;
        }
        if (strProfile.equals("HFP")) {
            return _devinfo.hf_state;
        }
        if (strProfile.equals("BATTERY")) {
            return _devinfo.battery;
        }
        if (strProfile.equals("SIGNAL")) {
            return _devinfo.signal;
        }
        if (strProfile.equals("AVRCP")) {
            return _devinfo.avrcp_state;
        }
        if (strProfile.equals("PBAP")) {
            return _devinfo.pbap_state;
        }
        return 1;
    }

    public void updateDeviceState(String addr, String strProfile, int state) {
        DeviceInfo _devinfo = this.DevicesList.get(addr);
        if (_devinfo == null) {
            _devinfo = new DeviceInfo(addr, BuildConfig.FLAVOR, (byte) 0);
        }
        if (strProfile.equals("A2DP")) {
            _devinfo.a2dp_state = state;
        } else if (strProfile.equals("HFP")) {
            _devinfo.hf_state = state;
        } else if (strProfile.equals("BATTERY")) {
            _devinfo.battery = state;
        } else if (strProfile.equals("SIGNAL")) {
            _devinfo.signal = state;
        } else if (strProfile.equals("AVRCP")) {
            _devinfo.avrcp_state = state;
        } else if (strProfile.equals("PBAP")) {
            _devinfo.pbap_state = state;
        }
        this.DevicesList.put(addr, _devinfo);
    }

    public void updateDeviceList(String addr, String name, byte deviceType, int _profile_connected) {
        DeviceInfo _devinfo = this.DevicesList.get(addr);
        if (_devinfo == null) {
            DeviceInfo _DeviceInfo = new DeviceInfo(addr, name, deviceType);
            _DeviceInfo.profile_connected = _profile_connected;
            this.DevicesList.put(addr, _DeviceInfo);
            return;
        }
        _devinfo.name = name;
        _devinfo.addr = addr;
        _devinfo.deviceType = deviceType;
        _devinfo.profile_connected = _profile_connected;
        this.DevicesList.put(addr, _devinfo);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public List<String> getProfileConnectedAddrByProile(String strProfile) {
        List<String> addrList = new ArrayList<>();
        if (TextUtils.isEmpty(strProfile)) {
            return addrList;
        }
        int profile = 0;
        if (strProfile.equals("A2DP")) {
            profile = 2;
        } else if (strProfile.equals("HFP")) {
            profile = 1;
        } else if (strProfile.equals("AVRCP")) {
            profile = 4;
        } else if (strProfile.equals("PBAP")) {
            profile = 32;
        } else if (strProfile.equals("SPP")) {
            profile = 128;
        } else if (strProfile.equals("MAP")) {
            profile = 64;
        } else if (strProfile.equals("HID")) {
            profile = 256;
        }
        for (Map.Entry<String, DeviceInfo> entry : this.DevicesList.entrySet()) {
            DeviceInfo _devinfo = entry.getValue();
            if (_devinfo != null && _devinfo.is_connected_by_profile(profile)) {
                addrList.add(_devinfo.addr);
            }
        }
        return addrList;
    }

    public boolean isBtConnected() {
        for (Map.Entry<String, DeviceInfo> entry : this.DevicesList.entrySet()) {
            DeviceInfo _devinfo = entry.getValue();
            Log.i(TAG, "addr: " + entry.getKey() + " DeviceInfo: " + _devinfo + " , " + _devinfo.is_connected());
            if (_devinfo.is_connected()) {
                return true;
            }
        }
        return false;
    }

    public boolean isBtConnectedByAddress(String address) {
        DeviceInfo info = getDeviceByAddr(address);
        if (info != null && info.is_connected()) {
            return true;
        }
        return false;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isProfileConnectedByAddr(String addr, String strProfile) {
        if (this.DevicesList == null || TextUtils.isEmpty(addr)) {
            return false;
        }
        DeviceInfo _device = this.DevicesList.get(addr);
        if (_device == null) {
            Log.d(TAG, "DevicesList not found devices");
            return false;
        } else if (strProfile.equals("A2DP")) {
            return _device.is_connected_by_profile(2);
        } else {
            if (strProfile.equals("HFP")) {
                return _device.is_connected_by_profile(1);
            }
            if (strProfile.equals("AVRCP")) {
                return _device.is_connected_by_profile(4);
            }
            if (strProfile.equals("PBAP")) {
                return _device.is_connected_by_profile(32);
            }
            if (strProfile.equals("SPP")) {
                return _device.is_connected_by_profile(128);
            }
            if (strProfile.equals("MAP")) {
                return _device.is_connected_by_profile(64);
            }
            if (strProfile.equals("HID")) {
                return _device.is_connected_by_profile(256);
            }
            return false;
        }
    }

    public void loopFindSetMainDev() {
        for (Map.Entry<String, DeviceInfo> entry : this.DevicesList.entrySet()) {
            DeviceInfo _devinfo = entry.getValue();
            if (_devinfo != null && isProfileConnectedByAddr(_devinfo.addr, "HFP") && this.cmdMainAddr == null) {
                setBtMainDevices(_devinfo.addr);
                Log.d(TAG, "----->loopFindSetMainDev set cmdMainAddr addr:" + this.cmdMainAddr);
                return;
            }
        }
    }

    public DeviceInfo[] loopFindConnectDevices() {
        DeviceInfo[] DeviceInfos = new DeviceInfo[2];
        int index = 0;
        for (Map.Entry<String, DeviceInfo> entry : this.DevicesList.entrySet()) {
            DeviceInfo _devinfo = entry.getValue();
            if (_devinfo != null && _devinfo.is_connected() && index < 2) {
                DeviceInfos[index] = _devinfo;
                index++;
            }
        }
        if (!(DeviceInfos[0] == null || DeviceInfos[1] == null || isBtMainDevices(DeviceInfos[0].addr))) {
            DeviceInfo DeviceInfoTemp = DeviceInfos[0];
            DeviceInfos[0] = DeviceInfos[1];
            DeviceInfos[1] = DeviceInfoTemp;
        }
        return DeviceInfos;
    }

    public void onConnectDevices(DeviceInfo[] deviceInfos) {
        String mainAddress = BuildConfig.FLAVOR;
        String subAddress = BuildConfig.FLAVOR;
        if (deviceInfos != null) {
            if (deviceInfos.length == 1) {
                mainAddress = deviceInfos[0].addr;
            } else if (deviceInfos.length == 2) {
                if (deviceInfos[0] != null && !TextUtils.isEmpty(deviceInfos[0].addr)) {
                    mainAddress = deviceInfos[0].addr;
                }
                if (deviceInfos[1] != null && !TextUtils.isEmpty(deviceInfos[1].addr)) {
                    subAddress = deviceInfos[1].addr;
                }
            }
            try {
                this.callback.onConnectedDevice(mainAddress, subAddress);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDeviceListProfileConnected(String addr, String strProfile, boolean is_connected) {
        int i = 0;
        for (String tmp_addr : this.DevicesList.keySet()) {
            if (i >= this.DevicesList.size()) {
                Log.e(TAG, "setDeviceListProfileConnected not found:" + addr);
                return;
            } else if (TextUtils.isEmpty(tmp_addr) || !tmp_addr.equals(addr)) {
                i++;
            } else {
                DeviceInfo _device = this.DevicesList.get(addr);
                if (_device == null) {
                    Log.e(TAG, "setDeviceListProfileConnected no devlist");
                    return;
                }
                if (strProfile.equals("A2DP")) {
                    _device.set_profile_connected(2, is_connected);
                } else if (strProfile.equals("HFP")) {
                    _device.set_profile_connected(1, is_connected);
                } else if (strProfile.equals("AVRCP")) {
                    _device.set_profile_connected(4, is_connected);
                } else if (strProfile.equals("PBAP")) {
                    _device.set_profile_connected(32, is_connected);
                } else if (strProfile.equals("SPP")) {
                    _device.set_profile_connected(128, is_connected);
                } else if (strProfile.equals("MAP")) {
                    _device.set_profile_connected(64, is_connected);
                } else if (strProfile.equals("HID")) {
                    _device.set_profile_connected(256, is_connected);
                }
                this.DevicesList.put(addr, _device);
                if (!_device.is_connected() && addr.equals(this.cmdMainAddr)) {
                    setBtMainDevices(null);
                    Log.d(TAG, "IND_A2DP_CONNECTED set cmdMainAddr null");
                    return;
                }
                return;
            }
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public void setDualDeviceEnable(boolean enable) {
        if (enable) {
            this.service.getCommand().setDualDevice("1");
        } else {
            this.service.getCommand().setDualDevice("0");
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean getDualDeviceEnable() {
        return this.is_DualDeviceEnable;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public void setDualCallEnable(boolean enable) {
        if (enable) {
            this.service.getCommand().setDualCall("1");
        } else {
            this.service.getCommand().setDualCall("0");
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean getDualCallEnable() {
        return this.is_DualCallEnable;
    }

    public CommandBluetoothImp(GocsdkService service2) {
        this.service = service2;
    }

    public void onBluetoothServiceReady() {
        Log.e(TAG, "onBluetoothServiceReady");
        try {
            if (this.callback != null) {
                this.callback.onBluetoothServiceReady();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        GocsdkService gocsdkService = this.service;
        gocsdkService.bserviceready = true;
        gocsdkService.getCommand().QueryBluetoothInit();
        this.service.getCommand().getVersion();
        onQueryBluetoothConnect();
        this.service.getCommand().getPairList();
    }

    public void onQueryBluetoothConnect() {
        this.service.getCommand().inqueryHfpStatus();
        this.service.getCommand().inqueryA2dpStatus();
        this.service.getCommand().inqueryAvrcpStatus();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isBluetoothServiceReady() throws RemoteException {
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public synchronized void setBtMainDevices(String address) {
        Log.d(TAG, "setBtMainDevices : " + address + "   cmdMainAddr " + this.cmdMainAddr);
        if (this.service.pbap.phonebookDowning && !TextUtils.isEmpty(this.cmdMainAddr)) {
            this.stopLoadPbapAddress = this.cmdMainAddr;
        }
        if (address == null) {
            this.cmdMainAddr = address;
            GocApplication.INSTANCE.mMainAddress = this.cmdMainAddr;
            this.service.getCommand().setBtMainDevice(this.cmdMainAddr);
            Log.d(TAG, "setBtMainDevices to null");
            return;
        }
        DeviceInfo _device = this.DevicesList.get(address);
        if (_device == null) {
            Log.d(TAG, "setBtMainDevices DevicesList not found devices");
        } else if (!_device.is_connected()) {
            Log.d(TAG, "setBtMainDevices device not connected");
        } else {
            Log.e(TAG, "cmd setBtMainDevices cmdMainAddr:" + this.cmdMainAddr + "  address:  " + address + "   stopLoadPbapAddress: " + this.stopLoadPbapAddress);
            this.cmdMainAddr = address;
            GocApplication.INSTANCE.mMainAddress = this.cmdMainAddr;
            this.service.getCommand().setBtMainDevice(this.cmdMainAddr);
            Log.e(TAG, "onMainDevicesChanged:cmdMainAddr  " + this.cmdMainAddr + "_device.name : " + _device.name);
            onMainDevicesChanged(this.cmdMainAddr, _device.name);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void delayConnectMap() {
        DeviceInfo[] infos = loopFindConnectDevices();
        if (infos[0] != null && infos[1] != null) {
            Log.d(TAG, "info_0 : " + infos[0].map_state + " , info_1 : " + infos[1].map_state);
            int i = infos[1].map_state;
            CommandMapImp commandMapImp = this.service.map;
            if (i > 1) {
                this.service.getCommand().diconnecyMapMessage(infos[1].addr);
            }
            Log.d(TAG, "---->>info_0 : " + infos[0].map_state);
            int i2 = infos[0].map_state;
            CommandMapImp commandMapImp2 = this.service.map;
            if (i2 < 2) {
                this.handler.removeMessages(8);
                Message msg = new Message();
                msg.what = 8;
                msg.obj = infos[0].addr;
                this.handler.sendMessageDelayed(msg, 2000);
            }
        } else if (infos[0] != null && infos[1] == null) {
            int i3 = infos[0].map_state;
            CommandMapImp commandMapImp3 = this.service.map;
            if (i3 < 2 && currentEnable) {
                int i4 = infos[0].hf_state;
                CommandHfpImp commandHfpImp = this.service.hfp;
                if (i4 >= 3) {
                    this.service.getCommand().connectMapMessage(infos[0].addr);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void hanldA2dpConnectAndCalllogUpdate() {
        DeviceInfo[] infos = loopFindConnectDevices();
        if (infos[0] == null && infos[1] == null) {
            Log.d(TAG, "No device connection");
        } else if (infos[0] == null || infos[1] != null) {
            Log.d(TAG, "switch mian device , download pbap");
            this.service.pbap.downloadAllCallLog();
        } else {
            Log.d(TAG, "Only one device is connected");
            this.service.pbap.downloadAllCallLog();
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getBtMainDevices() {
        Log.e(TAG, "getBtMainDevices:" + this.cmdMainAddr);
        if (this.cmdMainAddr == null) {
            return BuildConfig.FLAVOR;
        }
        return this.cmdMainAddr;
    }

    public String getBtMainDevicesName() {
        Log.e(TAG, "getBtMainDevicesName : " + getDeviceByAddr(this.cmdMainAddr).name);
        return getDeviceByAddr(this.cmdMainAddr).name;
    }

    public boolean isBtMainDevices(String address) {
        if (TextUtils.isEmpty(address) || !address.equals(getBtMainDevices())) {
            return false;
        }
        Log.d(TAG, "device : " + address + "is Main device");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public void switchingMainDevices(String address) throws RemoteException {
        for (Map.Entry<String, DeviceInfo> entry : this.DevicesList.entrySet()) {
            DeviceInfo _devinfo = entry.getValue();
            if (_devinfo != null && _devinfo.is_connected()) {
                boolean is_set = false;
                if (address == null) {
                    if (!_devinfo.addr.equals(this.cmdMainAddr)) {
                        is_set = true;
                    }
                } else if (_devinfo.addr == address) {
                    is_set = true;
                }
                if (is_set) {
                    setBtMainDevices(_devinfo.addr);
                    Log.d(TAG, "----->switchingMainDevices input:" + address + " cmdMainAddr:" + this.cmdMainAddr);
                    return;
                }
            }
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean registerBtCallback(GocCallbackBluetooth cb) throws RemoteException {
        Log.e(TAG, "registerBtCallback:");
        try {
            if (this.pairable) {
                cb.onAdapterDiscoverableModeChanged(310, 312);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.callback = cb;
        onBluetoothServiceReady();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean unregisterBtCallback(GocCallbackBluetooth cb) throws RemoteException {
        this.callback = null;
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getNfServiceVersionName() throws RemoteException {
        Log.e(TAG, "getNfServiceVersionName:" + this.versionData);
        return this.versionData;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getUiServiceVersionName() throws RemoteException {
        Log.d(TAG, "getUiServiceVersionName" + this.uiServiceVersion);
        return this.uiServiceVersion;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public void onQueryBluetoothConnect(int wh) throws RemoteException {
        onQueryBluetoothConnect();
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean setBtEnable(boolean enable) throws RemoteException {
        Log.e(TAG, "setBtEnable enable:" + enable);
        currentEnable = enable;
        if (enable) {
            this.service.getCommand().openBlueTooth();
            return true;
        }
        this.service.getCommand().closeBlueTooth();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean setBtDiscoverableTimeout(int timeout) throws RemoteException {
        Log.e(TAG, "setBtDiscoverableTimeout timeout:" + timeout);
        if (timeout == 0) {
            this.service.getCommand().setPairMode();
            try {
                if (this.callback == null) {
                    return true;
                }
                this.callback.onAdapterDiscoverableModeChanged(310, 312);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        } else if (timeout != -1) {
            return true;
        } else {
            this.service.getCommand().cancelPairMode();
            try {
                if (this.callback == null) {
                    return true;
                }
                this.callback.onAdapterDiscoverableModeChanged(312, 310);
                return true;
            } catch (RemoteException e2) {
                e2.printStackTrace();
                return true;
            }
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean startBtDiscovery() throws RemoteException {
        Log.e(TAG, "startBtDiscovery cancle oor connect");
        this.disconnectException = false;
        this.service.adayoAutoConnect.autoConnectCancel();
        this.scanning = true;
        this.service.getCommand().startDiscovery();
        try {
            if (this.callback != null) {
                this.callback.onAdapterDiscoveryStarted();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean cancelBtDiscovery() throws RemoteException {
        Log.e(TAG, "cancelBtDiscovery");
        this.scanning = false;
        this.service.getCommand().stopDiscovery();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean reqBtPair(String address) throws RemoteException {
        Log.e(TAG, "reqBtPair address:" + address);
        this.disconnectException = false;
        this.disconnectDeviceByCar = false;
        this.service.adayoAutoConnect.autoConnectCancel();
        this.isLocalReqPair = true;
        this.service.getCommand().connectHFP(address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean reqBtUnpair(String address) throws RemoteException {
        Log.e(TAG, "reqBtUnpair address:" + address);
        this.service.getCommand().deletePair(address);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean reqBtPairedDevices(int opt) throws RemoteException {
        Log.e(TAG, "reqBtPairedDevices");
        this.pairedDevices.clear();
        this.service.getCommand().getPairList();
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getBtLocalName() throws RemoteException {
        Log.e(TAG, "getBtLocalName : " + this.service.localName);
        return this.service.localName;
    }

    public String getBtLocalName1() {
        return this.service.localName;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getBtRemoteDeviceName(String address) throws RemoteException {
        Log.e(TAG, "cbluetoothimp getBtRemoteDeviceName address:" + address + " currentAddress:" + this.service.currentConnectedAddr);
        StringBuilder sb = new StringBuilder();
        sb.append("dev::: ");
        sb.append(address);
        Log.e(TAG, sb.toString());
        DeviceInfo dev = this.service.bluetooth.getDeviceByAddr(address);
        Log.e(TAG, "dev::: " + dev + "   address: " + address);
        if (dev != null) {
            Log.d(TAG, ">>>>>>>>>>getBtRemoteDeviceName : " + dev.name);
            return dev.name;
        } else if (TextUtils.isEmpty(address) || !address.equals(this.service.currentConnectedAddr)) {
            return BuildConfig.FLAVOR;
        } else {
            return this.service.currentConnectedName;
        }
    }

    public String getBtRemoteDeviceNameByAddress(String address) {
        Log.e(TAG, "getBtRemoteDeviceNameByAddress address:" + address + " currentAddress:" + this.service.currentConnectedAddr);
        if (address != null && address.equals(this.service.currentConnectedAddr)) {
            return this.service.currentConnectedName;
        }
        return BuildConfig.FLAVOR;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getBtLocalAddress() throws RemoteException {
        Log.e(TAG, "getBtLocalAddress localAddress:" + this.service.localAddress);
        return this.service.localAddress;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean setBtLocalName(String name) throws RemoteException {
        Log.e(TAG, "setBtLocalName name:" + name);
        this.service.getCommand().setLocalName(name);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isBtEnabled() throws RemoteException {
        Log.e(TAG, "isBtEnabled:" + currentEnable);
        return currentEnable;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int getBtState() throws RemoteException {
        Log.e(TAG, "getBtState:" + currentEnable + " btenalbe_state:" + this.btenalbe_state);
        if (currentEnable) {
            return this.btenalbe_state;
        }
        return this.btenalbe_state;
    }

    public int get_prestate(int state) {
        if (state == 301) {
            return 300;
        }
        if (state == 302) {
            return 301;
        }
        if (state != 303 && state == 300) {
            return 303;
        }
        return 302;
    }

    public void clearDeviceConnectInfo() {
        Log.d(TAG, "clearDeviceConnectInfo");
        if (this.DevicesList.size() > 0) {
            for (String addr : this.DevicesList.keySet()) {
                DeviceInfo info = this.DevicesList.get(addr);
                if (info != null) {
                    Log.d(TAG, "clearDeviceConnectInfo : " + info.addr);
                    CommandHfpImp commandHfpImp = this.service.hfp;
                    info.hf_state = 1;
                    CommandA2dpImp commandA2dpImp = this.service.a2dp;
                    info.a2dp_state = 1;
                    CommandAvrcpImp commandAvrcpImp = this.service.avrcp;
                    info.avrcp_state = 1;
                    this.service.bluetooth.updateDeviceInfo(info.addr, info);
                    this.service.hfp.onHfpClearConnectedDeviceInfo(info.addr);
                    this.service.a2dp.onA2dpClearConnectedDeviceInfo(info.addr);
                    this.service.callState = 7;
                }
            }
        }
    }

    public void onBTenable(int bt_state) {
        Log.e(TAG, "onBTenable : " + bt_state + " , currentEnable : " + currentEnable);
        if (bt_state == 1) {
            this.btenalbe_state = 302;
            if (!currentEnable) {
                currentEnable = true;
                this.service.adayoAutoConnect.autoConnectStart();
            }
        } else if (bt_state == 0) {
            currentEnable = false;
            this.btenalbe_state = 300;
            this.service.adayoAutoConnect.autoConnectCancel();
            this.autoConnectDeviceIndex = 0;
            this.firstDeviceToConnectTwo = 0;
            this.DevicesList.clear();
        } else if (bt_state == 2) {
            this.btenalbe_state = 301;
        } else if (bt_state == 3) {
            this.btenalbe_state = 303;
        } else if (bt_state == 4) {
            this.btenalbe_state = 301;
            currentEnable = true;
        } else if (bt_state == 5) {
            if (!currentEnable && this.bootBtStart) {
                Log.d(TAG, "rev IS5 bluetooth need open");
                this.service.getCommand().openBlueTooth();
                return;
            }
            return;
        }
        try {
            int pre = get_prestate(this.btenalbe_state);
            Log.e("wan", "<<<<<<<<<<<<<<<<<<<<" + pre + "  btenalbe_state " + this.btenalbe_state);
            if (this.callback != null) {
                this.callback.onAdapterStateChanged(pre, this.btenalbe_state);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void startBtEnable(int enable) {
        Log.d(TAG, "startBeEnable : " + enable);
        if (enable == 1) {
            this.bootBtStart = true;
        } else {
            this.bootBtStart = false;
        }
    }

    public void onMaxPairList(int count) {
        this.maxPairList = count;
    }

    public void onPlayPhoneBell(int enable) {
        if (enable == 1) {
            this.playPhoneBell = true;
        } else {
            this.playPhoneBell = false;
        }
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isBtDiscovering() throws RemoteException {
        Log.e(TAG, "isBtDiscovering:" + this.scanning);
        return this.scanning;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isBtDiscoverable() throws RemoteException {
        Log.e(TAG, "isBtDiscoverable:" + this.pairable);
        return this.pairable;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isBtAutoConnectEnable() throws RemoteException {
        Log.e(TAG, "isBtAutoConnectEnable:" + this.autoConnect + "AutoConnectCondition:" + this.AutoConnectCondition);
        return this.autoConnect;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int reqBtConnectHfpA2dp(String address) throws RemoteException {
        Log.e(TAG, "reqBtConnectHfpA2dp:" + address);
        this.disconnectException = false;
        this.disconnectDeviceByCar = false;
        this.service.adayoAutoConnect.autoConnectCancel();
        sendConnectHfpMessage(address);
        return 7;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int reqBtDisconnectAll(String address) throws RemoteException {
        Log.d(TAG, "reqBtDisconnectAll:" + address);
        this.service.getCommand().disconnect(address);
        this.disconnectDeviceByCar = true;
        return 39;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int getBtRemoteUuids(String address) throws RemoteException {
        Log.e(TAG, "getBtRemoteUuids address:" + address);
        return 39;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean isDeviceAclDisconnected(String address) throws RemoteException {
        Log.e(TAG, "isDeviceAclDisconnected address:" + address);
        if (address.equals(NfDef.DEFAULT_ADDRESS)) {
            return true;
        }
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(address);
        if (_devinfo != null) {
            return !_devinfo.isAclConnected;
        }
        Log.e(TAG, "isDeviceAclDisconnected dev is null");
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public boolean switchBtRoleMode(int mode) throws RemoteException {
        Log.e(TAG, "switchBtRoleMode mode:" + mode);
        return true;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int getBtRoleMode() throws RemoteException {
        Log.e(TAG, "getBtRoleMode");
        return 1;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public void setBtAutoConnect(int condition, int period) throws RemoteException {
        Log.e(TAG, "setBtAutoConnect condition:" + condition + " period:" + period);
        if (condition == 0) {
            this.service.getCommand().cancelAutoConnect();
            this.autoConnect = false;
        } else if (condition == 1) {
            this.service.getCommand().setAutoConnect();
            this.autoConnect = true;
            if (!isBtConnected() && this.pairedDevices.size() > 0) {
                reqBtConnectHfpA2dp(this.pairedDevices.get(0));
            }
        } else {
            this.service.getCommand().setAutoConnect();
            this.autoConnect = true;
            this.service.adayoAutoConnect.autoConnectStart();
        }
        this.AutoConnectCondition = condition;
        this.appSetConnectTimeOOR = period;
        if (this.appSetConnectTimeOOR == 0) {
            this.appSetConnectTimeOOR = 1;
        }
        setSencondOfAutoConnect(period);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int getBtAutoConnectCondition() throws RemoteException {
        Log.e(TAG, "getBtAutoConnectCondition : " + this.autoConnect + " , " + this.AutoConnectCondition);
        if (this.autoConnect) {
            return this.AutoConnectCondition;
        }
        return 0;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int getBtAutoConnectPeriod() throws RemoteException {
        Log.e(TAG, "getBtAutoConnectPeriod");
        return 60;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public int getBtAutoConnectState() throws RemoteException {
        Log.e(TAG, "getBtAutoConnectState");
        return 110;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getBtAutoConnectingAddress() throws RemoteException {
        Log.e(TAG, "getBtAutoConnectingAddress");
        return this.service.currentConnectedAddr;
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public void saveBellPath(String path) {
        ringPath = path;
        this.service.getCommand().setRingPath(path);
        Log.d(TAG, "saveBellPath path:" + ringPath);
    }

    @Override // com.goodocom.bttek.bt.aidl.GocCommandBluetooth
    public String getBellPath() {
        Log.d(TAG, "getBellPath");
        return ringPath;
    }

    public void onInPairMode() {
        this.pairable = true;
    }

    public void onExitPairMode() {
        this.pairable = false;
    }

    public void onDiscoverable(int enable) {
        if (enable == 1) {
            onInPairMode();
        } else {
            onExitPairMode();
        }
    }

    public void onInitSucceed() {
        Log.e(TAG, "onInitSucceed");
        this.cmdMainAddr = null;
        GocApplication.INSTANCE.mMainAddress = NfDef.DEFAULT_ADDRESS;
        this.rspAddr = null;
    }

    public void onAutoConnect(int autoConnect2) {
        Log.d(TAG, "onAutoConnect : " + autoConnect2);
        if (autoConnect2 == 1) {
            this.autoConnect = true;
            this.AutoConnectCondition = 7;
        } else {
            this.autoConnect = false;
            this.AutoConnectCondition = 0;
        }
        try {
            if (this.callback != null) {
                this.callback.onAutoConnect(autoConnect2);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onAutoAnwer(int state) {
        try {
            if (this.callback != null) {
                this.callback.onAutoAnwer(state);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onVersionDate(String versionDate) {
        this.versionData = versionDate;
        Log.e(TAG, "onVersionDate:" + this.versionData);
    }

    public void onBellPath(String path) {
        ringPath = path;
        Log.e(TAG, "onBellPath:" + ringPath);
    }

    public void onMainDevicesChanged(String addr, String name) {
        Log.e(TAG, "onMainDevicesChanged :" + addr + ":" + name);
        try {
            if (this.callback != null) {
                this.callback.onMainDevicesChanged(addr, name);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onLocalName(String localName2) {
        this.localName = localName2;
        GocCallbackBluetooth gocCallbackBluetooth = this.callback;
        if (gocCallbackBluetooth != null) {
            try {
                gocCallbackBluetooth.onLocalAdapterNameChanged(this.localName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLocalPin(String localPin2) {
        Log.e(TAG, "getlocalPin localPin:" + localPin2);
        this.localPin = localPin2;
    }

    public void onCmdAddress(String address) {
        this.rspAddr = address;
    }

    public void onConnectMode(String _mode) {
        if (_mode.equals("1")) {
            this.is_DualDeviceEnable = true;
        } else {
            this.is_DualDeviceEnable = false;
        }
    }

    public void onCallMode(String _mode) {
        if (_mode.equals("1")) {
            this.is_DualCallEnable = true;
        } else {
            this.is_DualCallEnable = false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void deleteOldDevice() {
        int elements = this.pairedDevices.size();
        for (String deviceAddr : this.DevicesList.keySet()) {
            int j = 0;
            for (String pairAddr : this.pairedDevices.keySet()) {
                if (!TextUtils.isEmpty(deviceAddr) && deviceAddr.equals(pairAddr)) {
                    break;
                }
                j++;
                if (j == elements) {
                    Log.d(TAG, "remove DeviceList : " + deviceAddr);
                    this.DevicesList.remove(deviceAddr);
                    return;
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v1, resolved type: java.util.LinkedHashMap<java.lang.String, com.goodocom.bttek.bt.bean.DeviceInfo> */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private List<DeviceInfo> sortArrays() {
        List<DeviceInfo> mList = new ArrayList<>(this.maxPairList);
        for (String addr : this.DevicesList.keySet()) {
            DeviceInfo _deviceinfo = this.DevicesList.get(addr);
            Log.e("sort", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + _deviceinfo.name);
            mList.add(_deviceinfo);
        }
        Collections.sort(mList, new Comparator<DeviceInfo>() {
            /* class com.goodocom.gocsdkserver.CommandBluetoothImp.AnonymousClass3 */

            public int compare(DeviceInfo o1, DeviceInfo o2) {
                int result = o2.profile_connected - o1.profile_connected;
                Log.e("sort", "o2.profile_connected: " + o2.profile_connected + "    sorta2dp: " + o2.sort + "  o1.profile_connected: " + o1.profile_connected + "     sort: " + o1.sort);
                if (o2.profile_connected > 0 && o1.profile_connected > 0) {
                    result = 0;
                }
                if (result == 0) {
                    return Long.valueOf(o2.sort).compareTo(Long.valueOf(o1.sort));
                }
                return result;
            }
        });
        LinkedHashMap<String, DeviceInfo> linkedHashMap = this.DevicesList;
        if (linkedHashMap != null && linkedHashMap.size() > 0) {
            Log.d(TAG, "DevicesList clear");
            this.DevicesList.clear();
            for (int i = 0; i < mList.size(); i++) {
                this.DevicesList.put(mList.get(i).addr, mList.get(i));
                Log.d(TAG, "mList " + i + " : " + mList.get(i).addr);
            }
        }
        return mList;
    }

    public boolean isPaired(String addr) {
        int elements = this.pairedDevices.size();
        String[] strArr = new String[elements];
        int i = 0;
        for (String str : this.DevicesList.keySet()) {
            if (i >= this.DevicesList.size()) {
                break;
            }
            DeviceInfo _devinfo = this.DevicesList.get(addr);
            Log.d(TAG, "_devinfo  name:" + _devinfo.name + " addr:" + _devinfo.addr + " supported:" + String.format("%x", Integer.valueOf(_devinfo.profile_supported)) + " connected:" + String.format("%x", Integer.valueOf(_devinfo.profile_connected)));
            i++;
        }
        int i2 = 0;
        for (String tmp_addr : this.pairedDevices.keySet()) {
            if (i2 >= elements) {
                break;
            } else if (tmp_addr.equals(addr)) {
                return true;
            } else {
                i2++;
            }
        }
        return false;
    }

    public void onBtPairModeChanged(int status, String name, String addr) {
        if (currentBondStatus != 331) {
            currentBondStatus = isPaired(addr) ? 332 : 330;
        }
        Log.d(TAG, "status:" + status + " currentBondStatus:" + currentBondStatus + " name:" + name + " addr:" + addr);
        try {
            if (this.callback != null) {
                this.callback.onDeviceBondStateChanged(addr, name, currentBondStatus, status);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentBondStatus = status;
    }

    public void onCurrentAndPairList(int index, String name, String addr, String cod, int connected_profile) {
        if (index == 1) {
            this.service.lastConnectedAddr = addr;
            Log.e(TAG, "set lastConnectedAddr:" + addr);
            this.pairedDevices.clear();
        } else if (index == -1) {
            this.pairedDevices.clear();
            this.DevicesList.clear();
            GocsdkService.getInstance().getHandler().removeCallbacks(this.pairListDoneRunnable);
            GocsdkService.getInstance().getHandler().postDelayed(this.pairListDoneRunnable, 200);
        }
        if (index > 0) {
            byte deviceType = (byte) this.service.decode_cod(Integer.parseInt(cod, 16));
            this.pairedDevices.put(addr, name);
            Log.e(TAG, "device list: addr:" + addr + " name:" + name + " type:" + ((int) deviceType) + " connected_profile:" + connected_profile);
            updateDeviceList(addr, name, deviceType, connected_profile);
            loopFindSetMainDev();
            GocsdkService.getInstance().getHandler().removeCallbacks(this.pairListDoneRunnable);
            GocsdkService.getInstance().getHandler().postDelayed(this.pairListDoneRunnable, 200);
        }
    }

    public void onDiscovery(String type, String name, String addr) {
        Log.e(TAG, "onDiscovery type:" + type);
        int int_type = Integer.parseInt(type, 16);
        this.scanning = true;
        byte category = (byte) this.service.decode_cod(int_type);
        try {
            if (this.callback != null) {
                this.callback.onDeviceFound(addr, name, category);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onDiscoveryDone() {
        this.scanning = false;
        try {
            if (this.callback != null) {
                this.callback.onAdapterDiscoveryFinished();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onDevicePairStaus(String address, String name, int newState) {
        Log.e(TAG, "onDevicePairStaus() " + address + " name: " + name + " state: " + newState);
        int bondstate = 330;
        int pre_bondstate = 330;
        if (newState == 0) {
            pre_bondstate = 331;
            bondstate = 330;
            this.isPairing = false;
        } else if (newState == 1) {
            pre_bondstate = 331;
            bondstate = 332;
            this.isPairing = false;
        } else if (newState == 2) {
            if (this.DevicesList.get(address) != null) {
                this.DevicesList.remove(address);
            }
            pre_bondstate = 332;
            bondstate = 330;
            this.isPairing = false;
            if (this.service.bluetooth.cmdMainAddr != null && address.equals(this.service.bluetooth.cmdMainAddr)) {
                this.service.bluetooth.setBtMainDevices(null);
                Log.d(TAG, "----->onDevicePairStaus set cmdMainAddr addr:" + this.service.bluetooth.cmdMainAddr);
                this.service.bluetooth.loopFindSetMainDev();
            }
        } else if (newState == 3) {
            pre_bondstate = 330;
            bondstate = 331;
            this.isPairing = true;
        }
        try {
            if (this.callback != null) {
                this.callback.onDeviceBondStateChanged(address, name, pre_bondstate, bondstate);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onBtDisconnectedReason(int reason) {
        Log.e(TAG, "onBtDisconnectedReason :" + this.service.bluetooth.rspAddr + ":" + reason);
        StringBuilder sb = new StringBuilder();
        sb.append("AutoConnectCondition :");
        sb.append(this.AutoConnectCondition);
        Log.e(TAG, sb.toString());
        if (reason == 8 || reason == 34) {
            this.disconnectException = true;
            this.autoConnectDeviceIndex = 0;
            if (this.DevicesList.size() > 0) {
                Iterator<String> it = this.DevicesList.keySet().iterator();
                while (true) {
                    if (it.hasNext()) {
                        DeviceInfo deviceInfo = this.DevicesList.get(it.next());
                        if (deviceInfo != null) {
                            this.outOfRangeDeviceAddress = deviceInfo.addr;
                            break;
                        }
                    }
                }
            }
            try {
                if (this.callback != null) {
                    this.callback.onDeviceOutOfRange(this.service.bluetooth.rspAddr);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.service.adayoAutoConnect.autoConnectStart();
        }
    }

    public void onHfpStatus(int status) {
        DeviceInfo _devinfo = this.service.bluetooth.getDeviceByAddr(this.service.bluetooth.rspAddr);
        if (_devinfo == null) {
            Log.e(TAG, "device onHfpStatus dev is null");
            return;
        }
        Log.d(TAG, "status:" + status + " hf_state" + status + " , " + this.service.bluetooth.rspAddr);
        if (status != _devinfo.hf_state && _devinfo.hf_state >= 3 && status < 3) {
            Log.e(TAG, "send acl disconnect:" + this.service.bluetooth.rspAddr);
            _devinfo.isAclConnected = false;
            this.service.bluetooth.updateDeviceInfo(this.service.bluetooth.rspAddr, _devinfo);
            try {
                if (this.callback != null) {
                    this.callback.onDeviceAclDisconnected(this.service.bluetooth.rspAddr);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSencondOfAutoConnect(int period) {
        this.delayMillOfAutoConnect = period;
        Log.e(TAG, "setSencondOfAutoConnect period:" + this.delayMillOfAutoConnect);
    }

    public void TimerOfAutoConnect() {
        Log.e(TAG, "TimerOfAutoConnect period:" + this.delayMillOfAutoConnect);
        Log.e(TAG, "is timer clear:" + this.timerconnect.isShutdown());
        Runnable runnable = new Runnable() {
            /* class com.goodocom.gocsdkserver.CommandBluetoothImp.AnonymousClass4 */

            @Override // java.lang.Runnable
            public void run() {
                StringBuilder sb = new StringBuilder();
                sb.append("timer running AutoConnectCondition:");
                sb.append((CommandBluetoothImp.this.AutoConnectCondition & 4) == 4);
                Log.e(CommandBluetoothImp.TAG, sb.toString());
                if ((CommandBluetoothImp.this.AutoConnectCondition & 4) == 4) {
                    CommandBluetoothImp.this.service.getCommand().connectLast();
                }
                CommandBluetoothImp.this.cancleTimerOfAutoConnect();
            }
        };
        if (!this.timerconnect.isShutdown()) {
            ScheduledExecutorService scheduledExecutorService = this.timerconnect;
            int i = this.delayMillOfAutoConnect;
            scheduledExecutorService.scheduleAtFixedRate(runnable, (long) i, (long) i, TimeUnit.MILLISECONDS);
            return;
        }
        Log.e(TAG, "timer restart");
        this.timerconnect = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService scheduledExecutorService2 = this.timerconnect;
        int i2 = this.delayMillOfAutoConnect;
        scheduledExecutorService2.scheduleAtFixedRate(runnable, (long) i2, (long) i2, TimeUnit.MILLISECONDS);
    }

    public void cancleTimerOfAutoConnect() {
        Log.e(TAG, "cancleTimerOfAutoConnect:" + this.AutoConnectCondition);
        if (!this.timerconnect.isShutdown()) {
            this.timerconnect.shutdown();
        }
    }

    public void autoconnectfunc() {
        Log.d(TAG, "autoconnectfunc : " + this.disconnectException);
        if (this.disconnectException) {
            this.delayMillOfAutoConnect *= 5;
            if (this.delayMillOfAutoConnect > 40000) {
                this.delayMillOfAutoConnect = 40000;
            }
            TimerOfAutoConnect();
        }
    }

    public boolean autoConnectFunc() {
        Log.d(TAG, "autoConnectFunc : " + this.autoConnect + " , " + isBtConnected() + " , " + this.pairedDevices.size());
        if (!this.autoConnect || isBtConnected() || this.pairedDevices.size() <= 0) {
            return false;
        }
        timeOfAutoConnect();
        return true;
    }

    public void sendConnectA2dpMessage(String address) {
        Log.d(TAG, "sendConnectA2dpMessage : " + address);
        if (!this.disconnectDeviceByCar) {
            Log.d(TAG, "send sendConnectA2dpMessage");
            Message msg = new Message();
            msg.what = 1;
            msg.obj = address;
            this.handler.sendMessageDelayed(msg, 4000);
        }
        this.disconnectDeviceByCar = false;
    }

    public void sendConnectHfpMessage(String address) {
        Log.d(TAG, "sendConnectHfpMessage : " + address);
        Message msg = new Message();
        msg.what = 2;
        msg.obj = address;
        this.handler.sendMessageDelayed(msg, 1000);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean loopConnectDevice() {
        DeviceInfo deviceInfo;
        Log.d(TAG, "loopConnectDevice : " + this.autoConnectDeviceIndex);
        int i = 0;
        if (this.autoConnectDeviceIndex > this.DevicesList.size()) {
            return false;
        }
        for (String tempAddr : this.DevicesList.keySet()) {
            DeviceInfo deviceInfo2 = this.DevicesList.get(tempAddr);
            if (deviceInfo2 != null) {
                if (!this.isPairing) {
                    int i2 = deviceInfo2.hf_state;
                    CommandHfpImp commandHfpImp = this.service.hfp;
                    if (i2 == 2) {
                    }
                }
                Log.d(TAG, "autoConnect return : " + this.isPairing + " , " + deviceInfo2.hf_state + " , " + deviceInfo2.addr);
                return false;
            }
        }
        Iterator<String> it = this.DevicesList.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String addr = it.next();
            if (i != this.autoConnectDeviceIndex || (deviceInfo = this.DevicesList.get(addr)) == null) {
                i++;
            } else if (this.disconnectException) {
                sendConnectHfpMessage(this.outOfRangeDeviceAddress);
            } else {
                sendConnectHfpMessage(deviceInfo.addr);
            }
        }
        this.firstDeviceToConnectTwo++;
        Log.d(TAG, "firstDeviceToConnectTwo : " + this.firstDeviceToConnectTwo);
        if (!this.disconnectException && this.firstDeviceToConnectTwo >= 2) {
            Log.d(TAG, "----------------------->> Index : " + this.autoConnectDeviceIndex);
            this.autoConnectDeviceIndex = this.autoConnectDeviceIndex + 1;
        }
        return true;
    }

    public void sendAutoConnectCancelMessage() {
        Log.d(TAG, "sendAutoConnectCancelMessage");
        Message msg = new Message();
        msg.what = 4;
        this.handler.sendMessageDelayed(msg, 500);
    }

    /* access modifiers changed from: private */
    public class AutoConnectTask extends TimerTask {
        private AutoConnectTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.d(CommandBluetoothImp.TAG, "AutoConnectTask currentAutoConnectMode : " + CommandBluetoothImp.this.currentAutoConnectMode + " , " + CommandBluetoothImp.this.isBtConnected());
            if (CommandBluetoothImp.this.isBtConnected() || !CommandBluetoothImp.currentEnable) {
                CommandBluetoothImp.this.sendAutoConnectCancelMessage();
                return;
            }
            int i = CommandBluetoothImp.this.currentAutoConnectMode;
            if (i == 0) {
                CommandBluetoothImp.this.loopConnectDevice();
            } else if (i == 1) {
                if (CommandBluetoothImp.this.loopConnectDevice()) {
                    CommandBluetoothImp.access$808(CommandBluetoothImp.this);
                    if (CommandBluetoothImp.this.firstDeviceToConnectTwo == 1) {
                        CommandBluetoothImp.access$810(CommandBluetoothImp.this);
                    }
                    Log.d(CommandBluetoothImp.TAG, "autoConnectCount : " + CommandBluetoothImp.this.autoConnectCount + " , current : " + CommandBluetoothImp.this.autoConnectCurrentCount);
                }
                if (CommandBluetoothImp.this.autoConnectCount == CommandBluetoothImp.this.autoConnectCurrentCount) {
                    CommandBluetoothImp.this.timeOfAutoConnectStop();
                }
            } else if (i == 2) {
                CommandBluetoothImp.this.loopConnectDevice();
                long currentTime = System.currentTimeMillis();
                Log.d(CommandBluetoothImp.TAG, "AutoConnectTask currentTime : " + currentTime);
                if (currentTime - CommandBluetoothImp.this.autoConnectTimeStart >= CommandBluetoothImp.this.autoConnectTime) {
                    Log.d(CommandBluetoothImp.TAG, "autoConnectTime : " + CommandBluetoothImp.this.autoConnectTime);
                    CommandBluetoothImp.this.timeOfAutoConnectStop();
                }
            }
            if (CommandBluetoothImp.this.autoConnectDeviceIndex == CommandBluetoothImp.this.DevicesList.size()) {
                CommandBluetoothImp.this.autoConnectDeviceIndex = 0;
            }
        }
    }

    public void timeOfAutoConnect() {
        Log.d(TAG, "timeOfAutoConnect : " + this.autoConnectTimer);
        if (this.autoConnectTimer == null) {
            this.autoConnectTimer = new Timer(true);
            this.autoConnectTask = new AutoConnectTask();
            this.autoConnectDeviceIndex = 0;
            if (this.currentAutoConnectMode == 2 && this.autoConnectTimeStart == 0) {
                this.autoConnectTimeStart = System.currentTimeMillis();
                Log.d(TAG, "autoConnectTimeStart : " + this.autoConnectTimeStart);
            }
            this.autoConnectTimer.schedule(this.autoConnectTask, 500, 5000);
        }
    }

    public void timeOfAutoConnectStop() {
        Log.d(TAG, "timeOfAutoConnectStop : " + this.autoConnectTimer);
        this.autoConnectDeviceIndex = 0;
        this.firstDeviceToConnectTwo = 0;
        Timer timer = this.autoConnectTimer;
        if (timer != null) {
            timer.cancel();
            this.autoConnectTimer.purge();
            this.autoConnectTimer = null;
            this.autoConnectTask = null;
            this.autoConnectCurrentCount = 0;
            this.autoConnectTimeStart = 0;
        }
    }

    public void sendGapSetLinkPolicyMessage() {
    }

    public void gapSetLinkPolicy() {
        Log.d(TAG, "gapSetLinkPolicy");
        DeviceInfo[] deviceInfos = loopFindConnectDevices();
        if (deviceInfos[0] != null && deviceInfos[1] != null) {
            this.service.getCommand().cancelPairMode();
        } else if (!this.pairable) {
            this.service.getCommand().setPairMode();
        }
    }
}
