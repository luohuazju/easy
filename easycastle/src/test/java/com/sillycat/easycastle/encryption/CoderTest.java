package com.sillycat.easycastle.encryption;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class CoderTest {

	@Test
	public void test() throws Exception {
		String inputStr = "�򵥼���";
		System.out.println("ԭ��:" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = Coder.encryptBASE64(inputData);
		System.out.println("BASE64���ܺ�:" + code);

		byte[] output = Coder.decryptBASE64(code);
		String outputStr = new String(output);
		System.out.println("BASE64���ܺ�:" + outputStr);

		// ��֤BASE64���ܽ���һ����
		assertEquals(inputStr, outputStr);

		// ��֤MD5����ͬһ���ݼ����Ƿ�һ��
		assertArrayEquals(Coder.encryptMD5(inputData),
				Coder.encryptMD5(inputData));

		// ��֤SHA����ͬһ���ݼ����Ƿ�һ��
		assertArrayEquals(Coder.encryptSHA(inputData),
				Coder.encryptSHA(inputData));

		String key = Coder.initMacKey();
		System.out.println("Mac��Կ:" + key);

		// ��֤HMAC����ͬһ���ݣ�ͬһ��Կ�����Ƿ�һ��
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
