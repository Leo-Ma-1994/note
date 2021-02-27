package com.huaqin.leddemo;


import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.ServiceManager;
import android.app.LEDManager;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private LEDManager ledManager;
    private Button mBtn_red;
    private Button mBtn_green;
    private Button mBtn_blue;
    private Button mBtn_led1;
    private Button mBtn_led2;
    private Button mBtn_led3;
    private Button mBtn_led4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LEDManager ledManager = (LEDManager)getSystemService(Context.LED_SERVICE);
        mBtn_red = findViewById(R.id.btn_red);
        mBtn_green = findViewById(R.id.btn_green);
        mBtn_blue = findViewById(R.id.btn_blue);
        mBtn_led1 = findViewById(R.id.btn_led1);
        mBtn_led2 = findViewById(R.id.btn_led2);
        mBtn_led3 = findViewById(R.id.btn_led3);
        mBtn_led4 = findViewById(R.id.btn_led4);
        
        
        mBtn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    ledManager.ledctrl(i, 1, 0 ,0);
                }

            }
        });

        mBtn_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    ledManager.ledctrl(i, 0 ,1,0);
                }
            }
        });

        mBtn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    ledManager.ledctrl(i, 0 ,0,1);
                }
            }
        });
        mBtn_led1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;

                        try {
                            while(count < 3){
                                if(count ==0){
                                    Log.e(TAG, "led1: "+"红灯亮" );
                                    ledManager.ledctrl(0,1,0,0);
                                }else if(count == 1){
                                    Log.e(TAG, "led1: "+"绿灯亮" );
                                    ledManager.ledctrl(0,0,1,0);
                                }else if(count == 2){
                                    Log.e(TAG, "led1: "+"蓝灯亮" );
                                    ledManager.ledctrl(0,0,0,1);
                                }
                                Thread.sleep(1000);
                                count++;
                            }
                            ledManager.ledctrl(1,0,0, 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        mBtn_led2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;

                        try {
                            while(count < 3){
                                if(count ==0){
                                    Log.e(TAG, "led2: "+"红灯亮" );
                                    ledManager.ledctrl(1,1,0,0);
                                }else if(count == 1){
                                    Log.e(TAG, "led2: "+"绿灯亮" );
                                    ledManager.ledctrl(1,0,1,0);
                                }else if(count == 2){
                                    Log.e(TAG, "led2: "+"蓝灯亮" );
                                    ledManager.ledctrl(1,0,0,1);
                                }
                                Thread.sleep(1000);
                                count++;
                            }
                            ledManager.ledctrl(1,0,0, 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        mBtn_led3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;

                        try {
                            while(count < 3){
                                if(count ==0){
                                    Log.e(TAG, "led3: "+"红灯亮" );
                                    ledManager.ledctrl(2,1,0,0);
                                }else if(count == 1){
                                    Log.e(TAG, "led3: "+"绿灯亮" );
                                    ledManager.ledctrl(2,0,1,0);
                                }else if(count == 2){
                                    Log.e(TAG, "led3: "+"蓝灯亮" );
                                    ledManager.ledctrl(2,0,0,1);
                                }
                                Thread.sleep(1000);
                                count++;
                            }
                            ledManager.ledctrl(2,0,0, 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        mBtn_led4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;

                        try {
                            while(count < 3){
                                if(count ==0){
                                    Log.e(TAG, "led4: "+"红灯亮" );
                                    ledManager.ledctrl(3,1,0,0);
                                }else if(count == 1){
                                    Log.e(TAG, "led4: "+"绿灯亮" );
                                    ledManager.ledctrl(3,0,1,0);
                                }else if(count == 2){
                                    Log.e(TAG, "led4: "+"蓝灯亮" );
                                    ledManager.ledctrl(3,0,0,1);
                                }
                                Thread.sleep(1000);
                                count++;
                            }
                            ledManager.ledctrl(3,0,0, 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });


        
    }
}