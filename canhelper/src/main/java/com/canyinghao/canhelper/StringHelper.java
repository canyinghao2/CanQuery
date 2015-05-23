package com.canyinghao.canhelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 字符串工具类
 * @author canyinghao
 *
 */
public class StringHelper {

	private static StringHelper util;

	public final String[] PHONE_PREFIX = new String[] { "130", "131", "132",
			"133", "134", "135", "136", "137", "138", "139", "150", "151",
			"152", "153", "154", "155", "156", "157", "158", "159", "180",
			"181", "182", "183", "184", "185", "186", "187", "188", "189" };

	synchronized public static StringHelper getInstance() {

		if (util == null) {
			util = new StringHelper();

		}
		return util;

	}

	private StringHelper() {
		super();
	}

	/**
	 * InputStream转byte数组
	 * 
	 * @param is
	 * @return
	 */
	public byte[] stream2Byte(InputStream is) {

		byte[] in_b = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			int i = -1;
			while ((i = is.read()) != -1) {
				os.write(i);

			}

			in_b = os.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in_b;

	}

	/**
	 * InputStream 转String
	 * 
	 * @param is
	 * @return
	 */
	public String stream2String(InputStream is) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringWriter sw = new StringWriter();
			int i = -1;
			while ((i = br.read()) != -1) {
				sw.write(i);
			}
			return sw.getBuffer().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	/**
	 * 字符串转InputStream
	 * 
	 * @param str
	 * @return
	 */
	public InputStream string2Stream(String str) {

		ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());

		return is;
	}

	/**
	 * byte数组转InputStream
	 * 
	 * @param by
	 * @return
	 */
	public InputStream byte2Stream(byte[] by) {

		ByteArrayInputStream stream = new ByteArrayInputStream(by);

		return stream;
	}

	/**
	 * 判断是否是中文字符,只能判断部分CJK字符
	 * 
	 * @param str
	 * @return
	 */
	public boolean isChina(String str) {
		Pattern p = Pattern.compile("[\\u4E00-\\u9FBF]+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 根据Unicode编码完美的判断中文汉字和符号
	 * 
	 * @param c
	 * @return
	 */
	private boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否包含中文汉字
	 * 
	 * @param strName
	 * @return
	 */
	public boolean isChineseHave(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否只有中文汉字
	 * 
	 * @param strName
	 * @return
	 */
	public boolean isChineseAll(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断邮政编码
	 * 
	 * @param str
	 * @return
	 */
	public boolean isCard(String str) {
		Pattern p = Pattern.compile("[1-9]\\d{5}(?!\\d)");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 检测邮箱合法性
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmail(String email) {
		if ((email == null) || (email.trim().length() == 0)) {
			return false;
		}
		String regEx = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(email.trim().toLowerCase());

		return m.find();
	}

	/**
	 * 检查手机号码合法性
	 * 
	 * @param mdn
	 * @return
	 */
	public boolean isPhoneNumber(String mdn, boolean checkLen) {
		if (mdn == null || mdn.equals("")) {
			return false;
		}

		if (mdn.startsWith("+86")) {
			mdn = mdn.substring(3);
		}
		if (checkLen && mdn.length() != 11) {
			return false;
		}
		boolean flag = false;
		String p = mdn.length() > 3 ? mdn.substring(0, 3) : mdn;
		for (int i = 0; i < PHONE_PREFIX.length; i++) {
			if (p.equals(PHONE_PREFIX[i])) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串中是否是整型数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isInt(String str) {
		if (!TextUtils.isEmpty(str)) {
			return str.toString().matches("^[-]?\\d+$");
		}

		return false;
	}

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 * @return
	 */
	public int getInt(String str) {
		if (!TextUtils.isEmpty(str)) {
			if (!isInt(str)) {
				return 0;
			}
			return Integer.parseInt(str);
		}
		return 0;
	}

	/**
	 * 判断字符串中是否是Double
	 * 
	 * @param str
	 * @return
	 */
	public boolean isDouble(String str) {
		if (!TextUtils.isEmpty(str)) {
			return str.toString().matches("^\\d+(\\.[\\d]+)?$");
		}
		return false;
	}

	/**
	 * 字符串转Double
	 * 
	 * @param str
	 * @return
	 */
	public double getDouble(String str) {
		if (!TextUtils.isEmpty(str)) {
			if (isDouble(str))
				return Double.parseDouble(str);
			else
				return 0.0D;
		}
		return 0.0D;
	}

	/**
	 * 字符串转Double
	 * 
	 * @param v
	 * @param scale
	 * @return
	 */
	public double getDouble(Double v, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(
				Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
