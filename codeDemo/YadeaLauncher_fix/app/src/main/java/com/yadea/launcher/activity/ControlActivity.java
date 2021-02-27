package com.yadea.launcher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.yadea.launcher.R;

public class ControlActivity extends Activity {
    private static final String TAG = "ControlActivity";
    private Button mBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);

        mBtn = findViewById(R.id.btn_control_test);
        mBtn.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        Log.e(TAG, "onClick: "+"hahah");
                                    }
                                }
        );

    }
}
