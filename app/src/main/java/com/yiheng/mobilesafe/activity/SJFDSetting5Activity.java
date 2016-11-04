package com.yiheng.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class SJFDSetting5Activity extends SJFDBaseActivity {
    private CheckBox cbx_opensjfd;
    private Button btn_next;
    private Button btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfdsetting5);
        cbx_opensjfd = (CheckBox) findViewById(R.id.cbx_opensjfd);
        cbx_opensjfd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferenceUtils
                        .putBoolean(getApplicationContext(), ConstantUtils.SJFD_SET_ON,isChecked);
            }
        });
        btn_next = (Button) findViewById(R.id.btn_sjfd_setting5_next);
        btn_pre = (Button) findViewById(R.id.btn_sjfd_setting5_pre);

        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextClick(v);
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                preClick(v);
            }
        });
    }

    @Override
    protected boolean toPrePage() {
        startActivity(new Intent(getApplicationContext(), SJFDSetting4Activity.class));
        return true;
    }

    @Override
    protected boolean toNextPage() {
        Boolean isSetOn = SharedPreferenceUtils
                .getBoolean(getApplicationContext(), ConstantUtils.SJFD_SET_ON, false);

        if (isSetOn) {
            startActivity(new Intent(getApplicationContext(), SJFDActivity.class));
            return true;
        } else {
            Toast.makeText(getApplicationContext(),"必须选中开启放到保护",Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
