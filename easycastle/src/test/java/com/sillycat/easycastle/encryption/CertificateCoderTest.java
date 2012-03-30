package com.sillycat.easycastle.encryption;

import static org.junit.Assert.*;

import org.junit.Test;

public class CertificateCoderTest {

	private String password = "123456";
	private String alias = "www.sillycat.com";
	private String certificatePath = "/Users/karl/work/easy/easycastle/src/main/resources/sillycat.cer";
	private String keyStorePath = "/Users/karl/work/easy/easycastle/src/main/resources/sillycat.keystore";

	@Test
	public void test() throws Exception {
		System.out.println("公钥加密——私钥解密");
		String inputStr = "Ceritifcate";
		byte[] data = inputStr.getBytes();

		byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
				certificatePath);

		byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
				keyStorePath, alias, password);
		String outputStr = new String(decrypt);

		System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

		// 验证数据一致
		assertArrayEquals(data, decrypt);

		// 验证证书有效
		assertTrue(CertificateCoder.verifyCertificate(certificatePath));

	}

	@Test
	public void testSign() throws Exception {
		System.out.println("私钥加密——公钥解密");

		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = CertificateCoder.encryptByPrivateKey(data,
				keyStorePath, alias, password);

		byte[] decodedData = CertificateCoder.decryptByPublicKey(encodedData,
				certificatePath);

		String outputStr = new String(decodedData);
		System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
		assertEquals(inputStr, outputStr);

		System.out.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = CertificateCoder.sign(encodedData, keyStorePath, alias,
				password);
		System.out.println("签名:\r" + sign);

		// 验证签名
		boolean status = CertificateCoder.verify(encodedData, sign,
				certificatePath);
		System.out.println("状态:\r" + status);
		assertTrue(status);

	}

}
