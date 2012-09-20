package com.sillycat.easyrestclientandroid.activity.impl;

import java.util.List;

import android.os.Bundle;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncListActivity;
import com.sillycat.easyrestclientandroid.adapter.PersonsListAdapter;
import com.sillycat.easyrestclientandroid.dao.rest.GetAllPersonRestDAO;

public class PersonListActivity extends AbstractAsyncListActivity{

protected static final String TAG = PersonListActivity.class.getSimpleName();
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	public void onStart()
	{
		super.onStart();
		// when this activity starts, initiate an asynchronous HTTP GET request
		new GetAllPersonRestDAO(this,getString(R.string.base_uri)).execute();
	}
	
	public void refreshStates(List<Person> persons) 
	{	
		if (persons == null || persons.isEmpty()) 
		{
			return;
		}
		PersonsListAdapter adapter = new PersonsListAdapter(this, persons);
		setListAdapter(adapter);
	}
	
}
