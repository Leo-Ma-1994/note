package com.goodocom.gocsdk.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GocThreadPoolFactory {
    private ExecutorService mThreadService;

    private GocThreadPoolFactory() {
        this.mThreadService = null;
        this.mThreadService = Executors.newSingleThreadExecutor();
    }

    /* access modifiers changed from: private */
    public static class GocThreadPoolFactoryHolder {
        private static final GocThreadPoolFactory INSTANCE = new GocThreadPoolFactory();

        private GocThreadPoolFactoryHolder() {
        }
    }

    public static GocThreadPoolFactory getInstance() {
        return GocThreadPoolFactoryHolder.INSTANCE;
    }

    public <T> Future<T> submitRequest(Runnable runnable, T result) {
        return this.mThreadService.submit(runnable, result);
    }

    public void executeRequest(Runnable runnable) {
        this.mThreadService.execute(runnable);
    }
}
