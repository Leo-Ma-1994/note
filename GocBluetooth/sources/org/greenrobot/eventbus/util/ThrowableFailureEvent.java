package org.greenrobot.eventbus.util;

public class ThrowableFailureEvent implements HasExecutionScope {
    private Object executionContext;
    protected final boolean suppressErrorUi;
    protected final Throwable throwable;

    public ThrowableFailureEvent(Throwable throwable2) {
        this.throwable = throwable2;
        this.suppressErrorUi = false;
    }

    public ThrowableFailureEvent(Throwable throwable2, boolean suppressErrorUi2) {
        this.throwable = throwable2;
        this.suppressErrorUi = suppressErrorUi2;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public boolean isSuppressErrorUi() {
        return this.suppressErrorUi;
    }

    @Override // org.greenrobot.eventbus.util.HasExecutionScope
    public Object getExecutionScope() {
        return this.executionContext;
    }

    @Override // org.greenrobot.eventbus.util.HasExecutionScope
    public void setExecutionScope(Object executionContext2) {
        this.executionContext = executionContext2;
    }
}
