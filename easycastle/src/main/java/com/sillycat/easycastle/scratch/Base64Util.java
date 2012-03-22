package com.sillycat.easycastle.scratch;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	/**
	 * BASE64 decryption
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String decryptBASE64(String source) throws Exception {
		byte[] return_source = (new BASE64Decoder()).decodeBuffer(source);
		return new String(return_source);
	}

	/**
	 * BASE64 encryption
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(String source) throws Exception {
		byte[] key = source.getBytes();
		return (new BASE64Encoder()).encodeBuffer(key);
	}

}
