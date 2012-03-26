package com.easybddweb.vendors.baidu;

import org.jbehave.web.selenium.WebDriverProvider;

import com.easybddweb.core.pages.HomePage;
import com.easybddweb.vendors.baidu.pages.vanilla.BaiduHomePage;


public class BaiduPageFactory {

	private final WebDriverProvider webDriverProvider;

    public BaiduPageFactory(WebDriverProvider webDriverProvider)
    {
        this.webDriverProvider = webDriverProvider;
    }

    public HomePage newHomePage()
    {
        return new BaiduHomePage(webDriverProvider);
    }

}
