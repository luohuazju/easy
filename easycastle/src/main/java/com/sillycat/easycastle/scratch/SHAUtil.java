package com.sillycat.easycastle.scratch;

import java.security.MessageDigest;

public class SHAUtil {
	
	// MD5, SHA, SHA1, SHA-1
	private static final String KEY_SHA = "SHA";
	
	/** 
     * SHA encrypt 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public static String encryptSHA(String source) throws Exception {  
    	byte[] data = source.getBytes();
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data);  
        byte[] return_data = sha.digest();  
        return new String(return_data);
    }  

}
