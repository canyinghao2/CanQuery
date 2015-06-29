package com.canyinghao.canquery.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.canyinghao.canhelper.JsonHelper;

public class JsonUtil {

	

	public static Map<String, String> getNewApiJson(String json) {

		Map<String, String> map = new HashMap<String, String>();

		try {
			String code = "";
			String message = "";
			String list = "";
			if (JsonHelper.getInstance().isGoodJson(json)) {
				JSONObject jObject = new JSONObject(json);
				if (jObject.has("code")) {
					code = jObject.optString("code");
				}
				if (jObject.has("message")) {
					message = jObject.optString("message");
				}

				if (jObject.has("list")) {
					list = jObject.optString("list");
				}
			}

			map.put("code", code);
			map.put("message", message);
			map.put("list", list);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return map;

	}

	public static Map<String, String> getNewApiJsonQuery(String json) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			String code = "";
			String message = "";
			String list = "";

			if (JsonHelper.getInstance().isGoodJson(json)) {

				JSONObject jObject = new JSONObject(json);

				if (jObject.has("resultcode")) {
					code = jObject.optString("resultcode");
				}
				if (jObject.has("reason")) {
					message = jObject.optString("reason");
				}

				if (jObject.has("result")) {
					list = jObject.optString("result");
					
				}
				if (jObject.has("error_code")) {
					int error_code = jObject.optInt("error_code");
					if (error_code>0) {
						
						
					}
				}

			}
			map.put("code", code);
			map.put("message", message);
			map.put("list", list);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;

	}

	
}
