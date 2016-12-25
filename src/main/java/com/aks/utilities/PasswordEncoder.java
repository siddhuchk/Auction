package com.aks.utilities;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("restriction")
@Service
public class PasswordEncoder {

	private static final Logger logger = Logger
			.getLogger(PasswordEncoder.class);

	private static Cipher initCipher(final int mode,
			final String initialVectorString, final String secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		final SecretKeySpec skeySpec = new SecretKeySpec(md5(secretKey)
				.getBytes(), "AES");
		final IvParameterSpec initialVector = new IvParameterSpec(
				initialVectorString.getBytes());
		final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
		cipher.init(mode, skeySpec, initialVector);
		return cipher;
	}

	public static String encrypt(final String dataToEncrypt,
			final String initialVector, final String secretKey) {
		String encryptedData = null;
		try {
			// Initialize the cipher
			final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE,
					initialVector, secretKey);
			// Encrypt the data
			final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt
					.getBytes());
			// Encode using Base64
			encryptedData = (new BASE64Encoder()).encode(encryptedByteArray);
		} catch (Exception e) {
			logger.error("Problem encrypting the data", e);
			e.printStackTrace();
		}
		return encryptedData;
	}

	public static String decrypt(final String encryptedData,
			final String initialVector, final String secretKey) {
		String decryptedData = null;
		try {
			// Initialize the cipher
			final Cipher cipher = initCipher(Cipher.DECRYPT_MODE,
					initialVector, secretKey);
			// Decode using Base64
			final byte[] encryptedByteArray = (new BASE64Decoder())
					.decodeBuffer(encryptedData);
			// Decrypt the data
			final byte[] decryptedByteArray = cipher
					.doFinal(encryptedByteArray);
			decryptedData = new String(decryptedByteArray, "UTF8");
		} catch (Exception e) {
			logger.error("Problem decrypting the data", e);
			e.printStackTrace();
		}
		return decryptedData;
	}

	public static String passowdEncrpt(String password)
			throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();

	}

	private static String md5(final String input)
			throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("MD5");
		final byte[] messageDigest = md.digest(input.getBytes());
		final BigInteger number = new BigInteger(1, messageDigest);
		return String.format("%032x", number);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		final String iv = "India@123"; // This has to be 16 characters
		final String secretKey = "Replace this by your secret key";
		final PasswordEncoder crypto = new PasswordEncoder();

		/*final String encryptedData = crypto.encrypt("This is a test message.",
				iv, secretKey);
		System.out.println(encryptedData);*/

		final String decryptedData = crypto.decrypt("753e91286bebce0ddd63dc0bb65bb7b5", iv,
				secretKey);
		System.out.println(decryptedData);
	}

}
