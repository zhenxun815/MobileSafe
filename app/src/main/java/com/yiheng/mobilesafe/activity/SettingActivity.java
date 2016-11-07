package com.yiheng.mobilesafe.activity;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.SettingItemView;
import com.yiheng.mobilesafe.service.CallSMSService;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.ServiceStateUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingItemView setting_update;//自动更新
    private SettingItemView setting_forbiddenlist;//骚扰拦截

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //手机防盗
        setting_update = (SettingItemView) findViewById(R.id.setting_update);
        setting_update.setOnClickListener(this);
        boolean isAutoUpdate = SharedPreferenceUtils
                .getBoolean(getApplicationContext(), ConstantUtils.AUTO_UPDATE, false);

        setting_update.setToogleOn(isAutoUpdate);
        //骚扰拦截
        setting_forbiddenlist = (SettingItemView) findViewById(R.id.setting_forbiddenlist);
        setting_forbiddenlist.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isForbiddenOn =  ServiceStateUtils.isRunning(getApplicationContext(), CallSMSService.class);
        setting_forbiddenlist.setToogleOn(isForbiddenOn);
        System.out.println("------------------ setting activity start ------------------" );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_update:
                //改变开关状态
                setting_update.toogle();
                SharedPreferenceUtils
                        .putBoolean(getApplicationContext(), ConstantUtils.AUTO_UPDATE, setting_update.isToogleOn());

                break;
            case R.id.setting_forbiddenlist:
                setting_forbiddenlist.toogle();
                Intent intent = new Intent(getApplicationContext(), CallSMSService.class);
                boolean running = ServiceStateUtils.isRunning(getApplicationContext(), CallSMSService.class);
                if (running) {
                    stopService(intent);
                    System.out.println("_________________service is not running_________________");

                } else {
                    startService(intent);
                    System.out.println("_________________service is running_________________");

                }

                break;

        }
    }
}
