package com.goodocom.sharedata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.bt.GocApplication;
import com.goodocom.gocsdkserver.CommandA2dpImp;
import com.goodocom.gocsdkserver.CommandAvrcpImp;
import com.goodocom.gocsdkserver.CommandBluetoothImp;
import com.goodocom.gocsdkserver.CommandHfpImp;
import com.goodocom.gocsdkserver.GocsdkService;

public class BtCommandReceiver extends BroadcastReceiver {
    public static final String MSG_SYS_STD_BT_MUSIC_PAUSE = "net.easyconn.a2dp.release";
    public static final String MSG_SYS_STD_BT_MUSIC_START = "net.easyconn.a2dp.acquire";
    public static final String MSG_SYS_STD_EASYCONN = "net.easyconn.bluetooth.call";
    public static final String MSG_SYS_STD_EASYCONN_CHECKSTATUS = "net.easyconn.bt.checkstatus";
    public static final String MSG_SYS_STD_EASYCONN_GWMEC = "net.easyconn.gwmec.bluetooth.call";
    public static final String MSG_SYS_STD_SCREEN_PAUSE = "net.easyconn.screen.pause";
    public static final String MSG_SYS_STD_SCREEN_START = "net.easyconn.screen.resume";
    public static final String ONKEYDOWN = "adayo.keyEvent.onKeyDown";
    public static final String ONKEYLONGPRESS = "adayo.keyEvent.onKeyLongPress";
    public static final String ONKEYUP = "adayo.keyEvent.onKeyUp";
    public static final String TAG = BtCommandReceiver.class.getSimpleName();
    private static boolean isEasyScreenOn = false;
    private static boolean mIsCarbitPlay = true;
    private static boolean mIsPhoneKeyLongPress = false;
    boolean isApplyA2dpAudioFocus;
    private CommandA2dpImp mCommandA2dp;
    private CommandAvrcpImp mCommandAvrcp;
    private CommandBluetoothImp mCommandBluetooth;
    private CommandHfpImp mCommandHfp;
    private GocsdkService mGocsdkService;
    private String mPhoneName;
    private String mPhoneNumber;

    public void setINfCommandA2dp(CommandA2dpImp commandA2dp) {
        this.mCommandA2dp = commandA2dp;
    }

    public void setINfCommandAvrcp(CommandAvrcpImp commandAvrcp) {
        this.mCommandAvrcp = commandAvrcp;
    }

    public void setINfCommandHfp(CommandHfpImp commandHfp) {
        this.mCommandHfp = commandHfp;
    }

    public void setINfCommandBluetooth(CommandBluetoothImp commandBluetooth) {
        this.mCommandBluetooth = commandBluetooth;
    }

    public void setGocsdkService(GocsdkService service) {
        this.mGocsdkService = service;
    }

    public String getPhoneName() {
        return this.mPhoneName;
    }

