package com.pengheng.util.security;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public final class AesCrypto {
	public static final String PKCS5Padding = "PKCS5Padding";

	public static final String PKCS7Padding = "PKCS7Padding";

	private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	private static final int BLOCK_SIZE = 16;

	public static final String encrypt(String paramString1, byte[] paramArrayOfByte, String paramString2)
			throws GeneralSecurityException {
		byte[] arrayOfByte1 = paramString1.getBytes(CHARSET_UTF8);
		byte[] arrayOfByte2 = arrayOfByte1;
		if (paramString2.equals("PKCS7Padding")) {
			arrayOfByte1 = paramString1.getBytes(CHARSET_UTF8);
			byte[] arrayOfByte = PKCS7Encoder.encode(arrayOfByte1.length);
			arrayOfByte2 = Arrays.copyOf(arrayOfByte1, arrayOfByte1.length + arrayOfByte.length);
			System.arraycopy(arrayOfByte, 0, arrayOfByte2, arrayOfByte1.length, arrayOfByte.length);
		}
		Cipher cipher = Cipher
				.getInstance("AES/CBC/" + (paramString2.equals("PKCS7Padding") ? "NoPadding" : "PKCS5Padding"));
		SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfByte, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfByte);
		cipher.init(1, secretKeySpec, ivParameterSpec);
		byte[] arrayOfByte3 = cipher.doFinal(arrayOfByte2);
		return Base64.encodeBase64String(arrayOfByte3);
	}

	public static final String decrypt(String paramString1, byte[] paramArrayOfByte, String paramString2)
			throws GeneralSecurityException {
		Cipher cipher = Cipher
				.getInstance("AES/CBC/" + (paramString2.equals("PKCS7Padding") ? "NoPadding" : "PKCS5Padding"));
		SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfByte, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfByte);
		cipher.init(2, secretKeySpec, ivParameterSpec);
		byte[] arrayOfByte = cipher.doFinal(Base64.decodeBase64(paramString1));
		if (paramString2.equals("PKCS7Padding"))
			arrayOfByte = PKCS7Encoder.decode(arrayOfByte);
		return new String(arrayOfByte, CHARSET_UTF8);
	}

	static class PKCS7Encoder {
		static byte[] encode(int param1Int) {
			int i = 16 - param1Int % 16;
			if (i == 0)
				i = 16;
			char c = toChar(i);
			String str = new String();
			for (byte b = 0; b < i; b++)
				str = str + c;
			return str.getBytes(CHARSET_UTF8);
		}

		static byte[] decode(byte[] param1ArrayOfByte) {
			byte b = param1ArrayOfByte[param1ArrayOfByte.length - 1];
			if (b < 1 || b > 32)
				b = 0;
			return Arrays.copyOfRange(param1ArrayOfByte, 0, param1ArrayOfByte.length - b);
		}

		private static char toChar(int param1Int) {
			byte b = (byte) (param1Int & 0xFF);
			return (char) b;
		}
	}
}
