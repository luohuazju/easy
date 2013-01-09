
package com.sillycat.easyrestclientandroid;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

	static final String SERVER_URL = "http://4mymessage.appspot.com";

	static final String SENDER_ID = "1044806436104";
	
	static final String TAG = "GCMDemo";

	static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";

	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
