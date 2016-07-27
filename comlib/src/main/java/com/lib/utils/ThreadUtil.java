package com.lib.utils;

import android.content.Context;
import android.os.Looper;

/**
 * Created by shenwenjie on 27/7/2016.
 */
public class ThreadUtil {

    /**
     * 通过looper对象判断是否是主线程
     * @return
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 通过Thread对象判断是否是主线程
     * @return
     */
    public static boolean isMainThread2() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
