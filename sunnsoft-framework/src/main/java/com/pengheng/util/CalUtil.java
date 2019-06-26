package com.pengheng.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;

public class CalUtil {
	public static String Add(String paramString1, String paramString2) {
		BigDecimal bigDecimal1 = new BigDecimal(paramString1);
		BigDecimal bigDecimal2 = new BigDecimal(paramString2);
		return bigDecimal1.add(bigDecimal2).toString();
	}

	public static String Sub(String paramString1, String paramString2) {
		BigDecimal bigDecimal1 = new BigDecimal(paramString1);
		BigDecimal bigDecimal2 = new BigDecimal(paramString2);
		return bigDecimal1.subtract(bigDecimal2).toString();
	}

	public static String Mult(String paramString1, String paramString2) {
		BigDecimal bigDecimal1 = new BigDecimal(paramString1);
		BigDecimal bigDecimal2 = new BigDecimal(paramString2);
		return bigDecimal1.multiply(bigDecimal2).toString();
	}

	public static String Divide(String paramString1, String paramString2, int paramInt) {
		if (paramInt < 0) {
            throw new IllegalArgumentException("必须为自然数");
        }
		if (CompareTo(paramString1, "0") == 0) {
            return "0";
        }
		BigDecimal bigDecimal1 = new BigDecimal(paramString1);
		BigDecimal bigDecimal2 = new BigDecimal(paramString2);
		return bigDecimal1.divide(bigDecimal2, paramInt, 4).toString();
	}

	public static int CompareTo(String paramString1, String paramString2) {
		BigDecimal bigDecimal1 = new BigDecimal(paramString1);
		BigDecimal bigDecimal2 = new BigDecimal(paramString2);
		return bigDecimal1.compareTo(bigDecimal2);
	}

	public static String Remainder(String paramString1, String paramString2) {
		BigDecimal bigDecimal1 = new BigDecimal(paramString1);
		BigDecimal bigDecimal2 = new BigDecimal(paramString2);
		return bigDecimal1.remainder(bigDecimal2).toString();
	}

	public static String Format(String paramString) {
		if (StringUtils.isEmpty(paramString)) {
            return "0.00";
        }
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.applyPattern("0.00");
		double d = Double.parseDouble(paramString);
		decimalFormat.setRoundingMode(RoundingMode.DOWN);
		return decimalFormat.format(d);
	}

	public static String Format2(String paramString) {
		if (StringUtils.isEmpty(paramString)) {
            return "0.00";
        }
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.applyPattern("##,##0.00");
		double d = Double.parseDouble(paramString);
		return decimalFormat.format(d);
	}

	public static String format(String paramString, int paramInt) {
		Double db = Double.valueOf(Double.parseDouble(paramString));
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(paramInt);
		decimalFormat.setGroupingSize(0);
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		return decimalFormat.format(db);
	}

	public static int diff(String paramString1, String paramString2, String paramString3) {
		String str = Sub(paramString1, paramString2);
		BigDecimal bigDecimal = (new BigDecimal(str)).abs();
		return bigDecimal.compareTo((new BigDecimal(paramString3)).abs());
	}

	public static String abs(String paramString) {
		BigDecimal bigDecimal = (new BigDecimal(paramString)).abs();
		return bigDecimal.toString();
	}

	public static String Pow(String paramString, int paramInt) {
		BigDecimal bigDecimal = new BigDecimal(paramString);
		return bigDecimal.pow(paramInt).toString();
	}
}
