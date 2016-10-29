package com.yiheng.mobilesafe.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yiheng.mobilesafe.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;
    private String mVersionName;
    private String downloadurl;
    private String versionName;
    private String mDesc;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 获取版本名字
        mVersionName = getVersionName();
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(mVersionName);
        checkVersion();


    }

    // 联网检查更新
    private void checkVersion() {

        RequestParams params = new RequestParams("http://10.0.2.2:8080/update.json");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int versionCode = jsonObject.getInt("versionCode");
                    System.out.println(versionCode+"___________________________");
                    System.out.println(getVersionCode()+"___________________________");
                    versionName = jsonObject.getString("versionName");
                    mDesc = jsonObject.getString("desc");
                    downloadurl = jsonObject.getString("downloadurl");

                    if (getVersionCode() < versionCode) {
                        shouUpdateDialog();
                    }else{
                        enterHomepage();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                enterHomepage();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    //进入主页面
    private void enterHomepage() {
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent);
        finish();
    }

    //弹出下载更新提示框
    private void shouUpdateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("更新提示");
        builder.setMessage(mDesc);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                downlaodAPK();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHomepage();
            }
        });
        builder.show();

    }

    //下载新版本apk
    private void downlaodAPK() {
        //判断SD卡状态
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //下载
        RequestParams params = new RequestParams(downloadurl);
        System.out.println("_____________"+downloadurl);
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/mobilesafe.apk");
        System.out.println(Environment.getExternalStorageDirectory()+"mobilesafe.apk");
        params.setAutoRename(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                //添加进度条
                System.out.println("______________________________starting1");

                progressDialog = new ProgressDialog(SplashActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("下载中...");
                progressDialog.setProgress(0);
                progressDialog.show();
                System.out.println("______________________________starting2");

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                System.out.println("______________________________loading");
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {

                System.out.println("______________success___________");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                //安装成功后提示是否打开apk
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");

                startActivity(intent);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

               // Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_LONG).show();
                System.out.println("_________________________________error");
                enterHomepage();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                enterHomepage();

            }

            @Override
            public void onFinished() {
                progressDialog.dismiss();
            }

        });
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
