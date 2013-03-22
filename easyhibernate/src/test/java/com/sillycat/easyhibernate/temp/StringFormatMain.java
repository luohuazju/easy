package com.sillycat.easyhibernate.temp;

public class StringFormatMain {
	
	private final static String mServerURL = "https://api.%sdigby.com%s/api/brands/%s/events";

	public static void main(String[] args) {
		String afterString = String.format(mServerURL, "local.", "", "sillycat");
		System.out.println("Format String=" + afterString);
	}

}
