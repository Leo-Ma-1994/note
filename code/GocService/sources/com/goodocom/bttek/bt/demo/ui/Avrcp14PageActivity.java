package com.goodocom.bttek.bt.demo.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCallbackA2dp;
import com.goodocom.bttek.bt.aidl.UiCallbackAvrcp;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.demo.service.BtService;
import com.goodocom.bttek.bt.res.NfDef;
import com.goodocom.bttek.util.db.DbHelperAvrcp;
import java.util.ArrayList;

public class Avrcp14PageActivity extends Activity {
    private static boolean D = true;
    private static final int HANDLER_EVENT_UPDATE_FOLDER = 2;
    private static final int HANDLER_EVENT_UPDATE_LIST = 1;
    private static final String KEY_SCOPE = "SCOPE";
    private static String TAG = "nFore_Avrcp14Page";
    private static final int TYPE_FOLDER = 2;
    private static final int TYPE_MEDIA = 3;
    private static final int TYPE_PLAYER = 1;
    private final String SQL_QUERY_AVRCP_FOLDER = "select * from FolderItems";
    private final String SQL_QUERY_AVRCP_MEDIA = "select * from MediaItems";
    private final String SQL_QUERY_AVRCP_PLAYERS = "select * from MediaPlayerItems";
    String address_browse;
    private Button button_back;
    private Button button_enter;
    private Button button_path_return;
    private Button button_player;
    SQLiteDatabase db;
    DbHelperAvrcp dbHelper;
    private Toast infoToast;
    private UiCallbackA2dp mA2dpCallback = new UiCallbackA2dp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass10 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackA2dp
        public void onA2dpServiceReady() throws RemoteException {
            Log.i(Avrcp14PageActivity.TAG, "onA2dpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackA2dp
        public void onA2dpStateChanged(String address, int prevState, int newState) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                String str = Avrcp14PageActivity.TAG;
                Log.i(str, "onA2dpStateChanged " + address + " State : " + prevState + " -> " + newState);
            }
        }
    };
    private UiCallbackAvrcp mAvrcpCallback = new UiCallbackAvrcp.Stub() {
        /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass11 */

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpServiceReady() throws RemoteException {
            Log.i(Avrcp14PageActivity.TAG, "onAvrcpServiceReady");
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpUpdateSongStatus(String artist, String album, String title) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, "retAvrcpUpdateSongStatus()");
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetBrowsedPlayerSuccess(String[] path, int uidCounter, long itemCount) throws RemoteException {
            String browsedPath = "/";
            Avrcp14PageActivity.this.mBrowsedPath.clear();
            for (int i = 0; i < path.length; i++) {
                browsedPath = browsedPath + path[i] + "/";
                Avrcp14PageActivity.this.mBrowsedPath.add(path[i]);
            }
            Avrcp14PageActivity avrcp14PageActivity = Avrcp14PageActivity.this;
            avrcp14PageActivity.runOnUiThread(new UiRunnable(avrcp14PageActivity.mSelectedPlayer) {
                /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass11.AnonymousClass1 */

                @Override // java.lang.Runnable
                public void run() {
                    String str = Avrcp14PageActivity.TAG;
                    Log.d(str, "Piggy Check mSelectedPlayer : " + this.data);
                    Avrcp14PageActivity.this.button_player.setText(this.data);
                }
            });
            Avrcp14PageActivity.this.runOnUiThread(new UiRunnable(browsedPath) {
                /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass11.AnonymousClass2 */

                @Override // java.lang.Runnable
                public void run() {
                    Avrcp14PageActivity.this.text_path.setText(this.data);
                }
            });
            Avrcp14PageActivity.this.sendHandlerMessage(2, Avrcp14PageActivity.KEY_SCOPE, (byte) 1);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, "onAvrcpEventAddressedPlayerChanged ");
            }
            Avrcp14PageActivity.this.sendHandlerMessage(1, BuildConfig.FLAVOR, (byte) 0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SetAbsoluteVolumeSuccess(byte volume) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14SearchResult(int uidCounter, long itemCount) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ItemAttributes(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14FolderItems(int uidCounter, long itemCount) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                String str = Avrcp14PageActivity.TAG;
                Log.i(str, "retAvrcp14FolderItems uidCounter : " + uidCounter + " itemCount : " + itemCount);
            }
            Avrcp14PageActivity.this.sendHandlerMessage(1, BuildConfig.FLAVOR, (byte) 0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14MediaItems(int uidCounter, long itemCount) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                String str = Avrcp14PageActivity.TAG;
                Log.i(str, "retAvrcp14MediaItems uidCounter : " + uidCounter + " itemCount : " + itemCount);
            }
            Avrcp14PageActivity.this.sendHandlerMessage(1, BuildConfig.FLAVOR, (byte) 0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14ChangePathSuccess(long itemCount) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, "retAvrcpPathChanged itemCount : " + itemCount);
            }
            String browsedPath = "/";
            if (Avrcp14PageActivity.this.mIsUpDirection) {
                Avrcp14PageActivity.this.mIsUpDirection = false;
                if (!Avrcp14PageActivity.this.mBrowsedPath.isEmpty()) {
                    Avrcp14PageActivity.this.mBrowsedPath.remove(Avrcp14PageActivity.this.mBrowsedPath.size() - 1);
                }
            } else {
                Avrcp14PageActivity.this.mBrowsedPath.add(Avrcp14PageActivity.this.mSelectedPath);
            }
            for (int i = 0; i < Avrcp14PageActivity.this.mBrowsedPath.size(); i++) {
                browsedPath = browsedPath + ((String) Avrcp14PageActivity.this.mBrowsedPath.get(i)) + "/";
            }
            Avrcp14PageActivity.this.runOnUiThread(new UiRunnable(browsedPath) {
                /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass11.AnonymousClass3 */

                @Override // java.lang.Runnable
                public void run() {
                    Avrcp14PageActivity.this.text_path.setText(this.data);
                }
            });
            Avrcp14PageActivity.this.sendHandlerMessage(2, Avrcp14PageActivity.KEY_SCOPE, (byte) 1);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcpPlayModeChanged(String address, int mode) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, "retAvrcpPlayModeChanged");
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingValuesList(byte attributeId, byte[] valueIds) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingCurrentValues(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayerSettingAttributesList(byte[] attributeIds) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13PlayStatus(long songLen, long songPos, byte statusId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherSuccess(byte eventId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                String str = Avrcp14PageActivity.TAG;
                Log.i(str, "onAvrcp13RegisterEventWatcherSuccess() " + ((int) eventId));
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13RegisterEventWatcherFail(byte eventId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                String str = Avrcp14PageActivity.TAG;
                Log.i(str, "onAvrcp13RegisterEventWatcherFail() " + ((int) eventId));
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13ElementAttributesPlaying(int[] metadataAtrributeIds, String[] texts) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void retAvrcp13CapabilitiesSupportEvent(byte[] eventIds) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpStateChanged(String address, int prevState, int newState) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                String str = Avrcp14PageActivity.TAG;
                Log.i(str, "onAvrcpStateChanged " + address + " State : " + prevState + " -> " + newState);
            }
            String state = BuildConfig.FLAVOR;
            if (newState == 140) {
                state = "Connected";
                Avrcp14PageActivity.this.address_browse = address;
            } else if (newState == 145) {
                state = "Browsing";
                Avrcp14PageActivity.this.address_browse = address;
            } else if (newState == 110) {
                state = "Ready";
            }
            Avrcp14PageActivity.this.runOnUiThread(new UiRunnable(state) {
                /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass11.AnonymousClass4 */

                @Override // java.lang.Runnable
                public void run() {
                    Avrcp14PageActivity.this.text_state.setText(this.data);
                }
            });
            Avrcp14PageActivity.this.state_browse = newState;
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcpErrorResponse(int opId, int reason, byte eventId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventVolumeChanged(byte volume) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventUidsChanged(int uidCounter) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, "onAvrcpEventAvailablePlayerChanged()");
            }
            Avrcp14PageActivity.this.sendHandlerMessage(1, BuildConfig.FLAVOR, (byte) 0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp14EventAddressedPlayerChanged(int playerId, int uidCounter) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, "onAvrcpEventAddressedPlayerChanged()");
            }
            Avrcp14PageActivity.this.sendHandlerMessage(1, BuildConfig.FLAVOR, (byte) 0);
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedStart() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventTrackChanged(long elementId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventSystemStatusChanged(byte statusId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlayerSettingChanged(byte[] attributeIds, byte[] valueIds) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackStatusChanged(byte statusId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventPlaybackPosChanged(long songPos) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }

        @Override // com.goodocom.bttek.bt.aidl.UiCallbackAvrcp
        public void onAvrcp13EventBatteryStatusChanged(byte statusId) throws RemoteException {
            if (Avrcp14PageActivity.D) {
                Log.i(Avrcp14PageActivity.TAG, BuildConfig.FLAVOR);
            }
        }
    };
    private ArrayList<String> mBrowsedPath = new ArrayList<>();
    private UiCommand mCommand;
    private ServiceConnection mConnection = new ServiceConnection() {
        /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass1 */

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.e(Avrcp14PageActivity.TAG, "ready  onServiceConnected");
            Avrcp14PageActivity.this.mCommand = UiCommand.Stub.asInterface(service);
            if (Avrcp14PageActivity.this.mCommand == null) {
                Log.e(Avrcp14PageActivity.TAG, "mCommand is null!!");
                Toast.makeText(Avrcp14PageActivity.this.getApplicationContext(), "UiService is null!", 0).show();
                Avrcp14PageActivity.this.finish();
            }
            try {
                Avrcp14PageActivity.this.mCommand.registerA2dpCallback(Avrcp14PageActivity.this.mA2dpCallback);
                Avrcp14PageActivity.this.mCommand.registerAvrcpCallback(Avrcp14PageActivity.this.mAvrcpCallback);
                Avrcp14PageActivity.this.address_browse = Avrcp14PageActivity.this.mCommand.getAvrcpConnectedAddress();
                if (Avrcp14PageActivity.this.address_browse.equals(NfDef.DEFAULT_ADDRESS)) {
                    Avrcp14PageActivity.this.showToast("Choose a device first !");
                }
                Avrcp14PageActivity.this.state_browse = Avrcp14PageActivity.this.mCommand.isAvrcp14BrowsingChannelEstablished() ? 140 : 110;
                String state = BuildConfig.FLAVOR;
                if (Avrcp14PageActivity.this.state_browse == 110) {
                    state = "Ready";
                } else if (Avrcp14PageActivity.this.state_browse == 145) {
                    state = "Browsing";
                } else if (Avrcp14PageActivity.this.state_browse >= 140) {
                    state = "Connected";
                }
                Avrcp14PageActivity.this.runOnUiThread(new UiRunnable(state) {
                    /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass1.AnonymousClass1 */

                    @Override // java.lang.Runnable
                    public void run() {
                        Avrcp14PageActivity.this.text_state.setText(this.data);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e(Avrcp14PageActivity.TAG, "end  onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Log.e(Avrcp14PageActivity.TAG, "onServiceDisconnected");
            Avrcp14PageActivity.this.mCommand = null;
        }
    };
    ListView mFolderListView;
    private ArrayAdapter<String> mFoldersArrayAdapter;
    private AdapterView.OnItemClickListener mFoldersClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass8 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            String str = Avrcp14PageActivity.TAG;
            Log.e(str, "Piggy Check position : " + position);
            Avrcp14PageActivity avrcp14PageActivity = Avrcp14PageActivity.this;
            avrcp14PageActivity.mSelectedUid = avrcp14PageActivity.mMediaFolders.getUidByIndex(position);
            String str2 = Avrcp14PageActivity.TAG;
            Log.e(str2, "Piggy Check mSelectedUid : " + Avrcp14PageActivity.this.mSelectedUid);
            Avrcp14PageActivity avrcp14PageActivity2 = Avrcp14PageActivity.this;
            avrcp14PageActivity2.mSelectedPath = avrcp14PageActivity2.mMediaFolders.getPlayerNameByIndex(position);
            Avrcp14PageActivity.this.mSelectedType = 2;
        }
    };
    private Handler mHandler = null;
    private boolean mIsUpDirection = false;
    private ArrayAdapter<String> mMediaArrayAdapter;
    private AdapterView.OnItemClickListener mMediaClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass9 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            String str = Avrcp14PageActivity.TAG;
            Log.e(str, "Piggy Check position : " + position);
            Avrcp14PageActivity avrcp14PageActivity = Avrcp14PageActivity.this;
            avrcp14PageActivity.mSelectedUid = avrcp14PageActivity.mMediaItems.getUidByIndex(position);
            String str2 = Avrcp14PageActivity.TAG;
            Log.e(str2, "Piggy Check mSelectedUid : " + Avrcp14PageActivity.this.mSelectedUid);
            Avrcp14PageActivity.this.mSelectedType = 3;
        }
    };
    private MediaFolders mMediaFolders;
    private MediaItems mMediaItems;
    ListView mMediaListView;
    private MediaPlayers mMediaPlayers;
    ListView mPlayerListView;
    private ArrayAdapter<String> mPlayersArrayAdapter;
    private AdapterView.OnItemClickListener mPlayersClickListener = new AdapterView.OnItemClickListener() {
        /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass7 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
            v.setSelected(true);
            ((TextView) v).setPressed(true);
            String str = Avrcp14PageActivity.TAG;
            Log.e(str, "Piggy Check position : " + position);
            Avrcp14PageActivity avrcp14PageActivity = Avrcp14PageActivity.this;
            avrcp14PageActivity.mSelectedPlayerId = avrcp14PageActivity.mMediaPlayers.getUidByIndex(position);
            String str2 = Avrcp14PageActivity.TAG;
            Log.e(str2, "Piggy Check mSelectedPlayerId : " + Avrcp14PageActivity.this.mSelectedPlayerId);
            Avrcp14PageActivity avrcp14PageActivity2 = Avrcp14PageActivity.this;
            avrcp14PageActivity2.mSelectedPlayer = avrcp14PageActivity2.mMediaPlayers.getNameByIndex(position);
            Avrcp14PageActivity.this.mSelectedType = 1;
        }
    };
    private String mSelectedPath = BuildConfig.FLAVOR;
    private String mSelectedPlayer = BuildConfig.FLAVOR;
    private int mSelectedPlayerId = 0;
    private int mSelectedType = 0;
    private long mSelectedUid = 0;
    String packageName;
    int state_browse;
    private TextView text_path;
    private TextView text_state;
    private TextView title_folders;
    private TextView title_media;
    private TextView title_players;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (D) {
            Log.e(TAG, "+++ ON CREATE +++");
        }
        setContentView(R.layout.avrcp14_page);
        this.mPlayersArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        this.mFoldersArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        this.mMediaArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        this.mMediaPlayers = new MediaPlayers();
        this.mMediaFolders = new MediaFolders();
        this.mMediaItems = new MediaItems();
        initHandler();
        initView();
        Intent intent = new Intent(this, BtService.class);
        startService(intent);
        bindService(intent, this.mConnection, 1);
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
            this.mCommand.unregisterA2dpCallback(this.mA2dpCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            this.mCommand.unregisterAvrcpCallback(this.mAvrcpCallback);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
        unbindService(this.mConnection);
        if (D) {
            Log.e(TAG, "--- ON DESTROY ---");
        }
        super.onDestroy();
    }

    public void initView() {
        this.button_player = (Button) findViewById(R.id.button_avrcp14_player);
        this.text_state = (TextView) findViewById(R.id.text_state_browse);
        this.text_path = (TextView) findViewById(R.id.text_avrcp_browse_path);
        this.button_path_return = (Button) findViewById(R.id.button_avrcp14_path_return);
        this.button_enter = (Button) findViewById(R.id.button_enter);
        this.button_back = (Button) findViewById(R.id.button_back);
        this.mPlayerListView = (ListView) findViewById(R.id.list_avrcp14_players);
        this.mPlayerListView.setAdapter((ListAdapter) this.mPlayersArrayAdapter);
        this.mPlayerListView.setOnItemClickListener(this.mPlayersClickListener);
        this.mFolderListView = (ListView) findViewById(R.id.list_avrcp14_folders);
        this.mFolderListView.setAdapter((ListAdapter) this.mFoldersArrayAdapter);
        this.mFolderListView.setOnItemClickListener(this.mFoldersClickListener);
        this.mMediaListView = (ListView) findViewById(R.id.list_avrcp14_media);
        this.mMediaListView.setAdapter((ListAdapter) this.mMediaArrayAdapter);
        this.mMediaListView.setOnItemClickListener(this.mMediaClickListener);
        this.title_players = (TextView) findViewById(R.id.title_players);
        this.title_folders = (TextView) findViewById(R.id.title_folders);
        this.title_media = (TextView) findViewById(R.id.title_media);
        this.button_player.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass2 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(Avrcp14PageActivity.TAG, "button_player onClicked");
                Avrcp14PageActivity.this.showPlayerList(true);
                String str = Avrcp14PageActivity.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Piggy Check mCommand is null ? ");
                sb.append(Avrcp14PageActivity.this.mCommand);
                Log.e(str, sb.toString() == null ? "YES" : "NO");
                try {
                    Avrcp14PageActivity.this.mCommand.reqAvrcp14GetFolderItems((byte) 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_path_return.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass3 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(Avrcp14PageActivity.TAG, "button_path_return onClicked");
                try {
                    Avrcp14PageActivity.this.mIsUpDirection = true;
                    Avrcp14PageActivity.this.mCommand.reqAvrcp14ChangePath(0, 0, (byte) 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_enter.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass4 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(Avrcp14PageActivity.TAG, "button_enter onClicked");
                Avrcp14PageActivity.this.showPlayerList(false);
                int i = Avrcp14PageActivity.this.mSelectedType;
                if (i == 1) {
                    try {
                        Avrcp14PageActivity.this.mCommand.reqAvrcp14SetBrowsedPlayer(Avrcp14PageActivity.this.mSelectedPlayerId);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else if (i == 2) {
                    try {
                        Avrcp14PageActivity.this.mCommand.reqAvrcp14ChangePath(0, Avrcp14PageActivity.this.mSelectedUid, (byte) 1);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                } else if (i == 3) {
                    try {
                        Avrcp14PageActivity.this.mCommand.reqAvrcp14PlaySelectedItem((byte) 1, 0, Avrcp14PageActivity.this.mSelectedUid);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        });
        this.button_back.setOnClickListener(new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass5 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.v(Avrcp14PageActivity.TAG, "button_back onClicked");
                Avrcp14PageActivity.this.finish();
            }
        });
    }

    private void initHandler() {
        this.mHandler = new Handler() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass6 */

            @Override // android.os.Handler
            public synchronized void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String str = Avrcp14PageActivity.TAG;
                Log.v(str, "handleMessage : " + Avrcp14PageActivity.this.getHandlerEventName(msg.what));
                int i = msg.what;
                if (i == 1) {
                    Avrcp14PageActivity.this.showMediaList(false);
                    Avrcp14PageActivity.this.showFolderList(false);
                    Avrcp14PageActivity.this.populatePlayerList();
                    Avrcp14PageActivity.this.populateFolderList();
                    Avrcp14PageActivity.this.populateMediaList();
                } else if (i == 2) {
                    try {
                        Avrcp14PageActivity.this.mCommand.reqAvrcp14GetFolderItems(bundle.getByte(Avrcp14PageActivity.KEY_SCOPE));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                String str2 = Avrcp14PageActivity.TAG;
                Log.v(str2, "handleMessage : " + Avrcp14PageActivity.this.getHandlerEventName(msg.what) + " finished");
            }
        };
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendHandlerMessage(int what, String key, byte value) {
        String str = TAG;
        Log.d(str, "sendHandlerMessage() what : " + what);
        Message msg = Message.obtain(this.mHandler, what);
        Bundle b = new Bundle();
        Log.d(TAG, "Piggy Check 1");
        b.putByte(key, value);
        Log.d(TAG, "Piggy Check 2");
        msg.setData(b);
        Log.d(TAG, "Piggy Check 3");
        this.mHandler.sendMessage(msg);
        Log.d(TAG, "sendHandlerMessage() finished");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getHandlerEventName(int what) {
        if (what == 1) {
            return "HANDLER_EVENT_UPDATE_LIST";
        }
        if (what != 2) {
            return "Unknown Event !!";
        }
        return "HANDLER_EVENT_UPDATE_FOLDER";
    }

    public abstract class UiRunnable implements Runnable {
        protected String data;

        public UiRunnable(String input) {
            this.data = input;
        }
    }

    public abstract class UiRunnableTwo implements Runnable {
        protected String data1;
        protected String data2;

        public UiRunnableTwo(String input1, String input2) {
            this.data1 = input1;
            this.data2 = input2;
        }
    }

    public abstract class UiRunnableThree implements Runnable {
        protected String data1;
        protected String data2;
        protected String data3;

        public UiRunnableThree(String input1, String input2, String input3) {
            this.data1 = input1;
            this.data2 = input2;
            this.data3 = input3;
        }
    }

    public abstract class UiRunnableArray implements Runnable {
        protected String[] dataes;

        public UiRunnableArray(String[] s) {
            this.dataes = s;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showPlayerList(boolean enable) {
        String str = TAG;
        Log.d(str, "showPlayerList " + enable);
        int i = 0;
        this.title_players.setVisibility(enable ? 0 : 8);
        ListView listView = this.mPlayerListView;
        if (!enable) {
            i = 8;
        }
        listView.setVisibility(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showFolderList(boolean enable) {
        String str = TAG;
        Log.d(str, "showFolderList " + enable);
        int i = 0;
        this.title_folders.setVisibility(enable ? 0 : 8);
        ListView listView = this.mFolderListView;
        if (!enable) {
            i = 8;
        }
        listView.setVisibility(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showMediaList(boolean enable) {
        String str = TAG;
        Log.d(str, "showMediaList " + enable);
        int i = 0;
        this.title_media.setVisibility(enable ? 0 : 8);
        ListView listView = this.mMediaListView;
        if (!enable) {
            i = 8;
        }
        listView.setVisibility(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populatePlayerList() {
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass12 */

            @Override // java.lang.Runnable
            public void run() {
                Log.d(Avrcp14PageActivity.TAG, "Clear MediaPlayerList!");
                Avrcp14PageActivity.this.mPlayersArrayAdapter.clear();
            }
        });
        updateAllPlayers();
        runOnUiThread(new UiRunnableArray(this.mMediaPlayers.getNameArray()) {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass13 */

            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < this.dataes.length; i++) {
                    Avrcp14PageActivity.this.mPlayersArrayAdapter.add(this.dataes[i]);
                }
            }
        });
    }

    private void updateAllPlayers() {
        Log.e(TAG, "getAllPlayers()");
        try {
            this.packageName = getContentResolver().acquireContentProviderClient(NfDef.DEFAULT_AUTHORITIES).getType(Uri.parse("content://nfore.db.provider"));
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        String str = TAG;
        Log.d(str, "Package Name : " + this.packageName);
        try {
            Log.d(TAG, "start createPackageContext");
            Context _context = createPackageContext(this.packageName, 2);
            Log.d(TAG, "start new helper");
            this.dbHelper = new DbHelperAvrcp(_context);
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.rawQuery("select * from MediaPlayerItems", new String[0]);
        this.mMediaPlayers.resetPlayers();
        if (cursor.getCount() > 0) {
            Log.e(TAG, "Piggy Check 4");
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                do {
                    this.mMediaPlayers.addPlayer(cursor.getString(cursor.getColumnIndex("Name")), cursor.getInt(cursor.getColumnIndex("UIDcounter")), cursor.getInt(cursor.getColumnIndex("PlayerId")));
                } while (cursor.moveToNext());
            }
            Log.d(TAG, "Piggy Check cursor.close()");
            cursor.close();
            this.db.close();
            return;
        }
        cursor.close();
        this.db.close();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populateFolderList() {
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass14 */

            @Override // java.lang.Runnable
            public void run() {
                Log.d(Avrcp14PageActivity.TAG, "Clear FolderList!");
                Avrcp14PageActivity.this.mFoldersArrayAdapter.clear();
            }
        });
        updateAllFolders();
        runOnUiThread(new UiRunnableArray(this.mMediaFolders.getNameArray()) {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass15 */

            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < this.dataes.length; i++) {
                    Avrcp14PageActivity.this.mFoldersArrayAdapter.add(this.dataes[i]);
                }
            }
        });
    }

    private void updateAllFolders() {
        Log.e(TAG, "updateAllFolders()");
        try {
            this.packageName = getContentResolver().acquireContentProviderClient(NfDef.DEFAULT_AUTHORITIES).getType(Uri.parse("content://nfore.db.provider"));
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        String str = TAG;
        Log.d(str, "Package Name : " + this.packageName);
        try {
            Log.d(TAG, "start createPackageContext");
            Context _context = createPackageContext(this.packageName, 2);
            Log.d(TAG, "start new helper");
            this.dbHelper = new DbHelperAvrcp(_context);
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.rawQuery("select * from FolderItems", new String[0]);
        this.mMediaFolders.resetFolders();
        if (cursor.getCount() > 0) {
            showFolderList(true);
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                do {
                    this.mMediaFolders.addFolder(cursor.getString(cursor.getColumnIndex("Name")), cursor.getInt(cursor.getColumnIndex("UIDcounter")), cursor.getLong(cursor.getColumnIndex("UID")));
                } while (cursor.moveToNext());
            }
            Log.d(TAG, "Piggy Check cursor.close()");
            cursor.close();
            this.db.close();
            return;
        }
        cursor.close();
        this.db.close();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populateMediaList() {
        runOnUiThread(new Runnable() {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass16 */

            @Override // java.lang.Runnable
            public void run() {
                Log.d(Avrcp14PageActivity.TAG, "Clear MediaList!");
                Avrcp14PageActivity.this.mMediaArrayAdapter.clear();
            }
        });
        updateAllMedia();
        runOnUiThread(new UiRunnableArray(this.mMediaItems.getNameArray()) {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass17 */

            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < this.dataes.length; i++) {
                    Avrcp14PageActivity.this.mMediaArrayAdapter.add(this.dataes[i]);
                }
            }
        });
    }

    private void updateAllMedia() {
        Log.e(TAG, "updateAllMedia()");
        try {
            this.packageName = getContentResolver().acquireContentProviderClient(NfDef.DEFAULT_AUTHORITIES).getType(Uri.parse("content://nfore.db.provider"));
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        String str = TAG;
        Log.d(str, "Package Name : " + this.packageName);
        try {
            Log.d(TAG, "start createPackageContext");
            Context _context = createPackageContext(this.packageName, 2);
            Log.d(TAG, "start new helper");
            this.dbHelper = new DbHelperAvrcp(_context);
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "createPackageContext: \"" + this.packageName + "\", NameNotFoundException");
        }
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.rawQuery("select * from MediaItems", new String[0]);
        this.mMediaItems.resetMediaItems();
        if (cursor.getCount() > 0) {
            showMediaList(true);
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                do {
                    this.mMediaItems.addMediaItem(cursor.getString(cursor.getColumnIndex("Name")), cursor.getInt(cursor.getColumnIndex("UIDcounter")), cursor.getLong(cursor.getColumnIndex("UID")));
                } while (cursor.moveToNext());
            }
            Log.d(TAG, "Piggy Check cursor.close()");
            cursor.close();
            this.db.close();
            return;
        }
        cursor.close();
        this.db.close();
    }

    /* access modifiers changed from: private */
    public class MediaPlayers {
        private ArrayList<Player> mPlayers = new ArrayList<>();

        /* access modifiers changed from: private */
        public class Player {
            String mName;
            int mUid;
            int mUidCounter;

            public Player(String name, int counter, int uid) {
                this.mName = name;
                this.mUidCounter = counter;
                this.mUid = uid;
            }

            public int getUid() {
                return this.mUid;
            }

            public int getUidCounter() {
                return this.mUidCounter;
            }

            public String getName() {
                return this.mName;
            }
        }

        public MediaPlayers() {
        }

        public void resetPlayers() {
            this.mPlayers.clear();
        }

        public void addPlayer(String name, int counter, int uid) {
            this.mPlayers.add(new Player(name, counter, uid));
        }

        public int getUidByIndex(int index) {
            if (this.mPlayers.isEmpty()) {
                return 0;
            }
            return this.mPlayers.get(index).getUid();
        }

        public String getNameByIndex(int index) {
            if (this.mPlayers.isEmpty()) {
                return BuildConfig.FLAVOR;
            }
            return this.mPlayers.get(index).getName();
        }

        public int getUidCounterByIndex(int index) {
            if (this.mPlayers.isEmpty()) {
                return 0;
            }
            return this.mPlayers.get(index).getUidCounter();
        }

        public String[] getNameArray() {
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < this.mPlayers.size(); i++) {
                l.add(this.mPlayers.get(i).getName());
            }
            return (String[]) l.toArray(new String[l.size()]);
        }
    }

    /* access modifiers changed from: private */
    public class MediaFolders {
        private ArrayList<Folder> mFolders = new ArrayList<>();

        /* access modifiers changed from: private */
        public class Folder {
            String mName;
            long mUid;
            int mUidCounter;

            public Folder(String name, int counter, long uid) {
                this.mName = name;
                this.mUidCounter = counter;
                this.mUid = uid;
            }

            public long getUid() {
                return this.mUid;
            }

            public int getUidCounter() {
                return this.mUidCounter;
            }

            public String getName() {
                return this.mName;
            }
        }

        public MediaFolders() {
        }

        public void resetFolders() {
            this.mFolders.clear();
        }

        public void addFolder(String name, int counter, long uid) {
            String str = Avrcp14PageActivity.TAG;
            Log.d(str, "addFolder() name : " + name + " counter : " + counter + " uid : " + uid);
            this.mFolders.add(new Folder(name, counter, uid));
        }

        public long getUidByIndex(int index) {
            if (this.mFolders.isEmpty()) {
                return 0;
            }
            return this.mFolders.get(index).getUid();
        }

        public int getUidCounterByIndex(int index) {
            if (this.mFolders.isEmpty()) {
                return 0;
            }
            return this.mFolders.get(index).getUidCounter();
        }

        public String getPlayerNameByIndex(int index) {
            if (this.mFolders.isEmpty()) {
                return BuildConfig.FLAVOR;
            }
            return this.mFolders.get(index).getName();
        }

        public String[] getNameArray() {
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < this.mFolders.size(); i++) {
                l.add(this.mFolders.get(i).getName());
            }
            return (String[]) l.toArray(new String[l.size()]);
        }
    }

    /* access modifiers changed from: private */
    public class MediaItems {
        private ArrayList<Media> mMediaItems = new ArrayList<>();

        /* access modifiers changed from: private */
        public class Media {
            String mName;
            long mUid;
            int mUidCounter;

            public Media(String name, int counter, long uid) {
                this.mName = name;
                this.mUidCounter = counter;
                this.mUid = uid;
            }

            public long getUid() {
                return this.mUid;
            }

            public int getUidCounter() {
                return this.mUidCounter;
            }

            public String getName() {
                return this.mName;
            }
        }

        public MediaItems() {
        }

        public void resetMediaItems() {
            this.mMediaItems.clear();
        }

        public void addMediaItem(String name, int counter, long uid) {
            this.mMediaItems.add(new Media(name, counter, uid));
        }

        public long getUidByIndex(int index) {
            if (this.mMediaItems.isEmpty()) {
                return 0;
            }
            return this.mMediaItems.get(index).getUid();
        }

        public int getUidCounterByIndex(int index) {
            if (this.mMediaItems.isEmpty()) {
                return 0;
            }
            return this.mMediaItems.get(index).getUidCounter();
        }

        public String[] getNameArray() {
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < this.mMediaItems.size(); i++) {
                l.add(this.mMediaItems.get(i).getName());
            }
            return (String[]) l.toArray(new String[l.size()]);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showToast(String message) {
        String str = TAG;
        Log.e(str, "showToast : " + message);
        runOnUiThread(new UiRunnable(message) {
            /* class com.goodocom.bttek.bt.demo.ui.Avrcp14PageActivity.AnonymousClass18 */

            @Override // java.lang.Runnable
            public void run() {
                if (Avrcp14PageActivity.this.infoToast != null) {
                    Avrcp14PageActivity.this.infoToast.cancel();
                    Avrcp14PageActivity.this.infoToast.setText(this.data);
                    Avrcp14PageActivity.this.infoToast.setDuration(0);
                } else {
                    Avrcp14PageActivity avrcp14PageActivity = Avrcp14PageActivity.this;
                    avrcp14PageActivity.infoToast = Toast.makeText(avrcp14PageActivity.getApplicationContext(), this.data, 0);
                }
                Avrcp14PageActivity.this.infoToast.show();
            }
        });
    }
}
