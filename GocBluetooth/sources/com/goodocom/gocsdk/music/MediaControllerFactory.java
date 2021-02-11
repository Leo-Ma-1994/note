package com.goodocom.gocsdk.music;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

public final class MediaControllerFactory {
    private static MediaController sInjectedController;

    public static MediaController wrap(MediaController delegate) {
        MediaController mediaController = sInjectedController;
        if (mediaController != null) {
            return mediaController;
        }
        if (delegate != null) {
            return new MediaController(delegate);
        }
        return null;
    }

    public static MediaController make(Context context, MediaSession.Token token) {
        MediaController mediaController = sInjectedController;
        if (mediaController != null) {
            return mediaController;
        }
        return new MediaController(context, token);
    }

    static void inject(MediaController controller) {
        sInjectedController = controller;
    }
}
