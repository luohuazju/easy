
package com.sillycat.easyrestclientandroid.constants;

import android.content.Context;
import android.content.Intent;

public final class AllConstants {
	
	//public static final String SERVER_URL = "http://4mymessage.appspot.com";
	
	public static final String SENDER_ID = "1044806436104";

	public static final String TAG = "GCMDemo";

	public static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "message";

	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
