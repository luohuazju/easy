package com.sillycat.easycastle.encryption;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.Cipher;

public abstract class CertificateCoder extends Coder {

	/**
	 * (Java Key Store，JKS)KEY_STORE
	 */
	public static final String KEY_STORE = "JKS";

	public static final String X509 = "X.509";

	/**
	 * get the private key from keystore
	 * 
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String keyStorePath, String alias,
			String password) throws Exception {
		KeyStore ks = getKeyStore(keyStorePath, password);
		PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
		return key;
	}

	/**
	 * get the public key from certificate
	 * 
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String certificatePath)
			throws Exception {
		Certificate certificate = getCertificate(certificatePath);
		PublicKey key = certificate.getPublicKey();
		return key;
	}

	private static Certificate getCertificate(String certificatePath)
			throws Exception {
		FileInputStream in = null;
		Certificate certificate = null;
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance(X509);
		in = new FileInputStream(certificatePath);
		certificate = certificateFactory.generateCertificate(in);
		in.close();
		return certificate;
	}

	private static Certificate getCertificate(String keyStorePath,
			String alias, String password) throws Exception {
		KeyStore ks = getKeyStore(keyStorePath, password);
		Certificate certificate = ks.getCertificate(alias);
		return certificate;
	}

	private static KeyStore getKeyStore(String keyStorePath, String password)
			throws Exception {
		FileInputStream is = new FileInputStream(keyStorePath);
		KeyStore ks = KeyStore.getInstance(KEY_STORE);
		ks.load(is, password.toCharArray());
		is.close();
		return ks;
	}

	/**
	 * encrypt the data using private key
	 * 
	 * @param data
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath,
			String alias, String password) throws Exception {
		// get private key
		PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
		// encrypt the data
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * decrypt the data using private key
	 * 
	 * @param data
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String keyStorePath,
			String alias, String password) throws Exception {
		// get the private key
		PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
		// decrypt the data
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * encrypt data based on public key
	 * @param data
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String certificatePath)
			throws Exception {
		// get the public key
		PublicKey publicKey = getPublicKey(certificatePath);
		// encrypt the data
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * decrypt data based on public key
	 * @param data
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String certificatePath)
			throws Exception {
		// get the public key
		PublicKey publicKey = getPublicKey(certificatePath);
		// decrypt data
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * verify Certificate
	 * @param certificatePath
	 * @return
	 */
	public static boolean verifyCertificate(String certificatePath) {
		return verifyCertificate(new Date(), certificatePath);
	}

	/**
	 * verify Certificate valid
	 * @param date
	 * @param certificatePath
	 * @return
	 */
	public static boolean verifyCertificate(Date date, String certificatePath) {
		boolean status = true;
		try {
			// get the certificate
			Certificate certificate = getCertificate(certificatePath);
			status = verifyCertificate(date, certificate);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	private static boolean verifyCertificate(Date date, Certificate certificate) {
		boolean status = true;
		try {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			x509Certificate.checkValidity(date);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	/**
	 * signature
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] sign, String keyStorePath, String alias,
			String password) throws Exception {
		X509Certificate x509Certificate = (X509Certificate) getCertificate(
				keyStorePath, alias, password);
		KeyStore ks = getKeyStore(keyStorePath, password);
		//get private key
		PrivateKey privateKey = (PrivateKey) ks.getKey(alias,
				password.toCharArray());

		//generate signature object
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initSign(privateKey);
		signature.update(sign);
		return encryptBASE64(signature.sign());
	}

	/**
	 * verify the signature
	 * @param data
	 * @param sign
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String sign,
			String certificatePath) throws Exception {
		X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
        //get public key
		PublicKey publicKey = x509Certificate.getPublicKey();
		//generate signature
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(decryptBASE64(sign));
	}

	/**
	 * verify keystore
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 */
	public static boolean verifyCertificate(Date date, String keyStorePath,
			String alias, String password) {
		boolean status = true;
		try {
			Certificate certificate = getCertificate(keyStorePath, alias,
					password);
			status = verifyCertificate(date, certificate);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	/**
	 * verify key store
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 */
	public static boolean verifyCertificate(String keyStorePath, String alias,
			String password) {
		return verifyCertificate(new Date(), keyStorePath, alias, password);
	}

}
