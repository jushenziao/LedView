package com.jushenziao.admin.ledview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LedView led1;
    private LedView led2;
    private LedView led3;
    private LedView led4;
    private LedView led5;
    private LedView led6;
    private LedView led7;
    private LedView led8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        led1.updateState(LedView.STATE_BUSYING);
        led2.updateState(LedView.STATE_NO_REGISTERED);
        led3.updateState(LedView.STATE_RINGING);
        led4.updateState(LedView.STATE_REGISTERED);
        led5.updateState(LedView.STATE_RINGING);
        led6.updateState(LedView.STATE_RINGING);
        led7.updateState(LedView.STATE_RINGING);
        led8.updateState(LedView.STATE_BUSYING);


    }

    private void initView() {
        led1 = (LedView) findViewById(R.id.led1);
        led2 = (LedView) findViewById(R.id.led2);
        led3 = (LedView) findViewById(R.id.led3);
        led4 = (LedView) findViewById(R.id.led4);
        led5 = (LedView) findViewById(R.id.led5);
        led6 = (LedView) findViewById(R.id.led6);
        led7 = (LedView) findViewById(R.id.led7);
        led8 = (LedView) findViewById(R.id.led8);
    }
}
