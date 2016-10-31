package com.yiheng.mobilesafe.activity;

import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.SettingItemView;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private SettingItemView setting_update;//自动更新
    private SettingItemView setting_blacklist;//骚扰拦截

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting_update = (SettingItemView) findViewById(R.id.setting_update);
        setting_blacklist = (SettingItemView) findViewById(R.id.setting_blacklist);
        //// TODO: 2016/10/30 0030 开启骚然拦截

        setting_update.setOnClickListener(this);
        Boolean isAutoUpdate = SharedPreferenceUtils
                .getBoolean(getApplicationContext(), ConstantUtils.AUTO_UPDATE, false);

        setting_update.setToogleOn(isAutoUpdate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_update:
                //改变开关状态
                setting_update.toogle();
                SharedPreferenceUtils
                        .putBoolean(getApplicationContext(),ConstantUtils.AUTO_UPDATE,setting_update.isToogleOn());
                break;
        }
    }
}
