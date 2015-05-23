package com.canyinghao.canquery.configs;

import com.canyinghao.canhelper.StringHelper;
import com.canyinghao.canquery.R;

import java.util.HashMap;
import java.util.Map;

public class QueryConfigs {

    public static  String Version="6.0";

	private static Map<Integer, Integer> weatherMap;


    public static final  String SWIPE="swipe";

	public static final String PUBLISHER_ID = "56OJxaUouN7Dd5cxoU";
	public static final String InlinePPID = "16TLe9kvAp8g4NUdtZAsyfZs";
	public static final String InterstitialPPID = "16TLwebvAchksY6iOa7F4DXs";
	public static final String SplashPPID = "16TLwebvAchksY6iOGe3xcik";
	
	public static Map<Integer, Integer> getWeatherMap() {
		if (weatherMap == null) {
			weatherMap = getWeatherMapList();
		}
		return weatherMap;

	}




	private static Map<Integer, Integer> getWeatherMapList() {
		if (weatherMap == null || weatherMap.size() == 0) {
			weatherMap = new HashMap<Integer, Integer>();
			weatherMap.put(0, R.drawable.query_weather_fine);
			weatherMap.put(1, R.drawable.query_weather_cloud);
			weatherMap.put(2, R.drawable.query_weather_cloudy);
			weatherMap.put(3, R.drawable.query_weather_rain_3);
			weatherMap.put(4, R.drawable.query_weather_rain_3);
			weatherMap.put(5, R.drawable.query_weather_rain_2);
			weatherMap.put(6, R.drawable.query_weather_snow);
			weatherMap.put(7, R.drawable.query_weather_rain);
			weatherMap.put(8, R.drawable.query_weather_rain_1);
			weatherMap.put(9, R.drawable.query_weather_rain_2);
			weatherMap.put(10, R.drawable.query_weather_rain_2);
			weatherMap.put(11, R.drawable.query_weather_rain_2);
			weatherMap.put(12, R.drawable.query_weather_rain_2);
			weatherMap.put(13, R.drawable.query_weather_snow_1);
			weatherMap.put(14, R.drawable.query_weather_snow_1);
			weatherMap.put(15, R.drawable.query_weather_snow);
			weatherMap.put(16, R.drawable.query_weather_snow);
			weatherMap.put(17, R.drawable.query_weather_snow);
			weatherMap.put(18, R.drawable.query_weather_snow);
			weatherMap.put(19, R.drawable.query_weather_rain_2);
			weatherMap.put(20, R.drawable.query_weather_dust);
			weatherMap.put(21, R.drawable.query_weather_rain);
			weatherMap.put(22, R.drawable.query_weather_rain_1);
			weatherMap.put(23, R.drawable.query_weather_rain_2);
			weatherMap.put(24, R.drawable.query_weather_rain_3);
			weatherMap.put(25, R.drawable.query_weather_flash);
			weatherMap.put(26, R.drawable.query_weather_snow_1);
			weatherMap.put(27, R.drawable.query_weather_snow);
			weatherMap.put(28, R.drawable.query_weather_snow);
			weatherMap.put(29, R.drawable.query_weather_wind);
			weatherMap.put(30, R.drawable.query_weather_dust);
			weatherMap.put(31, R.drawable.query_weather_dust);
		}
		return weatherMap;
	}





	public static int getWeatherRid(String str) {
		int img = StringHelper.getInstance().getInt(str);
		if (img >= getWeatherMap().size()) {
			img = 0;
		}

		Integer integer = getWeatherMap().get(img);
		return integer;

	}
}
