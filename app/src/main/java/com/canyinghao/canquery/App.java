package com.canyinghao.canquery;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaApplication;
import com.canyinghao.canhelper.CanHelper;
import com.canyinghao.canhelper.FileHelper;
import com.canyinghao.canhelper.LogHelper;
import com.thinkland.sdk.android.SDKInitializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import butterknife.ButterKnife;

public class App extends FrontiaApplication implements Thread.UncaughtExceptionHandler {

    private static App app;


    @Override
    public void onCreate() {


        super.onCreate();


        app = this;
        ButterKnife.setDebug(BuildConfig.DEBUG);
        LogHelper.DEBUG = BuildConfig.DEBUG;
        SDKInitializer.initialize(getApplicationContext());
        CanHelper.init(this);
//		ANRWatchDog anrWatchDog = new ANRWatchDog(2000);
//		anrWatchDog.start();


        Frontia.init(this.getApplicationContext(), "kRfd5zOLe2VUzjQglNKKsXrc");
        File file = new File(FileHelper.getInstance().getExternalStorePath(),
                "canquery");


        if (!file.exists()) {
            file.mkdirs();
        }
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;

    }

    public static App getContext() {
        if (app == null) {
            app = new App();
        }
        return app;

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        String eStr = getCrashReport(ex);
        LogHelper.loge("UncaughtException", eStr);
        try {
            File file = new File(FileHelper.getInstance()
                    .getExternalStorePath(), "canquery/faillog.txt");
            FileOutputStream f = new FileOutputStream(file);
            f.write(eStr.getBytes());
            f.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.exit(0);
    }

    private String getCrashReport(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String expcetionStr = sw.toString();
        try {
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.close();
        return expcetionStr;
    }
}
