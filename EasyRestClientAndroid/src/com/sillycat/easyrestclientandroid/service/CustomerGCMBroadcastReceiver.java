package com.sillycat.easyrestclientandroid.service;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

public class CustomerGCMBroadcastReceiver extends GCMBroadcastReceiver {

	protected String getGCMIntentServiceClassName(Context context) {
		return "com.sillycat.easyrestclientandroid.service.CustomerGCMIntentService";
	}

}
