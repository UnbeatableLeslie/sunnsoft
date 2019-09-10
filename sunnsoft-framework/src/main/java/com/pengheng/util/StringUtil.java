package com.pengheng.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      CES
 * @author liuhs
 * @version 1.0
 */

/**
 * 此类主要处理有关String类型的一些操作，如：替换字符串、查找字符串的个数、将一个String[]
 * 转化成一个int[]、将一个String[]中的某种字符串过滤掉、将一个String[]中的某种字符串移到
 * 数组的末尾、将String转化成gb2312的编码格式、将一个String转化成String[].
 */
public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public StringUtil() {
	}

	/**
	 * 判断NULL
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value) {
		try {
			if (value == null) {
                return true;
            }
			if ("null".equals(value)) {
                return true;
            }
			if (value.trim().length() == 0) {
                return true;
            } else {
                return false;
            }
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断对象是否为NULL，如为Null转化为空字符串
	 * 
	 * @param val
	 * @return
	 */
	public static String repNullToStr(String val) {
		return val == null ? "" : val;
	}

	/**
	 * 判断对象是否为NULL，如为Null转化为0
	 * 
	 * @param val
	 * @return
	 */
	public static String repNullToZero(String val) {
		return val == null ? "0" : val;
	}

	/**
	 * 处理非法字符（定义0-31的ASCII码为非法字符）
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isIllegalChar(String s) {
		boolean flag = false;
		char[] dest = s.trim().toCharArray();
		for (int i = 0; i < dest.length; i++) {
			if (((int) dest[i] > 0 && (int) dest[i] < 32)) {
				// Debug("含有非法字符!" + (int) dest[i]);
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判断是否为正数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkNum(String value) {
		try {
			if (value != null && value.trim().length() > 0) {
				if ((value.charAt(0) == '-')) {
					return false;
				} else {
					String[] temp = value.split("\\.");
					if (temp.length > 2) {
						return false;
					}
					value = value.replaceAll("\\.", "");
					char[] dest = value.trim().toCharArray();
					for (int i = 0; i < dest.length; i++) {
						if (!Character.isDigit(dest[i])) {
							return false;
						}
					}
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 替换String中的字符串. 在标准的String类中只有将String中的某一个字符替换成另一个字符，该函数可以将String
	 * 中的某一字符串替换成另一个字符串.
	 * 
	 * @param str
	 *            源字符串 tag 将要被替换的字符串 news 将要替换的字符串
	 * @return 将源字符串替换后的字符串
	 */
	public static String replace(String str, String tag, String news) {
		if (str == null || str.length() == 0 || tag == null || tag.length() == 0) {
            return (str);
        }
		String ret = str, temp = "";
		String l_str;
		int len_s = str.length();
		int len_t = tag.length();
		int pos_1 = 0, pos_2 = 0;
		int i = 0;
		while (i < len_s) {
			if (i >= (len_s - len_t)) {
                l_str = str.substring(i);
            } else {
                l_str = str.substring(i, i + len_t);
            }
			if (l_str.equals(tag)) {
				pos_2 = i;
				temp = temp + str.substring(pos_1, pos_2) + news;
				i = i + len_t;
				pos_1 = i;
			} else {
                i++;
            }
		}
		if (pos_1 < len_s) {
            temp = temp + str.substring(pos_1);
        }

		if (temp.length() > 0) {
            ret = temp;
        }

		return (ret);
	}

	/**
	 * 查找某一字符串包含另一字符串的个数. 此处规定已匹配过的字符
	 * 
	 * @param str1
	 *            源字符串 str2 将要被替换的字符串
	 * @return 包含的个数
	 */
	public static int hasStr(String str1, String str2) {
		int sum = 0;
		String sTemp = new String(str1);
		while (sTemp.indexOf(str2) != -1) {
			sum++;
			sTemp = sTemp.substring(sTemp.indexOf(str2) + str2.length());
		}
		return sum;
	}

	/**
	 * 将字符串转化成gb2312的编码格式
	 */
	public static String changeEncoding(String strChinese, String soureceEncoding, String targetEncoding) {
		if (strChinese == null) {
            return "";
        }
		try {
			return new String(strChinese.trim().getBytes(soureceEncoding), targetEncoding);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将一个字符串根据一个标示拆分成一个字符串数组.例如",1,2,3,"如果使用“,”分解，将被分成长度为5的字符串数组
	 * 
	 * @param source
	 *            源字符串,str 拆分标示字符。
	 * @return 被拆分的字符串
	 */
	public static String[] toArray(String source, String str) {
		Vector vResult = new Vector();
		if (source == null || "".equals(source)) {
            return new String[0];
        }
		if (str == null || "".equals(str)) {
            return new String[] { source };
        }
		int pos1 = 0;
		int pos2 = 0;
		pos2 = source.indexOf(str);
		String strItem = "";
		if (pos2 < 0) {
			pos2 = source.length();
			strItem = source;
		}
		while (pos2 >= pos1 && pos1 < source.length()) {
			strItem = source.substring(pos1, pos2);
			vResult.add(strItem);
			pos1 = pos2 + 1;
			pos2 = source.indexOf(str, pos1);
			if (pos2 <= 0) {
                pos2 = source.length();
            }

		}
		return toArray(vResult);
	}

	public static String[] split(String source, String token) {
		return stringToArray(source, token);
	}

	public static String[] splitString(String source, String token) {
		return stringToArray(source, token);
	}

	public static String[] stringToArray(String source, String str) {

		Vector vResult = new Vector();
		if (source == null || "".equals(source)) {
            String[] tmp = {""};
			return tmp;
		}

		if (str == null || "".equals(str)) {
            return new String[] { source };
        }
		int pos1 = 0;
		int pos2 = 0;
		pos2 = source.indexOf(str);
		String strItem = "";
		if (pos2 < 0) {
			pos2 = source.length();
			strItem = source;
		}
		while (pos2 >= pos1 && pos1 <= source.length()) {
			if (pos2 == source.length() && pos1 == source.length()) {
				strItem = source.substring(pos2);
				vResult.add(strItem);
				break;
			}
			strItem = source.substring(pos1, pos2);
			vResult.add(strItem);
			pos1 = pos2 + 1;
			pos2 = source.indexOf(str, pos1);
			if (pos2 <= 0) {
                pos2 = source.length();
            }
		}
		return toArray(vResult);
	}

	public static String[] toArray(Vector vec) {
		int intSize = vec.size();
		String[] str = new String[intSize];
		for (int i = 0; i < intSize; i++) {
            str[i] = vec.elementAt(i).toString();
        }
		return str;
	}

	public static String[] clearElement(String[] sources, String str) {
		sources = StringUtil.moveToEnd(sources, str);
		int size = 0;
		for (; size < sources.length; size++) {
			if (sources[size].equals(str)) {
                break;
            }
		}
		String[] temp = new String[size];
		for (int i = 0; i < size; i++) {
            temp[i] = sources[i];
        }
		return temp;
	}

	/**
	 * 将一个String[]中的某种字符串移到数组的末尾.
	 * 
	 * @param sources
	 *            源字符串数组 str 将要移动的字符串
	 * @return 移动后的字符串数组
	 */
	public static String[] moveToEnd(String[] sources, String str) {
		int head = 0;
		int end = sources.length - 1;
		while (head != end) {
			while (head != end) {
				if (sources[head].equals(str)) {
                    break;
                }
				head++;
			}
			while (head != end) {
				if (!sources[end].equals(str)) {
                    break;
                }
				end--;
			}
			if (head != end) {
				String temp = sources[head];
				sources[head] = sources[end];
				sources[end] = temp;
			}
		}
		return sources;
	}

	/**
	 * 将字符串数组转化成int型数组.
	 * 
	 * @param sources
	 *            源字符串数组
	 * @return int[]
	 */
	public static int[] pareInts(String[] sources) {
		int[] temp = new int[sources.length];
		for (int i = 0; i < sources.length; i++) {
			temp[i] = Integer.parseInt(sources[i]);
		}
		return temp;
	}

	/**
	 * 将数组vector转化为字符串
	 */
	public static String toString(Vector vec, String str) {
		String s = "";
		for (int i = 0; i < vec.size(); i++) {
			if (i != 0) {
                s += str;
            }
			s += vec.elementAt(i).toString().trim();
		}
		return s;
	}

	/**
	 * 将数组[]转化为字符串
	 */
	public static String toString(String[] scr, String str) {
		String s = "";
		for (int i = 0; i < scr.length; i++) {
			if (i != 0) {
                s += str;
            }
			s += scr[i];
		}
		return s;
	}

	/**
	 * 从字符串中取出一个单词
	 */
	public static String getWord(String str, int index) throws Exception {
		if (index < 1) {
            throw new Exception("getWord error,index less than 1");
        }
		String word = null;
		for (int i = 0; i < index; i++) {
			word = StringUtil.getFirstWord(str, true);
			str = str.substring(word.length());
		}
		return word;
	}

	/**
	 * 从字符串中取出第一个单词,并且将分隔符（'，'、‘ ’）也作为一个单词
	 */
	public static String getFirstWord(String str, boolean hasSeparator) throws Exception {
		if ("".equals(str)) {
            return "";
        }
		char ch = str.charAt(0);
		if (ch == '(' || ch == '[' || ch == '{') {
            return StringUtil.getBracket(str, ch);
        }
		if (ch == '\'') {
            return StringUtil.getString(str, ch);
        }
		int offs = str.indexOf(" ") == -1 ? str.length() : str.indexOf(" ");
		String separator = " ";
		if (str.indexOf(",") != -1 && str.indexOf(",") < offs) {
			offs = str.indexOf(",");
			separator = ",";
		}
		if (str.indexOf("-") != -1 && str.indexOf("-") < offs) {
			offs = str.indexOf("-");
			separator = "-";
		}
		if (str.indexOf("+") != -1 && str.indexOf("+") < offs) {
			offs = str.indexOf("+");
			separator = "+";
		}
		if (str.indexOf("*") != -1 && str.indexOf("*") < offs) {
			offs = str.indexOf("*");
			separator = "*";
		}
		if (str.indexOf("=") != -1 && str.indexOf("=") < offs) {
			offs = str.indexOf("=");
			separator = "=";
		}
		if (str.indexOf("||") != -1 && str.indexOf("||") < offs) {
			offs = str.indexOf("||");
			separator = "||";
		}
		if (str.indexOf("(") != -1 && str.indexOf("(") < offs) {
			offs = str.indexOf("(");
			// System.out.println("(:"+str.substring(0,offs)+" == "+str.substring(offs));
			return str.substring(0, offs) + StringUtil.getBracket(str.substring(offs), '(');
		}
		if (offs == -1) {
            return str;
        }
		if (hasSeparator && offs == 0) {
            return separator;
        }
		return str.substring(0, offs);
	}

	/**
	 * 从字符串中取出第一个单词,但将分隔符（'，'、‘ ’）也作为一个单词
	 */
	public static String getFirstWord(String str) throws Exception {
		return StringUtil.getFirstWord(str, false);
	}

	/**
	 * 匹配一个字符串中的第一个刮号. 这里假定该字符串的的刮号是正确的
	 */
	public static String getBracket(String str, char bracketType) throws Exception {
		// 确定刮号的种类
		// System.out.println("确定刮号的种类:"+str);
		char endType = ')';
		if (bracketType == '[') {
            endType = ']';
        } else if (bracketType == '{') {
            endType = '}';
        } else if (bracketType != '(') {
            throw new Exception(bracketType + " not a type of bracket");
        }

		char[] c = str.toCharArray();
		int sum = 1;
		String s = null;
		for (int i = 1; i < c.length; i++) {
			if (c[i] == bracketType) {
				sum++;
				continue;
			}
			if (c[i] == endType) {
				if (--sum == 0) {
					s = String.valueOf(c, 0, i + 1);
					break;
				}
			}
		}
		if (s == null) {
            throw new Exception("bracket not suited");
        }
		// System.out.println("确定刮号的种类 new:"+s);
		return s;
	}

	public static String ltrim(String str) {
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] != ' ') {
				str = String.valueOf(c, i, c.length - i);
				break;
			}
		}
		return str;
	}

	/**
	 * 根据标示符取一个字符串
	 */
	public static String getString(String str, char c) {
		char[] ch = str.toCharArray();
		for (int i = 1; i < ch.length; i++) {
			if (ch[i] == c) {
				if (i == ch.length - 1) {
					str = String.valueOf(ch, 0, i + 1);
					break;
				}
				if (ch[i + 1] != c) {
					str = String.valueOf(ch, 0, i + 1);
					break;
				} else {
					i++;
				}
			}
		}
		return str;
	}

	/**
	 * 转编码
	 **/
	public static String changeCnCode(String oldStr) {
		String value = null;
		try {
			value = new String(oldStr.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		return value;
	}

	/**
	 * 多个空格替换为一个
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String repSingleSpace(String sourceStr) {
		String regEx = "['   ']+";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(sourceStr);
		return m.replaceAll(" ");
	}

	/**
	 * 字符串为空补0.00
	 * 
	 * @param str
	 * @return
	 */
	public static String toBd0(String str) {
		return str != null ? str : "0.00";
	}

	public static String getObj(Object obj) {
		if (null == obj) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	public static String getPrMoney(Object obj) {
		if (obj != null && !"".equals(obj)) {
			return new BigDecimal(obj.toString()).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			return "0.00";
		}
		/*
		 * DecimalFormat df = new DecimalFormat("#.00");
		 * 
		 * if(null==obj||""==obj){ return new Double(0.00)+""; }else{ return
		 * df.format(obj); }
		 */

	}

	public static String toString(Object obj) {
		if (obj == null) {
            return "";
        }
		return obj.toString();
	}

	/**
	 * String 转 double，并保留N为小数
	 * 
	 * @author wqq
	 * @param str
	 * @param num
	 * @return
	 */
	public static double stringToDouble(String str, int num) {
		double dou = Double.valueOf(str);
		String pattern = "#.";
		for (int i = 0; i < num; i++) {
			pattern += "0";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		String temp = df.format(dou);
		dou = Double.valueOf(temp);
		return dou;
	}

	/**
	 * 测试stringToDouble方法
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		String str = "12.23";
//		int num = 6;
//		double dou = stringToDouble(str, num);
//		System.out.print(dou);
//	}


	/**
	 * 实现生成一个随机字符串方法
	 * 生成一个UUID去掉-符号并且返回
	 *
	 * @return 随机字符串
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
