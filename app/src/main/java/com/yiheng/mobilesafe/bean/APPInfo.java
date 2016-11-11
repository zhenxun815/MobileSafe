package com.yiheng.mobilesafe.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Yiheng on 2016/11/9 0009.
 */

public class AppInfo {
    public String name;
    public Drawable icon;// 图片
    public boolean isInstallSd;// 是否安装在sd卡
    public long size;// 安装包的大小
    public boolean isSys;// 是否是系统程序
    public String packageName;//包名

    public AppInfo() {
    }

    public AppInfo(String name, Drawable icon, boolean isInstallSd, long size, boolean isSys, String packageName) {

        this.name = name;
        this.icon = icon;
        this.isInstallSd = isInstallSd;
        this.size = size;
        this.isSys = isSys;
        this.packageName = packageName;
    }
}
