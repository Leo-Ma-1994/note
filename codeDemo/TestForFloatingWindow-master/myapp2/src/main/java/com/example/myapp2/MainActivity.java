package com.example.myapp2;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onResume() {

        Log.d(TAG, "onResume: ");
        Intent intent = new Intent("LEO");  //Itent就是我们要发送的内容
        intent.putExtra("isShow2", false);
        sendBroadcast(intent);   //发送广播
        super.onResume();
    }

    @Override
    protected void onPause() {
        //Launcher退出时，发送将悬浮窗可见的广播

        Log.d(TAG, "onPause: ");
        Intent intent = new Intent("LEO");  //Itent就是我们要发送的内容
        intent.putExtra("isShow2", true);
        sendBroadcast(intent);   //发送广播
        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
