package com.sillycat.easycastle.encryption;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public abstract class DHCoder extends Coder {

	public static final String ALGORITHM = "DH";

	/**
	 * default key size
	 * <pre>
	 * DH 
	 * Default Keysize 1024   
	 * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
	 * </pre>
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * DH need a symmetry like DES to encrypt the data
	 */
	public static final String SECRET_ALGORITHM = "DES";
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";

	/**
	 * initiate A key pair
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		//a public key
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		//a private key
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * initiate the b key pair
	 * @param key
	 *            a public key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String aPublicKey) throws Exception {
		//convert a public key
		byte[] keyBytes = decryptBASE64(aPublicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// generate b private key from a public key
		DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(keyFactory.getAlgorithm());
		keyPairGenerator.initialize(dhParamSpec);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		//b public key
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		//b private key
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * encryption
	 * 
	 * @param data
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String publicKey,
			String privateKey) throws Exception {
		//generate the local key from public and private keys.
		SecretKey secretKey = getSecretKey(publicKey, privateKey);
		//encrypt the data
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	/**
	 * decryption
	 * @param data
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String publicKey,
			String privateKey) throws Exception {
		// generate the local key from public and private keys
		SecretKey secretKey = getSecretKey(publicKey, privateKey);
		// decrypt the data
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	/**
	 * generate the local key
	 * 
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	private static SecretKey getSecretKey(String publicKey, String privateKey)
			throws Exception {
		//decrypt the public key string
		byte[] pubKeyBytes = decryptBASE64(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		//decrypt the private key string
		byte[] priKeyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
		Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory
				.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		//generate the local key
		SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);
		return secretKey;
	}

	/**
	 * get the private key from key map
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * get the public key from key map
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}
}
