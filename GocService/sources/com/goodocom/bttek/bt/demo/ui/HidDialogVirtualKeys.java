package com.goodocom.bttek.bt.demo.ui;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.alibaba.fastjson.asm.Opcodes;
import com.goodocom.bttek.R;
import com.goodocom.bttek.bt.aidl.UiCommand;
import com.goodocom.bttek.bt.res.NfDef;

public class HidDialogVirtualKeys extends Dialog {
    private final int BASIC_BTN_ID = 10000;
    String TAG = "VirtualKeys";
    private UiCommand mCommand;
    private MyKeys[] mKeys;
    View.OnClickListener mOnClick;

    /* access modifiers changed from: package-private */
    public class MyKeys {
        int key_code;
        String name;

        MyKeys(String n, int c) {
            this.name = n;
            this.key_code = c;
        }
    }

    public HidDialogVirtualKeys(Context context, UiCommand command) {
        super(context);
        this.mKeys = new MyKeys[]{new MyKeys("Power", 48), new MyKeys("Power Sleep", 50), new MyKeys("Menu", 64), new MyKeys("Menu Up", 66), new MyKeys("Menu Down", 67), new MyKeys("Escap", 70), new MyKeys("Media Select WWW", NfDef.GATT_STATUS_MORE), new MyKeys("Media Telephone", 410), new MyKeys("Play", Opcodes.ARETURN), new MyKeys("Scan Previous Track", Opcodes.INVOKEVIRTUAL), new MyKeys("Scan Next Track", Opcodes.PUTFIELD), new MyKeys("Mute", 226), new MyKeys("Volume Up", 233), new MyKeys("Volumn down", 234), new MyKeys("AL email", 394), new MyKeys("AL Internet", 406), new MyKeys("Screen Saver", 414), new MyKeys("AC Home", 547), new MyKeys("(Back)", 548)};
        this.mOnClick = new View.OnClickListener() {
            /* class com.goodocom.bttek.bt.demo.ui.HidDialogVirtualKeys.AnonymousClass1 */

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                int i = v.getId() - 10000;
                if (i < HidDialogVirtualKeys.this.mKeys.length) {
                    try {
                        if (!HidDialogVirtualKeys.this.mCommand.reqSendHidVirtualKeyCommand(HidDialogVirtualKeys.this.mKeys[i].key_code, 0)) {
                            Log.e(HidDialogVirtualKeys.this.TAG, "reqSendHidVirtualKeyCommand failed");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!HidDialogVirtualKeys.this.mCommand.reqSendHidVirtualKeyCommand(0, 0)) {
                            Log.e(HidDialogVirtualKeys.this.TAG, "reqSendHidVirtualKeyCommand failed");
                        }
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        setContentView(R.layout.activity_virtual_keys);
        setTitle("Virtual Keys Simulation");
        this.mCommand = command;
        LinearLayout ll = (LinearLayout) findViewById(R.id.btnList);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(-1, -2);
        int i = 0;
        MyKeys[] myKeysArr = this.mKeys;
        for (MyKeys key : myKeysArr) {
            Button btn = new Button(getContext());
            btn.setText(key.name);
            btn.setId(i + 10000);
            btn.setOnClickListener(this.mOnClick);
            ll.addView(btn, lp);
            i++;
        }
    }
}
