package com.canyinghao.canhelper;

import java.io.Serializable;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 异步任务的便捷使用工具类
 * @author canyinghao
 *
 */
public class ThreadHelper {

	private static ThreadHelper util;

	synchronized public static ThreadHelper getInstance() {

		if (util == null) {
			util = new ThreadHelper();

		}
		return util;

	}

	private ThreadHelper() {
		super();
	}

	

	public void runAsync(ThreadCallBack tcb) {

		new ThreadAsync().execute(tcb);

	}

	public class ThreadAsync extends AsyncTask<Object, Integer, Object> {
		ThreadCallBack tcb;

		@Override
		protected Object doInBackground(Object... tc) {

			if (tc != null && tc.length > 0) {
				tcb = (ThreadCallBack) tc[0];

				return tcb.run(this);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (tcb != null) {
				tcb.result((Serializable) result);
			}

		}

		public void progress(Integer... values) {
			publishProgress(values);

		}

		@Override
		public void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if (tcb != null) {
				tcb.progress(values);
			}
		}

	}

	public interface ThreadCallBack {

		void progress(Integer... values);

		Object run(ThreadAsync at);

		void result(Object result);

	}

	public interface ThreadLocalCallBack {

		Object run();

	}

	public boolean isServiceRunning(Context context, Class cl) {
		boolean isRunning = false;

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if (serviceList == null || serviceList.size() == 0) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(cl.getName())) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	public boolean isRunBackground(Context context) {

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {

					return true;
				} else {

					return false;
				}
			}
		}
		return false;
	}

}
