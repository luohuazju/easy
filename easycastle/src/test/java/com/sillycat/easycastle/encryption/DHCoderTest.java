package com.sillycat.easycastle.encryption;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class DHCoderTest {

	@Test
	public void test() throws Exception {
		//generate the public key pair
		Map<String, Object> aKeyMap = DHCoder.initKey();
		String aPublicKey = DHCoder.getPublicKey(aKeyMap);
		String aPrivateKey = DHCoder.getPrivateKey(aKeyMap);

		System.out.println("A Public Key:\r" + aPublicKey);
		System.out.println("A Private Key:\r" + aPrivateKey);

		// B will generate the key pair based on A public key
		Map<String, Object> bKeyMap = DHCoder.initKey(aPublicKey);
		String bPublicKey = DHCoder.getPublicKey(bKeyMap);
		String bPrivateKey = DHCoder.getPrivateKey(bKeyMap);

		System.out.println("B Public Key:\r" + bPublicKey);
		System.out.println("B Private Key:\r" + bPrivateKey);

		System.out.println(" ===============first process================== ");
		String aInput = "original data content";
		System.out.println("data: " + aInput);
		
		// based on a public key, b private key, encryption
		byte[] aCode = DHCoder.encrypt(aInput.getBytes(), aPublicKey,
				bPrivateKey);

		// based on b public key, a private key, decryption
		byte[] aDecode = DHCoder.decrypt(aCode, bPublicKey, aPrivateKey);
		String aOutput = (new String(aDecode));

		System.out.println("decryption 1: " + aOutput);
		assertEquals(aInput, aOutput);
		System.out.println(" ===============sencond process================== ");
		String bInput = "sencond data content";
		System.out.println("data: " + bInput);
		byte[] bCode = DHCoder.encrypt(bInput.getBytes(), bPublicKey,
				aPrivateKey);
		byte[] bDecode = DHCoder.decrypt(bCode, aPublicKey, bPrivateKey);
		String bOutput = (new String(bDecode));
		System.out.println("decryption 2: " + bOutput);
		assertEquals(bInput, bOutput);
	}

}
