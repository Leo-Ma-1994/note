package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackA2dp;
import com.goodocom.bttek.bt.aidl.UiCallbackAvrcp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class A2dpPageActivity extends Activity {
    private static boolean D = true;
    private static String EQUALIZER_SET_SELECTION = "EQUALIZER_SET_SELECTION";
    private static final int HANDLER_EVENT_EQUALIZER_SET_SELECTION = 8;
    private static final int HANDLER_EVENT_NOTIFY_ARRAYADAPTER = 7;
    private static final int HANDLER_EVENT_REPEAT_MODE_SET_SELECTION = 9;
    private static final int HANDLER_EVENT_SCAN_SET_SELECTION = 11;
    private static final int HANDLER_EVENT_SET_PROGRESSBAR_GONE = 5;
    private static final int HANDLER_EVENT_SET_PROGRESSBAR_VISIBLE = 6;
    private static final int HANDLER_EVENT_SHOW_TOAST = 12;
    private static final int HANDLER_EVENT_SHUFFLE_SET_SELECTION = 10;
    private static final int HANDLER_EVENT_UPDATE_PLAYING_PROGRESS = 4;
    private static final int HANDLER_EVENT_UPDATE_PLAY_STATUS = 13;
    private static final int HANDLER_EVENT_UPDATE_SONG_STATUS = 1;
    private static final int HANDLER_EVENT_UPDATE_STATE_A2DP = 2;
    private static final int HANDLER_EVENT_UPDATE_STATE_AVRCP = 3;
    private static String INFO_ALBUM = "INFO_ALBUM";
    private static String INFO_ARTIST = "INFO_ARTIST";
    private static String INFO_TITLE = "INFO_TITLE";
    private static String PLAYING_PROGRESS = "PLAYING_PROGRESS";
    private static String PLAY_STATUS = "PLAY_STATUS";
    private static String REPEAT_MODE_SET_SELECTION = "REPEAT_MODE_SET_SELECTION";
    private static String SCAN_SET_SELECTION = "SCAN_SET_SELECTION";
    private static String SHUFFLE_SET_SELECTION = "SHUFFLE_SET_SELECTION";
    private static String STATE_A2DP = "STATE_A2DP";
    private static String STATE_AVRCP = "STATE_AVRCP";
    private static String TAG = "NfDemo_A2dpPage";
    private static String TOAST_MESSAGE = "TOAST_MESSAGE";
    private String a2dp_address;
    private Button button_a2dp_connect;
    private Button button_a2dp_disconnect;
    private Button button_avrcp_capability;
    private Button button_avrcp_connect;
    private Button button_avrcp_disconnect;
    private Button button_avrcp_elements;
    private Button button_avrcp_register_1;
    private Button button_avrcp_register_2;
    private Button button_avrcp_register_5;
    private Button button_avrcp_register_8;
    private Button button_back;
    private Button button_backward;
    private Button button_browsing;
    private Button button_fastforward;
    private Button button_forward;
    private Button button_get_re_value;
    private Button button_get_sh_value;
    private Button button_get_status;
    private Button button_list_attr;
    private Button button_list_re_value;
    private Button button_list_sh_value;
    private Button button_next_group;
    private Button button_pause;
    private Button button_play;
    private Button button_prev_group;
    private Button button_rewind;
    private Button button_set_re_all;
    private Button button_set_re_off;
    private Button button_set_sh_all;
    private Button button_set_sh_off;
    private Button button_stop;
    private Button button_volume_down;
    private Button button_volume_up;
    private boolean checkSetSettingValues = false;
    private boolean doCommand = true;
    private boolean firstChose = true;
    private Byte firstEqualizerPosition;
    private Byte firstRepeatModePosition;
    private Byte firstScanPosition;
    private Byte firstShufflePosition;
    private Toast infoToast;
    private boolean isSupportSongPosition = false;
    private int mA2dpPlayingProgress = 0;
    private int mAttributeIdsCounter = 0;
    private UiCallbackA2dp mCallbackA2dp = new UiCallbackA2dp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass40 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackA2dp
        public void onA2dpServiceReady() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onA2dpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackA2dp
        public void onA2dpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String state;
            Log.i(A2dpPageActivity.TAG, "onA2dpStateChanged " + address + " state: " + prevState + "->" + newState);
            if (newState == 140) {
                state = "Connected";
            } else if (newState == 120) {
                state = "Connecting";
            } else if (newState == 150) {
                state = "Streaming";
            } else if (newState == 110) {
                state = "Ready";
            } else {
                state = BuildConfig.FLAVOR + newState;
            }
            A2dpPageActivity.this.sendHandlerMessage(2, new String[]{A2dpPageActivity.STATE_A2DP}, new String[]{state});
            A2dpPageActivity.this.state_a2dp = newState;
        }
    };
    private UiCallbackAvrcp mCallbackAvrcp = new UiCallbackAvrcp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass41 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpServiceReady() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onAvrcpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
            String state;
            if (A2dpPageActivity.D) {
                Log.i(A2dpPageActivity.TAG, "onAvrcpStateChanged " + address + " state: " + prevState + "->" + newState);
            }
            if (prevState >= 140 && newState == 110) {
                state = "Ready";
            } else if (newState == 120) {
                state = "Connecting";
            } else if (newState >= 140) {
                state = "Connected";
            } else {
                state = BuildConfig.FLAVOR + newState;
            }
            A2dpPageActivity.this.sendHandlerMessage(3, new String[]{A2dpPageActivity.STATE_AVRCP}, new String[]{state});
            A2dpPageActivity.this.state_avrcp = newState;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp13CapabilitiesSupportEvent()");
            for (byte b : eventIds) {
                switch (b) {
                    case 1:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_PLAYBACK_STATUS_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 1, 3);
                        break;
                    case 2:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_TRACK_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 2, 3);
                        break;
                    case 3:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_TRACK_REACHED_END]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 3, 3);
                        break;
                    case 4:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_TRACK_REACHED_START]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 4, 3);
                        break;
                    case 5:
                        A2dpPageActivity.this.isSupportSongPosition = true;
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_PLAYBACK_POS_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 5, 1);
                        break;
                    case 6:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_BATT_STATUS_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 6, 3);
                        break;
                    case 7:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_SYSTEM_STATUS_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 7, 3);
                        break;
                    case 8:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_PLAYER_APPLICATION_SETTING_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 8, 3);
                        break;
                    case 9:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_NOW_PLAYING_CONTENT_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 9, 3);
                        break;
                    case 10:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_AVAILABLE_PLAYERS_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 10, 3);
                        break;
                    case 11:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_ADDRESSED_PLAYER_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 11, 3);
                        break;
                    case 12:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_UIDS_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 12, 3);
                        break;
                    case 13:
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_EVENT_ID_VOLUME_CHANGED]");
                        A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 13, 3);
                        break;
                }
            }
            A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayStatus();
            if (A2dpPageActivity.this.isSupportSongPosition) {
                A2dpPageActivity.this.sendHandlerMessage(6, (String[]) null, (String[]) null);
                Log.e(A2dpPageActivity.TAG, "--- sendHandlerMessage : HANDLER_EVENT_SET_PROGRESSBAR_VISIBLE ---");
                return;
            }
            A2dpPageActivity.this.sendHandlerMessage(5, (String[]) null, (String[]) null);
            Log.e(A2dpPageActivity.TAG, "--- sendHandlerMessage : HANDLER_EVENT_SET_PROGRESSBAR_GONE ---");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp13PlayerSettingAttributesList()");
            for (byte b : attributeIds) {
                if (b == 1) {
                    Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_ATTRIBUTE_ID_EQUALIZER]");
                } else if (b == 2) {
                    Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_ATTRIBUTE_ID_REPEAT_MODE]");
                } else if (b == 3) {
                    Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_ATTRIBUTE_ID_SHUFFLE]");
                } else if (b == 4) {
                    Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_ATTRIBUTE_ID_SCAN]");
                }
            }
            A2dpPageActivity.this.myAttributeIds = attributeIds;
            for (int i = 0; i < A2dpPageActivity.this.myAttributeIds.length; i++) {
                Log.e(A2dpPageActivity.TAG, "myAttributeIds[" + i + "] = " + ((int) A2dpPageActivity.this.myAttributeIds[i]));
            }
            A2dpPageActivity.access$808(A2dpPageActivity.this);
            A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingValuesList(A2dpPageActivity.this.myAttributeIds[A2dpPageActivity.this.mAttributeIdsCounter - 1]);
            if (A2dpPageActivity.this.firstChose) {
                A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingCurrentValues();
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp13PlayerSettingValuesList()");
            if (attributeId == 1) {
                for (byte b : valueIds) {
                    if (b == 1) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_EQUALIZER_OFF]");
                    } else if (b == 2) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_EQUALIZER_ON]");
                    }
                }
                A2dpPageActivity.this.myValueIds[0] = valueIds;
                for (int i = 0; i < A2dpPageActivity.this.myValueIds[0].length; i++) {
                    Log.e(A2dpPageActivity.TAG, "*** myValueIds[" + Integer.toString(0) + "] = " + ((int) A2dpPageActivity.this.myValueIds[0][i]) + " ***");
                    Log.d(A2dpPageActivity.TAG, "ID_EQUALIZER");
                }
                A2dpPageActivity.this.spinner_equalizer.setPrompt("EQUALIZER");
                for (int i2 = 0; i2 < A2dpPageActivity.this.myValueIds[0].length; i2++) {
                    byte b2 = A2dpPageActivity.this.myValueIds[0][i2];
                    if (b2 == 1) {
                        A2dpPageActivity.this.myEqualizerArrayList.add("EQUALIZER_OFF");
                    } else if (b2 == 2) {
                        A2dpPageActivity.this.myEqualizerArrayList.add("EQUALIZER_ON");
                    }
                }
                if (A2dpPageActivity.this.mAttributeIdsCounter == A2dpPageActivity.this.myAttributeIds.length) {
                    A2dpPageActivity.this.sendHandlerMessage(7, new String[0], new String[0]);
                } else {
                    A2dpPageActivity.access$808(A2dpPageActivity.this);
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingValuesList(A2dpPageActivity.this.myAttributeIds[A2dpPageActivity.this.mAttributeIdsCounter - 1]);
                }
            }
            if (attributeId == 2) {
                for (byte b3 : valueIds) {
                    if (b3 == 1) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_REPEAT_OFF]");
                    } else if (b3 == 2) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_REPEAT_SINGLE]");
                    } else if (b3 == 3) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_REPEAT_ALL]");
                    } else if (b3 == 4) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_REPEAT_GROUP]");
                    }
                }
                A2dpPageActivity.this.myValueIds[1] = valueIds;
                for (int i3 = 0; i3 < A2dpPageActivity.this.myValueIds[1].length; i3++) {
                    Log.e(A2dpPageActivity.TAG, "*** myValueIds[" + Integer.toString(1) + "] = " + ((int) A2dpPageActivity.this.myValueIds[1][i3]) + " ***");
                    Log.d(A2dpPageActivity.TAG, "REPEAT_MODE");
                }
                A2dpPageActivity.this.spinner_repeat_mode.setPrompt("REPEAT MODE");
                for (int i4 = 0; i4 < A2dpPageActivity.this.myValueIds[1].length; i4++) {
                    byte b4 = A2dpPageActivity.this.myValueIds[1][i4];
                    if (b4 == 1) {
                        A2dpPageActivity.this.myRepeatModeArrayList.add("REPEAT_OFF");
                    } else if (b4 == 2) {
                        A2dpPageActivity.this.myRepeatModeArrayList.add("REPEAT_SINGLE");
                    } else if (b4 == 3) {
                        A2dpPageActivity.this.myRepeatModeArrayList.add("REPEAT_ALL");
                    } else if (b4 == 4) {
                        A2dpPageActivity.this.myRepeatModeArrayList.add("REPEAT_GROUP");
                    }
                }
                if (A2dpPageActivity.this.mAttributeIdsCounter == A2dpPageActivity.this.myAttributeIds.length) {
                    A2dpPageActivity.this.sendHandlerMessage(7, new String[0], new String[0]);
                } else {
                    A2dpPageActivity.access$808(A2dpPageActivity.this);
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingValuesList(A2dpPageActivity.this.myAttributeIds[A2dpPageActivity.this.mAttributeIdsCounter - 1]);
                }
            }
            if (attributeId == 3) {
                for (byte b5 : valueIds) {
                    if (b5 == 1) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SHUFFLE_OFF]");
                    } else if (b5 == 2) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SHUFFLE_ALL]");
                    } else if (b5 == 3) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SHUFFLE_GROUP]");
                    }
                }
                A2dpPageActivity.this.myValueIds[2] = valueIds;
                for (int i5 = 0; i5 < A2dpPageActivity.this.myValueIds[2].length; i5++) {
                    Log.e(A2dpPageActivity.TAG, "*** myValueIds[" + Integer.toString(2) + "] = " + ((int) A2dpPageActivity.this.myValueIds[2][i5]) + " ***");
                    Log.d(A2dpPageActivity.TAG, "SHUFFLE");
                }
                A2dpPageActivity.this.spinner_shuffle.setPrompt("SHUFFLE MODE");
                for (int i6 = 0; i6 < A2dpPageActivity.this.myValueIds[2].length; i6++) {
                    byte b6 = A2dpPageActivity.this.myValueIds[2][i6];
                    if (b6 == 1) {
                        A2dpPageActivity.this.myShuffleArrayList.add("SHUFFLE_OFF");
                    } else if (b6 == 2) {
                        A2dpPageActivity.this.myShuffleArrayList.add("SHUFFLE_ALL");
                    } else if (b6 == 3) {
                        A2dpPageActivity.this.myShuffleArrayList.add("SHUFFLE_GROUP");
                    }
                }
                if (A2dpPageActivity.this.mAttributeIdsCounter == A2dpPageActivity.this.myAttributeIds.length) {
                    A2dpPageActivity.this.sendHandlerMessage(7, new String[0], new String[0]);
                } else {
                    A2dpPageActivity.access$808(A2dpPageActivity.this);
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingValuesList(A2dpPageActivity.this.myAttributeIds[A2dpPageActivity.this.mAttributeIdsCounter - 1]);
                }
            }
            if (attributeId == 4) {
                for (byte b7 : valueIds) {
                    if (b7 == 1) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SCAN_OFF]");
                    } else if (b7 == 2) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SCAN_ALL]");
                    } else if (b7 == 3) {
                        Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SCAN_GROUP]");
                    }
                }
                A2dpPageActivity.this.myValueIds[3] = valueIds;
                for (int i7 = 0; i7 < A2dpPageActivity.this.myValueIds[3].length; i7++) {
                    Log.e(A2dpPageActivity.TAG, "*** myValueIds[" + Integer.toString(3) + "] = " + ((int) A2dpPageActivity.this.myValueIds[3][i7]) + " ***");
                    Log.d(A2dpPageActivity.TAG, "SCAN");
                }
                A2dpPageActivity.this.spinner_scan.setPrompt("SCAN MODE");
                for (int i8 = 0; i8 < A2dpPageActivity.this.myValueIds[3].length; i8++) {
                    byte b8 = A2dpPageActivity.this.myValueIds[3][i8];
                    if (b8 == 1) {
                        A2dpPageActivity.this.myScanArrayList.add("SCAN_OFF");
                    } else if (b8 == 2) {
                        A2dpPageActivity.this.myScanArrayList.add("SCAN_ALL");
                    } else if (b8 == 3) {
                        A2dpPageActivity.this.myScanArrayList.add("SCAN_GROUP");
                    }
                }
                if (A2dpPageActivity.this.mAttributeIdsCounter == A2dpPageActivity.this.myAttributeIds.length) {
                    A2dpPageActivity.this.sendHandlerMessage(7, new String[0], new String[0]);
                }
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpPlayModeChanged(String address, int mode) {
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp13PlayerSettingCurrentValues()");
            for (int i = 0; i < attributeIds.length; i++) {
                try {
                    if (attributeIds[i] == 1) {
                        byte b = valueIds[i];
                        if (b == 1) {
                            Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_EQUALIZER_OFF]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstEqualizerPosition = (byte) 1;
                            } else if (A2dpPageActivity.this.spinner_equalizer.getSelectedItemPosition() != 0) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(8, new String[]{A2dpPageActivity.EQUALIZER_SET_SELECTION}, new String[]{"0"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[0] == 1 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[0] = 1;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(8, new String[]{A2dpPageActivity.EQUALIZER_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[0])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b == 2) {
                            Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_EQUALIZER_ON]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstEqualizerPosition = (byte) 2;
                            } else if (A2dpPageActivity.this.spinner_equalizer.getSelectedItemPosition() != 1) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(8, new String[]{A2dpPageActivity.EQUALIZER_SET_SELECTION}, new String[]{"1"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[0] == 2 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[0] = 2;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(8, new String[]{A2dpPageActivity.EQUALIZER_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[0])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        }
                    }
                    if (attributeIds[i] == 2) {
                        byte b2 = valueIds[i];
                        if (b2 == 1) {
                            Log.i(A2dpPageActivity.TAG, "[REPEAT OFF]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstRepeatModePosition = (byte) 1;
                            } else if (A2dpPageActivity.this.spinner_repeat_mode.getSelectedItemPosition() != 0) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{"0"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[1] == 1 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[1] = 1;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[1])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b2 == 2) {
                            Log.i(A2dpPageActivity.TAG, "[REPEAT SINGLE]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstRepeatModePosition = (byte) 2;
                            } else if (A2dpPageActivity.this.spinner_repeat_mode.getSelectedItemPosition() != 1) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{"1"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[1] == 2 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[1] = 2;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[1])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b2 == 3) {
                            Log.i(A2dpPageActivity.TAG, "[REPEAT ALL]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstRepeatModePosition = (byte) 3;
                            } else if (A2dpPageActivity.this.spinner_repeat_mode.getSelectedItemPosition() != 2) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{"2"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[1] == 3 || !A2dpPageActivity.this.checkSetSettingValues) {
                                Log.e(A2dpPageActivity.TAG, "spinner_repeat_mode.setSelection(2)");
                                A2dpPageActivity.this.spinner_prev_mode[1] = 3;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[1])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b2 == 4) {
                            Log.i(A2dpPageActivity.TAG, "[REPEAT GROUP]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstRepeatModePosition = (byte) 4;
                            } else if (A2dpPageActivity.this.spinner_repeat_mode.getSelectedItemPosition() != 3) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{"3"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[1] == 4 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[1] = 4;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(9, new String[]{A2dpPageActivity.REPEAT_MODE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[1])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        }
                    }
                    if (attributeIds[i] == 3) {
                        byte b3 = valueIds[i];
                        if (b3 == 1) {
                            Log.i(A2dpPageActivity.TAG, "[SHUFFLE OFF]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstShufflePosition = (byte) 1;
                            } else if (A2dpPageActivity.this.spinner_shuffle.getSelectedItemPosition() != 0) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(10, new String[]{A2dpPageActivity.SHUFFLE_SET_SELECTION}, new String[]{"0"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[2] == 1 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[2] = 1;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(10, new String[]{A2dpPageActivity.SHUFFLE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[2])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b3 == 2) {
                            Log.i(A2dpPageActivity.TAG, "[SHUFFLE ALL]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstShufflePosition = (byte) 2;
                            } else if (A2dpPageActivity.this.spinner_shuffle.getSelectedItemPosition() != 1) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(10, new String[]{A2dpPageActivity.SHUFFLE_SET_SELECTION}, new String[]{"1"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[2] == 2 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[2] = 2;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(10, new String[]{A2dpPageActivity.SHUFFLE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[2])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b3 == 3) {
                            Log.i(A2dpPageActivity.TAG, "[SHUFFLE GROUP]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstShufflePosition = (byte) 3;
                            } else if (A2dpPageActivity.this.spinner_shuffle.getSelectedItemPosition() != 2) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(10, new String[]{A2dpPageActivity.SHUFFLE_SET_SELECTION}, new String[]{"2"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[2] == 3 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[2] = 3;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(10, new String[]{A2dpPageActivity.SHUFFLE_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[2])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        }
                    }
                    if (attributeIds[i] == 4) {
                        byte b4 = valueIds[i];
                        if (b4 == 1) {
                            Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SCAN_OFF]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstScanPosition = (byte) 1;
                            } else if (A2dpPageActivity.this.spinner_scan.getSelectedItemPosition() != 0) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(11, new String[]{A2dpPageActivity.SCAN_SET_SELECTION}, new String[]{"0"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[3] == 1 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[3] = 1;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(11, new String[]{A2dpPageActivity.SCAN_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[3])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b4 == 2) {
                            Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SCAN_ALL]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstScanPosition = (byte) 2;
                            } else if (A2dpPageActivity.this.spinner_scan.getSelectedItemPosition() != 1) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(11, new String[]{A2dpPageActivity.SCAN_SET_SELECTION}, new String[]{"1"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[3] == 2 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[3] = 2;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(11, new String[]{A2dpPageActivity.SCAN_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[3])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        } else if (b4 != 3) {
                            continue;
                        } else {
                            Log.i(A2dpPageActivity.TAG, "[AVRCP_SETTING_VALUE_ID_SCAN_GROUP]");
                            if (A2dpPageActivity.this.firstChose) {
                                A2dpPageActivity.this.firstScanPosition = (byte) 3;
                            } else if (A2dpPageActivity.this.spinner_scan.getSelectedItemPosition() != 2) {
                                A2dpPageActivity.this.doCommand = false;
                                A2dpPageActivity.this.sendHandlerMessage(11, new String[]{A2dpPageActivity.SCAN_SET_SELECTION}, new String[]{"2"});
                            }
                            if (A2dpPageActivity.this.spinner_new_mode[3] == 3 || !A2dpPageActivity.this.checkSetSettingValues) {
                                A2dpPageActivity.this.spinner_prev_mode[3] = 3;
                            } else {
                                A2dpPageActivity.this.sendHandlerMessage(11, new String[]{A2dpPageActivity.SCAN_SET_SELECTION}, new String[]{String.valueOf((int) A2dpPageActivity.this.spinner_prev_mode[3])});
                                A2dpPageActivity.this.checkSetSettingValues = false;
                                return;
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp13SetPlayerSettingValueSuccess()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            if (A2dpPageActivity.D) {
                Log.i(A2dpPageActivity.TAG, "retAvrcp13ElementAttributesPlaying()");
            }
            String title = BuildConfig.FLAVOR;
            String artist = BuildConfig.FLAVOR;
            String album = BuildConfig.FLAVOR;
            if (metadataAtrributeIds == null || texts == null) {
                Log.e(A2dpPageActivity.TAG, "elements is empty!");
            } else {
                for (int i = 0; i < metadataAtrributeIds.length; i++) {
                    if (metadataAtrributeIds[i] == 1) {
                        title = texts[i];
                    } else if (metadataAtrributeIds[i] == 3) {
                        album = texts[i];
                    } else if (metadataAtrributeIds[i] == 2) {
                        artist = texts[i];
                    } else if (metadataAtrributeIds[i] == 7 && A2dpPageActivity.D) {
                        String str = A2dpPageActivity.TAG;
                        Log.i(str, "AVRCP_META_ATTRIBUTE_ID_SONG_LENGTH: " + texts[i]);
                    }
                }
            }
            A2dpPageActivity.this.updateSongStatus(artist, album, title);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "retAvrcp13PlayStatus() songLen: " + songLen + " songPos: " + songPos + " statusId: " + ((int) statusId) + " isSupportSongPosition:" + A2dpPageActivity.this.isSupportSongPosition);
            if (A2dpPageActivity.this.isSupportSongPosition && songPos <= songLen) {
                A2dpPageActivity.this.songLength = songLen;
                A2dpPageActivity.this.songPosition = songPos;
                if (A2dpPageActivity.this.songLength != 0) {
                    A2dpPageActivity a2dpPageActivity = A2dpPageActivity.this;
                    a2dpPageActivity.mA2dpPlayingProgress = (int) ((a2dpPageActivity.songPosition * 100) / A2dpPageActivity.this.songLength);
                } else {
                    A2dpPageActivity.this.mA2dpPlayingProgress = 0;
                }
                A2dpPageActivity.this.sendHandlerMessage(13, A2dpPageActivity.PLAY_STATUS, statusId);
                A2dpPageActivity.this.sendHandlerMessage(4, new String[]{A2dpPageActivity.PLAYING_PROGRESS}, new String[]{Integer.toString(A2dpPageActivity.this.mA2dpPlayingProgress)});
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherSuccess(byte eventId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13RegisterEventWatcherSuccess() eventId: " + ((int) eventId));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherFail(byte eventId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13RegisterEventWatcherFail() eventId: " + ((int) eventId));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackStatusChanged(byte statusId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13EventPlaybackStatusChanged() statusId: " + ((int) statusId));
            A2dpPageActivity.this.sendHandlerMessage(13, A2dpPageActivity.PLAY_STATUS, statusId);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackChanged(long elementId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13EventTrackChanged() elementId: " + elementId);
            A2dpPageActivity.this.mA2dpPlayingProgress = 0;
            A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayStatus();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onAvrcp13EventTrackReachedEnd()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedStart() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onAvrcp13EventTrackReachedStart()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackPosChanged(long songPos) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13EventPlaybackPosChanged() songPos: " + songPos);
            if (A2dpPageActivity.this.isSupportSongPosition) {
                if (songPos >= 0) {
                    A2dpPageActivity.this.songPosition = songPos;
                } else {
                    A2dpPageActivity.this.songPosition = 0;
                }
                A2dpPageActivity a2dpPageActivity = A2dpPageActivity.this;
                a2dpPageActivity.mA2dpPlayingProgress = (int) ((a2dpPageActivity.songPosition * 100) / A2dpPageActivity.this.songLength);
                A2dpPageActivity.this.sendHandlerMessage(4, new String[]{A2dpPageActivity.PLAYING_PROGRESS}, new String[]{Integer.toString(A2dpPageActivity.this.mA2dpPlayingProgress)});
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventBatteryStatusChanged(byte statusId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13EventBatteryStatusChanged() statusId: " + ((int) statusId));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventSystemStatusChanged(byte statusId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp13EventSystemStatusChanged() statusId: " + ((int) statusId));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onAvrcp13EventPlayerSettingChanged()");
            A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingCurrentValues();
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onAvrcp14EventNowPlayingContentChanged()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "onAvrcp14EventAvailablePlayerChanged()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp14EventAddressedPlayerChanged() playerId: " + playerId + " uidCounter: " + uidCounter);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventUidsChanged(int uidCounter) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp14EventUidsChanged() uidCounter: " + uidCounter);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventVolumeChanged(byte volume) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcp14EventVolumeChanged() volume: " + ((int) volume));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp14SetAddressedPlayerSuccess()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp14SetBrowsedPlayerSuccess()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14FolderItems(int uidCounter, long itemCount) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "retAvrcp14FolderItems() uidCounter: " + uidCounter + " itemCount: " + itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14MediaItems(int uidCounter, long itemCount) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "retAvrcp14MediaItems() uidCounter: " + uidCounter + " itemCount: " + itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ChangePathSuccess(long itemCount) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "retAvrcp14ChangePathSuccess() itemCount: " + itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp14ItemAttributes()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp14ItemAttributes()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SearchResult(int uidCounter, long itemCount) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "retAvrcp14SearchResult() uidCounter: " + uidCounter + " itemCount: " + itemCount);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
            Log.i(A2dpPageActivity.TAG, "retAvrcp14AddToNowPlayingSuccess()");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "retAvrcp14SetAbsoluteVolumeSuccess() volume: " + ((int) volume));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpErrorResponse(int opId, int reason, byte eventId) throws RemoteException {
            String str = A2dpPageActivity.TAG;
            Log.i(str, "onAvrcpErrorResponse() opId: " + opId + " reason: " + reason + " eventId: " + ((int) eventId));
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpUpdateSongStatus(String artist, String album, String title) throws RemoteException {
            if (A2dpPageActivity.D) {
                Log.i(A2dpPageActivity.TAG, "retAvrcpUpdateSongStatus");
            }
            A2dpPageActivity.this.updateSongStatus(artist, album, title);
        }
    };
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            String avrcp = BuildConfig.FLAVOR;
            Log.e(A2dpPageActivity.TAG, "ready  onServiceConnected");
            A2dpPageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (A2dpPageActivity.this.mCommand == null) {
                Log.e(A2dpPageActivity.TAG, "mCommand is null!!");
                Toast.makeText(A2dpPageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                A2dpPageActivity.this.finish();
            }
            try {
                A2dpPageActivity.this.mCommand.registerA2dpCallback(A2dpPageActivity.this.mCallbackA2dp);
                A2dpPageActivity.this.mCommand.registerAvrcpCallback(A2dpPageActivity.this.mCallbackAvrcp);
                String str = A2dpPageActivity.TAG;
                Log.e(str, "Piggy Check A2DP getState() : " + A2dpPageActivity.this.mCommand.getA2dpConnectionState());
                String str2 = A2dpPageActivity.TAG;
                Log.e(str2, "Piggy Check AVRCP getState() : " + A2dpPageActivity.this.mCommand.getAvrcpConnectionState());
                A2dpPageActivity.this.a2dp_address = A2dpPageActivity.this.mCommand.getA2dpConnectedAddress();
                if (A2dpPageActivity.this.a2dp_address.equals(NfDef.DEFAULT_ADDRESS)) {
                    A2dpPageActivity.this.showToast("Choose a device first !");
                }
                A2dpPageActivity.this.state_a2dp = A2dpPageActivity.this.mCommand.getA2dpConnectionState();
                A2dpPageActivity.this.state_avrcp = A2dpPageActivity.this.mCommand.getAvrcpConnectionState();
                A2dpPageActivity.this.mCommand.reqAvrcp13GetElementAttributesPlaying();
                A2dpPageActivity.this.mCommand.reqAvrcpUpdateSongStatus();
                if (A2dpPageActivity.this.mCommand.isAvrcpConnected(A2dpPageActivity.this.a2dp_address)) {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetCapabilitiesSupportEvent();
                    Log.d(A2dpPageActivity.TAG, "isAvrcpConnected, reqAvrcp13GetCapabilitiesSupportEvent");
                }
                try {
                    A2dpPageActivity.this.mAttributeIdsCounter = 0;
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingAttributesList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                String a2dp = avrcp;
                if (A2dpPageActivity.this.state_a2dp == 110) {
                    a2dp = "Ready";
                } else if (A2dpPageActivity.this.state_a2dp == 140) {
                    a2dp = "Connected";
                } else if (A2dpPageActivity.this.state_a2dp == 150) {
                    a2dp = "Streaming";
                } else if (A2dpPageActivity.this.state_a2dp == 100) {
                    a2dp = "No Init";
                }
                if (A2dpPageActivity.this.state_avrcp == 110) {
                    avrcp = "Ready";
                } else if (A2dpPageActivity.this.state_avrcp >= 140) {
                    avrcp = "Connected";
                } else if (A2dpPageActivity.this.state_avrcp == 100) {
                    avrcp = "No Init";
                }
                A2dpPageActivity.this.sendHandlerMessage(2, new String[]{A2dpPageActivity.STATE_A2DP}, new String[]{a2dp});
                A2dpPageActivity.this.sendHandlerMessage(3, new String[]{A2dpPageActivity.STATE_AVRCP}, new String[]{avrcp});
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            Log.e(A2dpPageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(A2dpPageActivity.TAG, "onServiceDisconnected");
            A2dpPageActivity.this.mCommand = null;
        }
    };
    private ArrayAdapter<String> mEqualizerArrayAdapter;
    private Handler mHandler = null;
    private ArrayAdapter<String> mRepeatModeArrayAdapter;
    private ArrayAdapter<String> mScanArrayAdapter;
    private ArrayAdapter<String> mShuffleArrayAdapter;
    private double mVolume = 1.0d;
    private byte[] myAttributeIds = new byte[4];
    private ArrayList<String> myEqualizerArrayList;
    private ArrayList<String> myRepeatModeArrayList;
    private ArrayList<String> myScanArrayList;
    private ArrayList<String> myShuffleArrayList;
    private byte[][] myValueIds = ((byte[][]) Array.newInstance(byte.class, 4, 4));
    private ProgressBar progressBar_a2dp;
    private long songLength = 0;
    private long songPosition = 0;
    private Spinner spinner_equalizer;
    private byte[] spinner_new_mode = new byte[4];
    private byte[] spinner_prev_mode = new byte[4];
    private Spinner spinner_repeat_mode;
    private Spinner spinner_scan;
    private Spinner spinner_shuffle;
    private int state_a2dp;
    private int state_avrcp;
    private TextView text_album;
    private TextView text_artist;
    private TextView text_local_volume;
    private TextView text_no_support_playback_pos_changed;
    private TextView text_song_length_time;
    private TextView text_song_position_time;
    private TextView text_state_a2dp;
    private TextView text_state_avrcp;
    private TextView text_title;

    static /* synthetic */ int access$808(A2dpPageActivity x0) {
        int i = x0.mAttributeIdsCounter;
        x0.mAttributeIdsCounter = i + 1;
        return i;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.a2dp_page);
        initHandler();
        initView();
        bindService(new Intent(this, BtService.class), this.mConnection, 1);
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        if (D) {
            Log.e(TAG, "++ ON START ++");
        }
    }

    @Override // android.app.Activity
    public synchronized void onResume() {
        super.onResume();
        if (D) {
            Log.e(TAG, "++ ON Resume ++");
        }
    }

    @Override // android.app.Activity
    public synchronized void onPause() {
        super.onPause();
        if (D) {
            Log.e(TAG, "- ON PAUSE -");
        }
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        if (D) {
            Log.e(TAG, "-- ON STOP --");
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        try {
            this.mCommand.unregisterA2dpCallback(this.mCallbackA2dp);
            this.mCommand.unregisterAvrcpCallback(this.mCallbackAvrcp);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(this.mConnection);
        this.mHandler = null;
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        super.onDestroy();
    }

    public void initView() {
        this.text_state_a2dp = (TextView) findViewById(R.id.text_state_ad2p);
        this.text_state_avrcp = (TextView) findViewById(R.id.text_state_avrcp);
        this.text_local_volume = (TextView) findViewById(R.id.text_local_volume);
        this.text_artist = (TextView) findViewById(R.id.text_artist);
        this.text_album = (TextView) findViewById(R.id.text_album);
        this.text_title = (TextView) findViewById(R.id.text_title);
        this.text_no_support_playback_pos_changed = (TextView) findViewById(R.id.text_no_support_playback_pos_changed);
        this.text_song_position_time = (TextView) findViewById(R.id.text_song_position_time);
        this.text_song_length_time = (TextView) findViewById(R.id.text_song_length_time);
        this.progressBar_a2dp = (ProgressBar) findViewById(R.id.progressBar_a2dp);
        this.button_backward = (Button) findViewById(R.id.button_backward);
        this.button_rewind = (Button) findViewById(R.id.button_rewind);
        this.button_play = (Button) findViewById(R.id.button_play);
        this.button_pause = (Button) findViewById(R.id.button_pause);
        this.button_stop = (Button) findViewById(R.id.button_stop);
        this.button_fastforward = (Button) findViewById(R.id.button_fastforward);
        this.button_forward = (Button) findViewById(R.id.button_forward);
        this.button_browsing = (Button) findViewById(R.id.button_browsing);
        this.button_a2dp_connect = (Button) findViewById(R.id.button_a2dp_connect);
        this.button_a2dp_disconnect = (Button) findViewById(R.id.button_a2dp_disconnect);
        this.button_avrcp_connect = (Button) findViewById(R.id.button_avrcp_connect);
        this.button_avrcp_disconnect = (Button) findViewById(R.id.button_avrcp_disconnect);
        this.button_avrcp_register_1 = (Button) findViewById(R.id.button_avrcp_register_1);
        this.button_avrcp_register_2 = (Button) findViewById(R.id.button_avrcp_register_2);
        this.button_avrcp_register_5 = (Button) findViewById(R.id.button_avrcp_register_5);
        this.button_avrcp_register_8 = (Button) findViewById(R.id.button_avrcp_register_8);
        this.button_avrcp_capability = (Button) findViewById(R.id.button_avrcp_capability);
        this.button_avrcp_elements = (Button) findViewById(R.id.button_avrcp_element_attribute);
        this.button_get_status = (Button) findViewById(R.id.button_get_play_status);
        this.button_list_attr = (Button) findViewById(R.id.button_list_attr);
        this.button_list_re_value = (Button) findViewById(R.id.button_list_re_value);
        this.button_list_sh_value = (Button) findViewById(R.id.button_list_sh_value);
        this.button_set_re_off = (Button) findViewById(R.id.button_set_RE_value_off);
        this.button_set_re_all = (Button) findViewById(R.id.button_set_RE_value_all);
        this.button_set_sh_off = (Button) findViewById(R.id.button_set_SH_value_off);
        this.button_set_sh_all = (Button) findViewById(R.id.button_set_SH_value_all);
        this.button_get_re_value = (Button) findViewById(R.id.button_get_RE_value);
        this.button_get_sh_value = (Button) findViewById(R.id.button_get_SH_value);
        this.button_next_group = (Button) findViewById(R.id.button_next_group);
        this.button_prev_group = (Button) findViewById(R.id.button_prev_group);
        this.button_volume_up = (Button) findViewById(R.id.button_volume_up);
        this.button_volume_down = (Button) findViewById(R.id.button_volume_down);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.spinner_equalizer = (Spinner) findViewById(R.id.spinner_equalizer);
        this.spinner_repeat_mode = (Spinner) findViewById(R.id.spinner_repeat_mode);
        this.spinner_shuffle = (Spinner) findViewById(R.id.spinner_shuffle);
        this.spinner_scan = (Spinner) findViewById(R.id.spinner_scan);
        this.myEqualizerArrayList = new ArrayList<>();
        this.myRepeatModeArrayList = new ArrayList<>();
        this.myShuffleArrayList = new ArrayList<>();
        this.myScanArrayList = new ArrayList<>();
        this.mEqualizerArrayAdapter = new ArrayAdapter<>(this, (int) R.layout.a2dp_player_setting_value, this.myEqualizerArrayList);
        this.mRepeatModeArrayAdapter = new ArrayAdapter<>(this, (int) R.layout.a2dp_player_setting_value, this.myRepeatModeArrayList);
        this.mShuffleArrayAdapter = new ArrayAdapter<>(this, (int) R.layout.a2dp_player_setting_value, this.myShuffleArrayList);
        this.mScanArrayAdapter = new ArrayAdapter<>(this, (int) R.layout.a2dp_player_setting_value, this.myScanArrayList);
        this.mEqualizerArrayAdapter.setDropDownViewResource(17367049);
        this.mRepeatModeArrayAdapter.setDropDownViewResource(17367049);
        this.mShuffleArrayAdapter.setDropDownViewResource(17367049);
        this.mScanArrayAdapter.setDropDownViewResource(17367049);
        this.spinner_equalizer.setAdapter((SpinnerAdapter) this.mEqualizerArrayAdapter);
        this.spinner_repeat_mode.setAdapter((SpinnerAdapter) this.mRepeatModeArrayAdapter);
        this.spinner_shuffle.setAdapter((SpinnerAdapter) this.mShuffleArrayAdapter);
        this.spinner_scan.setAdapter((SpinnerAdapter) this.mScanArrayAdapter);
        this.button_backward.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_backward onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpBackward();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_rewind.setOnTouchListener(new View.OnTouchListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass3 */

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    Log.v(A2dpPageActivity.TAG, "button_rewind ACTION_DOWN");
                    try {
                        A2dpPageActivity.this.mCommand.reqAvrcpStartRewind();
                        return true;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else if (event.getAction() == 1) {
                    Log.v(A2dpPageActivity.TAG, "button_rewind ACTION_UP");
                    try {
                        A2dpPageActivity.this.mCommand.reqAvrcpStopRewind();
                        return true;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return false;
                    }
                } else if (event.getAction() != 3) {
                    return false;
                } else {
                    Log.v(A2dpPageActivity.TAG, "button_rewind ACTION_CANCEL");
                    try {
                        A2dpPageActivity.this.mCommand.reqAvrcpStopRewind();
                        return true;
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                        return false;
                    }
                }
            }
        });
        this.button_play.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_play onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpPlay();
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayStatus();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_pause.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_pause onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpPause();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_stop.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass6 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_stop onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpStop();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_fastforward.setOnTouchListener(new View.OnTouchListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass7 */

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    Log.v(A2dpPageActivity.TAG, "button_fastforward ACTION_DOWN");
                    try {
                        A2dpPageActivity.this.mCommand.reqAvrcpStartFastForward();
                        return true;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else if (event.getAction() == 1) {
                    Log.v(A2dpPageActivity.TAG, "button_fastforward ACTION_UP");
                    try {
                        A2dpPageActivity.this.mCommand.reqAvrcpStopFastForward();
                        return true;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return false;
                    }
                } else if (event.getAction() != 3) {
                    return false;
                } else {
                    Log.v(A2dpPageActivity.TAG, "button_fastforward ACTION_CANCEL");
                    try {
                        A2dpPageActivity.this.mCommand.reqAvrcpStopFastForward();
                        return true;
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                        return false;
                    }
                }
            }
        });
        this.button_forward.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass8 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_forward onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpForward();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_a2dp_connect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass9 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_a2dp_connect onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqA2dpConnect(A2dpPageActivity.this.mCommand.getTargetAddress());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_a2dp_disconnect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass10 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_a2dp_disconnect onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqA2dpDisconnect(A2dpPageActivity.this.mCommand.getTargetAddress());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_connect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass11 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_connect onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpConnect(A2dpPageActivity.this.mCommand.getTargetAddress());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_disconnect.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass12 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_disconnect onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpDisconnect(A2dpPageActivity.this.mCommand.getTargetAddress());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_register_1.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass13 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_register_1 onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 1, -1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_register_2.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass14 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_register_2 onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 2, -1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_register_5.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass15 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_register_5 onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 5, -1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_register_8.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass16 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_register_8 onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcpRegisterEventWatcher((byte) 8, -1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_capability.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass17 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_capability onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetCapabilitiesSupportEvent();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_avrcp_elements.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass18 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_avrcp_elements onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetElementAttributesPlaying();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_get_status.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass19 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_get_status onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayStatus();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_list_attr.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass20 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_list_attr onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingAttributesList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_list_re_value.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass21 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_list_re_value onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingValuesList((byte) 2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_list_sh_value.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass22 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_list_sh_value onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingValuesList((byte) 3);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_set_re_all.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass23 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_set_re_all onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 2, (byte) 3);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_set_re_off.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass24 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_set_re_off onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 2, (byte) 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_set_sh_all.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass25 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_set_sh_all onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 3, (byte) 2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_set_sh_off.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass26 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_set_sh_off onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 3, (byte) 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_next_group.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass27 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_next_group onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13NextGroup();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_prev_group.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass28 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_prev_group onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13PreviousGroup();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_volume_up.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass29 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_volume_up onClicked");
                A2dpPageActivity.this.mVolume += 0.1d;
                if (A2dpPageActivity.this.mVolume >= 1.0d || A2dpPageActivity.this.mVolume < 0.0d) {
                    A2dpPageActivity.this.mVolume = 1.0d;
                }
                A2dpPageActivity.this.text_local_volume.setText(BuildConfig.FLAVOR + ((float) A2dpPageActivity.this.mVolume));
                try {
                    A2dpPageActivity.this.mCommand.setA2dpLocalVolume((float) A2dpPageActivity.this.mVolume);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_volume_down.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass30 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_volume_down onClicked");
                if (A2dpPageActivity.this.mVolume < 0.1d || A2dpPageActivity.this.mVolume >= 0.2d) {
                    A2dpPageActivity.this.mVolume -= 0.1d;
                    if (A2dpPageActivity.this.mVolume <= 0.0d || A2dpPageActivity.this.mVolume > 1.0d) {
                        A2dpPageActivity.this.mVolume = 0.0d;
                    }
                } else {
                    A2dpPageActivity.this.mVolume = 0.0d;
                }
                A2dpPageActivity.this.text_local_volume.setText(BuildConfig.FLAVOR + ((float) A2dpPageActivity.this.mVolume));
                try {
                    A2dpPageActivity.this.mCommand.setA2dpLocalVolume((float) A2dpPageActivity.this.mVolume);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_get_re_value.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass31 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_get_re_value onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingCurrentValues();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_get_sh_value.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass32 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_get_sh_value onClicked");
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13GetPlayerSettingCurrentValues();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_browsing.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass33 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_browsing onClicked");
                Intent newAct = new Intent();
                newAct.setClass(A2dpPageActivity.this, Avrcp14PageActivity.class);
                A2dpPageActivity.this.startActivity(newAct);
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass34 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(A2dpPageActivity.TAG, "button_back onClicked");
                A2dpPageActivity.this.finish();
            }
        });
        this.spinner_equalizer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass35 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 1, A2dpPageActivity.this.myValueIds[0][position]);
                    if (position == 0) {
                        A2dpPageActivity.this.spinner_new_mode[0] = 1;
                    } else if (position == 1) {
                        A2dpPageActivity.this.spinner_new_mode[0] = 2;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinner_repeat_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass36 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                try {
                    if (A2dpPageActivity.this.doCommand) {
                        A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 2, A2dpPageActivity.this.myValueIds[1][position]);
                    }
                    A2dpPageActivity.this.doCommand = true;
                    Log.d(A2dpPageActivity.TAG, "in spinner_repeat_mode, reqAvrcp13SetPlayerSettingValue");
                    if (position == 0) {
                        A2dpPageActivity.this.spinner_new_mode[1] = 1;
                    } else if (position == 1) {
                        A2dpPageActivity.this.spinner_new_mode[1] = 2;
                    } else if (position == 2) {
                        A2dpPageActivity.this.spinner_new_mode[1] = 3;
                    } else if (position == 3) {
                        A2dpPageActivity.this.spinner_new_mode[1] = 4;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinner_shuffle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass37 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 3, A2dpPageActivity.this.myValueIds[2][position]);
                    if (position == 0) {
                        A2dpPageActivity.this.spinner_new_mode[2] = 1;
                    } else if (position == 1) {
                        A2dpPageActivity.this.spinner_new_mode[2] = 2;
                    } else if (position == 2) {
                        A2dpPageActivity.this.spinner_new_mode[2] = 3;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinner_scan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass38 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                try {
                    A2dpPageActivity.this.mCommand.reqAvrcp13SetPlayerSettingValue((byte) 4, A2dpPageActivity.this.myValueIds[3][position]);
                    if (position == 0) {
                        A2dpPageActivity.this.spinner_new_mode[3] = 1;
                    } else if (position == 1) {
                        A2dpPageActivity.this.spinner_new_mode[3] = 2;
                    } else if (position == 2) {
                        A2dpPageActivity.this.spinner_new_mode[3] = 3;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.A2dpPageActivity.AnonymousClass39 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Log.v(A2dpPageActivity.TAG, "handleMessage : " + A2dpPageActivity.this.getHandlerEventName(msg.what));
                try {
                    switch (msg.what) {
                        case 1:
                            A2dpPageActivity.this.text_album.setText(bundle.getString(A2dpPageActivity.INFO_ALBUM));
                            A2dpPageActivity.this.text_artist.setText(bundle.getString(A2dpPageActivity.INFO_ARTIST));
                            A2dpPageActivity.this.text_title.setText(bundle.getString(A2dpPageActivity.INFO_TITLE));
                            break;
                        case 2:
                            A2dpPageActivity.this.text_state_a2dp.setText(bundle.getString(A2dpPageActivity.STATE_A2DP));
                            break;
                        case 3:
                            A2dpPageActivity.this.text_state_avrcp.setText(bundle.getString(A2dpPageActivity.STATE_AVRCP));
                            try {
                                if (A2dpPageActivity.this.mCommand.isAvrcpConnected(BuildConfig.FLAVOR)) {
                                    A2dpPageActivity.this.mCommand.reqAvrcp13GetElementAttributesPlaying();
                                    A2dpPageActivity.this.mCommand.reqAvrcpUpdateSongStatus();
                                    A2dpPageActivity.this.mCommand.reqAvrcp13GetCapabilitiesSupportEvent();
                                    Log.d(A2dpPageActivity.TAG, "isAvrcpConnected, reqAvrcp13GetCapabilitiesSupportEvent");
                                    A2dpPageActivity.this.mAttributeIdsCounter = 0;
                                    break;
                                }
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                                break;
                            }
                            break;
                        case 4:
                            TextView textView = A2dpPageActivity.this.text_song_position_time;
                            StringBuilder sb = new StringBuilder();
                            sb.append((((int) A2dpPageActivity.this.songPosition) / 1000) / 60 < 10 ? "0" + Integer.toString((((int) A2dpPageActivity.this.songPosition) / 1000) / 60) : Integer.toString((((int) A2dpPageActivity.this.songPosition) / 1000) / 60));
                            sb.append(" : ");
                            sb.append(((int) ((A2dpPageActivity.this.songPosition / 1000) % 60)) < 10 ? "0" + Integer.toString((((int) A2dpPageActivity.this.songPosition) / 1000) % 60) : Integer.toString((((int) A2dpPageActivity.this.songPosition) / 1000) % 60));
                            sb.append(" ");
                            textView.setText(sb.toString());
                            A2dpPageActivity.this.progressBar_a2dp.setProgress(Integer.parseInt(bundle.getString(A2dpPageActivity.PLAYING_PROGRESS)));
                            TextView textView2 = A2dpPageActivity.this.text_song_length_time;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(" ");
                            sb2.append((((int) A2dpPageActivity.this.songLength) / 1000) / 60 < 10 ? "0" + Integer.toString((((int) A2dpPageActivity.this.songLength) / 1000) / 60) : Integer.toString((((int) A2dpPageActivity.this.songLength) / 1000) / 60));
                            sb2.append(" : ");
                            sb2.append(((int) ((A2dpPageActivity.this.songLength / 1000) % 60)) < 10 ? "0" + Integer.toString((((int) A2dpPageActivity.this.songLength) / 1000) % 60) : Integer.toString((((int) A2dpPageActivity.this.songLength) / 1000) % 60));
                            textView2.setText(sb2.toString());
                            break;
                        case 5:
                            A2dpPageActivity.this.progressBar_a2dp.setVisibility(8);
                            A2dpPageActivity.this.text_no_support_playback_pos_changed.setText("Not support playback position changed");
                            break;
                        case 6:
                            A2dpPageActivity.this.progressBar_a2dp.setVisibility(0);
                            A2dpPageActivity.this.text_no_support_playback_pos_changed.setText(BuildConfig.FLAVOR);
                            break;
                        case 7:
                            Log.e(A2dpPageActivity.TAG, "HANDLER_EVENT_NOTIFY_ARRAYADAPTER");
                            A2dpPageActivity.this.mEqualizerArrayAdapter.notifyDataSetChanged();
                            Log.d(A2dpPageActivity.TAG, "mEqualizerArrayAdapter.notifyDataSetChanged()");
                            A2dpPageActivity.this.mRepeatModeArrayAdapter.notifyDataSetChanged();
                            Log.d(A2dpPageActivity.TAG, "mRepeatModeArrayAdapter.notifyDataSetChanged()");
                            A2dpPageActivity.this.mShuffleArrayAdapter.notifyDataSetChanged();
                            Log.d(A2dpPageActivity.TAG, "mShuffleArrayAdapter.notifyDataSetChanged()");
                            A2dpPageActivity.this.mScanArrayAdapter.notifyDataSetChanged();
                            Log.d(A2dpPageActivity.TAG, "mScanArrayAdapter.notifyDataSetChanged()");
                            if (A2dpPageActivity.this.myValueIds[0][0] == 0) {
                                A2dpPageActivity.this.spinner_equalizer.setVisibility(8);
                            }
                            if (A2dpPageActivity.this.myValueIds[1][0] == 0) {
                                A2dpPageActivity.this.spinner_repeat_mode.setVisibility(8);
                            }
                            if (A2dpPageActivity.this.myValueIds[2][0] == 0) {
                                A2dpPageActivity.this.spinner_shuffle.setVisibility(8);
                            }
                            if (A2dpPageActivity.this.myValueIds[3][0] == 0) {
                                A2dpPageActivity.this.spinner_scan.setVisibility(8);
                            }
                            if (A2dpPageActivity.this.firstChose) {
                                for (int i = 0; i < A2dpPageActivity.this.myAttributeIds.length; i++) {
                                    byte b = A2dpPageActivity.this.myAttributeIds[i];
                                    if (b != 1) {
                                        if (b != 2) {
                                            if (b != 3) {
                                                if (b == 4) {
                                                    if (A2dpPageActivity.this.spinner_scan != null) {
                                                        A2dpPageActivity.this.spinner_scan.setSelection(A2dpPageActivity.this.firstScanPosition.byteValue() - 1);
                                                    }
                                                }
                                            } else if (A2dpPageActivity.this.spinner_shuffle != null) {
                                                A2dpPageActivity.this.spinner_shuffle.setSelection(A2dpPageActivity.this.firstShufflePosition.byteValue() - 1);
                                            }
                                        } else if (A2dpPageActivity.this.spinner_repeat_mode != null) {
                                            A2dpPageActivity.this.spinner_repeat_mode.setSelection(A2dpPageActivity.this.firstRepeatModePosition.byteValue() - 1);
                                        }
                                    } else if (A2dpPageActivity.this.spinner_equalizer != null) {
                                        A2dpPageActivity.this.spinner_equalizer.setSelection(A2dpPageActivity.this.firstEqualizerPosition.byteValue() - 1);
                                    }
                                }
                                A2dpPageActivity.this.firstChose = false;
                                break;
                            }
                            break;
                        case 8:
                            A2dpPageActivity.this.spinner_equalizer.setSelection(Integer.parseInt(bundle.getString(A2dpPageActivity.EQUALIZER_SET_SELECTION)));
                            break;
                        case 9:
                            A2dpPageActivity.this.spinner_repeat_mode.setSelection(Integer.parseInt(bundle.getString(A2dpPageActivity.REPEAT_MODE_SET_SELECTION)));
                            break;
                        case 10:
                            A2dpPageActivity.this.spinner_shuffle.setSelection(Integer.parseInt(bundle.getString(A2dpPageActivity.SHUFFLE_SET_SELECTION)));
                            break;
                        case 11:
                            A2dpPageActivity.this.spinner_scan.setSelection(Integer.parseInt(bundle.getString(A2dpPageActivity.SCAN_SET_SELECTION)));
                            break;
                        case 12:
                            if (A2dpPageActivity.this.infoToast != null) {
                                A2dpPageActivity.this.infoToast.cancel();
                                A2dpPageActivity.this.infoToast.setText(bundle.getString(A2dpPageActivity.TOAST_MESSAGE));
                                A2dpPageActivity.this.infoToast.setDuration(0);
                            } else {
                                A2dpPageActivity.this.infoToast = Toast.makeText(A2dpPageActivity.this.getApplicationContext(), bundle.getString(A2dpPageActivity.TOAST_MESSAGE), 0);
                            }
                            A2dpPageActivity.this.infoToast.show();
                            break;
                        case 13:
                            A2dpPageActivity.this.setPlayStatusDisplay(bundle.getInt(A2dpPageActivity.PLAY_STATUS));
                            break;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String message) {
        String str = TAG;
        Log.e(str, "showToast : " + message);
        sendHandlerMessage(12, new String[]{TOAST_MESSAGE}, new String[]{message});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateSongStatus(String artist, String album, String title) {
        sendHandlerMessage(1, new String[]{INFO_TITLE, INFO_ARTIST, INFO_ALBUM}, new String[]{title, artist, album});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendHandlerMessage(int what, String[] keys, String[] values) {
        Message msg = Message.obtain(this.mHandler, what);
        if (!(keys == null || values == null)) {
            Bundle b = new Bundle();
            for (int i = 0; i < keys.length; i++) {
                b.putString(keys[i], values[i]);
            }
            msg.setData(b);
        }
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendHandlerMessage(int what, String key, int value) {
        Message msg = Message.obtain(this.mHandler, what);
        Bundle b = new Bundle();
        b.putInt(key, value);
        msg.setData(b);
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getHandlerEventName(int what) {
        if (what == 1) {
            return "HANDLER_EVENT_UPDATE_SONG_STATUS";
        }
        if (what == 2) {
            return "HANDLER_EVENT_UPDATE_STATE_A2DP";
        }
        if (what == 3) {
            return "HANDLER_EVENT_UPDATE_STATE_AVRCP";
        }
        if (what == 4) {
            return "HANDLER_EVENT_UPDATE_PLAYING_PROGRESS";
        }
        if (what == 12) {
            return "HANDLER_EVENT_SHOW_TOAST";
        }
        if (what == 13) {
            return "HANDLER_EVENT_UPDATE_PLAY_STATUS";
        }
        return "Unknown Event:" + what + " !!";
    }

    /* access modifiers changed from: package-private */
    public void setPlayStatusDisplay(int status) {
        this.button_play.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.button_pause.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.button_stop.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.button_fastforward.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.button_rewind.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        if (status == 0) {
            this.button_stop.setTextColor(-16776961);
        } else if (status == 1) {
            this.button_play.setTextColor(-16776961);
        } else if (status == 2) {
            this.button_stop.setTextColor(-16776961);
        } else if (status == 3) {
            this.button_fastforward.setTextColor(-16776961);
        } else if (status == 4) {
            this.button_rewind.setTextColor(-16776961);
        }
    }
}
