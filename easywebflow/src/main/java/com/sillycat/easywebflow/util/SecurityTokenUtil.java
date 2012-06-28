package com.sillycat.easywebflow.util;

import java.security.SecureRandom;

import org.apache.log4j.Logger;

public class SecurityTokenUtil {

	private static final Logger log = Logger
			.getLogger(SecurityTokenUtil.class);

	private static final SecureRandom sr = new SecureRandom();

	/**
	 * generate random number
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomIntNum(int length) {
		sr.setSeed(sr.nextLong());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(Math.abs(sr.nextInt(10)));
		}
		log.debug("gen randomIntNum=" + sb.toString());
		return sb.toString();
	}

	/**
	 * generate random string
	 * 
	 * @param length
	 * @return
	 */
	public static char[] getRandomCharArray(int length) {
		sr.setSeed(sr.nextLong());
		char[] ca = new char[length];
		for (int i = 0; i < ca.length; i++) {
			ca[i] = (char) (((Math.abs(sr.nextInt())) % 26) + (sr.nextBoolean() ? 65
					: 97));
		}
		return ca;
	}

	/**
	 * get a random String
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String returnstr =  new String(getRandomCharArray(length));
		log.debug("gen randomIntNum=" + returnstr);
		return returnstr;
	}

}
