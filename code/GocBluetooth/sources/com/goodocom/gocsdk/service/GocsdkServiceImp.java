package com.goodocom.gocsdk.service;

import android.os.RemoteException;
import android.util.Log;
import com.goodocom.gocsdk.Commands;
import com.goodocom.gocsdk.IGocsdkCallback;
import com.goodocom.gocsdk.IGocsdkService;

public class GocsdkServiceImp extends IGocsdkService.Stub {
    public static final String TAG = "GocsdkServiceImp";
    private GocsdkService service;

    public GocsdkServiceImp(GocsdkService service2) {
        this.service = service2;
    }

    private void write(String str) {
        this.service.write(str);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void restBluetooth() throws RemoteException {
        write(Commands.RESET_BLUE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getLocalName() throws RemoteException {
        write(Commands.MODIFY_LOCAL_NAME);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void setLocalName(String name) throws RemoteException {
        write(Commands.MODIFY_LOCAL_NAME + name);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getPinCode() throws RemoteException {
        write(Commands.MODIFY_PIN_CODE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void setPinCode(String pincode) throws RemoteException {
        write(Commands.MODIFY_PIN_CODE + pincode);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getLocalAddress() throws RemoteException {
        write(Commands.LOCAL_ADDRESS);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getAutoConnectAnswer() throws RemoteException {
        write(Commands.INQUIRY_AUTO_CONNECT_ACCETP);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void setAutoConnect() throws RemoteException {
        write(Commands.SET_AUTO_CONNECT_ON_POWER);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void cancelAutoConnect() throws RemoteException {
        write(Commands.UNSET_AUTO_CONNECT_ON_POWER);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void setAutoAnswer() throws RemoteException {
        write(Commands.SET_AUTO_ANSWER);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void cancelAutoAnswer() throws RemoteException {
        write(Commands.UNSET_AUTO_ANSWER);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getVersion() throws RemoteException {
        write(Commands.INQUIRY_VERSION_DATE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void setPairMode() throws RemoteException {
        write(Commands.PAIR_MODE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void cancelPairMode() throws RemoteException {
        write(Commands.CANCEL_PAIR_MOD);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectLast() throws RemoteException {
        write(Commands.CONNECT_DEVICE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectA2dp(String addr) throws RemoteException {
        write(Commands.CONNECT_A2DP + addr);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectHFP(String addr) throws RemoteException {
        write(Commands.CONNECT_HFP + addr);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectHid(String addr) throws RemoteException {
        write(Commands.CONNECT_HID);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectSpp(String addr) throws RemoteException {
        write(Commands.CONNECT_SPP_ADDRESS);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void disconnect() throws RemoteException {
        write(Commands.DISCONNECT_DEVICE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void disconnectA2DP() throws RemoteException {
        write(Commands.DISCONNECT_A2DP);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void disconnectHFP() throws RemoteException {
        write(Commands.DISCONNECT_HFP);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void disconnectHid() {
        write(Commands.DISCONNECT_HID);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void disconnectSpp() throws RemoteException {
        write(Commands.SPP_DISCONNECT);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void deletePair(String addr) throws RemoteException {
        write(Commands.DELETE_PAIR_LIST + addr);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void startDiscovery() throws RemoteException {
        write(Commands.START_DISCOVERY);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getPairList() throws RemoteException {
        write(Commands.INQUIRY_PAIR_RECORD);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void stopDiscovery() throws RemoteException {
        write(Commands.STOP_DISCOVERY);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneAnswer() throws RemoteException {
        write(Commands.ACCEPT_INCOMMING);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneHangUp() throws RemoteException {
        write(Commands.REJECT_INCOMMMING);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneDail(String phonenum) throws RemoteException {
        write(Commands.DIAL + phonenum);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneTransmitDTMFCode(char code) throws RemoteException {
        write(Commands.DTMF + code);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneTransfer() throws RemoteException {
        write(Commands.VOICE_TRANSFER);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneTransferBack() throws RemoteException {
        write(Commands.VOICE_TO_BLUE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneVoiceDail() throws RemoteException {
        write(Commands.VOICE_DIAL);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void cancelPhoneVoiceDail() throws RemoteException {
        write(Commands.CANCEL_VOID_DIAL);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void phoneBookStartUpdate() throws RemoteException {
        write(Commands.SET_PHONE_PHONE_BOOK);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void callLogstartUpdate(int type) throws RemoteException {
        if (type == 1) {
            write(Commands.SET_OUT_GOING_CALLLOG);
        } else if (type == 2) {
            write(Commands.SET_MISSED_CALLLOG);
        } else if (type == 3) {
            write(Commands.SET_INCOMING_CALLLOG);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicPlayOrPause() throws RemoteException {
        write(Commands.PLAY_PAUSE_MUSIC);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musiclist() throws RemoteException {
        write(Commands.INQUIRY_MUSIC_LIST);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicIntoList(String index) throws RemoteException {
        Log.e(Commands.INQUIRY_MUSIC_INTER, index);
        write(Commands.INQUIRY_MUSIC_INTER + index);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicIntoPre() throws RemoteException {
        Log.e(TAG, Commands.INQUIRY_MUSIC_PRE);
        write(Commands.INQUIRY_MUSIC_PRE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicPlayNow(String index) throws RemoteException {
        Log.e("fjasmin", Commands.INQUIRY_MUSIC_IN);
        write(Commands.INQUIRY_MUSIC_IN + index);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getOnMusicCover(String index) throws RemoteException {
        Log.e("fjasmin", Commands.IND_MUSIC_COVER_SUCEESS);
        write(Commands.IND_MUSIC_COVER_SUCEESS + index);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getBtXchange(String addr) throws RemoteException {
        Log.e("fjasmin", Commands.INQUIRY_BT_XCHANGE + ":" + addr);
        StringBuilder sb = new StringBuilder();
        sb.append(Commands.INQUIRY_BT_XCHANGE);
        sb.append(addr);
        write(sb.toString());
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicStop() throws RemoteException {
        write(Commands.STOP_MUSIC);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicPrevious() throws RemoteException {
        Log.d("app", "write musicprevious");
        write(Commands.PREV_SOUND);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicNext() throws RemoteException {
        write(Commands.NEXT_SOUND);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicMute() throws RemoteException {
        write(Commands.MUSIC_MUTE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicUnmute() throws RemoteException {
        write(Commands.MUSIC_UNMUTE);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicBackground() throws RemoteException {
        write(Commands.MUSIC_BACKGROUND);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicNormal() throws RemoteException {
        write(Commands.MUSIC_NORMAL);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void registerCallback(IGocsdkCallback callback) throws RemoteException {
        this.service.registerCallback(callback);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void unregisterCallback(IGocsdkCallback callback) throws RemoteException {
        this.service.unregisterCallback(callback);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void hidMouseMove(String point) throws RemoteException {
        write(Commands.MOUSE_MOVE + point);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void hidMouseUp(String point) throws RemoteException {
        write(Commands.MOUSE_MOVE + point);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void hidMousDown(String point) throws RemoteException {
        write(Commands.MOUSE_DOWN + point);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void hidHomeClick() throws RemoteException {
        write(Commands.MOUSE_HOME);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void hidBackClick() throws RemoteException {
        write(Commands.MOUSE_BACK);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void hidMenuClick() throws RemoteException {
        write(Commands.MOUSE_MENU);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void sppSendData(String addr, String data) throws RemoteException {
        write(Commands.SPP_SEND_DATA + addr + data);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getMusicInfo() {
        write(Commands.INQUIRY_MUSIC_INFO);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void inqueryHfpStatus() {
        write(Commands.INQUIRY_HFP_STATUS);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getCurrentDeviceAddr() {
        write(Commands.INQUIRY_CUR_BT_ADDR);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getCurrentDeviceName() {
        write(Commands.INQUIRY_CUR_BT_NAME);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectDevice(String addr) throws RemoteException {
        write(Commands.CONNECT_DEVICE + addr);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void setProfileEnabled(boolean[] enabled) throws RemoteException {
        String str = "";
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

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getProfileEnabled() throws RemoteException {
        write(Commands.SET_PROFILE_ENABLED);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getMessageInboxList() throws RemoteException {
        write(Commands.GET_MESSAGE_INBOX_LIST);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getMessageText(String handle) throws RemoteException {
        write(Commands.GET_MESSAGE_TEXT + handle);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getMessageSentList() throws RemoteException {
        write(Commands.GET_MESSAGE_SENT_LIST);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void getMessageDeletedList() throws RemoteException {
        write(Commands.GET_MESSAGE_DELETED_LIST);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void pauseDownLoadContact() throws RemoteException {
        write(Commands.PAUSE_PHONEBOOK_DOWN);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void connectA2dpp() throws RemoteException {
        write(Commands.CONNECT_A2DP);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicPlay() throws RemoteException {
        write(Commands.PLAY_MUSIC);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void musicPause() throws RemoteException {
        write(Commands.PAUSE_MUSIC);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void pairedDevice(String addr) throws RemoteException {
        write(Commands.START_PAIR + addr);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void muteOpenAndClose(int status) throws RemoteException {
        write(Commands.MIC_OPEN_CLOSE + status);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void openBlueTooth() throws RemoteException {
        write(Commands.OPEN_BT);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void closeBlueTooth() throws RemoteException {
        write(Commands.CLOSE_BT);
    }

    @Override // com.goodocom.gocsdk.IGocsdkService
    public void inqueryA2dpStatus() throws RemoteException {
        write(Commands.INQUIRY_A2DP_STATUS);
    }
}
