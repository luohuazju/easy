package com.easybddweb.vendors.baidu;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.selenium.PerStoryWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProvider;

import com.easybddweb.core.pages.HomePage;

public class BaiduSteps extends PerStoryWebDriverSteps {

	private HomePage home;

	public BaiduSteps(WebDriverProvider driverProvider) {
		super(driverProvider);
		BaiduPageFactory pageFactory = new BaiduPageFactory(driverProvider);
		home = pageFactory.newHomePage();
	}
	
	@Given("open the home page <home_link>")
    public void homepage(@Named("home_link")String homeLink)
    {
        home.go(homeLink);
    }
	
	@When("I click the <url_link>")
	public void clickLink(@Named("url_link")String urlLink){
		home.lickLink(urlLink);
	}
	
	@Then("I can see the <pic_link>")
	public void isTherePictureLink(@Named("pic_link")String picLink){
		assertThat(home.foundPictureLink(picLink), Matchers.greaterThan(0));
	}
	
}
