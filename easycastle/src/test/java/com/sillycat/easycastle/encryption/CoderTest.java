package com.sillycat.easycastle.encryption;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class CoderTest {

	@Test
	public void test() throws Exception {
		String inputStr = "Just a Test";
		System.out.println("Original:" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = Coder.encryptBASE64(inputData);
		System.out.println("BASE64 encryption:" + code);

		byte[] output = Coder.decryptBASE64(code);
		String outputStr = new String(output);
		System.out.println("BASE64 decryption:" + outputStr);

		// assert the base64 original message and decryption message
		assertEquals(inputStr, outputStr);

		// assert that md5 encrypt the same string to the same encryption string
		assertArrayEquals(Coder.encryptMD5(inputData),
				Coder.encryptMD5(inputData));

		// assert that sha encrypt the same string to the same encryption string
		assertArrayEquals(Coder.encryptSHA(inputData),
				Coder.encryptSHA(inputData));

		String key = Coder.initMacKey();
		System.out.println("Mac Key:" + key);

		// assert that hmac encrypt the same string with same keys to the same encryption string
		assertArrayEquals(Coder.encryptHMAC(inputData, key),
				Coder.encryptHMAC(inputData, key));

		BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));
		System.out.println("MD5:" + md5.toString(16));

		BigInteger sha = new BigInteger(Coder.encryptSHA(inputData));
		System.out.println("SHA:" + sha.toString(32));

		BigInteger mac = new BigInteger(Coder.encryptHMAC(inputData, inputStr));
		System.out.println("HMAC:" + mac.toString(16));
	}

}
