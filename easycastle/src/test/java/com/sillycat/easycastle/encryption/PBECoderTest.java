package com.sillycat.easycastle.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PBECoderTest {

	@Test
	public void test() throws Exception {
		String inputStr = "abcdefghijklmn";
		System.out.println("original: " + inputStr);
		byte[] input = inputStr.getBytes();

		String pwd = "password_hello";
		System.out.println("password: " + pwd);

		byte[] salt = PBECoder.initSalt();

		byte[] data = PBECoder.encrypt(input, pwd, salt);

		System.out.println("encryption: " + PBECoder.encryptBASE64(data));

		byte[] output = PBECoder.decrypt(data, pwd, salt);
		String outputStr = new String(output);

		System.out.println("decryption: " + outputStr);
		assertEquals(inputStr, outputStr);
	}

}
