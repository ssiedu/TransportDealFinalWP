package com.wp.encrytion;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESDecryption {

	private static final int ITERATIONS = 65536;// standard
	private static final int KEY_LENGTH = 256;// SHA256 if SHA512 then 512
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
//	private static String INITIALIZATIO_VECTOR = "AODVNUASDNVVAOVF";
	public static String decrypt(String encryptedText) {
		try {
			String password = "Hello";
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// strip off the salt
			
			ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(encryptedText));
			byte[] saltBytes = new byte[20];
			buffer.get(saltBytes, 0, saltBytes.length);
			byte[] ivBytes1 = new byte[cipher.getBlockSize()];
		    buffer.get(ivBytes1, 0, ivBytes1.length);
		    byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];
		    buffer.get(encryptedTextBytes);
		    buffer.clear();
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);

			SecretKeyFactory factory;

			factory = SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey secretKey = factory.generateSecret(spec);
			// specifying type of algorithm
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

			// Note - above code only generate hashvalue but
			// for asymmetric encrytion decrytion we use cipher
			// creating cipher instance
			
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));
			
			byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
			
			return new String(decryptedTextBytes);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
