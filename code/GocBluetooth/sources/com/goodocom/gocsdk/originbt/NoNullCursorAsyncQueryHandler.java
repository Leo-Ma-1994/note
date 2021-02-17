package com.goodocom.gocsdk.originbt;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class NoNullCursorAsyncQueryHandler extends AsyncQueryHandler {
    private static final AtomicInteger pendingQueryCount = new AtomicInteger();
    private static PendingQueryCountChangedListener pendingQueryCountChangedListener;

    public interface PendingQueryCountChangedListener {
        void onPendingQueryCountChanged();
    }

    /* access modifiers changed from: protected */
    public abstract void onNotNullableQueryComplete(int i, Object obj, Cursor cursor);

    public NoNullCursorAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override // android.content.AsyncQueryHandler
    public void startQuery(int token, Object cookie, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        pendingQueryCount.getAndIncrement();
        PendingQueryCountChangedListener pendingQueryCountChangedListener2 = pendingQueryCountChangedListener;
        if (pendingQueryCountChangedListener2 != null) {
            pendingQueryCountChangedListener2.onPendingQueryCountChanged();
        }
        super.startQuery(token, new CookieWithProjection(cookie, projection), uri, projection, selection, selectionArgs, orderBy);
    }

    /* access modifiers changed from: protected */
    @Override // android.content.AsyncQueryHandler
    public final void onQueryComplete(int token, Object cookie, Cursor cursor) {
        CookieWithProjection projectionCookie = (CookieWithProjection) cookie;
        super.onQueryComplete(token, projectionCookie.originalCookie, cursor);
        if (cursor == null) {
            cursor = new EmptyCursor(projectionCookie.projection);
        }
        onNotNullableQueryComplete(token, projectionCookie.originalCookie, cursor);
        pendingQueryCount.getAndDecrement();
        PendingQueryCountChangedListener pendingQueryCountChangedListener2 = pendingQueryCountChangedListener;
        if (pendingQueryCountChangedListener2 != null) {
            pendingQueryCountChangedListener2.onPendingQueryCountChanged();
        }
    }

    public static void setPendingQueryCountChangedListener(PendingQueryCountChangedListener listener) {
        pendingQueryCountChangedListener = listener;
    }

    public static int getPendingQueryCount() {
        return pendingQueryCount.get();
    }

    private static class CookieWithProjection {
        public final Object originalCookie;
        public final String[] projection;

        public CookieWithProjection(Object cookie, String[] projection2) {
            this.originalCookie = cookie;
            this.projection = projection2;
        }
    }
}
