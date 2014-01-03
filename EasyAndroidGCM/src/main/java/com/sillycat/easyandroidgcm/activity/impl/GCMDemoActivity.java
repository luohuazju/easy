package com.sillycat.easyandroidgcm.activity.impl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sillycat.easyandroidgcm.R;
import com.sillycat.easyandroidgcm.common.AllConstant;
import com.sillycat.easyandroidgcm.util.GcmUtil;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by carl on 1/3/14.
 */
public class GCMDemoActivity extends Activity {

    static final String TAG = GCMDemoActivity.class.getSimpleName();

    TextView mDisplay;

    Context context;

    private GcmUtil gcmUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gcm_client);
        mDisplay = (TextView) findViewById(R.id.display);

        context = getApplicationContext();

        registerReceiver(mHandleMessageReceiver, new IntentFilter(AllConstant.ACTION_GCM_MESSAGE_DISPLAY));

        gcmUtil = new GcmUtil(getApplicationContext());
    }



    @Override
    protected void onDestroy() {
        unregisterReceiver(mHandleMessageReceiver);
        gcmUtil.cleanup();
        super.onDestroy();
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(
                    AllConstant.MESSAGE_EXTRA);
            mDisplay.append(newMessage + "\n");
        }
    };

}
