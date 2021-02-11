package com.goodocom.sharedata;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.bean.DeviceInfo;
import com.goodocom.gocsdkserver.GocsdkService;

public class AdayoShareInfoImple {
    private static final String TAG = "GoodocomSharedata";
    private String mShareBtConnStateTempStr = BuildConfig.FLAVOR;
    private String mShareBtMusicInfoTempStr = BuildConfig.FLAVOR;
    private String mShareBtOtherInfoTempStr = BuildConfig.FLAVOR;
    private String mShareBtPhoneInfoTempStr = BuildConfig.FLAVOR;
    private Handler mShareDataHandler = new Handler();
    private GocsdkService service;

    public AdayoShareInfoImple(GocsdkService service2) {
        Log.e(TAG, "CommandPbapImp");
        this.service = service2;
    }

    public void mShareBtMusicInfo(int opt, String title, String artist, String album, long songPos, long songLen, String albumPic, int playMode, int playState) {
        Exception e;
        Log.d(TAG, "title:" + title + " artist:" + artist + " album: " + album + " songPos:" + songPos + " songLen:" + songLen + " albumPic:" + albumPic + " playMode:" + playMode + " playState:" + playState);
        String dataStr = "{\"current_music_title\":\"" + string2Json(title) + "\",\"current_music_artist\":\"" + string2Json(artist) + "\",\"current_music_album\":\"" + string2Json(album) + "\",\"current_play_time\":\"" + songPos + "\",\"total_play_time\":\"" + songLen + "\",\"album_pic\":\"" + string2Json(albumPic) + "\",\"play_mode\":" + playMode + ",\"play_state\":" + playState + ",\"music_list_info\":[{\"music_title\":\"" + title + "\",\"music_artist\":\"" + artist + "\"}]}";
        try {
            if (dataStr.equals(this.mShareBtMusicInfoTempStr)) {
                Log.d(TAG, "mShareBtMusicInfo 数据重复，拒绝发送！！！");
                return;
            }
            this.mShareBtMusicInfoTempStr = dataStr;
            JSONObject json = JSON.parseObject(dataStr);
            Log.d(TAG, "send json=" + json);
            try {
                sendHandlerMessage(opt, "json", json.toString());
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public void mShareBtPhoneInfo(int callState, String callHeadSculp, String callNum, String callName, String callTime, boolean answerType, boolean micEnable) {
        String dataStr = "{\"call_state\":" + callState + ",\"call_headsculp\":\"" + string2Json(callHeadSculp) + "\",\"call_number\":\"" + string2Json(callNum) + "\",\"call_name\":\"" + string2Json(callName) + "\",\"call_time\":\"" + callTime + "\",\"answer_type\":" + answerType + ",\"mic_enable\":" + micEnable + "}";
        if (dataStr.equals(this.mShareBtPhoneInfoTempStr)) {
            Log.d(TAG, "mShareBtPhoneInfo 数据重复，拒绝发送！！！");
            return;
        }
        this.mShareBtPhoneInfoTempStr = dataStr;
        try {
            sendHandlerMessage(23, "json", JSON.parseObject(dataStr).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mShareBtOtherInfo(int electricQuantity, int signalLevel, int missedCalls, int unreadSMS, String operator, int downState) {
        String dataStr = "{\"electric_quantity\":" + electricQuantity + ",\"signal_level\":" + signalLevel + ",\"unread_missed_call_count\":" + missedCalls + ",\"unread_SMS\":" + unreadSMS + ",\"operator\":\"" + operator + "\",\"downState\":" + downState + "}";
        if (dataStr.equals(this.mShareBtOtherInfoTempStr)) {
            Log.d(TAG, "mShareBtOtherInfo 数据重复，拒绝发送！！！");
            return;
        }
        this.mShareBtOtherInfoTempStr = dataStr;
        string2Json(dataStr);
        try {
            sendHandlerMessage(24, "json", JSON.parseObject(dataStr).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mShareBtConnState(boolean isBtEnable, String mainDevicesName, String btLocalName, boolean hfpState, boolean a2dpState, boolean hfpState2, boolean a2dpState2, String phoneName, String phoneName2) {
        String dataStr = "{\"is_bt_enabled\":" + isBtEnable + ",\"mainDevicesName\":\"" + mainDevicesName + "\",\"btLocalName\":\"" + btLocalName + "\",\"is_hfp_connected\":" + hfpState + ",\"is_a2dp_connected\":" + a2dpState + ",\"is_hfp_connected_2\":" + hfpState2 + ",\"is_a2dp_connected_2\":" + a2dpState2 + ",\"phoneName\":\"" + phoneName + "\",\"phoneName_2\":\"" + phoneName2 + "\"}";
        if (dataStr.equals(this.mShareBtConnStateTempStr)) {
            Log.d(TAG, "mShareBtConnState 数据重复，拒绝发送！！！");
            return;
        }
        Log.e(TAG, "mShareBtConnState : " + dataStr);
        this.mShareBtConnStateTempStr = dataStr;
        try {
            sendHandlerMessage(27, "json", JSON.parseObject(dataStr).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendHandlerMessage(int what, String key, String val) {
        Message msg = Message.obtain(this.mShareDataHandler, what);
        if (!(key == null || val == null)) {
            Bundle b = new Bundle();
            b.putString(key, val);
            msg.setData(b);
        }
        this.mShareDataHandler.sendMessage(msg);
    }

    public void sendShareBtConnState(boolean btEnable, String address) {
        boolean hf1State;
        boolean a2dp1State;
        boolean hf2State;
        boolean a2dp2State;
        boolean hf1State2;
        boolean a2dp1State2;
        Log.e(TAG, "sendShareBtConnState : " + address + "   btEnable   " + btEnable);
        DeviceInfo[] deviceInfos = this.service.bluetooth.loopFindConnectDevices();
        if (deviceInfos[0] == null && deviceInfos[1] == null) {
            mShareBtConnState(btEnable, BuildConfig.FLAVOR, this.service.bluetooth.getBtLocalName1(), false, false, false, false, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        } else if (deviceInfos[0] == null || deviceInfos[1] != null) {
            Log.d(TAG, "device_0 : hf : " + deviceInfos[0].hf_state + ", a2dp : " + deviceInfos[0].a2dp_state + "; device_1 : hf : " + deviceInfos[1].hf_state + ", a2dp : " + deviceInfos[1].a2dp_state);
            if (deviceInfos[0].hf_state >= 3) {
                hf1State = true;
            } else {
                hf1State = false;
            }
            if (deviceInfos[0].a2dp_state > 1) {
                a2dp1State = true;
            } else {
                a2dp1State = false;
            }
            if (deviceInfos[1].hf_state >= 3) {
                hf2State = true;
            } else {
                hf2State = false;
            }
            if (deviceInfos[1].a2dp_state > 1) {
                a2dp2State = true;
            } else {
                a2dp2State = false;
            }
            mShareBtConnState(btEnable, deviceInfos[0].name, this.service.bluetooth.getBtLocalName1(), hf1State, a2dp1State, hf2State, a2dp2State, deviceInfos[0].name, deviceInfos[1].name);
        } else {
            Log.d(TAG, "device_0 , hf : " + deviceInfos[0].hf_state + ", a2dp : " + deviceInfos[0].a2dp_state);
            if (deviceInfos[0].hf_state >= 3) {
                hf1State2 = true;
            } else {
                hf1State2 = false;
            }
            if (deviceInfos[0].a2dp_state > 1) {
                a2dp1State2 = true;
            } else {
                a2dp1State2 = false;
            }
            mShareBtConnState(btEnable, deviceInfos[0].name, this.service.bluetooth.getBtLocalName1(), hf1State2, a2dp1State2, false, false, deviceInfos[0].name, BuildConfig.FLAVOR);
        }
    }

    public static String string2Json(String s) {
        if (s == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\f') {
                sb.append("\\f");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\"') {
                sb.append("\\\"");
            } else if (c == '/') {
                sb.append("\\/");
            } else if (c != '\\') {
                switch (c) {
                    case '\b':
                        sb.append("\\b");
                        continue;
                    case '\t':
                        sb.append("\\t");
                        continue;
                    case '\n':
                        sb.append("\\n");
                        continue;
                    default:
                        sb.append(c);
                        continue;
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
