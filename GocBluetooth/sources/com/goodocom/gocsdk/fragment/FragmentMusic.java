package com.goodocom.gocsdk.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.goodocom.bttek.bt.base.jar.GocJar;
import com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.Util.ImageUtils;
import com.goodocom.gocsdk.Util.Utils;
import com.goodocom.gocsdk.activity.MainActivity;
import com.goodocom.gocsdk.domain.MusicCoverInfo;
import com.goodocom.gocsdk.music.GocAvrcpControllerService;
import com.goodocom.gocsdk.originbt.OriginBluetoothService;
import java.text.SimpleDateFormat;
import org.greenrobot.eventbus.EventBus;

public class FragmentMusic extends BaseFragment implements View.OnClickListener, OriginBluetoothService.A2dpMusicInfoListener {
    public static final String NEXT = "goc_next";
    public static final String PAUSE = "goc_pause";
    public static final String PLAY = "goc_play";
    public static final String PRE = "goc_pre";
    private static final String TAG = FragmentMusic.class.getSimpleName();
    private static Handler hand = null;
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private MainActivity activity;
    private AudioManager audioManager;
    private MusicCoverInfo coverInfo;
    private boolean isA2dpConnected = false;
    private boolean isA2dpPlaying;
    private boolean isSuccess = false;
    private ImageView iv_music_cover;
    private ImageView iv_play_pause;
    private GocAvrcpControllerService.MusicInfoListener listener = new GocAvrcpControllerService.MusicInfoListener() {
        /* class com.goodocom.gocsdk.fragment.FragmentMusic.AnonymousClass2 */

        @Override // com.goodocom.gocsdk.music.GocAvrcpControllerService.MusicInfoListener
        public void onMusicinfoUpdate(String name, String art, String ablum) {
            String str = FragmentMusic.TAG;
            Log.e(str, "name : " + name + "  art " + art);
            if (!TextUtils.isEmpty(name)) {
                FragmentMusic.this.tv_music_name.setText(name);
            }
            if (!TextUtils.isEmpty(art)) {
                FragmentMusic.this.tv_music_artist.setText(art);
            }
        }

        @Override // com.goodocom.gocsdk.music.GocAvrcpControllerService.MusicInfoListener
        public void onMusicTimeUpdate(int current, int total) {
            String stotal = Utils.generateTime((long) total);
            String scurrent = Utils.generateTime((long) current);
            FragmentMusic.this.tv_totaltime.setText(stotal);
            FragmentMusic.this.tv_currenttime.setText(scurrent);
            float pressent = (((float) current) / ((float) total)) * 100.0f;
            String str = FragmentMusic.TAG;
            Log.e(str, "stotal : " + stotal + "  scurrent " + scurrent + " pressent : " + pressent);
            FragmentMusic.this.sb_progress.setProgress((int) pressent);
        }

        @Override // com.goodocom.gocsdk.music.GocAvrcpControllerService.MusicInfoListener
        public void onPlayeStateChange(int state) {
        }
    };
    private Button mButtonLocal;
    private Button mButtonRemote;
    private GocAvrcpControllerService mGocAvrcpControllerService;
    private PlaybackState mPlaybackState;
    private int mState = 0;
    private String music_cover_local = ImageUtils.PATH;
    private String music_cover_name_map;
    private String music_id;
    private String music_indec_type = "";
    private String music_type;
    private SeekBar sb_progress;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
    private TextView tv_currenttime;
    private TextView tv_music_artist;
    private TextView tv_music_name;
    private TextView tv_music_posandtotal;
    private TextView tv_totaltime;

    public void onMusicPlayChange(int state) {
        this.mState = state;
        if (state == 0) {
            this.isA2dpPlaying = false;
            this.iv_play_pause.setImageResource(R.drawable.ic_media_play);
        } else if (state == 1) {
            this.isA2dpPlaying = true;
            this.iv_play_pause.setImageResource(R.drawable.ic_media_pause);
        }
    }

