package com.canyinghao.canhelper;

import android.util.Log;

/**
 * 日志打印工具类
 *
 * @author canyinghao
 *
 */
public class LogHelper {

    public static String TAG = "LogHelper";
    public static boolean DEBUG = true;

    public static void logd(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.d(TAG, log);
        }

    }

    public static void logd(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.d(tag, log);
        }

    }

    public static void loge(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.e(className, log);
        }

    }

    public static void loge(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.e(TAG, log);

        }

    }

    public static void loge(String tag, String log, Throwable tb) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.e(tag, log, tb);

        }

    }

    public static void logi(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.i(TAG, log);
        }

    }

    public static void logi(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.i(tag, log);
        }
    }

    public static void logv(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.v(TAG, log);
        }
    }

    public static void logv(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.v(tag, log);
        }

    }

    public static void logw(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.w(TAG, log);
        }

    }

    public static void logw(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.w(tag, log);
        }
    }

}
