package com.pengheng.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.pengheng.util.security.MD5Util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {

	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			out = new FileOutputStream(path);
			out.write(b);
			out.flush();

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static void main(String[] args) {
		Long starttime = System.currentTimeMillis();
		String decode = Base64Utils.getImageStr("C:\\Users\\admin\\Desktop\\11.jpg");
		Base64Utils.generateImage(decode, "C:\\\\Users\\\\admin\\\\Desktop\\\\111.jpg");
		String a = MD5Util.string2MD5(decode);
		String decode2 = Base64Utils.getImageStr("C:\\Users\\admin\\Desktop\\111.jpg");
		String b = MD5Util.string2MD5(decode2);
		System.out.println(a.equals(b));
//		System.out.println(decode.equals(decode));
		Long endtime = System.currentTimeMillis();
		System.out.println(endtime-starttime);

	}
}
