package com.canyinghao.canhelper;

import java.util.Calendar;

/**
 * 星座属相工具类
 * @author canyinghao
 *
 */
public class ConstellationHepler {

	private static ConstellationHepler util;

	synchronized public static ConstellationHepler getInstance() {

		if (util == null) {
			util = new ConstellationHepler();

		}
		return util;

	}

	private ConstellationHepler() {
		super();
	}

	public final String[] zodiacArray = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎",
			"兔", "龙", "蛇", "马", "羊" };

	public final String[] constellationArray = { "水瓶座", "双鱼座", "牡羊座", "金牛座",
			"双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

	public final int[] constellationDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23,
			24, 23, 22 };

	/**
	 * 根据出生年份获取生肖
	 * 
	 * @param year
	 * @return
	 */
	public String date2Zodica(int year) {

		return zodiacArray[year % 12];
	}

	/**
	 * 根据日期获取星座
	 * 
	 * @param month
	 * @param day
	 * @return
	 */
	public String date2Constellation(int month, int day) {

		month = month - 1;
		if (day < constellationDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArray[month];
		}
		// 默认魔羯
		return constellationArray[11];
	}

	/**
	 * 根据出生年份得到年龄
	 * 
	 * @param year
	 * @return
	 */
	public int date2Age(int year) {

		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int age = y - year;

		if (age < 0) {
			age = 0;
		}
		return age;

	}

}
