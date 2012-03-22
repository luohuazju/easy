package com.sillycat.easycastle.runner;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DESEncryptAndDecrypt {
	
	private static final String JCE_PROVIDER_BC = "BC";
	
	public static void main(String[] args) {
		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Security.addProvider(new BouncyCastleProvider());
		try {
			// "BC" is the name of the BouncyCastle provider
			KeyGenerator kg = KeyGenerator.getInstance("DES",JCE_PROVIDER_BC);
			
			Key key = kg.generateKey();
			Cipher cipher = Cipher.getInstance("DES",JCE_PROVIDER_BC);

			byte[] data = "Hello World!".getBytes();
			System.out.println("Original data : " + new String(data));

			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(data);
			System.out.println("Encrypted data: " + new String(result));

			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] original = cipher.doFinal(result);
			System.out.println("Decrypted data: " + new String(original));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
