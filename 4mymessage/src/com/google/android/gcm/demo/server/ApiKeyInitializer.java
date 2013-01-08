
package com.google.android.gcm.demo.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApiKeyInitializer implements ServletContextListener {

  static final String ATTRIBUTE_ACCESS_KEY = "AIzaSyD3bB3-EMz6mZ3_5JTlzZ0XT6uVymo1iCQ";

  private static final String ENTITY_KIND = "Settings";
  private static final String ENTITY_KEY = "MyKey";
  private static final String ACCESS_KEY_FIELD = "ApiKey";

  private final Logger logger = Logger.getLogger(getClass().getName());

  public void contextInitialized(ServletContextEvent event) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key key = KeyFactory.createKey(ENTITY_KIND, ENTITY_KEY);

    Entity entity;
    try {
      entity = datastore.get(key);
    } catch (EntityNotFoundException e) {
      entity = new Entity(key);
      // NOTE: it's not possible to change entities in the local server, so
      // it will be necessary to hardcode the API key below if you are running
      // it locally.
      entity.setProperty(ACCESS_KEY_FIELD,
          "replace_this_text_by_your_Simple_API_Access_key");
      datastore.put(entity);
      logger.severe("Created fake key. Please go to App Engine admin "
          + "console, change its value to your API Key (the entity "
          + "type is '" + ENTITY_KIND + "' and its field to be changed is '"
          + ACCESS_KEY_FIELD + "'), then restart the server!");
    }
    String accessKey = (String) entity.getProperty(ACCESS_KEY_FIELD);
    event.getServletContext().setAttribute(ATTRIBUTE_ACCESS_KEY, accessKey);
  }

  public void contextDestroyed(ServletContextEvent event) {
  }

}
