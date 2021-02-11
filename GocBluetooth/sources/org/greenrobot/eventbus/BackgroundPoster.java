package org.greenrobot.eventbus;

import android.util.Log;

/* access modifiers changed from: package-private */
public final class BackgroundPoster implements Runnable {
    private final EventBus eventBus;
    private volatile boolean executorRunning;
    private final PendingPostQueue queue = new PendingPostQueue();

    BackgroundPoster(EventBus eventBus2) {
        this.eventBus = eventBus2;
    }

    public void enqueue(Subscription subscription, Object event) {
        PendingPost pendingPost = PendingPost.obtainPendingPost(subscription, event);
        synchronized (this) {
            this.queue.enqueue(pendingPost);
            if (!this.executorRunning) {
                this.executorRunning = true;
                this.eventBus.getExecutorService().execute(this);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            boolean z = false;
            try {
                PendingPost pendingPost = this.queue.poll(1000);
                if (pendingPost == null) {
                    synchronized (this) {
                        pendingPost = this.queue.poll();
                        if (pendingPost == null) {
                            this.executorRunning = false;
                            return;
                        }
                    }
                }
                this.eventBus.invokeSubscriber(pendingPost);
            } catch (InterruptedException e) {
                Log.w("Event", Thread.currentThread().getName() + " was interruppted", e);
                return;
            } finally {
                this.executorRunning = z;
            }
        }
    }
}
