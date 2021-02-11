package com.goodocom.gocsdk.music;

import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import java.util.List;

public class MediaController {
    public android.media.session.MediaController mDelegate;
    public TransportControls mTransportControls;
    public MediaController.TransportControls mTransportDelegate;

    public static abstract class Callback extends MediaController.Callback {
    }

    public MediaController(android.media.session.MediaController delegate) {
        this.mDelegate = delegate;
        this.mTransportDelegate = delegate.getTransportControls();
        this.mTransportControls = new TransportControls();
    }

    public MediaController(Context context, MediaSession.Token token) {
        this.mDelegate = new android.media.session.MediaController(context, token);
        this.mTransportDelegate = this.mDelegate.getTransportControls();
        this.mTransportControls = new TransportControls();
    }

    public android.media.session.MediaController getWrappedInstance() {
        return this.mDelegate;
    }

    public TransportControls getTransportControls() {
        return this.mTransportControls;
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        return this.mDelegate.dispatchMediaButtonEvent(keyEvent);
    }

    public PlaybackState getPlaybackState() {
        return this.mDelegate.getPlaybackState();
    }

    public MediaMetadata getMetadata() {
        return this.mDelegate.getMetadata();
    }

    public List<MediaSession.QueueItem> getQueue() {
        return this.mDelegate.getQueue();
    }

    public CharSequence getQueueTitle() {
        return this.mDelegate.getQueueTitle();
    }

    public Bundle getExtras() {
        return this.mDelegate.getExtras();
    }

    public int getRatingType() {
        return this.mDelegate.getRatingType();
    }

    public long getFlags() {
        return this.mDelegate.getFlags();
    }

    public MediaController.PlaybackInfo getPlaybackInfo() {
        return this.mDelegate.getPlaybackInfo();
    }

    public PendingIntent getSessionActivity() {
        return this.mDelegate.getSessionActivity();
    }

    public MediaSession.Token getSessionToken() {
        return this.mDelegate.getSessionToken();
    }

    public void setVolumeTo(int value, int flags) {
        this.mDelegate.setVolumeTo(value, flags);
    }

    public void adjustVolume(int direction, int flags) {
        this.mDelegate.adjustVolume(direction, flags);
    }

    public void registerCallback(Callback callback) {
        this.mDelegate.registerCallback(callback);
    }

    public void registerCallback(Callback callback, Handler handler) {
        this.mDelegate.registerCallback(callback, handler);
    }

    public void unregisterCallback(Callback callback) {
        this.mDelegate.unregisterCallback(callback);
    }

    public void sendCommand(String command, Bundle args, ResultReceiver cb) {
        this.mDelegate.sendCommand(command, args, cb);
    }

    public String getPackageName() {
        return this.mDelegate.getPackageName();
    }

    public boolean equals(Object o) {
        if (o instanceof android.media.session.MediaController) {
            return this.mDelegate.equals(o);
        }
        if (o instanceof MediaController) {
            return this.mDelegate.equals(((MediaController) o).mDelegate);
        }
        return false;
    }

    public String toString() {
        MediaMetadata data = getMetadata();
        MediaDescription desc = data == null ? null : data.getDescription();
        return "MediaController (" + getPackageName() + "@" + Integer.toHexString(this.mDelegate.hashCode()) + ") " + desc;
    }

    public class TransportControls {
        public TransportControls() {
        }

        public void prepare() {
            MediaController.this.mTransportDelegate.prepare();
        }

        public void prepareFromMediaId(String mediaId, Bundle extras) {
            MediaController.this.mTransportDelegate.prepareFromMediaId(mediaId, extras);
        }

        public void prepareFromSearch(String query, Bundle extras) {
            MediaController.this.mTransportDelegate.prepareFromSearch(query, extras);
        }

        public void prepareFromUri(Uri uri, Bundle extras) {
            MediaController.this.mTransportDelegate.prepareFromUri(uri, extras);
        }

        public void play() {
            MediaController.this.mTransportDelegate.play();
        }

        public void playFromMediaId(String mediaId, Bundle extras) {
            MediaController.this.mTransportDelegate.playFromMediaId(mediaId, extras);
        }

        public void playFromSearch(String query, Bundle extras) {
            MediaController.this.mTransportDelegate.playFromSearch(query, extras);
        }

        public void playFromUri(Uri uri, Bundle extras) {
            MediaController.this.mTransportDelegate.playFromUri(uri, extras);
        }

        public void skipToQueueItem(long id) {
            MediaController.this.mTransportDelegate.skipToQueueItem(id);
        }

        public void pause() {
            MediaController.this.mTransportDelegate.pause();
        }

        public void stop() {
            MediaController.this.mTransportDelegate.stop();
        }

        public void seekTo(long pos) {
            MediaController.this.mTransportDelegate.seekTo(pos);
        }

        public void fastForward() {
            MediaController.this.mTransportDelegate.fastForward();
        }

        public void skipToNext() {
            MediaController.this.mTransportDelegate.skipToNext();
        }

        public void rewind() {
            MediaController.this.mTransportDelegate.rewind();
        }

        public void skipToPrevious() {
            MediaController.this.mTransportDelegate.skipToPrevious();
        }

        public void setRating(Rating rating) {
            MediaController.this.mTransportDelegate.setRating(rating);
        }

        public void sendCustomAction(PlaybackState.CustomAction customAction, Bundle args) {
            MediaController.this.mTransportDelegate.sendCustomAction(customAction, args);
        }

        public void sendCustomAction(String action, Bundle args) {
            MediaController.this.mTransportDelegate.sendCustomAction(action, args);
        }
    }
}
