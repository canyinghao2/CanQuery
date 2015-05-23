package com.canyinghao.canhelper;

import android.app.Application;

/**
 * 用来初始化
 * @author canyinghao
 *
 */
public class CanHelper {

	private static Application app;

	/**
	 * 初始化CanHelper
	 * @param application
	 */
	public static void init(Application application) {
		app = application;

	}

	public static Application getApp() {
		if (app==null) {
			
			LogHelper.logv("CanHelper", "init error");
		}
		return app;
          
	}

}
