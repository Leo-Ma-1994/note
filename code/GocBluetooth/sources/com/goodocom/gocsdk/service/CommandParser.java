package com.goodocom.gocsdk.service;

import android.content.Context;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.goodocom.bttek.bt.res.GocDef;
import com.goodocom.gocsdk.Commands;
import com.goodocom.gocsdk.IGocsdkCallback;
import com.goodocom.gocsdk.activity.TransparentActivity;
import com.goodocom.gocsdk.event.BackgroundCurrentNumberEvent;
import com.goodocom.gocsdk.event.BackgroundHfpStatusEvent;
import org.greenrobot.eventbus.EventBus;

public class CommandParser {
    private static final String TAG = CommandParser.class.getName();
    private RemoteCallbackList<IGocsdkCallback> callbacks;
    private int count = 0;
    private Context mContext;
    private byte[] serialBuffer = new byte[1024];

    public CommandParser(RemoteCallbackList<IGocsdkCallback> callbacks2, GocsdkService gocsdkService) {
        this.callbacks = callbacks2;
        this.mContext = gocsdkService;
    }

    private void handleWithoutCallback(String cmd) {
        Log.d(TAG, "callbacks.getRegisteredCallbackCount() == 0");
        if (cmd.startsWith(Commands.IND_INCOMING)) {
            Log.d(TAG, "IND_INCOMING fromBehind!");
            TransparentActivity.start(this.mContext, 5, cmd.substring(2));
        } else if (cmd.startsWith(Commands.IND_CALL_SUCCEED)) {
            Log.d(TAG, "IND_CALL_SUCCEED fromBehind!");
            TransparentActivity.start(this.mContext, 4, cmd.substring(2));
        } else if (cmd.startsWith(Commands.IND_TALKING)) {
            Log.d(TAG, "IND_CALL_SUCCEED fromBehind!");
            TransparentActivity.start(this.mContext, 6, cmd.substring(2));
        } else if (cmd.startsWith(Commands.IND_HFP_STATUS)) {
            Log.d(TAG, "IND_HFP_STATUS fromBehind!");
            EventBus.getDefault().postSticky(new BackgroundHfpStatusEvent(Integer.parseInt(cmd.substring(Commands.IND_HFP_STATUS.length()))));
        } else if (cmd.startsWith(Commands.IND_OUTGOING_TALKING_NUMBER)) {
            Log.d(TAG, "IND_CALL_SUCCEED fromBehind!");
            EventBus.getDefault().postSticky(new BackgroundCurrentNumberEvent(cmd.substring(Commands.IND_OUTGOING_TALKING_NUMBER.length())));
        }
    }

