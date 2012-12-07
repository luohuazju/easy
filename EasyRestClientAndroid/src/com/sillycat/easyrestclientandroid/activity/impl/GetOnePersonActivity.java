package com.sillycat.easyrestclientandroid.activity.impl;

import android.os.Bundle;
import android.widget.Toast;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncListActivity;
import com.sillycat.easyrestclientandroid.dao.rest.GetOnePersonRestDAO;

public class GetOnePersonActivity extends AbstractAsyncListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		// when this activity starts, initiate an asynchronous HTTP GET request
		new GetOnePersonRestDAO(this, getString(R.string.base_uri),R.id.edit_text_id).execute();
	}

	public void showState(Person person) {
		// display a notification to the user with the state
		if (person != null) {
			Toast.makeText(this, "Name: " + person.getPersonName(),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "No person found with that ID!",
					Toast.LENGTH_LONG).show();
		}
	}

}
