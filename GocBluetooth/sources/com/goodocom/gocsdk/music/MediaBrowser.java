package com.goodocom.gocsdk.music;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;

public class MediaBrowser {
    android.media.browse.MediaBrowser mDelegate;

    public static abstract class ConnectionCallback extends MediaBrowser.ConnectionCallback {
    }

    public static abstract class ItemCallback extends MediaBrowser.ItemCallback {
    }

    public static abstract class SubscriptionCallback extends MediaBrowser.SubscriptionCallback {
    }

    public MediaBrowser(android.media.browse.MediaBrowser delegate) {
        this.mDelegate = delegate;
    }

    public MediaBrowser(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
        this.mDelegate = new android.media.browse.MediaBrowser(context, serviceComponent, callback, rootHints);
    }

    public void connect() {
        this.mDelegate.connect();
    }

    public void disconnect() {
        this.mDelegate.disconnect();
    }

    public Bundle getExtras() {
        return this.mDelegate.getExtras();
    }

    public void getItem(String mediaId, ItemCallback callback) {
        this.mDelegate.getItem(mediaId, callback);
    }

    public String getRoot() {
        return this.mDelegate.getRoot();
    }

    public ComponentName getServiceComponent() {
        return this.mDelegate.getServiceComponent();
    }

    public MediaSession.Token getSessionToken() {
        return this.mDelegate.getSessionToken();
    }

    public boolean isConnected() {
        return this.mDelegate.isConnected();
    }

    public void subscribe(String parentId, Bundle options, SubscriptionCallback callback) {
        this.mDelegate.subscribe(parentId, options, callback);
    }

    public void subscribe(String parentId, SubscriptionCallback callback) {
        this.mDelegate.subscribe(parentId, callback);
    }

    public void unsubscribe(String parentId) {
        this.mDelegate.unsubscribe(parentId);
    }

    public void unsubscribe(String parentId, SubscriptionCallback callback) {
        this.mDelegate.unsubscribe(parentId, callback);
    }

    public void testInit(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
    }
}
