package com.canyinghao.canhelper;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.widget.TextView;

/**
 * 其它的一些工具类
 * @author canyinghao
 *
 */
public class OtherHelper {

	private static OtherHelper util;

	synchronized public static OtherHelper getInstance() {

		if (util == null) {
			util = new OtherHelper();

		}
		return util;

	}

	private OtherHelper() {
		super();
	}

	/**
	 * 打开微信
	 * 
	 * @param context
	 * @param url
	 * @return
	 */
	public boolean openWeChat(Context context, String url) {
		if (PhoneHelper.getInstance().isInstall("com.tencent.mm")) {
			try {
				Intent localIntent = new Intent("android.intent.action.VIEW");
				localIntent.setData(Uri.parse(url));
				localIntent.setPackage("com.tencent.mm");

				context.startActivity(localIntent);
				if (context instanceof Activity)
					((Activity) context).overridePendingTransition(
							android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				return true;
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}
		}
		return false;
	}

	/**
	 * 直接分享到微信
	 * 
	 * @param context
	 * @param text
	 * @param subject
	 * @param title
	 * @param isFriend
	 *            是分享到好友还是朋友圈
	 */
	public void shareToWeChat(Context context, String text, String subject,
			String title, boolean isFriend) {
		Intent intent = new Intent();
		String str = "";
		if (isFriend) {
			str = "com.tencent.mm.ui.tools.ShareImgUI";
		} else {
			str = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
		}
		ComponentName comp = new ComponentName("com.tencent.mm", str);
		intent.setComponent(comp);
		intent.setAction("android.intent.action.SEND");
		intent.setType("text/*");
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TITLE, title);

		// intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		context.startActivity(intent);
	}

	/**
	 * 得到随机颜色
	 * 
	 * @return
	 */
	public String getRandomColor() {

		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return "#" + r + g + b;

	}

	/**
	 * 在TextView中插入图片
	 * 
	 * @param context
	 * @param tv
	 * @param rid
	 * @param size
	 */
	public void textViewInImag(final Context context, TextView tv, int rid,
			final int size) {

		String imgStr = "<img src=\"" + rid + "\"/>";
		Html.ImageGetter imageGetter = new Html.ImageGetter() {
			public Drawable getDrawable(String arg0) {

				int id = Integer.parseInt(arg0);
				Drawable draw = context.getResources().getDrawable(id);
				draw.setBounds(0, 0, size, size);
				return draw;
			}
		};
		tv.append(Html.fromHtml(imgStr, imageGetter, null));
	}

	/**
	 * 当前应用是否在后台运行
	 * 
	 * @param context
	 * @return
	 */
	public boolean isAppRunBackground(Context context) {

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

	/**
	 * 当前应用是否在后台运行 需要权限<uses-permission
	 * android:name="android.permission.GET_TASKS" />
	 * 
	 * @param context
	 * @return
	 */
	public boolean isAppRunBackGround(final Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 后台服务是否在运行
	 * 
	 * @param context
	 * @param cl
	 * @return
	 */
	public boolean isServiceRunning(Context context, String serName) {
		boolean isRunning = false;

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if (serviceList == null || serviceList.size() == 0) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(serName)) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * activity是否在运行
	 * 
	 * @param context
	 * @param className
	 * @return
	 */
	public boolean isActivityRunning(Context context, String className) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> appList = activityManager
				.getRunningTasks(1000);
		for (RunningTaskInfo running : appList) {

			if (className.equals(running.baseActivity.getClassName())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 最近是否运行过App
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean isRecentAppRunning(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RecentTaskInfo> appList = activityManager
				.getRecentTasks(100, 1);
		for (ActivityManager.RecentTaskInfo running : appList) {

			if (running.origActivity.getPackageName().equals(packageName)) {
				return true;
			}
		}
		return false;

	}

}
