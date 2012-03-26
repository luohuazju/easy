package com.easybddweb.vendors.petco.pages.fluent;

import static org.openqa.selenium.By.id;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

import com.easybddweb.core.pages.FluentPage;
import com.easybddweb.vendors.petco.pages.StoreLocator;

public class StoreLocatorFluent extends FluentPage implements StoreLocator {

	public StoreLocatorFluent(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void clickUserLocator() {
		input(id("uselocation")).click();
	}

	public boolean hasButton(String buttonId) {
		By by = id(buttonId);
		if (null != by) {
			return true;
		}
		return false;
	}

	public boolean hasText(String message) {
		By by = By.linkText(message);
		if (null != by) {
			return true;
		}
		return false;
	}
	
	public void typeZIPCode(String zipCode){
		input(By.name("zipcode")).sendKeys(zipCode);
		input(By.className("buttonstyle2")).submit();
	}
	
	public boolean hasMapButton(){
		By by = By.name("map");
		if(null != by){
			return true;
		}
		return false;
	}

}
