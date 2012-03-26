package com.easybddweb.vendors.petco.steps;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.easybddweb.vendors.petco.pages.Home;
import com.easybddweb.vendors.petco.pages.PetcoPageFactory;
import com.easybddweb.vendors.petco.pages.StoreLocator;

public class PetcoSteps {

	private Home home;

	private StoreLocator storeLocator;

	public PetcoSteps(PetcoPageFactory pageFactory) {
		home = pageFactory.newHome();
		storeLocator = pageFactory.newStoreLocator();
	}

	@Given("open home page")
	public void openHomePage() {
		home.go();
	}

	@When("click the store locator link")
	public void clickTheStoreLocatorLink() {
		home.goToStoreLocator();
	}

	@Then("get the store locator page button id is $buttonId")
	public void getTheStoreLocatorPage(String buttonId) {
		MatcherAssert.assertThat(storeLocator.hasButton(buttonId),
				Matchers.is(true));
	}

	@When("click the button user location")
	public void clickTheButtonUserLocation() {
		storeLocator.clickUserLocator();
	}

	@Then("get the google map, the text is $message")
	public void getTheGoogleMap(String message) {
		MatcherAssert.assertThat(storeLocator.hasText(message),
				Matchers.is(true));
	}

	@When("type in the $zipCode ZIP code")
	public void typeTheZipCode(String zipCode) {
		storeLocator.typeZIPCode(zipCode);
	}

	@Then("get some stores, see the map button")
	public void getMapButton() {
		MatcherAssert
				.assertThat(storeLocator.hasMapButton(), Matchers.is(true));
	}

}
