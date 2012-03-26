package com.easybddweb.vendors.petco.pages.fluent;

import static org.openqa.selenium.By.linkText;

import org.jbehave.web.selenium.WebDriverProvider;

import com.easybddweb.core.pages.FluentPage;
import com.easybddweb.vendors.petco.pages.Home;


public class HomeFluent extends FluentPage implements Home {

	public HomeFluent(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void go() {
        get("http://rd.digby.com/Petco");
    }
	
	public void goToStoreLocator(){
		link(linkText("Find a Store")).click();
	}

}
