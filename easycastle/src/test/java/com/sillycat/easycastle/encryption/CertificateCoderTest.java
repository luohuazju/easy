package com.sillycat.easycastle.encryption;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CertificateCoderTest {

	private String password = "123456";
	private String alias = "www.sillycat.com";
	private String certificatePath = "/Users/karl/work/easy/easycastle/src/main/resources/sillycat.cer";
	private String keyStorePath = "/Users/karl/work/easy/easycastle/src/main/resources/sillycat.keystore";

	@Test
	public void testPublic2Private() throws Exception {
		System.out.println("\npublic key encrypt——private key decrypt\n");
		String inputStr = "A new world will come at the end.";
		byte[] data = inputStr.getBytes();
		byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
				certificatePath);
		byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
				keyStorePath, alias, password);
		String outputStr = new String(decrypt);
		String encryptStr = new String(encrypt);
		System.out.println("data: " + inputStr);
		System.out.println("decryption: " + outputStr);
		System.out.println("encryption: " + encryptStr);
		assertArrayEquals(data, decrypt);
		// verify the cer file
		assertTrue(CertificateCoder.verifyCertificate(certificatePath));
	}

	@Test
	public void testPrivate2Public() throws Exception {
		System.out.println("\nprivate encryption——public decryption\n");
		String inputStr = "what is the status?";
		byte[] data = inputStr.getBytes();
		byte[] encodedData = CertificateCoder.encryptByPrivateKey(data,
				keyStorePath, alias, password);
		byte[] decodedData = CertificateCoder.decryptByPublicKey(encodedData,
				certificatePath);
		String outputStr = new String(decodedData);
		String encryptStr = new String(encodedData);
		System.out.println("data: " + inputStr);
		System.out.println("decryption: " + outputStr);
		System.out.println("encryption: " + encryptStr);
		assertEquals(inputStr, outputStr);
	}

	@Test
	public void testSign() throws Exception {
		System.out.println("\nprivate sign——public verify signature\n");
		String data = "It is rainy out side.";
		// generate the sign
		String sign = CertificateCoder.sign(data.getBytes(), keyStorePath, alias,
				password);
		System.out.println("signature:\r" + sign);
		// verification
		boolean status = CertificateCoder.verify(data.getBytes(), sign,
				certificatePath);
		System.out.println("status:\r" + status);
		assertTrue(status);
	}

}
