package com.goodocom.gocsdkserver;

import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.gocsdk.Commands;

public class GocsdkCommandSender {
    private static final String TAG = "GoodocomSender";
    private GocsdkService service;

    public GocsdkCommandSender(GocsdkService service2) {
        this.service = service2;
    }

    private void write(String str) {
        this.service.write(str);
    }

    public void restBluetooth() {
        write(Commands.RESET_BLUE);
    }

    public void QueryBluetoothInit() {
        write(Commands.RESET_BLUE + "9");
    }

    public void getLocalName() {
        write(Commands.MODIFY_LOCAL_NAME);
    }

    public void BtDeviceCheck(String checknumber) {
        write(Commands.BTDEVICECHECK + checknumber);
    }

    public void setLocalName(String name) {
        write(Commands.MODIFY_LOCAL_NAME + name);
    }

    public void setRingPath(String path) {
        write(Commands.SET_RING_PATH + path);
    }

    public void getPinCode() {
        write(Commands.MODIFY_PIN_CODE);
    }

    public void setPinCode(String pincode) {
        write(Commands.MODIFY_PIN_CODE + pincode);
    }

    public void getLocalAddress() {
        write(Commands.LOCAL_ADDRESS);
    }

    public void getAutoConnectAnswer() {
        write(Commands.INQUIRY_AUTO_CONNECT_ACCETP);
    }

    public void setAutoConnect() {
        Log.e(TAG, "setAutoConnect");
        write(Commands.SET_AUTO_CONNECT_ON_POWER);
    }

    public void cancelAutoConnect() {
        Log.d(TAG, "cancelAutoConnect");
        write(Commands.UNSET_AUTO_CONNECT_ON_POWER);
    }

    public void setAutoAnswer(int time) {
        write(Commands.SET_AUTO_ANSWER + time);
    }

    public void cancelAutoAnswer() {
        write(Commands.UNSET_AUTO_ANSWER);
    }

    public void inqueryAutoConnectAnswer() {
        write(Commands.INQUIRY_AUTO_CONNECT_ACCETP);
    }

    public void getVersion() {
        write(Commands.INQUIRY_VERSION_DATE);
    }

    public void setBtMainDevice(String address) {
        Log.e("setdr", "setBtMainDevice: " + address);
        if (address == null) {
            write(Commands.CMD_ADDR);
            return;
        }
        write(Commands.CMD_ADDR + GocsdkService.droidAddr2Goc(address));
    }

    public void setDualDevice(String _on) {
        write(Commands.CMD_CONNECT_MODE + _on);
    }

    public void setDualCall(String _on) {
        write(Commands.CMD_DUAL_CALL_MODE + _on);
    }

    public void setPairMode() {
        write(Commands.PAIR_MODE);
    }

    public void cancelPairMode() {
        write(Commands.CANCEL_PAIR_MOD);
    }

    public void connectLast() {
        Log.e(TAG, "connectLast");
        write(Commands.CONNECT_DEVICE);
    }

    public void connectA2dp(String addr) {
        write(Commands.CONNECT_A2DP + GocsdkService.droidAddr2Goc(addr));
    }

    public void connectHFP(String addr) {
        write(Commands.CONNECT_HFP + GocsdkService.droidAddr2Goc(addr));
    }

    public void connectHid(String addr) {
        write(Commands.CONNECT_HID);
    }

    public void connectSpp(String addr) {
        write(Commands.CONNECT_SPP_ADDRESS);
    }

    public void disconnect(String addr) {
        write(Commands.DISCONNECT_DEVICE + GocsdkService.droidAddr2Goc(addr));
    }

    public void disconnectA2DP(String addr) {
        write(Commands.DISCONNECT_A2DP + GocsdkService.droidAddr2Goc(addr));
    }

    public void disconnectHFP(String addr) {
        write(Commands.DISCONNECT_HFP + GocsdkService.droidAddr2Goc(addr));
    }

    public void disconnectHid() {
        write(Commands.DISCONNECT_HID);
    }

    public void disconnectSpp() {
        write(Commands.SPP_DISCONNECT);
    }

    public void deletePair(String addr) {
        if (addr == null) {
            write(Commands.DELETE_PAIR_LIST);
            return;
        }
        write(Commands.DELETE_PAIR_LIST + GocsdkService.droidAddr2Goc(addr));
    }