    @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.A2dpMusicInfoListener
    public void onMediaMetadataUpdate(MediaMetadata mediaMetadata) {
        String artist = mediaMetadata.getText(MediaMetadataCompat.METADATA_KEY_ARTIST).toString();
        String title = mediaMetadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE).toString();
        if (!TextUtils.isEmpty(artist)) {
            this.tv_music_artist.setText(artist);
        }
        if (!TextUtils.isEmpty(title)) {
            this.tv_music_name.setText(title);
        }
    }

    @Override // com.goodocom.gocsdk.originbt.OriginBluetoothService.A2dpMusicInfoListener
    public void onPlaybackStateUpdate(PlaybackState playbackState) {
        this.mPlaybackState = playbackState;
        Log.e("apps", "onPlaybackStateUpdate>>>>>" + playbackState.getState());
        if (playbackState.getState() == 3) {
            this.isA2dpPlaying = true;
            this.iv_play_pause.setImageResource(R.drawable.ic_media_pause);
            return;
        }
        this.isA2dpPlaying = false;
        this.iv_play_pause.setImageResource(R.drawable.ic_media_play);
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onConnected(BluetoothDevice device) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.activity, new BluetoothProfile.ServiceListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentMusic.AnonymousClass1 */

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.e("avrcp", "onServiceConnected : ");
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
            }
        }, 12);
        Log.e("connect", "FragmentMusic: onConnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment
    public void onDisconnected() {
        Log.e("connect", "FragmentMusic: onDisconnected");
    }

    @Override // com.goodocom.gocsdk.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
        registMusicinfo();
    }

    public static Handler getHandler() {
        return hand;
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(this.activity, R.layout.fragmentmusic, null);
        initView(view);
        this.audioManager = (AudioManager) this.activity.getSystemService("audio");
        return view;
    }

    private void initView(View view) {
        this.iv_play_pause = (ImageView) view.findViewById(R.id.iv_pause);
        this.iv_music_cover = (ImageView) view.findViewById(R.id.iv_music_cover);
        this.mButtonRemote = (Button) view.findViewById(R.id.swith_device_remote);
        this.mButtonLocal = (Button) view.findViewById(R.id.swith_device_local);
        view.findViewById(R.id.iv_previous).setOnClickListener(this);
        view.findViewById(R.id.iv_next).setOnClickListener(this);
        view.findViewById(R.id.iv_vol_up).setOnClickListener(this);
        view.findViewById(R.id.iv_vol_down).setOnClickListener(this);
        this.tv_music_name = (TextView) view.findViewById(R.id.tv_music_name);
        this.tv_music_artist = (TextView) view.findViewById(R.id.tv_music_artist);
        this.tv_totaltime = (TextView) view.findViewById(R.id.tv_totaltime);
        this.tv_music_posandtotal = (TextView) view.findViewById(R.id.tv_music_posandtotal);
        this.sb_progress = (SeekBar) view.findViewById(R.id.sb_progress);
        this.tv_currenttime = (TextView) view.findViewById(R.id.tv_currenttime);
        this.iv_play_pause.setOnClickListener(this);
        this.mButtonLocal.setOnClickListener(this);
        this.mButtonRemote.setOnClickListener(this);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next /* 2131165367 */:
                GocJar.playNext();
                return;
            case R.id.iv_pause /* 2131165372 */:
                try {
                    if (GocJar.getPlayStatus() == 1) {
                        GocJar.requestAvrcpPause();
                        return;
                    } else {
                        GocJar.requestAvrcpPlay();
                        return;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.iv_previous /* 2131165376 */:
                GocJar.playPrev();
                return;
            case R.id.swith_device_remote /* 2131165508 */:
            default:
                return;
        }
    }

    private boolean isBluetoothHeadsetConnected() {
        if (2 == BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(1)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateID3(final String title, final String art) {
        hanlder.post(new Runnable() {
            /* class com.goodocom.gocsdk.fragment.FragmentMusic.AnonymousClass3 */

            @Override // java.lang.Runnable
            public void run() {
                if (!TextUtils.isEmpty(title)) {
                    FragmentMusic.this.tv_music_name.setText(title);
                }
                if (!TextUtils.isEmpty(art)) {
                    FragmentMusic.this.tv_music_artist.setText(art);
                }
            }
        });
    }

    public void registMusicinfo() {
        GocJar.registerBluetoothMusicChangeListener(new GocBluetoothMusicChangeListener() {
            /* class com.goodocom.gocsdk.fragment.FragmentMusic.AnonymousClass4 */

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13CapabilitiesSupportEvent(byte[] bytes) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13PlayerSettingAttributesList(byte[] bytes) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13PlayerSettingValuesList(byte b, byte[] bytes) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13PlayerSettingCurrentValues(byte[] bytes, byte[] bytes1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13SetPlayerSettingValueSuccess() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13ElementAttributesPlaying(int[] ints, String[] strings) {
                if (strings.length >= 2) {
                    String title = strings[0];
                    String art = strings[1];
                    String str = FragmentMusic.TAG;
                    Log.e(str, "id3" + title + "   " + art);
                    FragmentMusic.this.updateID3(title, art);
                }
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp13PlayStatus(long l, long l1, byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13RegisterEventWatcherSuccess(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13RegisterEventWatcherFail(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventPlaybackStatusChanged(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventTrackChanged(long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventTrackReachedEnd() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventTrackReachedStart() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventPlaybackPosChanged(long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventBatteryStatusChanged(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventSystemStatusChanged(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp13EventPlayerSettingChanged(byte[] bytes, byte[] bytes1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp14EventNowPlayingContentChanged() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp14EventAvailablePlayerChanged() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp14EventAddressedPlayerChanged(int i, int i1) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp14EventUidsChanged(int i) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcp14EventVolumeChanged(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14SetAddressedPlayerSuccess() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14SetBrowsedPlayerSuccess(String[] strings, int i, long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14FolderItems(int i, long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14MediaItems(int i, long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14ChangePathSuccess(long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14ItemAttributes(int[] ints, String[] strings) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14PlaySelectedItemSuccess() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14SearchResult(int i, long l) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14AddToNowPlayingSuccess() {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcp14SetAbsoluteVolumeSuccess(byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void onAvrcpErrorResponse(int i, int i1, byte b) {
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcpUpdateSongStatus(String s, String s1, String s2) {
                String str = FragmentMusic.TAG;
                Log.e(str, "retAvrcpUpdateSongStatus : " + s + "  s1 " + s1 + "  s2 " + s2);
            }

            @Override // com.goodocom.bttek.bt.base.listener.GocBluetoothMusicChangeListener
            public void retAvrcpPlayModeChanged(String s, int i) {
            }
        });
    }
}
