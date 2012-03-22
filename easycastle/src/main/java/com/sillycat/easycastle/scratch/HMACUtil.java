package com.sillycat.easycastle.scratch;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class HMACUtil {

	//HMACSHA1,HmacMD5
	private final static String KEY_MAC = "HMACSHA1";

	/**
	 * HMAC key
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return Base64Util.encryptBASE64(new String(secretKey.getEncoded()));
	}

	/**
	 * HMAC encrypt
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptHMAC(String source, String key) throws Exception {
		byte[] data = source.getBytes();
		SecretKey secretKey = new SecretKeySpec(Base64Util.decryptBASE64(key)
				.getBytes(), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		byte[] return_data = mac.doFinal(data);
		return new String(return_data);
	}

}