    public void startDiscovery() {
        write(Commands.START_DISCOVERY);
    }

    public void getPairList() {
        write(Commands.INQUIRY_PAIR_RECORD);
    }

    public void stopDiscovery() {
        write(Commands.STOP_DISCOVERY);
    }

    public void HangupCurrentphoneAnswer() {
        write(Commands.HANG_UP_CURRENT_ACCEPT_WAIT);
    }

    public void HoldCurrentphoneAnswer() {
        write(Commands.HOLD_CURRENT_ACCEPT_WAIT);
    }

    public void phoneAnswer() {
        write(Commands.ACCEPT_INCOMMING);
    }

    public void phoneHangUp(String address) {
        write(Commands.FINISH_PHONE + GocsdkService.droidAddr2Goc(address));
    }

    public void phoneReject(String address) {
        write(Commands.REJECT_INCOMMMING + GocsdkService.droidAddr2Goc(address));
    }

    public void phoneRejecthold(String address) {
        write(Commands.HANG_UP_WAIT_PHONE + GocsdkService.droidAddr2Goc(address));
    }

    public void phoneDail(String phonenum) {
        write(Commands.DIAL + phonenum);
    }

    public void phoneReDial() {
        write(Commands.REDIAL);
    }

    public void phoneTransmitDTMFCode(String address, char code) {
        write(Commands.DTMF + GocsdkService.droidAddr2Goc(address) + code);
    }

    public void phoneTransfer(String address) {
        write(Commands.VOICE_TO_BLUE + GocsdkService.droidAddr2Goc(address));
    }

    public void phoneTransferBack(String address) {
        write(Commands.VOICE_TO_PHONE + GocsdkService.droidAddr2Goc(address));
    }

    public void setHfpAudioTransfer() {
        write(Commands.VOICE_TRANSFER);
    }

    public void getHfpAudioFoucs(int status) {
        write(Commands.HF_GET_FOUCS + status);
    }

    public void phoneVoiceDail() {
        write(Commands.VOICE_DIAL);
    }

    public void cancelPhoneVoiceDail() {
        write(Commands.CANCEL_VOID_DIAL);
    }

    public void phoneBookStartUpdate_memory() {
        Log.e("sync_pbap", "phoneBookStartUpdate_memory>>>>>>");
        write(Commands.SET_PHONE_PHONE_BOOK);
    }

    public void phoneBookStartUpdate_sim() {
        write(Commands.SET_SIM_PHONE_BOOK);
    }

    public void callogStartUpdateOutGoing() {
        write(Commands.SET_OUT_GOING_CALLLOG);
    }

    public void callogStartUpdateMissed() {
        write(Commands.SET_MISSED_CALLLOG);
    }

    public void callogStartUpdateALL(int offset) {
        Log.e("main", "service.bluetooth.rspAddr>>> " + this.service.bluetooth.rspAddr + "   service.bluetooth.cmdMainAddr: " + this.service.bluetooth.cmdMainAddr + "      offset: " + offset);
        StringBuilder sb = new StringBuilder();
        sb.append(Commands.SET_ALL_CALLLOG);
        sb.append(offset);
        write(sb.toString());
    }

    public void calllogByCallUptate(int offset) {
        if (this.service.pbap.calllogDownopt) {
            write(Commands.SET_ALL_CALLLOG + offset);
        }
    }

    public void callogStartUpdateIncoming() {
        write(Commands.SET_INCOMING_CALLLOG);
    }

    public void callLogstartUpdate(int type) {
        if (type == 1) {
            write(Commands.SET_OUT_GOING_CALLLOG);
        } else if (type == 2) {
            write(Commands.SET_MISSED_CALLLOG);
        } else if (type == 3) {
            write(Commands.SET_INCOMING_CALLLOG);
        }
    }

    public void musicStop() {
        write(Commands.STOP_MUSIC);
    }

    public void musicPrevious() {
        write(Commands.PREV_SOUND);
    }

    public void musicNext() {
        write(Commands.NEXT_SOUND);
    }

    public void musicMute() {
        write(Commands.MUSIC_MUTE);
    }

    public void musicUnmute() {
        write(Commands.MUSIC_UNMUTE);
    }

    public void musicBackground() {
        write(Commands.MUSIC_BACKGROUND);
    }

    public void musicNormal() {
        write(Commands.MUSIC_NORMAL);
    }

