package com.goodocom.gocsdk;

import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Commands {
    public static String ACCEPT_INCOMMING = "CE";
    public static String BTDEVICECHECK = "PW";
    public static String CANCEL_PAIR_MOD = "CB";
    public static String CANCEL_VOID_DIAL = "CJ";
    public static String CLOSE_BT = "P0";
    public static String CMD_ADDR = "DR";
    public static String CMD_CONNECT_MODE = "DE";
    public static String CMD_DUAL_CALL_MODE = "DL";
    public static String COMMAND_HEAD = "AT#";
    public static String CONNECT_A2DP = "DC";
    public static String CONNECT_DEVICE = "CC";
    public static String CONNECT_HFP = "SC";
    public static String CONNECT_HID = "HC";
    public static String CONNECT_HID_LAST = "HE";
    public static String CONNECT_LAST_AV_DEVICE = "MI";
    public static String CONNECT_MAP_MESSAGE = "YC";
    public static String CONNECT_SPP_ADDRESS = "SP";
    public static String DELETE_PAIR_LIST = "CV";
    public static String DIAL = "CW";
    public static String DISCONNECT_A2DP = "DA";
    public static String DISCONNECT_DEVICE = "CD";
    public static String DISCONNECT_HFP = "SE";
    public static String DISCONNECT_HID = "HD";
    public static String DISCONNECT_MAP_MESSAGE = "YD";
    public static String DTMF = "CX";
    public static String ENTER_TESTMODE = "TE";
    public static String FAST_BACK = "MT1";
    public static String FAST_FORWARD = "MR1";
    public static String FINISH_PHONE = "CG";
    public static String HANG_UP_CURRENT_ACCEPT_WAIT = "CR";
    public static String HANG_UP_WAIT_PHONE = "CQ";
    public static String HF_CMD = "HF";
    public static String HF_GET_FOUCS = "GF";
    public static String HID_ADJUST = "HP";
    public static String HOLD_CURRENT_ACCEPT_WAIT = "CS";
    public static String IND_A2DP_CONNECTED = "MH";
    public static String IND_A2DP_DISCONNECTED = "MY";
    public static String IND_AUTO_CONNECT_ACCEPT = "MF";
    public static String IND_AVRCP_STATUS = "ML";
    public static String IND_AV_STATUS = "MU";
    public static String IND_CALLLOG = "PD";
    public static String IND_CALLLOG_DONE = "PE";
    public static String IND_CALL_SUCCEED = "IC";
    public static String IND_CLCC = "CL";
    public static String IND_CMD_ADDR = "DR";
    public static String IND_CONNECTING = "IV";
    public static String IND_CONNECT_MAP_MESSAGE = "YC";
    public static String IND_CONNECT_MODE = "DE";
    public static String IND_CURRENT_ADDR = "JH";
    public static String IND_CURRENT_ADDR_NAME = "MX0";
    public static String IND_CURRENT_AND_PAIR_LIST = "XM";
    public static String IND_CURRENT_AND_PAIR_LIST_OVER = "PO";
    public static String IND_CURRENT_DEVICE_NAME = "MM";
    public static String IND_CURRENT_NAME = "SA";
    public static String IND_CURRENT_PIN_CODE = "MN";
    public static String IND_DEFAULT_SETING = "DS";
    public static String IND_DISCOVERY = "SF";
    public static String IND_DISCOVERY_DONE = "SH";
    public static String IND_DUAL_CALL_MODE = "DL";
    public static String IND_ERROR = "ERROR";
    public static String IND_EXIT_PAIR_MODE = "IJ";
    public static String IND_GET_MESSAGE_LIST_DONE = "YS";
    public static String IND_GET_MESSAGE_LIST_START = "YA";
    public static String IND_HANG_UP = "IF";
    public static String IND_HEAD = "\r\n";
    public static String IND_HFP_BANDRING = "RE";
    public static String IND_HFP_CONNECTED = "IB";
    public static String IND_HFP_DISCONNECTED = "IA";
    public static String IND_HFP_DISCONNECTED_RESON = "IY";
    public static String IND_HFP_STATUS = "MG";
    public static String IND_HF_LOCAL = "T1";
    public static String IND_HF_REMOTE = "T0";
    public static String IND_HID_ADJUST = "HP";
    public static String IND_HID_CONNECTED = "HB";
    public static String IND_HID_DISCONNECTED = "HA";
    public static String IND_HID_STATUS = "HS";
    public static String IND_INCOMING = "ID";
    public static String IND_INCOMING_NAME = "IQ";
    public static String IND_INIT_SUCCEED = "IS";
    public static String IND_IN_PAIR_MODE = "II";
    public static String IND_LOCAL_ADDRESS = "DB";
    public static String IND_LOCAL_PHONE_NUMBER = "CN";
    public static String IND_MAP_DELETE_MESSAGE_SUCCESS = "YT";
    public static String IND_MAP_SEND_MESSAGE_SUCCESS = "YP";
    public static String IND_MESSAGE_DISCONNECTED = "YD";
    public static String IND_MESSAGE_INFO = "YR";
    public static String IND_MESSAGE_LIST = "YL";
    public static String IND_MESSAGE_TEXT = "YU";
    public static String IND_MIC_STATUS = "IO";
    public static String IND_MUSIC_INFO = "MI";
    public static String IND_MUSIC_PLAYING = "MB";
    public static String IND_MUSIC_POS = "MP";
    public static String IND_MUSIC_STOPPED = "MA";
    public static String IND_OK = "OK";
    public static String IND_OPERATOR = "OP";
    public static String IND_OPP_PUSH_FAILED = "OF";
    public static String IND_OPP_PUSH_SUCCEED = "OC";
    public static String IND_OPP_RECEIVED_FILE = "OR";
    public static String IND_OUTGOING_TALKING_NUMBER = "IR";
    public static String IND_PAIR_LIST_DONE = "PL";
    public static String IND_PAIR_STATE = "PR";
    public static String IND_PAN_CONNECT = "NC";
    public static String IND_PAN_DISCONNECT = "NA";
    public static String IND_PAN_STATUS = "NS";
    public static String IND_PHONEBOOK_SIZE = "PF";
    public static String IND_PHONE_BOOK = "PB";
    public static String IND_PHONE_BOOK_DONE = "PC";
    public static String IND_PHONE_BOOK_VCARD_END = "VD";
    public static String IND_PHONE_BOOK_VCARD_START = "VS";
    public static String IND_PHOOKBOOK_PHOTO = "TP";
    public static String IND_PROFILE_ENABLED = "SX";
    public static String IND_PWD = "PW";
    public static String IND_RECEIVE_NEW_MESSAGE = "YW";
    public static String IND_RING_START = "VR1";
    public static String IND_RING_STOP = "VR0";
    public static String IND_SECOND_CALLING = "IT";
    public static String IND_SECOND_HANG_UP = "IN";
    public static String IND_SECOND_HOLD = "IH";
    public static String IND_SECOND_INCOMING = "IE";
    public static String IND_SECOND_TALKING = "IK";
    public static String IND_SET_PHONE_BOOK = "PA";
    public static String IND_SET_RING_PATH = "RP";
    public static String IND_SHUTDOWN = "ST";
    public static String IND_SIGNAL_BATTERY_VAL = "PS";
    public static String IND_SIM_BOOK = "PB";
    public static String IND_SIM_DONE = "PC";
    public static String IND_SPK_MIC_VAL = "KI";
    public static String IND_SPP_CONNECT = "SPC";
    public static String IND_SPP_DATA = "SPD";
    public static String IND_SPP_DISCONNECT = "SPS";
    public static String IND_SPP_STATUS = "SR";
    public static String IND_TALKING = "IG";
    public static String IND_UPDATE_SUCCESS = "US";
    public static String IND_VERSION_DATE = "MW";
    public static String IND_VOICE_CONNECTED = "MC";
    public static String IND_VOICE_DISCONNECTED = "MD";
    public static String INQUIRY_A2DP_STATUS = "MV";
    public static String INQUIRY_AUTO_CONNECT_ACCETP = "MF";
    public static String INQUIRY_AVRCP_STATUS = "MO";
    public static String INQUIRY_CUR_BT_ADDR = "QA";
    public static String INQUIRY_CUR_BT_NAME = "QB";
    public static String INQUIRY_DB_ADDR = "DF";
    public static String INQUIRY_HFP_STATUS = "CY";
    public static String INQUIRY_HID_STATUS = "HY";
    public static String INQUIRY_MUSIC_INFO = "MK";
    public static String INQUIRY_PAIR_RECORD = "MX";
    public static String INQUIRY_PAN_STATUS = "NY";
    public static String INQUIRY_PLAY_STATUS = "VI";
    public static String INQUIRY_SIGNEL_BATTERY_VAL = "QD";
    public static String INQUIRY_SPK_MIC_VAL = "QC";
    public static String INQUIRY_SPP_STATUS = "SY";
    public static String INQUIRY_VERSION_DATE = "MY";
    public static String LOCAL_ADDRESS = "VE";
    public static String MAP_DELETE_MESSAGES = "YR";
    public static String MAP_MESSAGE_DETAIL_GET = "YG";
    public static String MAP_MESSAGE_FOLDERDOWN = "YE";
    public static String MAP_MESSAGE_FOLDERUP = "YS";
    public static String MAP_MESSAGE_LIST_FOLDER = "YI";
    public static String MAP_MESSAGE_SEND = "YP";
    public static String MEETING_PHONE = "CT";
    public static String MIC_OPEN_CLOSE = "CM";
    public static String MODIFY_LOCAL_NAME = "MM";
    public static String MODIFY_PIN_CODE = "MN";
    public static String MOUSE_BACK = "HI";
    public static String MOUSE_CLICK = "HL";
    public static String MOUSE_DOWN = "HO";
    public static String MOUSE_HOME = "HH";
    public static String MOUSE_MENU = "HG";
    public static String MOUSE_MOVE = "HM";
    public static String MOUSE_UP = "HP";
    public static String MUSIC_BACKGROUND = "VC";
    public static String MUSIC_MUTE = "VA";
    public static String MUSIC_NORMAL = "VD";
    public static String MUSIC_UNMUTE = "VB";
    public static String MUSIC_VOL_SET = "VF";
    public static String NEXT_SOUND = "MD";
    public static String OPEN_BT = "P1";
    public static String OPP_SEND_FILE = "OS";
    public static String PAIR_DEVICE = "DP";
    public static String PAIR_MODE = "CA";
    public static String PAN_CONNECT = "NC";
    public static String PAN_DISCONNECT = "ND";
    public static String PAUSE_MUSIC = "MB";
    public static String PAUSE_PHONEBOOK_DOWN = "PO";
    public static String PLAY_MUSIC = "MS";
    public static String PLAY_PAUSE_MUSIC = "MA";
    public static String PLAY_PHONEBOOK_DOWN = "PQ";
    public static String PREV_SOUND = "ME";
    public static String READ_ALL_PHONEBOOK = "PX";
    public static String READ_LAST_PHONEBOOK_COUNT = "PD";
    public static String READ_NEXT_PHONEBOOK_COUNT = "PC";
    public static String REDIAL = "CH";
    public static String REJECT_INCOMMMING = "CF";
    public static String RESET_BLUE = "CZ";
    public static String SEND_KEY = "HK";
    public static String SEND_KEY_DOWN = "HKD";
    public static String SEND_KEY_UP = "HKU";
    public static String SEND_TOUCH_DOWN = "HQ";
    public static String SEND_TOUCH_MOVE = "HR";
    public static String SEND_TOUCH_UP = "HS";
    public static String SET_ALL_CALLLOG = "PK";
    public static String SET_AUTO_ANSWER = "MP";
    public static String SET_AUTO_CONNECT_ON_POWER = "MG";
    public static String SET_INCOMING_CALLLOG = "PI";
    public static String SET_LOCAL_PHONE_BOOK = "PN";
    public static String SET_MISSED_CALLLOG = "PJ";
    public static String SET_OPP_PATH = "OP";
    public static String SET_OUT_GOING_CALLLOG = "PH";
    public static String SET_PHONE_PHONE_BOOK = "PB";
    public static String SET_PROFILE_ENABLED = "SZ";
    public static String SET_RING_PATH = "RP";
    public static String SET_SIM_PHONE_BOOK = "PA";
    public static String SET_TOUCH_RESOLUTION = "HJ";
    public static String SPP_DISCONNECT = "SH";
    public static String SPP_SEND_DATA = "SG";
    public static String START_DISCOVERY = "SD";
    public static String START_PAIR = "DB";
    public static String STOP_DISCOVERY = "ST";
    public static String STOP_FAST_BACK = "MT0";
    public static String STOP_FAST_FORWARD = "MR0";
    public static String STOP_MUSIC = "MC";
    public static String STOP_PHONEBOOK_DOWN = "PS";
    public static String UNSET_AUTO_ANSWER = "MQ";
    public static String UNSET_AUTO_CONNECT_ON_POWER = "MH";
    public static String UPDATE_PSKEY = "UP";
    public static String VOICE_DIAL = "CI";
    public static String VOICE_MIC_GAIN = "VM";
    public static String VOICE_SIRI = "VO";
    public static String VOICE_TO_BLUE = "CP";
    public static String VOICE_TO_PHONE = "CN";
    public static String VOICE_TRANSFER = "CO";
    public static String VOLUME_DOWN = "CL";
    public static String VOLUME_UP = "CK";

    public static void initCommands() {
        BufferedReader br;
        String line;
        String str;
        String str2 = "=";
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("/system/config.ini")));
            while (true) {
                String line2 = br2.readLine();
                if (line2 != null) {
                    if (line2.contains(str2)) {
                        String[] keyValue = line2.split(str2);
                        String key = keyValue[0];
                        str = str2;
                        String value = keyValue[1].replace(" ", BuildConfig.FLAVOR);
                        line = line2;
                        if (keyValue.length < 2) {
                            br = br2;
                        } else if ("COMMAND_HEAD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                            COMMAND_HEAD = value.replace(" ", BuildConfig.FLAVOR);
                            StringBuilder sb = new StringBuilder();
                            br = br2;
                            sb.append("App  have ");
                            sb.append(value);
                            sb.append("Command!");
                            Log.d("app", sb.toString());
                        } else {
                            br = br2;
                            if ("IND_ERROR".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_ERROR = value.replace(" ", BuildConfig.FLAVOR);
                            } else if ("IND_OK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_OK = value.replace(" ", BuildConfig.FLAVOR);
                            } else if ("START_PAIR".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                START_PAIR = value.substring(0, 2);
                            } else if ("PAIR_MODE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAIR_MODE = value.substring(0, 2);
                            } else if ("CANCEL_PAIR_MOD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CANCEL_PAIR_MOD = value.substring(0, 2);
                            } else if ("CONNECT_HFP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_HFP = value.substring(0, 2);
                            } else if ("DISCONNECT_HFP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DISCONNECT_HFP = value.substring(0, 2);
                            } else if ("CONNECT_DEVICE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_DEVICE = value.substring(0, 2);
                            } else if ("DISCONNECT_DEVICE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DISCONNECT_DEVICE = value.substring(0, 2);
                            } else if ("ACCEPT_INCOMMING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                ACCEPT_INCOMMING = value.substring(0, 2);
                            } else if ("REJECT_INCOMMMING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                REJECT_INCOMMMING = value.substring(0, 2);
                            } else if ("FINISH_PHONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                FINISH_PHONE = value.substring(0, 2);
                            } else if ("REDIAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                REDIAL = value.substring(0, 2);
                            } else if ("VOICE_DIAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOICE_DIAL = value.substring(0, 2);
                            } else if ("CANCEL_VOID_DIAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CANCEL_VOID_DIAL = value.substring(0, 2);
                            } else if ("VOLUME_UP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOLUME_UP = value.substring(0, 2);
                            } else if ("VOLUME_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOLUME_DOWN = value.substring(0, 2);
                            } else if ("MIC_OPEN_CLOSE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MIC_OPEN_CLOSE = value.substring(0, 2);
                            } else if ("VOICE_TO_PHONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOICE_TO_PHONE = value.substring(0, 2);
                            } else if ("VOICE_TO_BLUE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOICE_TO_BLUE = value.substring(0, 2);
                            } else if ("VOICE_TRANSFER".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOICE_TRANSFER = value.substring(0, 2);
                            } else if ("HANG_UP_WAIT_PHONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                HANG_UP_WAIT_PHONE = value.substring(0, 2);
                            } else if ("HANG_UP_CURRENT_ACCEPT_WAIT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                HANG_UP_CURRENT_ACCEPT_WAIT = value.substring(0, 2);
                            } else if ("HOLD_CURRENT_ACCEPT_WAIT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                HOLD_CURRENT_ACCEPT_WAIT = value.substring(0, 2);
                            } else if ("MEETING_PHONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MEETING_PHONE = value.substring(0, 2);
                            } else if ("DELETE_PAIR_LIST".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DELETE_PAIR_LIST = value.substring(0, 2);
                            } else if ("DIAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DIAL = value.substring(0, 2);
                            } else if ("DTMF".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DTMF = value.substring(0, 2);
                            } else if ("INQUIRY_HFP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_HFP_STATUS = value.substring(0, 2);
                            } else if ("RESET_BLUE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                RESET_BLUE = value.substring(0, 2);
                            } else if ("CONNECT_A2DP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_A2DP = value.substring(0, 2);
                            } else if ("DISCONNECT_A2DP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DISCONNECT_A2DP = value.substring(0, 2);
                            } else if ("PLAY_PAUSE_MUSIC".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PLAY_PAUSE_MUSIC = value.substring(0, 2);
                            } else if ("STOP_MUSIC".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                STOP_MUSIC = value.substring(0, 2);
                            } else if ("NEXT_SOUND".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                NEXT_SOUND = value.substring(0, 2);
                            } else if ("PREV_SOUND".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PREV_SOUND = value.substring(0, 2);
                            } else if ("INQUIRY_AUTO_CONNECT_ACCETP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_AUTO_CONNECT_ACCETP = value.substring(0, 2);
                            } else if ("SET_AUTO_CONNECT_ON_POWER".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_AUTO_CONNECT_ON_POWER = value.substring(0, 2);
                            } else if ("UNSET_AUTO_CONNECT_ON_POWER".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                UNSET_AUTO_CONNECT_ON_POWER = value.substring(0, 2);
                            } else if ("CONNECT_LAST_AV_DEVICE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_LAST_AV_DEVICE = value.substring(0, 2);
                            } else if ("MODIFY_LOCAL_NAME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MODIFY_LOCAL_NAME = value.substring(0, 2);
                            } else if ("MODIFY_PIN_CODE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MODIFY_PIN_CODE = value.substring(0, 2);
                            } else if ("INQUIRY_AVRCP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_AVRCP_STATUS = value.substring(0, 2);
                            } else if ("SET_AUTO_ANSWER".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_AUTO_ANSWER = value.substring(0, 2);
                            } else if ("UNSET_AUTO_ANSWER".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                UNSET_AUTO_ANSWER = value.substring(0, 2);
                            } else if ("FAST_FORWARD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                FAST_FORWARD = value.substring(0, 3);
                            } else if ("STOP_FAST_FORWARD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                STOP_FAST_FORWARD = value.substring(0, 3);
                            } else if ("FAST_BACK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                FAST_BACK = value.substring(0, 3);
                            } else if ("STOP_FAST_BACK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                STOP_FAST_BACK = value.substring(0, 3);
                            } else if ("INQUIRY_A2DP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_A2DP_STATUS = value.substring(0, 2);
                            } else if ("INQUIRY_PAIR_RECORD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_PAIR_RECORD = value.substring(0, 2);
                            } else if ("INQUIRY_VERSION_DATE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_VERSION_DATE = value.substring(0, 2);
                            } else if ("SET_SIM_PHONE_BOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_SIM_PHONE_BOOK = value.substring(0, 2);
                            } else if ("SET_PHONE_PHONE_BOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_PHONE_PHONE_BOOK = value.substring(0, 2);
                            } else if ("SET_OUT_GOING_CALLLOG".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_OUT_GOING_CALLLOG = value.substring(0, 2);
                            } else if ("SET_INCOMING_CALLLOG".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_INCOMING_CALLLOG = value.substring(0, 2);
                            } else if ("SET_MISSED_CALLLOG".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_MISSED_CALLLOG = value.substring(0, 2);
                            } else if ("START_DISCOVERY".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                START_DISCOVERY = value.substring(0, 2);
                            } else if ("STOP_DISCOVERY".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                STOP_DISCOVERY = value.substring(0, 2);
                            } else if ("MUSIC_MUTE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MUSIC_MUTE = value.substring(0, 2);
                            } else if ("MUSIC_UNMUTE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MUSIC_UNMUTE = value.substring(0, 2);
                            } else if ("MUSIC_BACKGROUND".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MUSIC_BACKGROUND = value.substring(0, 2);
                            } else if ("MUSIC_NORMAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MUSIC_NORMAL = value.substring(0, 2);
                            } else if ("LOCAL_ADDRESS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                LOCAL_ADDRESS = value.substring(0, 2);
                            } else if ("OPP_SEND_FILE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                OPP_SEND_FILE = value.substring(0, 2);
                            } else if ("CONNECT_SPP_ADDRESS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_SPP_ADDRESS = value.substring(0, 2);
                            } else if ("SPP_SEND_DATA".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SPP_SEND_DATA = value.substring(0, 2);
                            } else if ("SPP_DISCONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SPP_DISCONNECT = value.substring(0, 2);
                            } else if ("INQUIRY_PLAY_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_PLAY_STATUS = value.substring(0, 2);
                            } else if ("CONNECT_HID".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_HID = value.substring(0, 2);
                            } else if ("CONNECT_HID_LAST".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CONNECT_HID_LAST = value.substring(0, 2);
                            } else if ("DISCONNECT_HID".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                DISCONNECT_HID = value.substring(0, 2);
                            } else if ("MOUSE_MENU".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_MENU = value.substring(0, 2);
                            } else if ("MOUSE_HOME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_HOME = value.substring(0, 2);
                            } else if ("MOUSE_BACK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_BACK = value.substring(0, 2);
                            } else if ("MOUSE_MOVE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_MOVE = value.substring(0, 2);
                            } else if ("MOUSE_CLICK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_CLICK = value.substring(0, 2);
                            } else if ("MOUSE_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_DOWN = value.substring(0, 2);
                            } else if ("MOUSE_UP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MOUSE_UP = value.substring(0, 2);
                            } else if ("SEND_TOUCH_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SEND_TOUCH_DOWN = value.substring(0, 2);
                            } else if ("SEND_TOUCH_MOVE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SEND_TOUCH_MOVE = value.substring(0, 2);
                            } else if ("SEND_TOUCH_UP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SEND_TOUCH_UP = value.substring(0, 2);
                            } else if ("HF_CMD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                HF_CMD = value.substring(0, 2);
                            } else if ("INQUIRY_MUSIC_INFO".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_MUSIC_INFO = value.substring(0, 2);
                            } else if ("INQUIRY_CUR_BT_ADDR".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_CUR_BT_ADDR = value.substring(0, 2);
                            } else if ("INQUIRY_CUR_BT_NAME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_CUR_BT_NAME = value.substring(0, 2);
                            } else if ("PAUSE_PHONEBOOK_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAUSE_PHONEBOOK_DOWN = value.substring(0, 2);
                            } else if ("STOP_PHONEBOOK_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                STOP_PHONEBOOK_DOWN = value.substring(0, 2);
                            } else if ("PLAY_MUSIC".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PLAY_MUSIC = value.substring(0, 2);
                            } else if ("PAUSE_MUSIC".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAUSE_MUSIC = value.substring(0, 2);
                            } else if ("SET_PROFILE_ENABLED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_PROFILE_ENABLED = value.substring(0, 2);
                            } else if ("OPEN_BT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                OPEN_BT = value.substring(0, 2);
                            } else if ("CLOSE_BT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CLOSE_BT = value.substring(0, 2);
                            } else if ("IND_HEAD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HEAD = value.substring(0, 2);
                            } else if ("IND_HFP_DISCONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HFP_DISCONNECTED = value.substring(0, 2);
                            } else if ("IND_HFP_CONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HFP_CONNECTED = value.substring(0, 2);
                            } else if ("IND_CALL_SUCCEED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CALL_SUCCEED = value.substring(0, 2);
                            } else if ("IND_INCOMING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_INCOMING = value.substring(0, 2);
                            } else if ("IND_SECOND_INCOMING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SECOND_INCOMING = value.substring(0, 2);
                            } else if ("IND_SECOND_HOLD".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SECOND_HOLD = value.substring(0, 2);
                            } else if ("IND_SECOND_CALLING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SECOND_CALLING = value.substring(0, 2);
                            } else if ("IND_SECOND_HANG_UP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SECOND_HANG_UP = value.substring(0, 2);
                            } else if ("IND_HANG_UP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HANG_UP = value.substring(0, 2);
                            } else if ("IND_SECOND_TALKING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SECOND_TALKING = value.substring(0, 2);
                            } else if ("IND_TALKING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_TALKING = value.substring(0, 2);
                            } else if ("IND_RING_START".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_RING_START = value.substring(0, 2);
                            } else if ("IND_RING_STOP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_RING_STOP = value.substring(0, 2);
                            } else if ("IND_HF_LOCAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HF_LOCAL = value.substring(0, 2);
                            } else if ("IND_HF_REMOTE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HF_REMOTE = value.substring(0, 2);
                            } else if ("IND_IN_PAIR_MODE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_IN_PAIR_MODE = value.substring(0, 2);
                            } else if ("IND_EXIT_PAIR_MODE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_EXIT_PAIR_MODE = value.substring(0, 2);
                            } else if ("IND_INCOMING_NAME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_INCOMING_NAME = value.substring(0, 2);
                            } else if ("IND_OUTGOING_TALKING_NUMBER".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_OUTGOING_TALKING_NUMBER = value.substring(0, 2);
                            } else if ("IND_INIT_SUCCEED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_INIT_SUCCEED = value.substring(0, 2);
                            } else if ("IND_CONNECTING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CONNECTING = value.substring(0, 2);
                            } else if ("IND_MUSIC_PLAYING".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MUSIC_PLAYING = value.substring(0, 2);
                            } else if ("IND_MUSIC_STOPPED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MUSIC_STOPPED = value.substring(0, 2);
                            } else if ("IND_VOICE_CONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_VOICE_CONNECTED = value.substring(0, 2);
                            } else if ("IND_VOICE_DISCONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_VOICE_DISCONNECTED = value.substring(0, 2);
                            } else if ("IND_AUTO_CONNECT_ACCEPT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_AUTO_CONNECT_ACCEPT = value.substring(0, 2);
                            } else if ("IND_CURRENT_ADDR".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CURRENT_ADDR = value.substring(0, 2);
                            } else if ("IND_CURRENT_NAME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CURRENT_NAME = value.substring(0, 2);
                            } else if ("IND_HFP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HFP_STATUS = value.substring(0, 2);
                            } else if ("IND_AV_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_AV_STATUS = value.substring(0, 2);
                            } else if ("IND_VERSION_DATE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_VERSION_DATE = value.substring(0, 2);
                            } else if ("IND_AVRCP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_AVRCP_STATUS = value.substring(0, 2);
                            } else if ("IND_CURRENT_DEVICE_NAME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CURRENT_ADDR_NAME = value.substring(0, 2);
                            } else if ("IND_CURRENT_PIN_CODE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CURRENT_PIN_CODE = value.substring(0, 2);
                            } else if ("IND_A2DP_CONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_A2DP_CONNECTED = value.substring(0, 2);
                            } else if ("IND_CURRENT_AND_PAIR_LIST".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CURRENT_AND_PAIR_LIST = value.substring(0, 2);
                            } else if ("IND_A2DP_DISCONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_A2DP_DISCONNECTED = value.substring(0, 2);
                            } else if ("IND_SET_PHONE_BOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SET_PHONE_BOOK = value.substring(0, 3);
                            } else if ("IND_PHONE_BOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PHONE_BOOK = value.substring(0, 2);
                            } else if ("IND_SIM_BOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SIM_BOOK = value.substring(0, 2);
                            } else if ("IND_PHONE_BOOK_DONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PHONE_BOOK_DONE = value.substring(0, 2);
                            } else if ("IND_SIM_DONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SIM_DONE = value.substring(0, 2);
                            } else if ("IND_CALLLOG_DONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CALLLOG_DONE = value.substring(0, 2);
                            } else if ("IND_CALLLOG".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CALLLOG = value.substring(0, 2);
                            } else if ("IND_DISCOVERY".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_DISCOVERY = value.substring(0, 2);
                            } else if ("IND_DISCOVERY_DONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_DISCOVERY_DONE = value.substring(0, 2);
                            } else if ("IND_LOCAL_ADDRESS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_LOCAL_ADDRESS = value.substring(0, 2);
                            } else if ("IND_SPP_DATA".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SPP_DATA = value.substring(0, 2);
                            } else if ("IND_SPP_CONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SPP_CONNECT = value.substring(0, 2);
                            } else if ("IND_SPP_DISCONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SPP_DISCONNECT = value.substring(0, 2);
                            } else if ("IND_OPP_RECEIVED_FILE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_OPP_RECEIVED_FILE = value.substring(0, 2);
                            } else if ("IND_OPP_PUSH_SUCCEED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_OPP_PUSH_SUCCEED = value.substring(0, 2);
                            } else if ("IND_OPP_PUSH_FAILED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_OPP_PUSH_FAILED = value.substring(0, 2);
                            } else if ("IND_HID_CONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HID_CONNECTED = value.substring(0, 2);
                            } else if ("IND_HID_DISCONNECTED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HID_DISCONNECTED = value.substring(0, 2);
                            } else if ("IND_MUSIC_INFO".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MUSIC_INFO = value.substring(0, 2);
                            } else if ("IND_PROFILE_ENABLED".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PROFILE_ENABLED = value.substring(0, 2);
                            } else if ("IND_MESSAGE_LIST".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MESSAGE_LIST = value.substring(0, 2);
                            } else if ("IND_MESSAGE_TEXT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MESSAGE_TEXT = value.substring(0, 2);
                            } else if ("IND_PAIR_STATE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PAIR_STATE = value.substring(0, 1);
                            } else if ("PAIR_DEVICE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAIR_DEVICE = value.substring(0, 2);
                            } else if ("PAUSE_MUSIC".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAUSE_MUSIC = value.substring(0, 2);
                            } else if ("SET_LOCAL_PHONE_BOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_LOCAL_PHONE_BOOK = value.substring(0, 2);
                            } else if ("SEND_KEY".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SEND_KEY = value.substring(0, 2);
                            } else if ("SEND_KEY_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SEND_KEY_DOWN = value.substring(0, 3);
                            } else if ("SEND_KEY_UP".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SEND_KEY_UP = value.substring(0, 3);
                            } else if ("INQUIRY_MUSIC_INFO".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_MUSIC_INFO = value.substring(0, 2);
                            } else if ("READ_NEXT_PHONEBOOK_COUNT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                READ_NEXT_PHONEBOOK_COUNT = value.substring(0, 2);
                            } else if ("READ_LAST_PHONEBOOK_COUNT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                READ_LAST_PHONEBOOK_COUNT = value.substring(0, 2);
                            } else if ("READ_ALL_PHONEBOOK".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                READ_ALL_PHONEBOOK = value.substring(0, 2);
                            } else if ("STOP_PHONEBOOK_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                STOP_PHONEBOOK_DOWN = value.substring(0, 2);
                            } else if ("PAUSE_PHONEBOOK_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAUSE_PHONEBOOK_DOWN = value.substring(0, 2);
                            } else if ("PLAY_PHONEBOOK_DOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PLAY_PHONEBOOK_DOWN = value.substring(0, 2);
                            } else if ("INQUIRY_HID_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_HID_STATUS = value.substring(0, 2);
                            } else if ("SET_TOUCH_RESOLUTION".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_TOUCH_RESOLUTION = value.substring(0, 2);
                            } else if ("HID_ADJUST".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                HID_ADJUST = value.substring(0, 2);
                            } else if ("PAN_CONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAN_CONNECT = value.substring(0, 2);
                            } else if ("PAN_DISCONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PAN_DISCONNECT = value.substring(0, 2);
                            } else if ("INQUIRY_PAN_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_PAN_STATUS = value.substring(0, 2);
                            } else if ("INQUIRY_DB_ADDR".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_DB_ADDR = value.substring(0, 2);
                            } else if ("OPEN_BT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                OPEN_BT = value.substring(0, 2);
                            } else if ("CLOSE_BT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                CLOSE_BT = value.substring(0, 2);
                            } else if ("INQUIRY_SPK_MIC_VAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_SPK_MIC_VAL = value.substring(0, 2);
                            } else if ("INQUIRY_SIGNEL_BATTERY_VAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_SIGNEL_BATTERY_VAL = value.substring(0, 2);
                            } else if ("INQUIRY_SPP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                INQUIRY_SPP_STATUS = value.substring(0, 2);
                            } else if ("MUSIC_VOL_SET".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                MUSIC_VOL_SET = value.substring(0, 2);
                            } else if ("VOICE_MIC_GAIN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOICE_MIC_GAIN = value.substring(0, 2);
                            } else if ("UPDATE_PSKEY".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                UPDATE_PSKEY = value.substring(0, 2);
                            } else if ("PLAY_MUSIC".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                PLAY_MUSIC = value.substring(0, 2);
                            } else if ("SET_OPP_PATH".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                SET_OPP_PATH = value.substring(0, 2);
                            } else if ("ENTER_TESTMODE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                ENTER_TESTMODE = value.substring(0, 2);
                            } else if ("VOICE_SIRI".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                VOICE_SIRI = value.substring(0, 2);
                            } else if ("IND_CURRENT_ADDR_NAME".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_CURRENT_ADDR_NAME = value.substring(0, 3);
                            } else if ("IND_PAIR_LIST_DONE".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PAIR_LIST_DONE = value.substring(0, 2);
                            } else if ("IND_HID_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HID_STATUS = value.substring(0, 2);
                            } else if ("IND_HID_ADJUST".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_HID_ADJUST = value.substring(0, 2);
                            } else if ("IND_MIC_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MIC_STATUS = value.substring(0, 2);
                            } else if ("IND_SPK_MIC_VAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SPK_MIC_VAL = value.substring(0, 2);
                            } else if ("IND_MUSIC_INFO".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_MUSIC_INFO = value.substring(0, 2);
                            } else if ("IND_SPP_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SPP_STATUS = value.substring(0, 2);
                            } else if ("IND_PAN_DISCONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PAN_DISCONNECT = value.substring(0, 2);
                            } else if ("IND_PAN_CONNECT".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PAN_CONNECT = value.substring(0, 2);
                            } else if ("IND_PAN_STATUS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_PAN_STATUS = value.substring(0, 2);
                            } else if ("IND_SIGNAL_BATTERY_VAL".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SIGNAL_BATTERY_VAL = value.substring(0, 2);
                            } else if ("IND_UPDATE_SUCCESS".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_UPDATE_SUCCESS = value.substring(0, 2);
                            } else if ("IND_SHUTDOWN".equals(key.replace(" ", BuildConfig.FLAVOR))) {
                                IND_SHUTDOWN = value.substring(0, 2);
                            } else if ("IND_PHOOKBOOK_PHOTO".equals(key.replace(BuildConfig.FLAVOR, BuildConfig.FLAVOR))) {
                                IND_PHOOKBOOK_PHOTO = value.substring(0, 2);
                            } else {
                                Log.d("ysy", "App not have " + key + "if Command!");
                            }
                        }
                    } else {
                        str = str2;
                        br = br2;
                        line = line2;
                    }
                    str2 = str;
                    br2 = br;
                } else {
                    br2.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
