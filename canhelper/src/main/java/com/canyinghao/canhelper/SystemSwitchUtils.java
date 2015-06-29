package com.canyinghao.canhelper;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * 一些系统的开关方法
 * @author canyinghao
 *
 */
public class SystemSwitchUtils {
    private Context context;
    private WifiManager mWifiManager;
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectivityManager connManager;
    private PowerManager mPowerManager;
    private AudioManager mAudioManager;
    private static Camera camera;

    private final int LIGHT_NORMAL = 64;
    private final int LIGHT_50_PERCENT = 127;
    private final int LIGHT_75_PERCENT = 191;
    private final int LIGHT_100_PERCENT = 255;
    private final int LIGHT_AUTO = 0;
    private final int LIGHT_ERR = -1;

    private static SystemSwitchUtils util;

    public static void getInstance(Context context){
        if (util==null) {
            util=new SystemSwitchUtils(context);
        }


    }

    private SystemSwitchUtils(Context context) {
        super();
        this.context = context;
    }

    /**
     * wifi是否开启
     * @return
     */
    public boolean isWifiOn() {
        if (mWifiManager == null) {
            mWifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
        }
        return mWifiManager.isWifiEnabled();
    }

    /**
     * wifi开关
     */
    public void wifiUtils() {

        if (isWifiOn()) {
            // 关闭Wifi，按钮显示开启
            mWifiManager.setWifiEnabled(false);
            Toast.makeText(context, "关闭wifi", Toast.LENGTH_SHORT).show();
        } else {
            // 开启Wifi，按钮显示关闭
            mWifiManager.setWifiEnabled(true);

            Toast.makeText(context, "开启wifi", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 蓝牙是否开启
     * @return
     */
    public boolean isBlueToothOn() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
         switch (mBluetoothAdapter.getState()) {
         case BluetoothAdapter.STATE_ON:
         return true;

         case BluetoothAdapter.STATE_TURNING_ON:
         return true;
         case BluetoothAdapter.STATE_OFF:
         return false;
         case BluetoothAdapter.STATE_TURNING_OFF:
         return false;
         }
        return false;
    }

    /**
     * 蓝牙开关
     */
    public void bluetoothUtils() {

        if (isBlueToothOn()) {
            mBluetoothAdapter.disable();
            Toast.makeText(context, "关闭蓝牙", Toast.LENGTH_SHORT).show();
        } else {
            mBluetoothAdapter.enable();
            Toast.makeText(context, "开启蓝牙", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 是否开启自动旋转
     * @return
     */
    public boolean isRotationOn() {
        int status = 0;

        try {
            status = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION);
        } catch (SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 设置status的值改变屏幕旋转设置
        if (status == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 屏幕旋转开关
     */
    public void rotationUtils() {
        int status = 0;

        Uri uri = Settings.System
                .getUriFor("accelerometer_rotation");
        // 设置status的值改变屏幕旋转设置
        if (!isRotationOn()) {
            status = 1;
            Toast.makeText(context, "开启旋转", Toast.LENGTH_SHORT).show();
        } else if (status == 1) {
            status = 0;
            Toast.makeText(context, "关闭旋转", Toast.LENGTH_SHORT).show();
        }
        Settings.System.putInt(context.getContentResolver(),
                "accelerometer_rotation", status);
        // 通知改变
        context.getContentResolver().notifyChange(uri, null);

    }

    /**
     * 是否开启同步
     * @return
     */
    @SuppressWarnings("deprecation")
    public boolean isSyncSwitchOn() {
        if (connManager == null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        return connManager.getBackgroundDataSetting()
                && ContentResolver.getMasterSyncAutomatically();
    }

    /**
     * 同步开关
     */
    public void syncSwitchUtils() {

        if (isSyncSwitchOn()) {
            Toast.makeText(context, "关闭同步", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "开启同步", Toast.LENGTH_SHORT).show();
        }
        ContentResolver.setMasterSyncAutomatically(!isSyncSwitchOn());

    }

    /**
     * 屏幕亮度切换
     */
    public void brightnessSwitchUtils() {

        int light = 0;
        ContentResolver cr = context.getContentResolver();
        try {
            boolean auto = Settings.System.getInt(cr,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

            if (!auto) {
                light = Settings.System.getInt(cr,
                        Settings.System.SCREEN_BRIGHTNESS, -1);
                if (light > 0 && light <= LIGHT_NORMAL) {
                    light = LIGHT_NORMAL;
                } else if (light > LIGHT_NORMAL && light <= LIGHT_50_PERCENT) {
                    light = LIGHT_50_PERCENT;
                } else if (light > LIGHT_50_PERCENT
                        && light <= LIGHT_75_PERCENT) {
                    light = LIGHT_75_PERCENT;
                } else {
                    light = LIGHT_100_PERCENT;
                }
            } else {
                light = LIGHT_AUTO;
            }

            switch (light) {
            case LIGHT_NORMAL:
                light = LIGHT_50_PERCENT - 1;
                Toast.makeText(context, "正常亮度", Toast.LENGTH_SHORT).show();
                break;
            case LIGHT_50_PERCENT:
                light = LIGHT_75_PERCENT - 1;
                Toast.makeText(context, "较高亮度", Toast.LENGTH_SHORT).show();
                break;
            case LIGHT_75_PERCENT:
                light = LIGHT_100_PERCENT - 1;
                Toast.makeText(context, "高亮度", Toast.LENGTH_SHORT).show();
                break;
            case LIGHT_100_PERCENT:
                light = LIGHT_NORMAL - 1;
                Settings.System.putInt(cr,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                Toast.makeText(context, "自动亮度", Toast.LENGTH_SHORT).show();

                break;
            case LIGHT_AUTO:
                light = LIGHT_NORMAL - 1;
                Settings.System.putInt(cr,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                Toast.makeText(context, "低亮度", Toast.LENGTH_SHORT).show();

                break;
            case LIGHT_ERR:
                light = LIGHT_NORMAL - 1;
                break;

            }

            setLight(light);
            Settings.System.putInt(cr,
                    Settings.System.SCREEN_BRIGHTNESS, light);

        } catch (SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 设置屏幕亮度
     * @param light
     */
    private void setLight(int light) {
        try {
            if (mPowerManager == null) {
                mPowerManager = (PowerManager) context
                        .getSystemService(Context.POWER_SERVICE);
            }

            Class<?> pmClass = Class
                    .forName(mPowerManager.getClass().getName());
            // 得到PowerManager类中的成员mService（mService为PowerManagerService类型）
            Field field = pmClass.getDeclaredField("mService");
            field.setAccessible(true);
            // 实例化mService
            Object iPM = field.get(mPowerManager);
            // 得到PowerManagerService对应的Class对象
            Class<?> iPMClass = Class.forName(iPM.getClass().getName());
            /*
             * 得到PowerManagerService的函数setBacklightBrightness对应的Method对象，
             * PowerManager的函数setBacklightBrightness实现在PowerManagerService中
             */
            Method method = iPMClass.getDeclaredMethod(
                    "setBacklightBrightness", int.class);
            method.setAccessible(true);
            // 调用实现PowerManagerService的setBacklightBrightness
            method.invoke(iPM, light);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 是否开启飞行模式
     * @return
     */
    @SuppressWarnings("deprecation")
    public boolean isAirplaneModeOn() {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 飞行模式开关
     */
    @SuppressWarnings("deprecation")
    public void airplaneModeSwitchUtils() {
        boolean enable = isAirplaneModeOn();
        if (enable) {

            Toast.makeText(context, "关闭飞行模式", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(context, "开启飞行模式", Toast.LENGTH_SHORT).show();
        }
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, !enable ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", !enable);
        context.sendBroadcast(intent);

    }

    /**
     * 是否开启数据连接
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean isMobileDataOn() {
        Boolean isOpen = false;
        if (connManager == null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        try {
            String methodName = "getMobileDataEnabled";
            Class cmClass = connManager.getClass();

            Method method = cmClass.getMethod(methodName);
            isOpen = (Boolean) method.invoke(connManager);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isOpen;

    }

    /**
     * 数据连接开关
     */
    @SuppressWarnings({ "unused", "unchecked" })
    public void MobileDataSwitchUtils() {
        if (connManager == null) {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        try {

            String methodName = "getMobileDataEnabled";
            Class cmClass = connManager.getClass();
            // Boolean isOpen = null;
            Method method = cmClass.getMethod(methodName);

            // isOpen = (Boolean) method.invoke(connManager, null);

            Class<?> conMgrClass = Class.forName(connManager.getClass()
                    .getName());
            // 得到ConnectivityManager类的成员变量mService（ConnectivityService类型）
            Field iConMgrField = conMgrClass.getDeclaredField("mService");
            iConMgrField.setAccessible(true);
            // mService成员初始化
            Object iConMgr = iConMgrField.get(connManager);
            // 得到mService对应的Class对象
            Class<?> iConMgrClass = Class.forName(iConMgr.getClass().getName());
            /*
             * 得到mService的setMobileDataEnabled(该方法在android源码的ConnectivityService类中实现
             * )， 该方法的参数为布尔型，所以第二个参数为Boolean.TYPE
             */
            Method setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
                    "setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            /*
             * 调用ConnectivityManager的setMobileDataEnabled方法（方法是隐藏的），
             * 实际上该方法的实现是在ConnectivityService(系统服务实现类)中的
             */

            if (isMobileDataOn()) {
                Toast.makeText(context, "关闭数据连接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "开启数据连接", Toast.LENGTH_SHORT).show();
            }
            setMobileDataEnabledMethod.invoke(iConMgr, !isMobileDataOn());

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /**
     * 情景模式切换
     */
    public void silentSwitchUtils() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
        }
        int ringerMode = mAudioManager.getRingerMode();

        switch (ringerMode) {
        case AudioManager.RINGER_MODE_SILENT:
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            Toast.makeText(context, "震动模式", Toast.LENGTH_SHORT).show();
            break;
        case AudioManager.RINGER_MODE_NORMAL:
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Toast.makeText(context, "静音模式", Toast.LENGTH_SHORT).show();
            break;
        case AudioManager.RINGER_MODE_VIBRATE:
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(context, "正常模式", Toast.LENGTH_SHORT).show();
            break;
        }

    }

    /**
     * 是否开启gps
     * @return
     */
    public boolean isGpsSwitchOn() {
        return Secure.isLocationProviderEnabled(context.getContentResolver(),
                LocationManager.GPS_PROVIDER);
    }

    /**
     * gps开关
     */
    public void GpsSwitchUtils() {

        Secure.setLocationProviderEnabled(context.getContentResolver(),
                LocationManager.GPS_PROVIDER, !isGpsSwitchOn());

    }

    /**
     * 锁屏
     */
    public void lockScreenSwitchUtils() {
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
        }
        mPowerManager.newWakeLock(1,"");
    }

    /**
     * 重启
     */
    public void rebootUtils() {
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
        }
        mPowerManager.reboot(null);

    }

    /**
     * 关机
     */
    public void shutDownSwitchUtils() {
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 弹出系统内置的对话框，选择确定关机或取消关机
        context.startActivity(intent);


    }

    /**
     * 是否开启了闪光灯
     * @return
     */
    public boolean isFlashlightOn() {
        if (camera == null) {
            camera = Camera.open();
        }

        Parameters parameters = camera.getParameters();
        String flashMode = parameters.getFlashMode();

        if (flashMode.equals(Parameters.FLASH_MODE_TORCH)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * 闪光灯开关
     */
    public void flashlightUtils() {
        if (camera == null) {
            camera = Camera.open();
        }

        Parameters parameters = camera.getParameters();
        // String flashMode = parameters.getFlashMode();

        if (isFlashlightOn()) {

            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 关闭
            camera.setParameters(parameters);
            camera.release();
            camera = null;
            Toast.makeText(context, "关闭手电筒", Toast.LENGTH_SHORT).show();
        } else {
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
            camera.setParameters(parameters);
            Toast.makeText(context, "开启手电筒", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 闪光灯开关2
     */
    public void flashUtils() {

        Camera camera = Camera.open();

        Parameters parameters = camera.getParameters();
        String flashMode = parameters.getFlashMode();
        if (flashMode.equals(Parameters.FLASH_MODE_TORCH)) {
            camera.stopPreview();
            camera.release();
            camera = null;

        } else {

            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                }
            });
            camera.startPreview();
        }
    }

    /**
     * 跳转到系统设置
     */
    public void systemSetUtils() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 跳转到系统app管理
     */
    public void systemAppsUtils() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 跳转到系统显示设置
     */
    public void systemDisplayUtils() {
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 跳转到系统声音设置
     */
    public void systemSoundUtils() {
        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 跳转到系统日期设置
     */
    public void systemDateUtils() {
        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 跳转到系统位置设置
     */
    public void systemLocationUtils() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
    /**
     * 跳转到系统同步设置
     */
    public void systemSyncUtils() {
        Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
    /**
     * 跳转到系统输入设置
     */
    public void systemInputUtils() {
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }



    /**
     * 调大媒体音量
     */
    public void setMusicAudio() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
        }

        // int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM
        // );
        // int current = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM
        // );
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);

    }

}