    private void onSerialCommand(String cmd) {
        Log.e("comm", "onSerialCommand cmd>>>>>>" + cmd);
        if (this.callbacks.getRegisteredCallbackCount() == 0) {
            handleWithoutCallback(cmd);
            return;
        }
        int i = this.callbacks.beginBroadcast();
        while (i > 0) {
            i--;
            IGocsdkCallback cbk = this.callbacks.getBroadcastItem(i);
            Log.e("casll", "");
            try {
                if (cmd.startsWith(Commands.IND_HFP_CONNECTED)) {
                    cbk.onHfpConnected();
                } else if (cmd.startsWith(Commands.IND_HFP_DISCONNECTED)) {
                    cbk.onHfpDisconnected();
                } else if (cmd.startsWith(Commands.IND_CALL_SUCCEED)) {
                    if (cmd.length() < 4) {
                        cbk.onCallSucceed("");
                    } else {
                        cbk.onCallSucceed(cmd.substring(2));
                    }
                } else if (cmd.startsWith(Commands.IND_INCOMING)) {
                    if (cmd.length() <= 2) {
                        cbk.onIncoming("");
                    } else {
                        cbk.onIncoming(cmd.substring(2));
                    }
                } else if (cmd.startsWith(Commands.IND_HANG_UP)) {
                    cbk.onHangUp();
                } else if (cmd.startsWith(Commands.IND_TALKING)) {
                    if (cmd.length() <= 2) {
                        cbk.onTalking("");
                    } else {
                        cbk.onTalking(cmd.substring(2));
                    }
                } else if (cmd.startsWith(Commands.IND_RING_START)) {
                    cbk.onRingStart();
                } else if (cmd.startsWith(Commands.IND_RING_STOP)) {
                    cbk.onRingStop();
                } else if (cmd.startsWith(Commands.IND_HF_LOCAL)) {
                    cbk.onHfpLocal();
                } else if (cmd.startsWith(Commands.IND_HF_REMOTE)) {
                    cbk.onHfpRemote();
                } else if (cmd.startsWith(Commands.IND_IN_PAIR_MODE)) {
                    cbk.onInPairMode();
                } else if (cmd.startsWith(Commands.IND_EXIT_PAIR_MODE)) {
                    cbk.onExitPairMode();
                } else if (cmd.startsWith(Commands.IND_INIT_SUCCEED)) {
                    cbk.onInitSucceed();
                } else if (cmd.startsWith(Commands.IND_MUSIC_PLAYING)) {
                    Log.d(TAG, "callback Commands playing" + cmd);
                    cbk.onMusicPlaying();
                } else if (cmd.startsWith(Commands.IND_MUSIC_STOPPED)) {
                    Log.d(TAG, "callback Commands stoped" + cmd);
                    cbk.onMusicStopped();
                } else if (!cmd.startsWith(Commands.IND_VOICE_CONNECTED)) {
                    if (!cmd.startsWith(Commands.IND_VOICE_DISCONNECTED)) {
                        if (cmd.startsWith(Commands.IND_AUTO_CONNECT_ACCEPT)) {
                            if (cmd.length() < 4) {
                                Log.e(TAG, cmd + "=====error command");
                            } else {
                                cbk.onAutoConnectAccept(cmd.substring(2, 4));
                            }
                        } else if (cmd.startsWith(Commands.IND_CURRENT_ADDR)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "==== error command");
                            } else {
                                cbk.onCurrentAddr(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_CURRENT_NAME)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "==== error command");
                            } else {
                                cbk.onCurrentName(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_AV_STATUS)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "=====error");
                            } else {
                                cbk.onAvStatus(Integer.parseInt(cmd.substring(2, 3)));
                            }
                        } else if (cmd.startsWith(Commands.IND_HFP_STATUS)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + " ==== error");
                            } else {
                                cbk.onHfpStatus(Integer.parseInt(cmd.substring(2, 3)));
                            }
                        } else if (cmd.startsWith(Commands.IND_VERSION_DATE)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                cbk.onVersionDate(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_CURRENT_DEVICE_NAME)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                cbk.onCurrentDeviceName(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_CURRENT_PIN_CODE)) {
                            if (cmd.length() < 3) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                cbk.onCurrentPinCode(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_A2DP_CONNECTED)) {
                            cbk.onA2dpConnected();
                        } else if (cmd.startsWith(Commands.IND_A2DP_DISCONNECTED)) {
                            cbk.onA2dpDisconnected();
                        } else if (cmd.startsWith(Commands.IND_CURRENT_AND_PAIR_LIST)) {
                            if (cmd.length() < 15) {
                                Log.e(TAG, cmd + "====error");
                            } else if (cmd.length() == 15) {
                                cbk.onCurrentAndPairList(Integer.parseInt(cmd.substring(2, 3)), "", cmd.substring(3, 15));
                            } else {
                                cbk.onCurrentAndPairList(Integer.parseInt(cmd.substring(2, 3)), cmd.substring(15), cmd.substring(3, 15));
                            }
                        } else if (cmd.startsWith(Commands.IND_PHONE_BOOK)) {
                            if (cmd.length() < 6) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                String name = null;
                                String number = null;
                                if (cmd.contains("[FF]")) {
                                    String[] split = cmd.split("\\[FF\\]");
                                    if (split.length == 2) {
                                        name = split[0].substring(2);
                                        number = split[1];
                                    }
                                } else {
                                    int nameLen = Integer.parseInt(cmd.substring(2, 4));
                                    int numLen = Integer.parseInt(cmd.substring(4, 6));
                                    byte[] bytes = cmd.getBytes();
                                    if (nameLen > 0) {
                                        byte[] buffer = new byte[nameLen];
                                        System.arraycopy(bytes, 6, buffer, 0, nameLen);
                                        name = new String(buffer);
                                    } else {
                                        name = "";
                                    }
                                    if (numLen <= 0) {
                                        number = "";
                                    } else if (nameLen + 6 + numLen == bytes.length) {
                                        byte[] buffer2 = new byte[numLen];
                                        System.arraycopy(bytes, nameLen + 6, buffer2, 0, numLen);
                                        number = new String(buffer2);
                                    } else {
                                        Log.e("goc", "PhoneBook bytes length is err!");
                                    }
                                }
                                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                                    cbk.onPhoneBook(name, number);
                                }
                            }
                        } else if (cmd.startsWith(Commands.IND_PHONE_BOOK_DONE)) {
                            cbk.onPhoneBookDone();
                        } else if (cmd.startsWith(Commands.IND_SIM_DONE)) {
                            cbk.onSimDone();
                        } else if (cmd.startsWith(Commands.IND_CALLLOG_DONE)) {
                            cbk.onCalllogDone();
                        } else if (cmd.startsWith(Commands.IND_CALLLOG)) {
                            if (cmd.length() < 4) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                String[] split2 = cmd.substring(3).split("\\[FF\\]");
                                cbk.onCalllog(Integer.parseInt(cmd.substring(2, 3)), split2[0], split2[1]);
                            }
                        } else if (cmd.startsWith(Commands.IND_DISCOVERY)) {
                            if (cmd.length() < 14) {
                                Log.e(TAG, cmd + "===error");
                            } else if (cmd.length() == 14) {
                                cbk.onDiscovery("", "", cmd.substring(2));
                            } else {
                                cbk.onDiscovery("", cmd.substring(14), cmd.substring(2, 14));
                            }
                        } else if (cmd.startsWith(Commands.IND_DISCOVERY_DONE)) {
                            cbk.onDiscoveryDone();
                        } else if (cmd.startsWith(Commands.IND_LOCAL_ADDRESS)) {
                            cmd.length();
                            cbk.onLocalAddress(cmd.substring(2));
                        } else if (cmd.startsWith(Commands.IND_OUTGOING_TALKING_NUMBER)) {
                            if (cmd.length() <= 2) {
                                cbk.onOutGoingOrTalkingNumber("");
                            } else {
                                cbk.onOutGoingOrTalkingNumber(cmd.substring(2));
                            }
                        } else if (cmd.startsWith(Commands.IND_MUSIC_INFO)) {
                            if (cmd.length() <= 2) {
                                Log.e(TAG, cmd + "===error");
                            } else {
                                String[] arr = cmd.substring(2).split("\\[FF\\]");
                                if (arr.length == 5) {
                                    cbk.onMusicInfo(arr[0], arr[1], "none", Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
                                } else if (arr.length == 6) {
                                    cbk.onMusicInfo(arr[0], arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Integer.parseInt(arr[5]));
                                } else {
                                    Log.e(TAG, cmd + "===error");
                                }
                            }
                        } else if (cmd.startsWith(Commands.IND_MUSIC_POS)) {
                            if (cmd.length() != 10) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                cbk.onMusicPos(Integer.parseInt(cmd.substring(2, 6), 16), Integer.parseInt(cmd.substring(6, 10), 16));
                            }
                        } else if (cmd.startsWith(Commands.IND_PROFILE_ENABLED)) {
                            if (cmd.length() < 12) {
                                Log.e(TAG, cmd + "====error");
                            } else {
                                boolean[] enabled = new boolean[10];
                                for (int ii = 0; ii < 10; ii++) {
                                    if (cmd.charAt(ii + 2) == '0') {
                                        enabled[ii] = false;
                                    } else {
                                        enabled[ii] = true;
                                    }
                                }
                                cbk.onProfileEnbled(enabled);
                            }
                        } else if (cmd.startsWith(Commands.IND_MESSAGE_LIST)) {
                            String text = cmd.substring(2);
                            if (text.length() == 0) {
                                Log.e("goc", "cmd error:param==0" + cmd);
                            } else {
                                String[] arr2 = text.split("\\[FF\\]", -1);
                                if (arr2.length != 6) {
                                    Log.e("goc", "cmd error:arr.length=" + arr2.length + ";" + cmd);
                                } else {
                                    cbk.onMessageInfo(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
                                }
                            }
                        } else if (cmd.startsWith(Commands.IND_MESSAGE_TEXT)) {
                            cbk.onMessageContent(cmd.substring(2));
                        } else if (!cmd.startsWith(Commands.IND_OK) && !cmd.startsWith(Commands.IND_ERROR)) {
                            if (cmd.startsWith(Commands.IND_MUSIC_LIST_TYPE)) {
                                if (cmd.length() < 19) {
                                    Log.e(TAG, cmd + "=====error IND_MUSIC_LIST_TYPE command");
                                } else {
                                    String type = cmd.substring(2, 3);
                                    String index = cmd.substring(3, 19);
                                    String name2 = cmd.substring(19);
                                    cbk.onMusicListState(type, index, name2);
                                    Log.e("fjasmin+ 音乐列表", type + " : " + index + " : " + name2);
                                }
                            } else if (!cmd.startsWith(Commands.INQUIRY_MUSIC_INTER)) {
                                if (cmd.startsWith(Commands.IND_MUSIC_TYPE_SUCCESS)) {
                                    Log.e("fjasmin: ", Commands.IND_MUSIC_TYPE_SUCCESS + "===success");
                                    cbk.onMusicListSucess();
                                } else if (cmd.startsWith(Commands.IND_MUSIC_TYPE_FAIL)) {
                                    Log.e("fjasmin: ", Commands.IND_MUSIC_TYPE_FAIL + "===false");
                                    cbk.onMusicListFail();
                                } else if (cmd.startsWith(Commands.IND_MUSIC_LIST_SUCESS)) {
                                    Log.e("fjasmin+ 音乐列表设置成功", Commands.IND_MUSIC_LIST_SUCESS + "===success");
                                    Log.e(TAG, cmd + Commands.IND_MUSIC_LIST_SUCESS + "===success");
                                    cbk.onMusicListSettingSuccess();
                                } else if (cmd.startsWith(Commands.IND_MUSIC_LIST_FAIL)) {
                                    Log.e("fjasmin+音乐列表设置失败", Commands.IND_MUSIC_LIST_FAIL + "===fail");
                                    Log.e(TAG, cmd + "===fail");
                                    cbk.onMusicListSettingFail();
                                } else if (cmd.startsWith(Commands.IND_MUSIC_PLAY_SUCESSS)) {
                                    Log.e("fjasmin+ 音乐列表设置成功", Commands.IND_MUSIC_PLAY_SUCESSS + "===success");
                                    Log.e(TAG, cmd + Commands.IND_MUSIC_PLAY_SUCESSS + "===success");
                                    cbk.onMusicPlaySuccess();
                                } else if (cmd.startsWith(Commands.IND_MUSIC_PLAY_FAIL)) {
                                    Log.e("fjasmin", Commands.IND_MUSIC_PLAY_FAIL + "===fail");
                                    Log.e(TAG, cmd + "===fail");
                                    cbk.onMusicListSettingFail();
                                } else if (cmd.startsWith(Commands.IND_MUSIC_COVER_SUCEESS)) {
                                    Log.e("fjasmin", Commands.IND_MUSIC_COVER_SUCEESS + "===success");
                                    Log.e(TAG, cmd + "===封面success");
                                    cbk.onMusicCoverSuccess(cmd.substring(2));
                                    Log.e("fjasmin 封面id", cmd.substring(2));
                                } else if (cmd.startsWith(Commands.IND_MUSIC_COVER_SUCEESS)) {
                                    Log.e("fjasmin", Commands.IND_MUSIC_COVER_FAIL + "===fail");
                                    Log.e(TAG, cmd + "===封面fail");
                                    cbk.onMusicCoverFail();
                                }
                            }
                        }
                    }
                }
            } catch (RemoteException e) {
            }
        }
        this.callbacks.finishBroadcast();
    }

    private void onByte(byte b) {
        if (10 != b) {
            if (this.count >= 1000) {
                this.count = 0;
            }
            if (13 == b) {
                int i = this.count;
                if (i > 0) {
                    byte[] buf = new byte[i];
                    System.arraycopy(this.serialBuffer, 0, buf, 0, i);
                    onSerialCommand(new String(buf));
                    this.count = 0;
                }
            } else if ((b & GocDef.AVRCP_PLAYING_STATUS_ID_ERROR) == 255) {
                byte[] bArr = this.serialBuffer;
                int i2 = this.count;
                this.count = i2 + 1;
                bArr[i2] = 91;
                int i3 = this.count;
                this.count = i3 + 1;
                bArr[i3] = 70;
                int i4 = this.count;
                this.count = i4 + 1;
                bArr[i4] = 70;
                int i5 = this.count;
                this.count = i5 + 1;
                bArr[i5] = 93;
            } else {
                byte[] bArr2 = this.serialBuffer;
                int i6 = this.count;
                this.count = i6 + 1;
                bArr2[i6] = b;
            }
        }
    }

    public void onBytes(byte[] data) {
        for (byte b : data) {
            onByte(b);
        }
    }
}
