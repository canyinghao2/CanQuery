package com.canyinghao.canquery.net;

import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

public class JuheWeb {

	public static void getJuheData(Parameters params, int apiid, String api,
			String method, final JuheRequestCallBack callback) {

		params.add("dtype", "json");

		if (!PhoneHelper.getInstance().isNetworkConnected()) {

			callback.requestEnd();
			callback.fail(0, "", "");
            callback.success(100, "no net", "");
			return;
		}
		JuheData.executeWithAPI(apiid, api, method, params, new DataCallBack() {

			/**
			 * @param err
			 *            错误码,0为成功
			 * @param reason
			 *            原因
			 * @param result
			 *            数据
			 */
			@Override
			public void resultLoaded(int err, String reason, String result) {
				// TODO Auto-generated method stub

				LogHelper.logd("err" + err);
				if (err == 0) {
					LogHelper.logd(result.toString());
					callback.success(err, reason, result);

				} else {
					PhoneHelper.getInstance().show(reason);
					callback.fail(err, reason, result);

				}
				callback.requestEnd();

			}
		});

	}

	public interface JuheRequestCallBack {

		void success(int err, String reason, String result);

		void requestEnd();

		void fail(int err, String reason, String result);

	}
}