    public void hidMouseMove(String point) {
        write(Commands.MOUSE_MOVE + point);
    }

    public void hidMouseUp(String point) {
        write(Commands.MOUSE_MOVE + point);
    }

    public void hidMousDown(String point) {
        write(Commands.MOUSE_DOWN + point);
    }

    public void hidHomeClick() {
        write(Commands.MOUSE_HOME);
    }

    public void hidBackClick() {
        write(Commands.MOUSE_BACK);
    }

    public void hidMenuClick() {
        write(Commands.MOUSE_MENU);
    }

    public void sppSendData(String addr, String data) {
        write(Commands.SPP_SEND_DATA + GocsdkService.droidAddr2Goc(addr) + data);
    }

    public void getMusicInfo() {
        write(Commands.INQUIRY_MUSIC_INFO);
    }

    public void inqueryHfpStatus() {
        write(Commands.INQUIRY_HFP_STATUS);
    }

    public void getCurrentDeviceAddr() {
        write(Commands.INQUIRY_CUR_BT_ADDR);
    }

    public void getCurrentDeviceName() {
        write(Commands.INQUIRY_CUR_BT_NAME);
    }

    public void connectDevice(String addr) {
        Log.e(TAG, "connectDevice:" + addr);
        write(Commands.CONNECT_DEVICE + GocsdkService.droidAddr2Goc(addr));
    }

    public void setProfileEnabled(boolean[] enabled) {
        String str = BuildConfig.FLAVOR;
        int i = 0;
        while (i < enabled.length && i < 10) {
            str = enabled[i] ? str + "1" : str + "0";
            i++;
        }
        int len = str.length();
        for (int i2 = 0; i2 < 10 - len; i2++) {
            str = str + "0";
        }
        write(Commands.SET_PROFILE_ENABLED + str);
    }

    public void getProfileEnabled() {
        write(Commands.SET_PROFILE_ENABLED);
    }

    public void connectMapMessage(String addr) {
        write(Commands.CONNECT_MAP_MESSAGE + GocsdkService.droidAddr2Goc(addr));
    }

    public void diconnecyMapMessage(String addr) {
        write(Commands.DISCONNECT_MAP_MESSAGE + GocsdkService.droidAddr2Goc(addr));
    }

    public void getOneMapMessage(String hanlde) {
        write(Commands.MAP_MESSAGE_DETAIL_GET + hanlde);
    }

    public void sendMapMesssage(String data) {
        write(Commands.MAP_MESSAGE_SEND + data);
    }

    public void getMapMessage(String visit) {
        write(Commands.MAP_MESSAGE_LIST_FOLDER + visit);
    }

    public void deleteMapMessage(String handle) {
        write(Commands.MAP_DELETE_MESSAGES + handle);
    }

    public void pauseDownLoadContact() {
        write(Commands.PAUSE_PHONEBOOK_DOWN);
    }

    public void connectA2dpp() {
        write(Commands.CONNECT_A2DP);
    }

    public void musicPlay() {
        write(Commands.PLAY_MUSIC);
    }

    public void musicPause() {
        write(Commands.PAUSE_MUSIC);
    }

    public void paireDevice(String addr) {
        write(Commands.START_PAIR + GocsdkService.droidAddr2Goc(addr));
    }

    public void muteOpenAndClose(int status) {
        write(Commands.MIC_OPEN_CLOSE + status);
    }

    public void openBlueTooth() {
        write(Commands.OPEN_BT);
    }

    public void closeBlueTooth() {
        write(Commands.CLOSE_BT);
    }

    public void inqueryA2dpStatus() {
        write(Commands.INQUIRY_A2DP_STATUS);
    }

    public void inqueryAvrcpStatus() {
        write(Commands.INQUIRY_AVRCP_STATUS);
    }

    public void setA2dpVolume(int volume) {
        if (volume > 100) {
            volume = 100;
        }
        if (volume < 0) {
            volume = 0;
        }
        write(Commands.MUSIC_VOL_SET + volume);
    }

    public void stopPhonebookDown(String address) {
        StringBuilder sb = new StringBuilder();
        sb.append(Commands.STOP_PHONEBOOK_DOWN);
        GocsdkService gocsdkService = this.service;
        sb.append(GocsdkService.droidAddr2Goc(address));
        write(sb.toString());
    }
}
