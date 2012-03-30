package com.sillycat.easycastle.encryption;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class DSACoderTest {

	@Test
	public void test() throws Exception {
		String inputStr = "hello world";
		byte[] data = inputStr.getBytes();

		// init the key pair
		Map<String, Object> keyMap = DSACoder.initKey();

		// get the public and private key
		String publicKey = DSACoder.getPublicKey(keyMap);
		String privateKey = DSACoder.getPrivateKey(keyMap);

		System.out.println("public key:\r" + publicKey);
		System.out.println("private key:\r" + privateKey);

		// generate the signature
		String sign = DSACoder.sign(data, privateKey);
		System.out.println("signature:\r" + sign);

		// validate the signature
		boolean status = DSACoder.verify(data, publicKey, sign);
		System.out.println("status:\r" + status);
		assertTrue(status);
	}

}
