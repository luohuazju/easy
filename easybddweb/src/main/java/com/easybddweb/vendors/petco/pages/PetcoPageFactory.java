package com.easybddweb.vendors.petco.pages;

import org.jbehave.web.selenium.WebDriverProvider;

import com.easybddweb.vendors.petco.pages.fluent.HomeFluent;
import com.easybddweb.vendors.petco.pages.fluent.StoreLocatorFluent;
import com.easybddweb.vendors.petco.pages.vanilla.HomeVanilla;

public class PetcoPageFactory {

	private final WebDriverProvider webDriverProvider;
	private final boolean fluentStyle;

	public PetcoPageFactory(WebDriverProvider webDriverProvider) {
		this(webDriverProvider, Boolean.parseBoolean(System.getProperty(
				"FLUENT_STYLE", "true")));
	}

	public PetcoPageFactory(WebDriverProvider webDriverProvider,
			boolean fluentStyle) {
		this.webDriverProvider = webDriverProvider;
		this.fluentStyle = fluentStyle;
	}
	
	public Home newHome() {
        if ( fluentStyle ){
            return new HomeFluent(webDriverProvider);
        }
        return new HomeVanilla(webDriverProvider);
    }
	
	public StoreLocator newStoreLocator(){
		if(fluentStyle){
			return new StoreLocatorFluent(webDriverProvider);
		}
		return null;
	}

}
