package com.sillycat.easycastle.encryption;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public abstract class DSACoder extends Coder {  

	public static final String ALGORITHM = "DSA";  
	  
    /** 
     * default key size
     * <pre> 
     * DSA  
     * Default Keysize 1024   
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive). 
     * </pre> 
     */  
    private static final int KEY_SIZE = 1024;  
  
    /** 
     * default seed 
     */  
    private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";  

    private static final String PUBLIC_KEY = "DSAPublicKey";  
    private static final String PRIVATE_KEY = "DSAPrivateKey";  
  
    /** 
     * use private key to signature the data  
     * @param data 
     * @param privateKey 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String sign(byte[] data, String privateKey) throws Exception {  
    	// decrypt the private key
        byte[] keyBytes = decryptBASE64(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        // KEY_ALGORITHM
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
        // convert to private key object
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
        // signature the data
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());  
        signature.initSign(priKey);  
        signature.update(data);  
        return encryptBASE64(signature.sign());  
    }  
  
    /** 
     * validate the signature
     * @param data 
     * @param publicKey 
     * @param sign 
     * @return true false 
     * @throws Exception 
     */  
    public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
        byte[] keyBytes = decryptBASE64(publicKey);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        // ALGORITHM 
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
        // convert to public key object
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());  
        signature.initVerify(pubKey);  
        signature.update(data);  
        return signature.verify(decryptBASE64(sign));  
    }  
    /** 
     * generate the key pair
     * @param seed 
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey(String seed) throws Exception {  
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);  
        SecureRandom secureRandom = new SecureRandom();  
        secureRandom.setSeed(seed.getBytes());  
        keygen.initialize(KEY_SIZE, secureRandom);  
        KeyPair keys = keygen.genKeyPair();  
        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();  
        DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();  
        Map<String, Object> map = new HashMap<String, Object>(2);  
        map.put(PUBLIC_KEY, publicKey);  
        map.put(PRIVATE_KEY, privateKey);  
        return map;  
    }  
    /** 
     * return key map object
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey() throws Exception {  
        return initKey(DEFAULT_SEED);  
    }  
    /** 
     * return private key  
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
     * return public key 
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
