package com.yadea.launcher.util;

import android.os.Process;
import android.util.Log;

public class LogUtils {
    private static  boolean s_isDebug = true;

    private static String createLog(String log ,String className, String methodName, int linNumber) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(Process.myTid());
        buffer.append("]");
        buffer.append("[");
        buffer.append(linNumber);
        buffer.append("]");
        buffer.append("[");
        buffer.append(className);
        buffer.append("::").append(methodName).append("]").append(" ");
        buffer.append(log);
        return buffer.toString();
    }

    public static void e(String TAG, String message){
        if (!s_isDebug) {
            return;
        }
        StackTraceElement[] sElements = new Throwable().getStackTrace();
        String className = sElements[1].getClassName();
        String methodName = sElements[1].getMethodName();
        int lineNumber = sElements[1].getLineNumber();
        Log.e(TAG, createLog(message,className.substring(className.lastIndexOf('.') + 1),methodName,lineNumber));
    }

    public static void i(String TAG, String message){
        if (!s_isDebug) {
            return;
        }
        StackTraceElement[] sElements = new Throwable().getStackTrace();
        String className = sElements[1].getClassName();
        String methodName = sElements[1].getMethodName();
        int lineNumber =sElements[1].getLineNumber();
        Log.i(TAG, createLog(message,className.substring(className.lastIndexOf('.') + 1),methodName,lineNumber));
    }

    public static void d(String TAG, String message){
        if (!s_isDebug) {
            return;
        }
        StackTraceElement[] sElements = new Throwable().getStackTrace();
        String className = sElements[1].getClassName();
        String methodName = sElements[1].getMethodName();
        int lineNumber =sElements[1].getLineNumber();
        Log.d(TAG, createLog(message,className.substring(className.lastIndexOf('.') + 1),methodName,lineNumber));
    }

    public static void v(String TAG, String message){
        if (!s_isDebug) {
            return;
        }
        StackTraceElement[] sElements = new Throwable().getStackTrace();
        String className = sElements[1].getClassName();
        String methodName = sElements[1].getMethodName();
        int lineNumber =sElements[1].getLineNumber();
        Log.v(TAG, createLog(message,className.substring(className.lastIndexOf('.') + 1),methodName,lineNumber));
    }

    public static void w(String TAG, String message){
        if (!s_isDebug) {
            return;
        }
        StackTraceElement[] sElements = new Throwable().getStackTrace();
        String className = sElements[1].getClassName();
        String methodName = sElements[1].getMethodName();
        int lineNumber =sElements[1].getLineNumber();
        Log.w(TAG, createLog(message,className.substring(className.lastIndexOf('.') + 1),methodName,lineNumber));
    }
}
