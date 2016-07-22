package com.app.mymusic.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ExecutorServiceUtil {

    public   ExecutorService getSingleThread( ) {
        ExecutorService mService= Executors.newSingleThreadExecutor();


     return mService;

    }
    public  ExecutorService getScheduledThreadPool(int count,int delay,int every)
    {
        ScheduledExecutorService  mService=Executors.newScheduledThreadPool(count);
//        mService.scheduleAtFixedRate()
        return mService;
    }




}
