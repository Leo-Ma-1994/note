package com.goodocom.gocsdk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.goodocom.gocsdk.R;

public class TransparentDialog extends Dialog {
    private Context mContext;

    public TransparentDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public TransparentDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public TransparentDialog(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_connect_device);
        Animation rotateAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.rotate);
        rotateAnim.setInterpolator(new LinearInterpolator());
        ((ImageView) findViewById(R.id.dialog_iv_anim)).startAnimation(rotateAnim);
    }
}
