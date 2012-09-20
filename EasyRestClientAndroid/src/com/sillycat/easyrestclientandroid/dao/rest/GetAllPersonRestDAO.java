package com.sillycat.easyrestclientandroid.dao.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.util.Log;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.activity.impl.PersonListActivity;

public class GetAllPersonRestDAO extends AsyncTask<Void, Void, List<Person>> {
	
	protected static final String TAG = GetAllPersonRestDAO.class.getSimpleName();

	private PersonListActivity _activity;
	
	private String _baseURI;
	
	public GetAllPersonRestDAO(PersonListActivity activity, String baseURI){
		this._activity = activity;
		this._baseURI = baseURI;
	}
	
	@Override
	protected void onPreExecute() 
	{
		// before the network request begins, show a progress indicator
		this._activity.showLoadingProgressDialog();
	}
	
	@Override
	protected List<Person> doInBackground(Void... parameters) {
		try 
		{
			// The URL for making the GET request
			final String url = this._baseURI + "/person/persons";
			
			// Set the Accept header for "application/json"
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);
			
			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			// Perform the HTTP GET request
			
			Person[] persons = restTemplate.getForObject(url, Person[].class);
			
			// convert the array to a list and return it
			return Arrays.asList(persons);
		} 
		catch(Exception e) 
		{
			Log.e(TAG, e.getMessage(), e);
		} 
		
		return null;
	}
	
	protected void onPostExecute(List<Person> result) 
	{
		// hide the progress indicator when the network request is complete
		this._activity.dismissProgressDialog();
		// return the list of states
		this._activity.refreshStates(result);
	}
	
	

}
