package com.sillycat.easywebflow.core;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

public class GroovyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private Resource[] locations;

	protected void loadProperties(Properties props) throws IOException {
		ConfigObject configObject = new ConfigObject();
		ConfigSlurper configSlurper = new ConfigSlurper();
		for (Resource location : locations) {
			configObject.merge(configSlurper.parse(location.getURL()));
		}
		props.putAll(configObject.toProperties());
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public void setLocation(Resource location) {
		this.locations = new Resource[] { location };
	}

}
