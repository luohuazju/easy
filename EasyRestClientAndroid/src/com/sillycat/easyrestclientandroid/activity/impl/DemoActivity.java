package com.sillycat.easyrestclientandroid.activity.impl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.R.id;
import com.sillycat.easyrestclientandroid.R.layout;
import com.sillycat.easyrestclientandroid.R.menu;
import com.sillycat.easyrestclientandroid.R.string;
import com.sillycat.easyrestclientandroid.constants.AllConstants;
import com.sillycat.easyrestclientandroid.dao.ServerUtilities;

public class DemoActivity extends Activity {

	TextView mDisplay;

	AsyncTask<Void, Void, Void> mRegisterTask; // Download Task

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// check the configuration
		String senderId = getString(R.string.gcm_sender);
		String gcmServerBaseUri = getString(R.string.gcm_server_base_uri);
		checkNotNull(gcmServerBaseUri, "SERVER_URL");
		checkNotNull(senderId, "SENDER_ID");

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set
		// comment out this line
		// while developing the app,
		// uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		setContentView(R.layout.gcm_client);
		mDisplay = (TextView) findViewById(R.id.display);

		// define the message receiver
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				AllConstants.DISPLAY_MESSAGE_ACTION));

		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(this, senderId);
		} else {
			// Device is already registered on GCM, check server.
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				mDisplay.append(getString(R.string.already_registered) + "\n");
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;

				mRegisterTask = new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						boolean registered = ServerUtilities.register(context,
								regId);
						// At this point all attempts to register with the app
						// server failed, so we need to unregister the device
						// from GCM - the app will try to register again when
						// it is restarted. Note that GCM will send an
						// unregistered callback upon completion, but
						// GCMIntentService.onUnregistered() will ignore it.
						if (!registered) {
							GCMRegistrar.unregister(context);
						}
						return null;
					}

					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * Typically, an application registers automatically, so options below
		 * are disabled. Uncomment them if you want to manually register or
		 * unregister the device (you will also need to uncomment the equivalent
		 * options on options_menu.xml).
		 */
		/*
		 * case R.id.options_register: GCMRegistrar.register(this, SENDER_ID);
		 * return true; case R.id.options_unregister:
		 * GCMRegistrar.unregister(this); return true;
		 */
		case R.id.options_clear:
			mDisplay.setText(null);
			return true;
		case R.id.options_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		unregisterReceiver(mHandleMessageReceiver);
		try {
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {

		}
		super.onDestroy();
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config,
					name));
		}
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(
					AllConstants.EXTRA_MESSAGE);
			mDisplay.append(newMessage + "\n");
		}
	};

}