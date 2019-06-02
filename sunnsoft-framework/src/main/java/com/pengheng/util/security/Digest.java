package com.pengheng.util.security;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Digest {
	private static final Logger logger = Logger.getLogger(Digest.class);

	public static final byte[] messageDigest(String paramString1, String paramString2, String paramString3) {
		byte[] arrayOfByte = new byte[0];
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(paramString2);
			messageDigest.update(paramString1.getBytes(paramString3));
			arrayOfByte = messageDigest.digest();
		} catch (Exception exception) {
			logger.warn("digest failure, " + exception.getLocalizedMessage());
		}
		return arrayOfByte;
	}

	public static final byte[] messageDigest(String paramString1, String paramString2) {
		return messageDigest(paramString1, paramString2, "UTF-8");
	}

	public static final String messageDigestByBase64(String paramString1, String paramString2) {
		return Base64.encodeBase64String(messageDigest(paramString1, paramString2, "UTF-8"));
	}

	public static final String messageDigestByBase64(String paramString1, String paramString2, String paramString3) {
		return Base64.encodeBase64String(messageDigest(paramString1, paramString2, paramString3));
	}
}
