package com.easybddweb.core.pages;

public interface Mp3Page {

	/**
     * Go to a section on page.
     * 
     * @param section
     *            the section which want to go
     */
    void go(String section);
	
	/**
     * Search things on picture page.
     * 
     * @param key
     *            the search key
     */
    void search(String key);
	
}
