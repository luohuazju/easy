package com.sillycat.easycastle.encryption;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class DHCoderTest {

	@Test
	public void test() throws Exception {
		// 生成甲方密钥对儿
		Map<String, Object> aKeyMap = DHCoder.initKey();
		String aPublicKey = DHCoder.getPublicKey(aKeyMap);
		String aPrivateKey = DHCoder.getPrivateKey(aKeyMap);

		System.out.println("甲方公钥:\r" + aPublicKey);
		System.out.println("甲方私钥:\r" + aPrivateKey);

		// 由甲方公钥产生本地密钥对儿
		Map<String, Object> bKeyMap = DHCoder.initKey(aPublicKey);
		String bPublicKey = DHCoder.getPublicKey(bKeyMap);
		String bPrivateKey = DHCoder.getPrivateKey(bKeyMap);

		System.out.println("乙方公钥:\r" + bPublicKey);
		System.out.println("乙方私钥:\r" + bPrivateKey);

		String aInput = "abc ";
		System.out.println("原文: " + aInput);

		// 由甲方公钥，乙方私钥构建密文
		byte[] aCode = DHCoder.encrypt(aInput.getBytes(), aPublicKey,
				bPrivateKey);

		// 由乙方公钥，甲方私钥解密
		byte[] aDecode = DHCoder.decrypt(aCode, bPublicKey, aPrivateKey);
		String aOutput = (new String(aDecode));

		System.out.println("解密: " + aOutput);

		assertEquals(aInput, aOutput);

		System.out.println(" ===============反过来加密解密================== ");
		String bInput = "def ";
		System.out.println("原文: " + bInput);

		// 由乙方公钥，甲方私钥构建密文
		byte[] bCode = DHCoder.encrypt(bInput.getBytes(), bPublicKey,
				aPrivateKey);

		// 由甲方公钥，乙方私钥解密
		byte[] bDecode = DHCoder.decrypt(bCode, aPublicKey, bPrivateKey);
		String bOutput = (new String(bDecode));

		System.out.println("解密: " + bOutput);

		assertEquals(bInput, bOutput);
	}

}
