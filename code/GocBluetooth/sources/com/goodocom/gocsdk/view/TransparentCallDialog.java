package com.goodocom.gocsdk.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.goodocom.gocsdk.R;

public class TransparentCallDialog extends AlertDialog {
    private View view;

    public TransparentCallDialog(Context context, int theme) {
        super(context, theme);
        this.view = View.inflate(context, R.layout.dialog_call, null);
    }

    /* access modifiers changed from: protected */
    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.view);
    }

    @Override // android.app.Dialog
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    public View getCustomView() {
        return this.view;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
    }
}
