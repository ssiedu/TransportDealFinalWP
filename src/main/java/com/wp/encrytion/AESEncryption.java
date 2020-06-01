package com.wp.encrytion;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
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

public class AESEncryption {
	
	private static final int ITERATIONS = 65536;//standard
	private static final int KEY_LENGTH = 256;//SHA256 if SHA512 then 512
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
//	private static String INITIALIZATIO_VECTOR = "AODVNUASDNVVAOVF";
	public static String encrypt(String word) {
		//this is our secret to generate hash in PBESpec,
		//we can give whatever we want
		//Note this secret must be same at decryption
		String password = "Hello"; 
		//created random salt of byte array size 20
		SecureRandom random = new SecureRandom();
		byte []saltBytes = new byte[20];
		random.nextBytes(saltBytes);
		
		//initializing specs for key
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS,KEY_LENGTH);
		
		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey secretKey = factory.generateSecret(spec);
			//specifying type of algorithm
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

			//Note - above code only generate hashvalue but
			//for asymmetric encrytion decrytion we use cipher
			//creating cipher instance 
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			System.out.println(cipher!=null);
			System.out.println(word.getBytes("UTF-8"));
			AlgorithmParameters params = cipher.getParameters();
		     byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
			byte[] encyrptedTextBytes = cipher.doFinal(word.getBytes("UTF-8"));
//			System.out.println(word.getBytes("UTF-8"));
			//now appending saltbytes and iv bytes to encryptedTextBytes
			byte[] buffer = new byte[saltBytes.length+encyrptedTextBytes.length + ivBytes.length];
			System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
			System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
			System.arraycopy(encyrptedTextBytes, 0, buffer, saltBytes.length+ivBytes.length,encyrptedTextBytes.length);
			
			return Base64.getEncoder().encodeToString(buffer);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
