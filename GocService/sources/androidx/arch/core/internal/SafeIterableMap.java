package androidx.arch.core.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class SafeIterableMap<K, V> implements Iterable<Map.Entry<K, V>> {
    private Entry<K, V> mEnd;
    private WeakHashMap<SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap<>();
    private int mSize = 0;
    Entry<K, V> mStart;

    /* access modifiers changed from: package-private */
    public interface SupportRemove<K, V> {
        void supportRemove(Entry<K, V> entry);
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> get(K k) {
        Entry<K, V> currentNode = this.mStart;
        while (currentNode != null && !currentNode.mKey.equals(k)) {
            currentNode = currentNode.mNext;
        }
        return currentNode;
    }

    public V putIfAbsent(K key, V v) {
        Entry<K, V> entry = get(key);
        if (entry != null) {
            return entry.mValue;
        }
        put(key, v);
        return null;
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> put(K key, V v) {
        Entry<K, V> newEntry = new Entry<>(key, v);
        this.mSize++;
        Entry<K, V> entry = this.mEnd;
        if (entry == null) {
            this.mStart = newEntry;
            this.mEnd = this.mStart;
            return newEntry;
        }
        entry.mNext = newEntry;
        newEntry.mPrevious = entry;
        this.mEnd = newEntry;
        return newEntry;
    }

    public V remove(K key) {
        Entry<K, V> toRemove = get(key);
        if (toRemove == null) {
            return null;
        }
        this.mSize--;
        if (!this.mIterators.isEmpty()) {
            for (SupportRemove<K, V> iter : this.mIterators.keySet()) {
                iter.supportRemove(toRemove);
            }
        }
        if (toRemove.mPrevious != null) {
            toRemove.mPrevious.mNext = toRemove.mNext;
        } else {
            this.mStart = toRemove.mNext;
        }
        if (toRemove.mNext != null) {
            toRemove.mNext.mPrevious = toRemove.mPrevious;
        } else {
            this.mEnd = toRemove.mPrevious;
        }
        toRemove.mNext = null;
        toRemove.mPrevious = null;
        return toRemove.mValue;
    }

    public int size() {
        return this.mSize;
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        ListIterator<K, V> iterator = new AscendingIterator<>(this.mStart, this.mEnd);
        this.mIterators.put(iterator, false);
        return iterator;
    }

    public Iterator<Map.Entry<K, V>> descendingIterator() {
        DescendingIterator<K, V> iterator = new DescendingIterator<>(this.mEnd, this.mStart);
        this.mIterators.put(iterator, false);
        return iterator;
    }

    public SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions() {
        SafeIterableMap<K, V>.IteratorWithAdditions iterator = new IteratorWithAdditions();
        this.mIterators.put(iterator, false);
        return iterator;
    }

    public Map.Entry<K, V> eldest() {
        return this.mStart;
    }

    public Map.Entry<K, V> newest() {
        return this.mEnd;
    }

    @Override // java.lang.Object
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap map = (SafeIterableMap) obj;
        if (size() != map.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> iterator1 = iterator();
        Iterator iterator2 = map.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            Map.Entry<K, V> next1 = iterator1.next();
            Object next2 = iterator2.next();
            if ((next1 == null && next2 != null) || (next1 != null && !next1.equals(next2))) {
                return false;
            }
        }
        if (iterator1.hasNext() || iterator2.hasNext()) {
            return false;
        }
        return true;
    }

    @Override // java.lang.Object
    public int hashCode() {
        int h = 0;
        Iterator<Map.Entry<K, V>> i = iterator();
        while (i.hasNext()) {
            h += i.next().hashCode();
        }
        return h;
    }

    @Override // java.lang.Object
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Iterator<Map.Entry<K, V>> iterator = iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next().toString());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private static abstract class ListIterator<K, V> implements Iterator<Map.Entry<K, V>>, SupportRemove<K, V> {
        Entry<K, V> mExpectedEnd;
        Entry<K, V> mNext;

        /* access modifiers changed from: package-private */
        public abstract Entry<K, V> backward(Entry<K, V> entry);

        /* access modifiers changed from: package-private */
        public abstract Entry<K, V> forward(Entry<K, V> entry);

        ListIterator(Entry<K, V> start, Entry<K, V> expectedEnd) {
            this.mExpectedEnd = expectedEnd;
            this.mNext = start;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mNext != null;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public void supportRemove(Entry<K, V> entry) {
            if (this.mExpectedEnd == entry && entry == this.mNext) {
                this.mNext = null;
                this.mExpectedEnd = null;
            }
            Entry<K, V> entry2 = this.mExpectedEnd;
            if (entry2 == entry) {
                this.mExpectedEnd = backward(entry2);
            }
            if (this.mNext == entry) {
                this.mNext = nextNode();
            }
        }

        private Entry<K, V> nextNode() {
            Entry<K, V> entry = this.mNext;
            Entry<K, V> entry2 = this.mExpectedEnd;
            if (entry == entry2 || entry2 == null) {
                return null;
            }
            return forward(entry);
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            Map.Entry<K, V> result = this.mNext;
            this.mNext = nextNode();
            return result;
        }
    }

    /* access modifiers changed from: package-private */
    public static class AscendingIterator<K, V> extends ListIterator<K, V> {
        AscendingIterator(Entry<K, V> start, Entry<K, V> expectedEnd) {
            super(start, expectedEnd);
        }

        /* access modifiers changed from: package-private */
        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public Entry<K, V> forward(Entry<K, V> entry) {
            return entry.mNext;
        }

        /* access modifiers changed from: package-private */
        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public Entry<K, V> backward(Entry<K, V> entry) {
            return entry.mPrevious;
        }
    }

    private static class DescendingIterator<K, V> extends ListIterator<K, V> {
        DescendingIterator(Entry<K, V> start, Entry<K, V> expectedEnd) {
            super(start, expectedEnd);
        }

        /* access modifiers changed from: package-private */
        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public Entry<K, V> forward(Entry<K, V> entry) {
            return entry.mPrevious;
        }

        /* access modifiers changed from: package-private */
        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public Entry<K, V> backward(Entry<K, V> entry) {
            return entry.mNext;
        }
    }

    /* access modifiers changed from: private */
    public class IteratorWithAdditions implements Iterator<Map.Entry<K, V>>, SupportRemove<K, V> {
        private boolean mBeforeStart = true;
        private Entry<K, V> mCurrent;

        IteratorWithAdditions() {
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public void supportRemove(Entry<K, V> entry) {
            Entry<K, V> entry2 = this.mCurrent;
            if (entry == entry2) {
                this.mCurrent = entry2.mPrevious;
                this.mBeforeStart = this.mCurrent == null;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (!this.mBeforeStart) {
                Entry<K, V> entry = this.mCurrent;
                if (entry == null || entry.mNext == null) {
                    return false;
                }
                return true;
            } else if (SafeIterableMap.this.mStart != null) {
                return true;
            } else {
                return false;
            }
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            if (this.mBeforeStart) {
                this.mBeforeStart = false;
                this.mCurrent = SafeIterableMap.this.mStart;
            } else {
                Entry<K, V> entry = this.mCurrent;
                this.mCurrent = entry != null ? entry.mNext : null;
            }
            return this.mCurrent;
        }
    }

    /* access modifiers changed from: package-private */
    public static class Entry<K, V> implements Map.Entry<K, V> {
        final K mKey;
        Entry<K, V> mNext;
        Entry<K, V> mPrevious;
        final V mValue;

        Entry(K key, V value) {
            this.mKey = key;
            this.mValue = value;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.mKey;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.mValue;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        @Override // java.lang.Object
        public String toString() {
            return ((Object) this.mKey) + "=" + ((Object) this.mValue);
        }

        @Override // java.util.Map.Entry, java.lang.Object
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (!this.mKey.equals(entry.mKey) || !this.mValue.equals(entry.mValue)) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry, java.lang.Object
        public int hashCode() {
            return this.mKey.hashCode() ^ this.mValue.hashCode();
        }
    }
}
