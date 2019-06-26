package com.pengheng.util.security;

import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.binary.Base64;

public final class DesCrypto {
	private static IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });

	private static Cipher encryptCipher = null;

	private static Cipher decryptCipher = null;

	public static final String ECB = "ECB";

	public static final String CBC = "CBC";

	public static final String DES = "DES";

	public static final String TRIPLE_DES = "DESEDE";

	public static String desEncryptWithCBC(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return encrypt(paramString1, paramString2, "DES", "CBC", paramString3);
	}

	public static String desEncryptWithCBC(String paramString1, String paramString2) throws Exception {
		return encrypt(paramString1, paramString2, "DES", "CBC", "UTF-8");
	}

	public static String desEncryptWithECB(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return encrypt(paramString1, paramString2, "DES", "ECB", paramString3);
	}

	public static String desEncryptWithECB(String paramString1, String paramString2) throws Exception {
		return encrypt(paramString1, paramString2, "DES", "ECB", "UTF-8");
	}

	public static String tripleDesEncryptWithCBC(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return encrypt(paramString1, paramString2, "DESEDE", "CBC", paramString3);
	}

	public static String tripleDesEncryptWithCBC(String paramString1, String paramString2) throws Exception {
		return encrypt(paramString1, paramString2, "DESEDE", "CBC", "UTF-8");
	}

	public static String tripleDesEncryptWithECB(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return encrypt(paramString1, paramString2, "DESEDE", "ECB", paramString3);
	}

	public static String tripleDesEncryptWithECB(String paramString1, String paramString2) throws Exception {
		return encrypt(paramString1, paramString2, "DESEDE", "ECB", "UTF-8");
	}

	public static String desDecryptWithCBC(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return decrypt(paramString1, paramString2, "DES", "CBC", paramString3);
	}

	public static String desDecryptWithCBC(String paramString1, String paramString2) throws Exception {
		return decrypt(paramString1, paramString2, "DES", "CBC", "UTF-8");
	}

	public static String desDecryptWithECB(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return decrypt(paramString1, paramString2, "DES", "ECB", paramString3);
	}

	public static String desDecryptWithECB(String paramString1, String paramString2) throws Exception {
		return decrypt(paramString1, paramString2, "DES", "ECB", "UTF-8");
	}

	public static String tripleDesDecryptWithCBC(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return decrypt(paramString1, paramString2, "DESEDE", "CBC", paramString3);
	}

	public static String tripleDesDecryptWithCBC(String paramString1, String paramString2) throws Exception {
		return decrypt(paramString1, paramString2, "DESEDE", "CBC", "UTF-8");
	}

	public static String tripleDesDecryptWithECB(String paramString1, String paramString2, String paramString3)
			throws Exception {
		return decrypt(paramString1, paramString2, "DESEDE", "ECB", paramString3);
	}

	public static String tripleDesDecryptWithECB(String paramString1, String paramString2) throws Exception {
		return decrypt(paramString1, paramString2, "DESEDE", "ECB", "UTF-8");
	}

	public static String encrypt(String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5) throws Exception {
		Key key = getKey(paramString2, paramString3);
		if (encryptCipher == null) {
			encryptCipher = Cipher.getInstance(paramString3 + "/" + paramString4 + "/PKCS5Padding");
			if ("CBC".equals(paramString4)) {
				encryptCipher.init(1, key, ivParameterSpec);
			} else {
				encryptCipher.init(1, key);
			}
		}
		byte[] arrayOfByte = encryptCipher.doFinal(paramString1.getBytes(paramString5));
		return Base64.encodeBase64String(arrayOfByte);
	}

	public static String decrypt(String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5) throws Exception {
		byte[] arrayOfByte1 = Base64.decodeBase64(paramString1);
		Key key = getKey(paramString2, paramString3);
		if (decryptCipher == null) {
			decryptCipher = Cipher.getInstance(paramString3 + "/" + paramString4 + "/PKCS5Padding");
			if ("CBC".equals(paramString4)) {
				decryptCipher.init(2, key, ivParameterSpec);
			} else {
				decryptCipher.init(2, key);
			}
		}
		byte[] arrayOfByte2 = decryptCipher.doFinal(arrayOfByte1);
		return new String(arrayOfByte2, paramString5);
	}

	private static Key getKey(String paramString1, String paramString2) throws Exception {
		KeySpec dESedeKeySpec = null;
		if ("DES".equals(paramString2)) {
			dESedeKeySpec = new DESKeySpec(paramString1.getBytes());
		} else if ("DESEDE".equals(paramString2)) {
			dESedeKeySpec = new DESedeKeySpec(paramString1.getBytes());
		}
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(paramString2);
		return secretKeyFactory.generateSecret(dESedeKeySpec);
	}

	public static void reset() {
		encryptCipher = null;
		decryptCipher = null;
	}

//	public static void main(String[] paramArrayOfString) throws Exception {
//		System.out.println(tripleDesEncryptWithCBC("adminadmin", "qwertyuiopasdfghjklouytrewqzxcvb"));
//		System.out.println(tripleDesDecryptWithCBC("z14l8dfKftiYDCvNy10XlQ==", "qwertyuiopasdfghjklouytrewqzxcvb"));
//	}
}
