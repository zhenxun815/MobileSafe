package com.yiheng.mobilesafe.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Yiheng on 2016/11/4 0004.
 */

public class ServiceStateUtils {
    public static boolean isRunning(Context context, Class<? extends Service> clazz) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> runningServices =
                manager.getRunningServices(1000);

        for (RunningServiceInfo infos : runningServices) {
            ComponentName service = infos.service;
            String className = service.getClassName();
            String clazzName = clazz.getName();
            if (TextUtils.equals(className,clazzName)){
                return true;
            }
        }
        return false;
    }
}
