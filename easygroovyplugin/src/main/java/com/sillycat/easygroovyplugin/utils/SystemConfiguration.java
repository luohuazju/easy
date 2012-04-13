package com.sillycat.easygroovyplugin.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class SystemConfiguration {

	private static CompositeConfiguration config;

	private static PropertiesConfiguration propertiesConfig;

	static {
		config = new CompositeConfiguration();
		if (propertiesConfig == null) {
			try {
				propertiesConfig = new PropertiesConfiguration(
						"easygroovy.properties");
				propertiesConfig
						.setReloadingStrategy(new FileChangedReloadingStrategy());
				config.addConfiguration(propertiesConfig);
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	private SystemConfiguration() {
	}

	public static String getString(String propertyKey) {
		return config.getString(propertyKey);
	}

	public static String getString(String propertyKey, String defaultValue) {
		return config.getString(propertyKey, defaultValue);
	}

	public static int getInt(String propertyKey) {
		return config.getInt(propertyKey);
	}

	public static int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	public static float getFloat(String propertyKey) {
		return config.getFloat(propertyKey);
	}

	public static float getFloat(String propertyKey, float defaultValue) {
		return config.getFloat(propertyKey, defaultValue);
	}

	public static boolean getBoolean(String propertyKey) {
		return config.getBoolean(propertyKey);
	}

	public static boolean getBoolean(String propertyKey, boolean defualtValue) {
		return config.getBoolean(propertyKey, defualtValue);
	}

	public static String[] getStringArray(String propertyKey) {
		return config.getStringArray(propertyKey);
	}

	public static List<String> getStringList(String propertyKey) {
		List<String> list = new ArrayList<String>();
		String[] strArr = getStringArray(propertyKey);
		for (String value : strArr) {
			list.add(value);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static List getList(String propertyKey) {
		return config.getList(propertyKey);
	}

}
