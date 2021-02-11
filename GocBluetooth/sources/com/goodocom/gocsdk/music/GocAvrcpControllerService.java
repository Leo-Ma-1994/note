package com.goodocom.gocsdk.music;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.goodocom.gocsdk.AvrcpInfo;

public class GocAvrcpControllerService extends Service {
    public static final String TAG = GocAvrcpControllerService.class.getSimpleName();
    private AvrcpInfoIml avrcpInfoIml;
    private MusicInfoListener mMusicInfoListener;

    public interface MusicInfoListener {
        void onMusicTimeUpdate(int i, int i2);

        void onMusicinfoUpdate(String str, String str2, String str3);

        void onPlayeStateChange(int i);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.avrcpInfoIml = new AvrcpInfoIml();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.avrcpInfoIml.asBinder();
    }

    public class AvrcpInfoIml extends AvrcpInfo.Stub {
        public AvrcpInfoIml() {
        }

        @Override // com.goodocom.gocsdk.AvrcpInfo
        public void musicTimeUpdate(int pos, int total) throws RemoteException {
            String str = GocAvrcpControllerService.TAG;
            Log.e(str, "musicTimeUpdate: " + pos + "   " + total);
            if (GocAvrcpControllerService.this.mMusicInfoListener != null) {
                GocAvrcpControllerService.this.mMusicInfoListener.onMusicTimeUpdate(pos, total);
            }
        }

        @Override // com.goodocom.gocsdk.AvrcpInfo
        public void musicPlayState(int state) throws RemoteException {
            String str = GocAvrcpControllerService.TAG;
            Log.e(str, "musicPlayState: " + state);
            if (GocAvrcpControllerService.this.mMusicInfoListener != null) {
                GocAvrcpControllerService.this.mMusicInfoListener.onPlayeStateChange(state);
            }
        }

        @Override // com.goodocom.gocsdk.AvrcpInfo
        public void musicMusicInfo(String name, String art, String abm) throws RemoteException {
            String str = GocAvrcpControllerService.TAG;
            Log.e(str, "musicMusicInfo: " + name + "     " + art + "    " + abm);
            if (GocAvrcpControllerService.this.mMusicInfoListener != null) {
                GocAvrcpControllerService.this.mMusicInfoListener.onMusicinfoUpdate(name, art, abm);
            }
        }

        public GocAvrcpControllerService getService() {
            return GocAvrcpControllerService.this;
        }
    }

    public void setMusicInfoListener(MusicInfoListener listener) {
        this.mMusicInfoListener = listener;
    }
}
