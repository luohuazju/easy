package com.sillycat.easyandroidgcm.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sillycat.easyandroidgcm.R;
import com.sillycat.easyandroidgcm.common.AllConstant;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Created by carl on 1/3/14.
 */
public class GcmUtil {

    private static final String TAG = "GcmUtil";

    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;

    private static final int MAX_ATTEMPTS = 5;

    private static final int BACKOFF_MILLI_SECONDS = 2000;

    private static final Random random = new Random();

    private Context ctx;

    private SharedPreferences prefs;

    private GoogleCloudMessaging gcm;

    private AsyncTask registrationTask;

    public GcmUtil(Context applicationContext) {
        super();
        ctx = applicationContext;
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        String regid = getRegistrationId();
        String senderId = applicationContext.getString(R.string.gcm_sender);
        if (regid.length() == 0) {
            registerBackground(applicationContext, senderId);
        } else {
            broadcastStatus(true);
        }
        gcm = GoogleCloudMessaging.getInstance(ctx);
    }

    private String getRegistrationId() {
        String registrationId = prefs.getString(AllConstant.PROPERTY_REG_ID, "");
        if (registrationId.length() == 0) {
            Log.d(TAG, "Registration not found.");
            return "";
        }
        // check if app was updated; if so, it must clear registration id to
        // avoid a race condition if GCM sends a message
        int registeredVersion = prefs.getInt(AllConstant.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion || isRegistrationExpired()) {
            Log.d(TAG, "App version changed or registration expired.");
            return "";
        }
        return registrationId;
    }

    private int getAppVersion() {
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void setRegistrationId(String regId) {
        int appVersion = this.getAppVersion();
        Log.d(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AllConstant.PROPERTY_REG_ID, regId);
        editor.putInt(AllConstant.PROPERTY_APP_VERSION, appVersion);
        long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

        Log.d(TAG, "Setting registration expiry time to " + new Date(expirationTime));
        editor.putLong(AllConstant.PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
        editor.commit();
    }

    private boolean isRegistrationExpired() {
        // checks if the information is not stale
        long expirationTime = prefs.getLong(AllConstant.PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
        return System.currentTimeMillis() > expirationTime;
    }

    private void registerBackground(final Context context,String senderId) {

        registrationTask = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                String senderId = params[0];

                long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

                for (int i = 1; i <= MAX_ATTEMPTS; i++) {
                    Log.d(TAG, "Attempt #" + i + " to register with senderId =" + senderId);
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(ctx);
                        }
                        String regid = gcm.register(senderId);

                        ServerUtil.register(context, regid);

                        setRegistrationId(regid);
                        return Boolean.TRUE;

                    } catch (IOException ex) {
                        //Log.e(TAG, "Failed to register on attempt " + i + ":" + ex);
                        if (i == MAX_ATTEMPTS) {
                            break;
                        }
                        try {
                            //Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                            Thread.sleep(backoff);
                        } catch (InterruptedException e1) {
                            // Activity finished before we complete - exit.
                            //Log.d(TAG, "Thread interrupted: abort remaining retries!");
                            Thread.currentThread().interrupt();
                        }
                        // increase backoff exponentially
                        backoff *= 2;
                    }
                }
                return Boolean.FALSE;
            }

            @Override
            protected void onPostExecute(Boolean status) {
                broadcastStatus(status);
            }
        }.execute(senderId, null, null);
    }

    private void broadcastStatus(boolean status) {
        Intent intent = new Intent(AllConstant.ACTION_GCM_MESSAGE_DISPLAY);
        intent.putExtra(AllConstant.MESSAGE_EXTRA, status ? "Success" : "Fail");
        ctx.sendBroadcast(intent);
    }

    public void cleanup() {
        if (registrationTask != null) {
            registrationTask.cancel(true);
        }
        if (gcm != null) {
            gcm.close();
        }
    }

}
