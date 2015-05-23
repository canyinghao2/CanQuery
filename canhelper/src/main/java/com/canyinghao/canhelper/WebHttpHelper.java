package com.canyinghao.canhelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.text.TextUtils;
import android.util.Log;

/**
 * 简易网络工具类
 * @author canyinghao
 *
 */
public class WebHttpHelper {

	private static WebHttpHelper util;

	synchronized public static WebHttpHelper getInstance() {

		if (util == null) {
			util = new WebHttpHelper();
		}
		return util;

	}

	private WebHttpHelper() {
		super();
	}

	public InputStream getInputStream(String urlS, Map<String, String> param,
			boolean isGet) throws Exception {
		URL url = null;
		HttpURLConnection hc = null;
		StringBuffer request = new StringBuffer();

		try {

			String get = "GET";
			for (String key : param.keySet()) {

				request.append(key + "=" + param.get(key) + "&");
			}

			String newUrl = urlS;
			if (!TextUtils.isEmpty(request.toString())) {
				newUrl = urlS + "?" + request.toString();
			}

			Log.e("url", newUrl);
			if (!isGet) {
				get = "POST";
				url = new URL(urlS);

			} else {

				url = new URL(newUrl);

			}

			hc = (HttpURLConnection) url.openConnection();
			hc.setConnectTimeout(45000);
			// hc.setRequestProperty("Cookie", ConstUtil.COOKIEMESSAGE);

			hc.setRequestMethod(get);
			if (!isGet) {

				for (String key : param.keySet()) {

					hc.addRequestProperty(key, param.get(key));
				}
				hc.setDoOutput(true);
				hc.getOutputStream().flush();
				hc.getOutputStream().close();
			}

			hc.setInstanceFollowRedirects(false);

			hc.setDoInput(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hc.getInputStream();

	}

	public String getString(String urlS, Map<String, String> param,
			boolean isGet, String encoding) {
		if (param == null) {
			param = new HashMap<String, String>();
		}
		BufferedReader br = null;

		StringBuffer sb = new StringBuffer();

		try {
			br = new BufferedReader(new InputStreamReader(getInputStream(urlS,
					param, isGet), encoding));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
