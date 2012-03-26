package com.easybddweb.vendors.baidu.pages.vanilla;

import java.util.List;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.easybddweb.core.pages.HomePage;
import com.easybddweb.core.pages.valilla.VanillaPage;

public class BaiduHomePage extends VanillaPage implements HomePage {

	public BaiduHomePage(WebDriverProvider webDriverProvider) {
		super(webDriverProvider);
	}

	public void go(String url) {
		get(url);
	}

	public void lickLink(String linkSrc) {
		findElement(By.xpath("//a[@href = '" + linkSrc + "']")).click();
	}

	public int foundPictureLink(String picLink) {
		int num = 0;
		List<WebElement> list = findElements(By.xpath("//img[@src = '" + picLink
				+ "']"));
		if (null != list) {
			num = list.size();
		}
		return num;
	}

}
