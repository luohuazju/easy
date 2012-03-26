package com.easybddweb.vendors.petco.steps;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.web.selenium.PerStoryWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriverException;

public class PetcoLifecycleSteps extends PerStoryWebDriverSteps {

	public PetcoLifecycleSteps(WebDriverProvider webDriverProvider) {
		super(webDriverProvider);
	}

	@BeforeScenario
	public void emptyCart() {
		try {
			driverProvider.get().manage().deleteCookieNamed("cart");
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
	}

}
