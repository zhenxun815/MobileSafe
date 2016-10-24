package com.yiheng.mobilesafe;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_version = (TextView) findViewById(R.id.tv_version);
        // 获取版本名字
        String versionName = getVersionName();
        tv_version.setText(versionName);

        // 联网检查更新

        // 1.去服务器获取更新信息 versionCode versionName 描述 下载地址 数据用json数据放到服务器上
        // 2.对比versionCode 拿本地的versionCode与服务器的对比 如果比服务器的小 说明有新版本 弹出提示框 如果没有新版本
        // 进入主页面
        // 3.用户点击提示框 下载 去下载新的apk 点击取消 进入主页面
        // 4. 下载完apk 去安装

        // HttpURLConnection 用xutils访问网络

        // 注意权限 <uses-permission android:name="android.permission.INTERNET" />
        // <uses-permission
        // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
