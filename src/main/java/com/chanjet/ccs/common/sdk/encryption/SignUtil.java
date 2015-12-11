package com.chanjet.ccs.common.sdk.encryption;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.xml.security.utils.Base64;

public class SignUtil {

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	public static String HmacSHA1Encrypt(String encryptText, String encryptKey) {
		try {
			byte[] data = encryptKey.getBytes(ENCODING);
			SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
			Mac mac = Mac.getInstance(MAC_NAME);
			mac.init(secretKey);
			byte[] text = encryptText.getBytes(ENCODING);
			byte[] result = mac.doFinal(text);
			return Base64.encode(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
