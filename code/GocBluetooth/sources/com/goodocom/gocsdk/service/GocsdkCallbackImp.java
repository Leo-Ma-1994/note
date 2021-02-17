package com.goodocom.gocsdk.service;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.gocsdk.GocAppData;
import com.goodocom.gocsdk.IGocsdkCallback;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.domain.BlueToothConnectInfo;
import com.goodocom.gocsdk.domain.BlueToothInfo;
import com.goodocom.gocsdk.domain.BlueToothPairedInfo;
import com.goodocom.gocsdk.domain.CallLogInfo;
import com.goodocom.gocsdk.domain.MusicCoverInfo;
import com.goodocom.gocsdk.domain.MusicInfo;
import com.goodocom.gocsdk.domain.PhoneBookInfo;
import com.goodocom.gocsdk.event.A2dpStatusEvent;
import com.goodocom.gocsdk.event.CurrentNumberEvent;
import com.goodocom.gocsdk.event.MusicInfoEvent;
import com.goodocom.gocsdk.event.MusicPosEvent;
import com.goodocom.gocsdk.event.PlayStatusEvent;
import com.goodocom.gocsdk.fragment.BaseFragment;
import com.goodocom.gocsdk.fragment.FragmentCallog;
import com.goodocom.gocsdk.fragment.FragmentMusic;
import com.goodocom.gocsdk.fragment.FragmentPairedList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class GocsdkCallbackImp extends IGocsdkCallback.Stub {
    public static final String TAG = "callback";
    public static int a2dpStatus = 1;
    public static int hfpStatus = 1;
    public static String number = "";
    private GocAppData mGocAppData = GocAppData.getInstance();
    private GocCallBackListener mGocCallBackListener;

    public interface GocCallBackListener {
        void onAddressUpdate();
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHfpConnected() throws RemoteException {
        Log.e(TAG, "onHfpConnected： ");
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHfpDisconnected() throws RemoteException {
        Log.e(TAG, "onHfpDisconnected： ");
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCallSucceed(String number2) throws RemoteException {
        Log.e(TAG, "onCallSucceed： " + number2);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onIncoming(String number2) throws RemoteException {
        Log.e(TAG, "onIncoming： " + number2);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHangUp() throws RemoteException {
        Log.e(TAG, "onHangUp： ");
        Handler handler1 = MainActivity.getHandler();
        if (handler1 != null) {
            handler1.sendEmptyMessage(7);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onTalking(String str) throws RemoteException {
        Log.e(TAG, "onTalking： " + str);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onRingStart() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onRingStop() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHfpLocal() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHfpRemote() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onInPairMode() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onExitPairMode() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onInitSucceed() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicPlaying() throws RemoteException {
        Log.e(TAG, "onMusicPlaying： ");
        Log.d("app", "callback play status event true");
        EventBus.getDefault().postSticky(new PlayStatusEvent(true));
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicStopped() throws RemoteException {
        Log.e(TAG, "onMusicStopped： ");
        Log.d("app", "callback play status event false");
        EventBus.getDefault().postSticky(new PlayStatusEvent(false));
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onAutoConnectAccept(String autoStatus) throws RemoteException {
        Log.e(TAG, "onAutoConnectAccept： " + autoStatus);
        Handler handler = BaseFragment.hanlder;
        if (handler != null) {
            Message msg = Message.obtain();
            msg.what = 34;
            msg.obj = autoStatus;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCurrentAddr(String addr) throws RemoteException {
        Log.e(TAG, "onCurrentAddr: " + addr + "   mGocCallBackListener:" + this.mGocCallBackListener);
        Handler handler2 = FragmentPairedList.getHandler();
        if (handler2 != null) {
            BlueToothPairedInfo blueToothPairedInfo = new BlueToothPairedInfo();
            blueToothPairedInfo.address = addr;
            blueToothPairedInfo.isConnected = true;
            if (!this.mGocAppData.mParedList.contains(blueToothPairedInfo)) {
                this.mGocAppData.mParedList.add(blueToothPairedInfo);
            }
            BlueToothConnectInfo info = new BlueToothConnectInfo();
            info.name = addr;
            info.address = addr;
            int size = this.mGocAppData.mConnectList.size();
            if (size == 0) {
                this.mGocAppData.mConnectList.add(info);
            } else if (size == 1) {
                if (!this.mGocAppData.mConnectList.get(0).address.equals(addr)) {
                    this.mGocAppData.mConnectList.add(info);
                }
            } else if (size == 2 && !this.mGocAppData.mConnectList.get(0).address.equals(addr) && !this.mGocAppData.mConnectList.get(1).address.equals(addr)) {
                this.mGocAppData.mConnectList.clear();
                this.mGocAppData.mConnectList.add(info);
            }
            Message msg = Message.obtain();
            msg.what = 14;
            msg.obj = addr;
            handler2.sendMessage(msg);
            GocCallBackListener gocCallBackListener = this.mGocCallBackListener;
            if (gocCallBackListener != null) {
                gocCallBackListener.onAddressUpdate();
            }
        }
    }

    private void setArrayListToSingle() {
        List<BlueToothConnectInfo> list = removeDuplicate(this.mGocAppData.mConnectList);
        this.mGocAppData.mConnectList.clear();
        Log.e(TAG, "list ::: " + list);
        this.mGocAppData.mConnectList.addAll(list);
    }

    private List<BlueToothConnectInfo> removeDuplicate(List<BlueToothConnectInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j && list.get(i).address.equals(list.get(j).address)) {
                    list.remove(list.get(j));
                }
            }
        }
        return list;
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCurrentAndPairList(int index, String name, String addr) throws RemoteException {
        Log.e(TAG, "onCurrentAndPairList： " + index + " name  " + name + "   addr " + addr);
        Handler handler = FragmentPairedList.getHandler();
        if (handler != null) {
            BlueToothPairedInfo info = new BlueToothPairedInfo();
            info.index = index;
            info.name = name;
            info.address = addr;
            Message msg = Message.obtain();
            msg.obj = info;
            msg.what = 13;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCurrentName(String name) throws RemoteException {
        Log.e(TAG, "onCurrentName： " + name);
        Handler handler = MainActivity.getHandler();
        if (handler != null) {
            Message msg = Message.obtain();
            msg.what = 29;
            msg.obj = name;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHfpStatus(int status) throws RemoteException {
        Log.e(TAG, "onHfpStatus:" + status);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onAvStatus(int status) throws RemoteException {
        Log.e(TAG, "onAvStatus:" + status);
        a2dpStatus = status;
        EventBus.getDefault().post(new A2dpStatusEvent(status));
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onVersionDate(String version) throws RemoteException {
    }

    /* JADX INFO: Multiple debug info for r1v2 android.os.Handler: [D('msg' android.os.Message), D('handler1' android.os.Handler)] */
    /* JADX INFO: Multiple debug info for r3v0 android.os.Handler: [D('msg' android.os.Message), D('handler2' android.os.Handler)] */
    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCurrentDeviceName(String name) throws RemoteException {
        Log.e(TAG, "onCurrentDeviceName： " + name);
        Handler handler = MainActivity.getHandler();
        if (handler != null) {
            Message msg = Message.obtain();
            msg.obj = name;
            msg.what = 11;
            handler.sendMessage(msg);
        }
        Handler handler1 = BaseFragment.hanlder;
        if (handler1 != null) {
            Message msg2 = Message.obtain();
            msg2.what = 27;
            msg2.obj = name;
            handler1.sendMessage(msg2);
        }
        Handler handler2 = BaseFragment.hanlder;
        if (handler2 != null) {
            Message msg3 = Message.obtain();
            msg3.what = 27;
            msg3.obj = name;
            handler2.sendMessage(msg3);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCurrentPinCode(String code) throws RemoteException {
        Log.e(TAG, "onCurrentPinCode： " + code);
        Handler handler = MainActivity.getHandler();
        if (handler != null) {
            Message msg = Message.obtain();
            msg.obj = code;
            msg.what = 12;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onA2dpConnected() throws RemoteException {
        Log.e(TAG, "onA2dpConnected： ");
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onA2dpDisconnected() throws RemoteException {
        Log.e(TAG, "onA2dpDisconnected： ");
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onPhoneBook(String name, String number2) throws RemoteException {
        Log.e(TAG, "onPhoneBook： " + name + "   number: " + number2);
        Handler handler = BaseFragment.hanlder;
        if (handler != null) {
            Message msg = Message.obtain();
            msg.what = 19;
            PhoneBookInfo phonebook = new PhoneBookInfo();
            phonebook.name = name;
            phonebook.num = number2;
            msg.obj = phonebook;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onPhoneBookDone() throws RemoteException {
        Log.e(TAG, "onPhoneBookDone： ");
        Handler mainActivityHandler = MainActivity.getHandler();
        if (mainActivityHandler != null) {
            mainActivityHandler.sendEmptyMessage(18);
            Handler handler = BaseFragment.hanlder;
            if (handler != null) {
                Message msg = Message.obtain();
                msg.what = 20;
                handler.sendMessage(msg);
            }
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onSimBook(String name, String number2) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onSimDone() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCalllog(int type, String name, String number2) throws RemoteException {
        Log.e(TAG, "onCalllog： ");
        Handler handler = FragmentCallog.getHandler();
        if (handler != null) {
            CallLogInfo info = new CallLogInfo();
            info.number = number2;
            info.type = type;
            info.name = name;
            Message msg = Message.obtain();
            msg.obj = info;
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onCalllogDone() throws RemoteException {
        Log.e(TAG, "onCalllogDone： ");
        MainActivity.getHandler().sendEmptyMessage(28);
        Handler handler = FragmentCallog.getHandler();
        if (handler != null) {
            Message msg = Message.obtain();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onDiscovery(String type, String name, String addr) throws RemoteException {
        Log.e(TAG, "onDiscovery： " + type + "    name: " + name + "   addr: " + addr);
        Handler handler = BaseFragment.hanlder;
        Message msg = Message.obtain();
        msg.what = 25;
        BlueToothInfo info = new BlueToothInfo();
        info.name = name;
        info.address = addr;
        msg.obj = info;
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onDiscoveryDone() throws RemoteException {
        Log.e(TAG, "onDiscoveryDone： ");
        Handler handler = BaseFragment.hanlder;
        if (handler != null) {
            handler.sendEmptyMessage(26);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onLocalAddress(String addr) throws RemoteException {
        Log.e(TAG, "onLocalAddress： " + addr);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onOutGoingOrTalkingNumber(String number2) throws RemoteException {
        Log.e(TAG, "onOutGoingOrTalkingNumber： " + number2);
        EventBus.getDefault().postSticky(new CurrentNumberEvent(number2));
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onConnecting() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onSppData(int index, String data) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onSppConnect(int index) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onSppDisconnect(int index) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onSppStatus(int status) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onOppReceivedFile(String path) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onOppPushSuccess() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onOppPushFailed() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHidConnected() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHidDisconnected() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onHidStatus(int status) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicInfo(String name, String artist, String album, int duration, int pos, int total) throws RemoteException {
        Log.e(TAG, "onMusicInfo： " + name);
        EventBus.getDefault().post(new MusicInfoEvent(name, artist, album, duration, pos, total));
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicPos(int current, int total) throws RemoteException {
        EventBus.getDefault().post(new MusicPosEvent(current, total));
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onPanConnect() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onPanDisconnect() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onPanStatus(int status) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onVoiceConnected() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onVoiceDisconnected() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onProfileEnbled(boolean[] enabled) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMessageInfo(String content_order, String read_status, String time, String name, String num, String title) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMessageContent(String content) throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onPairedState(int state) throws RemoteException {
        Log.d("app", "" + state);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicListState(String type, String index, String name) throws RemoteException {
        Log.d("music", "type:" + type + "   index: " + index + "   name: " + name);
        Handler handler = FragmentMusic.getHandler();
        Message msg = Message.obtain();
        msg.what = 5;
        MusicInfo info = new MusicInfo();
        info.music_name = name;
        info.music_singer = type;
        info.music_index_type = index;
        msg.obj = info;
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicIntoList(String index) throws RemoteException {
        Log.d("music", "onMusicIntoList: " + index);
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicListSucess() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicListFail() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicListSettingSuccess() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicListSettingFail() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicPlaySuccess() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicPlayFail() throws RemoteException {
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicCoverSuccess(String index) throws RemoteException {
        Log.d("music", "" + index);
        Handler handler = FragmentMusic.getHandler();
        Message msg = Message.obtain();
        msg.what = 6;
        MusicCoverInfo coverInfo = new MusicCoverInfo();
        coverInfo.music_cover_name = index;
        msg.obj = coverInfo;
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    @Override // com.goodocom.gocsdk.IGocsdkCallback
    public void onMusicCoverFail() throws RemoteException {
    }

    public void setGocCallBackListener(GocCallBackListener gocCallBackListener) {
        this.mGocCallBackListener = gocCallBackListener;
    }
}
