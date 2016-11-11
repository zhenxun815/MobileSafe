package com.yiheng.mobilesafe.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.yiheng.mobilesafe.activity.CYGJLockScreenActivity;
import com.yiheng.mobilesafe.db.ApplockDAO;
import com.yiheng.mobilesafe.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

public class Dog1Service extends Service {

    private ActivityManager activityManager;
    private ApplockDAO applockDAO;
    private ArrayList<String> lockedList;
    private ArrayList<String> unlockedApps;
    private boolean isRunning;
    private ContentObserver myObserver = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            lockedList = applockDAO.queryLockedList();

        }
    };
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Intent.ACTION_SCREEN_OFF:
                    isRunning = false;
                    System.out.println(unlockedApps.size());
                    unlockedApps.clear();
                    System.out.println(unlockedApps.size());
                    break;
                case Intent.ACTION_SCREEN_ON:
                    startDog1();
                    break;
                case ConstantUtils.ACTION_APPUNLOCK:
                    String packageName = intent.getStringExtra("packageName");
                    unlockedApps.add(packageName);
                    break;
            }
        }
    };

    public Dog1Service() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        applockDAO = new ApplockDAO(getApplicationContext());
        lockedList = applockDAO.queryLockedList();
        unlockedApps = new ArrayList<>();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantUtils.ACTION_APPUNLOCK);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myReceiver, filter);

        Uri uri = Uri.parse(ConstantUtils.APPLOCK_DB_CHANGED);
        getContentResolver().registerContentObserver(uri, true, myObserver);

        startDog1();
    }

    private void startDog1() {
        isRunning = true;
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    //获取栈顶应用,即刚打开的应用
                    String topPackageName = "";

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        //只适用于5.0之前
                        List<ActivityManager.RunningTaskInfo> runningTasks =
                                activityManager.getRunningTasks(1);
                        topPackageName = runningTasks.get(0).topActivity.getPackageName();
                    } else {
                        UsageStatsManager usageStatsManager =
                                (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
                        if (usageStatsManager != null) {
                            long now = System.currentTimeMillis();
                            //获取60秒之内的应用数据
                            List<UsageStats> stats =
                                    usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);

                            //取得最近运行的一个app，即当前运行的app
                            if ((stats != null) && (!stats.isEmpty())) {
                                int j = 0;
                                for (int i = 0; i < stats.size(); i++) {
                                    if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                                        j = i;
                                    }
                                }
                                topPackageName = stats.get(j).getPackageName();
                            }
                        }

                    }
                    //  System.out.println("-----------------" + topPackageName + "-----------------");

                    if (unlockedApps.contains(topPackageName)) {
                        continue;
                    }

                    if (lockedList.contains(topPackageName) &&
                            (!TextUtils.equals(topPackageName, "com.yiheng.mobilesafe"))) {

                        Intent intent = new Intent(Dog1Service.this, CYGJLockScreenActivity.class);
                        intent.putExtra("packageName", topPackageName);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                SystemClock.sleep(1000);
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        getContentResolver().unregisterContentObserver(myObserver);
        unregisterReceiver(myReceiver);
    }
}
