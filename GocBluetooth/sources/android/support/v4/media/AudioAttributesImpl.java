package android.support.v4.media;

import android.os.Bundle;
import androidx.versionedparcelable.VersionedParcelable;

/* access modifiers changed from: package-private */
public interface AudioAttributesImpl extends VersionedParcelable {
    Object getAudioAttributes();

    int getContentType();

    int getFlags();

    int getLegacyStreamType();

    int getRawLegacyStreamType();

    int getUsage();

    int getVolumeControlStream();

    Bundle toBundle();
}
