package com.pengheng.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;

public class TextUtils {
	public static final String ISO_NUMBER_THOUSAND_ZERO_FORMAT = "#,##0.00";

	public static final String ISO_NUMBER_THOUSAND_FORMAT = "#,##0.##";

	public static final String ISO_NUMBER_ZERO_FORMAT = "0.00";

	public static final String ISO_NUMBER_FORMAT = "#.##";

	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	public static final String ISO_DATE_TIME_ZONE_FORMAT = "yyyy-MM-ddZZ";

	public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String ISO_DATETIME_NO_T_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String ISO_DATETIME_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";

	public static final String ISO_DATETIME_NO_T_TIME_ZONE_FORMAT = "yyyy-MM-dd HH:mm:ssZZ";

	public static final String ISO_TIME_FORMAT = "'T'HH:mm:ss";

	public static final String ISO_TIME_NO_T_FORMAT = "HH:mm:ss";

	public static final String ISO_TIME_TIME_ZONE_FORMAT = "'T'HH:mm:ssZZ";

	public static final String ISO_TIME_NO_T_TIME_ZONE_FORMAT = "HH:mm:ssZZ";

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

	private static final DecimalFormat decimalFormat = new DecimalFormat();

	private static final Map<String, String> systemCharsets = getSupportCharsets();

	public static final String base64Encode(String paramString) throws IOException {
		return Base64.encodeBase64String(paramString.getBytes("UTF-8"));
	}

	public static final String base64Decode(String paramString) throws IOException {
		return new String(Base64.decodeBase64(paramString), "UTF-8");
	}

	public static final String stringEncode(String paramString1, String paramString2, String paramString3)
			throws UnsupportedEncodingException {
		return new String(paramString1.getBytes(paramString2), paramString3);
	}

	public static final String byteToHex(byte[] paramArrayOfByte) {
		String str = "";
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b = 0; b < paramArrayOfByte.length; b++) {
			str = Integer.toHexString(paramArrayOfByte[b] & 0xFF);
			if (str.length() == 1)
				stringBuffer.append("0");
			stringBuffer.append(str);
		}
		return stringBuffer.toString().toUpperCase();
	}

	public static final String byteToHex(byte paramByte) {
		char[] arrayOfChar1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] arrayOfChar2 = new char[2];
		arrayOfChar2[0] = arrayOfChar1[paramByte >>> 4 & 0xF];
		arrayOfChar2[1] = arrayOfChar1[paramByte & 0xF];
		return new String(arrayOfChar2);
	}

	public static final String dateFormat() {
		return dateFormat(new Date(System.currentTimeMillis()));
	}

	public static final String dateFormat(Date paramDate) {
		return dateFormat(paramDate, "yyyy-MM-dd HH:mm:ss");
	}

	public static final String dateFormat(Date paramDate, String paramString) {
		simpleDateFormat.applyPattern(paramString);
		return simpleDateFormat.format(paramDate);
	}

	public static final String numberFormat(double paramDouble) {
		return numberFormat(paramDouble, "#,##0.00");
	}

	public static final String numberFormat(long paramLong) {
		return numberFormat(paramLong, "#,##0.00");
	}

	public static final String numberFormat(Object paramObject) {
		return numberFormat(paramObject, "#,##0.00");
	}

	public static final String numberFormat(double paramDouble, String paramString) {
		decimalFormat.applyPattern(paramString);
		return decimalFormat.format(paramDouble);
	}

	public static final String numberFormat(long paramLong, String paramString) {
		decimalFormat.applyPattern(paramString);
		return decimalFormat.format(paramLong);
	}

	public static final String numberFormat(Object paramObject, String paramString) {
		decimalFormat.applyPattern(paramString);
		return decimalFormat.format(paramObject);
	}

	public static final boolean checkCharset(String paramString1, String paramString2) throws Exception {
		return checkCharset(paramString1, new String[] { paramString2 });
	}

	public static final boolean checkCharset(String paramString, String[] paramArrayOfString) throws Exception {
		if (paramString != null && !paramString.equals(""))
			for (byte b = 0; b < paramArrayOfString.length; b++) {
				if (paramString.equals(new String(paramString.getBytes(paramArrayOfString[b]))))
					return true;
			}
		return false;
	}

	public static final String getCharset(String paramString, String[] paramArrayOfString) throws Exception {
		if (paramString != null && !paramString.equals(""))
			for (byte b = 0; b < paramArrayOfString.length; b++) {
				if (paramString.equals(new String(paramString.getBytes(paramArrayOfString[b]))))
					return paramArrayOfString[b].toUpperCase();
			}
		return "UNKNOWN";
	}

	public static final String getSupportCharset(String paramString) throws IOException {
		if (paramString != null && !paramString.equals("")) {
			Iterator iterator = systemCharsets.keySet().iterator();
			while (iterator.hasNext()) {
				String str = iterator.next().toString().toUpperCase();
				if (paramString.equals(new String(paramString.getBytes(str))))
					return str.toUpperCase();
			}
		}
		return "UNKNOWN";
	}

	public static final Map<String, String> getSupportCharsets() {
		HashMap hashMap = new HashMap();
		hashMap.put("ISO-8859-1", "ISO-8859-1");
		Iterator iterator = Charset.availableCharsets().keySet().iterator();
		while (iterator.hasNext()) {
			String str = iterator.next().toString().trim().toUpperCase();
			if (str.indexOf("WINDOWS-") == -1 && str.indexOf("X-") == -1)
				hashMap.put(str, str);
		}
		return hashMap;
	}

	public static String compress(String paramString) throws IOException {
		if (paramString == null || paramString.length() == 0)
			return paramString;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
		gZIPOutputStream.write(paramString.getBytes());
		gZIPOutputStream.close();
		return byteArrayOutputStream.toString("ISO-8859-1");
	}

	public static String uncompress(String paramString) throws IOException {
		if (paramString == null || paramString.length() == 0)
			return paramString;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramString.getBytes("ISO-8859-1"));
		GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
		byte[] arrayOfByte = new byte[256];
		int i;
		while ((i = gZIPInputStream.read(arrayOfByte)) >= 0)
			byteArrayOutputStream.write(arrayOfByte, 0, i);
		return byteArrayOutputStream.toString();
	}
}
