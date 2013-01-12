package com.sillycat.easyrestclientandroid.activity.impl;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncListActivity;
import com.sillycat.easyrestclientandroid.adapter.PersonsListAdapter;
import com.sillycat.easyrestclientandroid.dao.PersonDAO;
import com.sillycat.easyrestclientandroid.dao.rest.PersonRESTDAOImpl;

public class PersonListActivity extends AbstractAsyncListActivity {

	protected static final String TAG = PersonListActivity.class
			.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		new DownloadStatesTask().execute();
	}

	public void refreshStates(List<Person> persons) {
		if (persons == null || persons.isEmpty()) {
			return;
		}
		PersonsListAdapter adapter = new PersonsListAdapter(this, persons);
		setListAdapter(adapter);
	}

	private class DownloadStatesTask extends
			AsyncTask<Void, Void, List<Person>> {
		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected List<Person> doInBackground(Void... params) {
			try {
				PersonDAO personDAO = new PersonRESTDAOImpl();
				personDAO.setBaseURL(getString(R.string.rest_server_base_uri));
				return personDAO.all();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Person> result) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();

			// return the list of states
			refreshStates(result);
		}
	}

}
