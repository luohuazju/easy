package com.sillycat.easycastle.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DESCoderTest {

	@Test
	public void test() throws Exception {
		String inputStr = "DES";
		String key = DESCoder.initKey();
		System.out.println("original:\t" + inputStr);

		System.out.println("key:\t" + key);
   
		byte[] inputData = inputStr.getBytes();
		inputData = DESCoder.encrypt(inputData, key);
        System.out.println("encryption data:\t" + inputData);
		System.out.println("encryption data(base64+des):\t" + DESCoder.encryptBASE64(inputData));

		byte[] outputData = DESCoder.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.out.println("decryption data:\t" + outputStr);

		assertEquals(inputStr, outputStr);
	}

}
