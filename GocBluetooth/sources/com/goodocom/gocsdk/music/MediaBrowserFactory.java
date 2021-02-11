package com.goodocom.gocsdk.music;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import com.goodocom.gocsdk.music.MediaBrowser;

public final class MediaBrowserFactory {
    private static MediaBrowser sInjectedBrowser;

    static MediaBrowser wrap(MediaBrowser delegate) {
        MediaBrowser mediaBrowser = sInjectedBrowser;
        if (mediaBrowser != null) {
            return mediaBrowser;
        }
        if (delegate != null) {
            return new MediaBrowser(delegate);
        }
        return null;
    }

    static MediaBrowser make(Context context, ComponentName serviceComponent, MediaBrowser.ConnectionCallback callback, Bundle rootHints) {
        MediaBrowser mediaBrowser = sInjectedBrowser;
        if (mediaBrowser == null) {
            return new MediaBrowser(context, serviceComponent, callback, rootHints);
        }
        mediaBrowser.testInit(context, serviceComponent, callback, rootHints);
        return sInjectedBrowser;
    }

    static void inject(MediaBrowser browser) {
        sInjectedBrowser = browser;
    }
}
