package com.sillycat.easyrestclientandroid.activity.impl;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncActivity;

public class PickupContactActivity extends AbstractAsyncActivity {

	protected static final String TAG = PickupContactActivity.class
			.getSimpleName();

	private static final int REQUEST_CODE = 10001;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.contact_pickup);

		// Initiate the request for JSON data when the JSON button is pushed
		final Button buttonJson = (Button) findViewById(R.id.pickup_contact);
		buttonJson.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri
						.parse("content://contacts"));
				pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only
																// contacts w/
																// phone numbers
				startActivityForResult(pickContactIntent, REQUEST_CODE);
			}
		});

		final Button buttonContent = (Button) findViewById(R.id.send_content);
		buttonContent.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "Here is an example.");
				sendIntent.setType("text/plain");
				//startActivity(sendIntent);
				startActivity(Intent.createChooser(sendIntent, "Choose an Application"));
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == REQUEST_CODE) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// The user picked a contact.
				// The Intent's data Uri identifies which contact was selected.
				Log.d(TAG, "I get here! I get the callback response.");
				Uri contactUri = data.getData();
				String[] projection = { Phone.NUMBER };
				Cursor cursor = getContentResolver().query(contactUri,
						projection, null, null, null);
				cursor.moveToFirst();
				int column = cursor.getColumnIndex(Phone.NUMBER);
				String number = cursor.getString(column);
				Log.d(TAG, "The number you choose it is = " + number);
				final TextView infoView = (TextView) findViewById(R.id.contact_info);
				infoView.setText("Number You Pickup is " + number);
			}
		}
	}
}
