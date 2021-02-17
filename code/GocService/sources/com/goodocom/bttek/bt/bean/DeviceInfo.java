package com.goodocom.bttek.bt.bean;

import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.aidl.GocHfpClientCall;
import com.goodocom.bttek.bt.res.NfDef;
import java.util.ArrayList;

public class DeviceInfo {
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
    public static final int SERVICE__CALLING = 4;
    public static final int SERVICE__CONNECTED = 3;
    public static final int SERVICE__CONNECTING = 2;
    public static final int SERVICE__DISCONNECTED = 1;
    public static final int SERVICE__DISCONNECTING = 9;
    public static final int SERVICE__HELD = 8;
    public static final int SERVICE__INCOMING = 5;
    public static final int SERVICE__READY = 0;
    public static final int SERVICE__STREAMING = 4;
    public static final int SERVICE__TALKING = 6;
    public static final int SERVICE__WAITING = 7;
    public boolean RingBandSupport = false;
    private String TAG = "gocsdkDeviceinfo";
    public int a2dp_state = 0;
    public String addr = NfDef.DEFAULT_ADDRESS;
    public int avrcp_state = 0;
    public int battery = 0;
    public volatile ArrayList<GocHfpClientCall> calllist = null;
    public int connectIndex = -1;
    public byte deviceType = 0;
    public int hf_state = 0;
    public boolean isAclConnected = false;
    public boolean isScoConnected = false;
    public int map_state = 0;
    public String name = "unkown";
    public String operator = BuildConfig.FLAVOR;
    public int pbap_state = 0;
    public String phoneSimNumber = BuildConfig.FLAVOR;
    public int playMode = 2;
    public int profile_connected = 0;
    public int profile_supported = 0;
    public int signal = 0;
    public long sort = 0;

    public DeviceInfo(String addr2, String name2, byte deviceType2) {
        this.addr = addr2;
        this.name = name2;
        this.deviceType = deviceType2;
        this.profile_supported = 0;
        this.profile_connected = 0;
        this.calllist = new ArrayList<>();
        this.hf_state = 0;
        this.a2dp_state = 0;
        this.avrcp_state = 0;
        this.pbap_state = 0;
        this.isScoConnected = false;
        this.isAclConnected = false;
        this.connectIndex = -1;
        this.operator = BuildConfig.FLAVOR;
        this.playMode = 2;
    }

    public boolean is_Sco_connected() {
        if (this.profile_connected != 0) {
            return false;
        }
        return this.isScoConnected;
    }

    public boolean is_connected() {
        if (this.profile_connected != 0) {
            return true;
        }
        return false;
    }

    public boolean is_connected_by_profile(int profile) {
        if ((this.profile_connected & profile) != 0) {
            return true;
        }
        return false;
    }

    public void set_profile_connected(int profile, boolean is_connect) {
        Log.d(this.TAG, "set_profile_connected old:" + this.profile_connected + " new:" + profile + " is_connect:" + is_connect);
        if (is_connect) {
            this.profile_connected |= profile;
            this.isAclConnected = true;
            return;
        }
        this.profile_connected &= ~profile;
    }

    public void set_profile_supported(int profile) {
        this.profile_connected |= profile;
    }
}
