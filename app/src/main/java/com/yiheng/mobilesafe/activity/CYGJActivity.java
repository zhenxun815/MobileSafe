package com.yiheng.mobilesafe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.SMSListener;
import com.yiheng.mobilesafe.SettingItemView;
import com.yiheng.mobilesafe.service.Dog1Service;
import com.yiheng.mobilesafe.service.Dog2Service;
import com.yiheng.mobilesafe.utils.SMSUtils;
import com.yiheng.mobilesafe.utils.ServiceStateUtils;

import static android.os.Environment.MEDIA_MOUNTED;

public class CYGJActivity extends AppCompatActivity implements View.OnClickListener {
    private SettingItemView tools_query_location;
    private SettingItemView tools_query_numbers;
    private SettingItemView tools_sms_backup;
    private SettingItemView tools_sms_restore;
    private SettingItemView tools_app_lock;
    private SettingItemView tools_dog1;
    private SettingItemView tools_dog2;

    private String state;//SD卡状态
    private boolean isDog1Running;
    private boolean isDog2Running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cygj);
        initView();


    }

    private void initView() {
        tools_query_location = (SettingItemView) findViewById(R.id.tools_query_location);
        tools_query_numbers = (SettingItemView) findViewById(R.id.tools_query_numbers);
        tools_sms_backup = (SettingItemView) findViewById(R.id.tools_sms_backup);
        tools_sms_restore = (SettingItemView) findViewById(R.id.tools_sms_restore);
        tools_app_lock = (SettingItemView) findViewById(R.id.tools_app_lock);
        tools_dog1 = (SettingItemView) findViewById(R.id.tools_dog1);
        tools_dog2 = (SettingItemView) findViewById(R.id.tools_dog2);

        tools_query_location.setOnClickListener(this);
        tools_query_numbers.setOnClickListener(this);
        tools_sms_backup.setOnClickListener(this);
        tools_sms_restore.setOnClickListener(this);
        tools_app_lock.setOnClickListener(this);
        tools_dog1.setOnClickListener(this);
        tools_dog2.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isDog1Running = ServiceStateUtils.isRunning(getApplicationContext(), Dog1Service.class);
        tools_dog1.setToogleOn(isDog1Running);

        isDog2Running = ServiceStateUtils.isRunning(getApplicationContext(), Dog2Service.class);
        tools_dog2.setToogleOn(isDog2Running);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tools_query_location:
                break;
            case R.id.tools_query_numbers:
                break;
            case R.id.tools_sms_backup:

                state = Environment.getExternalStorageState();
                if (!TextUtils.equals(state, MEDIA_MOUNTED)) {
                    Toast.makeText(getApplicationContext(), "SD异常,无法备份", Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog progressDialog1 = new ProgressDialog(this);
                progressDialog1.setMessage("备份中...");
                progressDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog1.show();
                SMSUtils.backup(this, new SMSListener() {
                    @Override
                    public void onMax(int max) {

                        progressDialog1.setMax(max);
                    }

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "备份成功!", Toast.LENGTH_LONG).show();
                            }
                        });
                        progressDialog1.dismiss();
                    }

                    @Override
                    public void onProgress(int progerss) {
                        progressDialog1.setProgress(progerss);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "备份失败!", Toast.LENGTH_LONG).show();
                            }
                        });
                        progressDialog1.dismiss();
                    }
                });
                break;
            case R.id.tools_sms_restore:
                state = Environment.getExternalStorageState();
                if (!TextUtils.equals(state, MEDIA_MOUNTED)) {
                    Toast.makeText(getApplicationContext(), "SD异常,无法备份", Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog progressDialog2 = new ProgressDialog(this);
                progressDialog2.setMessage("还原中...");
                progressDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog2.show();
                SMSUtils.restore(this, new SMSListener() {
                    @Override
                    public void onMax(int max) {
                        progressDialog2.setMax(max);
                    }

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "还原成功", Toast.LENGTH_LONG).show();
                            }
                        });
                        progressDialog2.dismiss();
                    }

                    @Override
                    public void onProgress(int progerss) {
                        progressDialog2.setProgress(progerss);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "还原失败", Toast.LENGTH_LONG).show();
                            }
                        });
                        progressDialog2.dismiss();
                    }
                });
                break;
            case R.id.tools_app_lock:
                startActivity(new Intent(getApplicationContext(), CYGJAPPLockActivity.class));
                break;
            case R.id.tools_dog1:
                tools_dog1.toogle();
                Intent intentDog1 = new Intent(getApplicationContext(),Dog1Service.class);
                if (isDog1Running){
                    stopService(intentDog1);
                }else {
                    startService(intentDog1);
                }
                break;
            case R.id.tools_dog2:
                tools_dog2.toogle();
                Intent intentDog2 = new Intent(getApplicationContext(),Dog2Service.class);
                if (isDog1Running){
                    stopService(intentDog2);
                }else {
                    startService(intentDog2);
                }
                break;
        }
    }
}
