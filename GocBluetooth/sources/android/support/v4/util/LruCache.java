package android.support.v4.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize2) {
        if (maxSize2 > 0) {
            this.maxSize = maxSize2;
            this.map = new LinkedHashMap<>(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public void resize(int maxSize2) {
        if (maxSize2 > 0) {
            synchronized (this) {
                this.maxSize = maxSize2;
            }
            trimToSize(maxSize2);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public final V get(K key) {
        V th;
        V mapValue;
        if (key != null) {
            synchronized (this) {
                try {
                    V mapValue2 = this.map.get(key);
                    if (mapValue2 != null) {
                        try {
                            this.hitCount++;
                            return mapValue2;
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    } else {
                        this.missCount++;
                    }
                } catch (Throwable mapValue3) {
                    th = mapValue3;
                    throw th;
                }
            }
            V createdValue = create(key);
            if (createdValue == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                mapValue = this.map.put(key, createdValue);
                if (mapValue != null) {
                    this.map.put(key, mapValue);
                } else {
                    this.size += safeSizeOf(key, createdValue);
                }
            }
            if (mapValue != null) {
                entryRemoved(false, key, createdValue, mapValue);
                return mapValue;
            }
            trimToSize(this.maxSize);
            return createdValue;
        }
        throw new NullPointerException("key == null");
    }

    public final V put(K key, V value) {
        Throwable th;
        V previous;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            try {
                this.putCount++;
                this.size += safeSizeOf(key, value);
                previous = this.map.put(key, value);
                if (previous != null) {
                    try {
                        this.size -= safeSizeOf(key, previous);
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize);
        return previous;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0074, code lost:
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r7) {
        /*
            r6 = this;
            r0 = 0
            r1 = r0
            r2 = r1
        L_0x0003:
            monitor-enter(r6)
            int r3 = r6.size     // Catch:{ all -> 0x0075 }
            if (r3 < 0) goto L_0x0056
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0075 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0075 }
            if (r3 == 0) goto L_0x0014
            int r3 = r6.size     // Catch:{ all -> 0x0075 }
            if (r3 != 0) goto L_0x0056
        L_0x0014:
            int r3 = r6.size     // Catch:{ all -> 0x0075 }
            if (r3 <= r7) goto L_0x0054
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0075 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0075 }
            if (r3 == 0) goto L_0x0021
            goto L_0x0054
        L_0x0021:
            java.util.LinkedHashMap<K, V> r3 = r6.map     // Catch:{ all -> 0x0075 }
            java.util.Set r3 = r3.entrySet()     // Catch:{ all -> 0x0075 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0075 }
            java.lang.Object r3 = r3.next()     // Catch:{ all -> 0x0075 }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x0075 }
            java.lang.Object r1 = r3.getKey()     // Catch:{ all -> 0x0075 }
            java.lang.Object r2 = r3.getValue()     // Catch:{ all -> 0x0052 }
            java.util.LinkedHashMap<K, V> r4 = r6.map     // Catch:{ all -> 0x0078 }
            r4.remove(r1)     // Catch:{ all -> 0x0078 }
            int r4 = r6.size     // Catch:{ all -> 0x0078 }
            int r5 = r6.safeSizeOf(r1, r2)     // Catch:{ all -> 0x0078 }
            int r4 = r4 - r5
            r6.size = r4     // Catch:{ all -> 0x0078 }
            int r4 = r6.evictionCount     // Catch:{ all -> 0x0078 }
            r5 = 1
            int r4 = r4 + r5
            r6.evictionCount = r4     // Catch:{ all -> 0x0078 }
            monitor-exit(r6)     // Catch:{ all -> 0x0078 }
            r6.entryRemoved(r5, r1, r2, r0)
            goto L_0x0003
        L_0x0052:
            r0 = move-exception
            goto L_0x0076
        L_0x0054:
            monitor-exit(r6)
            return
        L_0x0056:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r6.getClass()
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r4 = ".sizeOf() is reporting inconsistent results!"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x0075:
            r0 = move-exception
        L_0x0076:
            monitor-exit(r6)
            throw r0
        L_0x0078:
            r0 = move-exception
            goto L_0x0076
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.trimToSize(int):void");
    }

    public final V remove(K key) {
        V th;
        V previous;
        if (key != null) {
            synchronized (this) {
                try {
                    previous = this.map.remove(key);
                    if (previous != null) {
                        try {
                            this.size -= safeSizeOf(key, previous);
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    }
                } catch (Throwable previous2) {
                    th = previous2;
                    throw th;
                }
            }
            if (previous != null) {
                entryRemoved(false, key, previous, null);
            }
            return previous;
        }
        throw new NullPointerException("key == null");
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    /* access modifiers changed from: protected */
    public V create(K k) {
        return null;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        throw new IllegalStateException("Negative size: " + ((Object) key) + "=" + ((Object) value));
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K k, V v) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int accesses;
        accesses = this.hitCount + this.missCount;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(accesses != 0 ? (this.hitCount * 100) / accesses : 0));
    }
}
