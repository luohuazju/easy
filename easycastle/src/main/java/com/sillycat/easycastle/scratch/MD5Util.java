package com.sillycat.easycastle.scratch;

import java.security.MessageDigest;

public class MD5Util {

	// MD5, SHA, SHA1, SHA-1
	private static final String KEY_MD5 = "MD5";

	public static String encryptMD5(String source) throws Exception {
		byte[] data = source.getBytes();
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		byte[] return_data = md5.digest();
		return new String(return_data);
	}

}
