package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewModel {
    private final Map<String, Object> mBagOfTags = new HashMap();
    private volatile boolean mCleared = false;

    /* access modifiers changed from: protected */
    public void onCleared() {
    }

    /* access modifiers changed from: package-private */
    public final void clear() {
        this.mCleared = true;
        Map<String, Object> map = this.mBagOfTags;
        if (map != null) {
            synchronized (map) {
                for (Object value : this.mBagOfTags.values()) {
                    closeWithRuntimeException(value);
                }
            }
        }
        onCleared();
    }

    /* access modifiers changed from: package-private */
    public <T> T setTagIfAbsent(String key, T newValue) {
        Object obj;
        synchronized (this.mBagOfTags) {
            obj = this.mBagOfTags.get(key);
            if (obj == null) {
                this.mBagOfTags.put(key, newValue);
            }
        }
        T result = obj == null ? newValue : obj;
        if (this.mCleared) {
            closeWithRuntimeException(result);
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public <T> T getTag(String key) {
        T t;
        synchronized (this.mBagOfTags) {
            t = (T) this.mBagOfTags.get(key);
        }
        return t;
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
