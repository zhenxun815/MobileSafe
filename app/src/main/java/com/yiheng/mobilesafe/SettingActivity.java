package com.yiheng.mobilesafe;

import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private SettingItemView setting_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting_update = (SettingItemView) findViewById(R.id.setting_update);

        setting_update.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_update:

        }
    }
}
