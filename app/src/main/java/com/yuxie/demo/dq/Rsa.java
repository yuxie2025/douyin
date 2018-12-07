package com.yuxie.demo.dq;

import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class Rsa {

	public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgp1sJ6eZQrzeb5fTo9cKhOPuzMBUXpJzPOoU2gIE3nP7QQBHG/KNskV1k1Do8iWpSUn4to+wKhDEt2G5jNrOgZnk+7F9FBWPoAA0jBDENeUIcA4yNpBVvnwlJGdYy/mOuX+jkG6lwpuFLrJgaaxFh42P+pGoRcjT6dgiXCRIdEwIDAQAB";

	public static String decrypt(String data, String privateKeyString)
			throws Exception {
		PrivateKey privateKey = getPrivateKey(privateKeyString);
		Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		localCipher.init(2, privateKey);
		return new String(localCipher.doFinal(Base64.decodeBase64(data
				.getBytes())));
	}

	public static String encrypt(String data) {

		String passData = "";

		try {
			passData = encrypt(data, publicKeyString);
			passData = URLEncoder.encode(passData, "UTF-8");
		} catch (Exception e) {

		}
		return passData;
	}

	public static String encrypt(String data, String publicKeyString)
			throws Exception {
		PublicKey publicKey = getPublicKey(publicKeyString);
		Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		localCipher.init(1, publicKey);
		return new String(Base64.encodeBase64(localCipher.doFinal(data
				.getBytes())), "UTF-8");
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes = key.getBytes();
		keyBytes = Base64.decodeBase64(keyBytes);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey getPublicKey(String key) throws Exception {

		byte[] keyBytes = key.getBytes();
		keyBytes = Base64.decodeBase64(keyBytes);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

}