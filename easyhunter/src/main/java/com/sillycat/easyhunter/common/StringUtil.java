package com.sillycat.easyhunter.common;

public class StringUtil {

	public static boolean isBlank(String str) {
		if (str == null || str.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
}
