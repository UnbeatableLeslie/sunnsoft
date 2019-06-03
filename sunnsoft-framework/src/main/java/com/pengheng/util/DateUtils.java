package com.pengheng.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

public class DateUtils {
	private static final Calendar calendar = Calendar.getInstance();

	private static final Logger logger = Logger.getLogger(DateUtils.class);

	public static final int PART_OF_YEAR = 0;

	public static final int PART_OF_MONTH = 1;

	public static final int PART_OF_DAY_IN_MONTH = 2;

	public static final int PART_OF_DAY_IN_WEEK = 3;

	public static final int PART_OF_DAY_IN_YEAR = 4;

	public static final int PART_OF_REAL_WEEK_IN_MONTH = 5;

	public static final int PART_OF_WEEK_IN_MONTH = 6;

	public static final int PART_OF_WEEK_IN_YEAR = 7;

	public static final int PART_OF_HOUR = 8;

	public static final int PART_OF_HOUR_IN_DAY = 9;

	public static final int PART_OF_MINUTE = 10;

	public static final int PART_OF_SECOND = 11;

	public static final int PART_OF_AM_PM = 12;

	public static final int PART_OF_SEASON = 13;

	public static final long ONE_DAY_IN_HOUR = 24L;

	public static final long ONE_DAY_IN_MINUTE = 1440L;

	public static final long ONE_DAY_IN_SECOND = 86400L;

	public static final long ONE_DAY_IN_MILLISECOND = 86400000L;

	protected DateUtils() {
		calendar.clear();
	}

	public static final Date getDateByDays(Date paramDate, int paramInt) {
		return new Date(paramDate.getTime() + paramInt * 86400000L);
	}

	public static int getDatePart(long paramLong, int paramInt) throws Exception {
		return getDatePart(new Date(paramLong), paramInt);
	}

	public static int getDatePart(Date paramDate, int paramInt) throws Exception {
		calendar.setTime(paramDate);
		switch (paramInt) {
		case 0:
			return calendar.get(1);
		case 1:
			return calendar.get(2) + 1;
		case 2:
			return calendar.get(5);
		case 3:
			return calendar.get(7) - 1;
		case 4:
			return calendar.get(6);
		case 5:
			return calendar.get(4);
		case 6:
			return calendar.get(8);
		case 7:
			return calendar.get(3);
		case 8:
			return calendar.get(10);
		case 9:
			return calendar.get(11);
		case 10:
			return calendar.get(12);
		case 11:
			return calendar.get(13);
		case 12:
			return calendar.get(9);
		case 13:
			switch (calendar.get(2) + 1) {
			case 1:
			case 2:
			case 3:
				return 1;
			case 4:
			case 5:
			case 6:
				return 2;
			case 7:
			case 8:
			case 9:
				return 3;
			case 10:
			case 11:
			case 12:
			case 0:
				break;
			}
			return 4;
		}
		throw new IllegalArgumentException("Unknown argument \"" + paramInt + "\"");
	}

	public static final int getDaysOfMonth(Date paramDate) throws Exception {
		switch (getDatePart(paramDate, 1) - 1) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			return 31;
		case 3:
		case 5:
		case 8:
		case 10:
			return 30;
		case 1:
			return isLeapYear(paramDate) ? 29 : 28;
		}
		throw new Exception("Unknown month");
	}

	public static final int getDaysOfMonth(long paramLong) throws Exception {
		return getDaysOfMonth(new Date(paramLong));
	}

	public static Date getDate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5,
			int paramInt6) {
		calendar.set(1, paramInt1);
		calendar.set(2, paramInt2 - 1);
		calendar.set(5, paramInt3);
		calendar.set(11, paramInt4);
		calendar.set(12, paramInt5);
		calendar.set(13, paramInt6);
		return calendar.getTime();
	}

	public static Date getDate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
		return getDate(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0);
	}

	public static Date getDate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		return getDate(paramInt1, paramInt2, paramInt3, paramInt4, 0, 0);
	}

	public static Date getDate(int paramInt1, int paramInt2, int paramInt3) {
		return getDate(paramInt1, paramInt2, paramInt3, 0, 0, 0);
	}

	public static Date getDate(int paramInt1, int paramInt2) {
		return getDate(paramInt1, paramInt2, 1, 0, 0, 0);
	}

	public static Date getDate(int paramInt) {
		return getDate(paramInt, 1, 1, 0, 0, 0);
	}

	public static Date parseDate(String paramString1, String paramString2) {
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(paramString2);
			date = simpleDateFormat.parse(paramString1);
		} catch (ParseException parseException) {
			logger.warn("Convert date string to date object failure: " + parseException.getLocalizedMessage());
		}
		return date;
	}

	public static Timestamp parseTimestamp(String paramString1, String paramString2) {
		Timestamp timestamp = null;
		Date date = parseDate(paramString1, paramString2);
		if (date != null)
			timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	public static Timestamp parseTimestamp(Date paramDate, String paramString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(paramString);
		return parseTimestamp(simpleDateFormat.format(paramDate), paramString);
	}

	public static boolean isLeapYear(Date paramDate) throws Exception {
		int i = getDatePart(paramDate, 0);
		return (i % 400 == 0 || (i % 4 == 0 && i % 100 != 0));
	}

	public static boolean isLeapYear(long paramLong) throws Exception {
		return isLeapYear(new Date(paramLong));
	}

	public static boolean isDateEquels(Date paramDate1, Date paramDate2) {
		return (paramDate1.getTime() - paramDate2.getTime() == 0L);
	}

	public static int dateCompare(Date paramDate1, Date paramDate2) {
		long l = paramDate1.getTime() - paramDate2.getTime();
		return (l > 0L) ? 1 : ((l < 0L) ? -1 : 0);
	}
}