    public void setPhoneName(String phoneName) {
        this.mPhoneName = phoneName;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        String str = TAG;
        Log.i(str, "action:" + intent.getAction());
        String action = intent.getAction();
        switch (action.hashCode()) {
            case -1696288247:
                if (action.equals(ONKEYDOWN)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1548807104:
                if (action.equals(MSG_SYS_STD_EASYCONN_GWMEC)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1140741683:
                if (action.equals(MSG_SYS_STD_EASYCONN_CHECKSTATUS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -953524859:
                if (action.equals(MSG_SYS_STD_BT_MUSIC_PAUSE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -374337632:
                if (action.equals(MSG_SYS_STD_SCREEN_START)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -291134429:
                if (action.equals(MSG_SYS_STD_SCREEN_PAUSE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 541873696:
                if (action.equals(ONKEYLONGPRESS)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 699910594:
                if (action.equals(ONKEYUP)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1086625364:
                if (action.equals(MSG_SYS_STD_BT_MUSIC_START)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1981575181:
                if (action.equals(MSG_SYS_STD_EASYCONN)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                applyA2dpAudioFocus();
                return;
            case 1:
            default:
                return;
            case 2:
                Log.e(TAG, "----net.easyconn.bt.checkstatus----");
                reportConnectionToEC(context);
                return;
            case 3:
            case 4:
                try {
                    this.mPhoneName = intent.getStringExtra("phoneName");
                    this.mPhoneNumber = intent.getStringExtra("phoneNum");
                    String str2 = TAG;
                    Log.e(str2, "MSG_SYS_STD_EASYCONN_GWMEC Number=" + this.mPhoneNumber);
                    if (this.mCommandHfp.isHfpConnected(this.mCommandBluetooth.cmdMainAddr) && this.mGocsdkService.callState == 7) {
                        String str3 = TAG;
                        Log.e(str3, "MSG_SYS_STD_EASYCONN_GWMEC call number " + this.mPhoneNumber + " , " + this.mPhoneName + " , " + this.mCommandHfp.isAnswerType);
                        if (!TextUtils.isEmpty(this.mPhoneNumber)) {
                            if (!this.mCommandHfp.isAnswerType) {
                                GocApplication.INSTANCE.mAnswerToPhoneOrToCar = true;
                                String str4 = TAG;
                                Log.e(str4, "Competitor.mAnswerToPhoneOrToCar =" + GocApplication.INSTANCE.mAnswerToPhoneOrToCar);
                            }
                            this.mCommandHfp.reqHfpDialCall(this.mPhoneNumber);
                            return;
                        }
                        return;
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 5:
                isEasyScreenOn = true;
                Log.e(TAG, "----onReceive() action=MSG_SYS_STD_SCREEN_START----");
                return;
            case 6:
                isEasyScreenOn = false;
                Log.e(TAG, "----onReceive() action=MSG_SYS_STD_SCREEN_PAUSE----");
                return;
            case 7:
                String hardKey = intent.getStringExtra("hardKey");
                String str5 = TAG;
                Log.e(str5, "Action=" + intent.getAction() + ",hardKey=" + hardKey);
                if ("K_PHONE".equals(intent.getStringExtra("hardKey")) || "K_PHONE_ON".equals(intent.getStringExtra("hardKey"))) {
                    mIsPhoneKeyLongPress = false;
                    return;
                }
                return;
            case '\b':
                String hardKey2 = intent.getStringExtra("hardKey");
                String str6 = TAG;
                Log.e(str6, "Action=" + intent.getAction() + ",hardKey=" + hardKey2);
                if (!"K_PHONE".equals(intent.getStringExtra("hardKey")) && !"K_PHONE_ON".equals(intent.getStringExtra("hardKey"))) {
                    return;
                }
                if (this.mGocsdkService.callState != 7) {
                    mIsPhoneKeyLongPress = true;
                    return;
                } else {
                    mIsPhoneKeyLongPress = false;
                    return;
                }
            case '\t':
                String hardKey3 = intent.getStringExtra("hardKey");
                String str7 = TAG;
                Log.e(str7, "Action=" + intent.getAction() + ",hardKey=" + hardKey3);
                if (!"K_PHONE".equals(intent.getStringExtra("hardKey")) && !"K_PHONE_ON".equals(intent.getStringExtra("hardKey"))) {
                    return;
                }
                if (mIsPhoneKeyLongPress) {
                    mIsPhoneKeyLongPress = false;
                    Log.e(TAG, "----Phone Key Handler Long Press No Need Handler Key Up----");
                    return;
                }
                handlerPhoneKey(context);
                return;
        }
    }

    private void handlerPhoneKey(Context context) {
    }

    private void reportConnectionToEC(Context context) {
        try {
            boolean a2dp = this.mCommandA2dp.isA2dpConnected();
            boolean hfp = this.mCommandHfp.isHfpConnected(this.mCommandBluetooth.cmdMainAddr);
            String str = TAG;
            Log.e(str, "----reportConnectionToEC() isA2dpConn=" + a2dp + " ; isHfpConn=" + hfp + "  " + this.mCommandBluetooth.cmdMainAddr);
            Intent i = new Intent();
            if (!a2dp) {
                if (!hfp) {
                    i.setAction("net.easyconn.bt.opened");
                    i.putExtra("name", this.mCommandBluetooth.getBtLocalName());
                    i.putExtra("mac", this.mCommandBluetooth.getBtLocalAddress());
                    context.sendBroadcast(i);
                }
            }
            i.setAction("net.easyconn.bt.connected");
            context.sendBroadcast(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyA2dpAudioFocus() {
    }
}
