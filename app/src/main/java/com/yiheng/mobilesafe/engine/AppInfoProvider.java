package com.yiheng.mobilesafe.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.yiheng.mobilesafe.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yiheng on 2016/11/9 0009.
 */

public class AppInfoProvider {
    public static ArrayList<AppInfo> getAppInfo(Context context) {
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);


        for (PackageInfo packageInfo : packages) {

            String packageName = packageInfo.packageName;
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            Drawable icon = applicationInfo.loadIcon(packageManager);
            String name = applicationInfo.loadLabel(packageManager).toString();

            String sourceDir = applicationInfo.sourceDir;
            File file = new File(sourceDir);
            long size = file.length();

            int flag = applicationInfo.flags;

            boolean isSys = false;
            if ((applicationInfo.FLAG_SYSTEM & flag) == applicationInfo.FLAG_SYSTEM) {
                isSys = true;
            }

            boolean isInstallSd = false;
            if ((applicationInfo.FLAG_EXTERNAL_STORAGE& flag)==applicationInfo.FLAG_EXTERNAL_STORAGE){
                isInstallSd = true;
            }

            AppInfo appInfo = new AppInfo(name, icon, isInstallSd, size, isSys, packageName);
            appInfos.add(appInfo);
        }

        return appInfos;
    }
}
