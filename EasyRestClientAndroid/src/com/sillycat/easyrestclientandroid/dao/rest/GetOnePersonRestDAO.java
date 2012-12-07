package com.sillycat.easyrestclientandroid.dao.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.activity.impl.GetOnePersonActivity;

public class GetOnePersonRestDAO extends AsyncTask<MediaType, Void, Person>{

	protected static final String TAG = GetOnePersonRestDAO.class.getSimpleName();
	
	private GetOnePersonActivity _activity;
	
	private String _id;
	
	private String _baseURI;
	
	private int _textId;
	
	public GetOnePersonRestDAO(GetOnePersonActivity activity, String baseURI, int textId){
		this._activity = activity;
		this._baseURI = baseURI;
		this._textId = textId;
	}

	@Override
	protected void onPreExecute() {
		// before the network request begins, show a progress indicator
		this._activity.showLoadingProgressDialog();

		// retrieve the abbreviation from the EditText field
		EditText editText = (EditText) this._activity.findViewById(this._textId);

		_id = editText.getText().toString();
	}

	@Override
	protected Person doInBackground(MediaType... params) {
		try {
			if (params.length <= 0) {
				return null;
			}

			MediaType mediaType = params[0];

			// The URL for making the GET request
			final String url = _baseURI
					+ "/person/" + _id;

			// Set the Accept header for "application/json" or
			// "application/xml"
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(mediaType);
			requestHeaders.setAccept(acceptableMediaTypes);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			// Perform the HTTP GET request
			Person person = restTemplate.getForObject(url, Person.class);

			// Return the person from the ResponseEntity
			return person;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return null;
	}

	@Override
	protected void onPostExecute(Person person) {
		// hide the progress indicator when the network request is complete
		this._activity.dismissProgressDialog();
		// return the list of states
		this._activity.showState(person);
	}
	
}
