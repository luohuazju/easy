package com.easybddweb.vendors.petco.pages;

public interface StoreLocator {
	
	public boolean hasButton(String item);
	
	public void clickUserLocator();
	
	public boolean hasText(String message);
	
	public void typeZIPCode(String zipCode);
	
	public boolean hasMapButton();

}
