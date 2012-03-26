package com.easybddweb.vendors.petco.pages.vanilla;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

import com.easybddweb.core.pages.VanillaPage;
import com.easybddweb.vendors.petco.pages.Home;

public class HomeVanilla extends VanillaPage implements Home {

	public HomeVanilla(WebDriverProvider webDriverProvider) {
		super(webDriverProvider);
	}

	public void go() {
		get("http://www.etsy.com");
	}
	
	public void goToStoreLocator(){
		findElement(By.linkText("Find a Store")).click();
	}


}
