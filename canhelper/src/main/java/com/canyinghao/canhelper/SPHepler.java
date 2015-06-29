package com.canyinghao.canhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences工具类
 * 
 * @author canyinghao
 * 
 */
public class SPHepler {
	private static Context context = CanHelper.getApp();
	private static SPHepler util;
	private static String name = "";

	synchronized public static SPHepler getInstance() {

		if (util == null) {
			util = new SPHepler();
			name = context.getPackageName();

		}
		return util;

	}

	private SPHepler() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInt(String key) {

		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);

		return sp.getInt(key, 0);
	}

	public void setInt(String key, int num) {

		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, num);
		editor.commit();

	}

	public String getString(String key) {

		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);

		return sp.getString(key, "");
	}

	public void setString(String key, String str) {

		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, str);

		editor.commit();

	}
}
