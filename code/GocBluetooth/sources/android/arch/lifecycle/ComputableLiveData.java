package android.arch.lifecycle;

import android.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ComputableLiveData<T> {
    private AtomicBoolean mComputing;
    private final Executor mExecutor;
    private AtomicBoolean mInvalid;
    final Runnable mInvalidationRunnable;
    private final LiveData<T> mLiveData;
    final Runnable mRefreshRunnable;

    /* access modifiers changed from: protected */
    public abstract T compute();

    public ComputableLiveData() {
        this(ArchTaskExecutor.getIOThreadExecutor());
    }

    public ComputableLiveData(Executor executor) {
        this.mInvalid = new AtomicBoolean(true);
        this.mComputing = new AtomicBoolean(false);
        this.mRefreshRunnable = new Runnable() {
            /* class android.arch.lifecycle.ComputableLiveData.AnonymousClass2 */

            /* JADX DEBUG: Multi-variable search result rejected for r4v4, resolved type: java.lang.Object */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                do {
                    boolean computed = false;
                    boolean z = false;
                    if (ComputableLiveData.this.mComputing.compareAndSet(false, true)) {
                        T value = null;
                        while (ComputableLiveData.this.mInvalid.compareAndSet(true, false)) {
                            try {
                                computed = true;
                                value = ComputableLiveData.this.compute();
                            } finally {
                                ComputableLiveData.this.mComputing.set(z);
                            }
                        }
                        if (computed) {
                            ComputableLiveData.this.mLiveData.postValue(value);
                        }
                    }
                    if (!computed) {
                        return;
                    }
                } while (ComputableLiveData.this.mInvalid.get());
            }
        };
        this.mInvalidationRunnable = new Runnable() {
            /* class android.arch.lifecycle.ComputableLiveData.AnonymousClass3 */

            @Override // java.lang.Runnable
            public void run() {
                boolean isActive = ComputableLiveData.this.mLiveData.hasActiveObservers();
                if (ComputableLiveData.this.mInvalid.compareAndSet(false, true) && isActive) {
                    ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
                }
            }
        };
        this.mExecutor = executor;
        this.mLiveData = new LiveData<T>() {
            /* class android.arch.lifecycle.ComputableLiveData.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // android.arch.lifecycle.LiveData
            public void onActive() {
                ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
            }
        };
    }

    public LiveData<T> getLiveData() {
        return this.mLiveData;
    }

    public void invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(this.mInvalidationRunnable);
    }
}
