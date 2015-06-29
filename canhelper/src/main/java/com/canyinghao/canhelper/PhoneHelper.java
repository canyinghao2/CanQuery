package com.canyinghao.canhelper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * 与电话相关功能的工具类
 * 
 * @author canyinghao
 * 
 */
public class PhoneHelper {
	private Context context = CanHelper.getApp();
	private static PhoneHelper util;

	synchronized public static PhoneHelper getInstance() {
		if (util == null) {
			util = new PhoneHelper();
		}
		return util;

	}

	private PhoneHelper() {
		super();
	}

	/**
	 * 生产商家
	 * 
	 * @return
	 */
	public String getManufacturer() {
		return android.os.Build.MANUFACTURER;
	}

	/**
	 * 获得固件版本
	 * 
	 * @return
	 */
	public String getRelease() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获得手机型号
	 * 
	 * @return
	 */
	public String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获得手机品牌
	 * 
	 * @return
	 */
	public String getBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机运营商
	 */
	public String getSimOperatorName() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		return tm.getSimOperatorName();
	}

	/**
	 * 得到本机手机号码,未安装SIM卡或者SIM卡中未写入手机号，都会获取不到
	 * 
	 * @return
	 */
	public String getThisPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getLine1Number();

		return number;
	}

	/**
	 * 是否是电话号码
	 * 
	 * @param phonenumber
	 * @return
	 */
	public boolean isPhoneNumber(String phonenumber) {
		Pattern pa = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$");
		Matcher ma = pa.matcher(phonenumber);
		return ma.matches();
	}

	/**
	 * 打电话
	 * 
	 * @param phone
	 * @param context
	 */
	public void doPhone(String phone) {
		Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ phone));
		context.startActivity(phoneIntent);
	}

	/**
	 * 发短信
	 * 
	 * @param phone
	 * @param content
	 * @param c
	 */
	public void doSMS(String phone, String content) {
		Uri uri = null;
		if (!TextUtils.isEmpty(phone))
			uri = Uri.parse("smsto:" + phone);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

	/**
	 * 得到屏幕信息 getScreenDisplayMetrics().heightPixels 屏幕高
	 * getScreenDisplayMetrics().widthPixels 屏幕宽
	 * 
	 * @return
	 */
	public DisplayMetrics getScreenDisplayMetrics() {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		Display display = manager.getDefaultDisplay();
		display.getMetrics(displayMetrics);

		return displayMetrics;

	}

	/**
	 * 屏幕分辨率
	 * 
	 * @param drame
	 * @return
	 */
	public float getDip() {

		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * 状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public int getStatusBarHeight(Context context) {
		Class<?> c = null;

		Object obj = null;

		Field field = null;

		int x = 0, sbar = 0;

		try {

			c = Class.forName("com.android.internal.R$dimen");

			obj = c.newInstance();

			field = c.getField("status_bar_height");

			x = Integer.parseInt(field.get(obj).toString());

			sbar = context.getResources().getDimensionPixelSize(x);
			return sbar;

		} catch (Exception e1) {

			e1.printStackTrace();

		}
		return sbar;

	}

	/**
	 * 安装apk
	 */
	public void instance(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 是否安装了
	 * 
	 * @param packageName
	 * @return
	 */
	public boolean isInstall(String packageName) {
		PackageManager packageManager = context.getPackageManager();
		List<ApplicationInfo> packs = packageManager
				.getInstalledApplications(PackageManager.GET_ACTIVITIES);
		for (ApplicationInfo info : packs) {
			if (info.packageName.equals(packageName))
				return true;
		}
		return false;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnected();
	}

	/**
	 * 将Toast放在屏幕上方
	 * 
	 * @param message
	 */
	public void show(String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0,
				(getScreenDisplayMetrics().heightPixels / 5));
		toast.show();
	}

	/**
	 * 调用浏览器打开
	 * 
	 * @param activity
	 * @param url
	 */
	public void openWeb(String url) {
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

	}

	/**
	 * 是否有外存卡
	 * 
	 * @return
	 */
	public boolean isExistExternalStore() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到sd卡路径
	 * 
	 * @return
	 */
	public String getExternalStorePath() {
		if (isExistExternalStore()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	/**
	 * 得到网络类型，0是未知或未连上网络，1为WIFI，2为2g，3为3g，4为4g
	 * 
	 * @return
	 */
	public int getNetType() {
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		int type = 0;
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return type;
		}

		switch (info.getType()) {
		case ConnectivityManager.TYPE_WIFI:
			type = 1;
			break;
		case ConnectivityManager.TYPE_MOBILE:
			type = getNetworkClass(info.getSubtype());
			break;

		default:
			type = 0;
			break;
		}

		return type;
	}

	/**
	 * 判断数据连接的类型
	 * 
	 * @param networkType
	 * @return
	 */
	public int getNetworkClass(int networkType) {
		switch (networkType) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case TelephonyManager.NETWORK_TYPE_IDEN:
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:

			return 2;
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return 3;
		case TelephonyManager.NETWORK_TYPE_LTE:
			return 4;
		default:
			return 0;
		}
	}

	/**
	 * 开始震动
	 * 
	 * @param context
	 * @param repeat
	 *            0重复 -1不重复
	 * @param pattern
	 */
	@SuppressLint("NewApi")
	public synchronized void doVibrate(Context context, int repeat,
			long... pattern) {

		if (pattern == null) {
			pattern = new long[] { 1000, 1000, 1000 };
		}
		Vibrator mVibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (mVibrator.hasVibrator()) {
			mVibrator.vibrate(pattern, repeat);
		}

	}

	/**
	 * 建立一个NotificationCompat.Builder，并返回
	 * 
	 * @param contentTitle
	 * @param contentText
	 * @param contentInfo
	 * @param largeIcon
	 * @param smallIcon
	 * @param contentclass
	 * @return
	 */
	public Builder getNotifyBuilder(String contentTitle, String contentText,
			String contentInfo, Bitmap largeIcon, int smallIcon,
			Class contentclass,
			PendingIntent deleteIntent,int defaults) {
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				new Intent(), 0);
		if (contentclass != null) {
			pendingIntent = PendingIntent.getActivity(context, 0, new Intent(
					context, contentclass), 0);
		}
		Builder builder = new Builder(context)
				.setLargeIcon(largeIcon).setSmallIcon(smallIcon)
				.setContentInfo(contentInfo).setContentTitle(contentTitle)
				.setContentText(contentText).setAutoCancel(true)
				.setContentIntent(pendingIntent).setDeleteIntent(deleteIntent)
				.setDefaults(defaults);
		return builder;

	}

	/**
	 * 建一个带有ProgressBar的NotificationCompat.Builder，并返回
	 * 
	 * @param contentTitle
	 * @param contentText
	 * @param contentInfo
	 * @param largeIcon
	 * @param smallIcon
	 * @param contentclass
	 * @param max
	 * @param progress
	 * @param indeterminate
	 * @return
	 */
	public Builder getNotifyBuilderProgress(String contentTitle,
			String contentText, String contentInfo, Bitmap largeIcon,
			int smallIcon, Class contentclass, int max, int progress,
			boolean indeterminate,
			PendingIntent deleteIntent,int defaults) {
		Builder builder = getNotifyBuilder(contentTitle, contentText,
				contentInfo, largeIcon, smallIcon, contentclass,deleteIntent,defaults);

		builder.setProgress(max, progress, indeterminate);
				
				
		return builder;

	}

	/**
	 * 普通的Notification
	 * 
	 * @param builder
	 * @param notifyId
	 */
	public void showNotifyNormal(Builder builder, int notifyId) {

		Notification notification = builder.build();

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(notifyId, notification);

	}

	/**
	 * 大布局Notification
	 * 
	 * @param style
	 * @param builder
	 * @param notifyId
	 */
	public void showNotifyBigView(NotificationCompat.Style style,
			Builder builder, int notifyId) {
		builder.setStyle(style);

		Notification notification = builder.build();

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(notifyId, notification);
	}

	/**
	 * BigTextStyle风格的Notification
	 * 
	 * @param bigContentTitle
	 * @param summaryText
	 * @param bigText
	 * @param builder
	 * @param notifyId
	 */
	public void showNotifyBigText(String bigContentTitle, String summaryText,
			String bigText, Builder builder, int notifyId) {

		BigTextStyle textStyle = new BigTextStyle();
		textStyle.setBigContentTitle(bigContentTitle)
				.setSummaryText(summaryText).bigText(bigText);
		showNotifyBigView(textStyle, builder, notifyId);
	}

	/**
	 * BigPictureStyle风格的Notification
	 * 
	 * @param bigContentTitle
	 * @param summaryText
	 * @param bigPicture
	 * @param builder
	 * @param notifyId
	 */
	public void showNotifyBigPic(String bigContentTitle, String summaryText,
			Bitmap bigPicture, Builder builder, int notifyId) {

		BigPictureStyle pictureStyle = new BigPictureStyle();
		pictureStyle.setBigContentTitle(bigContentTitle)
				.setSummaryText(summaryText).bigPicture(bigPicture);
		showNotifyBigView(pictureStyle, builder, notifyId);
	}

	/**
	 * InboxStyle风格的Notification
	 * 
	 * @param bigContentTitle
	 * @param summaryText
	 * @param line
	 * @param builder
	 * @param notifyId
	 */
	public void showNotifyBigInbox(String bigContentTitle, String summaryText,
			String[] line, Builder builder, int notifyId) {

		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		inboxStyle.setBigContentTitle(bigContentTitle).setSummaryText(
				summaryText);
		for (int i = 0; i < line.length; i++) {
			inboxStyle.addLine(line[i]);
		}

		showNotifyBigView(inboxStyle, builder, notifyId);
	}

	/**
	 * 自定义Notification
	 * 
	 * @param layoutId
	 * @param contentclass
	 * @param smallIcon
	 * @param clickCla
	 * @param clickId
	 * @param notifyId
	 */
	public void showNotifyCustomView(int layoutId, Class contentclass,
			int smallIcon, Class clickCla, int clickId, int notifyId) {
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				layoutId);

		if (clickCla != null) {
			Intent intent = new Intent(context, clickCla);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);
			remoteViews.setOnClickPendingIntent(clickId, pendingIntent);
		}

		Builder builder = getNotifyBuilder("", "", "", null, smallIcon,
				contentclass,null,Notification.DEFAULT_ALL).setContent(remoteViews);
		manager.notify(notifyId, builder.build());
	}

}
