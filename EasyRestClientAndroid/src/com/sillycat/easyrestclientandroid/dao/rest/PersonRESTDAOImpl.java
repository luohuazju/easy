package com.sillycat.easyrestclientandroid.dao.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.dao.PersonDAO;

public class PersonRESTDAOImpl implements PersonDAO{
	
	protected static final String TAG = PersonRESTDAOImpl.class.getSimpleName();

	private String baseURL = null;
	
	
	public Person get(Integer id){
		Person item = null;
		final String url = baseURL + "/person/" + id;

		// Set the Accept header for "application/json" or
		// "application/xml"
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.TEXT_PLAIN);
		requestHeaders.setAccept(acceptableMediaTypes);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		// Perform the HTTP GET request
		item = restTemplate.getForObject(url, Person.class);
		
		return item;
	}
	
	public List<Person> all(){
		List<Person> items = null;
		final String url = baseURL + "/person/persons";
		
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
		items =  Arrays.asList(persons);
		
		return items;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	
	
	
}
