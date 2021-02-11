package android.arch.lifecycle;

import android.arch.core.util.Function;

public class Transformations {
    private Transformations() {
    }

    public static <X, Y> LiveData<Y> map(LiveData<X> source, final Function<X, Y> func) {
        final MediatorLiveData<Y> result = new MediatorLiveData<>();
        result.addSource(source, new Observer<X>() {
            /* class android.arch.lifecycle.Transformations.AnonymousClass1 */

            @Override // android.arch.lifecycle.Observer
            public void onChanged(X x) {
                result.setValue(func.apply(x));
            }
        });
        return result;
    }

    public static <X, Y> LiveData<Y> switchMap(LiveData<X> trigger, final Function<X, LiveData<Y>> func) {
        final MediatorLiveData<Y> result = new MediatorLiveData<>();
        result.addSource(trigger, new Observer<X>() {
            /* class android.arch.lifecycle.Transformations.AnonymousClass2 */
            LiveData<Y> mSource;

            @Override // android.arch.lifecycle.Observer
            public void onChanged(X x) {
                LiveData<Y> newLiveData = (LiveData) func.apply(x);
                LiveData<Y> liveData = this.mSource;
                if (liveData != newLiveData) {
                    if (liveData != null) {
                        result.removeSource(liveData);
                    }
                    this.mSource = newLiveData;
                    LiveData<Y> liveData2 = this.mSource;
                    if (liveData2 != null) {
                        result.addSource(liveData2, new Observer<Y>() {
                            /* class android.arch.lifecycle.Transformations.AnonymousClass2.AnonymousClass1 */

                            @Override // android.arch.lifecycle.Observer
                            public void onChanged(Y y) {
                                result.setValue(y);
                            }
                        });
                    }
                }
            }
        });
        return result;
    }
}
