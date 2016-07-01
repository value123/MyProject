package com.lib.utils;

import android.os.AsyncTask;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenwenjie on 29/6/2016.
 */
public class AsynTaskExcutorUtil {
    //根据系统资源数判断最大并发线程数量
    public static int ThreadPoolSize = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(ThreadPoolSize);
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(ThreadPoolSize);


    public static void execute(AsyncTask task){
        task.executeOnExecutor(executor);
    }

    public static void executeRunnable(Runnable runnable){
        scheduledThreadPool.schedule(runnable,0, TimeUnit.SECONDS);
    }
}
