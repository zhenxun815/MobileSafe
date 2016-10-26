package com.yiheng.mobilesafe;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Yiheng on 2016/10/25 0025.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        x.Ext.init(this);
    }
}
