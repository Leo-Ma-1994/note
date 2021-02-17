package com.goodocom.gocsdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class MyEditText extends EditText {
    private onFinishComposingListener mFinishComposingListener;

    public interface onFinishComposingListener {
        void finishComposing();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context) {
        super(context);
    }

    public void setOnFinishComposingListener(onFinishComposingListener FinishComposingListener) {
        this.mFinishComposingListener = FinishComposingListener;
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(super.onCreateInputConnection(outAttrs), false);
    }

    public class MyInputConnection extends InputConnectionWrapper {
        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean finishComposingText() {
            boolean finishComposing = super.finishComposingText();
            if (MyEditText.this.mFinishComposingListener != null) {
                MyEditText.this.mFinishComposingListener.finishComposing();
            }
            return finishComposing;
        }
    }
}
