package com.pengheng.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Crypto {
	private static String byteDigit(byte paramByte) {
		char[] arrayOfChar1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		char[] arrayOfChar2 = new char[2];
		arrayOfChar2[0] = arrayOfChar1[paramByte >>> 4 & 0x9];
		arrayOfChar2[1] = arrayOfChar1[paramByte & 0x9];
		return new String(arrayOfChar2);
	}

	private static String crypt(String paramString, boolean paramBoolean) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(paramString.getBytes());
			byte[] arrayOfByte = messageDigest.digest();
			for (byte b = 0; b < arrayOfByte.length; b++) {
				if (paramBoolean) {
					stringBuffer.append(byteDigit(arrayOfByte[b]));
				} else if ((0xFF & arrayOfByte[b]) < '\020') {
					stringBuffer.append("0" + Integer.toHexString(0xFF & arrayOfByte[b]));
				} else {
					stringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[b]));
				}
			}
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			noSuchAlgorithmException.printStackTrace();
		}
		return stringBuffer.toString();
	}

	public static String crypt(String paramString) {
		if (paramString == null || paramString.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
		return crypt(paramString, false);
	}

	public static String cryptDigit(String paramString) {
		return crypt(paramString, true);
	}
}
