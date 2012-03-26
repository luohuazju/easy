package com.easybddweb.core.pages.valilla;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;

public class VanillaPage extends WebDriverPage {

	public VanillaPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}