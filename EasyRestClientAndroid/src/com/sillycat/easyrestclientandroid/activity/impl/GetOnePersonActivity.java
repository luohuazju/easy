package com.sillycat.easyrestclientandroid.activity.impl;

import org.springframework.http.MediaType;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncActivity;
import com.sillycat.easyrestclientandroid.dao.PersonDAO;
import com.sillycat.easyrestclientandroid.dao.rest.PersonRESTDAOImpl;

public class GetOnePersonActivity extends AbstractAsyncActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.person_get_item);
		
		// Initiate the request for JSON data when the JSON button is pushed
		final Button buttonJson = (Button) findViewById(R.id.button_json);
		buttonJson.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DownloadStateTask().execute(MediaType.APPLICATION_JSON);
			}
		});
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
	
	
	private class DownloadStateTask extends AsyncTask<MediaType, Void, Person> {
		private String _id;

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();

			// retrieve the abbreviation from the EditText field
			EditText editText = (EditText) findViewById(R.id.edit_text_id);

			_id = editText.getText().toString();
		}

		@Override
		protected Person doInBackground(MediaType... params) {
			try {
				PersonDAO personDAO = new PersonRESTDAOImpl();
				personDAO.setBaseURL(getString(R.string.rest_server_base_uri));
				return personDAO.get(Integer.valueOf(_id));
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Person person) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			// return the list of states
			showState(person);
		}
	}

}
