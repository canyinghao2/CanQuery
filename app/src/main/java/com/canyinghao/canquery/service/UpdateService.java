package com.canyinghao.canquery.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.widget.TextView;

import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canhelper.StringHelper;
import com.canyinghao.canhelper.ThreadHelper;
import com.canyinghao.canhelper.ThreadHelper.ThreadAsync;
import com.canyinghao.canhelper.ThreadHelper.ThreadCallBack;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

public class UpdateService extends Service {
	public static String TAG = "com.canyinghao.canquery.service.UpdateService";
	private UpdateBinder uUpdateBinder = new UpdateBinder();

	public static final String ACTION_CANCEL = "com.cancel";

	private boolean isCancel;

	public InternalReceiver internalReceiver = null;

	class InternalReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent != null && intent.getAction().equals(ACTION_CANCEL)) {
				isCancel = true;
				NotificationManager manager = (NotificationManager) 
						getSystemService(Context.NOTIFICATION_SERVICE);
				manager.cancel(1);
			}

		}
	}

	public class UpdateBinder extends Binder {
		public UpdateService getService() {
			return UpdateService.this;
		}
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter intentfilter = new IntentFilter();
		
		intentfilter.addAction(ACTION_CANCEL);
		if (internalReceiver == null) {
			internalReceiver = new InternalReceiver();
		}
		registerReceiver(internalReceiver, intentfilter);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent != null && intent.hasExtra("update")) {

			UpDateInfo info = (UpDateInfo) intent
					.getSerializableExtra("update");
			download(info.getDownloadUrl(), info.getVersion(), info.getName(),
					"YdaPhone");
		}
		download("", "", "", "YdaPhone");
		return START_NOT_STICKY;

	}

	@Override
	public IBinder onBind(Intent intent) {
		return uUpdateBinder;
	}

	private void downloadNotify(int pro, int defaults,String message) {
		if (TextUtils.isEmpty(message)) {
			message="下载中...";
		}
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 10, new Intent(ACTION_CANCEL), PendingIntent.FLAG_UPDATE_CURRENT);
		Builder builder = PhoneHelper.getInstance().getNotifyBuilderProgress(
				getResources().getString(R.string.app_name), message,
				"download", null, R.drawable.canquery, null, 100, pro,
				false, pendingIntent, defaults);
		PhoneHelper.getInstance().showNotifyBigInbox(
				getResources().getString(R.string.app_name), message,
				new String[] { "1.ddd", "2.ldd" }, builder, 1);
	}

	/**
	 * 下载新版本
	 * 
	 * @param url
	 */
	public void download(String url, final String serVer, final String name,
			String apkName) {

		downloadNotify(0, Notification.DEFAULT_ALL,null);
		url = "http://down.yda360.com/Mall.apk";
		HttpUtils http = new HttpUtils(10000);
		http.download(url, "/sdcard/xx.apk", false, false,
				new RequestCallBack<File>() {

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {

						if (!isCancel) {
							downloadNotify(100, Notification.DEFAULT_LIGHTS,"下载成功");
							instance(new File("/sdcard/xx.apk"));
						}

						stopSelf();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);

						if (!isCancel) {
							float pr = ((current / (float) total) * 100);

							downloadNotify((int) pr,
									Notification.DEFAULT_LIGHTS,null);
						}

					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						
						if (!isCancel) {
							downloadNotify(0,
									Notification.DEFAULT_LIGHTS,"下载失败！");
						}
						stopSelf();
					}
				});

	}

	/**
	 * 安装
	 */
	public void instance(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public static void updateApp(final Context context, boolean isShow,
			final TextView tv) {

		ThreadHelper.getInstance().runAsync(new ThreadCallBack() {

			@Override
			public Object run(ThreadAsync arg0) {
				String string = "";
				try {
					URL aURL = new URL(
							"http://caying.duapp.com/app/android/canquery.json"
									+ "?r=" + System.currentTimeMillis());
					URLConnection con = aURL.openConnection();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(con.getInputStream(), Charset
									.forName("utf-8")));

					while (br.ready()) {

						String readLine = br.readLine();
						System.out.println(readLine);
						string += readLine;
					}
					br.close();

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!TextUtils.isEmpty(string)) {

					List<UpDateInfo> persons = JsonHelper.getInstance()
							.getList(string, new TypeToken<List<UpDateInfo>>() {
							});

					if (persons == null) {
						return null;
					}

					for (UpDateInfo info : persons) {

						if (info.getName().equals(
								context.getResources().getString(
										R.string.app_name))) {

							return info;

						}

					}

				}
				return null;
			}

			@Override
			public void result(Object arg0) {
				if (null == arg0)
					return;
				final UpDateInfo info = (UpDateInfo) arg0;

				int ver = StringHelper.getInstance().getInt(
						info.getVersion().replaceAll("\\.", ""));
				int lver = StringHelper.getInstance().getInt(
						"1.0".replaceAll("\\.", ""));
				if (lver >= ver) {
					return;
				}
				if (tv != null) {
					tv.setText("有新版本");
					return;
				}

				if (context instanceof Activity) {
					Activity ac = (Activity) context;
					if (ac.isFinishing()) {
						return;
					}
				}
				String text = "";
				// String text = "注：适配安卓4.0及以上版本！\n";
				String message = "发现新版本，是否去下载？\n" + "当前版本：" + "" + "\n最新版本："
						+ info.getVersion() + "\n" + text + "更新时间："
						+ info.getUpdateTime() + "\n" + info.getUpdateInfo();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setMessage(message);
				builder.setNegativeButton("xx", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent(UpdateService.TAG);
						intent.putExtra("update", info);
						App.getInstance().startService(intent);

					}
				});
				builder.show();

				// new CustomDialog("提示", message, context, "下载", "取消",
				// new OnClickListener() {
				//
				// @Override
				// public void onClick(View arg0) {
				//
				// Intent intent = new Intent(UpdateService.TAG);
				// intent.putExtra("update", info);
				// App.getInstance().startService(intent);
				// }
				// }, null).show();

			}

			@Override
			public void progress(Integer... arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println(TAG + "onDestroy");
		if (internalReceiver != null) {
			unregisterReceiver(internalReceiver);
		}
		

	}
}
