package com.pengheng.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandPwdUtil {

	public static String getRandPwd() {
		String[] num = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] lStr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		Random r = new Random();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			result.add(num[r.nextInt(10)]);
			result.add(lStr[r.nextInt(52)]);
		}
		Collections.shuffle(result);
		String str = "";
		for (String s : result) {
			str += s;
		}
		return str;
	}

//	public static void main(String[] args) {
//		String randPwd = getRandPwd();
//		System.out.println(randPwd);
//	}
}
