package com.sillycat.easycastle.encryption;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RSACoderTest {

	private String publicKey;
	private String privateKey;

	@Before
	public void setUp() throws Exception {
		Map<String, Object> keyMap = RSACoder.initKey();
		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		System.out.println("public key : \n\r" + publicKey);
		System.out.println("private key: \n\r" + privateKey);
	}

	@Test
	public void test() throws Exception {
		System.out.println("public key encryption——private key decryption");
		String inputStr = "hello, sillycat.";
		byte[] data = inputStr.getBytes();
		byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

		byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
				privateKey);

		String outputStr = new String(decodedData);
		System.out.println("original: " + inputStr + "\n\r" + "decryption: " + outputStr);
		assertEquals(inputStr, outputStr);
	}

	@Test
	public void testSign() throws Exception {
		System.out.println("private encryption——public decryption");
		String inputStr = "hello, kiko.";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

		byte[] decodedData = RSACoder
				.decryptByPublicKey(encodedData, publicKey);

		String outputStr = new String(decodedData);
		System.out.println("original: " + inputStr + "\n\r" + "decryption: " + outputStr);
		assertEquals(inputStr, outputStr);

		System.out.println("private signature——public validate the signature");
		// generate signature
		String sign = RSACoder.sign(encodedData, privateKey);
		System.out.println("singature string :\r" + sign);

		// validate signature string
		boolean status = RSACoder.verify(encodedData, publicKey, sign);
		System.out.println("status:\r" + status);
		assertTrue(status);

	}

}
